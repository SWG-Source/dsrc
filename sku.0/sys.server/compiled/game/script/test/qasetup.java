package script.test;

import script.library.*;
import script.obj_id;

public class qasetup extends script.base_script
{
    public qasetup()
    {
    }
    public static final float CONDITION = 1.0f;
    public static final float GENERAL_PROTECTION = 1.0f;
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
    public static final String[] CLOTHES = 
    {
        "object/tangible/wearables/shirt/shirt_s03.iff",
        "object/tangible/wearables/pants/pants_s05.iff",
        "object/tangible/wearables/gloves/gloves_s14.iff",
        "object/tangible/wearables/boots/boots_s05.iff",
        "object/tangible/wearables/ring/ring_s04.iff",
        "object/tangible/wearables/ring/ring_s04.iff",
        "object/tangible/wearables/bracelet/bracelet_s04_l.iff",
        "object/tangible/wearables/bracelet/bracelet_s04_r.iff",
        "object/tangible/wearables/necklace/necklace_s01.iff"
    };
    public static final String[] POWERUP_ITEMS = 
    {
        "item_reverse_engineering_powerup_armor_02_01",
        "item_reverse_engineering_powerup_clothing_02_01",
        "item_reverse_engineering_powerup_weapon_02_01"
    };
    public static final float WEAPON_SPEED = 1.0f;
    public static final float WEAPON_DAMAGE = 1.0f;
    public static final float WEAPON_ELEMENTAL = 1.0f;
    public static final float WEAPON_EFFECIENCY = 1.0f;
    public static final String[] buffComponentKeys = 
    {
        "kinetic",
        "energy",
        "action_cost_reduction",
        "dodge"
    };
    public static final int[] buffComponentValues = 
    {
        15,
        15,
        1,
        2
    };
    public static final String[] BH_RIFLE = 
    {
        "expertise_bh_stun_1",
        "expertise_bh_amb_cool_1",
        "expertise_bh_amb_cool_2",
        "expertise_bh_surprise_1",
        "expertise_bh_trap_duration_1",
        "expertise_bh_trap_duration_2",
        "expertise_bh_ass_act_1",
        "expertise_bh_ass_act_2",
        "expertise_bh_ass_act_3",
        "expertise_bh_ass_act_4",
        "expertise_bh_ass_dam_1",
        "expertise_bh_ass_dam_2",
        "expertise_bh_ass_dam_3",
        "expertise_bh_return_fire_1",
        "expertise_bh_ass_cool_1",
        "expertise_bh_ass_cool_2",
        "expertise_bh_innate_1",
        "expertise_bh_taunt_1",
        "expertise_bh_fumble_1",
        "expertise_bh_trap_duration_3",
        "expertise_bh_intimidate_1",
        "expertise_bh_dread_strike_1",
        "expertise_bh_precision_1",
        "expertise_bh_precision_2",
        "expertise_bh_precision_3",
        "expertise_bh_precision_4",
        "expertise_bh_rifle_act_1",
        "expertise_bh_rifle_act_2",
        "expertise_bh_rifle_act_3",
        "expertise_bh_rifle_act_4",
        "expertise_bh_rifle_dam_1",
        "expertise_bh_rifle_dam_2",
        "expertise_bh_sniper_1",
        "expertise_bh_cover_1",
        "expertise_bh_armor_mgb_1",
        "expertise_bh_armor_mgb_2",
        "expertise_bh_armor_mgb_3",
        "expertise_bh_armor_sprint_1",
        "expertise_bh_armor_eng_1",
        "expertise_bh_armor_eng_2",
        "expertise_bh_armor_eng_3",
        "expertise_bh_armor_rgb_1",
        "expertise_bh_armor_rgb_2",
        "expertise_bh_armor_rgb_3",
        "expertise_bh_armor_duelist_1"
    };
    public static final String[] BH_CARBINE = 
    {
        "expertise_bh_stun_1",
        "expertise_bh_absorbtion_1",
        "expertise_bh_absorbtion_2",
        "expertise_bh_absorbtion_3",
        "expertise_bh_absorbtion_4",
        "expertise_bh_deflection_1",
        "expertise_bh_deflection_2",
        "expertise_bh_deflection_3",
        "expertise_bh_deflection_4",
        "expertise_bh_shields_1",
        "expertise_bh_surprise_1",
        "expertise_bh_ass_act_1",
        "expertise_bh_ass_act_2",
        "expertise_bh_ass_dam_1",
        "expertise_bh_ass_dam_2",
        "expertise_bh_ass_dam_3",
        "expertise_bh_return_fire_1",
        "expertise_bh_ass_cool_1",
        "expertise_bh_ass_cool_2",
        "expertise_bh_innate_1",
        "expertise_bh_taunt_1",
        "expertise_bh_fumble_1",
        "expertise_bh_constitution_1",
        "expertise_bh_constitution_2",
        "expertise_bh_constitution_3",
        "expertise_bh_constitution_4",
        "expertise_bh_carbine_act_1",
        "expertise_bh_carbine_act_2",
        "expertise_bh_carbine_act_3",
        "expertise_bh_carbine_act_4",
        "expertise_bh_carbine_dam_1",
        "expertise_bh_carbine_dam_2",
        "expertise_bh_carbine_crit_1",
        "expertise_bh_relentless_1",
        "expertise_bh_armor_mgb_1",
        "expertise_bh_armor_mgb_2",
        "expertise_bh_armor_mgb_3",
        "expertise_bh_armor_sprint_1",
        "expertise_bh_armor_eng_1",
        "expertise_bh_armor_eng_2",
        "expertise_bh_armor_eng_3",
        "expertise_bh_armor_rgb_1",
        "expertise_bh_armor_rgb_2",
        "expertise_bh_armor_rgb_3",
        "expertise_bh_armor_duelist_1"
    };
    public static final String[] COMMANDO = 
    {
        "expertise_co_suppressing_fire_1",
        "expertise_co_suppression_efficiency_1",
        "expertise_co_suppression_efficiency_2",
        "expertise_co_suppression_efficiency_3",
        "expertise_co_suppression_efficiency_4",
        "expertise_co_powered_armor_1",
        "expertise_co_powered_armor_2",
        "expertise_co_powered_armor_3",
        "expertise_co_powered_armor_4",
        "expertise_co_pinpoint_shielding_1",
        "expertise_co_pinpoint_shielding_2",
        "expertise_co_pinpoint_shielding_3",
        "expertise_co_pinpoint_shielding_4",
        "expertise_co_deflective_armor_1",
        "expertise_co_deflective_armor_2",
        "expertise_co_deflective_armor_3",
        "expertise_co_deflective_armor_4",
        "expertise_co_riddle_armor_1",
        "expertise_co_armor_cracker_1",
        "expertise_co_armor_cracker_2",
        "expertise_co_armor_cracker_3",
        "expertise_co_armor_cracker_4",
        "expertise_co_killing_grimace_1",
        "expertise_co_stim_armor_1",
        "expertise_co_diagnostic_armor_1",
        "expertise_co_diagnostic_armor_2",
        "expertise_co_enhanced_precision_1",
        "expertise_co_enhanced_precision_2",
        "expertise_co_enhanced_constitution_1",
        "expertise_co_enhanced_constitution_2",
        "expertise_co_enhanced_stamina_1",
        "expertise_co_enhanced_stamina_2",
        "expertise_co_position_secured_1",
        "expertise_co_burst_fire_1",
        "expertise_co_burst_fire_2",
        "expertise_co_on_target_1",
        "expertise_co_on_target_2",
        "expertise_co_on_target_3",
        "expertise_co_on_target_4",
        "expertise_co_base_of_operations_1",
        "expertise_co_shock_tracer_1",
        "expertise_co_voltage_overload_1",
        "expertise_co_voltage_overload_2",
        "expertise_co_muscle_spasm_1",
        "expertise_co_muscle_spasm_2"
    };
    public static final String[] OFFICER_MELEE = 
    {
        "expertise_of_aoe_dam_1",
        "expertise_of_group_buff_duration_1",
        "expertise_of_group_buff_duration_2",
        "expertise_of_group_buff_duration_3",
        "expertise_of_group_buff_duration_4",
        "expertise_of_group_buff_act_1",
        "expertise_of_group_buff_act_2",
        "expertise_of_group_buff_act_3",
        "expertise_of_advanced_tactics_1",
        "expertise_of_focus_fire_1",
        "expertise_of_inspiration_1",
        "expertise_of_scatter_1",
        "expertise_of_charge_1",
        "expertise_of_last_words_1",
        "expertise_of_aoe_act_1",
        "expertise_of_aoe_crit_1",
        "expertise_of_aoe_crit_2",
        "expertise_of_aoe_crit_3",
        "expertise_of_aoe_dam_2",
        "expertise_of_firepower_1",
        "expertise_of_firepower_cool_1",
        "expertise_of_firepower_cool_2",
        "expertise_of_firepower_cool_3",
        "expertise_of_advanced_paint_1",
        "expertise_of_stamina_1",
        "expertise_of_stamina_2",
        "expertise_of_stamina_3",
        "expertise_of_stamina_4",
        "expertise_of_1h_dam_1",
        "expertise_of_1h_dam_2",
        "expertise_of_1h_dam_3",
        "expertise_of_1h_dam_4",
        "expertise_of_1h_crit_1",
        "expertise_of_1h_crit_2",
        "expertise_of_decapitate_1",
        "expertise_of_vortex_1",
        "expertise_of_armor_eng_1",
        "expertise_of_armor_eng_2",
        "expertise_of_stimulator_1",
        "expertise_of_heal_dam_1",
        "expertise_of_heal_dam_2",
        "expertise_of_armor_kin_1",
        "expertise_of_purge_1",
        "expertise_of_heal_cool_1",
        "expertise_of_heal_cool_2"
    };
    public static final String[] OFFICER_RANGED = 
    {
        "expertise_of_aoe_dam_1",
        "expertise_of_group_buff_duration_1",
        "expertise_of_group_buff_duration_2",
        "expertise_of_group_buff_duration_3",
        "expertise_of_group_buff_duration_4",
        "expertise_of_group_buff_act_1",
        "expertise_of_group_buff_act_2",
        "expertise_of_group_buff_act_3",
        "expertise_of_advanced_tactics_1",
        "expertise_of_inspiration_1",
        "expertise_of_charge_1",
        "expertise_of_sup_cool_1",
        "expertise_of_sup_cool_2",
        "expertise_of_sup_cool_3",
        "expertise_of_sup_cool_4",
        "expertise_of_medical_sup_1",
        "expertise_of_tactical_sup_1",
        "expertise_of_reinforcements_1",
        "expertise_of_aoe_crit_1",
        "expertise_of_aoe_crit_2",
        "expertise_of_aoe_crit_3",
        "expertise_of_aoe_dam_2",
        "expertise_of_firepower_1",
        "expertise_of_paint_act_1",
        "expertise_of_paint_act_2",
        "expertise_of_paint_act_3",
        "expertise_of_advanced_paint_1",
        "expertise_of_precision_1",
        "expertise_of_precision_2",
        "expertise_of_precision_3",
        "expertise_of_precision_4",
        "expertise_of_constitution_1",
        "expertise_of_constitution_2",
        "expertise_of_stamina_1",
        "expertise_of_stamina_2",
        "expertise_of_stamina_3",
        "expertise_of_stamina_4",
        "expertise_of_armor_eng_1",
        "expertise_of_armor_eng_2",
        "expertise_of_stimulator_1",
        "expertise_of_heal_dam_1",
        "expertise_of_heal_dam_2",
        "expertise_of_armor_kin_1",
        "expertise_of_armor_kin_2",
        "expertise_of_purge_1"
    };
    public static final String[] JEDI_TANK = 
    {
        "expertise_fs_general_enhanced_strength_1",
        "expertise_fs_general_enhanced_strength_2",
        "expertise_fs_general_enhanced_agility_1",
        "expertise_fs_general_enhanced_agility_2",
        "expertise_fs_general_alacrity_1",
        "expertise_fs_general_alacrity_2",
        "expertise_fs_general_alacrity_3",
        "expertise_fs_general_alacrity_4",
        "expertise_fs_general_force_cloak_1",
        "expertise_fs_general_exacting_strikes_1",
        "expertise_fs_general_exacting_strikes_2",
        "expertise_fs_general_exacting_strikes_3",
        "expertise_fs_general_stance_saber_block_1",
        "expertise_fs_general_improved_saber_block_1",
        "expertise_fs_general_improved_saber_block_2",
        "expertise_fs_general_improved_saber_block_3",
        "expertise_fs_path_cautious_nature_1",
        "expertise_fs_path_cautious_nature_2",
        "expertise_fs_path_cautious_nature_3",
        "expertise_fs_path_cautious_nature_4",
        "expertise_fs_path_practiced_fluidity_1",
        "expertise_fs_path_practiced_fluidity_2",
        "expertise_fs_path_practiced_fluidity_3",
        "expertise_fs_path_practiced_fluidity_4",
        "expertise_fs_path_reactive_response_1",
        "expertise_fs_path_reactive_response_2",
        "expertise_fs_path_force_clarity_1",
        "expertise_fs_path_anticipate_aggression_1",
        "expertise_fs_path_soothing_aura_1",
        "expertise_fs_path_soothing_aura_2",
        "expertise_fs_path_soothing_aura_3",
        "expertise_fs_path_soothing_aura_4",
        "expertise_fs_path_hermetic_touch_1",
        "expertise_fs_path_remorseless_nature_1",
        "expertise_fs_path_remorseless_nature_2",
        "expertise_fs_path_remorseless_nature_3",
        "expertise_fs_path_remorseless_nature_4",
        "expertise_fs_path_brutality_1",
        "expertise_fs_path_brutality_2",
        "expertise_fs_path_brutality_3",
        "expertise_fs_path_brutality_4",
        "expertise_fs_path_dark_lightning_1",
        "expertise_fs_path_wracking_energy_1",
        "expertise_fs_path_force_choke_1",
        "expertise_fs_path_cloud_minds_1"
    };
    public static final String[] JEDI_SOLO = 
    {
        "expertise_fs_general_enhanced_strength_1",
        "expertise_fs_general_enhanced_strength_2",
        "expertise_fs_general_enhanced_agility_1",
        "expertise_fs_general_enhanced_agility_2",
        "expertise_fs_general_alacrity_1",
        "expertise_fs_general_alacrity_2",
        "expertise_fs_general_alacrity_3",
        "expertise_fs_general_alacrity_4",
        "expertise_fs_general_force_cloak_1",
        "expertise_fs_general_exacting_strikes_1",
        "expertise_fs_general_exacting_strikes_2",
        "expertise_fs_general_exacting_strikes_3",
        "expertise_fs_general_exacting_strikes_4",
        "expertise_fs_general_stance_saber_block_1",
        "expertise_fs_general_improved_saber_block_1",
        "expertise_fs_general_improved_saber_block_2",
        "expertise_fs_general_improved_saber_block_3",
        "expertise_fs_path_cautious_nature_1",
        "expertise_fs_path_cautious_nature_2",
        "expertise_fs_path_cautious_nature_3",
        "expertise_fs_path_cautious_nature_4",
        "expertise_fs_path_soothing_aura_1",
        "expertise_fs_path_soothing_aura_2",
        "expertise_fs_path_soothing_aura_3",
        "expertise_fs_path_soothing_aura_4",
        "expertise_fs_path_hermetic_touch_1",
        "expertise_fs_path_lasting_aura_1",
        "expertise_fs_path_lasting_aura_2",
        "expertise_fs_path_remorseless_nature_1",
        "expertise_fs_path_remorseless_nature_2",
        "expertise_fs_path_remorseless_nature_3",
        "expertise_fs_path_remorseless_nature_4",
        "expertise_fs_path_brutality_1",
        "expertise_fs_path_brutality_2",
        "expertise_fs_path_brutality_3",
        "expertise_fs_path_brutality_4",
        "expertise_fs_path_dark_lightning_1",
        "expertise_fs_path_wracking_energy_1",
        "expertise_fs_path_wracking_energy_2",
        "expertise_fs_path_wracking_energy_3",
        "expertise_fs_path_maelstrom_1",
        "expertise_fs_path_force_choke_1",
        "expertise_fs_path_cloud_minds_1",
        "expertise_fs_path_expansive_trickery_1",
        "expertise_fs_path_expansive_trickery_2"
    };
    public static final String[] MEDIC_SUPPORT = 
    {
        "expertise_me_hot_duration_1",
        "expertise_me_hot_duration_2",
        "expertise_me_hot_duration_3",
        "expertise_me_hot_duration_4",
        "expertise_me_revive_duration_1",
        "expertise_me_revive_duration_2",
        "expertise_me_revive_duration_3",
        "expertise_me_blood_cleaners_1",
        "expertise_me_bacta_bomb_1",
        "expertise_me_heal_damage_1",
        "expertise_me_heal_damage_2",
        "expertise_me_heal_damage_3",
        "expertise_me_heal_damage_4",
        "expertise_me_cure_affliction_1",
        "expertise_me_heal_action_1",
        "expertise_me_heal_action_2",
        "expertise_me_heal_action_3",
        "expertise_me_serotonin_boost_1",
        "expertise_me_bacta_grenade_1",
        "expertise_me_enhance_duration_1",
        "expertise_me_enhance_duration_2",
        "expertise_me_enhance_duration_3",
        "expertise_me_enhancement_specialist_1",
        "expertise_me_reckless_stimulation_1",
        "expertise_me_stasis_1",
        "expertise_me_vital_action_1",
        "expertise_me_vital_action_2",
        "expertise_me_vital_action_3",
        "expertise_me_vital_action_4",
        "expertise_me_bacta_resistance_1",
        "expertise_me_dot_damage_1",
        "expertise_me_dot_damage_2",
        "expertise_me_dot_damage_3",
        "expertise_me_serotonin_purge_1",
        "expertise_me_induce_insanity_1",
        "expertise_me_vital_damage_1",
        "expertise_me_vital_damage_2",
        "expertise_me_vital_damage_3",
        "expertise_me_vital_damage_4",
        "expertise_me_electrolyte_drain_1",
        "expertise_me_dot_duration_1",
        "expertise_me_dot_duration_2",
        "expertise_me_dot_duration_3",
        "expertise_me_traumatize_1",
        "expertise_me_thyroid_rupture_1"
    };
    public static final String[] MEDIC_CARBINE = 
    {
        "expertise_me_hot_duration_1",
        "expertise_me_hot_duration_2",
        "expertise_me_hot_duration_3",
        "expertise_me_hot_duration_4",
        "expertise_me_drag",
        "expertise_me_revive_duration_1",
        "expertise_me_revive_duration_2",
        "expertise_me_revive_duration_3",
        "expertise_me_blood_cleaners_1",
        "expertise_me_bacta_bomb_1",
        "expertise_me_heal_damage_1",
        "expertise_me_heal_damage_2",
        "expertise_me_heal_damage_3",
        "expertise_me_heal_damage_4",
        "expertise_me_cure_affliction_1",
        "expertise_me_heal_action_1",
        "expertise_me_heal_action_2",
        "expertise_me_heal_action_3",
        "expertise_me_serotonin_boost_1",
        "expertise_me_bacta_grenade_1",
        "expertise_me_enhance_duration_1",
        "expertise_me_enhance_duration_2",
        "expertise_me_enhance_duration_3",
        "expertise_me_enhancement_specialist_1",
        "expertise_me_reckless_stimulation_1",
        "expertise_me_stasis_1",
        "expertise_me_dot_damage_1",
        "expertise_me_dot_damage_2",
        "expertise_me_dot_damage_3",
        "expertise_me_serotonin_purge_1",
        "expertise_me_dot_duration_1",
        "expertise_me_dot_duration_2",
        "expertise_me_carbine_damage_1",
        "expertise_me_carbine_damage_2",
        "expertise_me_carbine_damage_3",
        "expertise_me_carbine_damage_4",
        "expertise_me_dueterium_rounds_1",
        "expertise_me_agility_1",
        "expertise_me_agility_2",
        "expertise_me_agility_3",
        "expertise_me_agility_4",
        "expertise_me_agro_healing_1",
        "expertise_me_agro_healing_2",
        "expertise_me_agro_healing_3",
        "expertise_me_evasion_1"
    };
    public static final String[] SPY_MELEE = 
    {
        "expertise_sp_improved_first_aid_1",
        "expertise_sp_improved_first_aid_2",
        "expertise_sp_cloaked_recovery_1",
        "expertise_sp_rapid_concealment_1",
        "expertise_sp_rapid_concealment_2",
        "expertise_sp_rapid_concealment_3",
        "expertise_sp_rapid_concealment_4",
        "expertise_sp_smoke_screen_1",
        "expertise_sp_smoke_screen_2",
        "expertise_sp_smoke_screen_3",
        "expertise_sp_smoke_screen_4",
        "expertise_sp_burst_of_shadows_1",
        "expertise_sp_without_a_trace_1",
        "expertise_sp_cloaked_attacks_1",
        "expertise_sp_cloak_and_dagger_1",
        "expertise_sp_cloak_and_dagger_2",
        "expertise_sp_cloak_and_dagger_3",
        "expertise_sp_cloak_and_dagger_4",
        "expertise_sp_initiative_1",
        "expertise_sp_initiative_2",
        "expertise_sp_initiative_3",
        "expertise_sp_initiative_4",
        "expertise_sp_puncturing_strikes_1",
        "expertise_sp_puncturing_strikes_2",
        "expertise_sp_puncturing_strikes_3",
        "expertise_sp_puncturing_strikes_4",
        "expertise_sp_undercover_1",
        "expertise_sp_undercover_2",
        "expertise_sp_undercover_3",
        "expertise_sp_improved_spys_fang_1",
        "expertise_sp_hidden_daggers_1",
        "expertise_sp_improved_arachnids_web_1",
        "expertise_sp_noxious_traps_1",
        "expertise_sp_noxious_traps_2",
        "expertise_sp_deadly_toxins_1",
        "expertise_sp_increased_strength_1",
        "expertise_sp_increased_strength_2",
        "expertise_sp_increased_strength_3",
        "expertise_sp_increased_strength_4",
        "expertise_sp_close_quarters_1",
        "expertise_sp_close_quarters_2",
        "expertise_sp_close_quarters_3",
        "expertise_sp_cheap_strikes_1",
        "expertise_sp_cheap_strikes_2",
        "expertise_sp_cheap_strikes_3"
    };
    public static final String[] SPY_RANGED = 
    {
        "expertise_sp_improved_first_aid_1",
        "expertise_sp_improved_first_aid_2",
        "expertise_sp_cloaked_recovery_1",
        "expertise_sp_rapid_concealment_1",
        "expertise_sp_rapid_concealment_2",
        "expertise_sp_rapid_concealment_3",
        "expertise_sp_rapid_concealment_4",
        "expertise_sp_smoke_screen_1",
        "expertise_sp_smoke_screen_2",
        "expertise_sp_smoke_screen_3",
        "expertise_sp_smoke_screen_4",
        "expertise_sp_burst_of_shadows_1",
        "expertise_sp_without_a_trace_1",
        "expertise_sp_cloaked_attacks_1",
        "expertise_sp_cloak_and_dagger_1",
        "expertise_sp_cloak_and_dagger_2",
        "expertise_sp_cloak_and_dagger_3",
        "expertise_sp_cloak_and_dagger_4",
        "expertise_sp_initiative_1",
        "expertise_sp_initiative_2",
        "expertise_sp_initiative_3",
        "expertise_sp_initiative_4",
        "expertise_sp_undercover_1",
        "expertise_sp_undercover_2",
        "expertise_sp_undercover_3",
        "expertise_sp_improved_spys_fang_1",
        "expertise_sp_protective_armor_1",
        "expertise_sp_improved_arachnids_web_1",
        "expertise_sp_noxious_traps_1",
        "expertise_sp_noxious_traps_2",
        "expertise_sp_opportunity_1",
        "expertise_sp_opportunity_2",
        "expertise_sp_opportunity_3",
        "expertise_sp_opportunity_4",
        "expertise_sp_cheap_shots_1",
        "expertise_sp_cheap_shots_2",
        "expertise_sp_cheap_shots_3",
        "expertise_sp_cheap_shots_4",
        "expertise_sp_precision_1",
        "expertise_sp_precision_2",
        "expertise_sp_precision_3",
        "expertise_sp_precision_4",
        "expertise_sp_deadly_pistols_1",
        "expertise_sp_deadly_pistols_2",
        "expertise_sp_deadly_pistols_3"
    };
    public static final String[] SMUGGLER_HYBRID = 
    {
        "expertise_sm_general_hidden_padding_1",
        "expertise_sm_general_hidden_padding_2",
        "expertise_sm_general_lined_pockets_1",
        "expertise_sm_general_lined_pockets_2",
        "expertise_sm_general_gun_oil_1",
        "expertise_sm_general_hair_trigger_1",
        "expertise_sm_general_hair_trigger_2",
        "expertise_sm_general_hair_trigger_3",
        "expertise_sm_general_hair_trigger_4",
        "expertise_sm_general_precise_bead_1",
        "expertise_sm_general_precise_bead_2",
        "expertise_sm_general_precise_bead_3",
        "expertise_sm_general_precise_bead_4",
        "expertise_sm_general_hammer_fanning_1",
        "expertise_sm_general_hammer_fanning_2",
        "expertise_sm_general_hammer_fanning_3",
        "expertise_sm_general_hammer_fanning_4",
        "expertise_sm_general_break_the_deal_1",
        "expertise_sm_general_elbow_grease_1",
        "expertise_sm_general_switcheroo_1",
        "expertise_sm_general_switcheroo_2",
        "expertise_sm_general_switcheroo_3",
        "expertise_sm_general_switcheroo_4",
        "expertise_sm_general_head_crack_1",
        "expertise_sm_general_head_crack_2",
        "expertise_sm_general_head_crack_3",
        "expertise_sm_general_head_crack_4",
        "expertise_sm_general_one_two_pummel_1",
        "expertise_sm_general_one_two_pummel_2",
        "expertise_sm_general_one_two_pummel_3",
        "expertise_sm_general_one_two_pummel_4",
        "expertise_sm_general_bad_odds_1",
        "expertise_sm_general_spot_a_sucker_1",
        "expertise_sm_general_meager_fortune_1",
        "expertise_sm_general_meager_fortune_2",
        "expertise_sm_general_wretched_fate_1",
        "expertise_sm_general_wretched_fate_2",
        "expertise_sm_general_poor_prospect_1",
        "expertise_sm_general_poor_prospect_2",
        "expertise_sm_general_off_the_cuff_1",
        "expertise_sm_general_double_deal_1",
        "expertise_sm_general_double_deal_2",
        "expertise_sm_general_end_of_the_line_1",
        "expertise_sm_general_narrow_escape_1",
        "expertise_sm_path_healthy_profits_1"
    };
    public static final String[] SMUGGLER_MELEE = 
    {
        "expertise_sm_general_hidden_padding_1",
        "expertise_sm_general_hidden_padding_2",
        "expertise_sm_general_gun_oil_1",
        "expertise_sm_general_elbow_grease_1",
        "expertise_sm_general_switcheroo_1",
        "expertise_sm_general_switcheroo_2",
        "expertise_sm_general_switcheroo_3",
        "expertise_sm_general_switcheroo_4",
        "expertise_sm_general_head_crack_1",
        "expertise_sm_general_head_crack_2",
        "expertise_sm_general_head_crack_3",
        "expertise_sm_general_head_crack_4",
        "expertise_sm_general_one_two_pummel_1",
        "expertise_sm_general_one_two_pummel_2",
        "expertise_sm_general_one_two_pummel_3",
        "expertise_sm_general_one_two_pummel_4",
        "expertise_sm_general_bad_odds_1",
        "expertise_sm_general_off_the_cuff_1",
        "expertise_sm_general_double_deal_1",
        "expertise_sm_general_double_deal_2",
        "expertise_sm_general_end_of_the_line_1",
        "expertise_sm_general_narrow_escape_1",
        "expertise_sm_path_easy_money_1",
        "expertise_sm_path_easy_money_2",
        "expertise_sm_path_ploy_1",
        "expertise_sm_path_ploy_2",
        "expertise_sm_path_scandal_1",
        "expertise_sm_path_scandal_2",
        "expertise_sm_path_scandal_3",
        "expertise_sm_path_scandal_4",
        "expertise_sm_path_smooth_move_1",
        "expertise_sm_path_smooth_move_2",
        "expertise_sm_path_smooth_move_3",
        "expertise_sm_path_smooth_move_4",
        "expertise_sm_path_false_hope_1",
        "expertise_sm_path_fake_goods_1",
        "expertise_sm_path_fake_goods_2",
        "expertise_sm_path_healthy_profits_1",
        "expertise_sm_path_underworld_boss_1",
        "expertise_sm_path_inside_information_1",
        "expertise_sm_path_long_odds_1",
        "expertise_sm_path_quick_fix_1",
        "expertise_sm_path_quick_fix_2",
        "expertise_sm_path_quick_fix_3",
        "expertise_sm_path_quick_fix_4"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qasetup");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qasetup");
        }
        sendSystemMessage(self, "QA-Setup attached.", null);
        sendSystemMessage(self, "For available commands type 'qasetup'.", null);
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        obj_id pInv = utils.getInventoryContainer(self);
        if (text.equals("qasetup"))
        {
            sendSystemMessage(self, "*******************************************************", null);
            sendSystemMessage(self, "QA-Setup Commands - Say 'qasetup' to show this list", null);
            sendSystemMessage(self, "Using a template code will set your character to level 90 with a preset expertise template, suitable weapon(s), ", null);
            sendSystemMessage(self, "modded armor and clothing as well as some generic items that high level players would normally carry.", null);
            sendSystemMessage(self, "Use on of the following key phrases to set you template:", null);
            sendSystemMessage(self, "qa_bh_rifle, An offensive BH build that focuses on a high damage output.", null);
            sendSystemMessage(self, "qa_bh_carbine, A BH build using the Carbine line and most of the available defensive options. A strong tank.", null);
            sendSystemMessage(self, "qa_commando, A typical commando build with strong defense and debuff abilities.", null);
            sendSystemMessage(self, "qa_officer_Melee, An officer build with the officer melee line. Probably the the highest burst damage template available.", null);
            sendSystemMessage(self, "qa_officer_Ranged, This build focuses more on support through the supply line, group buffs and ranged abilities", null);
            sendSystemMessage(self, "qa_jedi_tank, Jedi with a full defensive setup. Very strong tank.", null);
            sendSystemMessage(self, "qa_jedi_solo, sacrifices some defense for more damage and versatility", null);
            sendSystemMessage(self, "qa_medic_support, Set up with full heals and debuff abilities. No weapon skills or threat reduction", null);
            sendSystemMessage(self, "qa_medic_carbine, This template drops some of the less popular debuffs in favor of carbine skills and threat reduction", null);
            sendSystemMessage(self, "qa_spy_melee, Melee spy with very high 'out of stealth' damage. The tradeoff is the risk of being in close with the spys low defense", null);
            sendSystemMessage(self, "qa_spy_ranged, Less damage potential but the ranged spy has the benifit of being effective at long range", null);
            sendSystemMessage(self, "qa_smuggler_hybrid, A smuggler build with both the melee and pistol lines. Very high damage potential, but suffers from high action costs", null);
            sendSystemMessage(self, "qa_smuggler_melee, This build uses the smuggler melee line in addition to improved heals and armor", null);
            sendSystemMessage(self, "'qa_buff' to grant your character a full set of buffs (Entertainer, Medic, Food and Mustafarian Injector buff", null);
            sendSystemMessage(self, "", null);
            sendSystemMessage(self, "For a list of commands to gain ONLY modded armor, clothing, and weapons, say 'qaobjects'", null);
            sendSystemMessage(self, "*******************************************************", null);
        }
        if (text.equals("qaobjects"))
        {
            sendSystemMessage(self, "*******************************************************", null);
            sendSystemMessage(self, "QA-Objects Commands - Say 'qaobjects' to show this list", null);
            sendSystemMessage(self, "Using a template code will set your character with suitable weapon(s), ", null);
            sendSystemMessage(self, "modded armor and clothing as well as some generic items that high level players would normally carry.", null);
            sendSystemMessage(self, "Use on of the following key phrases to set you template:", null);
            sendSystemMessage(self, "qa_objects_bh_rifle, An offensive BH build that focuses on a high damage output.", null);
            sendSystemMessage(self, "qa_objects_bh_carbine, A BH build using the Carbine line and most of the available defensive options. A strong tank.", null);
            sendSystemMessage(self, "qa_objects_commando, A typical commando build with strong defense and debuff abilities.", null);
            sendSystemMessage(self, "qa_objects_officer_Melee, An officer build with the officer melee line. Probably the the highest burst damage template available.", null);
            sendSystemMessage(self, "qa_objects_officer_Ranged, This build focuses more on support through the supply line, group buffs and ranged abilities", null);
            sendSystemMessage(self, "qa_objects_jedi_tank, Jedi with a full defensive setup. Very strong tank.", null);
            sendSystemMessage(self, "qa_objects_jedi_solo, sacrifices some defense for more damage and versatility", null);
            sendSystemMessage(self, "qa_objects_medic_support, Set up with full heals and debuff abilities. No weapon skills or threat reduction", null);
            sendSystemMessage(self, "qa_objects_medic_carbine, This template drops some of the less popular debuffs in favor of carbine skills and threat reduction", null);
            sendSystemMessage(self, "qa_objects_spy_melee, Melee spy with very high 'out of stealth' damage. The tradeoff is the risk of being in close with the spys low defense", null);
            sendSystemMessage(self, "qa_objects_spy_ranged, Less damage potential but the ranged spy has the benifit of being effective at long range", null);
            sendSystemMessage(self, "qa_objects_smuggler_hybrid, A smuggler build with both the melee and pistol lines. Very high damage potential, but suffers from high action costs", null);
            sendSystemMessage(self, "qa_objects_smuggler_melee, This build uses the smuggler melee line in addition to improved heals and armor", null);
            sendSystemMessage(self, "", null);
            sendSystemMessage(self, "'qa_buff' to grant your character a full set of buffs (Entertainer, Medic, Food and Mustafarian Injector buff", null);
            sendSystemMessage(self, "", null);
            sendSystemMessage(self, "For a list of commands to set your character to level 90 with a preset expertise template, say 'qasetup'", null);
            sendSystemMessage(self, "*******************************************************", null);
        }
        if (text.equals("qa_bh_rifle"))
        {
            setTemplate((self), BH_RIFLE, "bounty_hunter_1a");
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_4, "rangedDps", "shirtRifle");
            issueClothes((self), CLOTHES, "rangedDps", "shirtRifle");
            createPup((self), "expertise_action_weapon_0", POWERUP_ITEMS, 10);
            static_item.createNewItemFunction("weapon_tow_rifle_lightning_cannon_04_01", pInv);
        }
        if (text.equals("qa_objects_bh_rifle"))
        {
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_4, "rangedDps", "shirtRifle");
            issueClothes((self), CLOTHES, "rangedDps", "shirtRifle");
            createPup((self), "expertise_action_weapon_0", POWERUP_ITEMS, 10);
            static_item.createNewItemFunction("weapon_tow_rifle_lightning_cannon_04_01", pInv);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_bh_carbine"))
        {
            setTemplate((self), BH_CARBINE, "bounty_hunter_1a");
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_4, "rangedDps", "shirtCarbine");
            issueClothes((self), CLOTHES, "rangedDps", "shirtCarbine");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
        }
        if (text.equals("qa_objects_bh_carbine"))
        {
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_4, "rangedDps", "shirtCarbine");
            issueClothes((self), CLOTHES, "rangedDps", "shirtCarbine");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_commando"))
        {
            setTemplate((self), COMMANDO, "commando_1a");
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_1, "rangedTank", "shirtTank");
            issueClothes((self), CLOTHES, "rangedDps", "shirtTank");
            createPup((self), "expertise_critical_heavy", POWERUP_ITEMS, 10);
            static_item.createNewItemFunction("weapon_tow_heavy_acid_beam_04_01", pInv);
            static_item.createNewItemFunction("weapon_tow_cannon_04_02", pInv);
            static_item.createNewItemFunction("weapon_mandalorian_heavy_04_01", pInv);
        }
        if (text.equals("qa_objects_commando"))
        {
            issueAssaultArmorSet((self), ARMOR_SET_ASSAULT_1, "rangedTank", "shirtTank");
            issueClothes((self), CLOTHES, "rangedDps", "shirtTank");
            createPup((self), "expertise_critical_heavy", POWERUP_ITEMS, 10);
            static_item.createNewItemFunction("weapon_tow_heavy_acid_beam_04_01", pInv);
            static_item.createNewItemFunction("weapon_tow_cannon_04_02", pInv);
            static_item.createNewItemFunction("weapon_mandalorian_heavy_04_01", pInv);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_officer_Melee"))
        {
            setTemplate((self), OFFICER_MELEE, "officer_1a");
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_4, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirt1h");
            createPup((self), "expertise_critical_1h", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
        }
        if (text.equals("qa_objects_officer_Melee"))
        {
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_4, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirt1h");
            createPup((self), "expertise_critical_1h", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_officer_Ranged"))
        {
            setTemplate((self), OFFICER_RANGED, "officer_1a");
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_4, "rangedDps", "shirtTank");
            issueClothes((self), CLOTHES, "rangedDps", "shirtTank");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
        }
        if (text.equals("qa_objects_officer_Ranged"))
        {
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_4, "rangedDps", "shirtTank");
            issueClothes((self), CLOTHES, "rangedDps", "shirtTank");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_jedi_tank"))
        {
            setTemplate((self), JEDI_TANK, "force_sensitive_1a");
            static_item.createNewItemFunction("item_jedi_robe_06_01", pInv);
            issueClothes((self), CLOTHES, "tank", "shirtTank");
            createPup((self), "combat_parry", POWERUP_ITEMS, 6);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_tow_lava_crystal_06_01", pInv);
            obj_id lightsaber = createObject("object/weapon/melee/2h_sword/crafted_saber/sword_lightsaber_two_handed_gen4_must.iff", pInv, "");
            attachMods(lightsaber, "shirtTank");
        }
        if (text.equals("qa_objects_jedi_tank"))
        {
            static_item.createNewItemFunction("item_jedi_robe_06_01", pInv);
            issueClothes((self), CLOTHES, "tank", "shirtTank");
            createPup((self), "combat_parry", POWERUP_ITEMS, 6);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_tow_lava_crystal_06_01", pInv);
            obj_id lightsaber = createObject("object/weapon/melee/2h_sword/crafted_saber/sword_lightsaber_two_handed_gen4_must.iff", pInv, "");
            attachMods(lightsaber, "shirtTank");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_jedi_solo"))
        {
            setTemplate((self), JEDI_SOLO, "force_sensitive_1a");
            static_item.createNewItemFunction("item_jedi_robe_06_01", pInv);
            issueClothes((self), CLOTHES, "tank", "shirtTank");
            createPup((self), "combat_parry", POWERUP_ITEMS, 6);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_tow_lava_crystal_06_01", pInv);
            obj_id lightsaber = createObject("object/weapon/melee/2h_sword/crafted_saber/sword_lightsaber_two_handed_gen4_must.iff", pInv, "");
            attachMods(lightsaber, "shirtTank");
        }
        if (text.equals("qa_objects_jedi_solo"))
        {
            static_item.createNewItemFunction("item_jedi_robe_06_01", pInv);
            issueClothes((self), CLOTHES, "tank", "shirtTank");
            createPup((self), "combat_parry", POWERUP_ITEMS, 6);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_krayt_pearl_04_20", pInv);
            static_item.createNewItemFunction("item_tow_lava_crystal_06_01", pInv);
            obj_id lightsaber = createObject("object/weapon/melee/2h_sword/crafted_saber/sword_lightsaber_two_handed_gen4_must.iff", pInv, "");
            attachMods(lightsaber, "shirtTank");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_medic_support"))
        {
            setTemplate((self), MEDIC_SUPPORT, "medic_1a");
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_3, "healer", "shirtHealer");
            issueClothes((self), CLOTHES, "healer", "shirtHealer");
            createPup((self), "expertise_healing_all", POWERUP_ITEMS, 8);
        }
        if (text.equals("qa_objects_medic_support"))
        {
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_3, "healer", "shirtHealer");
            issueClothes((self), CLOTHES, "healer", "shirtHealer");
            createPup((self), "expertise_healing_all", POWERUP_ITEMS, 8);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_medic_carbine"))
        {
            setTemplate((self), MEDIC_CARBINE, "medic_1a");
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_3, "healer", "shirtTank");
            issueClothes((self), CLOTHES, "healer", "shirtTank");
            createPup((self), "expertise_healing_all", POWERUP_ITEMS, 8);
        }
        if (text.equals("qa_objects_medic_carbine"))
        {
            issueBattleArmorSet((self), ARMOR_SET_BATTLE_3, "healer", "shirtTank");
            issueClothes((self), CLOTHES, "healer", "shirtTank");
            createPup((self), "expertise_healing_all", POWERUP_ITEMS, 8);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_spy_melee"))
        {
            setTemplate((self), SPY_MELEE, "spy_1a");
            issueReconArmorSet((self), ARMOR_SET_RECON_2, "meleeSpy", "shirt1h");
            issueClothes((self), CLOTHES, "meleeSpy", "shirt1h");
            createPup((self), "expertise_critical_1h", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
        }
        if (text.equals("qa_objects_spy_melee"))
        {
            issueReconArmorSet((self), ARMOR_SET_RECON_2, "meleeSpy", "shirt1h");
            issueClothes((self), CLOTHES, "meleeSpy", "shirt1h");
            createPup((self), "expertise_critical_1h", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_spy_ranged"))
        {
            setTemplate((self), SPY_RANGED, "spy_1a");
            issueReconArmorSet((self), ARMOR_SET_RECON_2, "rangedSpy", "shirtCarbine");
            issueClothes((self), CLOTHES, "rangedSpy", "shirtCarbine");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
        }
        if (text.equals("qa_objects_spy_ranged"))
        {
            issueReconArmorSet((self), ARMOR_SET_RECON_2, "rangedSpy", "shirtCarbine");
            issueClothes((self), CLOTHES, "rangedSpy", "shirtCarbine");
            createPup((self), "expertise_action_weapon_1", POWERUP_ITEMS, 10);
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_smuggler_hybrid"))
        {
            setTemplate((self), SMUGGLER_HYBRID, "smuggler_1a");
            issueReconArmorSet((self), ARMOR_SET_RECON_3, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirtTank");
            createPup((self), "combat_dodge", POWERUP_ITEMS, 8);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
        }
        if (text.equals("qa_objects_smuggler_hybrid"))
        {
            issueReconArmorSet((self), ARMOR_SET_RECON_3, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirtTank");
            createPup((self), "combat_dodge", POWERUP_ITEMS, 8);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_smuggler_melee"))
        {
            setTemplate((self), SMUGGLER_MELEE, "smuggler_1a");
            issueReconArmorSet((self), ARMOR_SET_RECON_3, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirtTank");
            createPup((self), "expertise_action_weapon_4", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
        }
        if (text.equals("qa_objects_smuggler_melee"))
        {
            issueReconArmorSet((self), ARMOR_SET_RECON_3, "meleeDps", "shirt1h");
            issueClothes((self), CLOTHES, "meleeDps", "shirtTank");
            createPup((self), "expertise_action_weapon_4", POWERUP_ITEMS, 10);
            obj_id melee = weapons.createWeapon("object/weapon/melee/sword/sword_acid.iff", pInv, weapons.VIA_TEMPLATE, WEAPON_SPEED, WEAPON_DAMAGE, WEAPON_EFFECIENCY, WEAPON_ELEMENTAL);
            attachMods(melee, "shirt1h");
            sendSystemMessage(self, "You have received your objects.", null);
        }
        if (text.equals("qa_buff"))
        {
            addEntBuff(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void handleProfessionLevelToNinety(obj_id player, String roadmap) throws InterruptedException
    {
        revokeAllSkills(player);
        int currentCombatXp = getExperiencePoints(player, "combat_general");
        grantExperiencePoints(player, "combat_general", -currentCombatXp);
        skill.recalcPlayerPools(player, true);
        setSkillTemplate(player, roadmap);
        respec.autoLevelPlayer(player, 90, false);
        utils.fullExpertiseReset(player, false);
        skill.setPlayerStatsForLevel(player, 90);
    }
    public void revokeAllSkills(obj_id player) throws InterruptedException
    {
        String[] skillList = getSkillListingForPlayer(player);
        int attempts = skillList.length;
        if ((skillList != null) && (skillList.length != 0))
        {
            while (skillList.length > 0 && attempts > 0)
            {
                for (String skillName : skillList) {
                    if (!skillName.startsWith("species_") && !skillName.startsWith("social_language_") && !skillName.startsWith("utility_") && !skillName.startsWith("common_") && !skillName.startsWith("demo_") && !skillName.startsWith("force_title_") && !skillName.startsWith("force_sensitive_") && !skillName.startsWith("combat_melee_basic") && !skillName.startsWith("pilot_") && !skillName.startsWith("internal_expertise_") && !skillName.startsWith("class_chronicles_") && !skillName.startsWith("combat_ranged_weapon_basic")) {
                        skill.revokeSkillSilent(player, skillName);
                    }
                }
                skillList = getSkillListingForPlayer(player);
                --attempts;
            }
        }
        skill.recalcPlayerPools(player, true);
    }
    public void setTemplate(obj_id player, String[] skillList, String baseClass) throws InterruptedException
    {
        handleProfessionLevelToNinety(player, baseClass);
        utils.fullExpertiseReset(player, false);
        for (String expertiseSkill : skillList) {
            skill.grantSkillToPlayer(player, expertiseSkill);
        }
    }
    public void issueAssaultArmorSet(obj_id player, String[] armorPieces, String modType, String specialModType) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_body)) {
                    attachMods(armorItem, modType);
                } else {
                    attachMods(armorItem, specialModType);
                }
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 2, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_ASSAULT_LAYER, 1.0f);
                }
            }
        }
    }
    public void issueBattleArmorSet(obj_id player, String[] armorPieces, String modType, String specialModType) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_body)) {
                    attachMods(armorItem, modType);
                } else {
                    attachMods(armorItem, specialModType);
                }
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 1, GENERAL_PROTECTION, CONDITION);
                }
            }
        }
    }
    public void issueReconArmorSet(obj_id player, String[] armorPieces, String modType, String specialModType) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String armorPiece : armorPieces) {
            String armorTemplate = ARMOR_SET_PREFIX + armorPiece;
            obj_id armorItem = createObject(armorTemplate, pInv, "");
            if (isIdValid(armorItem)) {
                if (!isGameObjectTypeOf(armorItem, GOT_armor_body)) {
                    attachMods(armorItem, modType);
                } else {
                    attachMods(armorItem, specialModType);
                }
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand)) {
                    armor.setArmorDataPercent(armorItem, 2, 0, GENERAL_PROTECTION, CONDITION);
                    armor.setArmorSpecialProtectionPercent(armorItem, armor.DATATABLE_RECON_LAYER, 1.0f);
                }
            }
        }
    }
    public void issueClothes(obj_id player, String[] clothesPieces, String modType, String specialModType) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        static_item.createNewItemFunction("item_gcw_base_health_c_03_01", pInv);
        static_item.createNewItemFunction("item_gcw_base_health_c_03_01", pInv);
        obj_id PSGitem = createObject("object/tangible/component/armor/shield_generator_personal_c.iff", pInv, "");
        armor.initializePsg(PSGitem, 2.5f, 2500, 10000);
        static_item.createNewItemFunction("weapon_tow_carbine_05_01", pInv);
        for (String clothesTemplate : clothesPieces) {
            obj_id clothesItem = createObject(clothesTemplate, pInv, "");
            if (!isGameObjectTypeOf(clothesItem, GOT_clothing_shirt)) {
                attachMods(clothesItem, modType);
            } else {
                attachMods(clothesItem, specialModType);
            }
        }
    }
    public void attachMods(obj_id item, String template) throws InterruptedException
    {
        if (template.equals("rangedDps"))
        {
            setObjVar(item, "skillmod.bonus.precision_modified", 35);
            setObjVar(item, "skillmod.bonus.luck_modified", 35);
            setObjVar(item, "skillmod.bonus.stamina_modified", 35);
        }
        switch (template) {
            case "rangedTank":
                setObjVar(item, "skillmod.bonus.precision_modified", 35);
                setObjVar(item, "skillmod.bonus.agility_modified", 35);
                setObjVar(item, "skillmod.bonus.luck_modified", 35);
                break;
            case "meleeDps":
                setObjVar(item, "skillmod.bonus.strength_modified", 35);
                setObjVar(item, "skillmod.bonus.agility_modified", 35);
                setObjVar(item, "skillmod.bonus.stamina_modified", 35);
                break;
            case "healer":
                setObjVar(item, "skillmod.bonus.constitution_modified", 35);
                setObjVar(item, "skillmod.bonus.agility_modified", 35);
                setObjVar(item, "skillmod.bonus.stamina_modified", 35);
                break;
            case "tank":
                setObjVar(item, "skillmod.bonus.constitution_modified", 35);
                setObjVar(item, "skillmod.bonus.agility_modified", 35);
                setObjVar(item, "skillmod.bonus.strength_modified", 35);
                break;
            case "rangedSpy":
                setObjVar(item, "skillmod.bonus.camouflage", 35);
                setObjVar(item, "skillmod.bonus.precision_modified", 35);
                setObjVar(item, "skillmod.bonus.stamina_modified", 35);
                break;
            case "meleeSpy":
                setObjVar(item, "skillmod.bonus.camouflage", 35);
                setObjVar(item, "skillmod.bonus.strength_modified", 35);
                setObjVar(item, "skillmod.bonus.stamina_modified", 35);
                break;
            case "shirtTank":
                setObjVar(item, "skillmod.bonus.combat_dodge", 4);
                setObjVar(item, "skillmod.bonus.combat_parry", 5);
                setObjVar(item, "skillmod.bonus.combat_critical_hit_reduction", 8);
                break;
            case "shirtRifle":
                setObjVar(item, "skillmod.bonus.expertise_action_weapon_0", 3);
                setObjVar(item, "skillmod.bonus.expertise_critical_rifle", 3);
                setObjVar(item, "skillmod.bonus.combat_strikethrough_chance", 6);
                break;
            case "shirtCarbine":
                setObjVar(item, "skillmod.bonus.expertise_action_weapon_1", 3);
                setObjVar(item, "skillmod.bonus.expertise_critical_carbine", 3);
                setObjVar(item, "skillmod.bonus.combat_strikethrough_chance", 6);
                break;
            case "shirtPistol":
                setObjVar(item, "skillmod.bonus.expertise_action_weapon_2", 3);
                setObjVar(item, "skillmod.bonus.expertise_critical_pistol", 3);
                setObjVar(item, "skillmod.bonus.combat_strikethrough_chance", 6);
                break;
            case "shirt1h":
                setObjVar(item, "skillmod.bonus.expertise_action_weapon_4", 3);
                setObjVar(item, "skillmod.bonus.expertise_critical_1h", 3);
                setObjVar(item, "skillmod.bonus.combat_strikethrough_chance", 6);
                break;
            case "shirtHealer":
                setObjVar(item, "skillmod.bonus.expertise_healing_all", 4);
                setObjVar(item, "skillmod.bonus.combat_parry", 5);
                setObjVar(item, "skillmod.bonus.combat_critical_hit_reduction", 8);
                break;
        }
    }
    public void createPup(obj_id player, String mod, String[] pup_items, int r) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        for (String pup_item : pup_items) {
            int numPupCharges = 100;
            int power = 90;
            int ratio = r;
            String pupString = pup_item;
            obj_id powerup = static_item.createNewItemFunction(pupString, pInv);
            setObjVar(powerup, "reverse_engineering.reverse_engineering_power", power);
            setObjVar(powerup, "reverse_engineering.reverse_engineering_modifier", mod);
            setObjVar(powerup, "reverse_engineering.reverse_engineering_ratio", ratio);
            setCount(powerup, numPupCharges);
            setName(powerup, "[QA] Powerup - " + mod);
        }
    }
    public void addEntBuff(obj_id recipientId) throws InterruptedException
    {
        obj_id bufferId = recipientId;
        float currentBuffTime = performance.inspireGetMaxDuration(recipientId);
        buff.applyBuff(recipientId, "buildabuff_inspiration", 3600);
        utils.setScriptVar(recipientId, "performance.buildabuff.buffComponentKeys", buffComponentKeys);
        utils.setScriptVar(recipientId, "performance.buildabuff.buffComponentValues", buffComponentValues);
        utils.setScriptVar(recipientId, "performance.buildabuff.bufferId", bufferId);
        buff.applyBuff((recipientId), "me_buff_health_2", 3600);
        buff.applyBuff((recipientId), "me_buff_action_3", 3600);
        buff.applyBuff((recipientId), "me_buff_strength_3", 3600);
        buff.applyBuff((recipientId), "me_buff_agility_3", 3600);
        buff.applyBuff((recipientId), "me_buff_precision_3", 3600);
        buff.applyBuff((recipientId), "towPoisonResistAbsorb_3", 3600);
        buff.applyBuff((recipientId), "drink_flameout", 3600);
        sendSystemMessage(recipientId, "Buffs granted.", null);
    }
}
