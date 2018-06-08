package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class borvo extends script.base_script
{
    public borvo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Borvo the Hutt");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
