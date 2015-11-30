package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.collection;
import script.library.stealth;
import script.library.sui;
import script.library.utils;

public class listening_device extends script.base_script
{
    public listening_device()
    {
    }
    public static final string_id SID_USE_DEVICE = new string_id("collection", "use_device");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id MUST_DISMOUNT = new string_id("collection", "must_dismount");
    public static final string_id SID_NEED_TO_ACTIVATE_COLLECTION = new string_id("collection", "need_to_activate_collection");
    public static final String EAVESDROP_COLLECTION_NAME = "eavesdrop_location_";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listening_device = self;
        if (utils.isNestedWithinAPlayer(listening_device))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_DEVICE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendDirtyObjectMenuNotification(self);
        obj_id listening_device = self;
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
        if (utils.hasScriptVar(listening_device, "device_active"))
        {
            placeListeningDevice(player, listening_device);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean placeListeningDevice(obj_id player, obj_id listening_device) throws InterruptedException
    {
        int triggerLocation = 0;
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(listening_device) || !exists(listening_device))
        {
            return false;
        }
        if (!utils.hasScriptVar(listening_device, "device_active"))
        {
            return false;
        }
        if (utils.hasScriptVar(listening_device, "device_location"))
        {
            triggerLocation = utils.getIntScriptVar(listening_device, "device_location");
            updateCollection(player, triggerLocation);
            return true;
        }
        return false;
    }
    public boolean updateCollection(obj_id player, int triggerLocation) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        String slotToUpdate = EAVESDROP_COLLECTION_NAME + triggerLocation;
        if (!hasCompletedCollectionSlot(player, slotToUpdate))
        {
            modifyCollectionSlotValue(player, slotToUpdate, 1);
            return true;
        }
        else 
        {
            sendSystemMessage(player, SID_NEED_TO_ACTIVATE_COLLECTION);
            return false;
        }
    }
}
