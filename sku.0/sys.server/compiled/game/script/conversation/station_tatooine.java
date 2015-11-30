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

public class station_tatooine extends script.base_script
{
    public station_tatooine()
    {
    }
    public static String c_stringFile = "conversation/station_tatooine";
    public boolean station_tatooine_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean station_tatooine_condition_canAfford50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .50f);
    }
    public boolean station_tatooine_condition_canAfford25(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_crafting.canAffordShipRepairs(player, npc, .25f) && space_crafting.isDamaged(player));
    }
    public boolean station_tatooine_condition_canAfford75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, .75f);
    }
    public boolean station_tatooine_condition_canAfford100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.canAffordShipRepairs(player, npc, 1.0f);
    }
    public boolean station_tatooine_condition_needRepairs(obj_id player, obj_id npc) throws InterruptedException
    {
        float fltDamage = space_crafting.getDamageTotal(player, getPilotedShip(player));
        if (fltDamage > 0)
        {
            return true;
        }
        return false;
    }
    public boolean station_tatooine_condition_isTooFar(obj_id player, obj_id npc) throws InterruptedException
    {
        space_combat.playCombatTauntSound(player);
        obj_id containingShip = space_transition.getContainingShip(player);
        return (getDistance(npc, containingShip) > space_transition.STATION_COMM_MAX_DISTANCE);
    }
    public boolean station_tatooine_condition_isFirstTierTatooinePrivateer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_flags.isInTierOne(player) && space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE));
    }
    public boolean station_tatooine_condition_isFirstTierTatooineRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_flags.isInTierOne(player) && space_flags.isSpaceTrack(player, space_flags.REBEL_TATOOINE));
    }
    public boolean station_tatooine_condition_isFirstTierTatooineImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_flags.isInTierOne(player) && space_flags.isSpaceTrack(player, space_flags.IMPERIAL_TATOOINE));
    }
    public boolean station_tatooine_condition_canDoJabbaAccess(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasWonQuest(player, "assassinate", "tatooine_station_hutt_palace_access_quest_4"))
        {
            return false;
        }
        if (space_quest.hasQuest(player))
        {
            return false;
        }
        if (space_transition.getPlayerSpaceFaction(player) == (1153980303))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean station_tatooine_condition_canGetTier1RecoveryDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.hasCompletedTierOne(player))
        {
            return false;
        }
        if (space_quest.hasQuest(player, "recovery_duty", "tatooine_station_hutt_recovery_duty_tier1_1"))
        {
            return false;
        }
        if (hasSkill(player, "pilot_rebel_navy_novice"))
        {
            return true;
        }
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean station_tatooine_condition_canLandAtJabbas(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE) && !space_flags.isInTierTwo(player))
        {
            return true;
        }
        if (utils.isProfession(player, utils.SMUGGLER) || utils.isProfession(player, utils.BOUNTY_HUNTER))
        {
            return true;
        }
        return false;
    }
    public boolean station_tatooine_condition_canLandAtHouse(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "homingBeacon.planet"))
        {
            String homePlanet = getStringObjVar(player, "homingBeacon.planet");
            return (homePlanet.endsWith("tatooine"));
        }
        else 
        {
            return false;
        }
    }
    public boolean station_tatooine_condition_canTakeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_quest.hasQuest(player))
        {
            return false;
        }
        return true;
    }
    public boolean station_tatooine_condition_canAttackImperial(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isRebelPilot(player) || space_flags.isRebelHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean station_tatooine_condition_canAttackRebel(obj_id player, obj_id npc) throws InterruptedException
    {
        if (space_flags.isImperialPilot(player) || space_flags.isImperialHelperPilot(player))
        {
            return true;
        }
        return false;
    }
    public boolean station_tatooine_condition_canTakeTier5ImperialDuty(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean station_tatooine_condition_canTakeTier5RebelDuty(obj_id player, obj_id npc) throws InterruptedException
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
    public void station_tatooine_action_landStation3(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Mos Espa Starport");
    }
    public void station_tatooine_action_fix25(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .25f);
    }
    public void station_tatooine_action_fix50(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .50f);
    }
    public void station_tatooine_action_fix75(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, .75f);
    }
    public void station_tatooine_action_fix100(obj_id player, obj_id npc) throws InterruptedException
    {
        space_crafting.doStationToShipRepairs(player, npc, 1.0f);
    }
    public void station_tatooine_action_landStation1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Bestine Starport");
    }
    public void station_tatooine_action_landStation2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Mos Eisley Starport");
    }
    public void station_tatooine_action_landStation4(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Mos Entha Spaceport");
    }
    public void station_tatooine_action_giveJabbaAccessQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "rescue", "tatooine_station_hutt_palace_access_quest_1");
    }
    public void station_tatooine_action_grantTier1RecoveryDuty(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "recovery_duty", "tatooine_station_hutt_recovery_duty_tier1_1");
    }
    public void station_tatooine_action_landJabba(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayer(player, npc, "Jabba's Palace");
    }
    public void station_tatooine_action_landHoming(obj_id player, obj_id npc) throws InterruptedException
    {
        space_content.landPlayerHoming(player, npc);
    }
    public void station_tatooine_action_grantImperialDuty1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_imperial_tier1");
    }
    public void station_tatooine_action_grantImperialDuty2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_imperial_tier2");
    }
    public void station_tatooine_action_grantRebelDuty1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_rebel_tier1");
    }
    public void station_tatooine_action_grantRebelDuty2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_rebel_tier2");
    }
    public void station_tatooine_action_grantHiddenDaggerDuty2(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_hidden_dagger_tier2");
    }
    public void station_tatooine_action_grantHiddenDaggerDuty1(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_hidden_dagger_tier1");
    }
    public void station_tatooine_action_grantImperialDuty5(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_imperial_tier5");
    }
    public void station_tatooine_action_grantRebelDuty5(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "destroy_duty", "tatooine_rebel_tier5");
    }
    public String station_tatooine_tokenTO_tokenTO0001(obj_id player, obj_id npc) throws InterruptedException
    {
        return new String();
    }
    public int station_tatooine_tokenDI_getStationRepairCost25(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .25f);
    }
    public int station_tatooine_tokenDI_getStationRepairCost50(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .50f);
    }
    public int station_tatooine_tokenDI_getStationRepairCost75(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, .75f);
    }
    public int station_tatooine_tokenDI_getStationRepairCost100(obj_id player, obj_id npc) throws InterruptedException
    {
        return space_crafting.getStationRepairCost(player, npc, 1.0f);
    }
    public int station_tatooine_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_375a3c20"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83bace61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition_isFirstTierTatooineImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_isFirstTierTatooinePrivateer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_isFirstTierTatooineRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!station_tatooine_condition_isFirstTierTatooineImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!station_tatooine_condition_isFirstTierTatooinePrivateer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!station_tatooine_condition_isFirstTierTatooineRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (station_tatooine_condition_canLandAtJabbas(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (station_tatooine_condition_canLandAtHouse(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbc1c043");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd56d4d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cdf6820");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43d2bcbe");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ccbc6cf4");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75ba8830");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69ffeefe");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce4621b5");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_96bea28d"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35221afe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_canAfford50(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_canAfford75(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_tatooine_condition_canAfford100(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696719e4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6aea6f34");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5cf3921");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1076b9d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da196589");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f2b8a1d8"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_462a718b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5254f36");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3147a9c7");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_217"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_canTakeTier5RebelDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (station_tatooine_condition_canTakeTier5ImperialDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_197");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_205");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a5254f36"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39076454");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3147a9c7"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3cbb3f38");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2f2188d5");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd4bb16d");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2f2188d5"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66b68768");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_dd4bb16d"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_giveJabbaAccessQuest(player, npc);
                string_id message = new string_id(c_stringFile, "s_12b89c7f");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5984746e"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_83bace61");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition_isFirstTierTatooineImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_isFirstTierTatooinePrivateer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_isFirstTierTatooineRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (!station_tatooine_condition_isFirstTierTatooineImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (!station_tatooine_condition_isFirstTierTatooinePrivateer(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (!station_tatooine_condition_isFirstTierTatooineRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (station_tatooine_condition_canLandAtJabbas(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (station_tatooine_condition_canLandAtHouse(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dbc1c043");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4fd56d4d");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3cdf6820");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43d2bcbe");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ccbc6cf4");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75ba8830");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_69ffeefe");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ce4621b5");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_133");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6c8ffad8"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35221afe");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_canAfford50(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_canAfford75(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_tatooine_condition_canAfford100(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_696719e4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6aea6f34");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5cf3921");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d1076b9d");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da196589");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 37);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4360fc64"))
        {
            if (station_tatooine_condition_canGetTier1RecoveryDuty(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_165");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_167");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_171");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 51);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_tatooine_condition_canTakeQuest(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_121c3b6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 56);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_216");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_193"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_canTakeTier5RebelDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (station_tatooine_condition_canTakeTier5ImperialDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_197");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_205");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_dbc1c043"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_ea06a410");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4dc4840b");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4fd56d4d"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_6ff46cdd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d195782");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3cdf6820"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_895d093");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e1ef9209");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_43d2bcbe"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_41");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ccbc6cf4"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_53");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_57");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75ba8830"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_62");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_64");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_68");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_69ffeefe"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_78");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 28);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ce4621b5"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1d21e3c0");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_e76d5278");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 31);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_133"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_134");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_135");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_136");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 34);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4682ea6"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_c1013aa7");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4dc4840b"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1dbd74da");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_b9b27823"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_333ffc01");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d195782"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_43b6dcb9");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_895d093"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_4650b19f");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e1ef9209"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b729c637");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation1(player, npc);
                string_id message = new string_id(c_stringFile, "s_45");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_2a7858d1");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_53"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation2(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_57"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_64"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation3(player, npc);
                string_id message = new string_id(c_stringFile, "s_66");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_68"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_70");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch28(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_75"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landStation4(player, npc);
                string_id message = new string_id(c_stringFile, "s_b26eb03c");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_78"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_1ba81e14");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch31(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_52917b0d"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landJabba(player, npc);
                string_id message = new string_id(c_stringFile, "s_84");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_e76d5278"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_82f403b1");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch34(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_135"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_landHoming(player, npc);
                string_id message = new string_id(c_stringFile, "s_138");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_136"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_137");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch37(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_696719e4"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_eecfecc1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4c695dbd");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 38);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost25(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost25(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6aea6f34"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c130a6b8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_107");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 41);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost50(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost50(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_5cf3921"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42648804");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5e2a2bb1");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 44);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost75(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost75(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d1076b9d"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_7a45c683");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d0fd857b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_122");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 47);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost100(player, npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    pp.digitInteger = station_tatooine_tokenDI_getStationRepairCost100(player, npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_da196589"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_127");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch38(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_98"))
        {
            station_tatooine_action_fix25(player, npc);
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_4d9fae53");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_4c695dbd"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b086bdca");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch41(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d70dba34"))
        {
            station_tatooine_action_fix50(player, npc);
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c5b42136");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_107"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_109");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch44(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5e2a2bb1"))
        {
            station_tatooine_action_fix75(player, npc);
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_b6e0e78d");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d6695e83"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_116");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch47(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d0fd857b"))
        {
            station_tatooine_action_fix100(player, npc);
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_27445295");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_122"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_124");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch51(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_167"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_169");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_171"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_173");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_175");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_179");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 53);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch53(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_175"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantTier1RecoveryDuty(player, npc);
                string_id message = new string_id(c_stringFile, "s_177");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_179"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_181");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch56(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_218"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_195");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (station_tatooine_condition_canAttackImperial(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (station_tatooine_condition_canTakeTier5RebelDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (station_tatooine_condition_canAttackRebel(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (station_tatooine_condition_canTakeTier5ImperialDuty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                boolean hasResponse7 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse7 = true;
                }
                boolean hasResponse8 = false;
                if (station_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse8 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_197");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_199");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_163");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_201");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_203");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_164");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_205");
                    }
                    if (hasResponse7)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_207");
                    }
                    if (hasResponse8)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_214");
                    }
                    utils.setScriptVar(player, "conversation.station_tatooine.branchId", 58);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int station_tatooine_handleBranch58(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_197"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantRebelDuty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_208");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_199"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantRebelDuty2(player, npc);
                string_id message = new string_id(c_stringFile, "s_209");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_163"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantRebelDuty5(player, npc);
                string_id message = new string_id(c_stringFile, "s_166");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_201"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantImperialDuty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_210");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_203"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantImperialDuty2(player, npc);
                string_id message = new string_id(c_stringFile, "s_211");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_164"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantImperialDuty5(player, npc);
                string_id message = new string_id(c_stringFile, "s_168");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_205"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantHiddenDaggerDuty1(player, npc);
                string_id message = new string_id(c_stringFile, "s_212");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_207"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                station_tatooine_action_grantHiddenDaggerDuty2(player, npc);
                string_id message = new string_id(c_stringFile, "s_213");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_214"))
        {
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_215");
                utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_tatooine.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setObjVar(self, "convo.appearance", "object/mobile/space_comm_station_tatooine.iff");
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
        detachScript(self, "conversation.station_tatooine");
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
        if (station_tatooine_condition_isTooFar(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a9ce7ad3");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (station_tatooine_condition_canDoJabbaAccess(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_e2190b5d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_tatooine_condition_canAfford25(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (station_tatooine_condition_canTakeQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_375a3c20");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_96bea28d");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f2b8a1d8");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_217");
                }
                utils.setScriptVar(player, "conversation.station_tatooine.branchId", 2);
                npcStartConversation(player, npc, "station_tatooine", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (station_tatooine_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_a9152d31");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (station_tatooine_condition_canAfford25(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (station_tatooine_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (station_tatooine_condition_canTakeQuest(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5984746e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6c8ffad8");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4360fc64");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_193");
                }
                utils.setScriptVar(player, "conversation.station_tatooine.branchId", 8);
                npcStartConversation(player, npc, "station_tatooine", message, responses);
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
        if (!conversationId.equals("station_tatooine"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.station_tatooine.branchId");
        if (branchId == 2 && station_tatooine_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && station_tatooine_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && station_tatooine_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && station_tatooine_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && station_tatooine_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && station_tatooine_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && station_tatooine_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && station_tatooine_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && station_tatooine_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && station_tatooine_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && station_tatooine_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 28 && station_tatooine_handleBranch28(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 31 && station_tatooine_handleBranch31(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 34 && station_tatooine_handleBranch34(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 37 && station_tatooine_handleBranch37(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 38 && station_tatooine_handleBranch38(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 41 && station_tatooine_handleBranch41(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 44 && station_tatooine_handleBranch44(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 47 && station_tatooine_handleBranch47(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 51 && station_tatooine_handleBranch51(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 53 && station_tatooine_handleBranch53(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 56 && station_tatooine_handleBranch56(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 58 && station_tatooine_handleBranch58(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.station_tatooine.branchId");
        return SCRIPT_CONTINUE;
    }
}
