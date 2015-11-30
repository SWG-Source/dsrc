package script.systems.crafting.space.capacitor;

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

public class crafting_base_capacitor_component extends script.systems.crafting.crafting_base
{
    public crafting_base_capacitor_component()
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
            if (((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        attachScript(prototype, "space.crafting.subcomponent");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("cap_max_energy"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cap_max_energy", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cap_recharge_rate"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cap_recharge_rate", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".energy_maintenance", itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
