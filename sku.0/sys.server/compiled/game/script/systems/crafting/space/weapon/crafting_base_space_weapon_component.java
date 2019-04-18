package script.systems.crafting.space.weapon;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_space_weapon_component extends script.systems.crafting.crafting_base
{
    public crafting_base_space_weapon_component()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("energy_maintenance") || ((itemAttribute.name).getAsciiId()).equals("energy_per_shot") || ((itemAttribute.name).getAsciiId()).equals("mass") || ((itemAttribute.name).getAsciiId()).equals("refire_rate")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("refire_rate") || ((itemAttribute.name).getAsciiId()).equals("effective_shields") || ((itemAttribute.name).getAsciiId()).equals("effective_armor")) {
                itemAttribute.currentValue = (itemAttribute.currentValue * 0.001f);
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
                if (((itemAttribute.name).getAsciiId()).equals("damage_max")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".damage_max", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("damage_min")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".damage_min", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("effective_shields")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".effective_shields", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("effective_armor")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".effective_armor", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_per_shot")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".energy_per_shot", itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("refire_rate")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".refire_rate", itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
