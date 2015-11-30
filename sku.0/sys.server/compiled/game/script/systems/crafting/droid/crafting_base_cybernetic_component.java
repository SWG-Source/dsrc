package script.systems.crafting.droid;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_cybernetic_component extends script.systems.crafting.crafting_base
{
    public crafting_base_cybernetic_component()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int[] attribBonus = new int[NUM_ATTRIBUTES];
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_health"))
                {
                    obj_id self = getSelf();
                    attribBonus[0] = (int)itemAttributes[i].currentValue;
                    setAttributeBonuses(prototype, attribBonus);
                    setAttributeBonuses(self, attribBonus);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_action"))
                {
                    obj_id self = getSelf();
                    attribBonus[2] = (int)itemAttributes[i].currentValue;
                    setAttributeBonuses(prototype, attribBonus);
                    setAttributeBonuses(self, attribBonus);
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_electrical_resist_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttributes[i].currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection"))
                    {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttributes[i].currentValue);
                    }
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_acid_resist_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttributes[i].currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection"))
                    {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttributes[i].currentValue);
                    }
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_heat_resist_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttributes[i].currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection"))
                    {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttributes[i].currentValue);
                    }
                }
                if (((itemAttributes[i].name).getAsciiId()).equals("cybernetic_cold_resist_module"))
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".cybernetic_electrical_resist_module", itemAttributes[i].currentValue);
                    if (hasObjVar(prototype, "cybernetic.special_protection"))
                    {
                        setObjVar(prototype, "cybernetic.special_protection.type", getIntObjVar(prototype, "cybernetic.special_protection"));
                        setObjVar(prototype, "cybernetic.special_protection.value", itemAttributes[i].currentValue);
                    }
                }
            }
        }
    }
}
