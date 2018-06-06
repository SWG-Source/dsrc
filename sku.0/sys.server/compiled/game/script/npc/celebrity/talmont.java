package script.npc.celebrity;

import script.library.ai_lib;
import script.library.factions;
import script.obj_id;

public class talmont extends script.base_script
{
    public talmont()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!hasScript(self, "npc.converse.npc_converse_menu"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        if (!hasScript(self, "npc.static_quest.quest_convo"))
        {
            attachScript(self, "npc.static_quest.quest_convo");
        }
        setObjVar(self, "quest_table", "prefect_talmont");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        if (!hasScript(self, "npc.converse.npc_converse_menu"))
        {
            attachScript(self, "npc.converse.npc_converse_menu");
        }
        if (!hasScript(self, "npc.static_quest.quest_convo"))
        {
            attachScript(self, "npc.static_quest.quest_convo");
        }
        setObjVar(self, "quest_table", "prefect_talmont");
        factions.setFaction(self, "Imperial");
        return SCRIPT_CONTINUE;
    }
}
