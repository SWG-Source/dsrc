package script.systems.crafting.chemistry.enzyme;

import script.dictionary;
import script.draft_schematic;
import script.obj_id;

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
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("enzyme_purity")) {
                    setObjVar(prototype, "crafting.enzyme_purity", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("enzyme_mutagen")) {
                    setObjVar(prototype, "crafting.enzyme_mutagen", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("charges")) {
                    setCount(prototype, (int) itemAttribute.currentValue);
                }
            }
        }
    }
}
