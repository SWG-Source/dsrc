package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.util.Arrays;
import java.util.Vector;
import java.util.HashSet;
import script.library.create;
import script.library.qa;
import script.library.skill;
import script.library.space_crafting;
import script.library.sui;
import script.library.utils;

public class qa_jtl_tools extends script.base_script
{
    public qa_jtl_tools()
    {
    }
    public static final String SCRIPTVAR = "qajtl";
    public static final String LOOT_DATATABLE = "datatables/space_loot/loot_items.iff";
    public static final String SHIP_DATATABLE = "datatables/test/qa_jtl_ships.iff";
    public static final String SHIP_ARMOR_MINMAX = "datatables/ship/components/armor.iff";
    public static final String SHIP_BOOSTER_MINMAX = "datatables/ship/components/booster.iff";
    public static final String SHIP_CAPACITOR_MINMAX = "datatables/ship/components/capacitor.iff";
    public static final String SHIP_CARGO_MINMAX = "datatables/ship/components/cargo_hold.iff";
    public static final String SHIP_DROIDINTERFACE_MINMAX = "datatables/ship/components/droid_interface.iff";
    public static final String SHIP_ENGINE_MINMAX = "datatables/ship/components/engine.iff";
    public static final String SHIP_REACTOR_MINMAX = "datatables/ship/components/reactor.iff";
    public static final String SHIP_SHIELD_MINMAX = "datatables/ship/components/shield.iff";
    public static final String SHIP_WEAPON_MINMAX = "datatables/ship/components/weapon.iff";
    public static final String TITLE = "QA JTL TOOL";
    public static final String TOOL_DESCRIPTION = "Tool for JTL specific testing needs.";
    public static final String SHIP_TOOL_DESCRIPTION = "This Tool will automatically grant the tester Master Pilot Skills based upon the faction ship selection.  The Firespray ship will auto grant the tester Neutral Master Pilot.  To add additional ships, contact Development with the ship name, pcd command and how many weapon 'slots' the ship has.";
    public static final String SHIP_TOOL_DESCRIPTION_VALID = "This Tool will automatically grant the tester Master Pilot Skills based upon the faction ship selection.  The Firespray ship will auto grant the tester Neutral Master Pilot.  To add additional ships, contact Development with the ship name, pcd command and how many weapon 'slots' the ship has.\n\nTO GET SHIP COMPONENTS USE THE SPACE LOOT OPTION IN THIS SAME TOOL.\n\nLooted ship components will be the valid components needed for space flight.  The alternative is to craft ship components with a shipwright.";
    public static final String LOOT_TOOL_DESCRIPTION = "This Tool will automatically spawn the loot selection in the player's inventory.  All applicable space component loot should have stats.  All non-ship component loot IS NOT GUARANTEED TO HAVE STATS.  If you find a piece of loot that needs statistics, please contact the tool team.";
    public static final String VALID_SHIP_TOOL_DESCRIPTION = "To create a valid ship for testing, create one of the following chassis in your inventory then use other tools to attain valid components.";
    public static final String QUEST_SHIP_TOOL_DESCRIPTION = "This tool creates a valid chassis deed that is only available to players after they have completed a RotW quest. Testers will have to attain components before the ship can be flown";
    public static final String SHIP_COMPONENTS_ARMOR_TYPE_1 = "object/tangible/ship/components/armor/arm_corellian_enhanced_elite_plastisteel.iff";
    public static final String SHIP_COMPONENTS_ARMOR_TYPE_2 = "object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff";
    public static final String SHIP_COMPONENTS_BOOSTER_TYPE_1 = "object/tangible/ship/components/booster/bst_corellian_highly_modified_elite_thrust_enhancer.iff";
    public static final String SHIP_COMPONENTS_CAPACITOR_TYPE_1 = "object/tangible/ship/components/weapon_capacitor/cap_corellian_elite.iff";
    public static final String SHIP_COMPONENTS_CAPACITOR_TYPE_2 = "object/tangible/ship/components/weapon_capacitor/cap_qualdex_conservator_qx2.iff";
    public static final String SHIP_COMPONENTS_ENGINE_TYPE_1 = "object/tangible/ship/components/engine/eng_incom_elite.iff";
    public static final String SHIP_COMPONENTS_ENGINE_TYPE_2 = "object/tangible/ship/components/engine/eng_novaldex_x6.iff";
    public static final String SHIP_COMPONENTS_REACTOR_TYPE_1 = "object/tangible/ship/components/reactor/rct_corellian_modified_bt3.iff";
    public static final String SHIP_COMPONENTS_REACTOR_TYPE_2 = "object/tangible/ship/components/reactor/rct_rss_x8.iff";
    public static final String SHIP_COMPONENTS_SHIELD_TYPE_1 = "object/tangible/ship/components/shield_generator/shd_cygnus_elite.iff";
    public static final String SHIP_COMPONENTS_SHIELD_TYPE_2 = "object/tangible/ship/components/shield_generator/shd_cygnus_mk3.iff";
    public static final String SHIP_COMPONENTS_WEAPON_TYPE_1 = "object/tangible/ship/components/weapon/wpn_hk_scorcher_3.iff";
    public static final String SHIP_COMPONENTS_WEAPON_TYPE_2 = "object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff";
    public static final String[] ERROR_MESSAGE_IN_ARRAY = 
    {
        "The list came back blank.  Please inform the tool team."
    };
    public static final int SHIP_LOOT_COMPONENTS = 0;
    public static final int MINING_COMPONENTS = 1;
    public static final int GOD_SHIPS = 2;
    public static final int VALID_SHIPS = 3;
    public static final int VALID_SHIP_LOOT = 4;
    public static final int ROTW_SHIPS = 5;
    public static final int MAKE_IMP_PILOT = 6;
    public static final int MAKE_REB_PILOT = 7;
    public static final int MAKE_NEU_PILOT = 8;
    public static final int PILOT_QUEST_TOOLS = 9;
    public static final int REVOKE_PILOT = 10;
    public static final int GOD_BONUS_SHIP = 0;
    public static final int GOD_IMP_SHIP = 1;
    public static final int GOD_NEU_SHIP = 2;
    public static final int GOD_REB_SHIP = 3;
    public static final int VALID_BONUS_SHIP = 0;
    public static final int VALID_IMP_SHIP = 1;
    public static final int VALID_NEU_SHIP = 2;
    public static final int VALID_REB_SHIP = 3;
    public static final int PILOT_WAYPOINTS = 0;
    public static final int REB_TAT_PILOT = 1;
    public static final int IMP_TAT_PILOT = 2;
    public static final int CRIMSON_PHOENIX = 0;
    public static final int STORM_SQUADRON = 1;
    public static final String PILOT_IMPERIAL_TOOL_TITLE = "Tatooine Imperial Pilot";
    public static final String PILOT_IMPERIAL_TOOL_PROMPT = "Tatooine Imperial Pilot\nSelect the pilot roadmap quest or function to complete.";
    public static final String PILOT_REBEL_TOOL_TITLE = "Tatooine Rebel Pilot";
    public static final String PILOT_REBEL_TOOL_PROMPT = "Tatooine Rebel Pilot\nSelect the pilot roadmap quest or function to complete.";
    public static final String PILOT_WAYPOINT_TITLE = "Pilot Waypoints";
    public static final String PILOT_WAYPOINT_PROMPT = "Select the pilot roadmap and receive all the pilot trainer waypoints for that roadmap.";
    public static final String[] PILOT_ROADMAP_MENU = 
    {
        "Pilot Trainer Waypoints",
        "Rebel - Crimson Phoenix Squadron - Mos Espa, Tatooine",
        "Imperial - Storm Squadron - Bestine, Tatooine"
    };
    public static final String[] PILOT_WAYPOINT_MENU = 
    {
        "Rebel - Crimson Phoenix Squadron - Mos Espa, Tatooine",
        "Imperial - Storm Squadron - Bestine, Tatooine"
    };
    public static final String[] IMPERIAL_PILOT_MAIN_TOOL_MENU = 
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
    };
    public static final String[] REBEL_PILOT_MAIN_TOOL_MENU = 
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
    };
    public static final String[] SPACE_DEED_CONST = 
    {
        "advanced_xwing_reward_deed",
        "arc170_reward_deed",
        "blacksun_vaksai_reward_deed",
        "grievous_starfighter_reward_deed",
        "jedi_starfighter_reward_deed",
        "tieinterceptor_imperial_guard_reward_deed",
        "y8_mining_ship_deed"
    };
    public static final String[] SPACE_LOOT_CATEGORIES = 
    {
        "all components",
        "armor",
        "booster",
        "droid_interface",
        "engine",
        "reactor",
        "shield_generator",
        "weapon",
        "weapon_capacitor"
    };
    public static final String[] MAIN_MENU = 
    {
        "Ship Loot Components",
        "Mining Components",
        "God Ships",
        "Valid Ship Deeds",
        "Valid Ships With Components",
        "RoTW Quest Ship Deeds",
        "Make Master Imperial Pilot",
        "Make Master Rebel Pilot",
        "Make Master Neutral Pilot",
        "Pilot Quest Tools",
        "Revoke pilot skills"
    };
    public static final String[] MINING_COMPONENTS_TEMPLATES = 
    {
        "object/tangible/ship/components/cargo_hold/crg_mining_small.iff",
        "object/tangible/ship/components/cargo_hold/crg_pob_large.iff",
        "object/tangible/ship/components/cargo_hold/crg_pob_small.iff",
        "object/tangible/ship/components/cargo_hold/crg_starfighter_large.iff",
        "object/tangible/ship/components/cargo_hold/crg_starfighter_small.iff",
        "object/tangible/ship/components/weapon/wpn_mining_laser_mk1.iff",
        "object/tangible/ship/components/weapon/wpn_mining_laser_mk2.iff",
        "object/tangible/ship/components/weapon/wpn_mining_laser_mk3.iff",
        "object/tangible/ship/components/weapon/wpn_tractor_beam_gun.iff",
        "object/tangible/ship/components/weapon/wpn_tractor_pulse_gun.iff"
    };
    public static final String[] MINING_COMPONENTS_MENU = 
    {
        "crg_mining_small",
        "crg_pob_large",
        "crg_pob_small",
        "crg_starfighter_large",
        "crg_starfighter_small",
        "wpn_mining_laser_mk1",
        "wpn_mining_laser_mk2",
        "wpn_mining_laser_mk3",
        "wpn_tractor_beam_gun",
        "wpn_tractor_pulse_gun"
    };
    public static final String[] VALID_SHIPS_COMPONENTS_MENU = 
    {
        "Tie Fighter",
        "Tie Interceptor",
        "Y Wing",
        "X Wing",
        "Hutt Heavy - Kimogila Style 1"
    };
    public static final String[] VALID_SHIPS_COMPONENTS_LIST = 
    {
        "VALID_IMP_TIE_FIGHTER_PREDEFINED",
        "VALID_IMP_TIE_INTERCEPTOR_PREDEFINED",
        "VALID_REB_YWING_PREDEFINED",
        "VALID_REB_XWING_PREDEFINED",
        "VALID_NEU_KIMO_PREDEFINED"
    };
    public static final String[] VALID_IMP_TIE_FIGHTER_PREDEFINED = 
    {
        "object/tangible/ship/components/reactor/rct_seinar_level2.iff",
        "object/tangible/ship/components/engine/eng_sfs_imperial_2.iff",
        "object/tangible/ship/components/shield_generator/shd_kse_military_mk2.iff",
        "object/tangible/ship/components/armor/arm_sfs_light_durasteel.iff",
        "object/tangible/ship/components/armor/arm_sfs_standard_durasteel.iff",
        "object/tangible/ship/components/weapon_capacitor/cap_kse_performance_mk2.iff",
        "object/tangible/ship/components/booster/bst_kde_br12.iff",
        "object/tangible/ship/components/weapon/wpn_kdy_pounder_mk1.iff"
    };
    public static final String[] VALID_IMP_TIE_INTERCEPTOR_PREDEFINED = 
    {
        "object/tangible/ship/components/reactor/rct_sfs_imperial_3.iff",
        "object/tangible/ship/components/engine/eng_sds_imperial_2.iff",
        "object/tangible/ship/components/shield_generator/shd_sfs_modified_imperial_heavy.iff",
        "object/tangible/ship/components/armor/armor_trilon_custom.iff",
        "object/tangible/ship/components/armor/armor_trilon_custom.iff",
        "object/tangible/ship/components/armor/arm_kse_limited_heavy_durasteel.iff",
        "object/tangible/ship/components/weapon_capacitor/cap_sorosuub_turbo.iff",
        "object/tangible/ship/components/booster/bst_sorosuub_liberator_mk3.iff",
        "object/tangible/ship/components/weapon/wpn_hk_scorcher.iff"
    };
    public static final String[] VALID_REB_YWING_PREDEFINED = 
    {
        "object/tangible/ship/components/reactor/rct_unknown_multicore.iff",
        "object/tangible/ship/components/engine/eng_moncal_improved_heavy.iff",
        "object/tangible/ship/components/shield_generator/shd_armek_plasma_shell_s3.iff",
        "object/tangible/ship/components/armor/arm_freitek_standard.iff",
        "object/tangible/ship/components/armor/arm_freitek_standard.iff",
        "object/tangible/ship/components/droid_interface/ddi_freitek_modified_droid_commander_mk1.iff",
        "object/tangible/ship/components/weapon_capacitor/cap_incom_bjn825.iff",
        "object/tangible/ship/components/booster/bst_subpro_enhanced_accelatron_mk2.iff",
        "object/tangible/ship/components/weapon/wpn_incom_tt8.iff",
        "object/tangible/ship/components/weapon/wpn_incom_tt8.iff"
    };
    public static final String[] VALID_REB_XWING_PREDEFINED = 
    {
        "object/tangible/ship/components/reactor/rct_koensayr_supernova_advanced.iff",
        "object/tangible/ship/components/engine/eng_moncal_ifs32.iff",
        "object/tangible/ship/components/shield_generator/shd_mandalor_flexshield_ks18.iff",
        "object/tangible/ship/components/armor/arm_kse_supreme_durasteel.iff",
        "object/tangible/ship/components/armor/arm_kse_supreme_durasteel.iff",
        "object/tangible/ship/components/weapon_capacitor/cap_kse_mk4.iff",
        "object/tangible/ship/components/booster/bst_koensayr_heavy.iff",
        "object/tangible/ship/components/weapon/wpn_armek_sw4.iff",
        "object/tangible/ship/components/weapon/wpn_armek_sw4.iff",
        "object/tangible/ship/components/weapon/wpn_hk_scorcher.iff"
    };
    public static final String[] VALID_NEU_KIMO_PREDEFINED = 
    {
        "object/tangible/ship/components/reactor/rct_incom_mark3.iff",
        "object/tangible/ship/components/engine/eng_moncal_heavy.iff",
        "object/tangible/ship/components/shield_generator/shd_koensayr_deflector_m2.iff",
        "object/tangible/ship/components/armor/arm_kse_standard_durasteel.iff",
        "object/tangible/ship/components/armor/arm_kse_standard_durasteel.iff",
        "object/tangible/ship/components/weapon_capacitor/cap_qualdex_conservator_qx2.iff",
        "object/tangible/ship/components/booster/bst_koensayr_standard.iff",
        "object/tangible/ship/components/weapon/wpn_mandal_qv3_disruptor.iff",
        "object/tangible/ship/components/weapon/wpn_subpro_tricannon.iff",
        "object/tangible/ship/components/weapon/wpn_kse_double_blaster.iff"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_jtl_tools");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_jtl_tools");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                toolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int mainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    String[] tool_options = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
                    qa.refreshMenu(self, "Choose the tool you want to use", "QA Tools", tool_options, "toolMainMenu", true, "qatool.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".mainMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        removePlayer(self, "There was a menu index error.  The tool failed.");
                        toolMainMenu(self);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (idx)
                        {
                            case SHIP_LOOT_COMPONENTS:
                            qa.refreshMenu(player, "Select a Space Loot Category", TITLE, SPACE_LOOT_CATEGORIES, "handleLootCategories", SCRIPTVAR + ".pid", SCRIPTVAR + ".lootCategories", sui.OK_CANCEL_REFRESH);
                            break;
                            case MINING_COMPONENTS:
                            qa.refreshMenu(player, "Select a Mining Component", TITLE, MINING_COMPONENTS_MENU, "handleMiningComponents", SCRIPTVAR + ".pid", SCRIPTVAR + ".miningComponents", sui.OK_CANCEL_REFRESH);
                            break;
                            case GOD_SHIPS:
                            String[] mainMenuArray = qa.populateArray(self, "SHIP_FACTION", SHIP_DATATABLE);
                            qa.refreshMenu(self, SHIP_TOOL_DESCRIPTION, TITLE, mainMenuArray, "godShipMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".godShip", sui.OK_CANCEL_REFRESH);
                            break;
                            case VALID_SHIPS:
                            String[] validChassisMenuArray = qa.populateArray(self, "SHIP_FACTION", SHIP_DATATABLE);
                            qa.refreshMenu(self, SHIP_TOOL_DESCRIPTION_VALID, TITLE, validChassisMenuArray, "validChassisMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".validChassis", sui.OK_CANCEL_REFRESH);
                            break;
                            case VALID_SHIP_LOOT:
                            qa.refreshMenu(self, "Select a Ship Type.  Looted components will be provided.\n\nNote: Sometimes component stats will exceed chassis mass, reactor output may be too low, etc.  You may have to repeat this process a few times to get components to work properly.", TITLE, VALID_SHIPS_COMPONENTS_MENU, "predefinedShips", SCRIPTVAR + ".pid", SCRIPTVAR + ".predefinedShips", sui.OK_CANCEL_REFRESH);
                            break;
                            case ROTW_SHIPS:
                            qa.refreshMenu(self, "Select a RotW Expansion Ship Deed", TITLE, SPACE_DEED_CONST, "spaceDeedHandler", SCRIPTVAR + ".pid", SCRIPTVAR + ".spaceQuestDeeds", sui.OK_CANCEL_REFRESH);
                            break;
                            case MAKE_IMP_PILOT:
                            boolean impSuccess = qa.revokeAndGrantPilot(self, "Imperial Ships");
                            if (impSuccess)
                            {
                                toolMainMenu(self);
                            }
                            else 
                            {
                                removePlayer(self, "The tool failed to revoke or grant the requested permission. Exiting.");
                            }
                            break;
                            case MAKE_REB_PILOT:
                            boolean rebSuccess = qa.revokeAndGrantPilot(self, "Rebel Ships");
                            if (rebSuccess)
                            {
                                toolMainMenu(self);
                            }
                            else 
                            {
                                removePlayer(self, "The tool failed to revoke or grant the requested permission. Exiting.");
                            }
                            break;
                            case MAKE_NEU_PILOT:
                            boolean neuSuccess = qa.revokeAndGrantPilot(self, "Neutral/Freelancer Ships");
                            if (neuSuccess)
                            {
                                toolMainMenu(self);
                            }
                            else 
                            {
                                removePlayer(self, "The tool failed to revoke or grant the requested permission. Exiting.");
                            }
                            break;
                            case PILOT_QUEST_TOOLS:
                            qa.refreshMenu(self, "Select a Pilot Roadmap", TITLE, PILOT_ROADMAP_MENU, "pilotQuestRoadmaps", SCRIPTVAR + ".pid", SCRIPTVAR + ".pilotRoadmap", sui.OK_CANCEL_REFRESH);
                            break;
                            case REVOKE_PILOT:
                            qa.revokePilotingSkills(self);
                            sendSystemMessageTestingOnly(self, "Pilot Skills revoked");
                            toolMainMenu(self);
                            break;
                            default:
                            sendSystemMessageTestingOnly(player, "");
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int pilotQuestRoadmaps(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".pilotRoadmap");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        removePlayer(self, "There was a menu index error. The tool failed");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (idx)
                        {
                            case PILOT_WAYPOINTS:
                            qa.refreshMenu(self, PILOT_WAYPOINT_PROMPT, PILOT_WAYPOINT_TITLE, PILOT_WAYPOINT_MENU, "handleWaypointOptions", true, "pilotwaypoint.pid", "pilotwaypoint.mainMenu");
                            break;
                            case REB_TAT_PILOT:
                            attachScript(self, "test.qa_pilot_roadmap_tatooine_rebel");
                            utils.setScriptVar(self, "pilotrebtat.useTrigger", true);
                            qa.refreshMenu(self, PILOT_REBEL_TOOL_PROMPT, PILOT_REBEL_TOOL_TITLE, REBEL_PILOT_MAIN_TOOL_MENU, "handleRebelPilotMainMenuOptions", true, "pilotrebtat.pid", "pilotrebtat.mainMenu");
                            break;
                            case IMP_TAT_PILOT:
                            attachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
                            utils.setScriptVar(self, "pilotimptat.useTrigger", true);
                            qa.refreshMenu(self, PILOT_IMPERIAL_TOOL_PROMPT, PILOT_IMPERIAL_TOOL_TITLE, IMPERIAL_PILOT_MAIN_TOOL_MENU, "handleImperialPilotMainMenuOptions", true, "pilotimptat.pid", "pilotimptat.mainMenu");
                            break;
                            default:
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWaypointOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, "pilotwaypoint.mainMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        removePlayer(self, "There was a menu index error. The tool failed");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (idx)
                        {
                            case CRIMSON_PHOENIX:
                            boolean rebTatWaypointFunctionComplete = getWaypoint(self, "rebtat");
                            if (rebTatWaypointFunctionComplete)
                            {
                                sendSystemMessageTestingOnly(self, "Waypoints issued");
                                toolMainMenu(self);
                            }
                            break;
                            case STORM_SQUADRON:
                            boolean impTatWaypointFunctionComplete = getWaypoint(self, "imptat");
                            if (impTatWaypointFunctionComplete)
                            {
                                sendSystemMessageTestingOnly(self, "Waypoints issued");
                                toolMainMenu(self);
                            }
                            break;
                            default:
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int predefinedShips(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".predefinedShips");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    String componentListName = VALID_SHIPS_COMPONENTS_LIST[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        String[] shipChassisSelection = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "VALID_SHIP_NAME", "CHASSIS_TEMPLATE");
                        String shipChassisSelected = shipChassisSelection[0];
                        int shipRow = dataTableSearchColumnForString("" + shipChassisSelected, "CHASSIS_TEMPLATE", SHIP_DATATABLE);
                        String factionType = dataTableGetString(SHIP_DATATABLE, shipRow, "SHIP_FACTION");
                        obj_id inv = utils.getInventoryContainer(self);
                        String type = dataTableGetString(SHIP_DATATABLE, shipRow, "SHIP_STRING");
                        float mass = dataTableGetFloat(SHIP_DATATABLE, shipRow, "CHASSIS_MASS");
                        float hp = dataTableGetFloat(SHIP_DATATABLE, shipRow, "CHASSIS_HP");
                        space_crafting.createDeedFromBlueprints(player, type, inv, mass, hp);
                        String nameOfComponentListBasedOnTesterChoice = "";
                        Vector tempComponentList = new Vector();
                        tempComponentList.setSize(0);
                        obj_id testerInventory = utils.getInventoryContainer(player);
                        for (int i = 0; i < VALID_SHIPS_COMPONENTS_LIST.length; i++)
                        {
                            if (componentListName.equals("VALID_IMP_TIE_FIGHTER_PREDEFINED"))
                            {
                                for (int a = 0; a < VALID_IMP_TIE_FIGHTER_PREDEFINED.length; a++)
                                {
                                    qa.templateObjectSpawner(player, VALID_IMP_TIE_FIGHTER_PREDEFINED[a]);
                                }
                                break;
                            }
                            else if (componentListName.equals("VALID_IMP_TIE_INTERCEPTOR_PREDEFINED"))
                            {
                                for (int a = 0; a < VALID_IMP_TIE_INTERCEPTOR_PREDEFINED.length; a++)
                                {
                                    qa.templateObjectSpawner(player, VALID_IMP_TIE_INTERCEPTOR_PREDEFINED[a]);
                                }
                                break;
                            }
                            else if (componentListName.equals("VALID_REB_YWING_PREDEFINED"))
                            {
                                for (int a = 0; a < VALID_REB_YWING_PREDEFINED.length; a++)
                                {
                                    qa.templateObjectSpawner(player, VALID_REB_YWING_PREDEFINED[a]);
                                }
                                break;
                            }
                            else if (componentListName.equals("VALID_REB_XWING_PREDEFINED"))
                            {
                                for (int a = 0; a < VALID_REB_XWING_PREDEFINED.length; a++)
                                {
                                    qa.templateObjectSpawner(player, VALID_REB_XWING_PREDEFINED[a]);
                                }
                                break;
                            }
                            else if (componentListName.equals("VALID_NEU_KIMO_PREDEFINED"))
                            {
                                for (int a = 0; a < VALID_NEU_KIMO_PREDEFINED.length; a++)
                                {
                                    qa.templateObjectSpawner(player, VALID_NEU_KIMO_PREDEFINED[a]);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMiningComponents(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".miningComponents");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    String selectedTemplate = MINING_COMPONENTS_TEMPLATES[idx];
                    if (!previousSelection.equals(""))
                    {
                        obj_id testerInventory = utils.getInventoryContainer(self);
                        qa.templateObjectSpawner(self, selectedTemplate);
                        qa.refreshMenu(player, "Select a Mining Component", TITLE, MINING_COMPONENTS_MENU, "handleMiningComponents", SCRIPTVAR + ".pid", SCRIPTVAR + ".miningComponents", sui.OK_CANCEL_REFRESH);
                    }
                    else 
                    {
                        removePlayer(self, "There was an index error.  The tool failed.");
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int godShipMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".godShip");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (idx)
                        {
                            case GOD_BONUS_SHIP:
                            String[] bonusShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "GOD_SHIP_NAME");
                            if (bonusShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION, TITLE, bonusShipArray, "handleShipOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousShip", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case GOD_IMP_SHIP:
                            String[] imperialShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "GOD_SHIP_NAME");
                            if (imperialShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION, TITLE, imperialShipArray, "handleShipOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousShip", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case GOD_NEU_SHIP:
                            String[] neutralShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "GOD_SHIP_NAME");
                            if (neutralShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION, TITLE, neutralShipArray, "handleShipOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousShip", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case GOD_REB_SHIP:
                            String[] rebelShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "GOD_SHIP_NAME");
                            if (rebelShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION, TITLE, rebelShipArray, "handleShipOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousShip", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            default:
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int validChassisMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".validChassis");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        removePlayer(self, "There was a menu index error. The tool failed");
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        switch (idx)
                        {
                            case VALID_BONUS_SHIP:
                            String[] bonusShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "VALID_SHIP_NAME");
                            if (bonusShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION_VALID, TITLE, bonusShipArray, "handleChassisOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousChassis", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case VALID_IMP_SHIP:
                            String[] imperialShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "VALID_SHIP_NAME");
                            if (imperialShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION_VALID, TITLE, imperialShipArray, "handleChassisOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousChassis", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case VALID_NEU_SHIP:
                            String[] neutralShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "VALID_SHIP_NAME");
                            if (neutralShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION_VALID, TITLE, neutralShipArray, "handleChassisOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousChassis", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            case VALID_REB_SHIP:
                            String[] rebelShipArray = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "SHIP_FACTION", "VALID_SHIP_NAME");
                            if (rebelShipArray.length < 1)
                            {
                                sendSystemMessageTestingOnly(player, "SUI creation failed.");
                            }
                            else 
                            {
                                qa.refreshMenu(player, SHIP_TOOL_DESCRIPTION_VALID, TITLE, rebelShipArray, "handleChassisOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".previousChassis", sui.OK_CANCEL_REFRESH);
                            }
                            break;
                            default:
                            removePlayer(self, "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleShipOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".previousShip");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        String[] shipSelection = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "GOD_SHIP_NAME", "SHIP_STRING");
                        String shipSelected = shipSelection[0];
                        int shipRow = dataTableSearchColumnForString("" + shipSelected, "SHIP_STRING", SHIP_DATATABLE);
                        String factionType = dataTableGetString(SHIP_DATATABLE, shipRow, "SHIP_FACTION");
                        int weaponSlots = dataTableGetInt(SHIP_DATATABLE, shipRow, "WEAPON_SLOTS");
                        obj_id datapad = utils.getDatapad(self);
                        obj_id inv = utils.getInventoryContainer(self);
                        grantShip(self, shipSelected, weaponSlots, true);
                        String[] mainMenuArray = qa.populateArray(self, "SHIP_FACTION", SHIP_DATATABLE);
                        qa.refreshMenu(self, SHIP_TOOL_DESCRIPTION, TITLE, mainMenuArray, "godShipMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".godShip", sui.OK_CANCEL_REFRESH);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleChassisOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".previousChassis");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        String[] shipChassisSelection = qa.populateArray(player, SHIP_DATATABLE, previousSelection, "VALID_SHIP_NAME", "CHASSIS_TEMPLATE");
                        String shipChassisSelected = shipChassisSelection[0];
                        int shipRow = dataTableSearchColumnForString("" + shipChassisSelected, "CHASSIS_TEMPLATE", SHIP_DATATABLE);
                        String factionType = dataTableGetString(SHIP_DATATABLE, shipRow, "SHIP_FACTION");
                        obj_id inv = utils.getInventoryContainer(self);
                        String type = dataTableGetString(SHIP_DATATABLE, shipRow, "SHIP_STRING");
                        float mass = dataTableGetFloat(SHIP_DATATABLE, shipRow, "CHASSIS_MASS");
                        float hp = dataTableGetFloat(SHIP_DATATABLE, shipRow, "CHASSIS_HP");
                        obj_id pcd = space_crafting.createDeedFromBlueprints(player, type, inv, mass, hp);
                        String[] validChassisMenuArray = qa.populateArray(self, "SHIP_FACTION", SHIP_DATATABLE);
                        qa.refreshMenu(self, SHIP_TOOL_DESCRIPTION_VALID, TITLE, validChassisMenuArray, "validChassisMenuOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".validChassis", sui.OK_CANCEL_REFRESH);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleLootCategories(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".lootCategories");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (!previousSelection.equals(""))
                    {
                        String menuArray[] = createLootCategroies(player, previousSelection);
                        qa.refreshMenu(player, LOOT_TOOL_DESCRIPTION, TITLE, menuArray, "spawnLootItem", SCRIPTVAR + ".pid", SCRIPTVAR + ".lootItem", sui.OK_CANCEL_REFRESH);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnLootItem(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".lootItem");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        qa.templateObjectSpawner(player, previousSelection);
                        qa.refreshMenu(player, LOOT_TOOL_DESCRIPTION, TITLE, previousMainMenuArray, "spawnLootItem", SCRIPTVAR + ".pid", SCRIPTVAR + ".lootItem", sui.OK_CANCEL_REFRESH);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spaceDeedHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".spaceQuestDeeds");
                if (btn == sui.BP_CANCEL)
                {
                    removePlayer(self, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    removePlayer(self, "");
                    toolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals(""))
                    {
                        sendSystemMessageTestingOnly(player, "There was a menu index error. Script failed.");
                        utils.removeScriptVarTree(player, SCRIPTVAR);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        String deedString = "object/tangible/ship/crafted/chassis/" + previousSelection + ".iff";
                        qa.templateObjectSpawner(player, deedString);
                        qa.refreshMenu(player, "Select a RotW Expansion Ship Deed", TITLE, SPACE_DEED_CONST, "spaceDeedHandler", SCRIPTVAR + ".pid", SCRIPTVAR + ".spaceQuestDeeds", sui.OK_CANCEL_REFRESH);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id self) throws InterruptedException
    {
        qa.refreshMenu(self, TOOL_DESCRIPTION, TITLE, MAIN_MENU, "mainMenuOptions", true, SCRIPTVAR + ".pid", SCRIPTVAR + ".mainMenu");
    }
    public void removePlayer(obj_id self, String err) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, err);
        qa.removeScriptVars(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, SCRIPTVAR);
        utils.removeScriptVarTree(self, "pilotrebtat");
        utils.removeScriptVarTree(self, "pilotimptat");
        utils.removeScriptVarTree(self, "pilotwaypoint");
        detachScript(self, "test.qa_pilot_roadmap_tatooine_rebel");
        detachScript(self, "test.qa_pilot_roadmap_tatooine_imperial");
    }
    public void grantShip(obj_id player, String shipName, int weaponSlots, boolean godShip) throws InterruptedException
    {
        obj_id datapad = utils.getDatapad(player);
        obj_id inv = utils.getInventoryContainer(player);
        obj_id pcd = createObject("object/intangible/ship/" + shipName + "_pcd.iff", datapad, "");
        obj_id ship = createObject("object/ship/player/player_" + shipName + ".iff", pcd, "");
        setName(pcd, "[QA] " + localize(getNameStringId(pcd)));
        sendSystemMessageTestingOnly(player, "About to set chassis max mass.");
        setChassisComponentMassMaximum(ship, 210000.0f);
        setOwner(ship, player);
        sendSystemMessageTestingOnly(player, "About to unload all components.");
        if (isIdValid(ship))
        {
            obj_id objInventory = utils.getInventoryContainer(player);
            for (int i = 0; i < ship_chassis_slot_type.SCST_num_types; i++)
            {
                if (isShipSlotInstalled(ship, i))
                {
                    obj_id kill = shipUninstallComponent(player, ship, i, objInventory);
                    destroyObject(kill);
                }
                else 
                {
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Ship not valid.");
        }
        sendSystemMessageTestingOnly(player, "Components unloaded.");
        obj_id armor1 = createObjectOverloaded(SHIP_COMPONENTS_ARMOR_TYPE_2, inv);
        obj_id armor2 = createObjectOverloaded(SHIP_COMPONENTS_ARMOR_TYPE_2, inv);
        obj_id booster = createObjectOverloaded(SHIP_COMPONENTS_BOOSTER_TYPE_1, inv);
        obj_id capacitor = createObjectOverloaded(SHIP_COMPONENTS_CAPACITOR_TYPE_2, inv);
        obj_id engine = createObjectOverloaded(SHIP_COMPONENTS_ENGINE_TYPE_2, inv);
        obj_id reactor = createObjectOverloaded(SHIP_COMPONENTS_REACTOR_TYPE_2, inv);
        obj_id shield = createObjectOverloaded(SHIP_COMPONENTS_SHIELD_TYPE_2, inv);
        sendSystemMessageTestingOnly(player, "About to add weapons");
        if (weaponSlots >= 1)
        {
            obj_id weapon1 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_2, inv);
            setName(weapon1, "[QA] " + localize(getNameStringId(weapon1)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon1);
        }
        if (weaponSlots >= 2)
        {
            obj_id weapon2 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_1, inv);
            setName(weapon2, "[QA] " + localize(getNameStringId(weapon2)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon2);
        }
        if (weaponSlots >= 3)
        {
            obj_id weapon3 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_2, inv);
            setName(weapon3, "[QA] " + localize(getNameStringId(weapon3)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_2, weapon3);
        }
        if (weaponSlots >= 4)
        {
            obj_id weapon4 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_1, inv);
            setName(weapon4, "[QA] " + localize(getNameStringId(weapon4)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_3, weapon4);
        }
        if (weaponSlots >= 5)
        {
            obj_id weapon5 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_2, inv);
            setName(weapon5, "[QA] " + localize(getNameStringId(weapon5)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_4, weapon5);
        }
        if (weaponSlots >= 6)
        {
            obj_id weapon6 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_1, inv);
            setName(weapon6, "[QA] " + localize(getNameStringId(weapon6)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_5, weapon6);
        }
        if (weaponSlots >= 7)
        {
            obj_id weapon7 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_2, inv);
            setName(weapon7, "[QA] " + localize(getNameStringId(weapon7)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_6, weapon7);
        }
        if (weaponSlots >= 8)
        {
            obj_id weapon8 = createObjectOverloaded(SHIP_COMPONENTS_WEAPON_TYPE_1, inv);
            setName(weapon8, "[QA] " + localize(getNameStringId(weapon8)));
            shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_7, weapon8);
        }
        sendSystemMessageTestingOnly(player, "weapons installed");
        if (godShip == true)
        {
            setObjVar(armor1, "ship_comp.armor_hitpoints_maximum", 20000.0f);
            setObjVar(armor1, "ship_comp.armor_hitpoints_current", 20000.0f);
            setObjVar(armor1, "ship_comp.mass", 1.0f);
            setName(armor1, "[QA] " + localize(getNameStringId(armor1)));
            setObjVar(armor2, "ship_comp.armor_hitpoints_maximum", 20000.0f);
            setObjVar(armor2, "ship_comp.armor_hitpoints_current", 20000.0f);
            setObjVar(armor2, "ship_comp.mass", 1.0f);
            setName(armor2, "[QA] " + localize(getNameStringId(armor2)));
            setObjVar(booster, "ship_comp.booster.energy_maximum", 10000.0f);
            setObjVar(booster, "ship_comp.booster.speed_maximum", 100.0f);
            setObjVar(booster, "ship_comp.mass", 1.0f);
            setName(booster, "[QA] " + localize(getNameStringId(booster)));
            setObjVar(capacitor, "ship_comp.capacitor.energy_maximum", 10000.0f);
            setObjVar(capacitor, "ship_comp.capacitor.energy_recharge_rate", 1000.0f);
            setObjVar(capacitor, "ship_comp.mass", 1.0f);
            setName(capacitor, "[QA] " + localize(getNameStringId(capacitor)));
            setObjVar(engine, "ship_comp.engine.speed_maximum", 50.0f);
            setObjVar(engine, "ship_comp.mass", 1.0f);
            setName(engine, "[QA] " + localize(getNameStringId(engine)));
            setObjVar(reactor, "ship_comp.reactor.energy_generation_rate", 200000.0f);
            setObjVar(reactor, "ship_comp.mass", 1.0f);
            setName(reactor, "[QA] " + localize(getNameStringId(reactor)));
            setObjVar(shield, "ship_comp.shield.hitpoints_back_maximum", 2000.0f);
            setObjVar(shield, "ship_comp.shield.hitpoints_back_current", 2000.0f);
            setObjVar(shield, "ship_comp.shield.hitpoints_front_maximum", 2000.0f);
            setObjVar(shield, "ship_comp.shield.hitpoints_front_current", 2000.0f);
            setObjVar(shield, "ship_comp.mass", 1.0f);
            setName(shield, "[QA] " + localize(getNameStringId(shield)));
            sendSystemMessageTestingOnly(player, "All non weapon component attributes set");
        }
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_0, armor1);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_1, armor2);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_booster, booster);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_capacitor, capacitor);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_engine, engine);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_reactor, reactor);
        shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_shield_0, shield);
        CustomerServiceLog("qaTool", "User: (" + player + ") " + getName(player) + " has spawned a ship (" + ship + ") using the QA Space Tool.");
        sendSystemMessageTestingOnly(player, "Non-weapon components installed");
    }
    public String[] createLootCategroies(obj_id player, String categoryString) throws InterruptedException
    {
        Vector shipComponentVector = new Vector();
        Vector specificComponentVector = new Vector();
        String preAppend = "object/tangible/ship/components/";
        if (categoryString.indexOf("all ") > -1)
        {
            sendSystemMessageTestingOnly(player, "Please wait. Component list is long!");
        }
        int totalColumnsInDataTable = dataTableGetNumColumns(LOOT_DATATABLE);
        String[] allCellsInTable = returnListOfCellsInTable(player, totalColumnsInDataTable, LOOT_DATATABLE);
        for (int i = 0; i < allCellsInTable.length; i++)
        {
            if (allCellsInTable[i].indexOf("/ship/") > -1)
            {
                shipComponentVector.add(allCellsInTable[i]);
            }
        }
        if (shipComponentVector.size() > 0)
        {
            String[] allComponents = new String[shipComponentVector.size()];
            shipComponentVector.toArray(allComponents);
            Arrays.sort(allComponents);
            if (categoryString.indexOf("all ") > -1)
            {
                return allComponents;
            }
            else 
            {
                for (int x = 0; x < allComponents.length; x++)
                {
                    if (allComponents[x].startsWith(preAppend + categoryString + "/") == true)
                    {
                        specificComponentVector.add(allComponents[x]);
                    }
                }
                if (specificComponentVector.size() > 0)
                {
                    String[] componentArray = new String[specificComponentVector.size()];
                    specificComponentVector.toArray(componentArray);
                    Arrays.sort(componentArray);
                    return componentArray;
                }
            }
        }
        return ERROR_MESSAGE_IN_ARRAY;
    }
    public String[] returnListOfCellsInTable(obj_id player, int totalCols, String datatableName) throws InterruptedException
    {
        HashSet theList = new HashSet();
        for (int i = 0; i < totalCols; i++)
        {
            String[] arrayList = dataTableGetStringColumn(datatableName, i);
            if (arrayList.length <= 0)
            {
                sendSystemMessageTestingOnly(player, "Tool Not Functioning because the Datatable Rows equal ZERO!");
                return null;
            }
            int listingLength = arrayList.length;
            for (int y = 0; y < listingLength; y++)
            {
                if (!arrayList[y].equals("") && arrayList[y] != null)
                {
                    theList.add(arrayList[y]);
                }
            }
        }
        String[] theListToArray = new String[theList.size()];
        theList.toArray(theListToArray);
        Arrays.sort(theListToArray);
        return theListToArray;
    }
    public String[] getWaypointNames(obj_id self) throws InterruptedException
    {
        obj_id[] waypoints = getWaypointsInDatapad(self);
        if ((waypoints != null) && (waypoints.length > 0))
        {
            String[] waypointNameList = new String[waypoints.length];
            for (int i = 0; i < waypoints.length; i++)
            {
                waypointNameList[i] = getWaypointName(waypoints[i]);
            }
            return waypointNameList;
        }
        return null;
    }
    public boolean foundWaypointName(obj_id self, String waypointSearchName) throws InterruptedException
    {
        String[] allWaypointNames = getWaypointNames(self);
        if ((allWaypointNames != null) && (allWaypointNames.length > 0))
        {
            for (int i = 0; i < allWaypointNames.length; i++)
            {
                if (allWaypointNames[i].equals(waypointSearchName))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean getWaypoint(obj_id self, String groupName) throws InterruptedException
    {
        String waypName = "";
        if (groupName.equals("imptat"))
        {
            if (!foundWaypointName(self, "Lt. Akal Colzet (Imperial Pilot Trainer)"))
            {
                location locColzet = new location();
                locColzet.area = "tatooine";
                locColzet.x = -1132f;
                locColzet.y = 13.32f;
                locColzet.z = -3542f;
                waypName = "Lt. Akal Colzet (Imperial Pilot Trainer)";
                qa.createAQaWaypointInDataPad(self, locColzet, waypName);
            }
            if (!foundWaypointName(self, "Commander Oberhaur"))
            {
                location locOberhaur = new location();
                locOberhaur.area = "tatooine";
                locOberhaur.x = -1127f;
                locOberhaur.y = 15f;
                locOberhaur.z = -3589f;
                waypName = "Commander Oberhaur";
                qa.createAQaWaypointInDataPad(self, locOberhaur, waypName);
            }
            if (!foundWaypointName(self, "Field Commander Alozen"))
            {
                location locAlozen = new location();
                locAlozen.area = "yavin4";
                locAlozen.x = 3998f;
                locAlozen.y = 37f;
                locAlozen.z = -6195f;
                waypName = "Field Commander Alozen";
                qa.createAQaWaypointInDataPad(self, locAlozen, waypName);
            }
            if (!foundWaypointName(self, "Captain Denner"))
            {
                location locDenner = new location();
                locDenner.area = "yavin4";
                locDenner.x = 4000f;
                locDenner.y = 37f;
                locDenner.z = -6196f;
                waypName = "Captain Denner";
                qa.createAQaWaypointInDataPad(self, locDenner, waypName);
            }
            if (!foundWaypointName(self, "Admiral Kilnstrider"))
            {
                location locKilnstrider = new location();
                locKilnstrider.area = "endor";
                
                locKilnstrider.x = 3227f;
                locKilnstrider.y = 24f;
                locKilnstrider.z = -3436f;
                waypName = "Admiral Kilnstrider";
                qa.createAQaWaypointInDataPad(self, locKilnstrider, waypName);
            }
            if (!foundWaypointName(self, "Grand Admiral Nial Declann"))
            {
                location locDeclann = new location();
                locDeclann.area = "naboo";
                locDeclann.x = -5524f;
                locDeclann.y = 29f;
                locDeclann.z = 4618f;
                waypName = "Grand Admiral Nial Declann";
                qa.createAQaWaypointInDataPad(self, locDeclann, waypName);
            }
        }
        else if (groupName.equals("rebtat"))
        {
            if (!foundWaypointName(self, "Commander Da'la Socuna"))
            {
                location locSocuna = new location();
                locSocuna.area = "tatooine";
                locSocuna.x = -3002f;
                locSocuna.y = 4f;
                locSocuna.z = 2201f;
                waypName = "Commander Da'la Socuna";
                qa.createAQaWaypointInDataPad(self, locSocuna, waypName);
            }
            if (!foundWaypointName(self, "Major Eker"))
            {
                location locEker = new location();
                locEker.area = "yavin4";
                locEker.x = -6966f;
                locEker.y = 73f;
                locEker.z = -5660f;
                waypName = "Major Eker";
                qa.createAQaWaypointInDataPad(self, locEker, waypName);
            }
            if (!foundWaypointName(self, "Arnecio Ulvaw'op"))
            {
                location locArnecio = new location();
                locArnecio.area = "dathomir";
                locArnecio.x = -115f;
                locArnecio.y = 18f;
                locArnecio.z = -1579f;
                waypName = "Arnecio Ulvaw'op";
                qa.createAQaWaypointInDataPad(self, locArnecio, waypName);
            }
            if (!foundWaypointName(self, "General Ufwol"))
            {
                location locUfwol = new location();
                locUfwol.area = "rori";
                
                locUfwol.x = 3690f;
                locUfwol.y = 96f;
                locUfwol.z = -6463f;
                waypName = "General Ufwol";
                qa.createAQaWaypointInDataPad(self, locUfwol, waypName);
            }
            if (!foundWaypointName(self, "Admiral Wilham Burke"))
            {
                location locBurke = new location();
                locBurke.area = "corellia";
                
                locBurke.x = 3082f;
                locBurke.y = 301f;
                locBurke.z = -5203f;
                waypName = "Admiral Wilham Burke";
                qa.createAQaWaypointInDataPad(self, locBurke, waypName);
            }
        }
        return true;
    }
}
