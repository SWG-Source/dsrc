package script.item.container.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class base_container extends script.base_script
{
    public base_container()
    {
    }
    public static final String SCRIPT_BASE_CONTAINER = "item.container.base.base_container";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        switch (getContainerType(self))
        {
            case 0:
            detachScript(self, SCRIPT_BASE_CONTAINER);
            return SCRIPT_OVERRIDE;
            default:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data midOpen = mi.getMenuItemByType(menu_info_types.ITEM_OPEN);
        if (midOpen != null)
        {
            midOpen.setServerNotify(true);
        }
        menu_info_data midLoot = mi.getMenuItemByType(menu_info_types.LOOT);
        if (midLoot != null)
        {
            midLoot.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_OPEN)
        {
            if (!hasObjVar(self, permissions.VAR_PERMISSION_BASE))
            {
                switch (getContainerType(self))
                {
                    case 0:
                    detachScript(self, SCRIPT_BASE_CONTAINER);
                    break;
                    case 1:
                    utils.requestContainerOpen(player, self);
                    break;
                    default:
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
