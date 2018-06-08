package script.item.elitest;

import script.library.groundquests;
import script.*;

public class send_signal_test extends script.base_script
{
    public send_signal_test()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id SEND_SIGNAL = new string_id(STF, "lockbox_code");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, SEND_SIGNAL);
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            groundquests.sendSignal(player, "mustafar_uplink_comm");
        }
        return SCRIPT_CONTINUE;
    }
}
