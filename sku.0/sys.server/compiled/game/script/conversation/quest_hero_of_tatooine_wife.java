package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.badge;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;

public class quest_hero_of_tatooine_wife extends script.base_script
{
    public quest_hero_of_tatooine_wife()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_wife";
    public boolean quest_hero_of_tatooine_wife_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_wife_condition_Quest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.honor"))
        {
            if (hasObjVar(player, "quest.hero_of_tatooine.rancherquest"))
            {
                
            }
            return true;
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_wife_condition_AcknowledgeObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.honor"))
        {
            if (hasObjVar(player, "quest.hero_of_tatooine.acknowledge"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_wife_condition_DistractWife(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.honor"))
        {
            if (hasObjVar(player, "quest.hero_of_tatooine.distract"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_wife_condition_inProgress(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.rancherquest"))
        {
            obj_id building = getTopMostContainer(npc);
            if (hasObjVar(building, "quest.hero_of_tatooine.progress"))
            {
                obj_id guy = getObjIdObjVar(building, "quest.hero_of_tatooine.progress");
                if (player == guy)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_wife_condition_IntercomObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.intercom"))
        {
            return true;
        }
        return false;
    }
    public void quest_hero_of_tatooine_wife_action_Acknowledge(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.hero_of_tatooine.acknowledge", true);
    }
    public void quest_hero_of_tatooine_wife_action_Intercom(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.hero_of_tatooine.intercom", true);
    }
    public void quest_hero_of_tatooine_wife_action_MessagePirates(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.honor_waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "quest.hero_of_tatooine.honor_waypoint");
            setWaypointActive(waypoint, false);
            setWaypointVisible(waypoint, false);
            destroyWaypointInDatapad(waypoint, player);
            removeObjVar(player, "quest.hero_of_tatooine.honor_waypoint");
        }
        obj_id building = getTopMostContainer(npc);
        messageTo(building, "piratesEscape", null, 1, false);
    }
    public void quest_hero_of_tatooine_wife_action_Badge(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id mark_honor = createObjectInInventoryAllowOverload("object/tangible/loot/quest/hero_of_tatooine/mark_honor.iff", player);
        badge.grantBadge(player, "poi_prisonbreak");
        setObjVar(player, "quest.hero_of_tatooine.honor", true);
        groundquests.sendSignal(player, "hero_of_tatooine_main_honor");
        CustomerServiceLog("quest", "HERO OF TATOOINE - %TU has acquired the Mark of Honor", player);
        removeObjVar(player, "quest.hero_of_tatooine.intercom");
        removeObjVar(player, "quest.hero_of_tatooine.distract");
        removeObjVar(player, "quest.hero_of_tatooine.acknowledge");
        removeObjVar(player, "quest.hero_of_tatooine.rancherquest");
        if (!isIdValid(mark_honor))
        {
            sendSystemMessage(player, new string_id("quest/hero_of_tatooine/system_messages", "honor_inv_full"));
            setObjVar(player, "quest.hero_of_tatooine.owed.honor", 1);
        }
        if (hasObjVar(player, "quest.hero_of_tatooine.honor_waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(player, "quest.hero_of_tatooine.honor_waypoint");
            setWaypointActive(waypoint, false);
            setWaypointVisible(waypoint, false);
            destroyWaypointInDatapad(waypoint, player);
            removeObjVar(player, "quest.hero_of_tatooine.honor_waypoint");
        }
        obj_id building = getTopMostContainer(npc);
        messageTo(building, "piratesLose", null, 1, false);
        removeObjVar(building, "quest.hero_of_tatooine.progress");
        messageTo(building, "cleanUp", null, 100, false);
    }
    public int quest_hero_of_tatooine_wife_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f74cb815"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8ad6289");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_abec95a3");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ea1b59c"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_Badge(player, npc);
                string_id message = new string_id(c_stringFile, "s_63f6701b");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_wife_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_abec95a3"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_MessagePirates(player, npc);
                string_id message = new string_id(c_stringFile, "s_2622df9f");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_wife_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37c0824f"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50587782");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210b4c5b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ecfff90");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e83ad536"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6422820b");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_wife_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_210b4c5b"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_Intercom(player, npc);
                string_id message = new string_id(c_stringFile, "s_83da2e04");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1ecfff90"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_18dbba11");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_wife_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84ee73da"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aebcaa68");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5a33502");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e45adc31");
                    }
                    utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f6c25472"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_Acknowledge(player, npc);
                string_id message = new string_id(c_stringFile, "s_f26b5d58");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int quest_hero_of_tatooine_wife_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c5a33502"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_Intercom(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e45adc31"))
        {
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                quest_hero_of_tatooine_wife_action_Acknowledge(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
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
            detachScript(self, "conversation.quest_hero_of_tatooine_wife");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Helen Goldenfield (a rancher's wife)");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Helen Goldenfield (a rancher's wife)");
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
        detachScript(self, "conversation.quest_hero_of_tatooine_wife");
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
        if (!quest_hero_of_tatooine_wife_condition_inProgress(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c74cdffb");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_wife_condition_IntercomObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7ee07d1c");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_wife_condition_DistractWife(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3220d628");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f74cb815");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ea1b59c");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 3);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_wife", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_wife_condition_AcknowledgeObj(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_43f5726a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37c0824f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e83ad536");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 7);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_wife", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_wife_condition_Quest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_82bd1b20");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84ee73da");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f6c25472");
                }
                utils.setScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId", 12);
                npcStartConversation(player, npc, "quest_hero_of_tatooine_wife", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_wife_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_b8584429");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_hero_of_tatooine_wife"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
        if (branchId == 3 && quest_hero_of_tatooine_wife_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && quest_hero_of_tatooine_wife_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && quest_hero_of_tatooine_wife_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && quest_hero_of_tatooine_wife_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && quest_hero_of_tatooine_wife_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && quest_hero_of_tatooine_wife_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.quest_hero_of_tatooine_wife.branchId");
        return SCRIPT_CONTINUE;
    }
}
