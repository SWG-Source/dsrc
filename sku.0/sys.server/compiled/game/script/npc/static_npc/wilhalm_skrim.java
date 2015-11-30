package script.npc.static_npc;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.hue;
import script.library.colors;
import script.library.factions;
import script.library.chat;
import script.library.skill;

public class wilhalm_skrim extends script.base_script
{
    public wilhalm_skrim()
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
        setObjVar(self, "quest_table", "wilhalm_skrim");
        factions.setFaction(self, "Imperial");
        setName(self, "Wilhalm Skrim");
        return SCRIPT_CONTINUE;
    }
}
