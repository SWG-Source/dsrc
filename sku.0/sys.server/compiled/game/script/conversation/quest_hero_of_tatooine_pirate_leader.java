package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class quest_hero_of_tatooine_pirate_leader extends script.base_script
{
    public quest_hero_of_tatooine_pirate_leader()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_pirate_leader";
    public boolean quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_pirate_leader_condition_quest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasObjVar(player, "quest.hero_of_tatooine.badgequests") || groundquests.isTaskActive(player, "quest_hero_of_tatooine_main", "hero_of_tatooine_main_honor")) && !hasObjVar(player, "quest.hero_of_tatooine.honor");
    }
    public void quest_hero_of_tatooine_pirate_leader_action_Attackplayer(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "attackPlayer", params, 1, false);
    }
    public void quest_hero_of_tatooine_pirate_leader_action_QuestAccepted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.honor"))
        {
            setObjVar(player, "quest.hero_of_tatooine.rancherquest", 1);
            messageTo(npc, "die", null, 10, false);
            location site = new location(4993, 180, -4682);
            obj_id waypoint = createWaypointInDatapad(player, site);
            setWaypointName(waypoint, "Ranch House");
            setWaypointColor(waypoint, "blue");
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setObjVar(player, "quest.hero_of_tatooine.honor_waypoint", waypoint);
        }
        return;
    }
    public int quest_hero_of_tatooine_pirate_leader_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_446af42e"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_256d8f99");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_abc7e09d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34bde7db");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a6c6f57b"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_pirate_leader_action_Attackplayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_cacfa6a2");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_pirate_leader_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48566ef9"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4575b29e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da25a2d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d57d668a");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_abc7e09d"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_pirate_leader_action_Attackplayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_5a3d905f");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34bde7db"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_pirate_leader_action_QuestAccepted(player, npc);
                string_id message = new string_id(c_stringFile, "s_543c2619");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_pirate_leader_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_da25a2d9"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_pirate_leader_action_Attackplayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9706ae2");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d57d668a"))
        {
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_pirate_leader_action_QuestAccepted(player, npc);
                string_id message = new string_id(c_stringFile, "s_e30e7484");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.quest_hero_of_tatooine_pirate_leader");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.quest_hero_of_tatooine_pirate_leader");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7557e310");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_pirate_leader_condition_quest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_pirate_leader_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_446af42e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a6c6f57b");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId", 1);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_pirate_leader", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_hero_of_tatooine_pirate_leader"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
        if (branchId == 1 && quest_hero_of_tatooine_pirate_leader_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && quest_hero_of_tatooine_pirate_leader_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && quest_hero_of_tatooine_pirate_leader_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_pirate_leader.branchId");
        return SCRIPT_CONTINUE;
    }
}
