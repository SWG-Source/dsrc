package script.systems.crafting.munition;

import script.base_class;
import script.dictionary;
import script.draft_schematic;
import script.library.weapons;
import script.obj_id;

public class crafting_base_munition extends script.systems.crafting.crafting_base
{
    public crafting_base_munition()
    {
    }
    public static final String VERSION = "v1.00.00";
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
        int attackCost = 0;
        int accuracy = 0;
        base_class.range_info rangeData = new base_class.range_info();
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                switch (((itemAttribute.name).getAsciiId())) {
                    case "minDamage":
                        setWeaponMinDamage(prototype, (int) itemAttribute.currentValue);
                        break;
                    case "maxDamage":
                        setWeaponMaxDamage(prototype, (int) itemAttribute.currentValue);
                        break;
                    case "attackSpeed":
                        float speed = itemAttribute.currentValue;
                        if (speed >= 1000) {
                            speed /= 100;
                        } else {
                            speed /= 10;
                        }
                        setWeaponAttackSpeed(prototype, speed);
                        break;
                    case "woundChance":
                        setWeaponWoundChance(prototype, itemAttribute.currentValue);
                        break;
                    case "minRange":
                        rangeData.minRange = itemAttribute.currentValue;
                        break;
                    case "maxRange":
                        rangeData.maxRange = itemAttribute.currentValue;
                        break;
                    case "accuracy":
                        accuracy = (int) itemAttribute.currentValue;
                        break;
                    case "reuseTimer":
                        setObjVar(prototype, "reuseTimer", (int) itemAttribute.currentValue);
                        break;
                    case "attackCost":
                        attackCost = (int) itemAttribute.currentValue;
                        break;
                    case "blindDuration":
                        setObjVar(prototype, "blindDuration", (int) itemAttribute.currentValue);
                        break;
                    case "blindChance":
                        setObjVar(prototype, "blindChance", (int) itemAttribute.currentValue);
                        break;
                    case "slowDuration":
                        setObjVar(prototype, "slowDuration", (int) itemAttribute.currentValue);
                        break;
                    case "slowIntensity":
                        setObjVar(prototype, "slowIntensity", (int) itemAttribute.currentValue);
                        break;
                    case "burnDuration":
                        setObjVar(prototype, "burnDuration", (int) itemAttribute.currentValue);
                        break;
                    case "burnIntensity":
                        setObjVar(prototype, "burnIntensity", (int) itemAttribute.currentValue);
                        break;
                    default:
                        debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                        break;
                }
            }
        }
        setWeaponRangeInfo(prototype, rangeData);
        setWeaponAttackCost(prototype, attackCost);
        setWeaponAccuracy(prototype, accuracy);
        weapons.setWeaponData(prototype);
    }
}
