package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class veers extends script.base_script
{
    public veers()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Colonel Veers");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_veers_main");
        return SCRIPT_CONTINUE;
    }
}
