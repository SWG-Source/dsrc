package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class kaja_orzee extends script.base_script
{
    public kaja_orzee()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Kaja Or'Zee");
        pvpSetAlignedFaction(self, (-615855020));
        pvpMakeDeclared(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_kaja_main");
        return SCRIPT_CONTINUE;
    }
}
