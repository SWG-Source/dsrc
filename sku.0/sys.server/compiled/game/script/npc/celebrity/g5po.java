package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class g5po extends script.base_script
{
    public g5po()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "G-5P0");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
