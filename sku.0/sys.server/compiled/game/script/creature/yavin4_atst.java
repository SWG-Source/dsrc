package script.creature;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class yavin4_atst extends script.base_script
{
    public yavin4_atst()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int timeOut = rand(5, 15);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int timeOut = rand(5, 10);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int startPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        location guard1 = new location(4000, 37, -6284, "yavin4", null);
        ai_lib.aiPathTo(self, guard1);
        addLocationTarget("marker", guard1, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("marker"))
        {
            messageTo(self, "nextSpot", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        if (name.equals("marker2"))
        {
            messageTo(self, "startPatrol", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int nextSpot(obj_id self, dictionary params) throws InterruptedException
    {
        location home = new location(4075, 37, -6284, "yavin4", null);
        ai_lib.aiPathTo(self, home);
        addLocationTarget("marker2", home, 1);
        return SCRIPT_CONTINUE;
    }
}
