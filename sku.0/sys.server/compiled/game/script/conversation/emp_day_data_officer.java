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
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class emp_day_data_officer extends script.base_script
{
    public emp_day_data_officer()
    {
    }
    public static String c_stringFile = "conversation/emp_day_data_officer";
    public boolean emp_day_data_officer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_data_officer_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Rebel"))
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_data_officer_condition_notReadyForYou(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task1 = groundquests.getTaskId(questId, "converseHistorian");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        int task4 = groundquests.getTaskId(questId, "disksToHistorian");
        return (questIsTaskActive(questId, task1, player) || questIsTaskActive(questId, task2, player) || questIsTaskActive(questId, task3a, player) || questIsTaskActive(questId, task3b, player) || questIsTaskActive(questId, task3c, player) || questIsTaskActive(questId, task4, player) || !questIsQuestActive(questId, player));
    }
    public boolean emp_day_data_officer_condition_hasTask5(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task5 = groundquests.getTaskId(questId, "toDataOfficer");
        return questIsTaskActive(questId, task5, player);
    }
    public boolean emp_day_data_officer_condition_hasTask6(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task6 = groundquests.getTaskId(questId, "toCommander");
        return questIsTaskActive(questId, task6, player);
    }
    public void emp_day_data_officer_action_wpToCommander(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(4123, 37, 1618, "yavin4");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Commander");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_data_officer_action_signal(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "to_data_officer");
    }
    public int emp_day_data_officer_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_304"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                emp_day_data_officer_action_wpToCommander(player, npc);
                string_id message = new string_id(c_stringFile, "s_306");
                utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_data_officer_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_310"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_314");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_data_officer.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_data_officer_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_314"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_322");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_data_officer.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_326"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_328");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_data_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_330");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_334");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_data_officer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_data_officer_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_318"))
        {
            emp_day_data_officer_action_signal(player, npc);
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                emp_day_data_officer_action_wpToCommander(player, npc);
                string_id message = new string_id(c_stringFile, "s_320");
                utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_322"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_324");
                utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_data_officer_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_330"))
        {
            emp_day_data_officer_action_signal(player, npc);
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                emp_day_data_officer_action_wpToCommander(player, npc);
                string_id message = new string_id(c_stringFile, "s_332");
                utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_334"))
        {
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_336");
                utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.emp_day_data_officer");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.emp_day_data_officer");
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
        if (emp_day_data_officer_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_298");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_data_officer_condition_notReadyForYou(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_300");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_data_officer_condition_hasTask6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_302");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_304");
                }
                utils.setScriptVar(player, "conversation.emp_day_data_officer.branchId", 3);
                npcStartConversation(player, npc, "emp_day_data_officer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_data_officer_condition_hasTask5(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_308");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_data_officer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_310");
                }
                utils.setScriptVar(player, "conversation.emp_day_data_officer.branchId", 5);
                npcStartConversation(player, npc, "emp_day_data_officer", message, responses);
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
        if (!conversationId.equals("emp_day_data_officer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_data_officer.branchId");
        if (branchId == 3 && emp_day_data_officer_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && emp_day_data_officer_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && emp_day_data_officer_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && emp_day_data_officer_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && emp_day_data_officer_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_data_officer.branchId");
        return SCRIPT_CONTINUE;
    }
}
