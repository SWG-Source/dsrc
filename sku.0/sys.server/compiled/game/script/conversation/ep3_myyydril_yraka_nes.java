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

public class ep3_myyydril_yraka_nes extends script.base_script
{
    public ep3_myyydril_yraka_nes()
    {
    }
    public static String c_stringFile = "conversation/ep3_myyydril_yraka_nes";
    public boolean ep3_myyydril_yraka_nes_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedOther(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_nawika_escort_1");
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_destroyloot_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_destroyloot_1"));
    }
    public boolean ep3_myyydril_yraka_nes_condition_isActiveTaskOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_yraka_destroyloot_1", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_destroyloot_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_destroyloot_1");
    }
    public boolean ep3_myyydril_yraka_nes_condition_isActiveTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_yraka_retrieve_2", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_retrieve_2", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedTaskTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_retrieve_2", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_retrieve_2"));
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_retrieve_2");
    }
    public boolean ep3_myyydril_yraka_nes_condition_isActiveTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_yraka_retrieve_3", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_retrieve_3", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedTaskThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_retrieve_3", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_retrieve_3"));
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_retrieve_3");
    }
    public boolean ep3_myyydril_yraka_nes_condition_isTaskActiveFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_yraka_talkto_6", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_talkto_6", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_talkto_6");
    }
    public boolean ep3_myyydril_yraka_nes_condition_completeEpic1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_epic_1");
    }
    public boolean ep3_myyydril_yraka_nes_condition_isActiveQuestEpic(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_myyydril_yraka_epic_1", 0) || groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_epic_1", 0))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean ep3_myyydril_yraka_nes_condition_hasCompletedTaskEpic(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "ep3_myyydril_yraka_epic_1", 0) && !groundquests.hasCompletedQuest(player, "ep3_myyydril_yraka_epic_1"));
    }
    public void ep3_myyydril_yraka_nes_action_giveQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_yraka_destroyloot_1");
    }
    public void ep3_myyydril_yraka_nes_action_giveQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_yraka_retrieve_2");
    }
    public void ep3_myyydril_yraka_nes_action_giveQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_yraka_retrieve_3");
    }
    public void ep3_myyydril_yraka_nes_action_giveSignal1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "giveReward");
    }
    public void ep3_myyydril_yraka_nes_action_giveQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_yraka_talkto_6");
    }
    public void ep3_myyydril_yraka_nes_action_grantQuestEpic(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_myyydril_yraka_epic_1");
    }
    public void ep3_myyydril_yraka_nes_action_giveSignal2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "partsrule");
    }
    public int ep3_myyydril_yraka_nes_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_898"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_902");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_910"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveSignal2(player, npc);
                string_id message = new string_id(c_stringFile, "s_914");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_916");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_916"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_918");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_922"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_924");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_928"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_930");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_932");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_948");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_932"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_934");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_936");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_948"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_950");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_936"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_938");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_940");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_944");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_940"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_grantQuestEpic(player, npc);
                string_id message = new string_id(c_stringFile, "s_942");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_944"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_946");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_957"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_959");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_961");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_961"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_963");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_965");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_965"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_967");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_971"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_973");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_975");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_975"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_977");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_983"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_985");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_987");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_995"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_997");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_987"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_989");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_991");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_991"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_993");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1001"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1003");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1005");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1005"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1007");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1011"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1013");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1017"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1019");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1021");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1025");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1029"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1031");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1021"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1023");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1025"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1027");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1035"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_1037");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1043"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1045");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1047");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1047"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1049");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1051");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1096");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1051"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1053");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1055");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1096"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1098");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1100");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1112");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1055"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1057");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1059");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1059"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1061");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1063");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1063"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1065");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1067");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1080");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1067"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1069");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1071");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1075");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1080"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1082");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1084");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1071"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1073");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1075"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1078");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1084"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1086");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1088");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1092");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1088"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1090");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1092"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1094");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1100"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1104");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1108");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1112"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1116");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1120");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1104"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1106");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1108"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1110");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1120"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1125");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1129");
                    }
                    utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_myyydril_yraka_nes_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1125"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ep3_myyydril_yraka_nes_action_giveQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1127");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1129"))
        {
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1131");
                utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
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
            detachScript(self, "conversation.ep3_myyydril_yraka_nes");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "yraka"));
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, new string_id("ep3/npc_names", "yraka"));
        setInvulnerable(self, true);
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
        detachScript(self, "conversation.ep3_myyydril_yraka_nes");
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
        if (ep3_myyydril_yraka_nes_condition_completeEpic1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_894");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_898");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 1);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedTaskEpic(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_906");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_910");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 3);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_isActiveQuestEpic(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_920");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_922");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 6);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_926");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_928");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 8);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_isTaskActiveFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_952");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_955");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_957");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 16);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedTaskThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_969");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_971");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 20);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_isActiveTaskThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_979");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_981");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_983");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_995");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 24);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_999");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1001");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 29);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_isActiveTaskTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1009");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1011");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 32);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1015");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1017");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1029");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 34);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedTaskOne(player, npc))
        {
            ep3_myyydril_yraka_nes_action_giveSignal1(player, npc);
            string_id message = new string_id(c_stringFile, "s_1033");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1035");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 39);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_isActiveTaskOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1039");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition_hasCompletedOther(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1041");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1043");
                }
                utils.setScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId", 42);
                npcStartConversation(player, npc, "ep3_myyydril_yraka_nes", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_myyydril_yraka_nes_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1133");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_myyydril_yraka_nes"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
        if (branchId == 1 && ep3_myyydril_yraka_nes_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && ep3_myyydril_yraka_nes_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && ep3_myyydril_yraka_nes_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_myyydril_yraka_nes_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && ep3_myyydril_yraka_nes_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_myyydril_yraka_nes_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_myyydril_yraka_nes_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_myyydril_yraka_nes_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && ep3_myyydril_yraka_nes_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && ep3_myyydril_yraka_nes_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_myyydril_yraka_nes_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_myyydril_yraka_nes_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_myyydril_yraka_nes_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && ep3_myyydril_yraka_nes_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && ep3_myyydril_yraka_nes_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && ep3_myyydril_yraka_nes_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && ep3_myyydril_yraka_nes_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_myyydril_yraka_nes_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_myyydril_yraka_nes_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && ep3_myyydril_yraka_nes_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && ep3_myyydril_yraka_nes_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && ep3_myyydril_yraka_nes_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && ep3_myyydril_yraka_nes_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && ep3_myyydril_yraka_nes_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && ep3_myyydril_yraka_nes_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && ep3_myyydril_yraka_nes_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && ep3_myyydril_yraka_nes_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && ep3_myyydril_yraka_nes_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && ep3_myyydril_yraka_nes_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && ep3_myyydril_yraka_nes_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && ep3_myyydril_yraka_nes_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && ep3_myyydril_yraka_nes_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && ep3_myyydril_yraka_nes_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && ep3_myyydril_yraka_nes_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && ep3_myyydril_yraka_nes_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && ep3_myyydril_yraka_nes_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_myyydril_yraka_nes.branchId");
        return SCRIPT_CONTINUE;
    }
}
