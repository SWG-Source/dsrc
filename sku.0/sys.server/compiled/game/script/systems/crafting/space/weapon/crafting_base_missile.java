package script.systems.crafting.space.weapon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.space_crafting;

public class crafting_base_missile extends script.systems.crafting.crafting_base
{
    public crafting_base_missile()
    {
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("fltRefireRate"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].minValue + itemAttributes[i].maxValue) - itemAttributes[i].currentValue;
            }
            if (((itemAttributes[i].name).getAsciiId()).equals("fltRefireRate") || ((itemAttributes[i].name).getAsciiId()).equals("fltShieldEffectiveness") || ((itemAttributes[i].name).getAsciiId()).equals("fltArmorEffectiveness"))
            {
                itemAttributes[i].currentValue = (itemAttributes[i].currentValue * 0.001f);
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
                if (((itemAttributes[i].name).getAsciiId()).equals("fltMinDamage") || ((itemAttributes[i].name).getAsciiId()).equals("fltMinEffectiveness"))
                {
                    setObjVar(prototype, "fltMinDamage", (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("fltMaxDamage") || ((itemAttributes[i].name).getAsciiId()).equals("fltMaxEffectiveness"))
                {
                    setObjVar(prototype, "fltMaxDamage", (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("fltRefireRate"))
                {
                    setObjVar(prototype, "fltRefireRate", (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("fltShieldEffectiveness"))
                {
                    setObjVar(prototype, "fltShieldEffectiveness", (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("fltArmorEffectiveness"))
                {
                    setObjVar(prototype, "fltArmorEffectiveness", (float)itemAttributes[i].currentValue);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("fltMaxAmmo"))
                {
                    setCount(prototype, (int)itemAttributes[i].currentValue);
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
    }
}
