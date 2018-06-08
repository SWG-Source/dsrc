package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class king_terak extends script.base_script
{
    public king_terak()
    {
    }
    public static final String CONVO = "celebrity/king_terak";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setObjVar(self, "quest_table", "king_terak");
        attachScript(self, "npc.static_quest.quest_convo");
        return SCRIPT_CONTINUE;
    }
}
