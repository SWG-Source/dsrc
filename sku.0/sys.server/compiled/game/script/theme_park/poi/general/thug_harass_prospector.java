package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class thug_harass_prospector extends script.theme_park.poi.base
{
    public thug_harass_prospector()
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
        if (objective.equals("thug"))
        {
            debugSpeakMsg(self, "In Oninitialize");
            obj_id victim = poiCreateNpc(self, "townie", "townsperson", 5, 5);
            setHealth(victim, 10000);
            obj_id thug1 = poiCreateNpc(self, "thug1", "thug", 6, 6);
            obj_id thug2 = poiCreateNpc(self, "thug2", "thug", 4, 4);
            startCombat(thug1, victim);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
