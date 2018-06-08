package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class lord_hethrir extends script.base_script
{
    public lord_hethrir()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Lord Hethrir");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_hethrir_main");
        return SCRIPT_CONTINUE;
    }
}
