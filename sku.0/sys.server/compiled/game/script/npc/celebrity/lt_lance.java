package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class lt_lance extends script.base_script
{
    public lt_lance()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Lt. Lance");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
