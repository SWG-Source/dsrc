package script.working;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

public class jhaskell_test extends script.base_script
{
    public jhaskell_test()
    {
    }
    public static final int SF_COST_CITY_LOW = 16;
    public static final int SF_SKILL_TRAINER = 64;
    public static final String CITY_SKILL_TRAINERS = "datatables/city/skill_trainers.iff";
    public static final string_id SID_SYS_FOUND = new string_id("treasure_map/treasure_map", "sys_found");
    public static final String SCRIPT_DUMMY_CHEST = "systems.treasure_map.base.dummy_treasure_drum";
    public static final String TREASURE_TABLE = "datatables/treasure_map/treasure_map.iff";
    public static final String DUMMY_CHEST = "object/tangible/container/drum/nonopening_treasure_drum.iff";
    public static final String LOOT_TABLE_81_90 = "treasure/treasure_81_90";
    public static final String TREASURE_GUARDS = "treasure_guard_jedi_elite";
    public static final String BOSS_MOB = "none";
    public static final int TREASURE_GUARD_LEVEL = 90;
    public static final int GROUP_MODIFIER = 1;
    public static final int ENEMY_COUNT = 3;
    public static final int DATATABLE_IDX = 8;
    public static final String[] ALL_PLAYER_HOUSING =
    {
        "object/building/player/player_garage_corellia_style_01.iff",
        "object/building/player/player_garage_naboo_style_01.iff",
        "object/building/player/player_garage_tatooine_style_01.iff",
        "object/building/player/player_guildhall_corellia_style_01.iff",
        "object/building/player/player_guildhall_generic_style_01.iff",
        "object/building/player/player_guildhall_naboo_style_01.iff",
        "object/building/player/player_guildhall_tatooine_style_01.iff",
        "object/building/player/player_guildhall_tatooine_style_02.iff",
        "object/building/player/player_house_corellia_large_style_01.iff",
        "object/building/player/player_house_corellia_large_style_02.iff",
        "object/building/player/player_house_corellia_medium_style_01.iff",
        "object/building/player/player_house_corellia_medium_style_02.iff",
        "object/building/player/player_house_corellia_small_style_01.iff",
        "object/building/player/player_house_corellia_small_style_01_floorplan_02.iff",
        "object/building/player/player_house_corellia_small_style_02.iff",
        "object/building/player/player_house_corellia_small_style_02_floorplan_02.iff",
        "object/building/player/player_house_generic_large_style_01.iff",
        "object/building/player/player_house_generic_large_style_02.iff",
        "object/building/player/player_house_generic_medium_style_01.iff",
        "object/building/player/player_house_generic_medium_style_02.iff",
        "object/building/player/player_house_generic_small_style_01.iff",
        "object/building/player/player_house_generic_small_style_01_floorplan_02.iff",
        "object/building/player/player_house_generic_small_style_02.iff",
        "object/building/player/player_house_generic_small_style_02_floorplan_02.iff",
        "object/building/player/player_house_mustafar_lg.iff",
        "object/building/player/player_house_naboo_large_style_01.iff",
        "object/building/player/player_house_naboo_medium_style_01.iff",
        "object/building/player/player_house_naboo_small_style_01.iff",
        "object/building/player/player_house_naboo_small_style_02.iff",
        "object/building/player/player_house_tatooine_large_style_01.iff",
        "object/building/player/player_house_tatooine_medium_style_01.iff",
        "object/building/player/player_house_tatooine_small_style_01.iff",
        "object/building/player/player_house_tatooine_small_style_02.iff",
        "object/building/player/player_merchant_tent_style_01.iff",
        "object/building/player/player_merchant_tent_style_02.iff",
        "object/building/player/player_merchant_tent_style_03.iff",
        "object/building/player/player_mustafar_house_lg.iff"
    };
    public static final String[] ALL_PLAYER_CITY_STRUCTURES =
    {
        "object/building/player/city/bank_corellia.iff",
        "object/building/player/city/bank_naboo.iff",
        "object/building/player/city/bank_tatooine.iff",
        "object/building/player/city/barn_no_planet_restriction.iff",
        "object/building/player/city/cantina_corellia.iff",
        "object/building/player/city/cantina_naboo.iff",
        "object/building/player/city/cantina_tatooine.iff",
        "object/building/player/city/cityhall_corellia.iff",
        "object/building/player/city/cityhall_naboo.iff",
        "object/building/player/city/cityhall_tatooine.iff",
        "object/building/player/city/cloning_corellia.iff",
        "object/building/player/city/cloning_naboo.iff",
        "object/building/player/city/cloning_tatooine.iff",
        "object/building/player/city/diner_no_planet_restriction.iff",
        "object/building/player/city/e3_demo_cityhall_tatooine.iff",
        "object/building/player/city/e3_demo_shuttleport_tatooine.iff",
        "object/building/player/city/garden_corellia_lrg_01.iff",
        "object/building/player/city/garden_corellia_lrg_02.iff",
        "object/building/player/city/garden_corellia_lrg_03.iff",
        "object/building/player/city/garden_corellia_lrg_04.iff",
        "object/building/player/city/garden_corellia_lrg_05.iff",
        "object/building/player/city/garden_corellia_med_01.iff",
        "object/building/player/city/garden_corellia_med_02.iff",
        "object/building/player/city/garden_corellia_med_03.iff",
        "object/building/player/city/garden_corellia_med_04.iff",
        "object/building/player/city/garden_corellia_med_05.iff",
        "object/building/player/city/garden_corellia_sml_01.iff",
        "object/building/player/city/garden_corellia_sml_02.iff",
        "object/building/player/city/garden_corellia_sml_03.iff",
        "object/building/player/city/garden_corellia_sml_04.iff",
        "object/building/player/city/garden_corellia_sml_05.iff",
        "object/building/player/city/garden_dantooine_lrg_01.iff",
        "object/building/player/city/garden_dantooine_med_01.iff",
        "object/building/player/city/garden_dantooine_sml_01.iff",
        "object/building/player/city/garden_dathomir_lrg_01.iff",
        "object/building/player/city/garden_dathomir_med_01.iff",
        "object/building/player/city/garden_dathomir_sml_01.iff",
        "object/building/player/city/garden_endor_lrg_01.iff",
        "object/building/player/city/garden_endor_med_01.iff",
        "object/building/player/city/garden_endor_sml_01.iff",
        "object/building/player/city/garden_naboo_large.iff",
        "object/building/player/city/garden_naboo_lrg_01.iff",
        "object/building/player/city/garden_naboo_lrg_02.iff",
        "object/building/player/city/garden_naboo_lrg_03.iff",
        "object/building/player/city/garden_naboo_lrg_04.iff",
        "object/building/player/city/garden_naboo_lrg_05.iff",
        "object/building/player/city/garden_naboo_med_01.iff",
        "object/building/player/city/garden_naboo_med_02.iff",
        "object/building/player/city/garden_naboo_med_03.iff",
        "object/building/player/city/garden_naboo_med_04.iff",
        "object/building/player/city/garden_naboo_med_05.iff",
        "object/building/player/city/garden_naboo_sml_01.iff",
        "object/building/player/city/garden_naboo_sml_02.iff",
        "object/building/player/city/garden_naboo_sml_03.iff",
        "object/building/player/city/garden_naboo_sml_04.iff",
        "object/building/player/city/garden_naboo_sml_05.iff",
        "object/building/player/city/garden_tatooine_lrg_01.iff",
        "object/building/player/city/garden_tatooine_lrg_02.iff",
        "object/building/player/city/garden_tatooine_lrg_03.iff",
        "object/building/player/city/garden_tatooine_lrg_04.iff",
        "object/building/player/city/garden_tatooine_lrg_05.iff",
        "object/building/player/city/garden_tatooine_med_01.iff",
        "object/building/player/city/garden_tatooine_med_02.iff",
        "object/building/player/city/garden_tatooine_med_03.iff",
        "object/building/player/city/garden_tatooine_med_04.iff",
        "object/building/player/city/garden_tatooine_med_05.iff",
        "object/building/player/city/garden_tatooine_sml_01.iff",
        "object/building/player/city/garden_tatooine_sml_02.iff",
        "object/building/player/city/garden_tatooine_sml_03.iff",
        "object/building/player/city/garden_tatooine_sml_04.iff",
        "object/building/player/city/garden_tatooine_sml_05.iff",
        "object/building/player/city/hospital_corellia.iff",
        "object/building/player/city/hospital_naboo.iff",
        "object/building/player/city/hospital_tatooine.iff",
        "object/building/player/city/shuttleport_corellia.iff",
        "object/building/player/city/shuttleport_naboo.iff",
        "object/building/player/city/shuttleport_tatooine.iff",
        "object/building/player/city/theater_corellia.iff",
        "object/building/player/city/theater_naboo.iff",
        "object/building/player/city/theater_tatooine.iff"
    };
    public static final String[] TCG_EXPANSION =
    {
        "item_tcg_loot_reward_series2_v_wing",
        "item_tcg_loot_reward_series2_podracer_mawhonic",
        "item_tcg_loot_reward_series2_sanyassan_skull",
        "item_tcg_loot_reward_series1_video_game_table",
        "item_tcg_loot_reward_series1_organizational_datapad",
        "item_tcg_loot_reward_series2_barn",
        "item_tcg_loot_reward_series2_versafunction88_datapad",
        "item_tcg_loot_reward_series2_keelkana_tooth",
        "item_tcg_loot_reward_series2_painting_darth_vader",
        "item_tcg_loot_reward_series2_diner",
        "item_tcg_loot_reward_series2_display_case_02",
        "item_tcg_loot_reward_series2_greeter_ewok",
        "item_tcg_loot_reward_series2_chon_bust",
        "item_tcg_loot_reward_series2_vendor_bomarr_monk",
        "item_tcg_loot_reward_series2_greeter_serving_droid",
        "item_tcg_loot_reward_series2_drink_dispenser",
        "item_tcg_loot_reward_series2_painting_alliance_propaganda",
        "item_tcg_loot_reward_series2_vendor_meatlump",
        "item_tcg_loot_reward_series2_arc170_flightsuit",
        "item_tcg_loot_reward_series1_indoor_garden_01",
        "item_tcg_loot_reward_series2_mandalorian_strongbox",
        "item_tcg_loot_reward_series2_computer_console_02",
        "item_tcg_loot_reward_series1_indoor_fountain_02",
        "item_tcg_loot_reward_series9_lepese_dictionary",
        "item_tcg_loot_reward_series1_morgukai_shadow_scroll",
        "item_tcg_loot_reward_series6_snow_jacket",
        "item_tcg_loot_reward_series2_darth_vader_statuette",
        "item_tcg_loot_reward_series2_princess_leia_statuette",
        "item_tcg_loot_reward_series2_greeter_jawa",
        "item_tcg_loot_reward_series1_vendor_serving_droid",
        "item_tcg_loot_reward_series9_fish_tank",
        "item_tcg_loot_reward_series1_greeter_gungan"
    };
    public static final String[] TCG_LAUNCH =
    {
        "item_tcg_loot_reward_series1_black_corset_dress",
        "item_tcg_loot_reward_series1_naboo_jacket",
        "item_tcg_loot_reward_series1_dooku_bust",
        "item_tcg_loot_reward_series1_display_case_01",
        "item_tcg_loot_reward_series1_gorax_ear",
        "item_tcg_loot_reward_series1_bas_relief",
        "item_tcg_loot_reward_series1_glowing_blue_eyes",
        "item_tcg_loot_reward_series1_glowing_red_eyes",
        "item_tcg_loot_reward_series1_vendor_gungan",
        "item_tcg_loot_reward_series1_housecleaning_kit",
        "item_tcg_loot_reward_series1_indoor_fountain_01",
        "item_tcg_loot_reward_series1_vendor_jawa",
        "item_tcg_loot_reward_series1_greeter_meatlump",
        "item_tcg_loot_reward_series1_mechno_chair",
        "item_tcg_loot_reward_series1_nuna_ball_advertisement",
        "item_tcg_loot_reward_series1_podracer_gasgano",
        "item_tcg_loot_reward_series1_painting_jedi_crest",
        "item_tcg_loot_reward_series1_painting_trooper",
        "item_tcg_loot_reward_series1_radtrooper_badge",
        "item_tcg_loot_reward_series1_sith_speeder",
        "item_tcg_loot_reward_series1_tusken_talisman",
        "item_tcg_loot_reward_series1_organa_speeder",
        "item_tcg_loot_reward_series1_computer_console_01",
        "item_tcg_loot_reward_series1_vendor_ewok",
        "item_tcg_loot_reward_series1_hans_hydrospanner",
        "item_tcg_loot_reward_series1_painting_imperial_propaganda",
        "item_tcg_loot_reward_series1_indoor_garden_02",
        "item_tcg_loot_reward_series1_orange_flightsuit",
        "item_tcg_loot_reward_series1_vendor_bomarr_monk",
        "item_tcg_loot_reward_series1_target_creature",
        "item_tcg_loot_reward_series1_beru_whitesuns_cookbook",
        "item_tcg_loot_reward_series9_jedi_library_bookshelf"
    };
    public static final String[] TCG_ANIMATIONS =
    {
        "greeter_anims_face",
        "greeter_anims_happy",
        "greeter_anims_sick_sad",
        "greeter_anims_polite",
        "greeter_anims_rude",
        "greeter_anims_expression",
        "emote_greeter_ewok",
        "emote_greeter_jawa"
    };
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            debugConsoleMsg(self, "OnSpeaking: " + text);
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String priCommand = tok.nextToken();
                String secCommand = "";
                String thirdCommand = "";
                if (tok.hasMoreTokens())
                {
                    secCommand = tok.nextToken();
                }
                if (tok.hasMoreTokens())
                {
                    thirdCommand = tok.nextToken();
                }
                debugConsoleMsg(self, "command is: " + priCommand);
                if (priCommand.equalsIgnoreCase("what_scene") && secCommand.equals(""))
                {
                    sendSystemMessage(self, "You are in scene: " + getCurrentSceneName(), null);
                }
                else if (priCommand.equalsIgnoreCase("is_tutorial") && secCommand.equals(""))
                {
                    sendSystemMessage(self, "Is in Tutorial: " + isInTutorialArea(self), null);
                }
                else if (priCommand.equalsIgnoreCase("city") && secCommand.equalsIgnoreCase("set_trainers"))
                {
                    obj_id structure = qa.findTarget(self);
                    if (!isValidId(structure) || !exists(structure))
                    {
                        sendSystemMessage(self, "You must target object", null);
                    }
                    int city_id = findCityByCityHall(structure);
                    if (city_id <= 0)
                    {
                        sendSystemMessage(self, "You must target city hall", null);
                    }
                    if (!cityExists(city_id))
                    {
                        sendSystemMessage(self, "Structure doesn't exist", null);
                        return SCRIPT_CONTINUE;
                    }
                    boolean successSkillTrainers = setCitySkillTrainers(self, city_id);
                    if (successSkillTrainers)
                    {
                        sendSystemMessage(self, "Legacy Trainer Spawners placed", null);
                    }
                    else
                    {
                        sendSystemMessage(self, "The Trainer Routine Failed", null);
                    }
                }
                else if (priCommand.equalsIgnoreCase("city") && secCommand.equalsIgnoreCase("destroy_trainers"))
                {
                    obj_id structure = qa.findTarget(self);
                    if (!isValidId(structure) || !exists(structure))
                    {
                        sendSystemMessage(self, "You must target object", null);
                    }
                    int city_id = findCityByCityHall(structure);
                    if (city_id <= 0)
                    {
                        sendSystemMessage(self, "You must target city hall", null);
                    }
                    if (!cityExists(city_id))
                    {
                        sendSystemMessage(self, "Structure doesn't exist", null);
                        return SCRIPT_CONTINUE;
                    }
                    boolean destroySuccess = destroyCitySkillTrainers(self, city_id);
                }
                else if (priCommand.equalsIgnoreCase("city") && secCommand.equalsIgnoreCase("get_structures"))
                {
                    obj_id structure = qa.findTarget(self);
                    if (!isValidId(structure) || !exists(structure))
                    {
                        sendSystemMessage(self, "You must target object", null);
                    }
                    int city_id = findCityByCityHall(structure);
                    if (city_id <= 0)
                    {
                        sendSystemMessage(self, "You must target city hall", null);
                    }
                    if (!cityExists(city_id))
                    {
                        sendSystemMessage(self, "Structure doesn't exist", null);
                        return SCRIPT_CONTINUE;
                    }
                    obj_id[] structures = cityGetStructureIds(city_id);
                    for (obj_id structure1 : structures) {
                        sendSystemMessage(self, "Found: " + getName(structure1), null);
                    }
                    sendSystemMessage(self, "getTrainerCount: " + city.getTrainerCount(city_id), null);
                    sendSystemMessage(self, "getMaxTrainerCount: " + city.getMaxTrainerCount(city_id), null);
                }
                else if (priCommand.equalsIgnoreCase("get_name") && secCommand.equals(""))
                {
                    obj_id target = qa.findTarget(self);
                    if (!isValidId(target) || !exists(target))
                    {
                        sendSystemMessage(self, "You must target object", null);
                    }
                    sendSystemMessage(self, "Name: " + localize(getNameStringId(target)), null);
                }
                else if (priCommand.equalsIgnoreCase("get_loco") && secCommand.equals(""))
                {
                    sendSystemMessage(self, "Locomotion:" + getLocomotion(self), null);
                }
                else if (priCommand.equalsIgnoreCase("squad") && !secCommand.equals(""))
                {
                    sendSystemMessage(self, "Ship: " + secCommand, null);
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessage(self, "Ship OID not valid ", null);
                    }
                    sendSystemMessage(self, "Ship OID valid", null);
                    sendSystemMessage(self, "Ship Squad: " + _spaceUnitGetSquadId(secCmd), null);
                }
                else if (priCommand.equalsIgnoreCase("grant_schematic") && !secCommand.equals(""))
                {
                    grantSchematic(self, secCommand);
                }
                else if (priCommand.equalsIgnoreCase("test_chest") && secCommand.equals(""))
                {
                    dictionary params = new dictionary();
                    params.put("player", self);
                    messageTo(self, "spawnTreasure", params, 5, false);
                }
                else if (priCommand.equalsIgnoreCase("get_tat") && secCommand.equals(""))
                {
                    obj_id planetId = getPlanetByName("tatooine");
                    sendSystemMessageTestingOnly(self, "" + planetId);
                }
                else if (priCommand.equalsIgnoreCase("force_init") && secCommand.equals(""))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isIdValid(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "You need to target a deprecated collection reactor.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Forcing initialization on object.");
                        messageTo(lookAtTarget, "forceCollectionReactorInit", null, 1, false);
                    }
                }
                else if (priCommand.equalsIgnoreCase("force_init") && !secCommand.equals(""))
                {
                    obj_id lookAtTarget = utils.stringToObjId(secCommand);
                    if (!isValidId(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "You need to specify a valid OID of a collection reactor.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Forcing initialization on object.");
                        messageTo(lookAtTarget, "forceCollectionReactorInit", null, 1, false);
                    }
                }
                else if (priCommand.equalsIgnoreCase("bypass_forage_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.main_forage_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "main_forage_roll_bypass is 100%");
                }
                else if (priCommand.equalsIgnoreCase("bypass_worm_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.bypass_worm_roll", "testing");
                    sendSystemMessageTestingOnly(self, "bypass_worm_roll is 100%");
                }
                else if (priCommand.equalsIgnoreCase("bypass_component_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.component_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "bypass_component_roll is 100%");
                }
                else if (priCommand.equalsIgnoreCase("bypass_treasure_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.treasure_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "bypass_treasure_roll is 100%");
                }
                else if (priCommand.equalsIgnoreCase("bypass_enzyme_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.enzyme_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "bypass_enzyme_roll is 100%");
                }
                else if (priCommand.equalsIgnoreCase("remove_bypass") && secCommand.equals(""))
                {
                    utils.removeScriptVarTree(self, "qa");
                    sendSystemMessageTestingOnly(self, "Bypass scriptvars removed");
                }
                else if (priCommand.equalsIgnoreCase("bypass_searchlair_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.egg_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "egg_roll_bypass is setting roll to 89 so you only receive eggs.");
                }
                else if (priCommand.equalsIgnoreCase("bypass_milk_roll") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.milk_roll_bypass", "testing");
                    sendSystemMessageTestingOnly(self, "milk_roll_bypass is setting roll to receive milk repeatedly...as if magic.");
                }
                else if (priCommand.equalsIgnoreCase("give_forage_data") && secCommand.equals(""))
                {
                    utils.setScriptVar(self, "qa.give_forage_data", "testing");
                    sendSystemMessageTestingOnly(self, "Forage data will be displayed in console.");
                }
                else if (priCommand.equalsIgnoreCase("rare_forage_loot_logger"))
                {
                    utils.removeScriptVarTree(self, "qa");
                    utils.setScriptVar(self, "qa.main_forage_roll_bypass", "testing");
                    utils.setScriptVar(self, "qa.give_forage_data", "testing");
                    int iteration = 25;
                    sendSystemMessageTestingOnly(self, "Foraging Rares " + iteration + " times. You must keep Godmode on for this to work");
                    for (int i = 0; i < iteration; i++)
                    {
                        messageTo(self, "handlerForPlayerForaging", null, (5 + i), false);
                    }
                }
                else if (priCommand.equalsIgnoreCase("forage_loot_logger"))
                {
                    utils.removeScriptVarTree(self, "qa");
                    utils.setScriptVar(self, "qa.give_forage_data", "testing");
                    int iteration = 25;
                    sendSystemMessageTestingOnly(self, "Foraging Normally for " + iteration + " times. You must keep Godmode on for this to work");
                    for (int i = 0; i < iteration; i++)
                    {
                        messageTo(self, "handlerForPlayerForaging", null, (5 + i), false);
                    }
                }
                else if (priCommand.equalsIgnoreCase("buff_list") && secCommand.equals(""))
                {
                    int[] buffs = buff.getAllBuffs(self);
                    if (buffs == null || buffs.length == 0)
                    {
                        sendSystemMessageTestingOnly(self, "No Buffs found");
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i < buffs.length; i++)
                    {
                        sendSystemMessageTestingOnly(self, "Buff " + i + " is " + buff.getBuffNameFromCrc(buffs[i]));
                    }
                }
                else if (priCommand.equalsIgnoreCase("fs_crystal") && secCommand.equals(""))
                {
                    obj_id createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_buff_item.iff", self);
                    if (isValidId(createdObject))
                    {
                        setObjVar(createdObject, "item.time.reuse_time", 259200);
                        setObjVar(createdObject, "item.buff.type", 0);
                        setObjVar(createdObject, "item.buff.value", 2000);
                        setObjVar(createdObject, "item.buff.duration", 7200);
                    }
                }
                else if (priCommand.equalsIgnoreCase("give_lumps") && secCommand.equals(""))
                {
                    obj_id pInv = utils.getInventoryContainer(self);
                    loot.addMeatlumpLumpsAsLoot(self, pInv, 5);
                }
                else if (priCommand.equalsIgnoreCase("meatlump_container") && secCommand.equals("buff"))
                {
                    loot.giveMeatlumpPuzzleLoot(self, false, true);
                }
                else if (priCommand.equalsIgnoreCase("meatlump_container") && secCommand.equals("threshold"))
                {
                    loot.giveMeatlumpPuzzleLoot(self, true, false);
                }
                else if (priCommand.equalsIgnoreCase("meatlump_container") && secCommand.equals("both"))
                {
                    sendSystemMessageTestingOnly(self, "both received");
                    loot.giveMeatlumpPuzzleLoot(self, true, true);
                }
                else if (priCommand.equalsIgnoreCase("meatlump_trigger") && secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "removing all trigger scriptvars");
                    utils.removeScriptVar(self, "meatlump_trigger");
                }
                else if (priCommand.equalsIgnoreCase("remove_safe") && secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "removing all trigger scriptvars");
                    utils.removeScriptVarTree(self, "meatlump_safe");
                    utils.removeScriptVarTree(self, "meatlump_safe_nonimperative");
                }
                else if (priCommand.equalsIgnoreCase("num_combos"))
                {
                    if (secCommand.equals("") || thirdCommand.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "num_combos number1 number2");
                        return SCRIPT_CONTINUE;
                    }
                    int number1 = utils.stringToInt(secCommand);
                    int number2 = utils.stringToInt(thirdCommand);
                    int numCombos = 0;
                    for (int i = 70; i < 88; i++)
                    {
                        for (int j = 70; j < 88; j++)
                        {
                            sendSystemMessageTestingOnly(self, " " + i + "*" + j + "= " + (i * j));
                            if ((i * j) >= number1 && (i * j) <= number2)
                            {
                                numCombos++;
                                sendSystemMessageTestingOnly(self, "Valid hit " + i + "*" + j + "= " + (i * j));
                            }
                        }
                    }
                    sendSystemMessageTestingOnly(self, "numbCombos =" + numCombos);
                }
                else if (priCommand.equalsIgnoreCase("set_scriptvar"))
                {
                    if (secCommand.equals("") || thirdCommand.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "set_scriptvar <scriptvar name> value");
                        return SCRIPT_CONTINUE;
                    }
                    utils.setScriptVar(self, secCommand, thirdCommand);
                    sendSystemMessageTestingOnly(self, "Set " + secCommand + " to " + thirdCommand);
                }
                else if (priCommand.equalsIgnoreCase("removescriptvar") && !secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "removing scriptvar: " + secCommand);
                    utils.removeScriptVar(self, secCommand);
                }
                else if (priCommand.equalsIgnoreCase("removescriptvartree") && !secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "removing scriptvartree: " + secCommand);
                    utils.removeScriptVarTree(self, secCommand);
                }
                else if (priCommand.equalsIgnoreCase("addscriptvar") && !secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "adding scriptvar: " + secCommand);
                    utils.removeScriptVar(self, secCommand);
                }
                else if (priCommand.equalsIgnoreCase("add_skill_mod") && !secCommand.equals("") && !thirdCommand.equals(""))
                {
                    int amount = utils.stringToInt(thirdCommand);
                    applySkillStatisticModifier(self, secCommand, amount);
                    sendSystemMessageTestingOnly(self, "Attempting to apply Skill Mod: " + secCommand + " to : " + amount);
                }
                else if (priCommand.equalsIgnoreCase("has_skill_mod") && !secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "Attempting to find Skill Mod: " + secCommand);
                    int hasMod = getSkillStatMod(self, secCommand);
                    if (hasMod > 0)
                    {
                        sendSystemMessageTestingOnly(self, "You have Skill Mod: " + secCommand + " and the value is: " + getSkillStatisticModifier(self, secCommand));
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "CANNOT FIND SKILL MOD: " + secCommand);
                    }
                }
                else if (priCommand.equalsIgnoreCase("get_skill_mod") && !secCommand.equals(""))
                {
                    String skillMod = secCommand;
                    if (skillMod == null || skillMod.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "skill mod example: vendor.special_vendor_skillmod");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Getting: " + secCommand + ": " + getSkillStatMod(self, skillMod));
                }
                else if (priCommand.equalsIgnoreCase("set_skill_mod") && !secCommand.equals(""))
                {
                    String skillMod = secCommand;
                    if (skillMod == null || skillMod.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "skill mod example: vendor.special_vendor_skillmod");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Setting: " + secCommand + " to +1");
                    applySkillStatisticModifier(self, skillMod, 1);
                }
                else if (priCommand.equalsIgnoreCase("test_greeter_anim") && !secCommand.equals("") && !thirdCommand.equals(""))
                {
                    obj_id greeter = utils.stringToObjId(secCommand);
                    if (!isValidId(greeter) || !exists(greeter))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find Greeter with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Attempting to player animation: " + thirdCommand + " on greeter: " + greeter);
                    doAnimationAction(greeter, thirdCommand);
                }
                else if (priCommand.equalsIgnoreCase("test_greeter_sound") && !secCommand.equals("") && !thirdCommand.equals(""))
                {
                    obj_id greeter = utils.stringToObjId(secCommand);
                    if (!isValidId(greeter) || !exists(greeter))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find Greeter with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    String sound = thirdCommand;
                    if (sound == null || sound.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find sound argument");
                        return SCRIPT_CONTINUE;
                    }
                    location loc = getLocation(greeter);
                    if (loc == null)
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find greeter location");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Attempting to player sound: " + sound + " on greeter: " + greeter);
                    if (!playClientEffectLoc(greeter, sound, loc, 0.0f))
                    {
                        sendSystemMessageTestingOnly(self, "The sound could not be played per the game system.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "The sound should have played.");
                }
                else if (priCommand.equalsIgnoreCase("test_greeter_voice") && !secCommand.equals("") && !thirdCommand.equals(""))
                {
                    obj_id greeter = utils.stringToObjId(secCommand);
                    if (!isValidId(greeter) || !exists(greeter))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find Greeter with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    String voice = thirdCommand;
                    if (voice == null && voice.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find voice argument");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Attempting to player sound: " + voice + " on greeter: " + greeter);
                    if (!play2dNonLoopingSound(self, voice))
                    {
                        sendSystemMessageTestingOnly(self, "The sound could not be played per the game system.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "The sound should have played.");
                }
                else if (priCommand.equalsIgnoreCase("get_tcg_expansion_templates"))
                {
                    String data = "";
                    int iteration = TCG_EXPANSION.length;
                    for (String s : TCG_EXPANSION) {
                        dictionary itemData = dataTableGetRow(static_item.MASTER_ITEM_TABLE, s);
                        data += itemData.getString("template_name");
                        data += "\n\r";
                    }
                    saveTextOnClient(self, "templates.txt", data);
                }
                else if (priCommand.equalsIgnoreCase("get_tcg_expansion_items"))
                {
                    obj_id pInv = utils.getInventoryContainer(self);
                    if (!isValidId(pInv) || !exists(pInv))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (getVolumeFree(pInv) <= 0)
                    {
                        sendSystemMessage(self, vendor_lib.SID_INVENTORY_FULL_GENERIC);
                        return SCRIPT_CONTINUE;
                    }
                    for (String s : TCG_EXPANSION) {
                        static_item.createNewItemFunction(s, pInv);
                    }
                }
                else if (priCommand.equalsIgnoreCase("get_tcg_launch_items"))
                {
                    obj_id pInv = utils.getInventoryContainer(self);
                    if (!isValidId(pInv) || !exists(pInv))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (getVolumeFree(pInv) <= 0)
                    {
                        sendSystemMessage(self, vendor_lib.SID_INVENTORY_FULL_GENERIC);
                        return SCRIPT_CONTINUE;
                    }
                    for (String tcgLaunch : TCG_LAUNCH) {
                        static_item.createNewItemFunction(tcgLaunch, pInv);
                    }
                }
                else if (priCommand.equalsIgnoreCase("play_client_test"))
                {
                    playClientEffectObj(self, "sound/utinni.snd", self, "");
                }
                else if (priCommand.equalsIgnoreCase("play_2d_test"))
                {
                    play2dNonLoopingSound(self, "sound/utinni.snd");
                }
                else if (priCommand.equalsIgnoreCase("play_music_test"))
                {
                    playMusic(self, "sound/utinni.snd");
                }
                else if (priCommand.equalsIgnoreCase("play_client") && !secCommand.equals(""))
                {
                    if (secCommand == null || secCommand.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Invalid. Valid sound: sound/utinni.snd");
                        return SCRIPT_CONTINUE;
                    }
                    if (!playClientEffectObj(self, secCommand, self, ""))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Failed.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Sound should have played.");
                    }
                }
                else if (priCommand.equalsIgnoreCase("play_2d") && !secCommand.equals(""))
                {
                    if (secCommand == null || secCommand.equals("") || !secCommand.startsWith("sound/") || !secCommand.contains(".snd"))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Invalid. Valid sound: sound/utinni.snd");
                        return SCRIPT_CONTINUE;
                    }
                    if (!play2dNonLoopingSound(self, secCommand))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Failed.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Sound should have played.");
                    }
                }
                else if (priCommand.equalsIgnoreCase("play_music") && !secCommand.equals(""))
                {
                    if (secCommand == null || secCommand.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Invalid. Valid sound: sound/utinni.snd");
                        return SCRIPT_CONTINUE;
                    }
                    if (!playMusic(self, "sound/" + secCommand + ".snd"))
                    {
                        sendSystemMessageTestingOnly(self, "Sound Failed.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Sound should have played.");
                    }
                }
                else if (priCommand.equalsIgnoreCase("tcg_booster_msg"))
                {
                    sendSystemMessage(self, new string_id("veteran_new", "tcg_available_monthly_bonus_booster_pack"));
                }
                else if (priCommand.equalsIgnoreCase("packup_points_100"))
                {
                    int[] housePacking = new int[2];
                    housePacking[0] = 100;
                    housePacking[1] = 100;
                    setObjVar(self, "housePackup", housePacking);
                    sendSystemMessageTestingOnly(self, "You now have points equal to a player that has destroyed 100 houses.");
                }
                else if (priCommand.equalsIgnoreCase("packup_points") && !secCommand.equals(""))
                {
                    if (secCommand == null || secCommand.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Define how many abandoned houses you want to pack up");
                        return SCRIPT_CONTINUE;
                    }
                    int number1 = utils.stringToInt(secCommand);
                    int[] housePacking = new int[2];
                    housePacking[0] = number1;
                    housePacking[1] = number1;
                    setObjVar(self, "housePackup", housePacking);
                    sendSystemMessageTestingOnly(self, "You now have points equal to a player that has destroyed " + number1 + " houses.");
                }
                else if (priCommand.equalsIgnoreCase("packup_daily_max"))
                {
                    if (!hasObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR))
                    {
                        sendSystemMessageTestingOnly(self, "No houses destroyed today.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "You currently have destroyed " + getIntObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR) + " houses today.");
                }
                else if (priCommand.equalsIgnoreCase("packup_remove_daily"))
                {
                    if (!hasObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR))
                    {
                        sendSystemMessageTestingOnly(self, "No objvar found.");
                        return SCRIPT_CONTINUE;
                    }
                    removeObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR);
                    sendSystemMessageTestingOnly(self, "You currently have destroyed " + getIntObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR) + " houses today.");
                }
                else if (priCommand.equalsIgnoreCase("packup_remove_all"))
                {
                    messageTo(self, "handlePlayerStructurePackupLockoutRemoval", null, 0, false);
                    if (!hasObjVar(self, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR))
                    {
                        sendSystemMessageTestingOnly(self, "No houses destroyed ever.");
                        return SCRIPT_CONTINUE;
                    }
                    removeObjVar(self, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR);
                    if (!hasObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR))
                    {
                        sendSystemMessageTestingOnly(self, "No House Pack up tracking objvar found, removal skipped.");
                        return SCRIPT_CONTINUE;
                    }
                    removeObjVar(self, player_structure.HOUSE_PACKUP_DAILY_TALLY_OBJVAR);
                    messageTo(self, "handlePlayerStructurePackupLockoutRemoval", null, 0, false);
                    sendSystemMessageTestingOnly(self, "All House Pack Up tracking and Lock Outs  removed.");
                }
                else if (priCommand.equalsIgnoreCase("packup_houses"))
                {
                    if (!hasObjVar(self, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR))
                    {
                        sendSystemMessageTestingOnly(self, "No houses destroyed ever.");
                        return SCRIPT_CONTINUE;
                    }
                    int[] array = getIntArrayObjVar(self, player_structure.HOUSE_PACKUP_ARRAY_OBJVAR);
                    sendSystemMessageTestingOnly(self, "You have destroyed " + array[1] + " houses total.");
                }
                else if (priCommand.equalsIgnoreCase("packup_remove_lockout"))
                {
                    messageTo(self, "handleFailedStructurePackup", null, 0, false);
                    sendSystemMessageTestingOnly(self, "If you had a house pack up lockout it no longer exists.");
                }
                else if (priCommand.equalsIgnoreCase("check_abandonable"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    sendSystemMessageTestingOnly(self, "Can be abandoned: " + player_structure.doesUnmarkedStructureQualifyForHousePackup(lookAtTarget));
                }
                else if (priCommand.equalsIgnoreCase("isowner"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    sendSystemMessageTestingOnly(self, "Are you owner? " + player_structure.isOwner(lookAtTarget, self));
                    if (isGod(self))
                    {
                        sendSystemMessageTestingOnly(self, "You are in godmode and the isowner check may not work");
                    }
                }
                else if (priCommand.equalsIgnoreCase("set_abandoned"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!player_structure.doesUnmarkedStructureQualifyForHousePackup(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "Please target a valid player house or factory. This object cannot be packed up.");
                        return SCRIPT_CONTINUE;
                    }
                    if (hasObjVar(lookAtTarget, "player_structure.abandoned") && getIntObjVar(lookAtTarget, "player_structure.abandoned") == 1)
                    {
                        sendSystemMessageTestingOnly(self, "This structure is already abandoned.");
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(lookAtTarget, "player_structure.abandoned", 1);
                    sendSystemMessageTestingOnly(self, "Structure set abandoned.");
                }
                else if (priCommand.equalsIgnoreCase("setlots"))
                {
                    adjustLotCount(getPlayerObject(self), -10);
                    sendSystemMessageTestingOnly(self, "Lots set to 10");
                }
                else if (priCommand.equalsIgnoreCase("set_sarlacc"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isValidId(lookAtTarget))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    String sarlaccObjTemplate = getTemplateName(lookAtTarget);
                    if (sarlaccObjTemplate == null || sarlaccObjTemplate.equals(""))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (!sarlaccObjTemplate.contains("mini_s02.iff"))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    setObjVar(lookAtTarget, house_pet.SARLACC_FAST_UPDATE_RATE, true);
                    sendSystemMessageTestingOnly(self, "Sarlacc God Hack Applied.");
                }
                else if (priCommand.equalsIgnoreCase("get_player_housing_lots"))
                {
                    String data = "";
                    for (String s : ALL_PLAYER_HOUSING) {
                        String lotStr = "";
                        int lot = getAdjustedLotCount(s);
                        if (lot == -99) {
                            lotStr = "Lot count set to 1 because of error";
                        } else if (lot == -1) {
                            lotStr = "template error";
                        } else if (lot == -2) {
                            lotStr = "footprint template error";
                        } else if (lot == -3) {
                            lotStr = "index error";
                        } else if (lot > 0) {
                            lotStr = "" + lot;
                        } else if (lot == 0) {
                            lotStr = "none";
                        } else if (lot == -69) {
                            lotStr = "Exempt";
                        }
                        data += s + "\t" + lotStr + "\n";
                    }
                    saveTextOnClient(self, "playerHousingLots.txt", data);
                }
                else if (priCommand.equalsIgnoreCase("get_player_city_lots"))
                {
                    String data = "";
                    for (String allPlayerCityStructure : ALL_PLAYER_CITY_STRUCTURES) {
                        String lotStr = "";
                        int lot = getAdjustedLotCount(allPlayerCityStructure);
                        if (lot == -99) {
                            lotStr = "Lot count set to 1 because of error";
                        } else if (lot == -1) {
                            lotStr = "template error";
                        } else if (lot == -2) {
                            lotStr = "footprint template error";
                        } else if (lot == -3) {
                            lotStr = "index error";
                        } else if (lot > 0) {
                            lotStr = "" + lot;
                        } else if (lot == 0) {
                            lotStr = "none";
                        } else if (lot == -69) {
                            lotStr = "Exempt";
                        }
                        data += allPlayerCityStructure + "\t" + lotStr + "\n";
                    }
                    saveTextOnClient(self, "cityStructureLots.txt", data);
                }
                else if (priCommand.equalsIgnoreCase("persist_obj") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    persistObject(object);
                    sendSystemMessageTestingOnly(self, "Object: " + secCommand + " persisted.");
                }
                else if (priCommand.equalsIgnoreCase("is_persisted") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    if (isObjectPersisted(self))
                    {
                        sendSystemMessageTestingOnly(self, "Object: " + secCommand + " IS INDEED persisted.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Object: " + secCommand + " IS NOT persisted.");
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("get_sign_skillmod"))
                {
                    int special_sign_tcg_series3 = getSkillStatMod(self, "special_sign_tcg_series3");
                    sendSystemMessageTestingOnly(self, "special_sign_tcg_series3: " + special_sign_tcg_series3);
                    int special_sign_halloween_hanging_sign = getSkillStatMod(self, "special_sign_halloween_hanging_sign");
                    sendSystemMessageTestingOnly(self, "special_sign_halloween_hanging_sign: " + special_sign_halloween_hanging_sign);
                    int special_sign_halloween_standing_sign = getSkillStatMod(self, "special_sign_halloween_standing_sign");
                    sendSystemMessageTestingOnly(self, "special_sign_halloween_standing_sign: " + special_sign_halloween_standing_sign);
                    int shop_sign = getSkillStatMod(self, "shop_sign");
                    sendSystemMessageTestingOnly(self, "shop_sign: " + shop_sign);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("give_shop_sign"))
                {
                    applySkillStatisticModifier(self, "shop_sign", 1);
                    int shop_sign = getSkillStatMod(self, "shop_sign");
                    sendSystemMessageTestingOnly(self, "shop_sign: " + shop_sign);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("remove_shop_sign"))
                {
                    applySkillStatisticModifier(self, "shop_sign", -1);
                    int shop_sign = getSkillStatMod(self, "shop_sign");
                    sendSystemMessageTestingOnly(self, "shop_sign: " + shop_sign);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("decrease_sign_skillmod") && !secCommand.equals(""))
                {
                    int skillModAmount = getSkillStatMod(self, "special_sign_tcg_series3");
                    sendSystemMessageTestingOnly(self, "Before: " + skillModAmount);
                    int number1 = utils.stringToInt(secCommand);
                    if (number1 <= 0)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (!applySkillStatisticModifier(self, "special_sign_tcg_series3", -1 * number1))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    skillModAmount = getSkillStatMod(self, "special_sign_tcg_series3");
                    sendSystemMessageTestingOnly(self, "Now: " + skillModAmount);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("increase_sign_skillmod") && !secCommand.equals(""))
                {
                    int skillModAmount = getSkillStatMod(self, "special_sign_tcg_series3");
                    sendSystemMessageTestingOnly(self, "Before: " + skillModAmount);
                    int number1 = utils.stringToInt(secCommand);
                    if (number1 <= 0)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (!applySkillStatisticModifier(self, "special_sign_tcg_series3", number1))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    skillModAmount = getSkillStatMod(self, "special_sign_tcg_series3");
                    sendSystemMessageTestingOnly(self, "Now: " + skillModAmount);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("play_particle") && !secCommand.equals(""))
                {
                    playClientEffectObj(self, secCommand, self, "");
                    sendSystemMessageTestingOnly(self, "Particle should have played");
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("sarlacc_day") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    if (isDeluxSarlacc(object))
                    {
                        int totalSubtractedTime = house_pet.getUpdateDaily(object);
                        int lastFed = getIntObjVar(object, house_pet.SARLACC_BORN);
                        if (hasObjVar(object, house_pet.SARLACC_LAST_FED))
                        {
                            lastFed = getIntObjVar(object, house_pet.SARLACC_LAST_FED);
                        }
                        int newFed = lastFed - totalSubtractedTime;
                        setObjVar(object, house_pet.SARLACC_LAST_FED, newFed);
                        sendSystemMessageTestingOnly(self, "Deluxe Sarlacc: " + secCommand + " Last Feeding Time has had " + totalSubtractedTime + " subtracted from it.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Object: " + secCommand + " IS NOT a Deluxe Sarlacc or has never been fed.");
                    }
                    utils.removeScriptVar(object, house_pet.SARLACC_AVOID_REPEATED_UPDATES);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("sarlacc_phase_very_hungry") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    if (isDeluxSarlacc(object))
                    {
                        setObjVar(object, house_pet.SARLACC_CURRENT_PHASE, 2);
                        sendSystemMessageTestingOnly(self, "Deluxe Sarlacc: " + secCommand + " has been set to very hungry.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Object: " + secCommand + " IS NOT a Deluxe Sarlacc or has never been fed.");
                    }
                    utils.removeScriptVar(object, house_pet.SARLACC_AVOID_REPEATED_UPDATES);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("in_world") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "Is in World Cell: " + isInWorldCell(object));
                }
                else if (priCommand.equalsIgnoreCase("get_feed_list") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    String list = getListOfFoodItems(object);
                    if (list == null || list.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "Nothing returned");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "List of Items Fed: " + list);
                    }
                }
                else if (priCommand.equalsIgnoreCase("massiff_day") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    if (isMassiffBowl(object))
                    {
                        int totalSubtractedTime = house_pet.getUpdateDaily(object);
                        int lastFed = getIntObjVar(object, house_pet.MASSIFF_LAST_FED);
                        int newFed = lastFed - totalSubtractedTime;
                        setObjVar(object, house_pet.MASSIFF_LAST_FED, newFed);
                        sendSystemMessageTestingOnly(self, "Massif Bowl: " + secCommand + " Last Feeding Time has had " + totalSubtractedTime + " subtracted from it.");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Object: " + secCommand + " IS NOT a Massif Bowl or has no spawned and fed Massif.");
                    }
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("massiff_emote") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    playClientEffectObj(object, house_pet.MASSIFF_HUNGRY_EMOTE, object, "");
                }
                else if (priCommand.equalsIgnoreCase("getyaw") && !secCommand.equals(""))
                {
                    obj_id object = utils.stringToObjId(secCommand);
                    if (!isValidId(object) || !exists(object))
                    {
                        sendSystemMessageTestingOnly(self, "Couldn't find object with OID of: " + secCommand);
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "The yaw of that object is: " + getYaw(object));
                }
                else if (priCommand.equalsIgnoreCase("test_player_anim") && !secCommand.equals(""))
                {
                    sendSystemMessageTestingOnly(self, "Animating using: " + secCommand);
                    doAnimationAction(self, secCommand);
                }
                else if (priCommand.equalsIgnoreCase("remove_lockout"))
                {
                    removeObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_VANDAL_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_VANDAL_TIMESTAMP);
                    removeObjVar(self, "empire_day_events.locked_out");
                    sendSystemMessageTestingOnly(self, "Lockouts removed.");
                }
                else if (priCommand.equalsIgnoreCase("collections_removed"))
                {
                    collection.removeCollectionForRealsies(self, holiday.REBEL_RESISTANCE_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_VANDAL_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_ENGINEERING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_ENTERTAINER_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_RESCUE_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_RECRUITING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ANTIPROP_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENGINEERING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENTERTAINER_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_RESCUE_COLLECTION);
                }
                else if (priCommand.equalsIgnoreCase("buffs_removed"))
                {
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_SF);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_SF);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_RECRUITMENT_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_RESISTANCE_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_ANTIPROPAGANDA_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_PLAYER_VANDAL_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("apply_recruit_combat_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("apply_resist_combat_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("apply_recruit_sf_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_SF);
                }
                else if (priCommand.equalsIgnoreCase("apply_resist_sf_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_SF);
                }
                else if (priCommand.equalsIgnoreCase("apply_antiprop_combat_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("apply_antiprop_sf_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_SF);
                }
                else if (priCommand.equalsIgnoreCase("apply_vandal_combat_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("apply_vandal_sf_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_SF);
                }
                else if (priCommand.equalsIgnoreCase("apply_recruit_counter_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMPERIAL_RECRUITMENT_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("apply_resist_counter_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REBEL_RESISTANCE_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("apply_antiprop_counter_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMPERIAL_ANTIPROPAGANDA_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("apply_vandal_counter_buff"))
                {
                    buff.applyBuff(self, holiday.BUFF_REBEL_PLAYER_VANDAL_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("hasImpTraderQuest"))
                {
                    String quest = "";
                    for (int i = 0; i < holiday.ALL_IMPERIAL_QUESTS.length; i++)
                    {
                        if (groundquests.isQuestActive(self, holiday.ALL_IMPERIAL_QUESTS[i]))
                        {
                            quest = holiday.ALL_IMPERIAL_QUESTS[i];
                            sendSystemMessageTestingOnly(self, "FOUND QUEST: " + holiday.ALL_IMPERIAL_QUESTS[i]);
                            break;
                        }
                        else
                        {
                            sendSystemMessageTestingOnly(self, "is not quest: " + holiday.ALL_IMPERIAL_QUESTS[i]);
                        }
                    }
                    if (quest == null || quest.equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "no quest, exiting");
                        return SCRIPT_CONTINUE;
                    }
                    if (groundquests.isTaskActive(self, quest, quest))
                    {
                        sendSystemMessageTestingOnly(self, "TASK QUEST: " + quest);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "Task not found: " + quest);
                    }
                }
                else if (priCommand.equalsIgnoreCase("showBuildoutArea"))
                {
                    location here = getLocation(self);
                    obj_id containingBuilding = getTopMostContainer(self);
                    if (isIdValid(containingBuilding))
                    {
                        here = getLocation(containingBuilding);
                    }
                    String buildoutAreaName = getBuildoutAreaName(here.x, here.z);
                    sendSystemMessageTestingOnly(self, "You are in buildout area: " + buildoutAreaName);
                }
                else if (priCommand.equalsIgnoreCase("detach_spawner"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isValidId(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "failed");
                        return SCRIPT_CONTINUE;
                    }
                    detachScript(lookAtTarget, "theme_park.dungeon.empire_day_interior_npc_spawner");
                    sendSystemMessageTestingOnly(self, "removed");
                }
                else if (priCommand.equalsIgnoreCase("attach_spawner"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isValidId(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "failed");
                        return SCRIPT_CONTINUE;
                    }
                    attachScript(lookAtTarget, "theme_park.dungeon.empire_day_interior_npc_spawner");
                    sendSystemMessageTestingOnly(self, "removed");
                }
                else if (priCommand.equalsIgnoreCase("make_dead"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isValidId(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "failed");
                        return SCRIPT_CONTINUE;
                    }
                    ai_lib.aiSetPosture(lookAtTarget, POSTURE_KNOCKED_DOWN);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("has_lock_out_timer"))
                {
                    if (hasObjVar(self, holiday.EMPIRE_DAY_VANDAL_TIMESTAMP))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_VANDAL_TIMESTAMP);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_VANDAL_TIMESTAMP);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_TIMESTAMP))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_RESISTANCE_TIMESTAMP);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_RESISTANCE_TIMESTAMP);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP);
                    }
                }
                else if (priCommand.equalsIgnoreCase("has_lock_out_flag"))
                {
                    if (hasObjVar(self, holiday.EMPIRE_DAY_VANDAL_LOCKED_OUT))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_VANDAL_LOCKED_OUT);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_VANDAL_LOCKED_OUT);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_LOCKED_OUT))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_RESISTANCE_LOCKED_OUT);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_RESISTANCE_LOCKED_OUT);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_LOCKED_OUT))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_PROPAGANDA_LOCKED_OUT);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_PROPAGANDA_LOCKED_OUT);
                    }
                    if (hasObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_LOCKED_OUT))
                    {
                        sendSystemMessageTestingOnly(self, "HAS: " + holiday.EMPIRE_DAY_RECRUITMENT_LOCKED_OUT);
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "DOES NOT HAVE: " + holiday.EMPIRE_DAY_RECRUITMENT_LOCKED_OUT);
                    }
                }
                else if (priCommand.equalsIgnoreCase("tatooine_vars"))
                {
                    obj_id tatooine = getPlanetByName("tatooine");
                    if (!isValidId(tatooine))
                    {
                        sendSystemMessageTestingOnly(self, "No Tatooine Found.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "has script: " + hasScript(tatooine, "event.planet_event_handler"));
                    sendSystemMessageTestingOnly(self, "OID: " + tatooine);
                    String data = holiday.getEventHighScores(tatooine, holiday.PLANET_VAR_EVENT_PREFIX + holiday.PLANET_VAR_EMPIRE_DAY + holiday.PLANET_VAR_SCORE, true);
                    if (data == null || data.length() <= 0)
                    {
                        sendSystemMessageTestingOnly(self, "High Score Data Invalid.");
                        return SCRIPT_CONTINUE;
                    }
                    if (!holiday.createEventLeaderBoardUI(self, holiday.LEADER_BOARD_TITLE, data))
                    {
                        sendSystemMessageTestingOnly(self, "createEventLeaderBoardUI failed.");
                        return SCRIPT_CONTINUE;
                    }
                    sendSystemMessageTestingOnly(self, "High Score Data should have worked.");
                }
                else if (priCommand.equalsIgnoreCase("score_vars"))
                {
                    if (hasObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE))
                    {
                        sendSystemMessageTestingOnly(self, "Score var: " + getIntObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE));
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "You don't have var");
                    }
                }
                else if (priCommand.equalsIgnoreCase("score_var_removed"))
                {
                    if (hasObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE))
                    {
                        removeObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE);
                        sendSystemMessageTestingOnly(self, "Score var removed.");
                    }
                }
                else if (priCommand.equalsIgnoreCase("set_score_var"))
                {
                    setObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE, 33);
                    sendSystemMessageTestingOnly(self, "Set to 33");
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_SF);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_SF);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_RECRUITMENT_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_RESISTANCE_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_ANTIPROPAGANDA_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_PLAYER_VANDAL_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("empire_day_remove_all"))
                {
                    if (hasObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE))
                    {
                        removeObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE);
                        sendSystemMessageTestingOnly(self, "Score var removed.");
                    }
                    removeObjVar(self, holiday.EMPIRE_DAY_BUFF_TRACKER);
                    removeObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_VANDAL_LOCKED_OUT);
                    removeObjVar(self, holiday.EMPIRE_DAY_RECRUITMENT_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_RESISTANCE_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_PROPAGANDA_TIMESTAMP);
                    removeObjVar(self, holiday.EMPIRE_DAY_VANDAL_TIMESTAMP);
                    removeObjVar(self, "empire_day_events.locked_out");
                    sendSystemMessageTestingOnly(self, "Lockouts removed.");
                    collection.removeCollectionForRealsies(self, holiday.REBEL_RESISTANCE_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_VANDAL_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_ENGINEERING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_ENTERTAINER_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_RESCUE_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_RECRUITING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ANTIPROP_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENGINEERING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENTERTAINER_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_RESCUE_COLLECTION);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_SF);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_COMBATANT);
                    buff.removeBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_SF);
                    buff.removeBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_SF);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_RECRUITMENT_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_RESISTANCE_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_IMPERIAL_ANTIPROPAGANDA_COUNTER);
                    buff.removeBuff(self, holiday.BUFF_REBEL_PLAYER_VANDAL_COUNTER);
                }
                else if (priCommand.equalsIgnoreCase("give_both_faction_kiosk_collections"))
                {
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("give_reb_kiosk_collections"))
                {
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_START_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("complete_imp_kiosk_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_RECRUITING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ANTIPROP_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("complete_reb_kiosk_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_RESISTANCE_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.REBEL_VANDAL_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("remove_imp_kiosk_collection"))
                {
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_RECRUITING_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ANTIPROP_COLLECTION);
                }
                else if (priCommand.equalsIgnoreCase("remove_reb_kiosk_collection"))
                {
                    collection.removeCollectionForRealsies(self, holiday.REBEL_RESISTANCE_COLLECTION);
                    collection.removeCollectionForRealsies(self, holiday.REBEL_VANDAL_COLLECTION);
                }
                else if (priCommand.equalsIgnoreCase("complete_imp_engineer_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("complete_imp_entertainer_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("start_imp_engineer_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENGINEERING_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("start_imp_entertainer_collection"))
                {
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_START_SLOT, 1);
                    modifyCollectionSlotValue(self, holiday.IMPERIAL_ENTERTAINER_COUNTER_SLOT, 1);
                }
                else if (priCommand.equalsIgnoreCase("remove_imp_engineer_collection"))
                {
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENGINEERING_COLLECTION);
                }
                else if (priCommand.equalsIgnoreCase("remove_imp_entertainer_collection"))
                {
                    collection.removeCollectionForRealsies(self, holiday.IMPERIAL_ENTERTAINER_COLLECTION);
                }
                else if (priCommand.equalsIgnoreCase("get_reb_buffs"))
                {
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_RESISTANCE_COMBATANT);
                    buff.applyBuff(self, holiday.BUFF_REB_EMPIREDAY_VANDAL_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("reb_leader_board") && !secCommand.equals(""))
                {
                    int buffNumber = utils.stringToInt(secCommand);
                    if (buffNumber > 0)
                    {
                        setObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE, buffNumber);
                        buffNumber = buffNumber / 2;
                        for (int i = 0; i < buffNumber; i++)
                        {
                            buff.applyBuff(self, holiday.BUFF_REBEL_RESISTANCE_COUNTER);
                            buff.applyBuff(self, holiday.BUFF_REBEL_PLAYER_VANDAL_COUNTER);
                        }
                    }
                }
                else if (priCommand.equalsIgnoreCase("get_imp_buffs"))
                {
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_RECRUITMENT_COMBATANT);
                    buff.applyBuff(self, holiday.BUFF_IMP_EMPIREDAY_ANTIPROP_COMBATANT);
                }
                else if (priCommand.equalsIgnoreCase("imp_leader_board") && !secCommand.equals(""))
                {
                    int buffNumber = utils.stringToInt(secCommand);
                    if (buffNumber > 0)
                    {
                        setObjVar(self, holiday.PLAYER_EMPIRE_DAY_SCORE, buffNumber);
                        buffNumber = buffNumber / 2;
                        for (int i = 0; i < buffNumber; i++)
                        {
                            buff.applyBuff(self, holiday.BUFF_IMPERIAL_RECRUITMENT_COUNTER);
                            buff.applyBuff(self, holiday.BUFF_IMPERIAL_ANTIPROPAGANDA_COUNTER);
                        }
                    }
                }
                else if (priCommand.equalsIgnoreCase("reset_leaderboards"))
                {
                    dictionary params = new dictionary();
                    params.put("eventVar", holiday.PLANET_VAR_EMPIRE_DAY);
                    params.put("eventConfig", "empireday_ceremony");
                    obj_id tatooine = getPlanetByName("tatooine");
                    if (isValidId(tatooine))
                    {
                        sendSystemMessageTestingOnly(self, "Resetting Leaderboard");
                        messageTo(tatooine, "resetEventDataAfterDelay", params, 1, false);
                    }
                }
                else if (priCommand.equalsIgnoreCase("remove_console_vars"))
                {
                    utils.removeScriptVarTree(self, "consoleMiniGame");
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("giveSignal1"))
                {
                    if (groundquests.isQuestActive(self, "outbreak_quest_facility_02_imperial"))
                    {
                        sendSystemMessageTestingOnly(self, "has outbreak_quest_facility_02_imperial");
                    }
                    if (groundquests.isTaskActive(self, "outbreak_quest_facility_02_imperial", "body1Searched"))
                    {
                        sendSystemMessageTestingOnly(self, "has tsk");
                    }
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("goAlpha"))
                {
                    warpPlayer(self, "dathomir", -5925, 559, -6667, null, -5925, 559, -6667, null, true);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("goBeta"))
                {
                    warpPlayer(self, "dathomir", -6283, 561, -7521, null, -6283, 561, -7521, null, true);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("goGamma"))
                {
                    warpPlayer(self, "dathomir", -6824, 553, -6456, null, -6824, 553, -6456, null, true);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("goDelta"))
                {
                    warpPlayer(self, "dathomir", -7145, 562, -6922, null, -7145, 562, -6922, null, true);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("goEpsilon"))
                {
                    warpPlayer(self, "dathomir", -7448, 570, -7342, null, -7448, 570, -7342, null, true);
                    sendSystemMessageTestingOnly(self, "done");
                }
                else if (priCommand.equalsIgnoreCase("giveSignal") && !secCommand.equals(""))
                {
                    if (secCommand == null || secCommand.length() <= 0)
                    {
                    }
                    else
                    {
                        groundquests.sendSignal(self, secCommand);
                    }
                }
                else if (priCommand.equalsIgnoreCase("stopAi") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    stop(secCmd);
                }
                else if (priCommand.equalsIgnoreCase("getDeliveryObject"))
                {
                    obj_id[] myList = getAllObjectsWithScript(getLocation(self), 100, "theme_park.outbreak.camp_arrival_trigger_volume");
                    if (myList == null || myList.length <= 0)
                    {
                        sendSystemMessageTestingOnly(self, "failed");
                    }
                    else
                    {
                        for (obj_id obj_id : myList) {
                            sendSystemMessageTestingOnly(self, "found: " + obj_id);
                        }
                        sendSystemMessageTestingOnly(self, "done");
                    }
                }
                else if (priCommand.equalsIgnoreCase("finishSignal"))
                {
                    if (groundquests.isTaskActive(self, "outbreak_quest_facility_05_imperial", "leaveFacility") || groundquests.isTaskActive(self, "outbreak_quest_facility_05_rebel", "leaveFacility") || groundquests.isTaskActive(self, "outbreak_quest_facility_05_neutral", "leaveFacility"))
                    {
                        groundquests.sendSignal(self, "hasLeftFacility");
                    }
                    if (groundquests.isTaskActive(self, "outbreak_quest_facility_05_imperial", "coverSurvivors") || groundquests.isTaskActive(self, "outbreak_quest_facility_05_rebel", "coverSurvivors") || groundquests.isTaskActive(self, "outbreak_quest_facility_05_neutral", "coverSurvivors"))
                    {
                        groundquests.sendSignal(self, "allDoneSurvivors");
                    }
                }
                else if (priCommand.equalsIgnoreCase("isInEntertainmentCamp"))
                {
                    obj_id camp = getCurrentAdvancedCamp(self);
                    if (isIdValid(camp))
                    {
                        sendSystemMessageTestingOnly(self, "camp: " + camp);
                        if (camping.isInEntertainmentCamp(self, camp))
                        {
                            sendSystemMessageTestingOnly(self, "You are in entertainer camp");
                        }
                        else
                        {
                            sendSystemMessageTestingOnly(self, "You are NOT in entertainer camp");
                        }
                        sendSystemMessageTestingOnly(self, "can performance heal: " + performance.canPerformanceHeal(self));
                        sendSystemMessageTestingOnly(self, "Max Duration: " + performance.inspireGetMaxDuration(self));
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "not a valid camp");
                    }
                }
                else if (priCommand.equalsIgnoreCase("gethp") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    sendSystemMessageTestingOnly(self, "CUR HP: " + getHitpoints(secCmd));
                    sendSystemMessageTestingOnly(self, "MAX HP: " + getMaxHitpoints(secCmd));
                }
                else if (priCommand.equalsIgnoreCase("sethp") && !secCommand.equals("") && !thirdCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    int thrdCmd = utils.stringToInt(thirdCommand);
                    setHitpoints(secCmd, thrdCmd);
                    sendSystemMessageTestingOnly(self, "CUR HP: " + getHitpoints(secCmd));
                    sendSystemMessageTestingOnly(self, "MAX HP: " + getMaxHitpoints(secCmd));
                }
                else if (priCommand.equalsIgnoreCase("incrementstack"))
                {
                    buff.applyBuff(self, "event_lifeday_imperial_present_counter");
                }
                else if (priCommand.equalsIgnoreCase("normalbuff"))
                {
                    buff.applyBuff(self, "bh_dm_cc_1");
                }
                else if (priCommand.equalsIgnoreCase("damagelittle") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    int maxHp = getMaxHitpoints(secCmd);
                    int threshold = (maxHp / 3);
                    setHitpoints(secCmd, (maxHp - threshold));
                }
                else if (priCommand.equalsIgnoreCase("damagehalf") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    int maxHp = getMaxHitpoints(secCmd);
                    int threshold = (maxHp / 2);
                    setHitpoints(secCmd, (maxHp - threshold));
                }
                else if (priCommand.equalsIgnoreCase("damagemore") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    int maxHp = getMaxHitpoints(secCmd);
                    int threshold = (maxHp / 3);
                    setHitpoints(secCmd, (maxHp - (threshold + threshold)));
                }
                else if (priCommand.equalsIgnoreCase("destroyturret") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    advanced_turret.explodeTurret(secCmd, self);
                }
                else if (priCommand.equalsIgnoreCase("getTemplateNoPath") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    String template = utils.getTemplateFilenameNoPath(secCmd);
                    sendSystemMessageTestingOnly(self, "template: " + template);
                }
                else if (priCommand.equalsIgnoreCase("getAttackerList") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    obj_id[] attackerList = utils.getObjIdBatchScriptVar(secCmd, "creditForKills.attackerList.attackers");
                    String list = utils.getStringScriptVar(secCmd, "creditForKills.attackerList");
                    if (attackerList == null)
                    {
                        debugSpeakMsg(self, "attackerList was null");
                        debugSpeakMsg(self, "list was " + list);
                        return SCRIPT_CONTINUE;
                    }
                    for (int i = 0; i < attackerList.length; ++i)
                    {
                        debugSpeakMsg(self, "attackerList[" + i + "] " + attackerList[i]);
                    }
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("getskillmodresists") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    else
                    {
                        for (int i = 0; i < armor.DATATABLE_SPECIAL_PROTECTIONS.length; i++)
                        {
                            sendSystemMessageTestingOnly(self, "resist: " + armor.DATATABLE_SPECIAL_PROTECTIONS[i] + ": " + getSkillStatisticModifier(secCmd, "expertise_innate_protection_" + armor.DATATABLE_SPECIAL_PROTECTIONS[i]));
                        }
                        sendSystemMessageTestingOnly(self, "resist: all: " + getSkillStatisticModifier(secCmd, "expertise_innate_protection_all"));
                    }
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("playorbital"))
                {
                    playClientEffectLoc(self, "clienteffect/combat_pt_orbitalstrike_low_pt.cef", getLocation(self), 0);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("planet_message"))
                {
                    location loc = getLocation(self);
                    string_id message = new string_id("gcw", "static_base_capture_imperial" + loc.area);
                    sendFactionalSystemMessagePlanet(message, null, -1.0f, true, true);
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("defense_camp"))
                {
                    obj_id lookAtTarget = findTarget(self);
                    if (!isValidId(lookAtTarget))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    if (!utils.hasScriptVar(lookAtTarget, "defense_camp"))
                    {
                        sendSystemMessageTestingOnly(self, "failed hasScriptVar check");
                        return SCRIPT_CONTINUE;
                    }
                    obj_id defendingGeneral = utils.getObjIdScriptVar(lookAtTarget, "defense_camp");
                    if (!isValidId(defendingGeneral) || !exists(defendingGeneral))
                    {
                        sendSystemMessageTestingOnly(self, "no object defense_camp");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "defense_camp found");
                    }
                    return SCRIPT_CONTINUE;
                }
                else if (priCommand.equalsIgnoreCase("get_faction_flag") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    else
                    {
                        int factionFlag = factions.getFactionFlag(secCmd);
                        if (factionFlag == 0)
                        {
                            sendSystemMessageTestingOnly(self, "UNKNOWN FACTION");
                        }
                        else if (factionFlag == 1)
                        {
                            sendSystemMessageTestingOnly(self, "REBEL FACTION");
                        }
                        else if (factionFlag == 2)
                        {
                            sendSystemMessageTestingOnly(self, "IMPERIAL FACTION");
                        }
                        else if (factionFlag == 3)
                        {
                            sendSystemMessageTestingOnly(self, "NEUTRAL FACTION");
                        }
                    }
                }
                else if (priCommand.equalsIgnoreCase("reduce_hp") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    int currentHp = getHitpoints(secCmd);
                    setHitpoints(secCmd, currentHp - 5000);
                }
                else if (priCommand.equalsIgnoreCase("get_fatigue") && !secCommand.equals(""))
                {
                    int secCmd = utils.stringToInt(secCommand);
                    if (secCmd < 0)
                    {
                        sendSystemMessageTestingOnly(self, "invalid amt");
                    }
                    sendSystemMessageTestingOnly(self, "amt: " + secCmd);
                    buff.applyBuffWithStackCount(self, gcw.BUFF_PLAYER_FATIGUE, secCmd);
                }
                else if (priCommand.equalsIgnoreCase("isonlist") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    if (trial.isNonInstanceFactionParticipant(self, secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "You are on list");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "You are NOT on list");
                    }
                }
                else if (priCommand.equalsIgnoreCase("removemefromlist") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    if (trial.removeNonInstanceFactionParticipant(self, secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "You are removed");
                    }
                    else
                    {
                        sendSystemMessageTestingOnly(self, "You werent on it");
                    }
                }
                else if (priCommand.equalsIgnoreCase("getgcwtools"))
                {
                    obj_id pInv = utils.getInventoryContainer(self);
                    obj_id barricade = createObject("object/tangible/gcw/crafting_quest/gcw_barricade_tool.iff", pInv, null);
                    if (isValidId(barricade))
                    {
                        setCount(barricade, 100);
                    }
                    obj_id patrol = createObject("object/tangible/gcw/crafting_quest/gcw_patrol_tool.iff", pInv, null);
                    if (isValidId(patrol))
                    {
                        setCount(patrol, 100);
                    }
                    obj_id spawner = createObject("object/tangible/gcw/crafting_quest/gcw_spawner_tool.iff", pInv, null);
                    if (isValidId(spawner))
                    {
                        setCount(spawner, 100);
                    }
                    obj_id tower = createObject("object/tangible/gcw/crafting_quest/gcw_tower_tool.iff", pInv, null);
                    if (isValidId(tower))
                    {
                        setCount(tower, 100);
                    }
                    obj_id turret = createObject("object/tangible/gcw/crafting_quest/gcw_turret_tool.iff", pInv, null);
                    if (isValidId(turret))
                    {
                        setCount(turret, 100);
                    }
                    obj_id vehicle = createObject("object/tangible/gcw/crafting_quest/gcw_vehicle_tool.iff", pInv, null);
                    if (isValidId(vehicle))
                    {
                        setCount(vehicle, 100);
                    }
                }
                else if (priCommand.equalsIgnoreCase("remove_tutorial_flag"))
                {
                    removeObjVar(self, gcw.GCW_TUTORIAL_FLAG);
                    sendSystemMessageTestingOnly(self, "Tutorial Flag Removed");
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_choster"))
                {
                    String cantina = "8145375";
                    obj_id building = utils.stringToObjId(cantina);
                    obj_id room = getCellId(building, "alcove3");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, 9.0f, -0.9f, -16.1f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_moore"))
                {
                    String cantina = "8145375";
                    obj_id building = utils.stringToObjId(cantina);
                    obj_id room = getCellId(building, "back_hallway");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -36.0f, 0.1f, -14.5f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_vana"))
                {
                    String stronghold = "6595508";
                    obj_id building = utils.stringToObjId(stronghold);
                    obj_id room = getCellId(building, "lobby");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -3.7f, 3.5f, -14.9f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_pirate_troll"))
                {
                    String pirateCave = "5126308";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "r15");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -104.1f, -40.9f, -40.6f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_pirate_hacker"))
                {
                    String pirateCave = "5126308";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "r25");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -63.1f, -65.9f, -140.8f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_mine_wes"))
                {
                    String pirateCave = "5645584";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "r5");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -3.1f, -45.0f, -133.9f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_mine_raynar"))
                {
                    String pirateCave = "5645584";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "r8");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -92.0f, -73.3f, -170.7f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_mine_filter"))
                {
                    String pirateCave = "5645584";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "r11");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, -84.0f, -100.0f, -95.6f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_facility_kendo_platt"))
                {
                    String pirateCave = "5126270";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "crittercell");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, 70.4f, -12.0f, 79.4f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_facility_jeran_goll"))
                {
                    String pirateCave = "5126270";
                    obj_id building = utils.stringToObjId(pirateCave);
                    obj_id room = getCellId(building, "researchlab2");
                    warpPlayer(self, "lok", 0.0f, 0.0f, 0.0f, room, 20.0f, -20.0f, 139.1f);
                }
                else if (priCommand.equalsIgnoreCase("nym_warp_roof"))
                {
                    warpPlayer(self, "lok", 477.0f, 33.0f, 4785.0f, null, 0.0f, 0.0f, 0.0f);
                }
                else if (priCommand.equalsIgnoreCase("nym_prisoner_quest"))
                {
                    if (groundquests.isQuestActive(self, "u16_nym_themepark_pirate_prisoner_rescue_collection"))
                    {
                        groundquests.clearQuest(self, "u16_nym_themepark_pirate_prisoner_rescue_collection");
                    }
                    groundquests.grantQuest(self, "u16_nym_themepark_pirate_prisoner_rescue_collection");
                    if (hasCompletedCollectionSlot(self, "icon_nyms_rescue_prisoner"))
                    {
                        modifyCollectionSlotValue(self, "icon_nyms_rescue_prisoner", -1);
                    }
                }
                else if (priCommand.equalsIgnoreCase("nym_prisoner_log"))
                {
                    LOG("prisoner_error_log", "prisoner log test!!");
                }
                else if (priCommand.equalsIgnoreCase("nym_rifle_old"))
                {
                    obj_id playerInv = utils.getInventoryContainer(self);
                    obj_id prize = weapons.createWeapon("object/weapon/ranged/carbine/carbine_nym_slugthrower.iff", playerInv, rand(0.8f, 1.1f));
                }
                else if (priCommand.equalsIgnoreCase("nym_weed_signals"))
                {
                    groundquests.sendSignal(self, "tallStalkWeedFound");
                    groundquests.sendSignal(self, "yellowPodWeedFound");
                    groundquests.sendSignal(self, "floweringCactiWeedFound");
                    groundquests.sendSignal(self, "lowGrowingWeedsFound");
                    groundquests.sendSignal(self, "paddleCactiWeedFound");
                    groundquests.sendSignal(self, "hasDisposedOfWeeds");
                }
                else if (priCommand.equalsIgnoreCase("nym_weed_signals_no_disposal"))
                {
                    groundquests.sendSignal(self, "tallStalkWeedFound");
                    groundquests.sendSignal(self, "yellowPodWeedFound");
                    groundquests.sendSignal(self, "floweringCactiWeedFound");
                    groundquests.sendSignal(self, "lowGrowingWeedsFound");
                    groundquests.sendSignal(self, "paddleCactiWeedFound");
                }
                else if (priCommand.equalsIgnoreCase("remove_sale_flag") && !secCommand.equals(""))
                {
                    obj_id secCmd = utils.stringToObjId(secCommand);
                    if (!isValidId(secCmd))
                    {
                        sendSystemMessageTestingOnly(self, "invalid object");
                    }
                    utils.removeScriptVar(secCmd, "soldAsJunk");
                    removeObjVar(secCmd, "soldAsJunk");
                    sendSystemMessageTestingOnly(self, "Done");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnTreasure(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id map = self;
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int treasureLevel = 90;
        sendSystemMessage(player, SID_SYS_FOUND);
        location playerLocation = getLocation(player);
        playerLocation.y = getHeightAtLocation(playerLocation.x, playerLocation.z);
        sendSystemMessage(self, "Creating Chest", null);
        obj_id treasureChest = createObject(DUMMY_CHEST, playerLocation);
        if (isValidId(treasureChest))
        {
            setObjVar(treasureChest, "owner", player);
            setOwner(treasureChest, player);
            String invis = stealth.getInvisBuff(player);
            if (invis != null)
            {
                stealth.checkForAndMakeVisibleNoRecourse(player);
            }
            attachScript(treasureChest, SCRIPT_DUMMY_CHEST);
            setObjVar(treasureChest, "treasureLevel", treasureLevel);
            String loot_table = LOOT_TABLE_81_90;
            setObjVar(treasureChest, "loot_table", loot_table);
            String type = TREASURE_GUARDS;
            int mobLevel = TREASURE_GUARD_LEVEL;
            int groupModifier = GROUP_MODIFIER;
            int count = ENEMY_COUNT;
            int dataTableIdx = DATATABLE_IDX;
            boolean verifiedAllVars = verifyVariablesNotNull(loot_table, type, mobLevel, groupModifier, count, dataTableIdx);
            if (!verifiedAllVars)
            {
                sendSystemMessage(self, "The treasure chest didn't get all the correct data.", null);
                return SCRIPT_CONTINUE;
            }
            dictionary outparams = new dictionary();
            outparams.put("loot_table", loot_table);
            outparams.put("count", count);
            outparams.put("type", type);
            outparams.put("mobLevel", mobLevel);
            outparams.put("location", playerLocation);
            outparams.put("player", player);
            outparams.put("boss_mob", BOSS_MOB);
            sendSystemMessage(self, "Spawning Guards", null);
            messageTo(treasureChest, "spawnTreasureGuards", outparams, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean verifyVariablesNotNull(String loot_table, String type, int mobLevel, int groupModifier, int count, int datTableIdx) throws InterruptedException
    {
        if (loot_table == null || loot_table.equals(""))
        {
            return false;
        }
        if (type == null || type.equals(""))
        {
            return false;
        }
        if (mobLevel <= 0)
        {
            return false;
        }
        if (groupModifier < 0)
        {
            return false;
        }
        if (count < 2)
        {
            return false;
        }
        if (datTableIdx < 0)
        {
            return false;
        }
        return true;
    }
    public boolean setCitySkillTrainers(obj_id self, int city_id) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return false;
        }
        if (!cityExists(city_id))
        {
            return false;
        }
        location poiStartLocation = getLocation(self);
        float yaw = getYaw(self);
        String[] arrayOfAllTrianerTemplates = dataTableGetStringColumn(CITY_SKILL_TRAINERS, "TEMPLATE");
        String[] arrayOfAllTrianerStrings = dataTableGetStringColumn(CITY_SKILL_TRAINERS, "STRING");
        sendSystemMessage(self, "Number of Trainers Being Placed: " + arrayOfAllTrianerTemplates.length, null);
        if (arrayOfAllTrianerTemplates.length != arrayOfAllTrianerStrings.length)
        {
            return false;
        }
        for (int i = 0; i < arrayOfAllTrianerTemplates.length; i++)
        {
            location poiLocation = utils.getRandomAwayLocation(poiStartLocation, 5.0f, 10.0f);
            obj_id poi = createObject(arrayOfAllTrianerTemplates[i], poiLocation);
            if (isValidId(poi) && exists(poi))
            {
                setYaw(poi, yaw);
                setObjVar(poi, "creator", self);
                setName(poi, arrayOfAllTrianerStrings[i]);
                int flags = SF_COST_CITY_LOW | SF_SKILL_TRAINER;
                citySetStructureInfo(city_id, poi, flags, true);
                setObjVar(poi, "city_id", city_id);
            }
            sendSystemMessage(self, arrayOfAllTrianerStrings[i] + " placed.", null);
        }
        return true;
    }
    public boolean destroyCitySkillTrainers(obj_id self, int city_id) throws InterruptedException
    {
        obj_id[] structures = cityGetStructureIds(city_id);
        for (obj_id structure : structures) {
            if (structure.isLoaded()) {
                if (!city.isNormalStructure(city_id, structure)) {
                    destroyObject(structure);
                }
            }
        }
        return true;
    }
    public obj_id findTarget(obj_id self) throws InterruptedException
    {
        obj_id intendedTarget = getIntendedTarget(self);
        obj_id lookAtTarget = getLookAtTarget(self);
        obj_id finalTarget = null;
        if (isIdValid(intendedTarget))
        {
            finalTarget = intendedTarget;
        }
        else if (isIdValid(lookAtTarget))
        {
            finalTarget = lookAtTarget;
        }
        else
        {
            finalTarget = self;
        }
        if (!isIdNull(finalTarget))
        {
            return finalTarget;
        }
        return self;
    }
    public int getAdjustedLotCount(String template) throws InterruptedException
    {
        if (template == null || template.equals(""))
        {
            return -1;
        }
        String fp_template = player_structure.getFootprintTemplate(template);
        if (fp_template == null || fp_template.equals(""))
        {
            return -2;
        }
        int idx = player_structure.getStructureTableIndex(template);
        if (idx == -1)
        {
            return -3;
        }
        int ignore_lots = dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, idx, player_structure.DATATABLE_COL_NO_LOT_REQ);
        if (ignore_lots != 1)
        {
            int struct_lots = (getNumberOfLots(fp_template) / 4) - dataTableGetInt(player_structure.PLAYER_STRUCTURE_DATATABLE, idx, player_structure.DATATABLE_COL_LOT_REDUCTION);
            if (struct_lots < 1)
            {
                struct_lots = -99;
            }
            return struct_lots;
        }
        return -69;
    }
    public boolean isDeluxSarlacc(obj_id sarlacc) throws InterruptedException
    {
        if (!isValidId(sarlacc) || !exists(sarlacc))
        {
            return false;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_CURRENT_PHASE))
        {
            return false;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_BORN))
        {
            return false;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_PHASE_START))
        {
            return false;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_FEEDING_ITERATION))
        {
            return false;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_LAST_FED))
        {
            return false;
        }
        return true;
    }
    public boolean isMassiffBowl(obj_id massifBowl) throws InterruptedException
    {
        if (!isValidId(massifBowl) || !exists(massifBowl))
        {
            return false;
        }
        if (!hasObjVar(massifBowl, house_pet.MASSIFF_CURRENT_PHASE))
        {
            return false;
        }
        if (!hasObjVar(massifBowl, house_pet.CHILD_OBJ_ID))
        {
            return false;
        }
        if (!hasObjVar(massifBowl, house_pet.MASSIFF_LAST_FED))
        {
            return false;
        }
        return true;
    }
    public String getListOfFoodItems(obj_id sarlacc) throws InterruptedException
    {
        if (!isValidId(sarlacc))
        {
            debugSpeakMsg(sarlacc, "sarlacc invalid");
            return null;
        }
        if (!hasObjVar(sarlacc, house_pet.SARLACC_FEED_ARRAY))
        {
            debugSpeakMsg(sarlacc, "sarlacc doesnt have array objvar.");
            return null;
        }
        String logData = "";
        String[] feedingList = getStringArrayObjVar(sarlacc, house_pet.SARLACC_FEED_ARRAY);
        debugSpeakMsg(sarlacc, "List Length: " + feedingList.length);
        if (feedingList != null && feedingList.length > 0)
        {
            for (int i = 0; i < feedingList.length; i++)
            {
                debugSpeakMsg(sarlacc, "Fed Item: " + i + " " + feedingList[i]);
                logData += " Fed Item " + i + ": " + feedingList[i] + ". ";
            }
            debugSpeakMsg(sarlacc, "Data: " + logData);
        }
        return logData;
    }
    public obj_id getCurrentAdvancedCamp(obj_id player) throws InterruptedException
    {
        obj_id[] objects = getNonCreaturesInRange(player, 25.0f);
        if (objects == null || objects.length == 0)
        {
            return null;
        }
        for (obj_id object : objects) {
            if (hasScript(object, "item.camp.camp_advanced")) {
                sendSystemMessageTestingOnly(player, "found script.");
                if (isInTriggerVolume(object, "campsite", player)) {
                    return object;
                } else {
                    sendSystemMessageTestingOnly(player, "no objects found.");
                }
            }
        }
        return null;
    }
}
