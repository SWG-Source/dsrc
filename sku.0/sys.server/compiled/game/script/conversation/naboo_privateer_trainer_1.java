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
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class naboo_privateer_trainer_1 extends script.base_script
{
    public naboo_privateer_trainer_1()
    {
    }
    public static String c_stringFile = "conversation/naboo_privateer_trainer_1";
    public boolean naboo_privateer_trainer_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_privateer_trainer_1_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metRsfTrainer"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean naboo_privateer_trainer_1_condition_alreadyHasAQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player) && naboo_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc));
    }
    public boolean naboo_privateer_trainer_1_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean naboo_privateer_trainer_1_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean naboo_privateer_trainer_1_condition_isPilotButNotRsfMember(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice") && (!space_flags.isSpaceTrack(player, space_flags.PRIVATEER_NABOO)));
    }
    public boolean naboo_privateer_trainer_1_condition_hasCompletedQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_1"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasCompletedQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_2"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasCompletedQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_3"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasCompletedQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "assassinate", "naboo_privateer_tier1_4a"));
    }
    public boolean naboo_privateer_trainer_1_condition_missionOneFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuestRecursive(player, "patrol", "naboo_privateer_1") || space_quest.hasAbortedQuestRecursive(player, "patrol", "naboo_privateer_1"));
    }
    public boolean naboo_privateer_trainer_1_condition_isReadyForFirstTraining(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean naboo_privateer_trainer_1_condition_isReadyForMoreTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasAllTierOneSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierOne(player);
    }
    public boolean naboo_privateer_trainer_1_condition_missionTwoFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "naboo_privateer_2") || space_quest.hasAbortedQuest(player, "destroy", "naboo_privateer_2"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_1_condition_beenRewardedForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_1"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_1")) && (!space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_1")));
    }
    public boolean naboo_privateer_trainer_1_condition_beenRewardedForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_1"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "destroy", "naboo_privateer_1")) && (space_quest.hasReceivedReward(player, "destroy", "naboo_privateer_1")));
    }
    public boolean naboo_privateer_trainer_1_condition_beenRewardedForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_3"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_privateer_3")) && (space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_3")));
    }
    public boolean naboo_privateer_trainer_1_condition_beenRewardedForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuest(player, "assassinate", "naboo_privateer_tier1_4a"))
        {
            return true;
        }
        return ((space_quest.hasCompletedQuest(player, "assassinate", "naboo_privateer_tier1_4a")) && (space_quest.hasReceivedReward(player, "assassinate", "naboo_privateer_tier1_4a")));
    }
    public boolean naboo_privateer_trainer_1_condition_missionThreeFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuestRecursive(player, "patrol", "naboo_privateer_3")) || (space_quest.hasAbortedQuestRecursive(player, "patrol", "naboo_privateer_3"));
    }
    public boolean naboo_privateer_trainer_1_condition_missionFourFailed(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "naboo_privateer_tier1_4a") || space_quest.hasAbortedQuest(player, "assassinate", "naboo_privateer_tier1_4a"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_privateer_trainer_1_condition_hasStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_starships_01"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_droid_01"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasProceduresSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_procedures_01"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasWeaponsSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_weapons_01"));
    }
    public boolean naboo_privateer_trainer_1_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean naboo_privateer_trainer_1_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!space_quest.canGrantNewbieShip(player));
    }
    public boolean naboo_privateer_trainer_1_condition_hasVeryFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCertifiedShip(player))
        {
            return false;
        }
        else 
        {
            return (space_quest.hasQuest(player, "patrol", "naboo_privateer_1") || space_quest.hasQuest(player, "destroy_surpriseattack", "naboo_privateer_1"));
        }
    }
    public boolean naboo_privateer_trainer_1_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((hasSkill(player, "pilot_neutral_starships_01") || hasSkill(player, "pilot_neutral_procedures_01") || hasSkill(player, "pilot_neutral_weapons_01") || hasSkill(player, "pilot_neutral_droid_01")) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public void naboo_privateer_trainer_1_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metRsfTrainer", true);
    }
    public void naboo_privateer_trainer_1_action_grantNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceTrack(player, space_flags.PRIVATEER_NABOO);
        skill.noisyGrantSkill(player, "pilot_neutral_novice");
    }
    public void naboo_privateer_trainer_1_action_signUpForRsf(obj_id player, obj_id npc) throws InterruptedException
    {
        playMusic(player, "sound/music_themequest_acc_general.snd");
    }
    public void naboo_privateer_trainer_1_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_privateer_1");
        space_flags.restoreClientPath(player);
    }
    public void naboo_privateer_trainer_1_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_privateer_2");
    }
    public void naboo_privateer_trainer_1_action_grantMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_privateer_3");
    }
    public void naboo_privateer_trainer_1_action_grantMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "naboo_privateer_tier1_4a");
    }
    public void naboo_privateer_trainer_1_action_rewardForQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_1"))
        {
            space_quest.giveReward(player, "patrol", "naboo_privateer_1", 100);
        }
    }
    public void naboo_privateer_trainer_1_action_rewardForQuestTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "naboo_privateer_2"))
        {
            space_quest.giveReward(player, "destroy", "naboo_privateer_2", 200);
        }
    }
    public void naboo_privateer_trainer_1_action_rewardForQuestThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_privateer_3"))
        {
            if (getSpecies(player) != SPECIES_ITHORIAN)
            {
                space_quest.giveReward(player, "patrol", "naboo_privateer_3", 500, "object/tangible/wearables/bandolier/mercenary_bandolier.iff");
            }
            else 
            {
                space_quest.giveReward(player, "patrol", "naboo_privateer_3", 500, "object/tangible/wearables/bandolier/ith_mercenary_bandolier.iff");
            }
        }
    }
    public void naboo_privateer_trainer_1_action_rewardForQuestFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "naboo_privateer_tier1_4a"))
        {
            space_quest.giveReward(player, "assassinate", "naboo_privateer_tier1_4a", 1000, "object/tangible/ship/components/weapon/wpn_mission_reward_neutral_mandal_light_blaster.iff");
        }
    }
    public void naboo_privateer_trainer_1_action_grantDroidSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_neutral_droid_01");
    }
    public void naboo_privateer_trainer_1_action_grantStarshipSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_neutral_starships_01");
    }
    public void naboo_privateer_trainer_1_action_grantProceduresSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_neutral_procedures_01");
    }
    public void naboo_privateer_trainer_1_action_grantWeaponsSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.noisyGrantSkill(player, "pilot_neutral_weapons_01");
    }
    public void naboo_privateer_trainer_1_action_buyWeaponsSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_weapons_01");
    }
    public void naboo_privateer_trainer_1_action_buyDroidSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_droid_01");
    }
    public void naboo_privateer_trainer_1_action_buyProcedureSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_procedures_01");
    }
    public void naboo_privateer_trainer_1_action_buyStarshipSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_neutral_starships_01");
    }
    public void naboo_privateer_trainer_1_action_grantPatrolDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol_duty", "naboo_privateer_5");
    }
    public void naboo_privateer_trainer_1_action_grantDestroyDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_privateer_6");
    }
    public void naboo_privateer_trainer_1_action_grantEscortDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_privateer_7");
    }
    public void naboo_privateer_trainer_1_action_grantQuestEight(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_privateer_8");
    }
    public void naboo_privateer_trainer_1_action_grantSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "neutral");
    }
    public void naboo_privateer_trainer_1_action_eraseClientPaths(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.clearClientPath(player);
    }
    public int naboo_privateer_trainer_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41c478b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1664d807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a49a3700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41c478b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1664d807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a49a3700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41c478b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1664d807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a49a3700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41c478b2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_85dd7d6c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1664d807");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a49a3700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1ff5062"))
        {
            doAnimationAction(player, "shake_head_no");
            if (naboo_privateer_trainer_1_condition_canFlyNonTrackDuty(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_1e029d90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38d8b2c4");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8cdf3dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e814cf37"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e38f886b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40759c25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_629c7d23");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d36a0e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f4245779");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            doAnimationAction(player, "nod_head_once");
            naboo_privateer_trainer_1_action_eraseClientPaths(player, npc);
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                naboo_privateer_trainer_1_action_grantNoviceSkillBox(player, npc);
                string_id message = new string_id(c_stringFile, "s_c19e20c9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!naboo_privateer_trainer_1_condition_hasSpaceShip(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition_hasSpaceShip(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9817b9bb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c522568");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            doAnimationAction(player, "shake_head_no");
            naboo_privateer_trainer_1_action_eraseClientPaths(player, npc);
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_789f76ee");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9817b9bb"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_e3245432");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b9b27823");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5c522568"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5ed33504");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eed2fe3e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36a4e374");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68660d24");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_36a4e374"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_97a2f8dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68660d24"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f5f16210");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1db37a0f"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b02e116");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48566ef9"))
        {
            doAnimationAction(player, "shrug_hands");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_bbbfb8db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c261cfae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65d63b3c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8fb73c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1db37a0f"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b02e116");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48566ef9"))
        {
            doAnimationAction(player, "shrug_hands");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_bbbfb8db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c261cfae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65d63b3c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8fb73c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b3a9fd05"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_919029b");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aec5c3dd"))
        {
            doAnimationAction(player, "slow_down");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_ears_mocking");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7af03aab");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ea059f38"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b9c77549");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                naboo_privateer_trainer_1_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            doAnimationAction(player, "slow_down");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_ears_mocking");
                naboo_privateer_trainer_1_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_58");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7eb4d42"))
        {
            doAnimationAction(player, "wave_on_dismissing");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                naboo_privateer_trainer_1_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_983440a3");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48b8131c"))
        {
            doAnimationAction(player, "slow_down");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                naboo_privateer_trainer_1_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_80744015");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7736f08b"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                naboo_privateer_trainer_1_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_73f727e7");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1db37a0f"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b02e116");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48566ef9"))
        {
            doAnimationAction(player, "shrug_hands");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_bbbfb8db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c261cfae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65d63b3c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8fb73c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c261cfae"))
        {
            doAnimationAction(player, "snap_finger1");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7b3b28bf");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65d63b3c"))
        {
            doAnimationAction(player, "point_to_self");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_f07f26ad");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1db37a0f"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                naboo_privateer_trainer_1_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b02e116");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48566ef9"))
        {
            doAnimationAction(player, "shrug_hands");
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_bbbfb8db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c261cfae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65d63b3c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8fb73c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fb1c19c1"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_d487641c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b5b6e634");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 45);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
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
    public int naboo_privateer_trainer_1_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b5b6e634"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                naboo_privateer_trainer_1_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_d07cf233");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
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
    public int naboo_privateer_trainer_1_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4682ea6"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_d05d7112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f49e786");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1c8bddbb"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_d05d7112");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3f49e786");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3f49e786"))
        {
            if (naboo_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8853a92c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c1fe68f");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_isRebelPilot(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_72f94b8e");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_isImperialPilot(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_7217d77");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_isPilotButNotRsfMember(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6b25cc31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
            {
                doAnimationAction(npc, "greet");
                string_id message = new string_id(c_stringFile, "s_465e6eeb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasSpaceShip(player, npc))
            {
                naboo_privateer_trainer_1_action_grantSpaceShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_b24af40a");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_alreadyHasAQuest(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_61036267");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c4368e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b3a9fd05");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_missionOneFailed(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_443c036b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aec5c3dd");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ea059f38");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_missionTwoFailed(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                naboo_privateer_trainer_1_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_659e0fa0");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_missionThreeFailed(player, npc))
            {
                doAnimationAction(npc, "cover_eyes");
                string_id message = new string_id(c_stringFile, "s_652689c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_missionFourFailed(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_f742d1e8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7eb4d42");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48b8131c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7736f08b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
            {
                doAnimationAction(npc, "greet");
                naboo_privateer_trainer_1_action_rewardForQuestOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_5df0371b");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_904d71cf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
            {
                doAnimationAction(npc, "greet");
                naboo_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_3d6c25c7");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_bcdb501b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fb1c19c1");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
            {
                doAnimationAction(player, "pound_fist_palm");
                naboo_privateer_trainer_1_action_rewardForQuestThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_cd397d97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c29502");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                naboo_privateer_trainer_1_action_rewardForQuestFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_6ea4aa1e");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_966bc2b6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_5e36283d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_122b0165");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 57);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_63336624");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9237617f"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                naboo_privateer_trainer_1_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6c0bf4f");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_privateer_trainer_1_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_3175b359");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_139"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_140");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_141");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_141"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_grantStarshipSkill(player, npc);
                string_id message = new string_id(c_stringFile, "s_142");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_143"))
        {
            if (!naboo_privateer_trainer_1_condition_hasStarshipSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_144");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_148");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasWeaponsSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_145");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_151");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_privateer_trainer_1_condition_hasProceduresSkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_146");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_155");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_147");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_159");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_buyStarshipSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_149");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_151"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_buyWeaponsSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_153");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_155"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_buyProcedureSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_157");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_159"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_buyDroidSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_161");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8cdf3dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e814cf37"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e38f886b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40759c25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_629c7d23");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d36a0e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f4245779");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8cdf3dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e814cf37"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e38f886b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40759c25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_629c7d23");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d36a0e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f4245779");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_40759c25"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_grantDestroyDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_6575e5d0");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_629c7d23"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_privateer_trainer_1_action_grantEscortDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_90b16aa7");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3d36a0e4"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_792ad840");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_privateer_trainer_1_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6106187c"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8cdf3dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e814cf37"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e38f886b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_40759c25");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_629c7d23");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3d36a0e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db74ddc6"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f4245779");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                    }
                    utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2883b989"))
        {
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
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
            detachScript(self, "conversation.naboo_privateer_trainer_1");
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
        detachScript(self, "conversation.naboo_privateer_trainer_1");
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
        if (naboo_privateer_trainer_1_condition_hasVeryFirstQuest(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8853a92c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60c4f974");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85dd7d6c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 1);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasSpaceExpansion(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7c1fe68f");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_isRebelPilot(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_72f94b8e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_isImperialPilot(player, npc))
        {
            doAnimationAction(npc, "salute1");
            string_id message = new string_id(c_stringFile, "s_7217d77");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_isPilotButNotRsfMember(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6b25cc31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 8);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasNoviceSkillBox(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_465e6eeb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 11);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasSpaceShip(player, npc))
        {
            naboo_privateer_trainer_1_action_grantSpaceShip(player, npc);
            string_id message = new string_id(c_stringFile, "s_b24af40a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_alreadyHasAQuest(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_61036267");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_hasAllTierOneSkills(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_7c4368e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b3a9fd05");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 23);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_missionOneFailed(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_443c036b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aec5c3dd");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ea059f38");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 25);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_missionTwoFailed(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            naboo_privateer_trainer_1_action_grantMissionTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_659e0fa0");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_missionThreeFailed(player, npc))
        {
            doAnimationAction(npc, "cover_eyes");
            string_id message = new string_id(c_stringFile, "s_652689c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 29);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_missionFourFailed(player, npc))
        {
            doAnimationAction(npc, "smack_self");
            string_id message = new string_id(c_stringFile, "s_f742d1e8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7eb4d42");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48b8131c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7736f08b");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 32);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestOne(player, npc))
        {
            doAnimationAction(npc, "greet");
            naboo_privateer_trainer_1_action_rewardForQuestOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_5df0371b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasCompletedQuestOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_904d71cf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1db37a0f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48566ef9");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 37);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestTwo(player, npc))
        {
            doAnimationAction(npc, "greet");
            naboo_privateer_trainer_1_action_rewardForQuestTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_3d6c25c7");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasCompletedQuestTwo(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_bcdb501b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fb1c19c1");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 44);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestThree(player, npc))
        {
            doAnimationAction(player, "pound_fist_palm");
            naboo_privateer_trainer_1_action_rewardForQuestThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_cd397d97");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 47);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasCompletedQuestThree(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_c29502");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 49);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_beenRewardedForQuestFour(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            naboo_privateer_trainer_1_action_rewardForQuestFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_6ea4aa1e");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!naboo_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_966bc2b6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 52);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_isReadyForFirstTraining(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_5e36283d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_139");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 54);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_privateer_trainer_1_condition_isReadyForMoreTraining(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_122b0165");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 57);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", null, pp, responses);
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
        if (naboo_privateer_trainer_1_condition_hasCompletedQuestFour(player, npc))
        {
            doAnimationAction(npc, "pose_proudly");
            string_id message = new string_id(c_stringFile, "s_63336624");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_privateer_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e814cf37");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_db74ddc6");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2883b989");
                }
                utils.setScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId", 66);
                npcStartConversation(player, npc, "naboo_privateer_trainer_1", message, responses);
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
        if (!conversationId.equals("naboo_privateer_trainer_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
        if (branchId == 1 && naboo_privateer_trainer_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && naboo_privateer_trainer_1_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && naboo_privateer_trainer_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_privateer_trainer_1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_privateer_trainer_1_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && naboo_privateer_trainer_1_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_privateer_trainer_1_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && naboo_privateer_trainer_1_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && naboo_privateer_trainer_1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_privateer_trainer_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_privateer_trainer_1_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_privateer_trainer_1_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && naboo_privateer_trainer_1_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_privateer_trainer_1_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && naboo_privateer_trainer_1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && naboo_privateer_trainer_1_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && naboo_privateer_trainer_1_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && naboo_privateer_trainer_1_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && naboo_privateer_trainer_1_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && naboo_privateer_trainer_1_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && naboo_privateer_trainer_1_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && naboo_privateer_trainer_1_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && naboo_privateer_trainer_1_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && naboo_privateer_trainer_1_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && naboo_privateer_trainer_1_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && naboo_privateer_trainer_1_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && naboo_privateer_trainer_1_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && naboo_privateer_trainer_1_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && naboo_privateer_trainer_1_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && naboo_privateer_trainer_1_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && naboo_privateer_trainer_1_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && naboo_privateer_trainer_1_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && naboo_privateer_trainer_1_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && naboo_privateer_trainer_1_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && naboo_privateer_trainer_1_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && naboo_privateer_trainer_1_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && naboo_privateer_trainer_1_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_privateer_trainer_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
