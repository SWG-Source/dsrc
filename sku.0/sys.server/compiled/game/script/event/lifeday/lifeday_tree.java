package script.event.lifeday;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;
import script.library.badge;

public class lifeday_tree extends script.base_script
{
    public lifeday_tree()
    {
    }
    public static final string_id TREE_USE = new string_id("spam", "tree_use");
    public static final string_id NOT_OLD_ENOUGH = new string_id("spam", "not_old_enough");
    public static final string_id GIFT_GRANTED = new string_id("spam", "gift_granted");
    public static final String GIFT_SELF = "item_lifeday_gift_self_01_04";
    public static final String GIFT_OTHER = "item_lifeday_gift_other_01_04";
    public static final String LIFEDAY_BADGE = "lifeday_badge_09";
    public static final string_id TREE_BADGE = new string_id("spam", "tree_badge");
    public String currentYearObjVar() throws InterruptedException
    {
        return utils.XMAS_RECEIVED_IX_01;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String config = getConfigSetting("GameServer", "grantGift");
        if (config != null)
        {
            if (config.equals("false"))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (!hasObjVar(player, currentYearObjVar()))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, TREE_USE);
        }
        if (!hasCompletedCollectionSlot(player, LIFEDAY_BADGE))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, TREE_BADGE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!isGod(player))
            {
                if ((getCurrentBirthDate() - getPlayerBirthDate(player)) < 10)
                {
                    sendSystemMessage(player, NOT_OLD_ENOUGH);
                    return SCRIPT_CONTINUE;
                }
            }
            if (!hasObjVar(player, currentYearObjVar()))
            {
                grantGift(player);
            }
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            badge.grantBadge(player, LIFEDAY_BADGE);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean grantGift(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id inv = utils.getInventoryContainer(player);
        obj_id giftSelf = static_item.createNewItemFunction(GIFT_SELF, inv);
        obj_id giftOther = static_item.createNewItemFunction(GIFT_OTHER, inv);
        setObjVar(giftOther, utils.LIFEDAY_OWNER, player);
        utils.sendMail(utils.GIFT_GRANTED_SUB, utils.GIFT_GRANTED, player, "System");
        setObjVar(player, currentYearObjVar(), 1);
        sendSystemMessage(player, GIFT_GRANTED);
        CustomerServiceLog("grantGift", getFirstName(player) + "(" + player + ") has received his Christmas '09 gift.");
        return true;
    }
}
