package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npe;
import script.library.static_item;
import script.library.utils;

public class veteran_reward_crate_exp_buff extends script.base_script
{
    public veteran_reward_crate_exp_buff()
    {
    }
    public static final String STF_FILE = "npe";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "crate_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id pInv = utils.getInventoryContainer(player);
            sendSystemMessage(player, new string_id(STF_FILE, "opened_crate"));
            static_item.createNewItemFunction("item_vet_exp_buff_item_03_01", pInv);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
