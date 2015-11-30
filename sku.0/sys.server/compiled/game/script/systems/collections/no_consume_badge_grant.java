package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.collection;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class no_consume_badge_grant extends script.base_script
{
    public no_consume_badge_grant()
    {
    }
    public static final String PID_NAME = "collectionConsume";
    public static final String OBJVAR_BADGE_GRANTED = "badgeGrantedTo";
    public static final string_id SID_CONSUME_PROMPT = new string_id("collection", "non_consume_badge_prompt");
    public static final string_id SID_CONSUME_TITLE = new string_id("collection", "non_consume_badge_title");
    public static final string_id SID_CONSUME_ITEM = new string_id("collection", "non_consume_badge_use");
    public static final string_id SID_REPORT_CONSUME_ITEM_FAIL = new string_id("collection", "report_consume_item_fail");
    public static final string_id SID_ALREADY_HAVE_SLOT = new string_id("collection", "already_have_slot");
    public static final string_id SID_ALREADY_FINISHED_COLLECTION = new string_id("collection", "already_finished_collection");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id collectionItem = self;
        if (utils.isNestedWithinAPlayer(collectionItem))
        {
            String slotName = getStringObjVar(self, collection.OBJVAR_SLOT_NAME);
            if (!hasCompletedCollectionSlot(player, slotName) && !hasObjVar(collectionItem, OBJVAR_BADGE_GRANTED))
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_CONSUME_ITEM);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        obj_id collectionItem = self;
        if (item == menu_info_types.SERVER_MENU3 && utils.isNestedWithinAPlayer(collectionItem))
        {
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            String slotName = getStringObjVar(self, collection.OBJVAR_SLOT_NAME);
            if (!hasCompletedCollectionSlot(player, slotName) && !hasObjVar(collectionItem, OBJVAR_BADGE_GRANTED))
            {
                int pid = sui.msgbox(self, player, "@" + SID_CONSUME_PROMPT, sui.YES_NO, "@" + SID_CONSUME_TITLE, "handlerSuiAddBadge");
                sui.setPid(player, pid, PID_NAME);
                return SCRIPT_CONTINUE;
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
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, collection.OBJVAR_SLOT_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        String slotName = getStringObjVar(self, collection.OBJVAR_SLOT_NAME);
        names[idx] = "badge_granted";
        attribs[idx] = "@collection_n:" + slotName;
        idx++;
        if (hasObjVar(self, OBJVAR_BADGE_GRANTED))
        {
            names[idx] = "player_badge_granted";
            attribs[idx] = getPlayerName(getObjIdObjVar(self, OBJVAR_BADGE_GRANTED));
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiAddBadge(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id collectionItem = self;
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sui.removePid(player, PID_NAME);
            CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was NOT consumed into a collection, for player " + getFirstName(player) + "(" + player + "). collectionItem(" + collectionItem + "). Player attempted to collect an item that was not in their inventory.");
            return SCRIPT_CONTINUE;
        }
        String slotName = getStringObjVar(collectionItem, collection.OBJVAR_SLOT_NAME);
        if (!hasCompletedCollectionSlot(player, slotName) && !slotName.equals(""))
        {
            if (badge.grantBadge(player, slotName))
            {
                setObjVar(collectionItem, OBJVAR_BADGE_GRANTED, player);
                CustomerServiceLog("CollectionConsume: ", "BadgeGrantItem (" + collectionItem + ")" + " was used to grant a badge, for player " + getFirstName(player) + "(" + player + "). Item now has the objvar " + OBJVAR_BADGE_GRANTED + " with the value of the player's ID.");
            }
            else 
            {
                CustomerServiceLog("CollectionConsume: ", "BadgeGrantItem (" + collectionItem + ")" + " was NOT used to grant a badge, for player " + getFirstName(player) + "(" + player + ").");
                sendSystemMessage(player, SID_REPORT_CONSUME_ITEM_FAIL);
            }
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, SID_ALREADY_HAVE_SLOT);
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
    }
}
