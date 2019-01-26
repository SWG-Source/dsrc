package script.systems.crafting.munition;

import script.base_class;
import script.dictionary;
import script.draft_schematic;
import script.obj_id;

public class crafting_base_steroid extends script.systems.crafting.crafting_base
{
    public crafting_base_steroid()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("beastSteroidBonus") || ((itemAttribute.name).getAsciiId()).equals("charges")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) + itemAttribute.currentValue;
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
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("beastSteroidBonus")) {
                    setObjVar(prototype, "beastSteroidBonus", (int) itemAttribute.currentValue);
                } else if (((itemAttribute.name).getAsciiId()).equals("charges")) {
                    setObjVar(prototype, "charges", (int) itemAttribute.currentValue);
                    setCount(prototype, (int) itemAttribute.currentValue);
                } else {
                    debugServerConsoleMsg(null, "Error. Unknown Attribute Read in. Attribute was " + itemAttribute.name + ".");
                }
            }
        }
    }
}
