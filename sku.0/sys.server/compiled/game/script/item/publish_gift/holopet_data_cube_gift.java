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

public class holopet_data_cube_gift extends script.base_script
{
    public holopet_data_cube_gift()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setLabel(new string_id("spam", "open"));
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
            openMe(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void openMe(obj_id self, obj_id player) throws InterruptedException
    {
        int roll = rand(1, 100);
        String item_name = "";
        if (roll == 1)
        {
            item_name = "item_holopet_atat_data_cube_01_01";
        }
        else if (roll <= 4)
        {
            item_name = "item_holopet_ewok_data_cube_01_01";
        }
        else if (roll <= 7)
        {
            item_name = "item_holopet_rancor_data_cube_01_01";
        }
        else if (roll <= 10)
        {
            item_name = "item_holopet_jawa_data_cube_01_01";
        }
        else if (roll <= 25)
        {
            item_name = "item_holopet_quenker_data_cube_01_01";
        }
        else if (roll <= 40)
        {
            item_name = "item_holopet_squall_data_cube_01_01";
        }
        else if (roll <= 55)
        {
            item_name = "item_holopet_pekopeko_data_cube_01_01";
        }
        else if (roll <= 70)
        {
            item_name = "item_holopet_jax_data_cube_01_01";
        }
        else if (roll <= 85)
        {
            item_name = "item_holopet_veermok_data_cube_01_01";
        }
        else 
        {
            item_name = "item_holopet_slicehound_data_cube_01_01";
        }
        obj_id item = static_item.createNewItemFunction(item_name, player);
        if (isIdValid(item))
        {
            sendSystemMessage(player, new string_id("spam", "open_package"));
            destroyObject(self);
        }
    }
}
