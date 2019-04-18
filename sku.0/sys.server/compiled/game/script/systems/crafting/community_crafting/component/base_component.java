package script.systems.crafting.community_crafting.component;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class base_component extends script.systems.crafting.crafting_base
{
    public base_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute.currentValue > itemAttribute.maxValue) {
                itemAttribute.currentValue = itemAttribute.maxValue;
            } else if (itemAttribute.currentValue < itemAttribute.minValue) {
                itemAttribute.currentValue = itemAttribute.minValue;
            }
        }
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
            }
        }
    }
}
