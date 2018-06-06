package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class charal extends script.base_script
{
    public charal()
    {
    }
    public static final String CONVO = "celebrity/charal";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.converse.npc_converse_menu");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
