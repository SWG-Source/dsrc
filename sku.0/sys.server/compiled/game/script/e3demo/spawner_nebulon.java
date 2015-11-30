package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.space_combat;
import script.library.space_create;

public class spawner_nebulon extends script.base_script
{
    public spawner_nebulon()
    {
    }
    public static final String[] ATTACKER_LIST = 
    {
        "tiefighter",
        "tiefighter",
        "tiefighter",
        "tiefighter",
        "tiefighter",
        "tiefighter",
        "tiefighter",
        "tieinterceptor",
        "tieinterceptor",
        "tieinterceptor",
        "tiebomber",
        "tiebomber",
        "tiebomber",
        "tiebomber"
    };
    public static final String[] DEFENDER_LIST = 
    {
        "xwing",
        "xwing",
        "xwing",
        "xwing",
        "ywing_average",
        "ywing_average",
        "ywing_average"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "intNoDump");
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int intWeaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int spawnNebulon(obj_id self, dictionary params) throws InterruptedException
    {
        transform trMyTransform = getTransform_o2w(self);
        vector vctTest = new vector(100f, 100f, 100f);
        transform trNewTransform = trMyTransform.move_l(vctTest);
        obj_id objNewNebulon = space_create.createShip("corellian_corvette", trNewTransform, null);
        space_utils.setComponentDisabled(objNewNebulon, ship_chassis_slot_type.SCST_engine, true);
        space_utils.setComponentDisabled(self, ship_chassis_slot_type.SCST_engine, true);
        setObjVar(self, "objBuddy", objNewNebulon);
        setObjVar(objNewNebulon, "intNebulon", 1);
        setObjVar(objNewNebulon, "intNoDump", 1);
        attachScript(objNewNebulon, "e3demo.nebulon_damaged");
        dictionary dctParams = new dictionary();
        space_utils.notifyObject(self, "startNebulonAttack", dctParams);
        return SCRIPT_CONTINUE;
    }
    public int startNebulonAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objFoo = null;
        obj_id objFoo2 = null;
        for (int intI = 0; intI < DEFENDER_LIST.length; intI++)
        {
            transform trNewTransform = space_utils.getRandomPositionInSphere(getTransform_o2w(self), 100, 150, true);
            obj_id objShip = space_create.createShip(DEFENDER_LIST[intI], trNewTransform, null);
            if (isIdValid(objShip))
            {
                objFoo = objShip;
                setObjVar(objShip, "intNebulon", 1);
                setObjVar(objShip, "strType", DEFENDER_LIST[intI]);
                setObjVar(objShip, "trSpawnLocation", trNewTransform);
                setObjVar(objShip, "intNoDump", 1);
                setObjVar(objShip, "objNebulon", self);
                attachScript(objShip, "e3demo.nebulon_child_object");
            }
            else 
            {
                LOG("space", "BAD DEFENDER NEBULON E3 SHIP OF TYPE " + DEFENDER_LIST[intI]);
            }
        }
        for (int intI = 0; intI < ATTACKER_LIST.length; intI++)
        {
            transform trNewTransform = space_utils.getRandomPositionInSphere(getTransform_o2w(self), 500, 600, true);
            obj_id objShip = space_create.createShip(ATTACKER_LIST[intI], trNewTransform, null);
            if (isIdValid(objShip))
            {
                objFoo2 = objShip;
                setObjVar(objShip, "intNebulon", 1);
                setObjVar(objShip, "intAttacker", 1);
                setObjVar(objShip, "strType", ATTACKER_LIST[intI]);
                setObjVar(objShip, "trSpawnLocation", trNewTransform);
                setObjVar(objShip, "intNoDump", 1);
                setObjVar(objShip, "objNebulon", self);
                attachScript(objShip, "e3demo.nebulon_child_object");
                obj_id objBuddy = getObjIdObjVar(self, "objBuddy");
                ship_ai.spaceAttack(objShip, objBuddy);
            }
            else 
            {
                LOG("space", "BAD DEFENDER NEBULON E3 SHIP OF TYPE " + DEFENDER_LIST[intI]);
            }
        }
        ship_ai.spaceAttack(objFoo, objFoo2);
        return SCRIPT_CONTINUE;
    }
    public int shipDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        String strType = params.getString("strType");
        transform trSpawnLocation = params.getTransform("trSpawnLocation");
        int intAttacker = params.getInt("intAttacker");
        obj_id objShip = space_create.createShip(strType, trSpawnLocation, null);
        if (isIdValid(objShip))
        {
            setObjVar(objShip, "intNebulon", 1);
            setObjVar(objShip, "intAttacker", intAttacker);
            setObjVar(objShip, "strType", strType);
            setObjVar(objShip, "trSpawnLocation", trSpawnLocation);
            setObjVar(objShip, "intNoDump", 1);
            attachScript(objShip, "e3demo.nebulon_child_object");
            if (intAttacker == 1)
            {
                ship_ai.spaceAttack(objShip, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
