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

public class lok_rebel_trainer_2 extends script.base_script
{
    public lok_rebel_trainer_2()
    {
    }
    public static String c_stringFile = "conversation/lok_rebel_trainer_2";
    public boolean lok_rebel_trainer_2_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lok_rebel_trainer_2_condition_readyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((hasSkill(player, "pilot_rebel_navy_starships_02") || hasSkill(player, "pilot_rebel_navy_weapons_02") || hasSkill(player, "pilot_rebel_navy_procedures_02") || hasSkill(player, "pilot_rebel_navy_droid_02")) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasWonQuest(player, "inspect", "viopa_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasFailedMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuestRecursive(player, "inspect", "viopa_rebel_1") || space_quest.hasAbortedQuestRecursive(player, "inspect", "viopa_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasWonMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasCompletedQuestRecursive(player, "inspect", "viopa_rebel_1") && !space_quest.hasReceivedReward(player, "inspect", "viopa_rebel_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_readyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_02",
            "pilot_rebel_navy_weapons_02",
            "pilot_rebel_navy_procedures_02",
            "pilot_rebel_navy_droid_02"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 2) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasWonQuest(player, "escort", "viopa_rebel_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasWonMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "escort", "viopa_rebel_2") && !space_quest.hasReceivedReward(player, "escort", "viopa_rebel_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasFailedMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "escort", "viopa_rebel_2") || space_quest.hasAbortedQuest(player, "escort", "viopa_rebel_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player, "inspect", "viopa_rebel_1") || space_quest.hasQuest(player, "escort", "viopa_rebel_2") || space_quest.hasQuest(player, "recovery", "viopa_rebel_3") || space_quest.hasQuest(player, "destroy", "viopa_rebel_4") || space_quest.hasQuest(player, "escort_duty", "viopa_rebel_5") || space_quest.hasQuest(player, "destroy_duty", "corellia_imperial_6") || space_quest.hasQuest(player, "recovery_duty", "viopa_rebel_7"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_readyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_02",
            "pilot_rebel_navy_weapons_02",
            "pilot_rebel_navy_procedures_02",
            "pilot_rebel_navy_droid_02"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 3) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasWonQuest(player, "recovery", "viopa_rebel_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasFailedMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "recovery", "viopa_rebel_3") || space_quest.hasAbortedQuest(player, "recovery", "viopa_rebel_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasWonMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "recovery", "viopa_rebel_3") && !space_quest.hasReceivedReward(player, "recovery", "viopa_rebel_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_readyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] skills = 
        {
            "pilot_rebel_navy_starships_02",
            "pilot_rebel_navy_weapons_02",
            "pilot_rebel_navy_procedures_02",
            "pilot_rebel_navy_droid_02"
        };
        int j = 0;
        for (int i = 0; i < skills.length; i++)
        {
            if (hasSkill(player, skills[i]))
            {
                j++;
            }
        }
        if ((j == 4) && space_flags.hasSpaceFlag(player, "viopaPilot") && !space_quest.hasWonQuest(player, "assassinate", "viopa_rebel_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasWonMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "viopa_rebel_4") && !space_quest.hasReceivedReward(player, "assassinate", "viopa_rebel_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasFailedMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "viopa_rebel_4") || space_quest.hasAbortedQuest(player, "assassinate", "viopa_rebel_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_readyForDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaPilot") == 2)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_readyForSecondSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_starships_02") && !hasSkill(player, "pilot_rebel_navy_weapons_02") && !hasSkill(player, "pilot_rebel_navy_procedures_02") && !hasSkill(player, "pilot_rebel_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_canBuyStarships02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_starships_02") && space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_starships_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_canBuyWeapons02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_weapons_02") && space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_weapons_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_canBuyProcedures02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_procedures_02") && space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_procedures_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_canBuyDroids02(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_rebel_navy_droid_02") && space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_droid_02"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_canBuySkill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_starships_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_weapons_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_procedures_02") || space_quest.isPlayerQualifiedForSkill(player, "pilot_rebel_navy_droid_02") && space_flags.hasSpaceFlag(player, "viopaPilot"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lok_rebel_trainer_2_condition_hasAllSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "viopa_rebel_4") && space_flags.hasCompletedTierTwo(player))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_hasCompletedSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuestSeries(player, "imperial_corellia_1");
    }
    public boolean lok_rebel_trainer_2_condition_isGm(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasObjVar(player, "gm"));
    }
    public boolean lok_rebel_trainer_2_condition_isNeutralPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_neutral_novice"))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_isRebelNonMember(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice") && !space_flags.hasSpaceFlag(player, "viopaPilot"));
    }
    public boolean lok_rebel_trainer_2_condition_notTalkedSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 1)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_isImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        String playerFaction = factions.getFaction(player);
        if (playerFaction == null)
        {
            return hasSkill(player, "pilot_imperial_navy_novice");
        }
        if (playerFaction.equals("Imperial") || hasSkill(player, "pilot_imperial_navy_novice"))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_isCorrectRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && (space_flags.getIntSpaceFlag(player, "viopaPilot") == 1))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_talkedSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 2)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_notTalkedSmuggler2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 3)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_talkedSmuggler2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.getIntSpaceFlag(player, "viopaSmuggler") == 4)
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_readyForTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierTwo(player) && space_flags.hasSpaceFlag(player, "viopaPilot") && space_quest.hasReceivedReward(player, "assassinate", "viopa_rebel_4"))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean lok_rebel_trainer_2_condition_onMyTrack(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.isSpaceTrack(player, space_flags.REBEL_CORELLIA);
    }
    public boolean lok_rebel_trainer_2_condition_isPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasAnyPilotSkill(player);
    }
    public boolean lok_rebel_trainer_2_condition_isOnAnotherMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasQuest(player);
    }
    public boolean lok_rebel_trainer_2_condition_hasNegativeFaction(obj_id player, obj_id npc) throws InterruptedException
    {
        return (factions.getFactionStanding(player, factions.FACTION_REBEL) < 0.0f);
    }
    public void lok_rebel_trainer_2_action_grantMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.grantQuest(player, "inspect", "viopa_rebel_1");
    }
    public void lok_rebel_trainer_2_action_goToSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "viopaSmuggler", 1);
    }
    public void lok_rebel_trainer_2_action_giveRewardMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "inspect", "viopa_rebel_1"))
        {
            return;
        }
        faceTo(npc, player);
        space_quest.giveReward(player, "inspect", "viopa_rebel_1", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_rebel_incom_k77.iff");
        factions.addFactionStanding(player, factions.FACTION_REBEL, 75.0f);
    }
    public void lok_rebel_trainer_2_action_removeSmuggler(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.removeSpaceFlag(player, "viopaSmuggler");
    }
    public void lok_rebel_trainer_2_action_goToSmuggler2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "viopaSmuggler", 3);
    }
    public void lok_rebel_trainer_2_action_grantMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.grantQuest(player, "escort", "viopa_rebel_2");
    }
    public void lok_rebel_trainer_2_action_giveRewardMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "escort", "viopa_rebel_2"))
        {
            return;
        }
        faceTo(npc, player);
        space_quest.giveReward(player, "escort", "viopa_rebel_2", 5000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_rebel_moncal_d22.iff");
        factions.addFactionStanding(player, factions.FACTION_REBEL, 75.0f);
    }
    public void lok_rebel_trainer_2_action_grantMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.grantQuest(player, "recovery", "viopa_rebel_3");
    }
    public void lok_rebel_trainer_2_action_giveRewardMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "recovery", "viopa_rebel_3"))
        {
            return;
        }
        faceTo(npc, player);
        space_quest.giveReward(player, "recovery", "viopa_rebel_3", 5000, "object/tangible/ship/components/booster/bst_mission_reward_rebel_novaldex_hypernova.iff");
        factions.addFactionStanding(player, factions.FACTION_REBEL, 75.0f);
    }
    public void lok_rebel_trainer_2_action_grantMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.grantQuest(player, "assassinate", "viopa_rebel_4");
    }
    public void lok_rebel_trainer_2_action_giveRewardMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "viopa_rebel_4"))
        {
            return;
        }
        faceTo(npc, player);
        space_quest.giveReward(player, "assassinate", "viopa_rebel_4", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_rebel_taim_ion_driver.iff");
        space_flags.setSpaceFlag(player, "viopaPilot", 3);
        factions.addFactionStanding(player, factions.FACTION_REBEL, 75.0f);
    }
    public void lok_rebel_trainer_2_action_buyStarships02(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        skill.purchaseSkill(player, "pilot_rebel_navy_starships_02");
    }
    public void lok_rebel_trainer_2_action_buyWeapons02(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        skill.purchaseSkill(player, "pilot_rebel_navy_weapons_02");
    }
    public void lok_rebel_trainer_2_action_buyProcedures02(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        skill.purchaseSkill(player, "pilot_rebel_navy_procedures_02");
    }
    public void lok_rebel_trainer_2_action_buyDroid02(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        skill.purchaseSkill(player, "pilot_rebel_navy_droid_02");
    }
    public void lok_rebel_trainer_2_action_flagQuestSeriesComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        space_quest.setQuestSeriesFlag(player, "imperial_corellia_1", space_quest.QUEST_SERIES_COMPLETED);
    }
    public void lok_rebel_trainer_2_action_grantDestroyDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "viopa_rebel_6");
    }
    public void lok_rebel_trainer_2_action_grantRecoveryDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "viopa_rebel_7");
    }
    public void lok_rebel_trainer_2_action_grantEscortDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "viopa_rebel_5");
    }
    public void lok_rebel_trainer_2_action_removeViopaPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.removeSpaceFlag(player, "viopaPilot");
    }
    public void lok_rebel_trainer_2_action_face(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
    }
    public void lok_rebel_trainer_2_action_makeViopaPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceFlag(player, "viopaPilot", 2);
    }
    public String lok_rebel_trainer_2_tokenTO_tokenTO0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int lok_rebel_trainer_2_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e00ac98e"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "weeping");
                string_id message = new string_id(c_stringFile, "s_dd8c097b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a78a4ac3");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a78a4ac3"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_fa69f8de");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fcf4d778");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fcf4d778"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "cover_eyes");
                string_id message = new string_id(c_stringFile, "s_2817ec90");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1583743c"))
        {
            if (lok_rebel_trainer_2_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9d922723");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e87a40f8");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_990019e5");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e84d85e2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e87a40f8"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39e697de");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch15(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14310e04"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                string_id message = new string_id(c_stringFile, "s_4acb55cd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e811b51e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41ee99fd");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3892fde6"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_4ca03ddb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97682590");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b3f9c754");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e811b51e"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_c4ecbd2f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_902e7f6f");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_41ee99fd"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_6280da8c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_232c0049");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_94558476");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_902e7f6f"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_a3de93c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e2b5ea1a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1813f9b");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e2b5ea1a"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                string_id message = new string_id(c_stringFile, "s_54e06202");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1813f9b"))
        {
            doAnimationAction(player, "salute1");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_5b7c65e8");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_232c0049"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a1062586");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c8427191");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1f9f1bc");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_94558476"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_ffa35f86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_359d1c6c");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c8427191"))
        {
            doAnimationAction(player, "salute1");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_62d83905");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b1f9f1bc"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                string_id message = new string_id(c_stringFile, "s_47");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_359d1c6c"))
        {
            doAnimationAction(player, "salute2");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97682590"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_55");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fde7cbe6");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b3f9c754"))
        {
            doAnimationAction(player, "explain");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_b2e9f5b5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_105c0da2");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_57"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_59");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_65");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_fde7cbe6"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_a4ab00c9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61"))
        {
            doAnimationAction(player, "nod_head_once");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_63");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_67");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_73");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "salute2");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_105c0da2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_82");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_96");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_84"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_96"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_98");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_100");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 41);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_88"))
        {
            doAnimationAction(player, "nod_head_once");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_90");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_92"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_94");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_100"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_d64bce73");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_103"))
        {
            doAnimationAction(player, "salute2");
            lok_rebel_trainer_2_action_removeSmuggler(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_105");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e4a658"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_f4cf8776");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3209cb6f"))
        {
            doAnimationAction(player, "smack_self");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                string_id message = new string_id(c_stringFile, "s_c149497f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e28b657");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bfd9eed6");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5e28b657"))
        {
            doAnimationAction(player, "embarrassed");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_c9d58564");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_55ad580f");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ac3c20d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bfd9eed6"))
        {
            doAnimationAction(player, "slow_down");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_93c107c0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_126");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_55ad580f"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_66c9d146");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5ac3c20d"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ccd0caab");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_25fcc7b2");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_25fcc7b2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_5ee66c06");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_122"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_126"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_128");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_130"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_132");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_137a1cc5"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_6bcf16a1");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_809dbad9"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_1eaa0d45");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ef962200"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_d5693494");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch59(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4bf53cea"))
        {
            doAnimationAction(player, "bow3");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                lok_rebel_trainer_2_action_giveRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_71350221");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1d34c320"))
        {
            doAnimationAction(player, "hair_flip");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                lok_rebel_trainer_2_action_giveRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_6c130469");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95a207fe"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                string_id message = new string_id(c_stringFile, "s_d70d3929");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c84e7a8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d14eedc8");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_eb569089"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ba9fbf52");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6d2881d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35e906c1");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 66);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38aeb877"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_6bb4b597");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c84e7a8"))
        {
            doAnimationAction(player, "shrug_hands");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_6c7c317");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97f6ca14");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ccdbfbb");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d14eedc8"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler(player, npc);
                string_id message = new string_id(c_stringFile, "s_cafeab9");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97f6ca14"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler(player, npc);
                string_id message = new string_id(c_stringFile, "s_cafeab9");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2ccdbfbb"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_1dcfe231");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch66(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d6d2881d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8204eb26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e6242ff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b59feb8d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 67);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35e906c1"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler(player, npc);
                string_id message = new string_id(c_stringFile, "s_92199b82");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch67(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6e6242ff"))
        {
            doAnimationAction(player, "belly_laugh");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler(player, npc);
                string_id message = new string_id(c_stringFile, "s_cafeab9");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b59feb8d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_194e698d");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_307dbfe0"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "celebrate");
                doAnimationAction(player, "salute1");
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_e67149f");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6e92867a"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_af173f89");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ce671a71"))
        {
            doAnimationAction(player, "bow");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_eceafc6e");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27a9d3e4"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                lok_rebel_trainer_2_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_14791132");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da77c355");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e2ad7a0d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 77);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_da77c355"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e2ad7a0d"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                string_id message = new string_id(c_stringFile, "s_5164f707");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch79(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c8c2d2ea"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_disgust");
                string_id message = new string_id(c_stringFile, "s_163272c2");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c6b08612"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e7b6dca1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e0d2df48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54179ba5");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 81);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e0d2df48"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10221765");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_74a4f762");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db82fc61");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54179ba5"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_201");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 89);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_74a4f762"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3ae0bba3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e9e1660");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_56285e36");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db82fc61"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bbb72231");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5e9e1660"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_72b581a2");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56285e36"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3e303b17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a99ad4e8");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c942a6c8");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 85);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch85(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a99ad4e8"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "accept_affection");
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_723b4288");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c942a6c8"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_196");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_203"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_205");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_207"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_209");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_211");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_215");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_211"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "accept_affection");
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_213");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_215"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_217");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch94(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e4b3375d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "sigh_deeply");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_5300757c");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_222"))
        {
            doAnimationAction(player, "smack_self");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                string_id message = new string_id(c_stringFile, "s_224");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_226");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_226"))
        {
            doAnimationAction(player, "embarrassed");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_71a5deee");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_229"))
        {
            doAnimationAction(player, "slow_down");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9c5793bd");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1d85385d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_c13b1925");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6e943bb6"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_excited");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_11ba5f1a");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch102(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dcec6e65"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "nod_head_once");
                lok_rebel_trainer_2_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_44170670");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4a44213c"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_4af0c602");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a333c9dd"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "refuse_offer_affection");
                doAnimationAction(player, "offer_affection");
                string_id message = new string_id(c_stringFile, "s_a043fbc6");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_668e43a2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "weeping");
                string_id message = new string_id(c_stringFile, "s_fda95b05");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_66418240"))
        {
            doAnimationAction(player, "salute1");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_75e5035c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_531fb93c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9b2edf50");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 109);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_aa3d7ff6"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_a65cd6cc");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c100d375"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_933e5758");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1358322c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8cf1532");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 113);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch109(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_531fb93c"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nervous");
                string_id message = new string_id(c_stringFile, "s_b1cac9ab");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9b2edf50"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "offer_affection");
                doAnimationAction(player, "accept_affection");
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_b24f64b");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1358322c"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_c0422a2");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b8cf1532"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "gesticulate_wildly");
                doAnimationAction(player, "slow_down");
                string_id message = new string_id(c_stringFile, "s_b838d4b8");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5be79320"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2d07d661");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80b2d7f9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d17c64f9");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_697b5183"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_e4daa04e");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cf884764"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_5064a71e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38f4aa90");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb3507f");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 121);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch117(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80b2d7f9"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_c532ad1c");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d17c64f9"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_a98b1cf0");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch121(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38f4aa90"))
        {
            doAnimationAction(player, "slit_throat");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_fdc272e0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dce7fb");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cb3507f"))
        {
            doAnimationAction(player, "nervous");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "wave1");
                string_id message = new string_id(c_stringFile, "s_f4021854");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch122(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dce7fb"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_goToSmuggler2(player, npc);
                string_id message = new string_id(c_stringFile, "s_386d56a1");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_db9d43d"))
        {
            doAnimationAction(player, "embarrassed");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_826d3c9d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6ab7832d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6258192a");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 126);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6fad62ec"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "kiss_blow_kiss");
                string_id message = new string_id(c_stringFile, "s_ad5cafda");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch126(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6ab7832d"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_6752d582");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e565e3d3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_faf8066c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dc7c029d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 127);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6258192a"))
        {
            doAnimationAction(player, "goodbye");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_43fbe907");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9b82774b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0b37ad2");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 131);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch127(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e565e3d3"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_faf8066c"))
        {
            doAnimationAction(player, "dismiss");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_122e5dd7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4212435c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27101a28");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 128);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dc7c029d"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_fbef6556");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch128(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4212435c"))
        {
            doAnimationAction(player, "shrug_hands");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27101a28"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_746fda15");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch131(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9b82774b"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_f828b1ef");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d0b37ad2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c24c239"))
        {
            doAnimationAction(player, "pound_fist_palm");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_960afa57");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b862718f"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(player, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_6a43381d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c3e498de");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79f026e1");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 136);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch136(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c3e498de"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_3769a087");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79f026e1"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_up");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_cbd8ba35");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch139(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3a42c050"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c91bccf0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d08d9709");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2683b4a");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_65316548"))
        {
            doAnimationAction(player, "sigh_deeply");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_f182a09c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc4b144b");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 145);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21e0c701"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ae5a02a8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f1de2910");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 146);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e9c1932"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_b3a90c9");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch140(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d08d9709"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b9f9d416");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_16d870fe");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_780265b8");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 141);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b2683b4a"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "kiss_blow_kiss");
                doAnimationAction(player, "embarrassed");
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_a3790fac");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch141(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16d870fe"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "kiss_blow_kiss");
                doAnimationAction(player, "accept_affection");
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_507b9d02");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_780265b8"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_2c95410e");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch145(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc4b144b"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c91bccf0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d08d9709");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2683b4a");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch146(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f1de2910"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c91bccf0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d08d9709");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b2683b4a");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 140);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch148(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_95cc4d4e"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lok_rebel_trainer_2_condition_canBuyStarships02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_511");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyWeapons02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_520");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyProcedures02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_513");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_514");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f0a6ba8"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_eaf03b41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a6e1c30a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_42b39212");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 157);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dede1eae"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                string_id message = new string_id(c_stringFile, "s_b3a81c54");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ca7b758d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45bb907e");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 158);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c1c28382"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_9199b1bf");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch149(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_516"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                lok_rebel_trainer_2_action_buyStarships02(player, npc);
                string_id message = new string_id(c_stringFile, "s_518");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch151(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_520"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                lok_rebel_trainer_2_action_buyWeapons02(player, npc);
                string_id message = new string_id(c_stringFile, "s_522");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_524"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                lok_rebel_trainer_2_action_buyProcedures02(player, npc);
                string_id message = new string_id(c_stringFile, "s_526");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch155(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_528"))
        {
            doAnimationAction(player, "nod_head_multiple");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                lok_rebel_trainer_2_action_buyDroid02(player, npc);
                string_id message = new string_id(c_stringFile, "s_530");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch157(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a6e1c30a"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lok_rebel_trainer_2_condition_canBuyStarships02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_511");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyWeapons02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_520");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyProcedures02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_513");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_514");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_42b39212"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch158(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ca7b758d"))
        {
            if (lok_rebel_trainer_2_condition_canBuyStarships02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_511");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 149);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyWeapons02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_512");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_520");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 151);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition_canBuyProcedures02(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_513");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_514");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_528");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_45bb907e"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch160(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_366"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20b704e7"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_378");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8be640b8"))
        {
            doAnimationAction(player, "nod_head_once");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_7577882");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_382");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_385");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_391");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 167);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6b158a0e"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e53c5017");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_165db597");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64b6ea44");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b6af2b98");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_816055e2");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_954c43bb");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ec5b15d1");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_91156160");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch161(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e02126d9"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantRecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_a0ee0d37");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cd3e2b38"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_8447c7d7");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1dfeca09"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_ca434bbe");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cd1a5817"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_17653741");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch167(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_382"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantRecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_2af8de7c");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_385"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_7977cca8");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_388"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "laugh_titter");
                doAnimationAction(player, "goodbye");
                lok_rebel_trainer_2_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_596a74ca");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_391"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "bow3");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_14183b8e");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch172(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_165db597"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_8c839067");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_64b6ea44"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_37e5c8bc");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b6af2b98"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_e6569c04");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_816055e2"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_99210986");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_954c43bb"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_f3cbd30c");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ec5b15d1"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_407");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_91156160"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantRecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_410");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch180(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_65f334d5"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e58d7043");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7ac528cc");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9990d560");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 181);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_334b8b3a"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_438969d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f6927823");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1ec75f6d");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 185);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8125c052"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50dbe32c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1df58e48");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e2539e81");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 188);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d418971"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_5c191b57");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_443"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_445");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_447");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_451");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_455");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_459");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_463");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_467");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_471");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 192);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch181(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7ac528cc"))
        {
            doAnimationAction(player, "salute2");
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9990d560"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_129e1011");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97bbc2e4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_db065042");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 182);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch182(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97bbc2e4"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_db065042"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62fa8365");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c103ad7e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6f65083");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 183);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch183(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c103ad7e"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c6f65083"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bc44d715");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch185(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f6927823"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44dd253a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d21eee5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_429");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 186);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1ec75f6d"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch186(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d21eee5e"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_429"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7348150");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch188(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1df58e48"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e2539e81"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bcff0c86");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17e79a2c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98672851");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 189);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch189(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17e79a2c"))
        {
            lok_rebel_trainer_2_action_makeViopaPilot(player, npc);
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_565951d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e02126d9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd3e2b38");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1dfeca09");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd1a5817");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98672851"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                doAnimationAction(player, "goodbye");
                string_id message = new string_id(c_stringFile, "s_b0d973e8");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
    public int lok_rebel_trainer_2_handleBranch192(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_447"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_449");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_451"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_453");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_455"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_457");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_459"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_461");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_463"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_465");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_467"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_469");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_471"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantRecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_473");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch200(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_476"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_478");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_484");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_488");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_492");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_496");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_500");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_504");
                    }
                    utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 201);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d70dba34"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b02ee4b4");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b125bfdc");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lok_rebel_trainer_2_handleBranch201(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_480"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_482");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_484"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_486");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_488"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_490");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_492"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_494");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_496"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_498");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_500"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_502");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_504"))
        {
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                lok_rebel_trainer_2_action_grantRecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_506");
                utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
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
            detachScript(self, "conversation.lok_rebel_trainer_2");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Lady Viopa");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Lady Viopa");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_REBEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.lok_rebel_trainer_2");
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
        if (!lok_rebel_trainer_2_condition_isPilot(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14fcd38b");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isImperial(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_ab3a1cf4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isNeutralPilot(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_422f9bfa");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isOnMission(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_2baec20");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_hasNegativeFaction(player, npc))
        {
            doAnimationAction(npc, "cover_mouth");
            string_id message = new string_id(c_stringFile, "s_4104f818");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e00ac98e");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 5);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isOnAnotherMission(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_d356f8d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (!lok_rebel_trainer_2_condition_onMyTrack(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_148ee1c2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1583743c");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 10);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isRebelNonMember(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_b3a02be2");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(npc);
            chat.chat(npc, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_talkedSmuggler(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_12b3896");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14310e04");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3892fde6");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 15);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_notTalkedSmuggler(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_d0299d36");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3e4a658");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3209cb6f");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 44);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasFailedMission2(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_3406294f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_137a1cc5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_809dbad9");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ef962200");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 55);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasWonMission2(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_a3c6ae82");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4bf53cea");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1d34c320");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 59);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_readyForMission2(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_b2335add");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95a207fe");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_eb569089");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38aeb877");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 62);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasFailedMission3(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_e57d94c7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_307dbfe0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6e92867a");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 72);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_hasWonMission3(player, npc))
        {
            doAnimationAction(npc, "kiss_blow_kiss");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_9417b6d9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ce671a71");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_27a9d3e4");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 75);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_readyForMission3(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_e942aa77");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c8c2d2ea");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c6b08612");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 79);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_notTalkedSmuggler2(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_64370a0d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e4b3375d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_222");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 94);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasFailedMission4(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            doAnimationAction(player, "embarrassed");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_bbed8565");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1d85385d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6e943bb6");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 99);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasWonMission4(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_662d6c41");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dcec6e65");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a44213c");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 102);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_readyForTier3(player, npc))
        {
            doAnimationAction(npc, "greet");
            doAnimationAction(player, "bow3");
            string_id message = new string_id(c_stringFile, "s_5884fe0d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a333c9dd");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_668e43a2");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 105);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_talkedSmuggler2(player, npc))
        {
            doAnimationAction(npc, "nervous");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_506ee957");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_66418240");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_aa3d7ff6");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c100d375");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 108);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_readyForMission4(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_106f742d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5be79320");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_697b5183");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cf884764");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 116);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasWonMission1(player, npc))
        {
            doAnimationAction(npc, "celebrate");
            doAnimationAction(player, "wave1");
            lok_rebel_trainer_2_action_giveRewardMission1(player, npc);
            string_id message = new string_id(c_stringFile, "s_61e761a4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_db9d43d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6fad62ec");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 125);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_hasFailedMission1(player, npc))
        {
            doAnimationAction(npc, "offer_affection");
            doAnimationAction(player, "embarrassed");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_1cb5d5fd");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9c24c239");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b862718f");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 134);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_readyForMission1(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "wave1");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_e8a92b79");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a42c050");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65316548");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_21e0c701");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e9c1932");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 139);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_canBuySkill(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_53e43cdb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_95cc4d4e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2f0a6ba8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_dede1eae");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c1c28382");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 148);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition_readyForDuty(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "greet");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_5aa92dc4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lok_rebel_trainer_2_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_366");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_20b704e7");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8be640b8");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6b158a0e");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 160);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lok_rebel_trainer_2_condition_isCorrectRebelPilot(player, npc))
        {
            doAnimationAction(npc, "bow3");
            doAnimationAction(player, "wave1");
            lok_rebel_trainer_2_action_face(player, npc);
            string_id message = new string_id(c_stringFile, "s_15250124");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (lok_rebel_trainer_2_condition_isGm(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_65f334d5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_334b8b3a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8125c052");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9d418971");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_443");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 180);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", null, pp, responses);
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
        if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_14123c8b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lok_rebel_trainer_2_condition_isGm(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lok_rebel_trainer_2_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_476");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                }
                utils.setScriptVar(player, "conversation.lok_rebel_trainer_2.branchId", 200);
                npcStartConversation(player, npc, "lok_rebel_trainer_2", message, responses);
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
        if (!conversationId.equals("lok_rebel_trainer_2"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
        if (branchId == 5 && lok_rebel_trainer_2_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && lok_rebel_trainer_2_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lok_rebel_trainer_2_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && lok_rebel_trainer_2_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && lok_rebel_trainer_2_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && lok_rebel_trainer_2_handleBranch15(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && lok_rebel_trainer_2_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && lok_rebel_trainer_2_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && lok_rebel_trainer_2_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && lok_rebel_trainer_2_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && lok_rebel_trainer_2_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && lok_rebel_trainer_2_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && lok_rebel_trainer_2_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && lok_rebel_trainer_2_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && lok_rebel_trainer_2_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && lok_rebel_trainer_2_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && lok_rebel_trainer_2_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && lok_rebel_trainer_2_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && lok_rebel_trainer_2_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && lok_rebel_trainer_2_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && lok_rebel_trainer_2_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && lok_rebel_trainer_2_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && lok_rebel_trainer_2_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && lok_rebel_trainer_2_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && lok_rebel_trainer_2_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && lok_rebel_trainer_2_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && lok_rebel_trainer_2_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 59 && lok_rebel_trainer_2_handleBranch59(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && lok_rebel_trainer_2_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && lok_rebel_trainer_2_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && lok_rebel_trainer_2_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 66 && lok_rebel_trainer_2_handleBranch66(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 67 && lok_rebel_trainer_2_handleBranch67(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && lok_rebel_trainer_2_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && lok_rebel_trainer_2_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && lok_rebel_trainer_2_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 79 && lok_rebel_trainer_2_handleBranch79(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && lok_rebel_trainer_2_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && lok_rebel_trainer_2_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && lok_rebel_trainer_2_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 85 && lok_rebel_trainer_2_handleBranch85(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && lok_rebel_trainer_2_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && lok_rebel_trainer_2_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 94 && lok_rebel_trainer_2_handleBranch94(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && lok_rebel_trainer_2_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && lok_rebel_trainer_2_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 102 && lok_rebel_trainer_2_handleBranch102(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && lok_rebel_trainer_2_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && lok_rebel_trainer_2_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 109 && lok_rebel_trainer_2_handleBranch109(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && lok_rebel_trainer_2_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && lok_rebel_trainer_2_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 117 && lok_rebel_trainer_2_handleBranch117(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 121 && lok_rebel_trainer_2_handleBranch121(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 122 && lok_rebel_trainer_2_handleBranch122(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && lok_rebel_trainer_2_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 126 && lok_rebel_trainer_2_handleBranch126(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 127 && lok_rebel_trainer_2_handleBranch127(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 128 && lok_rebel_trainer_2_handleBranch128(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 131 && lok_rebel_trainer_2_handleBranch131(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && lok_rebel_trainer_2_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 136 && lok_rebel_trainer_2_handleBranch136(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 139 && lok_rebel_trainer_2_handleBranch139(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 140 && lok_rebel_trainer_2_handleBranch140(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 141 && lok_rebel_trainer_2_handleBranch141(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 145 && lok_rebel_trainer_2_handleBranch145(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 146 && lok_rebel_trainer_2_handleBranch146(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 148 && lok_rebel_trainer_2_handleBranch148(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 149 && lok_rebel_trainer_2_handleBranch149(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 151 && lok_rebel_trainer_2_handleBranch151(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && lok_rebel_trainer_2_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 155 && lok_rebel_trainer_2_handleBranch155(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 157 && lok_rebel_trainer_2_handleBranch157(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 158 && lok_rebel_trainer_2_handleBranch158(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 160 && lok_rebel_trainer_2_handleBranch160(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 161 && lok_rebel_trainer_2_handleBranch161(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 167 && lok_rebel_trainer_2_handleBranch167(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 172 && lok_rebel_trainer_2_handleBranch172(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 180 && lok_rebel_trainer_2_handleBranch180(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 181 && lok_rebel_trainer_2_handleBranch181(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 182 && lok_rebel_trainer_2_handleBranch182(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 183 && lok_rebel_trainer_2_handleBranch183(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 185 && lok_rebel_trainer_2_handleBranch185(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 186 && lok_rebel_trainer_2_handleBranch186(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 188 && lok_rebel_trainer_2_handleBranch188(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 189 && lok_rebel_trainer_2_handleBranch189(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 192 && lok_rebel_trainer_2_handleBranch192(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 200 && lok_rebel_trainer_2_handleBranch200(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 201 && lok_rebel_trainer_2_handleBranch201(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lok_rebel_trainer_2.branchId");
        return SCRIPT_CONTINUE;
    }
}
