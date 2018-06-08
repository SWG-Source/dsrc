package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class dodonna extends script.base_script
{
    public dodonna()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "I'm sort of old...");
        attachScript(self, "conversation.rtp_dodonna_main");
        return SCRIPT_CONTINUE;
    }
}
