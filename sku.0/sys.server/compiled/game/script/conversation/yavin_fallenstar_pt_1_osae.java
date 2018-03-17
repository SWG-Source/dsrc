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
import script.library.space_quest;
import script.library.utils;

public class yavin_fallenstar_pt_1_osae extends script.base_script
{
    public yavin_fallenstar_pt_1_osae()
    {
    }
    public static String c_stringFile = "conversation/yavin_fallenstar_pt_1_osae";
    public boolean yavin_fallenstar_pt_1_osae_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_questActiveorComplete_pt_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenStar_pt1");
        int questId2 = questGetQuestId("quest/yavin_fallenStar_pt2");
        boolean OnTask = (questIsQuestActive(questId1, player)) || (questIsQuestComplete(questId1, player)) || (questIsQuestActive(questId2, player)) || (questIsQuestComplete(questId2, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        int questId2 = questGetQuestId("quest/yavin_fallenstar_pt2");
        int questId3 = questGetQuestId("quest/yavin_fallenstar_pt3");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestComplete(questId2, player)) && (questIsQuestComplete(questId3, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_MeganWarned(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        int yavin_fallenstar_e7_getreward = groundquests.getTaskId(questId1, "yavin_fallenstar_e7_getreward");
        boolean OnTask = questIsTaskActive(questId1, yavin_fallenstar_e7_getreward, player);
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_questNotDone(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        int yavin_fallenstar_e3_warn_megan = groundquests.getTaskId(questId1, "yavin_fallenstar_e3_warn_megan");
        int yavin_fallenstar_e4_fight_yak = groundquests.getTaskId(questId1, "yavin_fallenstar_e4_fight_yak");
        boolean OnTask = questIsTaskActive(questId1, yavin_fallenstar_e3_warn_megan, player) || questIsTaskActive(questId1, yavin_fallenstar_e4_fight_yak, player);
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_Pt1_questComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        boolean OnTask = questIsQuestComplete(questId1, player);
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_questNotDone_pt_2_Wuioe(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt2");
        int yavin_fallenstar_e11_kill_robbers = groundquests.getTaskId(questId1, "yavin_fallenstar_e11_kill_robbers");
        int yavin_fallenstar_e12_kill_smugglers = groundquests.getTaskId(questId1, "yavin_fallenstar_e12_kill_smugglers");
        boolean OnTask = questIsTaskActive(questId1, yavin_fallenstar_e11_kill_robbers, player) || questIsTaskActive(questId1, yavin_fallenstar_e12_kill_smugglers, player);
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_spaceCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "recovery", "yavin_fallenstar_pt_2_recovery"))
        {
            return true;
        }
        return false;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_questNotDone_pt_3_ship(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt3");
        int yavin_fallenstar_e14_interception = groundquests.getTaskId(questId1, "yavin_fallenstar_e14_interception");
        boolean OnTask = questIsTaskActive(questId1, yavin_fallenstar_e14_interception, player) && !(space_quest.hasWonQuest(player, "recovery", "yavin_fallenstar_pt_2_recovery"));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_opcaCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt2");
        int yavin_fallenstar_e14_return_osae = groundquests.getTaskId(questId1, "yavin_fallenstar_e14_return_osae");
        boolean OnTask = questIsTaskActive(questId1, yavin_fallenstar_e14_return_osae, player);
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_HasJTL(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_fallenstar_pt1");
        int questId2 = questGetQuestId("quest/yavin_fallenstar_pt2");
        boolean OnTask = (questIsQuestComplete(questId1, player)) && (questIsQuestComplete(questId2, player)) && (features.isSpaceEdition(player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_onQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId1 = questGetQuestId("quest/yavin_meganjobs_lostworker");
        int questId2 = questGetQuestId("quest/yavin_meganjobs_hyperdrive");
        int questId3 = questGetQuestId("quest/yavin_meganjobs_killrival");
        boolean OnTask = (questIsQuestActive(questId1, player)) || (questIsQuestActive(questId2, player)) || (questIsQuestActive(questId3, player));
        return OnTask;
    }
    public boolean yavin_fallenstar_pt_1_osae_condition_spaceFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasFailedQuest(player, "recovery", "yavin_fallenstar_pt_2_recovery") || space_quest.hasAbortedQuest(player, "recovery", "yavin_fallenstar_pt_2_recovery");
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_1(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e8");
    }
    public void yavin_fallenstar_pt_1_osae_action_giveQuest_pt_1(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_fallenstar_pt1");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void yavin_fallenstar_pt_1_osae_action_facePlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void yavin_fallenstar_pt_1_osae_action_giveQuest_pt_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int questId = questGetQuestId("quest/yavin_fallenstar_pt2");
        groundquests.grantQuest(questId, player, npc, true);
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_2_Opca(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e26");
    }
    public void yavin_fallenstar_pt_1_osae_action_spaceSignal_pt(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenStar_launch_e14");
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Imperial(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenStar_launch_e22");
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_chose(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenStar_launch_e19");
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Rebel(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenStar_launch_e21");
    }
    public void yavin_fallenstar_pt_1_osae_action_giveQuest_pt_3(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.clearQuest(player, "yavin_fallenstar_pt3");
        groundquests.grantQuest(player, "yavin_fallenstar_pt3");
        space_quest.clearQuestFlags(player, "recovery", "yavin_fallenstar_pt_2_recovery");
        space_quest.grantQuest(player, "recovery", "yavin_fallenstar_pt_2_recovery");
    }
    public void yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_walkaway(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "yavin_fallenstar_launch_e35");
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_120"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Imperial(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_121"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Rebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_124"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Imperial(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_125"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Rebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_126"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_127");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_129");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Imperial(player, npc);
                string_id message = new string_id(c_stringFile, "s_133");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_129"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_Rebel(player, npc);
                string_id message = new string_id(c_stringFile, "s_134");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_130"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_walkaway(player, npc);
                string_id message = new string_id(c_stringFile, "s_132");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_106"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_109");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_110"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_giveQuest_pt_3(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_84"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_103"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_giveQuest_pt_3(player, npc);
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_108");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_835"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_837");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_839"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_841");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_845"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_847");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_849"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_851");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_853");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_855");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_853"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_847");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_855"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_847");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79"))
        {
            yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_2_Opca(player, npc);
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_2_Opca(player, npc);
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_76");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_76"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_83");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_giveQuest_pt_2(player, npc);
                string_id message = new string_id(c_stringFile, "s_88");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
                string_id message = new string_id(c_stringFile, "s_56");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_807"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_809");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_811");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_815");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_823");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_811"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_813");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_815"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_817");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_819");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_821");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_823"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_831");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_819"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_831");
                    }
                    utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_821"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_813");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int yavin_fallenstar_pt_1_osae_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_827"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                yavin_fallenstar_pt_1_osae_action_giveQuest_pt_1(player, npc);
                string_id message = new string_id(c_stringFile, "s_829");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_831"))
        {
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_813");
                utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
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
            detachScript(self, "conversation.yavin_fallenstar_pt_1_osae");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Osae Meilea");
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_INTERESTING);
        setInvulnerable(self, true);
        setName(self, "Osae Meilea");
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
        detachScript(self, "conversation.yavin_fallenstar_pt_1_osae");
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
        if (yavin_fallenstar_pt_1_osae_condition_questComplete(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_101");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_spaceCompleted(player, npc))
        {
            doAnimationAction(npc, "laugh_cackle");
            yavin_fallenstar_pt_1_osae_action_rewardSignal_pt_3_chose(player, npc);
            string_id message = new string_id(c_stringFile, "s_118");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 2);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_spaceFailed(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_105");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_106");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 8);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_questNotDone_pt_3_ship(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_75");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_84");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 10);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_HasJTL(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_102");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 12);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_questNotDone(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_833");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_835");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_839");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 15);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_MeganWarned(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_843");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_845");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_849");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 18);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_questNotDone_pt_2_Wuioe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_57");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 21);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_opcaCompleted(player, npc))
        {
            doAnimationAction(npc, "thank");
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_77");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 24);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition_Pt1_questComplete(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_52");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 26);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
        {
            yavin_fallenstar_pt_1_osae_action_facePlayer(player, npc);
            string_id message = new string_id(c_stringFile, "s_805");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (yavin_fallenstar_pt_1_osae_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_807");
                }
                utils.setScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId", 32);
                npcStartConversation(player, npc, "yavin_fallenstar_pt_1_osae", message, responses);
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
        if (!conversationId.equals("yavin_fallenstar_pt_1_osae"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
        if (branchId == 2 && yavin_fallenstar_pt_1_osae_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && yavin_fallenstar_pt_1_osae_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && yavin_fallenstar_pt_1_osae_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && yavin_fallenstar_pt_1_osae_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && yavin_fallenstar_pt_1_osae_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && yavin_fallenstar_pt_1_osae_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && yavin_fallenstar_pt_1_osae_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && yavin_fallenstar_pt_1_osae_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && yavin_fallenstar_pt_1_osae_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && yavin_fallenstar_pt_1_osae_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && yavin_fallenstar_pt_1_osae_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && yavin_fallenstar_pt_1_osae_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && yavin_fallenstar_pt_1_osae_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && yavin_fallenstar_pt_1_osae_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && yavin_fallenstar_pt_1_osae_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && yavin_fallenstar_pt_1_osae_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && yavin_fallenstar_pt_1_osae_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && yavin_fallenstar_pt_1_osae_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && yavin_fallenstar_pt_1_osae_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.yavin_fallenstar_pt_1_osae.branchId");
        return SCRIPT_CONTINUE;
    }
}
