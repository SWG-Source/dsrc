package script.data_item;

import script.menu_info;
import script.menu_info_data;
import script.menu_info_types;
import script.obj_id;

public class base extends script.base_script
{
    public base()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
        if (mid != null) {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.EXAMINE)
        {
            sendSystemMessageTestingOnly(player, "You would be seeing a ficticious data report here..");
        }
        return SCRIPT_CONTINUE;
    }
}
