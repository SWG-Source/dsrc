package script.theme_park.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class thrawn_veers_path extends script.base_script
{
    public thrawn_veers_path()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "thrawn_veers_MoveToBalcony", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int thrawn_veers_MoveToBalcony(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "thrawn"))
        {
            location thrawnLoc = new location(2369.5f, 291.91f, -3922.0f, "naboo", obj_id.NULL_ID);
            ai_lib.aiPathTo(self, thrawnLoc);
            addLocationTarget("marker", thrawnLoc, 1);
        }
        if (hasObjVar(self, "veers"))
        {
            location veersLoc = new location(2369.0f, 291.91f, -3921.0f, "naboo", obj_id.NULL_ID);
            ai_lib.aiPathTo(self, veersLoc);
            addLocationTarget("marker", veersLoc, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        messageTo(self, "thrawn_veers_DoFacing", null, 5, true);
        return SCRIPT_CONTINUE;
    }
    public int thrawn_veers_DoFacing(obj_id self, dictionary params) throws InterruptedException
    {
        int myYaw = -119;
        setYaw(self, myYaw);
        setAnimationMood(self, "conversation");
        return SCRIPT_CONTINUE;
    }
}
