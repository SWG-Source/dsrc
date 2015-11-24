package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class multi_item_set_crate extends script.base_script
{
    public multi_item_set_crate()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("npe", "crate_use"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (canManipulate(player, self, true, true, 15, true))
            {
                if (utils.isNestedWithinAPlayer(self))
                {
                    if (hasObjVar(self, "multi_item_set_name"))
                    {
                        String datatable = "datatables/tcg/multi_item_set.iff";
                        String multiItemSetName = getStringObjVar(self, "multi_item_set_name");
                        String setItemsString = dataTableGetString(datatable, multiItemSetName, "set_items");
                        if (setItemsString != null && setItemsString.length() > 0)
                        {
                            String[] setItems = split(setItemsString, ',');
                            if (setItems != null && setItems.length > 0)
                            {
                                obj_id[] lootItems = new obj_id[setItems.length];
                                for (int i = 0; i < setItems.length; i++)
                                {
                                    obj_id lootItem = static_item.createNewItemFunction(setItems[i], player);
                                    if (isIdValid(lootItem))
                                    {
                                        lootItems[i] = lootItem;
                                    }
                                }
                                showLootBox(player, lootItems);
                                sendSystemMessage(player, new string_id("npe", "opened_crate"));
                                destroyObject(self);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
