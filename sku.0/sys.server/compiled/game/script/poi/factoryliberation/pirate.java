package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class pirate extends script.base_script
{
    public pirate()
    {
    }
    public static final String pirateEmotes[] = 
    {
        "scratch",
        "yawn",
        "fidget",
        "cough"
    };
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        stop(self);
        messageTo(self, "emotePirate", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int emotePirate(obj_id self, dictionary params) throws InterruptedException
    {
        int dosocial = rand(1, 4);
        if (dosocial == 1)
        {
            int whichsocial = rand(0, 3);
            queueCommand(self, (1780871594), null, pirateEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
}
