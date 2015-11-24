package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.sui;
import script.library.utils;
import script.library.static_item;

public class consume_prereq_met extends script.base_script
{
    public consume_prereq_met()
    {
    }
    public static final String PID_NAME = "collectionConsume";
    public static final String SCRIPTVAR_LIST = "collections.availableCollections";
    public static final string_id SID_CONSUME_PROMPT = new string_id("collection", "consume_item_prompt");
    public static final string_id SID_CONSUME_TITLE = new string_id("collection", "consume_item_title");
    public static final string_id SID_CONSUME_ITEM = new string_id("collection", "consume_item");
    public static final string_id SID_REPORT_CONSUME_ITEM_FAIL = new string_id("collection", "report_consume_item_fail");
    public static final string_id SID_ALREADY_HAVE_SLOT = new string_id("collection", "already_have_slot");
    public static final string_id SID_ALREADY_FINISHED_COLLECTION = new string_id("collection", "already_finished_collection");
    public static final string_id STR_COLLECTION_LIST_PROMPT = new string_id("collection", "collection_list_prompt");
    public static final string_id STR_COLLECTION_LIST_TITLE = new string_id("collection", "collection_list_title");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id collectionItem = self;
        if (hasObjVar(collectionItem, static_item.ORIG_OWNER))
        {
            if (static_item.userIsOrigOwner(collectionItem, player))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (utils.isNestedWithinAPlayer(collectionItem))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_CONSUME_ITEM);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (hasObjVar(self, static_item.ORIG_OWNER))
        {
            if (static_item.userIsOrigOwner(self, player))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (item == menu_info_types.SERVER_MENU1 && utils.isNestedWithinAPlayer(self))
        {
            if (sui.hasPid(player, PID_NAME))
            {
                int pid = sui.getPid(player, PID_NAME);
                forceCloseSUIPage(pid);
            }
            String pageName = getStringObjVar(self, collection.OBJVAR_PAGE_NAME);
            String[] slotNames = getAllCollectionSlotsInCategoryInPage(pageName, collection.CATEGORY_PREREQ_MET);
            if (slotNames == null || slotNames.length <= 0)
            {
                return SCRIPT_CONTINUE;
            }
            boolean hasCompletedAll = true;
            for (int i = 0; i < slotNames.length; ++i)
            {
                if (hasCompletedCollectionSlotPrereq(player, slotNames[i]) && !hasCompletedCollectionSlot(player, slotNames[i]))
                {
                    hasCompletedAll = false;
                    int pid = sui.msgbox(self, player, "@" + SID_CONSUME_PROMPT, sui.YES_NO, "@" + SID_CONSUME_TITLE, "handlerSuiAddToCollection");
                    sui.setPid(player, pid, PID_NAME);
                    utils.setScriptVar(player, "collection.slotName", slotNames[i]);
                    return SCRIPT_CONTINUE;
                }
            }
            if (hasCompletedAll)
            {
                sendSystemMessage(player, SID_ALREADY_FINISHED_COLLECTION);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiAddToCollection(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id collectionItem = self;
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            sui.removePid(player, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(player, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        String slotName = utils.getStringScriptVar(player, "collection.slotName");
        utils.removeScriptVarTree(player, "collection");
        String[] slotInfo = getCollectionSlotInfo(slotName);
        String collectionName = slotInfo[COLLECTION_INFO_INDEX_COLLECTION];
        if (!hasCompletedCollection(player, collectionName))
        {
            if (!hasCompletedCollectionSlot(player, slotName) && !slotName.equals(""))
            {
                if (modifyCollectionSlotValue(player, slotName, 1))
                {
                    CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was consumed into a collection, for player " + getFirstName(player) + "(" + player + "). collectionItem(" + collectionItem + ") will now be destroyed.");
                    destroyObject(collectionItem);
                }
                else 
                {
                    CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was NOT consumed into a collection, for player " + getFirstName(player) + "(" + player + "). collectionItem(" + collectionItem + ") will NOT be destroyed.");
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
        else 
        {
            sendSystemMessage(player, SID_ALREADY_FINISHED_COLLECTION);
        }
        sui.removePid(player, PID_NAME);
        return SCRIPT_CONTINUE;
    }
}
