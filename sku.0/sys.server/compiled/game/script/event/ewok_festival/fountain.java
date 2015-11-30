package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

public class fountain extends script.base_script
{
    public fountain()
    {
    }
    public static final string_id FOUNTAIN_USE = new string_id("spam", "fountain_use");
    public static final string_id ALREADY_HAVE = new string_id("spam", "already_have");
    public static final string_id BERRY_GRANTED = new string_id("spam", "berry_granted");
    public static final String OBJ_BERRY_ONE = "item_event_ewok_berry_01_01";
    public static final String OBJ_BERRY_TWO = "item_event_ewok_berry_01_02";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.playerHasStaticItemInBankOrInventory(player, OBJ_BERRY_ONE))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, FOUNTAIN_USE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE && utils.playerHasStaticItemInBankOrInventory(player, OBJ_BERRY_ONE))
        {
            if (!utils.playerHasStaticItemInBankOrInventory(player, OBJ_BERRY_TWO))
            {
                convertBerry(player);
            }
            else 
            {
                sendSystemMessage(player, ALREADY_HAVE);
            }
            sendDirtyObjectMenuNotification(self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean convertBerry(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id berryOne = utils.getStaticItemInInventory(player, OBJ_BERRY_ONE);
        if (isIdValid(berryOne))
        {
            obj_id berryTwo = static_item.createNewItemFunction(OBJ_BERRY_TWO, inv);
            doAnimationAction(player, "manipulate_medium");
            sendSystemMessage(player, BERRY_GRANTED);
            destroyObject(berryOne);
            return true;
        }
        return false;
    }
}
