package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class no_consume_title_grant extends script.base_script
{
    public no_consume_title_grant()
    {
    }
    public static final String PID_NAME = "collectionConsume";
    public static final String SCRIPT_VAR_NUM_ALT_TITLE = "collection.numAltTitle";
    public static final String OBJVAR_TITLE_GRANTED = "titleGrantedTo";
    public static final string_id SID_CONSUME_PROMPT = new string_id("collection", "non_consume_title_prompt");
    public static final string_id SID_CONSUME_TITLE = new string_id("collection", "non_consume_title_title");
    public static final string_id SID_CONSUME_ITEM = new string_id("collection", "non_consume_title_use");
    public static final string_id SID_REPORT_CONSUME_ITEM_FAIL = new string_id("collection", "report_consume_item_fail");
    public static final string_id SID_ALREADY_HAVE_SLOT = new string_id("collection", "already_have_slot");
    public static final string_id COLLECTION_TITLE_GRANTED = new string_id("collection", "collection_title_granted");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkAltTitleCount", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPT_VAR_NUM_ALT_TITLE))
        {
            messageTo(self, "checkAltTitleCount", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id collectionItem = self;
        if (utils.isNestedWithinAPlayer(collectionItem))
        {
            String slotName = getStringObjVar(self, collection.OBJVAR_SLOT_NAME);
            if (!hasCompletedCollectionSlot(player, slotName) && !hasObjVar(collectionItem, OBJVAR_TITLE_GRANTED))
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
            if (!hasCompletedCollectionSlot(player, slotName) && !hasObjVar(collectionItem, OBJVAR_TITLE_GRANTED))
            {
                int pid = sui.msgbox(self, player, "@" + SID_CONSUME_PROMPT, sui.YES_NO, "@" + SID_CONSUME_TITLE, "handlerSuiAddTitle");
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
        names[idx] = "title_granted";
        attribs[idx] = "@collection_title:" + slotName;
        idx++;
        int numAltTitles = utils.getIntScriptVar(self, SCRIPT_VAR_NUM_ALT_TITLE);
        for (int i = 1; i < (numAltTitles + 1); ++i)
        {
            names[idx] = "title_granted";
            attribs[idx] = "@collection_title:" + slotName + "_alt_" + i;
            idx++;
        }
        if (hasObjVar(self, OBJVAR_TITLE_GRANTED))
        {
            names[idx] = "player_title_granted";
            attribs[idx] = getPlayerName(getObjIdObjVar(self, OBJVAR_TITLE_GRANTED));
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int checkAltTitleCount(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, collection.OBJVAR_SLOT_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, SCRIPT_VAR_NUM_ALT_TITLE))
        {
            return SCRIPT_CONTINUE;
        }
        String slotName = getStringObjVar(self, collection.OBJVAR_SLOT_NAME);
        String[] catagories = getCollectionSlotCategoryInfo(slotName);
        if (catagories != null && catagories.length > 0)
        {
            for (int i = 0; i < catagories.length; ++i)
            {
                if (catagories[i].indexOf(collection.CATEGORY_NUM_ALT_TITLES) > -1)
                {
                    String[] splitAltTitles = split(catagories[i], ':');
                    if (splitAltTitles == null || splitAltTitles.length != 2)
                    {
                        continue;
                    }
                    int numAltTitles = utils.stringToInt(splitAltTitles[1]);
                    utils.setScriptVar(self, SCRIPT_VAR_NUM_ALT_TITLE, numAltTitles);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerSuiAddTitle(obj_id self, dictionary params) throws InterruptedException
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
            if (modifyCollectionSlotValue(player, slotName, 1))
            {
                setObjVar(collectionItem, OBJVAR_TITLE_GRANTED, player);
                CustomerServiceLog("CollectionConsume: ", "titleGrantItem (" + collectionItem + ")" + " was used to grant a title, for player " + getFirstName(player) + "(" + player + "). Item now has the objvar " + OBJVAR_TITLE_GRANTED + " with the value of the player's ID.");
                prose_package pp = new prose_package();
                prose.setStringId(pp, COLLECTION_TITLE_GRANTED);
                prose.setTU(pp, "@collection_title:" + slotName);
                sendSystemMessageProse(player, pp);
                int numAltTitles = utils.getIntScriptVar(self, SCRIPT_VAR_NUM_ALT_TITLE);
                for (int i = 1; i < (numAltTitles + 1); ++i)
                {
                    prose.setTU(pp, utils.unpackString("@collection_title:" + slotName + "_alt_" + i));
                    sendSystemMessageProse(player, pp);
                }
            }
            else 
            {
                CustomerServiceLog("CollectionConsume: ", "titleGrantItem (" + collectionItem + ")" + " was NOT used to grant a title, for player " + getFirstName(player) + "(" + player + ").");
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
