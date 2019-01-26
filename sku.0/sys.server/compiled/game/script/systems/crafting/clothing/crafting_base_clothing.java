package script.systems.crafting.clothing;

import script.dictionary;
import script.draft_schematic;
import script.library.bio_engineer;
import script.library.craftinglib;
import script.obj_id;

public class crafting_base_clothing extends script.systems.crafting.crafting_base
{
    public crafting_base_clothing()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "crafting_base_clothing -- " + prototype);
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.TISSUE_SKILL_MODS + ".";
        int[] mod_idx = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_INDEX);
        int[] mod_val = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_VALUE);
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
                if (((itemAttribute.name).getAsciiId()).startsWith("mod_idx") || ((itemAttribute.name).getAsciiId()).startsWith("mod_val")) {
                } else {
                    switch (((itemAttribute.name).getAsciiId())) {
                        case "armor_rating":
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), (int) itemAttribute.currentValue);
                            break;
                        case "armor_health_encumbrance": {
                            float encum_value = (itemAttribute.maxValue + itemAttribute.minValue) - itemAttribute.currentValue;
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), encum_value);
                            break;
                        }
                        case "armor_action_encumbrance": {
                            float encum_value = (itemAttribute.maxValue + itemAttribute.minValue) - itemAttribute.currentValue;
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), encum_value);
                            break;
                        }
                        case "armor_mind_encumbrance": {
                            float encum_value = (itemAttribute.maxValue + itemAttribute.minValue) - itemAttribute.currentValue;
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), encum_value);
                            break;
                        }
                        default:
                            setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                            break;
                    }
                }
            }
        }
        int type = getGameObjectType(prototype);
        if (!isGameObjectTypeOf(type, GOT_armor))
        {
            if (mod_idx != null && mod_idx.length > 0)
            {
                for (int i = 0; i < 6; i++)
                {
                    if (mod_idx[i] > bio_engineer.BIO_COMP_EFFECT_CLOTHING)
                    {
                        String skill_mod = bio_engineer.BIO_COMP_EFFECT_SKILL_MODS[(mod_idx[i] - (bio_engineer.BIO_COMP_EFFECT_CLOTHING + 1))];
                        setSkillModBonus(prototype, skill_mod, mod_val[i]);
                        setCondition(prototype, CONDITION_MAGIC_ITEM);
                    }
                }
            }
        }
    }
}
