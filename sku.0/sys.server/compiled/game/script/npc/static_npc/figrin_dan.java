package script.npc.static_npc;

import script.library.ai_lib;
import script.obj_id;

public class figrin_dan extends script.base_script
{
    public figrin_dan()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "F'igrin Dan");
        return SCRIPT_CONTINUE;
    }
}
