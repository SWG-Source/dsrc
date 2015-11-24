package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class stormtrooper_crate extends script.theme_park.poi.base
{
    public stormtrooper_crate()
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
            obj_id st1 = poiCreateNpc("stormtrooper", 1, 1);
            obj_id st2 = poiCreateNpc("stormtrooper", 2, 2);
            obj_id crate = poiCreateObject(self, "crate", "object/tangible/test/test_barrel_a.iff", 0, 1);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
