package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class gins_darone extends script.base_script
{
    public gins_darone()
    {
    }
    public static final String CONVO = "celebrity/general_otto";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Sgt. Gins Darone");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
}
