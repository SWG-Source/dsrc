package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.static_item;
import script.library.sui;
import script.library.utils;

public class meatlump_hotdog_grill extends script.base_script
{
    public meatlump_hotdog_grill()
    {
    }
    public static final string_id SID_GET_FOOD_ITEM = new string_id("collection", "get_food_item");
    public static final string_id SID_MTP_ALREADY_ATE = new string_id("collection", "already_ate");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id MUST_DISMOUNT = new string_id("collection", "must_dismount");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_GET_FOOD_ITEM);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        String itemToGrant = "";
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(player, "mtp_already_ate"))
        {
            sendSystemMessage(player, SID_MTP_ALREADY_ATE);
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        if (getState(player, STATE_COMBAT) > 0)
        {
            sendSystemMessage(player, SID_NOT_WHILE_IN_COMBAT);
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_RIDING_MOUNT) == 1)
        {
            sendSystemMessage(player, MUST_DISMOUNT);
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, NOT_WHILE_INCAPPED);
            return SCRIPT_CONTINUE;
        }
        int myRoll = rand(1, 500);
        if (myRoll == 1)
        {
            itemToGrant = "item_mtp_meatlump_lump_schematic_02_01";
            grantMeatlumpItem(player, itemToGrant);
        }
        else 
        {
            itemToGrant = "item_mtp_meatlump_lump_02_01";
            grantMeatlumpItem(player, itemToGrant);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean grantMeatlumpItem(obj_id player, String itemToGrant) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (itemToGrant == null || itemToGrant.equals(""))
        {
            return false;
        }
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id itemId = static_item.createNewItemFunction(itemToGrant, pInv);
        if (!exists(itemId) || !isIdValid(itemId))
        {
        }
        buff.applyBuff(player, "mtp_already_ate");
        return true;
    }
}
