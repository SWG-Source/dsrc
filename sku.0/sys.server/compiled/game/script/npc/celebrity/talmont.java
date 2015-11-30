package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.ai_lib;
import script.library.chat;
import script.library.skill;

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
