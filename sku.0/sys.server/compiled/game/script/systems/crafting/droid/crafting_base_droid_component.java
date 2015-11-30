package script.systems.crafting.droid;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_droid_component extends script.systems.crafting.crafting_base
{
    public crafting_base_droid_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
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
                if (((itemAttributes[i].name).getAsciiId()).equals("mechanism_quality"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mechanism_quality", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("armor_toughness"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_toughness", (int)(itemAttributes[i].currentValue));
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("armor_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_module", itemAttributes[i].currentValue);
                }
                else if ((((itemAttributes[i].name).getAsciiId()).equals("charges")) || (((itemAttributes[i].name).getAsciiId()).equals("droid_count")))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("useModifier"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".useModifier", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("cmbt_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cmbt_module", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("struct_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".struct_module", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("fire_potency") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".fire_potency", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("arc_projector") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".arc_projector", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("shield_heatsink") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".shield_heatsink", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("pain_inducer") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".pain_inducer", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("quickset_metal") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".quickset_metal", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("dump_capacitors") && itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".dump_capacitors", itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("flame_jet_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("regenerative_plating_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("battery_dump_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("electrical_shock_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("droideka_shield_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("torturous_needle_level") && itemAttributes[i].currentValue > 0)
                {
                }
                else if (itemAttributes[i].currentValue > 0)
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
