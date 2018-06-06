package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class nien_nunb extends script.base_script
{
    public nien_nunb()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Nien Nunb");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.rtp_nien_nunb_main");
        return SCRIPT_CONTINUE;
    }
}
