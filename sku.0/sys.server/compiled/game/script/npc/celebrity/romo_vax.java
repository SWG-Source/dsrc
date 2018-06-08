package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class romo_vax extends script.base_script
{
    public romo_vax()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Romo Vax");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "Jabba the Hutt?  Never heard of him...");
        attachScript(self, "conversation.romo_vax");
        return SCRIPT_CONTINUE;
    }
}
