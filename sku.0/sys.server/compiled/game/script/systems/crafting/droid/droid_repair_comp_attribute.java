package script.systems.crafting.droid;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;

public class droid_repair_comp_attribute extends script.base_script
{
    public droid_repair_comp_attribute()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "power"))
        {
            names[idx] = "power";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "power");
            attribs[idx] = Integer.toString(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "energy"))
        {
            names[idx] = "energy";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "energy");
            attribs[idx] = Integer.toString(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "charges"))
        {
            names[idx] = "charges";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "charges");
            String attrib_str = Integer.toString(attrib);
            attribs[idx] = attrib_str;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "hitPoints"))
        {
            names[idx] = "hitpoints";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "hitPoints");
            attribs[idx] = Integer.toString(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
