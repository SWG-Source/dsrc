package script.systems.crafting.weapon;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.weapons;
import script.obj_id;

public class crafting_new_weapon_core extends script.systems.crafting.crafting_base
{
    public crafting_new_weapon_core()
    {
    }
    public static final String VERSION = "v0.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        obj_id self = getSelf();
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (itemAttribute.currentValue < itemAttribute.minValue) {
                    itemAttribute.currentValue = itemAttribute.minValue;
                } else if (itemAttribute.currentValue > itemAttribute.maxValue) {
                    itemAttribute.currentValue = itemAttribute.maxValue;
                }
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                setObjVar(prototype, weapons.OBJVAR_NEW_WP_COMPONENT, true);
                weapons.setComponentObjVars(prototype, itemAttributes);
            }
        }
        weapons.setSchematicVariablesFromProtoType(self, prototype);
    }
}
