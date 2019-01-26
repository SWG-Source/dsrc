package script.systems.crafting.food;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_ingredient extends script.systems.crafting.crafting_base
{
    public crafting_base_ingredient()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("filling")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int filling = 0;
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("filling")) {
                    filling = (int) (itemAttribute.currentValue);
                } else {
                    if (itemAttribute.minValue == itemAttribute.maxValue) {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), (int) itemAttribute.currentValue);
                    } else {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), (float) itemAttribute.currentValue);
                    }
                }
            }
        }
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.TISSUE_SKILL_MODS + ".";
        int[] mod_idx = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_INDEX);
        int[] mod_val = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_VALUE);
        if (((mod_idx != null) && (mod_idx.length > 0)) && ((mod_val != null) && (mod_val.length > 0)))
        {
            setObjVar(prototype, root + craftinglib.TISSUE_SKILL_INDEX, mod_idx);
            setObjVar(prototype, root + craftinglib.TISSUE_SKILL_VALUE, mod_val);
        }
        fillStomach(prototype, filling);
    }
    public void fillStomach(obj_id prototype, int filling) throws InterruptedException
    {
    }
}
