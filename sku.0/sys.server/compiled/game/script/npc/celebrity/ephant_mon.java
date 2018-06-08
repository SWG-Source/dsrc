package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class ephant_mon extends script.base_script
{
    public ephant_mon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        debugSpeakMsg(self, "Ephant Mon...at your service!");
        attachScript(self, "conversation.ephant_mon");
        return SCRIPT_CONTINUE;
    }
}
