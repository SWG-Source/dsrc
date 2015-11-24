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
import script.library.features;
import script.library.groundquests;
import script.library.utils;

public class yavin_fallenstar_pt_1_megan extends script.base_script
{
    public yavin_fallenstar_pt_1_megan()
    {
    }
    public static String c_stringFile = "conversation/yavin_fallenstar_pt_1_megan";
    public boolean yavin_fallenstar_pt_1_megan_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_onquestWarn(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_fallenstar_pt1");
        int yavin_fallenstar_e3_warn_megan = groundquests.getTaskId(questId, "yavin_fallenstar_e3_warn_megan");
        boolean onTask = (questIsTaskActive(questId, yavin_fallenstar_e3_warn_megan, player));
        return onTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_defeatYak(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        int yavin_fallenstar_e5_threaten_megan = groundquests.getTaskId(questId1, "yavin_fallenstar_e5_threaten_megan");
        boolean onTask = questIsTaskActive(questId1, yavin_fallenstar_e5_threaten_megan, player);
        return onTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_escortcomplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_lostworker");
        int finishEscort = groundquests.getTaskId(questId1, "yavin_meganjobs_e6_worker_megan");
        boolean onTask = questIsTaskActive(questId1, finishEscort, player);
        return onTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_lostWorker_Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_lostworker");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_hyperdriveRecovered(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        int recovered = groundquests.getTaskId(questId1, "yavin_meganjobs_e13_hyperdrive_megan");
        boolean onTask = questIsTaskActive(questId1, recovered, player);
        return onTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_hyperdrive_Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        boolean OnTask = (questIsQuestComplete(questId1, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_MeganJobs_Complete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_lostworker");
        int questId2 = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        int questId3 = questGetQuestId("quest/yavin_meganjobs_killrival");
        int questId4 = questGetQuestId("quest/yavin_fallenstar_pt1");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestComplete(questId2, player)) && (questIsQuestComplete(questId3, player)) && (!(questIsQuestActive(questId4, player)));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_rivalsKilled(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_killrival");
        int rivalskilled = groundquests.getTaskId(questId1, "yavin_meganjobs_e20_scavangers_megan");
        boolean onTask = questIsTaskActive(questId1, rivalskilled, player);
        return onTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_rivals(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_killrival");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_hyperdrive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_lostworker(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_lostworker");
        boolean OnTask = (questIsQuestActive(questId1, player));
        return OnTask;
    }
    public void yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e4(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e4");
    }
    public void yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e7(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e7");
    }
    public void yavin_fallenstar_pt_1_megan_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void yavin_fallenstar_pt_1_megan_action_giveQuest_lostWorker(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_meganjobs_lostworker");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void yavin_fallenstar_pt_1_megan_action_rewardSignal_lostWorker(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_meganjobs_launch_e7");
    }
    public void yavin_fallenstar_pt_1_megan_action_giveQuest_hyperdrive(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void yavin_fallenstar_pt_1_megan_action_rewardSignal_hyperDrive(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_meganjobs_launch_e14");
    }
    public void yavin_fallenstar_pt_1_megan_action_giveQuest_killRivals(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_meganjobs_killrival");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void yavin_fallenstar_pt_1_megan_action_rewardSignal_killRival(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_meganjobs_launch_e21");
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1489"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e4(player, npc);
                string_id message = new string_id(c_stringFile, "s_1491");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1493"))
        {
            if (yavin_fallenstar_pt_1_megan_condition_onquestWarn(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1495");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1497");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1497"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e4(player, npc);
                string_id message = new string_id(c_stringFile, "s_1491");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1501"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1503");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1505");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1511");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1513"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e7(player, npc);
                string_id message = new string_id(c_stringFile, "s_1515");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1505"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1507");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1509");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1511"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e7(player, npc);
                string_id message = new string_id(c_stringFile, "s_1515");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1509"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_yavin_fallenStar_launch_e7(player, npc);
                string_id message = new string_id(c_stringFile, "s_1515");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_rewardSignal_killRival(player, npc);
                string_id message = new string_id(c_stringFile, "s_103");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "adjust");
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_86"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_giveQuest_killRivals(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_68"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_rewardSignal_hyperDrive(player, npc);
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_giveQuest_hyperdrive(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_rewardSignal_lostWorker(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_106"))
        {
            doAnimationAction(player, "shake_head_no");
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_107");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_101");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_megan_action_giveQuest_lostWorker(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_megan_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_101"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_105"))
        {
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
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
            detachScript(self, "conversation.yavin_fallenstar_pt_1_megan");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Nagem Dr' Lar");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Nagem Dr' Lar");
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
        detachScript(self, "conversation.yavin_fallenstar_pt_1_megan");
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
        if (yavin_fallenstar_pt_1_megan_condition_MeganJobs_Complete(player, npc))
        {
            doAnimationAction(npc, "tap_foot");
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_102");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_onquestWarn(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1487");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1489");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1493");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 2);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_defeatYak(player, npc))
        {
            doAnimationAction(npc, "apologize");
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_1499");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1501");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1513");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 5);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_rivalsKilled(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_96");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_99");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 9);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_rivals(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_108");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_hyperdrive_Complete(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_72");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_74");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 12);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_hyperdriveRecovered(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_66");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 17);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_hyperdrive(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_100");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_lostWorker_Complete(player, npc))
        {
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 20);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition_MeganJobs_Onquests_lostworker(player, npc))
        {
            doAnimationAction(npc, "greet");
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_46");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition_escortcomplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!yavin_fallenstar_pt_1_megan_condition_escortcomplete(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 24);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            yavin_fallenstar_pt_1_megan_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_57");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_megan_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId", 27);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_megan", message, responses);
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
        if (!conversationId.equals("yavin_fallenstar_pt_1_megan"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
        if (branchId == 2 && yavin_fallenstar_pt_1_megan_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && yavin_fallenstar_pt_1_megan_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && yavin_fallenstar_pt_1_megan_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && yavin_fallenstar_pt_1_megan_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && yavin_fallenstar_pt_1_megan_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && yavin_fallenstar_pt_1_megan_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && yavin_fallenstar_pt_1_megan_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && yavin_fallenstar_pt_1_megan_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && yavin_fallenstar_pt_1_megan_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && yavin_fallenstar_pt_1_megan_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && yavin_fallenstar_pt_1_megan_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && yavin_fallenstar_pt_1_megan_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && yavin_fallenstar_pt_1_megan_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && yavin_fallenstar_pt_1_megan_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && yavin_fallenstar_pt_1_megan_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && yavin_fallenstar_pt_1_megan_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && yavin_fallenstar_pt_1_megan_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_megan.branchId");
        return SCRIPT_CONTINUE;
    }
}
