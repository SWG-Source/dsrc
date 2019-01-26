package script.systems.crafting.structure;

import script.dictionary;
import script.draft_schematic;
import script.obj_id;

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
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("extractRate")) {
                    setObjVar(prototype, "player_structure.deed.maxExtractionRate", (int) itemAttribute.currentValue);
                    setObjVar(prototype, "player_structure.deed.currentExtractionRate", (int) itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("hopperSize")) {
                    setObjVar(prototype, "player_structure.deed.maxHopperSize", (int) itemAttribute.currentValue);
                } else {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                }
            }
        }
    }
}
