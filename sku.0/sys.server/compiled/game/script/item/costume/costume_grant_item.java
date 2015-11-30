package script.item.costume;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

public class costume_grant_item extends script.base_script
{
    public costume_grant_item()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("spam", "costume_learn"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIncapacitated(player) || isDead(player))
            {
                sendSystemMessage(player, new string_id("player_structure", "while_dead"));
                return SCRIPT_CONTINUE;
            }
            String itemName = getStaticItemName(self);
            if (itemName == null || itemName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary itemData = new dictionary();
            itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
            if (itemData == null)
            {
                return SCRIPT_CONTINUE;
            }
            String costumeName = itemData.getString("buff_name");
            if (hasSkill(player, costumeName))
            {
                sendSystemMessage(player, new string_id("spam", "costume_already_known"));
            }
            else 
            {
                grantSkill(player, costumeName);
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        String itemName = getStaticItemName(self);
        if (itemName == null || itemName.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary itemData = new dictionary();
        itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
        if (itemData == null || itemData.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "storyteller_costume_already_known";
        String costumeName = itemData.getString("buff_name");
        if (hasSkill(player, costumeName))
        {
            attribs[idx] = " \\#B22222 Already Known";
        }
        else 
        {
            attribs[idx] = " \\#00FF00 Not Yet Known";
        }
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
