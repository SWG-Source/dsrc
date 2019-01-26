package script.systems.crafting.weapon;

import script.*;
import script.library.craftinglib;

public class crafting_base_saber_component extends script.systems.crafting.crafting_base
{
    public crafting_base_saber_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("attackSpeed") || ((itemAttribute.name).getAsciiId()).equals("attackCost")) {
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
                if (((itemAttribute.name).getAsciiId()).equals("accuracy")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".accuracy", itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
        custom_var myVar = getCustomVarByName(prototype, "private/index_color_1");
        if (myVar.isPalColor())
        {
            palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
            pcVar.setValue(31);
        }
    }
}
