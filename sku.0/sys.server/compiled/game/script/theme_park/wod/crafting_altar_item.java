package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class crafting_altar_item extends script.base_script
{
    public crafting_altar_item()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String altarNumber = getStringObjVar(self, "wod.altarNumber");
        String attrib1 = Float.toString(Math.round(getFloatObjVar(self, "crafting_components.wod_crafting_1")));
        String attrib2 = Float.toString(Math.round(getFloatObjVar(self, "crafting_components.wod_crafting_2")));
        String attrib3 = Float.toString(Math.round(getFloatObjVar(self, "crafting_components.wod_crafting_3")));
        String attrib4 = "";
        boolean showAttrib4 = hasObjVar(self, "crafting_components.wod_crafting_4");
        if (showAttrib4)
        {
            attrib4 = Float.toString(Math.round(getFloatObjVar(self, "crafting_components.wod_crafting_4")));
        }
        names[idx] = "wod_altar_" + altarNumber + "_1";
        attribs[idx] = attrib1;
        idx++;
        names[idx] = "wod_altar_" + altarNumber + "_2";
        attribs[idx] = attrib2;
        idx++;
        names[idx] = "wod_altar_" + altarNumber + "_3";
        attribs[idx] = attrib3;
        if (showAttrib4)
        {
            idx++;
            names[idx] = "wod_altar_" + altarNumber + "_4";
            attribs[idx] = attrib4;
        }
        return SCRIPT_CONTINUE;
    }
}
