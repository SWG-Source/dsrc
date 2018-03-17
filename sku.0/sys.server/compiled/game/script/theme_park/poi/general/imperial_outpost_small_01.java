package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class imperial_outpost_small_01 extends script.theme_park.poi.base
{
    public imperial_outpost_small_01()
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
            obj_id tower = poiCreateObject(self, "tower", "object/installation/turret/turret_dish_sm.iff", 1, 8);
            obj_id cmdr = poiCreateObject(self, "cmdr", "object/creature/npc/theme_park/prefect_talmont.iff", 0, 0);
            setName(cmdr, "Corporal Jenzen");
            obj_id st1 = poiCreateNpc("stormtrooper", 7, 5);
            obj_id st2 = poiCreateNpc("stormtrooper", 5, 7);
            obj_id st3 = poiCreateNpc("stormtrooper", 5, 5);
            obj_id st4 = poiCreateNpc("stormtrooper", 7, 7);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
