package script.theme_park.poi.corellia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;

public class victim_sandpanther extends script.theme_park.poi.base
{
    public victim_sandpanther()
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
        if (objective.equals("victim"))
        {
            debugSpeakMsg(self, "In Oninitialize");
            obj_id victim = poiCreateNpc(self, "townsperson", 5, 5);
            setHealth(victim, 10000);
            obj_id panther = poiCreateObject(self, "panther", "object/creature/monster/corellian_sand_panther/corellian_sand_panther.iff", 6, 6);
            startCombat(panther, victim);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
