package script.systems.crafting.chemistry;

import script.dictionary;
import script.draft_schematic;
import script.library.consumable;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_cure_chemical extends script.systems.crafting.crafting_base
{
    public crafting_base_cure_chemical()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int tempPower = 0;
        int[] skill_value = 
        {
            0
        };
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                switch (((itemAttribute.name).getAsciiId())) {
                    case "power":
                        setObjVar(prototype, "healing.dot_power", (int) itemAttribute.currentValue);
                        break;
                    case "charges":
                        setCount(prototype, (int) itemAttribute.currentValue);
                        break;
                    case "skillModMin":
                        skill_value[0] = (int) ((itemAttribute.maxValue + itemAttribute.minValue) - itemAttribute.currentValue);
                        break;
                    case "range":
                        setObjVar(prototype, "healing.range", (int) itemAttribute.currentValue);
                        break;
                    case "area":
                        setObjVar(prototype, "healing.area", (int) itemAttribute.currentValue);
                        break;
                    default:
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        break;
                }
            }
        }
        String[] skill_mod = 
        {
            "healing_ability"
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        setObjVar(prototype, consumable.VAR_SKILL_MOD_MIN, skill_value);
    }
}
