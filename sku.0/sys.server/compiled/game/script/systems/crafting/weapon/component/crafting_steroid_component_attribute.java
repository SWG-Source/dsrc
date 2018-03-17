package script.systems.crafting.weapon.component;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.craftinglib;
import script.library.utils;

public class crafting_steroid_component_attribute extends script.base_script
{
    public crafting_steroid_component_attribute()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self) && hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "charges"))
        {
            names[idx] = "charges";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "charges");
            attribs[idx] = Integer.toString(attrib);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (exists(self) && hasObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "beastSteroidBonus"))
        {
            names[idx] = "beastSteroidBonus";
            int attrib = (int)getFloatObjVar(self, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + "beastSteroidBonus");
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
