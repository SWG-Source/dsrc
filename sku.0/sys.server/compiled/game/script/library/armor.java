package script.library;

import script.*;

import java.util.Enumeration;
import java.util.Map;

public class armor extends script.base_script
{
    public armor()
    {
    }
    public static final String DATATABLE_ARMOR = "datatables/crafting/armor.iff";
    public static final String DATATABLE_ARMOR_PENALTY = "datatables/crafting/armor_penalty.iff";
    public static final String DATATABLE_TYPE_COL = "type";
    public static final String DATATABLE_ATTRIB_INC_COL = "attrib_inc";
    public static final String DATATABLE_ATTRIB_DEC_COL = "attrib_dec";
    public static final String DATATABLE_RES_GEN_PROT_COL = "resource_general_protection_percent";
    public static final String DATATABLE_MIN_GEN_PROT_COL = "min_general_protection";
    public static final String DATATABLE_MAX_GEN_PROT_COL = "max_general_protection";
    public static final String DATATABLE_RES_SPEC_PROT_COL = "resource_special_protection_percent";
    public static final String DATATABLE_MIN_SPEC_PROT_COL = "min_special_protection";
    public static final String DATATABLE_MAX_SPEC_PROT_COL = "max_special_protection";
    public static final String DATATABLE_RES_CONDITION_COL = "resource_condition_percent";
    public static final String DATATABLE_MIN_CONDITION_COL = "min_condition";
    public static final String DATATABLE_MAX_CONDITION_COL = "max_condition";
    public static final String DATATABLE_TYPE_PENALTY_COL = "type";
    public static final String DATATABLE_MOVE_PENALTY_COL = "move";
    public static final String DATATABLE_ACCURACY_PENALTY_COL = "accuracy";
    public static final String DATATABLE_ATTACK_PENALTY_COL = "attack";
    public static final String DATATABLE_DEFENSE_PENALTY_COL = "defense";
    public static final String DATATABLE_LAYER_ROW = "layer";
    public static final String DATATABLE_LAYER_REFERENCE_ROW = "layer_reference";
    public static final String DATATABLE_SEGMENT_ROW = "segment";
    public static final String DATATABLE_CORE_ROW = "core";
    public static final String DATATABLE_FINAL_ROW = "final";
    public static final String DATATABLE_GENERIC_ROW = "generic";
    public static final String DATATABLE_PSG_ROW = "psg";
    public static final String DATATABLE_CATEGORY_ROW = "category";
    public static final int DATATABLE_PSG_LAYER = 13;
    public static final int DATATABLE_RECON_LAYER = 14;
    public static final int DATATABLE_ASSAULT_LAYER = 15;
    public static final String DATATABLE_PSG_LAYER_ROW = DATATABLE_LAYER_ROW + DATATABLE_PSG_LAYER;
    public static final String DATATABLE_RECON_LAYER_ROW = DATATABLE_LAYER_ROW + DATATABLE_RECON_LAYER;
    public static final String DATATABLE_ASSAULT_LAYER_ROW = DATATABLE_LAYER_ROW + DATATABLE_ASSAULT_LAYER;
    public static final String DATATABLE_MASTER_ARMOR = "datatables/crafting/armor_schematics.iff";
    public static final int NUM_DATATABLE_ARMOR_LAYERS = 16;
    public static final float MIN_ARMOR_RANGE = -0.5f;
    public static final float MAX_ARMOR_RANGE = 1.5f;
    public static final String SCRIPT_ARMOR_EXAMINE = "item.armor.new_armor";
    public static final String SID_TABLE = "@obj_attr_n:";
    public static final String STANDARD_PROTECTION_PREFIX = "cat_armor_standard_protection.armor_eff_";
    public static final String SPECIAL_PROTECTION_PREFIX = "cat_armor_special_protection.armor_eff_";
    public static final String[] SID_SPECIAL_PROTECTIONS = 
    {
        SPECIAL_PROTECTION_PREFIX + "elemental_acid",
        SPECIAL_PROTECTION_PREFIX + "blast",
        SPECIAL_PROTECTION_PREFIX + "elemental_cold",
        SPECIAL_PROTECTION_PREFIX + "elemental_electrical",
        STANDARD_PROTECTION_PREFIX + "energy",
        SPECIAL_PROTECTION_PREFIX + "elemental_heat",
        STANDARD_PROTECTION_PREFIX + "kinetic",
        SPECIAL_PROTECTION_PREFIX + "stun",
        SPECIAL_PROTECTION_PREFIX + "lightsaber"
    };
    public static final String ARMOR_PENALTY_PREFIX = "cat_armor_penalty.armor_penalty_";
    public static final String RAW_ARMOR_PENALTY_PREFIX = "cat_armor_penalty_raw.armor_penalty_";
    public static final String[] SID_ARMOR_PENALTIES = 
    {
        ARMOR_PENALTY_PREFIX + "move",
        ARMOR_PENALTY_PREFIX + "accuracy",
        ARMOR_PENALTY_PREFIX + "attack",
        ARMOR_PENALTY_PREFIX + "defense"
    };
    public static final String[] SID_RAW_ARMOR_PENALTIES = 
    {
        RAW_ARMOR_PENALTY_PREFIX + "move",
        RAW_ARMOR_PENALTY_PREFIX + "accuracy",
        RAW_ARMOR_PENALTY_PREFIX + "attack",
        RAW_ARMOR_PENALTY_PREFIX + "defense"
    };
    public static final String[] DATATABLE_SPECIAL_PROTECTIONS = 
    {
        "acid",
        "blast",
        "cold",
        "electricity",
        "energy",
        "heat",
        "kinetic",
        "stun",
        "lightsaber"
    };
    public static final Map SPECIAL_PROTECTION_MAP = collections.newMap(DATATABLE_SPECIAL_PROTECTIONS, SID_SPECIAL_PROTECTIONS);
    public static final String SID_ARMOR_CATEGORY = "armor_category";
    public static final Integer[] ARMOR_CATEGORY_INDEXES = 
    {
            AC_reconnaissance,
            AC_battle,
            AC_assault
    };
    public static final String[] SID_ARMOR_CATEGORIES = 
    {
        SID_TABLE + "armor_reconnaissance",
        SID_TABLE + "armor_battle",
        SID_TABLE + "armor_assault"
    };
    public static final Map ARMOR_CATEGORY_MAP = collections.newMap(ARMOR_CATEGORY_INDEXES, SID_ARMOR_CATEGORIES);
    public static final Integer[] ARMOR_PENALTY_DATATABLE_TYPES = 
    {
            getStringCrc("basic_recon"),
            getStringCrc("std_recon"),
            getStringCrc("adv_recon"),
            getStringCrc("basic_battle"),
            getStringCrc("std_battle"),
            getStringCrc("adv_battle"),
            getStringCrc("basic_assault"),
            getStringCrc("std_assault"),
            getStringCrc("adv_assault")
    };
    public static final Integer[] ARMOR_PENALTY_OBJECT_TYPES = 
    {
            AL_basic,
            AL_standard,
            AL_advanced,
            AC_battle * 10 + AL_basic,
            AC_battle * 10 + AL_standard,
            AC_battle * 10 + AL_advanced,
            AC_assault * 10 + AL_basic,
            AC_assault * 10 + AL_standard,
            AC_assault * 10 + AL_advanced
    };
    public static final Map ARMOR_PENALTY_TYPE_MAP = collections.newMap(ARMOR_PENALTY_OBJECT_TYPES, ARMOR_PENALTY_DATATABLE_TYPES);
    public static final int ARMOR_MOVE_PENALTY = 0;
    public static final int ARMOR_ACCURACY_PENALTY = 1;
    public static final int ARMOR_ATTACK_PENALTY = 2;
    public static final int ARMOR_DEFENSE_PENALTY = 3;
    public static final Integer[] ARMOR_PENALTY_INDEXES = 
    {
            ARMOR_MOVE_PENALTY,
            ARMOR_ACCURACY_PENALTY,
            ARMOR_ATTACK_PENALTY,
            ARMOR_DEFENSE_PENALTY
    };
    public static final String[] ARMOR_PENALTY_DATATABLE_COLUMNS = 
    {
        DATATABLE_MOVE_PENALTY_COL,
        DATATABLE_ACCURACY_PENALTY_COL,
        DATATABLE_ATTACK_PENALTY_COL,
        DATATABLE_DEFENSE_PENALTY_COL
    };
    public static final Map ARMOR_PENALTY_COLUMN_MAP = collections.newMap(ARMOR_PENALTY_INDEXES, ARMOR_PENALTY_DATATABLE_COLUMNS);
    public static final int[][][] ARMOR_CERTIFICATION_REDUCTIONS = 
    {
        
        {
            
            {
                10,
                20,
                0
            },
            
            {
                20,
                30,
                40
            },
            
            {
                30,
                40,
                60
            }
        },
        
        {
            
            {
                20,
                30,
                40
            },
            
            {
                20,
                30,
                40
            },
            
            {
                20,
                30,
                40
            }
        },
        
        {
            
            {
                30,
                40,
                60
            },
            
            {
                20,
                30,
                40
            },
            
            {
                10,
                20,
                0
            }
        }
    };
    public static final String OBJVAR_ARMOR_BASE = "armor";
    public static final String OBJVAR_LAYER_PREFIX = "layer";
    public static final String OBJVAR_CORE_TYPE = "core";
    public static final String OBJVAR_ARMOR_LEVEL = "armorLevel";
    public static final String OBJVAR_ARMOR_CATEGORY = "armorCategory";
    public static final String OBJVAR_GENERAL_PROTECTION = "general_protection";
    public static final String OBJVAR_CONDITION = "condition";
    public static final String OBJVAR_CONDITION_MULTIPLIER = "conditionMultiplier";
    public static final String OBJVAR_PSG_CURRENT_EFFICIENCY = "efficiency";
    public static final String OBJVAR_PSG_RECHARGE_RATE = "recharge_rate";
    public static final String OBJVAR_SPECIES_RESTRICTIONS = "species_restrictions";
    public static final String SCRIPTVAR_ARMOR_COUNT = "armor_count";
    public static final String SCRIPTVAR_PSG_EFFICIENCY = OBJVAR_ARMOR_BASE + "." + OBJVAR_PSG_CURRENT_EFFICIENCY;
    public static final String SCRIPTVAR_CACHED_BASE = OBJVAR_ARMOR_BASE + ".cache";
    public static final String SCRIPTVAR_CACHED_DATATABLE_ROW = SCRIPTVAR_CACHED_BASE + "." + "datatableRow";
    public static final String SCRIPTVAR_CACHED_LEVEL = SCRIPTVAR_CACHED_BASE + "." + "level";
    public static final String SCRIPTVAR_CACHED_CATEGORY = SCRIPTVAR_CACHED_BASE + "." + "category";
    public static final String SCRIPTVAR_CACHED_GENERAL_PROTECTION = SCRIPTVAR_CACHED_BASE + "." + "generalProtection";
    public static final String SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS = SCRIPTVAR_CACHED_BASE + "." + "specialProtection";
    public static final String SCRIPTVAR_CACHED_PENALTIES = SCRIPTVAR_CACHED_BASE + "." + "penalties";
    public static final String SCRIPTVAR_SPECIES_RESTRICTIONS = OBJVAR_ARMOR_BASE + "." + OBJVAR_SPECIES_RESTRICTIONS;
    public static final String[] PSG_STARTUP_EFFECTS = 
    {
        "appearance/pt_psg_on_mark_1.prt",
        "appearance/pt_psg_on_mark_2.prt",
        "appearance/pt_psg_on_mark_3.prt"
    };
    public static final String[] PSG_HIT_EFFECTS = 
    {
        "appearance/pt_psg_hit_mark_1.prt",
        "appearance/pt_psg_hit_mark_2.prt",
        "appearance/pt_psg_hit_mark_3.prt"
    };
    public static final java.text.DecimalFormat ARMOR_PENALTY_FORMAT = new java.text.DecimalFormat("-##0.0'%'");
    public static final String POWER_1 = "pseudo_1";
    public static final String POWER_2 = "pseudo_2";
    public static final String POWER_3 = "pseudo_3";
    public static final String POWER_4 = "pseudo_4";
    public static final String POWER_5 = "pseudo_5";
    public static final String POWER_6 = "pseudo_6";
    public static final String POWER_7 = "pseudo_7";
    public static final string_id SID_ARMOR_TO_SCHEM = new string_id("spam", "armor_to_schem");
    public static final string_id SID_CONVERT_PROMPT = new string_id("spam", "armor_to_schem_prompt");
    public static final string_id SID_CONVERT_TITLE = new string_id("spam", "armor_to_schem_title");
    public static final string_id SID_CONVERT_CONVERT_FAIL = new string_id("spam", "armor_to_schem_failure");
    public static final string_id SID_CONVERT_CONVERT_SUCCESS = new string_id("spam", "armor_to_schem_success");
    public static final string_id SID_CONVERT_INVALID_RESPONSE = new string_id("spam", "armor_to_schem_invalid_response");
    public static void clearCachedData(obj_id armor) throws InterruptedException
    {
        if (isIdValid(armor))
        {
            utils.removeScriptVarTree(armor, SCRIPTVAR_CACHED_BASE);
        }
    }
    public static boolean isArmorComponent(obj_id target) throws InterruptedException
    {
        return isArmorComponent(getGameObjectType(target));
    }
    public static boolean isArmorComponent(int armorGOT) throws InterruptedException
    {
        return (isGameObjectTypeOf(armorGOT, GOT_armor) && (armorGOT == GOT_armor_layer || armorGOT == GOT_armor_segment || armorGOT == GOT_armor_core));
    }
    public static boolean isArmorLayer(obj_id target) throws InterruptedException
    {
        return isGameObjectTypeOf(getGameObjectType(target), GOT_armor_layer);
    }
    public static boolean isFinalArmor(obj_id target) throws InterruptedException
    {
        return isFinalArmor(getGameObjectType(target));
    }
    public static boolean isFinalArmor(int armorGOT) throws InterruptedException
    {
        return (isGameObjectTypeOf(armorGOT, GOT_armor) && (armorGOT != GOT_armor_layer && armorGOT != GOT_armor_segment && armorGOT != GOT_armor_core && armorGOT != GOT_armor_psg));
    }
    public static boolean isPsg(obj_id target) throws InterruptedException
    {
        return isPsg(getGameObjectType(target));
    }
    public static boolean isPsg(int armorGOT) throws InterruptedException
    {
        return armorGOT == GOT_armor_psg;
    }
    public static int getArmorDatatableRow(obj_id armor) throws InterruptedException
    {
        if (!isValidArmor(armor))
        {
            debugServerConsoleMsg(null, "WARNING: armor.getArmorDatatableRow called with invalid target " + armor);
            return -1;
        }
        if (utils.hasScriptVar(armor, SCRIPTVAR_CACHED_DATATABLE_ROW))
        {
            return utils.getIntScriptVar(armor, SCRIPTVAR_CACHED_DATATABLE_ROW);
        }
        String rowName = null;
        int armorType = getGameObjectType(armor);
        if (armorType == GOT_armor_layer)
        {
            rowName = DATATABLE_LAYER_REFERENCE_ROW;
        }
        else if (armorType == GOT_armor_segment)
        {
            rowName = DATATABLE_SEGMENT_ROW;
        }
        else if (armorType == GOT_armor_core)
        {
            int armorLevel = getArmorLevel(armor);
            if (armorLevel >= 0)
            {
                rowName = DATATABLE_CORE_ROW + armorLevel;
            }
        }
        else if (armorType == GOT_armor_psg)
        {
            int armorLevel = getArmorLevel(armor);
            if (armorLevel >= 0)
            {
                rowName = DATATABLE_PSG_ROW + armorLevel;
            }
        }
        else 
        {
            int armorLevel = getArmorLevel(armor);
            if (armorLevel >= 0)
            {
                rowName = DATATABLE_FINAL_ROW + armorLevel;
            }
            else 
            {
                rowName = DATATABLE_GENERIC_ROW;
            }
        }
        int armorLevelRow = dataTableSearchColumnForInt(getStringCrc(rowName), DATATABLE_TYPE_COL, DATATABLE_ARMOR);
        if (armorLevelRow < 0)
        {
            armorLevelRow = -1;
        }
        else 
        {
            utils.setScriptVar(armor, SCRIPTVAR_CACHED_DATATABLE_ROW, armorLevelRow);
        }
        return armorLevelRow;
    }
    public static String getArmorBaseObjvar(obj_id armor) throws InterruptedException
    {
        return getArmorBaseObjvar(getGameObjectType(armor));
    }
    public static String getArmorBaseObjvar(int armorGOT) throws InterruptedException
    {
        if (isArmorComponent(armorGOT))
        {
            return craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME;
        }
        return OBJVAR_ARMOR_BASE;
    }
    public static float getAbsoluteArmorAttribute(float value, int row, String column) throws InterruptedException
    {
        int columnIndex = dataTableFindColumnNumber(DATATABLE_ARMOR, column);
        if (columnIndex == -1)
        {
            debugServerConsoleMsg(null, "WARNING armor.getAbsoluteArmorAttribute passed bad column " + column);
            return Float.MIN_VALUE;
        }
        else if (row < 0 || row >= dataTableGetNumRows(DATATABLE_ARMOR))
        {
            debugServerConsoleMsg(null, "WARNING armor.getAbsoluteArmorAttribute passed bad row " + row);
            return Float.MIN_VALUE;
        }
        float min_value = dataTableGetInt(DATATABLE_ARMOR, row, columnIndex);
        float max_value = dataTableGetInt(DATATABLE_ARMOR, row, columnIndex + 1);
        return value * (max_value - min_value) + min_value;
    }
    public static dictionary getAbsoluteArmorSpecialProtection(obj_id armor, String layerName, float value, int row) throws InterruptedException
    {
        int layerRow = dataTableSearchColumnForInt(getStringCrc(layerName), DATATABLE_TYPE_COL, DATATABLE_ARMOR);
        if (layerRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: armor/component " + armor + " has bad objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + layerName);
            return null;
        }
        float newValue = getAbsoluteArmorAttribute(value, row, DATATABLE_MIN_SPEC_PROT_COL);
        if (newValue == Float.MIN_VALUE)
        {
            return null;
        }
        String[] attrib_incs = split(dataTableGetString(DATATABLE_ARMOR, layerRow, DATATABLE_ATTRIB_INC_COL), ',');
        String[] attrib_decs = split(dataTableGetString(DATATABLE_ARMOR, layerRow, DATATABLE_ATTRIB_DEC_COL), ',');
        dictionary protections = new dictionary();
        if (attrib_incs != null)
        {
            for (String attrib_inc : attrib_incs) {
                protections.addFloat(attrib_inc, newValue);
            }
        }
        if (attrib_decs != null)
        {
            for (String attrib_dec : attrib_decs) {
                protections.addFloat(attrib_dec, -newValue);
            }
        }
        return protections;
    }
    public static float[] getArmorPenalties(int armorLevel, int armorCategory) throws InterruptedException
    {
        if (armorLevel == AL_none || armorCategory == AC_none)
        {
            return null;
        }
        Object o = ARMOR_PENALTY_TYPE_MAP.get(armorCategory * 10 + armorLevel);
        if (o == null || !(o instanceof Integer))
        {
            return null;
        }
        int row = dataTableSearchColumnForInt((Integer) o, DATATABLE_TYPE_PENALTY_COL, DATATABLE_ARMOR_PENALTY);
        if (row < 0)
        {
            return null;
        }
        float[] penalties = new float[ARMOR_PENALTY_INDEXES.length];
        for (int i = 0; i < penalties.length; ++i)
        {
            o = ARMOR_PENALTY_COLUMN_MAP.get(ARMOR_PENALTY_INDEXES[i]);
            if (o == null || !(o instanceof String))
            {
                return null;
            }
            penalties[i] = dataTableGetFloat(DATATABLE_ARMOR_PENALTY, row, (String)o);
        }
        return penalties;
    }
    public static float[] getScaledArmorPenalties(obj_id armor, float generalProtection, int armorRow, int armorLevel, int armorCategory) throws InterruptedException
    {
        float[] penalties;
        if (isIdValid(armor))
        {
            penalties = utils.getFloatArrayScriptVar(armor, SCRIPTVAR_CACHED_PENALTIES);
            if (penalties != null)
            {
                return penalties;
            }
        }
        penalties = getArmorPenalties(armorLevel, armorCategory);
        if (penalties != null)
        {
            float gpMin = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MIN_GEN_PROT_COL);
            float gpMax = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MAX_GEN_PROT_COL);
            float gpPercent = 1.0f;
            if (gpMax != gpMin)
            {
                gpPercent = (generalProtection - gpMin) / (gpMax - gpMin);
            }
            for (int i = 0; i < penalties.length; ++i)
            {
                penalties[i] *= gpPercent;
            }
        }
        if (isIdValid(armor) && penalties != null)
        {
            utils.setScriptVar(armor, SCRIPTVAR_CACHED_PENALTIES, penalties);
        }
        return penalties;
    }
    public static float[] reduceCertifiedArmorPenalties(obj_id player, int armorCategory, float[] penalties) throws InterruptedException
    {
        if (armorCategory == AC_none)
        {
            return penalties;
        }
        String[] armorCatNames = 
        {
            "recon",
            "battle",
            "assault"
        };
        String[] armorPenaltyNames = 
        {
            "move",
            "accuracy",
            "firerate"
        };
        float[] certPenalties = new float[penalties.length];
        String certName;
        for (int i = 0; i < ARMOR_DEFENSE_PENALTY; i++)
        {
            certName = armorCatNames[armorCategory] + "_" + armorPenaltyNames[i] + "_mitigate_";
            certPenalties[i] = penalties[i];
            for (int j = 3; j > 0; j--)
            {
                if (hasCommand(player, certName + j))
                {
                    certPenalties[i] -= ARMOR_CERTIFICATION_REDUCTIONS[armorCategory][i][j - 1];
                    if (certPenalties[i] < 0f)
                    {
                        certPenalties[i] = 0f;
                    }
                    break;
                }
            }
        }
        certPenalties[ARMOR_DEFENSE_PENALTY] = penalties[ARMOR_DEFENSE_PENALTY];
        return certPenalties;
    }
    public static void calculateArmorHinderances(obj_id player) throws InterruptedException
    {
        float movePenalty = 0;
        float toHitPenalty = 0;
        float fireRatePenalty = 0;
        obj_id[] wornItems = metrics.getWornItems(player);
        if (wornItems != null)
        {
            int got;
            for (obj_id wornItem : wornItems) {
                got = getGameObjectType(wornItem);
                if (isGameObjectTypeOf(got, GOT_armor) && (!isGameObjectTypeOf(got, GOT_armor_foot) && !isGameObjectTypeOf(got, GOT_armor_hand))) {
                    float[] penalties;
                    penalties = getFinalArmorPenalties(player, wornItem);
                    if (penalties == null) {
                        continue;
                    }
                    if (penalties[0] > movePenalty) {
                        movePenalty = penalties[0];
                    }
                    if (penalties[1] > toHitPenalty) {
                        toHitPenalty = penalties[1];
                    }
                    if (penalties[2] > fireRatePenalty) {
                        fireRatePenalty = penalties[2];
                    }
                }
            }
        }
        utils.setScriptVar(player, "combat.armor.toHitPenalty", toHitPenalty);
        utils.setScriptVar(player, "combat.armor.fireRatePenalty", fireRatePenalty);
        if (movePenalty > 0)
        {
            buff.applyBuff(player, (-1748156929), 0, movePenalty);
        }
        else 
        {
            buff.removeBuff(player, (-1748156929));
        }
        if (toHitPenalty > 0)
        {
            buff.applyBuff(player, (552351019));
        }
        else 
        {
            buff.removeBuff(player, (552351019));
        }
        if (fireRatePenalty > 0)
        {
            buff.applyBuff(player, (1005058320));
        }
        else 
        {
            buff.removeBuff(player, (1005058320));
        }
    }
    public static void calculateWornArmor(obj_id player) throws InterruptedException
    {
        obj_id[] wornItems = metrics.getWornItems(player);
        int armorCount = 0;
        if (wornItems != null)
        {
            int got;
            for (obj_id wornItem : wornItems) {
                got = getGameObjectType(wornItem);
                if (isGameObjectTypeOf(got, GOT_armor) && !isGameObjectTypeOf(got, GOT_armor_psg)) {
                    armorCount++;
                    break;
                }
            }
        }
        if (armorCount > 0)
        {
            utils.setScriptVar(player, SCRIPTVAR_ARMOR_COUNT, armorCount);
        }
        else 
        {
            utils.removeScriptVar(player, SCRIPTVAR_ARMOR_COUNT);
        }
    }
    public static float[] getFinalArmorPenalties(obj_id player, obj_id armorPiece) throws InterruptedException
    {
        int armorType = getGameObjectType(armorPiece);
        if (armor.isFinalArmor(armorType))
        {
            armorType = GOT_armor;
        }
        int armorLevel = -1;
        int armorCategory = -1;
        int armorRow = armor.getArmorDatatableRow(armorPiece);
        if (armorRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: could not find armor datatable row for armor " + armorPiece);
            return null;
        }
        obj_var_list armorData = getObjVarList(armorPiece, armor.getArmorBaseObjvar(armorType));
        if (armorData == null)
        {
            CustomerServiceLog("armor", "WARNING: unable to get armor data for armor/component " + armorPiece);
            return null;
        }
        if (armorType == GOT_armor || armorType == GOT_armor_core || armorType == GOT_armor_psg)
        {
            if (!armorData.hasObjVar(armor.OBJVAR_ARMOR_LEVEL))
            {
                CustomerServiceLog("armor", "WARNING: armor " + armorPiece + " missing objvar " + armor.OBJVAR_ARMOR_LEVEL);
                return null;
            }
            if (!armorData.hasObjVar(armor.OBJVAR_ARMOR_CATEGORY))
            {
                CustomerServiceLog("armor", "WARNING: armor " + armorPiece + " missing objvar " + armor.OBJVAR_ARMOR_CATEGORY);
                return null;
            }
            armorLevel = armorData.getIntObjVar(armor.OBJVAR_ARMOR_LEVEL);
            armorCategory = armorData.getIntObjVar(armor.OBJVAR_ARMOR_CATEGORY);
        }
        float general_protection = armor.getArmorGeneralProtection(armorPiece);
        float[] penalties = null;
        if (general_protection > 0 && armorLevel >= 0 && armorCategory >= 0)
        {
            penalties = armor.getScaledArmorPenalties(armorPiece, general_protection, armorRow, armorLevel, armorCategory);
        }
        if (penalties != null)
        {
            penalties = armor.reduceCertifiedArmorPenalties(player, armorCategory, penalties);
        }
        return penalties;
    }
    public static boolean isArmorCertified(obj_id player, obj_id armorPiece) throws InterruptedException
    {
        if (!isValidArmor(armorPiece))
        {
            return true;
        }
        int armorType = getGameObjectType(armorPiece);
        if (isGameObjectTypeOf(armorType, GOT_cybernetic))
        {
            return true;
        }
        if (isPsg(armorPiece))
        {
            return true;
        }
        if (armor.isFinalArmor(armorType))
        {
            armorType = GOT_armor;
        }
        if (armor.getArmorDatatableRow(armorPiece) < 0)
        {
            if (hasScript(armorPiece, "item.armor.new_armor"))
            {
                CustomerServiceLog("armor", "WARNING: could not find armor datatable row for armor " + armorPiece);
                return false;
            }
            return true;
        }
        if (hasObjVar(armorPiece, "factionrestricted.rebel"))
        {
            if (!factions.isRebel(player))
            {
                return false;
            }
        }
        if (hasObjVar(armorPiece, "factionrestricted.imperial"))
        {
            if (!factions.isImperial(player))
            {
                return false;
            }
        }
        obj_var_list armorData = getObjVarList(armorPiece, armor.getArmorBaseObjvar(armorType));
        int armorCategory;
        if (armorData != null)
        {
            if (!armorData.hasObjVar(armor.OBJVAR_ARMOR_CATEGORY))
            {
                CustomerServiceLog("armor", "WARNING: armor " + armorPiece + " missing objvar " + armor.OBJVAR_ARMOR_CATEGORY);
                return false;
            }
            else 
            {
                armorCategory = armorData.getIntObjVar(armor.OBJVAR_ARMOR_CATEGORY);
            }
        }
        else 
        {
            CustomerServiceLog("armor", "WARNING: could not determine armor type for armor " + armorPiece);
            return false;
        }
        if (armorCategory < 0)
        {
            CustomerServiceLog("armor", "WARNING: could not determine armor type for armor " + armorPiece);
            return false;
        }
        return hasCommand(player, "wear_all_armor");
    }
    public static float convertArmorData(float value, int fromRow, int toRow, String column) throws InterruptedException
    {
        int columnIndex = dataTableFindColumnNumber(DATATABLE_ARMOR, column);
        if (columnIndex == -1)
        {
            debugServerConsoleMsg(null, "WARNING armor.convertArmorData passed bad column " + column);
            return value;
        }
        float min_value = dataTableGetInt(DATATABLE_ARMOR, fromRow, columnIndex);
        float max_value = dataTableGetInt(DATATABLE_ARMOR, fromRow, columnIndex + 1);
        float newValue = value * (max_value - min_value) + min_value;
        min_value = dataTableGetInt(DATATABLE_ARMOR, toRow, columnIndex);
        max_value = dataTableGetInt(DATATABLE_ARMOR, toRow, columnIndex + 1);
        if (max_value != min_value)
        {
            newValue = (newValue - min_value) / (max_value - min_value);
        }
        else 
        {
            newValue = 1.0f;
        }
        return newValue;
    }
    public static void rescaleComponentData(obj_id schematic, obj_var_list componentData, int fromRow, int toRow) throws InterruptedException
    {
        if (!hasObjVar(schematic, craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME + ".isScaled"))
        {
            Enumeration keys = componentData.keys();
            float value;
            float newValue;
            String key;
            while (keys.hasMoreElements())
            {
                key = (String)(keys.nextElement());
                if(componentData.isFloatObjVar(key) || componentData.isIntObjVar(key)) {
                    value = (componentData.isFloatObjVar(key) ? componentData.getFloatObjVar(key) : componentData.getIntObjVar(key));
                    newValue = rescaleArmorData(key, fromRow, toRow, value);
                    if (newValue != Float.MIN_VALUE)
                    {
                        debugServerConsoleMsg(null, "Armor scaling component data " + key + " from " + value + " to " + newValue + "(fromRow = " + fromRow + ", toRow = " + toRow + ")");
                        setObjVar(schematic, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".scaled." + key, newValue);
                        removeObjVar(schematic, craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME + "." + key);
                    }
                }
            }
            setObjVar(schematic, craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME + ".isScaled", true);
        }
    }
    public static float rescaleArmorData(String attrib, int fromRow, int toRow, float value) throws InterruptedException
    {
        float newValue = Float.MIN_VALUE;
        if (attrib.startsWith(OBJVAR_LAYER_PREFIX) || attrib.equals("special_protection"))
        {
            newValue = convertArmorData(value, fromRow, toRow, DATATABLE_MIN_SPEC_PROT_COL);
        }
        else if (attrib.equals(OBJVAR_GENERAL_PROTECTION))
        {
            newValue = convertArmorData(value, fromRow, toRow, DATATABLE_MIN_GEN_PROT_COL);
        }
        else if (attrib.equals(OBJVAR_CONDITION))
        {
            newValue = convertArmorData(value, fromRow, toRow, DATATABLE_MIN_CONDITION_COL);
        }
        return newValue;
    }
    public static void updateItemAttributes(obj_id schematic, draft_schematic.attribute[] itemAttributes, int armorRow) throws InterruptedException
    {
        float resourceScale;
        float value;
        String componentName;
        float componentValue;
        int minProtection;
        int maxProtection;
        int minCondition;
        int maxCondition;

        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if ((itemAttribute.name).equals(OBJVAR_GENERAL_PROTECTION)) {
                resourceScale = dataTableGetFloat(DATATABLE_ARMOR, armorRow, DATATABLE_RES_GEN_PROT_COL) / 100.0f;
                value = itemAttribute.currentValue * resourceScale;
                componentName = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".scaled." + OBJVAR_GENERAL_PROTECTION;
                if (hasObjVar(schematic, componentName)) {
                    componentValue = getFloatObjVar(schematic, componentName);
                    value += componentValue;
                    minProtection = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MIN_GEN_PROT_COL);
                    maxProtection = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MAX_GEN_PROT_COL);
                    if (maxProtection != minProtection && minProtection != 0) {
                        value += ((float) minProtection) / ((float) (maxProtection - minProtection));
                    }
                    debugServerConsoleMsg(null, "Armor scaling general protection from resource value " + itemAttribute.currentValue + ", component value " + componentValue + " to " + value);
                } else {
                    debugServerConsoleMsg(null, "Armor scaling general protection from resource value " + itemAttribute.currentValue + " to " + value);
                }
                itemAttribute.currentValue = value;
            } else if ((itemAttribute.name).equals(OBJVAR_CONDITION)) {
                resourceScale = dataTableGetFloat(DATATABLE_ARMOR, armorRow, DATATABLE_RES_CONDITION_COL) / 100.0f;
                value = itemAttribute.currentValue * resourceScale;
                componentName = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".scaled." + OBJVAR_CONDITION;
                if (hasObjVar(schematic, componentName)) {
                    componentValue = getFloatObjVar(schematic, componentName);
                    value += componentValue;
                    minCondition = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MIN_CONDITION_COL);
                    maxCondition = dataTableGetInt(DATATABLE_ARMOR, armorRow, DATATABLE_MAX_CONDITION_COL);
                    if (maxCondition != minCondition && minCondition != 0) {
                        value += ((float) minCondition) / ((float) (maxCondition - minCondition));
                    }
                    debugServerConsoleMsg(null, "Armor scaling condition from resource value " + itemAttribute.currentValue + ", component value " + componentValue + " to " + value);
                } else {
                    debugServerConsoleMsg(null, "Armor scaling condition from resource value " + itemAttribute.currentValue + " to " + value);
                }
                itemAttribute.currentValue = value;
            }
        }
    }
    public static void saveScaledDataToPrototype(obj_id schematic, obj_id prototype, draft_schematic.attribute[] itemAttributes, String objvarBase) throws InterruptedException
    {
        clearCachedData(prototype);
        obj_var_list componentData = getObjVarList(schematic, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".scaled");
        if (componentData != null)
        {
            Enumeration keys = componentData.keys();
            String key;
            float value;
            boolean haveAttribute;

            while (keys.hasMoreElements())
            {
                key = (String)(keys.nextElement());
                value = componentData.getFloatObjVar(key);
                if (value < 0)
                {
                    value = 0;
                }
                else if (value > 1.0f)
                {
                    value = 1.0f;
                }
                haveAttribute = false;
                for (draft_schematic.attribute itemAttribute : itemAttributes) {
                    if ((itemAttribute.name).equals(key)) {
                        haveAttribute = true;
                        break;
                    }
                }
                if (!haveAttribute)
                {
                    setObjVar(prototype, objvarBase + "." + key, value);
                }
            }
        }
    }
    public static boolean isValidArmor(obj_id target) throws InterruptedException
    {
        if (!isValidId(target))
        {
            return false;
        }
        int armorType = getGameObjectType(target);
        String baseObjvar = getArmorBaseObjvar(armorType);
        if (armorType != GOT_armor_layer && armorType != GOT_armor_segment)
        {
            if (!hasObjVar(target, baseObjvar + "." + OBJVAR_ARMOR_LEVEL))
            {
                return false;
            }
            if (!hasObjVar(target, baseObjvar + "." + OBJVAR_ARMOR_CATEGORY))
            {
                return false;
            }
        }
        else 
        {
            if (!hasScript(target, SCRIPT_ARMOR_EXAMINE))
            {
                return false;
            }
        }
        return true;
    }
    public static int getArmorLevel(obj_id armor) throws InterruptedException
    {
        if (isValidId(armor) && utils.hasScriptVar(armor, SCRIPTVAR_CACHED_LEVEL))
        {
            return utils.getIntScriptVar(armor, SCRIPTVAR_CACHED_LEVEL);
        }
        String baseObjvar = getArmorBaseObjvar(armor);
        if (baseObjvar == null)
        {
            return -1;
        }
        String armorLevelObjVar = baseObjvar + "." + OBJVAR_ARMOR_LEVEL;
        if (!hasObjVar(armor, armorLevelObjVar))
        {
            CustomerServiceLog("armor", "WARNING: armor object (" + armor + ") missing objvar " + armorLevelObjVar);
            return -1;
        }
        int armorLevel = getIntObjVar(armor, armorLevelObjVar);
        utils.setScriptVar(armor, SCRIPTVAR_CACHED_LEVEL, armorLevel);
        return armorLevel;
    }
    public static int getArmorCategory(obj_id armor) throws InterruptedException
    {
        if (isValidId(armor) && utils.hasScriptVar(armor, SCRIPTVAR_CACHED_CATEGORY))
        {
            return utils.getIntScriptVar(armor, SCRIPTVAR_CACHED_CATEGORY);
        }
        String baseObjvar = getArmorBaseObjvar(armor);
        if (baseObjvar == null)
        {
            return -1;
        }
        String armorCategoryObjVar = baseObjvar + "." + OBJVAR_ARMOR_CATEGORY;
        if (!hasObjVar(armor, armorCategoryObjVar))
        {
            CustomerServiceLog("armor", "WARNING: armor object (" + armor + ") missing objvar " + armorCategoryObjVar);
            return -1;
        }
        int armorCategory = getIntObjVar(armor, armorCategoryObjVar);
        utils.setScriptVar(armor, SCRIPTVAR_CACHED_CATEGORY, armorCategory);
        return armorCategory;
    }
    public static int getCombatArmorGeneralProtection(obj_id objTarget) throws InterruptedException
    {
        return utils.getIntScriptVar(objTarget, SCRIPTVAR_CACHED_GENERAL_PROTECTION);
    }
    public static int getArmorGeneralProtection(obj_id armor) throws InterruptedException
    {
        if (isGameObjectTypeOf(getGameObjectType(armor), GOT_creature))
        {
            return utils.getIntScriptVar(armor, SCRIPTVAR_CACHED_GENERAL_PROTECTION);
        }
        return _getArmorGeneralProtection(armor, getArmorDatatableRow(armor));
    }
    public static dictionary getCombatArmorSpecialProtections(obj_id objTarget) throws InterruptedException
    {
        return utils.getDictionaryScriptVar(objTarget, SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS);
    }
    public static dictionary getArmorSpecialProtections(obj_id armor) throws InterruptedException
    {
        return _getArmorSpecialProtections(armor, getArmorDatatableRow(armor));
    }
    public static dictionary getPsgSpecialProtections(obj_id armor) throws InterruptedException
    {
        return _getPsgSpecialProtections(armor, getArmorDatatableRow(armor));
    }
    public static float getPsgEfficiency(obj_id psg) throws InterruptedException
    {
        if (isPsg(psg))
        {
            return utils.getFloatScriptVar(psg, SCRIPTVAR_PSG_EFFICIENCY);
        }
        return 0;
    }
    public static void setPsgEfficiency(obj_id psg, float efficiency) throws InterruptedException
    {
        if (isPsg(psg))
        {
            if (efficiency > 1.0f)
            {
                efficiency = 1.0f;
            }
            else if (efficiency < 0)
            {
                efficiency = 0;
            }
            utils.setScriptVar(psg, SCRIPTVAR_PSG_EFFICIENCY, efficiency);
            int iconCount = (int)(efficiency * 100.0f + 0.5f);
            if (iconCount == 0)
            {
                iconCount = 1;
            }
            setCount(psg, iconCount);
        }
    }
    public static boolean initializeArmor(obj_id target, int armorLevel, int armorCategory) throws InterruptedException
    {
        if (!isValidId(target))
        {
            CustomerServiceLog("armor", "WARNING: armor.initializeArmor called with invalid target " + target);
            return false;
        }
        if (armorLevel < AL_none || armorLevel >= AL_max)
        {
            CustomerServiceLog("armor", "WARNING: armor.initializeArmor called for target " + target + " with invalid armor level " + armorLevel);
            return false;
        }
        if (armorCategory < AC_none || armorCategory >= AC_max)
        {
            CustomerServiceLog("armor", "WARNING: armor.initializeArmor called for target " + target + " with invalid armor category " + armorCategory);
            return false;
        }
        clearCachedData(target);
        int armorType = getGameObjectType(target);
        String objvarBase = getArmorBaseObjvar(armorType);
        if (objvarBase == null)
        {
            CustomerServiceLog("armor", "WARNING: armor.initializeArmor called for target " + target + " with bad GOT: " + armorType);
            return false;
        }
        else if (armorType != GOT_armor_layer && armorType != GOT_armor_segment)
        {
            setObjVar(target, objvarBase + "." + OBJVAR_ARMOR_LEVEL, armorLevel);
            setObjVar(target, objvarBase + "." + OBJVAR_ARMOR_CATEGORY, armorCategory);
        }
        if (!hasScript(target, SCRIPT_ARMOR_EXAMINE))
        {
            attachScript(target, SCRIPT_ARMOR_EXAMINE);
        }
        return true;
    }
    public static boolean initializePsg(obj_id target, float rechargeRate, int protection, int condition) throws InterruptedException
    {
        if (!isPsg(target))
        {
            return false;
        }
        clearCachedData(target);
        setObjVar(target, OBJVAR_ARMOR_BASE + "." + OBJVAR_PSG_RECHARGE_RATE, (rechargeRate / 100.0f) * protection);
        setAbsoluteArmorSpecialProtection(target, DATATABLE_PSG_LAYER, protection);
        setMaxHitpoints(target, condition);
        return true;
    }
    public static int getTemplateRowFromMasterTable(String template) throws InterruptedException
    {
        if ((template.trim()).equals(""))
        {
            return -1;
        }
        return dataTableSearchColumnForString(template, "crafted_object_template", DATATABLE_MASTER_ARMOR);
    }
    public static int getArmorCategoryByTemplate(String template) throws InterruptedException
    {
        int row = getTemplateRowFromMasterTable(template);
        if (row < 0)
        {
            return -1;
        }
        return dataTableGetInt(DATATABLE_MASTER_ARMOR, row, "category");
    }
    public static boolean setArmorDataPercent(obj_id target, int armorLevel, int armorCategory, float generalProtection, float condition) throws InterruptedException {
        return initializeArmor(target, armorLevel, armorCategory) && _setArmorDataPercent(target, generalProtection, condition, false);
    }
    public static boolean setArmorDataPercent(obj_id target, float generalProtection, float condition) throws InterruptedException
    {
        return _setArmorDataPercent(target, generalProtection, condition, true);
    }
    public static boolean setAbsoluteArmorData(obj_id target, int armorLevel, int armorCategory, int generalProtection, int condition) throws InterruptedException {
        return initializeArmor(target, armorLevel, armorCategory) && setAbsoluteArmorData(target, generalProtection, condition);
    }
    public static boolean setAbsoluteArmorData(obj_id target, int generalProtection, int condition) throws InterruptedException
    {
        clearCachedData(target);
        int armorLevelRow = getArmorDatatableRow(target);
        if (armorLevelRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: armor._setAbsoluteArmorData unable to get armor level row for armor " + target);
            return false;
        }
        float minGP = dataTableGetInt(DATATABLE_ARMOR, armorLevelRow, DATATABLE_MIN_GEN_PROT_COL);
        float maxGP = dataTableGetInt(DATATABLE_ARMOR, armorLevelRow, DATATABLE_MAX_GEN_PROT_COL);
        float minCondition = dataTableGetInt(DATATABLE_ARMOR, armorLevelRow, DATATABLE_MIN_CONDITION_COL);
        float maxCondition = dataTableGetInt(DATATABLE_ARMOR, armorLevelRow, DATATABLE_MAX_CONDITION_COL);
        if (minGP == maxGP && minCondition == maxCondition)
        {
            return true;
        }
        float scaledGP = 0;
        float scaledCondition = 0;
        if (maxGP != minGP)
        {
            scaledGP = (generalProtection - minGP) / (maxGP - minGP);
        }
        if (maxCondition != minCondition)
        {
            scaledCondition = (condition - minCondition) / (maxCondition - minCondition);
        }
        return _setArmorDataPercent(target, scaledGP, scaledCondition, armorLevelRow, false);
    }
    public static void removeAllArmorData(obj_id target) throws InterruptedException
    {
        if (isValidArmor(target))
        {
            String baseObjvar = getArmorBaseObjvar(target);
            if (baseObjvar != null)
            {
                removeObjVar(target, baseObjvar);
            }
            clearCachedData(target);
        }
    }
    public static boolean setAbsoluteArmorSpecialProtection(obj_id target, int specialLayer, int protection) throws InterruptedException
    {
        if (!isValidArmor(target))
        {
            CustomerServiceLog("armor", "WARNING: armor.setAbsoluteArmorSpecialProtection called with invalid target " + target);
            return false;
        }
        if (specialLayer < 0 || specialLayer >= NUM_DATATABLE_ARMOR_LAYERS)
        {
            CustomerServiceLog("armor", "WARNING: armor.setAbsoluteArmorSpecialProtection called for target " + target + " with bad special layer " + specialLayer);
            return false;
        }
        clearCachedData(target);
        float maxProtection = dataTableGetInt(DATATABLE_ARMOR, specialLayer, DATATABLE_MAX_SPEC_PROT_COL);
        if (maxProtection <= 0)
        {
            return true;
        }
        float minProtection = dataTableGetInt(DATATABLE_ARMOR, specialLayer, DATATABLE_MIN_SPEC_PROT_COL);
        float scaledProtection = 1.0f;
        if (maxProtection != minProtection)
        {
            scaledProtection = (protection - minProtection) / (maxProtection - minProtection);
        }
        return setArmorSpecialProtectionPercent(target, specialLayer, scaledProtection);
    }
    public static boolean setArmorSpecialProtectionPercent(obj_id target, int specialLayer, float protection) throws InterruptedException
    {
        if (!isValidArmor(target))
        {
            CustomerServiceLog("armor", "WARNING: armor.setArmorSpecialProtectionPercent called with invalid target " + target);
            return false;
        }
        if (specialLayer < 0 || specialLayer >= NUM_DATATABLE_ARMOR_LAYERS)
        {
            CustomerServiceLog("armor", "WARNING: armor.setArmorSpecialProtectionPercent called for target " + target + " with bad special layer " + specialLayer);
            return false;
        }
        clearCachedData(target);
        int armorLevelRow = getArmorDatatableRow(target);
        if (armorLevelRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: armor.setAbsoluteArmorSpecialProtection unable to get armor level row for armor " + target);
            return false;
        }
        if (armorLevelRow != specialLayer)
        {
            protection = rescaleArmorData(OBJVAR_LAYER_PREFIX + specialLayer, specialLayer, armorLevelRow, protection);
        }
        if (protection < MIN_ARMOR_RANGE)
        {
            protection = MIN_ARMOR_RANGE;
        }
        else if (protection > MAX_ARMOR_RANGE)
        {
            protection = MAX_ARMOR_RANGE;
        }
        String baseObjvar = getArmorBaseObjvar(target);
        if (baseObjvar == null)
        {
            return false;
        }
        setObjVar(target, baseObjvar + "." + OBJVAR_LAYER_PREFIX + specialLayer, protection);
        return true;
    }
    public static boolean removeArmorSpecialProtection(obj_id target, int specialLayer) throws InterruptedException
    {
        if (!isValidArmor(target))
        {
            CustomerServiceLog("armor", "WARNING: armor.removeArmorSpecialProtection called with invalid target " + target);
            return false;
        }
        if (specialLayer < 0 || specialLayer >= NUM_DATATABLE_ARMOR_LAYERS)
        {
            CustomerServiceLog("armor", "WARNING: armor.removeArmorSpecialProtection called for target " + target + " with bad special layer " + specialLayer);
            return false;
        }
        clearCachedData(target);
        String baseObjvar = getArmorBaseObjvar(target);
        if (baseObjvar == null)
        {
            return false;
        }
        removeObjVar(target, baseObjvar + "." + OBJVAR_LAYER_PREFIX + specialLayer);
        return true;
    }
    public static int _getArmorGeneralProtection(obj_id armor, int row) throws InterruptedException
    {
        if (!isValidArmor(armor))
        {
            CustomerServiceLog("armor", "WARNING: armor._getArmorGeneralProtection called with invalid target " + armor);
            return -1;
        }
        if (utils.hasScriptVar(armor, SCRIPTVAR_CACHED_GENERAL_PROTECTION))
        {
            return utils.getIntScriptVar(armor, SCRIPTVAR_CACHED_GENERAL_PROTECTION);
        }
        String genProtectionObjVar = getArmorBaseObjvar(armor) + "." + OBJVAR_GENERAL_PROTECTION;
        if (!hasObjVar(armor, genProtectionObjVar))
        {
            CustomerServiceLog("armor", "WARNING: armor object (" + armor + ") missing objvar " + genProtectionObjVar);
            return -1;
        }
        float genProtection = getAbsoluteArmorAttribute(getFloatObjVar(armor, genProtectionObjVar), row, DATATABLE_MIN_GEN_PROT_COL);
        utils.setScriptVar(armor, SCRIPTVAR_CACHED_GENERAL_PROTECTION, (int)genProtection);
        return (int)genProtection;
    }
    public static dictionary _getArmorSpecialProtections(obj_id armor, int row) throws InterruptedException
    {
        if (!isValidArmor(armor))
        {
            CustomerServiceLog("armor", "WARNING: armor._getArmorSpecialProtections called with invalid target " + armor);
            return null;
        }
        dictionary protections = utils.getDictionaryScriptVar(armor, SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS);
        if (isArmorLayer(armor))
        {
            utils.removeScriptVar(armor, SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS);
        }
        if (protections != null)
        {
            return protections;
        }
        boolean isLayer = isArmorLayer(armor);
        float generalProtection = 0;
        if (!isLayer)
        {
            generalProtection = getArmorGeneralProtection(armor);
        }
        obj_var layer;
        dictionary layerProtections;
        Enumeration keys;
        Object key;
        float protection;

        for (int i = 0; i < NUM_DATATABLE_ARMOR_LAYERS; i++)
        {
            layer = getObjVar(armor, getArmorBaseObjvar(armor) + "." + OBJVAR_LAYER_PREFIX + i);
            if (layer != null)
            {
                layerProtections = getAbsoluteArmorSpecialProtection(armor, DATATABLE_LAYER_ROW + i, layer.getFloatData(), row);
                if (layerProtections != null)
                {
                    if (protections == null)
                    {
                        protections = new dictionary();
                    }
                    keys = layerProtections.keys();
                    while (keys.hasMoreElements())
                    {
                        key = keys.nextElement();
                        protection = layerProtections.getFloat(key);
                        if (!isLayer && protection + generalProtection < 0)
                        {
                            protection = -generalProtection;
                        }
                        protections.addFloat(key, protection);
                    }
                }
            }
        }
        if (protections != null)
        {
            utils.setScriptVar(armor, SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS, protections);
        }
        return protections;
    }
    public static dictionary _getPsgSpecialProtections(obj_id armor, int row) throws InterruptedException
    {
        if (!isIdValid(armor) || !isPsg(armor))
        {
            return null;
        }
        dictionary protections = _getArmorSpecialProtections(armor, row);
        if (protections == null)
        {
            return null;
        }
        dictionary newProtections = new dictionary(protections.size() * 2);
        float generalProtection = getArmorGeneralProtection(armor);
        float efficiency = utils.getFloatScriptVar(armor, OBJVAR_ARMOR_BASE + "." + OBJVAR_PSG_CURRENT_EFFICIENCY);
        if (efficiency > 1.0f)
        {
            efficiency = 1.0f;
        }
        else if (efficiency < 0)
        {
            efficiency = 0;
        }
        Enumeration keys = protections.keys();
        String key;
        float value;

        while (keys.hasMoreElements())
        {
            key = (String) (keys.nextElement());
            value = protections.getFloat(key) * efficiency;
            if (value + generalProtection < 0)
            {
                value = -generalProtection;
            }
            newProtections.put(key, value);
        }
        return newProtections;
    }
    public static boolean _setArmorDataPercent(obj_id target, float generalProtection, float condition, boolean testForValidArmor) throws InterruptedException
    {
        int armorLevelRow = getArmorDatatableRow(target);
        if (armorLevelRow < 0)
        {
            CustomerServiceLog("armor", "WARNING: armor._setArmorDataPercent unable to get armor level row for armor " + target);
            return false;
        }
        return _setArmorDataPercent(target, generalProtection, condition, armorLevelRow, testForValidArmor);
    }
    public static boolean _setArmorDataPercent(obj_id target, float generalProtection, float condition, int armorLevelRow, boolean testForValidArmor) throws InterruptedException
    {
        if (testForValidArmor && !isValidArmor(target))
        {
            CustomerServiceLog("armor", "WARNING: armor._setArmorDataPercent called with invalid target " + target);
            return false;
        }
        clearCachedData(target);
        if (generalProtection < MIN_ARMOR_RANGE)
        {
            generalProtection = MIN_ARMOR_RANGE;
        }
        else if (generalProtection > MAX_ARMOR_RANGE)
        {
            generalProtection = MAX_ARMOR_RANGE;
        }
        if (condition < MIN_ARMOR_RANGE)
        {
            condition = MIN_ARMOR_RANGE;
        }
        else if (condition > MAX_ARMOR_RANGE)
        {
            condition = MAX_ARMOR_RANGE;
        }
        String objvarBase = getArmorBaseObjvar(target);
        if (objvarBase == null)
        {
            return false;
        }
        setObjVar(target, objvarBase + "." + OBJVAR_GENERAL_PROTECTION, generalProtection);
        setObjVar(target, objvarBase + "." + OBJVAR_CONDITION, condition);
        float hp = getAbsoluteArmorAttribute(condition, armorLevelRow, DATATABLE_MIN_CONDITION_COL);
        if (hp != Float.MIN_VALUE)
        {
            setMaxHitpoints(target, (int)hp);
        }
        return true;
    }
    public static void recalculateArmorForMob(obj_id mob) throws InterruptedException
    {
        utils.setScriptVar(
                mob,
                SCRIPTVAR_CACHED_GENERAL_PROTECTION,
                ((int) getFloatObjVar(mob, OBJVAR_ARMOR_BASE + "." + OBJVAR_GENERAL_PROTECTION) + (int) getSkillStatisticModifier(mob, "expertise_innate_protection_all"))
        );
    }
    public static void recalculateArmorForPlayer(obj_id objPlayer) throws InterruptedException
    {
        if (!isPlayer(objPlayer) && isGameObjectTypeOf(getGameObjectType(objPlayer), GOT_creature))
        {
            recalculateArmorForMob(objPlayer);
            return;
        }
        trial.bumpSession(objPlayer, "displayDefensiveMods");
        messageTo(objPlayer, "setDisplayOnlyDefensiveMods", trial.getSessionDict(objPlayer, "displayDefensiveMods"), 5, false);
        final String strArmor[] = 
        {
            "chest2",
            "bicep_r",
            "bicep_l",
            "pants2",
            "bracer_upper_r",
            "bracer_upper_l",
            "hat"
        };
        final float[] fltWeightings = 
        {
            5,
            1,
            1,
            3,
            1,
            1,
            2
        };
        final float[] fltSpecialProts = new float[armor.DATATABLE_SPECIAL_PROTECTIONS.length];
        float fltGeneralProtection = 0;
        float fltTotal = 0;
        int lastArmorType = -1;
        int numLikePieces = 0;
        int armorSetWorn = -1;
        int[] armorTallyType = 
        {
            0,
            0,
            0
        };
        obj_id objArmor;
        float fltWeight;
        float fltSpecialProt;
        int thisArmorType;
        float fltArmorGeneralProtection;
        float fltReduction;

        for (int intI = 0; intI < strArmor.length; intI++)
        {
            objArmor = getObjectInSlot(objPlayer, strArmor[intI]);
            fltWeight = fltWeightings[intI];
            if (strArmor[intI].equals("chest2") && isIdNull(objArmor))
            {
                objArmor = getObjectInSlot(objPlayer, "chest1");
                fltWeight = 5;
            }
            if (strArmor[intI].equals("pants2") && isIdNull(objArmor))
            {
                objArmor = getObjectInSlot(objPlayer, "pants1");
                fltWeight = 3;
            }
            if (hasObjVar(objArmor, "armor.fake_armor"))
            {
                for (int i = 0; i < fltSpecialProts.length; i++)
                {
                    if (hasObjVar(objArmor, "armor.fake_armor." + DATATABLE_SPECIAL_PROTECTIONS[i]))
                    {
                        fltSpecialProt = (float) getIntObjVar(objArmor, "armor.fake_armor." + DATATABLE_SPECIAL_PROTECTIONS[i]);
                        if (fltSpecialProt != 0)
                        {
                            fltSpecialProts[i] += fltWeight * fltSpecialProt;
                        }
                    }
                }
            }
            if (hasObjVar(objArmor, "armor.general_protection_clothing"))
            {
                recalculatePseudoArmorForPlayer(objPlayer, objArmor, true);
                return;
            }
            if (isGameObjectTypeOf(getGameObjectType(objArmor), GOT_cybernetic))
            {
                numLikePieces++;
            }
            if (hasObjVar(objArmor, "armor.armorCategory"))
            {
                thisArmorType = getIntObjVar(objArmor, "armor.armorCategory");
                if (intI == 0)
                {
                    lastArmorType = getIntObjVar(objArmor, "armor.armorCategory");
                    numLikePieces++;
                }
                else if (intI == numLikePieces && thisArmorType == lastArmorType)
                {
                    numLikePieces++;
                    if (strArmor.length == numLikePieces)
                    {
                        armorSetWorn = lastArmorType;
                    }
                }
                if (thisArmorType > -1 && thisArmorType < 3)
                {
                    armorTallyType[thisArmorType]++;
                }
            }
            if (isIdValid(objArmor) && isValidArmor(objArmor) && !hasObjVar(objArmor, "armor.fake_armor"))
            {
                LOG("armor.recalculateArmorForPlayer ", "checking is armor of obj_id:  " + objArmor);
                dictionary dctSpecialProtections = getArmorSpecialProtections(objArmor);
                fltArmorGeneralProtection = (float) utils.getIntScriptVar(objArmor, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
                fltReduction = combat.getArmorDecayPercentage(objArmor);
                fltArmorGeneralProtection = fltArmorGeneralProtection * fltReduction;
                if (dctSpecialProtections != null)
                {
                    for (int intJ = 0; intJ < fltSpecialProts.length; intJ++)
                    {
                        if (armor.DATATABLE_SPECIAL_PROTECTIONS[intJ] != null)
                        {
                            fltSpecialProt = dctSpecialProtections.getFloat(armor.DATATABLE_SPECIAL_PROTECTIONS[intJ]);
                            if (fltSpecialProt != 0)
                            {
                                fltSpecialProts[intJ] += fltWeight * fltSpecialProt;
                            }
                        }
                    }
                }
                fltGeneralProtection += fltWeight * fltArmorGeneralProtection;
            }
            fltTotal += fltWeight;
        }
        float fltTempProtection = 0;
        float fltGenericProtection = getSkillStatisticModifier(objPlayer, "expertise_overridable_protection_generic");
        float fltInnateProtectionAll = getSkillStatisticModifier(objPlayer, "expertise_innate_protection_all");
        fltGeneralProtection = fltGeneralProtection / fltTotal;
        dictionary dctProtections = new dictionary();
        int intMod;
        for (int intI = 0; intI < fltSpecialProts.length; intI++)
        {
            fltSpecialProts[intI] = fltSpecialProts[intI] / fltTotal;
            fltSpecialProts[intI] += fltInnateProtectionAll;
            fltSpecialProts[intI] += getSkillStatisticModifier(objPlayer, "expertise_innate_protection_" + DATATABLE_SPECIAL_PROTECTIONS[intI]);
            fltSpecialProts[intI] += getSkillStatisticModifier(objPlayer, "expertise_armorset_protection_" + armorSetWorn + "_" + DATATABLE_SPECIAL_PROTECTIONS[intI]);
            if ((fltSpecialProts[intI] + fltGeneralProtection) > 0)
            {
                fltTempProtection = getSkillStatisticModifier(objPlayer, "expertise_overridable_protection_" + DATATABLE_SPECIAL_PROTECTIONS[intI]) + fltGenericProtection;
                if (((fltSpecialProts[intI] + fltGeneralProtection) * 0.5) < fltTempProtection)
                {
                    fltSpecialProts[intI] += fltTempProtection - ((fltSpecialProts[intI] + fltGeneralProtection) * 0.5);
                }
            }
            else 
            {
                fltSpecialProts[intI] += fltTempProtection = getSkillStatisticModifier(objPlayer, "expertise_overridable_protection_" + DATATABLE_SPECIAL_PROTECTIONS[intI]) + fltGenericProtection;
            }
            intMod = (int) (fltSpecialProts[intI] + fltGeneralProtection + 0.5f);
            applySkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI], -1 * getSkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI]));
            applySkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI], intMod);
            dctProtections.put(armor.DATATABLE_SPECIAL_PROTECTIONS[intI], fltSpecialProts[intI]);
        }
        utils.setScriptVar(objPlayer, armor.SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS, dctProtections);
        utils.setScriptVar(objPlayer, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION, (int)fltGeneralProtection);
        utils.setScriptVar(objPlayer, "armor.armor_set_worn", armorSetWorn);
        utils.setScriptVar(objPlayer, "armor.armor_type_tally", armorTallyType);
    }
    public static void recalculatePseudoArmorForPlayer(obj_id objPlayer, obj_id item, boolean applyArmor) throws InterruptedException
    {
        int intProtection = getIntObjVar(item, "armor.general_protection_clothing");
        int generalProtectionValue = 0;
        final float[] fltSpecialProts = new float[armor.DATATABLE_SPECIAL_PROTECTIONS.length];
        float fltInnateProtectionAll = getSkillStatisticModifier(objPlayer, "expertise_innate_protection_all");
        trial.bumpSession(objPlayer, "displayDefensiveMods");
        messageTo(objPlayer, "setDisplayOnlyDefensiveMods", trial.getSessionDict(objPlayer, "displayDefensiveMods"), 5, false);
        if (intProtection > 0)
        {
            dictionary dctProtections = new dictionary();
            int armorValue;
            int intMod;

            for (int intI = 0; intI < fltSpecialProts.length; intI++)
            {
                armorValue = 0;
                if (applyArmor)
                {
                    armorValue = intProtection;
                    fltSpecialProts[intI] += fltInnateProtectionAll;
                    fltSpecialProts[intI] += getSkillStatisticModifier(objPlayer, "expertise_innate_protection_" + DATATABLE_SPECIAL_PROTECTIONS[intI]);
                    fltSpecialProts[intI] += getSkillStatisticModifier(objPlayer, "expertise_armorset_protection_" + "_" + DATATABLE_SPECIAL_PROTECTIONS[intI]);
                    intMod = (int) (fltSpecialProts[intI] + armorValue + 0.5f);
                    applySkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI], -1 * getSkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI]));
                    applySkillStatisticModifier(objPlayer, DATATABLE_SPECIAL_PROTECTIONS[intI], intMod);
                    dctProtections.put(armor.DATATABLE_SPECIAL_PROTECTIONS[intI], fltSpecialProts[intI]);
                }
            }
            utils.setScriptVar(objPlayer, armor.SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS, dctProtections);
            if (applyArmor)
            {
                generalProtectionValue = intProtection;
                int[] armorTallyType = 
                {
                    7,
                    0,
                    0
                };
                utils.setScriptVar(objPlayer, "armor.armor_type_tally", armorTallyType);
            }
            utils.setScriptVar(objPlayer, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION, generalProtectionValue);
        }
        if (!applyArmor)
        {
            recalculateArmorForPlayer(objPlayer);
        }
    }
    public static String getPseudoArmorLevel(int pseudoLevel) throws InterruptedException
    {
        if (pseudoLevel < 2000)
        {
            return POWER_1;
        }
        else if (pseudoLevel < 4000)
        {
            return POWER_2;
        }
        else if (pseudoLevel < 5000)
        {
            return POWER_3;
        }
        else if (pseudoLevel < 6000)
        {
            return POWER_4;
        }
        else if (pseudoLevel < 7000)
        {
            return POWER_5;
        }
        else if (pseudoLevel < 8000)
        {
            return POWER_6;
        }
        else 
        {
            return POWER_7;
        }
    }
    public static boolean hasExpertiseArmorSetBonus(obj_id wearer) throws InterruptedException
    {
        if (!isPlayer(wearer))
        {
            return false;
        }
        if (!utils.hasScriptVar(wearer, "armor.armor_set_worn"))
        {
            return false;
        }
        else if (utils.getIntScriptVar(wearer, "armor.armor_set_worn") > -1)
        {
            return true;
        }
        return false;
    }
    public static int getExpertiseArmorSetId(obj_id wearer) throws InterruptedException
    {
        if (utils.hasScriptVar(wearer, "armor.armor_set_worn"))
        {
            return utils.getIntScriptVar(wearer, "armor.armor_set_worn");
        }
        return -1;
    }
    public static boolean turnArmorIntoSchem(obj_id player, obj_id armor) throws InterruptedException
    {
        if (!isIdValid(armor) || !exists(armor))
        {
            return false;
        }
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        String template = getTemplateName(armor);
        int row = dataTableSearchColumnForString(template, "crafted_object_template", DATATABLE_MASTER_ARMOR);
        if (row < 0)
        {
            return false;
        }
        String schemName = dataTableGetString(DATATABLE_MASTER_ARMOR, row, "schematic_name");
        if (schemName == null || schemName.equals(""))
        {
            return false;
        }
        obj_id newSchem = createObjectOverloaded("object/tangible/loot/loot_schematic/deconstructed_armor_schematic.iff", utils.getInventoryContainer(player));
        CustomerServiceLog("new_armor_conversion", "New schematic(" + schemName + ") converted from armor(" + armor + ") for player " + getFirstName(player) + "(" + player + ")");
        if (!isIdValid(newSchem) || !exists(newSchem))
        {
            return false;
        }
        setName(newSchem, utils.packStringId(getNameFromTemplate(template)));
        setObjVar(newSchem, "loot_schematic.schematic", "object/draft_schematic/armor/" + schemName + ".iff");
        setObjVar(newSchem, "loot_schematic.uses", 1);
        if (getGameObjectType(armor) == GOT_cybernetic_forearm)
        {
            setObjVar(newSchem, "loot_schematic.skill_req", "class_engineering_phase1_master");
        }
        else 
        {
            setObjVar(newSchem, "loot_schematic.skill_req", "class_munitions_phase1_master");
        }
        obj_id bioLink = getBioLink(armor);
        if (isIdValid(bioLink))
        {
            setBioLink(newSchem, bioLink);
        }
        if (utils.isItemNoDrop(armor))
        {
            setBioLink(newSchem, player);
        }
        attachScript(newSchem, "item.loot_schematic.loot_schematic");
        CustomerServiceLog("new_armor_conversion", "armor(" + armor + ") about to be destroyed on player " + getFirstName(player) + "(" + player + ") because it was converted into schematic " + schemName + "(" + newSchem + ")");
        return true;
    }
}
