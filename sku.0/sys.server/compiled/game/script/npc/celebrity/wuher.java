package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class wuher extends script.base_script
{
    public wuher()
    {
    }
    public static final String CONVO = "celebrity/wuher";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
