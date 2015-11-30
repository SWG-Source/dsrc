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
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class naboo_imperial_tier4 extends script.base_script
{
    public naboo_imperial_tier4()
    {
    }
    public static String c_stringFile = "conversation/naboo_imperial_tier4";
    public boolean naboo_imperial_tier4_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_imperial_tier4_condition_isWrongFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean naboo_imperial_tier4_condition_hasCompletedTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierThree(player);
    }
    public boolean naboo_imperial_tier4_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean naboo_imperial_tier4_condition_faliedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "patrol", "naboo_imperial_tier4_1") || space_quest.hasFailedQuestRecursive(player, "patrol", "naboo_imperial_tier4_1"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_failedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "recovery", "naboo_imperial_tier4_2") || space_quest.hasFailedQuestRecursive(player, "recovery", "naboo_imperial_tier4_2"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_failedMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "survival", "naboo_imperial_tier4_3") || space_quest.hasFailedQuestRecursive(player, "survival", "naboo_imperial_tier4_3"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_failedMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "escort", "naboo_imperial_tier4_4") || space_quest.hasFailedQuestRecursive(player, "escort", "naboo_imperial_tier4_4"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_failedMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "destroy", "naboo_imperial_master") || space_quest.hasFailedQuestRecursive(player, "destroy", "naboo_imperial_master"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_isReadyForTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (naboo_imperial_tier4_condition_hasCompletedTier4(player, npc))
        {
            return false;
        }
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_04"));
    }
    public boolean naboo_imperial_tier4_condition_hasCompletedTier4(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierFour(player);
    }
    public boolean naboo_imperial_tier4_condition_hasMasterSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_master"));
    }
    public boolean naboo_imperial_tier4_condition_isGettingMissionOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_imperial_tier4_1"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_imperial_tier4_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_isGettingMissionTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "recovery", "naboo_imperial_tier4_2"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "recovery", "naboo_imperial_tier4_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_isGettingMissionThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "survival", "naboo_imperial_tier4_3"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "survival", "naboo_imperial_tier4_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_isGettingMissionFourReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "escort", "naboo_imperial_tier4_4"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "escort", "naboo_imperial_tier4_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_isGettingMasterMissionReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "destroy", "naboo_imperial_master"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "destroy", "naboo_imperial_master"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_hasCompletedMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "survival", "naboo_imperial_tier4_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_hasCompletedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "recovery", "naboo_imperial_tier4_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_hasCompletedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "patrol", "naboo_imperial_tier4_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_tier4_condition_hasDroidSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_imperial_navy_droid_04");
    }
    public boolean naboo_imperial_tier4_condition_hasWeaponSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_imperial_navy_weapons_04");
    }
    public boolean naboo_imperial_tier4_condition_hasProcedureSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_imperial_navy_procedures_04");
    }
    public boolean naboo_imperial_tier4_condition_hasStarshipSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_imperial_navy_starships_04");
    }
    public boolean naboo_imperial_tier4_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.IMPERIAL_NABOO);
    }
    public boolean naboo_imperial_tier4_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierThree(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_tier4_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean naboo_imperial_tier4_condition_hasMetMe(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "metTier4");
    }
    public boolean naboo_imperial_tier4_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 0.0f);
    }
    public boolean naboo_imperial_tier4_condition_isReadyForMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "readyForTier4Mission");
    }
    public void naboo_imperial_tier4_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_imperial_tier4_1");
        if (space_flags.hasSpaceFlag(player, "readyForTier4Mission"))
        {
            space_flags.removeSpaceFlag(player, "readyForTier4Mission");
        }
    }
    public void naboo_imperial_tier4_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "naboo_imperial_tier4_2");
        if (space_flags.hasSpaceFlag(player, "readyForTier4Mission"))
        {
            space_flags.removeSpaceFlag(player, "readyForTier4Mission");
        }
    }
    public void naboo_imperial_tier4_action_grantMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "survival", "naboo_imperial_tier4_3");
        if (space_flags.hasSpaceFlag(player, "readyForTier4Mission"))
        {
            space_flags.removeSpaceFlag(player, "readyForTier4Mission");
        }
    }
    public void naboo_imperial_tier4_action_grantMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "naboo_imperial_tier4_4");
        if (space_flags.hasSpaceFlag(player, "readyForTier4Mission"))
        {
            space_flags.removeSpaceFlag(player, "readyForTier4Mission");
        }
    }
    public void naboo_imperial_tier4_action_grantMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "naboo_imperial_master");
    }
    public void naboo_imperial_tier4_action_grantDutyMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_imperial_tier4_1");
    }
    public void naboo_imperial_tier4_action_grantDutyMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_imperial_tier4_2");
    }
    public void naboo_imperial_tier4_action_grantDutyMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "naboo_imperial_tier4_3");
    }
    public void naboo_imperial_tier4_action_grantDutyMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue_duty", "naboo_imperial_tier4_4");
    }
    public void naboo_imperial_tier4_action_rewardForMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "patrol", "naboo_imperial_tier4_1"))
        {
            space_quest.giveReward(player, "patrol", "naboo_imperial_tier4_1", 10000, "object/tangible/ship/components/weapon/wpn_mission_reward_imperial_sds_boltdriver.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 150.0f);
        }
    }
    public void naboo_imperial_tier4_action_rewardForMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "naboo_imperial_tier4_2"))
        {
            space_quest.giveReward(player, "recovery", "naboo_imperial_tier4_2", 10000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_cygnus_holoscreen.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 150.0f);
        }
    }
    public void naboo_imperial_tier4_action_rewardForMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "survival", "naboo_imperial_tier4_3"))
        {
            space_quest.giveReward(player, "survival", "naboo_imperial_tier4_3", 10000, "object/tangible/ship/components/armor/arm_mission_reward_imperial_rss_special_durasteel.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 150.0f);
        }
    }
    public void naboo_imperial_tier4_action_rewardForMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "escort", "naboo_imperial_tier4_4"))
        {
            space_quest.giveReward(player, "escort", "naboo_imperial_tier4_4", 10000, "object/tangible/ship/components/reactor/rct_mission_reward_imperial_rss_advanced_military.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 150.0f);
        }
    }
    public void naboo_imperial_tier4_action_rewardForMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "naboo_imperial_master"))
        {
            space_quest.giveReward(player, "destroy", "naboo_imperial_master", 5000);
            grantSkill(player, "pilot_imperial_navy_master");
        }
    }
    public void naboo_imperial_tier4_action_grantProcedureSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_procedures_04");
    }
    public void naboo_imperial_tier4_action_grantStarshipSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_starships_04");
    }
    public void naboo_imperial_tier4_action_grantDroidSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_droid_04");
    }
    public void naboo_imperial_tier4_action_grantWeaponSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_weapons_04");
    }
    public void naboo_imperial_tier4_action_setMetMe(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "metTier4", true);
    }
    public void naboo_imperial_tier4_action_setReadyForMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier4Mission", true);
    }
    public int naboo_imperial_tier4_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53de9d8f"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_9e131808");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8feb33c1");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8feb33c1"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_3686a31b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4854758d"))
        {
            if (naboo_imperial_tier4_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1832fe3c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c82e9a2f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90ce1297");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1c3578bb");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c82e9a2f"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85c9e37e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7e0eff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90ce1297"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1769a080");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67183c1c"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_a6f30b2b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ec457ebc");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26bb0194"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_487cad");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ec457ebc"))
        {
            doAnimationAction(player, "nervous");
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_f7e33371");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            doAnimationAction(player, "nervous");
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_b1a3b711");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97ce1c41"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a72c3c89");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f115f554");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e69b471b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                naboo_imperial_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_659c15a1");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f115f554"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_5e0158c");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85b1adc5"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_da128253");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9c6ec037");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_873e726e"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_424a2157");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9ec59458");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c6ec037"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_57274357");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9ec59458"))
        {
            doAnimationAction(player, "embarrassed");
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                naboo_imperial_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_f435fbd2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7017e938"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_5f1cd2f8");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39ec8285"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_8bfb7a2f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbb3a834");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dbb3a834"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecd3f704");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c17d2691");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c17d2691"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                naboo_imperial_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_7e2d0f69");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_834bed59"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9d1c334d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1cd21b47");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0918370");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5c9b37f9"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d5225828");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54ed82f4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41040de1");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e479ad44"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9bc48781");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ef1e040");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1cd21b47"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_722463c0");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d0918370"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b12a7f6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_416d1887");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416d1887"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_af5329b0");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_54ed82f4"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_7bc04afa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_719a036e");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41040de1"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_725be20b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52c3d844");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_719a036e"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36af2d20");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52c3d844"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_dc31882");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ef1e040"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_da53e28f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c0f7c528");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c0f7c528"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c92f77a");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64ce1b1d"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90f30b97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a38a5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a459860a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_40671b7a"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_30d23b2c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bdebb4cc");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f5d2aa8d"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1cfa3230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_aa695923");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8a38a5ed"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c25d28ef");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a459860a"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4c65eeef");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bdebb4cc"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90f30b97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a38a5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a459860a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aa695923"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_90f30b97");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a38a5ed");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a459860a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_28b58d98"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bfcf1757");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_32bd6884"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185e40da");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e9065e37");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_593c68fd"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6145c468");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6d60481e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dd8800a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3dd8800a"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ac4bb3e6");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e9065e37"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34aaab36");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77e48d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6d60481e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dd8800a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6d60481e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3dd8800a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57a5120c"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe2a11f4");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2bfb5526"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_8e075bc2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_44867e8a"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_rewardForMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_f82c2226");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
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
    public int naboo_imperial_tier4_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52917b0d"))
        {
            if (naboo_imperial_tier4_condition_hasCompletedMissionThree(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_673b4c9b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionTwo(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_ad56bc53");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionOne(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0feed48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0abb9a4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_338");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_344"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_352"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_356");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_362"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_378"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_380");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_398"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_400");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_408"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_410");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_412");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_432"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_436");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_462"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_464");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_466");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_480"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_482");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_484");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_504"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_506");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_508");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 112);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_544"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_546");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_548");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_572");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_319"))
        {
            if (!naboo_imperial_tier4_condition_hasStarshipSkill04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_320");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_324");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_imperial_tier4_condition_hasWeaponSkill04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_321");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_328");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 131);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_imperial_tier4_condition_hasProcedureSkill04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_322");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_330");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 132);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_323");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_332");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 133);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_324"))
        {
            naboo_imperial_tier4_action_grantStarshipSkill(player, npc);
            if (naboo_imperial_tier4_condition_hasCompletedMissionThree(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionTwo(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionOne(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_478");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_336"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_338");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_340");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_344"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_346");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_352"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_354");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_356");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_340"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_342");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_348"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_350");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_356"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_358");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
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
    public int naboo_imperial_tier4_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_362"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_378"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_380");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_398"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_400");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_402");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_368");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_370"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_372");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_374");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_374"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_376");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_382"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_384");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_386");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_386"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_388");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_390");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_390"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_392");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_394");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_394"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_396");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_402"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_404");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_368");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_370");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_408"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_410");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_412");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_432"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_434");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_436");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 94);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_462"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_464");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_466");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_412"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_414");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_428"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_430");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_416");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_418");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_420");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_420"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_422");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_424");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_424"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_426");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_416"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_418");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_420");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_436"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_438");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_440");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_460");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_440"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_442");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_444");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_460"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_446");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_448");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_444"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_446");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_448");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_448"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_450");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_452");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_452"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_454");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_456");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 99);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_456"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_458");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_466"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_468");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_470");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 102);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch102(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_470"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_472");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch103(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_474"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_480"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_482");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_484");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_504"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_506");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_508");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 112);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_544"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_546");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_548");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_572");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_484"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_486");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_488"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_490");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_492"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_494");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_496");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_496"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_498");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_500");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 110);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch110(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_500"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_502");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch112(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_508"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_510");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_512");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_520");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_528"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_530");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_532");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 118);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_512"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_514");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_516");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 114);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_520"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_522");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_524");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 116);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch114(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_516"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_518");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_524"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_526");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch118(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_532"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_cackle");
                string_id message = new string_id(c_stringFile, "s_534");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_536");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 119);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch119(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_536"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_538");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_540");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 120);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch120(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_540"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_542");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch122(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_548"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_550");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_552");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 123);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_572"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_574");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_576");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 129);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch123(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_552"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_554");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_556");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_568");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 124);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch124(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_556"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_558");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_560");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_564");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 125);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_568"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_570");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_560"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_562");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_564"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_566");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch129(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_576"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_578");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
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
    public int naboo_imperial_tier4_handleBranch131(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_328"))
        {
            naboo_imperial_tier4_action_grantWeaponSkill(player, npc);
            if (naboo_imperial_tier4_condition_hasCompletedMissionThree(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionTwo(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionOne(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_478");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch132(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_330"))
        {
            naboo_imperial_tier4_action_grantProcedureSkill(player, npc);
            if (naboo_imperial_tier4_condition_hasCompletedMissionThree(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionTwo(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionOne(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_478");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch133(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_332"))
        {
            naboo_imperial_tier4_action_grantDroidSkill(player, npc);
            if (naboo_imperial_tier4_condition_hasCompletedMissionThree(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_334");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_336");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_344");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionTwo(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_398");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition_hasCompletedMissionOne(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_408");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_462");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_setReadyForMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_478");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_480");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_544");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_277"))
        {
            naboo_imperial_tier4_action_setMetMe(player, npc);
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85c9e37e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7e0eff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch135(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_851406fb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1a40d259");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82095b20"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_84350489");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60a8bfcb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_647c9fa9");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_be7e95c"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_5ca877f1");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49805f81"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85c9e37e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7e0eff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch140(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8d7e0eff"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_327da557");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61657d0f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 141);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch141(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61657d0f"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f76cec26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_362a48e0");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 142);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch142(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_362a48e0"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ee9d18a3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_851406fb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82095b20");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_60a8bfcb");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_be7e95c");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49805f81");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch143(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_851406fb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1a40d259");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82095b20"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_84350489");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60a8bfcb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_647c9fa9");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_be7e95c"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_5ca877f1");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49805f81"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85c9e37e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7e0eff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch144(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7177c3f2"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_55905a28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a4b30c88");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 145);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fae9dc1b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cd04b926");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58edcac7");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a3aef707"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c33416a7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3b9d259a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 152);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch145(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a4b30c88"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3e261fd0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6844241f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 146);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch146(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6844241f"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a9a7c838");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_270e6f7b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch147(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_270e6f7b"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a0d28eb");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch149(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_58edcac7"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_365243f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83803b19");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 150);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch150(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83803b19"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ee94373b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch152(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3b9d259a"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d8b3a5bc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3b2c48b2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5dff5608");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3b2c48b2"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8bae75a0");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5dff5608"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c28f20f3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_tier4_handleBranch156(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_851406fb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_1a40d259");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_82095b20"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_84350489");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60a8bfcb"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_647c9fa9");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_be7e95c"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                naboo_imperial_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_5ca877f1");
                utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49805f81"))
        {
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85c9e37e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d7e0eff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
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
            detachScript(self, "conversation.naboo_imperial_tier4");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
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
        detachScript(self, "conversation.naboo_imperial_tier4");
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
        if (naboo_imperial_tier4_condition_isWrongFaction(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8a7c509a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_tier4_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4b509b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "cover_mouth");
            string_id message = new string_id(c_stringFile, "s_e3ea301");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_53de9d8f");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "naboo_imperial_tier4", null, pp, responses);
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
        if (!naboo_imperial_tier4_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3a3805e3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4854758d");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 6);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_tier4_condition_hasCompletedTier3(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_a11a905b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isOnMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_fcbb92d0");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_faliedMissionOne(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a6380860");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67183c1c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26bb0194");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 12);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_failedMissionTwo(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            string_id message = new string_id(c_stringFile, "s_cfb7c310");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_97ce1c41");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e69b471b");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 17);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "naboo_imperial_tier4", null, pp, responses);
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
        if (naboo_imperial_tier4_condition_failedMissionThree(player, npc))
        {
            doAnimationAction(npc, "hair_flip");
            string_id message = new string_id(c_stringFile, "s_1bfd2d3e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85b1adc5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_873e726e");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 21);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_failedMissionFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_91d10a84");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7017e938");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_39ec8285");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 26);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isGettingMissionOneReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_417ab2a4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_834bed59");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c9b37f9");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e479ad44");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 31);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isGettingMissionTwoReward(player, npc))
        {
            naboo_imperial_tier4_action_rewardForMissionTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_c4f74be1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_64ce1b1d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_40671b7a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f5d2aa8d");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 44);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isGettingMissionThreeReward(player, npc))
        {
            naboo_imperial_tier4_action_rewardForMissionThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_bcd45d4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_28b58d98");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_32bd6884");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_593c68fd");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 50);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isGettingMissionFourReward(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5a0327df");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_57a5120c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2bfb5526");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_44867e8a");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 57);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isReadyForMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ace49d41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 61);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition_isReadyForTraining(player, npc))
        {
            doAnimationAction(npc, "tap_foot");
            string_id message = new string_id(c_stringFile, "s_5dce257f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_319");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 66);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_tier4_condition_hasMetMe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_59da8c80");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_277");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 134);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_tier4_condition_hasCompletedTier4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d70698c1");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_851406fb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82095b20");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60a8bfcb");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_be7e95c");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49805f81");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 135);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_tier4_condition_hasMasterSkill(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6fb4f06a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7177c3f2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fae9dc1b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a3aef707");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 144);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_145d7cc3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (naboo_imperial_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_851406fb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_82095b20");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60a8bfcb");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_be7e95c");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_49805f81");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_tier4.branchId", 135);
                npcStartConversation(player, npc, "naboo_imperial_tier4", message, responses);
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
        if (!conversationId.equals("naboo_imperial_tier4"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
        if (branchId == 3 && naboo_imperial_tier4_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_imperial_tier4_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_imperial_tier4_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_imperial_tier4_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && naboo_imperial_tier4_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && naboo_imperial_tier4_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_imperial_tier4_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && naboo_imperial_tier4_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && naboo_imperial_tier4_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_imperial_tier4_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_imperial_tier4_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && naboo_imperial_tier4_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && naboo_imperial_tier4_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && naboo_imperial_tier4_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && naboo_imperial_tier4_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && naboo_imperial_tier4_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && naboo_imperial_tier4_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && naboo_imperial_tier4_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && naboo_imperial_tier4_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && naboo_imperial_tier4_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && naboo_imperial_tier4_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && naboo_imperial_tier4_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && naboo_imperial_tier4_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && naboo_imperial_tier4_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && naboo_imperial_tier4_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && naboo_imperial_tier4_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && naboo_imperial_tier4_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && naboo_imperial_tier4_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && naboo_imperial_tier4_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && naboo_imperial_tier4_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && naboo_imperial_tier4_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && naboo_imperial_tier4_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && naboo_imperial_tier4_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && naboo_imperial_tier4_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && naboo_imperial_tier4_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && naboo_imperial_tier4_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && naboo_imperial_tier4_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && naboo_imperial_tier4_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && naboo_imperial_tier4_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && naboo_imperial_tier4_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && naboo_imperial_tier4_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && naboo_imperial_tier4_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && naboo_imperial_tier4_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && naboo_imperial_tier4_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && naboo_imperial_tier4_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && naboo_imperial_tier4_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && naboo_imperial_tier4_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && naboo_imperial_tier4_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && naboo_imperial_tier4_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && naboo_imperial_tier4_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && naboo_imperial_tier4_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && naboo_imperial_tier4_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && naboo_imperial_tier4_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && naboo_imperial_tier4_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && naboo_imperial_tier4_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && naboo_imperial_tier4_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && naboo_imperial_tier4_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && naboo_imperial_tier4_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && naboo_imperial_tier4_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && naboo_imperial_tier4_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && naboo_imperial_tier4_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && naboo_imperial_tier4_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && naboo_imperial_tier4_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && naboo_imperial_tier4_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && naboo_imperial_tier4_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && naboo_imperial_tier4_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && naboo_imperial_tier4_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && naboo_imperial_tier4_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 102 && naboo_imperial_tier4_handleBranch102(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 103 && naboo_imperial_tier4_handleBranch103(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && naboo_imperial_tier4_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && naboo_imperial_tier4_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && naboo_imperial_tier4_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && naboo_imperial_tier4_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 110 && naboo_imperial_tier4_handleBranch110(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 112 && naboo_imperial_tier4_handleBranch112(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && naboo_imperial_tier4_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 114 && naboo_imperial_tier4_handleBranch114(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && naboo_imperial_tier4_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 118 && naboo_imperial_tier4_handleBranch118(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 119 && naboo_imperial_tier4_handleBranch119(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 120 && naboo_imperial_tier4_handleBranch120(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 122 && naboo_imperial_tier4_handleBranch122(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 123 && naboo_imperial_tier4_handleBranch123(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 124 && naboo_imperial_tier4_handleBranch124(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && naboo_imperial_tier4_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 129 && naboo_imperial_tier4_handleBranch129(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 131 && naboo_imperial_tier4_handleBranch131(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 132 && naboo_imperial_tier4_handleBranch132(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 133 && naboo_imperial_tier4_handleBranch133(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && naboo_imperial_tier4_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 135 && naboo_imperial_tier4_handleBranch135(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 140 && naboo_imperial_tier4_handleBranch140(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 141 && naboo_imperial_tier4_handleBranch141(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 142 && naboo_imperial_tier4_handleBranch142(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 143 && naboo_imperial_tier4_handleBranch143(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 144 && naboo_imperial_tier4_handleBranch144(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 145 && naboo_imperial_tier4_handleBranch145(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 146 && naboo_imperial_tier4_handleBranch146(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 147 && naboo_imperial_tier4_handleBranch147(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 149 && naboo_imperial_tier4_handleBranch149(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 150 && naboo_imperial_tier4_handleBranch150(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 152 && naboo_imperial_tier4_handleBranch152(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && naboo_imperial_tier4_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 156 && naboo_imperial_tier4_handleBranch156(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_imperial_tier4.branchId");
        return SCRIPT_CONTINUE;
    }
}
