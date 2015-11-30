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

public class corellia_rebel_tier4 extends script.base_script
{
    public corellia_rebel_tier4()
    {
    }
    public static String c_stringFile = "conversation/corellia_rebel_tier4";
    public boolean corellia_rebel_tier4_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_rebel_tier4_condition_isWrongFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (!hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean corellia_rebel_tier4_condition_hasCompletedTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierThree(player) && space_flags.hasSpaceFlag(player, "viopaPilot"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player));
    }
    public boolean corellia_rebel_tier4_condition_failedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "survival", "corellia_rebel_tier4_1") || space_quest.hasFailedQuest(player, "survival", "corellia_rebel_tier4_1")) && !space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_1_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "space_battle", "corellia_rebel_tier4_1_b") || space_quest.hasFailedQuest(player, "space_battle", "corellia_rebel_tier4_1_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "space_battle", "corellia_rebel_tier4_1_a") || space_quest.hasFailedQuest(player, "space_battle", "corellia_rebel_tier4_1_a"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_failedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "assassinate", "corellia_rebel_tier4_2") || space_quest.hasFailedQuest(player, "assassinate", "corellia_rebel_tier4_2")) && !space_quest.hasWonQuest(player, "rescue", "corellia_rebel_tier4_2_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "rescue", "corellia_rebel_tier4_2_b") || space_quest.hasFailedQuest(player, "rescue", "corellia_rebel_tier4_2_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "delivery_no_pickup", "corellia_rebel_tier4_2_a") || space_quest.hasFailedQuest(player, "delivery_no_pickup", "corellia_rebel_tier4_2_a"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_failedMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "space_battle", "corellia_rebel_tier4_3") || space_quest.hasFailedQuest(player, "space_battle", "corellia_rebel_tier4_3")) && !space_quest.hasWonQuest(player, "survival", "corellia_rebel_tier4_3_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "space_battle", "corellia_rebel_tier4_3_a") || space_quest.hasFailedQuest(player, "space_battle", "corellia_rebel_tier4_3_a"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "survival", "corellia_rebel_tier4_3_b") || space_quest.hasFailedQuest(player, "survival", "corellia_rebel_tier4_3_b"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_failedMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((space_quest.hasAbortedQuest(player, "recovery", "corellia_rebel_tier4_4") || space_quest.hasFailedQuest(player, "recovery", "corellia_rebel_tier4_4")) && !space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_4_c"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "assassinate", "corellia_rebel_tier4_4_a") || space_quest.hasFailedQuest(player, "assassinate", "corellia_rebel_tier4_4_a"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "rescue", "corellia_rebel_tier4_4_b") || space_quest.hasFailedQuest(player, "rescue", "corellia_rebel_tier4_4_b"))
        {
            return true;
        }
        if (space_quest.hasAbortedQuest(player, "space_battle", "corellia_rebel_tier4_4_c") || space_quest.hasFailedQuest(player, "space_battle", "corellia_rebel_tier4_4_c"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_failedMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasAbortedQuestRecursive(player, "destroy", "corellia_rebel_master") || space_quest.hasFailedQuestRecursive(player, "destroy", "corellia_rebel_master"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_isReadyForTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (corellia_rebel_tier4_condition_hasCompletedTier4(player, npc))
        {
            return false;
        }
        return (space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_starships_04"));
    }
    public boolean corellia_rebel_tier4_condition_hasCompletedTier4(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierFour(player);
    }
    public boolean corellia_rebel_tier4_condition_hasMasterSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_master"));
    }
    public boolean corellia_rebel_tier4_condition_isGettingMissionOneReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "survival", "corellia_rebel_tier4_1"))
        {
            return false;
        }
        if (space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_1_a") || space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_1_b"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_isGettingMissionTwoReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "corellia_rebel_tier4_2"))
        {
            return false;
        }
        if (space_quest.hasWonQuest(player, "delivery_no_pickup", "corellia_rebel_tier4_2_a") || space_quest.hasWonQuest(player, "rescue", "corellia_rebel_tier4_2_b"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_isGettingMissionThreeReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "space_battle", "corellia_rebel_tier4_3"))
        {
            return false;
        }
        if (space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_3_a") || space_quest.hasWonQuest(player, "survival", "corellia_rebel_tier4_3_b"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_isGettingMissionFourReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "recovery", "corellia_rebel_tier4_4"))
        {
            return false;
        }
        if (space_quest.hasWonQuest(player, "space_battle", "corellia_rebel_tier4_4_c"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_isGettingMasterMissionReward(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCompletedQuestRecursive(player, "destroy", "corellia_rebel_master"))
        {
            return false;
        }
        if (!space_quest.hasReceivedReward(player, "destroy", "corellia_rebel_master"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_readyForMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((hasSkill(player, "pilot_rebel_navy_starships_04") || hasSkill(player, "pilot_rebel_navy_weapons_04") || hasSkill(player, "pilot_rebel_navy_procedures_04") || hasSkill(player, "pilot_rebel_navy_droid_04")) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasReceivedReward(player, "survival", "corellia_rebel_tier4_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_readyForMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_04",
            "pilot_rebel_navy_weapons_04",
            "pilot_rebel_navy_procedures_04",
            "pilot_rebel_navy_droid_04"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 2) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasReceivedReward(player, "assassinate", "corellia_rebel_tier4_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_readyForMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_04",
            "pilot_rebel_navy_weapons_04",
            "pilot_rebel_navy_procedures_04",
            "pilot_rebel_navy_droid_04"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 3) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasReceivedReward(player, "space_battle", "corellia_rebel_tier4_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_readyForMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_04",
            "pilot_rebel_navy_weapons_04",
            "pilot_rebel_navy_procedures_04",
            "pilot_rebel_navy_droid_04"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 4) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasReceivedReward(player, "recovery", "corellia_rebel_tier4_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_rebel_tier4_condition_hasDroid04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_droid_04");
    }
    public boolean corellia_rebel_tier4_condition_hasWeapon04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_weapons_04");
    }
    public boolean corellia_rebel_tier4_condition_hasStarship04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_starships_04");
    }
    public boolean corellia_rebel_tier4_condition_hasProcedures04(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_rebel_navy_procedures_04");
    }
    public boolean corellia_rebel_tier4_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierThree(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_rebel_tier4_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.REBEL_CORELLIA);
    }
    public boolean corellia_rebel_tier4_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean corellia_rebel_tier4_condition_hasMetMe(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "metTier4");
    }
    public boolean corellia_rebel_tier4_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_REBEL) < 0.0f);
    }
    public void corellia_rebel_tier4_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "space_battle", "corellia_rebel_tier4_1_b");
        space_quest.clearQuestFlags(player, "space_battle", "corellia_rebel_tier4_1_a");
        space_quest.grantQuest(player, "survival", "corellia_rebel_tier4_1");
    }
    public void corellia_rebel_tier4_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "rescue", "corellia_rebel_tier4_2_b");
        space_quest.clearQuestFlags(player, "delivery_no_pickup", "corellia_rebel_tier4_2_a");
        space_quest.grantQuest(player, "assassinate", "corellia_rebel_tier4_2");
    }
    public void corellia_rebel_tier4_action_grantMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "survival", "corellia_rebel_tier4_3_b");
        space_quest.clearQuestFlags(player, "space_battle", "corellia_rebel_tier4_3_a");
        space_quest.grantQuest(player, "space_battle", "corellia_rebel_tier4_3");
    }
    public void corellia_rebel_tier4_action_grantMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.clearQuestFlags(player, "rescue", "corellia_rebel_tier4_4_b");
        space_quest.clearQuestFlags(player, "assassinate", "corellia_rebel_tier4_4_a");
        space_quest.clearQuestFlags(player, "space_battle", "corellia_rebel_tier4_4_c");
        space_quest.grantQuest(player, "recovery", "corellia_rebel_tier4_4");
    }
    public void corellia_rebel_tier4_action_grantMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy", "corellia_rebel_master");
    }
    public void corellia_rebel_tier4_action_grantDutyMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "corellia_rebel_tier4_1");
    }
    public void corellia_rebel_tier4_action_grantDutyMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue_duty", "corellia_rebel_tier4_1");
    }
    public void corellia_rebel_tier4_action_grantDutyMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "corellia_rebel_tier4_1");
    }
    public void corellia_rebel_tier4_action_grantDutyMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "corellia_rebel_tier4_1");
    }
    public void corellia_rebel_tier4_action_rewardForMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "survival", "corellia_rebel_tier4_1"))
        {
            space_quest.giveReward(player, "survival", "corellia_rebel_tier4_1", 10000, "object/tangible/ship/components/shield_generator/shd_mission_reward_rebel_taim_military_grade.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 150.0f);
        }
    }
    public void corellia_rebel_tier4_action_rewardForMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "corellia_rebel_tier4_2"))
        {
            space_quest.giveReward(player, "assassinate", "corellia_rebel_tier4_2", 10000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_rebel_novaldex_low_latency.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 150.0f);
        }
    }
    public void corellia_rebel_tier4_action_rewardForMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "space_battle", "corellia_rebel_tier4_3"))
        {
            space_quest.giveReward(player, "space_battle", "corellia_rebel_tier4_3", 10000, "object/tangible/ship/components/booster/bst_mission_reward_rebel_qualdex_halcyon.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 150.0f);
        }
    }
    public void corellia_rebel_tier4_action_rewardForMissionFour(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "corellia_rebel_tier4_4"))
        {
            space_quest.giveReward(player, "recovery", "corellia_rebel_tier4_4", 10000, "object/tangible/ship/components/weapon/wpn_mission_reward_rebel_incom_tricannon.iff");
            factions.addFactionStanding(player, factions.FACTION_REBEL, 150.0f);
        }
    }
    public void corellia_rebel_tier4_action_rewardForMasterMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy", "corellia_rebel_master"))
        {
            space_quest.giveReward(player, "destroy", "corellia_rebel_master", 5000);
            grantSkill(player, "pilot_rebel_navy_master");
        }
    }
    public void corellia_rebel_tier4_action_buyWeaponsSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_rebel_navy_weapons_04");
    }
    public void corellia_rebel_tier4_action_buyStarshipsSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_rebel_navy_starships_04");
    }
    public void corellia_rebel_tier4_action_buyDroidSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_rebel_navy_droid_04");
    }
    public void corellia_rebel_tier4_action_buyProceduresSkill04(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_rebel_navy_procedures_04");
    }
    public void corellia_rebel_tier4_action_setMetMe(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "metTier4", true);
    }
    public int corellia_rebel_tier4_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7e579e1e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_649d2a19");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fb4c1f1f"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_raise_fist");
                string_id message = new string_id(c_stringFile, "s_2da082b6");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6222b64d"))
        {
            if (corellia_rebel_tier4_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0743f82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c82e9a2f");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c73f5fdb");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c82e9a2f"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e0dc5fad"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3bcc34e7");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e86f1062"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                string_id message = new string_id(c_stringFile, "s_5e0e14bc");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4ccb8d59"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_raise_fist");
                string_id message = new string_id(c_stringFile, "s_549b97e6");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c1013aa7"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_86df2510");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7090182"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_436c2ab2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77e48d5b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2744b825");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad621c08");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ad621c08"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_ee83a0c2");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95c399f6"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6fdab943");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6f679fea"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_b4e09e44");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_def742"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "flex_biceps");
                string_id message = new string_id(c_stringFile, "s_c4dc6b9d");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b20c71fe"))
        {
            doAnimationAction(player, "shake_head_no");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger1");
                string_id message = new string_id(c_stringFile, "s_868699d3");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_aed5139a"))
        {
            doAnimationAction(player, "embarrassed");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_f87c573");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c72c337a");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cb6eb294"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e3653d50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc27931b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c72c337a"))
        {
            doAnimationAction(player, "embarrassed");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_9a4a59e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f60b81f8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f60b81f8"))
        {
            doAnimationAction(player, "nod_head_once");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_3172f07e");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3bfd507c");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61485366"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_85fbc617");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_197c8928"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                string_id message = new string_id(c_stringFile, "s_4c97efb1");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8f3b3edb"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                corellia_rebel_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_76c0ae11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88563b84"))
        {
            doAnimationAction(player, "embarrassed");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                corellia_rebel_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa6bfaff");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87d7e908"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                corellia_rebel_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_16e3e795");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e42b260e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                corellia_rebel_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_13835aab");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e30b9697"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                corellia_rebel_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_f5d1e7f4");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_10eafe13"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_42b12fa4");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4dd9a7b9"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "stamp_feet");
                corellia_rebel_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_2eec3c3b");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35dd4aa2"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                corellia_rebel_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_6561f20e");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3609c810"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_99069e90");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f27f6d98");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3c546115"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9be0e2f1");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ef3e641a"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e380bbf8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96bfa401");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f27f6d98"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_449db6d4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67e6df55"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d98a6441");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d566a3c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f35bbdbd");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d566a3c"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ffeba47a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_997f2d21");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f35bbdbd"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2284c46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2a5d0bf1");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_997f2d21"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_f5e4dcb6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f5cc7cf3");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f5cc7cf3"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c95dbd1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d0a2207");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9d0a2207"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                corellia_rebel_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_526ce515");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_410d920c");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_410d920c"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a434ad25");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2a5d0bf1"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dfc39325");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b045a378");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b045a378"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9f50a13");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_102");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_104"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_106");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96bfa401"))
        {
            doAnimationAction(player, "shrug_hands");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_fcddb599");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6318beeb");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6318beeb"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_115");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_117");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_117"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_121"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                corellia_rebel_tier4_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_123");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_125");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_125"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6adec6b0"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_5d976742");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd445db2");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dcabe09b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_f15aeb5");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_beaf1664"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_87e30fff");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb703128");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cd445db2"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8b4d636d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_841d27db");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_841d27db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_900f01d1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c985b91");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 69);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch69(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c985b91"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_d6f2e64d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_29544149");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29544149"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_f59d9106");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37e345fe");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 71);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37e345fe"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_9a01b8f2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eb1c3d4e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb1c3d4e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_forward");
                corellia_rebel_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_f3603d67");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb703128"))
        {
            doAnimationAction(player, "nod_head_once");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_3b361a5f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_761dd18e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 76);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_761dd18e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_a04dd423");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7052aaab");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7052aaab"))
        {
            doAnimationAction(player, "shrug_hands");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_29f4061f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2bbb747");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c2bbb747"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_d16ee99f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 79);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_160"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_162");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_164"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_166");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_168");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 82);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_168"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_170");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_172");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_172"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                corellia_rebel_tier4_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_174");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1c8bddbb"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_178");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3b95cf2f");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f48fdfbd"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_1af32f06");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1551b284"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "curtsey1");
                string_id message = new string_id(c_stringFile, "s_83f72ca3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7065a3b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_60080304"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_7da3fc7a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d054f893");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3b95cf2f"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ff9fae78");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8fc8c755");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 87);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8fc8c755"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4057d656");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_840af840");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_840af840"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_882e7ab9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d06c9b96");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d06c9b96"))
        {
            doAnimationAction(player, "point_to_self");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_a6e1a5f4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bed0dfe7");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bed0dfe7"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8cac3c56");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98199797");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98199797"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_1bb2f893");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7ae51280");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 92);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch92(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7ae51280"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                corellia_rebel_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_11769c29");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7065a3b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a2c04cca");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7fca522a");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7fca522a"))
        {
            doAnimationAction(player, "shake_head_no");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_7afc40b8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c55a344e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c55a344e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_9e1c0119");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1be90a9");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d1be90a9"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ccac6a71");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f50be00f");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 99);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f50be00f"))
        {
            doAnimationAction(player, "nod_head_once");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_207");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_209");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 100);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch100(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_209"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_211");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                corellia_rebel_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_215");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch103(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d054f893"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b45dc260");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 104);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch104(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b45dc260"))
        {
            doAnimationAction(player, "shiver");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_268606ba");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7ce1c24");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7ce1c24"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_21374155");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77633b61");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77633b61"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                string_id message = new string_id(c_stringFile, "s_a64a6f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98588d70");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98588d70"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_229");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_231");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_231"))
        {
            doAnimationAction(player, "point_to_self");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_233");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_235");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_235"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_237");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_239");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 110);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch110(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_239"))
        {
            doAnimationAction(player, "nod_head_once");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_241");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 111);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch111(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_243"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                corellia_rebel_tier4_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_245");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5673ded2"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_1c669");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9cced1f");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 114);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9976a72"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9e3df78d");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5fe6ae71"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f5427310");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ac3e9a6");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 123);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch114(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d9cced1f"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5e4c5d9a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_99d3b4fd");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 115);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch115(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_99d3b4fd"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50669846");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a626852");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 116);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9a626852"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e93fce3e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53544796");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 117);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch117(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53544796"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b607bb2e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8729f60e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 118);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch118(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8729f60e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31fd5acf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9e4fd02");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 119);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch119(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9e4fd02"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127f2e23");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a739e6");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 120);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch120(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_69a739e6"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_abf08a18");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch123(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2ac3e9a6"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_cbcfe001");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4519dec9");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 124);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch124(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4519dec9"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_8762aa6e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_272");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 125);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_272"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b1f1f364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_af22b9b2");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch126(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_af22b9b2"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_chest");
                string_id message = new string_id(c_stringFile, "s_dbcde2d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 127);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch127(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_277"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_279");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_281");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 128);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch128(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_281"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_283");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_285");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 129);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
    public int corellia_rebel_tier4_handleBranch129(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_285"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_287");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_289");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch130(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_head");
                string_id message = new string_id(c_stringFile, "s_291");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_293");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 131);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch131(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_293"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_295");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_297");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 132);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch132(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_297"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_299");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_421"))
        {
            if (!corellia_rebel_tier4_condition_hasStarship04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_422");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_426");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!corellia_rebel_tier4_condition_hasWeapon04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_423");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_428");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 137);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!corellia_rebel_tier4_condition_hasProcedures04(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_424");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_432");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_425");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_434");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 142);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch135(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_426"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_buyStarshipsSkill04(player, npc);
                string_id message = new string_id(c_stringFile, "s_427");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch137(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_428"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_buyWeaponsSkill04(player, npc);
                string_id message = new string_id(c_stringFile, "s_429");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_430");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 138);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch138(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_430"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_431");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch140(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_432"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_buyProceduresSkill04(player, npc);
                string_id message = new string_id(c_stringFile, "s_433");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch142(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_434"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "giveup");
                corellia_rebel_tier4_action_buyDroidSkill04(player, npc);
                string_id message = new string_id(c_stringFile, "s_435");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch143(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_436"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_437");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch145(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_34d61fa3"))
        {
            corellia_rebel_tier4_action_setMetMe(player, npc);
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_450d7208");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 146);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch146(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_mouth");
                string_id message = new string_id(c_stringFile, "s_c384d95e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2846f47d");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 147);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch147(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2846f47d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "tap_foot");
                string_id message = new string_id(c_stringFile, "s_981faf84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8209f997");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 148);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch148(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8209f997"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_3dc78ada");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_afc14266");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch149(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_afc14266"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fc6dfba9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_325");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_345");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 150);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch150(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_325"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_58e80738");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d79718db");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_345"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_347");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_349");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 160);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch151(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d79718db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f51574ed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73313039");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 152);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch152(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_73313039"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28d914fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42880861");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_42880861"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_dc241019");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48529763");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 154);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch154(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_48529763"))
        {
            doAnimationAction(player, "explain");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f13513cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c3363bcd");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch155(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c3363bcd"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_337");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_539f9a2e");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 156);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch156(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_539f9a2e"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                string_id message = new string_id(c_stringFile, "s_e3e480fe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e602d1b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 157);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch157(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7e602d1b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6429f86b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e9d0848");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 158);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch158(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8e9d0848"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9bbb9999");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_647ad2bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9116b4d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8450d7db");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9943289");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9775f996");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 167);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch159(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647ad2bc"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8209030");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9116b4d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fed79efe");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8450d7db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0d064ef");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9943289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_835acc11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9775f996"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch160(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_349"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_351");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_353");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch161(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_353"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_355");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_357");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 162);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch162(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_357"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_359");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_361");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 163);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch163(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_361"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                string_id message = new string_id(c_stringFile, "s_363");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_365");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 164);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch164(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_365"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_367");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 165);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch165(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_369"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_371");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_647ad2bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9116b4d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8450d7db");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9943289");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9775f996");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 167);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch166(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647ad2bc"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8209030");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9116b4d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fed79efe");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8450d7db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0d064ef");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9943289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_835acc11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9775f996"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch167(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647ad2bc"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8209030");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9116b4d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fed79efe");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8450d7db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0d064ef");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9943289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_835acc11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9775f996"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch172(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_384"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_386");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 173);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch173(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388"))
        {
            doAnimationAction(player, "explain");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_390");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_392");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 174);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch174(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_392"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_394");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_396");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 175);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch175(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_396"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "threaten");
                string_id message = new string_id(c_stringFile, "s_398");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_400");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 176);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch176(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_400"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_402");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_404");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch177(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_404"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_406");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_647ad2bc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d9116b4d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8450d7db");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9943289");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9775f996");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 167);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch178(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647ad2bc"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8209030");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9116b4d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fed79efe");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8450d7db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0d064ef");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9943289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_835acc11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9775f996"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch179(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bbe3529b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_bcbc9230");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_86677c6b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 180);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36f346a5"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh");
                string_id message = new string_id(c_stringFile, "s_8057f803");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ff9bf289");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 184);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch180(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_86677c6b"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6634adb9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bcc6b959");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 181);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch181(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bcc6b959"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d74e67f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c4789b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 182);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch182(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c4789b"))
        {
            doAnimationAction(player, "nod_head_once");
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_4ef35507");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch184(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ff9bf289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_cb25bcd4");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_rebel_tier4_handleBranch186(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_647ad2bc"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionFour(player, npc);
                string_id message = new string_id(c_stringFile, "s_d8209030");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d9116b4d"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_fed79efe");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8450d7db"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_b0d064ef");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9943289"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                corellia_rebel_tier4_action_grantDutyMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_835acc11");
                utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9775f996"))
        {
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4bce8f6f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_384");
                    }
                    utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
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
            detachScript(self, "conversation.corellia_rebel_tier4");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Worvog Arkon");
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Worvog Arkon");
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setInvulnerable(self, true);
        factions.setFaction(self, factions.FACTION_REBEL);
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
        detachScript(self, "conversation.corellia_rebel_tier4");
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
        if (!corellia_rebel_tier4_condition_isPilot(player, npc))
        {
            doAnimationAction(npc, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_92c6d3dd");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isWrongFaction(player, npc))
        {
            doAnimationAction(npc, "listen");
            string_id message = new string_id(c_stringFile, "s_774fb2f0");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7e579e1e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fb4c1f1f");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 2);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_rebel_tier4_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_84c908b8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6222b64d");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 5);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_rebel_tier4_condition_hasCompletedTier3(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_4f44d41d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e0dc5fad");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e86f1062");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 8);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isOnMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_256f3624");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4ccb8d59");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1013aa7");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 11);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_rebel_tier4", null, pp, responses);
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
        if (corellia_rebel_tier4_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "slit_throat");
            string_id message = new string_id(c_stringFile, "s_de1e215f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7090182");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 14);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isGettingMissionOneReward(player, npc))
        {
            doAnimationAction(npc, "applause_excited");
            corellia_rebel_tier4_action_rewardForMissionOne(player, npc);
            string_id message = new string_id(c_stringFile, "s_f95cd5f5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95c399f6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6f679fea");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 18);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isGettingMissionTwoReward(player, npc))
        {
            doAnimationAction(npc, "shrug_hands");
            corellia_rebel_tier4_action_rewardForMissionTwo(player, npc);
            string_id message = new string_id(c_stringFile, "s_da8e9da5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_def742");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b20c71fe");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 21);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isGettingMissionThreeReward(player, npc))
        {
            doAnimationAction(npc, "salute1");
            corellia_rebel_tier4_action_rewardForMissionThree(player, npc);
            string_id message = new string_id(c_stringFile, "s_10d8bf69");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aed5139a");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cb6eb294");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 24);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isGettingMissionFourReward(player, npc))
        {
            doAnimationAction(npc, "laugh");
            corellia_rebel_tier4_action_rewardForMissionFour(player, npc);
            string_id message = new string_id(c_stringFile, "s_81e25d24");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_61485366");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_197c8928");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 30);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_failedMissionOne(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_4f755af2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8f3b3edb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_88563b84");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 33);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_failedMissionTwo(player, npc))
        {
            doAnimationAction(npc, "stamp_feet");
            string_id message = new string_id(c_stringFile, "s_1909d363");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87d7e908");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e42b260e");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 36);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_failedMissionThree(player, npc))
        {
            doAnimationAction(npc, "smack_self");
            string_id message = new string_id(c_stringFile, "s_d3d98e75");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e30b9697");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10eafe13");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 39);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_failedMissionFour(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_78b8ac8f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4dd9a7b9");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_35dd4aa2");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 42);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_readyForMissionOne(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            string_id message = new string_id(c_stringFile, "s_5bf6779f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3609c810");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3c546115");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ef3e641a");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 45);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_rebel_tier4", null, pp, responses);
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
        if (corellia_rebel_tier4_condition_readyForMissionTwo(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1047bc70");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6adec6b0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dcabe09b");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_beaf1664");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 66);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_readyForMissionThree(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_e8f374d9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1c8bddbb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f48fdfbd");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1551b284");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60080304");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 85);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_readyForMissionFour(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ceb77d0c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5673ded2");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9976a72");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5fe6ae71");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 113);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition_isReadyForTraining(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_8907c99");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_421");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 134);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_rebel_tier4_condition_hasMetMe(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_71f899d5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34d61fa3");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 145);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_rebel_tier4_condition_hasCompletedTier4(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_a5958aac");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_647ad2bc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d9116b4d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8450d7db");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9943289");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9775f996");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 167);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!corellia_rebel_tier4_condition_hasMasterSkill(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_b6b4b241");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bbe3529b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_36f346a5");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 179);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d318a2bb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (corellia_rebel_tier4_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_647ad2bc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d9116b4d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8450d7db");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9943289");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9775f996");
                }
                utils.setScriptVar(player, "conversation.corellia_rebel_tier4.branchId", 167);
                npcStartConversation(player, npc, "corellia_rebel_tier4", message, responses);
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
        if (!conversationId.equals("corellia_rebel_tier4"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
        if (branchId == 2 && corellia_rebel_tier4_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && corellia_rebel_tier4_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_rebel_tier4_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && corellia_rebel_tier4_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && corellia_rebel_tier4_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_rebel_tier4_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corellia_rebel_tier4_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && corellia_rebel_tier4_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && corellia_rebel_tier4_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && corellia_rebel_tier4_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && corellia_rebel_tier4_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corellia_rebel_tier4_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corellia_rebel_tier4_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && corellia_rebel_tier4_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && corellia_rebel_tier4_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && corellia_rebel_tier4_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && corellia_rebel_tier4_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && corellia_rebel_tier4_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && corellia_rebel_tier4_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && corellia_rebel_tier4_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && corellia_rebel_tier4_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && corellia_rebel_tier4_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && corellia_rebel_tier4_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && corellia_rebel_tier4_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && corellia_rebel_tier4_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && corellia_rebel_tier4_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && corellia_rebel_tier4_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && corellia_rebel_tier4_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && corellia_rebel_tier4_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && corellia_rebel_tier4_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && corellia_rebel_tier4_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && corellia_rebel_tier4_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && corellia_rebel_tier4_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && corellia_rebel_tier4_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && corellia_rebel_tier4_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && corellia_rebel_tier4_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && corellia_rebel_tier4_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && corellia_rebel_tier4_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && corellia_rebel_tier4_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 69 && corellia_rebel_tier4_handleBranch69(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && corellia_rebel_tier4_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && corellia_rebel_tier4_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && corellia_rebel_tier4_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && corellia_rebel_tier4_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && corellia_rebel_tier4_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && corellia_rebel_tier4_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && corellia_rebel_tier4_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && corellia_rebel_tier4_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && corellia_rebel_tier4_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && corellia_rebel_tier4_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && corellia_rebel_tier4_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && corellia_rebel_tier4_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && corellia_rebel_tier4_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && corellia_rebel_tier4_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && corellia_rebel_tier4_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && corellia_rebel_tier4_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && corellia_rebel_tier4_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && corellia_rebel_tier4_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && corellia_rebel_tier4_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 92 && corellia_rebel_tier4_handleBranch92(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && corellia_rebel_tier4_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && corellia_rebel_tier4_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && corellia_rebel_tier4_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && corellia_rebel_tier4_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && corellia_rebel_tier4_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 100 && corellia_rebel_tier4_handleBranch100(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && corellia_rebel_tier4_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 103 && corellia_rebel_tier4_handleBranch103(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 104 && corellia_rebel_tier4_handleBranch104(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && corellia_rebel_tier4_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && corellia_rebel_tier4_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && corellia_rebel_tier4_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && corellia_rebel_tier4_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && corellia_rebel_tier4_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 110 && corellia_rebel_tier4_handleBranch110(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 111 && corellia_rebel_tier4_handleBranch111(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && corellia_rebel_tier4_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 114 && corellia_rebel_tier4_handleBranch114(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 115 && corellia_rebel_tier4_handleBranch115(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && corellia_rebel_tier4_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 117 && corellia_rebel_tier4_handleBranch117(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 118 && corellia_rebel_tier4_handleBranch118(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 119 && corellia_rebel_tier4_handleBranch119(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 120 && corellia_rebel_tier4_handleBranch120(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 123 && corellia_rebel_tier4_handleBranch123(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 124 && corellia_rebel_tier4_handleBranch124(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && corellia_rebel_tier4_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 126 && corellia_rebel_tier4_handleBranch126(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 127 && corellia_rebel_tier4_handleBranch127(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 128 && corellia_rebel_tier4_handleBranch128(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 129 && corellia_rebel_tier4_handleBranch129(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 130 && corellia_rebel_tier4_handleBranch130(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 131 && corellia_rebel_tier4_handleBranch131(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 132 && corellia_rebel_tier4_handleBranch132(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && corellia_rebel_tier4_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 135 && corellia_rebel_tier4_handleBranch135(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 137 && corellia_rebel_tier4_handleBranch137(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 138 && corellia_rebel_tier4_handleBranch138(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 140 && corellia_rebel_tier4_handleBranch140(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 142 && corellia_rebel_tier4_handleBranch142(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 143 && corellia_rebel_tier4_handleBranch143(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 145 && corellia_rebel_tier4_handleBranch145(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 146 && corellia_rebel_tier4_handleBranch146(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 147 && corellia_rebel_tier4_handleBranch147(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 148 && corellia_rebel_tier4_handleBranch148(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 149 && corellia_rebel_tier4_handleBranch149(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 150 && corellia_rebel_tier4_handleBranch150(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 151 && corellia_rebel_tier4_handleBranch151(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 152 && corellia_rebel_tier4_handleBranch152(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && corellia_rebel_tier4_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 154 && corellia_rebel_tier4_handleBranch154(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 155 && corellia_rebel_tier4_handleBranch155(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 156 && corellia_rebel_tier4_handleBranch156(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 157 && corellia_rebel_tier4_handleBranch157(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 158 && corellia_rebel_tier4_handleBranch158(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 159 && corellia_rebel_tier4_handleBranch159(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 160 && corellia_rebel_tier4_handleBranch160(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 161 && corellia_rebel_tier4_handleBranch161(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 162 && corellia_rebel_tier4_handleBranch162(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 163 && corellia_rebel_tier4_handleBranch163(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 164 && corellia_rebel_tier4_handleBranch164(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 165 && corellia_rebel_tier4_handleBranch165(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 166 && corellia_rebel_tier4_handleBranch166(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 167 && corellia_rebel_tier4_handleBranch167(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 172 && corellia_rebel_tier4_handleBranch172(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 173 && corellia_rebel_tier4_handleBranch173(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 174 && corellia_rebel_tier4_handleBranch174(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 175 && corellia_rebel_tier4_handleBranch175(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 176 && corellia_rebel_tier4_handleBranch176(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 177 && corellia_rebel_tier4_handleBranch177(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 178 && corellia_rebel_tier4_handleBranch178(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 179 && corellia_rebel_tier4_handleBranch179(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 180 && corellia_rebel_tier4_handleBranch180(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 181 && corellia_rebel_tier4_handleBranch181(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 182 && corellia_rebel_tier4_handleBranch182(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 184 && corellia_rebel_tier4_handleBranch184(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 186 && corellia_rebel_tier4_handleBranch186(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_rebel_tier4.branchId");
        return SCRIPT_CONTINUE;
    }
}
