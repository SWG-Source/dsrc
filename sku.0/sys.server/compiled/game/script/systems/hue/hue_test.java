package script.systems.hue;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class hue_test extends script.base_script
{
    public hue_test()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnuHue = mi.addRootMenu(menu_info_types.SERVER_HUE, new string_id("hue", "hue"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_HUE)
        {
            custom_var[] allVars = getAllCustomVars(self);
            for (int i = 0; i < allVars.length; i++)
            {
                custom_var cv = allVars[i];
                if (cv.isPalColor())
                {
                    ranged_int_custom_var ri = (ranged_int_custom_var)cv;
                    int min = ri.getMinRangeInclusive();
                    int max = ri.getMaxRangeInclusive();
                    int randVal = rand(min, max);
                    ri.setValue(randVal);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
