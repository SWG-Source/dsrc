package script.systems.crafting.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_harvester extends script.systems.crafting.crafting_base
{
    public crafting_base_harvester()
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
                if (((itemAttributes[i].name).getAsciiId()).equals("extractRate"))
                {
                    setObjVar(prototype, "player_structure.deed.maxExtractionRate", (int)itemAttributes[i].currentValue);
                    setObjVar(prototype, "player_structure.deed.currentExtractionRate", (int)itemAttributes[i].currentValue);
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("hopperSize"))
                {
                    setObjVar(prototype, "player_structure.deed.maxHopperSize", (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttributes[i].name + ".");
                }
            }
        }
    }
}
