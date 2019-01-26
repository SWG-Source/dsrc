package script.systems.crafting.space.reactor;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_reactor_component extends script.systems.crafting.crafting_base
{
    public crafting_base_reactor_component()
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
        attachScript(prototype, "space.crafting.subcomponent");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("energy_gen_rate")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".energy_gen_rate", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("mass")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".mass", itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
