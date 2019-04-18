package script.systems.crafting.bio_engineer;

import script.attrib_mod;
import script.dictionary;
import script.draft_schematic;
import script.library.consumable;
import script.library.craftinglib;
import script.library.utils;
import script.obj_id;

public class crafting_base_pet_meds extends script.systems.crafting.crafting_base
{
    public crafting_base_pet_meds()
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
                    case "strength":
                        setObjVar(prototype, "consumable.strength", (int) itemAttribute.currentValue);
                        break;
                    default:
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        break;
                }
            }
        }
        attrib_mod[] am = createHealDamageMedicineMod(tempPower);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
    }
    public attrib_mod[] createHealDamageMedicineMod(int power) throws InterruptedException
    {
        attrib_mod[] am = new attrib_mod[3];
        for (int i = 0; i < 3; i++)
        {
            am[i] = utils.createHealDamageAttribMod(i * 3, power);
        }
        return am;
    }
}
