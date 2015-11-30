package script.systems.crafting.munition;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.weapons;

public class crafting_base_steroid extends script.systems.crafting.crafting_base
{
    public crafting_base_steroid()
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
            if (((itemAttributes[i].name).getAsciiId()).equals("beastSteroidBonus") || ((itemAttributes[i].name).getAsciiId()).equals("charges"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) + itemAttributes[i].currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int attackCost = 0;
        int accuracy = 0;
        base_class.range_info rangeData = new base_class.range_info();
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("beastSteroidBonus"))
                {
                    setObjVar(prototype, "beastSteroidBonus", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("charges"))
                {
                    setObjVar(prototype, "charges", (int)itemAttributes[i].currentValue);
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttributes[i].name + ".");
                }
            }
        }
    }
}
