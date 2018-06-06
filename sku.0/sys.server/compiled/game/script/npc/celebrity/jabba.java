package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class jabba extends script.base_script
{
    public jabba()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Jabba the Hutt");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.jabba");
        return SCRIPT_CONTINUE;
    }
}
