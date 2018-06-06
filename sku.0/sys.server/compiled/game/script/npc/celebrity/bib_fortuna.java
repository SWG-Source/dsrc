package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class bib_fortuna extends script.base_script
{
    public bib_fortuna()
    {
    }
    public static final String CONVO = "celebrity/captain_eso";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "conversation.bib_fortuna");
        return SCRIPT_CONTINUE;
    }
}
