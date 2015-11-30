package script.systems.crafting.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;
import script.library.powerup;
import script.library.stealth;

public class crafting_base_ap_trap extends script.systems.crafting.crafting_base
{
    public crafting_base_ap_trap()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        int accuracy = 0;
        int power = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("accuracy"))
                {
                    accuracy = (int)itemAttributes[i].currentValue;
                }
                else if (((itemAttributes[i].name).getAsciiId()).equals("power"))
                {
                    power = (int)itemAttributes[i].currentValue;
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        if (accuracy > 0)
        {
            setObjVar(prototype, stealth.TRAP_TR_ACCURACY, accuracy);
        }
        if (power > 0)
        {
            setObjVar(prototype, stealth.TRAP_POWER, power);
        }
    }
}
