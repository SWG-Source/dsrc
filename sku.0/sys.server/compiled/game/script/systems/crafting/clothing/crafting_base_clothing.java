package script.systems.crafting.clothing;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.bio_engineer;

public class crafting_base_clothing extends script.systems.crafting.crafting_base
{
    public crafting_base_clothing()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
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
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).startsWith("mod_idx") || ((itemAttributes[i].name).getAsciiId()).startsWith("mod_val"))
                {
                }
                else 
                {
                    if (((itemAttributes[i].name).getAsciiId()).equals("armor_rating"))
                    {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), (int)itemAttributes[i].currentValue);
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).equals("armor_health_encumbrance"))
                    {
                        float encum_value = (itemAttributes[i].maxValue + itemAttributes[i].minValue) - itemAttributes[i].currentValue;
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), encum_value);
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).equals("armor_action_encumbrance"))
                    {
                        float encum_value = (itemAttributes[i].maxValue + itemAttributes[i].minValue) - itemAttributes[i].currentValue;
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), encum_value);
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).equals("armor_mind_encumbrance"))
                    {
                        float encum_value = (itemAttributes[i].maxValue + itemAttributes[i].minValue) - itemAttributes[i].currentValue;
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), encum_value);
                    }
                    else 
                    {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
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
