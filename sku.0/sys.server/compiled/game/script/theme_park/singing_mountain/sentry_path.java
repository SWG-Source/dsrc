package script.theme_park.singing_mountain;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class sentry_path extends script.base_script
{
    public sentry_path()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, true);
        return SCRIPT_CONTINUE;
    }
    public int startPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id hall = getCellId(stronghold, "hallway");
        location guard1 = new location(-3.36f, 2.01f, 7.92f, "dathomir", hall);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot1", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("spot1"))
        {
            messageTo(self, "spot2", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("spot2"))
        {
            messageTo(self, "spot3", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("spot3"))
        {
            messageTo(self, "spot4", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("spot4"))
        {
            messageTo(self, "spot5", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("spot5"))
        {
            messageTo(self, "startPatrol", null, 10, true);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int spot2(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id guard = getCellId(stronghold, "guard");
        location guard1 = new location(-11.36f, 2.01f, 12.81f, "dathomir", guard);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot2", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot3(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id guard2 = getCellId(stronghold, "guard2");
        location guard1 = new location(-26.26f, 2.01f, 4.23f, "dathomir", guard2);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot3", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot4(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id healing = getCellId(stronghold, "healing");
        location guard1 = new location(-26.19f, 2.01f, -6.81f, "dathomir", healing);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot4", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot5(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id throne = getCellId(stronghold, "throne");
        location guard1 = new location(8.33f, 2.01f, -24.84f, "dathomir", throne);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot5", guard1, 1);
        return SCRIPT_CONTINUE;
    }
}
