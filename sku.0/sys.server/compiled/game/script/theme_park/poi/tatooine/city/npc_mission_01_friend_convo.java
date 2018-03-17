package script.theme_park.poi.tatooine.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;
import script.ai.ai;

public class npc_mission_01_friend_convo extends script.base_script
{
    public npc_mission_01_friend_convo()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        attachScript(self, "npc.converse.npc_converse_menu");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(speaker))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hasObjVar(speaker, "knowsFriend"))
        {
            string_id greeting = new string_id("npc_mission/npc_mission_01", "npc_msn_01_friend_greeting_02");
            string_id response[] = new string_id[1];
            response[0] = new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01");
            npcStartConversation(speaker, self, "recruiting/imperial_recruit", greeting, response);
        }
        else 
        {
            string_id greeting = new string_id("npc_mission/npc_mission_01", "npc_msn_01_friend_greeting_01");
            string_id response[] = new string_id[1];
            response[0] = new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01");
            npcStartConversation(speaker, self, "recruiting/imperial_recruit", greeting, response);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        if ((response.getAsciiId()).equals("npc_msn_01_presp_greeting_01") || (response.getAsciiId()).equals("npc_msn_01_presp_greeting_02"))
        {
            string_id message = new string_id("npc_mission/npc_mission_01", "npc_msn_01_friend_work_01");
            npcSpeak(player, message);
            setObjVar(player, "knowsFriend", 1);
            npcRemoveConversationResponse(player, new string_id("npc_mission/npc_mission_01", "npc_msn_01_presp_greeting_01"));
            npcEndConversation(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
