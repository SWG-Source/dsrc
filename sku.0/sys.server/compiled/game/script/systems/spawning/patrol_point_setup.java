package script.systems.spawning;

import script.dictionary;
import script.obj_id;

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
