package script.theme_park.newbie_tutorial;

import script.library.utils;
import script.menu_info;
import script.menu_info_data;
import script.menu_info_types;
import script.obj_id;

public class insurance extends script.theme_park.newbie_tutorial.tutorial_base
{
    public insurance()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        utils.setScriptVar(player, "newbie.usedInsureTerm", true);
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            utils.setScriptVar(player, "newbie.insuredStuff", true);
            messageTo(player, "handleInsuranceInfoSpam", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
