package script.terminal.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.structure;

public class terminal_add_use extends script.terminal.base.base_terminal
{
    public terminal_add_use()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("", ""));
        }
        return super.OnObjectMenuRequest(self, player, mi);
    }
}
