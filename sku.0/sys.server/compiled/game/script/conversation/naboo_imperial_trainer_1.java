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
import script.library.features;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;
import script.library.xp;

public class naboo_imperial_trainer_1 extends script.base_script
{
    public naboo_imperial_trainer_1()
    {
    }
    public static String c_stringFile = "conversation/naboo_imperial_trainer_1";
    public boolean naboo_imperial_trainer_1_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean naboo_imperial_trainer_1_condition_readyForMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasSkill(player, "pilot_imperial_navy_novice"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasFailedMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_imperial_1") || space_quest.hasAbortedQuest(player, "patrol", "naboo_imperial_1") || space_quest.hasFailedQuest(player, "destroy_surpriseattack", "naboo_imperial_1") || space_quest.hasAbortedQuest(player, "destroy_surpriseattack", "naboo_imperial_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasWonMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "destroy_surpriseattack", "naboo_imperial_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_readyForMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy_surpriseattack", "naboo_imperial_1"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasWonMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "destroy", "naboo_imperial_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasFailedMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "destroy", "naboo_imperial_2") || space_quest.hasAbortedQuest(player, "destroy", "naboo_imperial_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_isOnMission(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasQuest(player) && naboo_imperial_trainer_1_condition_hasNoviceSkillBox(player, npc));
    }
    public boolean naboo_imperial_trainer_1_condition_readyForMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy", "naboo_imperial_2"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasFailedMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "patrol", "naboo_imperial_3") || space_quest.hasAbortedQuest(player, "patrol", "naboo_imperial_3") || space_quest.hasFailedQuest(player, "escort", "naboo_imperial_3") || space_quest.hasAbortedQuest(player, "escort", "naboo_imperial_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasWonMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "escort", "naboo_imperial_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_readyForMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "escort", "naboo_imperial_3"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasWonMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "naboo_imperial_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasFailedMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasFailedQuest(player, "assassinate", "naboo_imperial_4") || space_quest.hasAbortedQuest(player, "assassinate", "naboo_imperial_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_readyForDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_4"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_readyForSecondSkill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_4"))
        {
            return false;
        }
        if (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_canBuySkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else if (!hasSkill(player, "pilot_imperial_navy_starships_01") && space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_canBuySkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else if (!hasSkill(player, "pilot_imperial_navy_weapons_01") && space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_canBuySkill3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else if (!hasSkill(player, "pilot_imperial_navy_procedures_01") && space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasNoviceSkillBox(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean naboo_imperial_trainer_1_condition_canBuySkill(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_starships_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_weapons_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_procedures_01") || space_quest.isPlayerQualifiedForSkill(player, "pilot_imperial_navy_droid_01"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean naboo_imperial_trainer_1_condition_hasAllSkills(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player) && space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_4"))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_trainer_1_condition_hasCompletedSeries(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuestSeries(player, "imperial_naboo_1");
    }
    public boolean naboo_imperial_trainer_1_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        int playerFactionID = pvpGetAlignedFaction(player);
        if (playerFactionID == (370444368))
        {
            return true;
        }
        else 
        {
            return (hasSkill(player, "pilot_rebel_navy_novice"));
        }
    }
    public boolean naboo_imperial_trainer_1_condition_isNeutralPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_neutral_novice"));
    }
    public boolean naboo_imperial_trainer_1_condition_isImperialNotMember(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice") && !space_flags.isSpaceTrack(player, space_flags.IMPERIAL_NABOO));
    }
    public boolean naboo_imperial_trainer_1_condition_lostShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.canGrantNewbieShip(player) && hasSkill(player, "pilot_imperial_navy_novice");
    }
    public boolean naboo_imperial_trainer_1_condition_hasAShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return !space_quest.canGrantNewbieShip(player);
    }
    public boolean naboo_imperial_trainer_1_condition_isWorkingForHolfheim(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_quest.hasCompletedQuestSeries(player, "naboo_imperial_1");
    }
    public boolean naboo_imperial_trainer_1_condition_hasVeryFirstQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_quest.hasCertifiedShip(player))
        {
            return false;
        }
        else 
        {
            return (space_quest.hasQuest(player, "patrol", "naboo_imperial_1") || space_quest.hasQuest(player, "destroy_surpriseattack", "naboo_imperial_1"));
        }
    }
    public boolean naboo_imperial_trainer_1_condition_canFlyNonTrackDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((hasSkill(player, "pilot_imperial_navy_starships_01") || hasSkill(player, "pilot_imperial_navy_procedures_01") || hasSkill(player, "pilot_imperial_navy_weapons_01") || hasSkill(player, "pilot_imperial_navy_droid_01")) && !space_quest.hasQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean naboo_imperial_trainer_1_condition_isWookieeOrIthorian(obj_id player, obj_id npc) throws InterruptedException
    {
        int plrSpecies = getSpecies(player);
        return (plrSpecies == SPECIES_WOOKIEE || plrSpecies == SPECIES_ITHORIAN);
    }
    public boolean naboo_imperial_trainer_1_condition_isNotImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return !factions.isImperial(player);
    }
    public boolean naboo_imperial_trainer_1_condition_hasWeirdHead(obj_id player, obj_id npc) throws InterruptedException
    {
        int plrSpecies = getSpecies(player);
        if (plrSpecies == SPECIES_HUMAN || plrSpecies == SPECIES_ZABRAK)
        {
            return false;
        }
        return true;
    }
    public boolean naboo_imperial_trainer_1_condition_hasAbortedMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasAbortedQuest(player, "destroy", "naboo_imperial_2"));
    }
    public boolean naboo_imperial_trainer_1_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public void naboo_imperial_trainer_1_action_grantMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "conversation_1");
        space_quest.grantQuest(player, "patrol", "naboo_imperial_1");
        space_flags.restoreClientPath(player);
    }
    public void naboo_imperial_trainer_1_action_giveRewardMission1(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy_surpriseattack", "naboo_imperial_1"))
        {
            return;
        }
        faceTo(npc, player);
        doAnimationAction(npc, "conversation_1");
        factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 25.0f);
        space_quest.giveReward(player, "destroy_surpriseattack", "naboo_imperial_1", 100);
    }
    public void naboo_imperial_trainer_1_action_giveMembershipBadge(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.setSpaceTrack(player, space_flags.IMPERIAL_NABOO);
        skill.noisyGrantSkill(player, "pilot_imperial_navy_novice");
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
    }
    public void naboo_imperial_trainer_1_action_animNodHead(obj_id player, obj_id npc) throws InterruptedException
    {
        setName(npc, "Lt. Barn Sinkko");
        setInvulnerable(npc, true);
        faceTo(npc, player);
        doAnimationAction(npc, "nod_head_once");
    }
    public void naboo_imperial_trainer_1_action_animConverse(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "conversation_1");
    }
    public void naboo_imperial_trainer_1_action_animStop(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "stop");
    }
    public void naboo_imperial_trainer_1_action_animBye(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "goodbye");
    }
    public void naboo_imperial_trainer_1_action_animBothSalute(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        doAnimationAction(player, "salute1");
    }
    public void naboo_imperial_trainer_1_action_animDisgust(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "shake_head_disgust");
    }
    public void naboo_imperial_trainer_1_action_animGestureWild(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "gesticulate_wildly");
    }
    public void naboo_imperial_trainer_1_action_animRubChin(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "rub_chin_thoughtful");
    }
    public void naboo_imperial_trainer_1_action_animPoliteApplause(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(player, "salute1");
        doAnimationAction(npc, "applause_polite");
    }
    public void naboo_imperial_trainer_1_action_animRaiseFist(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "standing_raise_fist");
    }
    public void naboo_imperial_trainer_1_action_grantMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "wave_finger_warning");
        space_quest.grantQuest(player, "destroy", "naboo_imperial_2");
    }
    public void naboo_imperial_trainer_1_action_grantMission2Again(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        doAnimationAction(player, "salute1");
        space_quest.grantQuest(player, "destroy", "naboo_imperial_2");
    }
    public void naboo_imperial_trainer_1_action_giveRewardMission2(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "destroy", "naboo_imperial_2"))
        {
            return;
        }
        faceTo(npc, player);
        doAnimationAction(npc, "nod_head_once");
        factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 50.0f);
        space_quest.giveReward(player, "destroy", "naboo_imperial_2", 200);
    }
    public void naboo_imperial_trainer_1_action_grantMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "patrol", "naboo_imperial_3");
    }
    public void naboo_imperial_trainer_1_action_giveRewardMission3(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "escort", "naboo_imperial_3"))
        {
            return;
        }
        faceTo(npc, player);
        doAnimationAction(npc, "applause_polite");
        factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 50.0f);
        if (!naboo_imperial_trainer_1_condition_isWookieeOrIthorian(player, npc))
        {
            space_quest.giveReward(player, "escort", "naboo_imperial_3", 500, "object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff");
        }
        else if (getSpecies(player) != SPECIES_ITHORIAN)
        {
            space_quest.giveReward(player, "escort", "naboo_imperial_3", 500, "object/tangible/wearables/bandolier/double_bandolier.iff");
        }
        else 
        {
            space_quest.giveReward(player, "escort", "naboo_imperial_3", 500, "object/tangible/wearables/bandolier/ith_double_bandolier.iff");
        }
    }
    public void naboo_imperial_trainer_1_action_grantMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "pound_fist_palm");
        space_quest.grantQuest(player, "assassinate", "naboo_imperial_4");
    }
    public void naboo_imperial_trainer_1_action_giveRewardMission4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasReceivedReward(player, "assassinate", "naboo_imperial_4"))
        {
            return;
        }
        faceTo(npc, player);
        doAnimationAction(npc, "explain");
        factions.addFactionStanding(player, factions.FACTION_IMPERIAL, 75.0f);
        space_quest.giveReward(player, "assassinate", "naboo_imperial_4", 1000, "object/tangible/wearables/helmet/helmet_tie_fighter.iff");
    }
    public void naboo_imperial_trainer_1_action_giveSkill1(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            skill.noisyGrantSkill(player, "pilot_imperial_navy_starships_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_imperial_navy_starships_01");
        }
    }
    public void naboo_imperial_trainer_1_action_giveSkill2(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            skill.noisyGrantSkill(player, "pilot_imperial_navy_weapons_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_imperial_navy_weapons_01");
        }
    }
    public void naboo_imperial_trainer_1_action_giveSkill3(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            skill.noisyGrantSkill(player, "pilot_imperial_navy_procedures_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_imperial_navy_procedures_01");
        }
    }
    public void naboo_imperial_trainer_1_action_giveSkill4(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        if (!hasSkill(player, "pilot_imperial_navy_starships_01") && !hasSkill(player, "pilot_imperial_navy_weapons_01") && !hasSkill(player, "pilot_imperial_navy_procedures_01") && !hasSkill(player, "pilot_imperial_navy_droid_01"))
        {
            skill.noisyGrantSkill(player, "pilot_imperial_navy_droid_01");
        }
        else 
        {
            skill.purchaseSkill(player, "pilot_imperial_navy_droid_01");
        }
    }
    public void naboo_imperial_trainer_1_action_flagQuestSeriesComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        doAnimationAction(npc, "salute1");
        doAnimationAction(player, "salute1");
        space_quest.setQuestSeriesFlag(player, "naboo_imperial_1", space_quest.QUEST_SERIES_COMPLETED);
    }
    public void naboo_imperial_trainer_1_action_grantDestroyDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "naboo_imperial_6");
    }
    public void naboo_imperial_trainer_1_action_grantEscortDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "naboo_imperial_7");
    }
    public void naboo_imperial_trainer_1_action_grantTheNewbieShip(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantNewbieShip(player, "imperial");
    }
    public void naboo_imperial_trainer_1_action_eraseClientPath(obj_id player, obj_id npc) throws InterruptedException
    {
        space_flags.clearClientPath(player);
    }
    public int naboo_imperial_trainer_1_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_50ad850c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69a4eec5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4d88001");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b29a803f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_50ad850c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69a4eec5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4d88001");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b29a803f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_50ad850c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69a4eec5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4d88001");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b29a803f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60c4f974"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_away");
                string_id message = new string_id(c_stringFile, "s_50ad850c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69a4eec5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4d88001");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4358efe9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_b29a803f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4854758d"))
        {
            if (naboo_imperial_trainer_1_condition_canFlyNonTrackDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a087f2d5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2692ee30");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_90ce1297");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5eba6ff3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2692ee30"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_45b14b72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87bb7015");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79ab4bbe");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ebce168");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_90ce1297"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4cf693b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7d477c70"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_bc695163");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8efde2ae");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_169df3bb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8efde2ae"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                naboo_imperial_trainer_1_action_grantTheNewbieShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_94131047");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_169df3bb"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_5f2d3c1e");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2d5d4bf0"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_45b14b72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87bb7015");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79ab4bbe");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ebce168");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_943c7ba6"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_34bc5f5e");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (naboo_imperial_trainer_1_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_471");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_475");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_canBuySkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_472");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_478");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_canBuySkill3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_482");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_474");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_486");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3f6a8711"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                naboo_imperial_trainer_1_action_flagQuestSeriesComplete(player, npc);
                string_id message = new string_id(c_stringFile, "s_4e0900db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_304d169e");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 22);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_31967d85"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_14aa29c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b8ca7ab0");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24890a54"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_flagQuestSeriesComplete(player, npc);
                string_id message = new string_id(c_stringFile, "s_30a48366");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c0c8fe59");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_304d169e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_6e62e955");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_565cc8ba");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_565cc8ba"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_4918bb61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7d82215");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c7d82215"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_56dbc789");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9be59baa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e9a88a8"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1cfc4684");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11b1315a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch26(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9be59baa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e9a88a8"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1cfc4684");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11b1315a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch27(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_11b1315a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                doAnimationAction(player, "salute1");
                string_id message = new string_id(c_stringFile, "s_448fbf05");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
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
    public int naboo_imperial_trainer_1_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b8ca7ab0"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_flagQuestSeriesComplete(player, npc);
                string_id message = new string_id(c_stringFile, "s_7258dd22");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9be59baa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e9a88a8"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1cfc4684");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11b1315a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c0c8fe59"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_f6c21772");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_67");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_67"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6396820e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_9be59baa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1e9a88a8");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1e9a88a8"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_1cfc4684");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_11b1315a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 27);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch35(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_19ff301f"))
        {
            if (naboo_imperial_trainer_1_condition_canBuySkill1(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_471");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_475");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 36);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_canBuySkill2(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_472");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_478");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_canBuySkill3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_473");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_482");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_474");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_486");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 42);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bf64734d"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animRubChin(player, npc);
                string_id message = new string_id(c_stringFile, "s_fd4fe208");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch36(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_475"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_giveSkill1(player, npc);
                string_id message = new string_id(c_stringFile, "s_476");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_478"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_giveSkill2(player, npc);
                string_id message = new string_id(c_stringFile, "s_480");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_482"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_giveSkill3(player, npc);
                string_id message = new string_id(c_stringFile, "s_484");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch42(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_486"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_giveSkill4(player, npc);
                string_id message = new string_id(c_stringFile, "s_488");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch45(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6a128385"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_d25c510b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_110");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1091c96a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 46);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_114"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_45b14b72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87bb7015");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79ab4bbe");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ebce168");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3b3aabc1"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_8e66d267");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch46(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_110"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_45b14b72");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_87bb7015");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_79ab4bbe");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ebce168");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 48);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1091c96a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_88ffee3a");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch48(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_87bb7015"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                naboo_imperial_trainer_1_action_grantDestroyDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_c80f115e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a490d2c6");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 49);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_79ab4bbe"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_grantEscortDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_98c44634");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_123");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5ebce168"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                string_id message = new string_id(c_stringFile, "s_101a6629");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch49(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a490d2c6"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                string_id message = new string_id(c_stringFile, "s_ee63dfa9");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_123"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                string_id message = new string_id(c_stringFile, "s_ee63dfa9");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch54(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e12b6418"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animPoliteApplause(player, npc);
                string_id message = new string_id(c_stringFile, "s_b5d0e16a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d09c1ecb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 55);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4d7a1ae4"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8273f08f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c01da9e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 62);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2a1af001"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_b1f99085");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce681825");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 70);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch55(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d09c1ecb"))
        {
            if (naboo_imperial_trainer_1_condition_hasWeirdHead(player, npc))
            {
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_247ae5a6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c6aae4cb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_143");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_b8f011dc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 60);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c6aae4cb"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42dfe6db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 57);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_143"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_145");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch57(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_987445c9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_49ab06fa");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch60(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_148"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_150");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch62(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c01da9e4"))
        {
            if (naboo_imperial_trainer_1_condition_hasWeirdHead(player, npc))
            {
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_15daf24e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_156");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_166");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 63);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_dfd0987b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_171");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 68);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch63(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_156"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_158");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6f5e007c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_162");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 64);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_166"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch64(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6f5e007c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_19d7d16a");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_162"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_164");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch68(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_171"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_95f0e74b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch70(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ce681825"))
        {
            if (naboo_imperial_trainer_1_condition_hasWeirdHead(player, npc))
            {
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_177");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_187");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 71);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_191");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cd024d84");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 75);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch71(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_179"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_181");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_183");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 72);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_187"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_189");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch72(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_183"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_185");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch75(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cd024d84"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_ca3578cc");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch77(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59d306ec"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animRubChin(player, npc);
                string_id message = new string_id(c_stringFile, "s_1a5d8b73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_198");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3de840b5");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 78);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch78(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_198"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_trainer_1_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_adf9eee2");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3de840b5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_6868d86b");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch81(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_651c4d7a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_cfb2442e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 82);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch82(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_206"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_368fd90b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_209");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e347138");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch83(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_209"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2ac29eb6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ea112eca");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7e347138"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_c5d91ca3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e034da9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8529aa51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_209");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e347138");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch84(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ea112eca"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                naboo_imperial_trainer_1_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_bcadc07d");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch86(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e034da9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_trainer_1_action_grantMission4(player, npc);
                string_id message = new string_id(c_stringFile, "s_e8a06d76");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch88(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_209"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_2ac29eb6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ea112eca");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 84);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7e347138"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_c5d91ca3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e034da9");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 86);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_7478cd28"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8529aa51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_209");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7e347138");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7478cd28");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 83);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch89(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fe5a7681"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_1301bfd7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_223");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 90);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c0265f54"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_78efc377");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e7988c1");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 95);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43d18dec"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_7bdc0484");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61eba7c4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 104);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch90(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_223"))
        {
            if (naboo_imperial_trainer_1_condition_isWookieeOrIthorian(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_424f48fc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_729d0338");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 91);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe2c6775");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_229");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 93);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch91(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_729d0338"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_d9f28087");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch93(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_229"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_231");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch95(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3e7988c1"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_336e9422");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30e39201");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 96);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch96(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_30e39201"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_f7924d1a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb0c70a2");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 97);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch97(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cb0c70a2"))
        {
            if (naboo_imperial_trainer_1_condition_isWookieeOrIthorian(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_69a4d9f8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6456f762");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 98);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_1110cd35");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 101);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch98(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6456f762"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_abb72a4a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9c80b7c7");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 99);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch99(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c80b7c7"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_fa956108");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch101(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_246"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_248");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_250");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 102);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch102(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_250"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_252");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch104(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_61eba7c4"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_7c4734cc");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f02428e4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 105);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch105(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f02428e4"))
        {
            if (naboo_imperial_trainer_1_condition_isWookieeOrIthorian(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_f123cba6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 106);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_53f85004");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_264");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 108);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch106(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_89bc583c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_261");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch108(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_264"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_266");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch110(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b6b25f13"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                naboo_imperial_trainer_1_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_34975e9f");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_668cac00"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_d2ed722");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch113(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37b6c92d"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ce63cdc0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_df1a0b2a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 114);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8c9c5f1a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_a38ce775");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b6c92d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c9c5f1a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch114(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_df1a0b2a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_aa61375");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8a2b78b3");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 115);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch115(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8a2b78b3"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e660e010");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b24a6589");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 116);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch116(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b24a6589"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_eb4bd86c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dca3d46b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 117);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch117(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dca3d46b"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_2a29a3de");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_284");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1598dcb2");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b0ef8499");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 118);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch118(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_284"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_bb462a81");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1598dcb2"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3d71cce7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71ab0619");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 120);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b0ef8499"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_4774404f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2ddcdfff");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 122);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch120(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71ab0619"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_a8264b81");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch122(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2ddcdfff"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_grantMission3(player, npc);
                string_id message = new string_id(c_stringFile, "s_fa7b1fcc");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch124(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37b6c92d"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ce63cdc0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_df1a0b2a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 114);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_8c9c5f1a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_a38ce775");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37b6c92d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c9c5f1a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 113);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch125(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9081167"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_36ccf586");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_300");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 126);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5c314851"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_ae102a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fe48d61f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 128);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e80d813e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                naboo_imperial_trainer_1_action_giveRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_b33bb9fd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_313");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 132);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch126(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_300"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBothSalute(player, npc);
                string_id message = new string_id(c_stringFile, "s_62645c46");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch128(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fe48d61f"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_giveRewardMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_a7f18ee0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e2a2bb1");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 129);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch129(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5e2a2bb1"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "belly_laugh");
                string_id message = new string_id(c_stringFile, "s_2501f4f1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f79f892a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 130);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch130(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f79f892a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_bda5060f");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch132(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_313"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_b01365ea");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch134(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_29ad976e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_730419d7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4aa87431");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 135);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19288eda"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_3b1fee95");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch135(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_4aa87431"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_trainer_1_action_grantMission2Again(player, npc);
                string_id message = new string_id(c_stringFile, "s_5fdfcebf");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch138(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f9660223"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_325");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_327");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 139);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_330"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_332");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch139(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_327"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_trainer_1_action_grantMission2Again(player, npc);
                string_id message = new string_id(c_stringFile, "s_81849afb");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch142(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78d92576"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e1147f5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_337");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48d418c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_f3e660d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de1d8561");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 146);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5ee0d552"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_bc1b7622");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44a60f99");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 148);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_350"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_6554bb6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78d92576");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48d418c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ee0d552");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_350");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 142);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch143(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_337"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animRaiseFist(player, npc);
                string_id message = new string_id(c_stringFile, "s_e24fd46f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6f12f083");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 144);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch144(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6f12f083"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_a44450e3");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch146(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_de1d8561"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                naboo_imperial_trainer_1_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_c17b4421");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch148(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_44a60f99"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                naboo_imperial_trainer_1_action_grantMission2(player, npc);
                string_id message = new string_id(c_stringFile, "s_bb78a22c");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch150(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_78d92576"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e1147f5d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_337");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 143);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48d418c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_f3e660d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de1d8561");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 146);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5ee0d552"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_bc1b7622");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_44a60f99");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 148);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_350"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_6554bb6c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78d92576");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48d418c");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5ee0d552");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_350");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 142);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch151(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_3a1ddc14"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_916fe685");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_add5962c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 152);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da5219a7"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9c044885");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f5559c6a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 155);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_a6069c84"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_610250ee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8c85a270");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 160);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch152(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_add5962c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                naboo_imperial_trainer_1_action_giveRewardMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_f50739c7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_358");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 153);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch153(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_358"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_1614fceb");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch155(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f5559c6a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e1d8cd2a");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8bc18758");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 156);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch156(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8bc18758"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "applause_polite");
                naboo_imperial_trainer_1_action_giveRewardMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_a9bcd9f0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 157);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch157(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc27931b"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_20f98863");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_efa5623a");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 158);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch158(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_efa5623a"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_370");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch160(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8c85a270"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_feb83ab9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5bc9cf4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 161);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch161(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a5bc9cf4"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveRewardMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_25dee8db");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_378");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 162);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch162(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_378"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_9b6aa4b0");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch164(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_16fc739e"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_68951976");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59c8db72");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cb99174");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 165);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch165(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59c8db72"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                naboo_imperial_trainer_1_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_8d324b72");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cb99174"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_on_dismissing");
                string_id message = new string_id(c_stringFile, "s_47691a6");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch168(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e9339227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6047677");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 169);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bd7e7db"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_5109c7f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5ec97c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5bc07030"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3f082a8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7ca1c2c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b39049de"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_2670fd69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch169(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6047677"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_8b493b63");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a8c82b1c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 170);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch170(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a8c82b1c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute1");
                doAnimationAction(player, "salute1");
                naboo_imperial_trainer_1_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_62ee6ad5");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch172(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bd5ec97c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_a1f66568");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 173);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch173(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_400"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_859cd287");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d06e1eb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 174);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch174(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d06e1eb"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "listen");
                string_id message = new string_id(c_stringFile, "s_486c459c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_405");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 175);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch175(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_405"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                naboo_imperial_trainer_1_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_ea289c5d");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch177(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c7ca1c2c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_14abbd01");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb8aff15");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 178);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch178(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb8aff15"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_ce7aa9b7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_414");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 179);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch179(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_414"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                naboo_imperial_trainer_1_action_grantMission1(player, npc);
                string_id message = new string_id(c_stringFile, "s_37979335");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch181(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e9339227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6047677");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 169);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bd7e7db"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_5109c7f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5ec97c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5bc07030"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3f082a8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7ca1c2c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b39049de"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_2670fd69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch182(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b00e89d5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animConverse(player, npc);
                string_id message = new string_id(c_stringFile, "s_890c4859");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_388cfea9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_88f723eb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 183);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6df58958"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_f6c0eced");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9c2b0ed7");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 191);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6fc304c8"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_8153a680");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cf68a5b5");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 197);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch183(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_388cfea9"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animNodHead(player, npc);
                string_id message = new string_id(c_stringFile, "s_550165e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_519c6a35");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37eb1d12");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 184);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_88f723eb"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_9a34f731");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37650e5c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_438");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 185);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch184(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_519c6a35"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animStop(player, npc);
                string_id message = new string_id(c_stringFile, "s_9a34f731");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_37650e5c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_438");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 185);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_37eb1d12"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBye(player, npc);
                string_id message = new string_id(c_stringFile, "s_c2685407");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch185(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_37650e5c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_giveMembershipBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_2fe328e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 186);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_438"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                naboo_imperial_trainer_1_action_animBye(player, npc);
                string_id message = new string_id(c_stringFile, "s_c2685407");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch186(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_430"))
        {
            if (!naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_grantTheNewbieShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_1d0e7364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_433");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 187);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_436");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch187(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_433"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b3408be1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch188(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e9339227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6047677");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 169);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bd7e7db"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_5109c7f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5ec97c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5bc07030"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3f082a8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7ca1c2c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b39049de"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_2670fd69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch189(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_389"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_e9339227");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6047677");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 169);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9bd7e7db"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "standing_placate");
                string_id message = new string_id(c_stringFile, "s_5109c7f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5ec97c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 172);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5bc07030"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_3f082a8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c7ca1c2c");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 177);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b39049de"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_to_self");
                string_id message = new string_id(c_stringFile, "s_2670fd69");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch191(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_9c2b0ed7"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_c760c195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_798ef1b");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 192);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch192(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_798ef1b"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_fa138f3b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e565620f");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 193);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch193(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e565620f"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_shoulders");
                string_id message = new string_id(c_stringFile, "s_448");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7bdfe36");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f967fcee");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 194);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch194(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b7bdfe36"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveMembershipBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_95d2ba30");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f774e399");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 195);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f967fcee"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "dismiss");
                string_id message = new string_id(c_stringFile, "s_b317642c");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch195(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_f774e399"))
        {
            if (!naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_grantTheNewbieShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_1d0e7364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_433");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 187);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_436");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch197(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cf68a5b5"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_d2639576");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc7d6b01");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 198);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch198(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bc7d6b01"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_a1302371");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c297a8d4");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 199);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch199(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c297a8d4"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_ec61d38d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6e71a8bb");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 200);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch200(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6e71a8bb"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "point_accusingly");
                string_id message = new string_id(c_stringFile, "s_e16ed40c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9f55c8c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9d7bc2d3");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 201);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch201(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9f55c8c"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                naboo_imperial_trainer_1_action_giveMembershipBadge(player, npc);
                string_id message = new string_id(c_stringFile, "s_466");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_468");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 202);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_9d7bc2d3"))
        {
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "greet");
                string_id message = new string_id(c_stringFile, "s_a54e8a02");
                utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int naboo_imperial_trainer_1_handleBranch202(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_468"))
        {
            if (!naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                naboo_imperial_trainer_1_action_grantTheNewbieShip(player, npc);
                string_id message = new string_id(c_stringFile, "s_1d0e7364");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_433");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 187);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (naboo_imperial_trainer_1_condition_hasAShip(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_436");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                    }
                    utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
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
            detachScript(self, "conversation.naboo_imperial_trainer_1");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Lt. Barn Sinkko");
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        factions.setFaction(self, factions.FACTION_IMPERIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setName(self, "Lt. Barn Sinkko");
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
        detachScript(self, "conversation.naboo_imperial_trainer_1");
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
        if (naboo_imperial_trainer_1_condition_hasVeryFirstQuest(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_c19ed115");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_69a4eec5");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4358efe9");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 1);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!naboo_imperial_trainer_1_condition_hasSpaceExp(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a8755a48");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isNeutralPilot(player, npc))
        {
            doAnimationAction(npc, "wave_finger_warning");
            string_id message = new string_id(c_stringFile, "s_fc1001ba");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isRebelPilot(player, npc))
        {
            doAnimationAction(npc, "gesticulate_wildly");
            string_id message = new string_id(c_stringFile, "s_6159d1a3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isNotImperial(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_87019da4");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isImperialNotMember(player, npc))
        {
            doAnimationAction(npc, "shake_head_no");
            string_id message = new string_id(c_stringFile, "s_f6e10f56");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
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
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 9);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_lostShip(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_e106780b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7d477c70");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 13);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isWorkingForHolfheim(player, npc))
        {
            doAnimationAction(npc, "point_away");
            string_id message = new string_id(c_stringFile, "s_1fa7fe89");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_isOnMission(player, npc))
        {
            doAnimationAction(npc, "dismiss");
            string_id message = new string_id(c_stringFile, "s_8c262163");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasCompletedSeries(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_2e935a2c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!naboo_imperial_trainer_1_condition_canBuySkill(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition_canBuySkill(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2d5d4bf0");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_943c7ba6");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 19);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasAllSkills(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_d25495b9");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3f6a8711");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_31967d85");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_24890a54");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 21);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForSecondSkill(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_7ae0d223");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19ff301f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bf64734d");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 35);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForDuty(player, npc))
        {
            doAnimationAction(npc, "standing_placate");
            string_id message = new string_id(c_stringFile, "s_d8487693");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (!naboo_imperial_trainer_1_condition_canBuySkill(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6a128385");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_114");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3b3aabc1");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 45);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasWonMission4(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_ec04a9b3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e12b6418");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4d7a1ae4");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2a1af001");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 54);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasFailedMission4(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_38becb2d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59d306ec");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 77);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForMission4(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_a6e045f3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_651c4d7a");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 81);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasWonMission3(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_b6683056");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fe5a7681");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c0265f54");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43d18dec");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 89);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasFailedMission3(player, npc))
        {
            naboo_imperial_trainer_1_action_animGestureWild(player, npc);
            string_id message = new string_id(c_stringFile, "s_d82fc96f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6b25f13");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_668cac00");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 110);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForMission3(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_2bbdc667");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_37b6c92d");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8c9c5f1a");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 113);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasWonMission2(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_11dce039");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f9081167");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c314851");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_e80d813e");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 125);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasAbortedMission2(player, npc))
        {
            naboo_imperial_trainer_1_action_animDisgust(player, npc);
            string_id message = new string_id(c_stringFile, "s_ada6c751");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_29ad976e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19288eda");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 134);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasFailedMission2(player, npc))
        {
            naboo_imperial_trainer_1_action_animDisgust(player, npc);
            string_id message = new string_id(c_stringFile, "s_d66c56cf");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f9660223");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_330");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 138);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForMission2(player, npc))
        {
            naboo_imperial_trainer_1_action_animPoliteApplause(player, npc);
            string_id message = new string_id(c_stringFile, "s_c15ca8cc");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_78d92576");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48d418c");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5ee0d552");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_350");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 142);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasWonMission1(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_76892f68");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3a1ddc14");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_da5219a7");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a6069c84");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 151);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_hasFailedMission1(player, npc))
        {
            naboo_imperial_trainer_1_action_animDisgust(player, npc);
            string_id message = new string_id(c_stringFile, "s_7724d1e6");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_16fc739e");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 164);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition_readyForMission1(player, npc))
        {
            naboo_imperial_trainer_1_action_animBothSalute(player, npc);
            string_id message = new string_id(c_stringFile, "s_64fb691f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_389");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9bd7e7db");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5bc07030");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b39049de");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 168);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "spit_hands");
            naboo_imperial_trainer_1_action_eraseClientPath(player, npc);
            string_id message = new string_id(c_stringFile, "s_5797c779");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (naboo_imperial_trainer_1_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b00e89d5");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6df58958");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6fc304c8");
                }
                utils.setScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId", 182);
                npcStartConversation(player, npc, "naboo_imperial_trainer_1", message, responses);
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
        if (!conversationId.equals("naboo_imperial_trainer_1"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
        if (branchId == 1 && naboo_imperial_trainer_1_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && naboo_imperial_trainer_1_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && naboo_imperial_trainer_1_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && naboo_imperial_trainer_1_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && naboo_imperial_trainer_1_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && naboo_imperial_trainer_1_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && naboo_imperial_trainer_1_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && naboo_imperial_trainer_1_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && naboo_imperial_trainer_1_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && naboo_imperial_trainer_1_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && naboo_imperial_trainer_1_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && naboo_imperial_trainer_1_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && naboo_imperial_trainer_1_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && naboo_imperial_trainer_1_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 26 && naboo_imperial_trainer_1_handleBranch26(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 27 && naboo_imperial_trainer_1_handleBranch27(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && naboo_imperial_trainer_1_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && naboo_imperial_trainer_1_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && naboo_imperial_trainer_1_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && naboo_imperial_trainer_1_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && naboo_imperial_trainer_1_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && naboo_imperial_trainer_1_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 35 && naboo_imperial_trainer_1_handleBranch35(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 36 && naboo_imperial_trainer_1_handleBranch36(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && naboo_imperial_trainer_1_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && naboo_imperial_trainer_1_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 42 && naboo_imperial_trainer_1_handleBranch42(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 45 && naboo_imperial_trainer_1_handleBranch45(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 46 && naboo_imperial_trainer_1_handleBranch46(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 48 && naboo_imperial_trainer_1_handleBranch48(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 49 && naboo_imperial_trainer_1_handleBranch49(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && naboo_imperial_trainer_1_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 54 && naboo_imperial_trainer_1_handleBranch54(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 55 && naboo_imperial_trainer_1_handleBranch55(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && naboo_imperial_trainer_1_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 57 && naboo_imperial_trainer_1_handleBranch57(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 60 && naboo_imperial_trainer_1_handleBranch60(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 62 && naboo_imperial_trainer_1_handleBranch62(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 63 && naboo_imperial_trainer_1_handleBranch63(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 64 && naboo_imperial_trainer_1_handleBranch64(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 68 && naboo_imperial_trainer_1_handleBranch68(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 70 && naboo_imperial_trainer_1_handleBranch70(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 71 && naboo_imperial_trainer_1_handleBranch71(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 72 && naboo_imperial_trainer_1_handleBranch72(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 75 && naboo_imperial_trainer_1_handleBranch75(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 77 && naboo_imperial_trainer_1_handleBranch77(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 78 && naboo_imperial_trainer_1_handleBranch78(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 81 && naboo_imperial_trainer_1_handleBranch81(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 82 && naboo_imperial_trainer_1_handleBranch82(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 83 && naboo_imperial_trainer_1_handleBranch83(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 84 && naboo_imperial_trainer_1_handleBranch84(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 86 && naboo_imperial_trainer_1_handleBranch86(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 88 && naboo_imperial_trainer_1_handleBranch88(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 89 && naboo_imperial_trainer_1_handleBranch89(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 90 && naboo_imperial_trainer_1_handleBranch90(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 91 && naboo_imperial_trainer_1_handleBranch91(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 93 && naboo_imperial_trainer_1_handleBranch93(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 95 && naboo_imperial_trainer_1_handleBranch95(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 96 && naboo_imperial_trainer_1_handleBranch96(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 97 && naboo_imperial_trainer_1_handleBranch97(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 98 && naboo_imperial_trainer_1_handleBranch98(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 99 && naboo_imperial_trainer_1_handleBranch99(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 101 && naboo_imperial_trainer_1_handleBranch101(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 102 && naboo_imperial_trainer_1_handleBranch102(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 104 && naboo_imperial_trainer_1_handleBranch104(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 105 && naboo_imperial_trainer_1_handleBranch105(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 106 && naboo_imperial_trainer_1_handleBranch106(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 108 && naboo_imperial_trainer_1_handleBranch108(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 110 && naboo_imperial_trainer_1_handleBranch110(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 113 && naboo_imperial_trainer_1_handleBranch113(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 114 && naboo_imperial_trainer_1_handleBranch114(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 115 && naboo_imperial_trainer_1_handleBranch115(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 116 && naboo_imperial_trainer_1_handleBranch116(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 117 && naboo_imperial_trainer_1_handleBranch117(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 118 && naboo_imperial_trainer_1_handleBranch118(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 120 && naboo_imperial_trainer_1_handleBranch120(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 122 && naboo_imperial_trainer_1_handleBranch122(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 124 && naboo_imperial_trainer_1_handleBranch124(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 125 && naboo_imperial_trainer_1_handleBranch125(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 126 && naboo_imperial_trainer_1_handleBranch126(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 128 && naboo_imperial_trainer_1_handleBranch128(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 129 && naboo_imperial_trainer_1_handleBranch129(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 130 && naboo_imperial_trainer_1_handleBranch130(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 132 && naboo_imperial_trainer_1_handleBranch132(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 134 && naboo_imperial_trainer_1_handleBranch134(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 135 && naboo_imperial_trainer_1_handleBranch135(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 138 && naboo_imperial_trainer_1_handleBranch138(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 139 && naboo_imperial_trainer_1_handleBranch139(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 142 && naboo_imperial_trainer_1_handleBranch142(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 143 && naboo_imperial_trainer_1_handleBranch143(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 144 && naboo_imperial_trainer_1_handleBranch144(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 146 && naboo_imperial_trainer_1_handleBranch146(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 148 && naboo_imperial_trainer_1_handleBranch148(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 150 && naboo_imperial_trainer_1_handleBranch150(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 151 && naboo_imperial_trainer_1_handleBranch151(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 152 && naboo_imperial_trainer_1_handleBranch152(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 153 && naboo_imperial_trainer_1_handleBranch153(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 155 && naboo_imperial_trainer_1_handleBranch155(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 156 && naboo_imperial_trainer_1_handleBranch156(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 157 && naboo_imperial_trainer_1_handleBranch157(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 158 && naboo_imperial_trainer_1_handleBranch158(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 160 && naboo_imperial_trainer_1_handleBranch160(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 161 && naboo_imperial_trainer_1_handleBranch161(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 162 && naboo_imperial_trainer_1_handleBranch162(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 164 && naboo_imperial_trainer_1_handleBranch164(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 165 && naboo_imperial_trainer_1_handleBranch165(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 168 && naboo_imperial_trainer_1_handleBranch168(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 169 && naboo_imperial_trainer_1_handleBranch169(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 170 && naboo_imperial_trainer_1_handleBranch170(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 172 && naboo_imperial_trainer_1_handleBranch172(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 173 && naboo_imperial_trainer_1_handleBranch173(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 174 && naboo_imperial_trainer_1_handleBranch174(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 175 && naboo_imperial_trainer_1_handleBranch175(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 177 && naboo_imperial_trainer_1_handleBranch177(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 178 && naboo_imperial_trainer_1_handleBranch178(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 179 && naboo_imperial_trainer_1_handleBranch179(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 181 && naboo_imperial_trainer_1_handleBranch181(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 182 && naboo_imperial_trainer_1_handleBranch182(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 183 && naboo_imperial_trainer_1_handleBranch183(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 184 && naboo_imperial_trainer_1_handleBranch184(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 185 && naboo_imperial_trainer_1_handleBranch185(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 186 && naboo_imperial_trainer_1_handleBranch186(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 187 && naboo_imperial_trainer_1_handleBranch187(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 188 && naboo_imperial_trainer_1_handleBranch188(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 189 && naboo_imperial_trainer_1_handleBranch189(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 191 && naboo_imperial_trainer_1_handleBranch191(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 192 && naboo_imperial_trainer_1_handleBranch192(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 193 && naboo_imperial_trainer_1_handleBranch193(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 194 && naboo_imperial_trainer_1_handleBranch194(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 195 && naboo_imperial_trainer_1_handleBranch195(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 197 && naboo_imperial_trainer_1_handleBranch197(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 198 && naboo_imperial_trainer_1_handleBranch198(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 199 && naboo_imperial_trainer_1_handleBranch199(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 200 && naboo_imperial_trainer_1_handleBranch200(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 201 && naboo_imperial_trainer_1_handleBranch201(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 202 && naboo_imperial_trainer_1_handleBranch202(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.naboo_imperial_trainer_1.branchId");
        return SCRIPT_CONTINUE;
    }
}
