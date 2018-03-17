package script.systems.crafting.bio_engineer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_bio_component extends script.systems.crafting.crafting_base
{
    public crafting_base_bio_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int[] effect_type = 
        {
            0,
            0,
            0,
            0,
            0,
            0
        };
        int[] effect_mod = 
        {
            0,
            0,
            0,
            0,
            0,
            0
        };
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).startsWith("mod_idx"))
                {
                    if (((itemAttributes[i].name).getAsciiId()).endsWith("one"))
                    {
                        effect_type[0] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("two"))
                    {
                        effect_type[1] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("three"))
                    {
                        effect_type[2] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("four"))
                    {
                        effect_type[3] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("five"))
                    {
                        effect_type[4] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("six"))
                    {
                        effect_type[5] = (int)itemAttributes[i].currentValue;
                    }
                }
                else if (((itemAttributes[i].name).getAsciiId()).startsWith("mod_val"))
                {
                    if (((itemAttributes[i].name).getAsciiId()).endsWith("one"))
                    {
                        effect_mod[0] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("two"))
                    {
                        effect_mod[1] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("three"))
                    {
                        effect_mod[2] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("four"))
                    {
                        effect_mod[3] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("five"))
                    {
                        effect_mod[4] = (int)itemAttributes[i].currentValue;
                    }
                    else if (((itemAttributes[i].name).getAsciiId()).endsWith("six"))
                    {
                        effect_mod[5] = (int)itemAttributes[i].currentValue;
                    }
                }
                else 
                {
                    if (itemAttributes[i].minValue == itemAttributes[i].maxValue)
                    {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), (int)itemAttributes[i].currentValue);
                    }
                    else 
                    {
                        setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), (float)itemAttributes[i].currentValue);
                    }
                }
            }
        }
        for (int i = 5; i >= 0; i--)
        {
            if (effect_type[i] != 0)
            {
                for (int j = 0; j < i; j++)
                {
                    if (effect_type[j] == 0)
                    {
                        effect_type[j] = effect_type[i];
                        effect_mod[j] = effect_mod[i];
                        effect_type[i] = 0;
                        effect_mod[i] = 0;
                    }
                }
            }
        }
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.TISSUE_SKILL_MODS + ".";
        setObjVar(prototype, root + craftinglib.TISSUE_SKILL_INDEX, effect_type);
        setObjVar(prototype, root + craftinglib.TISSUE_SKILL_VALUE, effect_mod);
    }
}
