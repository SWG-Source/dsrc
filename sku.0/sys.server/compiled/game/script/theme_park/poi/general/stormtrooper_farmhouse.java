package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class stormtrooper_farmhouse extends script.theme_park.poi.base
{
    public stormtrooper_farmhouse()
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
            obj_id farm = poiCreateObject(self, "farm", "object/building/tatooine/housing_tatt_style01_small.iff", 10, 10);
            obj_id victim = poiCreateNpc(self, "farmer", "farmer", 2, 2);
            setHealth(victim, 10000);
            obj_id st1 = poiCreateNpc("stormtrooper", 3, 3);
            obj_id st2 = poiCreateNpc("stormtrooper", 1, 1);
            startCombat(st1, victim);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
