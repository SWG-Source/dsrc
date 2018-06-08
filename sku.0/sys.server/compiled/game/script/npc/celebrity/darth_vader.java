package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class darth_vader extends script.base_script
{
    public darth_vader()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmMood(self, "npc_imperial");
        attachScript(self, "conversation.itp_vader_main");
        return SCRIPT_CONTINUE;
    }
}
