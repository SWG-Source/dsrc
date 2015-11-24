package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;
import script.library.space_transition;

public class repair_kit extends script.base_script
{
    public repair_kit()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String strKitType = getStringObjVar(self, "strKitType");
        if (strKitType.equals("plasma_conduit"))
        {
            setCount(self, 10);
        }
        else 
        {
            setCount(self, 1000);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int charges = getCount(self);
        names[idx] = "repairpoints";
        attribs[idx] = Integer.toString(charges);
        idx++;
        return SCRIPT_CONTINUE;
    }
}
