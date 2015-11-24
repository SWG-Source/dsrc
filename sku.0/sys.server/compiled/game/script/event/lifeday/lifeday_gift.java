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
import java.util.HashSet;

public class lifeday_gift extends script.base_script
{
    public lifeday_gift()
    {
    }
    public static final string_id CRATE_USED = new string_id("spam", "opened_crate");
    public static final string_id GIVE_AWAY = new string_id("spam", "give_away");
    public static final string_id OUTSIDE = new string_id("spam", "must_be_outside");
    public static final string_id MOUNTED = new string_id("spam", "mounted");
    public static final string_id UNABLE = new string_id("spam", "no_data");
    public static final String LIFEDAY_TABLE = "datatables/event/lifeday/lifeday_gift.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 5, true))
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
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
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
            String[] itemNames = new String[1];
            dictionary giftData = new dictionary();
            String year = getStringObjVar(self, "year");
            if (year == null || year.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            giftData = dataTableGetRow(LIFEDAY_TABLE, year);
            if (giftData == null)
            {
                sendSystemMessage(player, UNABLE);
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(self, utils.LIFEDAY_OWNER))
            {
                obj_id owner = getObjIdObjVar(self, utils.LIFEDAY_OWNER);
                if (owner != player)
                {
                    String giftsOtherUnparsed = giftData.getString("gift_other");
                    String[] giftsOther = split(giftsOtherUnparsed, ',');
                    itemNames[0] = giftsOther[rand(0, giftsOther.length - 1)];
                    int grantAllGiftsOther = giftData.getInt("grant_all_other");
                    if (grantAllGiftsOther != 0)
                    {
                        itemNames = giftsOther;
                    }
                    if (grantReward(player, itemNames, giftData))
                    {
                        destroyObject(self);
                        sendSystemMessage(player, CRATE_USED);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, GIVE_AWAY);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                String giftsSelfUnparsed = giftData.getString("gift_self");
                String[] giftsSelf = split(giftsSelfUnparsed, ',');
                itemNames[0] = giftsSelf[rand(0, giftsSelf.length - 1)];
                int grantAllGiftsSelf = giftData.getInt("grant_all_self");
                if (grantAllGiftsSelf != 0)
                {
                    itemNames = giftsSelf;
                }
                if (grantReward(player, itemNames, giftData))
                {
                    destroyObject(self);
                    sendSystemMessage(player, CRATE_USED);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean grantReward(obj_id player, String[] itemNames, dictionary giftData) throws InterruptedException
    {
        obj_id mount = getMountId(player);
        if (isIdValid(mount))
        {
            sendSystemMessage(player, MOUNTED);
            return false;
        }
        if (getTopMostContainer(player) != player)
        {
            sendSystemMessage(player, OUTSIDE);
            return false;
        }
        if (giftData == null)
        {
            return false;
        }
        if (isIdValid(player))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (isIdValid(pInv) && (itemNames != null && itemNames.length > 0))
            {
                HashSet theSet = new HashSet();
                for (int i = 0; i < itemNames.length; i++)
                {
                    theSet.add(static_item.createNewItemFunction(itemNames[i], pInv));
                }
                obj_id[] gift = new obj_id[theSet.size()];
                theSet.toArray(gift);
                showLootBox(player, gift);
                playMusic(player, giftData.getString("music"));
                playClientEffectObj(player, giftData.getString("client_effect"), player, "");
                return true;
            }
        }
        return false;
    }
}
