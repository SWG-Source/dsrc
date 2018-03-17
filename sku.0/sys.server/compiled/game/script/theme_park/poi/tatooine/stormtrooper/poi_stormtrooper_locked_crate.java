package script.theme_park.poi.tatooine.stormtrooper;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.weapons;

public class poi_stormtrooper_locked_crate extends script.theme_park.poi.base
{
    public poi_stormtrooper_locked_crate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            obj_id tr1 = poiCreateNpc("st1", "stormtrooper", 2, 0);
            obj_id tr2 = poiCreateNpc("st2", "stormtrooper", 1, 1);
            obj_id tr3 = poiCreateNpc("st3", "stormtrooper", 1, -1);
            attachScript(tr1, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr2, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            attachScript(tr3, "theme_park.poi.tatooine.behavior.stormtrooper_stationary_poi");
            poiSetDestroyMessage(tr1, "trooperKilled");
            poiSetDestroyMessage(tr2, "trooperKilled");
            poiSetDestroyMessage(tr3, "trooperKilled");
            obj_id crate = poiCreateObject(self, "center", "object/tangible/container/drum/tatt_drum_1.iff", 1, 0);
            debugSpeakMsg(crate, "I should be LOCKED!");
            int treasureItems = rand(1, 3);
            int treasureValue = rand(1, 2);
            treasureValue = (treasureValue - 1);
            int intX = 1;
            int treasureRows = dataTableGetNumRows("datatables/poi/treasure.iff");
            while (intX <= treasureItems)
            {
                int randItem = rand(1, treasureRows);
                randItem = (randItem - 1);
                String treasure = dataTableGetString("datatables/poi/treasure.iff", randItem, treasureValue);
                obj_id treasureItem = weapons.createPossibleWeapon(treasure, crate, rand(0.5f, 0.85f));
                intX = intX + 1;
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int trooperKilled(obj_id self, dictionary params) throws InterruptedException
    {
        int numKilled = getIntObjVar(self, "numTroopersKilled");
        numKilled++;
        if (numKilled > 1)
        {
            poiComplete(POI_SUCCESS);
        }
        setObjVar(self, "numTroopersKilled", numKilled);
        return SCRIPT_CONTINUE;
    }
}
