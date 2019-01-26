package script.systems.crafting.droid;

import script.attrib_mod;
import script.dictionary;
import script.draft_schematic;
import script.library.consumable;
import script.library.craftinglib;
import script.library.healing;
import script.library.utils;
import script.obj_id;

public class crafting_base_droid_repair extends script.systems.crafting.crafting_base
{
    public crafting_base_droid_repair()
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
                        tempPower = (int) (itemAttribute.currentValue);
                        break;
                    case "charges":
                        setCount(prototype, (int) itemAttribute.currentValue);
                        break;
                    case "energy":
                        setObjVar(prototype, "consumable.energy", (int) itemAttribute.currentValue);
                        break;
                    default:
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        break;
                }
            }
        }
        attrib_mod[] am = createDroidMedicineMod(prototype, tempPower);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
    }
    public attrib_mod[] createDroidMedicineMod(obj_id prototype, int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[1];
        int value = power;
        float attack = healing.AM_HEAL_WOUND;
        float decay = 0.0f;
        if (hasObjVar(prototype, "consumable.energy"))
        {
            value = getIntObjVar(prototype, "consumable.energy");
            attack = 0.0f;
            decay = MOD_POOL;
        }
        am[0] = utils.createAttribMod(0, value, 0.0f, attack, decay);
        return am;
    }
}
