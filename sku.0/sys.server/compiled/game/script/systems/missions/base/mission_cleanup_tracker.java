package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class mission_cleanup_tracker extends script.base_script
{
    public mission_cleanup_tracker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int startTime = getGameTime();
        setObjVar(self, "time_created", startTime);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "time_created"))
        {
            int startTime = getGameTime();
            setObjVar(self, "time_created", startTime);
            return SCRIPT_CONTINUE;
        }
        int created = getIntObjVar(self, "time_created");
        int current = getGameTime();
        int elapsed = current - created;
        if (elapsed > 259200)
        {
            if (hasScript(self, "systems.missions.dynamic.mission_bounty"))
            {
                messageTo(self, "timedOutMission", null, 10, true);
            }
            else if (hasScript(self, "systems.missions.base.mission_object"))
            {
                messageTo(self, "abortMission", null, 10, true);
            }
            else 
            {
                messageTo(self, "cleanMe", null, rand(10, 100), true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanMe(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
