package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class r2d2 extends script.base_script
{
    public r2d2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "R2 D2");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
