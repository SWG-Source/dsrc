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
import script.library.xp;

public class corellia_imperial_trainer_2 extends script.base_script
{
    public corellia_imperial_trainer_2()
    {
    }
    public static String c_stringFile = "conversation/corellia_imperial_trainer_2";
    public boolean corellia_imperial_trainer_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_imperial_trainer_2_condition_hasWonMission15(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "destroy_surpriseattack", "corellia_imperial_15_split") && !space_quest.hasReceivedReward(player, "destroy_surpriseattack", "corellia_imperial_15_split"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasWonMission12(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "corellia_imperial_12") && !space_quest.hasReceivedReward(player, "assassinate", "corellia_imperial_12"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.IMPERIAL_CORELLIA);
    }
    public boolean corellia_imperial_trainer_2_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean corellia_imperial_trainer_2_condition_isReadyForTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player))
        {
            return false;
        }
        if (space_quest.hasWonQuest(player, "recovery", "corellia_imperial_16") && space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_trainer_2_condition_hasDroidSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_droid_02"));
    }
    public boolean corellia_imperial_trainer_2_condition_hasProceduresSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_procedures_02"));
    }
    public boolean corellia_imperial_trainer_2_condition_hasStarshipSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_starships_02"));
    }
    public boolean corellia_imperial_trainer_2_condition_hasWeaponSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_weapons_02"));
    }
    public boolean corellia_imperial_trainer_2_condition_hasAllTier2Skills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player) && space_quest.hasReceivedReward(player, "destroy_surpriseattack", "corellia_imperial_15_split"))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_trainer_2_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean corellia_imperial_trainer_2_condition_isPrivateer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_neutral_novice"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.getFaction(player) == factions.FACTION_REBEL)
        {
            return true;
        }
        if (hasSkill(player, "pilot_rebel_navy_novice"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedMission12(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "corellia_imperial_12") || space_quest.hasAbortedQuest(player, "assassinate", "corellia_imperial_12"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedMission13(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "inspect", "corellia_imperial_13") || space_quest.hasAbortedQuest(player, "inspect", "corellia_imperial_13"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedMission14(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "corellia_imperial_14") || space_quest.hasAbortedQuest(player, "assassinate", "corellia_imperial_14"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedMission15(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy_surpriseattack", "corellia_imperial_15_split") || space_quest.hasAbortedQuest(player, "destroy_surpriseattack", "corellia_imperial_15_split") || space_quest.hasFailedQuest(player, "patrol", "corellia_imperial_15") || space_quest.hasAbortedQuest(player, "patrol", "corellia_imperial_15"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasCompletedOpening(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "recovery", "corellia_imperial_16");
    }
    public boolean corellia_imperial_trainer_2_condition_isReadyForPhase2Other(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuestSeries(player, "imperial_corellia_1");
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedMissionFinal(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "survival", "corellia_tier2_final") || space_quest.hasAbortedQuest(player, "survival", "corellia_tier2_final") || space_quest.hasFailedQuest(player, "assassinate", "corellia_imperial_17") || space_quest.hasAbortedQuest(player, "assassinate", "corellia_imperial_17"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasCompletedFinal(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "survival", "corellia_tier2_final");
    }
    public boolean corellia_imperial_trainer_2_condition_hasWonMission13(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "inspect", "corellia_imperial_13") && !space_quest.hasReceivedReward(player, "inspect", "corellia_imperial_13"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasWonMission14(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "corellia_imperial_14") && !space_quest.hasReceivedReward(player, "assassinate", "corellia_imperial_14"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 0.0f);
    }
    public boolean corellia_imperial_trainer_2_condition_hasNegativeFactionMale(obj_id player, obj_id npc) throws InterruptedException
    {
        return ((factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 0.0f) && getGender(player) == GENDER_MALE);
    }
    public boolean corellia_imperial_trainer_2_condition_hasFailedOpening(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "recovery", "corellia_imperial_16") || space_quest.hasAbortedQuest(player, "recovery", "corellia_imperial_16"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean corellia_imperial_trainer_2_condition_isReadyForMission1(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corellia_imperial_trainer_2_condition_isReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corellia_imperial_trainer_2_condition_isReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corellia_imperial_trainer_2_condition_isReadyForMission4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean corellia_imperial_trainer_2_condition_isReadyForMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "readyForTier2Mission");
    }
    public void corellia_imperial_trainer_2_action_giveReward12(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "corellia_imperial_12"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "assassinate", "corellia_imperial_12", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_imperial_cygnus_starblaster.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void corellia_imperial_trainer_2_action_grantMission08(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "corellia_imperial_8");
    }
    public void corellia_imperial_trainer_2_action_grantMission09(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "corellia_imperial_9");
    }
    public void corellia_imperial_trainer_2_action_grantMission10(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "corellia_imperial_10");
    }
    public void corellia_imperial_trainer_2_action_grantMission11(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "corellia_imperial_11");
    }
    public void corellia_imperial_trainer_2_action_buyWeaponSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_weapons_02");
    }
    public void corellia_imperial_trainer_2_action_buyStarshipSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_starships_02");
    }
    public void corellia_imperial_trainer_2_action_buyProcedureSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_procedures_02");
    }
    public void corellia_imperial_trainer_2_action_buyDroidSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_droid_02");
    }
    public void corellia_imperial_trainer_2_action_grantMission12(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "corellia_imperial_12");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void corellia_imperial_trainer_2_action_grantMission13(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "corellia_imperial_13");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void corellia_imperial_trainer_2_action_grantMission14(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "corellia_imperial_14");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void corellia_imperial_trainer_2_action_grantMission15(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "corellia_imperial_15");
        space_flags.removeSpaceFlag(player, "readyForTier2Mission");
    }
    public void corellia_imperial_trainer_2_action_grantMissionOpen(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "corellia_imperial_16");
    }
    public void corellia_imperial_trainer_2_action_grantMissionFinal(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "corellia_imperial_17");
    }
    public void corellia_imperial_trainer_2_action_giveReward13(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "inspect", "corellia_imperial_13"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "inspect", "corellia_imperial_13", 5000, "object/tangible/ship/components/armor/arm_mission_reward_imperial_sfs_light_military.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void corellia_imperial_trainer_2_action_giveReward14(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "corellia_imperial_14"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "assassinate", "corellia_imperial_14", 5000, "object/tangible/ship/components/booster/bst_mission_reward_imperial_rss_ion_booster.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void corellia_imperial_trainer_2_action_giveReward15(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "destroy_surpriseattack", "corellia_imperial_15_split"))
        {
            faceTo(npc, player);
            space_quest.giveReward(player, "destroy_surpriseattack", "corellia_imperial_15_split", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_rendili_dual_projector.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void corellia_imperial_trainer_2_action_setReadyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 1);
    }
    public void corellia_imperial_trainer_2_action_setReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 2);
    }
    public void corellia_imperial_trainer_2_action_setReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 3);
    }
    public void corellia_imperial_trainer_2_action_setReadyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "readyForTier2Mission", 4);
    }
    public int corellia_imperial_trainer_2_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7090182"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_83993cf3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ecb17e60");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ecb17e60"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_3ec7906d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b204877");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5b204877"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_8f26747e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71d84d34");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71d84d34"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_f35be898");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
    public int corellia_imperial_trainer_2_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c2b7ea3a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_af154d31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_24");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
    public int corellia_imperial_trainer_2_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4854758d"))
        {
            if (corellia_imperial_trainer_2_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60a52374");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5df827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_798fbe2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a0f022");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e38e6a0");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42aa1748");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bd5df827"))
        {
            corellia_imperial_trainer_2_action_grantMission08(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc33e486");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_798fbe2"))
        {
            corellia_imperial_trainer_2_action_grantMission09(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e8db34a5");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a0f022"))
        {
            corellia_imperial_trainer_2_action_grantMission10(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e17fc7e4");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3e38e6a0"))
        {
            corellia_imperial_trainer_2_action_grantMission11(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3ab6a9ed");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6d3be6aa"))
        {
            if (corellia_imperial_trainer_2_condition_isReadyForMission1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50b5b468");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_trainer_2_condition_isReadyForMission2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_79813bfb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_trainer_2_condition_isReadyForMission3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c52a69c1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1946850");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_246"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                corellia_imperial_trainer_2_action_grantMission13(player, npc);
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_208");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_79375065"))
        {
            doAnimationAction(player, "salute2");
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25457635");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d335136f"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ee11d13c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5df827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_798fbe2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a0f022");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e38e6a0");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9d2c9b0a"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e978ca6b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7ca4fcb8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7ca4fcb8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25edc644");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17780ca7");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17780ca7"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_af16cd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9cca1500"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0c8febb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ed7de92"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2fa7f312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83bf02e8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1f1fea2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dbd8c729"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionFinal(player, npc);
                string_id message = new string_id(c_stringFile, "s_329f7488");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9cca1500"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0c8febb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ed7de92"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2fa7f312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83bf02e8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1f1fea2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dbd8c729"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionFinal(player, npc);
                string_id message = new string_id(c_stringFile, "s_329f7488");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9cca1500"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0c8febb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ed7de92"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2fa7f312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83bf02e8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1f1fea2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dbd8c729"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionFinal(player, npc);
                string_id message = new string_id(c_stringFile, "s_329f7488");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9cca1500"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f0c8febb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6ed7de92"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2fa7f312");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_83bf02e8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1f1fea2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9cca1500");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ed7de92");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83bf02e8");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbd8c729");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dbd8c729"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionFinal(player, npc);
                string_id message = new string_id(c_stringFile, "s_329f7488");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5ffdd434"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_ccf466b2");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37680b25"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionOpen(player, npc);
                string_id message = new string_id(c_stringFile, "s_5bfd4371");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59894e66"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_giveReward12(player, npc);
                string_id message = new string_id(c_stringFile, "s_c4179a5d");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d4374209"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_giveReward13(player, npc);
                string_id message = new string_id(c_stringFile, "s_9f952cc3");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16439d46"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_giveReward14(player, npc);
                string_id message = new string_id(c_stringFile, "s_a70cef5");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8e613fca"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_giveReward15(player, npc);
                string_id message = new string_id(c_stringFile, "s_345e676a");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_164"))
        {
            if (!corellia_imperial_trainer_2_condition_hasStarshipSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_165");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_trainer_2_condition_hasWeaponSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_166");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!corellia_imperial_trainer_2_condition_hasProceduresSkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_167");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_182");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 65);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_170");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_214"))
        {
            corellia_imperial_trainer_2_action_buyStarshipSkill2(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_setReadyForMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_218");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_218"))
        {
            doAnimationAction(player, "shake_head_disgust");
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_220");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_246"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_226"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_228");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_230");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 54);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_230"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_232");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_238"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_242"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMission12(player, npc);
                string_id message = new string_id(c_stringFile, "s_244");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
    public int corellia_imperial_trainer_2_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_238"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_242"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMission12(player, npc);
                string_id message = new string_id(c_stringFile, "s_244");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
    public int corellia_imperial_trainer_2_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_238"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_240");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_238");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_242");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_242"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMission12(player, npc);
                string_id message = new string_id(c_stringFile, "s_244");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
    public int corellia_imperial_trainer_2_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_246"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            corellia_imperial_trainer_2_action_buyWeaponSkill2(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_setReadyForMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_200");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_202");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_202"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_204");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_206");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_208");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_210");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_210"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMission14(player, npc);
                string_id message = new string_id(c_stringFile, "s_212");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch65(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_182"))
        {
            corellia_imperial_trainer_2_action_buyProcedureSkill2(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_setReadyForMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_184");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_186"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_188");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_190"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_192");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_194");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_194"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMission15(player, npc);
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_170"))
        {
            corellia_imperial_trainer_2_action_buyDroidSkill2(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_setReadyForMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_172");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_174");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_176");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_178");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_178"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                corellia_imperial_trainer_2_action_grantMission13(player, npc);
                string_id message = new string_id(c_stringFile, "s_180");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_128"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ee11d13c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5df827");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_798fbe2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a0f022");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e38e6a0");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25c6e073"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_395e48e8");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bd5df827"))
        {
            corellia_imperial_trainer_2_action_grantMission08(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_dc33e486");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_798fbe2"))
        {
            corellia_imperial_trainer_2_action_grantMission09(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e8db34a5");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a0f022"))
        {
            corellia_imperial_trainer_2_action_grantMission10(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e17fc7e4");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3e38e6a0"))
        {
            corellia_imperial_trainer_2_action_grantMission11(player, npc);
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3ab6a9ed");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9edd30bf"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c71a5ce3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f87e5fe8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f87e5fe8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_eea972b1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f4f691ae");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f4f691ae"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2ee19e18");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7a33d3dc");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7a33d3dc"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_fd8878bc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e7f16de3"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_683cbf70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f37f3d1a"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slit_throat");
                string_id message = new string_id(c_stringFile, "s_bc90f6c5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8bf01045"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aa457e5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e7f16de3"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_683cbf70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f37f3d1a"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slit_throat");
                string_id message = new string_id(c_stringFile, "s_bc90f6c5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8bf01045"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aa457e5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e7f16de3"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_683cbf70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f37f3d1a"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slit_throat");
                string_id message = new string_id(c_stringFile, "s_bc90f6c5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e7f16de3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f37f3d1a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bf01045");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8bf01045"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aa457e5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83c3790d"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52841755");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26912e4b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8516ab0"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4ea4b48d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1d1f03a8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionOpen(player, npc);
                string_id message = new string_id(c_stringFile, "s_38d28ac2");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_26912e4b"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4a5ef35b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83c3790d"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52841755");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26912e4b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8516ab0"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4ea4b48d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1d1f03a8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionOpen(player, npc);
                string_id message = new string_id(c_stringFile, "s_38d28ac2");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_imperial_trainer_2_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_83c3790d"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_52841755");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26912e4b");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c8516ab0"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4ea4b48d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_83c3790d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8516ab0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1d1f03a8");
                    }
                    utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1d1f03a8"))
        {
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                corellia_imperial_trainer_2_action_grantMissionOpen(player, npc);
                string_id message = new string_id(c_stringFile, "s_38d28ac2");
                utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
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
            detachScript(self, "conversation.corellia_imperial_trainer_2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setupNpc(self, "Prisk Kith`Vys", true, true);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setupNpc(self, "Prisk Kith`Vys", true, true);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
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
        detachScript(self, "conversation.corellia_imperial_trainer_2");
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
        if (corellia_imperial_trainer_2_condition_isPrivateer(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_fc007f1");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_isRebel(player, npc))
        {
            doAnimationAction(npc, "slow_down");
            string_id message = new string_id(c_stringFile, "s_dbf39d2a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!corellia_imperial_trainer_2_condition_isPilot(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_4391b2de");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasNegativeFactionMale(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            string_id message = new string_id(c_stringFile, "s_f7df921b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", null, pp, responses);
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
        if (corellia_imperial_trainer_2_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "sigh_deeply");
            string_id message = new string_id(c_stringFile, "s_9d62faa2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 9);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", null, pp, responses);
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
        if (!corellia_imperial_trainer_2_condition_onMyTrack(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            string_id message = new string_id(c_stringFile, "s_74f9775d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 14);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_isReadyForMission(player, npc))
        {
            doAnimationAction(npc, "nod");
            string_id message = new string_id(c_stringFile, "s_9a117462");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6d3be6aa");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 17);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasCompletedFinal(player, npc))
        {
            doAnimationAction(npc, "thumb_up");
            string_id message = new string_id(c_stringFile, "s_be3a7e31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_79375065");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d335136f");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 22);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedMissionFinal(player, npc))
        {
            doAnimationAction(npc, "pound_fist_palm");
            corellia_imperial_trainer_2_action_grantMissionFinal(player, npc);
            string_id message = new string_id(c_stringFile, "s_4cc1cf3b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasAllTier2Skills(player, npc))
        {
            doAnimationAction(npc, "nod");
            string_id message = new string_id(c_stringFile, "s_d9391d4a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9d2c9b0a");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 25);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_isOnQuest(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_ae8492d2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5ffdd434");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 33);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedOpening(player, npc))
        {
            doAnimationAction(npc, "point_accusingly");
            string_id message = new string_id(c_stringFile, "s_e2d5b9eb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37680b25");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 35);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedMission12(player, npc))
        {
            doAnimationAction(npc, "point_up");
            corellia_imperial_trainer_2_action_grantMission12(player, npc);
            string_id message = new string_id(c_stringFile, "s_bae9fe80");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedMission13(player, npc))
        {
            doAnimationAction(npc, "shake_head_disgust");
            corellia_imperial_trainer_2_action_grantMission13(player, npc);
            string_id message = new string_id(c_stringFile, "s_63e01bb7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedMission14(player, npc))
        {
            corellia_imperial_trainer_2_action_grantMission14(player, npc);
            string_id message = new string_id(c_stringFile, "s_b5a3714a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasFailedMission15(player, npc))
        {
            doAnimationAction(npc, "point_up");
            corellia_imperial_trainer_2_action_grantMission15(player, npc);
            string_id message = new string_id(c_stringFile, "s_fe284208");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasWonMission12(player, npc))
        {
            doAnimationAction(npc, "thumb_up");
            string_id message = new string_id(c_stringFile, "s_e67a0734");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59894e66");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 41);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasWonMission13(player, npc))
        {
            doAnimationAction(npc, "point_to_self");
            string_id message = new string_id(c_stringFile, "s_cfb9fb6e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d4374209");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 43);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasWonMission14(player, npc))
        {
            doAnimationAction(npc, "applause_polite");
            string_id message = new string_id(c_stringFile, "s_b69b5bf4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16439d46");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 45);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_hasWonMission15(player, npc))
        {
            doAnimationAction(npc, "thumb_up");
            string_id message = new string_id(c_stringFile, "s_9d96ae32");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8e613fca");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 47);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition_isReadyForTraining(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_584a90f8");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 49);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", null, pp, responses);
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
        if (corellia_imperial_trainer_2_condition_hasCompletedOpening(player, npc))
        {
            doAnimationAction(npc, "thumbs_up");
            string_id message = new string_id(c_stringFile, "s_392fc531");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25c6e073");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 74);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", null, pp, responses);
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
        if (corellia_imperial_trainer_2_condition_isReadyForPhase2Other(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6a1562ad");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9edd30bf");
                }
                utils.setScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId", 81);
                npcStartConversation(player, npc, "corellia_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_imperial_trainer_2_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_e71f0d40");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_imperial_trainer_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
        if (branchId == 4 && corellia_imperial_trainer_2_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && corellia_imperial_trainer_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && corellia_imperial_trainer_2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && corellia_imperial_trainer_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && corellia_imperial_trainer_2_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && corellia_imperial_trainer_2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && corellia_imperial_trainer_2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && corellia_imperial_trainer_2_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && corellia_imperial_trainer_2_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && corellia_imperial_trainer_2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && corellia_imperial_trainer_2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && corellia_imperial_trainer_2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && corellia_imperial_trainer_2_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && corellia_imperial_trainer_2_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && corellia_imperial_trainer_2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && corellia_imperial_trainer_2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && corellia_imperial_trainer_2_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && corellia_imperial_trainer_2_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && corellia_imperial_trainer_2_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && corellia_imperial_trainer_2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && corellia_imperial_trainer_2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && corellia_imperial_trainer_2_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && corellia_imperial_trainer_2_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && corellia_imperial_trainer_2_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && corellia_imperial_trainer_2_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && corellia_imperial_trainer_2_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && corellia_imperial_trainer_2_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && corellia_imperial_trainer_2_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && corellia_imperial_trainer_2_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && corellia_imperial_trainer_2_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && corellia_imperial_trainer_2_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && corellia_imperial_trainer_2_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && corellia_imperial_trainer_2_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && corellia_imperial_trainer_2_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && corellia_imperial_trainer_2_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && corellia_imperial_trainer_2_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && corellia_imperial_trainer_2_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && corellia_imperial_trainer_2_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && corellia_imperial_trainer_2_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && corellia_imperial_trainer_2_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && corellia_imperial_trainer_2_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && corellia_imperial_trainer_2_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && corellia_imperial_trainer_2_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 65 && corellia_imperial_trainer_2_handleBranch65(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && corellia_imperial_trainer_2_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && corellia_imperial_trainer_2_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && corellia_imperial_trainer_2_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && corellia_imperial_trainer_2_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && corellia_imperial_trainer_2_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && corellia_imperial_trainer_2_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && corellia_imperial_trainer_2_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && corellia_imperial_trainer_2_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && corellia_imperial_trainer_2_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && corellia_imperial_trainer_2_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && corellia_imperial_trainer_2_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && corellia_imperial_trainer_2_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && corellia_imperial_trainer_2_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && corellia_imperial_trainer_2_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && corellia_imperial_trainer_2_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && corellia_imperial_trainer_2_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && corellia_imperial_trainer_2_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && corellia_imperial_trainer_2_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && corellia_imperial_trainer_2_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_imperial_trainer_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
