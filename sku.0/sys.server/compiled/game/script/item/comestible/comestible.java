package script.item.comestible;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.consumable;

public class comestible extends script.base_script
{
    public comestible()
    {
    }
    public static final String SCRIPT_COMESTIBLE = "item.comestible.comestible";
    public static final String EXAM_ATTRIB_MOD = "attribmods";
    public static final String EXAM_NONE = "@consumable:none";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        mid = mi.getMenuItemByType(menu_info_types.EXAMINE);
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
            queueCommand(player, (1129569737), self, "", COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
}
