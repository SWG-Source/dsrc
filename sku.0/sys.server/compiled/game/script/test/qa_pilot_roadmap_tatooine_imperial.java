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
import script.library.utils;

public class qa_pilot_roadmap_tatooine_imperial extends script.base_script
{
    public qa_pilot_roadmap_tatooine_imperial()
    {
    }
    public static final String TOOL_TITLE = "Tatooine Imperial Pilot";
    public static final String TOOL_PROMPT = "Tatooine Imperial Pilot\nSelect the pilot roadmap quest or function to complete.";
    public static final String SCRIPTVAR = "pilotimptat";
    public static final String[][] MAIN_TOOL_MENU = 
    {
        
        {
            "Get Novice Tatooine Imperial Pilot and ship",
            "Complete First 2 Missions (patrol/tatooine_imperial_1 & destroy_surpriseattack/tatooine_imperial_1)",
            "Complete Second Mission (destroy/tatooine_imperial_2)",
            "Complete Third Mission Set (patrol/tatooine_imperial_3 & spacequest/escort/tatooine_imperial_3)",
            "Complete Fourth Mission (assassinate/tatooine_imperial_4)",
            "Train Tier 1 Pilot Skills",
            "Complete Fifth Mission (space_quest/inspect/imperial_ss_1)",
            "Complete Sixth Mission (space_quest/recovery/imperial_ss_2)",
            "Train Naval Pilot 2",
            "Complete Seventh Mission (spacequest/assassinate/imperial_ss_3)",
            "Train Naval Weapons 2",
            "Complete Eighth Mission (spacequest/inspect/imperial_ss_4)",
            "Complete Ninth Mission (spacequest/escort/imperial_ss_5)",
            "Train Naval Procedures 2 and Droid 2",
            "Complete Tenth Mission (spacequest/recovery/imperial_ss_6)",
            "Complete Tier 3, Mission Set 1 (spacequest/escort/tatooine_imperial_tier3_1)",
            "Train Navy Starships 3",
            "Complete Tier 3, Mission Set 2 (spacequest/inspect/tatooine_imperial_tier3_2)",
            "Train Naval Weapons 3",
            "Complete Tier 3, Mission Set 3 (spacequest/delivery/tatooine_imperial_tier3_3)",
            "Train Naval Procedures 3",
            "Complete Tier 3, Mission Set 4 (spacequest/assassinate/tatooine_imperial_tier3_4)",
            "Train Naval Droid 3",
            "Train Naval Starships 4",
            "Complete Tier 4, Mission Set 1 (spacequest/patrol/tatooine_imperial_tier4_1)",
            "Train Naval Weapons 4",
            "Complete Tier 4, Mission Set 2 (spacequest/recovery/tatooine_imperial_tier4_2)",
            "Train Naval Procedures 4",
            "Complete Tier 4, Mission Set 3 (spacequest/escort/tatooine_imperial_tier4_3)",
            "Train Naval Droid 4",
            "Complete Tier 4, Mission Set 4 (spacequest/assassinate/tatooine_imperial_tier4_4)",
            "Complete 1st Master Mission (spacequest/destroy/master_imperial_1)",
            "Complete 2nd Master Mission (spacequest/destroy/master_imperial_2)"
        },
        
        {
            "",
            "spacequest/patrol/tatooine_imperial_1;spacequest/destroy_surpriseattack/tatooine_imperial_1",
            "spacequest/destroy/tatooine_imperial_2",
            "spacequest/patrol/tatooine_imperial_3;spacequest/escort/tatooine_imperial_3",
            "spacequest/assassinate/tatooine_imperial_4",
            "pilot_imperial_navy_starships_01;pilot_imperial_navy_weapons_01;pilot_imperial_navy_procedures_01;pilot_imperial_navy_droid_01",
            "spacequest/inspect/imperial_ss_1",
            "spacequest/recovery/imperial_ss_2",
            "pilot_imperial_navy_starships_02",
            "spacequest/assassinate/imperial_ss_3",
            "pilot_imperial_navy_weapons_02",
            "spacequest/inspect/imperial_ss_4",
            "spacequest/escort/imperial_ss_5",
            "pilot_imperial_navy_procedures_02;pilot_imperial_navy_droid_02",
            "spacequest/recovery/imperial_ss_6",
            "spacequest/escort/tatooine_imperial_tier3_1;spacequest/recovery/tatooine_imperial_tier3_1_a;spacequest/inspect/tatooine_imperial_tier3_1_b;spacequest/rescue/tatooine_imperial_tier3_1_c;spacequest/delivery_no_pickup/tatooine_imperial_tier3_1_d",
            "pilot_imperial_navy_starships_03",
            "spacequest/inspect/tatooine_imperial_tier3_2;spacequest/destroy_surpriseattack/tatooine_imperial_tier3_2_a;spacequest/inspect/tatooine_imperial_tier3_2_b;spacequest/delivery_no_pickup/tatooine_imperial_tier3_2_c;spacequest/patrol/tatooine_imperial_tier3_2_d;spacequest/space_battle/tatooine_imperial_tier3_2_e",
            "pilot_imperial_navy_weapons_03",
            "spacequest/delivery/tatooine_imperial_tier3_3;spacequest/destroy/tatooine_imperial_tier3_3_b;spacequest/escort/tatooine_imperial_tier3_3_a;spacequest/destroy_surpriseattack/tatooine_imperial_tier3_3_d;spacequest/survival/tatooine_imperial_tier3_3_c",
            "pilot_imperial_navy_procedures_03",
            "spacequest/assassinate/tatooine_imperial_tier3_4;spacequest/rescue/tatooine_imperial_tier3_4_a;spacequest/inspect/tatooine_imperial_tier3_4_b;spacequest/assassinate/tatooine_imperial_tier3_4_c",
            "pilot_imperial_navy_droid_03",
            "pilot_imperial_navy_starships_04",
            "spacequest/patrol/tatooine_imperial_tier4_1;spacequest/destroy_surpriseattack/tatooine_imperial_tier4_1_a;spacequest/space_battle/tatooine_imperial_tier4_1_b;spacequest/inspect/tatooine_imperial_tier4_1_c;spacequest/delivery_no_pickup/tatooine_imperial_tier4_1_d",
            "pilot_imperial_navy_weapons_04",
            "spacequest/recovery/tatooine_imperial_tier4_2;spacequest/recovery/tatooine_imperial_tier4_2_a;spacequest/delivery/tatooine_imperial_tier4_2_b;spacequest/delivery_no_pickup/tatooine_imperial_tier4_2_c",
            "pilot_imperial_navy_procedures_04",
            "spacequest/escort/tatooine_imperial_tier4_3;spacequest/rescue/tatooine_imperial_tier4_3_a;spacequest/space_battle/tatooine_imperial_tier4_3_b",
            "pilot_imperial_navy_droid_04",
            "spacequest/assassinate/tatooine_imperial_tier4_4;spacequest/assassinate/tatooine_imperial_tier4_4_a;spacequest/recovery/tatooine_imperial_tier4_4_b;spacequest/assassinate/tatooine_imperial_tier4_4_c",
            "spacequest/destroy/master_imperial_1",
            "pilot_imperial_navy_master"
        }
    };
    public static final int GET_NOVICE_PILOT_AND_SHIP = 0;
    public static final int COMPLETE_FIRST_MISSION = 1;
    public static final int COMPLETE_SECOND_MISSION = 2;
    public static final int COMPLETE_THIRD_MISSION = 3;
    public static final int COMPLETE_FOURTH_MISSION = 4;
    public static final int GET_FIRST_TIER_SKILLS = 5;
    public static final int COMPLETE_FIFTH_MISSION = 6;
    public static final int COMPLETE_SIXTH_MISSION = 7;
    public static final int TRAIN_NAVAL_PILOT_2 = 8;
    public static final int COMPLETE_SEVENTH_MISSION = 9;
    public static final int TRAIN_NAVAL_WEAPONS_2 = 10;
    public static final int COMPLETE_EIGHTH_MISSION = 11;
    public static final int COMPLETE_NINTH_MISSION = 12;
    public static final int TRAIN_NAVAL_PROCEDURES_AND_DROID_2 = 13;
    public static final int COMPLETE_TENTH_MISSION = 14;
    public static final int COMPLETE_TIER_3 = 15;
    public static final int TRAIN_NAVAL_STARSHIPS_3 = 16;
    public static final int COMPLETE_TIER_3_2 = 17;
    public static final int TRAIN_NAVAL_WEAPONS_3 = 18;
    public static final int COMPLETE_TIER_3_3 = 19;
    public static final int TRAIN_NAVAL_PROCEDURES_3 = 20;
    public static final int COMPLETE_TIER_3_4 = 21;
    public static final int TRAIN_NAVAL_DROIDS_3 = 22;
    public static final int TRAIN_NAVAL_STARSHIPS_4 = 23;
    public static final int COMPLETE_TIER_4_1 = 24;
    public static final int TRAIN_NAVAL_WEAPONS_4 = 25;
    public static final int COMPLETE_TIER_4_2 = 26;
    public static final int TRAIN_NAVAL_PROCEDURES_4 = 27;
    public static final int COMPLETE_TIER_4_3 = 28;
    public static final int TRAIN_NAVAL_DROIDS_4 = 29;
    public static final int COMPLETE_TIER_4_4 = 30;
    public static final int COMPLETE_MASTER_1 = 31;
    public static final int COMPLETE_MASTER_2 = 32;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
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
            int questidRecovery3_1_a = questGetQuestId("spacequest/recovery/tatooine_imperial_tier3_1_a");
            int questidInspect3_1_b = questGetQuestId("spacequest/inspect/tatooine_imperial_tier3_1_b");
            int questidRescue3_1_c = questGetQuestId("spacequest/rescue/tatooine_imperial_tier3_1_c");
            int questidDelivery_no_pickup3_1_d = questGetQuestId("spacequest/delivery_no_pickup/tatooine_imperial_tier3_1_d");
            int questidDestroy_surpriseattack3_2_a = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_imperial_tier3_2_a");
            int questidInspect3_2_b = questGetQuestId("spacequest/inspect/tatooine_imperial_tier3_2_b");
            int questidDelivery_no_pickup3_2_c = questGetQuestId("spacequest/delivery_no_pickup/tatooine_imperial_tier3_2_c");
            int questidPatrol3_2_d = questGetQuestId("spacequest/patrol/tatooine_imperial_tier3_2_d");
            int questidSpace_battle3_2_e = questGetQuestId("spacequest/space_battle/tatooine_imperial_tier3_2_e");
            int questidDestroy3_3_a = questGetQuestId("spacequest/destroy/tatooine_imperial_tier3_3_b");
            int questidEscort3_3_b = questGetQuestId("spacequest/escort/tatooine_imperial_tier3_3_a");
            int questidDestroy_surpriseattack3_3_c = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_imperial_tier3_3_d");
            int questidSurvival3_3_d = questGetQuestId("spacequest/survival/tatooine_imperial_tier3_3_c");
            int questidRescue3_4_a = questGetQuestId("spacequest/rescue/tatooine_imperial_tier3_4_a");
            int questidInspect3_4_b = questGetQuestId("spacequest/inspect/tatooine_imperial_tier3_4_b");
            int questidAssassinate3_4_c = questGetQuestId("spacequest/assassinate/tatooine_imperial_tier3_4_c");
            int questidDestroy_surpriseattack4_1_a = questGetQuestId("spacequest/destroy_surpriseattack/tatooine_imperial_tier4_1_a");
            int questidSpace_battle4_1_b = questGetQuestId("spacequest/space_battle/tatooine_imperial_tier4_1_b");
            int questidInspect4_1_c = questGetQuestId("spacequest/inspect/tatooine_imperial_tier4_1_c");
            int questidDelivery_no_pickup4_1_d = questGetQuestId("spacequest/delivery_no_pickup/tatooine_imperial_tier4_1_d");
            int questidRevocery4_2_a = questGetQuestId("spacequest/recovery/tatooine_imperial_tier4_2_a");
            int questidDelivery4_2_b = questGetQuestId("spacequest/delivery/tatooine_imperial_tier4_2_b");
            int questidDelivery_no_pickup4_2_c = questGetQuestId("spacequest/delivery_no_pickup/tatooine_imperial_tier4_2_c");
            int questidRescue4_3_a = questGetQuestId("spacequest/rescue/tatooine_imperial_tier4_3_a");
            int questidDelivery4_3_b = questGetQuestId("spacequest/delivery/tatooine_imperial_tier4_2_b");
            int questidAssassinate4_4_a = questGetQuestId("spacequest/assassinate/tatooine_imperial_tier4_4_a");
            int questidRecovery4_4_b = questGetQuestId("spacequest/recovery/tatooine_imperial_tier4_4_b");
            int questidAssassinate4_4_c = questGetQuestId("spacequest/assassinate/tatooine_imperial_tier4_4_c");
            if (questId == questidRecovery3_1_a)
            {
                qa.completeActiveQuest(self, "spacequest/recovery/tatooine_imperial_tier3_1_a");
            }
            else if (questId == questidInspect3_1_b)
            {
                qa.completeActiveQuest(self, "spacequest/inspect/tatooine_imperial_tier3_1_b");
            }
            else if (questId == questidRescue3_1_c)
            {
                qa.completeActiveQuest(self, "spacequest/rescue/tatooine_imperial_tier3_1_c");
            }
            else if (questId == questidDelivery_no_pickup3_1_d)
            {
                boolean successCompleteDelivery_no_pickup = qa.completeActiveQuest(self, "spacequest/delivery_no_pickup/tatooine_imperial_tier3_1_d");
                if (successCompleteDelivery_no_pickup)
                {
                    space_quest.giveReward(self, "escort", "tatooine_imperial_tier3_1", 25000, "object/tangible/ship/components/weapon_capacitor/cap_mission_reward_imperial_rendili_k_class.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 100.0f);
                }
            }
            if (questId == questidDestroy_surpriseattack3_2_a)
            {
                qa.completeActiveQuest(self, "spacequest/destroy_surpriseattack/tatooine_imperial_tier3_2_a");
            }
            else if (questId == questidInspect3_2_b)
            {
                qa.completeActiveQuest(self, "spacequest/inspect/tatooine_imperial_tier3_2_b");
            }
            else if (questId == questidDelivery_no_pickup3_2_c)
            {
                qa.completeActiveQuest(self, "spacequest/delivery_no_pickup/tatooine_imperial_tier3_2_c");
            }
            else if (questId == questidPatrol3_2_d)
            {
                qa.completeActiveQuest(self, "spacequest/patrol/tatooine_imperial_tier3_2_d");
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/space_battle/tatooine_imperial_tier3_2_e", "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "inspect", "tatooine_imperial_tier3_2", 25000, "object/tangible/ship/components/droid_interface/ddi_mission_reward_imperial_sfs_military_grade.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 100.0f);
                }
            }
            if (questId == questidDestroy3_3_a)
            {
                qa.completeActiveQuest(self, "spacequest/destroy/tatooine_imperial_tier3_3_b");
            }
            else if (questId == questidEscort3_3_b)
            {
                qa.completeActiveQuest(self, "spacequest/escort/tatooine_imperial_tier3_3_a");
                qa.grantOrClearSpaceQuest(self, "spacequest/destroy_surpriseattack/tatooine_imperial_tier3_3_d", "grant");
            }
            else if (questId == questidSurvival3_3_d)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/survival/tatooine_imperial_tier3_3_c");
                if (successGrant)
                {
                    space_quest.giveReward(self, "delivery", "tatooine_imperial_tier3_3", 25000, "object/tangible/ship/components/reactor/rct_mission_reward_imperial_sds_high_output.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 100.0f);
                }
            }
            if (questId == questidRescue3_4_a)
            {
                qa.completeActiveQuest(self, "spacequest/rescue/tatooine_imperial_tier3_4_a");
            }
            else if (questId == questidInspect3_4_b)
            {
                qa.completeActiveQuest(self, "spacequest/inspect/tatooine_imperial_tier3_4_b");
            }
            else if (questId == questidAssassinate3_4_c)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_imperial_tier3_4_c");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "tatooine_imperial_tier3_4", 25000, "object/tangible/ship/components/engine/eng_mission_reward_imperial_cygnus_megadrive.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 100.0f);
                }
            }
            if (questId == questidSpace_battle4_1_b)
            {
                qa.completeActiveQuest(self, "spacequest/space_battle/tatooine_imperial_tier4_1_b");
            }
            else if (questId == questidInspect4_1_c)
            {
                qa.completeActiveQuest(self, "spacequest/inspect/tatooine_imperial_tier4_1_c");
            }
            else if (questId == questidDelivery_no_pickup4_1_d)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/delivery_no_pickup/tatooine_imperial_tier4_1_d");
                if (successGrant)
                {
                    space_quest.giveReward(self, "patrol", "tatooine_imperial_tier4_1", 10000, "object/tangible/ship/components/weapon/wpn_mission_reward_imperial_sds_boltdriver.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 150.0f);
                }
            }
            if (questId == questidRevocery4_2_a)
            {
                qa.completeActiveQuest(self, "spacequest/recovery/tatooine_imperial_tier4_2_a");
            }
            else if (questId == questidDelivery4_2_b)
            {
                qa.completeActiveQuest(self, "spacequest/delivery/tatooine_imperial_tier4_2_b");
            }
            else if (questId == questidDelivery_no_pickup4_2_c)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/delivery_no_pickup/tatooine_imperial_tier4_2_c");
                if (successGrant)
                {
                    space_quest.giveReward(self, "recovery", "tatooine_imperial_tier4_2", 10000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_cygnus_holoscreen.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 150.0f);
                }
            }
            if (questId == questidRescue4_3_a)
            {
                qa.completeActiveQuest(self, "spacequest/rescue/tatooine_imperial_tier4_3_a");
            }
            else if (questId == questidDelivery4_3_b)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/space_battle/tatooine_imperial_tier4_3_b");
                if (successGrant)
                {
                    space_quest.giveReward(self, "escort", "tatooine_imperial_tier4_3", 10000, "object/tangible/ship/components/armor/arm_mission_reward_imperial_rss_special_durasteel.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 150.0f);
                }
            }
            if (questId == questidAssassinate4_4_a)
            {
                qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_imperial_tier4_4_a");
            }
            else if (questId == questidRecovery4_4_b)
            {
                qa.completeActiveQuest(self, "spacequest/recovery/tatooine_imperial_tier4_4_b");
            }
            else if (questId == questidAssassinate4_4_c)
            {
                boolean successGrant = qa.completeActiveQuest(self, "spacequest/assassinate/tatooine_imperial_tier4_4_c");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "tatooine_imperial_tier4_4", 10000, "object/tangible/ship/components/reactor/rct_mission_reward_imperial_rss_advanced_military.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 150.0f);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int delay(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleImperialPilotMainMenuOptions(obj_id self, dictionary params) throws InterruptedException
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
                space_flags.setSpaceTrack(self, space_flags.IMPERIAL_TATOOINE);
                skill.noisyGrantSkill(self, "pilot_imperial_navy_novice");
                messageTo(self, "delay", null, 6, false);
                if (space_quest.canGrantNewbieShip(self) && hasSkill(self, "pilot_imperial_navy_novice"))
                {
                    space_quest.grantNewbieShip(self, "imperial");
                }
            }
            if (step >= COMPLETE_FIRST_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FIRST_MISSION], "grant");
                if (successGrant)
                {
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 25.0f);
                    space_quest.giveReward(self, "destroy_surpriseattack", "tatooine_imperial_1", 100);
                }
            }
            if (step >= COMPLETE_SECOND_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SECOND_MISSION], "grant");
                if (successGrant)
                {
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 50.0f);
                    space_quest.giveReward(self, "destroy", "tatooine_imperial_2", 200);
                }
            }
            if (step >= COMPLETE_THIRD_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_THIRD_MISSION], "grant");
                if (successGrant)
                {
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 50.0f);
                    space_quest.giveReward(self, "escort", "tatooine_imperial_3", 500, "object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff");
                    space_quest.giveReward(self, "escort", "tatooine_imperial_3", 500, "object/tangible/wearables/bandolier/double_bandolier.iff");
                    space_quest.giveReward(self, "escort", "tatooine_imperial_3", 500, "object/tangible/wearables/bandolier/ith_double_bandolier.iff");
                }
            }
            if (step >= COMPLETE_FOURTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FOURTH_MISSION], "grant");
                if (successGrant)
                {
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 75.0f);
                    space_quest.giveReward(self, "assassinate", "tatooine_imperial_4", 1000, "object/tangible/wearables/helmet/helmet_tie_fighter.iff");
                }
            }
            if (step >= GET_FIRST_TIER_SKILLS)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][GET_FIRST_TIER_SKILLS]);
                obj_id pInv = utils.getInventoryContainer(self);
                if (!isIdNull(pInv))
                {
                    obj_id authorizationTier1 = createObjectOverloaded("object/tangible/space/mission_objects/transfer_auth.iff", pInv);
                }
            }
            if (step >= COMPLETE_FIFTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_FIFTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "inspect", "imperial_ss_1", 5000, "object/tangible/ship/components/weapon/wpn_mission_reward_imperial_cygnus_starblaster.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 75.0f);
                }
            }
            if (step >= COMPLETE_SIXTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SIXTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "recovery", "imperial_ss_2", 5000, "object/tangible/ship/components/armor/arm_mission_reward_imperial_sfs_light_military.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 75.0f);
                }
            }
            if (step >= TRAIN_NAVAL_PILOT_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PILOT_2]);
            }
            if (step >= COMPLETE_SEVENTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_SEVENTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "assassinate", "imperial_ss_3", 5000, "object/tangible/ship/components/booster/bst_mission_reward_imperial_rss_ion_booster.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 75.0f);
                    space_flags.removeSpaceFlag(self, "ss");
                }
            }
            if (step >= TRAIN_NAVAL_WEAPONS_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_2]);
            }
            if (step >= COMPLETE_EIGHTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_EIGHTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "inspect", "imperial_ss_4", 5000, "object/tangible/ship/components/shield_generator/shd_mission_reward_imperial_rendili_dual_projector.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 75.0f);
                }
            }
            if (step >= COMPLETE_NINTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_NINTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "escort", "imperial_ss_5", 5000);
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 50.0f);
                }
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_AND_DROID_2)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_AND_DROID_2]);
            }
            if (step >= COMPLETE_TENTH_MISSION)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_TENTH_MISSION], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "recovery", "imperial_ss_6", 5000);
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 50.0f);
                }
            }
            if (step >= COMPLETE_TIER_3)
            {
                boolean successGrantEscort = qa.grantOrClearSpaceQuest(self, "spacequest/escort/tatooine_imperial_tier3_1", "grant");
            }
            if (step >= TRAIN_NAVAL_STARSHIPS_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_STARSHIPS_3]);
            }
            if (step >= COMPLETE_TIER_3_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/inspect/tatooine_imperial_tier3_2", "grant");
            }
            if (step >= TRAIN_NAVAL_WEAPONS_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_3]);
            }
            if (step >= COMPLETE_TIER_3_3)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/delivery/tatooine_imperial_tier3_3", "grant");
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_3)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_3]);
            }
            if (step >= COMPLETE_TIER_3_4)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/assassinate/tatooine_imperial_tier3_4", "grant");
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
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/patrol/tatooine_imperial_tier4_1", "grant");
                if (successGrant)
                {
                    qa.grantOrClearSpaceQuest(self, "spacequest/destroy_surpriseattack/tatooine_imperial_tier4_1_a", "grant");
                }
            }
            if (step >= TRAIN_NAVAL_WEAPONS_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_WEAPONS_4]);
            }
            if (step >= COMPLETE_TIER_4_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/recovery/tatooine_imperial_tier4_2", "grant");
            }
            if (step >= TRAIN_NAVAL_PROCEDURES_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_PROCEDURES_4]);
            }
            if (step >= COMPLETE_TIER_4_3)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/escort/tatooine_imperial_tier4_3", "grant");
            }
            if (step >= TRAIN_NAVAL_DROIDS_4)
            {
                boolean successGrant = qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][TRAIN_NAVAL_DROIDS_4]);
            }
            if (step >= COMPLETE_TIER_4_4)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/assassinate/tatooine_imperial_tier4_4", "grant");
            }
            if (step >= COMPLETE_MASTER_1)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, MAIN_TOOL_MENU[1][COMPLETE_MASTER_1], "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy", "master_imperial_1", 25000, "object/tangible/wearables/jacket/jacket_ace_imperial.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 200.0f);
                }
            }
            if (step >= COMPLETE_MASTER_2)
            {
                boolean successGrant = qa.grantOrClearSpaceQuest(self, "spacequest/destroy/master_imperial_2", "grant");
                if (successGrant)
                {
                    space_quest.giveReward(self, "destroy", "master_imperial_2", 50000, "object/tangible/wearables/helmet/helmet_fighter_imperial_ace.iff");
                    factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 400.0f);
                    obj_id item = static_item.createNewItemFunction("item_quest_reward_imperial_pilot_medal_01_01", self);
                    string_id name = new string_id("static_item_n", "item_quest_reward_imperial_pilot_medal_01_01");
                    prose_package pp = new prose_package();
                    pp = prose.setStringId(pp, new string_id("quest/ground/system_message", "placed_in_inventory"));
                    pp = prose.setTO(pp, name);
                    sendQuestSystemMessage(self, pp);
                    qa.grantPilotSkill(self, MAIN_TOOL_MENU[1][COMPLETE_MASTER_2]);
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
        qa.refreshMenu(self, TOOL_PROMPT, TOOL_TITLE, MAIN_TOOL_MENU, "handleImperialPilotMainMenuOptions", true, SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu");
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
