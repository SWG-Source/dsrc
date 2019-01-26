package script.systems.crafting.space.cargo_hold;

import script.dictionary;
import script.draft_schematic;
import script.library.space_crafting;
import script.obj_id;

public class crafting_base_cargo_hold extends script.systems.crafting.crafting_base
{
    public crafting_base_cargo_hold()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("mass")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
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
                if (((itemAttribute.name).getAsciiId()).equals("mass")) {
                    space_crafting.setComponentMass(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("hitPointsMax")) {
                    space_crafting.setComponentMaximumHitpoints(prototype, (float) itemAttribute.currentValue);
                    space_crafting.setComponentCurrentHitpoints(prototype, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("maxCapacity")) {
                    space_crafting.setCargoHoldMaxCapacity(prototype, (int) itemAttribute.currentValue);
                }
            }
        }
    }
}
