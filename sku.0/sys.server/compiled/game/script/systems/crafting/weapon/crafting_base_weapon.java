package script.systems.crafting.weapon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.weapons;
import script.library.prose;
import java.lang.Math;

public class crafting_base_weapon extends script.systems.crafting.crafting_base
{
    public crafting_base_weapon()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final float MAX_STAT_INTENSITY = 1.00f;
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("attackSpeed") || ((itemAttributes[i].name).getAsciiId()).equals("attackCost"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
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
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("minDamage"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getMinDamageHighCap(weaponDat);
                    curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                    if (curCraftVal > curMaxVal)
                    {
                        notifyStatCapped(curCraftVal, curMaxVal, "Minimum Damage", prototype);
                        curCraftVal = curMaxVal;
                    }
                    setWeaponMinDamage(prototype, (int)curCraftVal);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("maxDamage"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getMaxDamageHighCap(weaponDat);
                    curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                    if (curCraftVal > curMaxVal)
                    {
                        notifyStatCapped(curCraftVal, curMaxVal, "Maximum Damage", prototype);
                        curCraftVal = curMaxVal;
                    }
                    setWeaponMaxDamage(prototype, (int)curCraftVal);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackSpeed"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getSpeedLow(weaponDat);
                    curMaxVal = (float)Math.floor(curMasterVal / MAX_STAT_INTENSITY);
                    if (curCraftVal < curMaxVal)
                    {
                        notifyStatCapped(curCraftVal / 100.0f, curMaxVal / 100.0f, "Attack Speed", prototype);
                        curCraftVal = curMaxVal;
                    }
                    setWeaponAttackSpeed(prototype, curCraftVal / 100.0f);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("woundChance"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getWoundChanceHigh(weaponDat);
                    curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                    if (curCraftVal > curMaxVal)
                    {
                        notifyStatCapped(curCraftVal, curMaxVal, "Wound Chance", prototype);
                        curCraftVal = curMaxVal;
                    }
                    setWeaponWoundChance(prototype, curCraftVal);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("accuracy"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    accuracy = Math.round(curCraftVal);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("charges"))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackCost"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getAttackCostLow(weaponDat);
                    curMaxVal = (float)Math.floor(curMasterVal / MAX_STAT_INTENSITY);
                    if (curCraftVal < curMaxVal)
                    {
                        notifyStatCapped(curCraftVal, curMaxVal, "Attack Cost", prototype);
                        curCraftVal = curMaxVal;
                    }
                    setWeaponAttackCost(prototype, (int)curCraftVal);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("damageType"))
                {
                    damageType = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("elementalType"))
                {
                    elementalType = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("elementalValue"))
                {
                    curCraftVal = itemAttributes[i].currentValue;
                    curMasterVal = weapons.getElementalValueHigh(weaponDat);
                    curMaxVal = (curMasterVal * MAX_STAT_INTENSITY);
                    if (curCraftVal > curMaxVal)
                    {
                        notifyStatCapped(curCraftVal, curMaxVal, "Elemental Value", prototype);
                        curCraftVal = curMaxVal;
                    }
                    elementalValue = (int)curCraftVal;
                }
                else 
                {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttributes[i].name + ".");
                }
            }
        }
        obj_id player = getOwner(prototype);
        int[] mods = getEnhancedSkillStatisticModifiers(player, getExperimentSkillMods());
        if (mods != null)
        {
            int sockets = 0;
            int experimentModTotal = 0;
            for (int j = 0; j < mods.length; ++j)
            {
                experimentModTotal += mods[j];
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
