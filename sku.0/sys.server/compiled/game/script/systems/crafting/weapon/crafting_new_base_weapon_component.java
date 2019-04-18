package script.systems.crafting.weapon;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.weapons;
import script.obj_id;

public class crafting_new_base_weapon_component extends script.systems.crafting.crafting_base
{
    public crafting_new_base_weapon_component()
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
        obj_id self = getSelf();
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                float modifiedValue = itemAttribute.currentValue;
                setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), modifiedValue);
                setObjVar(prototype, weapons.OBJVAR_NEW_WP_COMPONENT, true);
                weapons.setComponentObjVars(prototype, itemAttributes);
            }
        }
        weapons.setSchematicVariablesFromProtoType(self, prototype);
    }
}
