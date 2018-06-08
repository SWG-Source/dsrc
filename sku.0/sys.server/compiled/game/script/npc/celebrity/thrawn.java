package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class thrawn extends script.base_script
{
    public thrawn()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Captain Thrawn");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_thrawn_main");
        return SCRIPT_CONTINUE;
    }
}
