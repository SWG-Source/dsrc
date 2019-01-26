package script.systems.crafting.space.chassis;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_twing_chassis extends script.systems.crafting.crafting_base
{
    public crafting_base_twing_chassis()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        setObjVar(prototype, "isShipDeed", true);
        setObjVar(prototype, "shiptype", "twing");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("massMax")) {
                    String OBJVAR_NAME = "ship_chassis.mass";
                    setObjVar(prototype, OBJVAR_NAME, (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("hp")) {
                    String OBJVAR_NAME = "ship_chassis.hp";
                    setObjVar(prototype, OBJVAR_NAME, (float) itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
