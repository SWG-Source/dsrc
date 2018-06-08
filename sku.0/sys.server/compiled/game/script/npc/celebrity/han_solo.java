package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class han_solo extends script.base_script
{
    public han_solo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "It's not my fault!");
        attachScript(self, "conversation.rtp_han_solo_main");
        return SCRIPT_CONTINUE;
    }
}
