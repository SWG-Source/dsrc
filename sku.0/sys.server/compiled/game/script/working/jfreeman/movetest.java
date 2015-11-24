package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class movetest extends script.base_script
{
    public movetest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "attached");
        location destLoc = new location(getLocation(self));
        destLoc.x = 0;
        destLoc.z = 0;
        pathTo(self, destLoc);
        return SCRIPT_CONTINUE;
    }
}
