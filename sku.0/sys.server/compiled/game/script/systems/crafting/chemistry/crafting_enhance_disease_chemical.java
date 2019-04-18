package script.systems.crafting.chemistry;

import script.attrib_mod;
import script.dictionary;
import script.draft_schematic;
import script.library.consumable;
import script.library.craftinglib;
import script.library.healing;
import script.obj_id;

public class crafting_enhance_disease_chemical extends script.systems.crafting.crafting_base
{
    public crafting_enhance_disease_chemical()
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
        int tempDuration = 0;
        int tempAbsorption = 0;
        int tempCharges = 0;
        int tempSkill_req = 0;
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
                        tempPower = (int) ((itemAttribute.currentValue) / 2);
                        break;
                    case "absorption":
                        tempAbsorption = (int) (itemAttribute.currentValue);
                        if (tempAbsorption > 0) {
                            setObjVar(prototype, healing.VAR_HEALING_ABSORPTION, tempAbsorption);
                        }
                        break;
                    case "charges":
                        tempCharges = (int) (itemAttribute.currentValue);
                        break;
                    case "skillModMin":
                        tempSkill_req = (int) ((itemAttribute.maxValue + itemAttribute.minValue) - itemAttribute.currentValue);
                        break;
                    case "duration":
                        tempDuration = (int) (itemAttribute.currentValue);
                        break;
                    default:
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                        break;
                }
            }
        }
        int buff_type = 10;
        if (buff_type == -1)
        {
            buff_type = rand(0, 5);
        }
        attrib_mod[] am = new attrib_mod[1];
        am[0] = new attrib_mod(buff_type, tempPower, tempDuration, healing.VAR_BUFF_MOD_ATTACK, healing.VAR_BUFF_MOD_DECAY);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MODS, am);
        setObjVar(prototype, consumable.VAR_CONSUMABLE_MEDICINE, true);
        setCount(prototype, tempCharges);
        int[] stomach = 
        {
            0,
            0,
            0
        };
        setObjVar(prototype, consumable.VAR_CONSUMABLE_STOMACH_VALUES, stomach);
        String[] skill_mod = 
        {
            "healing_ability"
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_REQUIRED, skill_mod);
        int[] skill_mod_value = 
        {
            tempSkill_req
        };
        setObjVar(prototype, consumable.VAR_SKILL_MOD_MIN, skill_mod_value);
    }
}
