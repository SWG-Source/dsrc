package script.systems.crafting.space.booster;

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

public class crafting_base_booster extends script.systems.crafting.crafting_base
{
    public crafting_base_booster()
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
            if (((itemAttributes[i].name).getAsciiId()).equals("mass") || ((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance") || ((itemAttributes[i].name).getAsciiId()).equals("booster_consumption"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
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
                if (((itemAttributes[i].name).getAsciiId()).equals("booster_energy"))
                {
                    space_crafting.setBoosterMaximumEnergy(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("booster_recharge"))
                {
                    space_crafting.setBoosterEnergyRechargeRate(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("booster_consumption"))
                {
                    space_crafting.setBoosterEnergyConsumptionRate(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("booster_speed"))
                {
                    space_crafting.setBoosterMaximumSpeed(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("booster_acceleration"))
                {
                    space_crafting.setBoosterAcceleration(prototype, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance"))
                {
                    space_crafting.setComponentEnergyMaintenance(prototype, (float)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
