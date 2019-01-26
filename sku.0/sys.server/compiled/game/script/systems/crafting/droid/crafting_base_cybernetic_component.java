package script.systems.crafting.droid;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_cybernetic_component extends script.systems.crafting.crafting_base
{
    public crafting_base_cybernetic_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int[] attribBonus = new int[NUM_ATTRIBUTES];
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_health")) {
                    obj_id self = getSelf();
                    attribBonus[0] = (int) itemAttribute.currentValue;
                    setAttributeBonuses(prototype, attribBonus);
                    setAttributeBonuses(self, attribBonus);
                }
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_action")) {
                    obj_id self = getSelf();
                    attribBonus[2] = (int) itemAttribute.currentValue;
                    setAttributeBonuses(prototype, attribBonus);
                    setAttributeBonuses(self, attribBonus);
                }
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_electrical_resist_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttribute.currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection")) {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttribute.currentValue);
                    }
                }
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_acid_resist_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttribute.currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection")) {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttribute.currentValue);
                    }
                }
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_heat_resist_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttribute.currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection")) {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttribute.currentValue);
                    }
                }
                if (((itemAttribute.name).getAsciiId()).equals("cybernetic_cold_resist_module")) {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttribute.currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection")) {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttribute.currentValue);
                    }
                }
            }
        }
    }
}
