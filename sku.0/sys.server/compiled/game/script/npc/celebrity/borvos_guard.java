package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class borvos_guard extends script.base_script
{
    public borvos_guard()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Borvo The Hutt's Guard");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "quest_table", "borvo_guard");
        return SCRIPT_CONTINUE;
    }
}
