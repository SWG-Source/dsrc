package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class loam_redge extends script.base_script
{
    public loam_redge()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Inquisitor Loam Redge");
        pvpSetAlignedFaction(self, (-615855020));
        pvpMakeDeclared(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.itp_loam_main");
        return SCRIPT_CONTINUE;
    }
}
