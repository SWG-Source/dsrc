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

public class crafting_base_decimator_chassis extends script.systems.crafting.crafting_base
{
    public crafting_base_decimator_chassis()
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
            setObjVar(prototype, "shiptype", "decimator");
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
            }
        }
    }
}
