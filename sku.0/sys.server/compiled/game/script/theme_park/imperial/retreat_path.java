package script.theme_park.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class retreat_path extends script.base_script
{
    public retreat_path()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        int timeOut = rand(20, 40);
        messageTo(self, "startPatrol", null, timeOut, false);
        return SCRIPT_CONTINUE;
    }
    public int startPatrol(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id bldg = getTopMostContainer(self);
        obj_id library = getCellId(bldg, "library");
        obj_id mainhall = getCellId(bldg, "mainhall");
        obj_id vader = getCellId(bldg, "vader");
        obj_id meetingroom = getCellId(bldg, "meetingroom");
        obj_id hall3 = getCellId(bldg, "hall3");
        location mouse1 = new location(-52.15f, .2f, -23.82f, "naboo", vader);
        location mouse2 = new location(23.83f, 0.2f, -40.42f, "naboo", meetingroom);
        location mouse3 = new location(-45.79f, .2f, -12.5f, "naboo", hall3);
        if (hasObjVar(self, "mouse1"))
        {
            ai_lib.aiPathTo(self, mouse1);
            addLocationTarget("marker", mouse1, 1);
        }
        if (hasObjVar(self, "mouse2"))
        {
            ai_lib.aiPathTo(self, mouse2);
            addLocationTarget("marker", mouse2, 1);
        }
        if (hasObjVar(self, "mouse3"))
        {
            ai_lib.aiPathTo(self, mouse3);
            addLocationTarget("marker", mouse3, 1);
        }
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
        final location home = aiGetHomeLocation(self);
        ai_lib.aiPathTo(self, home);
        addLocationTarget("marker2", home, 1);
        return SCRIPT_CONTINUE;
    }
}
