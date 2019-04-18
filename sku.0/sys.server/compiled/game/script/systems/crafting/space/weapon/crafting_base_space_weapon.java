package script.systems.crafting.space.weapon;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.space_crafting;
import script.obj_id;

public class crafting_base_space_weapon extends script.systems.crafting.crafting_base
{
    public crafting_base_space_weapon()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("mass") || ((itemAttribute.name).getAsciiId()).equals("energy_maintenance") || ((itemAttribute.name).getAsciiId()).equals("energy_per_shot") || ((itemAttribute.name).getAsciiId()).equals("refire_rate")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("refire_rate") || ((itemAttribute.name).getAsciiId()).equals("effective_shields") || ((itemAttribute.name).getAsciiId()).equals("effective_armor")) {
                itemAttribute.currentValue = (itemAttribute.currentValue * 0.001f);
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float damage_min = 0;
        int damage_min_i = 0;
        float damage_max = 0;
        int damage_max_i = 0;
        space_crafting.setWeaponEnergyPerShot(prototype, 0);
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("effective_shields") || ((itemAttributes[i].name).getAsciiId()).equals("effective_armor"))
            {
                LOG("VALUE", "current value " + itemAttributes[i].currentValue);
                if (itemAttributes[i].currentValue > 1.0f)
                {
                    itemAttributes[i].currentValue = 1.0f;
                }
                else if (itemAttributes[i].currentValue < 0.01f)
                {
                    itemAttributes[i].currentValue = 0.01f;
                }
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("damage_min"))
            {
                damage_min = itemAttributes[i].currentValue;
                damage_min_i = i;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("damage_max"))
            {
                damage_max = itemAttributes[i].currentValue;
                damage_max_i = i;
            }
        }
        if ((damage_min > damage_max) || (damage_max < damage_min))
        {
            itemAttributes[damage_min_i].currentValue = damage_max;
            itemAttributes[damage_max_i].currentValue = damage_min;
        }
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("hitPointsMax")) {
                    setObjVar(prototype, "ship_comp.weapon.ammo_current", 0);
                    setObjVar(prototype, "ship_comp.weapon.ammo_maximum", 0);
                    space_crafting.setComponentMaximumHitpoints(prototype, (float) itemAttribute.currentValue);
                    space_crafting.setComponentCurrentHitpoints(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("efficiency")) {
                    space_crafting.setComponentGeneralEfficiency(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("mass")) {
                    space_crafting.setComponentMass(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("armorHpMax")) {
                    space_crafting.setComponentMaximumArmorHitpoints(prototype, (float) itemAttribute.currentValue);
                    space_crafting.setComponentCurrentArmorHitpoints(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_efficiency")) {
                    space_crafting.setComponentEnergyEfficiency(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_maintenance")) {
                    space_crafting.setComponentEnergyMaintenance(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("damage_max")) {
                    space_crafting.setWeaponMaximumDamage(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("damage_min")) {
                    space_crafting.setWeaponMinimumDamage(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("effective_shields")) {
                    space_crafting.setWeaponShieldEffectiveness(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("effective_armor")) {
                    space_crafting.setWeaponArmorEffectiveness(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_per_shot")) {
                    space_crafting.setWeaponEnergyPerShot(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("refire_rate")) {
                    space_crafting.setWeaponRefireRate(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("projectile_speed")) {
                    space_crafting.setWeaponProjectileSpeed(prototype, (float) itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
