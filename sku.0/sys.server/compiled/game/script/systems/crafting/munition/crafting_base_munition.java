package script.systems.crafting.munition;

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

public class crafting_base_munition extends script.systems.crafting.crafting_base
{
    public crafting_base_munition()
    {
    }
    public static final String VERSION = "v1.00.00";
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
        int attackCost = 0;
        int accuracy = 0;
        base_class.range_info rangeData = new base_class.range_info();
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
                    setWeaponMinDamage(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("maxDamage"))
                {
                    setWeaponMaxDamage(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackSpeed"))
                {
                    float speed = itemAttributes[i].currentValue;
                    if (speed >= 1000)
                    {
                        speed /= 100;
                    }
                    else 
                    {
                        speed /= 10;
                    }
                    setWeaponAttackSpeed(prototype, speed);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("woundChance"))
                {
                    setWeaponWoundChance(prototype, itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("minRange"))
                {
                    rangeData.minRange = itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("maxRange"))
                {
                    rangeData.maxRange = itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("accuracy"))
                {
                    accuracy = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("reuseTimer"))
                {
                    setObjVar(prototype, "reuseTimer", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackCost"))
                {
                    attackCost = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("blindDuration"))
                {
                    setObjVar(prototype, "blindDuration", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("blindChance"))
                {
                    setObjVar(prototype, "blindChance", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("slowDuration"))
                {
                    setObjVar(prototype, "slowDuration", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("slowIntensity"))
                {
                    setObjVar(prototype, "slowIntensity", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("burnDuration"))
                {
                    setObjVar(prototype, "burnDuration", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("burnIntensity"))
                {
                    setObjVar(prototype, "burnIntensity", (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttributes[i].name + ".");
                }
            }
        }
        setWeaponRangeInfo(prototype, rangeData);
        setWeaponAttackCost(prototype, attackCost);
        setWeaponAccuracy(prototype, accuracy);
        weapons.setWeaponData(prototype);
    }
}
