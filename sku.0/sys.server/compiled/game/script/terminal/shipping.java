package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class shipping extends script.terminal.base.base_terminal
{
    public shipping()
    {
    }
    public static final string_id SID_SHIPPING_OPTIONS = new string_id("terminal_ui", "shipping_options");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.SERVER_SHIPPING_OPTIONS, SID_SHIPPING_OPTIONS);
        return super.OnObjectMenuRequest(self, player, mi);
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_SHIPPING_OPTIONS)
        {
        }
        return SCRIPT_CONTINUE;
    }
}
