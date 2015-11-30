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
import script.library.money;
import script.library.space_quest;
import script.library.utils;

public class c_story1_4_imp_contact extends script.base_script
{
    public c_story1_4_imp_contact()
    {
    }
    public static String c_stringFile = "conversation/c_story1_4_imp_contact";
    public boolean c_story1_4_imp_contact_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean c_story1_4_imp_contact_condition_playerCanMakeChoice(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4a");
        int chooseimp = groundquests.getTaskId(questId1, "chooseimp");
        return questIsTaskActive(questId1, chooseimp, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerisImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_4_imp_contact_condition_playerOnRebQuestFirst(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_rebel");
        int reb_choice = groundquests.getTaskId(questId1, "reb_choice");
        return questIsTaskActive(questId1, reb_choice, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerOnRebQuestSecond(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_rebel");
        int rebel_blackmail = groundquests.getTaskId(questId1, "rebel_blackmail");
        return questIsTaskActive(questId1, rebel_blackmail, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerOnImpQuestFirst(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_imperial");
        int imperial_nobribe = groundquests.getTaskId(questId1, "imperial_nobribe");
        return questIsTaskActive(questId1, imperial_nobribe, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerisRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isRebel(player))
        {
            return false;
        }
        return true;
    }
    public boolean c_story1_4_imp_contact_condition_playerIsImperialCompletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_imperial");
        return questIsQuestComplete(questId1, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerOnImpQuestSecond(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_imperial");
        int imperial_space = groundquests.getTaskId(questId1, "imperial_space");
        int imperial_nospace = groundquests.getTaskId(questId1, "getinfo");
        return questIsTaskActive(questId1, imperial_space, player) || questIsTaskComplete(questId1, imperial_space, player) || questIsTaskComplete(questId1, imperial_nospace, player) || questIsTaskActive(questId1, imperial_nospace, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerWonSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "c_story1_4_imp_space"))
        {
            return true;
        }
        return false;
    }
    public boolean c_story1_4_imp_contact_condition_playerHasBribe(obj_id player, obj_id npc) throws InterruptedException
    {
        int cash = getTotalMoney(player);
        if (cash >= 10000)
        {
            return true;
        }
        return false;
    }
    public boolean c_story1_4_imp_contact_condition_playerLostSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "assassinate", "c_story1_4_imp_space") || space_quest.hasFailedQuest(player, "assassinate", "c_story1_4_imp_space")) && (!space_quest.hasWonQuest(player, "assassinate", "c_story1_4_imp_space")))
        {
            return true;
        }
        return false;
    }
    public boolean c_story1_4_imp_contact_condition_playerOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4b_imperial");
        return questIsQuestActive(questId1, player);
    }
    public boolean c_story1_4_imp_contact_condition_playerFailedImpQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/c_story1_4a");
        int questId2 = questGetQuestId("quest/c_story1_4b_imperial");
        return questIsQuestComplete(questId1, player) && (!questIsQuestActive(questId2, player) && (!questIsQuestComplete(questId2, player)));
    }
    public void c_story1_4_imp_contact_action_completeFirstGiveSecond(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_4a_chooseimperial");
        int questId = questGetQuestId("quest/c_story1_4b_imperial");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_story1_4_imp_contact_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void c_story1_4_imp_contact_action_rebStrongarmed(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_4b_rebel_blackmail");
        space_quest.grantQuest(player, "assassinate", "c_story1_4_reb_space");
    }
    public void c_story1_4_imp_contact_action_rebPlayerNoBribe(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.failTask(player, "quest/c_story1_4b_rebel", "reb_choice");
    }
    public void c_story1_4_imp_contact_action_rebPaybribe(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_story1_4b_rebel");
        int rebel_nobribe = groundquests.getTaskId(questId, "rebel_nobribe");
        dictionary params = new dictionary();
        money.requestPayment(player, npc, 10000, "pass_fail", params, true);
        groundquests.sendSignal(player, "c_story1_4b_rebel_oppcontact");
        questFailTask(questId, rebel_nobribe, player);
        space_quest.grantQuest(player, "assassinate", "c_story1_4_reb_space");
    }
    public void c_story1_4_imp_contact_action_impSignal1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_4b_imperial_nobribe");
    }
    public void c_story1_4_imp_contact_action_finishThisQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.completeQuest(player, "quest/c_story1_4b_imperial");
    }
    public void c_story1_4_imp_contact_action_giveSecond(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/c_story1_4b_imperial");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void c_story1_4_imp_contact_action_impGrantSpace(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "c_story1_4_imp_space");
    }
    public void c_story1_4_imp_contact_action_completeSecond(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "c_story1_4b_imperial_infogained1");
        groundquests.sendSignal(player, "c_story1_4b_imperial_infogained2");
    }
    public int c_story1_4_imp_contact_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            doAnimationAction(player, "nod");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 11);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            doAnimationAction(player, "shake_head_no");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            doAnimationAction(player, "taken_aback");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_24"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                c_story1_4_imp_contact_action_completeFirstGiveSecond(player, npc);
                string_id message = new string_id(c_stringFile, "s_25");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                c_story1_4_imp_contact_action_completeFirstGiveSecond(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "backhand_threaten");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "mock");
                string_id message = new string_id(c_stringFile, "s_60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_195");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "expect_tip");
                string_id message = new string_id(c_stringFile, "s_174");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition_playerHasBribe(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!c_story1_4_imp_contact_condition_playerHasBribe(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_176");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_192");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_195"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_rebPlayerNoBribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_176"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                c_story1_4_imp_contact_action_rebPaybribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_180");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_188"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_rebPlayerNoBribe(player, npc);
                string_id message = new string_id(c_stringFile, "s_190");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_192"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_180"))
        {
            doAnimationAction(player, "threaten");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shoo");
                string_id message = new string_id(c_stringFile, "s_182");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_184");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_184"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_186");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "taken_aback");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_104");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            doAnimationAction(player, "point_accusingly");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                c_story1_4_imp_contact_action_rebStrongarmed(player, npc);
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_149"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_151");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_153");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_153"))
        {
            doAnimationAction(player, "salute1");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                c_story1_4_imp_contact_action_giveSecond(player, npc);
                string_id message = new string_id(c_stringFile, "s_155");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_138");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_126"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_138"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_134"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_impSignal1(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_144"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_127"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_167"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_completeSecond(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_152");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_156"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_completeSecond(player, npc);
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_160");
                    }
                    utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_152"))
        {
            doAnimationAction(player, "salute1");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_finishThisQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_finishThisQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_162");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_135"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                c_story1_4_imp_contact_action_impGrantSpace(player, npc);
                string_id message = new string_id(c_stringFile, "s_139");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int c_story1_4_imp_contact_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_161"))
        {
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_165");
                utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
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
            detachScript(self, "conversation.c_story1_4_imp_contact");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Officer M'kae");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Officer M'kae");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.c_story1_4_imp_contact");
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
        if (c_story1_4_imp_contact_condition_playerCanMakeChoice(player, npc))
        {
            doAnimationAction(npc, "salute1");
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition_playerisImperial(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!c_story1_4_imp_contact_condition_playerisImperial(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 1);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerOnRebQuestFirst(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 14);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerOnRebQuestSecond(player, npc))
        {
            doAnimationAction(npc, "shoo");
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_94");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 23);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerisRebel(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_164");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerFailedImpQuest(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_147");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_149");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 29);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerOnImpQuestFirst(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_120");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 32);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerIsImperialCompletedQuest(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_166");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerOnImpQuestSecond(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_142");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition_playerWonSpace(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (c_story1_4_imp_contact_condition_playerLostSpace(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_144");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_127");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 39);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerOnQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_157");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_161");
                }
                utils.setScriptVar(player, "conversation.c_story1_4_imp_contact.branchId", 48);
                npcStartConversation(player, npc, "c_story1_4_imp_contact", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition_playerisImperial(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_170");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (c_story1_4_imp_contact_condition__defaultCondition(player, npc))
        {
            c_story1_4_imp_contact_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_173");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("c_story1_4_imp_contact"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
        if (branchId == 1 && c_story1_4_imp_contact_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && c_story1_4_imp_contact_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && c_story1_4_imp_contact_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && c_story1_4_imp_contact_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && c_story1_4_imp_contact_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && c_story1_4_imp_contact_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && c_story1_4_imp_contact_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && c_story1_4_imp_contact_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && c_story1_4_imp_contact_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && c_story1_4_imp_contact_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && c_story1_4_imp_contact_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && c_story1_4_imp_contact_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && c_story1_4_imp_contact_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && c_story1_4_imp_contact_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && c_story1_4_imp_contact_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && c_story1_4_imp_contact_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && c_story1_4_imp_contact_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && c_story1_4_imp_contact_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && c_story1_4_imp_contact_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && c_story1_4_imp_contact_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && c_story1_4_imp_contact_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && c_story1_4_imp_contact_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && c_story1_4_imp_contact_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && c_story1_4_imp_contact_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && c_story1_4_imp_contact_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && c_story1_4_imp_contact_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && c_story1_4_imp_contact_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && c_story1_4_imp_contact_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && c_story1_4_imp_contact_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && c_story1_4_imp_contact_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.c_story1_4_imp_contact.branchId");
        return SCRIPT_CONTINUE;
    }
}
