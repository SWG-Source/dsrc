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

public class npe_station_han_solo extends script.base_script
{
    public npe_station_han_solo()
    {
    }
    public static String c_stringFile = "conversation/npe_station_han_solo";
    public boolean npe_station_han_solo_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean npe_station_han_solo_condition_playerOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isQuestActive(player, "npe_main_quest") && !groundquests.isTaskActive(player, "npe_main_quest", "talktohan1"));
    }
    public boolean npe_station_han_solo_condition_playerOnLastHanStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest", "talktohan2");
    }
    public boolean npe_station_han_solo_condition_playerCompletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_main_quest");
    }
    public boolean npe_station_han_solo_condition_playeronFirstHanStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest", "talktohan1");
    }
    public boolean npe_station_han_solo_condition_playerOnQuest_2(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isQuestActive(player, "npe_main_quest_2");
    }
    public boolean npe_station_han_solo_condition_Main2onInaldrastep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest_2", "inaldra1");
    }
    public boolean npe_station_han_solo_condition_Main2onHanStep1(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest_2", "han1");
    }
    public boolean npe_station_han_solo_condition_Main2onInfoStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest_2", "getinfo");
    }
    public boolean npe_station_han_solo_condition_Main2onPerformStep(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest_2", "perform");
    }
    public boolean npe_station_han_solo_condition_Main2onTarandQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "npe_main_quest_2", "tarand") || groundquests.isQuestActive(player, "npe_side_smuggle");
    }
    public boolean npe_station_han_solo_condition_Main2FinishedTarandQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.isTaskActive(player, "npe_main_quest_2", "han2") && groundquests.hasCompletedQuest(player, "npe_side_smuggle"));
    }
    public boolean npe_station_han_solo_condition_Main2Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "npe_main_quest_2");
    }
    public void npe_station_han_solo_action_signalHan1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_main_han1");
    }
    public void npe_station_han_solo_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void npe_station_han_solo_action_signalHan2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_main_han2");
    }
    public void npe_station_han_solo_action_giveMainQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_main_quest");
    }
    public void npe_station_han_solo_action_giveMainQuest_2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.grantQuest(player, "npe_main_quest_2");
    }
    public void npe_station_han_solo_action_signalMain2Han1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_main2_han_reply");
    }
    public void npe_station_han_solo_action_signalMain2Han2(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "npe_main2_han_final");
    }
    public int npe_station_han_solo_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_86");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_70"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_71");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_77"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "expect_tip");
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                npe_station_han_solo_action_signalMain2Han1(player, npc);
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_81"))
        {
            doAnimationAction(player, "feed_creature_medium");
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                npe_station_han_solo_action_signalMain2Han2(player, npc);
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_46"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                npe_station_han_solo_action_giveMainQuest_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thank");
                npe_station_han_solo_action_signalHan2(player, npc);
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_50"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_46");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_96"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_66");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "thumb_up");
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cough_polite");
                npe_station_han_solo_action_signalHan1(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int npe_station_han_solo_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.npe_station_han_solo");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Han Solo");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Han Solo");
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
        detachScript(self, "conversation.npe_station_han_solo");
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
        if (npe_station_han_solo_condition_Main2Complete(player, npc))
        {
            npe_station_han_solo_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_83");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo_condition_playerOnQuest_2(player, npc))
        {
            npe_station_han_solo_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_67");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo_condition_Main2onInaldrastep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_han_solo_condition_Main2onHanStep1(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (npe_station_han_solo_condition_Main2onInfoStep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (npe_station_han_solo_condition_Main2onPerformStep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (npe_station_han_solo_condition_Main2onTarandQuest(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (npe_station_han_solo_condition_Main2FinishedTarandQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_70");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_72");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 2);
                npcStartConversation(player, npc, "npe_station_han_solo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo_condition_playerCompletedQuest(player, npc))
        {
            doAnimationAction(npc, "thank");
            npe_station_han_solo_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_34");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 11);
                npcStartConversation(player, npc, "npe_station_han_solo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo_condition_playerOnQuest(player, npc))
        {
            npe_station_han_solo_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!npe_station_han_solo_condition_playerOnLastHanStep(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_han_solo_condition_playerOnLastHanStep(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 16);
                npcStartConversation(player, npc, "npe_station_han_solo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo_condition_playeronFirstHanStep(player, npc))
        {
            npe_station_han_solo_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_58");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (npe_station_han_solo_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                }
                utils.setScriptVar(player, "conversation.npe_station_han_solo.branchId", 20);
                npcStartConversation(player, npc, "npe_station_han_solo", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (npe_station_han_solo_condition__defaultCondition(player, npc))
        {
            npe_station_han_solo_action_giveMainQuest(player, npc);
            string_id message = new string_id(c_stringFile, "s_102");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("npe_station_han_solo"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.npe_station_han_solo.branchId");
        if (branchId == 2 && npe_station_han_solo_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && npe_station_han_solo_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && npe_station_han_solo_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && npe_station_han_solo_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && npe_station_han_solo_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && npe_station_han_solo_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && npe_station_han_solo_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && npe_station_han_solo_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && npe_station_han_solo_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && npe_station_han_solo_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && npe_station_han_solo_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && npe_station_han_solo_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && npe_station_han_solo_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && npe_station_han_solo_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.npe_station_han_solo.branchId");
        return SCRIPT_CONTINUE;
    }
}
