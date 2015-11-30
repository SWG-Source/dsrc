package script.systems.crafting.space.weapon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.space_crafting;

public class crafting_base_space_weapon extends script.systems.crafting.crafting_base
{
    public crafting_base_space_weapon()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("mass") || ((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance") || ((itemAttributes[i].name).getAsciiId()).equals("energy_per_shot") || ((itemAttributes[i].name).getAsciiId()).equals("refire_rate"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("refire_rate") || ((itemAttributes[i].name).getAsciiId()).equals("effective_shields") || ((itemAttributes[i].name).getAsciiId()).equals("effective_armor"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].currentValue * 0.001f);
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
                if (itemAttributes[i].currentValue > 1.f)
                {
                    itemAttributes[i].currentValue = 1.f;
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
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("hitPointsMax"))
                {
                    setObjVar(prototype, "ship_comp.weapon.ammo_current", 0);
                    setObjVar(prototype, "ship_comp.weapon.ammo_maximum", 0);
                    space_crafting.setComponentMaximumHitpoints(prototype, (float)itemAttributes[i].currentValue);
                    space_crafting.setComponentCurrentHitpoints(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("efficiency"))
                {
                    space_crafting.setComponentGeneralEfficiency(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("mass"))
                {
                    space_crafting.setComponentMass(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("armorHpMax"))
                {
                    space_crafting.setComponentMaximumArmorHitpoints(prototype, (float)itemAttributes[i].currentValue);
                    space_crafting.setComponentCurrentArmorHitpoints(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_efficiency"))
                {
                    space_crafting.setComponentEnergyEfficiency(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance"))
                {
                    space_crafting.setComponentEnergyMaintenance(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("damage_max"))
                {
                    space_crafting.setWeaponMaximumDamage(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("damage_min"))
                {
                    space_crafting.setWeaponMinimumDamage(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("effective_shields"))
                {
                    space_crafting.setWeaponShieldEffectiveness(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("effective_armor"))
                {
                    space_crafting.setWeaponArmorEffectiveness(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_per_shot"))
                {
                    space_crafting.setWeaponEnergyPerShot(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("refire_rate"))
                {
                    space_crafting.setWeaponRefireRate(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("projectile_speed"))
                {
                    space_crafting.setWeaponProjectileSpeed(prototype, (float)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
