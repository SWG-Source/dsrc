package script.systems.hue;

import script.*;

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
            for (custom_var cv : allVars) {
                if (cv.isPalColor()) {
                    ranged_int_custom_var ri = (ranged_int_custom_var) cv;
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
