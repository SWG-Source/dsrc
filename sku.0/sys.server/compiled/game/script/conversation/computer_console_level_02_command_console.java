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
import script.library.conversation;
import script.library.groundquests;
import script.library.utils;

public class computer_console_level_02_command_console extends script.base_script
{
    public computer_console_level_02_command_console()
    {
    }
    public static String c_stringFile = "conversation/computer_console_level_02_command_console";
    public boolean computer_console_level_02_command_console_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean computer_console_level_02_command_console_condition_canUseFirstTime(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "outbreak_quest_facility_02_imperial", "useConsole") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_rebel", "useConsole") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_neutral", "useConsole");
    }
    public boolean computer_console_level_02_command_console_condition_answerCall(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_02_imperial", "commFromSurvivor") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_rebel", "commFromSurvivor") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_neutral", "commFromSurvivor")) || (groundquests.isTaskActive(player, "outbreak_quest_facility_02_imperial", "answerCall") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_rebel", "answerCall") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_neutral", "answerCall"));
    }
    public boolean computer_console_level_02_command_console_condition_answerSecCall(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_02_imperial", "comm2") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_rebel", "comm2") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_neutral", "comm2")) || (groundquests.isTaskActive(player, "outbreak_quest_facility_02_imperial", "shutDownTask") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_rebel", "shutDownTask") || groundquests.isTaskActive(player, "outbreak_quest_facility_02_neutral", "shutDownTask"));
    }
    public boolean computer_console_level_02_command_console_condition_hasQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "outbreak_quest_facility_02_imperial") || groundquests.isQuestActive(player, "outbreak_quest_facility_02_rebel") || groundquests.isQuestActive(player, "outbreak_quest_facility_02_neutral")) || (groundquests.isQuestActive(player, "outbreak_quest_facility_03"));
    }
    public boolean computer_console_level_02_command_console_condition_mustFindVents(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "outbreak_quest_facility_03", "comm3") || groundquests.isTaskActive(player, "outbreak_quest_facility_03", "comm3") || groundquests.isTaskActive(player, "outbreak_quest_facility_03", "comm3")) || (groundquests.isTaskActive(player, "outbreak_quest_facility_03", "useConsoleFindVents") || groundquests.isTaskActive(player, "outbreak_quest_facility_03", "useConsoleFindVents") || groundquests.isTaskActive(player, "outbreak_quest_facility_03", "useConsoleFindVents"));
    }
    public boolean computer_console_level_02_command_console_condition_hasDeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((groundquests.hasCompletedQuest(player, "outbreak_quest_facility_02_neutral") || groundquests.hasCompletedQuest(player, "outbreak_quest_facility_02_rebel") || groundquests.hasCompletedQuest(player, "outbreak_quest_facility_02_imperial")) && !groundquests.isQuestActiveOrComplete(player, "outbreak_quest_facility_03"));
    }
    public void computer_console_level_02_command_console_action_completeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "scienceConsoleUsed");
        String faction = "";
        String questPrefix = "quest/outbreak_faciltiy_02_";
        if (groundquests.hasCompletedQuest(player, "outbreak_quest_02_imperial"))
        {
            faction = "imperial";
        }
        else if (groundquests.hasCompletedQuest(player, "outbreak_quest_02_rebel"))
        {
            faction = "rebel";
        }
        else 
        {
            faction = "neutral";
        }
        if (faction == null || faction.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.completeQuest() Previous faction quest was not found for player: " + player + " (" + getPlayerName(player) + "). This may be due to the player somehow having a previous quest removed.");
            faction = "neutral";
        }
        String questString = questPrefix + faction;
        dictionary params = new dictionary();
        params.put("faction_quest", questString);
        params.put("player", player);
        messageTo(npc, "rewardPlayerFactionalQuest", params, 0, true);
    }
    public void computer_console_level_02_command_console_action_signalConsoleUsedFirstTime(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasUsedCommandConsole");
    }
    public void computer_console_level_02_command_console_action_signalCallAnswered(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "playerAnswered");
    }
    public void computer_console_level_02_command_console_action_signalShutDown(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasDoneShutDown");
    }
    public void computer_console_level_02_command_console_action_signalFoundDmgVents(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "hasFoundAllDmgVents");
    }
    public void computer_console_level_02_command_console_action_fixDeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "outbreak_quest_facility_03");
    }
    public int computer_console_level_02_command_console_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                computer_console_level_02_command_console_action_signalConsoleUsedFirstTime(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                computer_console_level_02_command_console_action_signalShutDown(player, npc);
                string_id message = new string_id(c_stringFile, "s_129");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition_answerSecCall(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition_canUseFirstTime(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                computer_console_level_02_command_console_action_signalConsoleUsedFirstTime(player, npc);
                string_id message = new string_id(c_stringFile, "s_131");
                utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_117"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_120");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_118"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_126");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_118");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                    }
                    utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                computer_console_level_02_command_console_action_fixDeletedQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_27");
                utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int computer_console_level_02_command_console_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.computer_console_level_02_command_console");
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
        detachScript(self, "conversation.computer_console_level_02_command_console");
        return SCRIPT_CONTINUE;
    }
    public int rewardPlayerFactionalQuest(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received no data. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("player"))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical PLAYER key. Catestrophic error.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        if (!isValidId(player) || !exists(player))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but Player is no longer valid or no longer exists. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("faction_quest"))
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but without critical faction key. Catestrophic error for player: " + player + ".");
            return SCRIPT_CONTINUE;
        }
        String factionQuest = params.getString("faction_quest");
        if (factionQuest == null || factionQuest.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler received data but faction quest is not valid. Catestrophic error for player: " + player);
            return SCRIPT_CONTINUE;
        }
        groundquests.requestGrantQuest(player, factionQuest);
        CustomerServiceLog("outbreak_themepark", "computer_console_level_02 conversation script.rewardPlayerFactionalQuest() messageHandler requesting that player: " + player + " be granted quest: " + factionQuest);
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
        if (computer_console_level_02_command_console_condition_hasQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_38");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (computer_console_level_02_command_console_condition_answerSecCall(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (computer_console_level_02_command_console_condition_canUseFirstTime(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 1);
                npcStartConversation(player, npc, "computer_console_level_02_command_console", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (computer_console_level_02_command_console_condition_hasDeletedQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 11);
                npcStartConversation(player, npc, "computer_console_level_02_command_console", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
        {
            doAnimationAction(player, "shrug_shoulders");
            string_id message = new string_id(c_stringFile, "s_65");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (computer_console_level_02_command_console_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                utils.setScriptVar(player, "conversation.computer_console_level_02_command_console.branchId", 13);
                npcStartConversation(player, npc, "computer_console_level_02_command_console", message, responses);
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
        if (!conversationId.equals("computer_console_level_02_command_console"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
        if (branchId == 1 && computer_console_level_02_command_console_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && computer_console_level_02_command_console_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && computer_console_level_02_command_console_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && computer_console_level_02_command_console_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && computer_console_level_02_command_console_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && computer_console_level_02_command_console_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && computer_console_level_02_command_console_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && computer_console_level_02_command_console_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && computer_console_level_02_command_console_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && computer_console_level_02_command_console_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && computer_console_level_02_command_console_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.computer_console_level_02_command_console.branchId");
        return SCRIPT_CONTINUE;
    }
}
