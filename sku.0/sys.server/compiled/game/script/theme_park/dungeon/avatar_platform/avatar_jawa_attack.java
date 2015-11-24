package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.create;
import script.library.utils;
import script.library.chat;
import script.ai.ai_combat;

public class avatar_jawa_attack extends script.base_script
{
    public avatar_jawa_attack()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        obj_id structure = getTopMostContainer(self);
        setObjVar(structure, "avatar_platform.jawa_attacker", self);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("attackPoint"))
        {
            aiEquipPrimaryWeapon(self);
            obj_id structure = getTopMostContainer(self);
            obj_id target = getObjIdObjVar(structure, "avatar_platform.wke_prisoner_06");
            addHate(self, target, 1000);
            messageTo(target, "handleDeathByJawa", null, 5f, false);
            messageTo(self, "handleReturnHome", null, 10f, false);
        }
        if (name.equals("chatterPoint"))
        {
            obj_id player = getObjIdObjVar(self, "summoner");
            playClientEffectLoc(player, "clienteffect/jawa_chatter_01.cef", getLocation(self), 0f);
            obj_id structure = getTopMostContainer(self);
            obj_id cellDoor = getObjIdObjVar(structure, "avatar_platform.cell_door_06");
            destroyObject(cellDoor);
            messageTo(self, "handleJawaDeath", null, 5f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSummonJawa(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        obj_id player = params.getObjId("player");
        setObjVar(self, "summoner", player);
        setObjVar(structure, "avatar_platform.wke_completed_06", 1);
        obj_id destination = getCellId(structure, "jails");
        location here = getLocation(self);
        String planet = here.area;
        location chatterPoint = new location(-171.7f, 0, -155.5f, planet, destination);
        ai_lib.aiPathTo(self, chatterPoint);
        addLocationTarget("chatterPoint", chatterPoint, 1);
        return SCRIPT_CONTINUE;
    }
    public int handleJawaDeath(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id structure = getTopMostContainer(self);
        obj_id destination = getCellId(structure, "jails");
        location here = getLocation(self);
        String planet = here.area;
        location attackPoint = new location(-181.0f, 0, -155.5f, planet, destination);
        ai_lib.aiPathTo(self, attackPoint);
        addLocationTarget("attackPoint", attackPoint, 1);
        return SCRIPT_CONTINUE;
    }
    public int handleReturnHome(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        obj_id structure = getTopMostContainer(self);
        obj_id destination = getCellId(structure, "securityoffice");
        location here = getLocation(self);
        String planet = here.area;
        location home = new location(-160.8f, 0, -124.7f, planet, destination);
        ai_lib.aiPathTo(self, home);
        return SCRIPT_CONTINUE;
    }
}
