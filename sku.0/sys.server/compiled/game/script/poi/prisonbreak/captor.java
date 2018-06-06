package script.poi.prisonbreak;

import script.dictionary;
import script.modifiable_float;
import script.obj_id;

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
