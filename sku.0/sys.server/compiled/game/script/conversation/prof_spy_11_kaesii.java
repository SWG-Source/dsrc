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

public class prof_spy_11_kaesii extends script.base_script
{
    public prof_spy_11_kaesii()
    {
    }
    public static String c_stringFile = "conversation/prof_spy_11_kaesii";
    public boolean prof_spy_11_kaesii_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean prof_spy_11_kaesii_condition_onContact(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/prof_spy_11");
        int prof_spy_goto_e2 = groundquests.getTaskId(questId1, "prof_spy_goto_e2");
        boolean onTask = questIsTaskActive(questId1, prof_spy_goto_e2, player);
        return onTask;
    }
    public boolean prof_spy_11_kaesii_condition_onMission11(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/prof_spy_11a");
        int spy_mission_1_e1 = groundquests.getTaskId(questId1, "spy_mission_1_e1");
        boolean onTask = questIsTaskActive(questId1, spy_mission_1_e1, player);
        return onTask;
    }
    public boolean prof_spy_11_kaesii_condition_mission11Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/prof_spy_11a");
        int spy_mission_1_e3 = groundquests.getTaskId(questId1, "spy_mission_1_e3");
        boolean onTask = questIsTaskActive(questId1, spy_mission_1_e3, player);
        return onTask;
    }
    public boolean prof_spy_11_kaesii_condition_missionIntroComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/prof_spy_11");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean prof_spy_11_kaesii_condition_finishedMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest/prof_spy_11a");
    }
    public void prof_spy_11_kaesii_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void prof_spy_11_kaesii_action_signalMission11Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "spy_mission_1_e3");
    }
    public void prof_spy_11_kaesii_action_grantMission11(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/prof_spy_11a");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void prof_spy_11_kaesii_action_signalGoto(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "prof_spy_goto_e2");
    }
    public int prof_spy_11_kaesii_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_81");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                prof_spy_11_kaesii_action_signalMission11Complete(player, npc);
                string_id message = new string_id(c_stringFile, "s_87");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_76");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_108"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_109");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_31"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_55"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                prof_spy_11_kaesii_action_grantMission11(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                prof_spy_11_kaesii_action_grantMission11(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_63"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                prof_spy_11_kaesii_action_grantMission11(player, npc);
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_113");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_93"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_95");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_113"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_99");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_103");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                    }
                    utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int prof_spy_11_kaesii_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105"))
        {
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_111");
                utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
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
            detachScript(self, "conversation.prof_spy_11_kaesii");
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
        detachScript(self, "conversation.prof_spy_11_kaesii");
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
        if (prof_spy_11_kaesii_condition_finishedMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_115");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (prof_spy_11_kaesii_condition_mission11Complete(player, npc))
        {
            prof_spy_11_kaesii_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_77");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 2);
                npcStartConversation(player, npc, "prof_spy_11_kaesii", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (prof_spy_11_kaesii_condition_onMission11(player, npc))
        {
            prof_spy_11_kaesii_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                }
                utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 8);
                npcStartConversation(player, npc, "prof_spy_11_kaesii", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (prof_spy_11_kaesii_condition_missionIntroComplete(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_107");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 11);
                npcStartConversation(player, npc, "prof_spy_11_kaesii", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (prof_spy_11_kaesii_condition_onContact(player, npc))
        {
            prof_spy_11_kaesii_action_signalGoto(player, npc);
            string_id message = new string_id(c_stringFile, "s_25");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                }
                utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 13);
                npcStartConversation(player, npc, "prof_spy_11_kaesii", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
        {
            prof_spy_11_kaesii_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (prof_spy_11_kaesii_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                }
                utils.setScriptVar(player, "conversation.prof_spy_11_kaesii.branchId", 25);
                npcStartConversation(player, npc, "prof_spy_11_kaesii", message, responses);
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
        if (!conversationId.equals("prof_spy_11_kaesii"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
        if (branchId == 2 && prof_spy_11_kaesii_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && prof_spy_11_kaesii_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && prof_spy_11_kaesii_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && prof_spy_11_kaesii_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && prof_spy_11_kaesii_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && prof_spy_11_kaesii_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && prof_spy_11_kaesii_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && prof_spy_11_kaesii_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && prof_spy_11_kaesii_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && prof_spy_11_kaesii_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && prof_spy_11_kaesii_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && prof_spy_11_kaesii_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && prof_spy_11_kaesii_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && prof_spy_11_kaesii_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && prof_spy_11_kaesii_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && prof_spy_11_kaesii_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && prof_spy_11_kaesii_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && prof_spy_11_kaesii_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && prof_spy_11_kaesii_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && prof_spy_11_kaesii_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && prof_spy_11_kaesii_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && prof_spy_11_kaesii_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && prof_spy_11_kaesii_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && prof_spy_11_kaesii_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.prof_spy_11_kaesii.branchId");
        return SCRIPT_CONTINUE;
    }
}
