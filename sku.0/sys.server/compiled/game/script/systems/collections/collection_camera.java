package script.systems.collections;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.collection;
import script.library.sui;
import script.library.utils;

public class collection_camera extends script.base_script
{
    public collection_camera()
    {
    }
    public static final string_id SID_USE_CAMERA = new string_id("collection", "use_camera");
    public static final string_id SID_PICTURE_TAKEN = new string_id("collection", "picture_taken");
    public static final string_id SID_INVALID_TARGET = new string_id("collection", "invalid_target");
    public static final string_id SID_SUCCESS_SNAPSHOT = new string_id("collection", "successful_snapshot");
    public static final string_id SID_PHOTO_SLOT_COMPLETE = new string_id("collection", "photo_slot_complete");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final string_id MUST_DISMOUNT = new string_id("collection", "must_dismount");
    public static final string_id CREATURE_IS_DEAD = new string_id("collection", "creature_is_dead");
    public static final String PHOTO_COLLECTION = "col_photo_durni_01";
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
            checkValidCreature(player, intended);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkValidCreature(obj_id player, obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(target, "picture_taken_debuff"))
        {
            sendSystemMessage(player, SID_PICTURE_TAKEN);
            return false;
        }
        if (hasCompletedCollection(player, PHOTO_COLLECTION))
        {
            return false;
        }
        if (isDead(target) || isIncapacitated(target))
        {
            sendSystemMessage(player, CREATURE_IS_DEAD);
            return false;
        }
        String creatureName = getCreatureName(target);
        if (creatureName.equals("durni"))
        {
            if (!hasCompletedCollectionSlot(player, "photo_durni_01"))
            {
                modifyCollectionSlotValue(player, "photo_durni_01", 1);
                tagTheCreature(player, target);
                return true;
            }
            else 
            {
                photoSlotComplete(player);
                return false;
            }
        }
        else if (creatureName.equals("corellia_durni_fiendish"))
        {
            if (!hasCompletedCollectionSlot(player, "photo_durni_fiendish_01"))
            {
                modifyCollectionSlotValue(player, "photo_durni_fiendish_01", 1);
                tagTheCreature(player, target);
                return true;
            }
            else 
            {
                photoSlotComplete(player);
                return false;
            }
        }
        else if (creatureName.equals("crazed_durni"))
        {
            if (!hasCompletedCollectionSlot(player, "photo_crazed_durni_01"))
            {
                modifyCollectionSlotValue(player, "photo_crazed_durni_01", 1);
                tagTheCreature(player, target);
                return true;
            }
            else 
            {
                photoSlotComplete(player);
                return false;
            }
        }
        else if (creatureName.equals("durni_vehement_warrior"))
        {
            if (!hasCompletedCollectionSlot(player, "photo_durni_vehement_warrior_01"))
            {
                modifyCollectionSlotValue(player, "photo_durni_vehement_warrior_01", 1);
                tagTheCreature(player, target);
                return true;
            }
            else 
            {
                photoSlotComplete(player);
                return false;
            }
        }
        else 
        {
            sendSystemMessage(player, SID_INVALID_TARGET);
            return false;
        }
    }
    public void tagTheCreature(obj_id player, obj_id photoTarget) throws InterruptedException
    {
        String pictureParticle = "appearance/pt_durni_camera.prt";
        dictionary dict = new dictionary();
        dict.put("target", photoTarget);
        sendSystemMessage(player, SID_SUCCESS_SNAPSHOT);
        playClientEffectObj(photoTarget, pictureParticle, photoTarget, "root", null, "root_buff");
        utils.setScriptVar(photoTarget, "picture_taken_debuff", "active");
        messageTo(photoTarget, "clearCollectionCameraParticle", dict, 300, false);
    }
    public void photoSlotComplete(obj_id player) throws InterruptedException
    {
        sendSystemMessage(player, SID_PHOTO_SLOT_COMPLETE);
        return;
    }
}
