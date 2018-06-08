package script.npc.celebrity;

import script.library.ai_lib;
import script.obj_id;

public class thale_dustrunner extends script.base_script
{
    public thale_dustrunner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Thale Dustrunner (Corsec Captain)");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        attachScript(self, "npc.static_quest.quest_convo");
        setObjVar(self, "quest_table", "corsec_captain");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
