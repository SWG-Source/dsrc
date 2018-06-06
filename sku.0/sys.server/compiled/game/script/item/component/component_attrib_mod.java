package script.item.component;

import script.library.utils;
import script.obj_id;

public class component_attrib_mod extends script.base_script
{
    public component_attrib_mod()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "attribute.bonus.0"))
        {
            names[idx] = "health_bonus";
            attribs[idx] = "+" + getIntObjVar(self, "attribute.bonus.0");
            
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
