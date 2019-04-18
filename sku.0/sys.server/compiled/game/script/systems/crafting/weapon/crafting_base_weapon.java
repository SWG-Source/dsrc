package script.systems.crafting.weapon;

import script.*;
import script.library.craftinglib;
import script.library.prose;
import script.library.utils;
import script.library.weapons;

public class crafting_base_weapon extends script.systems.crafting.crafting_base
{
    public crafting_base_weapon()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final float MAX_STAT_INTENSITY = 1.00f;
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("attackSpeed") || ((itemAttribute.name).getAsciiId()).equals("attackCost")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        base_class.range_info rangeData = new base_class.range_info();
        float curCraftVal = 0;
        float curMasterVal = 0;
        float curMaxVal = 0;
        dictionary weaponDat = weapons.getWeaponDat(prototype);
        if (weaponDat == null)
        {
            CustomerServiceLog("weaponsCraftingError", "Unable to load master weapon data for object " + prototype + "(" + getTemplateName(prototype) + "). Aborting crafting process.");
            return;
        }
        int damageType = weapons.getDamageType(weaponDat);
        int elementalType = weapons.getElementalType(weaponDat);
        int elementalValue = weapons.getElementalValueLow(weaponDat);
        int accuracy = 0;
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                switch (((itemAttribute.name).getAsciiId())) {
                    case "minDamage":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getMinDamageHighCap(weaponDat);
                        curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                        if (curCraftVal > curMaxVal) {
                            notifyStatCapped(curCraftVal, curMaxVal, "Minimum Damage", prototype);
                            curCraftVal = curMaxVal;
                        }
                        setWeaponMinDamage(prototype, (int) curCraftVal);
                        break;
                    case "maxDamage":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getMaxDamageHighCap(weaponDat);
                        curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                        if (curCraftVal > curMaxVal) {
                            notifyStatCapped(curCraftVal, curMaxVal, "Maximum Damage", prototype);
                            curCraftVal = curMaxVal;
                        }
                        setWeaponMaxDamage(prototype, (int) curCraftVal);
                        break;
                    case "attackSpeed":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getSpeedLow(weaponDat);
                        curMaxVal = (float) Math.floor(curMasterVal / MAX_STAT_INTENSITY);
                        if (curCraftVal < curMaxVal) {
                            notifyStatCapped(curCraftVal / 100.0f, curMaxVal / 100.0f, "Attack Speed", prototype);
                            curCraftVal = curMaxVal;
                        }
                        setWeaponAttackSpeed(prototype, curCraftVal / 100.0f);
                        break;
                    case "woundChance":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getWoundChanceHigh(weaponDat);
                        curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                        if (curCraftVal > curMaxVal) {
                            notifyStatCapped(curCraftVal, curMaxVal, "Wound Chance", prototype);
                            curCraftVal = curMaxVal;
                        }
                        setWeaponWoundChance(prototype, curCraftVal);
                        break;
                    case "accuracy":
                        curCraftVal = itemAttribute.currentValue;
                        accuracy = Math.round(curCraftVal);
                        break;
                    case "charges":
                        setCount(prototype, (int) itemAttribute.currentValue);
                        break;
                    case "attackCost":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getAttackCostLow(weaponDat);
                        curMaxVal = (float) Math.floor(curMasterVal / MAX_STAT_INTENSITY);
                        if (curCraftVal < curMaxVal) {
                            notifyStatCapped(curCraftVal, curMaxVal, "Attack Cost", prototype);
                            curCraftVal = curMaxVal;
                        }
                        setWeaponAttackCost(prototype, (int) curCraftVal);
                        break;
                    case "damageType":
                        damageType = (int) itemAttribute.currentValue;
                        break;
                    case "elementalType":
                        elementalType = (int) itemAttribute.currentValue;
                        break;
                    case "elementalValue":
                        curCraftVal = itemAttribute.currentValue;
                        curMasterVal = weapons.getElementalValueHigh(weaponDat);
                        curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                        if (curCraftVal > curMaxVal) {
                            notifyStatCapped(curCraftVal, curMaxVal, "Elemental Value", prototype);
                            curCraftVal = curMaxVal;
                        }
                        elementalValue = (int) curCraftVal;
                        break;
                    default:
                        debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                        break;
                }
            }
        }
        obj_id player = getOwner(prototype);
        int[] mods = getEnhancedSkillStatisticModifiers(player, getExperimentSkillMods());
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
        rangeData.minRange = weapons.getMinRangeDistance(weaponDat);
        rangeData.maxRange = weapons.getMaxRangeDistance(weaponDat);
        setObjVar(prototype, "weapon.original_max_range", rangeData.maxRange);
        setWeaponAccuracy(prototype, accuracy);
        setWeaponRangeInfo(prototype, rangeData);
        setWeaponDamageType(prototype, damageType);
        setWeaponElementalType(prototype, elementalType);
        setWeaponElementalValue(prototype, elementalValue);
        weapons.setHeavyWeaponAoeSplashPercent(prototype);
        setConversionId(prototype, weapons.CONVERSION_VERSION);
        weapons.setWeaponData(prototype);
    }
    public void notifyStatCapped(float attemptedValue, float maxValue, String stat, obj_id prototype) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(getSelf());
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, new string_id("craft_results", "crafting_stat_capped"));
        pp = prose.setTT(pp, prototype);
        pp = prose.setTU(pp, stat);
        pp = prose.setTO(pp, Float.toString(attemptedValue));
        pp = prose.setDF(pp, maxValue);
        sendSystemMessageProse(player, pp);
    }
    public int OnFinalizeSchematic(obj_id self, obj_id player, obj_id prototype, draft_schematic schematic) throws InterruptedException
    {
        setConversionId(self, weapons.CONVERSION_VERSION);
        setConversionId(prototype, weapons.CONVERSION_VERSION);
        return SCRIPT_CONTINUE;
    }
}
