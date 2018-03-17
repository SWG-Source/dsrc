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

public class crafting_base_space_weapon_component extends script.systems.crafting.crafting_base
{
    public crafting_base_space_weapon_component()
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
            if (((itemAttributes[i].name).getAsciiId()).equals("energy_maintenance") || ((itemAttributes[i].name).getAsciiId()).equals("energy_per_shot") || ((itemAttributes[i].name).getAsciiId()).equals("mass") || ((itemAttributes[i].name).getAsciiId()).equals("refire_rate"))
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
                if (((itemAttributes[i].name).getAsciiId()).equals("damage_max"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".damage_max", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("damage_min"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".damage_min", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("effective_shields"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".effective_shields", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("effective_armor"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".effective_armor", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("energy_per_shot"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".energy_per_shot", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("refire_rate"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".refire_rate", itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
