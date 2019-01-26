package script.systems.crafting.space.chassis;

import script.dictionary;
import script.draft_schematic;
import script.obj_id;

public class crafting_base_blacksun_heavy_s01_chassis extends script.systems.crafting.crafting_base
{
    public crafting_base_blacksun_heavy_s01_chassis()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            setObjVar(prototype, "isShipDeed", true);
            setObjVar(prototype, "shiptype", "blacksun_heavy_s01");
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
                }
            }
        }
    }
}
