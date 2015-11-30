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
import script.library.factions;
import script.library.space_combat;
import script.library.space_content;
import script.library.space_crafting;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.space_transition;
import script.library.utils;

public class station_lok extends script.base_script
{
    public station_lok()
    {
    }
    public static String c_stringFile = "conversation/station_lok";
    public boolean station_lok_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_lok_condition_canAfford50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .50f);
    }
    public boolean station_lok_condition_canAfford25(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_crafting.canAffordShipRepairs(player, npc, .25f) && space_crafting.isDamaged(player));
    }
    public boolean station_lok_condition_canAfford75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .75f);
    }
    public boolean station_lok_condition_canAfford100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, 1.0f);
    }
    public boolean station_lok_condition_needRepairs(obj_id player, obj_id npc) throws InterruptedException
    {
        float fltDamage = space_crafting.getDamageTotal(player, getPilotedShip(player));
        if (fltDamage > 0)
        {
            return true;
        }
        return false;
    }
    public boolean station_lok_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean station_lok_condition_canDoWorkForNym(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player))
        {
            return false;
        }
        if (space_flags.isImperialPilot(player))
        {
            return false;
        }
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_NABOO))
        {
            return false;
        }
        if (!space_flags.hasCompletedTierOne(player))
        {
            return false;
        }
        return true;
    }
    public boolean station_lok_condition_isReadyForMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!station_lok_condition_canDoWorkForNym(player, npc))
        {
            return false;
        }
        if (station_lok_condition_hasCompletedMissionOne(player, npc))
        {
            return false;
        }
        return true;
    }
    public boolean station_lok_condition_hasCompletedMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "recovery", "stn_lok_nym_reb_tier2_1a"));
    }
    public boolean station_lok_condition_hasCompletedMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "escort", "stn_lok_nym_reb_tier2_2a"));
    }
    public boolean station_lok_condition_isReadyForMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!station_lok_condition_canDoWorkForNym(player, npc))
        {
            return false;
        }
        if (!station_lok_condition_hasCompletedMissionOne(player, npc))
        {
            return false;
        }
        if (station_lok_condition_hasCompletedMissionTwo(player, npc))
        {
            return false;
        }
        return true;
    }
    public boolean station_lok_condition_isReadyForMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!station_lok_condition_canDoWorkForNym(player, npc))
        {
            return false;
        }
        if (!station_lok_condition_hasCompletedMissionOne(player, npc))
        {
            return false;
        }
        if (!station_lok_condition_hasCompletedMissionTwo(player, npc))
        {
            return false;
        }
        if (station_lok_condition_hasCompletedMissionThree(player, npc))
        {
            return false;
        }
        return true;
    }
    public boolean station_lok_condition_hasCompletedMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasCompletedQuest(player, "assassinate", "stn_lok_nym_reb_tier2_3a"));
    }
    public boolean station_lok_condition_canLandAtHouse(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "homingBeacon.planet"))
        {
            String homePlanet = getStringObjVar(player, "homingBeacon.planet");
            return (homePlanet.endsWith("lok"));
        }
        else 
        {
            return false;
        }
    }
    public boolean station_lok_condition_canTakeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player))
        {
            return false;
        }
        return true;
    }
    public boolean station_lok_condition_canHandleTier3(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_flags.hasCompletedTierTwo(player);
    }
    public boolean station_lok_condition_canAttackImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isRebelPilot(player) || space_flags.isRebelHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean station_lok_condition_canAttackRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isImperialPilot(player) || space_flags.isImperialHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean station_lok_condition_canTakeRebel5Duty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_skill.isMasterPilot(player))
        {
            return false;
        }
        if (space_flags.isRebelPilot(player) || space_flags.isRebelHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean station_lok_condition_canTakeImperial5Duty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_skill.isMasterPilot(player))
        {
            return false;
        }
        if (space_flags.isImperialPilot(player) || space_flags.isImperialHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public void station_lok_action_landStation3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Mos Espa Starport");
    }
    public void station_lok_action_fix25(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .25f);
    }
    public void station_lok_action_fix50(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .50f);
    }
    public void station_lok_action_fix75(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .75f);
    }
    public void station_lok_action_fix100(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, 1.0f);
    }
    public void station_lok_action_landStation1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Nym's Stronghold");
    }
    public void station_lok_action_landStation2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Mos Eisley Starport");
    }
    public void station_lok_action_grantDestroyDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "stn_lok_nym_reb_tier2_1");
    }
    public void station_lok_action_grantEscortDutyMission(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort_duty", "stn_lok_nym_reb_tier2_1");
    }
    public void station_lok_action_grantMissionOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery", "stn_lok_nym_reb_tier2_1a");
    }
    public void station_lok_action_grantMissionTwo(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "escort", "stn_lok_nym_reb_tier2_2a");
    }
    public void station_lok_action_grantMissionThree(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "assassinate", "stn_lok_nym_reb_tier2_3a");
    }
    public void station_lok_action_landHoming(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayerHoming(player, npc);
    }
    public void station_lok_action_grantImperialDuty3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_imperial_tier3");
    }
    public void station_lok_action_grantRebelDuty3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_rebel_tier3");
    }
    public void station_lok_action_grantHiddenDaggerDuty3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_hidden_dagger_tier3");
    }
    public void station_lok_action_grantDroidDuty3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_rogue_droid_tier3");
    }
    public void station_lok_action_grantImperialDuty5(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_imperial_tier5");
    }
    public void station_lok_action_grantRebelDuty5(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "lok_rebel_tier5");
    }
    public String station_lok_tokenTO_tokenTO0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int station_lok_tokenDI_getStationRepairCost25(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .25f);
    }
    public int station_lok_tokenDI_getStationRepairCost50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .50f);
    }
    public int station_lok_tokenDI_getStationRepairCost75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .75f);
    }
    public int station_lok_tokenDI_getStationRepairCost100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, 1.0f);
    }
    public int station_lok_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_305434ff"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35f718b7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_lok_condition_canLandAtHouse(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d70dba34");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6c8ffad8"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3538339");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition_canAfford50(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_lok_condition_canAfford75(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_lok_condition_canAfford100(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_lok_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bf151ca2");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c80840ca");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2d1962b");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_445607d0");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_169df3bb");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1b3d0b9e"))
        {
            if (station_lok_condition_isReadyForMissionOne(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6cc25feb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_77685adb");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20fce91c");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_lok_condition_isReadyForMissionTwo(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb83a046");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_474141fe");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 29);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_lok_condition_isReadyForMissionThree(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_40a7b30f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b1729740");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_63942640");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ad7b177d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e5ce7fcb");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 44);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_108"))
        {
            if (station_lok_condition_canHandleTier3(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_110");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition_canTakeRebel5Duty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_lok_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_lok_condition_canTakeImperial5Duty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_lok_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_112");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_115");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_134");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_120");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_124");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_128");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 47);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_132");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            station_lok_action_landStation1(player, npc);
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_landStation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_94d99aa4");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96576ff4");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_95"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_96");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 6);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_landHoming(player, npc);
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_100");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bf151ca2"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d38dd40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebe2e111");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 10);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost25(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost25(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_c80840ca"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_360058b6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 13);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost50(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost50(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2d1962b"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d47aa2c4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_39");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 16);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost75(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost75(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_445607d0"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3e88ac8b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fef28268");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 19);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost100(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_lok_tokenDI_getStationRepairCost100(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_169df3bb"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f33a826f");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ebe2e111"))
        {
            station_lok_action_fix25(player, npc);
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_60460c5");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d6695e83"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bc1b6f03");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            station_lok_action_fix50(player, npc);
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a6054d56");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8a98e9e7");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_35"))
        {
            station_lok_action_fix75(player, npc);
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_39"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fef28268"))
        {
            station_lok_action_fix100(player, npc);
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ce3f3ff6");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_49");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_77685adb"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_10c4c33b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e998576d");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 24);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_20fce91c"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_5e3dd46f");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch24(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_e998576d"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b69f3131");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e39c0a00");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_eb2cfdac"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantMissionOne(player, npc);
                string_id message = new string_id(c_stringFile, "s_d34f2ea4");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e39c0a00"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea7d0b42");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch29(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_474141fe"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c6280dee");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8d68ee17");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 30);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_80");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch30(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8d68ee17"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f17c177b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a7ea491f");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a7ea491f"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7488284");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14a79f3e");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 32);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch32(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14a79f3e"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a77bdd74");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bbfa316b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2f9e3b7b");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 33);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch33(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bbfa316b"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantMissionTwo(player, npc);
                string_id message = new string_id(c_stringFile, "s_c408ae1e");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2f9e3b7b"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_185ada38");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b1729740"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_e99611e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fc6171f1");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 38);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_101");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_fc6171f1"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_676e1e17");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5adb5e96");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 39);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch39(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5adb5e96"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_716b5194");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_lok_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_59b2e836");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cac4df9f");
                    }
                    utils.setScriptVar(player, "conversation.station_lok.branchId", 40);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_lok.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_lok_handleBranch40(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_59b2e836"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantMissionThree(player, npc);
                string_id message = new string_id(c_stringFile, "s_a6a8081b");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cac4df9f"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c23916c6");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ad7b177d"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantDestroyDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_720fe84e");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e5ce7fcb"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantEscortDutyMission(player, npc);
                string_id message = new string_id(c_stringFile, "s_daa54d2");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
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
    public int station_lok_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_112"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantRebelDuty3(player, npc);
                string_id message = new string_id(c_stringFile, "s_118");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_133"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantRebelDuty5(player, npc);
                string_id message = new string_id(c_stringFile, "s_135");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_115"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantImperialDuty3(player, npc);
                string_id message = new string_id(c_stringFile, "s_119");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_134"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantImperialDuty5(player, npc);
                string_id message = new string_id(c_stringFile, "s_136");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_120"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantDroidDuty3(player, npc);
                string_id message = new string_id(c_stringFile, "s_122");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_124"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                station_lok_action_grantHiddenDaggerDuty3(player, npc);
                string_id message = new string_id(c_stringFile, "s_126");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_128"))
        {
            if (station_lok_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_130");
                utils.removeScriptVar(player, "conversation.station_lok.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_lok.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_lok.iff");
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
        detachScript(self, "conversation.station_lok");
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
        if (station_lok_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5d8ae855");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_lok_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_bc62d0fb");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_lok_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_lok_condition_canAfford25(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (station_lok_condition_canDoWorkForNym(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (station_lok_condition_canTakeQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_305434ff");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6c8ffad8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_1b3d0b9e");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_108");
                }
                utils.setScriptVar(player, "conversation.station_lok.branchId", 2);
                npcStartConversation(player, npc, "station_lok", message, responses);
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
        if (!conversationId.equals("station_lok"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_lok.branchId");
        if (branchId == 2 && station_lok_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && station_lok_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && station_lok_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && station_lok_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && station_lok_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && station_lok_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && station_lok_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && station_lok_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && station_lok_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 24 && station_lok_handleBranch24(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && station_lok_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 29 && station_lok_handleBranch29(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 30 && station_lok_handleBranch30(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && station_lok_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 32 && station_lok_handleBranch32(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 33 && station_lok_handleBranch33(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && station_lok_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && station_lok_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 39 && station_lok_handleBranch39(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 40 && station_lok_handleBranch40(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && station_lok_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && station_lok_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_lok.branchId");
        return SCRIPT_CONTINUE;
    }
}
