package script.systems.crafting.weapon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;
import script.library.weapons;

public class crafting_new_weapon_final extends script.systems.crafting.crafting_base
{
    public crafting_new_weapon_final()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        obj_var_list componentData = getObjVarList(getSelf(), craftinglib.COMPONENT_ATTRIBUTE_INTERNAL_OBJVAR_NAME);
        String[] componentDataNames = componentData.getAllObjVarNames();
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (((itemAttributes[i].name).getAsciiId()).equals("appearanceBonusLow"))
            {
                float appBonus = itemAttributes[i].currentValue;
                CustomerServiceLog("new_weapon_crafting", "appearanceBonusLow pre-division on prototype " + prototype + "(" + getTemplateName(prototype) + ") is " + appBonus);
                appBonus /= weapons.NEW_COMPONENT_MODIFIER;
                CustomerServiceLog("new_weapon_crafting", "setting appearanceBonusLow on prototype " + prototype + "(" + getTemplateName(prototype) + ") to " + appBonus);
                setObjVar(prototype, weapons.OBJVAR_MODIFIER_APPEARANCE_BONUS_MIN, appBonus);
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("appearanceBonusHigh"))
            {
                float appBonus = itemAttributes[i].currentValue;
                CustomerServiceLog("new_weapon_crafting", "appearanceBonusHigh pre-division on prototype " + prototype + "(" + getTemplateName(prototype) + ") is " + appBonus);
                appBonus /= weapons.NEW_COMPONENT_MODIFIER;
                CustomerServiceLog("new_weapon_crafting", "setting appearanceBonusHigh on prototype " + prototype + "(" + getTemplateName(prototype) + ") to " + appBonus);
                setObjVar(prototype, weapons.OBJVAR_MODIFIER_APPEARANCE_BONUS_MAX, appBonus);
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        obj_id self = getSelf();
        weapons.setSchematicVariablesFromProtoType(self, prototype);
        base_class.range_info rangeData = new base_class.range_info();
        int coreLevel = weapons.getCoreLevel(prototype);
        CustomerServiceLog("new_weapon_crafting", "core level " + coreLevel);
        dictionary weaponCoreDat = weapons.getWeaponCoreData(coreLevel);
        if (weaponCoreDat == null)
        {
            CustomerServiceLog("weaponsCraftingError", "Unable to load master weapon data for object " + prototype + "(" + getTemplateName(prototype) + "). Aborting crafting process.");
            return;
        }
        int damageType = 0;
        int elementalType = 0;
        int elementalValue = 0;
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
                    float craftedBonus = weapons.getWeaponCoreQualityMin(prototype);
                    CustomerServiceLog("new_weapon_crafting", "craftedBonus preComponentBonusMin on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + craftedBonus);
                    craftedBonus += weapons.getWeaponComponentBonusesMinDamage(prototype);
                    CustomerServiceLog("new_weapon_crafting", "craftedBonus postComponentBonusMin on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + craftedBonus);
                    int tableMin = weapons.getCoreMinDamage(prototype, weaponCoreDat);
                    CustomerServiceLog("new_weapon_crafting", "tableMinDamage on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + tableMin);
                    float minDamage = tableMin * craftedBonus;
                    CustomerServiceLog("new_weapon_crafting", "minDamage pre-VerifyDamageRangeMin on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + minDamage);
                    minDamage = weapons.verifyDamageRangeMin(prototype, minDamage, weaponCoreDat);
                    CustomerServiceLog("new_weapon_crafting", "minDamage post-VerifyDamageRangeMin on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + minDamage);
                    setWeaponMinDamage(prototype, (int)minDamage);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("maxDamage"))
                {
                    float craftedBonus = weapons.getWeaponCoreQualityMax(prototype);
                    CustomerServiceLog("new_weapon_crafting", "craftedBonus preComponentBonusMax on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + craftedBonus);
                    craftedBonus += weapons.getWeaponComponentBonusesMaxDamage(prototype);
                    CustomerServiceLog("new_weapon_crafting", "craftedBonus postComponentBonusMax on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + craftedBonus);
                    int tableMax = weapons.getCoreMaxDamage(prototype, weaponCoreDat);
                    CustomerServiceLog("new_weapon_crafting", "tableMaxDamage on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + tableMax);
                    float maxDamage = tableMax * craftedBonus;
                    CustomerServiceLog("new_weapon_crafting", "maxDamage  pre-VerifyDamageRangeMax on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + maxDamage);
                    maxDamage = weapons.verifyDamageRangeMax(prototype, maxDamage, weaponCoreDat);
                    CustomerServiceLog("new_weapon_crafting", "minDamage post-VerifyDamageRangeMax on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + maxDamage);
                    setWeaponMaxDamage(prototype, (int)maxDamage);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackSpeed"))
                {
                    setWeaponAttackSpeed(prototype, weapons.getWeaponSpeed(prototype) / 100.0f);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("woundChance"))
                {
                    setWeaponWoundChance(prototype, weapons.getNewWeaponWoundChance(prototype));
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("accuracy"))
                {
                    accuracy = weapons.getNewWeaponAccuracy(prototype);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("charges"))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("attackCost"))
                {
                    setWeaponAttackCost(prototype, weapons.getNewWeaponAttackCost(prototype));
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("damageType"))
                {
                    damageType = weapons.getNewWeaponDamageType(prototype);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("elementalType"))
                {
                    int tableElementalType = weapons.getNewWeaponTableElementType(prototype);
                    CustomerServiceLog("new_weapon_crafting", "tableElementalType for prototype " + prototype + "(" + getTemplateName(prototype) + ") " + tableElementalType);
                    elementalType = (int)weapons.getNewWeaponElementalType(prototype);
                    CustomerServiceLog("new_weapon_crafting", "elementalType on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + elementalType);
                    if (tableElementalType != -1)
                    {
                        CustomerServiceLog("new_weapon_crafting", "tableElementalType is defiened, so we take it over what is passed in from components on prototype " + prototype + "(" + getTemplateName(prototype) + ")");
                        elementalType = tableElementalType;
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("elementalValue"))
                {
                    float craftedBonus = weapons.getNewWeaponElementalValue(prototype);
                    CustomerServiceLog("new_weapon_crafting", "craftedBonus for elementalValue on prototype " + prototype + "(" + getTemplateName(prototype) + ") " + craftedBonus);
                    int tableValue = weapons.getNewWeaponTableElementalValue(prototype, weaponCoreDat);
                    CustomerServiceLog("new_weapon_crafting", "tableElementalValue for prototype " + prototype + "(" + getTemplateName(prototype) + ") " + tableValue);
                    elementalValue = (int)(tableValue * craftedBonus);
                    if (elementalValue > 0)
                    {
                        CustomerServiceLog("new_weapon_crafting", "elementalValue on prototype " + prototype + "(" + getTemplateName(prototype) + ") before Appearance bonus " + elementalValue);
                        int elementalAppearanceBonusValue = weapons.getElementalAppearanceBonus(prototype);
                        CustomerServiceLog("new_weapon_crafting", "elementalAppearanceBonusValue on prototype " + prototype + "(" + getTemplateName(prototype) + ") is " + elementalAppearanceBonusValue);
                        if (elementalAppearanceBonusValue < 0)
                        {
                            elementalValue -= elementalValue * elementalAppearanceBonusValue * -1 / 100;
                            CustomerServiceLog("new_weapon_crafting", "elementalAppearanceBonusValue on prototype " + prototype + "(" + getTemplateName(prototype) + ") was a negative number. So we now reduce the elementalValue (" + elementalValue + ") by that number(" + elementalAppearanceBonusValue + ") in percentage form. This results in elementalValue being " + elementalValue);
                        }
                        else 
                        {
                            elementalValue += elementalAppearanceBonusValue;
                            CustomerServiceLog("new_weapon_crafting", "elementalAppearanceBonusValue was a postive number so elementalValue on prototype " + prototype + "(" + getTemplateName(prototype) + ") after Appearance bonus is " + elementalValue);
                        }
                    }
                }
                else 
                {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttributes[i].name + ".");
                }
            }
        }
        weapons.performSocketing(prototype, getExperimentSkillMods());
        rangeData.minRange = weapons.getNewWeaponMinRange(prototype);
        rangeData.maxRange = weapons.getNewWeaponMaxRange(prototype);
        setObjVar(prototype, "weapon.original_max_range", rangeData.maxRange);
        setWeaponAccuracy(prototype, accuracy);
        setWeaponRangeInfo(prototype, rangeData);
        setWeaponDamageType(prototype, damageType);
        if (elementalType >= 0 && elementalValue > 0)
        {
            setWeaponElementalType(prototype, elementalType);
            setWeaponElementalValue(prototype, elementalValue);
        }
        else 
        {
            setWeaponElementalType(prototype, -1);
            setWeaponElementalValue(prototype, 0);
        }
        weapons.setHeavyWeaponAoeSplashPercent(prototype);
        setConversionId(prototype, weapons.CORED_WEAPON_CONVERSION_VERSION);
        weapons.setWeaponData(prototype);
        if (!hasObjVar(prototype, weapons.OBJVAR_NEW_WP_WEAPON))
        {
            setObjVar(prototype, weapons.OBJVAR_NEW_WP_WEAPON, true);
        }
        if (!hasScript(prototype, "item.armor.biolink_item_non_faction"))
        {
            attachScript(prototype, "item.armor.biolink_item_non_faction");
        }
    }
}
