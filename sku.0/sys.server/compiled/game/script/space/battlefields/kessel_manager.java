package script.space.battlefields;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.group;
import script.library.space_utils;
import script.library.space_create;
import script.library.load_test;
import script.library.prose;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_transition;
import script.library.utils;
import script.library.space_battlefield;
import java.lang.Math;

public class kessel_manager extends script.base_script
{
    public kessel_manager()
    {
    }
    public static String RETAIN_TABLE = "datatables/space_content/battlefields/kessel_no_destroy.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlerInitialize", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "intPhase", 0);
        obj_id objKesselObject = getPlanetByName("space_light1");
        LOG("space", "setting scriptVar of " + self + " on " + objKesselObject);
        utils.setScriptVar(objKesselObject, "objKesselManager", self);
        messageTo(self, "handlerInitialize", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int changeSpawnerPhase(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space", "Changing phase");
        int intPhase = getIntObjVar(self, "intPhase");
        int intNewPhase = params.getInt("intCompletionId");
        if (intPhase == intNewPhase)
        {
            LOG("space", "no change of kessel");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "intPhase", intNewPhase);
        obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/tangible/space/content_infrastructure/basic_spawner.iff");
        obj_id[] objShips = getObjectsInRange(getLocation(self), 320000);
        String[] noDeleteShipTypes = dataTableGetStringColumn(RETAIN_TABLE, "strShipType");
        String[] noReSpawnSpawnerNames = dataTableGetStringColumn(RETAIN_TABLE, "strSpawnerName");
        for (int intI = 0; intI < objTestObjects.length; intI++)
        {
            boolean foundInList = false;
            String spawnerType = getStringObjVar(objTestObjects[intI], "strSpawnerType");
            for (int j = 0; j < noReSpawnSpawnerNames.length; ++j)
            {
                if (hasObjVar(objTestObjects[intI], "strSpawnerName"))
                {
                    if (!spawnerType.equals("asteroid"))
                    {
                        if (exists(objTestObjects[intI]))
                        {
                            String spawnerName = getStringObjVar(objTestObjects[intI], "strSpawnerName");
                            if (spawnerName.equals(noReSpawnSpawnerNames[j]))
                            {
                                foundInList = true;
                            }
                        }
                    }
                }
            }
            if (foundInList == false && !spawnerType.equals("asteroid"))
            {
                messageTo(objTestObjects[intI], "startSpawning", null, intI, false);
            }
        }
        for (int k = 0; k < objShips.length; ++k)
        {
            boolean isNoDeleteShip = false;
            if (hasObjVar(objShips[k], "ship.shipName"))
            {
                for (int l = 0; l < noDeleteShipTypes.length; ++l)
                {
                    if (exists(objShips[k]))
                    {
                        String shipName = getStringObjVar(objShips[k], "ship.shipName");
                        if (shipName.equals(noDeleteShipTypes[l]))
                        {
                            isNoDeleteShip = true;
                        }
                    }
                }
                if (isNoDeleteShip == false)
                {
                    destroyObject(objShips[k]);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objKesselObject = getPlanetByName("space_light1");
        LOG("space", "setting scriptVar of " + self + " on " + objKesselObject);
        utils.setScriptVar(objKesselObject, "objKesselManager", self);
        setObjVar(self, "intPhase", 0);
        return SCRIPT_CONTINUE;
    }
}
