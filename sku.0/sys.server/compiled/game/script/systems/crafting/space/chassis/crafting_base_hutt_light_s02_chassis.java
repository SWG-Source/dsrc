package script.systems.crafting.space.chassis;

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
import script.library.create;

public class crafting_base_hutt_light_s02_chassis extends script.systems.crafting.crafting_base
{
    public crafting_base_hutt_light_s02_chassis()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            setObjVar(prototype, "isShipDeed", true);
            setObjVar(prototype, "shiptype", "hutt_light_s02");
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("massMax"))
                {
                    String OBJVAR_NAME = "ship_chassis.mass";
                    setObjVar(prototype, OBJVAR_NAME, (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("hp"))
                {
                    String OBJVAR_NAME = "ship_chassis.hp";
                    setObjVar(prototype, OBJVAR_NAME, (float)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
