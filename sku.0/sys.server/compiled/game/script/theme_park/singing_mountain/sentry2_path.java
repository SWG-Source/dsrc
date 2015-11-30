package script.theme_park.singing_mountain;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class sentry2_path extends script.base_script
{
    public sentry2_path()
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
        obj_id hall = getCellId(stronghold, "crafthall");
        location guard1 = new location(16.32f, 2.01f, 0.33f, "dathomir", hall);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot1", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        doAnimationAction(self, pickOverlyElaborateAnimationActionFromList());
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
        obj_id guard = getCellId(stronghold, "crafthall");
        location guard1 = new location(18.30f, 2.01f, -4.16f, "dathomir", guard);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot2", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot3(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id guard2 = getCellId(stronghold, "crafthall");
        location guard1 = new location(19.33f, 2.01f, -12.27f, "dathomir", guard2);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot3", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot4(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id healing = getCellId(stronghold, "crafthall");
        location guard1 = new location(18.33f, 2.01f, -19.11f, "dathomir", healing);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot4", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int spot5(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id stronghold = getTopMostContainer(self);
        obj_id throne = getCellId(stronghold, "crafthall");
        location guard1 = new location(13.46f, 2.01f, -23.86f, "dathomir", throne);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("spot5", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public String pickOverlyElaborateAnimationActionFromList() throws InterruptedException
    {
        String action = "listen";
        int random = rand(1, 2);
        if (random == 1)
        {
            return action;
        }
        else 
        {
            return "search";
        }
    }
}
