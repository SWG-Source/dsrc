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
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class tatooine_privateer_trainer_shamdon extends script.base_script
{
    public tatooine_privateer_trainer_shamdon()
    {
    }
    public static String c_stringFile = "conversation/tatooine_privateer_trainer_shamdon";
    public boolean tatooine_privateer_trainer_shamdon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_gettingTransReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasWonQuest(player, "assassinate", "tat_priv_quest_trans"))
        {
            return false;
        }
        else if (!space_quest.hasReceivedReward(player, "assassinate", "tat_priv_quest_trans"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasWonQuest(player, "assassinate", "tat_priv_quest_trans"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isMale(obj_id player, obj_id npc) throws InterruptedException
    {
        return (getGender(player) == GENDER_MALE);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_neutral_starships_02"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasDroidSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_droid_02"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasProceduresSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_procedures_02"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasStarshipSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_starships_02"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasWeaponSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_weapons_02"));
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierTwo(player);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_2a") && !space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_2a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_3a") && !space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_3a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "escort", "tatooine_privateer_tier2_4a") && !space_quest.hasReceivedReward(player, "escort", "tatooine_privateer_tier2_4a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "tatooine_privateer_tier2_1a") || space_quest.hasAbortedQuest(player, "assassinate", "tatooine_privateer_tier2_1a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "tatooine_privateer_tier2_2a") || space_quest.hasAbortedQuest(player, "assassinate", "tatooine_privateer_tier2_2a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "tatooine_privateer_tier2_3a") || space_quest.hasAbortedQuest(player, "assassinate", "tatooine_privateer_tier2_3a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "escort", "tatooine_privateer_tier2_4a") || space_quest.hasAbortedQuest(player, "escort", "tatooine_privateer_tier2_4a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "tatooine_privateer_tier2_1a") && !space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_1a"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isRebelPilot(player);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isImperialPilot(player);
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        int mission = 0;
        if (space_flags.hasSpaceFlag(player, "readyForTier2Mission"))
        {
            mission = space_flags.getIntSpaceFlag(player, "readyForTier2Mission");
        }
        if (mission == 1)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        int mission = 0;
        if (space_flags.hasSpaceFlag(player, "readyForTier2Mission"))
        {
            mission = space_flags.getIntSpaceFlag(player, "readyForTier2Mission");
        }
        if (mission == 2)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        int mission = 0;
        if (space_flags.hasSpaceFlag(player, "readyForTier2Mission"))
        {
            mission = space_flags.getIntSpaceFlag(player, "readyForTier2Mission");
        }
        if (mission == 3)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        int mission = 0;
        if (space_flags.hasSpaceFlag(player, "readyForTier2Mission"))
        {
            mission = space_flags.getIntSpaceFlag(player, "readyForTier2Mission");
        }
        if (mission == 4)
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_shamdon_condition_isReadyForMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "readyForTier2Mission");
    }
    public void tatooine_privateer_trainer_shamdon_action_giveTransReward(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.giveReward(player, "assassinate", "tat_priv_quest_trans", 5000);
    }
    public void tatooine_privateer_trainer_shamdon_action_grantEscortDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "tatooine_privateer_tier2_1");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "tatooine_privateer_tier2_1");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_privateer_tier2_1");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "tatooine_privateer_tier2_1a");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void tatooine_privateer_trainer_shamdon_action_buyWeaponSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_weapons_02");
    }
    public void tatooine_privateer_trainer_shamdon_action_buyStarshipSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_starships_02");
    }
    public void tatooine_privateer_trainer_shamdon_action_buyProcedureSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_procedures_02");
    }
    public void tatooine_privateer_trainer_shamdon_action_buyDroidSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_droid_02");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "tatooine_privateer_tier2_2a");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantMisisonThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "tatooine_privateer_tier2_3a");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void tatooine_privateer_trainer_shamdon_action_grantMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "tatooine_privateer_tier2_4a");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void tatooine_privateer_trainer_shamdon_action_flagSeriesComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.setQuestSeriesFlag(player, "tatooine_privateer_tier2", space_quest.QUEST_SERIES_COMPLETED);
    }
    public void tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_1a"))
        {
            space_quest.giveReward(player, "assassinate", "tatooine_privateer_tier2_1a", 5000, "object/tangible/ship/components/booster/bst_mission_reward_neutral_mandal_m_series.iff");
        }
    }
    public void tatooine_privateer_trainer_shamdon_action_rewardForMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_2a"))
        {
            space_quest.giveReward(player, "assassinate", "tatooine_privateer_tier2_2a", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_neutral_hk_military_blaster.iff");
        }
    }
    public void tatooine_privateer_trainer_shamdon_action_rewardForMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier2_3a"))
        {
            space_quest.giveReward(player, "assassinate", "tatooine_privateer_tier2_3a", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_neutral_koensayr_ds23.iff");
        }
    }
    public void tatooine_privateer_trainer_shamdon_action_rewardForMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "tatooine_privateer_tier2_4a"))
        {
            space_quest.giveReward(player, "escort", "tatooine_privateer_tier2_4a", 5000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_neutral_sorosuub_w19.iff");
        }
    }
    public void tatooine_privateer_trainer_shamdon_action_setReadyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 1);
    }
    public void tatooine_privateer_trainer_shamdon_action_setReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 2);
    }
    public void tatooine_privateer_trainer_shamdon_action_setReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 3);
    }
    public void tatooine_privateer_trainer_shamdon_action_setReadyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 4);
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1ff5062"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9839c24f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_739845d3");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aed52b48"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_3c3002fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32965dab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb27779e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d01d0154"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave2");
                string_id message = new string_id(c_stringFile, "s_c261cfae");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32965dab"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_isMale(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_9939e2d7");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_90a85d36");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bb27779e"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_2118caa8");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d14c381d"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_isMale(player, npc))
            {
                doAnimationAction(npc, "greet");
                tatooine_privateer_trainer_shamdon_action_giveTransReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_2e6c7754");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4682ea6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a3dce13");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                tatooine_privateer_trainer_shamdon_action_giveTransReward(player, npc);
                string_id message = new string_id(c_stringFile, "s_c562c4fb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4682ea6");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a3dce13");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4682ea6"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_69e17dd2");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a3dce13"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_69e17dd2");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4682ea6"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_69e17dd2");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a3dce13"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_69e17dd2");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bc000879"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c29ec5c2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_f1bbe36f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_895d093");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34e4d30"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_63227fe4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8be640b8"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_895d093"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_acda8fe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d6695e83"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ad49b89");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_53"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4362ba26"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionTwo(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_dffe951a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 33);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43f63a81"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionTwo(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_75");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 37);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9f5f269"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionTwo(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_88");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 41);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
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
    public int tatooine_privateer_trainer_shamdon_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_92"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_96");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1fe46aff"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_1b1dd9b4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8179979d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8179979d"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionThree(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_1f4922ce");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_109");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 47);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d70dba34"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionThree(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_57adb96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 51);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
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
    public int tatooine_privateer_trainer_shamdon_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_109"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_111"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_66b1a06f");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_124"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_128"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_130");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eca7c053"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionFour(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_2b2c40c1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_137");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_766563c7"))
        {
            tatooine_privateer_trainer_shamdon_action_rewardForMissionFour(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_148");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_d4079379");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_360d8728");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_139"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_141");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_143"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dab97824");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36bdb4df");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_b4790825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_8be95ab4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_98adf66d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_3b48f08");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae1a4b87");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a24306df");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_e4c0ecd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9b1bd09");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f520ab35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b1bed8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7cb248ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d6924ee9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_650a0c35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                string_id message = new string_id(c_stringFile, "s_383435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d262b71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_360d8728"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_154");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_156"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c522568"))
        {
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission1(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_89555084");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission2(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_666ebdc8");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission3(player, npc))
            {
                doAnimationAction(npc, "explain");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_d70e2c34");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_a07f4bc4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f3d46f0b"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bce510d4");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9b0ac27a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff5b4126");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71a8cc01");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ff5b4126"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2640d39c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114c29a9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_71a8cc01"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6e21ce1c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a24761e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_114c29a9"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_75d43a2b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_725573da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_474f9205");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_725573da"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_52e7e5e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_474f9205"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_5f96d75a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 74);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
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
    public int tatooine_privateer_trainer_shamdon_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_179"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_181");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8a24761e"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2640d39c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_114c29a9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_236"))
        {
            if (!tatooine_privateer_trainer_shamdon_condition_hasStarshipSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_241");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasWeaponSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_238");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_243");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_shamdon_condition_hasProceduresSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_239");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_245");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_247");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_241"))
        {
            tatooine_privateer_trainer_shamdon_action_buyStarshipSkill2(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_shamdon_action_setReadyForMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_279");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_281");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_285");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_281"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_283");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_285"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_287");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_243"))
        {
            tatooine_privateer_trainer_shamdon_action_buyWeaponSkill2(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_shamdon_action_setReadyForMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_269");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_271");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_275");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_271"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_273");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_275"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_277");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_245"))
        {
            tatooine_privateer_trainer_shamdon_action_buyProcedureSkill2(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_shamdon_action_setReadyForMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_259");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_261");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_265");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_261"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_263");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_265"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_267");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_247"))
        {
            tatooine_privateer_trainer_shamdon_action_buyDroidSkill2(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_shamdon_action_setReadyForMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_249");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_251");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_255");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_251"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_253");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_255"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_257");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f4b9423e"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "beckon");
                string_id message = new string_id(c_stringFile, "s_5af9448");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c631f8da");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ff30da7");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93ba78e7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1aaeed53"))
        {
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_cbe97427");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_shamdon_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c631f8da"))
        {
            tatooine_privateer_trainer_shamdon_action_grantEscortDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_down");
                string_id message = new string_id(c_stringFile, "s_5423b2d0");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ff30da7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantRecoveryDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                string_id message = new string_id(c_stringFile, "s_9eb35e93");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93ba78e7"))
        {
            tatooine_privateer_trainer_shamdon_action_grantDestroyDuty(player, npc);
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "clap_rousing");
                string_id message = new string_id(c_stringFile, "s_9fa5f777");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
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
            detachScript(self, "conversation.tatooine_privateer_trainer_shamdon");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.tatooine_privateer_trainer_shamdon");
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
        if (!tatooine_privateer_trainer_shamdon_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_dab97824");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isImperial(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_shamdon_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_36bdb4df");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1ff5062");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 4);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_shamdon_condition_hasWonTransQuest(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_b4790825");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aed52b48");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d01d0154");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 7);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_gettingTransReward(player, npc))
        {
            doAnimationAction(npc, "shrug_hands");
            string_id message = new string_id(c_stringFile, "s_8be95ab4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d14c381d");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 13);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_98adf66d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionOne(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            tatooine_privateer_trainer_shamdon_action_grantMissionOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_3b48f08");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionTwo(player, npc))
        {
            doAnimationAction(npc, "point_away");
            tatooine_privateer_trainer_shamdon_action_grantMissionTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_ae1a4b87");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionThree(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            tatooine_privateer_trainer_shamdon_action_grantMisisonThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_a24306df");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_hasFailedMissionFour(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            tatooine_privateer_trainer_shamdon_action_grantMissionFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_e4c0ecd");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardOne(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            tatooine_privateer_trainer_shamdon_action_rewardForMissionOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_d9b1bd09");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bc000879");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34e4d30");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 22);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f520ab35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4362ba26");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43f63a81");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9f5f269");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 31);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardThree(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5b1bed8b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1fe46aff");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 44);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isGettingRewardFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7cb248ee");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eca7c053");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_766563c7");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 54);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isReadyForMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d6924ee9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f3d46f0b");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 63);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_hasAllTier2Skills(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_650a0c35");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 69);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition_isReadyForTraining(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            string_id message = new string_id(c_stringFile, "s_383435");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_236");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 77);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_5d262b71");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_shamdon_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f4b9423e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1aaeed53");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId", 94);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_shamdon", message, responses);
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
        if (!conversationId.equals("tatooine_privateer_trainer_shamdon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
        if (branchId == 4 && tatooine_privateer_trainer_shamdon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_privateer_trainer_shamdon_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_privateer_trainer_shamdon_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && tatooine_privateer_trainer_shamdon_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && tatooine_privateer_trainer_shamdon_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_privateer_trainer_shamdon_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_privateer_trainer_shamdon_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && tatooine_privateer_trainer_shamdon_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_privateer_trainer_shamdon_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && tatooine_privateer_trainer_shamdon_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tatooine_privateer_trainer_shamdon_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_privateer_trainer_shamdon_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_privateer_trainer_shamdon_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && tatooine_privateer_trainer_shamdon_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && tatooine_privateer_trainer_shamdon_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_privateer_trainer_shamdon_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_privateer_trainer_shamdon_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && tatooine_privateer_trainer_shamdon_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && tatooine_privateer_trainer_shamdon_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_privateer_trainer_shamdon_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && tatooine_privateer_trainer_shamdon_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_privateer_trainer_shamdon_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && tatooine_privateer_trainer_shamdon_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && tatooine_privateer_trainer_shamdon_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && tatooine_privateer_trainer_shamdon_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && tatooine_privateer_trainer_shamdon_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && tatooine_privateer_trainer_shamdon_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && tatooine_privateer_trainer_shamdon_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && tatooine_privateer_trainer_shamdon_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && tatooine_privateer_trainer_shamdon_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && tatooine_privateer_trainer_shamdon_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && tatooine_privateer_trainer_shamdon_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && tatooine_privateer_trainer_shamdon_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_privateer_trainer_shamdon_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && tatooine_privateer_trainer_shamdon_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && tatooine_privateer_trainer_shamdon_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && tatooine_privateer_trainer_shamdon_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && tatooine_privateer_trainer_shamdon_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && tatooine_privateer_trainer_shamdon_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && tatooine_privateer_trainer_shamdon_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_privateer_trainer_shamdon_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && tatooine_privateer_trainer_shamdon_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && tatooine_privateer_trainer_shamdon_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && tatooine_privateer_trainer_shamdon_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && tatooine_privateer_trainer_shamdon_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && tatooine_privateer_trainer_shamdon_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && tatooine_privateer_trainer_shamdon_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && tatooine_privateer_trainer_shamdon_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && tatooine_privateer_trainer_shamdon_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && tatooine_privateer_trainer_shamdon_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && tatooine_privateer_trainer_shamdon_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && tatooine_privateer_trainer_shamdon_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && tatooine_privateer_trainer_shamdon_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && tatooine_privateer_trainer_shamdon_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && tatooine_privateer_trainer_shamdon_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && tatooine_privateer_trainer_shamdon_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && tatooine_privateer_trainer_shamdon_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_shamdon.branchId");
        return SCRIPT_CONTINUE;
    }
}
