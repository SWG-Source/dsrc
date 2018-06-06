package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class palpatine extends script.base_script
{
    public palpatine()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "The Emperor");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmMood(self, "npc_imperial");
        attachScript(self, "conversation.itp_emperor_main");
        return SCRIPT_CONTINUE;
    }
}
