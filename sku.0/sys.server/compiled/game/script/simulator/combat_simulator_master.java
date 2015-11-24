package script.simulator;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.combat;
import script.library.create;
import script.library.weapons;
import script.library.armor;
import script.library.healing;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class combat_simulator_master extends script.base_script
{
    public combat_simulator_master()
    {
    }
    public static boolean debug = false;
    public static final String actorScript = "simulator.combat_simulator_actor";
    public static final float CRAFTED_QUALITY = 0.85f;
    public static final float CONDITION = 1.0f;
    public static final float GENERAL_PROTECTION = 0.94f;
    public static final float ENCUMBRANCE = 0.75f;
    public static final String[] PROFESSIONS = 
    {
        "architect",
        "armorsmith",
        "artisan",
        "bioengineer",
        "bountyhunter",
        "brawler",
        "carbineer",
        "chef",
        "combatmedic",
        "commando",
        "creaturehandler",
        "dancer",
        "doctor",
        "droidengineer",
        "entertainer",
        "fencer",
        "imagedesigner",
        "marksman",
        "medic",
        "merchant",
        "musician",
        "pikeman",
        "pistoleer",
        "politician",
        "ranger",
        "rifleman",
        "scout",
        "smuggler",
        "squadleader",
        "swordsman",
        "tailor",
        "teraskasi",
        "weaponsmith"
    };
    public static final String[][] SKILLS = 
    {
        
        {
            "crafting_architect_novice",
            "crafting_architect_production_01",
            "crafting_architect_production_02",
            "crafting_architect_production_03",
            "crafting_architect_production_04",
            "crafting_architect_techniques_01",
            "crafting_architect_techniques_02",
            "crafting_architect_techniques_03",
            "crafting_architect_techniques_04",
            "crafting_architect_harvesting_01",
            "crafting_architect_harvesting_02",
            "crafting_architect_harvesting_03",
            "crafting_architect_harvesting_04",
            "crafting_architect_blueprints_01",
            "crafting_architect_blueprints_02",
            "crafting_architect_blueprints_03",
            "crafting_architect_blueprints_04",
            "crafting_architect_master"
        },
        
        {
            "crafting_armorsmith_novice",
            "crafting_armorsmith_personal_01",
            "crafting_armorsmith_personal_02",
            "crafting_armorsmith_personal_03",
            "crafting_armorsmith_personal_04",
            "crafting_armorsmith_heavy_01",
            "crafting_armorsmith_heavy_02",
            "crafting_armorsmith_heavy_03",
            "crafting_armorsmith_heavy_04",
            "crafting_armorsmith_deflectors_01",
            "crafting_armorsmith_deflectors_02",
            "crafting_armorsmith_deflectors_03",
            "crafting_armorsmith_deflectors_04",
            "crafting_armorsmith_complexity_01",
            "crafting_armorsmith_complexity_02",
            "crafting_armorsmith_complexity_03",
            "crafting_armorsmith_complexity_04",
            "crafting_armorsmith_master"
        },
        
        {
            "crafting_artisan_novice",
            "crafting_artisan_engineering_01",
            "crafting_artisan_engineering_02",
            "crafting_artisan_engineering_03",
            "crafting_artisan_engineering_04",
            "crafting_artisan_domestic_01",
            "crafting_artisan_domestic_02",
            "crafting_artisan_domestic_03",
            "crafting_artisan_domestic_04",
            "crafting_artisan_business_01",
            "crafting_artisan_business_02",
            "crafting_artisan_business_03",
            "crafting_artisan_business_04",
            "crafting_artisan_survey_01",
            "crafting_artisan_survey_02",
            "crafting_artisan_survey_03",
            "crafting_artisan_survey_04",
            "crafting_artisan_master"
        },
        
        {
            "outdoors_bio_engineer_novice",
            "outdoors_bio_engineer_creature_01",
            "outdoors_bio_engineer_creature_02",
            "outdoors_bio_engineer_creature_03",
            "outdoors_bio_engineer_creature_04",
            "outdoors_bio_engineer_tissue_01",
            "outdoors_bio_engineer_tissue_02",
            "outdoors_bio_engineer_tissue_03",
            "outdoors_bio_engineer_tissue_04",
            "outdoors_bio_engineer_dna_harvesting_01",
            "outdoors_bio_engineer_dna_harvesting_02",
            "outdoors_bio_engineer_dna_harvesting_03",
            "outdoors_bio_engineer_dna_harvesting_04",
            "outdoors_bio_engineer_production_01",
            "outdoors_bio_engineer_production_02",
            "outdoors_bio_engineer_production_03",
            "outdoors_bio_engineer_production_04",
            "outdoors_bio_engineer_master"
        },
        
        {
            "combat_bountyhunter_novice",
            "combat_bountyhunter_investigation_01",
            "combat_bountyhunter_investigation_02",
            "combat_bountyhunter_investigation_03",
            "combat_bountyhunter_investigation_04",
            "combat_bountyhunter_droidcontrol_01",
            "combat_bountyhunter_droidcontrol_02",
            "combat_bountyhunter_droidcontrol_03",
            "combat_bountyhunter_droidcontrol_04",
            "combat_bountyhunter_droidresponse_01",
            "combat_bountyhunter_droidresponse_02",
            "combat_bountyhunter_droidresponse_03",
            "combat_bountyhunter_droidresponse_04",
            "combat_bountyhunter_support_01",
            "combat_bountyhunter_support_02",
            "combat_bountyhunter_support_03",
            "combat_bountyhunter_support_04",
            "combat_bountyhunter_master"
        },
        
        {
            "combat_brawler_novice",
            "combat_brawler_unarmed_01",
            "combat_brawler_unarmed_02",
            "combat_brawler_unarmed_03",
            "combat_brawler_unarmed_04",
            "combat_brawler_1handmelee_01",
            "combat_brawler_1handmelee_02",
            "combat_brawler_1handmelee_03",
            "combat_brawler_1handmelee_04",
            "combat_brawler_2handmelee_01",
            "combat_brawler_2handmelee_02",
            "combat_brawler_2handmelee_03",
            "combat_brawler_2handmelee_04",
            "combat_brawler_polearm_01",
            "combat_brawler_polearm_02",
            "combat_brawler_polearm_03",
            "combat_brawler_polearm_04",
            "combat_brawler_master"
        },
        
        {
            "combat_carbine_novice",
            "combat_carbine_accuracy_01",
            "combat_carbine_accuracy_02",
            "combat_carbine_accuracy_03",
            "combat_carbine_accuracy_04",
            "combat_carbine_speed_01",
            "combat_carbine_speed_02",
            "combat_carbine_speed_03",
            "combat_carbine_speed_04",
            "combat_carbine_ability_01",
            "combat_carbine_ability_02",
            "combat_carbine_ability_03",
            "combat_carbine_ability_04",
            "combat_carbine_support_01",
            "combat_carbine_support_02",
            "combat_carbine_support_03",
            "combat_carbine_support_04",
            "combat_carbine_master"
        },
        
        {
            "crafting_chef_novice",
            "crafting_chef_dish_01",
            "crafting_chef_dish_02",
            "crafting_chef_dish_03",
            "crafting_chef_dish_04",
            "crafting_chef_dessert_01",
            "crafting_chef_dessert_02",
            "crafting_chef_dessert_03",
            "crafting_chef_dessert_04",
            "crafting_chef_drink_01",
            "crafting_chef_drink_02",
            "crafting_chef_drink_03",
            "crafting_chef_drink_04",
            "crafting_chef_techniques_01",
            "crafting_chef_techniques_02",
            "crafting_chef_techniques_03",
            "crafting_chef_techniques_04",
            "crafting_chef_master"
        },
        
        {
            "science_combatmedic_novice",
            "science_combatmedic_healing_range_01",
            "science_combatmedic_healing_range_02",
            "science_combatmedic_healing_range_03",
            "science_combatmedic_healing_range_04",
            "science_combatmedic_healing_range_speed_01",
            "science_combatmedic_healing_range_speed_02",
            "science_combatmedic_healing_range_speed_03",
            "science_combatmedic_healing_range_speed_04",
            "science_combatmedic_medicine_01",
            "science_combatmedic_medicine_02",
            "science_combatmedic_medicine_03",
            "science_combatmedic_medicine_04",
            "science_combatmedic_support_01",
            "science_combatmedic_support_02",
            "science_combatmedic_support_03",
            "science_combatmedic_support_04",
            "science_combatmedic_master"
        },
        
        {
            "combat_commando_novice",
            "combat_commando_heavyweapon_accuracy_01",
            "combat_commando_heavyweapon_accuracy_02",
            "combat_commando_heavyweapon_accuracy_03",
            "combat_commando_heavyweapon_accuracy_04",
            "combat_commando_heavyweapon_speed_01",
            "combat_commando_heavyweapon_speed_02",
            "combat_commando_heavyweapon_speed_03",
            "combat_commando_heavyweapon_speed_04",
            "combat_commando_thrownweapon_01",
            "combat_commando_thrownweapon_02",
            "combat_commando_thrownweapon_03",
            "combat_commando_thrownweapon_04",
            "combat_commando_support_01",
            "combat_commando_support_02",
            "combat_commando_support_03",
            "combat_commando_support_04",
            "combat_commando_master"
        },
        
        {
            "outdoors_creaturehandler_novice",
            "outdoors_creaturehandler_taming_01",
            "outdoors_creaturehandler_taming_02",
            "outdoors_creaturehandler_taming_03",
            "outdoors_creaturehandler_taming_04",
            "outdoors_creaturehandler_training_01",
            "outdoors_creaturehandler_training_02",
            "outdoors_creaturehandler_training_03",
            "outdoors_creaturehandler_training_04",
            "outdoors_creaturehandler_healing_01",
            "outdoors_creaturehandler_healing_02",
            "outdoors_creaturehandler_healing_03",
            "outdoors_creaturehandler_healing_04",
            "outdoors_creaturehandler_support_01",
            "outdoors_creaturehandler_support_02",
            "outdoors_creaturehandler_support_03",
            "outdoors_creaturehandler_support_04",
            "outdoors_creaturehandler_master"
        },
        
        {
            "social_dancer_novice",
            "social_dancer_ability_01",
            "social_dancer_ability_02",
            "social_dancer_ability_03",
            "social_dancer_ability_04",
            "social_dancer_wound_01",
            "social_dancer_wound_02",
            "social_dancer_wound_03",
            "social_dancer_wound_04",
            "social_dancer_knowledge_01",
            "social_dancer_knowledge_02",
            "social_dancer_knowledge_03",
            "social_dancer_knowledge_04",
            "social_dancer_shock_01",
            "social_dancer_shock_02",
            "social_dancer_shock_03",
            "social_dancer_shock_04",
            "social_dancer_master"
        },
        
        {
            "science_doctor_novice",
            "science_doctor_wound_speed_01",
            "science_doctor_wound_speed_02",
            "science_doctor_wound_speed_03",
            "science_doctor_wound_speed_04",
            "science_doctor_wound_01",
            "science_doctor_wound_02",
            "science_doctor_wound_03",
            "science_doctor_wound_04",
            "science_doctor_ability_01",
            "science_doctor_ability_02",
            "science_doctor_ability_03",
            "science_doctor_ability_04",
            "science_doctor_support_01",
            "science_doctor_support_02",
            "science_doctor_support_03",
            "science_doctor_support_04",
            "science_doctor_master"
        },
        
        {
            "crafting_droidengineer_novice",
            "crafting_droidengineer_production_01",
            "crafting_droidengineer_production_02",
            "crafting_droidengineer_production_03",
            "crafting_droidengineer_production_04",
            "crafting_droidengineer_techniques_01",
            "crafting_droidengineer_techniques_02",
            "crafting_droidengineer_techniques_03",
            "crafting_droidengineer_techniques_04",
            "crafting_droidengineer_refinement_01",
            "crafting_droidengineer_refinement_02",
            "crafting_droidengineer_refinement_03",
            "crafting_droidengineer_refinement_04",
            "crafting_droidengineer_blueprints_01",
            "crafting_droidengineer_blueprints_02",
            "crafting_droidengineer_blueprints_03",
            "crafting_droidengineer_blueprints_04",
            "crafting_droidengineer_master"
        },
        
        {
            "social_entertainer_novice",
            "social_entertainer_hairstyle_01",
            "social_entertainer_hairstyle_02",
            "social_entertainer_hairstyle_03",
            "social_entertainer_hairstyle_04",
            "social_entertainer_music_01",
            "social_entertainer_music_02",
            "social_entertainer_music_03",
            "social_entertainer_music_04",
            "social_entertainer_dance_01",
            "social_entertainer_dance_02",
            "social_entertainer_dance_03",
            "social_entertainer_dance_04",
            "social_entertainer_healing_01",
            "social_entertainer_healing_02",
            "social_entertainer_healing_03",
            "social_entertainer_healing_04",
            "social_entertainer_master"
        },
        
        {
            "combat_1hsword_novice",
            "combat_1hsword_accuracy_01",
            "combat_1hsword_accuracy_02",
            "combat_1hsword_accuracy_03",
            "combat_1hsword_accuracy_04",
            "combat_1hsword_speed_01",
            "combat_1hsword_speed_02",
            "combat_1hsword_speed_03",
            "combat_1hsword_speed_04",
            "combat_1hsword_ability_01",
            "combat_1hsword_ability_02",
            "combat_1hsword_ability_03",
            "combat_1hsword_ability_04",
            "combat_1hsword_support_01",
            "combat_1hsword_support_02",
            "combat_1hsword_support_03",
            "combat_1hsword_support_04",
            "combat_1hsword_master"
        },
        
        {
            "social_imagedesigner_novice",
            "social_imagedesigner_hairstyle_01",
            "social_imagedesigner_hairstyle_02",
            "social_imagedesigner_hairstyle_03",
            "social_imagedesigner_hairstyle_04",
            "social_imagedesigner_exotic_01",
            "social_imagedesigner_exotic_02",
            "social_imagedesigner_exotic_03",
            "social_imagedesigner_exotic_04",
            "social_imagedesigner_bodyform_01",
            "social_imagedesigner_bodyform_02",
            "social_imagedesigner_bodyform_03",
            "social_imagedesigner_bodyform_04",
            "social_imagedesigner_markings_01",
            "social_imagedesigner_markings_02",
            "social_imagedesigner_markings_03",
            "social_imagedesigner_markings_04",
            "social_imagedesigner_master"
        },
        
        {
            "combat_marksman_novice",
            "combat_marksman_rifle_01",
            "combat_marksman_rifle_02",
            "combat_marksman_rifle_03",
            "combat_marksman_rifle_04",
            "combat_marksman_pistol_01",
            "combat_marksman_pistol_02",
            "combat_marksman_pistol_03",
            "combat_marksman_pistol_04",
            "combat_marksman_carbine_01",
            "combat_marksman_carbine_02",
            "combat_marksman_carbine_03",
            "combat_marksman_carbine_04",
            "combat_marksman_support_01",
            "combat_marksman_support_02",
            "combat_marksman_support_03",
            "combat_marksman_support_04",
            "combat_marksman_master"
        },
        
        {
            "science_medic_novice",
            "science_medic_injury_01",
            "science_medic_injury_02",
            "science_medic_injury_03",
            "science_medic_injury_04",
            "science_medic_injury_speed_01",
            "science_medic_injury_speed_02",
            "science_medic_injury_speed_03",
            "science_medic_injury_speed_04",
            "science_medic_ability_01",
            "science_medic_ability_02",
            "science_medic_ability_03",
            "science_medic_ability_04",
            "science_medic_crafting_01",
            "science_medic_crafting_02",
            "science_medic_crafting_03",
            "science_medic_crafting_04",
            "science_medic_master"
        },
        
        {
            "crafting_merchant_novice",
            "crafting_merchant_advertising_01",
            "crafting_merchant_advertising_02",
            "crafting_merchant_advertising_03",
            "crafting_merchant_advertising_04",
            "crafting_merchant_sales_01",
            "crafting_merchant_sales_02",
            "crafting_merchant_sales_03",
            "crafting_merchant_sales_04",
            "crafting_merchant_hiring_01",
            "crafting_merchant_hiring_02",
            "crafting_merchant_hiring_03",
            "crafting_merchant_hiring_04",
            "crafting_merchant_management_01",
            "crafting_merchant_management_02",
            "crafting_merchant_management_03",
            "crafting_merchant_management_04",
            "crafting_merchant_master"
        },
        
        {
            "social_musician_novice",
            "social_musician_ability_01",
            "social_musician_ability_02",
            "social_musician_ability_03",
            "social_musician_ability_04",
            "social_musician_wound_01",
            "social_musician_wound_02",
            "social_musician_wound_03",
            "social_musician_wound_04",
            "social_musician_knowledge_01",
            "social_musician_knowledge_02",
            "social_musician_knowledge_03",
            "social_musician_knowledge_04",
            "social_musician_shock_01",
            "social_musician_shock_02",
            "social_musician_shock_03",
            "social_musician_shock_04",
            "social_musician_master"
        },
        
        {
            "combat_polearm_novice",
            "combat_polearm_accuracy_01",
            "combat_polearm_accuracy_02",
            "combat_polearm_accuracy_03",
            "combat_polearm_accuracy_04",
            "combat_polearm_speed_01",
            "combat_polearm_speed_02",
            "combat_polearm_speed_03",
            "combat_polearm_speed_04",
            "combat_polearm_ability_01",
            "combat_polearm_ability_02",
            "combat_polearm_ability_03",
            "combat_polearm_ability_04",
            "combat_polearm_support_01",
            "combat_polearm_support_02",
            "combat_polearm_support_03",
            "combat_polearm_support_04",
            "combat_polearm_master"
        },
        
        {
            "combat_pistol_novice",
            "combat_pistol_accuracy_01",
            "combat_pistol_accuracy_02",
            "combat_pistol_accuracy_03",
            "combat_pistol_accuracy_04",
            "combat_pistol_speed_01",
            "combat_pistol_speed_02",
            "combat_pistol_speed_03",
            "combat_pistol_speed_04",
            "combat_pistol_ability_01",
            "combat_pistol_ability_02",
            "combat_pistol_ability_03",
            "combat_pistol_ability_04",
            "combat_pistol_support_01",
            "combat_pistol_support_02",
            "combat_pistol_support_03",
            "combat_pistol_support_04",
            "combat_pistol_master"
        },
        
        {
            "social_politician_novice",
            "social_politician_fiscal_01",
            "social_politician_fiscal_02",
            "social_politician_fiscal_03",
            "social_politician_fiscal_04",
            "social_politician_martial_01",
            "social_politician_martial_02",
            "social_politician_martial_03",
            "social_politician_martial_04",
            "social_politician_civic_01",
            "social_politician_civic_02",
            "social_politician_civic_03",
            "social_politician_civic_04",
            "social_politician_urban_01",
            "social_politician_urban_02",
            "social_politician_urban_03",
            "social_politician_urban_04",
            "social_politician_master"
        },
        
        {
            "outdoors_ranger_novice",
            "outdoors_ranger_movement_01",
            "outdoors_ranger_movement_02",
            "outdoors_ranger_movement_03",
            "outdoors_ranger_movement_04",
            "outdoors_ranger_tracking_01",
            "outdoors_ranger_tracking_02",
            "outdoors_ranger_tracking_03",
            "outdoors_ranger_tracking_04",
            "outdoors_ranger_harvest_01",
            "outdoors_ranger_harvest_02",
            "outdoors_ranger_harvest_03",
            "outdoors_ranger_harvest_04",
            "outdoors_ranger_support_01",
            "outdoors_ranger_support_02",
            "outdoors_ranger_support_03",
            "outdoors_ranger_support_04",
            "outdoors_ranger_master"
        },
        
        {
            "combat_rifleman_novice",
            "combat_rifleman_accuracy_01",
            "combat_rifleman_accuracy_02",
            "combat_rifleman_accuracy_03",
            "combat_rifleman_accuracy_04",
            "combat_rifleman_speed_01",
            "combat_rifleman_speed_02",
            "combat_rifleman_speed_03",
            "combat_rifleman_speed_04",
            "combat_rifleman_ability_01",
            "combat_rifleman_ability_02",
            "combat_rifleman_ability_03",
            "combat_rifleman_ability_04",
            "combat_rifleman_support_01",
            "combat_rifleman_support_02",
            "combat_rifleman_support_03",
            "combat_rifleman_support_04",
            "combat_rifleman_master"
        },
        
        {
            "outdoors_scout_novice",
            "outdoors_scout_movement_01",
            "outdoors_scout_movement_02",
            "outdoors_scout_movement_03",
            "outdoors_scout_movement_04",
            "outdoors_scout_tools_01",
            "outdoors_scout_tools_02",
            "outdoors_scout_tools_03",
            "outdoors_scout_tools_04",
            "outdoors_scout_harvest_01",
            "outdoors_scout_harvest_02",
            "outdoors_scout_harvest_03",
            "outdoors_scout_harvest_04",
            "outdoors_scout_camp_01",
            "outdoors_scout_camp_02",
            "outdoors_scout_camp_03",
            "outdoors_scout_camp_04",
            "outdoors_scout_master"
        },
        
        {
            "combat_smuggler_novice",
            "combat_smuggler_master",
            "combat_smuggler_underworld_01",
            "combat_smuggler_underworld_02",
            "combat_smuggler_underworld_03",
            "combat_smuggler_underworld_04",
            "combat_smuggler_slicing_01",
            "combat_smuggler_slicing_02",
            "combat_smuggler_slicing_03",
            "combat_smuggler_slicing_04",
            "combat_smuggler_combat_01",
            "combat_smuggler_combat_02",
            "combat_smuggler_combat_03",
            "combat_smuggler_combat_04",
            "combat_smuggler_spice_01",
            "combat_smuggler_spice_02",
            "combat_smuggler_spice_03",
            "combat_smuggler_spice_04"
        },
        
        {
            "outdoors_squadleader_novice",
            "outdoors_squadleader_movement_01",
            "outdoors_squadleader_movement_02",
            "outdoors_squadleader_movement_03",
            "outdoors_squadleader_movement_04",
            "outdoors_squadleader_offense_01",
            "outdoors_squadleader_offense_02",
            "outdoors_squadleader_offense_03",
            "outdoors_squadleader_offense_04",
            "outdoors_squadleader_defense_01",
            "outdoors_squadleader_defense_02",
            "outdoors_squadleader_defense_03",
            "outdoors_squadleader_defense_04",
            "outdoors_squadleader_support_01",
            "outdoors_squadleader_support_02",
            "outdoors_squadleader_support_03",
            "outdoors_squadleader_support_04",
            "outdoors_squadleader_master"
        },
        
        {
            "combat_2hsword_novice",
            "combat_2hsword_accuracy_01",
            "combat_2hsword_accuracy_02",
            "combat_2hsword_accuracy_03",
            "combat_2hsword_accuracy_04",
            "combat_2hsword_speed_01",
            "combat_2hsword_speed_02",
            "combat_2hsword_speed_03",
            "combat_2hsword_speed_04",
            "combat_2hsword_ability_01",
            "combat_2hsword_ability_02",
            "combat_2hsword_ability_03",
            "combat_2hsword_ability_04",
            "combat_2hsword_support_01",
            "combat_2hsword_support_02",
            "combat_2hsword_support_03",
            "combat_2hsword_support_04",
            "combat_2hsword_master"
        },
        
        {
            "crafting_tailor_novice",
            "crafting_tailor_casual_01",
            "crafting_tailor_casual_02",
            "crafting_tailor_casual_03",
            "crafting_tailor_casual_04",
            "crafting_tailor_field_01",
            "crafting_tailor_field_02",
            "crafting_tailor_field_03",
            "crafting_tailor_field_04",
            "crafting_tailor_formal_01",
            "crafting_tailor_formal_02",
            "crafting_tailor_formal_03",
            "crafting_tailor_formal_04",
            "crafting_tailor_production_01",
            "crafting_tailor_production_02",
            "crafting_tailor_production_03",
            "crafting_tailor_production_04",
            "crafting_tailor_master"
        },
        
        {
            "combat_unarmed_novice",
            "combat_unarmed_accuracy_01",
            "combat_unarmed_accuracy_02",
            "combat_unarmed_accuracy_03",
            "combat_unarmed_accuracy_04",
            "combat_unarmed_speed_01",
            "combat_unarmed_speed_02",
            "combat_unarmed_speed_03",
            "combat_unarmed_speed_04",
            "combat_unarmed_ability_01",
            "combat_unarmed_ability_02",
            "combat_unarmed_ability_03",
            "combat_unarmed_ability_04",
            "combat_unarmed_support_01",
            "combat_unarmed_support_02",
            "combat_unarmed_support_03",
            "combat_unarmed_support_04",
            "combat_unarmed_master"
        },
        
        {
            "crafting_weaponsmith_novice",
            "crafting_weaponsmith_melee_01",
            "crafting_weaponsmith_melee_02",
            "crafting_weaponsmith_melee_03",
            "crafting_weaponsmith_melee_04",
            "crafting_weaponsmith_firearms_01",
            "crafting_weaponsmith_firearms_02",
            "crafting_weaponsmith_firearms_03",
            "crafting_weaponsmith_firearms_04",
            "crafting_weaponsmith_munitions_01",
            "crafting_weaponsmith_munitions_02",
            "crafting_weaponsmith_munitions_03",
            "crafting_weaponsmith_munitions_04",
            "crafting_weaponsmith_techniques_01",
            "crafting_weaponsmith_techniques_02",
            "crafting_weaponsmith_techniques_03",
            "crafting_weaponsmith_techniques_04",
            "crafting_weaponsmith_master"
        }
    };
    public String[] getSkills(String profession) throws InterruptedException
    {
        if (profession == null || profession.equals(""))
        {
            return null;
        }
        for (int i = 0; i < PROFESSIONS.length; i++)
        {
            if (profession.equals(PROFESSIONS[i]))
            {
                return SKILLS[i];
            }
        }
        return null;
    }
    public obj_id makePlayer(obj_id player, location here, String species, String gender, String profession) throws InterruptedException
    {
        if (!profession.equals("artisan") && !profession.equals("brawler") && !profession.equals("entertainer") && !profession.equals("marksman") && !profession.equals("medic") && !profession.equals("scout") && !profession.equals("jedi"))
        {
            if (debug)
            {
                debugSpeakMsg(player, "Invalid profession given");
            }
            return null;
        }
        String speciesRoot = "object/creature/player/";
        String object = speciesRoot + species + "_" + gender + ".iff";
        obj_id npc = createObjectSimulator(object, here);
        if (npc == null)
        {
            if (debug)
            {
                debugSpeakMsg(player, "Could not create player from " + object);
            }
            return null;
        }
        dictionary racial_mods = null;
        String template = species + "_" + gender;
        String racial_tbl = "datatables/creation/racial_mods.iff";
        int templateRow = 0;
        if (gender.equals("male"))
        {
            templateRow = dataTableSearchColumnForString(template, 0, racial_tbl);
        }
        else 
        {
            templateRow = dataTableSearchColumnForString(template, 1, racial_tbl);
        }
        racial_mods = dataTableGetRow(racial_tbl, templateRow);
        if (racial_mods == null || racial_mods.isEmpty())
        {
            return null;
        }
        int professionRow = 0;
        if (profession.equals("artisan"))
        {
            professionRow = 0;
        }
        else if (profession.equals("brawler"))
        {
            professionRow = 1;
        }
        else if (profession.equals("entertainer"))
        {
            professionRow = 2;
        }
        else if (profession.equals("marksman"))
        {
            professionRow = 3;
        }
        else if (profession.equals("medic"))
        {
            professionRow = 4;
        }
        else if (profession.equals("scout"))
        {
            professionRow = 5;
        }
        else if (profession.equals("jedi"))
        {
            professionRow = 6;
        }
        dictionary prof_mods = dataTableGetRow("datatables/creation/profession_mods.iff", professionRow);
        if (debug)
        {
            debugSpeakMsg(player, "I want to set the following values:");
        }
        attribute[] attribs = new attribute[9];
        for (int x = HEALTH; x <= WILLPOWER; x++)
        {
            String aname = "";
            switch (x)
            {
                case 0:
                aname = "health";
                break;
                case 1:
                aname = "constitution";
                break;
                case 2:
                aname = "action";
                break;
                case 3:
                aname = "stamina";
                break;
                case 4:
                aname = "mind";
                break;
                case 5:
                aname = "willpower";
                break;
            }
            int newval = racial_mods.getInt(aname) + prof_mods.getInt(aname);
            attribs[x] = new attribute(x, newval);
            if (debug)
            {
                debugSpeakMsg(player, x + " " + newval);
            }
        }
        setMaxAttribs(npc, attribs);
        setAttribs(npc, attribs);
        String[] skills = getSkillListingForPlayer(npc);
        for (int i = 0; i < skills.length; ++i)
        {
            String[] skillCommands = getSkillCommandsProvided(skills[i]);
            for (int j = 0; j < skillCommands.length; ++j)
            {
                grantCommand(npc, skillCommands[j]);
            }
        }
        return npc;
    }
    public obj_id giveEquipment(obj_id player, obj_id reciever, String object) throws InterruptedException
    {
        if (reciever == null)
        {
            if (debug)
            {
                debugSpeakMsg(player, "Cannot give equipment to a null player");
            }
            return null;
        }
        obj_id id;
        if (object.indexOf("/weapon/") != -1)
        {
            id = weapons.createWeapon(object, reciever, CRAFTED_QUALITY);
            if (debug)
            {
                debugSpeakMsg(player, "Setting as current weapon");
            }
            if (!setCurrentWeapon(reciever, id))
            {
                if (debug)
                {
                    debugSpeakMsg(player, "There was an error setting the current weapon");
                }
            }
        }
        else 
        {
            id = createObjectInInventoryAllowOverload(object, reciever);
        }
        if (id == null)
        {
            if (debug)
            {
                debugSpeakMsg(player, "Could not create " + object);
            }
            else if (!equipOverride(id, reciever))
            {
                if (debug)
                {
                    debugSpeakMsg(player, "Could not equip " + object);
                }
                else if (debug)
                {
                    debugSpeakMsg(player, "Successfully created and equipped " + object);
                }
            }
        }
        if (object.indexOf("/armor/") != -1)
        {
            int armorLevel = AL_advanced;
            int armorCategory = AC_assault;
            if (object.indexOf("assault") != -1)
            {
                armorCategory = AC_assault;
            }
            else if (object.indexOf("battle") != -1)
            {
                armorCategory = AC_battle;
            }
            else if (object.indexOf("recon") != -1)
            {
                armorCategory = AC_reconnaissance;
            }
            else if (object.indexOf("composite") != -1 || object.indexOf("kashyyykian_hunting") != -1 || object.indexOf("chitin") != -1 || object.indexOf("ithorian_sentinel") != -1 || object.indexOf("assault_trooper") != -1 || object.indexOf("rebel_assault") != -1)
            {
                armorCategory = AC_assault;
            }
            else if (object.indexOf("padded") != -1 || object.indexOf("kashyyykian_black_mtn") != -1 || object.indexOf("bone") != -1 || object.indexOf("ithorian_defender") != -1 || object.indexOf("stormtrooper") != -1 || object.indexOf("rebel_battle") != -1)
            {
                armorCategory = AC_battle;
            }
            else if (object.indexOf("tantel") != -1 || object.indexOf("kashyyykian_ceremonial") != -1 || object.indexOf("ubese") != -1 || object.indexOf("ithorian_guardian") != -1 || object.indexOf("scout_trooper") != -1 || object.indexOf("marine") != -1)
            {
                armorCategory = AC_reconnaissance;
            }
            armor.setArmorDataPercent(id, armorLevel, armorCategory, GENERAL_PROTECTION, CONDITION);
            if (!isGameObjectTypeOf(id, GOT_armor_foot) && !isGameObjectTypeOf(id, GOT_armor_hand))
            {
                if (armorCategory == AC_assault)
                {
                    armor.setArmorSpecialProtectionPercent(id, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                }
                else if (armorCategory == AC_reconnaissance)
                {
                    armor.setArmorSpecialProtectionPercent(id, armor.DATATABLE_RECON_LAYER, 1.0f);
                }
            }
        }
        return id;
    }
    public void addBuff(obj_id player, obj_id reciever, String attrib) throws InterruptedException
    {
    }
    public int getSkillLevel(String skill) throws InterruptedException
    {
        if (skill.endsWith("_novice"))
        {
            return 0;
        }
        else if (skill.endsWith("_01"))
        {
            return 1;
        }
        else if (skill.endsWith("_02"))
        {
            return 2;
        }
        else if (skill.endsWith("_03"))
        {
            return 3;
        }
        else if (skill.endsWith("_04"))
        {
            return 4;
        }
        else if (skill.endsWith("_master"))
        {
            return 5;
        }
        else 
        {
            return -1;
        }
    }
    public boolean grantSkillRecursive(obj_id player, obj_id recipient, String skill) throws InterruptedException
    {
        if (debug)
        {
            debugSpeakMsg(player, "Granting skill " + skill);
        }
        String[] skills = getSkillPrerequisiteSkills(skill);
        for (int i = 0; i < skills.length; ++i)
        {
            if (!hasSkill(recipient, skills[i]))
            {
                grantSkillRecursive(player, recipient, skills[i]);
            }
        }
        return grantSkill(recipient, skill);
    }
    public void reset() throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id playerA = getObjIdObjVar(self, "combat_simulator.player_a");
        obj_id playerB = getObjIdObjVar(self, "combat_simulator.player_b");
        if (debug)
        {
            debugSpeakMsg(self, "Resetting simulator");
        }
        if (playerA != null)
        {
            if (hasObjVar(playerA, "combat_simulator.is_creature"))
            {
                removeAllObjVars(playerA);
                detachAllScripts(playerA);
                destroyObject(playerA);
            }
            else 
            {
                removeAllObjVars(playerA);
                detachAllScripts(playerA);
                destroyObjectSimulator(playerA);
            }
        }
        if (playerB != null)
        {
            if (hasObjVar(playerB, "combat_simulator.is_creature"))
            {
                removeAllObjVars(playerB);
                detachAllScripts(playerB);
                destroyObject(playerB);
            }
            else 
            {
                removeAllObjVars(playerB);
                detachAllScripts(playerB);
                destroyObjectSimulator(playerB);
            }
        }
        removeObjVar(self, "combat_simulator.player_a");
        removeObjVar(self, "combat_simulator.player_b");
        removeObjVar(self, "combat_simulator.commands_a");
        removeObjVar(self, "combat_simulator.commands_b");
        removeObjVar(self, "combat_simulator.label");
        removeObjVar(self, "combat_simulator.rounds");
        removeObjVar(self, "combat_simulator.range");
    }
    public void removeCommandList(obj_id player) throws InterruptedException
    {
        int commandListNumber = 0;
        while (hasObjVar(player, "combat_simulator.command_list." + commandListNumber))
        {
            removeObjVar(player, "combat_simulator.command_list." + commandListNumber);
            ++commandListNumber;
        }
    }
    public void putCommandList(obj_id player, String[] commands) throws InterruptedException
    {
        removeCommandList(player);
        int commandListNumber = 0;
        Vector newCommands = new Vector();
        int sizeOfNewCommands = 0;
        for (int i = 0; i < commands.length; ++i)
        {
            String nextCommand = commands[i];
            if (nextCommand.length() + sizeOfNewCommands > 900)
            {
                setObjVar(player, "combat_simulator.command_list." + commandListNumber, newCommands);
                ++commandListNumber;
                sizeOfNewCommands = 0;
                newCommands.clear();
            }
            newCommands.add(nextCommand);
            sizeOfNewCommands += nextCommand.length();
        }
    }
    public Vector getCommandList(obj_id player) throws InterruptedException
    {
        int commandListNumber = 0;
        Vector commands = new Vector();
        int sizeOfNewCommands = 0;
        while (hasObjVar(player, "combat_simulator.command_list." + commandListNumber))
        {
            String[] commandList = getStringArrayObjVar(player, "combat_simulator.command_list." + commandListNumber);
            ++commandListNumber;
            for (int i = 0; i < commandList.length; i++)
            {
                commands.add(commandList[i]);
            }
        }
        return commands;
    }
    public static final String[] validCommands = 
    {
        "setLabel <label>",
        "setNumRounds <numRounds>",
        "setRange <range>",
        "makePlayer <A|B> <species> <gender> <profession>",
        "makeCreature <A|B> <creature>",
        "giveEquipment <A|B> <object>",
        "giveProfession <A|B> <profession> <level> (level is 0 (novice) to 5 (master))",
        "addCommand <A|B> <command>",
        "getPlayerInfo <A|B>",
        "setAttribWound <A|B> <attrib> <value>",
        "giveBuff <A|B> <attrib>",
        "sendCombatInfo",
        "startCombat",
        "reset"
    };
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        if (arg.equals("debug"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerA = getObjIdObjVar(self, "combat_simulator.player_a");
        obj_id playerB = getObjIdObjVar(self, "combat_simulator.player_b");
        String label = getStringObjVar(self, "combat_simulator.label");
        int rounds = getIntObjVar(self, "combat_simulator.rounds");
        Vector commandsA = getResizeableStringArrayObjVar(self, "combat_simulator.commands_a");
        if (commandsA == null)
        {
            commandsA = new Vector();
        }
        Vector commandsB = getResizeableStringArrayObjVar(self, "combat_simulator.commands_b");
        if (commandsB == null)
        {
            commandsB = new Vector();
        }
        Vector commandList = getCommandList(self);
        if (commandList == null)
        {
            commandList = new Vector();
        }
        if (!isGod(self) && !hasObjVar(self, "combat_simulator.created_from_combat_simulator_user"))
        {
            if (debug)
            {
                debugSpeakMsg(self, "You cannot run the combat simulator when not in god mode");
            }
            return SCRIPT_OVERRIDE;
        }
        if (arg.equals("setLabel"))
        {
            if (st.countTokens() != 1)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[0]);
                }
                return SCRIPT_OVERRIDE;
            }
            label = st.nextToken();
            setObjVar(self, "combat_simulator.label", label);
            if (debug)
            {
                debugSpeakMsg(self, "Label set as : " + label);
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("setNumRounds"))
        {
            if (st.countTokens() != 1)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[1]);
                }
                return SCRIPT_OVERRIDE;
            }
            int numRounds = utils.stringToInt(st.nextToken());
            setObjVar(self, "combat_simulator.rounds", numRounds);
            if (debug)
            {
                debugSpeakMsg(self, "Number of rounds set as : " + numRounds);
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("setRange"))
        {
            if (st.countTokens() != 1)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[2]);
                }
                return SCRIPT_OVERRIDE;
            }
            int range = utils.stringToInt(st.nextToken());
            setObjVar(self, "combat_simulator.range", range);
            if (debug)
            {
                debugSpeakMsg(self, "Range set as : " + range);
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("makePlayer"))
        {
            if (st.countTokens() != 4)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[3]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String species = st.nextToken();
            String gender = st.nextToken();
            String profession = st.nextToken();
            location loc = getLocation(self);
            float range = 1.0f;
            if (hasObjVar(self, "combat_simulator.range"))
            {
                range = getIntObjVar(self, "combat_simulator.range") / 2.0f;
            }
            loc.z += 3;
            if (player.equals("A") && playerA == null)
            {
                loc.x -= range;
                playerA = makePlayer(self, loc, species, gender, profession);
                attachScript(playerA, actorScript);
                setObjVar(self, "combat_simulator.player_a", playerA);
            }
            else if (player.equals("B") && playerB == null)
            {
                loc.x += range;
                playerB = makePlayer(self, loc, species, gender, profession);
                attachScript(playerB, actorScript);
                setObjVar(self, "combat_simulator.player_b", playerB);
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("makePlayer " + player + " " + species + " " + gender + " " + profession);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("makeCreature"))
        {
            if (st.countTokens() != 2)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[4]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String creature = st.nextToken();
            location loc = getLocation(self);
            float range = 1.0f;
            if (hasObjVar(self, "combat_simulator.range"))
            {
                range = getIntObjVar(self, "combat_simulator.range") / 2.0f;
            }
            loc.z += 3;
            if (player.equals("A") && playerA == null)
            {
                loc.x -= range;
                playerA = create.createCreature(creature, loc, true);
                stop(playerA);
                attachScript(playerA, actorScript);
                setObjVar(self, "combat_simulator.player_a", playerA);
                setObjVar(playerA, "combat_simulator.is_creature", true);
            }
            else if (player.equals("B") && playerB == null)
            {
                loc.x += range;
                playerB = create.createCreature(creature, loc, true);
                stop(playerB);
                attachScript(playerB, actorScript);
                setObjVar(self, "combat_simulator.player_b", playerB);
                setObjVar(playerB, "combat_simulator.is_creature", true);
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("makeCreature " + player + " " + creature);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("giveEquipment"))
        {
            if (st.countTokens() != 2)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[5]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String object = st.nextToken();
            if (player.equals("A"))
            {
                giveEquipment(self, playerA, object);
            }
            else if (player.equals("B"))
            {
                giveEquipment(self, playerB, object);
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("giveEquipment " + player + " " + object);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("giveProfession"))
        {
            if (st.countTokens() != 3)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[6]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String profession = st.nextToken();
            int level = utils.stringToInt(st.nextToken());
            obj_id playerId = null;
            if (player.equals("A"))
            {
                playerId = playerA;
            }
            else if (player.equals("B"))
            {
                playerId = playerB;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
            if (playerId == null)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "Cannot give profession to a null player");
                }
                return SCRIPT_OVERRIDE;
            }
            String[] skills = getSkills(profession);
            if (skills == null || skills.length == 0)
            {
                return SCRIPT_OVERRIDE;
            }
            boolean result = true;
            for (int i = 0; i < skills.length; i++)
            {
                if (!hasSkill(playerId, skills[i]) && getSkillLevel(skills[i]) <= level)
                {
                    result &= grantSkillRecursive(self, playerId, skills[i]);
                }
            }
            if (result)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "Profession granted");
                }
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("giveProfession " + player + " " + profession + " " + level);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("addCommand"))
        {
            if (st.countTokens() != 2)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[7]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String command = st.nextToken();
            if (player.equals("A"))
            {
                if (!hasCommand(playerA, command))
                {
                    if (debug)
                    {
                        debugSpeakMsg(self, "Player did not have " + command + ", granting it");
                    }
                    if (!grantCommand(playerA, command))
                    {
                        if (debug)
                        {
                            debugSpeakMsg(self, command + " could not be given");
                        }
                        return SCRIPT_OVERRIDE;
                    }
                    else if (!hasCommand(playerA, command))
                    {
                        if (debug)
                        {
                            debugSpeakMsg(self, "Invalid command, not adding to list");
                        }
                        return SCRIPT_OVERRIDE;
                    }
                }
                if (debug)
                {
                    debugSpeakMsg(self, command + " added");
                }
                commandsA.add(command);
                setObjVar(self, "combat_simulator.commands_a", commandsA);
            }
            else if (player.equals("B"))
            {
                if (!hasCommand(playerB, command))
                {
                    if (debug)
                    {
                        debugSpeakMsg(self, "Player did not have " + command + ", granting it");
                    }
                    if (!grantCommand(playerB, command))
                    {
                        if (debug)
                        {
                            debugSpeakMsg(self, command + " could not be given");
                        }
                        return SCRIPT_OVERRIDE;
                    }
                    else if (!hasCommand(playerB, command))
                    {
                        if (debug)
                        {
                            debugSpeakMsg(self, "Invalid command, not adding to list");
                        }
                        return SCRIPT_OVERRIDE;
                    }
                }
                if (debug)
                {
                    debugSpeakMsg(self, command + " added");
                }
                commandsB.add(command);
                setObjVar(self, "combat_simulator.commands_b", commandsB);
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("addCommand " + player + " " + command);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("getPlayerInfo"))
        {
            if (st.countTokens() != 1)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[8]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            obj_id playerId = null;
            Vector playerCommandList = new Vector();
            if (player.equals("A"))
            {
                playerId = playerA;
                playerCommandList = commandsA;
            }
            else if (player.equals("B"))
            {
                playerId = playerB;
                playerCommandList = commandsB;
            }
            else if (player.equals("self"))
            {
                playerId = self;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
            if (playerId == null)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "Cannot get info of a null player");
                }
                return SCRIPT_OVERRIDE;
            }
            if (debug)
            {
                debugSpeakMsg(self, "Attributes:");
            }
            for (int x = HEALTH; x <= WILLPOWER; x++)
            {
                String aname = "";
                switch (x)
                {
                    case 0:
                    aname = "health";
                    break;
                    case 1:
                    aname = "constitution";
                    break;
                    case 2:
                    aname = "action";
                    break;
                    case 3:
                    aname = "stamina";
                    break;
                    case 4:
                    aname = "mind";
                    break;
                    case 5:
                    aname = "willpower";
                    break;
                }
                String spaces = "   ";
                if (x % 3 != 0)
                {
                    spaces = "      ";
                }
                if (debug)
                {
                    debugSpeakMsg(self, spaces + aname + ": " + getAttrib(playerId, x));
                }
            }
            if (debug)
            {
                debugSpeakMsg(self, "Skills:");
            }
            String[] skills = getSkillListingForPlayer(playerId);
            Arrays.sort(skills);
            for (int i = 0; i < skills.length; ++i)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "   " + skills[i]);
                }
            }
            if (debug)
            {
                debugSpeakMsg(self, "Commands:");
            }
            String[] commands = getCommandListingForPlayer(playerId);
            Arrays.sort(commands);
            for (int i = 0; i < commands.length; ++i)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "   " + commands[i]);
                }
            }
            if (debug)
            {
                debugSpeakMsg(self, "Command List:");
            }
            for (int i = 0; i < playerCommandList.size(); ++i)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "   " + ((String)playerCommandList.get(i)));
                }
            }
            obj_id objWeapon = getCurrentWeapon(playerId);
            int intWeaponType = getWeaponType(objWeapon);
            String strWeaponType = combat.getWeaponStringType(intWeaponType);
            if (debug)
            {
                debugSpeakMsg(self, "Weapon: " + strWeaponType + " Id: " + objWeapon + " Type: " + intWeaponType);
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("setAttribWound"))
        {
            if (st.countTokens() != 3)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[9]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String attrib = st.nextToken();
            int value = utils.stringToInt(st.nextToken());
            int attribNum;
            obj_id playerId = null;
            if (player.equals("A"))
            {
                playerId = playerA;
            }
            else if (player.equals("B"))
            {
                playerId = playerB;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
            if (attrib.equals("health"))
            {
                attribNum = 0;
            }
            else if (attrib.equals("constitution"))
            {
                attribNum = 1;
            }
            else if (attrib.equals("action"))
            {
                attribNum = 2;
            }
            else if (attrib.equals("stamina"))
            {
                attribNum = 3;
            }
            else if (attrib.equals("mind"))
            {
                attribNum = 4;
            }
            else if (attrib.equals("willpower"))
            {
                attribNum = 5;
            }
            else 
            {
                if (debug)
                {
                    debugSpeakMsg(self, attrib + " is not a valid attribute");
                }
                return SCRIPT_OVERRIDE;
            }
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("setAttribWound " + player + " " + attrib + " " + value);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("giveBuff"))
        {
            if (st.countTokens() != 2)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[10]);
                }
                return SCRIPT_OVERRIDE;
            }
            String player = st.nextToken();
            String attrib = st.nextToken();
            if (!attrib.equals("health") && !attrib.equals("constitution") && !attrib.equals("action") && !attrib.equals("stamina") && !attrib.equals("mind") && !attrib.equals("willpower"))
            {
                if (debug)
                {
                    debugSpeakMsg(self, attrib + " is not a valid attribute to buff");
                }
            }
            obj_id playerId = null;
            if (player.equals("A"))
            {
                playerId = playerA;
            }
            else if (player.equals("B"))
            {
                playerId = playerB;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
            addBuff(self, playerId, attrib);
            if (!hasObjVar(self, "combat_simulator.stop_accumulating_commands"))
            {
                commandList.add("giveBuff " + player + " " + attrib);
                putCommandList(self, (String[])commandList.toArray(new String[0]));
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("sendCombatInfo"))
        {
            if (playerA == null || playerB == null)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "need to have both players for combat simulator");
                }
                return SCRIPT_OVERRIDE;
            }
            if (commandsA.size() == 0)
            {
                commandsA.add("defaultAttack");
            }
            if (commandsB.size() == 0)
            {
                commandsB.add("defaultAttack");
            }
            if (!hasObjVar(self, "combat_simulator.first_shot"))
            {
                setObjVar(self, "combat_simulator.first_shot", "A");
            }
            String firstShotPlayer = getStringObjVar(self, "combat_simulator.first_shot");
            if (firstShotPlayer.equals("A"))
            {
                if (debug)
                {
                    debugSpeakMsg(self, "player A is getting info first");
                }
                setObjVar(playerA, "combat_simulator.owner", self);
                setObjVar(playerA, "combat_simulator.enemy", playerB);
                setObjVar(playerA, "combat_simulator.queue_commands", commandsA);
                if (!hasObjVar(playerA, "combat_simulator.is_creature"))
                {
                    pvpMakeDeclared(playerA);
                    pvpSetAlignedFaction(playerA, (370444368));
                }
                messageTo(playerA, "prepareForCombat", null, 0, false);
                setObjVar(playerB, "combat_simulator.owner", self);
                setObjVar(playerB, "combat_simulator.enemy", playerA);
                setObjVar(playerB, "combat_simulator.queue_commands", commandsB);
                if (!hasObjVar(playerB, "combat_simulator.is_creature"))
                {
                    pvpMakeDeclared(playerB);
                    pvpSetAlignedFaction(playerB, (-615855020));
                }
                messageTo(playerB, "prepareForCombat", null, 0, false);
            }
            else if (firstShotPlayer.equals("B"))
            {
                if (debug)
                {
                    debugSpeakMsg(self, "player B is getting info first");
                }
                setObjVar(playerB, "combat_simulator.owner", self);
                setObjVar(playerB, "combat_simulator.enemy", playerA);
                setObjVar(playerB, "combat_simulator.queue_commands", commandsB);
                if (!hasObjVar(playerB, "combat_simulator.is_creature"))
                {
                    pvpMakeDeclared(playerB);
                    pvpSetAlignedFaction(playerB, (-615855020));
                }
                messageTo(playerB, "prepareForCombat", null, 0, false);
                setObjVar(playerA, "combat_simulator.owner", self);
                setObjVar(playerA, "combat_simulator.enemy", playerB);
                setObjVar(playerA, "combat_simulator.queue_commands", commandsA);
                if (!hasObjVar(playerA, "combat_simulator.is_creature"))
                {
                    pvpMakeDeclared(playerA);
                    pvpSetAlignedFaction(playerA, (370444368));
                }
                messageTo(playerA, "prepareForCombat", null, 0, false);
            }
            if (debug)
            {
                debugSpeakMsg(self, "Combat info sent");
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("startCombat"))
        {
            if (playerA == null || playerB == null || label == null || label.equals(""))
            {
                if (debug)
                {
                    debugSpeakMsg(self, "need to have both players and a label for combat simulator");
                }
                return SCRIPT_OVERRIDE;
            }
            setObjVar(self, "combat_simulator.combat_start_time", Long.toString(System.currentTimeMillis()));
            if (!hasObjVar(self, "combat_simulator.rounds"))
            {
                setObjVar(self, "combat_simulator.rounds", 1);
            }
            if (!hasObjVar(self, "combat_simulator.current_round"))
            {
                setObjVar(self, "combat_simulator.current_round", 1);
            }
            setObjVar(self, "combat_simulator.stop_accumulating_commands", 1);
            if (!hasObjVar(self, "combat_simulator.first_shot"))
            {
                setObjVar(self, "combat_simulator.first_shot", "A");
            }
            String firstShotPlayer = getStringObjVar(self, "combat_simulator.first_shot");
            if (firstShotPlayer.equals("A"))
            {
                if (debug)
                {
                    debugSpeakMsg(self, "player A is going first");
                }
                messageTo(playerA, "startCombat", null, 0, false);
                messageTo(playerB, "startCombat", null, 0, false);
                setObjVar(self, "combat_simulator.first_shot", "B");
            }
            else if (firstShotPlayer.equals("B"))
            {
                if (debug)
                {
                    debugSpeakMsg(self, "player B is going first");
                }
                messageTo(playerB, "startCombat", null, 0, false);
                messageTo(playerA, "startCombat", null, 0, false);
                setObjVar(self, "combat_simulator.first_shot", "A");
            }
            if (debug)
            {
                if (utils.hasScriptVar(playerA, "inAlignedStructure"))
                {
                    debugSpeakMsg(self, "player A has factional bonus");
                }
                if (utils.hasScriptVar(playerA, "inAlignedStructure"))
                {
                    debugSpeakMsg(self, "player B has factional bonus");
                }
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("destroySim"))
        {
            if (st.countTokens() != 1)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + "destroySim <obj_id>");
                }
                return SCRIPT_OVERRIDE;
            }
            obj_id target = utils.stringToObjId(st.nextToken());
            detachAllScripts(target);
            destroyObjectSimulator(target);
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("getIds"))
        {
            if (debug)
            {
                debugSpeakMsg(self, "self id: " + self);
            }
            if (playerA != null)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "playerA id: " + playerA);
                }
            }
            if (playerB != null)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "playerB id: " + playerB);
                }
            }
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("reset"))
        {
            reset();
            removeObjVar(self, "combat_simulator.current_round");
            removeObjVar(self, "combat_simulator.stop_accumulating_commands");
            removeObjVar(self, "combat_simulator.first_shot");
            removeCommandList(self);
            return SCRIPT_OVERRIDE;
        }
        else if (arg.equals("help"))
        {
            if (debug)
            {
                debugSpeakMsg(self, "Available Commands:");
            }
            for (int i = 0; i < validCommands.length; ++i)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "[combat_simulator] " + validCommands[i]);
                }
            }
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("debug on"))
        {
            debug = true;
        }
        else if (text.equals("debug off"))
        {
            debug = false;
        }
        return SCRIPT_CONTINUE;
    }
    public int endCombat(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            debugSpeakMsg(self, "Got message that the fight is over");
        }
        obj_id playerA = getObjIdObjVar(self, "combat_simulator.player_a");
        obj_id playerB = getObjIdObjVar(self, "combat_simulator.player_b");
        long combatStartTime = Long.parseLong(getStringObjVar(self, "combat_simulator.combat_start_time"));
        String label = getStringObjVar(self, "combat_simulator.label");
        int rounds = getIntObjVar(self, "combat_simulator.rounds");
        int currentRound = getIntObjVar(self, "combat_simulator.current_round");
        obj_id winner = params.getObjId("winner");
        obj_id loser = null;
        String winningPlayer = "";
        if (winner == playerA)
        {
            winningPlayer = "A";
            loser = playerB;
        }
        else if (winner == playerB)
        {
            winningPlayer = "B";
            loser = playerA;
        }
        else if (debug)
        {
            debugSpeakMsg(self, "Can't determine winner of combat for some reason");
        }
        if (currentRound == 1)
        {
            try
            {
                FileWriter writer = new FileWriter("combat_simulator." + label);
                writer.write("Round:\tWinner:\tWinnerH:\tWinnerA:\tWinnerM:\t#AttacksW:\t#AttacksL:\tTime:\n");
                writer.close();
            }
            catch(Exception e)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "failed to create combat_simulator." + label + " : " + e);
                }
            }
            try
            {
                FileWriter writer = new FileWriter("combat_simulator." + label + ".info");
                String[] commands = (String[])(getCommandList(self)).toArray(new String[0]);
                for (int i = 0; i < commands.length; ++i)
                {
                    writer.write(commands[i] + "\n");
                }
                writer.close();
            }
            catch(Exception e)
            {
                if (debug)
                {
                    debugSpeakMsg(self, "failed to create combat_simulator." + label + ".info : " + e);
                }
            }
        }
        int health = getAttrib(winner, HEALTH);
        int action = getAttrib(winner, ACTION);
        int mind = getAttrib(winner, MIND);
        int numAttacksWinner = getIntObjVar(winner, "combat_simulator.number_of_attacks");
        int numAttacksLoser = getIntObjVar(loser, "combat_simulator.number_of_attacks");
        long currentTime = System.currentTimeMillis();
        long totalTime = currentTime - combatStartTime;
        totalTime /= 1000.0;
        setObjVar(self, "combat_simulator.combat_start_time", currentTime);
        String message = (currentRound + "/" + rounds + "\t" + winningPlayer + "\t" + health + "\t" + action + "\t" + mind + "\t" + numAttacksWinner + "\t" + numAttacksLoser + "\t" + totalTime + "\n");
        if (debug)
        {
            debugSpeakMsg(self, message);
        }
        LOG("combat_simulator." + label, message);
        addToOutputFile(message);
        debugSpeakMsg(self, "Completed combat round " + currentRound);
        if (currentRound < rounds)
        {
            setObjVar(self, "combat_simulator.current_round", currentRound + 1);
            messageTo(self, "runSimulation", null, 1.0f, false);
        }
        else 
        {
            mergeSubLabelFiles();
            reset();
            removeAllObjVars(self);
            detachScript(self, "player.base.base_player");
            destroyObjectSimulator(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int runSimulation(obj_id self, dictionary params) throws InterruptedException
    {
        reset();
        String[] commands = (String[])(getCommandList(self)).toArray(new String[0]);
        if (commands == null)
        {
            debugSpeakMsg(self, "Cannot run simulator - null commands issued");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < commands.length; ++i)
        {
            debugSpeakMsg(self, commands[i]);
        }
        messageTo(self, "sendCombatInfo", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int sendCombatInfo(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "sendCombatInfo");
        messageTo(self, "startCombat", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int startCombat(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "startCombat");
        return SCRIPT_CONTINUE;
    }
    public void addToOutputFile(String fileContents) throws InterruptedException
    {
        obj_id self = getSelf();
        String fileName = getStringObjVar(self, "combat_simulator.label");
        fileName = "combat_simulator." + fileName;
        try
        {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(fileContents);
            writer.close();
        }
        catch(Exception e)
        {
            if (debug)
            {
                debugSpeakMsg(self, "failed to write " + fileName + " : " + e);
            }
        }
    }
    public void mergeSubLabelFiles() throws InterruptedException
    {
        obj_id self = getSelf();
        String label = getStringObjVar(self, "combat_simulator.label");
        int subLabelIndex = label.indexOf('.');
        if (subLabelIndex == -1)
        {
            return;
        }
        label = "combat_simulator." + label.substring(0, subLabelIndex + 1);
        String output = "Round:\tWinner:\tWinnerH:\tWinnerA:\tWinnerM:\t#AttacksW:\t#AttacksL:\tTime:\n";
        File dir = new File(".");
        String[] children = dir.list();
        if (children == null)
        {
            return;
        }
        else 
        {
            for (int i = 0; i < children.length; ++i)
            {
                String filename = children[i];
                File file = new File(filename);
                if (file.isFile() && filename.startsWith(label) && !filename.endsWith(".info"))
                {
                    try
                    {
                        BufferedReader reader = new BufferedReader(new FileReader(filename));
                        String lineRead = reader.readLine();
                        while ((lineRead = reader.readLine()) != null)
                        {
                            output += (lineRead + "\n");
                        }
                        reader.close();
                    }
                    catch(Exception e)
                    {
                        if (debug)
                        {
                            debugSpeakMsg(self, "failed to open " + filename + " : " + e);
                        }
                    }
                }
            }
        }
        label = label.substring(0, label.length() - 1);
        try
        {
            FileWriter writer = new FileWriter(label);
            writer.write(output);
            writer.close();
        }
        catch(Exception e)
        {
            if (debug)
            {
                debugSpeakMsg(self, "failed to create " + label + " : " + e);
            }
        }
    }
}
