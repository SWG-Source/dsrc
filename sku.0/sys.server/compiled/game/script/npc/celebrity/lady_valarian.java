package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class lady_valarian extends script.base_script
{
    public lady_valarian()
    {
    }
    public static final String CONVO = "celebrity/lady_valarian";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.celebrity.lady_valarian");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.celebrity.lady_valarian");
        return SCRIPT_CONTINUE;
    }
}
