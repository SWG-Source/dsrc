package script.item.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class publish29 extends script.base_script
{
    public publish29()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setLabel(new string_id("spam", "unroll"));
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id containingPlayer = utils.getContainingPlayer(self);
        if (!isIdValid(containingPlayer))
        {
            sendSystemMessage(player, new string_id("spam", "cannot_use_not_in_inv"));
            return SCRIPT_CONTINUE;
        }
        if (containingPlayer != player)
        {
            sendSystemMessage(player, new string_id("spam", "cannot_use_not_in_inv"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            unrollMe(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void unrollMe(obj_id self, obj_id player) throws InterruptedException
    {
        int which_one = rand(1, 3);
        String poster = "";
        switch (which_one)
        {
            case 1:
            poster = "item_publish_gift_29_corellia_04_01";
            break;
            case 2:
            poster = "item_publish_gift_29_ryatt_04_01";
            break;
            case 3:
            poster = "item_publish_gift_29_mustafar_04_01";
            break;
        }
        obj_id item = static_item.createNewItemFunction(poster, player);
        if (isIdValid(item))
        {
            sendSystemMessage(player, new string_id("spam", "unroll_poster"));
            destroyObject(self);
        }
    }
}
