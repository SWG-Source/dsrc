package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class mon_mothma extends script.base_script
{
    public mon_mothma()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "I am stern and stately.");
        attachScript(self, "conversation.rtp_mon_mothma_main");
        return SCRIPT_CONTINUE;
    }
}
