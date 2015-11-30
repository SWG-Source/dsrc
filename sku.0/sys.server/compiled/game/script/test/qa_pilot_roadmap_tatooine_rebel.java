package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.prose;
import script.library.qa;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.static_item;
import script.library.sui;
import script.library.trainerlocs;
import script.library.utils;

public class qa_pilot_roadmap_tatooine_rebel extends script.base_script
{
    public qa_pilot_roadmap_tatooine_rebel()
    {
    }
    public static final String TOOL_TITLE = "Tatooine Rebel Pilot";
    public static final String TOOL_PROMPT = "Tatooine Rebel Pilot\nSelect the pilot roadmap quest or function to complete.";
    public static final String SCRIPTVAR = "pilotrebtat";
    public static final String[][] MAIN_TOOL_MENU = 
    {
        
        {
            "Get Novice Tatooine Rebel Pilot and ship",
            "Complete First Mission Set (spacequest/patrol/tatooine_rebel_1)",
            "Complete Second Mission (spacequest/destroy/tatooine_rebel_3)",
            "Complete Third Mission Set (spacequest/patrol/tatooine_rebel_2)",
            "Complete Fourth Mission (spacequest/assassinate/tatooine_rebel_4)",
            "Get First Tier Pilot Skills",
            "Get Starships Tier 2",
            "Complete Fifth Mission (spacequest/escort/yavin_rebel_13)",
            "Get Pilot Weapons Tier 2",
            "Complete Sixth Mission (spacequest/inspect/yavin_rebel_14)",
            "Train Pilot Procedures Tier 2",
            "Complete Seventh Mission Set (spacequest/inspect/yavin_rebel_15)",
            "Train Droid Tier 2",
            "Complete Eighth Mission (spacequest/escort/yavin_rebel_16)",
            "Complete Tier 3_1 missions",
            "Train Pilot Tier 3",
            "Complete Tier 3_2 missions",
            "Train Naval Weapons Tier 3",
            "Complete Tier 3_3 missions",
            "Train Naval Procedures Tier 3",
            "Complete Tier 3_4 missions",
            "Train Droid Tier 3",
            "Train Pilot Tier 4",
            "Complete Tier 4_1 missions",
            "Get Pilot Weapons Tier 4",
            "Complete Tier 4_2 missions",
            "Train Pilot Procedures Tier 4",
            "Complete Tier 4_3 missions",
            "Train Droid Tier 4",
            "Complete Tier 4_4 missions",
            "Complete Master Mission 1 (spacequest/destroy/master_rebel_1)",
            "Complete Master Mission 1 (spacequest/destroy/master_rebel_2)"
        },
        
        {
            "pilot_rebel_navy_novice",
            "spacequest/patrol/tatooine_rebel_1;spacequest/destroy_surpriseattack/tatooine_rebel_1",
            "spacequest/destroy/tatooine_rebel_3",
            "spacequest/patrol/tatooine_rebel_2;spacequest/escort/tatooine_rebel_2",
            "spacequest/assassinate/tatooine_rebel_4",
            "pilot_rebel_navy_starships_01;pilot_rebel_navy_weapons_01;pilot_rebel_navy_procedures_01;pilot_rebel_navy_droid_01",
            "pilot_rebel_navy_starships_02",
            "spacequest/escort/yavin_rebel_13",
            "pilot_rebel_navy_weapons_02",
            "spacequest/inspect/yavin_rebel_14",
            "pilot_rebel_navy_procedures_02",
            "spacequest/inspect/yavin_rebel_15;spacequest/destroy_surpriseattack/yavin_rebel_15",
            "pilot_rebel_navy_droid_02",
            "spacequest/escort/yavin_rebel_16",
            "spacequest/recovery/tatooine_rebel_tier3_1;spacequest/delivery/tatooine_rebel_tier3_1_a;spacequest/survival/tatooine_rebel_tier3_1_b;spacequest/escort/tatooine_rebel_tier3_1_c",
            "pilot_rebel_navy_starships_03",
            "spacequest/inspect/tatooine_rebel_tier3_2;spacequest/destroy_surpriseattack/tatooine_rebel_tier3_2_a;spacequest/assassinate/tatooine_rebel_tier3_2_b;spacequest/space_battle/tatooine_rebel_tier3_2_c",
            "pilot_rebel_navy_weapons_03",
            "spacequest/delivery/tatooine_rebel_tier3_3;spacequest/space_battle/tatooine_rebel_tier3_3_a;spacequest/escort/tatooine_rebel_tier3_3_b;spacequest/survival/tatooine_rebel_tier3_3_c",
            "pilot_rebel_navy_procedures_03",
            "spacequest/assassinate/tatooine_rebel_tier3_4;spacequest/patrol/tatooine_rebel_tier3_4_a;spacequest/destroy_surpriseattack/tatooine_rebel_tier3_4_b;spacequest/delivery_no_pickup/tatooine_rebel_tier3_4_c;spacequest/space_battle/tatooine_rebel_tier3_4_d",
            "pilot_rebel_navy_droid_03",
            "pilot_rebel_navy_starships_04",
            "spacequest/space_battle/tatooine_rebel_tier4_1;spacequest/assassinate/tatooine_rebel_tier4_1_a;spacequest/patrol/tatooine_rebel_tier4_1_b;spacequest/destroy_surpriseattack/tatooine_rebel_tier4_1_c",
            "pilot_rebel_navy_weapons_04",
            "spacequest/recovery/tatooine_rebel_tier4_2;spacequest/delivery/tatooine_rebel_tier4_2_a;spacequest/survival/tatooine_rebel_tier4_2_b",
            "pilot_rebel_navy_procedures_04",
            "spacequest/space_battle/tatooine_rebel_tier4_3;spacequest/assassinate/tatooine_rebel_tier4_3_a;spacequest/assassinate/tatooine_rebel_tier4_3_b",
            "pilot_rebel_navy_droid_04",
            "spacequest/assassinate/tatooine_rebel_tier4_4;spacequest/survival/tatooine_rebel_tier4_4_a;spacequest/space_battle/tatooine_rebel_tier4_4_b",
            "spacequest/destroy/master_rebel_1",
            "spacequest/destroy/master_rebel_2"
        }
    };
    public static final int GET_NOVICE_PILOT_AND_SHIP = 0;
    public static final int COMPLETE_FIRST_MISSION = 1;
    public static final int COMPLETE_SECOND_MISSION = 2;
    public static final int COMPLETE_THIRD_MISSION = 3;
    public static final int COMPLETE_FOURTH_MISSION = 4;
    public static final int GET_FIRST_TIER_SKILLS = 5;
    public static final int TRAIN_NAVAL_STARSHIPS_2 = 6;
    public static final int COMPLETE_FIFTH_MISSION = 7;
    public static final int TRAIN_NAVAL_WEAPONS_2 = 8;
    public static final int COMPLETE_SIXTH_MISSION = 9;
    public static final int TRAIN_NAVAL_PROCEDURES_2 = 10;
    public static final int COMPLETE_SEVENTH_MISSION = 11;
    public static final int TRAIN_NAVAL_DROID_2 = 12;
    public static final int COMPLETE_EIGHTH_MISSION = 13;
    public static final int COMPLETE_TIER_3_1 = 14;
    public static final int TRAIN_NAVAL_STARSHIPS_3 = 15;
    public static final int COMPLETE_TIER_3_2 = 16;
    public static final int TRAIN_NAVAL_WEAPONS_3 = 17;
    public static final int COMPLETE_TIER_3_3 = 18;
    public static final int TRAIN_NAVAL_PROCEDURES_3 = 19;
    public static final int COMPLETE_TIER_3_4 = 20;
    public static final int TRAIN_NAVAL_DROIDS_3 = 21;
    public static final int TRAIN_NAVAL_STARSHIPS_4 = 22;
    public static final int COMPLETE_TIER_4_1 = 23;
    public static final int TRAIN_NAVAL_WEAPONS_4 = 24;
    public static final int COMPLETE_TIER_4_2 = 25;
    public static final int TRAIN_NAVAL_PROCEDURES_4 = 26;
    public static final int COMPLETE_TIER_4_3 = 27;
    public static final int TRAIN_NAVAL_DROID_4 = 28;
    public static final int COMPLETE_TIER_4_4 = 29;
    public static final int COMPLETE_MASTER_1 = 30;
    public static final int COMPLETE_MASTER_2 = 31;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_pilot_roadmap_tatooine_rebel");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_pilot_roadmap_tatooine_rebel");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                showToolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questId) throws InterruptedException
    {
        if (utils.hasScriptVar(self, SCRIPTVAR + ".useTrigger") && isGod(self))
        {
            int questidDelivery3_1_a = questGetQuestId("spacequest/delivery/tatooine_rebel_tier3_1_a");
            int questidSurvival3_1_b = questGetQuestId("spacequest/survival/tatooine_rebel_tier3_1_b");
            int questidEscort3_1_c = questGetQuestId("spacequest/escort/tatooine_rebel_tier3_1_c");
            if (questId == questidDelivery3_1_a)
            {
                qa.completeActiveQuest(self, "spacequest/delivery/tatooine_rebel_tier3_1_a");
            }
            else if (questId == questidSurvival3_1_b)
            {
                qa.completeActiveQuest(self, "spacequest/survival/tatooine_rebel_tier3_1_b");
            }
            else if (questId == questidEscort3_1_c)
            {
                boolean successCompleteEscort = qa.completeActiveQuest(self, "spacequest/escort/tatooine_rebel_tier3_1_c");
                if (successCompleteEscort)
                {
                    space_quest.giveReward(self, "recovery", "tatooine_rebel_tier3_1", 25000, "object/tangible/ship/components/armor/arm_mission_reward_rebel_corellian_triplate.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 100.0f);
                }
                else 
                {
                }
            }
            int questidDestroy_surpriseattack3_2_a = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_rebel_tier3_2_a");
            int questidAssassinate3_2_b = questGetQuestId("spacequest/assassinate/tatooine_rebel_tier3_2_b");
            int questidSpace_battle3_2_c = questGetQuestId("spacequest/space_battle/tatooine_rebel_tier3_2_c");
            if (questId == questidDestroy_surpriseattack3_2_a)
            {
                qa.completeActiveQuest(self, "spacequest/destroy_surpriseattack/tatooine_rebel_tier3_2_a");
            }
            else if (questId == questidAssassinate3_2_b)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_rebel_tier3_2_b");
                if (successGrant)
                {
                    space_quest.giveReward(self, "inspect", "tatooine_rebel_tier3_2", 25000, "object/tangible/ship/components/weapon_capacitor/cap_mission_reward_rebel_qualdex_battery_array.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 100.0f);
                }
            }
            int questidSpace_battle3_3_a = questGetQuestId("spacequest/space_battle/tatooine_rebel_tier3_3_a");
            int questidEscort3_3_b = questGetQuestId("spacequest/escort/tatooine_rebel_tier3_3_b");
            int questidSurvival3_3_c = questGetQuestId("spacequest/survival/tatooine_rebel_tier3_3_c");
            if (questId == questidSpace_battle3_3_a)
            {
                qa.completeActiveQuest(self, "spacequest/space_battle/tatooine_rebel_tier3_3_a");
            }
            else if (questId == questidEscort3_3_b)
            {
                qa.completeActiveQuest(self, "spacequest/escort/tatooine_rebel_tier3_3_b");
            }
            else if (questId == questidSurvival3_3_c)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/survival/tatooine_rebel_tier3_3_c");
                if (successGrant)
                {
                    space_quest.giveReward(self, "delivery", "tatooine_rebel_tier3_3", 25000, "object/tangible/ship/components/engine/eng_mission_reward_rebel_incom_military.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 100.0f);
                }
            }
            int questidPatrol3_4_a = questGetQuestId("spacequest/patrol/tatooine_rebel_tier3_4_a");
            int questidDestroy_surpriseattack3_4_b = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_rebel_tier3_4_b");
            int questidDelivery_no_pickup3_4_c = questGetQuestId("spacequest/delivery_no_pickup/tatooine_rebel_tier3_4_c");
            int questidSpace_battle3_4_d = questGetQuestId("spacequest/space_battle/tatooine_rebel_tier3_4_d");
            if (questId == questidPatrol3_4_a)
            {
                qa.completeActiveQuest(self, "spacequest/patrol/tatooine_rebel_tier3_4_a");
                qa.grantOrClearSpaceQuest(self, "spacequest/destroy_surpriseattack/tatooine_rebel_tier3_4_b", "grant");
            }
            else if (questId == questidDestroy_surpriseattack3_4_b)
            {
                qa.completeActiveQuest(self, "spacequest/destroy_surpriseattack/tatooine_rebel_tier3_4_b");
            }
            else if (questId == questidDelivery_no_pickup3_4_c)
            {
                qa.completeActiveQuest(self, "spacequest/delivery_no_pickup/tatooine_rebel_tier3_4_c");
            }
            else if (questId == questidSpace_battle3_4_d)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/space_battle/tatooine_rebel_tier3_4_d");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "tatooine_rebel_tier3_4", 25000, "object/tangible/ship/components/reactor/rct_mission_reward_rebel_slayn_hypervortex.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 100.0f);
                }
            }
            int questidAssassinate4_1_a = questGetQuestId("spacequest/assassinate/tatooine_rebel_tier4_1_a");
            int questidDestroy_surpriseattack4_1_c = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_rebel_tier4_1_c");
            if (questId == questidDestroy_surpriseattack4_1_c)
            {
                qa.completeActiveQuest(self, "spacequest/destroy_surpriseattack/tatooine_rebel_tier4_1_c");
            }
            else if (questId == questidAssassinate4_1_a)
            {
                boolean successComplete = qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_rebel_tier4_1_a");
                if (successComplete)
                {
                    space_quest.giveReward(self, "space_battle", "tatooine_rebel_tier4_1", 10000, "object/tangible/ship/components/shield_generator/shd_mission_reward_rebel_taim_military_grade.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 150.0f);
                }
            }
            int questidDelivery4_2_a = questGetQuestId("spacequest/delivery/tatooine_rebel_tier4_2_a");
            int questidSurvival4_2_b = questGetQuestId("spacequest/survival/tatooine_rebel_tier4_2_b");
            if (questId == questidSurvival4_2_b)
            {
                qa.completeActiveQuest(self, "spacequest/survival/tatooine_rebel_tier4_2_b");
            }
            else if (questId == questidDelivery4_2_a)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/delivery/tatooine_rebel_tier4_2_a");
                if (successGrant)
                {
                    space_quest.giveReward(self, "recovery", "tatooine_rebel_tier4_2", 10000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_rebel_novaldex_low_latency.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 150.0f);
                }
            }
            int questidAssassinate4_3_a = questGetQuestId("spacequest/assassinate/tatooine_rebel_tier4_3_a");
            int questidAssassinate4_3_b = questGetQuestId("spacequest/assassinate/tatooine_rebel_tier4_3_b");
            if (questId == questidAssassinate4_3_b)
            {
                qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_rebel_tier4_3_b");
            }
            else if (questId == questidAssassinate4_3_a)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_rebel_tier4_3_a");
                if (successGrant)
                {
                    space_quest.giveReward(self, "space_battle", "tatooine_rebel_tier4_3", 10000, "object/tangible/ship/components/booster/bst_mission_reward_rebel_qualdex_halcyon.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 150.0f);
                }
            }
            int questidSurvival4_4_a = questGetQuestId("spacequest/survival/tatooine_rebel_tier4_4_a");
            int questidSpace_battle4_4_b = questGetQuestId("spacequest/space_battle/tatooine_rebel_tier4_4_b");
            if (questId == questidSpace_battle4_4_b)
            {
                qa.completeActiveQuest(self, "spacequest/space_battle/tatooine_rebel_tier4_4_b");
            }
            else if (questId == questidSurvival4_4_a)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/survival/tatooine_rebel_tier4_4_a");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "tatooine_rebel_tier4_4", 10000, "object/tangible/ship/components/weapon/wpn_mission_reward_rebel_incom_tricannon.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 150.0f);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRebelPilotMainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    detachAndcleanAllScriptVars(self);
                    String[] tool_options = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
                    qa.refreshMenu(self, "Choose the tool you want to use", "QA Tools", tool_options, "toolMainMenu", true, "qatool.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (hasSkill(self, "pilot_imperial_navy_novice") || hasSkill(self, "pilot_rebel_navy_novice") || hasSkill(self, "pilot_neutral_novice"))
                    {
                        qa.revokePilotingSkills(self);
                        qa.blowOutObjVars(self, "space");
                    }
                    boolean successStep = stepThroughPilot(self, idx);
                    showToolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean stepThroughPilot(obj_id self, int step) throws InterruptedException
    {
        if (step >= 0)
        {
            if (step >= GET_NOVICE_PILOT_AND_SHIP)
            {
                space_flags.setSpaceTrack(self, space_flags.REBEL_TATOOINE);
                qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][GET_NOVICE_PILOT_AND_SHIP]);
                if (space_quest.canGrantNewbieShip(self) && hasSkill(self, MAIN_TOOL_MENU[1][GET_NOVICE_PILOT_AND_SHIP]))
                {
                    space_quest.grantNewbieShip(self, "rebel");
                }
            }
            if (step >= COMPLETE_FIRST_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FIRST_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy_surpriseattack", "tatooine_rebel_1", 100);
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 25.0f);
                }
            }
            if (step >= COMPLETE_SECOND_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SECOND_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy", "tatooine_rebel_3", 200);
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 50.0f);
                }
            }
            if (step >= COMPLETE_THIRD_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_THIRD_MISSION], "grant");
                if (successGrant)
                {
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 50.0f);
                    space_quest.giveReward(self, "escort", "tatooine_rebel_2", 500, "object/tangible/wearables/bodysuit/rebel_bodysuit_s14.iff");
                    space_quest.giveReward(self, "escort", "tatooine_rebel_2", 500, "object/tangible/wearables/bandolier/ith_multipocket_bandolier.iff");
                    space_quest.giveReward(self, "escort", "tatooine_rebel_2", 500, "object/tangible/wearables/bandolier/multipocket_bandolier.iff");
                }
            }
            if (step >= COMPLETE_FOURTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FOURTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "tatooine_rebel_4", 1000, "object/tangible/ship/components/armor/arm_mission_reward_rebel_incom_ultralight.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 75.0f);
                }
            }
            if (step >= GET_FIRST_TIER_SKILLS)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][GET_FIRST_TIER_SKILLS]);
            }
            if (step >= TRAIN_NAVAL_STARSHIPS_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_STARSHIPS_2]);
            }
            if (step >= COMPLETE_FIFTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FIFTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "escort", "yavin_rebel_13", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_rebel_incom_k77.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 75.0f);
                }
            }
            if (step >= TRAIN_NAVAL_WEAPONS_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_2]);
            }
            if (step >= COMPLETE_SIXTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SIXTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "inspect", "yavin_rebel_14", 5000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_rebel_moncal_d22.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 75.0f);
                }
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_2]);
            }
            if (step >= COMPLETE_SEVENTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SEVENTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy_surpriseattack", "yavin_rebel_15", 5000, "object/tangible/ship/components/booster/bst_mission_reward_rebel_novaldex_hypernova.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 75.0f);
                }
            }
            if (step >= TRAIN_NAVAL_DROID_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_DROID_2]);
            }
            if (step >= COMPLETE_EIGHTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_EIGHTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "escort", "yavin_rebel_16", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_rebel_taim_ion_driver.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 75.0f);
                    space_flags.setSpaceFlag(self, "ekerPilot", 3);
                }
            }
            if (step >= COMPLETE_TIER_3_1)
            {
                boolean successGrantEscort = qa.grantOrClearSpaceQuest(self, "spacequest/recovery/tatooine_rebel_tier3_1", "grant");
            }
            if (step >= TRAIN_NAVAL_STARSHIPS_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_STARSHIPS_3]);
            }
            if (step >= COMPLETE_TIER_3_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/inspect/tatooine_rebel_tier3_2", "grant");
            }
            if (step >= TRAIN_NAVAL_WEAPONS_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_3]);
            }
            if (step >= COMPLETE_TIER_3_3)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/delivery/tatooine_rebel_tier3_3", "grant");
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_3]);
            }
            if (step >= COMPLETE_TIER_3_4)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/assassinate/tatooine_rebel_tier3_4", "grant");
            }
            if (step >= TRAIN_NAVAL_DROIDS_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_DROIDS_3]);
            }
            if (step >= TRAIN_NAVAL_STARSHIPS_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_STARSHIPS_4]);
            }
            if (step >= COMPLETE_TIER_4_1)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/space_battle/tatooine_rebel_tier4_1", "grant");
            }
            if (step >= TRAIN_NAVAL_WEAPONS_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_4]);
            }
            if (step >= COMPLETE_TIER_4_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/recovery/tatooine_rebel_tier4_2", "grant");
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_4]);
            }
            if (step >= COMPLETE_TIER_4_3)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/space_battle/tatooine_rebel_tier4_3", "grant");
            }
            if (step >= TRAIN_NAVAL_DROID_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_DROID_4]);
            }
            if (step >= COMPLETE_TIER_4_4)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/assassinate/tatooine_rebel_tier4_4", "grant");
            }
            if (step >= COMPLETE_MASTER_1)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_MASTER_1], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy", "master_rebel_1", 25000, "object/tangible/wearables/jacket/jacket_ace_rebel.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 200.0f);
                }
            }
            if (step >= COMPLETE_MASTER_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_MASTER_2], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy", "master_rebel_2", 50000, "object/tangible/wearables/helmet/helmet_fighter_rebel_ace.iff");
                    factions.addFactionStanding(self, factions.FACTION_REBEL, 400.0f);
                    if (getGender(self) == GENDER_MALE)
                    {
                        if (getSpecies(self) == SPECIES_ITHORIAN)
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_rebel_m.iff", self);
                            setBioLink(medal, self);
                        }
                        else if (getSpecies(self) == SPECIES_WOOKIEE)
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_rebel_wke_m.iff", self);
                            setBioLink(medal, self);
                        }
                        else 
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_ace_pilot_rebel_m.iff", self);
                            setBioLink(medal, self);
                        }
                    }
                    else 
                    {
                        if (getSpecies(self) == SPECIES_ITHORIAN)
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/ith_necklace_ace_pilot_rebel_f.iff", self);
                            setBioLink(medal, self);
                        }
                        else if (getSpecies(self) == SPECIES_WOOKIEE)
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_rebel_f.iff", self);
                            setBioLink(medal, self);
                        }
                        else 
                        {
                            obj_id medal = createObjectInInventoryAllowOverload("object/tangible/wearables/necklace/necklace_trando_ace_pilot_rebel_f.iff", self);
                            setBioLink(medal, self);
                        }
                    }
                    skill.noisyGrantSkill(self, "pilot_rebel_navy_master");
                    space_flags.setSpaceFlag(self, "master_pilot_medal_recieved", true);
                }
            }
            return true;
        }
        return false;
    }
    public void showToolMainMenu(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, SCRIPTVAR + ".useTrigger", true);
        qa.refreshMenu(self, TOOL_PROMPT, TOOL_TITLE, MAIN_TOOL_MENU, "handleRebelPilotMainMenuOptions", true, SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu");
    }
    public void cleanAllScriptVars(obj_id self) throws InterruptedException
    {
        qa.removeScriptVars(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
        detachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
    }
    public void detachAndcleanAllScriptVars(obj_id self) throws InterruptedException
    {
        utils.removeScriptVarTree(self, SCRIPTVAR);
        detachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
    }
}
