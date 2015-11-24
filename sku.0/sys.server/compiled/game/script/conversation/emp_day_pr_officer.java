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

public class emp_day_pr_officer extends script.base_script
{
    public emp_day_pr_officer()
    {
    }
    public static String c_stringFile = "conversation/emp_day_pr_officer";
    public boolean emp_day_pr_officer_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_pr_officer_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean emp_day_pr_officer_condition_onMyTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task1 = groundquests.getTaskId(questId, "converseHistorian");
        return questIsTaskActive(questId, task1, player);
    }
    public boolean emp_day_pr_officer_condition_pastMyTask(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task1 = groundquests.getTaskId(questId, "converseHistorian");
        return questIsQuestComplete(questId, player) || (!questIsTaskActive(questId, task1, player) && questIsQuestActive(questId, player));
    }
    public void emp_day_pr_officer_action_giveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        groundquests.grantQuest(questId, player, npc, true);
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(-5186, 81, -2443, "rori");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Garrison Historian");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public void emp_day_pr_officer_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "empire_day.waypoint"))
        {
            obj_id oldWaypoint = getObjIdObjVar(player, "empire_day.waypoint");
            destroyWaypointInDatapad(oldWaypoint, player);
            removeObjVar(player, "empire_day.waypoint");
        }
        location destination = new location(-5186, 81, -2443, "rori");
        obj_id waypoint = createWaypointInDatapad(player, destination);
        setObjVar(player, "empire_day.waypoint", waypoint);
        setWaypointName(waypoint, "Garrison Historian");
        sendSystemMessage(player, new string_id("event/empire_day", "sys_msg_wp_added"));
    }
    public int emp_day_pr_officer_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_530"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                emp_day_pr_officer_action_giveWaypoint(player, npc);
                string_id message = new string_id(c_stringFile, "s_532");
                utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_538"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_540");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_542");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_554");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_562"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_564");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_566");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_542"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_544");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_546");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_550");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_554"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_556");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_558");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_546"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                emp_day_pr_officer_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_548");
                utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_550"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_552");
                utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_558"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                emp_day_pr_officer_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_560");
                utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_566"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_568");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_570");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_pr_officer_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_570"))
        {
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                emp_day_pr_officer_action_giveQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_572");
                utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
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
            detachScript(self, "conversation.emp_day_pr_officer");
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
        detachScript(self, "conversation.emp_day_pr_officer");
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
        if (emp_day_pr_officer_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_526");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_pr_officer_condition_onMyTask(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_528");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_530");
                }
                utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 2);
                npcStartConversation(player, npc, "emp_day_pr_officer", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_pr_officer_condition_pastMyTask(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_534");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_pr_officer_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_536");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (emp_day_pr_officer_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_538");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_562");
                }
                utils.setScriptVar(player, "conversation.emp_day_pr_officer.branchId", 5);
                npcStartConversation(player, npc, "emp_day_pr_officer", message, responses);
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
        if (!conversationId.equals("emp_day_pr_officer"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_pr_officer.branchId");
        if (branchId == 2 && emp_day_pr_officer_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && emp_day_pr_officer_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && emp_day_pr_officer_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && emp_day_pr_officer_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && emp_day_pr_officer_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && emp_day_pr_officer_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && emp_day_pr_officer_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_pr_officer.branchId");
        return SCRIPT_CONTINUE;
    }
}
