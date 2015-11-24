package script.player;

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
import script.library.stealth;
import script.library.sui;
import script.library.utils;
import script.library.trial;

public class player_collection extends script.base_script
{
    public player_collection()
    {
    }
    public static final string_id SID_SLOT_ADDED = new string_id("collection", "player_slot_added");
    public static final string_id SID_HIDDEN_SLOT = new string_id("collection", "player_hidden_slot_added");
    public static final string_id SID_COLLECTION_COMPLETE = new string_id("collection", "player_collection_complete");
    public static final string_id SID_SERVER_FIRST = new string_id("collection", "player_server_first");
    public static final String SERVER_FIRST_SLOT = "bdg_server_first_01";
    public static final String VAR_ACCESS_DELAY = "delay.access_delay";
    public static final string_id SID_REPORT_CONSUME_ITEM_FAIL = new string_id("collection", "report_consume_item_fail");
    public static final string_id COUNTDOWN_TIMER = new string_id("collection", "click_countdown_timer");
    public static final string_id SID_COUNTDOWN_LOCOMOTION = new string_id("quest/groundquests", "countdown_interrupted_locomotion");
    public static final string_id SID_INTERRUPTED_INCAPACITATED = new string_id("quest/groundquests", "countdown_interrupted_incapacitated");
    public static final string_id SID_INTERRUPTED_DAMAGED = new string_id("quest/groundquests", "countdown_interrupted_damaged");
    public static final string_id SID_INVIS_COLLECTION_FAIL = new string_id("collection", "invis_collection_failed");
    public static final int GCW_INSURGENT_LOCKOUT = 3600;
    public int OnCollectionSlotModified(obj_id self, String bookName, String pageName, String collectionName, String slotName, boolean isCounterTypeSlot, int previousValue, int currentValue, int maxSlotValue, boolean slotCompleted) throws InterruptedException
    {
        if (bookName.equals(badge.BADGE_BOOK))
        {
            return SCRIPT_CONTINUE;
        }
        String[] collectionSlots = getAllCollectionSlotsInCollection(collectionName);
        if (collectionSlots == null || collectionSlots.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        boolean newCollection = true;
        boolean canResetCollection = false;
        prose_package pp = new prose_package();
        for (int i = 0; i < collectionSlots.length; ++i)
        {
            if (!collectionSlots[i].equals(slotName))
            {
                long value = getCollectionSlotValue(self, collectionSlots[i]);
                if (value > 0)
                {
                    newCollection = false;
                    break;
                }
            }
        }
        int row = dataTableSearchColumnForString(slotName, "slotName", collection.COLLECTION_TABLE);
        if (row < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int isHidden = dataTableGetInt(collection.COLLECTION_TABLE, row, "hidden");
        if ((newCollection && currentValue == 1) && isHidden == 0)
        {
            prose.setStringId(pp, SID_HIDDEN_SLOT);
            prose.setTU(pp, new string_id("collection_n", slotName));
            prose.setTO(pp, new string_id("collection_n", collectionName));
            sendSystemMessageProse(self, pp);
            play2dNonLoopingSound(self, "sound/utinni.snd");
        }
        String[] catagories = getCollectionSlotCategoryInfo(slotName);
        if (catagories != null && catagories.length > 0)
        {
            for (int i = 0; i < catagories.length; ++i)
            {
                if (catagories[i].equals(collection.REWARD_ON_UPDATE_CATEGORY))
                {
                    collection.grantCollectionReward(self, slotName, false);
                }
                if (catagories[i].startsWith(collection.CATEGORY_UPDATE_ON_COUNT))
                {
                    String[] splitUpdateCount = split(catagories[i], ':');
                    if (splitUpdateCount != null && splitUpdateCount.length > 0)
                    {
                        int countToUpdateAt = utils.stringToInt(splitUpdateCount[1]);
                        if ((countToUpdateAt == currentValue) && isCounterTypeSlot)
                        {
                            collection.grantCollectionReward(self, slotName + ":" + splitUpdateCount[1], false);
                        }
                    }
                }
            }
        }
        if (slotCompleted)
        {
            boolean giveMessage = true;
            if (catagories != null && catagories.length > 0)
            {
                for (int i = 0; i < catagories.length; ++i)
                {
                    if (catagories[i].equals(collection.REWARD_ON_COMPLETE_CATEGORY))
                    {
                        collection.grantCollectionReward(self, slotName, false);
                    }
                    if (catagories[i].equals(collection.NO_MESSAGE_CATEGORY))
                    {
                        giveMessage = false;
                    }
                    if (bookName.equals("saga_relic_book"))
                    {
                        giveMessage = false;
                    }
                    if (catagories[i].equals(collection.CLEAR_ON_COMPLETE) && hasCompletedCollection(self, collectionName))
                    {
                        canResetCollection = true;
                    }
                }
            }
            if (isHidden > 0 && giveMessage)
            {
                prose.setStringId(pp, SID_HIDDEN_SLOT);
                prose.setTU(pp, new string_id("collection_n", slotName));
                prose.setTO(pp, new string_id("collection_n", collectionName));
                sendSystemMessageProse(self, pp);
                play2dNonLoopingSound(self, "sound/utinni.snd");
            }
            else if (giveMessage)
            {
                prose.setStringId(pp, SID_SLOT_ADDED);
                prose.setTU(pp, new string_id("collection_n", slotName));
                prose.setTO(pp, new string_id("collection_n", collectionName));
                sendSystemMessageProse(self, pp);
                play2dNonLoopingSound(self, "sound/utinni.snd");
            }
            if (hasCompletedCollection(self, collectionName))
            {
                int rowNum = dataTableSearchColumnForString(collectionName, "collectionName", collection.COLLECTION_TABLE);
                dictionary dd = dataTableGetRow(collection.COLLECTION_TABLE, rowNum);
                int giveCompletionMessage = dd.getInt("hidden");
                if (giveCompletionMessage == 0)
                {
                    prose.setTO(pp, new string_id("collection_n", collectionName));
                    prose.setStringId(pp, SID_COLLECTION_COMPLETE);
                    sendSystemMessageProse(self, pp);
                    collection.grantCollectionReward(self, collectionName, canResetCollection);
                }
                else 
                {
                    collection.grantCollectionReward(self, collectionName, canResetCollection);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCollectionServerFirst(obj_id self, String bookName, String pageName, String collectionName) throws InterruptedException
    {
        prose_package pp = new prose_package();
        prose.setStringId(pp, SID_SERVER_FIRST);
        prose.setTO(pp, new string_id("collection_n", collectionName));
        prose.setTT(pp, getClusterName());
        sendSystemMessageProse(self, pp);
        if (!badge.hasBadge(self, SERVER_FIRST_SLOT))
        {
            badge.grantBadge(self, SERVER_FIRST_SLOT);
        }
        return SCRIPT_CONTINUE;
    }
    public int modifySlot(obj_id self, dictionary params) throws InterruptedException
    {
        int pid = params.getInt("id");
        int test_pid = getIntObjVar(self, sui.COUNTDOWNTIMER_SUI_VAR);
        if (pid != test_pid)
        {
            utils.removeScriptVarTree(self, "col");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            LOG("collection", "Player PID Var wrong. Aborting. ");
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(self, collection.CONSUME_PID_NAME))
        {
            utils.removeScriptVarTree(self, "col");
            LOG("collection", "Player PID Var wrong. Aborting. ");
            return SCRIPT_CONTINUE;
        }
        test_pid = sui.getPid(self, collection.CONSUME_PID_NAME);
        if (test_pid != pid)
        {
            utils.removeScriptVarTree(self, "col");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            LOG("collection", "Player PID Var wrong. Aborting. ");
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVarTree(self, "col"))
        {
            LOG("collection", "Player is missing col scriptvar tree. we are now aborting");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            return SCRIPT_CONTINUE;
        }
        obj_id collectionItem = utils.getObjIdScriptVar(self, "col.collectionItem");
        String slotName = utils.getStringScriptVar(self, "col.slotName");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            utils.removeScriptVarTree(self, "col");
            LOG("collection", "Button Press Cancel - Player too far away. Aborting. ");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            utils.setScriptVar(self, VAR_ACCESS_DELAY, getGameTime());
            return SCRIPT_CONTINUE;
        }
        else if (bp == sui.BP_REVERT)
        {
            int event = params.getInt("event");
            if (event == sui.CD_EVENT_LOCOMOTION)
            {
                sendSystemMessage(self, SID_COUNTDOWN_LOCOMOTION);
            }
            else if (event == sui.CD_EVENT_INCAPACITATE)
            {
                sendSystemMessage(self, SID_INTERRUPTED_INCAPACITATED);
            }
            else if (event == sui.CD_EVENT_DAMAGED || event == sui.CD_EVENT_COMBAT)
            {
                sendSystemMessage(self, SID_INTERRUPTED_DAMAGED);
            }
            LOG("collection", "Button Press Revert - Aborting. ");
            utils.removeScriptVarTree(self, "col");
            utils.setScriptVar(self, VAR_ACCESS_DELAY, getGameTime());
            sui.removePid(self, collection.CONSUME_PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, sui.COUNTDOWNTIMER_SUI_VAR))
        {
            utils.removeScriptVarTree(self, "col");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            LOG("collection", "Player has no COUNTDOWNTIMER_SUI_VAR. Aborting. ");
            return SCRIPT_CONTINUE;
        }
        LOG("collection", "Button Press not cancel or revert.  Player passed distance check.");
        if (stealth.hasInvisibleBuff(self))
        {
            sendSystemMessage(self, SID_INVIS_COLLECTION_FAIL);
            forceCloseSUIPage(pid);
            utils.removeScriptVarTree(self, "col");
            sui.removePid(self, collection.CONSUME_PID_NAME);
            LOG("collection", "Player Invisible. Aborting. ");
            return SCRIPT_CONTINUE;
        }
        LOG("collection", "Force Close the SUI.");
        forceCloseSUIPage(pid);
        if (modifyCollectionSlotValue(self, slotName, 1))
        {
            CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was consumed into a collection, for player " + getFirstName(self) + "(" + self + ").");
            if (utils.hasScriptVar(self, "col.isGCW"))
            {
                utils.setScriptVar(collectionItem, "collection.gcw_lockout_time", "1");
                messageTo(collectionItem, "removeGcwCollectionLockout", null, GCW_INSURGENT_LOCKOUT, false);
            }
            if (hasObjVar(collectionItem, "spawn_id"))
            {
                String collectedId = getStringObjVar(collectionItem, "spawn_id");
                obj_id collectedParent = trial.getParent(collectionItem);
                dictionary dict = trial.getSessionDict(collectedParent);
                dict.put("triggerType", "triggerId");
                dict.put("triggerName", collectedId + "_collected");
                messageTo(collectedParent, "triggerFired", dict, 0.0f, false);
            }
        }
        else 
        {
            CustomerServiceLog("CollectionConsume: ", "collectionItem (" + collectionItem + ")" + " was NOT consumed into a collection, for player " + getFirstName(self) + "(" + self + ").");
            sendSystemMessage(self, SID_REPORT_CONSUME_ITEM_FAIL);
        }
        utils.removeScriptVarTree(self, "col");
        sui.removePid(self, collection.CONSUME_PID_NAME);
        return SCRIPT_CONTINUE;
    }
}
