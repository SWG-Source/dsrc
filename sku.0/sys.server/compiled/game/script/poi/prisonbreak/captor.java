package script.poi.prisonbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.ai.ai_combat;

public class captor extends script.base_script
{
    public captor()
    {
    }
    public static final String captorEmotes[] = 
    {
        "scratch",
        "yawn",
        "fidget",
        "cough"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        stop(self);
        messageTo(self, "emoteCaptor", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int emoteCaptor(obj_id self, dictionary params) throws InterruptedException
    {
        int dosocial = rand(1, 4);
        if (dosocial == 1)
        {
            int whichsocial = rand(0, 3);
            queueCommand(self, (1780871594), null, captorEmotes[whichsocial], COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "resumeDefaultCalmBehavior", null, 3, false);
        return SCRIPT_CONTINUE;
    }
}
