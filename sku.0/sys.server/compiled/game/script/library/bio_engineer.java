package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.lang.Math;
import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.callable;
import script.library.corpse;
import script.library.create;
import script.library.skill;
import script.library.utils;
import script.library.xp;
import script.library.sui;

public class bio_engineer extends script.base_script
{
    public bio_engineer()
    {
    }
    public static final float DNA_HARVEST_CHECK_FRENZY = 0.3f;
    public static final float DNA_HARVEST_CHECK_ATTACK = 0.7f;
    public static final float CREATURE_MIN_TO_HIT_MOD = -0.25f;
    public static final float CREATURE_MAX_TO_HIT_MOD = 0.25f;
    public static final float CREATURE_MIN_WEAPON_SPEED_MOD = 0.25f;
    public static final float CREATURE_MAX_WEAPON_SPEED_MOD = -0.25f;
    public static final float CREATURE_MIN_DAMAGE_MOD = -0.25f;
    public static final float CREATURE_MAX_DAMAGE_MOD = 0.25f;
    public static final float CREATURE_MIN_HEALTH_REGEN = 0.75f;
    public static final float CREATURE_MAX_HEALTH_REGEN = 1.25f;
    public static final float CREATURE_MIN_FEROCITY_MOD = -0.25f;
    public static final float CREATURE_MAX_FEROCITY_MOD = 0.25f;
    public static final int HARVEST_TIME = 10;
    public static final int DNA_V_LOW_QUALITY = 1;
    public static final int DNA_LOW_QUALITY = 2;
    public static final int DNA_B_AVG_QUALITY = 3;
    public static final int DNA_AVG_QUALITY = 4;
    public static final int DNA_A_AVG_QUALITY = 5;
    public static final int DNA_HIGH_QUALITY = 6;
    public static final int DNA_V_HIGH_QUALITY = 7;
    public static final int DNA_V_LOW_QUALITY_THRESHOLD = 0;
    public static final int DNA_LOW_QUALITY_THRESHOLD = 15;
    public static final int DNA_B_AVG_QUALITY_THRESHOLD = 35;
    public static final int DNA_AVG_QUALITY_THRESHOLD = 60;
    public static final int DNA_A_AVG_QUALITY_THRESHOLD = 90;
    public static final int DNA_HIGH_QUALITY_THRESHOLD = 115;
    public static final int DNA_V_HIGH_QUALITY_THRESHOLD = 135;
    public static final int BIO_COMP_EFFECT_FOOD = 0;
    public static final int BIO_COMP_EFFECT_DURATION = 1;
    public static final int BIO_COMP_EFFECT_FLAVOR = 2;
    public static final int BIO_COMP_EFFECT_HEALTH_MOD = 3;
    public static final int BIO_COMP_EFFECT_CON_MOD = 4;
    public static final int BIO_COMP_EFFECT_ACTION_MOD = 5;
    public static final int BIO_COMP_EFFECT_STAM_MOD = 6;
    public static final int BIO_COMP_EFFECT_MIND_MOD = 7;
    public static final int BIO_COMP_EFFECT_WILL_MOD = 8;
    public static final int BIO_COMP_EFFECT_CLOTHING = 100;
    public static final int BIO_COMP_EFFECT_HEALING_ABILITY = 101;
    public static final int BIO_COMP_EFFECT_HEALING_INJURY_TREATMENT = 102;
    public static final int BIO_COMP_EFFECT_HEALING_WOUND_TREATMENT = 103;
    public static final int BIO_COMP_EFFECT_TAME_NON_AGGRO = 104;
    public static final int BIO_COMP_EFFECT_TAME_AGGRO = 105;
    public static final int BIO_COMP_EFFECT_CREATURE_TRAINING = 106;
    public static final int BIO_COMP_EFFECT_CREATURE_EMPATHY = 107;
    public static final int BIO_COMP_EFFECT_HEALING_MUSIC_WOUND = 108;
    public static final int BIO_COMP_EFFECT_HEALING_DANCE_WOUND = 109;
    public static final int BIO_COMP_EFFECT_BUSINESS_ACCUMEN = 110;
    public static final int BIO_COMP_EFFECT_CAMOUFLAGE = 111;
    public static final int BIO_COMP_EFFECT_COVER = 112;
    public static final int BIO_COMP_EFFECT_TAKE_COVER = 113;
    public static final int BIO_COMP_EFFECT_COMBAT_BLEEDING_DEFENSE = 114;
    public static final int BIO_COMP_EFFECT_STUN_DEFENSE = 115;
    public static final int BIO_COMP_EFFECT_MELEE_DEFENSE = 116;
    public static final int BIO_COMP_EFFECT_INTIMIDATION = 117;
    public static final int BIO_COMP_EFFECT_WARCRY = 118;
    public static final int BIO_COMP_EFFECT_MASK_SCENT = 119;
    public static final String[] DNA_COMP_QUALITY_ATTRIB = 
    {
        "very_high",
        "high",
        "above_average",
        "average",
        "below_average",
        "low",
        "very_low"
    };
    public static final String[] BIO_COMP_EFFECT_FOOD_MODS = 
    {
        "duration",
        "flavor",
        "health_mod",
        "con_mod",
        "action_mod",
        "stam_mod",
        "mind_mod",
        "will_mod"
    };
    public static final String[] BIO_COMP_EFFECT_SKILL_MODS = 
    {
        "debuffing_efficiency",
        "augmentation_efficiency",
        "cure_efficiency",
        "tame_non_aggro",
        "tame_aggro",
        "creature_training",
        "creature_empathy",
        "healing_music_shock",
        "healing_dance_shock",
        "business_accumen",
        "camouflage",
        "creature_harvesting",
        "surveying",
        "healing_efficiency",
        "ranged_defense",
        "melee_defense",
        "slope_move",
        "unarmed_damage",
        "mask_scent"
    };
    public static final int[] CREATURE_SPECIAL_ATTACK_LIST = 
    {
        (-1328770430),
        (1524908377),
        (-827784351),
        (830990453),
        (-2010795594),
        (847492984),
        (-38093787),
        (-225405717),
        (1245394953),
        (-1229626420),
        (1467687391),
        (540899646)
    };
    public static final String[] DNA_TEMPLATE_SLOT_NAMES = 
    {
        "aggression_profile",
        "psychological_profile",
        "mental_profile",
        "prowess_profile",
        "physique_profile"
    };
    public static final String CREATURE_DICT_NAME = "creatureName";
    public static final String CREATURE_DICT_MIN_HEALTH = "minHealth";
    public static final String CREATURE_DICT_MAX_HEALTH = "maxHealth";
    public static final String CREATURE_DICT_MIN_CONSTITUTION = "minConstitution";
    public static final String CREATURE_DICT_MAX_CONSTITUTION = "maxConstitution";
    public static final String CREATURE_DICT_MIN_ACTION = "minAction";
    public static final String CREATURE_DICT_MAX_ACTION = "maxAction";
    public static final String CREATURE_DICT_MIN_STAMINA = "minStamina";
    public static final String CREATURE_DICT_MAX_STAMINA = "maxStamina";
    public static final String CREATURE_DICT_MIN_MIND = "minMind";
    public static final String CREATURE_DICT_MAX_MIND = "maxMind";
    public static final String CREATURE_DICT_MIN_WILLPOWER = "minWillpower";
    public static final String CREATURE_DICT_MAX_WILLPOWER = "maxWillpower";
    public static final String CREATURE_DICT_MIN_SCALE = "minScale";
    public static final String CREATURE_DICT_MAX_SCALE = "maxScale";
    public static final String CREATURE_DICT_CRAFTED_PET = "craftedPet";
    public static final String CREATURE_DICT_MEAT = "meatType";
    public static final String CREATURE_DICT_NICHE = "niche";
    public static final String CREATURE_DICT_LEVEL = "level";
    public static final String CREATURE_DICT_HEALTH_REGEN = "healthRegen";
    public static final String CREATURE_DICT_TO_HIT = "toHit";
    public static final String CREATURE_DICT_DEFENSE_VALUE = "defenseValue";
    public static final String CREATURE_DICT_MIN_DAMAGE = "minDamage";
    public static final String CREATURE_DICT_MAX_DAMAGE = "maxDamage";
    public static final String CREATURE_DICT_STATE_RESIST = "stateResist";
    public static final String CREATURE_DICT_ARMOR_BASE = "armorBase";
    public static final String CREATURE_DICT_RANGED_WEAPON = "rangedWeapon";
    public static final String CREATURE_DICT_SPECIAL_ATTACK_1 = "specialAttack1";
    public static final String CREATURE_DICT_SPECIAL_ATTACK_2 = "specialAttack2";
    public static final String PROFILE_DICT_MIN_HARDINESS = "minHardiness";
    public static final String PROFILE_DICT_MAX_HARDINESS = "maxHardiness";
    public static final String PROFILE_DICT_MIN_FORTITUDE = "minFortitude";
    public static final String PROFILE_DICT_MAX_FORTITUDE = "maxFortitude";
    public static final String PROFILE_DICT_MIN_DEXTERITY = "minDexterity";
    public static final String PROFILE_DICT_MAX_DEXTERITY = "maxDexterity";
    public static final String PROFILE_DICT_MIN_ENDURANCE = "minEndurance";
    public static final String PROFILE_DICT_MAX_ENDURANCE = "maxEndurance";
    public static final String PROFILE_DICT_MIN_INTELLECT = "minIntellect";
    public static final String PROFILE_DICT_MAX_INTELLECT = "maxIntellect";
    public static final String PROFILE_DICT_MIN_CLEVERNESS = "minCleverness";
    public static final String PROFILE_DICT_MAX_CLEVERNESS = "maxCleverness";
    public static final String PROFILE_DICT_MIN_DEPENDABILITY = "minDependability";
    public static final String PROFILE_DICT_MAX_DEPENDABILITY = "maxDependability";
    public static final String PROFILE_DICT_MIN_COURAGE = "minCourage";
    public static final String PROFILE_DICT_MAX_COURAGE = "maxCourage";
    public static final String PROFILE_DICT_MIN_FIERCENESS = "minFierceness";
    public static final String PROFILE_DICT_MAX_FIERCENESS = "maxFierceness";
    public static final String PROFILE_DICT_MIN_POWER = "minPower";
    public static final String PROFILE_DICT_MAX_POWER = "maxPower";
    public static final String OBJ_VAR_ATTRIB_PREFIX = "dna_attributes.";
    public static final String ATTRIB_DICT_QUALITY = "quality";
    public static final String ATTRIB_DICT_SOURCE = "source";
    public static final String ATTRIB_DICT_HARDINESS = "hardiness";
    public static final String ATTRIB_DICT_FORTITUDE = "fortitude";
    public static final String ATTRIB_DICT_DEXTERITY = "dexterity";
    public static final String ATTRIB_DICT_ENDURANCE = "endurance";
    public static final String ATTRIB_DICT_INTELLECT = "intellect";
    public static final String ATTRIB_DICT_CLEVERNESS = "cleverness";
    public static final String ATTRIB_DICT_DEPENDABILITY = "dependability";
    public static final String ATTRIB_DICT_COURAGE = "courage";
    public static final String ATTRIB_DICT_FIERCENESS = "fierceness";
    public static final String ATTRIB_DICT_POWER = "power";
    public static final String ATTRIB_DICT_MEAT = "meatType";
    public static final String ATTRIB_DICT_LEVEL = "level";
    public static final String ATTRIB_DICT_HEALTH_REGEN = "healthRegen";
    public static final String ATTRIB_DICT_TO_HIT = "toHit";
    public static final String ATTRIB_DICT_MIN_DAMAGE = "minDamage";
    public static final String ATTRIB_DICT_MAX_DAMAGE = "maxDamage";
    public static final String ATTRIB_DICT_ARMOR_BASE = "armorBase";
    public static final String ATTRIB_DICT_ARMOR_DATA = "armorData";
    public static final String ATTRIB_DICT_RANGED_WEAPON = "rangedWeapon";
    public static final String ATTRIB_DICT_SPECIAL_ATTACK_1 = "specialAttack1";
    public static final String ATTRIB_DICT_SPECIAL_ATTACK_2 = "specialAttack2";
    public static final int CREATURE_ATTRIB_HARDINESS = 0;
    public static final int CREATURE_ATTRIB_FORTITUDE = 1;
    public static final int CREATURE_ATTRIB_DEXTERITY = 2;
    public static final int CREATURE_ATTRIB_ENDURANCE = 3;
    public static final int CREATURE_ATTRIB_INTELLECT = 4;
    public static final int CREATURE_ATTRIB_CLEVERNESS = 5;
    public static final int CREATURE_ATTRIB_DEPENDABILITY = 6;
    public static final int CREATURE_ATTRIB_COURAGE = 7;
    public static final int CREATURE_ATTRIB_FIERCENESS = 8;
    public static final int CREATURE_ATTRIB_POWER = 9;
    public static final int CREATURE_ATTRIB_QUALITY = 10;
    public static final int CREATURE_ATTRIB_NUM = 11;
    public static final String[] CREATURE_ATTRIB_OBJVAR_NAMES = 
    {
        ATTRIB_DICT_HARDINESS,
        ATTRIB_DICT_FORTITUDE,
        ATTRIB_DICT_DEXTERITY,
        ATTRIB_DICT_ENDURANCE,
        ATTRIB_DICT_INTELLECT,
        ATTRIB_DICT_CLEVERNESS,
        ATTRIB_DICT_DEPENDABILITY,
        ATTRIB_DICT_COURAGE,
        ATTRIB_DICT_FIERCENESS,
        ATTRIB_DICT_POWER,
        ATTRIB_DICT_QUALITY
    };
    public static final int CREATURE_ATTRIB_WEIGHT_HEALTH = 0;
    public static final int CREATURE_ATTRIB_WEIGHT_CONSTITUTION = 1;
    public static final int CREATURE_ATTRIB_WEIGHT_DEFENSE = 2;
    public static final int CREATURE_ATTRIB_WEIGHT_STAMINA = 3;
    public static final int CREATURE_ATTRIB_WEIGHT_STATE_RESIST = 4;
    public static final int CREATURE_ATTRIB_WEIGHT_WILLPOWER = 5;
    public static final int CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN = 6;
    public static final int CREATURE_ATTRIB_WEIGHT_TO_HIT = 7;
    public static final int CREATURE_ATTRIB_WEIGHT_SKITTISHNESS = 8;
    public static final int CREATURE_ATTRIB_WEIGHT_SKITTISH_CURVE = 9;
    public static final int CREATURE_ATTRIB_WEIGHT_SAVE = 10;
    public static final int CREATURE_ATTRIB_WEIGHT_CRIT = 11;
    public static final int CREATURE_ATTRIB_WEIGHT_AGGRO = 12;
    public static final int CREATURE_ATTRIB_WEIGHT_DAMAGE = 13;
    public static final int CREATURE_ATTRIB_WEIGHT_ARMOR = 14;
    public static final int CREATURE_ATTRIB_WEIGHT_COUNT = 15;
    public static final int[][] CREATURE_ATTRIB_WEIGHTS = 
    {
        
        {
            CREATURE_ATTRIB_HARDINESS,
            10,
            CREATURE_ATTRIB_DEXTERITY,
            2
        },
        
        {
            CREATURE_ATTRIB_HARDINESS,
            10,
            CREATURE_ATTRIB_FORTITUDE,
            0
        },
        
        {
            CREATURE_ATTRIB_DEXTERITY,
            10,
            CREATURE_ATTRIB_INTELLECT,
            2
        },
        
        {
            CREATURE_ATTRIB_DEXTERITY,
            10,
            CREATURE_ATTRIB_ENDURANCE,
            0
        },
        
        {
            CREATURE_ATTRIB_INTELLECT,
            10,
            CREATURE_ATTRIB_HARDINESS,
            2
        },
        
        {
            CREATURE_ATTRIB_INTELLECT,
            10,
            CREATURE_ATTRIB_CLEVERNESS,
            0
        },
        
        {
            CREATURE_ATTRIB_ENDURANCE,
            10,
            CREATURE_ATTRIB_DEPENDABILITY,
            2
        },
        
        {
            CREATURE_ATTRIB_CLEVERNESS,
            10,
            CREATURE_ATTRIB_INTELLECT,
            0
        },
        
        {
            CREATURE_ATTRIB_DEPENDABILITY,
            10,
            CREATURE_ATTRIB_COURAGE,
            0
        },
        
        {
            CREATURE_ATTRIB_DEPENDABILITY,
            10,
            CREATURE_ATTRIB_COURAGE,
            0
        },
        
        {
            CREATURE_ATTRIB_DEPENDABILITY,
            10,
            CREATURE_ATTRIB_ENDURANCE,
            0
        },
        
        {
            CREATURE_ATTRIB_FIERCENESS,
            10,
            CREATURE_ATTRIB_POWER,
            0
        },
        
        {
            CREATURE_ATTRIB_COURAGE,
            10,
            CREATURE_ATTRIB_CLEVERNESS,
            0
        },
        
        {
            CREATURE_ATTRIB_POWER,
            10,
            CREATURE_ATTRIB_COURAGE,
            0
        },
        
        {
            CREATURE_ATTRIB_FORTITUDE,
            10,
            CREATURE_ATTRIB_ENDURANCE,
            0
        }
    };
    public static final String DNA_V_HIGH_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_very_high.iff";
    public static final String DNA_HIGH_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_high.iff";
    public static final String DNA_A_AVG_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_above_average.iff";
    public static final String DNA_AVG_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_average.iff";
    public static final String DNA_B_AVG_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_below_average.iff";
    public static final String DNA_LOW_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_low.iff";
    public static final String DNA_V_LOW_QUALITY_OBJ_TEMPLATE = "object/tangible/component/dna/dna_sample_very_low.iff";
    public static final String DATATABLE_GENE_PROFILE_TABLE = "datatables/bio_engineer/gene_profiles.iff";
    public static final String DATATABLE_COL_CREATURE_PROFILE = "geneProfile";
    public static final String VAR_DNA_HARVEST_COUNT = "bioEngineer.dnaHarvestCount";
    public static final string_id SID_HARVEST_DNA = new string_id("sui", "harvest_dna");
    public static final string_id SID_HARVEST_DNA_SUCCEED = new string_id("error_message", "harvest_dna_succeed");
    public static final string_id PROSE_HARVEST_DNA_SUCCEED = new string_id("error_message", "prose_harvest_dna_succeed");
    public static final string_id SID_HARVEST_DNA_FAILED = new string_id("error_message", "harvest_dna_failed");
    public static final string_id PROSE_HARVEST_DNA_FAILED = new string_id("error_message", "prose_harvest_dna_failed");
    public static final string_id SID_HARVEST_ALREADY_HARVESTING = new string_id("bio_engineer", "harvest_dna_already_harvesting");
    public static final string_id SID_HARVEST_NEED_TARGET = new string_id("bio_engineer", "harvest_dna_need_target");
    public static final string_id SID_HARVEST_SKILL_TOO_LOW = new string_id("bio_engineer", "harvest_dna_skill_too_low");
    public static final string_id SID_HARVEST_CREATURE_IN_COMBAT = new string_id("bio_engineer", "harvest_dna_creature_in_combat");
    public static final string_id SID_HARVEST_FAILED = new string_id("bio_engineer", "harvest_dna_failed");
    public static final string_id SID_HARVEST_SUCCEED = new string_id("bio_engineer", "harvest_dna_succeed");
    public static final string_id SID_HARVEST_INVALID_TARGET = new string_id("bio_engineer", "harvest_dna_invalid_target");
    public static final string_id SID_HARVEST_TARGET_CORPSE = new string_id("bio_engineer", "harvest_dna_target_corpse");
    public static final string_id SID_HARVEST_TARGET_PET = new string_id("bio_engineer", "harvest_dna_target_pet");
    public static final string_id SID_HARVEST_TARGET_BABY = new string_id("bio_engineer", "harvest_dna_target_baby");
    public static final string_id SID_HARVEST_CANT_HARVEST = new string_id("bio_engineer", "harvest_dna_cant_harvest");
    public static final string_id SID_HARVEST_OUT_OF_RANGE = new string_id("bio_engineer", "harvest_dna_out_of_range");
    public static final string_id SID_HARVEST_ATTRIB_TOO_LOW = new string_id("bio_engineer", "harvest_dna_attrib_too_low");
    public static final string_id SID_HARVEST_BEGIN_HARVEST = new string_id("bio_engineer", "harvest_dna_begin_harvest");
    public static final string_id SID_NEW_LEVEL_TOO_HIGH = new string_id("bio_engineer", "pet_sui_level_too_high");
    public static final string_id SID_PET_SUI_TITLE = new string_id("bio_engineer", "pet_sui_title");
    public static final string_id SID_PET_SUI_TEXT = new string_id("bio_engineer", "pet_sui_text");
    public static final string_id SID_PET_SUI_FIX_LEVEL = new string_id("bio_engineer", "pet_sui_fix_level");
    public static final string_id SID_PET_SUI_FIX_STATS = new string_id("bio_engineer", "pet_sui_fix_stats");
    public static final string_id SID_PET_SUI_ABORT = new string_id("bio_engineer", "pet_sui_abort");
    public static final string_id SID_PET_SUI_LEVEL_FIXED = new string_id("bio_engineer", "pet_sui_level_fixed");
    public static final string_id SID_PET_SUI_STATS_FIXED = new string_id("bio_engineer", "pet_sui_stats_fixed");
    public static final string_id SID_PET_SUI_FIX_ERROR = new string_id("bio_engineer", "pet_sui_fix_error");
    public static final String COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME = "crafting_component_resource_attribs";
    public static boolean harvestCreatureDNA(obj_id player, obj_id creature) throws InterruptedException
    {
        deltadictionary dctScriptVars = player.getScriptVars();
        if (dctScriptVars == null)
        {
            return false;
        }
        int skillMod = getSkillStatisticModifier(player, "dna_harvesting");
        if (skillMod < 1)
        {
            sendSystemMessage(player, SID_HARVEST_SKILL_TOO_LOW);
            return false;
        }
        int harvesting = dctScriptVars.getInt("bio_engineer.harvest_dna.harvesting");
        if (harvesting == 1)
        {
            int harvestTime = dctScriptVars.getInt("bio_engineer.harvest_dna.harvest_time");
            if ((getGameTime() - harvestTime) <= HARVEST_TIME)
            {
                sendSystemMessage(player, SID_HARVEST_ALREADY_HARVESTING);
                return false;
            }
        }
        if (!isIdValid(creature))
        {
            sendSystemMessage(player, SID_HARVEST_NEED_TARGET);
            return false;
        }
        if (pet_lib.hasMaster(creature))
        {
            sendSystemMessage(player, SID_HARVEST_TARGET_PET);
            return false;
        }
        if (hasScript(creature, "ai.pet_advance"))
        {
            sendSystemMessage(player, SID_HARVEST_TARGET_BABY);
            return false;
        }
        if (hasScript(creature, "corpse.ai_corpse"))
        {
            sendSystemMessage(player, SID_HARVEST_TARGET_CORPSE);
            return false;
        }
        String mobType = ai_lib.getCreatureName(creature);
        if (mobType == null || mobType.equals(""))
        {
            String err = "WARNING: bui_engineer::harvestCreatureDNA(" + creature + ") returning false because getCreatureName failed. Template=" + getTemplateName(creature) + ", IsAuthoritative=" + creature.isAuthoritative() + ". Stack Trace as follows:";
            CustomerServiceLog("creatureNameErrors", err);
            debugServerConsoleMsg(creature, err);
            Thread.dumpStack();
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        String creatureProfile = dataTableGetString(create.CREATURE_TABLE, mobType, DATATABLE_COL_CREATURE_PROFILE);
        if (creatureProfile == null || creatureProfile.equals("") || creatureProfile.equals("none"))
        {
            sendSystemMessage(player, SID_HARVEST_CANT_HARVEST);
            return false;
        }
        dictionary profileDict = dataTableGetRow(DATATABLE_GENE_PROFILE_TABLE, creatureProfile);
        if (profileDict == null)
        {
            LOG("dna_harvest", "ERROR: Invalid geneProfile, doesn't exist in gene_profile.tab");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, mobType);
        if (creatureDict == null)
        {
            LOG("dna_harvest", "ERROR:  Unable to read creature entry in Creature Table.");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        if (ai_lib.isInCombat(creature))
        {
            sendSystemMessage(player, SID_HARVEST_CREATURE_IN_COMBAT);
            return false;
        }
        int action = getAttrib(player, ACTION);
        int mind = getAttrib(player, MIND);
        int actioncost = 100;
        int mindcost = 250;
        if ((action < actioncost) || (mind < mindcost))
        {
            sendSystemMessage(player, SID_HARVEST_ATTRIB_TOO_LOW);
            return false;
        }
        drainAttributes(player, actioncost, mindcost);
        sendSystemMessage(player, SID_HARVEST_BEGIN_HARVEST);
        location loc = getLocation(player);
        dctScriptVars.put("bio_engineer.harvest_dna.harvesting", 1);
        dctScriptVars.put("bio_engineer.harvest_dna.harvest_time", getGameTime());
        dctScriptVars.put("bio_engineer.harvest_dna.creature", creature);
        dctScriptVars.put("bio_engineer.harvest_dna.loc", loc);
        dctScriptVars.put("bio_engineer.harvest_dna.mobType", mobType);
        dctScriptVars.put("bio_engineer.harvest_dna.creatureProfile", creatureProfile);
        dctScriptVars.put("bio_engineer.harvest_dna.profileDict", profileDict);
        dctScriptVars.put("bio_engineer.harvest_dna.creatureDict", creatureDict);
        int harvestTime = HARVEST_TIME;
        if (hasObjVar(player, "quick_dna_sample"))
        {
            harvestTime = 1;
        }
        messageTo(player, "completeHarvestDNA", null, harvestTime, false);
        if (!hasScript(creature, "ai.pet"))
        {
            setMaster(creature, player);
        }
        doAnimationAction(player, "heal_other");
        return true;
    }
    public static boolean harvestCreatureDeedDNA(obj_id player, obj_id creatureDeed) throws InterruptedException
    {
        deltadictionary dctScriptVars = player.getScriptVars();
        if (dctScriptVars == null)
        {
            return false;
        }
        int skillMod = getSkillStatisticModifier(player, "dna_harvesting");
        if (skillMod < 1)
        {
            sendSystemMessage(player, SID_HARVEST_SKILL_TOO_LOW);
            return false;
        }
        int harvesting = dctScriptVars.getInt("bio_engineer.harvest_dna.harvesting");
        if (harvesting == 1)
        {
            int harvestTime = dctScriptVars.getInt("bio_engineer.harvest_dna.harvest_time");
            if ((getGameTime() - harvestTime) <= HARVEST_TIME)
            {
                sendSystemMessage(player, SID_HARVEST_ALREADY_HARVESTING);
                return false;
            }
        }
        if (!isIdValid(creatureDeed))
        {
            sendSystemMessage(player, SID_HARVEST_NEED_TARGET);
            return false;
        }
        String mobType = getStringObjVar(creatureDeed, "creature_attribs.type");
        if (mobType == null || mobType.equals(""))
        {
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        String creatureProfile = dataTableGetString(create.CREATURE_TABLE, mobType, DATATABLE_COL_CREATURE_PROFILE);
        if (creatureProfile == null || creatureProfile.equals("") || creatureProfile.equals("none"))
        {
            sendSystemMessage(player, SID_HARVEST_CANT_HARVEST);
            return false;
        }
        dictionary profileDict = dataTableGetRow(DATATABLE_GENE_PROFILE_TABLE, creatureProfile);
        if (profileDict == null)
        {
            LOG("dna_harvest", "ERROR: Invalid geneProfile, doesn't exist in gene_profile.tab");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, mobType);
        if (creatureDict == null)
        {
            LOG("dna_harvest", "ERROR:  Unable to read creature entry in Creature Table.");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        int action = getAttrib(player, ACTION);
        int mind = getAttrib(player, MIND);
        int actioncost = 100;
        int mindcost = 250;
        if ((action < actioncost) || (mind < mindcost))
        {
            sendSystemMessage(player, SID_HARVEST_ATTRIB_TOO_LOW);
            return false;
        }
        drainAttributes(player, actioncost, mindcost);
        sendSystemMessage(player, SID_HARVEST_BEGIN_HARVEST);
        location loc = getLocation(player);
        dctScriptVars.put("bio_engineer.harvest_dna.harvesting", 1);
        dctScriptVars.put("bio_engineer.harvest_dna.harvest_time", getGameTime());
        dctScriptVars.put("bio_engineer.harvest_dna.creatureDeed", creatureDeed);
        dctScriptVars.put("bio_engineer.harvest_dna.loc", loc);
        dctScriptVars.put("bio_engineer.harvest_dna.mobType", mobType);
        dctScriptVars.put("bio_engineer.harvest_dna.creatureProfile", creatureProfile);
        dctScriptVars.put("bio_engineer.harvest_dna.profileDict", profileDict);
        dctScriptVars.put("bio_engineer.harvest_dna.creatureDict", creatureDict);
        int harvestTime = HARVEST_TIME;
        if (hasObjVar(player, "quick_dna_sample"))
        {
            harvestTime = 1;
        }
        messageTo(player, "completeHarvestDNA", null, harvestTime, false);
        return true;
    }
    public static boolean completeHarvest(obj_id player) throws InterruptedException
    {
        boolean failed = false;
        boolean error = false;
        boolean creatureSurvived = true;
        int harvestCount = 0;
        int survivalCheck = 0;
        deltadictionary dctScriptVars = player.getScriptVars();
        if (dctScriptVars == null)
        {
            return false;
        }
        obj_id creature = dctScriptVars.getObjId("bio_engineer.harvest_dna.creature");
        obj_id creatureDeed = dctScriptVars.getObjId("bio_engineer.harvest_dna.creatureDeed");
        location loc = dctScriptVars.getLocation("bio_engineer.harvest_dna.loc");
        String mobType = dctScriptVars.getString("bio_engineer.harvest_dna.mobType");
        String creatureProfile = dctScriptVars.getString("bio_engineer.harvest_dna.creatureProfile");
        dictionary profileDict = dctScriptVars.getDictionary("bio_engineer.harvest_dna.profileDict");
        dictionary creatureDict = dctScriptVars.getDictionary("bio_engineer.harvest_dna.creatureDict");
        boolean deed = false;
        if (!isIdValid(creature) || !exists(creature))
        {
            if (isIdValid(creatureDeed) && exists(creatureDeed))
            {
                creature = creatureDeed;
                deed = true;
            }
            else 
            {
                failed = true;
            }
        }
        int targetDiff = 0;
        if (deed)
        {
            targetDiff = getIntObjVar(creatureDeed, "creature_attribs.level");
        }
        else 
        {
            targetDiff = getLevel(creature);
        }
        LOG("dna_harvest", "************************* HARVESTING CREATURE DNA *************************");
        if (!failed && hasScript(creature, "corpse.ai_corpse"))
        {
            sendSystemMessage(player, SID_HARVEST_TARGET_CORPSE);
            failed = true;
        }
        if (!failed && ai_lib.isInCombat(creature))
        {
            sendSystemMessage(player, SID_HARVEST_CREATURE_IN_COMBAT);
            failed = true;
        }
        if (!failed && !deed && getDistance(player, creature) > 16)
        {
            sendSystemMessage(player, SID_HARVEST_OUT_OF_RANGE);
            failed = true;
        }
        if (!deed)
        {
            if (!isIdValid(creature))
            {
                return false;
            }
            if (!hasScript(creature, "ai.pet"))
            {
                setMaster(creature, null);
            }
            doAnimationAction(player, "heal_other");
        }
        float skillMod = getSkillStatisticModifier(player, "dna_harvesting");
        float levelMod = targetDiff - (skillMod / 1.5f);
        if (levelMod < 0)
        {
            levelMod = 0;
        }
        float difficulty = ((skillMod / 1.5f) + 35) - (levelMod * (skillMod / 10));
        if (difficulty > 85)
        {
            difficulty = 85;
        }
        if (difficulty < 0)
        {
            difficulty = 0;
        }
        int dieRoll = rand(1, 100);
        if (dieRoll > difficulty || failed)
        {
            failed = true;
        }
        else 
        {
            LOG("dna_harvest", "Creature: " + mobType);
            LOG("dna_harvest", "geneProfile: " + creatureProfile);
            dictionary creatureStats = null;
            if (deed)
            {
                creatureStats = getCreatureDeedStats(creature);
            }
            else 
            {
                creatureStats = getCreatureStats(creature);
            }
            if (creatureStats == null)
            {
                LOG("dna_harvest", "ERROR: Failed creation of DNA dictionary.");
                error = true;
            }
            dictionary attributeDict = calculateDNAAttributes(player, creatureStats, profileDict, -1);
            if (attributeDict == null)
            {
                LOG("dna_harvest", "ERROR: Failed creation of DNA dictionary.");
                error = true;
            }
            obj_id inventory = utils.getInventoryContainer(player);
            if (inventory == null || inventory == obj_id.NULL_ID)
            {
                LOG("dna_harvest", "ERROR: Failed to find the player's inventory.");
                error = true;
            }
            obj_id DNA_obj = createDNAComponent(player, inventory, attributeDict);
            if (DNA_obj == null || DNA_obj == obj_id.NULL_ID)
            {
                LOG("dna_harvest", "ERROR: Failed creation of DNA object.");
                error = true;
            }
            if (!error)
            {
                harvestCount = getIntObjVar(creature, VAR_DNA_HARVEST_COUNT);
                if (harvestCount < 0)
                {
                    harvestCount = 0;
                }
                harvestCount++;
                setObjVar(creature, VAR_DNA_HARVEST_COUNT, harvestCount);
                survivalCheck = (int)((skillMod - targetDiff) * ((100 - (harvestCount * 10)) / 100f) * 2);
                if (survivalCheck < 0)
                {
                    survivalCheck = 0;
                }
                if (survivalCheck > 100)
                {
                    survivalCheck = 100;
                }
                dieRoll = rand(1, 100);
                if (dieRoll > survivalCheck)
                {
                    creatureSurvived = false;
                    if (deed)
                    {
                        destroyObject(creature);
                    }
                    else 
                    {
                        if (pet_lib.hasMaster(creature))
                        {
                            setMaster(creature, null);
                            pet_lib.releasePet(creature);
                        }
                        attacker_results cbtAttackerResults = new attacker_results();
                        cbtAttackerResults.endPosture = POSTURE_DEAD;
                        cbtAttackerResults.id = creature;
                        doCombatResults("posture_scramble", cbtAttackerResults, null);
                        attachScript(creature, corpse.SCRIPT_AI_CORPSE);
                    }
                }
            }
        }
        if (creatureSurvived && !error)
        {
            float attackCheck = ((8 - (skillMod / 30f)) * (harvestCount - 1) / 4f);
            attackCheck = 10 + (attackCheck * attackCheck);
            if (attackCheck > 100)
            {
                attackCheck = 100;
            }
            float frenzyCheck = ((8 - (skillMod / 15f)) * (harvestCount - 1));
            frenzyCheck = 30 + (frenzyCheck * frenzyCheck);
            if (frenzyCheck > 100)
            {
                frenzyCheck = 100;
            }
            dieRoll = rand(1, 100);
            if (dieRoll < attackCheck)
            {
                if (deed)
                {
                    destroyObject(creature);
                }
                else 
                {
                    if (pet_lib.hasMaster(creature))
                    {
                        setMaster(creature, null);
                        pet_lib.releasePet(creature);
                    }
                    startCombat(creature, player);
                }
            }
            else if (dieRoll < frenzyCheck)
            {
                if (!deed)
                {
                    addToMentalStateToward(creature, player, FEAR, 45.0f);
                }
            }
        }
        dctScriptVars.remove("bio_engineer.harvest_dna.harvesting");
        dctScriptVars.remove("bio_engineer.harvest_dna.harvest_time");
        dctScriptVars.remove("bio_engineer.harvest_dna.creature");
        dctScriptVars.remove("bio_engineer.harvest_dna.creatureDeed");
        dctScriptVars.remove("bio_engineer.harvest_dna.loc");
        dctScriptVars.remove("bio_engineer.harvest_dna.mobType");
        dctScriptVars.remove("bio_engineer.harvest_dna.creatureProfile");
        dctScriptVars.remove("bio_engineer.harvest_dna.profileDict");
        dctScriptVars.remove("bio_engineer.harvest_dna.creatureDict");
        if (failed || error)
        {
            LOG("dna_harvest", "FAILED: Failed skill check.");
            sendSystemMessage(player, SID_HARVEST_FAILED);
            return false;
        }
        else 
        {
            sendSystemMessage(player, SID_HARVEST_SUCCEED);
            int xpAmount = 0;
            xpAmount = (int)(Math.pow(targetDiff, 1.2f) * 5f) + 50;
            if (xpAmount > 2500)
            {
                xpAmount = 2500;
            }
            if (deed)
            {
                xpAmount = xpAmount / 4;
            }
            if (xpAmount > 0)
            {
                xp.grant(player, xp.BIO_ENGINEER_DNA_HARVESTING, xpAmount);
            }
            LOG("dna_harvest", "SUCCESS: " + xpAmount + " XP awarded.");
        }
        return true;
    }
    public static boolean quickHarvest(obj_id player, String creatureName) throws InterruptedException
    {
        dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, creatureName);
        if (creatureDict == null)
        {
            LOG("dna_harvest", "ERROR:  Unable to read creature entry in Creature Table.");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        String creatureProfile = creatureDict.getString(DATATABLE_COL_CREATURE_PROFILE);
        if (creatureProfile == null || creatureProfile.equals("") || creatureProfile.equals("none"))
        {
            sendSystemMessage(player, SID_HARVEST_CANT_HARVEST);
            return false;
        }
        dictionary profileDict = dataTableGetRow(DATATABLE_GENE_PROFILE_TABLE, creatureProfile);
        if (profileDict == null)
        {
            LOG("dna_harvest", "ERROR: Invalid geneProfile, doesn't exist in gene_profile.tab");
            sendSystemMessage(player, SID_HARVEST_INVALID_TARGET);
            return false;
        }
        int baseLevel = creatureDict.getInt("BaseLevel");
        int dmgLevel = baseLevel + creatureDict.getInt("Damagelevelmodifier");
        int statLevel = baseLevel + creatureDict.getInt("StatLevelModifier");
        int toHitLevel = baseLevel + creatureDict.getInt("ToHitLevelModifier");
        int armorLevel = baseLevel + creatureDict.getInt("ArmorLevelModifier");
        if (dmgLevel <= 0)
        {
            dmgLevel = 1;
        }
        if (statLevel <= 0)
        {
            statLevel = 1;
        }
        if (toHitLevel <= 0)
        {
            toHitLevel = 1;
        }
        if (armorLevel <= 0)
        {
            armorLevel = 1;
        }
        dictionary creatureStats = new dictionary();
        int avgAttrib = dataTableGetInt(create.STAT_BALANCE_TABLE, statLevel - 1, "HP");
        int minAttrib = minAttrib = (int)(avgAttrib * 0.9f);
        int maxAttrib = maxAttrib = (int)(avgAttrib * 1.1f);
        float minScale = creatureDict.getFloat("minScale");
        float maxScale = creatureDict.getFloat("maxScale");
        float dps = dataTableGetFloat(create.STAT_BALANCE_TABLE, dmgLevel - 1, "damagePerSecond");
        int minDamage = Math.round((dps * 2.0f) * 0.5f);
        int maxDamage = Math.round((dps * 2.0f) * 1.5f);
        creatureStats.put(CREATURE_DICT_NAME, ("mob/creature_names:" + creatureName));
        creatureStats.put(CREATURE_DICT_LEVEL, create.calcCreatureLevel(statLevel, dmgLevel, toHitLevel, armorLevel));
        creatureStats.put(CREATURE_DICT_MAX_HEALTH, rand(minAttrib, maxAttrib));
        creatureStats.put(CREATURE_DICT_HEALTH_REGEN, dataTableGetInt(create.STAT_BALANCE_TABLE, statLevel - 1, "HealthRegen"));
        creatureStats.put(CREATURE_DICT_TO_HIT, dataTableGetInt(create.STAT_BALANCE_TABLE, toHitLevel - 1, "ToHit"));
        creatureStats.put(CREATURE_DICT_DEFENSE_VALUE, dataTableGetInt(create.STAT_BALANCE_TABLE, toHitLevel - 1, "Def"));
        creatureStats.put(CREATURE_DICT_ARMOR_BASE, dataTableGetInt(create.STAT_BALANCE_TABLE, armorLevel - 1, "Armor"));
        creatureStats.put(CREATURE_DICT_MIN_DAMAGE, minDamage);
        creatureStats.put(CREATURE_DICT_MAX_DAMAGE, maxDamage);
        creatureStats.put(CREATURE_DICT_RANGED_WEAPON, "");
        creatureStats.put(CREATURE_DICT_MAX_SCALE, rand(minScale, maxScale));
        creatureStats.put(CREATURE_DICT_NICHE, creatureDict.getInt("niche"));
        creatureStats.put(CREATURE_DICT_MEAT, creatureDict.getString("meatType"));
        dictionary attributeDict = calculateDNAAttributes(player, creatureStats, profileDict, -1);
        if (attributeDict == null)
        {
            LOG("dna_harvest", "ERROR: Failed creation of DNA dictionary.");
            sendSystemMessage(player, SID_HARVEST_FAILED);
            return false;
        }
        obj_id inventory = utils.getInventoryContainer(player);
        if (inventory == null || inventory == obj_id.NULL_ID)
        {
            LOG("dna_harvest", "ERROR: Failed to find the player's inventory.");
            sendSystemMessage(player, SID_HARVEST_FAILED);
            return false;
        }
        obj_id DNA_obj = createDNAComponent(player, inventory, attributeDict);
        if (DNA_obj == null || DNA_obj == obj_id.NULL_ID)
        {
            LOG("dna_harvest", "ERROR: Failed creation of DNA object.");
            sendSystemMessage(player, SID_HARVEST_FAILED);
            return false;
        }
        sendSystemMessage(player, SID_HARVEST_SUCCEED);
        return true;
    }
    public static dictionary getCreatureStats(obj_id creature) throws InterruptedException
    {
        dictionary creatureStats = new dictionary();
        String creatureName = (getNameStringId(creature)).toString();
        char openParen = '(';
        if (creatureName.indexOf(openParen) != -1)
        {
            creatureName = creatureName.substring(0, (creatureName.indexOf(openParen) - 1));
        }
        creatureStats.put(CREATURE_DICT_NAME, creatureName);
        creatureStats.put(CREATURE_DICT_MAX_HEALTH, getMaxHealth(creature));
        creatureStats.put(CREATURE_DICT_HEALTH_REGEN, getMaxConst(creature));
        creatureStats.put(CREATURE_DICT_LEVEL, getLevel(creature));
        creatureStats.put(CREATURE_DICT_NICHE, dataTableGetInt(create.CREATURE_TABLE, creatureName, "niche"));
        creatureStats.put(CREATURE_DICT_MEAT, dataTableGetString(create.CREATURE_TABLE, creatureName, "meatType"));
        if (callable.hasCallableCD(creature))
        {
            creatureStats.put(CREATURE_DICT_CRAFTED_PET, 1);
            obj_id pcd = callable.getCallableCD(creature);
            creatureStats.put(CREATURE_DICT_TO_HIT, getIntObjVar(pcd, "creature_attribs.toHitChance"));
            creatureStats.put(CREATURE_DICT_DEFENSE_VALUE, getIntObjVar(pcd, "creature_attribs.defenseValue"));
            creatureStats.put(CREATURE_DICT_ARMOR_BASE, getIntObjVar(pcd, "creature_attribs.general_protection"));
            creatureStats.put(CREATURE_DICT_MIN_DAMAGE, getIntObjVar(pcd, "creature_attribs." + ATTRIB_DICT_MIN_DAMAGE));
            creatureStats.put(CREATURE_DICT_MAX_DAMAGE, getIntObjVar(pcd, "creature_attribs." + ATTRIB_DICT_MAX_DAMAGE));
            creatureStats.put(CREATURE_DICT_RANGED_WEAPON, getStringObjVar(pcd, "creature_attribs." + ATTRIB_DICT_RANGED_WEAPON));
            creatureStats.put(CREATURE_DICT_MAX_SCALE, getFloatObjVar(pcd, "creature_attribs.scale"));
        }
        else 
        {
            obj_id primaryWeapon = aiGetPrimaryWeapon(creature);
            creatureStats.put(CREATURE_DICT_TO_HIT, getSkillStatMod(creature, "toHitChance"));
            creatureStats.put(CREATURE_DICT_DEFENSE_VALUE, getSkillStatMod(creature, "ranged_defense"));
            creatureStats.put(CREATURE_DICT_ARMOR_BASE, (int)getFloatObjVar(creature, armor.OBJVAR_ARMOR_BASE + "." + armor.OBJVAR_GENERAL_PROTECTION));
            creatureStats.put(CREATURE_DICT_MIN_DAMAGE, getWeaponMinDamage(primaryWeapon));
            creatureStats.put(CREATURE_DICT_MAX_DAMAGE, getWeaponMaxDamage(primaryWeapon));
            creatureStats.put(CREATURE_DICT_RANGED_WEAPON, "");
            float baseScale = 1f;
            if (utils.hasScriptVar(creature, "ai.baseScale"))
            {
                baseScale = utils.getFloatScriptVar(creature, "ai.baseScale");
            }
            creatureStats.put(CREATURE_DICT_MAX_SCALE, getScale(creature) / baseScale);
        }
        return creatureStats;
    }
    public static dictionary getCreatureDeedStats(obj_id creatureDeed) throws InterruptedException
    {
        dictionary creatureStats = new dictionary();
        String creatureName = "mob/creature_names:" + getStringObjVar(creatureDeed, "creature_attribs.type");
        creatureStats.put(CREATURE_DICT_CRAFTED_PET, 1);
        creatureStats.put(CREATURE_DICT_NAME, creatureName);
        creatureStats.put(CREATURE_DICT_MAX_HEALTH, getIntObjVar(creatureDeed, "creature_attribs.maxHealth"));
        creatureStats.put(CREATURE_DICT_HEALTH_REGEN, getIntObjVar(creatureDeed, "creature_attribs.maxConstitution"));
        creatureStats.put(CREATURE_DICT_LEVEL, getIntObjVar(creatureDeed, "creature_attribs." + ATTRIB_DICT_LEVEL));
        creatureStats.put(CREATURE_DICT_TO_HIT, getIntObjVar(creatureDeed, "creature_attribs.toHitChance"));
        creatureStats.put(CREATURE_DICT_DEFENSE_VALUE, getIntObjVar(creatureDeed, "creature_attribs.defenseValue"));
        creatureStats.put(CREATURE_DICT_ARMOR_BASE, getIntObjVar(creatureDeed, "creature_attribs.general_protection"));
        creatureStats.put(CREATURE_DICT_MIN_DAMAGE, getIntObjVar(creatureDeed, "creature_attribs." + ATTRIB_DICT_MIN_DAMAGE));
        creatureStats.put(CREATURE_DICT_MAX_DAMAGE, getIntObjVar(creatureDeed, "creature_attribs." + ATTRIB_DICT_MAX_DAMAGE));
        creatureStats.put(CREATURE_DICT_RANGED_WEAPON, getStringObjVar(creatureDeed, "creature_attribs." + ATTRIB_DICT_RANGED_WEAPON));
        creatureStats.put(CREATURE_DICT_MAX_SCALE, getFloatObjVar(creatureDeed, "creature_attribs.scale"));
        return creatureStats;
    }
    public static dictionary calculateDNAAttributes(obj_id player, dictionary creatureStats, dictionary profileDict, int qualityOverride) throws InterruptedException
    {
        String creatureName = creatureStats.getString(CREATURE_DICT_NAME);
        int level = creatureStats.getInt(CREATURE_DICT_LEVEL);
        int health = creatureStats.getInt(CREATURE_DICT_MAX_HEALTH);
        float healthRegen = creatureStats.getInt(CREATURE_DICT_HEALTH_REGEN);
        int toHit = creatureStats.getInt(CREATURE_DICT_TO_HIT);
        int defenseValue = creatureStats.getInt(CREATURE_DICT_DEFENSE_VALUE);
        int minDamage = creatureStats.getInt(CREATURE_DICT_MIN_DAMAGE);
        int maxDamage = creatureStats.getInt(CREATURE_DICT_MAX_DAMAGE);
        int armorBase = creatureStats.getInt(CREATURE_DICT_ARMOR_BASE);
        String rangedWeapon = creatureStats.getString(CREATURE_DICT_RANGED_WEAPON);
        int specialAttack1 = 0;
        int specialAttack2 = 0;
        float maxScale = creatureStats.getFloat(CREATURE_DICT_MAX_SCALE);
        int niche = creatureStats.getInt(CREATURE_DICT_NICHE);
        String meat = creatureStats.getString(CREATURE_DICT_MEAT);
        LOG("creature_dna", "healthRegen = " + healthRegen);
        LOG("creature_dna", "toHit = " + toHit);
        LOG("creature_dna", "defenseValue = " + defenseValue);
        LOG("creature_dna", "minDamage = " + minDamage);
        LOG("creature_dna", "maxDamage = " + maxDamage);
        LOG("creature_dna", "armorBase = " + armorBase);
        LOG("creature_dna", "rangedWeapon = " + rangedWeapon);
        if (rangedWeapon == null)
        {
            rangedWeapon = "";
        }
        int skillMod = 0;
        if (player != null)
        {
            skillMod = getSkillStatisticModifier(player, "dna_harvesting");
        }
        int qualityRoll = rand(0, 50) + skillMod;
        int qualityRank = 0;
        if (qualityRoll > DNA_V_LOW_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_V_LOW_QUALITY;
        }
        if (qualityRoll > DNA_LOW_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_LOW_QUALITY;
        }
        if (qualityRoll > DNA_B_AVG_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_B_AVG_QUALITY;
        }
        if (qualityRoll > DNA_AVG_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_AVG_QUALITY;
        }
        if (qualityRoll > DNA_A_AVG_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_A_AVG_QUALITY;
        }
        if (qualityRoll > DNA_HIGH_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_HIGH_QUALITY;
        }
        if (qualityRoll > DNA_V_HIGH_QUALITY_THRESHOLD)
        {
            qualityRank = DNA_V_HIGH_QUALITY;
        }
        if (qualityOverride >= 0)
        {
            qualityRank = qualityOverride;
        }
        LOG("dna_harvest", "DNA Harvesting: +" + skillMod);
        LOG("dna_harvest", "DNA Quality: " + qualityRank);
        int minQualityMod = (qualityRank - 6) * 5;
        int maxQualityMod = (qualityRank - 4) * 5;
        int Hardiness = calcDnaHardiness(health) + rand(minQualityMod, maxQualityMod);
        int Fortitude = calcDnaFortitude(armorBase) + rand(minQualityMod, maxQualityMod);
        int Cleverness = calcDnaCleverness(toHit) + rand(minQualityMod, maxQualityMod);
        int Dexterity = calcDnaDexterity(defenseValue) + rand(minQualityMod, maxQualityMod);
        int Power = calcDnaPower(((maxDamage + minDamage) / 2)) + rand(minQualityMod, maxQualityMod);
        int Endurance = calcDnaEndurance(healthRegen) + rand(minQualityMod, maxQualityMod);
        int Intellect = calcDnaIntellect(maxScale) + rand(minQualityMod, maxQualityMod);
        int Fierceness = calcDnaFierceness(level, niche) + rand(minQualityMod, maxQualityMod);
        int Dependability = calcDnaDependability(level, niche) + rand(minQualityMod, maxQualityMod);
        int Courage = calcDnaCourage(level, meat) + rand(minQualityMod, maxQualityMod);
        if (Hardiness < 0)
        {
            Hardiness = 0;
        }
        if (Hardiness > 1000)
        {
            Hardiness = 1000;
        }
        if (Fortitude < 0)
        {
            Fortitude = 0;
        }
        if (Fortitude > 1000)
        {
            Fortitude = 1000;
        }
        if (Dexterity < 0)
        {
            Dexterity = 0;
        }
        if (Dexterity > 1000)
        {
            Dexterity = 1000;
        }
        if (Endurance < 0)
        {
            Endurance = 0;
        }
        if (Endurance > 1000)
        {
            Endurance = 1000;
        }
        if (Intellect < 0)
        {
            Intellect = 0;
        }
        if (Intellect > 1000)
        {
            Intellect = 1000;
        }
        if (Cleverness < 0)
        {
            Cleverness = 0;
        }
        if (Cleverness > 1000)
        {
            Cleverness = 1000;
        }
        if (Dependability < 0)
        {
            Dependability = 0;
        }
        if (Dependability > 1000)
        {
            Dependability = 1000;
        }
        if (Courage < 0)
        {
            Courage = 0;
        }
        if (Courage > 1000)
        {
            Courage = 1000;
        }
        if (Fierceness < 0)
        {
            Fierceness = 0;
        }
        if (Fierceness > 1000)
        {
            Fierceness = 1000;
        }
        if (Power < 0)
        {
            Power = 0;
        }
        if (Power > 1000)
        {
            Power = 1000;
        }
        dictionary attributeDict = new dictionary();
        attributeDict.put(ATTRIB_DICT_QUALITY, qualityRank);
        attributeDict.put(ATTRIB_DICT_SOURCE, creatureName);
        attributeDict.put(ATTRIB_DICT_HARDINESS, Hardiness);
        attributeDict.put(ATTRIB_DICT_FORTITUDE, Fortitude);
        attributeDict.put(ATTRIB_DICT_DEXTERITY, Dexterity);
        attributeDict.put(ATTRIB_DICT_ENDURANCE, Endurance);
        attributeDict.put(ATTRIB_DICT_INTELLECT, Intellect);
        attributeDict.put(ATTRIB_DICT_CLEVERNESS, Cleverness);
        attributeDict.put(ATTRIB_DICT_DEPENDABILITY, Dependability);
        attributeDict.put(ATTRIB_DICT_COURAGE, Courage);
        attributeDict.put(ATTRIB_DICT_FIERCENESS, Fierceness);
        attributeDict.put(ATTRIB_DICT_POWER, Power);
        attributeDict.put(ATTRIB_DICT_RANGED_WEAPON, rangedWeapon);
        return attributeDict;
    }
    public static int calcDnaHardiness(int health) throws InterruptedException
    {
        final int minHealth = 50;
        final int maxHealth = 8000;
        int hardiness = (int)(((float)(health - minHealth) / (float)(maxHealth - minHealth)) * 1000f);
        return hardiness;
    }
    public static int calcDnaFortitude(int armorBase) throws InterruptedException
    {
        final int minArmorBase = 0;
        final int maxArmorBase = 6000;
        int fortitude = (int)(((float)(armorBase - minArmorBase) / (float)(maxArmorBase - minArmorBase)) * 1000f);
        return fortitude;
    }
    public static int calcDnaDexterity(float defenseValue) throws InterruptedException
    {
        final int minDefense = 5;
        final int maxDefense = 209;
        int dexterity = (int)(((defenseValue - minDefense) / (maxDefense - minDefense)) * 1000f);
        return dexterity;
    }
    public static int calcDnaEndurance(float healthRegen) throws InterruptedException
    {
        final float minHealthRegen = 12;
        final float maxHealthRegen = 859;
        int endurance = (int)(((healthRegen - minHealthRegen) / (maxHealthRegen - minHealthRegen)) * 1000f);
        return endurance;
    }
    public static int calcDnaIntellect(float scale) throws InterruptedException
    {
        final float minScale = 0.5f;
        final float maxScale = 5.0f;
        if (scale < minScale)
        {
            scale = minScale;
        }
        else if (scale > maxScale)
        {
            scale = maxScale;
        }
        int intellect = (int)(((float)(scale - minScale) / (float)(maxScale - minScale)) * 1000f);
        return intellect;
    }
    public static int calcDnaCleverness(float toHit) throws InterruptedException
    {
        final int minToHit = 5;
        final int maxToHit = 209;
        int cleverness = (int)(((toHit - minToHit) / (maxToHit - minToHit)) * 1000f);
        return cleverness;
    }
    public static int calcDnaDependability(int level, int niche) throws InterruptedException
    {
        final int minDepend = 1;
        final int maxDepend = 100;
        float flevel = (float)level;
        if (niche != 7)
        {
            flevel /= 2;
        }
        if (flevel > maxDepend)
        {
            flevel = maxDepend;
        }
        int dependability = (int)(((flevel - minDepend) / (maxDepend - minDepend)) * 1000f);
        LOG("dna_harvest", "Dependability: " + dependability + " [level = " + level + "(" + minDepend + "/" + maxDepend + ")]");
        return dependability;
    }
    public static int calcDnaCourage(int level, String meat) throws InterruptedException
    {
        final int minLevel = 1;
        final int maxLevel = 100;
        float flevel = (float)level;
        if (meat == null || meat.equals(""))
        {
            flevel /= 5;
        }
        else if (meat.equals("meat_wild") || meat.equals("meat_avian"))
        {
            flevel /= 4;
        }
        else if (meat.equals("meat_herbivore"))
        {
            flevel /= 3;
        }
        else if (meat.equals("meat_carnivore") || meat.equals("meat_reptilian"))
        {
            flevel /= 2;
        }
        if (flevel > maxLevel)
        {
            flevel = maxLevel;
        }
        int courage = (int)(((flevel - minLevel) / (maxLevel - minLevel)) * 1000f);
        return courage;
    }
    public static int calcDnaFierceness(int level, int niche) throws InterruptedException
    {
        final int minFerocity = 1;
        final int maxFerocity = 100;
        float flevel = (float)level;
        if (niche != 8)
        {
            flevel /= 2;
        }
        if (flevel > maxFerocity)
        {
            flevel = maxFerocity;
        }
        int fierceness = (int)(((float)(flevel - minFerocity) / (float)(maxFerocity - minFerocity)) * 1000f);
        return fierceness;
    }
    public static int calcDnaPower(int avgDamage) throws InterruptedException
    {
        final int minBaseDamage = 17;
        final int maxBaseDamage = 431;
        int power = (int)(((float)(avgDamage - minBaseDamage) / (float)(maxBaseDamage - minBaseDamage)) * 1000f);
        return power;
    }
    public static obj_id createDNAComponent(obj_id player, obj_id inventory, dictionary attributeDict) throws InterruptedException
    {
        obj_id DNA_obj = null;
        switch (attributeDict.getInt(ATTRIB_DICT_QUALITY))
        {
            case DNA_V_LOW_QUALITY:
            DNA_obj = createObject(DNA_V_LOW_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_LOW_QUALITY:
            DNA_obj = createObject(DNA_LOW_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_B_AVG_QUALITY:
            DNA_obj = createObject(DNA_B_AVG_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_AVG_QUALITY:
            DNA_obj = createObject(DNA_AVG_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_A_AVG_QUALITY:
            DNA_obj = createObject(DNA_A_AVG_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_HIGH_QUALITY:
            DNA_obj = createObject(DNA_HIGH_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
            case DNA_V_HIGH_QUALITY:
            DNA_obj = createObject(DNA_V_HIGH_QUALITY_OBJ_TEMPLATE, inventory, "");
            break;
        }
        if (DNA_obj != null)
        {
            setCraftedId(DNA_obj, DNA_obj);
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_QUALITY, attributeDict.getInt(ATTRIB_DICT_QUALITY));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_SOURCE, attributeDict.getString(ATTRIB_DICT_SOURCE));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_HARDINESS, attributeDict.getInt(ATTRIB_DICT_HARDINESS));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_FORTITUDE, attributeDict.getInt(ATTRIB_DICT_FORTITUDE));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_DEXTERITY, attributeDict.getInt(ATTRIB_DICT_DEXTERITY));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_ENDURANCE, attributeDict.getInt(ATTRIB_DICT_ENDURANCE));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_INTELLECT, attributeDict.getInt(ATTRIB_DICT_INTELLECT));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_CLEVERNESS, attributeDict.getInt(ATTRIB_DICT_CLEVERNESS));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_DEPENDABILITY, attributeDict.getInt(ATTRIB_DICT_DEPENDABILITY));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_COURAGE, attributeDict.getInt(ATTRIB_DICT_COURAGE));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_FIERCENESS, attributeDict.getInt(ATTRIB_DICT_FIERCENESS));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_POWER, attributeDict.getInt(ATTRIB_DICT_POWER));
            setObjVar(DNA_obj, COMPONENT_RESOURCE_ATTRIB_OBJVAR_NAME + "." + ATTRIB_DICT_RANGED_WEAPON, attributeDict.getString(ATTRIB_DICT_RANGED_WEAPON));
        }
        return DNA_obj;
    }
    public static int grantTemplateCraftingExperience(int level) throws InterruptedException
    {
        return level * 10;
    }
    public static int calcCreatureAttrib(int attrib, int[] dnaAttributes) throws InterruptedException
    {
        final int minAttrib = 50;
        final int maxAttrib = 8000;
        int attribRange = maxAttrib - minAttrib;
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[attrib][0]] * CREATURE_ATTRIB_WEIGHTS[attrib][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[attrib][2]] * CREATURE_ATTRIB_WEIGHTS[attrib][3])) / (CREATURE_ATTRIB_WEIGHTS[attrib][1] + CREATURE_ATTRIB_WEIGHTS[attrib][3]));
        int newAttrib = (int)((attribRange * dnaAttrib) / 1000) + minAttrib;
        LOG("creature_crafting", "Attribute: " + create.MINATTRIBNAMES[attrib]);
        LOG("creature_crafting", "    minVal: " + minAttrib);
        LOG("creature_crafting", "    maxVal: " + maxAttrib);
        LOG("creature_crafting", "    Range: " + attribRange);
        LOG("creature_crafting", "    dnaVal: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[attrib][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[attrib][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[attrib][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[attrib][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[attrib][1] + " + " + CREATURE_ATTRIB_WEIGHTS[attrib][3] + "))");
        LOG("creature_crafting", "    endVal: " + newAttrib);
        return newAttrib;
    }
    public static int calcCreatureToHit(int[] dnaAttributes) throws InterruptedException
    {
        final int minToHit = 5;
        final int maxToHit = 209;
        float toHitChance = 0;
        LOG("creature_crafting", "Attribute: To Hit");
        LOG("creature_crafting", "    minVal: " + minToHit);
        LOG("creature_crafting", "    maxVal: " + maxToHit);
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][3]));
        toHitChance = minToHit + (((maxToHit - minToHit) * dnaAttrib) / 1000);
        LOG("creature_crafting", "    dnaToHit: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_TO_HIT][3] + "))");
        LOG("creature_crafting", "    endToHit: " + toHitChance);
        return (int)toHitChance;
    }
    public static float calcCreatureCritChance(int[] dnaAttributes) throws InterruptedException
    {
        float critChance = 0;
        LOG("creature_crafting", "Attribute: Crit Chance");
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][3]));
        critChance = 0.08f * (dnaAttrib / 1000.f);
        LOG("creature_crafting", "    dnaCrit: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_CRIT][3] + "))");
        LOG("creature_crafting", "    endCrit: " + critChance);
        return critChance;
    }
    public static float calcCreatureCritSave(int[] dnaAttributes) throws InterruptedException
    {
        float critSave = 0;
        LOG("creature_crafting", "Attribute: Crit Save");
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][3]));
        critSave = 0.08f * (dnaAttrib / 1000.f);
        LOG("creature_crafting", "    dnaSave: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_SAVE][3] + "))");
        LOG("creature_crafting", "    endSave: " + critSave);
        return critSave;
    }
    public static float calcCreatureAggroBonus(int[] dnaAttributes) throws InterruptedException
    {
        float aggroBonus = 0;
        LOG("creature_crafting", "Attribute: Aggro Bonus");
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][3]));
        aggroBonus = 0.25f * (dnaAttrib / 1000.f);
        LOG("creature_crafting", "    dnaAggro: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_AGGRO][3] + "))");
        LOG("creature_crafting", "    endAggro: " + aggroBonus);
        return aggroBonus;
    }
    public static float calcCreatureStateResist(int[] dnaAttributes) throws InterruptedException
    {
        float stateResist = 0;
        LOG("creature_crafting", "Attribute: State Resist");
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3]));
        stateResist = dnaAttrib / 1000;
        LOG("creature_crafting", "    dnaStateResist: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3] + "))");
        LOG("creature_crafting", "    endStateResist: " + stateResist);
        return stateResist;
    }
    public static float calcCreatureMaxScale(int[] dnaAttributes) throws InterruptedException
    {
        final float minScalef = 0.5f;
        final float maxScalef = 5.0f;
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_STATE_RESIST][3]));
        float maxScale = minScalef + (((maxScalef - minScalef) * dnaAttrib) / 1000);
        return maxScale;
    }
    public static int[] calcCreatureDamage(int[] dnaAttributes) throws InterruptedException
    {
        final int minBaseDamage = 17;
        final int maxBaseDamage = 431;
        int baseDamage = 0;
        LOG("creature_crafting", "Attribute: Damage");
        LOG("creature_crafting", "    minBaseDamage: " + minBaseDamage);
        LOG("creature_crafting", "    maxBaseDamage: " + maxBaseDamage);
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][3]));
        baseDamage = (int)(minBaseDamage + (((maxBaseDamage - minBaseDamage) * dnaAttrib) / 1000));
        int[] damage = new int[2];
        damage[0] = minBaseDamage;
        damage[1] = maxBaseDamage;
        LOG("creature_crafting", "    dnaDamage: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DAMAGE][3] + "))");
        LOG("creature_crafting", "    endBaseDmg: " + baseDamage);
        LOG("creature_crafting", "    minDamage:  " + damage[0]);
        LOG("creature_crafting", "    maxDamage:  " + damage[1]);
        return damage;
    }
    public static int calcCreatureHealthRegen(int[] dnaAttributes) throws InterruptedException
    {
        final float minHealthRegen = 12f;
        final float maxHealthRegen = 859f;
        LOG("creature_crafting", "Attribute: Health Regen");
        LOG("creature_crafting", "    minVal: " + minHealthRegen);
        LOG("creature_crafting", "    maxVal: " + maxHealthRegen);
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][3]));
        float healthRegen = minHealthRegen + (((maxHealthRegen - minHealthRegen) * dnaAttrib) / 1000);
        LOG("creature_crafting", "    dnaHealthRegen: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_HEALTH_REGEN][3] + "))");
        LOG("creature_crafting", "    endHealthRegen: " + healthRegen);
        return (int)healthRegen;
    }
    public static int calcCreatureDefenseValue(int[] dnaAttributes) throws InterruptedException
    {
        final int minDefense = 5;
        final int maxDefense = 209;
        float defenseValue = 0;
        LOG("creature_crafting", "Attribute: Defense Value");
        LOG("creature_crafting", "    minVal: " + minDefense);
        LOG("creature_crafting", "    maxVal: " + maxDefense);
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][3]));
        defenseValue = minDefense + (((maxDefense - minDefense) * dnaAttrib) / 1000);
        LOG("creature_crafting", "    dnaDefense: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_DEFENSE][3] + "))");
        LOG("creature_crafting", "    endDefenseValue: " + defenseValue);
        return (int)defenseValue;
    }
    public static int calcCreatureArmor(int[] dnaAttributes) throws InterruptedException
    {
        final int minArmorBase = 0;
        final int maxArmorBase = 6000;
        int armorBase = 0;
        LOG("creature_crafting", "Attribute: Armor");
        LOG("creature_crafting", "    minVal: " + minArmorBase);
        LOG("creature_crafting", "    maxVal: " + maxArmorBase);
        float dnaAttrib = (((dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][0]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][1]) + (dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][2]] * CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][3])) / (CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][1] + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][3]));
        armorBase = (int)(minArmorBase + (((maxArmorBase - minArmorBase) * dnaAttrib) / 1000));
        LOG("creature_crafting", "    dnaArmor: " + dnaAttrib);
        LOG("creature_crafting", "    dnaCalc = (((" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][0]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][1] + ") + (" + dnaAttributes[CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][2]] + " * " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][3] + ")) / (" + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][1] + " + " + CREATURE_ATTRIB_WEIGHTS[CREATURE_ATTRIB_WEIGHT_ARMOR][3] + "))");
        LOG("creature_crafting", "    endArmorBase: " + armorBase);
        return armorBase;
    }
    public static int calcCreature(int[] dnaAttributes) throws InterruptedException
    {
        return 0;
    }
    public static int getCraftedCreatureLevel(dictionary creatureDict) throws InterruptedException
    {
        int statLevel = calcCraftedStatLevel(creatureDict);
        int dmgLevel = calcDmgLevel(creatureDict);
        int regenLevel = calcRegenLevel(creatureDict);
        int toHitLevel = calcToHitLevel(creatureDict);
        if (creatureDict.getInt("BaseLevel") == 0)
        {
            int tempLevel = (int)((((statLevel * 6) + (regenLevel * 2) + (dmgLevel * 10) + (toHitLevel * 1)) / 19f) + 0.5f);
            creatureDict.put("BaseLevel", tempLevel);
        }
        int armorLevel = calcArmorLevel(creatureDict);
        int defenseLevel = calcDefenseLevel(creatureDict);
        LOG("creature_balance_calcs", "Level: " + (int)((((statLevel * 6) + (regenLevel * 2) + (dmgLevel * 10) + (toHitLevel * 1) + (defenseLevel * 1) + (armorLevel * 2)) / 22f) + 0.5f) + " (" + statLevel + "/" + regenLevel + "/" + dmgLevel + "/" + toHitLevel + "/" + defenseLevel + "/" + armorLevel + ")");
        return (int)((((statLevel * 6) + (regenLevel * 2) + (dmgLevel * 10) + (toHitLevel * 1) + (defenseLevel * 1) + (armorLevel * 2)) / 22f) + 0.5f);
    }
    public static int getCreatureLevel(dictionary creatureDict) throws InterruptedException
    {
        int statLevel = calcStatLevel(creatureDict);
        int dmgLevel = calcDmgLevel(creatureDict);
        int regenLevel = calcRegenLevel(creatureDict);
        int toHitLevel = calcToHitLevel(creatureDict);
        int armorLevel = calcArmorLevel(creatureDict);
        int defenseLevel = calcDefenseLevel(creatureDict);
        LOG("creature_balance_calcs", "Level: " + (int)((((statLevel * 6) + (regenLevel * 2) + (dmgLevel * 10) + (defenseLevel * 1) + (toHitLevel * 1) + (armorLevel * 2)) / 22f) + 0.5f) + " (" + statLevel + "/" + regenLevel + "/" + dmgLevel + "/" + toHitLevel + "/" + defenseLevel + "/" + armorLevel + ")");
        return (int)((((statLevel * 6) + (dmgLevel * 10) + (regenLevel * 2) + (toHitLevel * 1) + (defenseLevel * 1) + (armorLevel * 2)) / 22f) + 0.5f);
    }
    public static int calcCraftedStatLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] hitpoints = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "HP");
        int health = creatureDict.getInt("maxHealth");
        int level = 0;
        while (level < hitpoints.length && health > hitpoints[level])
        {
            level++;
        }
        LOG("creature_balance_calcs", "StatLevel = " + (level + 1) + ": Health - (" + health + ")");
        return (level + 1);
    }
    public static int calcStatLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] hitpoints = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "HP");
        int minHealth = creatureDict.getInt("minHealth");
        int maxHealth = creatureDict.getInt("maxHealth");
        int avgStat = (minHealth + maxHealth) / 2;
        int level = 0;
        while (level < hitpoints.length && avgStat > hitpoints[level])
        {
            level++;
        }
        LOG("creature_balance_calcs", "StatLevel = " + (level + 1) + ": Health - (" + minHealth + "-" + maxHealth + ")");
        return (level + 1);
    }
    public static int calcDmgLevel(dictionary creatureDict) throws InterruptedException
    {
        return 1;
    }
    public static int calcRegenLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] regenList = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "HealthRegen");
        int healthRegen = creatureDict.getInt("healthRegen");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int level = 0;
        while (level < regenList.length && healthRegen > regenList[level])
        {
            level++;
        }
        level += 1;
        LOG("creature_balance_calcs", "RegenLevel = " + level + ": healthRegen - (" + healthRegen + ")");
        return level;
    }
    public static int calcToHitLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] toHitList = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "ToHit");
        int toHit = creatureDict.getInt("toHitChance");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int level = 0;
        while (level < toHitList.length && toHit > toHitList[level])
        {
            level++;
        }
        level += 1;
        LOG("creature_balance_calcs", "ToHitLevel = " + level + ": ToHit - (" + toHit + ")");
        return level;
    }
    public static int calcOldToHitLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] toHitList = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "ToHit");
        int toHit = creatureDict.getInt("toHitChanceOld");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int level = 0;
        while (level < toHitList.length && toHit > toHitList[level])
        {
            level++;
        }
        level += 1;
        if (level < baseLevel)
        {
            int levelRange = level;
            while (levelRange < toHitList.length && toHitList[levelRange] == toHitList[level])
            {
                levelRange++;
            }
            levelRange += 1;
            if (levelRange < baseLevel)
            {
                level = levelRange;
            }
            else 
            {
                level = baseLevel;
            }
        }
        LOG("creature_balance_calcs", "ToHitLevel = " + level + ": ToHit - (" + toHit + ")");
        return level;
    }
    public static int calcDefenseLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] defenseList = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "Def");
        int defenseValue = creatureDict.getInt("defenseValue");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int level = 0;
        while (level < defenseList.length && defenseValue > defenseList[level])
        {
            level++;
        }
        level += 1;
        LOG("creature_balance_calcs", "DefenseLevel = " + level + ": defenseValue - (" + defenseValue + ")");
        return level;
    }
    public static int calcAttackSpeedLevel(dictionary creatureDict) throws InterruptedException
    {
        return 0;
    }
    public static int calcArmorLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] baseEffectiveness = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "Armor");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int armorEffectiveness = creatureDict.getInt("armorEffectiveness");
        int level = 0;
        while (level < baseEffectiveness.length && armorEffectiveness > baseEffectiveness[level])
        {
            level++;
        }
        level += 1;
        if (level < baseLevel)
        {
            int levelRange = level;
            while (levelRange < baseEffectiveness.length && baseEffectiveness[levelRange] == baseEffectiveness[level])
            {
                levelRange++;
            }
            levelRange += 1;
            if (levelRange < baseLevel)
            {
                level = levelRange;
            }
            else 
            {
                level = baseLevel;
            }
        }
        return level;
    }
    public static int calcOldArmorLevel(dictionary creatureDict) throws InterruptedException
    {
        int[] baseRating = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "BaseArmor");
        int[] baseEffectiveness = dataTableGetIntColumn("datatables/mob/stat_balance.iff", "Armor");
        int baseLevel = creatureDict.getInt("BaseLevel");
        int armorRating = creatureDict.getInt("armorRatingOld");
        int armorEffectiveness = creatureDict.getInt("armorEffectivenessOld");
        int armorKinetic = creatureDict.getInt("armorKineticOld");
        int armorEnergy = creatureDict.getInt("armorEnergyOld");
        int armorBlast = creatureDict.getInt("armorBlastOld");
        int armorHeat = creatureDict.getInt("armorHeatOld");
        int armorCold = creatureDict.getInt("armorColdOld");
        int armorElectric = creatureDict.getInt("armorElectricOld");
        int armorAcid = creatureDict.getInt("armorAcidOld");
        int armorStun = creatureDict.getInt("armorStunOld");
        int level = 0;
        float resistanceLevel = 0;
        while (level < baseRating.length && armorRating >= baseRating[level] && (armorRating > baseRating[level] || armorEffectiveness > baseEffectiveness[level]))
        {
            level++;
        }
        level += 1;
        if (level < baseLevel)
        {
            int levelRange = level;
            while (levelRange < baseRating.length && baseRating[levelRange] == baseRating[level] && baseEffectiveness[levelRange] == baseEffectiveness[level])
            {
                levelRange++;
            }
            levelRange += 1;
            if (levelRange < baseLevel)
            {
                level = levelRange;
            }
            else 
            {
                level = baseLevel;
            }
        }
        if (armorKinetic > 0)
        {
            resistanceLevel += Math.max((armorKinetic - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) * 3;
        }
        else 
        {
            resistanceLevel += armorKinetic * (((armorRating * 25) + (armorEffectiveness / (armorRating + 1))) * 6);
        }
        if (armorEnergy > 0)
        {
            resistanceLevel += Math.max((armorEnergy - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) * 3;
        }
        else 
        {
            resistanceLevel += armorEnergy * (((armorRating * 25) + (armorEffectiveness / (armorRating + 1))) * 6);
        }
        if (armorBlast > 0)
        {
            resistanceLevel += Math.max((armorBlast - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorBlast * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        if (armorHeat > 0)
        {
            resistanceLevel += Math.max((armorHeat - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorHeat * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        if (armorCold > 0)
        {
            resistanceLevel += Math.max((armorCold - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorCold * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        if (armorElectric > 0)
        {
            resistanceLevel += Math.max((armorElectric - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorElectric * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        if (armorAcid > 0)
        {
            resistanceLevel += Math.max((armorAcid - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorAcid * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        if (armorStun > 0)
        {
            resistanceLevel += Math.max((armorStun - ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)))), 0) / 2f;
        }
        else 
        {
            resistanceLevel += armorStun * ((armorRating * 25) + (armorEffectiveness / (armorRating + 1)));
        }
        level += (int)(resistanceLevel / 10f);
        LOG("creature_balance_calcs", "ArmorLevel = " + level + ": Rating - (" + armorRating + "); Effect - (" + armorEffectiveness + "); Resistances - (" + armorKinetic + ", " + armorEnergy + ", " + armorBlast + ", " + armorHeat + ", " + armorCold + ", " + armorElectric + ", " + armorAcid + ", " + armorStun + ")");
        return level;
    }
    public static int calcSpecialAttackLevel(dictionary creatureDict) throws InterruptedException
    {
        return 0;
    }
    public static int calcRangedAttackLevel(dictionary creatureDict) throws InterruptedException
    {
        return 0;
    }
    public static int getCalcLevelFromPcd(obj_id pcd) throws InterruptedException
    {
        dictionary creatureDict = new dictionary();
        creatureDict.put("BaseLevel", getIntObjVar(pcd, "creature_attribs.level"));
        creatureDict.put(CREATURE_DICT_MAX_HEALTH, getIntObjVar(pcd, "creature_attribs.maxHealth"));
        creatureDict.put(CREATURE_DICT_HEALTH_REGEN, getIntObjVar(pcd, "creature_attribs.maxConstitution"));
        creatureDict.put(CREATURE_DICT_TO_HIT, getIntObjVar(pcd, "creature_attribs.toHitChance"));
        creatureDict.put(CREATURE_DICT_DEFENSE_VALUE, getIntObjVar(pcd, "creature_attribs.defenseValue"));
        creatureDict.put(CREATURE_DICT_ARMOR_BASE, getIntObjVar(pcd, "creature_attribs.general_protection"));
        creatureDict.put(CREATURE_DICT_MIN_DAMAGE, getIntObjVar(pcd, "creature_attribs." + ATTRIB_DICT_MIN_DAMAGE));
        creatureDict.put(CREATURE_DICT_MAX_DAMAGE, getIntObjVar(pcd, "creature_attribs." + ATTRIB_DICT_MAX_DAMAGE));
        creatureDict.put(CREATURE_DICT_MAX_SCALE, getFloatObjVar(pcd, "creature_attribs.scale"));
        int calcLevel = getCraftedCreatureLevel(creatureDict);
        return calcLevel;
    }
    public static void stripOldStats(obj_id pcd) throws InterruptedException
    {
        if (hasObjVar(pcd, "creature_attribs.armorData"))
        {
            removeObjVar(pcd, "creature_attribs.armorData");
        }
        if (hasObjVar(pcd, "creature_attribs.armorBase"))
        {
            removeObjVar(pcd, "creature_attribs.armorBase");
        }
        if (hasObjVar(pcd, "creature_attribs.armorBase"))
        {
            removeObjVar(pcd, "creature_attribs.armorBase");
        }
        if (hasObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[ACTION]))
        {
            removeObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[ACTION]);
        }
        if (hasObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[MIND]))
        {
            removeObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[MIND]);
        }
    }
    public static boolean validatePcdLevel(obj_id pcd, obj_id player) throws InterruptedException
    {
        int level = getIntObjVar(pcd, "creature_attribs.level");
        int health = getIntObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH]);
        int minDamage = getIntObjVar(pcd, "creature_attribs.minDamage");
        int maxDamage = getIntObjVar(pcd, "creature_attribs.maxDamage");
        int toHit = getIntObjVar(pcd, "creature_attribs.toHitChance");
        int defenseValue = getIntObjVar(pcd, "creature_attribs.defenseValue");
        int general_protection = getIntObjVar(pcd, "creature_attribs.general_protection");
        stripOldStats(pcd);
        if (!hasObjVar(pcd, "creature_attribs.toHitChance"))
        {
            setObjVar(pcd, "creature_attribs.toHitChance", dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "ToHit"));
        }
        if (!hasObjVar(pcd, "creature_attribs.defenseValue"))
        {
            setObjVar(pcd, "creature_attribs.defenseValue", dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "Def"));
        }
        if (!hasObjVar(pcd, "creature_attribs.general_protection"))
        {
            setObjVar(pcd, "creature_attribs.general_protection", dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "Elite_Armor"));
        }
        if (!hasObjVar(pcd, "creature_attribs.critChance"))
        {
            setObjVar(pcd, "creature_attribs.critChance", 0.0f);
        }
        if (!hasObjVar(pcd, "creature_attribs.critSave"))
        {
            setObjVar(pcd, "creature_attribs.critSave", 0.0f);
        }
        if (!hasObjVar(pcd, "creature_attribs.stateResist"))
        {
            setObjVar(pcd, "creature_attribs.stateResist", 0.0f);
        }
        if (!hasObjVar(pcd, "creature_attribs.aggroBonus"))
        {
            setObjVar(pcd, "creature_attribs.aggroBonus", 0.0f);
        }
        if (level == 0)
        {
            return true;
        }
        int calcLevel = getCalcLevelFromPcd(pcd);
        if (level >= (calcLevel * 0.85f))
        {
            return true;
        }
        else 
        {
            CustomerServiceLog("be_clone_validation", "Invalid Pet Found: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ") Level(" + level + ") calcLevel(" + calcLevel + ") H(" + health + ") Dam(" + minDamage + "-" + maxDamage + ") toHit(" + toHit + ") DV(" + defenseValue + ") GP(" + general_protection + ")");
            showBrokenPetDialog(pcd, player);
            return false;
        }
    }
    public static int showBrokenPetDialog(obj_id pcd, obj_id player) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, pcd, player, "handlePetFixDialog");
        setSUIProperty(pid, "", "Size", "450,375");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, localize(SID_PET_SUI_TITLE));
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, localize(SID_PET_SUI_TEXT));
        sui.msgboxButtonSetup(pid, sui.YES_NO_CANCEL);
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, sui.PROP_TEXT, "@" + SID_PET_SUI_FIX_LEVEL.toString());
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "@" + SID_PET_SUI_FIX_STATS.toString());
        setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "@" + SID_PET_SUI_ABORT.toString());
        setSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "OnPress", "RevertWasPressed=1\r\nparent.btnOk.press=t");
        subscribeToSUIProperty(pid, sui.MSGBOX_BTN_REVERT, "RevertWasPressed");
        sui.showSUIPage(pid);
        return pid;
    }
    public static void adjustBrokenPetLevel(obj_id pcd, obj_id player) throws InterruptedException
    {
        int oldLevel = getIntObjVar(pcd, "creature_attribs.level");
        int level = getCalcLevelFromPcd(pcd);
        if (level < 1)
        {
            CustomerServiceLog("be_clone_validation", "ERROR - Unable to adjust pet: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ")");
            sendSystemMessage(player, SID_PET_SUI_FIX_ERROR);
            return;
        }
        if (level > 75)
        {
            prose_package pp = prose.getPackage(SID_NEW_LEVEL_TOO_HIGH, level);
            sendSystemMessageProse(player, pp);
            adjustBrokenPetStats(pcd, player);
            return;
        }
        stripOldStats(pcd);
        setObjVar(pcd, "creature_attribs.level", level);
        prose_package proseMsg = prose.getPackage(SID_PET_SUI_LEVEL_FIXED, level);
        sendSystemMessageProse(player, proseMsg);
        CustomerServiceLog("be_clone_validation", "Invalid Pet Fixed: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ") oldLevel(" + oldLevel + ") newLevel(" + level + ")");
    }
    public static void adjustBrokenPetStats(obj_id pcd, obj_id player) throws InterruptedException
    {
        int level = getIntObjVar(pcd, "creature_attribs.level");
        if (level < 1)
        {
            CustomerServiceLog("be_clone_validation", "ERROR - Unable to adjust pet: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ")");
            sendSystemMessage(player, SID_PET_SUI_FIX_ERROR);
            return;
        }
        int health = dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "HP");
        
        int iconst = dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "HealthRegen");
        float dps = dataTableGetFloat("datatables/mob/stat_balance.iff", level - 1, "damagePerSecond");
        int minDamage = Math.round((dps * 2.0f) * 0.5f);
        int maxDamage = Math.round((dps * 2.0f) * 1.5f);
        int toHit = dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "ToHit");
        int defenseValue = dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "Def");
        int general_protection = dataTableGetInt("datatables/mob/stat_balance.iff", level - 1, "Armor");
        dictionary creatureDict = new dictionary();
        creatureDict.put("BaseLevel", level);
        creatureDict.put("maxHealth", health);
        creatureDict.put("healthRegen", iconst);
        creatureDict.put("minDamage", minDamage);
        creatureDict.put("maxDamage", maxDamage);
        creatureDict.put("toHitChance", toHit);
        creatureDict.put("defenseValue", defenseValue);
        creatureDict.put("armorEffectiveness", general_protection);
        int calcLevel = getCraftedCreatureLevel(creatureDict);
        if (calcLevel != level)
        {
            CustomerServiceLog("be_clone_validation", "ERROR - Unable to adjust pet: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ")");
            sendSystemMessage(player, SID_PET_SUI_FIX_ERROR);
            return;
        }
        stripOldStats(pcd);
        setObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[HEALTH], health);
        setObjVar(pcd, "creature_attribs." + create.MAXATTRIBNAMES[CONSTITUTION], iconst);
        setObjVar(pcd, "creature_attribs.minDamage", minDamage);
        setObjVar(pcd, "creature_attribs.maxDamage", maxDamage);
        setObjVar(pcd, "creature_attribs.toHitChance", toHit);
        setObjVar(pcd, "creature_attribs.defenseValue", defenseValue);
        setObjVar(pcd, "creature_attribs.general_protection", general_protection);
        prose_package proseMsg = prose.getPackage(SID_PET_SUI_STATS_FIXED, level);
        sendSystemMessageProse(player, proseMsg);
        CustomerServiceLog("be_clone_validation", "Invalid Pet Fixed: " + getName(player) + "(" + player + ") " + getEncodedName(pcd) + "(pcd-" + pcd + ") Level(" + level + ") NewStats: H(" + health + ") Dam(" + minDamage + "-" + maxDamage + ") toHit(" + toHit + ") DV(" + defenseValue + ") GP(" + general_protection + ")");
    }
    public static boolean validateCreatureSpecialAttack(int attack) throws InterruptedException
    {
        for (int i = 0; i < CREATURE_SPECIAL_ATTACK_LIST.length; i++)
        {
            if (attack == CREATURE_SPECIAL_ATTACK_LIST[i])
            {
                return true;
            }
        }
        return false;
    }
    public static String convertSampleTypeToCreature(String sampleType) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(sampleType, ":");
        if (st.countTokens() != 2)
        {
            LOG("creature_crafting", "ERROR:  Unable to convert Sample Type to creature name.");
            return "eopie";
        }
        String creature = st.nextToken();
        creature = st.nextToken();
        LOG("creature_crafting", "Converted Sample: " + sampleType + " to Creature: " + creature);
        return creature;
    }
    public static String convertTemplateToCreature(String templateName) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(templateName, "/");
        String creature = st.nextToken();
        while (st.hasMoreTokens())
        {
            creature = st.nextToken();
        }
        creature = creature.substring(13);
        st = new java.util.StringTokenizer(creature, ".");
        creature = st.nextToken();
        LOG("creature_crafting", "Converted Template: " + templateName + " to Creature: " + creature);
        return creature;
    }
    public static int translateDnaSampleAttributes(String attribName, int attribValue, String sampleType, String templateName) throws InterruptedException
    {
        LOG("creature_crafting", "Translating DNA Sample Attribute to DNA Template relative values");
        LOG("creature_crafting", "Attribute Name = " + attribName);
        String sampleCreature = convertSampleTypeToCreature(sampleType);
        dictionary sampleCreatureDict = dataTableGetRow(create.CREATURE_TABLE, sampleCreature);
        if (sampleCreatureDict == null)
        {
            LOG("creature_crafting", "ERROR:  Unable to read creature entry in Creature Table.");
            return attribValue;
        }
        String templateCreature = convertTemplateToCreature(templateName);
        dictionary templateCreatureDict = dataTableGetRow(create.CREATURE_TABLE, templateCreature);
        if (templateCreatureDict == null)
        {
            LOG("creature_crafting", "ERROR:  Unable to read creature entry in Creature Table.");
            return attribValue;
        }
        int newValue = convertDnaAttributes(attribName, attribValue, sampleCreatureDict, templateCreatureDict);
        LOG("creature_crafting", "DNA Sample Type = " + sampleCreature);
        LOG("creature_crafting", "DNA Sample Attribute Value = " + attribValue);
        LOG("creature_crafting", "DNA Template Name = " + templateCreature);
        LOG("creature_crafting", "DNA Template Attribute Value = " + newValue);
        return newValue;
    }
    public static int convertDnaAttributes(String attribName, int attribValue, dictionary sampleCreatureDict, dictionary templateCreatureDict) throws InterruptedException
    {
        int minSampleAttrib = 0;
        int maxSampleAttrib = 0;
        int minTemplateAttrib = 0;
        int maxTemplateAttrib = 0;
        int sampleAttribRange = 0;
        int templateAttribRange = 0;
        int attribWeight = 0;
        int newAttribValue = 0;
        for (int i = 0; i < (CREATURE_ATTRIB_WEIGHT_WILLPOWER + 1); i++)
        {
            if (attribName.equals(CREATURE_ATTRIB_OBJVAR_NAMES[CREATURE_ATTRIB_WEIGHTS[i][0]]))
            {
                minSampleAttrib = sampleCreatureDict.getInt(create.MINATTRIBNAMES[i]);
                maxSampleAttrib = sampleCreatureDict.getInt(create.MAXATTRIBNAMES[i]);
                sampleAttribRange = maxSampleAttrib - minSampleAttrib;
                minTemplateAttrib += templateCreatureDict.getInt(create.MINATTRIBNAMES[i]) * CREATURE_ATTRIB_WEIGHTS[i][1];
                maxTemplateAttrib += templateCreatureDict.getInt(create.MAXATTRIBNAMES[i]) * CREATURE_ATTRIB_WEIGHTS[i][1];
                attribWeight += CREATURE_ATTRIB_WEIGHTS[i][1];
                newAttribValue += ((int)((sampleAttribRange * newAttribValue) / 1000f) + minSampleAttrib) * CREATURE_ATTRIB_WEIGHTS[i][1];
            }
            if (attribName.equals(CREATURE_ATTRIB_OBJVAR_NAMES[CREATURE_ATTRIB_WEIGHTS[i][2]]))
            {
                minSampleAttrib = sampleCreatureDict.getInt(create.MINATTRIBNAMES[i]);
                maxSampleAttrib = sampleCreatureDict.getInt(create.MAXATTRIBNAMES[i]);
                sampleAttribRange = maxSampleAttrib - minSampleAttrib;
                minTemplateAttrib += templateCreatureDict.getInt(create.MINATTRIBNAMES[i]) * CREATURE_ATTRIB_WEIGHTS[i][2];
                maxTemplateAttrib += templateCreatureDict.getInt(create.MAXATTRIBNAMES[i]) * CREATURE_ATTRIB_WEIGHTS[i][2];
                attribWeight += CREATURE_ATTRIB_WEIGHTS[i][2];
                newAttribValue += ((int)((sampleAttribRange * newAttribValue) / 1000f) + minSampleAttrib) * CREATURE_ATTRIB_WEIGHTS[i][2];
            }
        }
        if (attribWeight == 0)
        {
            LOG("creature_crafting", "ERROR: No attribute weights found!");
            return attribValue;
        }
        newAttribValue = newAttribValue / attribWeight;
        LOG("creature_crafting", "Weighted Sample Attribute = " + newAttribValue);
        minTemplateAttrib = minTemplateAttrib / attribWeight;
        maxTemplateAttrib = maxTemplateAttrib / attribWeight;
        templateAttribRange = (maxTemplateAttrib - minTemplateAttrib) / 2;
        LOG("creature_crafting", "Weighted Template Attribute = " + (minTemplateAttrib + templateAttribRange));
        int attribDiff = newAttribValue - (minTemplateAttrib + templateAttribRange);
        if ((attribDiff > -templateAttribRange) && (attribDiff < templateAttribRange))
        {
            LOG("creature_crafting", "New Attrib within Template Range.");
            return attribValue;
        }
        LOG("creature_crafting", "Attrib Diff = " + attribDiff);
        if (attribDiff < 0)
        {
            float diffFactor = 1 + (-(attribDiff + templateAttribRange) / (float)templateAttribRange);
            newAttribValue = (int)(attribValue / diffFactor);
            LOG("creature_crafting", "Diff Factor  = " + diffFactor);
            LOG("creature_crafting", "Attrib Value = " + attribValue);
            LOG("creature_crafting", "New Value    = " + newAttribValue);
        }
        else 
        {
            float diffFactor = 1 + ((attribDiff - templateAttribRange) / (float)templateAttribRange);
            if (diffFactor > 20)
            {
                diffFactor = 20;
            }
            diffFactor /= 100f;
            newAttribValue = (int)(attribValue * (1 + diffFactor));
            LOG("creature_crafting", "Diff Factor  = " + diffFactor);
            LOG("creature_crafting", "Attrib Value = " + attribValue);
            LOG("creature_crafting", "New Value    = " + newAttribValue);
        }
        return newAttribValue;
    }
}
