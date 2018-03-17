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
import script.library.pet_lib;

public class cybernetic_component_attribute extends script.base_script
{
    public cybernetic_component_attribute()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "cybernetic.special_protection.value") && hasObjVar(self, "cybernetic.special_protection.type"))
        {
            int protectionType = getIntObjVar(self, "cybernetic.special_protection.type");
            String protectionString = getSpecialProtectionString(protectionType);
            names[idx] = "special_protection_type" + "_" + protectionString;
            float preAttrib = getFloatObjVar(self, "cybernetic.special_protection.value");
            preAttrib *= 100.0f;
            int attrib = (int)preAttrib;
            attribs[idx] = " " + attrib;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String getSpecialProtectionString(int protectionType) throws InterruptedException
    {
        switch (protectionType)
        {
            case 0:
            return "kinetic";
            case 1:
            return "energy";
            case 2:
            return "electricity";
            case 4:
            return "heat";
            case 5:
            return "cold";
            case 6:
            return "acid";
            default:
            return "unknown";
        }
    }
}
