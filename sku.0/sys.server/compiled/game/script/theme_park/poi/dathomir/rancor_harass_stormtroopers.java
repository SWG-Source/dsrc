package script.theme_park.poi.dathomir;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class rancor_harass_stormtroopers extends script.theme_park.poi.base
{
    public rancor_harass_stormtroopers()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "First POI Test Attached");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            debugSpeakMsg(self, "In Oninitialize");
            obj_id rancor = poiCreateObject(self, "rancor", "object/creature/monster/rancor/rancor.iff", 0, 0);
            obj_id st1 = poiCreateNpc("stormtrooper", 7, 5);
            obj_id st2 = poiCreateNpc("stormtrooper", 5, 5);
            obj_id st3 = poiCreateNpc("stormtrooper", 7, 7);
            obj_id st4 = poiCreateNpc("stormtrooper", 5, 7);
            dictionary parms = new dictionary();
            parms.put("rancor", rancor);
            messageTo(self, "attackRancor", parms, 10, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int attackRancor(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id rancor = params.getObjId("rancor");
        startCombat(self, rancor);
        return SCRIPT_CONTINUE;
    }
}
