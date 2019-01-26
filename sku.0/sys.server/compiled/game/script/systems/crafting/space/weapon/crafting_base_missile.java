package script.systems.crafting.space.weapon;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_missile extends script.systems.crafting.crafting_base
{
    public crafting_base_missile()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("fltRefireRate")) {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("fltRefireRate") || ((itemAttribute.name).getAsciiId()).equals("fltShieldEffectiveness") || ((itemAttribute.name).getAsciiId()).equals("fltArmorEffectiveness")) {
                itemAttribute.currentValue = (itemAttribute.currentValue * 0.001f);
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
                if (((itemAttribute.name).getAsciiId()).equals("fltMinDamage") || ((itemAttribute.name).getAsciiId()).equals("fltMinEffectiveness")) {
                    setObjVar(prototype, "fltMinDamage", (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("fltMaxDamage") || ((itemAttribute.name).getAsciiId()).equals("fltMaxEffectiveness")) {
                    setObjVar(prototype, "fltMaxDamage", (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("fltRefireRate")) {
                    setObjVar(prototype, "fltRefireRate", (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("fltShieldEffectiveness")) {
                    setObjVar(prototype, "fltShieldEffectiveness", (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("fltArmorEffectiveness")) {
                    setObjVar(prototype, "fltArmorEffectiveness", (float) itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("fltMaxAmmo")) {
                    setCount(prototype, (int) itemAttribute.currentValue);
                } else {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
