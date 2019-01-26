package script.systems.crafting.item;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.stealth;
import script.obj_id;

public class crafting_base_ap_trap extends script.systems.crafting.crafting_base
{
    public crafting_base_ap_trap()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int accuracy = 0;
        int power = 0;
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("accuracy")) {
                    accuracy = (int) itemAttribute.currentValue;
                } else if (((itemAttribute.name).getAsciiId()).equals("power")) {
                    power = (int) itemAttribute.currentValue;
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
        if (accuracy > 0)
        {
            setObjVar(prototype, stealth.TRAP_TR_ACCURACY, accuracy);
        }
        if (power > 0)
        {
            setObjVar(prototype, stealth.TRAP_POWER, power);
        }
    }
}
