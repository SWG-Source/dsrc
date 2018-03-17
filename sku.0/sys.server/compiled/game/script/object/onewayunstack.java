package script.object;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.craftinglib;

public class onewayunstack extends script.base_script
{
    public onewayunstack()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!canUnstack(player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU10, new string_id("sui", "unstack"));
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.SERVER_MENU10);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!canUnstack(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            unstackIt(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void unstackIt(obj_id stackedItem, obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id container = utils.getInventoryContainer(player);
        if (getVolumeFree(container) < getVolume(stackedItem))
        {
            sendSystemMessage(player, new string_id("sui", "unstack_noroom"));
            return;
        }
        String template = getTemplateName(stackedItem);
        obj_id copy_item = createObject(template, container, "");
        if (!isIdValid(copy_item))
        {
            return;
        }
        copyObjVar(stackedItem, copy_item, "crafting");
        copyObjVar(stackedItem, copy_item, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME);
        copyObjVar(stackedItem, copy_item, "attribute");
        setCraftedId(copy_item, getObjIdObjVar(stackedItem, "unstack.serialNumber"));
        setCrafter(copy_item, getCrafter(stackedItem));
        setAttributeBonuses(copy_item, getAttributeBonuses(stackedItem));
        incrementCount(stackedItem, -1);
        int count = getCount(stackedItem);
        if (count == 1)
        {
            removeObjVar(stackedItem, "unstack.serialNumber");
            detachScript(stackedItem, "object.onewayunstack");
            sendDirtyObjectMenuNotification(stackedItem);
        }
        return;
    }
    public boolean canUnstack(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        obj_id playerInventory = utils.getInventoryContainer(player);
        int count = getCount(self);
        if (count < 2)
        {
            return false;
        }
        if (!hasObjVar(self, "unstack.serialNumber"))
        {
            return false;
        }
        return utils.isNestedWithin(getSelf(), playerInventory);
    }
}
