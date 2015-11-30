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

public class crafting_base_clothing_component extends script.systems.crafting.crafting_base
{
    public crafting_base_clothing_component()
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
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("armor_rating") || ((itemAttributes[i].name).getAsciiId()).equals("armor_special_type") || ((itemAttributes[i].name).getAsciiId()).equals("armor_elemental_type"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), (int)itemAttributes[i].currentValue);
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
        obj_id self = getSelf();
        String root = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + craftinglib.TISSUE_SKILL_MODS + ".";
        int[] mod_idx = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_INDEX);
        int[] mod_val = getIntArrayObjVar(self, root + craftinglib.TISSUE_SKILL_VALUE);
        if (((mod_idx != null) && (mod_idx.length > 0)) && ((mod_val != null) && (mod_val.length > 0)))
        {
            setObjVar(prototype, root + craftinglib.TISSUE_SKILL_INDEX, mod_idx);
            setObjVar(prototype, root + craftinglib.TISSUE_SKILL_VALUE, mod_val);
        }
    }
}
