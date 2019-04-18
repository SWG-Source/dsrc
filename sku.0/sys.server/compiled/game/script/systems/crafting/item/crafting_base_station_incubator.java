package script.systems.crafting.item;

import script.dictionary;
import script.draft_schematic;
import script.obj_id;

public class crafting_base_station_incubator extends script.systems.crafting.crafting_base
{
    public crafting_base_station_incubator()
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
                if (((itemAttribute.name).getAsciiId()).equals("mechanism_quality")) {
                    setObjVar(prototype, "crafting.stationMod", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("useModifier")) {
                    setObjVar(prototype, "crafting.stationMod_1", itemAttribute.currentValue);
                }
            }
        }
    }
}
