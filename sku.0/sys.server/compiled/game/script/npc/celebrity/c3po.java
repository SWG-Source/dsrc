package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class c3po extends script.base_script
{
    public c3po()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "C-3PO");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.rtp_c3po_main");
        return SCRIPT_CONTINUE;
    }
}
