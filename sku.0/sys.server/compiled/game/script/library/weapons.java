package script.library;

import script.*;
import script.combat_engine.combat_data;
import script.combat_engine.weapon_data;

import java.util.Vector;

public class weapons extends script.base_script
{
    public weapons()
    {
    }
    public static final String WEAPON_DATA_TABLE = "datatables/crafting/weapon_schematics.iff";
    public static final String COL_SCHEMATIC_NAME = "schematic_name";
    public static final String COL_CRAFTING_SCRIPT = "crafting_script";
    public static final String COL_ITEMS_PER_CONTAINER = "items_per_container";
    public static final String COL_NUM_COMPONENTS = "slots";
    public static final String COL_COMPLEXITY = "complexity";
    public static final String COL_XP = "xp";
    public static final String COL_XP_TYPE = "xp_type";
    public static final String COL_TEMPLATE = "crafted_object_template";
    public static final String COL_TEMPLATE_HASH = "crafted_object_template_hash";
    public static final String COL_MIN_DMG_LOW = "min_damage_low";
    public static final String COL_MIN_DMG_HIGH = "min_damage_high";
    public static final String COL_MAX_DMG_LOW = "max_damage_low";
    public static final String COL_MAX_DMG_HIGH = "max_damage_high";
    public static final String COL_MIN_DMG_HIGH_CAP = "min_damage_high_cap";
    public static final String COL_MAX_DMG_HIGH_CAP = "max_damage_high_cap";
    public static final String COL_MIN_DMG_LOW_CAP = "min_damage_low_cap";
    public static final String COL_MAX_DMG_LOW_CAP = "max_damage_low_cap";
    public static final String COL_SPEED_LOW = "attack_speed_low";
    public static final String COL_SPEED_HIGH = "attack_speed_high";
    public static final String COL_WOUND_CHANCE_LOW = "wound_chance_low";
    public static final String COL_WOUND_CHANCE_HIGH = "wound_chance_high";
    public static final String COL_HP_LOW = "hit_points_low";
    public static final String COL_HP_HIGH = "hit_points_high";
    public static final String COL_ACCURACY_LOW = "min_accuracy_low";
    public static final String COL_ACCURACY_HIGH = "min_accuracy_high";
    public static final String COL_MIN_RANGE_DISTANCE = "min_range_distance";
    public static final String COL_MAX_RANGE_DISTANCE = "max_range_distance";
    public static final String COL_ATTACK_COST_LOW = "special_attack_cost_low";
    public static final String COL_ATTACK_COST_HIGH = "special_attack_cost_high";
    public static final String COL_DAMAGE_TYPE = "damage_type";
    public static final String COL_ELEMENTAL_TYPE = "elemental_type";
    public static final String COL_ELEMENTAL_VALUE_LOW = "elemental_value_low";
    public static final String COL_ELEMENTAL_VALUE_HIGH = "elemental_value_high";
    public static final String COL_SLOT_TYPE = "slot_type";
    public static final String COL_SLOT_OPTIONAL = "optional";
    public static final String COL_SLOT_NAME_KEY = "slot_name";
    public static final String COL_INGREDIENT = "slot_ingredient_name";
    public static final String COL_INGREDIENT_COUNT = "slot_ingredient_count";
    public static final int VIA_TEMPLATE = 1;
    public static final int VIA_SCHEMATIC = 2;
    public static final int DEFAULT_VALUE = 10;
    public static final String WEAPONS = "weapons";
    public static final float MIN_AOE_PERCENT = 0.1f;
    public static final float MAX_AOE_PERCENT = 1.0f;
    public static final float MAX_WEAPON_RANGE = 64;
    public static final float MAX_STAT_VALIDATION_INTENSITY = 1.00f;
    public static final String OBJVAR_NEW_WP = "newWeapon";
    public static final String OBJVAR_NEW_WP_COMPONENT = OBJVAR_NEW_WP + ".component";
    public static final String OBJVAR_NEW_WP_WEAPON = OBJVAR_NEW_WP + ".weapon";
    public static final String OBJVAR_WP_LEVEL = OBJVAR_NEW_WP + ".coreLevel";
    public static final String OBJVAR_WP_CORE_QUALITY_MIN = OBJVAR_NEW_WP + ".coreQualityMin";
    public static final String OBJVAR_WP_CORE_QUALITY_MAX = OBJVAR_NEW_WP + ".coreQualityMax";
    public static final String OBJVAR_MODIFIER_GAS_QUALITY_MIN = OBJVAR_NEW_WP + ".gasQualityMin";
    public static final String OBJVAR_MODIFIER_GAS_QUALITY_MAX = OBJVAR_NEW_WP + ".gasQualityMax";
    public static final String OBJVAR_MODIFIER_MELEE_QUALITY_MIN = OBJVAR_NEW_WP + ".meleeQualityMin";
    public static final String OBJVAR_MODIFIER_MELEE_QUALITY_MAX = OBJVAR_NEW_WP + ".meleeQualityMax";
    public static final String OBJVAR_MODIFIER_COMPONENT_BONUS_MIN = OBJVAR_NEW_WP + ".componentBonusMin";
    public static final String OBJVAR_MODIFIER_COMPONENT_BONUS_MAX = OBJVAR_NEW_WP + ".componentBonusMax";
    public static final String OBJVAR_MODIFIER_APPEARANCE_BONUS_MIN = OBJVAR_NEW_WP + ".appearanceBonusMin";
    public static final String OBJVAR_MODIFIER_APPEARANCE_BONUS_MAX = OBJVAR_NEW_WP + ".appearanceBonusMax";
    public static final String OBJVAR_ELEMENTAL_VALUE = OBJVAR_NEW_WP + ".elementalValue";
    public static final String OBJVAR_ELEMENTAL_TYPE = OBJVAR_NEW_WP + ".elementalType";
    public static final String OBJVAR_CRIT_BONUS_RANGED = OBJVAR_NEW_WP + ".critBonusRanged";
    public static final String OBJVAR_CRIT_BONUS_MELEE = OBJVAR_NEW_WP + ".critBonusMelee";
    public static final int NEW_COMPONENT_MODIFIER = 1000;
    public static final int WP_CORE_MODIFIER = 10;
    public static final float NEW_COMPONENT_CAP = 0.075f;
    public static final float NEW_WP_DAMAGE_CAP = 1.50f;
    public static final String[] WEAPON_TYPES = 
    {
        "none",
        "rifle",
        "carbine",
        "pistol",
        "heavy",
        "melee",
        "directional",
        "component"
    };
    public static final String[] ATTRIBUTES = 
    {
        "HEALTH",
        "CONSTITUTION",
        "ACTION",
        "STAMINA",
        "MIND",
        "WILLPOWER"
    };
    public static final String WEAPON_CORE_TABLE = "datatables/crafting/weapon_core.iff";
    public static final String WEAPON_APP_TABLE = "datatables/crafting/weapon_appearance.iff";
    public static final String WEAPON_APP_BONUS_TABLE = "datatables/crafting/appearance_bonus.iff";
    public static final String WEAPON_CORE_LEVEL_COL = "core_level";
    public static final String WEAPON_APP_TYPE = "type";
    public static final String WEAPON_APP_TEMPLATE = "crafted_object_template";
    public static final String WEAPON_APP_SPEED = "attack_speed_high";
    public static final String WEAPON_APP_WOUND = "wound_chance_high";
    public static final String WEAPON_APP_COST = "special_attack_cost_high";
    public static final String WEAPON_APP_DAMAGE_TYPE = "damage_type";
    public static final String WEAPON_APP_ELEMENT_TYPE = "elemental_type";
    public static final String WEAPON_APP_ACC = "accuracy_high";
    public static final String WEAPON_APP_MAX_RANGE = "max_range_distance";
    public static final String WEAPON_APP_MIN_RANGE = "min_range_distance";
    public static final int NUM_DAMAGE_TYPES = 13;
    public static final string_id SID_WEAPON_TO_SCHEM = new string_id("spam", "weapon_to_schem");
    public static final string_id SID_CONVERT_PROMPT = new string_id("spam", "weapon_to_schem_prompt");
    public static final string_id SID_CONVERT_TITLE = new string_id("spam", "weapon_to_schem_title");
    public static final string_id SID_CONVERT_CONVERT_FAIL = new string_id("spam", "weapon_to_schem_failure");
    public static final string_id SID_CONVERT_CONVERT_SUCCESS = new string_id("spam", "weapon_to_schem_success");
    public static final string_id SID_CONVERT_INVALID_RESPONSE = new string_id("spam", "weapon_to_schem_invalid_response");
    public static final string_id SID_MIN_DAMAGE_GREATER_THAN_MAX = new string_id("spam", "min_damage_greater_than_max");
    public static final String[] DAMAGE_TYPE_NAMES = new String[NUM_DAMAGE_TYPES];
    public static final int CONVERSION_VERSION = 14;
    public static final int CORED_WEAPON_CONVERSION_VERSION = 3;
    public static int _GetTableValue(String col, String searchArg, int VIA_what) throws InterruptedException
    {
        String searchCol = COL_SCHEMATIC_NAME;
        int row;
        if (VIA_what == VIA_TEMPLATE)
        {
            searchCol = COL_TEMPLATE_HASH;
            row = dataTableSearchColumnForInt(getStringCrc(searchArg), searchCol, WEAPON_DATA_TABLE);
        }
        else 
        {
            row = dataTableSearchColumnForString(searchArg, searchCol, WEAPON_DATA_TABLE);
        }
        if (row < 0)
        {
            return DEFAULT_VALUE;
        }
        return dataTableGetInt(WEAPON_DATA_TABLE, row, col);
    }
    public static int getMinDamageLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MIN_DMG_LOW))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MIN_DMG_LOW);
    }
    public static int getMinDamageLow(String weaponName, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MIN_DMG_LOW, weaponName, VIA_what);
    }
    public static int getMinDamageHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MIN_DMG_HIGH))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MIN_DMG_HIGH);
    }
    public static int getMinDamageHighCap(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MIN_DMG_HIGH_CAP))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MIN_DMG_HIGH_CAP);
    }
    public static int getMinDamageLowCap(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MIN_DMG_LOW_CAP))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MIN_DMG_LOW_CAP);
    }
    public static int getMinDamageHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MIN_DMG_HIGH, name, VIA_what);
    }
    public static int getMinDamageHighCap(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MIN_DMG_HIGH_CAP, name, VIA_what);
    }
    public static int getMaxDamageLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MAX_DMG_LOW))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MAX_DMG_LOW);
    }
    public static int getMaxDamageLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MAX_DMG_LOW, name, VIA_what);
    }
    public static int getMaxDamageHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MAX_DMG_HIGH))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MAX_DMG_HIGH);
    }
    public static int getMaxDamageHighCap(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MAX_DMG_HIGH_CAP))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MAX_DMG_HIGH_CAP);
    }
    public static int getMaxDamageLowCap(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MAX_DMG_LOW_CAP))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MAX_DMG_LOW_CAP);
    }
    public static int getMaxDamageHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MAX_DMG_HIGH, name, VIA_what);
    }
    public static int getMaxDamageHighCap(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MAX_DMG_HIGH_CAP, name, VIA_what);
    }
    public static int getSpeedLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_SPEED_LOW))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_SPEED_LOW);
    }
    public static int getSpeedLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_SPEED_LOW, name, VIA_what);
    }
    public static int getSpeedHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_SPEED_HIGH))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_SPEED_HIGH);
    }
    public static int getSpeedHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_SPEED_HIGH, name, VIA_what);
    }
    public static int getWoundChanceLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_WOUND_CHANCE_LOW))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_WOUND_CHANCE_LOW);
    }
    public static int getWoundChanceLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_WOUND_CHANCE_LOW, name, VIA_what);
    }
    public static int getWoundChanceHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_WOUND_CHANCE_HIGH))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_WOUND_CHANCE_HIGH);
    }
    public static int getWoundChanceHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_WOUND_CHANCE_HIGH, name, VIA_what);
    }
    public static int getHPLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_HP_LOW))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_HP_LOW);
    }
    public static int getHPLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_HP_LOW, name, VIA_what);
    }
    public static int getHPHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_HP_HIGH))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_HP_HIGH);
    }
    public static int getHPHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_HP_HIGH, name, VIA_what);
    }
    public static int getAccuracyLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ACCURACY_LOW))
        {
            return 0;
        }
        return dic.getInt(COL_ACCURACY_LOW);
    }
    public static int getAccuracyLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ACCURACY_LOW, name, VIA_what);
    }
    public static int getAccuracyHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ACCURACY_HIGH))
        {
            return 0;
        }
        return dic.getInt(COL_ACCURACY_HIGH);
    }
    public static int getAccuracyHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ACCURACY_HIGH, name, VIA_what);
    }
    public static int getMinRangeDistance(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MIN_RANGE_DISTANCE))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MIN_RANGE_DISTANCE);
    }
    public static int getMinRangeDistance(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MIN_RANGE_DISTANCE, name, VIA_what);
    }
    public static int getMaxRangeDistance(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_MAX_RANGE_DISTANCE))
        {
            return DEFAULT_VALUE;
        }
        return dic.getInt(COL_MAX_RANGE_DISTANCE);
    }
    public static int getMaxRangeDistance(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_MAX_RANGE_DISTANCE, name, VIA_what);
    }
    public static int getAttackCostLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ATTACK_COST_LOW))
        {
            return 0;
        }
        return dic.getInt(COL_ATTACK_COST_LOW);
    }
    public static int getAttackCostLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ATTACK_COST_LOW, name, VIA_what);
    }
    public static int getAttackCostHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ATTACK_COST_HIGH))
        {
            return 0;
        }
        return dic.getInt(COL_ATTACK_COST_HIGH);
    }
    public static int getAttackCostHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ATTACK_COST_HIGH, name, VIA_what);
    }
    public static int getDamageType(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_DAMAGE_TYPE))
        {
            return -1;
        }
        return dic.getInt(COL_DAMAGE_TYPE);
    }
    public static int getDamageType(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_DAMAGE_TYPE, name, VIA_what);
    }
    public static int getElementalType(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ELEMENTAL_TYPE))
        {
            return 0;
        }
        return dic.getInt(COL_ELEMENTAL_TYPE);
    }
    public static int getElementalType(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ELEMENTAL_TYPE, name, VIA_what);
    }
    public static int getElementalValueLow(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ELEMENTAL_VALUE_LOW))
        {
            return 0;
        }
        return dic.getInt(COL_ELEMENTAL_VALUE_LOW);
    }
    public static int getElementalValueLow(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ELEMENTAL_VALUE_LOW, name, VIA_what);
    }
    public static int getElementalValueHigh(dictionary dic) throws InterruptedException
    {
        if (!dic.containsKey(COL_ELEMENTAL_VALUE_HIGH))
        {
            return 0;
        }
        return dic.getInt(COL_ELEMENTAL_VALUE_HIGH);
    }
    public static int getElementalValueHigh(String name, int VIA_what) throws InterruptedException
    {
        return _GetTableValue(COL_ELEMENTAL_VALUE_HIGH, name, VIA_what);
    }
    public static obj_id _createFromDictionary(dictionary dat, obj_id container, float speed, float damage, float effeciency, float elementalVal) throws InterruptedException
    {
        return _createFromDictionary(dat, container, speed, damage, effeciency, elementalVal, false);
    }
    public static obj_id _createFromDictionary(dictionary dat, obj_id container, float speed, float damage, float effeciency, float elementalVal, boolean overloadInv) throws InterruptedException
    {
        obj_id obj;
        if (!dat.containsKey(COL_TEMPLATE))
        {
            return null;
        }
        if (overloadInv)
        {
            obj = createObjectOverloaded(dat.getString(COL_TEMPLATE), container);
        }
        else 
        {
            obj = createObject(dat.getString(COL_TEMPLATE), container, "");
        }
        if (obj == null)
        {
            return null;
        }
        setWeaponAttributes(obj, dat, speed, damage, effeciency, elementalVal);
        return obj;
    }
    public static obj_id _createFromDictionary(dictionary dat, obj_id container, float percentOfMax) throws InterruptedException
    {
        return _createFromDictionary(dat, container, percentOfMax, percentOfMax, percentOfMax, percentOfMax);
    }
    public static void setWeaponAttributes(obj_id weapon, dictionary dat, float speedPercent, float damagePercent, float effeciencyPercent, float elementalValPercent) throws InterruptedException
    {
        int damageLow = (int)(getMinDamageHighCap(dat) * damagePercent);
        int minDamageLow = getMinDamageLow(dat);
        if (damageLow < minDamageLow)
        {
            damageLow = minDamageLow;
        }
        int damageHigh = (int)(getMaxDamageHighCap(dat) * damagePercent);
        int minDamageHigh = getMaxDamageLow(dat);
        if (damageHigh < minDamageHigh)
        {
            damageHigh = minDamageHigh;
        }
        int minSpeed = getSpeedLow(dat);
        int maxSpeed = getSpeedHigh(dat);
        int diff = maxSpeed - minSpeed;
        diff = Math.round((diff * (1.0f - speedPercent)));
        int speed = minSpeed + diff;
        if (speed < minSpeed)
        {
            speed = minSpeed;
        }
        int woundChance = (int)(getWoundChanceHigh(dat) * damagePercent);
        int minWoundChance = getWoundChanceLow(dat);
        if (woundChance < minWoundChance)
        {
            woundChance = minWoundChance;
        }
        int hp = (int)(getHPHigh(dat) * 0.3f);
        int minHp = getHPLow(dat);
        if (hp < minHp)
        {
            hp = minHp;
        }
        int accuracy = (int)(getAccuracyHigh(dat) * damagePercent);
        int minAccuracy = getAccuracyLow(dat);
        if (accuracy < minAccuracy)
        {
            accuracy = minAccuracy;
        }
        int minAttackCost = getAttackCostLow(dat);
        int maxAttackCost = getAttackCostHigh(dat);
        int valueRange = maxAttackCost - minAttackCost;
        valueRange = (int)(valueRange * effeciencyPercent);
        int attackCost = maxAttackCost - valueRange;
        int elementalType = getElementalType(dat);
        int elementalValue = (int)(getElementalValueHigh(dat) * elementalValPercent);
        int minElementalValue = getElementalValueLow(dat);
        if (elementalValue < minElementalValue)
        {
            elementalValue = minElementalValue;
        }
        int damType = getDamageType(dat);
        if (damType == -1)
        {
            damType = DAMAGE_KINETIC;
        }
        setWeaponMinDamage(weapon, damageLow);
        setWeaponMaxDamage(weapon, damageHigh);
        setWeaponAttackSpeed(weapon, speed / 100.0f);
        setWeaponWoundChance(weapon, woundChance / 10.0f);
        setMaxHitpoints(weapon, hp);
        setHitpoints(weapon, hp);
        setWeaponAccuracy(weapon, accuracy);
        setWeaponDamageType(weapon, damType);
        setWeaponAttackCost(weapon, attackCost);
        setWeaponElementalDamage(weapon, elementalType, elementalValue);
        setHeavyWeaponAoeSplashPercent(weapon);
    }
    public static void setWeaponAttributes(obj_id weapon, dictionary dat, float percentOfMax) throws InterruptedException
    {
        setWeaponAttributes(weapon, dat, percentOfMax, percentOfMax, percentOfMax, percentOfMax);
    }
    public static dictionary getWeaponDat(String weapon) throws InterruptedException
    {
        int row = dataTableSearchColumnForInt(getStringCrc(weapon), COL_TEMPLATE_HASH, WEAPON_DATA_TABLE);
        if (row < 0)
        {
            return null;
        }
        return dataTableGetRow(WEAPON_DATA_TABLE, row);
    }
    public static dictionary getWeaponDat(obj_id weapon) throws InterruptedException
    {
        String template = getTemplateName(weapon);
        return getWeaponDat(template);
    }
    public static dictionary getWeaponCoreData(int coreLevel) throws InterruptedException
    {
        int row = dataTableSearchColumnForInt(coreLevel, WEAPON_CORE_LEVEL_COL, WEAPON_CORE_TABLE);
        if (row < 0)
        {
            return null;
        }
        return dataTableGetRow(WEAPON_CORE_TABLE, row);
    }
    public static obj_id createPossibleWeapon(String name, obj_id container, float percentOfMax) throws InterruptedException
    {
        return createPossibleWeapon(name, container, percentOfMax, percentOfMax, percentOfMax, percentOfMax);
    }
    public static obj_id createPossibleWeapon(String name, obj_id container, float speed, float damage, float effeciency, float elementalVal) throws InterruptedException
    {
        obj_id thing = null;
        if (name.startsWith("object/weapon/"))
        {
            thing = createWeapon(name, container, VIA_TEMPLATE, speed, damage, effeciency, elementalVal);
        }
        if (!isIdValid(thing))
        {
            thing = createObject(name, container, "");
        }
        return thing;
    }
    public static obj_id createWeapon(String name, obj_id container, float percentOfMax) throws InterruptedException
    {
        return createWeapon(name, container, VIA_TEMPLATE, percentOfMax);
    }
    public static obj_id createWeapon(String name, obj_id container, float speed, float damage, float effeciency, float elementalVal) throws InterruptedException
    {
        return createWeapon(name, container, VIA_TEMPLATE, speed, damage, effeciency, elementalVal);
    }
    public static obj_id createWeapon(String name, obj_id container, int VIA_what, float speed, float damage, float effeciency, float elementalVal) throws InterruptedException
    {
        return createWeapon(name, container, VIA_what, speed, damage, effeciency, elementalVal, false);
    }
    public static obj_id createWeaponOverloaded(String name, obj_id container, float speed, float damage, float effeciency, float elementalVal) throws InterruptedException
    {
        return createWeapon(name, container, VIA_TEMPLATE, speed, damage, effeciency, elementalVal, true);
    }
    public static obj_id createWeapon(String name, obj_id container, int VIA_what, float speed, float damage, float effeciency, float elementalVal, boolean overloadInv) throws InterruptedException
    {
        String col = COL_SCHEMATIC_NAME;
        int row;
        if (VIA_what == VIA_TEMPLATE)
        {
            col = COL_TEMPLATE_HASH;
            row = dataTableSearchColumnForInt(getStringCrc(name), col, WEAPON_DATA_TABLE);
        }
        else 
        {
            row = dataTableSearchColumnForString(name, col, WEAPON_DATA_TABLE);
        }
        LOG("weapons", "Found " + name + " at row " + row);
        if (row < 0)
        {
            return null;
        }
        if (isPlayer(container))
        {
            container = utils.getInventoryContainer(container);
        }
        dictionary dic = dataTableGetRow(WEAPON_DATA_TABLE, row);
        obj_id weapon = _createFromDictionary(dic, container, speed, damage, effeciency, elementalVal, overloadInv);
        if (weapon != null && name.contains("component"))
        {
            attachScript(weapon, "item.special.serialize_component");
        }
        weapons.setWeaponData(weapon);
        return weapon;
    }
    public static obj_id createWeapon(String name, obj_id container, int VIA_what, float percentOfMax) throws InterruptedException
    {
        return createWeapon(name, container, VIA_what, percentOfMax, percentOfMax, percentOfMax, percentOfMax);
    }
    public static Vector getWeaponIdsForType(String fragment) throws InterruptedException
    {
        String type = toLower(fragment);
        String[] allWeaps = dataTableGetStringColumn(WEAPON_DATA_TABLE, COL_TEMPLATE);
        Vector names = new Vector();
        String template;
        for (int i = 0; i < allWeaps.length; i++)
        {
            template = dataTableGetString(WEAPON_DATA_TABLE, i, COL_TEMPLATE);
            if (template != null && (type.equals("all") || template.contains(type)))
            {
                if (template.contains("component"))
                {
                    continue;
                }
                names.add(dataTableGetString(WEAPON_DATA_TABLE, i, COL_SCHEMATIC_NAME));
            }
        }
        return names;
    }
    public static int createOneOfEach(String nameFragment, obj_id container, int VIA_what, float percentOfMax) throws InterruptedException
    {
        String col = COL_SCHEMATIC_NAME;
        if (VIA_what == VIA_TEMPLATE)
        {
            col = COL_TEMPLATE;
        }
        if (isPlayer(container))
        {
            container = utils.getInventoryContainer(container);
        }
        nameFragment = toLower(nameFragment);
        String[] allWeaps = dataTableGetStringColumn(WEAPON_DATA_TABLE, col);
        if (allWeaps == null || allWeaps.length < 1)
        {
            return -1;
        }
        int numCreated = 0;
        dictionary dic;
        for (int i = 0; i < allWeaps.length; i++)
        {
            dic = dataTableGetRow(WEAPON_DATA_TABLE, i);
            if (nameFragment.equals("all") || (dic.getString(col)).contains(nameFragment))
            {
                numCreated = (_createFromDictionary(dic, container, percentOfMax) != null ? numCreated + 1 : numCreated);
            }
        }
        return numCreated;
    }
    public static void grantOrDenyAllWeaponSchematics(String nameFragment, obj_id player, int VIA_what, boolean grant) throws InterruptedException
    {
        String col = COL_SCHEMATIC_NAME;
        if (VIA_what == VIA_TEMPLATE)
        {
            col = COL_TEMPLATE;
        }
        nameFragment = toLower(nameFragment);
        String[] allEntries = dataTableGetStringColumnNoDefaults(WEAPON_DATA_TABLE, col);
        trace.log(WEAPONS, "Got " + allEntries.length + " schematic entries from col " + col);
        String path;
        for (String allEntry : allEntries) {
            if (!nameFragment.equals("all") && !(toLower(allEntry)).contains(nameFragment)) {
                trace.log(WEAPONS, allEntry + " does will not be adjusted.");
                continue;
            }
            trace.log(WEAPONS, allEntry + " will be " + (grant ? "granted" : "revoked"));
            path = "object/draft_schematic/weapon/";
            if ((grant ? grantSchematic(player, path + allEntry + ".iff") : revokeSchematic(player, path + allEntry + ".iff"))) {
                sendSystemMessageTestingOnly(player, "Schematic " + (grant ? "granted: " : "revoked: ") + allEntry);
            }
        }
    }
    public static obj_id createLimitedUseSchematic(String name, int numUses, obj_id container, String schematicNameKey) throws InterruptedException
    {
        return createLimitedUseSchematic(name, VIA_TEMPLATE, numUses, container, schematicNameKey);
    }
    public static obj_id createLimitedUseSchematic(String name, int VIA_what, int numUses, obj_id container, String schematicNameKey) throws InterruptedException
    {
        return createLimitedUseSchematic(name, VIA_what, numUses, container, schematicNameKey, "class_munitions_phase4_master");
    }
    public static obj_id createLimitedUseSchematic(String name, int VIA_what, int numUses, obj_id container, String schematicNameKey, String skillRequired) throws InterruptedException
    {
        if ((toLower(name)).contains("component"))
        {
            return null;
        }
        String col = COL_SCHEMATIC_NAME;
        int row;
        if (VIA_what == VIA_TEMPLATE)
        {
            col = COL_TEMPLATE_HASH;
            row = dataTableSearchColumnForInt(getStringCrc(name), col, WEAPON_DATA_TABLE);
        }
        else 
        {
            row = dataTableSearchColumnForString(name, col, WEAPON_DATA_TABLE);
        }
        if (row < 0)
        {
            return null;
        }
        if (isPlayer(container))
        {
            container = utils.getInventoryContainer(container);
        }
        String schematicName = dataTableGetString(WEAPON_DATA_TABLE, row, COL_SCHEMATIC_NAME);
        obj_id obj = createObject("object/tangible/loot/loot_schematic/generic_limited_use.iff", container, "");
        if (obj == null)
        {
            return null;
        }
        setObjVar(obj, "loot_schematic.schematic", "object/draft_schematic/weapon/" + schematicName + ".iff");
        setObjVar(obj, "loot_schematic.uses", numUses);
        setObjVar(obj, "loot_schematic.skill_req", skillRequired);
        attachScript(obj, "item.loot_schematic.loot_schematic");
        setName(obj, new string_id("craft_weapon_ingredients_n", "sch_" + schematicNameKey));
        return obj;
    }
    public static float setHeavyWeaponAoeSplashPercent(obj_id weapon) throws InterruptedException
    {
        if (getWeaponType(weapon) == WEAPON_TYPE_HEAVY)
        {
            int weaponType = heavyweapons.getHeavyWeaponType(weapon);
            range_info rangeData = getWeaponRangeInfo(weapon);
            switch (weaponType)
            {
                case heavyweapons.TYPE_LAUNCHER_PISTOL:
                case heavyweapons.TYPE_LAVA_CANNON:
                case heavyweapons.TYPE_FLAME_THROWER:
                case heavyweapons.TYPE_HEAVY_FLAME_THROWER:
                case heavyweapons.TYPE_HEAVY_PARTICLE_BEAM:
                case heavyweapons.TYPE_HEAVY_ACID_BEAM:
                case heavyweapons.TYPE_LIGHTNING_BEAM_CANON:
                float rangeRatio = rangeData.maxRange / MAX_WEAPON_RANGE;
                float aoePercent = MAX_AOE_PERCENT - ((MAX_AOE_PERCENT - MIN_AOE_PERCENT) * rangeRatio);
                if (aoePercent > MAX_AOE_PERCENT)
                {
                    aoePercent = MAX_AOE_PERCENT;
                }
                else if (aoePercent < MIN_AOE_PERCENT)
                {
                    aoePercent = MIN_AOE_PERCENT;
                }
                setObjVar(weapon, "intAOEDamagePercent", aoePercent);
                return aoePercent;
            }
        }
        return 0;
    }
    public static void clickCombatWeaponConversion(obj_id weapon) throws InterruptedException
    {
        dictionary weaponDat = getWeaponDat(weapon);
        if (weaponDat == null)
        {
            CustomerServiceLog("weap_conversion_bad", "POSSIBLE :BROKEN WEAPON: TRIED TO CONVERT BUT COULD NOT GET WEAPONDATA FOR " + weapon);
            String template = getTemplateName(weapon);
            CustomerServiceLog("weap_conversion_bad", "WEAPON TEMPLATE IS: " + template);
            return;
        }
        if (jedi.isLightsaber(weapon))
        {
            validateLightsaberData(weapon, weaponDat);
        }
        else 
        {
            if (powerup.hasPowerUpInstalled(weapon))
            {
                powerup.cleanupWeaponPowerup(weapon);
            }
            validateWeaponData(weapon, weaponDat);
        }
    }
    public static boolean coredWeaponConversion(obj_id weapon) throws InterruptedException
    {
        int coreLevel = getCoreLevel(weapon);
        if (coreLevel <= 0)
        {
            CustomerServiceLog("weap_conversion_bad", "POSSIBLE :BROKEN WEAPON: TRIED TO CONVERT BUT COULD NOT GET WEAPONDATA FOR " + weapon);
            CustomerServiceLog("weap_conversion_bad", "Core level was " + coreLevel);
            return false;
        }
        dictionary weaponCoreDat = getWeaponCoreData(coreLevel);
        if (weaponCoreDat == null || weaponCoreDat.isEmpty())
        {
            CustomerServiceLog("weap_conversion_bad", "POSSIBLE :BROKEN WEAPON: TRIED TO CONVERT BUT COULD NOT GET WEAPONDATA FOR " + weapon);
            String template = getTemplateName(weapon);
            CustomerServiceLog("weap_conversion_bad", "WEAPON TEMPLATE IS: " + template);
            return false;
        }
        int damageType;
        int elementalType;
        int elementalValue;
        int accuracy;
        float coreBonus;
        float craftedBonus;
        coreBonus = getWeaponCoreQualityMin(weapon);
        craftedBonus = coreBonus + getWeaponComponentBonusesMinDamage(weapon);
        int tableMin = getCoreMinDamage(weapon, weaponCoreDat);
        float minDamage = tableMin * craftedBonus;
        CustomerServiceLog("new_weap_conversion", "minDamage pre-VerifyDamageRangeMin on weapon " + weapon + "(" + getTemplateName(weapon) + ") " + minDamage);
        minDamage = verifyDamageRangeMin(weapon, minDamage, weaponCoreDat);
        CustomerServiceLog("new_weap_conversion", "minDamage post-VerifyDamageRangeMin on weapon " + weapon + "(" + getTemplateName(weapon) + ") " + minDamage);
        setWeaponMinDamage(weapon, (int)minDamage);
        coreBonus = getWeaponCoreQualityMax(weapon);
        craftedBonus = coreBonus + getWeaponComponentBonusesMaxDamage(weapon);
        int tableMax = getCoreMaxDamage(weapon, weaponCoreDat);
        float maxDamage = tableMax * craftedBonus;
        CustomerServiceLog("new_weap_conversion", "maxDamage pre-VerifyDamageRangeMin on weapon " + weapon + "(" + getTemplateName(weapon) + ") " + maxDamage);
        maxDamage = verifyDamageRangeMax(weapon, maxDamage, weaponCoreDat);
        CustomerServiceLog("new_weap_conversion", "maxDamage post-VerifyDamageRangeMin on weapon " + weapon + "(" + getTemplateName(weapon) + ") " + maxDamage);
        setWeaponMaxDamage(weapon, (int)maxDamage);
        setWeaponAttackSpeed(weapon, getWeaponSpeed(weapon) / 100.0f);
        setWeaponWoundChance(weapon, getNewWeaponWoundChance(weapon));
        accuracy = getNewWeaponAccuracy(weapon);
        setWeaponAccuracy(weapon, accuracy);
        setWeaponAttackCost(weapon, getNewWeaponAttackCost(weapon));
        damageType = getNewWeaponDamageType(weapon);
        setWeaponDamageType(weapon, damageType);
        int tableElementalType = getNewWeaponTableElementType(weapon);
        elementalType = (int)getNewWeaponElementalType(weapon);
        if (tableElementalType != -1)
        {
            elementalType = tableElementalType;
        }
        craftedBonus = getNewWeaponElementalValue(weapon);
        int tableValue = getNewWeaponTableElementalValue(weapon, weaponCoreDat);
        elementalValue = (int)(tableValue * craftedBonus);
        if (elementalValue > 0)
        {
            CustomerServiceLog("new_weap_conversion", "elementalValue on prototype " + weapon + "(" + getTemplateName(weapon) + ") before Appearance bonus " + elementalValue);
            elementalValue += getElementalAppearanceBonus(weapon);
            CustomerServiceLog("new_weapon_crafting", "elementalValue on prototype " + weapon + "(" + getTemplateName(weapon) + ") after Appearance bonus " + elementalValue);
        }
        if (elementalType >= 0 && elementalValue > 0)
        {
            setWeaponElementalType(weapon, elementalType);
            setWeaponElementalValue(weapon, elementalValue);
        }
        base_class.range_info rangeData = new base_class.range_info();
        rangeData.minRange = getNewWeaponMinRange(weapon);
        rangeData.maxRange = getNewWeaponMaxRange(weapon);
        setObjVar(weapon, "weapon.original_max_range", rangeData.maxRange);
        setWeaponRangeInfo(weapon, rangeData);
        setHeavyWeaponAoeSplashPercent(weapon);
        setConversionId(weapon, CORED_WEAPON_CONVERSION_VERSION);
        return true;
    }
    public static void staticDynamicWeaponConversion(obj_id weapon) throws InterruptedException
    {
        int level = 1;
        if (hasObjVar(weapon, "dynamic_item.intLevelRequired"))
        {
            level = getIntObjVar(weapon, "dynamic_item.intLevelRequired");
        }
        dictionary weaponData = dataTableGetRow("datatables/item/dynamic_item/root_balance_data/weapon_data.iff", level);
        static_item.setupDynamicWeapon(weapon, "", level, weaponData, new dictionary(), false);
    }
    public static void validateWeaponData(obj_id weapon, dictionary weaponDat) throws InterruptedException
    {
        float randomMeter = rand(0.95f, 1.00f);
        int currentMinDamage = getWeaponMinDamage(weapon);
        int currentMaxDamage = getWeaponMaxDamage(weapon);
        int clickMasterMinDamageHigh = getMinDamageHighCap(weaponDat);
        int clickMasterMaxDamageHigh = getMaxDamageHighCap(weaponDat);
        int clickMasterMinDamageLow = getMinDamageLowCap(weaponDat);
        int clickMasterMaxDamageLow = getMaxDamageLowCap(weaponDat);
        int finalMinDamage = currentMinDamage;
        int finalMaxDamage = currentMaxDamage;
        if (finalMinDamage > clickMasterMinDamageHigh || finalMinDamage < clickMasterMinDamageLow)
        {
            finalMinDamage = (int)(clickMasterMinDamageHigh * randomMeter);
        }
        if (finalMaxDamage > clickMasterMaxDamageHigh || finalMaxDamage < clickMasterMaxDamageLow)
        {
            finalMaxDamage = (int)(clickMasterMaxDamageHigh * randomMeter);
        }
        setWeaponMinDamage(weapon, finalMinDamage);
        setWeaponMaxDamage(weapon, finalMaxDamage);
        float clickMasterSpeed = getSpeedLow(weaponDat) / 100.0f;
        setWeaponAttackSpeed(weapon, clickMasterSpeed);
        int clickMasterActionCost = getAttackCostLow(weaponDat);
        setWeaponAttackCost(weapon, clickMasterActionCost);
        int clickMasterWoundChance = getWoundChanceLow(weaponDat);
        setWeaponWoundChance(weapon, clickMasterWoundChance);
        validateWeaponRange(weapon, weaponDat);
        validateWeaponDamageType(weapon, weaponDat);
        if (getWeaponElementalType(weapon) > 0)
        {
            int currentElementalDamageType = getWeaponElementalType(weapon);
            int clickMasterDamageType = getElementalType(weaponDat);
            int currentElementalDamage = getWeaponElementalValue(weapon);
            int minElementalDamage = getElementalValueLow(weaponDat);
            int maxElementalDamage = getElementalValueHigh(weaponDat);
            if (currentElementalDamageType != clickMasterDamageType){
                setWeaponElementalType(weapon, clickMasterDamageType);
            }
            if (currentElementalDamage < minElementalDamage || currentElementalDamage > maxElementalDamage)
            {
                randomMeter = rand(0.95f, 1.00f);
                setWeaponElementalValue(weapon, (int)(maxElementalDamage * randomMeter));
            }
        }
        else 
        {
            int clickMasterDamageType = getElementalType(weaponDat);
            if (clickMasterDamageType > 0)
            {
                int clickMasterElementalDamage = ((getElementalValueLow(weaponDat) + getElementalValueHigh(weaponDat)) / 2);
                setWeaponElementalType(weapon, clickMasterDamageType);
                setWeaponElementalValue(weapon, clickMasterElementalDamage);
            }
        }
        setConversionId(weapon, CONVERSION_VERSION);
        weapons.setWeaponData(weapon);
        removeObjVar(weapon, "slicing");
        setHeavyWeaponAoeSplashPercent(weapon);
        obj_id player = utils.getContainingPlayer(weapon);
        if (isIdValid(player) && isPlayer(player))
        {
            String template = getTemplateName(weapon);
            CustomerServiceLog("jedi_saber", "WEAPON TEMPLATE CONVERTED!: " + template);
            CustomerServiceLog("jedi_saber", "Converting Weapon (" + weapon + ") - OLD MIN DAMAGE:" + currentMinDamage + " - NEW MIN DAMAGE - " + finalMinDamage);
            CustomerServiceLog("jedi_saber", "Converting Weapon (" + weapon + ") - OLD MAX DAMAGE:" + currentMaxDamage + " - NEW MAX DAMAGE - " + finalMaxDamage);
            CustomerServiceLog("jedi_saber", "Converting Weapon (" + weapon + ") - NEW SPEED - " + clickMasterSpeed);
            CustomerServiceLog("jedi_saber", "Converting Weapon (" + weapon + ") - NEW ACTION - " + clickMasterActionCost);
        }
    }
    public static void validateLightsaberData(obj_id saber, dictionary weaponDat) throws InterruptedException
    {
        int baseMinDamage = getIntObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MIN_DMG);
        int baseMaxDamage = getIntObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MAX_DMG);
        int clickMasterMinDamage = getMinDamageHighCap(weaponDat);
        int clickMasterMaxDamage = getMaxDamageHighCap(weaponDat);
        int clickMasterLowMinDamage = getMinDamageLowCap(weaponDat);
        int clickMasterLowMaxDamage = getMaxDamageLowCap(weaponDat);
        int finalMinDamage;
        int finalMaxDamage;
        finalMinDamage = (int)(clickMasterLowMinDamage + ((clickMasterMinDamage - clickMasterLowMinDamage) * 0.99f));
        finalMaxDamage = (int)(clickMasterLowMaxDamage + ((clickMasterMaxDamage - clickMasterLowMaxDamage) * 0.99f));
        setObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MIN_DMG, finalMinDamage);
        setObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MAX_DMG, finalMaxDamage);
        float clickMasterSpeed = getSpeedLow(weaponDat) / 100.0f;
        setWeaponAttackSpeed(saber, clickMasterSpeed);
        setObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_SPEED, clickMasterSpeed);
        int clickMasterActionCost = getAttackCostLow(weaponDat);
        setObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_ATTACK_COST, clickMasterActionCost);
        removeObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_FORCE);
        removeObjVar(saber, jedi.VAR_SABER_BASE + "." + jedi.VAR_FORCE);
        int clickMasterWoundChance = getWoundChanceLow(weaponDat);
        setObjVar(saber, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_WOUND, clickMasterWoundChance);
        validateWeaponRange(saber, weaponDat);
        setConversionId(saber, CONVERSION_VERSION);
        weapons.setWeaponData(saber);
        String template = getTemplateName(saber);
        CustomerServiceLog("jedi_saber", "LIGHTSABER TEMPLATE CONVERTED!: " + template);
        CustomerServiceLog("jedi_saber", "Converting LightSaber (" + saber + ") - OLD MIN DAMAGE:" + baseMinDamage + " - NEW MIN DAMAGE - " + finalMinDamage);
        CustomerServiceLog("jedi_saber", "Converting LightSaber (" + saber + ") - OLD MAX DAMAGE:" + baseMaxDamage + " - NEW MAX DAMAGE - " + finalMaxDamage);
        CustomerServiceLog("jedi_saber", "Converting LightSaber (" + saber + ") - NEW SPEED - " + clickMasterSpeed);
        CustomerServiceLog("jedi_saber", "Converting LightSaber (" + saber + ") - NEW ACTION - " + clickMasterActionCost);
        messageTo(saber, "handleResetSaberStats", null, 2.0f, false);
    }
    public static void validateWeaponRange(obj_id weapon) throws InterruptedException
    {
        if (hasObjVar(weapon, OBJVAR_NEW_WP))
        {
            range_info rangeData = getWeaponRangeInfo(weapon);
            boolean rangeAltered = false;
            float currentMinRange = rangeData.minRange;
            float currentMaxRange = rangeData.maxRange;
            int minRange = getNewWeaponMinRange(weapon);
            int maxRange = getNewWeaponMaxRange(weapon);
            if (currentMinRange > minRange)
            {
                rangeData.minRange = minRange;
                rangeAltered = true;
            }
            if (currentMaxRange != maxRange)
            {
                rangeData.maxRange = maxRange;
                rangeAltered = true;
            }
            if (rangeAltered)
            {
                setWeaponRangeInfo(weapon, rangeData);
            }
        }
        else 
        {
            validateWeaponRange(weapon, getWeaponDat(weapon));
        }
    }
    public static void validateWeaponRange(obj_id weapon, dictionary weaponDat) throws InterruptedException
    {
        if (weaponDat == null)
        {
            return;
        }
        range_info rangeData = getWeaponRangeInfo(weapon);
        boolean rangeAltered = false;
        float currentMinRange = rangeData.minRange;
        float currentMaxRange = rangeData.maxRange;
        int minRange = getMinRangeDistance(weaponDat);
        int maxRange = getMaxRangeDistance(weaponDat);
        if (currentMinRange > minRange)
        {
            rangeData.minRange = minRange;
            rangeAltered = true;
        }
        if (currentMaxRange != maxRange)
        {
            rangeData.maxRange = maxRange;
            rangeAltered = true;
        }
        if (rangeAltered)
        {
            setWeaponRangeInfo(weapon, rangeData);
        }
    }
    public static void validateWeaponFacoryCrateRange(obj_id crate, dictionary weaponDat) throws InterruptedException
    {
        float baseMinDamage = getFloatObjVar(crate, "crafting_attributes.crafting:minDamage");
        float baseMaxDamage = getFloatObjVar(crate, "crafting_attributes.crafting:maxDamage");
        int clickMasterMinDamageHigh = getMinDamageHighCap(weaponDat);
        int clickMasterMaxDamageHigh = getMaxDamageHighCap(weaponDat);
        int clickMasterMinDamageLow = getMinDamageLowCap(weaponDat);
        int clickMasterMaxDamageLow = getMaxDamageLowCap(weaponDat);
        if (baseMinDamage > clickMasterMinDamageHigh || baseMinDamage < clickMasterMinDamageLow)
        {
            setObjVar(crate, "crafting_attributes.crafting:minDamage", (float)(clickMasterMinDamageHigh));
        }
        if (baseMaxDamage > clickMasterMaxDamageHigh || baseMaxDamage < clickMasterMaxDamageLow)
        {
            setObjVar(crate, "crafting_attributes.crafting:maxDamage", (float)(clickMasterMaxDamageHigh));
        }
        int clickMasterSpeed = getSpeedLow(weaponDat);
        setObjVar(crate, "crafting_attributes.crafting:attackSpeed", (float)(clickMasterSpeed));
        int clickMasterActionCost = getAttackCostLow(weaponDat);
        setObjVar(crate, "crafting_attributes.crafting:attackCost", (float)(clickMasterActionCost));
        int clickMasterWoundChance = getWoundChanceLow(weaponDat);
        setObjVar(crate, "crafting_attributes.crafting:woundChance", (float)(clickMasterWoundChance));
        int clickMasterMaxRange = getMaxRangeDistance(weaponDat);
        setObjVar(crate, "crafting_attributes.crafting:maxRange", (float)(clickMasterMaxRange));
        float currentDamageType = getFloatObjVar(crate, "crafting_attributes.crafting:damageType");
        int clickMasterDamageType = getDamageType(weaponDat);
        if (currentDamageType != clickMasterDamageType)
        {
            setObjVar(crate, "crafting_attributes.crafting:damageType", (float)(clickMasterDamageType));
        }
        float weaponElementalType = getFloatObjVar(crate, "crafting_attributes.crafting:elementalType");
        if (weaponElementalType > 0)
        {
            int clickMasterElementalDamageType = getElementalType(weaponDat);
            float currentElementalDamage = getFloatObjVar(crate, "crafting_attributes.crafting:elementalValue");
            int clickMasterElementalDamage = ((getElementalValueLow(weaponDat) + getElementalValueHigh(weaponDat)) / 2);
            if (weaponElementalType != clickMasterElementalDamageType)
            {
                setObjVar(crate, "crafting_attributes.crafting:elementalType", (float)(clickMasterElementalDamageType));
            }
            if (currentElementalDamage > clickMasterElementalDamage)
            {
                setObjVar(crate, "crafting_attributes.crafting:elementalValue", (float)(clickMasterElementalDamage));
            }
        }
        setConversionId(crate, CONVERSION_VERSION);
        recomputeCrateAttributes(crate);
    }
    public static boolean isVersionCurrent(obj_id object) throws InterruptedException
    {
        int currentVersion = getConversionId(object);
        int masterVersion = CONVERSION_VERSION;
        return currentVersion == masterVersion;
    }
    public static void validateWeaponFactoryCrateFromSchematic(obj_id crate) throws InterruptedException
    {
        int draftSchematicObjectTemplateCrc = getIntObjVar(crate, "draftSchematic");
        if (draftSchematicObjectTemplateCrc != 0)
        {
            String draftSchematicObjectTemplate = getObjectTemplateName(draftSchematicObjectTemplateCrc);
            if (draftSchematicObjectTemplate != null && !draftSchematicObjectTemplate.equals(""))
            {
                String weaponObjectTemplate = getTemplateCreatedFromSchematic(draftSchematicObjectTemplate);
                if (weaponObjectTemplate != null && !weaponObjectTemplate.equals(""))
                {
                    dictionary weaponDat = getWeaponDat(weaponObjectTemplate);
                    if (weaponDat != null) {
                        validateWeaponFacoryCrateRange(crate, weaponDat);
                    }
                }
            }
        }
    }
    public static void initalizeWeaponFactoryCrateManufacturingSchematics(obj_id self) throws InterruptedException
    {
        if (!weapons.isVersionCurrent(self))
        {
            if (isGameObjectTypeOf(getGameObjectType(self), GOT_misc_factory_crate))
            {
                weapons.validateWeaponFactoryCrateFromSchematic(self);
            }
            if (isGameObjectTypeOf(getGameObjectType(self), GOT_data_manufacturing_schematic))
            {
                if (!hasObjVar(self, craftinglib.OBJVAR_IN_CRAFTING_SESSION))
                {
                    destroyObject(self);
                }
            }
        }
    }
    public static weapon_data getNewWeaponData(obj_id objWeapon) throws InterruptedException
    {
        if (!utils.hasScriptVar(objWeapon, "dctWeaponStats"))
        {
            return null;
        }
        dictionary dctWeaponStats = utils.getDictionaryScriptVar(objWeapon, "dctWeaponStats");
        return fillWeaponData(objWeapon, dctWeaponStats);
    }
    public static weapon_data fillWeaponData(obj_id weapon, combat_data dat) throws InterruptedException
    {
        weapon_data weaponData = new weapon_data();
        weaponData.id = weapon;
        weaponData.minDamage = dat.overloadWeaponMinDamage;
        weaponData.maxDamage = dat.overloadWeaponMaxDamage;
        weaponData.maxRange = dat.overloadWeaponMaxRange;
        if (!dat.specialLine.equals("no_proc"))
        {
            weaponData.weaponType = dat.overloadWeaponType;
            weaponData.weaponCategory = dat.overloadWeaponCategory;
        }
        else 
        {
            weapon_data default_data = getWeaponData(weapon);
            weaponData.weaponType = default_data.weaponType;
            weaponData.weaponCategory = default_data.weaponCategory;
        }
        weaponData.damageType = dat.overloadWeaponDamageType;
        weaponData.elementalType = dat.overloadWeaponElementalType;
        weaponData.elementalValue = dat.overloadWeaponElementalValue;
        weaponData.attackSpeed = dat.overloadWeaponAttackSpeed;
        weaponData.damageRadius = dat.overloadWeaponDamageRadius;
        return weaponData;
    }
    public static weapon_data fillWeaponData(obj_id objWeapon, dictionary dctWeaponStats) throws InterruptedException
    {
        weapon_data weaponData = new weapon_data();
        weaponData.id = objWeapon;
        weaponData.minDamage = dctWeaponStats.getInt("minDamage");
        weaponData.maxDamage = dctWeaponStats.getInt("maxDamage");
        weaponData.maxRange = dctWeaponStats.getFloat("maxRange");
        weaponData.weaponType = dctWeaponStats.getInt("weaponType");
        weaponData.weaponCategory = dctWeaponStats.getInt("weaponCategory");
        weaponData.damageType = dctWeaponStats.getInt("damageType");
        weaponData.elementalType = dctWeaponStats.getInt("elementalType");
        weaponData.elementalValue = dctWeaponStats.getInt("elementalValue");
        weaponData.attackSpeed = dctWeaponStats.getFloat("attackSpeed");
        weaponData.damageRadius = dctWeaponStats.getFloat("damageRadius");
        return weaponData;
    }
    public static void setWeaponData(obj_id objWeapon) throws InterruptedException
    {
        dictionary dctWeaponStats = new dictionary();
        dctWeaponStats.put("id", objWeapon);
        dctWeaponStats.put("minDamage", getWeaponMinDamage(objWeapon));
        dctWeaponStats.put("maxDamage", getWeaponMaxDamage(objWeapon));
        dctWeaponStats.put("maxRange", getMaxRange(objWeapon));
        dctWeaponStats.put("weaponType", getWeaponType(objWeapon));
        dctWeaponStats.put("weaponCategory", combat.getWeaponCategory(getWeaponType(objWeapon)));
        dctWeaponStats.put("damageType", getWeaponDamageType(objWeapon));
        dctWeaponStats.put("elementalType", getWeaponElementalType(objWeapon));
        dctWeaponStats.put("elementalValue", getWeaponElementalValue(objWeapon));
        dctWeaponStats.put("attackSpeed", getWeaponAttackSpeed(objWeapon));
        dctWeaponStats.put("damageRadius", getWeaponDamageRadius(objWeapon));
        utils.setScriptVar(objWeapon, "dctWeaponStats", dctWeaponStats);
    }
    public static void setWeaponData(obj_id objWeapon, int intMinDamage, int intMaxDamage, float fltMaxRange, int intWeaponType, int intDamageType, int intElementalType, int intElementalValue, float fltAttackSpeed, float fltDamageRadius) throws InterruptedException
    {
        dictionary dctWeaponStats = new dictionary();
        dctWeaponStats.put("id", objWeapon);
        dctWeaponStats.put("minDamage", intMinDamage);
        dctWeaponStats.put("maxDamage", intMaxDamage);
        dctWeaponStats.put("maxRange", fltMaxRange);
        dctWeaponStats.put("weaponType", intWeaponType);
        dctWeaponStats.put("weaponCategory", combat.getWeaponCategory(intWeaponType));
        dctWeaponStats.put("damageType", intDamageType);
        dctWeaponStats.put("elementalType", intElementalType);
        dctWeaponStats.put("elementalValue", intElementalValue);
        dctWeaponStats.put("attackSpeed", fltAttackSpeed);
        dctWeaponStats.put("damageRadius", fltDamageRadius);
        utils.setScriptVar(objWeapon, "dctWeaponStats", dctWeaponStats);
    }
    public static void adjustWeaponRangeForExpertise(obj_id player, obj_id self, boolean modify) throws InterruptedException
    {
        if (!hasObjVar(self, "weapon.original_max_range"))
        {
            return;
        }
        range_info rangeData = getWeaponRangeInfo(self);
        float originalRange = getFloatObjVar(self, "weapon.original_max_range");
        if (modify)
        {
            int weaponType = getWeaponType(self);
            float rangeMod = getSkillStatisticModifier(player, "expertise_range_bonus_all");
            rangeMod += getSkillStatisticModifier(player, "expertise_range_bonus_" + combat.getWeaponStringType(weaponType));
            if (combat.isRangedWeapon(self) || combat.isHeavyWeapon(self))
            {
                rangeMod += getSkillStatisticModifier(player, "expertise_range_bonus_ranged");
            }
            if (combat.isMeleeWeapon(self))
            {
                rangeMod += getSkillStatisticModifier(player, "expertise_range_bonus_melee");
            }
            rangeMod += getSkillStatisticModifier(player, "expertise_sm_rank_range_bonus");
            float adjustedMaxRange = rangeMod + originalRange;
            if (adjustedMaxRange > 64)
            {
                adjustedMaxRange = 64;
            }
            rangeData.maxRange = adjustedMaxRange;
        }
        else 
        {
            rangeData.maxRange = originalRange;
        }
        setWeaponRangeInfo(self, rangeData);
        setWeaponData(self);
    }
    public static void validateWeaponDamageType(obj_id weapon) throws InterruptedException
    {
        if (hasObjVar(weapon, OBJVAR_NEW_WP))
        {
            int currentDamageType = getWeaponDamageType(weapon);
            int damageType = getNewWeaponDamageType(weapon);
            if (currentDamageType != damageType)
            {
                setWeaponDamageType(weapon, damageType);
            }
        }
        else 
        {
            validateWeaponDamageType(weapon, getWeaponDat(weapon));
        }
    }
    public static void validateWeaponDamageType(obj_id weapon, dictionary weaponDat) throws InterruptedException
    {
        if (weaponDat == null)
        {
            return;
        }
        int currentDamageType = getWeaponDamageType(weapon);
        int damageType = getDamageType(weaponDat);
        if (currentDamageType != damageType)
        {
            setWeaponDamageType(weapon, damageType);
        }
    }
    public static int getCoreLevel(obj_id prototype) throws InterruptedException
    {
        if (hasObjVar(prototype, OBJVAR_WP_LEVEL))
        {
            return getIntObjVar(prototype, OBJVAR_WP_LEVEL);
        }
        else 
        {
            return (int)getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel");
        }
    }
    public static float getWeaponCoreQualityMin(obj_id prototype) throws InterruptedException
    {
        return getFloatObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN);
    }
    public static float getWeaponCoreQualityMax(obj_id prototype) throws InterruptedException
    {
        return getFloatObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX);
    }
    public static float getNewWeaponElementalType(obj_id prototype) throws InterruptedException
    {
        if (hasObjVar(prototype, OBJVAR_ELEMENTAL_TYPE))
        {
            return getFloatObjVar(prototype, OBJVAR_ELEMENTAL_TYPE);
        }
        return -1;
    }
    public static float getNewWeaponElementalValue(obj_id prototype) throws InterruptedException
    {
        return getFloatObjVar(prototype, OBJVAR_ELEMENTAL_VALUE);
    }
    public static float getWeaponGasQualityMin(obj_id prototype) throws InterruptedException
    {
        return getFloatObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN);
    }
    public static float getWeaponGasQualityMax(obj_id prototype) throws InterruptedException
    {
        return getFloatObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX);
    }
    public static int getElementalAppearanceBonus(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return 0;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_BONUS_TABLE);
        if (row < 0)
        {
            return 0;
        }
        return dataTableGetInt(WEAPON_APP_BONUS_TABLE, row, "ele_bonus");
    }
    public static int getCoreMinDamage(obj_id prototype, dictionary weaponDat) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        if (weaponDat == null || weaponDat.isEmpty())
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getCoreMinDamage::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        int wpType = dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_TYPE);
        String weaponType = WEAPON_TYPES[wpType];
        return weaponDat.getInt(weaponType + "_min_damage");
    }
    public static int getCoreMaxDamage(obj_id prototype, dictionary weaponDat) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        if (weaponDat == null || weaponDat.isEmpty())
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getCoreMaxDamage::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        int wpType = dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_TYPE);
        return weaponDat.getInt(WEAPON_TYPES[wpType] + "_max_damage");
    }
    public static int getWeaponSpeed(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return 1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getWeaponSpeed::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return 1;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_SPEED);
    }
    public static int getNewWeaponWoundChance(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return 0;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponWoundChance::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return 0;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_WOUND);
    }
    public static int getNewWeaponAttackCost(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return 0;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponAttackCost::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return 0;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_COST);
    }
    public static int getNewWeaponAccuracy(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return 0;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponAccuracy::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return 0;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_ACC);
    }
    public static int getNewWeaponTableElementType(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponTableElementType::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_ELEMENT_TYPE);
    }
    public static int getNewWeaponTableElementalValue(obj_id prototype, dictionary weaponDat) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        if (weaponDat == null || weaponDat.isEmpty())
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponTableElementalValue::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        int wpType = dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_TYPE);
        String weaponType = WEAPON_TYPES[wpType];
        return weaponDat.getInt("elemental_" + weaponType + "_average_damage");
    }
    public static int getNewWeaponDamageType(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponDamageType::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_DAMAGE_TYPE);
    }
    public static int getNewWeaponMaxRange(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponMaxRange::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_MAX_RANGE);
    }
    public static int getNewWeaponMinRange(obj_id prototype) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return -1;
        }
        String template = getTemplateName(prototype);
        int row = dataTableSearchColumnForString(template, WEAPON_APP_TEMPLATE, WEAPON_APP_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("weaponsCraftingError", "getNewWeaponMinRange::Could not find template(" + template + ") in weapon appearance table for prototype " + prototype);
            return -1;
        }
        return dataTableGetInt(WEAPON_APP_TABLE, row, WEAPON_APP_MIN_RANGE);
    }
    public static boolean performSocketing(obj_id prototype, String[] expMods) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return false;
        }
        if (expMods == null || expMods.length < 0)
        {
            return false;
        }
        obj_id player = getOwner(prototype);
        int[] mods = getEnhancedSkillStatisticModifiers(player, expMods);
        if (mods != null)
        {
            int sockets = 0;
            int experimentModTotal = 0;
            for (int mod : mods) {
                experimentModTotal += mod;
            }
            if (experimentModTotal > craftinglib.socketThreshold)
            {
                int chances = 1 + (experimentModTotal - craftinglib.socketThreshold) / craftinglib.socketDelta;
                for (int j = 0; j < chances; ++j)
                {
                    if (rand(1, 100) > craftinglib.socketChance)
                    {
                        ++sockets;
                    }
                }
                if (sockets > craftinglib.maxSockets)
                {
                    sockets = craftinglib.maxSockets;
                }
                if (sockets > 0)
                {
                    setCondition(prototype, CONDITION_MAGIC_ITEM);
                }
            }
            setSkillModSockets(prototype, sockets);
        }
        return true;
    }
    public static String getDamageTypeString(int type) throws InterruptedException
    {
        int next = 0;

        DAMAGE_TYPE_NAMES[next++] = "kinetic";
        DAMAGE_TYPE_NAMES[next++] = "energy";
        DAMAGE_TYPE_NAMES[next++] = "blast";
        DAMAGE_TYPE_NAMES[next++] = "stun";
        DAMAGE_TYPE_NAMES[next++] = "restraint";
        DAMAGE_TYPE_NAMES[next++] = "elemental_heat";
        DAMAGE_TYPE_NAMES[next++] = "elemental_cold";
        DAMAGE_TYPE_NAMES[next++] = "elemental_acid";
        DAMAGE_TYPE_NAMES[next++] = "elemental_electrical";
        DAMAGE_TYPE_NAMES[next++] = "environmental_heat";
        DAMAGE_TYPE_NAMES[next++] = "environmental_cold";
        DAMAGE_TYPE_NAMES[next++] = "environmental_acid";
        DAMAGE_TYPE_NAMES[next] = "environmental_electrical";

        int i = 0;
        int c = 1;
        while (c < type)
        {
            c *= 2;
            i++;
        }
        return DAMAGE_TYPE_NAMES[i];
    }
    public static boolean storeWeaponCraftingValues(obj_id prototype, obj_id schematicId, draft_schematic schematic) throws InterruptedException
    {
        draft_schematic.slot[] slots = schematic.getSlots();
        obj_id componentId;
        String reStatMod;
        String statModified;
        for (draft_schematic.slot slot : slots) {
            componentId = slot.ingredients[0].ingredient;
            if (!isIdValid(componentId) || !exists(componentId)) {
                componentId = schematicId;
                if (!isIdValid(componentId) || !exists(componentId)) {
                    continue;
                }
            }
            if (hasObjVar(componentId, "reverse_engineering.reverse_engineering_modifier")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar reverse_engineering.reverse_engineering_modifier");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting RE Bit data now on prototype " + prototype);
                reStatMod = getStringObjVar(componentId, "reverse_engineering.reverse_engineering_modifier");
                int ratio = getIntObjVar(componentId, "reverse_engineering.reverse_engineering_ratio");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ratio " + ratio + " on prototype " + prototype);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::reStatMod " + reStatMod + " on prototype " + prototype);
                setObjVar(prototype, craftinglib.OBJVAR_RE_RATIO, ratio);
                setObjVar(prototype, craftinglib.OBJVAR_RE_STAT_MODIFIED, reStatMod);
            }
            if (hasObjVar(componentId, "reverse_engineering.reverse_engineering_power")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar reverse_engineering.reverse_engineering_power");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting RE Bit data now on prototype " + prototype);
                float powerMod = getIntObjVar(componentId, "reverse_engineering.reverse_engineering_power");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::powerMod before modification of 40% " + powerMod + " on prototype " + prototype);
                powerMod *= craftinglib.RE_POWER_MODIFIER;
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::powerMod after modification of 40% " + powerMod + " on prototype " + prototype);
                setObjVar(prototype, craftinglib.OBJVAR_RE_VALUE, powerMod);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar" + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQualityLow data now on prototype " + prototype);
                float coreQualityLow = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::coreQualityLow " + coreQualityLow + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN, coreQualityLow);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar" + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQualityHigh data now on prototype " + prototype);
                float coreQualityHigh = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::coreQualityHigh " + coreQualityHigh + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX, coreQualityHigh);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusLow")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar" + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQuality data now on prototype " + prototype);
                float appBonus = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::appearanceBonusLow " + appBonus + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_APPEARANCE_BONUS_MIN, appBonus);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusHigh")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar" + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQuality data now on prototype " + prototype);
                float appBonus = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "appearanceBonusHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::appearanceBonusHigh " + appBonus + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_APPEARANCE_BONUS_MAX, appBonus);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting gasQualityLow data now on prototype " + prototype);
                if (!hasObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MIN)) {
                    float gasQualityLow = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow");
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::gasQualityLow " + gasQualityLow + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, gasQualityLow);
                } else {
                    float gasQualityLow = getIntObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MIN);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::gasQualityLow " + gasQualityLow + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, gasQualityLow);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting gasQualityHigh data now on prototype " + prototype);
                if (!hasObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MAX)) {
                    float gasQualityHigh = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh");
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::gasQualityHigh " + gasQualityHigh + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, gasQualityHigh);
                } else {
                    float gasQualityHigh = getIntObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MAX);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::gasQualityHigh " + gasQualityHigh + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, gasQualityHigh);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting meleeComponentQualityLow data now on prototype " + prototype);
                if (!hasObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MIN)) {
                    float meleeQuality = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow");
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::meleeComponentQualityLow " + meleeQuality + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, meleeQuality);
                } else {
                    float meleeQuality = getIntObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::meleeComponentQualityLow " + meleeQuality + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, meleeQuality);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting meleeComponentQualityHigh data now on prototype " + prototype);
                if (!hasObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MAX)) {
                    float meleeQuality = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh");
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::meleeComponentQualityHigh " + meleeQuality + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, meleeQuality);
                } else {
                    float meleeQuality = getIntObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::meleeComponentQualityHigh " + meleeQuality + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, meleeQuality);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalValue")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalValue");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting elementalValue data now on prototype " + prototype);
                float elementalValue = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalValue");
                if (elementalValue >= 0.0f) {
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::elementalValue " + elementalValue + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_ELEMENTAL_VALUE, elementalValue);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalType")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalType");
                float elementalType = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "elementalType");
                if (elementalType >= 0) {
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting elementalType data now on prototype " + prototype);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::elementalType " + elementalType + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_ELEMENTAL_TYPE, elementalType);
                }
            }
            if (hasObjVar(componentId, craftinglib.OBJVAR_RE_STAT_MODIFIED)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.OBJVAR_RE_STAT_MODIFIED);
                statModified = getStringObjVar(componentId, craftinglib.OBJVAR_RE_STAT_MODIFIED);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting statModified data now on prototype " + prototype);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::statModified " + statModified + " on prototype " + prototype);
                setObjVar(prototype, craftinglib.OBJVAR_RE_STAT_MODIFIED, statModified);
                int ratio = getIntObjVar(componentId, craftinglib.OBJVAR_RE_RATIO);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting ratio data now on prototype " + prototype);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ratio " + ratio + " on prototype " + prototype);
                setObjVar(prototype, craftinglib.OBJVAR_RE_RATIO, ratio);
            }
            if (hasObjVar(componentId, craftinglib.OBJVAR_RE_VALUE)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.OBJVAR_RE_VALUE);
                float powerBit = getFloatObjVar(componentId, craftinglib.OBJVAR_RE_VALUE);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting powerBit data now on prototype " + prototype);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::powerBit " + powerBit + " on prototype " + prototype);
                setObjVar(prototype, craftinglib.OBJVAR_RE_VALUE, powerBit);
            }
            if (hasObjVar(componentId, OBJVAR_WP_CORE_QUALITY_MIN)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_WP_CORE_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQualityLow data now on prototype " + prototype);
                float coreQuality = getFloatObjVar(componentId, OBJVAR_WP_CORE_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::coreQualityLow " + coreQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN, coreQuality);
            }
            if (hasObjVar(componentId, OBJVAR_WP_CORE_QUALITY_MAX)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_WP_CORE_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting coreQualityHigh data now on prototype " + prototype);
                float coreQuality = getFloatObjVar(componentId, OBJVAR_WP_CORE_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::coreQualityHigh " + coreQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX, coreQuality);
            }
            if (hasObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MIN)) {
                float gasQuality = getFloatObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_MODIFIER_GAS_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting gasQualityLow data now to " + gasQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, gasQuality);
            }
            if (hasObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MAX)) {
                float gasQuality = getFloatObjVar(componentId, OBJVAR_MODIFIER_GAS_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_MODIFIER_GAS_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting gasQualityHigh data now to " + gasQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, gasQuality);
            }
            if (hasObjVar(componentId, OBJVAR_ELEMENTAL_VALUE)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_ELEMENTAL_VALUE);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting elementalValue data now on prototype " + prototype);
                float elementalValue = getFloatObjVar(componentId, OBJVAR_ELEMENTAL_VALUE);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::elementalValue " + elementalValue + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_ELEMENTAL_VALUE, elementalValue);
            }
            if (hasObjVar(componentId, OBJVAR_ELEMENTAL_TYPE)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_ELEMENTAL_TYPE);
                float elementalType = getFloatObjVar(componentId, OBJVAR_ELEMENTAL_TYPE);
                if (elementalType >= 0) {
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting elementalType data now on prototype " + prototype);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::elementalType " + elementalType + " on prototype " + prototype);
                    setObjVar(prototype, OBJVAR_ELEMENTAL_TYPE, elementalType);
                }
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel")) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel");
                int corelevel = (int) getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreLevel");
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting corelevel data now on prototype " + prototype);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::corelevel " + corelevel + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_WP_LEVEL, corelevel);
            }
            if (hasObjVar(componentId, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN) && !hasObjVar(prototype, OBJVAR_NEW_WP_WEAPON)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::ingredient " + componentId + " has the objvar " + weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
                float previousBonus = getFloatObjVar(prototype, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::previousBonusMin " + previousBonus + " from prototype " + prototype);
                float componentBonus = getFloatObjVar(componentId, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::componentBonusMin " + componentBonus + " from slotObject " + componentId);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::setting componentBonus data now on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN, (previousBonus + componentBonus));
            }
            if (hasObjVar(componentId, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MAX) && !hasObjVar(prototype, OBJVAR_NEW_WP_WEAPON)) {
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::ingredient " + componentId + " has the objvar " + weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
                float previousBonus = getFloatObjVar(prototype, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::previousBonusMax " + previousBonus + " from prototype " + prototype);
                float componentBonus = getFloatObjVar(componentId, weapons.OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ccomponentBonus::omponentBonusMax " + componentBonus + " from slotObject " + componentId);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::componentBonus::setting componentBonus data now on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX, (previousBonus + componentBonus));
            }
            if (hasObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MIN)) {
                float meleeQuality = getFloatObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting meleeQuality_min data now to " + meleeQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, meleeQuality);
            }
            if (hasObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MAX)) {
                float meleeQuality = getFloatObjVar(componentId, OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
                CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting meleeQuality_max data now to " + meleeQuality + " on prototype " + prototype);
                setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, meleeQuality);
            }
            if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged") || hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_melee")) {
                if (isRangedCore(prototype) || isMeleeCore(prototype) || isHeavyCore(prototype)) {
                    int critBonus;
                    if (hasObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged")) {
                        CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged");
                        critBonus = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_ranged");
                        setObjVar(prototype, OBJVAR_CRIT_BONUS_RANGED, critBonus);
                        CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting critBonus data now to " + critBonus + " on prototype " + prototype);
                        setCategorizedSkillModBonus(prototype, "weapon", "combat_critical_ranged", critBonus);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_melee");
                        critBonus = getIntObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "combat_critical_melee");
                        setObjVar(prototype, OBJVAR_CRIT_BONUS_MELEE, critBonus);
                        CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting critBonus data now to " + critBonus + " on prototype " + prototype);
                        setCategorizedSkillModBonus(prototype, "weapon", "combat_critical_melee", critBonus);
                    }
                }
            }
            if (hasObjVar(componentId, "attribute.bonus")) {
                LOG("sissynoid", "attribute.bonus found");
                int[] attribsModified = getAttributeBonuses(componentId);
                if (attribsModified != null && attribsModified.length > 0) {
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::ingredient " + componentId + " has an attribute bonus.");
                    int[] attribBonus = new int[NUM_ATTRIBUTES];
                    for (int j = 0; j < attribsModified.length; ++j) {
                        if (attribsModified[j] > 0) {
                            attribBonus[j] = attribsModified[j];
                            CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting Attribute Bonus " + ATTRIBUTES[j] + " To " + attribsModified[j]);
                        }
                    }
                    setAttributeBonuses(prototype, attribBonus);
                }
                if (hasObjVar(componentId, "attribute.bonus") && hasObjVar(prototype, "armor.general_protection")) {
                    CustomerServiceLog("new_weapon_crafting", "Cybernetics: Component(" + componentId + ") is being added to a Cybernetic(" + prototype + ") that has an Armor Core; Armor Value Reduction will occur by design.");
                    LOG("sissynoid", "Cybernetic has Armor Core and Modules - Nerf the Core: prototype: " + prototype);
                    setObjVar(prototype, "modifyCyberneticArmorValue", true);
                }
            }
            if (hasObjVar(componentId, "cybernetic.special_protection")) {
                LOG("sissynoid", "Cybernetic Protection Module found!");
                int protectionType = getIntObjVar(componentId, "cybernetic.special_protection");
                setObjVar(prototype, "cybernetic.special_protection.type", protectionType);
                float protectionValue = getFloatObjVar(componentId, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "cybernetic_electrical_resist_module");
                setObjVar(prototype, "cybernetic.special_protection.value", protectionValue);
                CustomerServiceLog("new_weapon_crafting", "Cybernetics: Component(" + componentId + ") has Special Protection - Adding these to the Cybernetic(" + prototype + "): protection type: (" + protectionType + ") and Value: (" + protectionValue + ").");
                LOG("sissynoid", "WEAPON SCRIPTLIB: protection module type: " + protectionType + " and Value: " + protectionValue);
            }
            if (hasObjVar(componentId, OBJVAR_CRIT_BONUS_MELEE) || hasObjVar(componentId, OBJVAR_CRIT_BONUS_RANGED)) {
                if (hasObjVar(componentId, OBJVAR_CRIT_BONUS_MELEE)) {
                    int critBonus = getIntObjVar(componentId, OBJVAR_CRIT_BONUS_MELEE);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting critBonus data now to " + critBonus + " on prototype " + prototype);
                    setCategorizedSkillModBonus(prototype, "weapon", "combat_critical_melee", critBonus);
                    setObjVar(prototype, OBJVAR_CRIT_BONUS_MELEE, critBonus);
                }
                if (hasObjVar(componentId, OBJVAR_CRIT_BONUS_RANGED)) {
                    int critBonus = getIntObjVar(componentId, OBJVAR_CRIT_BONUS_RANGED);
                    CustomerServiceLog("new_weapon_crafting", "storeWeaponCraftingValues::setting critBonus data now to " + critBonus + " on prototype " + prototype);
                    setCategorizedSkillModBonus(prototype, "weapon", "combat_critical_ranged", critBonus);
                    setObjVar(prototype, OBJVAR_CRIT_BONUS_RANGED, critBonus);
                }
            }
        }
        return true;
    }
    public static boolean setComponentObjVars(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        if (!isIdValid(prototype) || !exists(prototype))
        {
            return false;
        }
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            float attribValue = itemAttribute.currentValue;
            if (((itemAttribute.name).getAsciiId()).equals("gasQualityLow")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityLow value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityLow value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityLow");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MIN, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("gasQualityHigh")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityHigh value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityHigh value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityHigh on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "gasQualityHigh");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_GAS_QUALITY_MAX, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("componentBonusLow")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusLow")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusLow");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new componentBonusLow value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusLow");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new componentBonusLow value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::componentBonusLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusLow")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusLow");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityHigh on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("componentBonusHigh")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusHigh")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusHigh");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new componentBonusHigh value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusHigh");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new componentBonusHigh value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::componentBonusHigh on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusHigh")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "componentBonusHigh");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("meleeComponentQualityLow")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new meleeComponentQualityLow value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new meleeComponentQualityLow value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::meleeComponentQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityLow");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MIN, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("meleeComponentQualityHigh")) {
                if (hasObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh");
                        attribValue = getFloatObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new meleeComponentQualityHigh value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh");
                        attribValue /= NEW_COMPONENT_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new meleeComponentQualityHigh value is " + attribValue);
                    }
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, attribValue);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::meleeComponentQualityHigh on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "meleeComponentQualityHigh");
                    attribValue /= NEW_COMPONENT_MODIFIER;
                    if (attribValue > NEW_COMPONENT_CAP) {
                        attribValue = NEW_COMPONENT_CAP;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::gasQualityLow on prototype(" + prototype + ")was higher than the cap of " + NEW_COMPONENT_CAP + ", so we lowered it to the cap.");
                    }
                    setObjVar(prototype, OBJVAR_MODIFIER_MELEE_QUALITY_MAX, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("coreQualityLow")) {
                if (hasObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                        attribValue = getFloatObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityLow value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                        attribValue /= WP_CORE_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new coreQualityLow value is " + attribValue);
                    }
                    setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN, attribValue);
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow", attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow");
                    attribValue /= WP_CORE_MODIFIER;
                    setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MIN, attribValue);
                }
            }
            if (((itemAttribute.name).getAsciiId()).equals("coreQualityHigh")) {
                if (hasObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX)) {
                    if (!hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh")) {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") dont have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                        attribValue = getFloatObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX);
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new gasQualityHigh value is " + attribValue);
                    } else {
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::we(" + prototype + ") have the objvar " + craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                        attribValue /= WP_CORE_MODIFIER;
                        CustomerServiceLog("new_weapon_crafting", "setComponentObjVars::new coreQualityHigh value is " + attribValue);
                    }
                    setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX, attribValue);
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh", attribValue);
                } else if (hasObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh")) {
                    attribValue = getFloatObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                    attribValue /= WP_CORE_MODIFIER;
                    setObjVar(prototype, OBJVAR_WP_CORE_QUALITY_MAX, attribValue);
                }
            }
        }
        return true;
    }
    public static boolean isCoredWeapon(obj_id weapon) throws InterruptedException {
        return !(!isIdValid(weapon) || !exists(weapon)) && hasObjVar(weapon, OBJVAR_NEW_WP_WEAPON);
    }
    public static float getWeaponComponentBonusesMinDamage(obj_id weapon) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return 0.0f;
        }
        float craftBonuses = 0.0f;
        if (hasObjVar(weapon, OBJVAR_MODIFIER_GAS_QUALITY_MIN))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_GAS_QUALITY_MIN);
        }
        else 
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_MELEE_QUALITY_MIN);
        }
        if (hasObjVar(weapon, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_COMPONENT_BONUS_MIN);
        }
        if (hasObjVar(weapon, OBJVAR_MODIFIER_APPEARANCE_BONUS_MIN))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_APPEARANCE_BONUS_MIN);
        }
        return craftBonuses;
    }
    public static float getWeaponComponentBonusesMaxDamage(obj_id weapon) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return 0.0f;
        }
        float craftBonuses = 0.0f;
        if (hasObjVar(weapon, OBJVAR_MODIFIER_GAS_QUALITY_MAX))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_GAS_QUALITY_MAX);
        }
        else 
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_MELEE_QUALITY_MAX);
        }
        if (hasObjVar(weapon, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_COMPONENT_BONUS_MAX);
        }
        if (hasObjVar(weapon, OBJVAR_MODIFIER_APPEARANCE_BONUS_MAX))
        {
            craftBonuses += getFloatObjVar(weapon, OBJVAR_MODIFIER_APPEARANCE_BONUS_MAX);
        }
        return craftBonuses;
    }
    public static boolean isRangedCore(obj_id core) throws InterruptedException
    {
        if (!isIdValid(core) || !exists(core))
        {
            return false;
        }
        switch (getTemplateName(core)) {
            case "object/tangible/component/weapon/core/weapon_core_ranged_base.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_ranged_advanced.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_ranged_basic.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_ranged_standard.iff":
                return true;
            default:
                return false;
        }
    }
    public static boolean isHeavyCore(obj_id core) throws InterruptedException
    {
        if (!isIdValid(core) || !exists(core))
        {
            return false;
        }
        switch (getTemplateName(core)) {
            case "object/tangible/component/weapon/core/weapon_core_heavy_base.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_heavy_advanced.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_heavy_standard.iff":
                return true;
            default:
                return false;
        }
    }
    public static boolean isMeleeCore(obj_id core) throws InterruptedException
    {
        if (!isIdValid(core) || !exists(core))
        {
            return false;
        }
        switch (getTemplateName(core)) {
            case "object/tangible/component/weapon/core/weapon_core_melee_base.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_melee_basic.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_melee_standard.iff":
                return true;
            case "object/tangible/component/weapon/core/weapon_core_melee_advanced.iff":
                return true;
            default:
                return false;
        }
    }
    public static float verifyDamageRangeMin(obj_id weapon, float currentDamage, dictionary coreData) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return 0.0f;
        }
        if (coreData == null || coreData.isEmpty())
        {
            return 0.0f;
        }
        int tableMin = getCoreMinDamage(weapon, coreData);
        float tableMinCap = tableMin * NEW_WP_DAMAGE_CAP;
        if (currentDamage > tableMinCap)
        {
            return tableMinCap;
        }
        return currentDamage;
    }
    public static float verifyDamageRangeMax(obj_id weapon, float currentDamage, dictionary coreData) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return 0.0f;
        }
        if (coreData == null || coreData.isEmpty())
        {
            return 0.0f;
        }
        int tableMax = getCoreMaxDamage(weapon, coreData);
        float tableMaxCap = tableMax * NEW_WP_DAMAGE_CAP;
        if (currentDamage > tableMaxCap)
        {
            return tableMaxCap;
        }
        return currentDamage;
    }
    public static boolean isProfWheelSchemWeapon(obj_id weapon) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            CustomerServiceLog("new_weapon_conversion", "Invalid ids passed in");
            return true;
        }
        String template = getTemplateName(weapon);
        if (template == null || template.equals(""))
        {
            CustomerServiceLog("new_weapon_conversion", "Couldnt find a valid object template for weapon(" + weapon + ")");
            return true;
        }
        String schemGroupTable = "datatables/crafting/schematic_group.iff";
        int row = dataTableSearchColumnForString(template, "crafted_object_template", WEAPON_APP_BONUS_TABLE);
        if (row < 0)
        {
            CustomerServiceLog("new_weapon_conversion", "Couldnt find a valid draft schematic in appearance table for template(" + template + ") on weapon(" + weapon + ")");
            return true;
        }
        String schemName = dataTableGetString(WEAPON_APP_BONUS_TABLE, row, "schematic_name");
        if (schemName == null || schemName.equals(""))
        {
            CustomerServiceLog("new_weapon_conversion", "bad schematic name when searcing in weapons appearance table for template(" + template + ") on weapon(" + weapon + ")");
            return true;
        }
        schemName = "object/draft_schematic/weapon/appearance/" + schemName + ".iff";
        row = dataTableSearchColumnForString(schemName, "SchematicName", schemGroupTable);
        if (row >= 0)
        {
            CustomerServiceLog("new_weapon_conversion", "Schematic found in profession wheel for schematic(" + schemName + ") on template(" + template + ") on weapon(" + weapon + ")");
            return true;
        }
        CustomerServiceLog("new_weapon_conversion", "Schematic NOT found in profession wheel for schematic(" + schemName + ") on template(" + template + ") on weapon(" + weapon + ")");
        return false;
    }
    public static boolean turnWeaponIntoSchem(obj_id player, obj_id weapon) throws InterruptedException
    {
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return false;
        }
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        String template = getTemplateName(weapon);
        int row = dataTableSearchColumnForString(template, "crafted_object_template", WEAPON_APP_BONUS_TABLE);
        if (row < 0)
        {
            return false;
        }
        String schemName = dataTableGetString(WEAPON_APP_BONUS_TABLE, row, "schematic_name");
        if (schemName == null || schemName.equals(""))
        {
            return false;
        }
        String limitedUseTemplate = "object/tangible/loot/loot_schematic/deconstructed_weapon_schematic.iff";
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id newSchem = createObjectOverloaded(limitedUseTemplate, pInv);
        CustomerServiceLog("new_weapon_conversion", "New schematic(" + schemName + ") converted from weapon(" + weapon + ") for player " + getFirstName(player) + "(" + player + ")");
        if (!isIdValid(newSchem) || !exists(newSchem))
        {
            return false;
        }
        setName(newSchem, utils.packStringId(getNameFromTemplate(template)));
        setObjVar(newSchem, "loot_schematic.schematic", "object/draft_schematic/weapon/appearance/" + schemName + ".iff");
        setObjVar(newSchem, "loot_schematic.uses", 1);
        setObjVar(newSchem, "loot_schematic.skill_req", "class_munitions_phase1_master");
        obj_id bioLink = getBioLink(weapon);
        if (isIdValid(bioLink))
        {
            setBioLink(newSchem, bioLink);
        }
        if (utils.isItemNoDrop(weapon))
        {
            setBioLink(newSchem, player);
        }
        attachScript(newSchem, "item.loot_schematic.loot_schematic");
        CustomerServiceLog("new_weapon_conversion", "Weapon(" + weapon + ") about to be destroyed on player " + getFirstName(player) + "(" + player + ") because it was converted into schematic " + schemName + "(" + newSchem + ")");
        return true;
    }
    public static boolean validateDamage(obj_id player, obj_id item) throws InterruptedException
    {
        if (!isCoredWeapon(item))
        {
            return false;
        }
        float coreBonus;
        float craftedBonus;
        int coreLevel = getCoreLevel(item);
        if (coreLevel <= 0)
        {
            return false;
        }
        dictionary weaponCoreDat = getWeaponCoreData(coreLevel);
        if (weaponCoreDat == null || weaponCoreDat.isEmpty())
        {
            return false;
        }
        coreBonus = getWeaponCoreQualityMin(item);
        craftedBonus = coreBonus + getWeaponComponentBonusesMinDamage(item);
        int tableMin = weaponCoreDat.getInt("pistol_min_damage");
        float minDamage = tableMin * craftedBonus;
        coreBonus = getWeaponCoreQualityMax(item);
        craftedBonus = coreBonus + getWeaponComponentBonusesMaxDamage(item);
        int tableMax = weaponCoreDat.getInt("pistol_max_damage");
        float maxDamage = tableMax * craftedBonus;
        if (minDamage > maxDamage)
        {
            CustomerServiceLog("new_weapon_crafting", "player " + getFirstName(player) + "(" + player + ") crafted an invalid weapon item, their min damage(" + minDamage + ") was higher than their max(" + maxDamage + ")");
            CustomerServiceLog("new_weapon_crafting", "player " + getFirstName(player) + "(" + player + ") Their coreQuality min has been reset to coreQuality max - 1%");
            sendSystemMessage(player, SID_MIN_DAMAGE_GREATER_THAN_MAX);
            if (hasObjVar(item, OBJVAR_WP_CORE_QUALITY_MIN) && hasObjVar(item, OBJVAR_WP_CORE_QUALITY_MAX))
            {
                float coreMax = getFloatObjVar(item, OBJVAR_WP_CORE_QUALITY_MAX);
                setObjVar(item, OBJVAR_WP_CORE_QUALITY_MIN, (coreMax - 0.01f));
            }
            if (hasObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow") && hasObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh"))
            {
                float coreMax = getFloatObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityHigh");
                setObjVar(item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "coreQualityLow", (coreMax - 0.01f));
            }
            coredWeaponConversion(item);
        }
        return true;
    }
    public static boolean setSchematicVariablesFromSchematic(obj_id schematic, obj_id prototype) throws InterruptedException
    {
        boolean objvarsCopied = false;
        if (hasObjVar(prototype, OBJVAR_NEW_WP))
        {
            copyObjVar(prototype, schematic, OBJVAR_NEW_WP);
            objvarsCopied = true;
        }
        return objvarsCopied;
    }
    public static boolean setSchematicVariablesFromProtoType(obj_id schematic, obj_id prototype) throws InterruptedException
    {
        boolean objvarsCopied = false;
        if (hasObjVar(schematic, OBJVAR_NEW_WP))
        {
            copyObjVar(schematic, prototype, OBJVAR_NEW_WP);
            objvarsCopied = true;
        }
        return objvarsCopied;
    }
    public static boolean checkForIllegalStorytellerWeapon(obj_id player, obj_id weapon) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(weapon))
        {
            if (isPlayer(player))
            {
                if (isWeapon(weapon) && getWeaponAttackSpeed(weapon) >= 2.0f)
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static void handleIllegalStorytellerWeapon(obj_id player, obj_id weapon, String source) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(weapon))
        {
            String weaponTemplate = getTemplateName(weapon);
            if (!weaponTemplate.contains("grenade") && !utils.hasScriptVar(weapon, "illegalStorytellerWeapon"))
            {
                String msg = "Weapon gained from a storyteller NPC via an exploit was detected in " + source + ". Player (" + player + ") and weapon | " + getName(weapon) + " | " + weaponTemplate + " | (" + weapon + ").";
                CustomerServiceLog("storyteller_weapon_exploit", msg);
                CustomerServiceLog("storyteller_weapon_exploit", logWeaponStats(weapon));
            }
        }
    }
    public static String logWeaponStats(obj_id weapon) throws InterruptedException
    {
        String logMsg = "Storyteller Exploited Weapon Stats: ";
        String weaponTemplate = getTemplateName(weapon);
        logMsg += localize(getNameFromTemplate(weaponTemplate)) + "(" + weaponTemplate + ") - ";
        combat_engine.weapon_data weaponData = getWeaponData(weapon);
        logMsg += "Spd (" + weaponData.attackSpeed + "), ";
        logMsg += "Dmg (" + weaponData.minDamage + "-" + weaponData.maxDamage + "), ";
        logMsg += "DmgType (";
        if ((weaponData.damageType & DAMAGE_KINETIC) != 0)
        {
            logMsg += "KINETIC, ";
        }
        if ((weaponData.damageType & DAMAGE_ENERGY) != 0)
        {
            logMsg += "ENERGY, ";
        }
        if ((weaponData.damageType & DAMAGE_BLAST) != 0)
        {
            logMsg += "BLAST, ";
        }
        if ((weaponData.damageType & DAMAGE_STUN) != 0)
        {
            logMsg += "STUN, ";
        }
        if ((weaponData.damageType & DAMAGE_RESTRAINT) != 0)
        {
            logMsg += "RESTRAINT, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_HEAT) != 0)
        {
            logMsg += "ELEMENTAL_HEAT, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_COLD) != 0)
        {
            logMsg += "ELEMENTAL_COLD, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_ACID) != 0)
        {
            logMsg += "ELEMENTAL_ACID, ";
        }
        if ((weaponData.damageType & DAMAGE_ELEMENTAL_ELECTRICAL) != 0)
        {
            logMsg += "ELEMENTAL_ELECTRICAL, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_HEAT) != 0)
        {
            logMsg += "ENVIRONMENTAL_HEAT, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_COLD) != 0)
        {
            logMsg += "ENVIRONMENTAL_COLD, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_ACID) != 0)
        {
            logMsg += "ENVIRONMENTAL_ACID, ";
        }
        if ((weaponData.damageType & DAMAGE_ENVIRONMENTAL_ELECTRICAL) != 0)
        {
            logMsg += "ENVIRONMENTAL_ELECTRICAL, ";
        }
        logMsg += "), ";
        logMsg += "Range (" + weaponData.minRange + ", " + weaponData.maxRange + "), ";
        logMsg += "Cost (" + weaponData.attackCost + ")";
        logMsg += "Wound (" + weaponData.woundChance + ")";
        logMsg += "Accuracy (" + weaponData.accuracy + ")";
        return logMsg;
    }
}
