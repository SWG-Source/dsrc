package script.systems.crafting.armor;

import script.*;
import script.library.craftinglib;

public class crafting_base_armor_component extends script.systems.crafting.crafting_base
{
    public crafting_base_armor_component()
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
        CustomerServiceLog("Crafting", "WARNING: Old crafting_base_armor_component script called for object " + prototype + ", one of the new armor crafting scripts should be used instead!!");
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes) {
            if (itemAttribute == null) {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute)) {
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
