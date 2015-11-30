package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class clothing_dispenser extends script.base_script
{
    public clothing_dispenser()
    {
    }
    public static final String DATATABLE_INVENTORY = "datatables/dispenser/clothing.iff";
    public static final String SCRIPT_DISPENSER_SELECT = "player.player_dispenser";
    public static final string_id SID_PURCHASE_CLOTHING = new string_id("dispenser", "purchase_clothing");
    public static final string_id SID_NOTHING_TO_BUY = new string_id("dispenser", "nothing_to_buy");
    public static final String VAR_DISPENSER = "dispenser.dispenser";
    public static final String VAR_DATATABLE = "dispenser.datatable";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (data != null)
        {
            data.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_PURCHASE_CLOTHING);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String[] inventory = getDispenserInventory(self);
            if (inventory == null)
            {
                sendSystemMessage(player, SID_NOTHING_TO_BUY);
            }
            else 
            {
                sui.listbox(player, player, "@dispenser:select_purchase", sui.OK_CANCEL, "@dispenser:clothing_dispenser", inventory, "msgDispenserPurchaseSelected");
                setObjVar(player, VAR_DISPENSER, self);
                setObjVar(player, VAR_DATATABLE, DATATABLE_INVENTORY);
                if (!hasScript(player, SCRIPT_DISPENSER_SELECT))
                {
                    attachScript(player, SCRIPT_DISPENSER_SELECT);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public String[] getDispenserInventory(obj_id dispenser) throws InterruptedException
    {
        if (dispenser == null || dispenser == obj_id.NULL_ID)
        {
            return null;
        }
        int num_items = dataTableGetNumRows(DATATABLE_INVENTORY);
        String[] inventory = new String[num_items];
        for (int i = 0; i < num_items; i++)
        {
            inventory[i] = dataTableGetString(DATATABLE_INVENTORY, i, "name");
        }
        if (inventory.length < 1)
        {
            return null;
        }
        else 
        {
            return inventory;
        }
    }
}
