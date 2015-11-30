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
import script.library.features;
import script.library.groundquests;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class tatooine_privateer_trainer_1 extends script.base_script
{
    public tatooine_privateer_trainer_1()
    {
    }
    public static String c_stringFile = "conversation/tatooine_privateer_trainer_1";
    public boolean tatooine_privateer_trainer_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tatooine_privateer_trainer_1_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metDravis"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasSmugglerQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player) && tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc));
    }
    public boolean tatooine_privateer_trainer_1_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean tatooine_privateer_trainer_1_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice") && (!space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE)));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_1"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "tatooine_privateer_2"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_3"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "assassinate", "tatooine_privateer_tier1_4a"));
    }
    public boolean tatooine_privateer_trainer_1_condition_missionOneFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuestRecursive(player, "patrol", "tatooine_privateer_1") || space_quest.hasAbortedQuestRecursive(player, "patrol", "tatooine_privateer_1"));
    }
    public boolean tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_neutral_starships_01") || hasSkill(player, "pilot_neutral_procedures_01") || hasSkill(player, "pilot_neutral_weapons_01") || hasSkill(player, "pilot_neutral_droid_01"))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierOne(player);
    }
    public boolean tatooine_privateer_trainer_1_condition_missionTwoFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "tatooine_privateer_2") || space_quest.hasAbortedQuest(player, "destroy", "tatooine_privateer_2"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_1"))
        {
            return true;
        }
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_1") && space_quest.hasReceivedReward(player, "patrol", "tatooine_privateer_1"));
    }
    public boolean tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "destroy", "tatooine_privateer_2"))
        {
            return true;
        }
        return (space_quest.hasCompletedQuest(player, "destroy", "tatooine_privateer_2") && space_quest.hasReceivedReward(player, "destroy", "tatooine_privateer_2"));
    }
    public boolean tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_3"))
        {
            return true;
        }
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "tatooine_privateer_3") && space_quest.hasReceivedReward(player, "patrol", "tatooine_privateer_3"));
    }
    public boolean tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "assassinate", "tatooine_privateer_tier1_4a"))
        {
            return true;
        }
        return (space_quest.hasCompletedQuest(player, "assassinate", "tatooine_privateer_tier1_4a") && space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier1_4a"));
    }
    public boolean tatooine_privateer_trainer_1_condition_missionThreeFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuestRecursive(player, "patrol", "tatooine_privateer_3") || space_quest.hasAbortedQuestRecursive(player, "patrol", "tatooine_privateer_3"));
    }
    public boolean tatooine_privateer_trainer_1_condition_missionFourFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "tatooine_privateer_tier1_4a") || space_quest.hasAbortedQuest(player, "assassinate", "tatooine_privateer_tier1_4a"))
        {
            return true;
        }
        return false;
    }
    public boolean tatooine_privateer_trainer_1_condition_hasStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_droid_01"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasWeaponSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_weapons_01"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasProceduresSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_procedures_01"));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!space_quest.canGrantNewbieShip(player));
    }
    public boolean tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCertifiedShip(player))
        {
            return false;
        }
        else 
        {
            return (space_quest.hasQuest(player, "patrol", "tatooine_privateer_1") || space_quest.hasQuest(player, "destroy_surpriseattack", "tatooine_privateer_1"));
        }
    }
    public boolean tatooine_privateer_trainer_1_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((hasSkill(player, "pilot_neutral_starships_01") || hasSkill(player, "pilot_neutral_procedures_01") || hasSkill(player, "pilot_neutral_weapons_01") || hasSkill(player, "pilot_neutral_droid_01")) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public void tatooine_privateer_trainer_1_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metDravis", true);
    }
    public void tatooine_privateer_trainer_1_action_grantNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
        space_flags.setSpaceTrack(player, space_flags.PRIVATEER_TATOOINE);
        skill.noisyGrantSkill(player, "pilot_neutral_novice");
    }
    public void tatooine_privateer_trainer_1_action_signUpPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
    }
    public void tatooine_privateer_trainer_1_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_snapFingerOne(player, npc);
        if (!space_quest.hasReceivedReward(player, "patrol", "tatooine_privateer_1"))
        {
            space_quest.giveReward(player, "patrol", "tatooine_privateer_1", 100);
        }
    }
    public void tatooine_privateer_trainer_1_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_greet(player, npc);
        space_quest.grantQuest(player, "patrol", "tatooine_privateer_1");
        space_flags.restoreClientPath(player);
    }
    public void tatooine_privateer_trainer_1_action_grantQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_pointAway(player, npc);
        space_quest.grantQuest(player, "destroy", "tatooine_privateer_2");
    }
    public void tatooine_privateer_trainer_1_action_grantQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_shakeHeadDisgust(player, npc);
        space_quest.grantQuest(player, "patrol", "tatooine_privateer_3");
    }
    public void tatooine_privateer_trainer_1_action_grantQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_pointAccusingly(player, npc);
        space_quest.grantQuest(player, "assassinate", "tatooine_privateer_tier1_4a");
    }
    public void tatooine_privateer_trainer_1_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_celebrate(player, npc);
        if (!space_quest.hasReceivedReward(player, "destroy", "tatooine_privateer_2"))
        {
            space_quest.giveReward(player, "destroy", "tatooine_privateer_2", 200);
        }
    }
    public void tatooine_privateer_trainer_1_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_poseProudly(player, npc);
        if (!space_quest.hasReceivedReward(player, "patrol", "tatooine_privateer_3"))
        {
            if (getSpecies(player) != SPECIES_ITHORIAN)
            {
                space_quest.giveReward(player, "patrol", "tatooine_privateer_3", 500, "object/tangible/wearables/bandolier/mercenary_bandolier.iff");
            }
            else 
            {
                space_quest.giveReward(player, "patrol", "tatooine_privateer_3", 500, "object/tangible/wearables/bandolier/ith_mercenary_bandolier.iff");
            }
        }
    }
    public void tatooine_privateer_trainer_1_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "tatooine_privateer_tier1_4a"))
        {
            tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
            space_quest.giveReward(player, "assassinate", "tatooine_privateer_tier1_4a", 1000, "object/tangible/ship/components/weapon/wpn_mission_reward_neutral_mandal_light_blaster.iff");
        }
    }
    public void tatooine_privateer_trainer_1_action_grantStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.noisyGrantSkill(player, "pilot_neutral_starships_01");
    }
    public void tatooine_privateer_trainer_1_action_grantDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.noisyGrantSkill(player, "pilot_neutral_droid_01");
    }
    public void tatooine_privateer_trainer_1_action_grantWeaponSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.noisyGrantSkill(player, "pilot_neutral_weapons_01");
    }
    public void tatooine_privateer_trainer_1_action_grantProceduresSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.noisyGrantSkill(player, "pilot_neutral_procedures_01");
    }
    public void tatooine_privateer_trainer_1_action_buyStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.purchaseSkill(player, "pilot_neutral_starships_01");
    }
    public void tatooine_privateer_trainer_1_action_buyDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.purchaseSkill(player, "pilot_neutral_droid_01");
    }
    public void tatooine_privateer_trainer_1_action_buyWeaponSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.purchaseSkill(player, "pilot_neutral_weapons_01");
    }
    public void tatooine_privateer_trainer_1_action_buyProcedureSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        skill.purchaseSkill(player, "pilot_neutral_procedures_01");
    }
    public void tatooine_privateer_trainer_1_action_grantDestroyDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        space_quest.grantQuest(player, "destroy_duty", "tatooine_privateer_6");
    }
    public void tatooine_privateer_trainer_1_action_grantRecoveryDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_poundFistPalm(player, npc);
        space_quest.grantQuest(player, "recovery_duty", "tatooine_privateer_5");
    }
    public void tatooine_privateer_trainer_1_action_grantEscortDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
        space_quest.grantQuest(player, "escort_duty", "tatooine_privateer_7");
    }
    public void tatooine_privateer_trainer_1_action_grantQuestEight(obj_id player, obj_id npc) throws InterruptedException
    {
        tatooine_privateer_trainer_1_action_pointAccusingly(player, npc);
        space_quest.grantQuest(player, "destroy_duty", "tatooine_privateer_8");
    }
    public void tatooine_privateer_trainer_1_action_pointForward(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_forward");
    }
    public void tatooine_privateer_trainer_1_action_pointToSelf(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_to_self");
    }
    public void tatooine_privateer_trainer_1_action_nodHeadMultiple(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "nod_head_multiple");
    }
    public void tatooine_privateer_trainer_1_action_handsAboveHead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "hands_above_head");
    }
    public void tatooine_privateer_trainer_1_action_rubChinThoughtful(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void tatooine_privateer_trainer_1_action_beckon(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "beckon");
        groundquests.sendSignal(player, "tat_vourk_smugpilot_e1");
    }
    public void tatooine_privateer_trainer_1_action_greet(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "greet");
    }
    public void tatooine_privateer_trainer_1_action_pointAway(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_away");
    }
    public void tatooine_privateer_trainer_1_action_shakeHeadDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void tatooine_privateer_trainer_1_action_pointAccusingly(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "point_accusingly");
    }
    public void tatooine_privateer_trainer_1_action_snapFingerOne(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "snap_finger1");
    }
    public void tatooine_privateer_trainer_1_action_celebrate(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "celebrate");
    }
    public void tatooine_privateer_trainer_1_action_poseProudly(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "pose_proudly");
    }
    public void tatooine_privateer_trainer_1_action_poundFistPalm(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "pound_fist_palm");
    }
    public void tatooine_privateer_trainer_1_action_shrugShoulders(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "shrug_shoulders");
    }
    public void tatooine_privateer_trainer_1_action_slowDown(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "slow_down");
    }
    public void tatooine_privateer_trainer_1_action_smackSelf(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "smack_self");
    }
    public void tatooine_privateer_trainer_1_action_dismiss(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "dismiss");
    }
    public void tatooine_privateer_trainer_1_action_nodHeadOnce(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "nod_head_once");
    }
    public void tatooine_privateer_trainer_1_action_explain(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "explain");
    }
    public void tatooine_privateer_trainer_1_action_tapHead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "tap_head");
    }
    public void tatooine_privateer_trainer_1_action_laughCackle(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "laugh_cackle");
    }
    public void tatooine_privateer_trainer_1_action_lookCasual(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "look_casual");
    }
    public void tatooine_privateer_trainer_1_action_grantSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "neutral");
    }
    public void tatooine_privateer_trainer_1_action_eraseClientPath(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.clearClientPath(player);
        groundquests.sendSignal(player, "tat_vourk_smugpilot_e1");
    }
    public int tatooine_privateer_trainer_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111621e7"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8622fd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fd8bee64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7425c603");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111621e7"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8622fd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fd8bee64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7425c603");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4738e99f"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_599b087d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c4c3d546"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2e13d0e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d038a005"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_7f1ca275");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4738e99f"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_599b087d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c4c3d546"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2e13d0e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d038a005"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_7f1ca275");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4738e99f"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_599b087d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c4c3d546"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2e13d0e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d038a005"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_7f1ca275");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111621e7"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8622fd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fd8bee64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7425c603");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_111621e7"))
        {
            doAnimationAction(player, "shrug_hands");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8622fd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fd8bee64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4738e99f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4c3d546");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7425c603");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1ff5062"))
        {
            doAnimationAction(player, "nod_head_once");
            if (tatooine_privateer_trainer_1_condition_canFlyNonTrackDuty(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_32c51f93");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35047e82");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d43b2932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8aeba6e5"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f04d5e44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_695b8483");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61b6e28a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87f48e61");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96294214");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5c453e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41e93b9e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_900f01d1"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_slowDown(player, npc);
                string_id message = new string_id(c_stringFile, "s_cd038c67");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1f10b5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5f272b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2b595da6"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_shakeHeadDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_c28cc06d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e5f3688");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2b691aa0"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_dc5b3564");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_681e480d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6f8e9e9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2268b9e0"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_140229b8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d335136f");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4440ce87");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b1f10b5"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_shakeHeadDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_c28cc06d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e5f3688");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5f272b1e"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_shakeHeadDisgust(player, npc);
                string_id message = new string_id(c_stringFile, "s_c28cc06d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e5f3688");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_31");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2e5f3688"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                tatooine_privateer_trainer_1_action_smackSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc4ba900");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b26194cb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cdcb429b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                tatooine_privateer_trainer_1_action_smackSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_fc4ba900");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b26194cb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cdcb429b");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b26194cb"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_c86b6bef");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e81a1436");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cdcb429b"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_58b6db3f");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e81a1436"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                tatooine_privateer_trainer_1_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_d60a3e3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ff172ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80094dc8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ff172ab"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_cff9699e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e03f969");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_80094dc8"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_slowDown(player, npc);
                string_id message = new string_id(c_stringFile, "s_7262f027");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd0dcead");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40a69ad7");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6e03f969"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_aa69d394");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381dd7dd");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cd0dcead"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadOnce(player, npc);
                string_id message = new string_id(c_stringFile, "s_ea9c260b");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40a69ad7"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_slowDown(player, npc);
                string_id message = new string_id(c_stringFile, "s_174c33a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf4d3521");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_aa69d394");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381dd7dd");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cf4d3521"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_laughCackle(player, npc);
                string_id message = new string_id(c_stringFile, "s_d57c89f2");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_381dd7dd"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_snapFingerOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_62f16f7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc9e436d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebe2e111");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9dcdd171");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dc9e436d"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_explain(player, npc);
                string_id message = new string_id(c_stringFile, "s_298df3ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f8c06d0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ebe2e111"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantNoviceSkillBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_807f6dd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb2cfdac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d95c731");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc0b7ade");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9dcdd171"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadOnce(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c2f70");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9f8c06d0"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d5cecb82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84d85e2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2d1e63d0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e84d85e2"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantNoviceSkillBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_807f6dd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb2cfdac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d95c731");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc0b7ade");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2d1e63d0"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_tapHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_88a0099");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca932c28");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_429872ac");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca932c28"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadOnce(player, npc);
                string_id message = new string_id(c_stringFile, "s_541c2f70");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_429872ac"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantNoviceSkillBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_807f6dd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb2cfdac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d95c731");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc0b7ade");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_681e480d"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_bb3a34dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d273b75");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c6f8e9e9"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_lookCasual(player, npc);
                string_id message = new string_id(c_stringFile, "s_afc7e628");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_940e1e78");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9eefb7e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6d273b75"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_aa69d394");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381dd7dd");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_940e1e78"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_snapFingerOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_a02b95fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9eefb7e"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_snapFingerOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_a02b95fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 35);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_aa69d394");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_381dd7dd");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_82"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_explain(player, npc);
                string_id message = new string_id(c_stringFile, "s_298df3ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9f8c06d0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d335136f"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantNoviceSkillBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_807f6dd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb2cfdac");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d95c731");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc0b7ade");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4440ce87"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_dismiss(player, npc);
                string_id message = new string_id(c_stringFile, "s_31fc5801");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb2cfdac"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_95df6b05");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8d95c731"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9ea8105f");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fc0b7ade"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_94e896c1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a0b4565");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d24a2285");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a0b4565"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_b4d6cb30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1adbadc4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d24a2285"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ac0c4a5");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1adbadc4"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_97"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d345d41f");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_676c343f"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ff2a95e8"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9237617f"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_ca38bc3e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d0cc72e0"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_a2123b71");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98d00f66"))
        {
            doAnimationAction(player, "applause_excited");
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60225cc9"))
        {
            doAnimationAction(player, "slow_down");
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_110c7bdd");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_481c3940"))
        {
            if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_70421509");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_71e7a88");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "point_away");
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bedd221");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
                string_id message = new string_id(c_stringFile, "s_49a60ce1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
            {
                doAnimationAction(npc, "beckon");
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_20df70f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "hands_above_head");
                tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
                string_id message = new string_id(c_stringFile, "s_e2bc57c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_ce52ddaa");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
            {
                tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
                string_id message = new string_id(c_stringFile, "s_c30fda16");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa5082f1");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3924fc00");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_999009d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7705d12c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_113");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_1f0c9843");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25669048");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b34a0dd2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_25cf6221");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_66ef1520");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_2a8b0366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                tatooine_privateer_trainer_1_action_pointForward(player, npc);
                string_id message = new string_id(c_stringFile, "s_be1beb37");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_605d90db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
                string_id message = new string_id(c_stringFile, "s_ae220ebc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_beckon(player, npc);
                string_id message = new string_id(c_stringFile, "s_4d19fcb1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc963b1e"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_85355c2f");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8467c710"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f17c03b4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9fdd7cf3");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 65);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
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
    public int tatooine_privateer_trainer_1_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9fdd7cf3"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_7d5682f4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370822d1");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370822d1"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_d3d66c68");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_187");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_188");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_188"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_grantStarshipSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_189");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            if (!tatooine_privateer_trainer_1_condition_hasStarshipSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_191");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_196");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasWeaponSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_193");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_200");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!tatooine_privateer_trainer_1_condition_hasProceduresSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_204");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_208");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_196"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_buyStarshipSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_198");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_200"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_buyWeaponSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_202");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_204"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_buyProcedureSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_206");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_208"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                tatooine_privateer_trainer_1_action_buyDroidSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_210");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d43b2932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8aeba6e5"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f04d5e44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_695b8483");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61b6e28a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87f48e61");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96294214");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5c453e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41e93b9e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d43b2932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8aeba6e5"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f04d5e44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_695b8483");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61b6e28a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87f48e61");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96294214");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5c453e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41e93b9e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_695b8483"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                tatooine_privateer_trainer_1_action_grantDestroyDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_71015a7d");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_61b6e28a"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                tatooine_privateer_trainer_1_action_grantRecoveryDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_c8bcb663");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87f48e61"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                tatooine_privateer_trainer_1_action_grantQuestEight(player, npc);
                string_id message = new string_id(c_stringFile, "s_a2e76d1a");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_96294214"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                tatooine_privateer_trainer_1_action_grantEscortDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_7436b5b6");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int tatooine_privateer_trainer_1_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d43b2932");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8aeba6e5"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f04d5e44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_695b8483");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61b6e28a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87f48e61");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96294214");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e5c453e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41e93b9e");
                utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
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
            detachScript(self, "conversation.tatooine_privateer_trainer_1");
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
        detachScript(self, "conversation.tatooine_privateer_trainer_1");
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
        if (tatooine_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_70421509");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_111621e7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 1);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_71e7a88");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_isRebelPilot(player, npc))
        {
            doAnimationAction(npc, "point_away");
            tatooine_privateer_trainer_1_action_pointForward(player, npc);
            string_id message = new string_id(c_stringFile, "s_6bedd221");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_isImperialPilot(player, npc))
        {
            doAnimationAction(npc, "refuse_offer_affection");
            tatooine_privateer_trainer_1_action_pointToSelf(player, npc);
            string_id message = new string_id(c_stringFile, "s_49a60ce1");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_isPilotButNotSmuggler(player, npc))
        {
            doAnimationAction(npc, "beckon");
            tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
            string_id message = new string_id(c_stringFile, "s_20df70f7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 11);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
        {
            doAnimationAction(npc, "hands_above_head");
            tatooine_privateer_trainer_1_action_eraseClientPath(player, npc);
            string_id message = new string_id(c_stringFile, "s_e2bc57c4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (tatooine_privateer_trainer_1_condition_remembersPlayer(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_900f01d1");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2b595da6");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2b691aa0");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2268b9e0");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 14);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasSpaceShip(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            tatooine_privateer_trainer_1_action_grantSpaceShip(player, npc);
            string_id message = new string_id(c_stringFile, "s_ce52ddaa");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_hasSmugglerQuest(player, npc))
        {
            tatooine_privateer_trainer_1_action_rubChinThoughtful(player, npc);
            string_id message = new string_id(c_stringFile, "s_c30fda16");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_missionOneFailed(player, npc))
        {
            doAnimationAction(npc, "nod_head_multiple");
            tatooine_privateer_trainer_1_action_grantQuestOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_fa5082f1");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_missionTwoFailed(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            tatooine_privateer_trainer_1_action_grantQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_3924fc00");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_missionThreeFailed(player, npc))
        {
            doAnimationAction(npc, "nod_head_once");
            tatooine_privateer_trainer_1_action_grantQuestThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_6d6f9a8d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_missionFourFailed(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            tatooine_privateer_trainer_1_action_grantQuestFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_999009d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            tatooine_privateer_trainer_1_action_rewardForQuestOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_7705d12c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_676c343f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ff2a95e8");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 52);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_113");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9237617f");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 53);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
        {
            tatooine_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_1f0c9843");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d0cc72e0");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 55);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_25669048");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_121");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 56);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
        {
            doAnimationAction(npc, "nod_head_multiple");
            tatooine_privateer_trainer_1_action_rewardForQuestThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_b34a0dd2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98d00f66");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60225cc9");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 58);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_25cf6221");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 59);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            tatooine_privateer_trainer_1_action_rewardForQuestFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_66ef1520");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_481c3940");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 61);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!tatooine_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_2a8b0366");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fc963b1e");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 62);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
        {
            tatooine_privateer_trainer_1_action_pointForward(player, npc);
            string_id message = new string_id(c_stringFile, "s_be1beb37");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8467c710");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 64);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
        {
            tatooine_privateer_trainer_1_action_beckon(player, npc);
            string_id message = new string_id(c_stringFile, "s_605d90db");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_186");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 68);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
        {
            tatooine_privateer_trainer_1_action_nodHeadMultiple(player, npc);
            string_id message = new string_id(c_stringFile, "s_ae220ebc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_190");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 71);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
        {
            tatooine_privateer_trainer_1_action_beckon(player, npc);
            string_id message = new string_id(c_stringFile, "s_4d19fcb1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (tatooine_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6106187c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8aeba6e5");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                }
                utils.setScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId", 80);
                npcStartConversation(player, npc, "tatooine_privateer_trainer_1", message, responses);
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
        if (!conversationId.equals("tatooine_privateer_trainer_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
        if (branchId == 1 && tatooine_privateer_trainer_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && tatooine_privateer_trainer_1_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && tatooine_privateer_trainer_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && tatooine_privateer_trainer_1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && tatooine_privateer_trainer_1_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && tatooine_privateer_trainer_1_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && tatooine_privateer_trainer_1_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && tatooine_privateer_trainer_1_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && tatooine_privateer_trainer_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && tatooine_privateer_trainer_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && tatooine_privateer_trainer_1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && tatooine_privateer_trainer_1_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && tatooine_privateer_trainer_1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && tatooine_privateer_trainer_1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && tatooine_privateer_trainer_1_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && tatooine_privateer_trainer_1_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && tatooine_privateer_trainer_1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && tatooine_privateer_trainer_1_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && tatooine_privateer_trainer_1_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && tatooine_privateer_trainer_1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && tatooine_privateer_trainer_1_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && tatooine_privateer_trainer_1_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && tatooine_privateer_trainer_1_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && tatooine_privateer_trainer_1_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && tatooine_privateer_trainer_1_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && tatooine_privateer_trainer_1_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && tatooine_privateer_trainer_1_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && tatooine_privateer_trainer_1_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && tatooine_privateer_trainer_1_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && tatooine_privateer_trainer_1_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && tatooine_privateer_trainer_1_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && tatooine_privateer_trainer_1_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && tatooine_privateer_trainer_1_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && tatooine_privateer_trainer_1_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && tatooine_privateer_trainer_1_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && tatooine_privateer_trainer_1_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && tatooine_privateer_trainer_1_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && tatooine_privateer_trainer_1_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && tatooine_privateer_trainer_1_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && tatooine_privateer_trainer_1_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && tatooine_privateer_trainer_1_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && tatooine_privateer_trainer_1_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && tatooine_privateer_trainer_1_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && tatooine_privateer_trainer_1_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && tatooine_privateer_trainer_1_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && tatooine_privateer_trainer_1_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && tatooine_privateer_trainer_1_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && tatooine_privateer_trainer_1_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && tatooine_privateer_trainer_1_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && tatooine_privateer_trainer_1_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && tatooine_privateer_trainer_1_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && tatooine_privateer_trainer_1_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && tatooine_privateer_trainer_1_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tatooine_privateer_trainer_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
