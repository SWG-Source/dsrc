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

public class naboo_imperial_trainer_2 extends script.base_script
{
    public naboo_imperial_trainer_2()
    {
    }
    public static String c_stringFile = "conversation/naboo_imperial_trainer_2";
    public boolean naboo_imperial_trainer_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_imperial_trainer_2_condition_hasAllSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierTwo(player);
    }
    public boolean naboo_imperial_trainer_2_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.IMPERIAL_NABOO);
    }
    public boolean naboo_imperial_trainer_2_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasSpaceFlag(player, "inquisitorialAgent");
    }
    public boolean naboo_imperial_trainer_2_condition_isPrivateer(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean naboo_imperial_trainer_2_condition_isRebel(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean naboo_imperial_trainer_2_condition_hasFailedQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "inspect", "naboo_imperial_tier2_1") || space_quest.hasAbortedQuest(player, "inspect", "naboo_imperial_tier2_1"));
    }
    public boolean naboo_imperial_trainer_2_condition_hasWonQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "inspect", "naboo_imperial_tier2_1");
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForDutyBlock1(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "survival", "naboo_imperial_tier2_2");
    }
    public boolean naboo_imperial_trainer_2_condition_hasWonQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "survival", "naboo_imperial_tier2_2");
    }
    public boolean naboo_imperial_trainer_2_condition_hasFailedQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "survival", "naboo_imperial_tier2_2") || space_quest.hasAbortedQuest(player, "survival", "naboo_imperial_tier2_2"));
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "inspect", "naboo_imperial_tier2_1");
    }
    public boolean naboo_imperial_trainer_2_condition_canBuySkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_2_condition_canBuySkill1A(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_02") && !hasSkill(player, "pilot_imperial_navy_starships_02");
    }
    public boolean naboo_imperial_trainer_2_condition_canBuySkill1B(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_02") && !hasSkill(player, "pilot_imperial_navy_weapons_02");
    }
    public boolean naboo_imperial_trainer_2_condition_canBuySkill1C(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_02") && !hasSkill(player, "pilot_imperial_navy_procedures_02");
    }
    public boolean naboo_imperial_trainer_2_condition_canBuySkill1D(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_02") && !hasSkill(player, "pilot_imperial_navy_droid_02");
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_imperial_navy_starships_02") || hasSkill(player, "pilot_imperial_navy_weapons_02") || hasSkill(player, "pilot_imperial_navy_procedures_02") || hasSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_2_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean naboo_imperial_trainer_2_condition_isWorkingForTrent(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player) && space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_tier2_4"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierTwo(player);
    }
    public boolean naboo_imperial_trainer_2_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean naboo_imperial_trainer_2_condition_isReadyForTraining(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "survival", "naboo_imperial_tier2_2"))
        {
            return false;
        }
        if (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_2_condition_rewardedForClaw(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "assassinate", "imperial_ss_3");
    }
    public boolean naboo_imperial_trainer_2_condition_isHereToSignUp(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuestSeries(player, "naboo_imperial_1");
    }
    public boolean naboo_imperial_trainer_2_condition_hasFailedQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "recovery", "naboo_imperial_tier2_3") || space_quest.hasAbortedQuest(player, "recovery", "naboo_imperial_tier2_3"));
    }
    public boolean naboo_imperial_trainer_2_condition_hasWonQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "recovery", "naboo_imperial_tier2_3");
    }
    public boolean naboo_imperial_trainer_2_condition_hasFailedQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasFailedQuest(player, "assassinate", "naboo_imperial_tier2_4") || space_quest.hasAbortedQuest(player, "assassinate", "naboo_imperial_tier2_4"));
    }
    public boolean naboo_imperial_trainer_2_condition_hasWonQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasWonQuest(player, "assassinate", "naboo_imperial_tier2_4");
    }
    public boolean naboo_imperial_trainer_2_condition_hasWonQuest3A(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasReceivedReward(player, "recovery", "naboo_imperial_tier2_3");
    }
    public boolean naboo_imperial_trainer_2_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 0.0f);
    }
    public void naboo_imperial_trainer_2_action_makeInquisitorialAgent(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "inquisitorialAgent", 1);
    }
    public void naboo_imperial_trainer_2_action_giveQuest1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "inspect", "naboo_imperial_tier2_1");
    }
    public void naboo_imperial_trainer_2_action_giveReward1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "inspect", "naboo_imperial_tier2_1"))
        {
            space_quest.giveReward(player, "inspect", "naboo_imperial_tier2_1", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_imperial_cygnus_starblaster.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void naboo_imperial_trainer_2_action_giveQuest2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "survival", "naboo_imperial_tier2_2");
    }
    public void naboo_imperial_trainer_2_action_giveReward2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "survival", "naboo_imperial_tier2_2"))
        {
            space_quest.giveReward(player, "survival", "naboo_imperial_tier2_2", 5000, "object/tangible/ship/components/armor/arm_mission_reward_imperial_sfs_light_military.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void naboo_imperial_trainer_2_action_grantSkill1A(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_starships_02");
    }
    public void naboo_imperial_trainer_2_action_grantSkill1B(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_weapons_02");
    }
    public void naboo_imperial_trainer_2_action_grantSkill1C(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_procedures_02");
    }
    public void naboo_imperial_trainer_2_action_grantSkill1D(obj_id player, obj_id npc) throws InterruptedException
    {
        skill.purchaseSkill(player, "pilot_imperial_navy_droid_02");
    }
    public void naboo_imperial_trainer_2_action_giveReward4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_tier2_4"))
        {
            space_quest.giveReward(player, "assassinate", "naboo_imperial_tier2_4", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_rendili_dual_projector.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public void naboo_imperial_trainer_2_action_giveDuty1A(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "naboo_imperial_tier2_recoveryduty");
    }
    public void naboo_imperial_trainer_2_action_giveDuty1B(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_imperial_tier2_escortduty");
    }
    public void naboo_imperial_trainer_2_action_giveDuty1C(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_imperial_tier2_destroyduty");
    }
    public void naboo_imperial_trainer_2_action_giveQuest3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "naboo_imperial_tier2_3");
    }
    public void naboo_imperial_trainer_2_action_giveQuest4(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "naboo_imperial_tier2_4");
    }
    public void naboo_imperial_trainer_2_action_removeQuest3ObjVar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.removeSpaceFlag(player, "ss.finishedQuest3");
    }
    public void naboo_imperial_trainer_2_action_giveReward3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "recovery", "naboo_imperial_tier2_3"))
        {
            space_quest.giveReward(player, "recovery", "naboo_imperial_tier2_3", 5000, "object/tangible/ship/components/booster/bst_mission_reward_imperial_rss_ion_booster.iff");
            factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        }
    }
    public int naboo_imperial_trainer_2_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2313ac9e"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_c5e2a04a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97222fa4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 5);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
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
    public int naboo_imperial_trainer_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97222fa4"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slit_throat");
                string_id message = new string_id(c_stringFile, "s_3ce9c229");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a7cceea9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a7cceea9"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_34d641c9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f8b02865");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f8b02865"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "snap_finger2");
                string_id message = new string_id(c_stringFile, "s_2a77f19e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ad5c0e0");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 8);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
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
    public int naboo_imperial_trainer_2_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1ad5c0e0"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_9930b065");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4854758d"))
        {
            if (naboo_imperial_trainer_2_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3ead1387");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2a924ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ac71da57");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a2cfa58e");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2a924ab"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6fe80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5102164f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac71da57"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3ba5368f"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f693949d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_959d8d9b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2cf68b3"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b0e9edf4");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_959d8d9b"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4c695dbd");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97d51490"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveReward4(player, npc);
                string_id message = new string_id(c_stringFile, "s_49be19d2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1bac802d");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c104d444"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7be3e248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae26c22");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1bac802d"))
        {
            doAnimationAction(player, "salute1");
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_bc64aa40");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (naboo_imperial_trainer_2_condition_canBuySkill1A(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1B(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1C(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_217");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_224");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae26c22"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b25fbc3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c394eb4"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_306da215");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_846f2737");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_846f2737"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_164b059e");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1f033c46"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d1fa21c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20b3aa70");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 26);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20b3aa70"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest4(player, npc);
                string_id message = new string_id(c_stringFile, "s_e17af774");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2a924ab"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6fe80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5102164f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac71da57"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_96db4fc7"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_196d2fe6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_369ec9fc");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_369ec9fc"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveReward3(player, npc);
                string_id message = new string_id(c_stringFile, "s_85fe2519");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2a924ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ac71da57");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2a924ab"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6fe80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5102164f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac71da57"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f6d4476d"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_825fac62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91e4b620");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91e4b620"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_172871ff");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_51fe08f5"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de870f1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3c9b80ee");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3c9b80ee"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26408ea");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e39f51b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6e39f51b"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b257ddf8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fcff7b8e");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fcff7b8e"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1d63bf5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9b43a7ee");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9b43a7ee"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest3(player, npc);
                string_id message = new string_id(c_stringFile, "s_e1771eb6");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c1bc23b4"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9ac35c60");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae26c22");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (naboo_imperial_trainer_2_condition_canBuySkill1A(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1B(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1C(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_217");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_224");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae26c22"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b25fbc3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch43(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2a924ab"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6fe80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5102164f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac71da57"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5102164f"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b8791e16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45fa29cc");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 45);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45fa29cc"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b2634e3d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e71434ff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e71434ff"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e84f3a62"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveDuty1A(player, npc);
                string_id message = new string_id(c_stringFile, "s_4a2282c8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8efde2ae");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e6765c30"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveDuty1B(player, npc);
                string_id message = new string_id(c_stringFile, "s_dfdec194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 50);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f0a7a4ef"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveDuty1C(player, npc);
                string_id message = new string_id(c_stringFile, "s_79d26fde");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 52);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b0d3b474"))
        {
            if (!naboo_imperial_trainer_2_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2f8399b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_89bc583c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 54);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_213");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ae26c22");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8efde2ae"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_205f33ca");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch50(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fb6ae829");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch52(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4682fc3c");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89bc583c"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49f3270b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_213"))
        {
            if (naboo_imperial_trainer_2_condition_canBuySkill1A(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_214");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1B(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_220");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition_canBuySkill1C(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_217");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_224");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ae26c22"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7b25fbc3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_218"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_grantSkill1A(player, npc);
                string_id message = new string_id(c_stringFile, "s_244");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_246");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_246"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_248");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_220"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_grantSkill1B(player, npc);
                string_id message = new string_id(c_stringFile, "s_238");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_240");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 61);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch61(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_240"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_242");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_222"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_grantSkill1C(player, npc);
                string_id message = new string_id(c_stringFile, "s_232");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_234");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_234"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_236");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_224"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_grantSkill1D(player, npc);
                string_id message = new string_id(c_stringFile, "s_226");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_228");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_228"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_230");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_987445c9"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_78c7dc33");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_130");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 71);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
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
    public int naboo_imperial_trainer_2_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveReward2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b784db84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_133"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2865fd30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d8b8f3ac");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 73);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch73(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d8b8f3ac"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4df61c15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa3398f3");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 74);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch74(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fa3398f3"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_960be58b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91223b5c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_91223b5c"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f85fd827");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2a924ab");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ac71da57");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 43);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch76(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2a924ab"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6a6fe80");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5102164f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 44);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ac71da57"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5b4ccb84");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e84f3a62");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e6765c30");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f0a7a4ef");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0d3b474");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_143"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_964c5567");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_147"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_6ca30e9d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_150");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 80);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch80(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_150"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a5dff683");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c0996c6b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c0996c6b"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2dcd5da9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19fe9586");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19fe9586"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3252d1a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de5b2183");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_de5b2183"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2e7df33d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1980f233");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1980f233"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3b69b91e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c602907a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c602907a"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest2(player, npc);
                string_id message = new string_id(c_stringFile, "s_576f96b8");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch87(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1d5aba18"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f0459fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22a1fc52");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 88);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22a1fc52"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveReward1(player, npc);
                string_id message = new string_id(c_stringFile, "s_ad754");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_168"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_61eac8c");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29d0a2db"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4eef704f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 92);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch92(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_174"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_aceff31e");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c8f39347"))
        {
            doAnimationAction(player, "salute1");
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_41786376");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7c7a7681");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7c7a7681"))
        {
            doAnimationAction(player, "nod_head_once");
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_5960dfeb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d4948205");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d4948205"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "check_wrist_device");
                string_id message = new string_id(c_stringFile, "s_a2137dae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f1be3213");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f1be3213"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f283cf35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_daf7bb7d");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_daf7bb7d"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_199bd27f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_483fa8ea");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 99);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_483fa8ea"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5bb1e2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fed6017c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 100);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch100(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fed6017c"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d2fa677d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32a6fe1c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32a6fe1c"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_giveQuest1(player, npc);
                string_id message = new string_id(c_stringFile, "s_486a2be");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch103(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb972bed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2101d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 104);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ce84ec1"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f0835ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6396820e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ce84ec1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696a6a00");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_696a6a00"))
        {
            if (naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c0ff63d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95dcb85a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecb5e1e2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch104(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b2101d5b"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e2cb470f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6396820e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ce84ec1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696a6a00");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb972bed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2101d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 104);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ce84ec1"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f0835ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6396820e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ce84ec1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696a6a00");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_696a6a00"))
        {
            if (naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c0ff63d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95dcb85a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecb5e1e2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb972bed");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2101d5b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 104);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3ce84ec1"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5f0835ab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6396820e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3ce84ec1");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696a6a00");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 103);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_696a6a00"))
        {
            if (naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7c0ff63d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95dcb85a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 107);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (!naboo_imperial_trainer_2_condition_isHereToSignUp(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ecb5e1e2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch107(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95dcb85a"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8e68e9b0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4d0198f9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4d0198f9"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_860b7132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8f913d8b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 109);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8f913d8b"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_872eaec4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53d26b00");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 110);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch110(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53d26b00"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_2_action_makeInquisitorialAgent(player, npc);
                string_id message = new string_id(c_stringFile, "s_131def5c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6a9a7be2");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 111);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_2_handleBranch111(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6a9a7be2"))
        {
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50f84d04");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
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
            detachScript(self, "conversation.naboo_imperial_trainer_2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Inquisitor Holfheim");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Inquisitor Holfheim");
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
        detachScript(self, "conversation.naboo_imperial_trainer_2");
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
        if (!naboo_imperial_trainer_2_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_21772e5a");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isRebel(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isPrivateer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_245945cb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2313ac9e");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 4);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_trainer_2_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1c006c1c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 10);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isOnMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a59c7bd7");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isWorkingForTrent(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_69ea2753");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3ba5368f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2cf68b3");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 14);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasWonQuest4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a08c6dc4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition_hasAllSkills(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!naboo_imperial_trainer_2_condition_hasAllSkills(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_97d51490");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c104d444");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 18);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasFailedQuest4(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_681fb295");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9c394eb4");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 22);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isReadyForMission4(player, npc))
        {
            naboo_imperial_trainer_2_action_removeQuest3ObjVar(player, npc);
            string_id message = new string_id(c_stringFile, "s_e1b07878");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1f033c46");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 25);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasWonQuest3A(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_9962665d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b2a924ab");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ac71da57");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 43);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasWonQuest3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_2ccef947");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96db4fc7");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 29);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasFailedQuest3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ff5a78b2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f6d4476d");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 32);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isReadyForMission3(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6309f032");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51fe08f5");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 35);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", null, pp, responses);
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
        if (naboo_imperial_trainer_2_condition_isReadyForTraining(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_883a28d9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1bc23b4");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 41);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isReadyForDutyBlock1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_be89481");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b2a924ab");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ac71da57");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 43);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasWonQuest2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_32701355");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_987445c9");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 70);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasFailedQuest2(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_c50d6541");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 77);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isReadyForMission2(player, npc))
        {
            doAnimationAction(npc, "point_forward");
            string_id message = new string_id(c_stringFile, "s_7f95f0a5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_147");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 79);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_hasWonQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_f91e446d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1d5aba18");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 87);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", null, pp, responses);
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
        if (naboo_imperial_trainer_2_condition_hasFailedQuest1(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_6e9c09c4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29d0a2db");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 91);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition_isReadyForMission1(player, npc))
        {
            doAnimationAction(npc, "salute1");
            string_id message = new string_id(c_stringFile, "s_1f0f791f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c8f39347");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 94);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_26cb713a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6396820e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3ce84ec1");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_696a6a00");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId", 103);
                npcStartConversation(player, npc, "naboo_imperial_trainer_2", message, responses);
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
        if (!conversationId.equals("naboo_imperial_trainer_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
        if (branchId == 4 && naboo_imperial_trainer_2_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && naboo_imperial_trainer_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && naboo_imperial_trainer_2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && naboo_imperial_trainer_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && naboo_imperial_trainer_2_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && naboo_imperial_trainer_2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && naboo_imperial_trainer_2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_imperial_trainer_2_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && naboo_imperial_trainer_2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && naboo_imperial_trainer_2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && naboo_imperial_trainer_2_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_imperial_trainer_2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_imperial_trainer_2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_imperial_trainer_2_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && naboo_imperial_trainer_2_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && naboo_imperial_trainer_2_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && naboo_imperial_trainer_2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && naboo_imperial_trainer_2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && naboo_imperial_trainer_2_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && naboo_imperial_trainer_2_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && naboo_imperial_trainer_2_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && naboo_imperial_trainer_2_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && naboo_imperial_trainer_2_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && naboo_imperial_trainer_2_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && naboo_imperial_trainer_2_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && naboo_imperial_trainer_2_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && naboo_imperial_trainer_2_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && naboo_imperial_trainer_2_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && naboo_imperial_trainer_2_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 43 && naboo_imperial_trainer_2_handleBranch43(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && naboo_imperial_trainer_2_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && naboo_imperial_trainer_2_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && naboo_imperial_trainer_2_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && naboo_imperial_trainer_2_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && naboo_imperial_trainer_2_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 50 && naboo_imperial_trainer_2_handleBranch50(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 52 && naboo_imperial_trainer_2_handleBranch52(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && naboo_imperial_trainer_2_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && naboo_imperial_trainer_2_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && naboo_imperial_trainer_2_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && naboo_imperial_trainer_2_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && naboo_imperial_trainer_2_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 61 && naboo_imperial_trainer_2_handleBranch61(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && naboo_imperial_trainer_2_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && naboo_imperial_trainer_2_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && naboo_imperial_trainer_2_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && naboo_imperial_trainer_2_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && naboo_imperial_trainer_2_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && naboo_imperial_trainer_2_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && naboo_imperial_trainer_2_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 73 && naboo_imperial_trainer_2_handleBranch73(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 74 && naboo_imperial_trainer_2_handleBranch74(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && naboo_imperial_trainer_2_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 76 && naboo_imperial_trainer_2_handleBranch76(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && naboo_imperial_trainer_2_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && naboo_imperial_trainer_2_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 80 && naboo_imperial_trainer_2_handleBranch80(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && naboo_imperial_trainer_2_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && naboo_imperial_trainer_2_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && naboo_imperial_trainer_2_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && naboo_imperial_trainer_2_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && naboo_imperial_trainer_2_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 87 && naboo_imperial_trainer_2_handleBranch87(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && naboo_imperial_trainer_2_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && naboo_imperial_trainer_2_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && naboo_imperial_trainer_2_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 92 && naboo_imperial_trainer_2_handleBranch92(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && naboo_imperial_trainer_2_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && naboo_imperial_trainer_2_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && naboo_imperial_trainer_2_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && naboo_imperial_trainer_2_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && naboo_imperial_trainer_2_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && naboo_imperial_trainer_2_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 100 && naboo_imperial_trainer_2_handleBranch100(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && naboo_imperial_trainer_2_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 103 && naboo_imperial_trainer_2_handleBranch103(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 104 && naboo_imperial_trainer_2_handleBranch104(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && naboo_imperial_trainer_2_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && naboo_imperial_trainer_2_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 107 && naboo_imperial_trainer_2_handleBranch107(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && naboo_imperial_trainer_2_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && naboo_imperial_trainer_2_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 110 && naboo_imperial_trainer_2_handleBranch110(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 111 && naboo_imperial_trainer_2_handleBranch111(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
