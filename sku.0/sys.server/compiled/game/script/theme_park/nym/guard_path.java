package script.theme_park.nym;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class guard_path extends script.base_script
{
    public guard_path()
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
        obj_id meeting3 = getCellId(stronghold, "meeting3");
        location guard1 = new location(-22.34f, 4.23f, 14.24f, "lok", meeting3);
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
        obj_id meeting4 = getCellId(stronghold, "meeting4");
        location guard1 = new location(22.32f, 4.23f, 14.36f, "lok", meeting4);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot2", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot3(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id meeting6 = getCellId(stronghold, "meeting6");
        location guard1 = new location(22.24f, 4.23f, -24.45f, "lok", meeting6);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot3", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot4(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id lobby = getCellId(stronghold, "lobby");
        location guard1 = new location(9.08f, 4.07f, -20.27f, "lok", lobby);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot4", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot5(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id meeting2 = getCellId(stronghold, "meeting2");
        location guard1 = new location(-28.92f, 2.3f, 0.73f, "lok", meeting2);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot5", guard1, 1);
        return SCRIPT_CONTINUE;
    }
}
