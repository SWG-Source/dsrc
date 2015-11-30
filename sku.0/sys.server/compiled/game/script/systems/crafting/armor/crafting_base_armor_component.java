package script.systems.crafting.armor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class crafting_base_armor_component extends script.systems.crafting.crafting_base
{
    public crafting_base_armor_component()
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
        CustomerServiceLog("Crafting", "WARNING: Old crafting_base_armor_component script called for object " + prototype + ", one of the new armor crafting scripts should be used instead!!");
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (int i = 0; i < itemAttributes.length; ++i)
        {
            if (itemAttributes[i] == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttributes[i]))
            {
            }
        }
        
        {
            String name = craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + ".armor_special_protection";
            obj_var special = getObjVar(getSelf(), name);
            if (special != null && special instanceof obj_var_list)
            {
                obj_var_list specialList = (obj_var_list)special;
                int count = specialList.getNumItems();
                for (int i = 0; i < count; ++i)
                {
                    obj_var attrib = specialList.getObjVar(i);
                    setObjVar(prototype, name + "." + attrib.getName(), attrib.getData());
                }
            }
        }
    }
}
