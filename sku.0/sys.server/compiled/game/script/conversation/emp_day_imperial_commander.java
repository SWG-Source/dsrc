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

public class emp_day_imperial_commander extends script.base_script
{
    public emp_day_imperial_commander()
    {
    }
    public static String c_stringFile = "conversation/emp_day_imperial_commander";
    public boolean emp_day_imperial_commander_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean emp_day_imperial_commander_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean emp_day_imperial_commander_condition_notReadyForYou(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task1 = groundquests.getTaskId(questId, "converseHistorian");
        int task2 = groundquests.getTaskId(questId, "killRebels");
        int task3a = groundquests.getTaskId(questId, "diskGuy1");
        int task3b = groundquests.getTaskId(questId, "diskGuy2");
        int task3c = groundquests.getTaskId(questId, "diskGuy3");
        int task4 = groundquests.getTaskId(questId, "disksToHistorian");
        int task5 = groundquests.getTaskId(questId, "toDataOfficer");
        return (questIsTaskActive(questId, task1, player) || questIsTaskActive(questId, task2, player) || questIsTaskActive(questId, task3a, player) || questIsTaskActive(questId, task3b, player) || questIsTaskActive(questId, task3c, player) || questIsTaskActive(questId, task4, player) || questIsTaskActive(questId, task5, player) || !questIsQuestActive(questId, player));
    }
    public boolean emp_day_imperial_commander_condition_questCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        return questIsQuestComplete(questId, player);
    }
    public boolean emp_day_imperial_commander_condition_hasTask6(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task6 = groundquests.getTaskId(questId, "toCommander");
        int task7 = groundquests.getTaskId(questId, "toVader");
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        return (questIsTaskActive(questId, task6, player) || (questIsTaskActive(questId, task7, player) && whichFaction == null));
    }
    public boolean emp_day_imperial_commander_condition_isNeutral(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_imperial_commander_condition_hasTask8(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task8 = groundquests.getTaskId(questId, "empDayComplete");
        return questIsTaskActive(questId, task8, player);
    }
    public boolean emp_day_imperial_commander_condition_isImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (whichFaction == null)
        {
            return false;
        }
        if (whichFaction.equals("Imperial"))
        {
            return true;
        }
        return false;
    }
    public boolean emp_day_imperial_commander_condition_hasTask7(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/emp_day_imperial");
        int task7 = groundquests.getTaskId(questId, "toVader");
        int factionHashCode = factions.pvpGetAlignedFaction(player);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        return (questIsTaskActive(questId, task7, player) && whichFaction.equals("Imperial"));
    }
    public boolean emp_day_imperial_commander_condition_noPrisonAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!hasObjVar(player, "event.emp_day.prisoner"));
    }
    public void emp_day_imperial_commander_action_rewardAndEndNow(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/furniture/all/bestine_quest_imp_banner.iff", playerInventory, "");
        if (isIdValid(createdObject))
        {
            groundquests.sendSignal(player, "to_vader");
            groundquests.sendSignal(player, "emp_day_complete");
            removeObjVar(player, "empire_day.waypoint");
            setObjVar(player, "event.emp_day.rewarded", 1);
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public void emp_day_imperial_commander_action_givePrisonerAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "event.emp_day.prisoner", 0);
    }
    public void emp_day_imperial_commander_action_signal6(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "to_commander");
    }
    public int emp_day_imperial_commander_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_367"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_givePrisonerAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_369");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_373"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_375");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_377");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_377"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_rewardAndEndNow(player, npc);
                string_id message = new string_id(c_stringFile, "s_379");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_385"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition_noPrisonAccess(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_419");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_427"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_429");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (emp_day_imperial_commander_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_391");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_393");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_397");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (emp_day_imperial_commander_condition_isNeutral(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_401");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_403");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_407");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_419"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_421");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_423");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_393"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_395");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_397"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_399");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_403"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_405");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_407"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_409");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_411");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_415");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_411"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_rewardAndEndNow(player, npc);
                string_id message = new string_id(c_stringFile, "s_413");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_415"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_417");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_403");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_407");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_403"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_405");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_407"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_signal6(player, npc);
                string_id message = new string_id(c_stringFile, "s_409");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_411");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_415");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_423"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_givePrisonerAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_425");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition_noPrisonAccess(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_419");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (emp_day_imperial_commander_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_391");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_393");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_397");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (emp_day_imperial_commander_condition_isNeutral(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_401");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_403");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_407");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_419"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_421");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_423");
                    }
                    utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int emp_day_imperial_commander_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_433"))
        {
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                emp_day_imperial_commander_action_givePrisonerAccess(player, npc);
                string_id message = new string_id(c_stringFile, "s_435");
                utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
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
            detachScript(self, "conversation.emp_day_imperial_commander");
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
        detachScript(self, "conversation.emp_day_imperial_commander");
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
        if (emp_day_imperial_commander_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_363");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition_questCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_365");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_imperial_commander_condition_noPrisonAccess(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_367");
                }
                utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 2);
                npcStartConversation(player, npc, "emp_day_imperial_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition_hasTask8(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_371");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_373");
                }
                utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 4);
                npcStartConversation(player, npc, "emp_day_imperial_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition_notReadyForYou(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_381");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition_hasTask6(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_383");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_385");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_427");
                }
                utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 8);
                npcStartConversation(player, npc, "emp_day_imperial_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition_hasTask7(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_431");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (emp_day_imperial_commander_condition_noPrisonAccess(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_433");
                }
                utils.setScriptVar(player, "conversation.emp_day_imperial_commander.branchId", 21);
                npcStartConversation(player, npc, "emp_day_imperial_commander", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (emp_day_imperial_commander_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_437");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("emp_day_imperial_commander"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
        if (branchId == 2 && emp_day_imperial_commander_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && emp_day_imperial_commander_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && emp_day_imperial_commander_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && emp_day_imperial_commander_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && emp_day_imperial_commander_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && emp_day_imperial_commander_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && emp_day_imperial_commander_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && emp_day_imperial_commander_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && emp_day_imperial_commander_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && emp_day_imperial_commander_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && emp_day_imperial_commander_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && emp_day_imperial_commander_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.emp_day_imperial_commander.branchId");
        return SCRIPT_CONTINUE;
    }
}
