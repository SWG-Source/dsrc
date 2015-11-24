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

public class crafting_base_detect_item extends script.systems.crafting.crafting_base
{
    public crafting_base_detect_item()
    {
    }
    public static final String VERSION = "v1.00.00";
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }
    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        float effectiveness = 0;
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
                if (((itemAttributes[i].name).getAsciiId()).equals("effectiveness"))
                {
                    effectiveness = (int)itemAttributes[i].currentValue;
                }
                else 
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttributes[i].name).getAsciiId(), itemAttributes[i].currentValue);
                }
            }
        }
        if (effectiveness > 0)
        {
            String template = getTemplateName(prototype);
            if (template.indexOf("biosensorprobe") > -1)
            {
                setObjVar(prototype, stealth.BIO_PROBE_STORAGE_TIME, effectiveness / 1000 * stealth.MAX_BIO_PROBE_STORAGE_TIME);
            }
            else if (template.indexOf("trackingbeacon") > -1 || template.indexOf("motionsensor") > -1)
            {
                setObjVar(prototype, stealth.BEACON_BATTERY, effectiveness);
            }
            else 
            {
                setObjVar(prototype, stealth.DETECT_EFFECTIVENESS, effectiveness);
            }
        }
    }
}
