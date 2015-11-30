package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.city;
import script.library.create;
import script.library.hue;
import script.library.loot;
import script.library.npe;
import script.library.pet_lib;
import script.library.resource;
import script.library.utils;

public class incubator extends script.base_script
{
    public incubator()
    {
    }
    public static final int MAX_ADJUSTED_POINTS_PER_SESSION_DPS_ARMOR = 8;
    public static final int MAX_ADJUSTED_POINTS_PER_SESSION_ATTRIBUTES = 16;
    public static final int MAX_SESSION_SKILL_INCREMENT = 10;
    public static final int MAX_TOTAL_POINTS_ATTRIBUTES = 50;
    public static final int MAX_TOTAL_POINTS_DPS_ARMOR = 23;
    public static final int MAX_TOTAL_SKILL_INCREMENT = 10;
    public static final int MAX_SESSION_TIME = 60 * 60 * 40;
    public static final int MAX_POINTS_PER_SESSION_DPS_ARMOR = 5;
    public static final int MAX_POINTS_PER_SESSION_ATTRIBUTES = 14;
    public static final int MAX_GUI_POWER = 1000;
    public static final int MAX_BONUS_FOR_POWER_QUALITY = 11;
    public static final int MAX_POWER_QUALITY = 1000;
    public static final int CHANCE_FOR_ONE_KNOWN_SKILL = 70;
    public static final int NUMBER_OF_COLORS = 8;
    public static final int SESSIONS_TO_HATCH = 4;
    public static final int NEXT_SESSION_TIME = 60 * 60 * 24;
    public static final int TEMP_SCALE_MAX_RANGE = 10;
    public static final int RESOURCE_POWER_AMOUNT_CAP = 50000;
    public static final int POWER_PER_SESSION = 2500;
    public static final int MAX_MUTAGEN_POINTS = 20;
    public static final int MUTATION_MAX_INCREASE = 18;
    public static final int MAX_MUTATIONS = 3;
    public static final int MUTATION_SKILL_BONUS_AMT = 2;
    public static final float MUTATION_BONUS_DNA = 3.5f;
    public static final float MUTATION_BONUS_ENZYME = 3.5f;
    public static final float MUTATION_BONUS_INCUBATOR = 3.5f;
    public static final float MUTATION_BONUS_MUTAGEN = 4.5f;
    public static final float BASE_MUTATION_CHANCE = 2.0f;
    public static final int MUTATION_PALETTE_LENGTH = 20;
    public static final int GUI_TIME_TO_REFRESH = 10800;
    public static final float TEMP_SCALE_CONVERSION_TO_PERCENT = 0.1f;
    public static final float MIN_QUALITY_RANGE = 10.0f;
    public static final float MAX_QUALITY_RANGE = 90.0f;
    public static final float STATION_QUALITY_MIN = -12.0f;
    public static final float STATION_QUALITY_MAX = 4.0f;
    public static final float STATION_FUNCTIONALITY_MIN = -12.0f;
    public static final float STATION_FUNCTIONALITY_MAX = 4.0f;
    public static final int MAX_RE_EXOTIC_DPS_ARMOR_SKILLMOD = 10;
    public static final int MAX_RE_EXOTIC_MUTATION_SKILLMOD = 10;
    public static final String DATATABLE_INCUBATOR_TEMPLATES = "datatables/beast/incubator_templates.iff";
    public static final String DATATABLE_TEMPLATE_MUTATION_COLORS = "datatables/beast/beast_master_color_chances.iff";
    public static final String BASE_ENZYME_OBJVAR = "beast.enzyme";
    public static final String BASE_DNA_OBJVAR = "beast.dna";
    public static final String ENZYME_LEVEL_OBJVAR = BASE_ENZYME_OBJVAR + ".level";
    public static final String ENZYME_TYPE_OBJVAR = BASE_ENZYME_OBJVAR + ".type";
    public static final String ENZYME_QUALITY_OBJVAR = BASE_ENZYME_OBJVAR + ".quality";
    public static final String ENZYME_RANDOM_STATS_OBJVAR = BASE_ENZYME_OBJVAR + ".randomStats";
    public static final String ENZYME_FREE_STAT_OBJVAR = BASE_ENZYME_OBJVAR + ".freeStatName";
    public static final String ENZYME_PURITY_OBJVAR = "enzyme.enzyme_purity";
    public static final String ENZYME_MUTAGEN_OBJVAR = "enzyme.enzyme_mutagen";
    public static final String ENZYME_TRAIT_OBJVAR = "trait";
    public static final String ENZYME_COLOR_OBJVAR = BASE_ENZYME_OBJVAR + ".color";
    public static final String DNA_TEMPLATE_OBJVAR = BASE_DNA_OBJVAR + ".creature_template";
    public static final String DNA_PARENT_TEMPLATE = BASE_DNA_OBJVAR + ".parent_creature_template";
    public static final String DNA_PARENT_TEMPLATE_NAME = BASE_DNA_OBJVAR + ".parent_creature_name";
    public static final String DNA_QUALITY_OBJVAR = BASE_DNA_OBJVAR + ".quality";
    public static final String DNA_BEEN_SAMPLED_SCRIPTVAR = BASE_DNA_OBJVAR + ".sampled";
    public static final String DNA_HUE_OLD_PET = BASE_DNA_OBJVAR + ".hue";
    public static final String DNA_SCALE_OLD_PET = BASE_DNA_OBJVAR + ".scale";
    public static final String DNA_OLD_PET_IDENTIFIER = BASE_DNA_OBJVAR + ".converted_pet";
    public static final String DNA_PARENT_NAME = BASE_DNA_OBJVAR + ".name";
    public static final String DNA_CS = BASE_DNA_OBJVAR + ".cs_item";
    public static final String BASE_INCUBATOR_OBJVAR = "incubator";
    public static final String BASE_INCUBATOR_POWER_OBJVAR = "power";
    public static final String ACTIVE_INCUBATOR = BASE_INCUBATOR_OBJVAR + ".active";
    public static final String SESSION_NUMBER = BASE_INCUBATOR_OBJVAR + ".session";
    public static final String LAST_MUTATION = BASE_INCUBATOR_OBJVAR + ".last_mutation";
    public static final String ACTIVE_SESSION = SESSION_NUMBER + ".active";
    public static final String NEXT_SESSION = BASE_INCUBATOR_OBJVAR + ".next_session";
    public static final String POINTS_ALLOCATED = BASE_INCUBATOR_OBJVAR + ".points_allocated";
    public static final String OBJVAR_EGG_CREATED = BASE_INCUBATOR_OBJVAR + ".egg_created";
    public static final String ALLOCATED_DPS = POINTS_ALLOCATED + ".dps";
    public static final String ALLOCATED_ARMOR = POINTS_ALLOCATED + ".armor";
    public static final String ALLOCATED_HEALTH = POINTS_ALLOCATED + ".health";
    public static final String ALLOCATED_HIT_CHANCE = POINTS_ALLOCATED + ".hit_chance";
    public static final String ALLOCATED_DODGE = POINTS_ALLOCATED + ".dodge";
    public static final String ALLOCATED_PARRY_CHANCE = POINTS_ALLOCATED + ".parry_chance";
    public static final String ALLOCATED_GLANCING_BLOW = POINTS_ALLOCATED + ".glancing_blow";
    public static final String ALLOCATED_BLOCK_CHANCE = POINTS_ALLOCATED + ".block_chance";
    public static final String ALLOCATED_BLOCK_VALUE = POINTS_ALLOCATED + ".block_value";
    public static final String ALLOCATED_EVASION = POINTS_ALLOCATED + ".evasion";
    public static final String ALLOCATED_EVASION_RATING = POINTS_ALLOCATED + ".evasion_rating";
    public static final String ALLOCATED_STRIKETHROUGH = POINTS_ALLOCATED + ".strikethrough";
    public static final String ALLOCATED_STRIKETHROUGH_RATING = POINTS_ALLOCATED + ".strikethrough_rating";
    public static final String ALLOCATED_CRITICAL_HIT = POINTS_ALLOCATED + ".critical_hit";
    public static final String ALLOCATED_AGGRESSION = POINTS_ALLOCATED + ".aggression";
    public static final String ALLOCATED_BEASTIAL_RESILIENCE = POINTS_ALLOCATED + ".beastialResilience";
    public static final String ALLOCATED_HUNTERS_INSTINCT = POINTS_ALLOCATED + ".huntersInstinct";
    public static final String ALLOCATED_INTELLIGENCE = POINTS_ALLOCATED + ".intelligence";
    public static final String ALLOCATED_SURVIVAL = POINTS_ALLOCATED + ".survival";
    public static final String ALLOCATED_CUNNING = POINTS_ALLOCATED + ".cunning";
    public static final String ALLOCATED_CREATURE_HUE_INDEX = "incubator.hueIndex";
    public static final String TEMPLATE_STORED = ".creature_template";
    public static final String STATION_QUALITY_OBJVAR = "crafting.stationMod_1";
    public static final String STATION_FUNCTIONALITY_OBJVAR = "crafting.stationMod";
    public static final String POINTS_SPENT_OBJVAR = ".points_spent";
    public static final String POINTS_SPENT_SLOT_1_QUALITY = POINTS_SPENT_OBJVAR + ".slotOneQuality";
    public static final String POINTS_SPENT_SLOT_3_QUALITY = POINTS_SPENT_OBJVAR + ".slotThreeQuality";
    public static final String POINTS_SPENT_SLOT_2_STAT = POINTS_SPENT_OBJVAR + ".slotTwoStat";
    public static final String POINTS_SPENT_RANDOM_STAT_COUNT = POINTS_SPENT_OBJVAR + ".slotTwoRandomStatCount";
    public static final String POINTS_SPENT_SLOT_4_MUTAGEN = POINTS_SPENT_OBJVAR + ".slotFourMutagenAmt";
    public static final String POINTS_SPENT_SLOT_4_PURITY = POINTS_SPENT_OBJVAR + ".slotFourPurity";
    public static final String POINTS_SPENT_SLOT_4_TRAIT = POINTS_SPENT_OBJVAR + ".slotFourTrait";
    public static final String POINTS_SPENT_INCREMENT_SURVIVAL = POINTS_SPENT_OBJVAR + ".survivalSkillIncrement";
    public static final String POINTS_SPENT_INCREMENT_BEASTIAL = POINTS_SPENT_OBJVAR + ".beastialSkillIncrement";
    public static final String POINTS_SPENT_INCREMENT_CUNNING = POINTS_SPENT_OBJVAR + ".cunningSkillIncrement";
    public static final String POINTS_SPENT_INCREMENT_INTELLIGENCE = POINTS_SPENT_OBJVAR + ".intelligenceSkillIncrement";
    public static final String POINTS_SPENT_INCREMENT_AGGRESSION = POINTS_SPENT_OBJVAR + ".aggressionSkillIncrement";
    public static final String POINTS_SPENT_INCREMENT_HUNTERS = POINTS_SPENT_OBJVAR + ".huntersSkillIncrement";
    public static final String POINTS_SPENT_POS_TEMP_SLIDER = POINTS_SPENT_OBJVAR + ".tempGaugeSliderPos";
    public static final String POINTS_SPENT_POS_NUTRIENT_SLIDER = POINTS_SPENT_OBJVAR + ".nutGaugeSliderPos";
    public static final String POINTS_SPENT_DPS = POINTS_SPENT_OBJVAR + ".dps";
    public static final String POINTS_SPENT_ARMOR = POINTS_SPENT_OBJVAR + ".armor";
    public static final String POINTS_SPENT_HEALTH = POINTS_SPENT_OBJVAR + ".health";
    public static final String POINTS_SPENT_HIT_CHANCE = POINTS_SPENT_OBJVAR + ".hit_chance";
    public static final String POINTS_SPENT_DODGE = POINTS_SPENT_OBJVAR + ".dodge";
    public static final String POINTS_SPENT_PARRY_CHANCE = POINTS_SPENT_OBJVAR + ".parry_chance";
    public static final String POINTS_SPENT_GLANCING_BLOW = POINTS_SPENT_OBJVAR + ".glancing_blow";
    public static final String POINTS_SPENT_BLOCK_CHANCE = POINTS_SPENT_OBJVAR + ".block_chance";
    public static final String POINTS_SPENT_BLOCK_VALUE = POINTS_SPENT_OBJVAR + ".block_value";
    public static final String POINTS_SPENT_CRITICAL_HIT = POINTS_SPENT_OBJVAR + ".critical_hit";
    public static final String POINTS_SPENT_EVASION = POINTS_SPENT_OBJVAR + ".evasion";
    public static final String POINTS_SPENT_EVASION_RATING = POINTS_SPENT_OBJVAR + ".evasion_rating";
    public static final String POINTS_SPENT_STRIKETHROUGH = POINTS_SPENT_OBJVAR + ".strikethrough";
    public static final String POINTS_SPENT_STRIKETHROUGH_RATING = POINTS_SPENT_OBJVAR + ".strikethrough_rating";
    public static final String COLORS_USED_OBJVAR = ".colors_used";
    public static final String COLORS_USED_SLOT_1 = COLORS_USED_OBJVAR + ".slotOneColor";
    public static final String COLORS_USED_SLOT_2 = COLORS_USED_OBJVAR + ".slotTwoColor";
    public static final String COLORS_USED_SLOT_3 = COLORS_USED_OBJVAR + ".slotThreeColor";
    public static final String STATION_DNA = BASE_INCUBATOR_OBJVAR + ".dna";
    public static final String STATION_DNA_ID = STATION_DNA + ".id";
    public static final String STATION_DNA_CREATURE = STATION_DNA + ".parent_creature_template";
    public static final String STATION_DNA_QUALITY = STATION_DNA + ".quality";
    public static final String STATION_DNA_CREATURE_TEMPLATE = STATION_DNA + ".creature_template";
    public static final String STATION_DNA_OLD_PET_IDENTIFIER = STATION_DNA + ".converted_pet";
    public static final String STATION_DNA_HUE_OLD_PET = STATION_DNA + ".hue";
    public static final String STATION_DNA_PARENT_NAME = STATION_DNA + ".name";
    public static final String STATION_STORED_CREATURE_TEMPLATE = BASE_INCUBATOR_OBJVAR + TEMPLATE_STORED;
    public static final String RESOURCE_POWER_NAME = BASE_INCUBATOR_POWER_OBJVAR + ".name";
    public static final String RESOURCE_POWER_QUALITY = BASE_INCUBATOR_POWER_OBJVAR + ".quality";
    public static final String RESOURCE_POWER_AMOUNT = BASE_INCUBATOR_POWER_OBJVAR + ".amount";
    public static final String PARTICLE_LABEL_DEFAULT = "default_bubbles";
    public static final String PARTICLE_LABEL_PHASE_ONE = "phase_1";
    public static final String PARTICLE_LABEL_PHASE_TWO = "phase_2";
    public static final String PARTICLE_LABEL_PHASE_THREE = "phase_3";
    public static final String PARTICLE_LABEL_PHASE_FOUR = "phase_4";
    public static final String PARTICLE_DEFAULT = "appearance/pt_incubator_bubbles_mid.prt";
    public static final String PARTICLE_PHASE_ONE = "appearance/pt_incubator_swirl_s01.prt";
    public static final String PARTICLE_PHASE_TWO = "appearance/pt_incubator_swirl_s02.prt";
    public static final String PARTICLE_PHASE_THREE = "appearance/pt_incubator_swirl_s03.prt";
    public static final String PARTICLE_PHASE_FOUR = "appearance/pt_incubator_egg.prt";
    public static final String PARTICLE_LABEL_SCRIPT_VAR = "particleLabel";
    public static final String PARTICLE_HARDPOINT_ONE = "hardpoint_1";
    public static final String PARTICLE_HARDPOINT_TWO = "hardpoint_2";
    public static final String PARTICLE_HARDPOINT_THREE = "hardpoint_3";
    public static final String COMM_APPEARANCE_ACTIVATE = "object/mobile/incubator_activation_comm_01.iff";
    public static final String COMM_APPEARANCE_VADER = "object/mobile/darth_vader.iff";
    public static final String COMM_SFX_ACTIVATE = "sound/voc_huttese_blurt_m_09.snd";
    public static final String COMM_SFX_MUTATE_TEMPLATE = "sound/voc_huttese_blurt_rnd_03_thru_05.snd";
    public static final String COMM_SFX_MUTATE_ATTRIB = "sound/voc_huttese_blurt_rnd_06_thru_08.snd";
    public static final String COMM_SFX_MUTATION_3_VADER = "sound/darth_1.snd";
    public static final String GUI_SCRIPT_VAR = "incubator.lastSessionTime";
    public static final String[] ENZYME_TYPES = 
    {
        "",
        "Quality",
        "Stat",
        "Skill"
    };
    public static final String[] STAT_LIST = 
    {
        "Hit Chance",
        "Dodge",
        "Parry Chance",
        "Glancing Blow",
        "Block Chance",
        "Block Value",
        "Critical Hit",
        "Evasion",
        "Evasion Rating",
        "Strikethrough",
        "Strikethrough Rating",
        "Health Bonus"
    };
    public static final String[] COMM_MUTATION_APP = 
    {
        "object/mobile/incubator_mutation_comm_01.iff",
        "object/mobile/incubator_mutation_comm_02.iff",
        "object/mobile/incubator_mutation_comm_03.iff"
    };
    public static final String[] ARRAY_SKILLS = 
    {
        ALLOCATED_AGGRESSION,
        ALLOCATED_BEASTIAL_RESILIENCE,
        ALLOCATED_HUNTERS_INSTINCT,
        ALLOCATED_INTELLIGENCE,
        ALLOCATED_SURVIVAL,
        ALLOCATED_CUNNING
    };
    public static final String[] SKILL_DISPLAY_NAMES = 
    {
        "aggression_skill",
        "beastial_resilience_skill",
        "hunters_instinct_skill",
        "intelligence_skill",
        "survival_skill",
        "cunning_skill"
    };
    public static final String[] ARRAY_ATTRIBUTES = 
    {
        ALLOCATED_DPS,
        ALLOCATED_ARMOR,
        ALLOCATED_HEALTH,
        ALLOCATED_HIT_CHANCE,
        ALLOCATED_DODGE,
        ALLOCATED_PARRY_CHANCE,
        ALLOCATED_GLANCING_BLOW,
        ALLOCATED_BLOCK_CHANCE,
        ALLOCATED_BLOCK_VALUE,
        ALLOCATED_CRITICAL_HIT,
        ALLOCATED_EVASION,
        ALLOCATED_EVASION_RATING,
        ALLOCATED_STRIKETHROUGH,
        ALLOCATED_STRIKETHROUGH_RATING
    };
    public static final String[] ATTRIBUTE_DISPLAY_NAMES = 
    {
        "dps_bonus",
        "armor_bonus",
        "health_bonus",
        "hit_chance_bonus",
        "dodge_bonus",
        "parry_bonus",
        "glancing_blow_bonus",
        "block_chance_bonus",
        "block_value_bonus",
        "critical_hit_bonus",
        "evasion_bonus",
        "evasion_rating_bonus",
        "strikethrough_bonus",
        "strikethrough_rating_bonus"
    };
    public static final String[] ENZYME_COLORS = 
    {
        "Yellow",
        "Orange",
        "Red",
        "Violet",
        "Purple",
        "Dark Blue",
        "Light Blue",
        "Teal",
        "Green",
        "Lime"
    };
    public static final String[] ENZYME_COLLECTION_NAMES = 
    {
        "col_yellow_enzyme",
        "col_orange_enzyme",
        "col_red_enzyme",
        "col_violet_enzyme",
        "col_purple_enzyme",
        "col_dark_blue_enzyme",
        "col_light_blue_enzyme",
        "col_teal_enzyme",
        "col_green_enzyme",
        "none"
    };
    public static final float[] ATTRIBUTE_DISPLAY_CONVERSION_RATES = 
    {
        0.1f,
        0.1f,
        0.2f,
        0.1f,
        0.1f,
        0.1f,
        0.1f,
        0.1f,
        1.0f,
        0.1f,
        0.1f,
        1.0f,
        0.1f,
        1.0f
    };
    public static final string_id SID_NOT_YOUR_INCUBATOR = new string_id("beast", "incubator_not_yours");
    public static final string_id SID_DNA_ALREADY_BEEN_SAMPLED = new string_id("incubator", "dna_already_sampled");
    public static final string_id SID_DNA_CREATURE_NOT_ON_LIST = new string_id("incubator", "dna_creature_not_on_list");
    public static final string_id SID_DNA_SAMPLED = new string_id("incubator", "dna_sampled");
    public static final string_id SID_DNA_FAILED_SAMPLE = new string_id("incubator", "dna_failed_get");
    public static final string_id SID_DNA_FAILED_SAMPLE_FULL_INVENTORY = new string_id("incubator", "dna_failed_full_inventory");
    public static final string_id SID_DNA_NO_ALLOW_COLLECT = new string_id("incubator", "dna_cannot_vehicle");
    public static final string_id SID_DNA_CANT_COLLECT_MOUNTED = new string_id("incubator", "dna_cannot_mounted");
    public static final string_id SID_DNA_CANT_COLLECT = new string_id("incubator", "dna_is_pet");
    public static final string_id SID_DNA_CANT_IN_COMBAT = new string_id("incubator", "dna_is_in_combat");
    public static final string_id SID_DNA_INVALID_TARGET = new string_id("incubator", "dna_invalid_target");
    public static final string_id RESOURCE_POWER_AMOUNT_FULL = new string_id("incubator", "full_station");
    public static final string_id RESOURCE_POWER_AMOUNT_MUST_EMPTY = new string_id("incubator", "must_empty");
    public static final string_id RESOURCE_TYPE_MISMATCH = new string_id("incubator", "type_mismatch");
    public static final string_id RESOURCE_POWER_INV_FULL = new string_id("incubator", "inventory_full");
    public static final string_id RESOURCE_POWER_NOT_ENOUGH = new string_id("incubator", "need_power");
    public static final string_id SID_REPORT_PET_NOT_CONVERTING = new string_id("incubator", "report_pet_conversion_fail");
    public static final string_id SID_COMM_ACTIVATION = new string_id("incubator", "comm_activate");
    public static final string_id SID_COMM_RDM_MUTATION_1 = new string_id("incubator", "comm_rdm_mutation_1");
    public static final string_id SID_COMM_RDM_MUTATION_2 = new string_id("incubator", "comm_rdm_mutation_2");
    public static final string_id SID_COMM_RDM_MUTATION_3 = new string_id("incubator", "comm_rdm_mutation_3");
    public static final string_id SID_COMM_VADER_MUTATION_3 = new string_id("incubator", "comm_vader_mutation_3");
    public static final string_id SID_COMM_ATTRIB_MUTATION_1 = new string_id("incubator", "comm_attrib_mutation_1");
    public static final string_id SID_COMM_ATTRIB_MUTATION_2 = new string_id("incubator", "comm_attrib_mutation_2");
    public static final string_id SID_COMM_ATTRIB_MUTATION_3 = new string_id("incubator", "comm_attrib_mutation_3");
    public static final string_id SID_COMM_MUTATION_COLOR_CORRECT = new string_id("incubator", "comm_mutation_color_correct");
    public static final string_id SID_ATTRIBUTE_MUTATION = new string_id("incubator", "sys_message_attribute_mutated");
    public static final string_id SID_CHEATER_DNA_CHANGED = new string_id("incubator", "cheater_dna_changed");
    public static final string_id[] COMM_MUTATED_TEMPLATE_TEXT = 
    {
        SID_COMM_RDM_MUTATION_1,
        SID_COMM_RDM_MUTATION_2,
        SID_COMM_RDM_MUTATION_3
    };
    public static final string_id[] COMM_MUTATED_ATTRIB_TEXT = 
    {
        SID_COMM_ATTRIB_MUTATION_1,
        SID_COMM_ATTRIB_MUTATION_2,
        SID_COMM_ATTRIB_MUTATION_3
    };
    public static final boolean LOGGING_ENABLED = true;
    public static int getIntEnzymeType(obj_id enzyme) throws InterruptedException
    {
        if (!hasObjVar(enzyme, ENZYME_TYPE_OBJVAR))
        {
            return 0;
        }
        return getIntObjVar(enzyme, ENZYME_TYPE_OBJVAR);
    }
    public static String getStringEnzymeType(obj_id enzyme) throws InterruptedException
    {
        if (!hasObjVar(enzyme, ENZYME_TYPE_OBJVAR))
        {
            return "";
        }
        int enzymeType = getIntObjVar(enzyme, ENZYME_TYPE_OBJVAR);
        return ENZYME_TYPES[enzymeType];
    }
    public static boolean isQualityEnzyme(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_QUALITY_OBJVAR) && exists(enzyme))
        {
            return true;
        }
        return false;
    }
    public static boolean isStatEnzyme(obj_id enzyme) throws InterruptedException
    {
        if (exists(enzyme))
        {
            if (hasObjVar(enzyme, ENZYME_RANDOM_STATS_OBJVAR) || hasObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isSkillEnzyme(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_PURITY_OBJVAR) && exists(enzyme))
        {
            return true;
        }
        return false;
    }
    public static int getEnzymeRandomStats(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_RANDOM_STATS_OBJVAR) && exists(enzyme))
        {
            return getIntObjVar(enzyme, ENZYME_RANDOM_STATS_OBJVAR);
        }
        else 
        {
            return 0;
        }
    }
    public static String getEnzymeFreeStat(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR) && exists(enzyme))
        {
            return getStringObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR);
        }
        else 
        {
            return "";
        }
    }
    public static float getEnzymeSkillPoints(obj_id enzyme) throws InterruptedException
    {
        return getFloatObjVar(enzyme, ENZYME_PURITY_OBJVAR);
    }
    public static float getEnzymeQuality(obj_id enzyme) throws InterruptedException
    {
        float baseQuality = 0.0f;
        if (hasObjVar(enzyme, ENZYME_QUALITY_OBJVAR))
        {
            baseQuality = getFloatObjVar(enzyme, ENZYME_QUALITY_OBJVAR);
        }
        return baseQuality;
    }
    public static int getEnzyemColor(obj_id enzyme) throws InterruptedException
    {
        return getIntObjVar(enzyme, ENZYME_COLOR_OBJVAR);
    }
    public static float getEnzyemMutagen(obj_id enzyme) throws InterruptedException
    {
        float mutagen = 0.0f;
        if (hasObjVar(enzyme, ENZYME_MUTAGEN_OBJVAR))
        {
            mutagen = getFloatObjVar(enzyme, ENZYME_MUTAGEN_OBJVAR);
        }
        return mutagen;
    }
    public static String getEnzyemTrait(obj_id enzyme) throws InterruptedException
    {
        String trait = "";
        if (hasObjVar(enzyme, ENZYME_TRAIT_OBJVAR))
        {
            trait = getStringObjVar(enzyme, ENZYME_TRAIT_OBJVAR);
        }
        return trait;
    }
    public static boolean hasEnzymeColor(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_COLOR_OBJVAR) && exists(enzyme))
        {
            return true;
        }
        return false;
    }
    public static boolean hasDnaQuality(obj_id dnaContainer) throws InterruptedException
    {
        if (hasObjVar(dnaContainer, DNA_QUALITY_OBJVAR) && exists(dnaContainer))
        {
            return true;
        }
        return false;
    }
    public static boolean hasDnaTemplate(obj_id dnaContainer) throws InterruptedException
    {
        if (hasObjVar(dnaContainer, DNA_TEMPLATE_OBJVAR) && exists(dnaContainer))
        {
            return true;
        }
        return false;
    }
    public static boolean hasDnaParentCreature(obj_id dnaContainer) throws InterruptedException
    {
        if (hasObjVar(dnaContainer, DNA_PARENT_TEMPLATE) && exists(dnaContainer))
        {
            return true;
        }
        return false;
    }
    public static float getDnaQuality(obj_id dnaContainer) throws InterruptedException
    {
        return getFloatObjVar(dnaContainer, DNA_QUALITY_OBJVAR);
    }
    public static int getDnaCreatureTemplate(obj_id dnaContainer) throws InterruptedException
    {
        return getIntObjVar(dnaContainer, DNA_TEMPLATE_OBJVAR);
    }
    public static String getDnaParentCreature(obj_id dnaContainer) throws InterruptedException
    {
        return getStringObjVar(dnaContainer, DNA_PARENT_TEMPLATE);
    }
    public static void setUpEnzymeWithDummyData(obj_id enzyme) throws InterruptedException
    {
        if (hasObjVar(enzyme, ENZYME_TYPE_OBJVAR))
        {
            int enzymeType = getIntEnzymeType(enzyme);
            int enzymeColor = 0;
            switch (enzymeType)
            {
                case 1:
                setObjVar(enzyme, ENZYME_QUALITY_OBJVAR, 90.0f);
                enzymeColor = rand(0, NUMBER_OF_COLORS);
                setObjVar(enzyme, ENZYME_COLOR_OBJVAR, enzymeColor);
                hue.setColor(enzyme, "/private/index_color_1", enzymeColor);
                break;
                case 2:
                initializeEnzymes(enzyme);
                break;
                case 3:
                blog("BEAST_ENZYME", "Skill Point Enzyme, we no longer do anything with these. They are setup elsewhere");
                break;
                default:
                blog("BEAST_ENZYME", "default case in switch statement. This shouldnt happen.");
                break;
            }
        }
    }
    public static void initializeEnzymes(obj_id enzyme) throws InterruptedException
    {
        int creatureLevel = getIntObjVar(enzyme, ENZYME_LEVEL_OBJVAR);
        blog("BEAST_ENZYME", "creatureLevel = " + creatureLevel);
        if (hasObjVar(enzyme, ENZYME_TYPE_OBJVAR))
        {
            int enzymeType = getIntEnzymeType(enzyme);
            switch (enzymeType)
            {
                case 1:
                float minRange = 0.0f;
                float maxRange = MIN_QUALITY_RANGE;
                boolean foundRange = false;
                for (int i = 10; i <= 75; i += 5)
                {
                    if (creatureLevel <= i)
                    {
                        minRange = creatureLevel;
                        maxRange = creatureLevel + 5;
                        foundRange = true;
                        break;
                    }
                }
                if (!foundRange)
                {
                    minRange = 80.0f;
                    maxRange = MAX_QUALITY_RANGE;
                    foundRange = true;
                }
                float quality = rand(minRange, maxRange);
                blog("BEAST_ENZYME", "quality = " + quality);
                setObjVar(enzyme, ENZYME_QUALITY_OBJVAR, quality);
                break;
                case 2:
                int randomChance = rand(1, 100);
                int statListMinusOne = STAT_LIST.length - 1;
                int maxRandomWithKnown = STAT_LIST.length - 3;
                if (randomChance <= CHANCE_FOR_ONE_KNOWN_SKILL)
                {
                    int statIndex = rand(0, statListMinusOne);
                    String freeStatName = STAT_LIST[statIndex];
                    int numRandomStats = rand(0, maxRandomWithKnown);
                    blog("BEAST_ENZYME", "numRandomStats = " + numRandomStats);
                    blog("BEAST_ENZYME", "freeStatName = " + freeStatName);
                    setObjVar(enzyme, ENZYME_RANDOM_STATS_OBJVAR, numRandomStats);
                    setObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR, freeStatName);
                }
                else 
                {
                    if (hasObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR))
                    {
                        removeObjVar(enzyme, ENZYME_FREE_STAT_OBJVAR);
                    }
                    int numRandomStats = rand(1, statListMinusOne);
                    blog("BEAST_ENZYME", "numRandomStats = " + numRandomStats);
                    setObjVar(enzyme, ENZYME_RANDOM_STATS_OBJVAR, numRandomStats);
                }
                break;
                case 3:
                blog("BEAST_ENZYME", "Skill Point Enzyme, we no longer do anything with these. They are setup elsewhere");
                default:
                blog("BEAST_ENZYME", "default case in switch statement. This shouldnt happen.");
                break;
            }
        }
        int enzymeColor = rand(0, NUMBER_OF_COLORS);
        setObjVar(enzyme, ENZYME_COLOR_OBJVAR, enzymeColor);
        hue.setColor(enzyme, "/private/index_color_1", enzymeColor);
    }
    public static boolean hasActiveUser(obj_id station) throws InterruptedException
    {
        if (!hasObjVar(station, ACTIVE_INCUBATOR))
        {
            return false;
        }
        return true;
    }
    public static obj_id getIncubatorActiveUser(obj_id station) throws InterruptedException
    {
        return (utils.getObjIdObjVar(station, ACTIVE_INCUBATOR, obj_id.NULL_ID));
    }
    public static boolean hasActiveIncubator(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, ACTIVE_INCUBATOR))
        {
            return false;
        }
        return true;
    }
    public static obj_id getActiveIncubator(obj_id player) throws InterruptedException
    {
        return (utils.getObjIdObjVar(player, ACTIVE_INCUBATOR, obj_id.NULL_ID));
    }
    public static boolean setActiveUser(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(station))
        {
            if (!hasActiveUser(station))
            {
                setObjVar(station, ACTIVE_INCUBATOR, player);
                setObjVar(player, ACTIVE_INCUBATOR, station);
                return true;
            }
        }
        return false;
    }
    public static boolean setNextSessionTime(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(station))
        {
            int currentTime = getGameTime();
            int expertiseModifier = (int)getSkillStatisticModifier(player, "expertise_bm_incubation_time");
            float stationFunctionality = getFloatObjVar(station, STATION_FUNCTIONALITY_OBJVAR);
            int modifiedTime = currentTime + NEXT_SESSION_TIME;
            if (expertiseModifier > 0)
            {
                modifiedTime -= expertiseModifier * 3600;
            }
            int skillModifier = (int)getSkillStatisticModifier(player, "incubation_time_reduction");
            if (skillModifier > 0)
            {
                modifiedTime -= skillModifier * 60;
            }
            int city_id = city.checkCity(player, false);
            if (city.cityHasSpec(city_id, city.SF_SPEC_INCUBATOR))
            {
                modifiedTime -= (3 * 60 * 60);
            }
            if (stationFunctionality != 0)
            {
                modifiedTime -= Math.round(stationFunctionality * 3600);
            }
            if (modifiedTime > currentTime + MAX_SESSION_TIME)
            {
                modifiedTime = currentTime + MAX_SESSION_TIME;
            }
            if (modifiedTime <= 0)
            {
                modifiedTime = 3600;
            }
            if (modifiedTime > 0)
            {
                setObjVar(player, NEXT_SESSION, modifiedTime);
                CustomerServiceLog("INCUBATOR: ", "Incubator Session Completed, next session can be performed in  " + ((modifiedTime - currentTime) / 3600) + " hours");
                return true;
            }
        }
        return false;
    }
    public static boolean isSessionEligible(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            int currentTime = getGameTime();
            int nextSessionTime = getIntObjVar(player, NEXT_SESSION);
            if (nextSessionTime >= 0)
            {
                if (currentTime >= nextSessionTime)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isIncubationComplete(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(station))
        {
            int sessionNumber = getIntObjVar(station, ACTIVE_SESSION);
            if (sessionNumber >= SESSIONS_TO_HATCH && isSessionEligible(player))
            {
                return true;
            }
        }
        return false;
    }
    public static obj_id extractDna(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            blog("BEAST_DNA", "invalid Ids");
            return obj_id.NULL_ID;
        }
        if (!beast_lib.isBeastMaster(player))
        {
            sendSystemMessage(player, beast_lib.SID_NOT_BEAST_MASTER);
            return obj_id.NULL_ID;
        }
        String targetAppearance = getAppearance(target);
        String finalTargetAppearance = targetAppearance;
        finalTargetAppearance = cleanAppearanceOfHueTag(targetAppearance);
        int row = dataTableSearchColumnForString(finalTargetAppearance, "start_appearance", DATATABLE_INCUBATOR_TEMPLATES);
        doAnimationAction(player, "heal_other");
        if (row < 0)
        {
            blog("BEAST_DNA", "invalid appearance");
            sendSystemMessage(player, SID_DNA_CREATURE_NOT_ON_LIST);
            return obj_id.NULL_ID;
        }
        if (hasObjVar(target, "no_dna_harvest"))
        {
            sendSystemMessage(player, SID_DNA_CREATURE_NOT_ON_LIST);
            blog("BEAST_DNA", "invalid creature - stopped by objvar 'no_dna_harvest");
            return obj_id.NULL_ID;
        }
        dictionary dict = dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row);
        int harvestChance = dict.getInt("harvestChance");
        if (harvestChance == 0)
        {
            blog("BEAST_DNA", "harvestChance = 0");
            sendSystemMessage(player, SID_DNA_CREATURE_NOT_ON_LIST);
            return obj_id.NULL_ID;
        }
        if (hasObjVar(target, "qa.dnaChance"))
        {
            harvestChance = getIntObjVar(target, "qa.dnaChance");
        }
        int chance = rand(1, 100);
        if (chance <= 50)
        {
            if (!isDead(target) || !isIncapacitated(target))
            {
                addHate(target, player, 50.0f);
            }
        }
        utils.setScriptVar(target, DNA_BEEN_SAMPLED_SCRIPTVAR, 1);
        chance = rand(1, 100);
        if (chance <= harvestChance)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (getVolumeFree(pInv) <= 0)
            {
                utils.removeScriptVar(target, DNA_BEEN_SAMPLED_SCRIPTVAR);
                sendSystemMessage(player, SID_DNA_FAILED_SAMPLE_FULL_INVENTORY);
                return obj_id.NULL_ID;
            }
            obj_id dnaContainer = createObject("object/tangible/loot/beast/dna_container.iff", pInv, "");
            if (!isIdValid(dnaContainer) || !exists(dnaContainer))
            {
                blog("BEAST_DNA", "invalid dnaContainer");
                utils.removeScriptVar(target, DNA_BEEN_SAMPLED_SCRIPTVAR);
                return obj_id.NULL_ID;
            }
            int template = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
            setObjVar(dnaContainer, DNA_TEMPLATE_OBJVAR, template);
            sendSystemMessage(player, SID_DNA_SAMPLED);
            return dnaContainer;
        }
        else 
        {
            sendSystemMessage(player, SID_DNA_FAILED_SAMPLE);
            return obj_id.NULL_ID;
        }
    }
    public static void setUpDnaWithDummyData(obj_id dnaContainer, obj_id player) throws InterruptedException
    {
        initializeDna(dnaContainer, player);
        setObjVar(dnaContainer, DNA_PARENT_TEMPLATE, "bull_rancor");
        String dummyTemplate = "object/mobile/beast_master/bm_rancor.iff";
        int row = dataTableSearchColumnForString(dummyTemplate, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
        int hashTemplate = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
        setObjVar(dnaContainer, DNA_TEMPLATE_OBJVAR, hashTemplate);
    }
    public static void initializeDna(obj_id dna, obj_id player) throws InterruptedException
    {
        if (hasObjVar(dna, DNA_QUALITY_OBJVAR))
        {
            float quality = getFloatObjVar(dna, DNA_QUALITY_OBJVAR);
            if (quality != 0)
            {
                return;
            }
        }
        if (!hasObjVar(dna, DNA_OLD_PET_IDENTIFIER) && !hasObjVar(dna, DNA_CS))
        {
            int expertiseQualityBonus = getEnhancedSkillStatisticModifierUncapped(player, "expertise_bm_dna_harvesting_1");
            blog("BEAST_DNA", "expertiseQualityBonus " + expertiseQualityBonus);
            float modifiedExpertiseBonus = (expertiseQualityBonus * .01f) * 50;
            blog("BEAST_DNA", "modifiedExpertiseBonus " + modifiedExpertiseBonus);
            float min_range = MIN_QUALITY_RANGE + modifiedExpertiseBonus;
            blog("BEAST_DNA", "min_range " + min_range);
            float quality = rand(min_range, MAX_QUALITY_RANGE);
            blog("BEAST_DNA", "quality " + quality);
            setObjVar(dna, DNA_QUALITY_OBJVAR, quality);
        }
        else 
        {
            setObjVar(dna, DNA_QUALITY_OBJVAR, MAX_QUALITY_RANGE);
        }
    }
    public static void clearMiniGameCooldown(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isGod(player))
        {
            utils.removeScriptVar(player, GUI_SCRIPT_VAR);
            setObjVar(player, NEXT_SESSION, getGameTime());
        }
    }
    public static boolean removeActiveUser(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(station))
        {
            obj_id stationOwner = getIncubatorActiveUser(station);
            if (validateActiveUser(station, player))
            {
                removeObjVar(player, ACTIVE_INCUBATOR);
                removeObjVar(station, ACTIVE_INCUBATOR);
                return true;
            }
        }
        return false;
    }
    public static void forceRemoveIncubatorFromUser(obj_id station, obj_id player) throws InterruptedException
    {
        if (station == getActiveIncubator(player))
        {
            removeObjVar(player, ACTIVE_INCUBATOR);
        }
    }
    public static boolean validateActiveUser(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(station))
        {
            obj_id stationOwner = getIncubatorActiveUser(station);
            obj_id currentStation = getActiveIncubator(player);
            if (!isIdNull(stationOwner) && !isIdNull(currentStation))
            {
                if ((stationOwner == player) && (currentStation == station))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static void checkIncubatorForMismatch(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(station))
        {
            obj_id stationOwner = getIncubatorActiveUser(station);
            obj_id currentStation = getActiveIncubator(player);
            if (!isIdNull(stationOwner) && !isIdNull(currentStation))
            {
                if ((stationOwner == player) && (currentStation != station))
                {
                    incubatorTotalCleanse(station);
                }
            }
        }
    }
    public static boolean incubatorTotalCleanse(obj_id station) throws InterruptedException
    {
        if (isIdValid(station))
        {
            removeObjVar(station, BASE_INCUBATOR_OBJVAR);
            obj_id[] stationContents = utils.getContents(station, false);
            if (stationContents != null && stationContents.length > 0)
            {
                destroyObject(stationContents[0]);
            }
            if (utils.isInHouseCellSpace(station))
            {
                messageTo(station, "refreshCurrentParticle", null, 0, false);
            }
            return true;
        }
        return false;
    }
    public static boolean isSessionActive(obj_id station) throws InterruptedException
    {
        if (isIdValid(station))
        {
            int sessionNumber = getIntObjVar(station, SESSION_NUMBER);
            if (sessionNumber > 0)
            {
                return true;
            }
        }
        return false;
    }
    public static int getCurrentSessionNumber(obj_id station) throws InterruptedException
    {
        if (isIdValid(station))
        {
            int sessionNumber = getIntObjVar(station, ACTIVE_SESSION);
            if (sessionNumber > 0 && sessionNumber < 10)
            {
                return sessionNumber;
            }
        }
        return 1;
    }
    public static int getIntBonusLastSession(obj_id station, int sessionNumber, String bonusItem) throws InterruptedException
    {
        String objVarNeeded = POINTS_ALLOCATED + "." + bonusItem;
        blog("INCUBATOR", "objVarNeededs = " + objVarNeeded);
        if (hasObjVar(station, objVarNeeded))
        {
            return getIntObjVar(station, objVarNeeded);
        }
        else 
        {
            return 0;
        }
    }
    public static float getFloatBonusLastSession(obj_id station, int sessionNumber, String bonusItem) throws InterruptedException
    {
        String objVarNeeded = POINTS_ALLOCATED + "." + bonusItem;
        blog("INCUBATOR", "objVarNeededs = " + objVarNeeded);
        if (hasObjVar(station, objVarNeeded))
        {
            return getFloatObjVar(station, objVarNeeded);
        }
        else 
        {
            return 0.0f;
        }
    }
    public static String getTemplateLastSession(obj_id station, int sessionNumber) throws InterruptedException
    {
        String objVarNeeded = SESSION_NUMBER + sessionNumber + TEMPLATE_STORED;
        if (hasObjVar(station, objVarNeeded))
        {
            return getStringObjVar(station, objVarNeeded);
        }
        else 
        {
            return "";
        }
    }
    public static int getIncubatorCreatureTemplate(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, STATION_STORED_CREATURE_TEMPLATE))
        {
            return getIntObjVar(station, STATION_STORED_CREATURE_TEMPLATE);
        }
        return 0;
    }
    public static boolean getMutationChance(String template, int slotOneColor, int slotTwoColor, int slotThreeColor, obj_id station, obj_id player) throws InterruptedException
    {
        if (isConvertedPet(station))
        {
            return false;
        }
        int randChance = rand(1, 200);
        if (randChance == 1)
        {
            return true;
        }
        int row = dataTableSearchColumnForString(template, "base_template", DATATABLE_TEMPLATE_MUTATION_COLORS);
        if (row < 0)
        {
            blog("INCUBATOR", "There is no mutation chance for template " + template);
            blog("INCUBATOR", "This is because it is either the last mutation for its base or it is missing from the table");
            return false;
        }
        dictionary dict = dataTableGetRow(DATATABLE_TEMPLATE_MUTATION_COLORS, row);
        int numRight = 3;
        if (slotOneColor != dict.getInt("color_1"))
        {
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "slotOneColor is wrong.");
                sendSystemMessageTestingOnly(player, "slotOneColor needed to be " + ENZYME_COLORS[dict.getInt("color_1")]);
                sendSystemMessageTestingOnly(player, "slotOneColor used was " + ENZYME_COLORS[slotOneColor]);
            }
            --numRight;
        }
        if (slotTwoColor != dict.getInt("color_2"))
        {
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "slotTwoColor is wrong.");
                sendSystemMessageTestingOnly(player, "slotTwoColor needed to be " + ENZYME_COLORS[dict.getInt("color_2")]);
                sendSystemMessageTestingOnly(player, "slotTwoColor used was " + ENZYME_COLORS[slotTwoColor]);
            }
            --numRight;
        }
        else 
        {
            int index = rand(0, 2);
            npe.commTutorialPlayer(station, player, 12.0f, SID_COMM_MUTATION_COLOR_CORRECT, "", COMM_MUTATION_APP[index]);
        }
        if (slotThreeColor != dict.getInt("color_3"))
        {
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "slotThreeColor is wrong.");
                sendSystemMessageTestingOnly(player, "slotThreeColor needed to be " + ENZYME_COLORS[dict.getInt("color_3")]);
                sendSystemMessageTestingOnly(player, "slotThreeColor used was " + ENZYME_COLORS[slotThreeColor]);
            }
            --numRight;
        }
        if (numRight == 3)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static float getIncubatorQuality(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, STATION_QUALITY_OBJVAR))
        {
            float stationQuality = getFloatObjVar(station, STATION_QUALITY_OBJVAR);
            if (stationQuality > STATION_QUALITY_MAX)
            {
                setObjVar(station, STATION_QUALITY_OBJVAR, STATION_QUALITY_MAX);
                stationQuality = STATION_QUALITY_MAX;
            }
            if (stationQuality < STATION_QUALITY_MIN)
            {
                setObjVar(station, STATION_QUALITY_OBJVAR, STATION_QUALITY_MIN);
                stationQuality = STATION_QUALITY_MIN;
            }
            return stationQuality;
        }
        else 
        {
            setObjVar(station, STATION_QUALITY_OBJVAR, STATION_QUALITY_MIN);
            return STATION_QUALITY_MIN;
        }
    }
    public static float getIncubatorFunctionality(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, STATION_FUNCTIONALITY_OBJVAR))
        {
            float stationFunctionality = getFloatObjVar(station, STATION_FUNCTIONALITY_OBJVAR);
            if (stationFunctionality > STATION_FUNCTIONALITY_MAX)
            {
                setObjVar(station, STATION_FUNCTIONALITY_OBJVAR, STATION_FUNCTIONALITY_MAX);
                stationFunctionality = STATION_FUNCTIONALITY_MAX;
            }
            if (stationFunctionality < STATION_FUNCTIONALITY_MIN)
            {
                setObjVar(station, STATION_FUNCTIONALITY_OBJVAR, STATION_FUNCTIONALITY_MIN);
                stationFunctionality = STATION_FUNCTIONALITY_MIN;
            }
            return stationFunctionality;
        }
        else 
        {
            setObjVar(station, STATION_FUNCTIONALITY_OBJVAR, STATION_FUNCTIONALITY_MIN);
            return STATION_FUNCTIONALITY_MIN;
        }
    }
    public static int getLastSessionMutated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, LAST_MUTATION))
        {
            return getIntObjVar(station, LAST_MUTATION);
        }
        else 
        {
            return 0;
        }
    }
    public static String getMutatedTemplate(String template, int session, obj_id station, obj_id player) throws InterruptedException
    {
        blog("INCUBATOR", "session(" + session + ") getMutatedTemplate::template " + template);
        int row = -1;
        int lastMutationSession = getLastSessionMutated(station);
        if (lastMutationSession > MAX_MUTATIONS)
        {
            return template;
        }
        blog("INCUBATOR", "session(" + session + ") lastMutationSession " + lastMutationSession);
        if (lastMutationSession == 0)
        {
            row = dataTableSearchColumnForString(template, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
            blog("INCUBATOR", "session(" + session + ") initial_template: row " + row);
        }
        else 
        {
            row = dataTableSearchColumnForString(template, "mutated_template_" + lastMutationSession, DATATABLE_INCUBATOR_TEMPLATES);
            blog("INCUBATOR", "session(" + session + ") mutated_template_" + lastMutationSession + ": row " + row);
        }
        if (row < 0)
        {
            blog("INCUBATOR", "someone tried to mutate " + template + ", and it wasnt in the table");
            return template;
        }
        dictionary dict = dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row);
        String newTemplate = dict.getString("mutated_template_" + (lastMutationSession + 1));
        blog("INCUBATOR", "mutated_template_+(lastMutationSession + 1) mutated_template_" + (lastMutationSession + 1));
        if (newTemplate == null || newTemplate.equals(""))
        {
            blog("INCUBATOR", "No Mutated Appearance for this Creature");
            int index = rand(0, 2);
            npe.commTutorialPlayer(station, player, 12.0f, COMM_MUTATED_ATTRIB_TEXT[index], COMM_SFX_MUTATE_ATTRIB, COMM_MUTATION_APP[index]);
            playClientEffectObj(station, "clienteffect/incubator_mutation.cef", station, PARTICLE_HARDPOINT_THREE, null, "");
            return template;
        }
        else 
        {
            blog("INCUBATOR", "This creature has a mutation");
            setObjVar(station, LAST_MUTATION, (lastMutationSession + 1));
            if ((lastMutationSession + 1) >= 3)
            {
                npe.commTutorialPlayer(station, player, 12.0f, SID_COMM_VADER_MUTATION_3, COMM_SFX_MUTATION_3_VADER, COMM_APPEARANCE_VADER);
            }
            else 
            {
                int index = rand(0, 2);
                if (template.equals(newTemplate))
                {
                    npe.commTutorialPlayer(station, player, 12.0f, COMM_MUTATED_ATTRIB_TEXT[index], COMM_SFX_MUTATE_ATTRIB, COMM_MUTATION_APP[index]);
                }
                else 
                {
                    npe.commTutorialPlayer(station, player, 12.0f, COMM_MUTATED_TEMPLATE_TEXT[index], COMM_SFX_MUTATE_TEMPLATE, COMM_MUTATION_APP[index]);
                }
            }
            playClientEffectObj(station, "clienteffect/incubator_mutation.cef", station, PARTICLE_HARDPOINT_THREE, null, "");
            return dict.getString("mutated_template_" + (lastMutationSession + 1));
        }
    }
    public static boolean updateSessionVariables(obj_id station, dictionary values) throws InterruptedException
    {
        if (values == null || values.isEmpty())
        {
            blog("INCUBATOR", "Empty dictionary sent in function updateSessionVariables");
            return false;
        }
        float dpsBonus = values.getFloat("dpsBonus");
        float armorBonus = values.getFloat("armorBonus");
        float healthBonus = values.getFloat("healthBonus");
        float hitChanceBonus = values.getFloat("hitChanceBonus");
        float dodgeBonus = values.getFloat("dodgeBonus");
        float glancingBlowBonus = values.getFloat("glancingBlowBonus");
        float parryBonus = values.getFloat("parryBonus");
        float blockChanceBonus = values.getFloat("blockChanceBonus");
        float blockValueBonus = values.getFloat("blockValueBonus");
        float criticalHitBonus = values.getFloat("criticalHitBonus");
        float evasionBonus = values.getFloat("evasionBonus");
        float evasionRatingBonus = values.getFloat("evasionRatingBonus");
        float strikethroughBonus = values.getFloat("strikethroughBonus");
        float strikethroughRatingBonus = values.getFloat("strikethroughRatingBonus");
        int survivalUpdate = values.getInt("survivalUpdate");
        int beastialResilienceUpdate = values.getInt("beastialResilienceUpdate");
        int cunningUpdate = values.getInt("cunningUpdate");
        int intelligenceUpdate = values.getInt("intelligenceUpdate");
        int aggressionUpdate = values.getInt("aggressionUpdate");
        int huntersInstinctUpdate = values.getInt("huntersInstinctUpdate");
        int newTemplate = values.getInt("newTemplate");
        int session = values.getInt("sessionNumber");
        int creatureHueIndex = values.getInt("creatureHueIndex");
        String baseObjVarString = SESSION_NUMBER + "." + session;
        String builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_1_QUALITY;
        setObjVar(station, builtObjVarString, values.getFloat("slotOneQuality"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_3_QUALITY;
        setObjVar(station, builtObjVarString, values.getFloat("slotThreeQuality"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_4_MUTAGEN;
        setObjVar(station, builtObjVarString, values.getFloat("slotFourMutagen"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_4_PURITY;
        setObjVar(station, builtObjVarString, values.getFloat("slotFourPurity"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_4_TRAIT;
        setObjVar(station, builtObjVarString, values.getString("slotFourTrait"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_RANDOM_STAT_COUNT;
        setObjVar(station, builtObjVarString, values.getInt("slotTwoRandomStatCount"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_SURVIVAL;
        setObjVar(station, builtObjVarString, values.getInt("survivalSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_BEASTIAL;
        setObjVar(station, builtObjVarString, values.getInt("beastialSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_CUNNING;
        setObjVar(station, builtObjVarString, values.getInt("cunningSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_INTELLIGENCE;
        setObjVar(station, builtObjVarString, values.getInt("intelligenceSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_AGGRESSION;
        setObjVar(station, builtObjVarString, values.getInt("aggressionSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_INCREMENT_HUNTERS;
        setObjVar(station, builtObjVarString, values.getInt("huntersSkillIncrement"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_POS_TEMP_SLIDER;
        setObjVar(station, builtObjVarString, values.getInt("tempGaugeSliderPos"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_POS_NUTRIENT_SLIDER;
        setObjVar(station, builtObjVarString, values.getInt("nutGaugeSliderPos"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_SLOT_2_STAT;
        setObjVar(station, builtObjVarString, values.getString("slotTwoStat"));
        builtObjVarString = baseObjVarString + COLORS_USED_SLOT_1;
        setObjVar(station, builtObjVarString, values.getInt("slotOneColor"));
        builtObjVarString = baseObjVarString + COLORS_USED_SLOT_2;
        setObjVar(station, builtObjVarString, values.getInt("slotTwoColor"));
        builtObjVarString = baseObjVarString + COLORS_USED_SLOT_3;
        setObjVar(station, builtObjVarString, values.getInt("slotThreeColor"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_DPS;
        setObjVar(station, builtObjVarString, values.getFloat("pointsTowardDps"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_ARMOR;
        setObjVar(station, builtObjVarString, values.getFloat("pointsTowardArmor"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_HIT_CHANCE;
        setObjVar(station, builtObjVarString, values.getFloat("hitChanceUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_DODGE;
        setObjVar(station, builtObjVarString, values.getFloat("dodgeUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_PARRY_CHANCE;
        setObjVar(station, builtObjVarString, values.getFloat("parryChanceUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_GLANCING_BLOW;
        setObjVar(station, builtObjVarString, values.getFloat("glancingBlowUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_BLOCK_CHANCE;
        setObjVar(station, builtObjVarString, values.getFloat("blockChanceUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_BLOCK_VALUE;
        setObjVar(station, builtObjVarString, values.getFloat("blockValueUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_CRITICAL_HIT;
        setObjVar(station, builtObjVarString, values.getFloat("criticalHitUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_EVASION;
        setObjVar(station, builtObjVarString, values.getFloat("evasionUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_EVASION_RATING;
        setObjVar(station, builtObjVarString, values.getFloat("evasionRatingUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_STRIKETHROUGH;
        setObjVar(station, builtObjVarString, values.getFloat("strikethroughUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_STRIKETHROUGH_RATING;
        setObjVar(station, builtObjVarString, values.getFloat("strikethroughRatingUpdate"));
        builtObjVarString = baseObjVarString + POINTS_SPENT_HEALTH;
        setObjVar(station, builtObjVarString, values.getFloat("healthUpdate"));
        if (dpsBonus > MAX_TOTAL_POINTS_DPS_ARMOR)
        {
            dpsBonus = MAX_TOTAL_POINTS_DPS_ARMOR;
        }
        setObjVar(station, ALLOCATED_DPS, dpsBonus);
        if (armorBonus > MAX_TOTAL_POINTS_DPS_ARMOR)
        {
            armorBonus = MAX_TOTAL_POINTS_DPS_ARMOR;
        }
        setObjVar(station, ALLOCATED_ARMOR, armorBonus);
        if (hitChanceBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            hitChanceBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_HIT_CHANCE, hitChanceBonus);
        if (dodgeBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            dodgeBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_DODGE, dodgeBonus);
        if (glancingBlowBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            glancingBlowBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_GLANCING_BLOW, glancingBlowBonus);
        if (parryBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            parryBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_PARRY_CHANCE, parryBonus);
        if (blockChanceBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            blockChanceBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_BLOCK_CHANCE, blockChanceBonus);
        if (blockValueBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            blockValueBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_BLOCK_VALUE, blockValueBonus);
        if (criticalHitBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            criticalHitBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_CRITICAL_HIT, criticalHitBonus);
        if (evasionBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            evasionBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_EVASION, evasionBonus);
        if (evasionRatingBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            evasionRatingBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_EVASION_RATING, evasionRatingBonus);
        if (strikethroughBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            strikethroughBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_STRIKETHROUGH, strikethroughBonus);
        if (strikethroughRatingBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            strikethroughRatingBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_STRIKETHROUGH_RATING, strikethroughRatingBonus);
        if (healthBonus > MAX_TOTAL_POINTS_ATTRIBUTES)
        {
            healthBonus = MAX_TOTAL_POINTS_ATTRIBUTES;
        }
        setObjVar(station, ALLOCATED_HEALTH, healthBonus);
        if (survivalUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            survivalUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_SURVIVAL, survivalUpdate);
        if (beastialResilienceUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            beastialResilienceUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_BEASTIAL_RESILIENCE, beastialResilienceUpdate);
        if (cunningUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            cunningUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_CUNNING, cunningUpdate);
        if (intelligenceUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            intelligenceUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_INTELLIGENCE, intelligenceUpdate);
        if (aggressionUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            aggressionUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_AGGRESSION, aggressionUpdate);
        if (huntersInstinctUpdate > MAX_TOTAL_SKILL_INCREMENT)
        {
            huntersInstinctUpdate = MAX_TOTAL_SKILL_INCREMENT;
        }
        setObjVar(station, ALLOCATED_HUNTERS_INSTINCT, huntersInstinctUpdate);
        builtObjVarString = baseObjVarString + TEMPLATE_STORED;
        setObjVar(station, builtObjVarString, newTemplate);
        setObjVar(station, ALLOCATED_CREATURE_HUE_INDEX, creatureHueIndex);
        setObjVar(station, ACTIVE_SESSION, (session + 1));
        setObjVar(station, STATION_STORED_CREATURE_TEMPLATE, newTemplate);
        return true;
    }
    public static obj_id getStationDnaInInventory(obj_id station) throws InterruptedException
    {
        obj_id[] stationContents = utils.getContents(station, false);
        if (stationContents.length > 1 || stationContents.length < 1)
        {
            return obj_id.NULL_ID;
        }
        return stationContents[0];
    }
    public static boolean stationHasDnaInInventory(obj_id station) throws InterruptedException
    {
        obj_id dna = getStationDnaInInventory(station);
        if (isIdNull(dna))
        {
            return false;
        }
        return true;
    }
    public static boolean canOpenInventory(obj_id station) throws InterruptedException
    {
        boolean ready = false;
        if (hasObjVar(station, ACTIVE_SESSION))
        {
            int sessionNumber = getIntObjVar(station, ACTIVE_SESSION);
            if (sessionNumber < 1 || isReadyToRetrieveEgg(station))
            {
                ready = true;
            }
        }
        else 
        {
            if (hasActiveUser(station))
            {
                ready = true;
            }
        }
        return ready;
    }
    public static boolean isReadyToRetrieveEgg(obj_id station) throws InterruptedException
    {
        if (getCurrentSessionNumber(station) == 5)
        {
            return true;
        }
        return false;
    }
    public static boolean stationHasDnaStored(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, STATION_DNA))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean startSession(obj_id station, obj_id player) throws InterruptedException
    {
        if (!validateActiveUser(station, player))
        {
            sendSystemMessage(player, SID_NOT_YOUR_INCUBATOR);
            return false;
        }
        if (!beast_lib.isBeastMaster(player))
        {
            sendSystemMessage(player, beast_lib.SID_NOT_BEAST_MASTER);
            return false;
        }
        if (!hasPowerForSession(station))
        {
            sendSystemMessage(player, RESOURCE_POWER_NOT_ENOUGH);
            return false;
        }
        int session = getCurrentSessionNumber(station);
        if (isIncubationComplete(station, player))
        {
            return false;
        }
        obj_id dnaCore = getStationDnaInInventory(station);
        if (isIdNull(dnaCore))
        {
            return false;
        }
        if (session <= 1)
        {
            String dnaParentTemplate = getStringObjVar(dnaCore, DNA_PARENT_TEMPLATE);
            int dnaCreatureTemplate = getDnaCreatureTemplate(dnaCore);
            float dnaQuality = getDnaQuality(dnaCore);
            if (isConvertedPet(dnaCore))
            {
                int oldHue = getIntObjVar(dnaCore, DNA_HUE_OLD_PET);
                String oldName = getStringObjVar(dnaCore, DNA_PARENT_NAME);
                setObjVar(station, STATION_DNA_HUE_OLD_PET, oldHue);
                setObjVar(station, STATION_DNA_OLD_PET_IDENTIFIER, 1);
                setObjVar(station, STATION_DNA_PARENT_NAME, oldName);
            }
            setObjVar(station, STATION_DNA_CREATURE, dnaParentTemplate);
            setObjVar(station, STATION_DNA_QUALITY, dnaQuality);
            setObjVar(station, STATION_DNA_CREATURE_TEMPLATE, dnaCreatureTemplate);
            setObjVar(station, STATION_DNA_ID, dnaCore);
        }
        obj_id stationsDna = getObjIdObjVar(station, STATION_DNA_ID);
        if (exists(stationsDna) && isIdValid(stationsDna))
        {
            if (stationsDna != dnaCore)
            {
                prose_package pp = new prose_package();
                String playerName = getPlayerName(player);
                pp = prose.setTT(pp, playerName);
                pp = prose.setStringId(pp, SID_CHEATER_DNA_CHANGED);
                CustomerServiceLog("SuspectedCheaterChannel: ", "Player '" + playerName + "'(" + player + ") has DNA in their incubator that doesnt match the DNA stamped on incubator from first session. Exiting session.");
                sendSystemMessageProse(player, pp);
                return false;
            }
        }
        else 
        {
            prose_package pp = new prose_package();
            String playerName = getPlayerName(player);
            pp = prose.setTT(pp, playerName);
            pp = prose.setStringId(pp, SID_CHEATER_DNA_CHANGED);
            CustomerServiceLog("SuspectedCheaterChannel: ", "Player '" + playerName + "'(" + player + ") has DNA in their incubator that doesnt match the DNA stamped on incubator from first session. Exiting session.");
            sendSystemMessageProse(player, pp);
            return false;
        }
        int initialPointsSurvival = getSurvivalPointsAllocated(station);
        int initialPointsBeastialResilience = getBeastialResiliencePointsAllocated(station);
        int initialPointsCunning = getCunningPointsAllocated(station);
        int initialPointsIntelligence = getIntelligencePointsAllocated(station);
        int initialPointsAggression = getAggressionPointsAllocated(station);
        int initialPointsHuntersInstinct = getHuntersInstinctPointsAllocated(station);
        int stationPowerAmount = getStationPowerAmount(station);
        int tempGaugeSliderPos = getTemperatureGaugePosition(station, session);
        int nutGaugeSliderPos = getNutrientGaugePosition(station, session);
        int initialCreatureColorIndex = getCreatureHueIndex(station, session);
        String template = "";
        int hashTemplate = 0;
        if (hasObjVar(station, STATION_DNA_CREATURE_TEMPLATE) && session <= 1)
        {
            blog("INCUBATOR", "startSession hasObjVar(station, STATION_DNA_CREATURE_TEMPLATE) && session <= 1");
            hashTemplate = getIntObjVar(station, STATION_DNA_CREATURE_TEMPLATE);
            blog("INCUBATOR", "startSession hashTemplate " + hashTemplate);
        }
        else 
        {
            if (session > 1)
            {
                blog("INCUBATOR", "startSession session > 1");
                hashTemplate = getIncubatorCreatureTemplate(station);
                blog("INCUBATOR", "startSession hashTemplate " + hashTemplate);
            }
        }
        template = convertHashTemplateToString(hashTemplate, station);
        blog("INCUBATOR", "template " + template);
        int powerPercentage = Math.round(((float)stationPowerAmount / (float)RESOURCE_POWER_AMOUNT_CAP) * MAX_GUI_POWER);
        blog("INCUBATOR", "powerPercentage = (" + stationPowerAmount + " / " + RESOURCE_POWER_AMOUNT_CAP + ") * " + MAX_GUI_POWER);
        blog("INCUBATOR", "powerPercentage " + powerPercentage);
        incubatorStart(session, player, station, powerPercentage, initialPointsSurvival, initialPointsBeastialResilience, initialPointsCunning, initialPointsIntelligence, initialPointsAggression, initialPointsHuntersInstinct, tempGaugeSliderPos, nutGaugeSliderPos, initialCreatureColorIndex, template);
        return true;
    }
    public static int getTemperatureGaugePosition(obj_id station, int session) throws InterruptedException
    {
        String builtObjVarString = SESSION_NUMBER + "." + session + POINTS_SPENT_POS_TEMP_SLIDER;
        if (hasObjVar(station, builtObjVarString))
        {
            return getIntObjVar(station, builtObjVarString);
        }
        return 5;
    }
    public static int getNutrientGaugePosition(obj_id station, int session) throws InterruptedException
    {
        String builtObjVarString = SESSION_NUMBER + "." + session + POINTS_SPENT_POS_NUTRIENT_SLIDER;
        if (hasObjVar(station, builtObjVarString))
        {
            return getIntObjVar(station, builtObjVarString);
        }
        return 5;
    }
    public static int getCreatureHueIndex(obj_id station, int session) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_CREATURE_HUE_INDEX))
        {
            return getIntObjVar(station, ALLOCATED_CREATURE_HUE_INDEX);
        }
        return 0;
    }
    public static int getAggressionPointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_AGGRESSION))
        {
            return getIntObjVar(station, ALLOCATED_AGGRESSION);
        }
        return 0;
    }
    public static int getBeastialResiliencePointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_BEASTIAL_RESILIENCE))
        {
            return getIntObjVar(station, ALLOCATED_BEASTIAL_RESILIENCE);
        }
        return 0;
    }
    public static int getHuntersInstinctPointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_HUNTERS_INSTINCT))
        {
            return getIntObjVar(station, ALLOCATED_HUNTERS_INSTINCT);
        }
        return 0;
    }
    public static int getIntelligencePointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_INTELLIGENCE))
        {
            return getIntObjVar(station, ALLOCATED_INTELLIGENCE);
        }
        return 0;
    }
    public static int getSurvivalPointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_SURVIVAL))
        {
            return getIntObjVar(station, ALLOCATED_SURVIVAL);
        }
        return 0;
    }
    public static int getCunningPointsAllocated(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, ALLOCATED_CUNNING))
        {
            return getIntObjVar(station, ALLOCATED_CUNNING);
        }
        return 0;
    }
    public static obj_id convertDnaToEgg(obj_id station, obj_id player) throws InterruptedException
    {
        if (!validateActiveUser(station, player))
        {
            sendSystemMessage(player, SID_NOT_YOUR_INCUBATOR);
            return obj_id.NULL_ID;
        }
        if (!beast_lib.isBeastMaster(player))
        {
            sendSystemMessage(player, beast_lib.SID_NOT_BEAST_MASTER);
            return obj_id.NULL_ID;
        }
        obj_id[] stationContents = utils.getContents(station, false);
        if (stationContents.length > 1)
        {
            return obj_id.NULL_ID;
        }
        obj_id dnaCore = stationContents[0];
        obj_id stationsDna = getObjIdObjVar(station, STATION_DNA_ID);
        if (exists(stationsDna) && isIdValid(stationsDna))
        {
            if (stationsDna != dnaCore)
            {
                prose_package pp = new prose_package();
                String playerName = getPlayerName(player);
                pp = prose.setTT(pp, playerName);
                pp = prose.setStringId(pp, SID_CHEATER_DNA_CHANGED);
                CustomerServiceLog("SuspectedCheaterChannel: ", "Player '" + playerName + "'(" + player + ") has DNA in their incubator that doesnt match the DNA stamped on incubator from first session. Exiting session.");
                sendSystemMessageProse(player, pp);
                return obj_id.NULL_ID;
            }
        }
        else 
        {
            prose_package pp = new prose_package();
            String playerName = getPlayerName(player);
            pp = prose.setTT(pp, playerName);
            pp = prose.setStringId(pp, SID_CHEATER_DNA_CHANGED);
            CustomerServiceLog("SuspectedCheaterChannel: ", "Player '" + playerName + "'(" + player + ") has DNA in their incubator that doesnt match the DNA stamped on incubator from first session. Exiting session.");
            sendSystemMessageProse(player, pp);
            return obj_id.NULL_ID;
        }
        boolean dnaDestroyed = destroyObject(dnaCore);
        if (dnaDestroyed)
        {
            obj_id egg = createObject("object/tangible/item/beast/bm_egg.iff", station, "");
            if (!isIdValid(egg) || isIdNull(egg))
            {
                return obj_id.NULL_ID;
            }
            setObjVar(station, ACTIVE_SESSION, 5);
            messageTo(station, "refreshCurrentParticle", null, 1, false);
            CustomerServiceLog("BeastEggCreated: ", "Player " + getPlayerName(player) + "(" + player + ") has finished incubation and created an egg (" + egg + ").");
            return egg;
        }
        else 
        {
            return obj_id.NULL_ID;
        }
    }
    public static void setUpEggWithDummyData(obj_id player, obj_id egg, int beastType) throws InterruptedException
    {
        setObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE, beastType);
        setObjVar(egg, beast_lib.OBJVAR_SKILL_AGGRESSION, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_SKILL_BEASTIAL_RESILIENCE, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_SKILL_CUNNING, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_SKILL_HUNTERS_INSTINCT, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_SKILL_INTELLIGENCE, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_SKILL_SURVIVAL, rand(0, MAX_TOTAL_SKILL_INCREMENT));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_ARMOR, rand(0, MAX_TOTAL_POINTS_DPS_ARMOR));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_DPS, rand(0, MAX_TOTAL_POINTS_DPS_ARMOR));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_HEALTH, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_HIT_CHANCE, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_DODGE, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_PARRY, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_GLANCING_BLOW, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_BLOCK_CHANCE, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_BLOCK_VALUE, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_CRITICAL_HIT, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_EVASION, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_EVASION_RATING, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_STRIKETHROUGH, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
        setObjVar(egg, beast_lib.OBJVAR_INCREASE_STRIKETHROUGH_RATING, rand(0, MAX_TOTAL_POINTS_ATTRIBUTES));
    }
    public static void initializeEgg(obj_id egg) throws InterruptedException
    {
        obj_id station = getContainedBy(egg);
        if (isIdNull(station))
        {
            return;
        }
        String template = getTemplateName(station);
        blog("INCUBATOR", "template = " + template);
        if (template.equals("object/tangible/crafting/station/incubator_station.iff"))
        {
            String creatureName = getCreatureTypeFromHashTemplate(station);
            blog("INCUBATOR", "creatureName = " + creatureName);
            int hashCreatureType = getHashType(creatureName);
            int dpsIncrease = Math.round(getFloatObjVar(station, ALLOCATED_DPS));
            int armorIncrease = Math.round(getFloatObjVar(station, ALLOCATED_ARMOR));
            int healthIncrease = Math.round(getFloatObjVar(station, ALLOCATED_HEALTH));
            int hitChanceIncrease = Math.round(getFloatObjVar(station, ALLOCATED_HIT_CHANCE));
            int dodgeIncrease = Math.round(getFloatObjVar(station, ALLOCATED_DODGE));
            int parryChanceIncrease = Math.round(getFloatObjVar(station, ALLOCATED_PARRY_CHANCE));
            int glancingBlowIncrease = Math.round(getFloatObjVar(station, ALLOCATED_GLANCING_BLOW));
            int blockChanceIncrease = Math.round(getFloatObjVar(station, ALLOCATED_BLOCK_CHANCE));
            int blockValueIncrease = Math.round(getFloatObjVar(station, ALLOCATED_BLOCK_VALUE));
            int criticalHitIncrease = Math.round(getFloatObjVar(station, ALLOCATED_CRITICAL_HIT));
            int evasionIncrease = Math.round(getFloatObjVar(station, ALLOCATED_EVASION));
            int evasionRatingIncrease = Math.round(getFloatObjVar(station, ALLOCATED_EVASION_RATING));
            int strikethroughIncrease = Math.round(getFloatObjVar(station, ALLOCATED_STRIKETHROUGH));
            int strikethroughRatingIncrease = Math.round(getFloatObjVar(station, ALLOCATED_STRIKETHROUGH_RATING));
            int aggression = getIntObjVar(station, ALLOCATED_AGGRESSION);
            int beastialResilience = getIntObjVar(station, ALLOCATED_BEASTIAL_RESILIENCE);
            int huntersInstinct = getIntObjVar(station, ALLOCATED_HUNTERS_INSTINCT);
            int intelligence = getIntObjVar(station, ALLOCATED_INTELLIGENCE);
            int survival = getIntObjVar(station, ALLOCATED_SURVIVAL);
            int cunning = getIntObjVar(station, ALLOCATED_CUNNING);
            String parentCreatureTemplate = getStringObjVar(station, STATION_DNA_CREATURE);
            int eggHue = getIntObjVar(station, ALLOCATED_CREATURE_HUE_INDEX);
            obj_id creatorId = getIncubatorActiveUser(station);
            String creatorName = "";
            if (isIdValid(creatorId))
            {
                creatorName = getPlayerName(creatorId);
            }
            setObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE, hashCreatureType);
            setObjVar(egg, beast_lib.OBJVAR_BEAST_PARENT, parentCreatureTemplate);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_AGGRESSION, aggression);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_BEASTIAL_RESILIENCE, beastialResilience);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_CUNNING, cunning);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_HUNTERS_INSTINCT, huntersInstinct);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_INTELLIGENCE, intelligence);
            setObjVar(egg, beast_lib.OBJVAR_SKILL_SURVIVAL, survival);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_ARMOR, armorIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_DPS, dpsIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_HEALTH, healthIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_HIT_CHANCE, hitChanceIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_DODGE, dodgeIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_PARRY, parryChanceIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_GLANCING_BLOW, glancingBlowIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_BLOCK_CHANCE, blockChanceIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_BLOCK_VALUE, blockValueIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_CRITICAL_HIT, criticalHitIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_EVASION, evasionIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_EVASION_RATING, evasionRatingIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_STRIKETHROUGH, strikethroughIncrease);
            setObjVar(egg, beast_lib.OBJVAR_INCREASE_STRIKETHROUGH_RATING, strikethroughRatingIncrease);
            if (!creatorName.equals("") && creatorName != null)
            {
                setObjVar(egg, beast_lib.OBJVAR_BEAST_ENGINEER, creatorName);
            }
            if (hasObjVar(station, STATION_DNA_OLD_PET_IDENTIFIER))
            {
                String name = getStringObjVar(station, STATION_DNA_PARENT_NAME);
                setObjVar(egg, beast_lib.OBJVAR_OLD_PET_REHUED, 1);
                setObjVar(egg, beast_lib.OBJVAR_OLD_PET_IDENTIFIER, 1);
                setName(egg, name);
            }
            setObjVar(egg, beast_lib.OBJVAR_BEAST_HUE, eggHue);
            setObjVar(station, OBJVAR_EGG_CREATED, 1);
            setEggHue(egg);
        }
        else 
        {
            blog("beast", "Beast egg not not in incubator, we are in: (" + station + ")" + template);
        }
    }
    public static String cleanAppearanceOfHueTag(String appearance) throws InterruptedException
    {
        blog("BEAST_DNA_CLEAN_HUE", "appearance = " + appearance);
        if (appearance.indexOf("_hue") > -1)
        {
            String[] splitAppearance = split(appearance, '_');
            blog("BEAST_DNA_CLEAN_HUE", "splitAppearance.length = " + splitAppearance.length);
            if (splitAppearance.length >= 2)
            {
                appearance = splitAppearance[0];
                blog("BEAST_DNA_CLEAN_HUE", "appearance = " + appearance);
                for (int i = 1; i < splitAppearance.length - 1; ++i)
                {
                    appearance += "_" + splitAppearance[i];
                    blog("BEAST_DNA_CLEAN_HUE", "appearance = " + appearance);
                }
            }
            appearance += ".sat";
            blog("BEAST_DNA_CLEAN_HUE", "FinalAappearance = " + appearance);
            return appearance;
        }
        else if (appearance.indexOf(".iff") > -1)
        {
            String[] splitAppearance = split(appearance, '.');
            blog("BEAST_DNA_CLEAN_HUE", "splitAppearance.length = " + splitAppearance.length);
            if (splitAppearance.length >= 2)
            {
                appearance = splitAppearance[0];
                blog("BEAST_DNA_CLEAN_HUE", "appearance = " + appearance);
            }
            appearance += ".sat";
            blog("BEAST_DNA_CLEAN_HUE", "FinalAappearance = " + appearance);
            return appearance;
        }
        else 
        {
            return appearance;
        }
    }
    public static boolean hasPower(obj_id station) throws InterruptedException
    {
        int amount = getStationPowerAmount(station);
        if (amount > 0)
        {
            return true;
        }
        return false;
    }
    public static String getStationPowerName(obj_id station) throws InterruptedException
    {
        return (getStringObjVar(station, RESOURCE_POWER_NAME));
    }
    public static int getStationPowerAmount(obj_id station) throws InterruptedException
    {
        return (getIntObjVar(station, RESOURCE_POWER_AMOUNT));
    }
    public static int getStationPowerQuality(obj_id station) throws InterruptedException
    {
        return (getIntObjVar(station, RESOURCE_POWER_QUALITY));
    }
    public static boolean addPowerIncubator(obj_id station, obj_id player, int amount, int quality, String resourceName) throws InterruptedException
    {
        if (isIdValid(station) && isIdValid(player))
        {
            if (amount > 0 && quality > 0)
            {
                int amountInStation = getStationPowerAmount(station);
                int finalAmount = amountInStation + amount;
                if (finalAmount > RESOURCE_POWER_AMOUNT_CAP)
                {
                    finalAmount = RESOURCE_POWER_AMOUNT_CAP;
                }
                setIncubatorPowerResourceName(station, resourceName);
                setObjVar(station, RESOURCE_POWER_AMOUNT, finalAmount);
                setObjVar(station, RESOURCE_POWER_QUALITY, quality);
                return true;
            }
        }
        return false;
    }
    public static boolean removeAllPowerIncubator(obj_id station, obj_id player) throws InterruptedException
    {
        if (isIdValid(station) && isIdValid(player))
        {
            String resourceName = getStationPowerName(station);
            int amount = getStationPowerAmount(station);
            if (amount > 0)
            {
                obj_id resourceType = getResourceTypeByName(resourceName);
                if (isIdValid(resourceType))
                {
                    obj_id inventoryId = utils.getInventoryContainer(player);
                    if (isIdValid(inventoryId))
                    {
                        if (getVolumeFree(inventoryId) <= 0)
                        {
                            sendSystemMessage(player, RESOURCE_POWER_INV_FULL);
                            return false;
                        }
                        resource.create(resourceType, amount, inventoryId, player);
                        removeObjVar(station, BASE_INCUBATOR_POWER_OBJVAR);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean hasPowerForSession(obj_id station) throws InterruptedException
    {
        if (isIdValid(station))
        {
            int powerInStation = getStationPowerAmount(station);
            if (powerInStation >= POWER_PER_SESSION)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean decrementPowerForSession(obj_id station) throws InterruptedException
    {
        if (isIdValid(station))
        {
            if (!hasPowerForSession(station))
            {
                return false;
            }
            int powerAmount = getStationPowerAmount(station);
            powerAmount -= POWER_PER_SESSION;
            if (powerAmount == 0)
            {
                removeObjVar(station, BASE_INCUBATOR_POWER_OBJVAR);
                return true;
            }
            else 
            {
                setObjVar(station, RESOURCE_POWER_AMOUNT, powerAmount);
                return true;
            }
        }
        return false;
    }
    public static boolean addPowerPreCheck(obj_id station, obj_id player, int amount, String resourceName) throws InterruptedException
    {
        if (isIdValid(station) && isIdValid(player))
        {
            if (resourceName == null || resourceName.equals(""))
            {
                return false;
            }
            if (amount == 0)
            {
                sendSystemMessage(player, RESOURCE_POWER_AMOUNT_FULL);
                return false;
            }
            String resourceInStation = getStationPowerName(station);
            if (resourceInStation == null || resourceInStation.equals(""))
            {
                return true;
            }
            if (resourceName.equals(resourceInStation))
            {
                int amountInStation = getStationPowerAmount(station);
                if ((amount + amountInStation) > RESOURCE_POWER_AMOUNT_CAP)
                {
                    sendSystemMessage(player, RESOURCE_POWER_AMOUNT_FULL);
                    return false;
                }
                else 
                {
                    return true;
                }
            }
            else 
            {
                sendSystemMessage(player, RESOURCE_TYPE_MISMATCH);
                return false;
            }
        }
        return false;
    }
    public static float getIncubatorDnaQuality(obj_id station) throws InterruptedException
    {
        if (hasObjVar(station, STATION_DNA_QUALITY))
        {
            return getFloatObjVar(station, STATION_DNA_QUALITY);
        }
        return MIN_QUALITY_RANGE;
    }
    public static boolean convertPcdIntoPetItem(obj_id player, obj_id pcd) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        String pcdAppearance = getAppearance(pcd);
        String finalPcdAppearance = pcdAppearance;
        finalPcdAppearance = cleanAppearanceOfHueTag(finalPcdAppearance);
        int row = dataTableSearchColumnForString(finalPcdAppearance, "start_appearance", DATATABLE_INCUBATOR_TEMPLATES);
        if (row < 0)
        {
            String parentTemplate = getStringObjVar(pcd, "pet.creatureName");
            row = dataTableSearchColumnForString(parentTemplate, "creatureName", create.CREATURE_TABLE);
            if (row < 0)
            {
                CustomerServiceLog("BeastPetConversion: ", "Old Pcd (" + pcd + ")" + " is contained in player's " + getFirstName(player) + "(" + player + ") Datapad.");
                CustomerServiceLog("BeastPetConversion: ", "Initial appearance on Old Pcd (" + pcd + ")" + " is " + pcdAppearance);
                CustomerServiceLog("BeastPetConversion: ", "Appearance searched for in datatable for pcd (" + pcd + ")" + " was " + finalPcdAppearance + " and this appearance was not found. We are now bailing out and not deleting or creating anything. This needs to be reported to development");
                sendSystemMessage(player, SID_REPORT_PET_NOT_CONVERTING);
                return false;
            }
            else 
            {
                dictionary dict = dataTableGetRow(create.CREATURE_TABLE, row);
                pcdAppearance = dict.getString("template");
                finalPcdAppearance = pcdAppearance;
                finalPcdAppearance = cleanAppearanceOfHueTag(finalPcdAppearance);
                finalPcdAppearance = "appearance/" + finalPcdAppearance;
                row = dataTableSearchColumnForString(finalPcdAppearance, "start_appearance", DATATABLE_INCUBATOR_TEMPLATES);
                if (row < 0)
                {
                    row = dataTableSearchColumnForString(finalPcdAppearance, "pcd_template_appearance", DATATABLE_INCUBATOR_TEMPLATES);
                    if (row < 0)
                    {
                        CustomerServiceLog("BeastPetConversion: ", "Old Pcd (" + pcd + ")" + " is contained in player's " + getFirstName(player) + "(" + player + ") Datapad.");
                        CustomerServiceLog("BeastPetConversion: ", "Initial appearance on Old Pcd (" + pcd + ")" + " is " + pcdAppearance);
                        CustomerServiceLog("BeastPetConversion: ", "Appearance searched for in datatable for pcd (" + pcd + ")" + " was " + finalPcdAppearance + " and this appearance was not found. We are now bailing out and not deleting or creating anything. This needs to be reported to development");
                        sendSystemMessage(player, SID_REPORT_PET_NOT_CONVERTING);
                        return false;
                    }
                    String overrideAppearance = dataTableGetString(DATATABLE_INCUBATOR_TEMPLATES, row, "incubator_template_appearance");
                    row = dataTableSearchColumnForString(overrideAppearance, "start_appearance", DATATABLE_INCUBATOR_TEMPLATES);
                    if (row < 0)
                    {
                        CustomerServiceLog("BeastPetConversion: ", "Old Pcd (" + pcd + ")" + " is contained in player's " + getFirstName(player) + "(" + player + ") Datapad.");
                        CustomerServiceLog("BeastPetConversion: ", "Initial appearance on Old Pcd (" + pcd + ")" + " is " + pcdAppearance);
                        CustomerServiceLog("BeastPetConversion: ", "Appearance searched for in datatable for pcd (" + pcd + ")" + " was " + overrideAppearance + " and this appearance was not found. This appearance was an override taken because we failed on first attempts. We are now bailing out and not deleting or creating anything. This needs to be reported to development");
                        sendSystemMessage(player, SID_REPORT_PET_NOT_CONVERTING);
                        return false;
                    }
                }
            }
        }
        dictionary dict = dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row);
        if (dict == null)
        {
            blog("BeastPetConversion", "dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row) was null");
            return false;
        }
        String templateOfConvertedItem = dict.getString("convert_item_template");
        String creatureTemplate = dict.getString("initial_template");
        String parentTemplate = getStringObjVar(pcd, "pet.creatureName");
        obj_id convertedPetItem = createObjectOverloaded(templateOfConvertedItem, pInv);
        if (!isIdValid(convertedPetItem) || isIdNull(convertedPetItem) || !exists(convertedPetItem))
        {
            return false;
        }
        if (copyObjVar(pcd, convertedPetItem, "ai"))
        {
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "pcd (" + pcd + ")" + " was NOT converted into a stuffed pet, for player " + getFirstName(player) + "(" + player + "). This is because we had an invalid objvar (ai) transfer.");
            return false;
        }
        if (copyObjVar(pcd, convertedPetItem, "pet"))
        {
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "pcd (" + pcd + ")" + " was NOT converted into a stuffed pet, for player " + getFirstName(player) + "(" + player + "). This is because we had an invalid objvar (pet) transfer.");
            return false;
        }
        if (hasObjVar(pcd, "creature_attribs.hue"))
        {
            int hueColor = getIntObjVar(pcd, "creature_attribs.hue");
            hue.setColor(convertedPetItem, hue.INDEX_1, hueColor);
            setObjVar(convertedPetItem, DNA_HUE_OLD_PET, hueColor);
        }
        else if (hasObjVar(pcd, "ai.pet.palvar.vars./private/index_color_1"))
        {
            int hueColor = getIntObjVar(pcd, "ai.pet.palvar.vars./private/index_color_1");
            hue.setColor(convertedPetItem, hue.INDEX_1, hueColor);
            setObjVar(convertedPetItem, DNA_HUE_OLD_PET, hueColor);
        }
        else 
        {
            row = dataTableSearchColumnForString(parentTemplate, "creatureName", create.CREATURE_TABLE);
            if (row >= 0)
            {
                dict = dataTableGetRow(create.CREATURE_TABLE, row);
                if (dict != null && !dict.isEmpty())
                {
                    int hueColor = dict.getInt("hue");
                    if (hueColor != 0)
                    {
                        int highhuevar = (hueColor * 8) - 1;
                        int lowhuevar = highhuevar - 7;
                        hueColor = rand(lowhuevar, highhuevar);
                    }
                    hue.setColor(convertedPetItem, hue.INDEX_1, hueColor);
                    setObjVar(convertedPetItem, DNA_HUE_OLD_PET, hueColor);
                }
            }
        }
        row = dataTableSearchColumnForString(parentTemplate, "creatureName", create.CREATURE_TABLE);
        if (row >= 0)
        {
            dict = dataTableGetRow(create.CREATURE_TABLE, row);
            if (dict != null && !dict.isEmpty())
            {
                float minScale = dict.getFloat("minScale");
                float maxScale = dict.getFloat("maxScale");
                float myScale = rand(minScale, maxScale);
                setObjVar(convertedPetItem, DNA_SCALE_OLD_PET, myScale);
            }
        }
        row = dataTableSearchColumnForString(creatureTemplate, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
        int hashTemplate = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
        setObjVar(convertedPetItem, DNA_TEMPLATE_OBJVAR, hashTemplate);
        setObjVar(convertedPetItem, DNA_PARENT_TEMPLATE, parentTemplate);
        String name = getAssignedName(pcd);
        if (!name.equals("") && name != null)
        {
            setName(convertedPetItem, name);
            setObjVar(convertedPetItem, DNA_PARENT_NAME, name);
        }
        CustomerServiceLog("BeastPetConversion: ", "Old Pcd (" + pcd + ")" + " is about to be destroyed. New Pet Decoration item (" + convertedPetItem + ") has been placed in thier inventory, complete with all the necessary data.");
        return true;
    }
    public static boolean convertDeedIntoPetItem(obj_id player, obj_id deed) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        String deedAppearance = getAppearance(deed);
        String finalDeedAppearance = deedAppearance;
        finalDeedAppearance = cleanAppearanceOfHueTag(finalDeedAppearance);
        int row = dataTableSearchColumnForString(finalDeedAppearance, "start_appearance", DATATABLE_INCUBATOR_TEMPLATES);
        if (row < 0)
        {
            CustomerServiceLog("BeastPetConversion: ", "Old deed (" + deed + ")" + " is contained in player's " + getFirstName(player) + "(" + player + ") inventory.");
            CustomerServiceLog("BeastPetConversion: ", "Initial appearance on Old deed (" + deed + ")" + " is " + deedAppearance);
            CustomerServiceLog("BeastPetConversion: ", "Appearance searched for in datatable for deed (" + deed + ")" + " was " + finalDeedAppearance + " and this appearance was not found. We are now bailing out and not deleting or creating anything. This needs to be reported to development");
            sendSystemMessage(player, SID_REPORT_PET_NOT_CONVERTING);
            return false;
        }
        dictionary dict = dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row);
        if (dict == null)
        {
            blog("BeastPetConversion", "dataTableGetRow(DATATABLE_INCUBATOR_TEMPLATES, row) was null");
            return false;
        }
        String creatureTemplate = dict.getString("initial_template");
        String parentTemplate = getStringObjVar(deed, "creature_attribs.type");
        row = dataTableSearchColumnForString(creatureTemplate, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
        int hashTemplate = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
        setObjVar(deed, DNA_TEMPLATE_OBJVAR, hashTemplate);
        setObjVar(deed, DNA_PARENT_TEMPLATE, parentTemplate);
        String name = getAssignedName(deed);
        if (!name.equals("") && name != null)
        {
            setObjVar(deed, DNA_PARENT_NAME, name);
        }
        return true;
    }
    public static boolean convertPetItemToDna(obj_id player, obj_id petItem) throws InterruptedException
    {
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (!isValidId(petItem) || !exists(petItem))
        {
            return false;
        }
        if (!beast_lib.isBeastMaster(player))
        {
            CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was not converted into a dna object, because player " + getFirstName(player) + "(" + player + ") is not a beast master.");
            return false;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id dnaContainer = createObjectOverloaded("object/tangible/loot/beast/dna_container.iff", pInv);
        if (!isValidId(dnaContainer) || !exists(dnaContainer))
        {
            CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was NOT converted into a dna object, for player " + getFirstName(player) + "(" + player + "). This is because creation of the new item failed.");
            return false;
        }
        setObjVar(dnaContainer, DNA_OLD_PET_IDENTIFIER, 1);
        if (copyObjVar(petItem, dnaContainer, "ai"))
        {
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was not converted into a dna object, for player " + getFirstName(player) + "(" + player + "). This is because we had an invalid objvar (ai) transfer.");
            destroyObject(dnaContainer);
            return false;
        }
        if (copyObjVar(petItem, dnaContainer, "pet"))
        {
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was NOT converted into a dna object, for player " + getFirstName(player) + "(" + player + "). This is because we had an invalid objvar (pet) transfer.");
            destroyObject(dnaContainer);
            return false;
        }
        if (copyObjVar(petItem, dnaContainer, "beast"))
        {
            String name = getName(petItem);
            if (name != null && !name.equals(""))
            {
                setObjVar(dnaContainer, DNA_PARENT_TEMPLATE_NAME, name);
            }
        }
        else 
        {
            CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was NOT converted into a dna object, for player " + getFirstName(player) + "(" + player + "). This is because we had an invalid objvar (beast) transfer.");
            destroyObject(dnaContainer);
            return false;
        }
        if (hasObjVar(dnaContainer, DNA_PARENT_NAME))
        {
            setName(dnaContainer, getStringObjVar(dnaContainer, DNA_PARENT_NAME));
        }
        CustomerServiceLog("BeastPetConversion: ", "petItem (" + petItem + ")" + " was converted into a dna object" + dnaContainer + "), for player " + getFirstName(player) + "(" + player + "). All data has been transfered, and we will now destroy the PetItem(" + petItem + ")");
        return true;
    }
    public static String convertHashTemplateToString(int hashTemplate, obj_id station) throws InterruptedException
    {
        if (hashTemplate == 0)
        {
            blog("INCUBATOR", "convertHashTemplateToString hashTemplate == 0");
            return "";
        }
        if (!isValidId(station) || !exists(station))
        {
            blog("INCUBATOR", "convertHashTemplateToString !isValidId(station) || !exists(station)");
            return "";
        }
        blog("INCUBATOR", "convertHashTemplateToString start");
        blog("INCUBATOR", "convertHashTemplateToString hashTemplate " + hashTemplate);
        int row = -1;
        String template = "";
        int lastMutatedSession = getLastSessionMutated(station);
        blog("INCUBATOR", "convertHashTemplateToString lastMutatedSession " + lastMutatedSession);
        boolean movedSessionVariable = false;
        if (lastMutatedSession >= 1)
        {
            row = dataTableSearchColumnForInt(hashTemplate, "hash_mutated_template_" + lastMutatedSession, DATATABLE_INCUBATOR_TEMPLATES);
            if (row < 0)
            {
                row = dataTableSearchColumnForInt(hashTemplate, "hash_mutated_template_" + (lastMutatedSession + 1), DATATABLE_INCUBATOR_TEMPLATES);
                blog("INCUBATOR", "convertHashTemplateToString column is hash_mutated_template_" + (lastMutatedSession + 1));
                if (row < 0)
                {
                    blog("INCUBATOR", "convertHashTemplateToString row " + row + " bailing out.");
                    return "";
                }
                movedSessionVariable = true;
                setObjVar(station, LAST_MUTATION, (lastMutatedSession + 1));
            }
            blog("INCUBATOR", "convertHashTemplateToString row " + row);
            if (!movedSessionVariable)
            {
                template = dataTableGetString(DATATABLE_INCUBATOR_TEMPLATES, row, "mutated_template_" + lastMutatedSession);
            }
            else 
            {
                template = dataTableGetString(DATATABLE_INCUBATOR_TEMPLATES, row, "mutated_template_" + (lastMutatedSession + 1));
            }
        }
        else 
        {
            blog("INCUBATOR", "convertHashTemplateToString no mutation yet");
            row = dataTableSearchColumnForInt(hashTemplate, "hash_initial_template", DATATABLE_INCUBATOR_TEMPLATES);
            if (row < 0)
            {
                blog("INCUBATOR", "convertHashTemplateToString row " + row + " bailing out.");
                return "";
            }
            blog("INCUBATOR", "convertHashTemplateToString row " + row);
            template = dataTableGetString(DATATABLE_INCUBATOR_TEMPLATES, row, "initial_template");
        }
        blog("INCUBATOR", "convertHashTemplateToString returning " + template);
        return template;
    }
    public static int convertStringTemplateToCrC(String strTemplate) throws InterruptedException
    {
        if (strTemplate == null || strTemplate.equals(""))
        {
            blog("INCUBATOR", "convertStringTemplateToCrC hashTemplate null");
            return 0;
        }
        blog("INCUBATOR", "convertStringTemplateToCrC start");
        blog("INCUBATOR", "convertStringTemplateToCrC strTemplate " + strTemplate);
        int row = -1;
        int crcTemplate = 0;
        row = dataTableSearchColumnForString(strTemplate, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
        if (row < 0)
        {
            blog("INCUBATOR", "convertStringTemplateToCrC row " + row + " bailing out.");
            return 0;
        }
        blog("INCUBATOR", "convertStringTemplateToCrC row " + row);
        crcTemplate = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
        blog("INCUBATOR", "convertStringTemplateToCrC returning " + crcTemplate);
        return crcTemplate;
    }
    public static int getHashType(String beastType) throws InterruptedException
    {
        int hashType = 0;
        if (beastType == null || beastType.equals(""))
        {
            blog("INCUBATOR", "getHashType beastType == null");
            return 0;
        }
        blog("INCUBATOR", "getHashType start");
        blog("INCUBATOR", "getHashType beastType " + beastType);
        int row = dataTableSearchColumnForString(beastType, "beastType", beast_lib.BEASTS_TABLE);
        if (row < 0)
        {
            blog("INCUBATOR", "getHashType row < 0");
            return 0;
        }
        blog("INCUBATOR", "getHashType row " + row);
        hashType = dataTableGetInt(beast_lib.BEASTS_TABLE, row, "hashBeastType");
        blog("INCUBATOR", "getHashType returning " + hashType);
        return hashType;
    }
    public static String convertHashTypeToString(int hashType) throws InterruptedException
    {
        if (hashType == 0)
        {
            blog("INCUBATOR", "convertHashTypeToString hashType == 0");
            return "";
        }
        String beastType = "";
        blog("INCUBATOR", "convertHashTypeToString start");
        blog("INCUBATOR", "convertHashTypeToString hashType " + hashType);
        int row = dataTableSearchColumnForInt(hashType, "hashBeastType", beast_lib.BEASTS_TABLE);
        if (row < 0)
        {
            blog("INCUBATOR", "convertHashTypeToString row < 0");
            return "";
        }
        blog("INCUBATOR", "convertHashTypeToString row " + row);
        beastType = dataTableGetString(beast_lib.BEASTS_TABLE, row, "beastType");
        blog("INCUBATOR", "convertHashTypeToString returning " + beastType);
        return beastType;
    }
    public static void blog(String identifier, String text) throws InterruptedException
    {
        if (LOGGING_ENABLED)
        {
            LOG(identifier, text);
        }
    }
    public static boolean isEggMountType(obj_id egg) throws InterruptedException
    {
        int hashBeastType = 0;
        if (hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE))
        {
            hashBeastType = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE);
        }
        else 
        {
            return false;
        }
        int row = dataTableSearchColumnForInt(hashBeastType, "hashBeastType", beast_lib.BEASTS_TABLE);
        String mountTemplate = dataTableGetString(beast_lib.BEASTS_TABLE, row, "mountTemplate");
        if (mountTemplate == null || mountTemplate.equals(""))
        {
            return false;
        }
        return true;
    }
    public static String getMountTemplate(obj_id egg, obj_id player) throws InterruptedException
    {
        int hashBeastType = 0;
        if (hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE))
        {
            hashBeastType = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE);
        }
        else 
        {
            return "";
        }
        int row = dataTableSearchColumnForInt(hashBeastType, "hashBeastType", beast_lib.BEASTS_TABLE);
        String mountTemplate = dataTableGetString(beast_lib.BEASTS_TABLE, row, "mountTemplate");
        return mountTemplate;
    }
    public static boolean stampEggAsMount(obj_id egg, obj_id player) throws InterruptedException
    {
        if (!isIdValid(egg) || !exists(egg))
        {
            return false;
        }
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (isEggMountFlagged(egg))
        {
            return false;
        }
        setObjVar(egg, beast_lib.OBJVAR_BEAST_MOUNT_FLAG, 1);
        return true;
    }
    public static obj_id convertEggToMount(obj_id egg, obj_id player) throws InterruptedException
    {
        String mountType = getMountTemplate(egg, player);
        if (mountType == null && mountType.equals(""))
        {
            return obj_id.NULL_ID;
        }
        if (!hasScript(player, "ai.pet_master"))
        {
            attachScript(player, "ai.pet_master");
        }
        if (!pet_lib.hasMaxStoredPetsOfType(player, pet_lib.PET_TYPE_MOUNT))
        {
            obj_id datapad = utils.getPlayerDatapad(player);
            if (!isIdValid(datapad))
            {
                CustomerServiceLog("BeastEggToMountConversion: ", "Player (" + player + ") tried to convert thier beast egg(" + egg + ") into a mount and FAILED. This was due to them returning an invalid Datapad objId. Egg (" + egg + ") was not destroyed.");
                return obj_id.NULL_ID;
            }
            String controlTemplate = "object/intangible/pet/" + utils.dataTableGetString(create.CREATURE_TABLE, mountType, "template");
            if (!controlTemplate.endsWith(".iff"))
            {
                controlTemplate = pet_lib.PET_CTRL_DEVICE_TEMPLATE;
            }
            obj_id petControlDevice = createObject(controlTemplate, datapad, "");
            if (!isIdValid(petControlDevice))
            {
                petControlDevice = createObject(pet_lib.PET_CTRL_DEVICE_TEMPLATE, datapad, "");
                if (!isIdValid(petControlDevice))
                {
                    sendSystemMessage(player, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
                    CustomerServiceLog("BeastEggToMountConversion: ", "Player (" + player + ") tried to convert thier beast egg(" + egg + ") into a mount and FAILED. This was due being unable to create a PCD in thier datatpad. Most likely they have too many objects in it. Egg (" + egg + ") was not destroyed.");
                    return obj_id.NULL_ID;
                }
            }
            int mountHue = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_HUE);
            setUpPetControlDevice(petControlDevice, mountType, egg);
            setObjVar(petControlDevice, "ai.pet.trainedMount", 1);
            setObjVar(petControlDevice, "ai.pet.type", pet_lib.PET_TYPE_MOUNT);
            setObjVar(petControlDevice, beast_lib.OBJVAR_BEAST_HUE, mountHue);
            setObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_REHUED, 1);
            hue.setColor(petControlDevice, hue.INDEX_1, mountHue);
            CustomerServiceLog("BeastEggToMountConversion: ", "Player (" + player + ") converted thier beast egg(" + egg + ") into a mount and SUCEEDED. PCD(" + petControlDevice + ") was created. We will now destroy the egg(" + egg + ").");
            return petControlDevice;
        }
        sendSystemMessage(player, pet_lib.SID_SYS_TOO_MANY_STORED_PETS);
        return obj_id.NULL_ID;
    }
    public static void setUpPetControlDevice(obj_id petControlDevice, String petType, obj_id egg) throws InterruptedException
    {
        setObjVar(petControlDevice, "pet.creatureName", petType);
        attachScript(petControlDevice, "ai.pet_control_device");
        String myName = getAssignedName(egg);
        if (myName != null && !myName.equals(""))
        {
            if (myName.startsWith("an incubated"))
            {
                if (setBeastTypeToName(egg, petControlDevice))
                {
                    setObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_IDENTIFIER, 1);
                    return;
                }
            }
            setName(petControlDevice, myName);
            setObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_IDENTIFIER, 1);
        }
        setBeastTypeToName(egg, petControlDevice);
        setObjVar(petControlDevice, beast_lib.OBJVAR_OLD_PET_IDENTIFIER, 1);
        return;
    }
    public static boolean setBeastTypeToName(obj_id egg, obj_id pcd) throws InterruptedException
    {
        if (hasObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE))
        {
            int hashType = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE);
            String beastType = incubator.convertHashTypeToString(hashType);
            String beastDisplayType = beast_lib.stripBmFromType(beastType);
            string_id beastTypeSid = new string_id("monster_name", beastDisplayType);
            setName(pcd, localize(beastTypeSid));
            return true;
        }
        return false;
    }
    public static boolean giveMutationAttributeBonus(obj_id station, obj_id player, float[] attributesUpdateAmount, String[] attributes) throws InterruptedException
    {
        if (!isValidId(station) || !exists(station))
        {
            return false;
        }
        if (!isValidId(player) || !exists(player))
        {
            return false;
        }
        if (attributesUpdateAmount.length <= 0)
        {
            return false;
        }
        if (attributes.length <= 0)
        {
            return false;
        }
        int index = rand(0, (attributes.length - 1));
        int bonusAmt = rand(1, 10);
        incubator.blog("INCUBATOR", attributes[index] + " Before " + attributesUpdateAmount[index]);
        attributesUpdateAmount[index] += (float)bonusAmt;
        float attrib = 0.0f;
        if (attributes[index].equals("Health Bonus"))
        {
            attrib = attributesUpdateAmount[index] * 0.2f;
        }
        else 
        {
            attrib = attributesUpdateAmount[index] * .1f;
        }
        attrib = utils.roundFloatByDecimal(attrib);
        incubator.blog("INCUBATOR", attributes[index] + " After " + attributesUpdateAmount[index]);
        incubator.blog("INCUBATOR", "attrib " + attrib);
        prose_package pp = new prose_package();
        pp = prose.setDF(pp, attrib);
        pp = prose.setDI(pp, bonusAmt);
        pp = prose.setTT(pp, attributes[index]);
        pp = prose.setStringId(pp, SID_ATTRIBUTE_MUTATION);
        sendSystemMessageProse(player, pp);
        CustomerServiceLog("incubatorMutationLog: ", "Player '" + getPlayerName(player) + "' (" + player + ") received a Type 1 mutation. This type of mutation gives a bonuse to stats. They received " + bonusAmt + " points to " + attributes[index] + ".");
        return true;
    }
    public static String getCreatureTypeFromHashTemplate(obj_id station) throws InterruptedException
    {
        if (!isValidId(station) || !exists(station))
        {
            return "";
        }
        int hashTemplate = getIncubatorCreatureTemplate(station);
        String creatureTemplate = convertHashTemplateToString(hashTemplate, station);
        blog("INCUBATOR", "creatureTemplate = " + creatureTemplate);
        String[] splitCreatureTemplate = split(creatureTemplate, '.');
        blog("INCUBATOR", "splitCreatureTemplate[0] = " + splitCreatureTemplate[0] + " splitCreatureTemplate[1] = " + splitCreatureTemplate[1]);
        String[] finalCreatureTemplate = split(splitCreatureTemplate[0], '/');
        int finalIndex = finalCreatureTemplate.length - 1;
        blog("INCUBATOR", "finalCreatureTemplate[finalIndex] = " + finalCreatureTemplate[finalIndex]);
        String creatureName = finalCreatureTemplate[finalIndex];
        blog("INCUBATOR", "creatureName = " + creatureName);
        return creatureName;
    }
    public static boolean isConvertedPet(obj_id item) throws InterruptedException
    {
        if (hasObjVar(item, DNA_OLD_PET_IDENTIFIER) || hasObjVar(item, STATION_DNA_OLD_PET_IDENTIFIER))
        {
            return true;
        }
        return false;
    }
    public static boolean fixDnaData(obj_id dna, obj_id player) throws InterruptedException
    {
        if (hasObjVar(dna, DNA_TEMPLATE_OBJVAR))
        {
            String templateString = getStringObjVar(dna, DNA_TEMPLATE_OBJVAR);
            int row = dataTableSearchColumnForString(templateString, "initial_template", DATATABLE_INCUBATOR_TEMPLATES);
            int hashTemplate = dataTableGetInt(DATATABLE_INCUBATOR_TEMPLATES, row, "hash_initial_template");
            setObjVar(dna, DNA_TEMPLATE_OBJVAR, hashTemplate);
            return true;
        }
        return false;
    }
    public static String getCurrentSessionParticleLabelName(obj_id station) throws InterruptedException
    {
        int currentSession = getCurrentSessionNumber(station);
        String sessionLabel = PARTICLE_LABEL_DEFAULT;
        switch (currentSession)
        {
            case 1:
            sessionLabel = PARTICLE_LABEL_DEFAULT;
            break;
            case 2:
            sessionLabel = PARTICLE_LABEL_PHASE_ONE;
            break;
            case 3:
            sessionLabel = PARTICLE_LABEL_PHASE_TWO;
            break;
            case 4:
            sessionLabel = PARTICLE_LABEL_PHASE_THREE;
            break;
            case 5:
            sessionLabel = PARTICLE_LABEL_PHASE_FOUR;
            break;
            default:
            sessionLabel = PARTICLE_LABEL_DEFAULT;
            break;
        }
        return sessionLabel;
    }
    public static String advanceParticleLabelName(String labelName) throws InterruptedException
    {
        String sessionLabel = null;
        if (labelName != null && !labelName.equals(""))
        {
            if (labelName.equals(PARTICLE_LABEL_DEFAULT))
            {
                sessionLabel = PARTICLE_LABEL_PHASE_ONE;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_ONE))
            {
                sessionLabel = PARTICLE_LABEL_PHASE_TWO;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_TWO))
            {
                sessionLabel = PARTICLE_LABEL_PHASE_THREE;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_THREE))
            {
                sessionLabel = PARTICLE_LABEL_PHASE_FOUR;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_FOUR))
            {
                sessionLabel = PARTICLE_LABEL_DEFAULT;
            }
        }
        return sessionLabel;
    }
    public static String getCurrentSessionParticle(obj_id station) throws InterruptedException
    {
        int currentSession = getCurrentSessionNumber(station);
        String sessionParticle = PARTICLE_DEFAULT;
        switch (currentSession)
        {
            case 1:
            sessionParticle = PARTICLE_DEFAULT;
            break;
            case 2:
            sessionParticle = PARTICLE_PHASE_ONE;
            break;
            case 3:
            sessionParticle = PARTICLE_PHASE_TWO;
            break;
            case 4:
            sessionParticle = PARTICLE_PHASE_THREE;
            break;
            case 5:
            sessionParticle = PARTICLE_PHASE_FOUR;
            break;
            default:
            sessionParticle = PARTICLE_DEFAULT;
            break;
        }
        return sessionParticle;
    }
    public static String getCurrentSessionParticle(String labelName) throws InterruptedException
    {
        String sessionParticle = null;
        if (labelName != null && !labelName.equals(""))
        {
            if (labelName.equals(PARTICLE_LABEL_DEFAULT))
            {
                sessionParticle = PARTICLE_DEFAULT;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_ONE))
            {
                sessionParticle = PARTICLE_PHASE_ONE;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_TWO))
            {
                sessionParticle = PARTICLE_PHASE_TWO;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_THREE))
            {
                sessionParticle = PARTICLE_PHASE_THREE;
            }
            if (labelName.equals(PARTICLE_LABEL_PHASE_FOUR))
            {
                sessionParticle = PARTICLE_PHASE_FOUR;
            }
        }
        return sessionParticle;
    }
    public static String getCurrentParticleHardpoint(String sessionParticle) throws InterruptedException
    {
        String hardPoint = null;
        if (sessionParticle != null && !sessionParticle.equals(""))
        {
            if (sessionParticle.equals(PARTICLE_DEFAULT))
            {
                hardPoint = PARTICLE_HARDPOINT_TWO;
            }
            if (sessionParticle.equals(PARTICLE_PHASE_ONE))
            {
                hardPoint = PARTICLE_HARDPOINT_THREE;
            }
            if (sessionParticle.equals(PARTICLE_PHASE_TWO))
            {
                hardPoint = PARTICLE_HARDPOINT_THREE;
            }
            if (sessionParticle.equals(PARTICLE_PHASE_THREE))
            {
                hardPoint = PARTICLE_HARDPOINT_THREE;
            }
            if (sessionParticle.equals(PARTICLE_PHASE_FOUR))
            {
                hardPoint = PARTICLE_HARDPOINT_THREE;
            }
        }
        return hardPoint;
    }
    public static void advanceSessionParticle(obj_id station) throws InterruptedException
    {
        if (!isValidId(station))
        {
            return;
        }
        String currentLabel = utils.getStringScriptVar(station, PARTICLE_LABEL_SCRIPT_VAR);
        if (currentLabel != null && !currentLabel.equals(""))
        {
            String newLabel = advanceParticleLabelName(currentLabel);
            String sessionParticle = getCurrentSessionParticle(newLabel);
            String hardPoint = incubator.getCurrentParticleHardpoint(sessionParticle);
            if ((newLabel != null && !newLabel.equals("")) && (sessionParticle != null && !sessionParticle.equals("")) && (!hardPoint.equals("") && hardPoint != null))
            {
                stopClientEffectObjByLabel(station, currentLabel);
                utils.setScriptVar(station, incubator.PARTICLE_LABEL_SCRIPT_VAR, newLabel);
                playClientEffectObj(station, sessionParticle, station, hardPoint, null, newLabel);
            }
        }
        return;
    }
    public static void stopAllSessionParticles(obj_id station) throws InterruptedException
    {
        stopClientEffectObjByLabel(station, PARTICLE_LABEL_DEFAULT);
        stopClientEffectObjByLabel(station, PARTICLE_LABEL_PHASE_ONE);
        stopClientEffectObjByLabel(station, PARTICLE_LABEL_PHASE_TWO);
        stopClientEffectObjByLabel(station, PARTICLE_LABEL_PHASE_THREE);
        stopClientEffectObjByLabel(station, PARTICLE_LABEL_PHASE_FOUR);
        return;
    }
    public static boolean isEggMountFlagged(obj_id egg) throws InterruptedException
    {
        if (!isIdValid(egg) || !exists(egg))
        {
            return false;
        }
        if (hasObjVar(egg, beast_lib.OBJVAR_BEAST_MOUNT_FLAG))
        {
            return true;
        }
        return false;
    }
    public static boolean giveMutationSkillBonus(obj_id station, obj_id player, int[] skillArray, String newTemplate) throws InterruptedException
    {
        blog("INCUBATOR_SKILLS", "giveMutationSkillBonus::start");
        if (!exists(station) || !isIdValid(station))
        {
            return false;
        }
        if (!exists(player) || !isIdValid(player))
        {
            return false;
        }
        if (skillArray.length <= 0)
        {
            return false;
        }
        for (int i = 0; i < skillArray.length; ++i)
        {
            skillArray[i] += MUTATION_SKILL_BONUS_AMT;
            blog("INCUBATOR_SKILLS", "giveMutationSkillBonus::skillArray[i]" + skillArray[i]);
        }
        CustomerServiceLog("incubatorMutationLog: ", "Player '" + getPlayerName(player) + "' (" + player + ") received a template mutation. This type of mutation gives a bonus of " + MUTATION_SKILL_BONUS_AMT + " to all skills and a template appearance change. They now have the template of " + newTemplate + " in their incubator.");
        return true;
    }
    public static boolean setEggHue(obj_id egg) throws InterruptedException
    {
        return setEggHue(egg, obj_id.NULL_ID);
    }
    public static boolean setEggHue(obj_id egg, obj_id player) throws InterruptedException
    {
        if (!exists(egg) || !isIdValid(egg))
        {
            return false;
        }
        boolean hasPlayerId = false;
        if (isIdValid(player))
        {
            hasPlayerId = true;
        }
        if (hasObjVar(egg, beast_lib.OBJVAR_BEAST_HUE))
        {
            int eggHue = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_HUE);
            int crcType = getIntObjVar(egg, beast_lib.OBJVAR_BEAST_TYPE);
            int row = dataTableSearchColumnForInt(crcType, "hashBeastType", beast_lib.BEASTS_TABLE);
            if (row < 0)
            {
                blog("EggHue", "setEggHue row " + row + " bailing out.");
                return false;
            }
            int paletteLength = dataTableGetInt(beast_lib.BEASTS_TABLE, row, "palette_length");
            if (paletteLength < 0)
            {
                return false;
            }
            int paletteMinusMutation = paletteLength - MUTATION_PALETTE_LENGTH;
            eggHue -= paletteMinusMutation;
            if (hasPlayerId)
            {
                debugSpeakMsg(player, "eggHue is " + eggHue);
            }
            hue.setColor(egg, "/private/index_color_1", eggHue);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static String getCreatureTypeFromHashTemplateDna(obj_id dna, int hashTemplate) throws InterruptedException
    {
        if (!isValidId(dna) || !exists(dna))
        {
            return "";
        }
        String creatureTemplate = convertHashTemplateToString(hashTemplate, dna);
        blog("INCUBATOR", "creatureTemplate = " + creatureTemplate);
        String[] splitCreatureTemplate = split(creatureTemplate, '.');
        blog("INCUBATOR", "splitCreatureTemplate[0] = " + splitCreatureTemplate[0] + " splitCreatureTemplate[1] = " + splitCreatureTemplate[1]);
        String[] finalCreatureTemplate = split(splitCreatureTemplate[0], '/');
        int finalIndex = finalCreatureTemplate.length - 1;
        blog("INCUBATOR", "finalCreatureTemplate[finalIndex] = " + finalCreatureTemplate[finalIndex]);
        String creatureName = finalCreatureTemplate[finalIndex];
        blog("INCUBATOR", "creatureName = " + creatureName);
        return creatureName;
    }
    public static String convertHashTemplateToStringDna(int hashTemplate, obj_id dna) throws InterruptedException
    {
        if (hashTemplate == 0)
        {
            blog("INCUBATOR", "convertHashTemplateToString hashTemplate == 0");
            return "";
        }
        if (!isValidId(dna) || !exists(dna))
        {
            blog("INCUBATOR", "convertHashTemplateToString !isValidId(dna) || !exists(dna)");
            return "";
        }
        blog("INCUBATOR", "convertHashTemplateToString start");
        blog("INCUBATOR", "convertHashTemplateToString hashTemplate " + hashTemplate);
        int row = -1;
        String template = "";
        row = dataTableSearchColumnForInt(hashTemplate, "hash_initial_template", DATATABLE_INCUBATOR_TEMPLATES);
        if (row < 0)
        {
            blog("INCUBATOR", "convertHashTemplateToString row " + row + " bailing out.");
            return "";
        }
        blog("INCUBATOR", "convertHashTemplateToString row " + row);
        template = dataTableGetString(DATATABLE_INCUBATOR_TEMPLATES, row, "initial_template");
        blog("INCUBATOR", "convertHashTemplateToString returning " + template);
        return template;
    }
    public static void awardEnzymeCollection(obj_id enzyme) throws InterruptedException
    {
        if (!isIdValid(enzyme) || !exists(enzyme))
        {
            return;
        }
        if (!hasObjVar(enzyme, "collection_enzyme"))
        {
            return;
        }
        if (hasObjVar(enzyme, incubator.ENZYME_COLOR_OBJVAR))
        {
            int color = getIntObjVar(enzyme, incubator.ENZYME_COLOR_OBJVAR);
            if (color >= ENZYME_COLLECTION_NAMES.length)
            {
                return;
            }
            obj_id player = utils.getContainingPlayer(enzyme);
            if (!isIdValid(player) || !exists(player) || !isPlayer(player))
            {
                return;
            }
            removeObjVar(enzyme, "collection_enzyme");
            int collectionSlot = (int)getCollectionSlotValue(player, ENZYME_COLLECTION_NAMES[color]);
            if (collectionSlot <= 0)
            {
                boolean modified = modifyCollectionSlotValue(player, ENZYME_COLLECTION_NAMES[color], 1);
            }
        }
    }
}
