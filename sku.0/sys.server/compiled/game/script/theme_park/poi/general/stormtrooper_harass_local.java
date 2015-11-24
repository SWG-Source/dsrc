package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class stormtrooper_harass_local extends script.theme_park.poi.base
{
    public stormtrooper_harass_local()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("troopers"))
        {
            debugSpeakMsg(self, "In Oninitialize");
            obj_id victim = poiCreateNpc(self, "townsperson", 5, 5);
            setHealth(victim, 10000);
            obj_id st1 = poiCreateNpc("stormtrooper", 6, 6);
            obj_id st2 = poiCreateNpc("stormtrooper", 4, 4);
            startCombat(st1, victim);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
