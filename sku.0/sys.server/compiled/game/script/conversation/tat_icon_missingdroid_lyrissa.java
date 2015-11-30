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

public class tat_icon_missingdroid_lyrissa extends script.base_script
{
    public tat_icon_missingdroid_lyrissa()
    {
    }
    public static String c_stringFile = "conversation/tat_icon_missingdroid_lyrissa";
    public boolean tat_icon_missingdroid_lyrissa_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_stage9(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_stage8(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e5ore6(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_lyrissa_to_tosche_e5 = groundquests.getTaskId(questId1, "tat_icon_lyrissa_to_tosche_e5");
        int tat_icon_tosche_to_lyrissa_e6 = groundquests.getTaskId(questId1, "tat_icon_tosche_to_lyrissa_e6");
        boolean onTask1 = questIsTaskActive(questId1, tat_icon_lyrissa_to_tosche_e5, player);
        boolean onTask2 = questIsTaskActive(questId1, tat_icon_tosche_to_lyrissa_e6, player);
        boolean onTask = false;
        if (onTask1 || onTask2)
        {
            onTask = true;
        }
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e8_droid2Dren(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_droid_to_dren_e8 = groundquests.getTaskId(questId1, "tat_icon_droid_to_dren_e8");
        boolean onTask = questIsTaskActive(questId1, tat_icon_droid_to_dren_e8, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e7_lyrissa2Droid(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_lyrissa_to_droid_e7 = groundquests.getTaskId(questId1, "tat_icon_lyrissa_to_droid_e7");
        boolean onTask = questIsTaskActive(questId1, tat_icon_lyrissa_to_droid_e7, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e6_tosche2Lyrissa(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_tosche_to_lyrissa_e6 = groundquests.getTaskId(questId1, "tat_icon_tosche_to_lyrissa_e6");
        boolean onTask = questIsTaskActive(questId1, tat_icon_tosche_to_lyrissa_e6, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e5_lyrissa2Tosche(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_lyrissa_to_tosche_e5 = groundquests.getTaskId(questId1, "tat_icon_lyrissa_to_tosche_e5");
        boolean onTask = questIsTaskActive(questId1, tat_icon_lyrissa_to_tosche_e5, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingJawa_e4_dren2Lyrissa(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_dren_to_lyrissa_e4 = groundquests.getTaskId(questId1, "tat_icon_dren_to_lyrissa_e4");
        boolean onTask = questIsTaskActive(questId1, tat_icon_dren_to_lyrissa_e4, player);
        return onTask;
    }
    public boolean tat_icon_missingdroid_lyrissa_condition_fixingjJawa_e3_droid2Dren(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/tat_icon_fixingjawa_1");
        int tat_icon_droid_to_dren_e3 = groundquests.getTaskId(questId1, "tat_icon_droid_to_dren_e3");
        boolean onTask = questIsTaskActive(questId1, tat_icon_droid_to_dren_e3, player);
        return onTask;
    }
    public void tat_icon_missingdroid_lyrissa_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void tat_icon_missingdroid_lyrissa_action_giveQuest_missingHarvest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/tatooine_fixingjawa_missingharvest1");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void tat_icon_missingdroid_lyrissa_action_signal_gotoTosche_e4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_icon_fixingjawa_dren_to_lyrissa_e4");
    }
    public void tat_icon_missingdroid_lyrissa_action_signal_gotoLyrissa_e5(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_icon_fixingjawa_lyrissa_to_tosche_e5");
    }
    public void tat_icon_missingdroid_lyrissa_action_signal_gotoDroid_e6(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "tat_icon_fixingjawa_tosche_to_lyrissa_e6");
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51"))
        {
            if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e8_droid2Dren(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tat_icon_missingdroid_lyrissa_condition_fixingJawa_e8_droid2Dren(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_92");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e6_tosche2Lyrissa(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_94"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                tat_icon_missingdroid_lyrissa_action_signal_gotoDroid_e6(player, npc);
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_114");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_116");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_116"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_118");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tat_icon_missingdroid_lyrissa_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                tat_icon_missingdroid_lyrissa_action_signal_gotoTosche_e4(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
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
            detachScript(self, "conversation.tat_icon_missingdroid_lyrissa");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Lyrissa");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Lyrissa");
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
        detachScript(self, "conversation.tat_icon_missingdroid_lyrissa");
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
        if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e8_droid2Dren(player, npc))
        {
            tat_icon_missingdroid_lyrissa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_33");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                }
                utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 1);
                npcStartConversation(player, npc, "tat_icon_missingdroid_lyrissa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e7_lyrissa2Droid(player, npc))
        {
            tat_icon_missingdroid_lyrissa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_45");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 8);
                npcStartConversation(player, npc, "tat_icon_missingdroid_lyrissa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e5ore6(player, npc))
        {
            tat_icon_missingdroid_lyrissa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_84");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                }
                utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 14);
                npcStartConversation(player, npc, "tat_icon_missingdroid_lyrissa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_lyrissa_condition_fixingJawa_e4_dren2Lyrissa(player, npc))
        {
            tat_icon_missingdroid_lyrissa_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_102");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId", 19);
                npcStartConversation(player, npc, "tat_icon_missingdroid_lyrissa", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tat_icon_missingdroid_lyrissa_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_124");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tat_icon_missingdroid_lyrissa"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
        if (branchId == 1 && tat_icon_missingdroid_lyrissa_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && tat_icon_missingdroid_lyrissa_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tat_icon_missingdroid_lyrissa_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tat_icon_missingdroid_lyrissa_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tat_icon_missingdroid_lyrissa_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && tat_icon_missingdroid_lyrissa_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && tat_icon_missingdroid_lyrissa_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tat_icon_missingdroid_lyrissa_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tat_icon_missingdroid_lyrissa_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tat_icon_missingdroid_lyrissa_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tat_icon_missingdroid_lyrissa_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tat_icon_missingdroid_lyrissa_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tat_icon_missingdroid_lyrissa_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tat_icon_missingdroid_lyrissa_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tat_icon_missingdroid_lyrissa_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tat_icon_missingdroid_lyrissa.branchId");
        return SCRIPT_CONTINUE;
    }
}
