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
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class ep3_mining_thom_steele extends script.base_script
{
    public ep3_mining_thom_steele()
    {
    }
    public static String c_stringFile = "conversation/ep3_mining_thom_steele";
    public boolean ep3_mining_thom_steele_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean ep3_mining_thom_steele_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_mining_quest_1"));
    }
    public boolean ep3_mining_thom_steele_condition_isQuestOneActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_mining_quest_1"));
    }
    public boolean ep3_mining_thom_steele_condition_isQuestTwoActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player, "patrol", "corellia_mining_quest_2") || space_quest.hasQuest(player, "destroy_surpriseattack", "corellia_mining_quest_2"));
    }
    public boolean ep3_mining_thom_steele_condition_canTakeQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedQuest(player, "ep3_mining_quest_1") && !(space_quest.hasCompletedQuest(player, "patrol", "corellia_mining_quest_2")));
    }
    public boolean ep3_mining_thom_steele_condition_canTakeQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "patrol", "corellia_mining_quest_2") && !(space_quest.hasQuest(player, "space_mining_destroy", "corellia_mining_quest_3")));
    }
    public boolean ep3_mining_thom_steele_condition_isQuestThreeActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player, "space_mining_destroy", "corellia_mining_quest_3");
    }
    public boolean ep3_mining_thom_steele_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "patrol", "corellia_mining_quest_2") && space_quest.hasWonQuest(player, "destroy_surpriseattack", "corellia_mining_quest_2") && !space_quest.hasQuest(player, "space_mining_destroy", "corellia_mining_quest_3"));
    }
    public boolean ep3_mining_thom_steele_condition_hasBeenRewardedForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasReceivedReward(player, "patrol", "corellia_mining_quest_2") && !space_quest.hasQuest(player, "space_mining_destroy", "corellia_mining_quest_3"));
    }
    public boolean ep3_mining_thom_steele_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "space_mining_destroy", "corellia_mining_quest_3") && !space_quest.hasReceivedReward(player, "space_mining_destroy", "corellia_mining_quest_3"));
    }
    public boolean ep3_mining_thom_steele_condition_completedAll(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "patrol", "corellia_mining_quest_2") && space_quest.hasCompletedQuest(player, "space_mining_destroy", "corellia_mining_quest_3") && space_quest.hasCompletedQuest(player, "space_mining_destroy", "corellia_mining_quest_3") && (groundquests.isQuestActive(player, "ep3_mining_quest_4") || groundquests.hasCompletedQuest(player, "ep3_mining_quest_4")));
    }
    public boolean ep3_mining_thom_steele_condition_canTakeQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "space_mining_destroy", "corellia_mining_quest_3") && !groundquests.isQuestActive(player, "ep3_mining_quest_4") && space_quest.hasReceivedReward(player, "space_mining_destroy", "corellia_mining_quest_3"));
    }
    public boolean ep3_mining_thom_steele_condition_isQuestFourActive(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "ep3_mining_quest_4"));
    }
    public boolean ep3_mining_thom_steele_condition_hasFailedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "patrol", "corellia_mining_quest_2") || space_quest.hasFailedQuest(player, "patrol", "corellia_mining_quest_2"))
        {
            if (!space_quest.hasWonQuest(player, "patrol", "corellia_mining_quest_2"))
            {
                return true;
            }
        }
        if ((space_quest.hasAbortedQuest(player, "destroy_surpriseattack", "corellia_mining_quest_2") || space_quest.hasFailedQuest(player, "destroy_surpriseattack", "corellia_mining_quest_2")) && !space_quest.hasWonQuest(player, "destroy_surpriseattack", "corellia_mining_quest_2"))
        {
            return true;
        }
        return false;
    }
    public boolean ep3_mining_thom_steele_condition_hasFailedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuest(player, "space_mining_destroy", "corellia_mining_quest_3") || space_quest.hasFailedQuest(player, "space_mining_destroy", "corellia_mining_quest_3"))
        {
            if (!space_quest.hasWonQuest(player, "space_mining_destroy", "corellia_mining_quest_3"))
            {
                return true;
            }
        }
        return false;
    }
    public void ep3_mining_thom_steele_action_sendSignalToPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "talkedToThom");
    }
    public void ep3_mining_thom_steele_action_grantQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "corellia_mining_quest_2");
        space_flags.restoreClientPath(player);
    }
    public void ep3_mining_thom_steele_action_grantQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "space_mining_destroy", "corellia_mining_quest_3");
    }
    public void ep3_mining_thom_steele_action_giveReward2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "patrol", "corellia_mining_quest_2"))
        {
            return;
        }
        space_quest.giveReward(player, "patrol", "corellia_mining_quest_2", 1500, "object/tangible/ship/components/weapon/wpn_mining_laser_mk1.iff");
        createObjectInInventoryAllowOverload("object/tangible/ship/components/cargo_hold/crg_starfighter_small.iff", player);
    }
    public void ep3_mining_thom_steele_action_giveReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_mining_destroy", "corellia_mining_quest_3"))
        {
            return;
        }
        space_quest.giveReward(player, "space_mining_destroy", "corellia_mining_quest_3", 2000);
    }
    public void ep3_mining_thom_steele_action_grantQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "ep3_mining_quest_4");
    }
    public int ep3_mining_thom_steele_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_236"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_238");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_240"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_242");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_248"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_250");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_252");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_252"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_254");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_256");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_256"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_giveReward3(player, npc);
                string_id message = new string_id(c_stringFile, "s_258");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_260");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_260"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_262");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_264"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_272");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_274"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_276");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_280"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_282");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_284"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                ep3_mining_thom_steele_action_giveReward2(player, npc);
                string_id message = new string_id(c_stringFile, "s_286");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_288");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_288"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_290");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_292");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_296");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_292"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_294");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_296"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_298");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_304"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_306");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_308");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_308"))
        {
            ep3_mining_thom_steele_action_grantQuest2(player, npc);
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_310");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_314"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_sendSignalToPlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_316");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_318");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_318"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_322");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_326");
                    }
                    utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int ep3_mining_thom_steele_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_322"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ep3_mining_thom_steele_action_grantQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_324");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_326"))
        {
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "yawn");
                string_id message = new string_id(c_stringFile, "s_328");
                utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
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
            detachScript(self, "conversation.ep3_mining_thom_steele");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Thom Steele");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setName(self, "Thom Steele");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.ep3_mining_thom_steele");
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
        if (ep3_mining_thom_steele_condition_completedAll(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_53");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_isQuestFourActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_52");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_canTakeQuestFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_234");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 3);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_hasFailedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_63");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 6);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_isQuestThreeActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_244");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_hasCompletedQuestThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_246");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_248");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 9);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_hasBeenRewardedForQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_268");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_270");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_274");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 15);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_hasFailedQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_55");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 18);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "wave1");
            string_id message = new string_id(c_stringFile, "s_278");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_280");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 20);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_isQuestTwoActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_300");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_canTakeQuestTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_302");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 27);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition_isQuestOneActive(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_312");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_314");
                }
                utils.setScriptVar(player, "conversation.ep3_mining_thom_steele.branchId", 30);
                npcStartConversation(player, npc, "ep3_mining_thom_steele", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (ep3_mining_thom_steele_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_330");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("ep3_mining_thom_steele"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
        if (branchId == 3 && ep3_mining_thom_steele_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && ep3_mining_thom_steele_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && ep3_mining_thom_steele_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && ep3_mining_thom_steele_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && ep3_mining_thom_steele_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && ep3_mining_thom_steele_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && ep3_mining_thom_steele_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && ep3_mining_thom_steele_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && ep3_mining_thom_steele_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && ep3_mining_thom_steele_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && ep3_mining_thom_steele_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && ep3_mining_thom_steele_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && ep3_mining_thom_steele_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && ep3_mining_thom_steele_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && ep3_mining_thom_steele_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && ep3_mining_thom_steele_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && ep3_mining_thom_steele_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.ep3_mining_thom_steele.branchId");
        return SCRIPT_CONTINUE;
    }
}
