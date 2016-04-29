package script.event.lifeday;

import script.library.static_item;
import script.library.utils;
import script.*;

import java.util.HashSet;

public class lifeday_gift_06 extends script.base_script
{
    public lifeday_gift_06()
    {
    }
    private static final string_id CRATE_USED = new string_id("spam", "opened_crate");
    private static final string_id GIVE_AWAY = new string_id("spam", "give_away");
    private static final string_id OUTSIDE = new string_id("spam", "must_be_outside");
    private static final String[] GIFTS_SELF =
    {
        "item_lifeday_wreath_01_01",
        "item_lifeday_mini_tree_01_01"
    };
    private static final String[] GIFTS_RANDOM =
    {
        "item_lifeday_bunting_01_01",
        "item_lifeday_incense_burner_01_01",
        "item_lifeday_painting_01_01_01",
        "item_lifeday_painting_02_01_01",
        "item_lifeday_painting_03_01_01",
        "item_lifeday_painting_04_01_01",
        "item_lifeday_painting_05_01_01"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "event.lifeday.lifeday_gift_06"))
        {
            attachScript(self, "event.lifeday.lifeday_gift");
            detachScript(self, "event.lifeday.lifeday_gift_06");
        }
        return SCRIPT_CONTINUE;
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
            if (hasObjVar(self, "lifeday.other"))
            {
                obj_id owner = getObjIdObjVar(self, "lifeday.owner");
                if (owner != player)
                {
                    String[] items = new String[1];
                    items[0] = GIFTS_RANDOM[rand(0, GIFTS_RANDOM.length - 1)];
                    if (grantReward(player, items))
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
                String[] items = new String[(GIFTS_SELF.length + 1)];
                System.arraycopy(GIFTS_SELF, 0, items, 0, GIFTS_SELF.length);
                items[GIFTS_SELF.length] = GIFTS_RANDOM[rand(0, GIFTS_RANDOM.length - 1)];
                if (grantReward(player, items))
                {
                    destroyObject(self);
                    sendSystemMessage(player, CRATE_USED);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean grantReward(obj_id player, String[] items) throws InterruptedException
    {
        if (getTopMostContainer(player) != player)
        {
            sendSystemMessage(player, OUTSIDE);
            return false;
        }
        if (isIdValid(player))
        {
            obj_id pInv = utils.getInventoryContainer(player);
            if (isIdValid(pInv) && (items != null && items.length > 0))
            {
                HashSet theSet = new HashSet();
                for (String item : items) {
                    theSet.add(static_item.createNewItemFunction(item, pInv));
                }
                obj_id[] gift = new obj_id[theSet.size()];
                theSet.toArray(gift);
                showLootBox(player, gift);
                playMusic(player, "sound/music_celebration_a.snd");
                playClientEffectObj(player, "clienteffect/pt_lifeday.cef", player, "");
                return true;
            }
        }
        return false;
    }
}
