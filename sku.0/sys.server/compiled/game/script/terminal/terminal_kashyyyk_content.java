package script.terminal;

import script.*;
import script.library.*;

import java.util.Vector;

public class terminal_kashyyyk_content extends script.base_script
{
    public terminal_kashyyyk_content()
    {
    }
    public static final int CASH_AMOUNT = 50000;
    public static final int AMT = 100000;
    public static final int FACTION_AMT = 250000;
    public static final float CRAFTED_QUALITY = 0.85f;
    public static final float CONDITION = 1.0f;
    public static final float GENERAL_PROTECTION = 0.94f;
    public static final String SKILL_TBL = "datatables/skill/skills.iff";
    public static final String SKILL_TERMINAL_TBL = "datatables/skill/skill_terminal.iff";
    public static final String SKILL_LOADOUT_TBL = "datatables/skill/loadout.iff";
    public static final string_id SID_TERMINAL_PROMPT = new string_id("skill_teacher", "skill_terminal_prompt");
    public static final string_id SID_TERMINAL_TITLE = new string_id("skill_teacher", "skill_terminal_title");
    public static final string_id SID_TERMINAL_DISABLED = new string_id("skill_teacher", "skill_terminal_disabled");
    public static final string_id SID_TERMINAL_DENIED = new string_id("skill_teacher", "skill_terminal_denied");
    public static final string_id SID_TERMINAL_MAX_SKILLS = new string_id("skill_teacher", "skill_terminal_max_skills");
    public static final string_id PROSE_GRANT_SKILL = new string_id("skill_teacher", "skill_terminal_grant");
    public static final string_id PROSE_GRANT_SKILL_FAIL = new string_id("skill_teacher", "skill_terminal_grant_fail");
    public static final String[] CHARACTER_BUILDER_OPTIONS = 
    {
        "Kachirho",
        "Dead Forest",
        "Myyydril Caverns",
        "Hunting Grounds",
        "Bocctyyy",
        "Hracca",
        "Rryatt Trail",
        "Blackscale Slave Camp",
        "Avatar Platform",
        "Space Mining",
        "Space Missions",
        "Cybernetics"
    };
    public static final String[] KACHIRHO_OPTIONS = 
    {
        "Melee",
        "Ranged",
        "Healer"
    };
    public static final String[] SPACE_MINING_OPTIONS = 
    {
        "Rebel Pilot",
        "Imperial Pilot",
        "Neutral Pilot"
    };
    public static final String ARMOR_SET_PREFIX = "object/tangible/wearables/armor/";
    public static final String[] ARMOR_SET_ASSAULT_1 = 
    {
        "composite/armor_composite_bicep_l.iff",
        "composite/armor_composite_chest_plate.iff",
        "composite/armor_composite_bicep_r.iff",
        "composite/armor_composite_gloves.iff",
        "composite/armor_composite_boots.iff",
        "composite/armor_composite_helmet.iff",
        "composite/armor_composite_bracer_l.iff",
        "composite/armor_composite_leggings.iff",
        "composite/armor_composite_bracer_r.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_2 = 
    {
        "chitin/armor_chitin_s01_bicep_l.iff",
        "chitin/armor_chitin_s01_bicep_r.iff",
        "chitin/armor_chitin_s01_boots.iff",
        "chitin/armor_chitin_s01_bracer_l.iff",
        "chitin/armor_chitin_s01_bracer_r.iff",
        "chitin/armor_chitin_s01_chest_plate.iff",
        "chitin/armor_chitin_s01_gloves.iff",
        "chitin/armor_chitin_s01_helmet.iff",
        "chitin/armor_chitin_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_3 = 
    {
        "marauder/armor_marauder_s03_bicep_l.iff",
        "marauder/armor_marauder_s03_bicep_r.iff",
        "marauder/armor_marauder_s03_boots.iff",
        "marauder/armor_marauder_s03_bracer_l.iff",
        "marauder/armor_marauder_s03_bracer_r.iff",
        "marauder/armor_marauder_s03_chest_plate.iff",
        "marauder/armor_marauder_s03_gloves.iff",
        "marauder/armor_marauder_s03_helmet.iff",
        "marauder/armor_marauder_s03_leggings.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_4 = 
    {
        "bounty_hunter/armor_bounty_hunter_crafted_bicep_l.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bicep_r.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_boots.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bracer_l.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_bracer_r.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_chest_plate.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_gloves.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_helmet.iff",
        "bounty_hunter/armor_bounty_hunter_crafted_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_1 = 
    {
        "bone/armor_bone_s01_bicep_l.iff",
        "bone/armor_bone_s01_bicep_r.iff",
        "bone/armor_bone_s01_boots.iff",
        "bone/armor_bone_s01_bracer_l.iff",
        "bone/armor_bone_s01_bracer_r.iff",
        "bone/armor_bone_s01_chest_plate.iff",
        "bone/armor_bone_s01_gloves.iff",
        "bone/armor_bone_s01_helmet.iff",
        "bone/armor_bone_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_2 = 
    {
        "marauder/armor_marauder_s01_bicep_l.iff",
        "marauder/armor_marauder_s01_bicep_r.iff",
        "marauder/armor_marauder_s01_boots.iff",
        "marauder/armor_marauder_s01_bracer_l.iff",
        "marauder/armor_marauder_s01_bracer_r.iff",
        "marauder/armor_marauder_s01_chest_plate.iff",
        "marauder/armor_marauder_s01_gloves.iff",
        "marauder/armor_marauder_s01_helmet.iff",
        "marauder/armor_marauder_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_3 = 
    {
        "padded/armor_padded_s01_bicep_l.iff",
        "padded/armor_padded_s01_bicep_r.iff",
        "padded/armor_padded_s01_boots.iff",
        "padded/armor_padded_s01_bracer_l.iff",
        "padded/armor_padded_s01_bracer_r.iff",
        "padded/armor_padded_s01_chest_plate.iff",
        "padded/armor_padded_s01_gloves.iff",
        "padded/armor_padded_s01_helmet.iff",
        "padded/armor_padded_s01_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_4 = 
    {
        "ris/armor_ris_bicep_l.iff",
        "ris/armor_ris_bracer_l.iff",
        "ris/armor_ris_gloves.iff",
        "ris/armor_ris_bicep_r.iff",
        "ris/armor_ris_bracer_r.iff",
        "ris/armor_ris_helmet.iff",
        "ris/armor_ris_boots.iff",
        "ris/armor_ris_chest_plate.iff",
        "ris/armor_ris_leggings.iff"
    };
    public static final String[] ARMOR_SET_RECON_1 = 
    {
        "zam/armor_zam_wesell_helmet.iff",
        "zam/armor_zam_wesell_boots.iff",
        "zam/armor_zam_wesell_chest_plate.iff",
        "zam/armor_zam_wesell_pants.iff",
        "zam/armor_zam_wesell_gloves.iff"
    };
    public static final String[] ARMOR_SET_RECON_2 = 
    {
        "marauder/armor_marauder_s02_bicep_l.iff",
        "marauder/armor_marauder_s02_bicep_r.iff",
        "marauder/armor_marauder_s02_boots.iff",
        "marauder/armor_marauder_s02_bracer_l.iff",
        "marauder/armor_marauder_s02_bracer_r.iff",
        "marauder/armor_marauder_s02_chest_plate.iff",
        "marauder/armor_marauder_s02_gloves.iff",
        "marauder/armor_marauder_s02_helmet.iff",
        "marauder/armor_marauder_s02_leggings.iff"
    };
    public static final String[] ARMOR_SET_RECON_3 = 
    {
        "tantel/armor_tantel_skreej_bicep_l.iff",
        "tantel/armor_tantel_skreej_chest_plate.iff",
        "tantel/armor_tantel_skreej_bicep_r.iff",
        "tantel/armor_tantel_skreej_boots.iff",
        "tantel/armor_tantel_skreej_gloves.iff",
        "tantel/armor_tantel_skreej_helmet.iff",
        "tantel/armor_tantel_skreej_bracer_l.iff",
        "tantel/armor_tantel_skreej_bracer_r.iff",
        "tantel/armor_tantel_skreej_leggings.iff"
    };
    public static final String[] ARMOR_SET_RECON_4 = 
    {
        "ubese/armor_ubese_boots.iff",
        "ubese/armor_ubese_jacket.iff",
        "ubese/armor_ubese_bracer_l.iff",
        "ubese/armor_ubese_bracer_r.iff",
        "ubese/armor_ubese_pants.iff",
        "ubese/armor_ubese_gloves.iff",
        "ubese/armor_ubese_helmet.iff",
        "ubese/armor_ubese_shirt.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_ITHORIAN = 
    {
        "ithorian_sentinel/ith_armor_s03_bicep_l.iff",
        "ithorian_sentinel/ith_armor_s03_chest_plate.iff",
        "ithorian_sentinel/ith_armor_s03_bicep_r.iff",
        "ithorian_sentinel/ith_armor_s03_gloves.iff",
        "ithorian_sentinel/ith_armor_s03_boots.iff",
        "ithorian_sentinel/ith_armor_s03_helmet.iff",
        "ithorian_sentinel/ith_armor_s03_bracer_l.iff",
        "ithorian_sentinel/ith_armor_s03_leggings.iff",
        "ithorian_sentinel/ith_armor_s03_bracer_r.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_ITHORIAN = 
    {
        "ithorian_defender/ith_armor_s01_bicep_l.iff",
        "ithorian_defender/ith_armor_s01_chest_plate.iff",
        "ithorian_defender/ith_armor_s01_bicep_r.iff",
        "ithorian_defender/ith_armor_s01_gloves.iff",
        "ithorian_defender/ith_armor_s01_boots.iff",
        "ithorian_defender/ith_armor_s01_helmet.iff",
        "ithorian_defender/ith_armor_s01_bracer_l.iff",
        "ithorian_defender/ith_armor_s01_leggings.iff",
        "ithorian_defender/ith_armor_s01_bracer_r.iff"
    };
    public static final String[] ARMOR_SET_RECON_ITHORIAN = 
    {
        "ithorian_guardian/ith_armor_s02_bicep_l.iff",
        "ithorian_guardian/ith_armor_s02_chest_plate.iff",
        "ithorian_guardian/ith_armor_s02_bicep_r.iff",
        "ithorian_guardian/ith_armor_s02_gloves.iff",
        "ithorian_guardian/ith_armor_s02_boots.iff",
        "ithorian_guardian/ith_armor_s02_helmet.iff",
        "ithorian_guardian/ith_armor_s02_bracer_l.iff",
        "ithorian_guardian/ith_armor_s02_leggings.iff",
        "ithorian_guardian/ith_armor_s02_bracer_r.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_WOOKIEE = 
    {
        "kashyyykian_hunting/armor_kashyyykian_hunting_bracer_l.iff",
        "kashyyykian_hunting/armor_kashyyykian_hunting_bracer_r.iff",
        "kashyyykian_hunting/armor_kashyyykian_hunting_chest_plate.iff",
        "kashyyykian_hunting/armor_kashyyykian_hunting_leggings.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_WOOKIEE = 
    {
        "kashyyykian_black_mtn/armor_kashyyykian_black_mtn_bracer_l.iff",
        "kashyyykian_black_mtn/armor_kashyyykian_black_mtn_bracer_r.iff",
        "kashyyykian_black_mtn/armor_kashyyykian_black_mtn_chest_plate.iff",
        "kashyyykian_black_mtn/armor_kashyyykian_black_mtn_leggings.iff"
    };
    public static final String[] ARMOR_SET_RECON_WOOKIEE = 
    {
        "kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bracer_l.iff",
        "kashyyykian_ceremonial/armor_kashyyykian_ceremonial_bracer_r.iff",
        "kashyyykian_ceremonial/armor_kashyyykian_ceremonial_chest_plate.iff",
        "kashyyykian_ceremonial/armor_kashyyykian_ceremonial_leggings.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_IMPERIAL = 
    {
        "assault_trooper/armor_assault_trooper_chest_plate.iff",
        "assault_trooper/armor_assault_trooper_leggings.iff",
        "assault_trooper/armor_assault_trooper_helmet.iff",
        "assault_trooper/armor_assault_trooper_bicep_l.iff",
        "assault_trooper/armor_assault_trooper_bicep_r.iff",
        "assault_trooper/armor_assault_trooper_bracer_l.iff",
        "assault_trooper/armor_assault_trooper_bracer_r.iff",
        "assault_trooper/armor_assault_trooper_boots.iff",
        "assault_trooper/armor_assault_trooper_gloves.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_IMPERIAL = 
    {
        "stormtrooper/armor_stormtrooper_chest_plate.iff",
        "stormtrooper/armor_stormtrooper_leggings.iff",
        "stormtrooper/armor_stormtrooper_helmet.iff",
        "stormtrooper/armor_stormtrooper_bicep_l.iff",
        "stormtrooper/armor_stormtrooper_bicep_r.iff",
        "stormtrooper/armor_stormtrooper_bracer_l.iff",
        "stormtrooper/armor_stormtrooper_bracer_r.iff",
        "stormtrooper/armor_stormtrooper_boots.iff",
        "stormtrooper/armor_stormtrooper_gloves.iff"
    };
    public static final String[] ARMOR_SET_RECON_IMPERIAL = 
    {
        "scout_trooper/armor_scout_trooper_chest_plate.iff",
        "scout_trooper/armor_scout_trooper_leggings.iff",
        "scout_trooper/armor_scout_trooper_helmet.iff",
        "scout_trooper/armor_scout_trooper_bicep_l.iff",
        "scout_trooper/armor_scout_trooper_bicep_r.iff",
        "scout_trooper/armor_scout_trooper_bracer_l.iff",
        "scout_trooper/armor_scout_trooper_bracer_r.iff",
        "scout_trooper/armor_scout_trooper_boots.iff",
        "scout_trooper/armor_scout_trooper_gloves.iff"
    };
    public static final String[] ARMOR_SET_ASSAULT_REBEL = 
    {
        "rebel_assault/armor_rebel_assault_chest_plate.iff",
        "rebel_assault/armor_rebel_assault_leggings.iff",
        "rebel_assault/armor_rebel_assault_helmet.iff",
        "rebel_assault/armor_rebel_assault_bicep_l.iff",
        "rebel_assault/armor_rebel_assault_bicep_r.iff",
        "rebel_assault/armor_rebel_assault_bracer_l.iff",
        "rebel_assault/armor_rebel_assault_bracer_r.iff",
        "rebel_assault/armor_rebel_assault_boots.iff",
        "rebel_assault/armor_rebel_assault_gloves.iff"
    };
    public static final String[] ARMOR_SET_BATTLE_REBEL = 
    {
        "rebel_battle/armor_rebel_battle_chest_plate.iff",
        "rebel_battle/armor_rebel_battle_leggings.iff",
        "rebel_battle/armor_rebel_battle_helmet.iff",
        "rebel_battle/armor_rebel_battle_bicep_l.iff",
        "rebel_battle/armor_rebel_battle_bicep_r.iff",
        "rebel_battle/armor_rebel_battle_bracer_l.iff",
        "rebel_battle/armor_rebel_battle_bracer_r.iff",
        "rebel_battle/armor_rebel_battle_boots.iff",
        "rebel_battle/armor_rebel_battle_gloves.iff"
    };
    public static final String[] ARMOR_SET_RECON_REBEL = 
    {
        "marine/armor_marine_chest_plate_rebel.iff",
        "marine/armor_marine_leggings.iff",
        "marine/armor_marine_helmet.iff",
        "marine/armor_marine_bicep_l.iff",
        "marine/armor_marine_bicep_r.iff",
        "marine/armor_marine_bracer_l.iff",
        "marine/armor_marine_bracer_r.iff",
        "marine/armor_marine_boots.iff",
        "marine/armor_marine_gloves.iff"
    };
    public static final String[][] ARMOR_SETS_ASSAULT = 
    {
        ARMOR_SET_ASSAULT_1,
        ARMOR_SET_ASSAULT_2,
        ARMOR_SET_ASSAULT_3
    };
    public static final String[][] ARMOR_SETS_BATTLE = 
    {
        ARMOR_SET_BATTLE_1,
        ARMOR_SET_BATTLE_2,
        ARMOR_SET_BATTLE_3,
        ARMOR_SET_BATTLE_4
    };
    public static final String[][] ARMOR_SETS_RECON = 
    {
        ARMOR_SET_RECON_1,
        ARMOR_SET_RECON_2,
        ARMOR_SET_RECON_3,
        ARMOR_SET_RECON_4
    };
    public static final String[] CYBERNETIC_ITEMS = 
    {
        "object/tangible/wearables/cybernetic/s02/cybernetic_s02_arm_l.iff",
        "object/tangible/wearables/cybernetic/s02/cybernetic_s02_arm_r.iff",
        "object/tangible/wearables/cybernetic/s02/cybernetic_s02_legs.iff",
        "object/tangible/wearables/cybernetic/s03/cybernetic_s03_arm_l.iff",
        "object/tangible/wearables/cybernetic/s03/cybernetic_s03_arm_r.iff",
        "object/tangible/wearables/cybernetic/s05/cybernetic_s05_arm_l.iff",
        "object/tangible/wearables/cybernetic/s05/cybernetic_s05_arm_r.iff",
        "object/tangible/wearables/cybernetic/s05/cybernetic_s05_legs.iff"
    };
    public static final String[] BRAWLER = 
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
    };
    public static final String[] MARKSMAN = 
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
    };
    public static final String[] PISTOL = 
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
    };
    public static final String[] COMMANDO = 
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
    };
    public static final String[] SCOUT = 
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
    };
    public static final String[] BOUNTY_HUNTER = 
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
    };
    public static final String[] CARBINEER = 
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
    };
    public static final String[] TKA = 
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
    };
    public static final String[] PIKEMAN = 
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
    };
    public static final String[] MEDIC = 
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
    };
    public static final String[] DOCTOR = 
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
    };
    public static final String[] REBEL_PILOT = 
    {
        "pilot_rebel_navy_novice",
        "pilot_rebel_navy_starships_01",
        "pilot_rebel_navy_starships_02",
        "pilot_rebel_navy_starships_03",
        "pilot_rebel_navy_starships_04",
        "pilot_rebel_navy_weapons_01",
        "pilot_rebel_navy_weapons_02",
        "pilot_rebel_navy_weapons_03",
        "pilot_rebel_navy_weapons_04",
        "pilot_rebel_navy_procedures_01",
        "pilot_rebel_navy_procedures_02",
        "pilot_rebel_navy_procedures_03",
        "pilot_rebel_navy_procedures_04",
        "pilot_rebel_navy_droid_01",
        "pilot_rebel_navy_droid_02",
        "pilot_rebel_navy_droid_03",
        "pilot_rebel_navy_droid_04",
        "pilot_rebel_navy_master"
    };
    public static final String[] IMPERIAL_PILOT = 
    {
        "pilot_imperial_navy_novice",
        "pilot_imperial_navy_starships_01",
        "pilot_imperial_navy_starships_02",
        "pilot_imperial_navy_starships_03",
        "pilot_imperial_navy_starships_04",
        "pilot_imperial_navy_weapons_01",
        "pilot_imperial_navy_weapons_02",
        "pilot_imperial_navy_weapons_03",
        "pilot_imperial_navy_weapons_04",
        "pilot_imperial_navy_procedures_01",
        "pilot_imperial_navy_procedures_02",
        "pilot_imperial_navy_procedures_03",
        "pilot_imperial_navy_procedures_04",
        "pilot_imperial_navy_droid_01",
        "pilot_imperial_navy_droid_02",
        "pilot_imperial_navy_droid_03",
        "pilot_imperial_navy_droid_04",
        "pilot_imperial_navy_master"
    };
    public static final String[] NEUTRAL_PILOT = 
    {
        "pilot_neutral_novice",
        "pilot_neutral_starships_01",
        "pilot_neutral_starships_02",
        "pilot_neutral_starships_03",
        "pilot_neutral_starships_04",
        "pilot_neutral_weapons_01",
        "pilot_neutral_weapons_02",
        "pilot_neutral_weapons_03",
        "pilot_neutral_weapons_04",
        "pilot_neutral_procedures_01",
        "pilot_neutral_procedures_02",
        "pilot_neutral_procedures_03",
        "pilot_neutral_procedures_04",
        "pilot_neutral_droid_01",
        "pilot_neutral_droid_02",
        "pilot_neutral_droid_03",
        "pilot_neutral_droid_04",
        "pilot_neutral_master"
    };
    public static final String[] allTemplates = 
    {
        "bactainfusion",
        "bactajab",
        "bactashot",
        "bactaspray",
        "bactatoss",
        "deuteriumtoss",
        "disinfect",
        "endorphineinjection",
        "neurotoxin",
        "nutrientinjection",
        "stabilizer",
        "thyroidrupture",
        "traumatize"
    };
    public boolean checkConfigSetting(String configString) throws InterruptedException
    {
        String enabled = toLower(getConfigSetting("CharacterBuilder", configString));
        if (enabled == null)
        {
            return false;
        }
        if (enabled.equals("true") || enabled.equals("1"))
        {
            return true;
        }
        return false;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (data != null)
        {
            data.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            boolean enabled = features.hasEpisode3Expansion(player);
            if (enabled == true)
            {
                startCharacterBuilder(player);
            }
            else 
            {
                sendSystemMessageTestingOnly(player, "You must be enabled for Rage Of The Wookiees to use this");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void startCharacterBuilder(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired character option";
        String title = "Character Builder Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, CHARACTER_BUILDER_OPTIONS, "handleOptionSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleOptionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > CHARACTER_BUILDER_OPTIONS.length)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        switch (idx)
        {
            case 0:
            handleKachirhoOption(player);
            break;
            case 1:
            handleDeadForestOption(player);
            break;
            case 2:
            handleMyyydrilOption(player);
            break;
            case 3:
            handleHuntingGroundsOption(player);
            break;
            case 4:
            if (isGod(player))
            {
                handleBocctyyyOption(player);
            }
            else 
            {
                sendSystemMessage(player, SID_TERMINAL_DISABLED);
            }
            break;
            case 5:
            handleHraccaOption(player);
            break;
            case 6:
            handleRryattOption(player);
            break;
            case 7:
            handleBlackscaleOption(player);
            break;
            case 8:
            handleAvatarOption(player);
            break;
            case 9:
            handleSpaceMiningOption(player);
            break;
            case 10:
            handleSpaceMissionOption(player);
            break;
            case 11:
            handleCyberneticsOption(player);
            break;
            default:
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(player, "character_builder");
        utils.removeScriptVarTree(self, "character_builder");
        setObjVar(player, "character_builder", true);
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        String playerPath = "character_builder.";
        if (utils.hasScriptVar(player, "character_builder.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "character_builder.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "character_builder.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "character_builder.pid", pid);
        }
    }
    public void handleKachirhoOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleKachirhoSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleKachirhoSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                skill.grantSkill(player, BRAWLER[0]);
                skill.grantSkill(player, BRAWLER[1]);
                skill.grantSkill(player, BRAWLER[2]);
                skill.grantSkill(player, BRAWLER[3]);
                skill.grantSkill(player, BRAWLER[4]);
                skill.grantSkill(player, BRAWLER[13]);
                skill.grantSkill(player, BRAWLER[14]);
                skill.grantSkill(player, BRAWLER[15]);
                skill.grantSkill(player, BRAWLER[16]);
                skill.grantSkill(player, PIKEMAN[0]);
                skill.grantSkill(player, PIKEMAN[1]);
                skill.grantSkill(player, PIKEMAN[2]);
                skill.grantSkill(player, PIKEMAN[5]);
                skill.grantSkill(player, PIKEMAN[6]);
                skill.grantSkill(player, PIKEMAN[9]);
                skill.grantSkill(player, PIKEMAN[10]);
                skill.grantSkill(player, PIKEMAN[13]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_kaminoan.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                warpPlayer(player, "kashyyyk_main", -568, 0, -100, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                skill.grantSkill(player, MARKSMAN[0]);
                skill.grantSkill(player, MARKSMAN[9]);
                skill.grantSkill(player, MARKSMAN[10]);
                skill.grantSkill(player, MARKSMAN[11]);
                skill.grantSkill(player, MARKSMAN[12]);
                skill.grantSkill(player, MARKSMAN[13]);
                skill.grantSkill(player, MARKSMAN[14]);
                skill.grantSkill(player, MARKSMAN[15]);
                skill.grantSkill(player, MARKSMAN[16]);
                skill.grantSkill(player, CARBINEER[0]);
                skill.grantSkill(player, CARBINEER[1]);
                skill.grantSkill(player, CARBINEER[2]);
                skill.grantSkill(player, CARBINEER[5]);
                skill.grantSkill(player, CARBINEER[6]);
                skill.grantSkill(player, CARBINEER[9]);
                skill.grantSkill(player, CARBINEER[10]);
                skill.grantSkill(player, CARBINEER[13]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_laser.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                warpPlayer(player, "kashyyyk_main", -568, 0, -100, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                skill.grantSkill(player, MEDIC[0]);
                skill.grantSkill(player, MEDIC[1]);
                skill.grantSkill(player, MEDIC[2]);
                skill.grantSkill(player, MEDIC[3]);
                skill.grantSkill(player, MEDIC[4]);
                skill.grantSkill(player, MEDIC[9]);
                skill.grantSkill(player, MEDIC[10]);
                skill.grantSkill(player, MEDIC[11]);
                skill.grantSkill(player, MEDIC[12]);
                skill.grantSkill(player, DOCTOR[0]);
                skill.grantSkill(player, DOCTOR[1]);
                skill.grantSkill(player, DOCTOR[2]);
                skill.grantSkill(player, DOCTOR[5]);
                skill.grantSkill(player, DOCTOR[6]);
                skill.grantSkill(player, DOCTOR[9]);
                skill.grantSkill(player, DOCTOR[10]);
                skill.grantSkill(player, DOCTOR[13]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                    flagForDestruction(stimd);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        flagForDestruction(enh);
                        if (isIdValid(enh)) {
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                warpPlayer(player, "kashyyyk_main", -568, 0, -100, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleDeadForestOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleDeadForestSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleDeadForestSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s2 : BRAWLER) {
                        skill.grantSkill(player, s2);
                    }
                skill.grantSkill(player, PIKEMAN[0]);
                skill.grantSkill(player, PIKEMAN[1]);
                skill.grantSkill(player, PIKEMAN[2]);
                skill.grantSkill(player, PIKEMAN[3]);
                skill.grantSkill(player, PIKEMAN[5]);
                skill.grantSkill(player, PIKEMAN[6]);
                skill.grantSkill(player, PIKEMAN[7]);
                skill.grantSkill(player, PIKEMAN[9]);
                skill.grantSkill(player, PIKEMAN[10]);
                skill.grantSkill(player, PIKEMAN[13]);
                skill.grantSkill(player, PIKEMAN[14]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_shock.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                warpPlayer(player, "kashyyyk_dead_forest", 84, 26, -460, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s1 : MARKSMAN) {
                        skill.grantSkill(player, s1);
                    }
                skill.grantSkill(player, CARBINEER[0]);
                skill.grantSkill(player, CARBINEER[1]);
                skill.grantSkill(player, CARBINEER[2]);
                skill.grantSkill(player, CARBINEER[3]);
                skill.grantSkill(player, CARBINEER[5]);
                skill.grantSkill(player, CARBINEER[6]);
                skill.grantSkill(player, CARBINEER[7]);
                skill.grantSkill(player, CARBINEER[9]);
                skill.grantSkill(player, CARBINEER[10]);
                skill.grantSkill(player, CARBINEER[13]);
                skill.grantSkill(player, CARBINEER[14]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_geo.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                warpPlayer(player, "kashyyyk_dead_forest", 84, 26, -460, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s : MEDIC) {
                        skill.grantSkill(player, s);
                    }
                skill.grantSkill(player, DOCTOR[0]);
                skill.grantSkill(player, DOCTOR[1]);
                skill.grantSkill(player, DOCTOR[2]);
                skill.grantSkill(player, DOCTOR[3]);
                skill.grantSkill(player, DOCTOR[5]);
                skill.grantSkill(player, DOCTOR[6]);
                skill.grantSkill(player, DOCTOR[7]);
                skill.grantSkill(player, DOCTOR[9]);
                skill.grantSkill(player, DOCTOR[10]);
                skill.grantSkill(player, DOCTOR[13]);
                skill.grantSkill(player, DOCTOR[14]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                    flagForDestruction(stimd);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            setCount(enh, 150);
                            flagForDestruction(enh);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                warpPlayer(player, "kashyyyk_dead_forest", 84, 26, -460, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleMyyydrilOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleMyyydrilSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleMyyydrilSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s7 : BRAWLER) {
                        skill.grantSkill(player, s7);
                    }
                    for (String s6 : PIKEMAN) {
                        skill.grantSkill(player, s6);
                    }
                    for (String s5 : TKA) {
                        skill.grantSkill(player, s5);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                warpPlayer(player, "kashyyyk_dead_forest", -242, 38, 302, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s4 : MARKSMAN) {
                        skill.grantSkill(player, s4);
                    }
                    for (String s3 : CARBINEER) {
                        skill.grantSkill(player, s3);
                    }
                    for (String s2 : PISTOL) {
                        skill.grantSkill(player, s2);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_geo.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                warpPlayer(player, "kashyyyk_dead_forest", -242, 38, 302, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s1 : MEDIC) {
                        skill.grantSkill(player, s1);
                    }
                    for (String s : DOCTOR) {
                        skill.grantSkill(player, s);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                warpPlayer(player, "kashyyyk_dead_forest", -242, 38, 302, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleHuntingGroundsOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleHuntingGroundsSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleHuntingGroundsSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s2 : BRAWLER) {
                        skill.grantSkill(player, s2);
                    }
                skill.grantSkill(player, PIKEMAN[0]);
                skill.grantSkill(player, PIKEMAN[1]);
                skill.grantSkill(player, PIKEMAN[2]);
                skill.grantSkill(player, PIKEMAN[3]);
                skill.grantSkill(player, PIKEMAN[4]);
                skill.grantSkill(player, PIKEMAN[5]);
                skill.grantSkill(player, PIKEMAN[6]);
                skill.grantSkill(player, PIKEMAN[7]);
                skill.grantSkill(player, PIKEMAN[8]);
                skill.grantSkill(player, PIKEMAN[9]);
                skill.grantSkill(player, PIKEMAN[10]);
                skill.grantSkill(player, PIKEMAN[11]);
                skill.grantSkill(player, PIKEMAN[13]);
                skill.grantSkill(player, PIKEMAN[14]);
                skill.grantSkill(player, PIKEMAN[15]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_shock.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                warpPlayer(player, "kashyyyk_hunting", 658, 8, 666, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s1 : MARKSMAN) {
                        skill.grantSkill(player, s1);
                    }
                skill.grantSkill(player, CARBINEER[0]);
                skill.grantSkill(player, CARBINEER[1]);
                skill.grantSkill(player, CARBINEER[2]);
                skill.grantSkill(player, CARBINEER[3]);
                skill.grantSkill(player, CARBINEER[4]);
                skill.grantSkill(player, CARBINEER[5]);
                skill.grantSkill(player, CARBINEER[6]);
                skill.grantSkill(player, CARBINEER[7]);
                skill.grantSkill(player, CARBINEER[8]);
                skill.grantSkill(player, CARBINEER[9]);
                skill.grantSkill(player, CARBINEER[10]);
                skill.grantSkill(player, CARBINEER[11]);
                skill.grantSkill(player, CARBINEER[13]);
                skill.grantSkill(player, CARBINEER[14]);
                skill.grantSkill(player, CARBINEER[15]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_geo.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                warpPlayer(player, "kashyyyk_hunting", 658, 8, 666, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s : MEDIC) {
                        skill.grantSkill(player, s);
                    }
                skill.grantSkill(player, DOCTOR[0]);
                skill.grantSkill(player, DOCTOR[1]);
                skill.grantSkill(player, DOCTOR[2]);
                skill.grantSkill(player, DOCTOR[3]);
                skill.grantSkill(player, DOCTOR[4]);
                skill.grantSkill(player, DOCTOR[5]);
                skill.grantSkill(player, DOCTOR[6]);
                skill.grantSkill(player, DOCTOR[7]);
                skill.grantSkill(player, DOCTOR[8]);
                skill.grantSkill(player, DOCTOR[9]);
                skill.grantSkill(player, DOCTOR[10]);
                skill.grantSkill(player, DOCTOR[11]);
                skill.grantSkill(player, DOCTOR[13]);
                skill.grantSkill(player, DOCTOR[14]);
                skill.grantSkill(player, DOCTOR[15]);
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                warpPlayer(player, "kashyyyk_hunting", 658, 8, 666, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleBocctyyyOption(obj_id player) throws InterruptedException
    {
    }
    public void handleHraccaOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleHraccaSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleHraccaSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s7 : BRAWLER) {
                        skill.grantSkill(player, s7);
                    }
                    for (String s6 : PIKEMAN) {
                        skill.grantSkill(player, s6);
                    }
                    for (String s5 : TKA) {
                        skill.grantSkill(player, s5);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                groundquests.clearQuest(player, "ep3_hunt_johnson_seek_kint");
                groundquests.grantQuest(player, "ep3_hunt_johnson_seek_kint");
                warpPlayer(player, "kashyyyk_hunting", -88, 23, -245, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s4 : MARKSMAN) {
                        skill.grantSkill(player, s4);
                    }
                    for (String s3 : CARBINEER) {
                        skill.grantSkill(player, s3);
                    }
                    for (String s2 : PISTOL) {
                        skill.grantSkill(player, s2);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_elite.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                obj_id weapon3 = weapons.createWeapon("object/weapon/ranged/pistol/pistol_dx2.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                flagForDestruction(weapon3);
                groundquests.clearQuest(player, "ep3_hunt_johnson_seek_kint");
                groundquests.grantQuest(player, "ep3_hunt_johnson_seek_kint");
                warpPlayer(player, "kashyyyk_hunting", -88, 23, -245, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s1 : MEDIC) {
                        skill.grantSkill(player, s1);
                    }
                    for (String s : DOCTOR) {
                        skill.grantSkill(player, s);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                groundquests.clearQuest(player, "ep3_hunt_johnson_seek_kint");
                groundquests.grantQuest(player, "ep3_hunt_johnson_seek_kint");
                warpPlayer(player, "kashyyyk_hunting", -88, 23, -245, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleRryattOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleRryattSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleRryattSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s7 : BRAWLER) {
                        skill.grantSkill(player, s7);
                    }
                    for (String s6 : PIKEMAN) {
                        skill.grantSkill(player, s6);
                    }
                    for (String s5 : TKA) {
                        skill.grantSkill(player, s5);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                warpPlayer(player, "kashyyyk_main", -67, 0, 808, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s4 : MARKSMAN) {
                        skill.grantSkill(player, s4);
                    }
                    for (String s3 : CARBINEER) {
                        skill.grantSkill(player, s3);
                    }
                    for (String s2 : PISTOL) {
                        skill.grantSkill(player, s2);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_elite.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                obj_id weapon3 = weapons.createWeapon("object/weapon/ranged/pistol/pistol_dx2.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                flagForDestruction(weapon3);
                warpPlayer(player, "kashyyyk_main", -67, 0, 808, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeInventory(player);
                    for (String s1 : MEDIC) {
                        skill.grantSkill(player, s1);
                    }
                    for (String s : DOCTOR) {
                        skill.grantSkill(player, s);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                warpPlayer(player, "kashyyyk_main", -67, 0, 808, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleBlackscaleOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleBlackscaleSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleBlackscaleSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s7 : BRAWLER) {
                        skill.grantSkill(player, s7);
                    }
                    for (String s6 : PIKEMAN) {
                        skill.grantSkill(player, s6);
                    }
                    for (String s5 : TKA) {
                        skill.grantSkill(player, s5);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                groundquests.clearQuest(player, "ep3_slaver_gursan_entry_quest");
                groundquests.grantQuest(player, "ep3_slaver_gursan_entry_quest");
                warpPlayer(player, "kashyyyk_main", 404, 18, 656, null, 0, 0, 0, "", false);
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s4 : MARKSMAN) {
                        skill.grantSkill(player, s4);
                    }
                    for (String s3 : CARBINEER) {
                        skill.grantSkill(player, s3);
                    }
                    for (String s2 : PISTOL) {
                        skill.grantSkill(player, s2);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_elite.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                obj_id weapon3 = weapons.createWeapon("object/weapon/ranged/pistol/pistol_dx2.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                flagForDestruction(weapon3);
                groundquests.clearQuest(player, "ep3_slaver_gursan_entry_quest");
                groundquests.grantQuest(player, "ep3_slaver_gursan_entry_quest");
                warpPlayer(player, "kashyyyk_main", 404, 18, 656, null, 0, 0, 0, "", false);
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s1 : MEDIC) {
                        skill.grantSkill(player, s1);
                    }
                    for (String s : DOCTOR) {
                        skill.grantSkill(player, s);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                groundquests.clearQuest(player, "ep3_slaver_gursan_entry_quest");
                groundquests.grantQuest(player, "ep3_slaver_gursan_entry_quest");
                warpPlayer(player, "kashyyyk_main", 404, 18, 656, null, 0, 0, 0, "", false);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleAvatarOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, KACHIRHO_OPTIONS, "handleAvatarSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleAvatarSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int species = getSpecies(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            switch (idx)
            {
                case 0:
                handleRevokeAllSkills(player);
                nukeSelectedInventory(player);
                    for (String s7 : BRAWLER) {
                        skill.grantSkill(player, s7);
                    }
                    for (String s6 : PIKEMAN) {
                        skill.grantSkill(player, s6);
                    }
                    for (String s5 : TKA) {
                        skill.grantSkill(player, s5);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_1);
                }
                obj_id weapon1 = weapons.createWeapon("object/weapon/melee/polearm/lance_massassi.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon1);
                groundquests.clearQuest(player, "ep3_trando_hssissk_zssik_10");
                groundquests.grantQuest(player, "ep3_trando_hssissk_zssik_10");
                obj_id newship = space_utils.createShipControlDevice(player, "xwing", true);
                if (isIdValid(newship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(newship);
                    location space = new location(2600, 360, -2650, "space_kashyyyk");
                    location ground = getLocation(player);
                    launch(player, ship, null, space, ground);
                }
                break;
                case 1:
                handleRevokeAllSkills(player);
                nukeInventory(player);
                    for (String s4 : MARKSMAN) {
                        skill.grantSkill(player, s4);
                    }
                    for (String s3 : CARBINEER) {
                        skill.grantSkill(player, s3);
                    }
                    for (String s2 : PISTOL) {
                        skill.grantSkill(player, s2);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_3);
                }
                obj_id weapon2 = weapons.createWeapon("object/weapon/ranged/carbine/carbine_elite.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                obj_id weapon3 = weapons.createWeapon("object/weapon/ranged/pistol/pistol_dx2.iff", pInv, weapons.VIA_TEMPLATE, CRAFTED_QUALITY);
                flagForDestruction(weapon2);
                flagForDestruction(weapon3);
                groundquests.clearQuest(player, "ep3_trando_hssissk_zssik_10");
                groundquests.grantQuest(player, "ep3_trando_hssissk_zssik_10");
                obj_id newship2 = space_utils.createShipControlDevice(player, "xwing", true);
                if (isIdValid(newship2))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(newship2);
                    location space = new location(2600, 360, -2650, "space_kashyyyk");
                    location ground = getLocation(player);
                    launch(player, ship, null, space, ground);
                }
                break;
                case 2:
                handleRevokeAllSkills(player);
                nukeInventory(player);
                    for (String s1 : MEDIC) {
                        skill.grantSkill(player, s1);
                    }
                    for (String s : DOCTOR) {
                        skill.grantSkill(player, s);
                    }
                if (species == 4)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_WOOKIEE);
                }
                else if (species == 33)
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_ITHORIAN);
                }
                else 
                {
                    issueBattleArmorSet(player, ARMOR_SET_BATTLE_2);
                }
                obj_id stimd = createObject("object/tangible/medicine/instant_stimpack/stimpack_d.iff", pInv, "");
                if (isIdValid(stimd))
                {
                    flagForDestruction(stimd);
                    setCount(stimd, 350);
                    setObjVar(stimd, "healing.power", 1200);
                }
                    for (String allTemplate : allTemplates) {
                        obj_id enh = createObject("object/tangible/medicine/enhancer/enhancer_" + allTemplate + ".iff", pInv, "");
                        if (isIdValid(enh)) {
                            flagForDestruction(enh);
                            setCount(enh, 150);
                            setObjVar(enh, "healing.enhancement", 700);
                        }
                    }
                groundquests.clearQuest(player, "ep3_trando_hssissk_zssik_10");
                groundquests.grantQuest(player, "ep3_trando_hssissk_zssik_10");
                obj_id newship3 = space_utils.createShipControlDevice(player, "xwing", true);
                if (isIdValid(newship3))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(newship3);
                    location space = new location(2600, 360, -2650, "space_kashyyyk");
                    location ground = getLocation(player);
                    launch(player, ship, null, space, ground);
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleSpaceMiningOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, SPACE_MINING_OPTIONS, "handleSpaceMiningSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleSpaceMiningSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id objInventory = utils.getInventoryContainer(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            handleRevokeAllSkills(player);
            obj_id weapon1 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mining_laser_mk2.iff", objInventory);
            obj_id weapon2 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_tractor_pulse_gun.iff", objInventory);
            obj_id cargoHold = createObjectOverloaded("object/tangible/ship/components/cargo_hold/crg_starfighter_large.iff", objInventory);
            switch (idx)
            {
                case 0:
                    for (String s2 : REBEL_PILOT) {
                        skill.grantSkill(player, s2);
                    }
                obj_id clothing = createObjectOverloaded("object/tangible/wearables/bodysuit/rebel_bodysuit_s14.iff", objInventory);
                obj_id rebboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id newship = space_utils.createShipControlDevice(player, "xwing", true);
                if (isIdValid(newship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(newship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_0))
                    {
                        obj_id comp1 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, objInventory);
                        destroyObject(comp1);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon1);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_1))
                    {
                        obj_id comp2 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, objInventory);
                        destroyObject(comp2);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon2);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_cargo_hold, cargoHold);
                }
                break;
                case 1:
                    for (String s1 : IMPERIAL_PILOT) {
                        skill.grantSkill(player, s1);
                    }
                obj_id impclothing = createObjectOverloaded("object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff", objInventory);
                obj_id impboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id impship = space_utils.createShipControlDevice(player, "tieadvanced", true);
                if (isIdValid(impship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(impship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_0))
                    {
                        obj_id comp1 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, objInventory);
                        destroyObject(comp1);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon1);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_1))
                    {
                        obj_id comp2 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, objInventory);
                        destroyObject(comp2);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon2);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_cargo_hold, cargoHold);
                }
                break;
                case 2:
                    for (String s : NEUTRAL_PILOT) {
                        skill.grantSkill(player, s);
                    }
                obj_id neuclothing = createObjectOverloaded("object/tangible/wearables/bodysuit/bodysuit_bwing.iff", objInventory);
                obj_id neuboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id neuship = space_utils.createShipControlDevice(player, "hutt_medium_s01", true);
                if (isIdValid(neuship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(neuship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_0))
                    {
                        obj_id comp1 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, objInventory);
                        destroyObject(comp1);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon1);
                    if (isShipSlotInstalled(ship, ship_chassis_slot_type.SCST_weapon_1))
                    {
                        obj_id comp2 = shipUninstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, objInventory);
                        destroyObject(comp2);
                    }
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon2);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_cargo_hold, cargoHold);
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void handleSpaceMissionOption(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        String prompt = "Select the desired testing option";
        String title = "Kashyyyk Content Terminal";
        closeOldWindow(player);
        int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, SPACE_MINING_OPTIONS, "handleSpaceMissionSelect", true, false);
        setWindowPid(player, pid);
    }
    public int handleSpaceMissionSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        obj_id objInventory = utils.getInventoryContainer(player);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(player))
        {
            handleRevokeAllSkills(player);
            switch (idx)
            {
                case 0:
                    for (String s2 : REBEL_PILOT) {
                        skill.grantSkill(player, s2);
                    }
                obj_id clothing = createObjectOverloaded("object/tangible/wearables/bodysuit/rebel_bodysuit_s14.iff", objInventory);
                obj_id rebboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id newship = space_utils.createShipControlDevice(player, "xwing", true);
                if (isIdValid(newship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(newship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    space_crafting.uninstallAll(ship);
                    obj_id armor1 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    obj_id armor2 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_0, armor1);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_1, armor2);
                    obj_id capacitor = createObjectOverloaded("object/tangible/ship/components/weapon_capacitor/cap_qualdex_conservator_qx2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_capacitor, capacitor);
                    obj_id engine = createObjectOverloaded("object/tangible/ship/components/engine/eng_novaldex_x6.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_engine, engine);
                    obj_id reactor = createObjectOverloaded("object/tangible/ship/components/reactor/rct_rss_x8.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_reactor, reactor);
                    obj_id shield = createObjectOverloaded("object/tangible/ship/components/shield_generator/shd_cygnus_mk3.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_shield_0, shield);
                    obj_id weapon = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon);
                    obj_id weapon1 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon1);
                }
                break;
                case 1:
                    for (String s1 : IMPERIAL_PILOT) {
                        skill.grantSkill(player, s1);
                    }
                obj_id impclothing = createObjectOverloaded("object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff", objInventory);
                obj_id impboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id impship = space_utils.createShipControlDevice(player, "tieinterceptor", true);
                if (isIdValid(impship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(impship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    space_crafting.uninstallAll(ship);
                    obj_id armor1 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    obj_id armor2 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_0, armor1);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_1, armor2);
                    obj_id capacitor = createObjectOverloaded("object/tangible/ship/components/weapon_capacitor/cap_qualdex_conservator_qx2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_capacitor, capacitor);
                    obj_id engine = createObjectOverloaded("object/tangible/ship/components/engine/eng_novaldex_x6.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_engine, engine);
                    obj_id reactor = createObjectOverloaded("object/tangible/ship/components/reactor/rct_rss_x8.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_reactor, reactor);
                    obj_id shield = createObjectOverloaded("object/tangible/ship/components/shield_generator/shd_cygnus_mk3.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_shield_0, shield);
                    obj_id weapon = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon);
                }
                break;
                case 2:
                    for (String s : NEUTRAL_PILOT) {
                        skill.grantSkill(player, s);
                    }
                obj_id neuclothing = createObjectOverloaded("object/tangible/wearables/bodysuit/bodysuit_bwing.iff", objInventory);
                obj_id neuboots = createObjectOverloaded("object/tangible/wearables/boots/boots_s03.iff", objInventory);
                obj_id neuship = space_utils.createShipControlDevice(player, "blacksun_medium_s03", true);
                if (isIdValid(neuship))
                {
                    obj_id ship = space_transition.getShipFromShipControlDevice(neuship);
                    setChassisComponentMassMaximum(ship, 110000.0f);
                    space_crafting.uninstallAll(ship);
                    obj_id armor1 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    obj_id armor2 = createObjectOverloaded("object/tangible/ship/components/armor/armor_subpro_corrugated_durasteel.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_0, armor1);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_armor_1, armor2);
                    obj_id capacitor = createObjectOverloaded("object/tangible/ship/components/weapon_capacitor/cap_qualdex_conservator_qx2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_capacitor, capacitor);
                    obj_id engine = createObjectOverloaded("object/tangible/ship/components/engine/eng_novaldex_x6.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_engine, engine);
                    obj_id reactor = createObjectOverloaded("object/tangible/ship/components/reactor/rct_rss_x8.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_reactor, reactor);
                    obj_id shield = createObjectOverloaded("object/tangible/ship/components/shield_generator/shd_cygnus_mk3.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_shield_0, shield);
                    obj_id weapon = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_0, weapon);
                    obj_id weapon1 = createObjectOverloaded("object/tangible/ship/components/weapon/wpn_mandal_annihilator_mk2.iff", objInventory);
                    shipInstallComponent(player, ship, ship_chassis_slot_type.SCST_weapon_1, weapon1);
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void nukeInventory(obj_id player) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (isIdValid(content)) {
                    destroyObject(content);
                }
            }
        }
    }
    public void nukeSelectedInventory(obj_id player) throws InterruptedException
    {
        obj_id[] contents = getInventoryAndEquipment(player);
        if (contents != null)
        {
            for (obj_id content : contents) {
                if (isIdValid(content) && hasObjVar(content, "frog.item")) {
                    destroyObject(content);
                }
            }
        }
    }
    public void flagForDestruction(obj_id item) throws InterruptedException
    {
        setObjVar(item, "frog.item", 1);
    }
    public void issueReconArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            flagForDestruction(armorItem);
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 0, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_RECON_LAYER, 1.0f);
                }
            }
        }
    }
    public void issueBattleArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            flagForDestruction(armorItem);
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 1, GENERAL_PROTECTION, CONDITION);
                }
            }
        }
    }
    public void issueAssaultArmorSet(obj_id player, String[] armorPieces) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            flagForDestruction(armorItem);
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 2, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                }
            }
        }
    }
    public void handleRevokeAllSkills(obj_id player) throws InterruptedException
    {
        if (exists(player))
        {
            String[] skillList = getSkillListingForPlayer(player);
            int attempts = skillList.length;
            if ((skillList != null) && (skillList.length != 0))
            {
                while (skillList.length > 0 && attempts > 0)
                {
                    for (String skillName : skillList) {
                        if (skillName.startsWith("pilot_")) {
                            utils.setScriptVar(player, "revokePilotSkill", 1);
                            skill.revokeSkillSilent(player, skillName);
                        }
                        if (!skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("demo_") && !skillName.startsWith("force_title_") && !skillName.startsWith("force_sensitive_") && !skillName.startsWith("combat_melee_basic") && !skillName.startsWith("combat_ranged_weapon_basic")) {
                            skill.revokeSkillSilent(player, skillName);
                        }
                    }
                    skillList = getSkillListingForPlayer(player);
                    --attempts;
                }
            }
        }
    }
    public void launch(obj_id player, obj_id ship, obj_id[] membersApprovedByShipOwner, location warpLocation, location groundLoc) throws InterruptedException
    {
        space_transition.clearOvertStatus(ship);
        Vector groupMembersToWarp = utils.addElement(null, player);
        Vector groupMemberStartIndex = utils.addElement(null, 0);
        utils.setScriptVar(player, "strLaunchPointName", "launching");
        Vector shipStartLocations = space_transition.getShipStartLocations(ship);
        if (shipStartLocations != null && shipStartLocations.size() > 0)
        {
            int startIndex = 0;
            location playerLoc = getLocation(player);
            if (isIdValid(playerLoc.cell))
            {
                obj_id group = getGroupObject(player);
                if (isIdValid(group))
                {
                    obj_id[] groupMembers = getGroupMemberIds(group);
                    for (obj_id groupMember : groupMembers) {
                        if (groupMember != player && exists(groupMember) && getLocation(groupMember).cell == playerLoc.cell && groupMemberApproved(membersApprovedByShipOwner, groupMember)) {
                            startIndex = getNextStartIndex(shipStartLocations, startIndex);
                            if (startIndex <= shipStartLocations.size()) {
                                groupMembersToWarp = utils.addElement(groupMembersToWarp, groupMember);
                                groupMemberStartIndex = utils.addElement(groupMemberStartIndex, startIndex);
                            } else {
                                string_id strSpam = new string_id("space/space_interaction", "no_space_expansion");
                                sendSystemMessage(groupMember, strSpam);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < groupMembersToWarp.size(); ++i)
        {
            space_transition.setLaunchInfo(((obj_id)groupMembersToWarp.get(i)), ship, (Integer) groupMemberStartIndex.get(i), groundLoc);
            warpPlayer(((obj_id)groupMembersToWarp.get(i)), warpLocation.area, warpLocation.x, warpLocation.y, warpLocation.z, null, warpLocation.x, warpLocation.y, warpLocation.z);
        }
    }
    public int getNextStartIndex(Vector shipStartLocations, int lastStartIndex) throws InterruptedException
    {
        int startIndex = lastStartIndex + 1;
        if (startIndex > shipStartLocations.size())
        {
            for (startIndex = 1; startIndex <= shipStartLocations.size(); ++startIndex)
            {
                if (((location)shipStartLocations.get(startIndex - 1)).cell != null)
                {
                    break;
                }
            }
        }
        return startIndex;
    }
    public boolean groupMemberApproved(obj_id[] membersApprovedByShipOwner, obj_id memberToTest) throws InterruptedException
    {
        for (obj_id obj_id : membersApprovedByShipOwner) {
            if (obj_id == memberToTest) {
                return true;
            }
        }
        return false;
    }
    public void handleCyberneticsOption(obj_id player) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return;
        }
        for (String cyberneticItem : CYBERNETIC_ITEMS) {
            createObject(cyberneticItem, inv, "");
        }
        sendSystemMessageTestingOnly(player, "Cybernetics issued. Pay a cybernetic Engineer to install the items");
        sendSystemMessageTestingOnly(player, "Locate the cybernetic engineer on the 2nd floor of a medical center");
    }
}
