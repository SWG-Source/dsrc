package script.systems.spawning;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class patrol_point_setup extends script.base_script
{
    public patrol_point_setup()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setPatrolName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int setPatrolName(obj_id self, dictionary params) throws InterruptedException
    {
        setName(self, "Orphaned Patrol Point");
        return SCRIPT_CONTINUE;
    }
}
