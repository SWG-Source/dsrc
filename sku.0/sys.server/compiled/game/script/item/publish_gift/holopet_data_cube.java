package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.utils;

public class holopet_data_cube extends script.base_script
{
    public holopet_data_cube()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER))
        {
            String holobeastCreator = getStringObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER);
            if (holobeastCreator != null && holobeastCreator.length() > 0)
            {
                names[idx] = "holobeast_creator";
                attribs[idx] = "\n" + holobeastCreator;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        if (hasObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR))
        {
            int holobeastColor = getIntObjVar(self, beast_lib.HOLO_BEAST_RARE_COLOR_OBJVAR);
            String rareColor = "";
            switch (holobeastColor)
            {
                case beast_lib.HOLO_BEAST_TYPE3_QUALITY3:
                rareColor = "Burnt Orange";
                break;
                default:
                break;
            }
            if (rareColor.length() > 0)
            {
                names[idx] = "holobeast_rare_color";
                attribs[idx] = rareColor;
                idx++;
                if (idx >= names.length)
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
