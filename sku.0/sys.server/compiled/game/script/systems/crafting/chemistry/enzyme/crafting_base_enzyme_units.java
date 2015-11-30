package script.systems.crafting.chemistry.enzyme;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_enzyme_units extends script.systems.crafting.crafting_base
{
    public crafting_base_enzyme_units()
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
                if (((itemAttributes[i].name).getAsciiId()).equals("enzyme_purity"))
                {
                    setObjVar(prototype, "crafting.enzyme_purity", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("enzyme_mutagen"))
                {
                    setObjVar(prototype, "crafting.enzyme_mutagen", itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("charges"))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
            }
        }
    }
}
