package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class luke extends script.base_script
{
    public luke()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.rtp_luke_main");
        return SCRIPT_CONTINUE;
    }
}
