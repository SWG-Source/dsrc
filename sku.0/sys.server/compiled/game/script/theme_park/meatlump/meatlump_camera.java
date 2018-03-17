package script.theme_park.meatlump;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.collection;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class meatlump_camera extends script.base_script
{
    public meatlump_camera()
    {
    }
    public static final string_id SID_USE_CAMERA = new string_id("collection", "use_camera");
    public static final string_id SID_INVALID_OBJECT = new string_id("collection", "invalid_camera_target");
    public static final string_id SID_PICTURE_TAKEN = new string_id("collection", "picture_taken");
    public static final string_id SID_INVALID_TARGET = new string_id("collection", "invalid_target");
    public static final string_id SID_SUCCESS_SNAPSHOT = new string_id("collection", "successful_snapshot");
    public static final string_id SID_PHOTO_SLOT_COMPLETE = new string_id("collection", "photo_slot_complete");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id MUST_DISMOUNT = new string_id("collection", "must_dismount");
    public static final string_id SID_NO_DESTROY_YOUR_STUFF = new string_id("collection", "nodestroy_your_stuff");
    public static final String PHOTO_COLLECTION = "col_meatlump_photo_01";
    public static final String HOPPER_OBJVAR = "player_structure.deed.maxHopperSize";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id myCamera = self;
        if (utils.isNestedWithinAPlayer(myCamera))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE_CAMERA);
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
        obj_id myCamera = self;
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
        obj_id intended = getIntendedTarget(player);
        if (!isIdValid(intended))
        {
            sendSystemMessage(player, SID_INVALID_TARGET);
        }
        else 
        {
            checkValidObject(player, intended);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkValidObject(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(target) || !exists(target))
        {
            return false;
        }
        String[] adminList = getStringArrayObjVar(target, player_structure.VAR_ADMIN_LIST);
        if(adminList != null && adminList.length > 0) {
            String str_player_id = "" + player;
            int adminSize = adminList.length;
            for (int q = 0; q < adminSize; q++) {
                if (adminList[q].equals(str_player_id)) {
                    sendSystemMessage(player, SID_NO_DESTROY_YOUR_STUFF);
                    return false;
                }
            }
        }
        if (hasCompletedCollection(player, PHOTO_COLLECTION))
        {
            return false;
        }
        if (!hasObjVar(target, HOPPER_OBJVAR) || hasScript(target, "structure.temporary_structure"))
        {
            sendSystemMessage(player, SID_INVALID_OBJECT);
            return false;
        }
        if (utils.hasScriptVar(target, "collection.picture_taken"))
        {
            sendSystemMessage(player, SID_PICTURE_TAKEN);
            return false;
        }
        String planetName = getCurrentSceneName();
        String slotToModify = "meatlump_photo_" + planetName;
        if (hasCompletedCollectionSlot(player, slotToModify))
        {
            sendSystemMessage(player, SID_PHOTO_SLOT_COMPLETE);
            return false;
        }
        modifyCollectionSlotValue(player, slotToModify, 1);
        tagTheObject(player, target);
        return true;
    }
    public void tagTheObject(obj_id player, obj_id photoTarget) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        if (!isIdValid(photoTarget) || !exists(photoTarget))
        {
            return;
        }
        utils.setScriptVar(photoTarget, "collection.picture_taken", 1);
        dictionary dict = new dictionary();
        dict.put("target", photoTarget);
        messageTo(photoTarget, "clearCollectionMeatlumpCamera", dict, 3600, false);
        sendSystemMessage(player, SID_SUCCESS_SNAPSHOT);
    }
}
