package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class pro_seed_jar extends script.base_script
{
    public pro_seed_jar()
    {
    }
    public static final string_id SID_MNU_USE = new string_id("spam", "open");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("encoded_disk/message_fragment", "sys_not_in_inv");
    public static final String[] TREES = 
    {
        "item_schematic_wod_pro_tree_01",
        "item_schematic_wod_pro_tree_02",
        "item_schematic_wod_pro_tree_03",
        "item_schematic_wod_pro_tree_04",
        "item_schematic_wod_pro_tree_05",
        "item_schematic_wod_pro_tree_06",
        "item_schematic_wod_pro_tree_07",
        "item_schematic_wod_pro_tree_08"
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int treeSelect = rand(0, 7);
            obj_id createdSchematic = static_item.createNewItemFunction(TREES[treeSelect], player);
            if (isIdValid(createdSchematic))
            {
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
