package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class wedge_antilles extends script.base_script
{
    public wedge_antilles()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "2 METERS?!?");
        attachScript(self, "conversation.rtp_wedge_main");
        return SCRIPT_CONTINUE;
    }
}
