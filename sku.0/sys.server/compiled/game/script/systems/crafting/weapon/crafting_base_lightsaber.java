package script.systems.crafting.weapon;

import script.base_class;
import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.jedi;
import script.library.weapons;
import script.obj_id;

public class crafting_base_lightsaber extends script.systems.crafting.crafting_base
{
    public crafting_base_lightsaber()
    {
    }
    public static final String VERSION = "v1.00.00";
    public static final String SABER_BLADE_INDEX = "private/index_color_blade";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("attackSpeed")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float weaponMinDamage = 0.0f;
        float weaponMaxDamage = 0.0f;
        float weaponAttackSpeed = 0.0f;
        float weaponWoundChance = 0.0f;
        float weaponForceCost = 0.0f;
        int attackCost = 0;
        int accuracy = 0;
        base_class.range_info rangeData = new base_class.range_info();
        rangeData.minRange = 0.0f;
        rangeData.maxRange = 5.0f;
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting for CRAFTING_BASE_LIGHTSABER");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                switch (((itemAttribute.name).getAsciiId())) {
                    case "minDamage":
                        weaponMinDamage = (int) (itemAttribute.currentValue);
                        if (weaponMinDamage < 1) {
                            weaponMinDamage = 1;
                        }
                        break;
                    case "maxDamage":
                        weaponMaxDamage = (int) (itemAttribute.currentValue);
                        if (weaponMaxDamage < 2) {
                            weaponMaxDamage = 2;
                        }
                        break;
                    case "attackSpeed":
                        weaponAttackSpeed = (itemAttribute.currentValue / 100.0f);
                        if (weaponAttackSpeed < 0.25f) {
                            weaponAttackSpeed = 0.25f;
                        }
                        setWeaponAttackSpeed(prototype, weaponAttackSpeed);
                        break;
                    default:
                        LOG("saberscrafting", "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                        debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                        break;
                }
            }
        }
        if (weaponMinDamage >= weaponMaxDamage)
        {
            weaponMinDamage -= 1;
        }
        setWeaponMinDamage(prototype, (int)(weaponMinDamage));
        setWeaponMaxDamage(prototype, (int)(weaponMaxDamage));
        setWeaponRangeInfo(prototype, rangeData);
        setWeaponAccuracy(prototype, accuracy);
        setWeaponAttackCost(prototype, attackCost);
        setWeaponDamageType(prototype, DAMAGE_ENERGY);
        setConversionId(prototype, weapons.CONVERSION_VERSION);
        weapons.setWeaponData(prototype);
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
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_ELEMENTAL_DAM_TYPE, DAMAGE_NONE);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_ELEMENTAL_DAM_AMNT, 0);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MIN_DMG, (int)weaponMinDamage);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_MAX_DMG, (int)weaponMaxDamage);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_SPEED, weaponAttackSpeed);
        range_info ri = getWeaponRangeInfo(prototype);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_WOUND, weaponWoundChance);
        float radius = getWeaponDamageRadius(prototype);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_RADIUS, radius);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_ATTACK_COST, attackCost);
        setObjVar(prototype, jedi.VAR_SABER_DEFAULT_STATS + "." + jedi.VAR_ACCURACY, accuracy);
    }
}
