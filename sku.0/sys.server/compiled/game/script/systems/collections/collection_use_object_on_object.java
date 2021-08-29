package script.systems.collections;

import script.*;
import script.library.groundquests;
import script.library.utils;

public class collection_use_object_on_object extends script.base_script
{
    public collection_use_object_on_object()
    {
    }

    public static final string_id SID_INVALID_TARGET_MSG = new string_id("collection", "invalid_target");
    public static final string_id SID_INVALID_TARGET_FLY = new string_id("spam", "bad_target");
    public static final string_id SID_NOT_WHILE_INCAPACITATED_OR_DEAD = new string_id("spam", "while_dead");
    public static final string_id SID_MUST_DISMOUNT = new string_id("spam", "mounted");
    public static final string_id SID_CREATURE_IS_DEAD = new string_id("collection", "creature_is_dead");
    public static final string_id SID_OBJECT_DESTROYED = new string_id("collection", "object_destroyed");
    public static final string_id SID_NO_MOVE = new string_id("spam", "no_move_item");
    public static final string_id SID_TOO_FAR = new string_id("spam", "too_far");
    public static final string_id SID_CANT_SEE = new string_id("combat_effects", "cant_see");

    public static final String VAR_OBJECT_KEY = "quest.object_key";
    public static final String TABLE_COLLECTION_OBJECT = "datatables/collection/use_object_on_object.iff";

    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupVars", null, 3f, false);
        return SCRIPT_CONTINUE;
    }

    // just in case it isn't added by static item, we shouldn't be able to trade this
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if(!hasObjVar(self, "noTrade"))
        {
            setObjVar(self, "noTrade", true);
        }
        return SCRIPT_CONTINUE;
    }

    // keep in inventory, and don't allow placement into volume containers inside inventory
    // so we don't have to recursively search for this later on collection completion
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if(isIdValid(transferer) && isPlayerConnected(transferer))
        {
            sendSystemMessage(transferer, SID_NO_MOVE);
        }
        return SCRIPT_OVERRIDE;
    }

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if(isObjectInvalidInContext(self))
        {
            dictionary d = new dictionary();
            d.put("player", player);
            messageTo(self, "onRequestDestroy", d, 0f, false);
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(player) || isDead(player))
        {
            sendSystemMessage(player, SID_NOT_WHILE_INCAPACITATED_OR_DEAD);
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_RIDING_MOUNT) == 1 || isIdValid(getMountId(player)))
        {
            sendSystemMessage(player, SID_MUST_DISMOUNT);
            return SCRIPT_CONTINUE;
        }
        final obj_id intended = getLookAtTarget(player);
        if (!isIdValid(intended))
        {
            sendSystemMessage(player, SID_INVALID_TARGET_MSG);
            return SCRIPT_CONTINUE;
        }
        handleTarget(player, intended, self);
        return SCRIPT_CONTINUE;
    }

    public static void handleTarget(obj_id player, obj_id target, obj_id self) throws InterruptedException
    {
        if (isMob(target) && (isDead(target) || isIncapacitated(target)))
        {
            sendSystemMessage(player, SID_CREATURE_IS_DEAD);
            return;
        }

        // targetContainedScript validates if the mobs are in (and in the correct)
        // container, e.g. a dungeon, and therefore using dungeon spawner
        final String targetContainedScript = utils.getStringScriptVar(self, "collection.targetContainedScript");
        if(targetContainedScript != null && targetContainedScript.length() > 0)
        {
            if(!hasScript(getTopMostContainer(target), targetContainedScript))
            {
                showFlyTextPrivate(target, player, SID_INVALID_TARGET_FLY, 1.5f, color.RED);
                return;
            }
        }

        // targetCreature validates a specific type of mob from creatures table
        final String targetCreature = utils.getStringScriptVar(self, "collection.targetCreature");
        if(targetCreature != null && targetCreature.length() > 0)
        {
            final String creature = getCreatureName(target);
            if(creature != null && !getCreatureName(target).equalsIgnoreCase(targetCreature))
            {
                showFlyTextPrivate(target, player, SID_INVALID_TARGET_FLY, 1.5f, color.RED);
                return;
            }
        }

        // make sure we aren't an unreasonable distance away
        if(utils.getDistance2D(player, target) > 5.0f)
        {
            showFlyTextPrivate(target, player, SID_TOO_FAR, 1.5f, color.RED);
            return;
        }

        // and make sure we can actually see the thing
        if(!canSee(self, target) || !canSee(target, self))
        {
            showFlyTextPrivate(self, player, SID_CANT_SEE, 1.5f, color.RED);
            return;
        }

        // effectively do a ++ on the next available slot in the collection
        final String collectionName = utils.getStringScriptVar(self, "collection.collectionName");
        final String targetEffect = utils.getStringScriptVar(self, "collection.targetEffect");
        if(collectionName != null && collectionName.length() > 0)
        {
            final String[] slots = utils.getStringArrayScriptVar(self, "collection.collectionSlots");
            if(slots != null && slots.length > 0)
            {
                for(String slot : slots)
                {
                    if(!hasCompletedCollectionSlotPrereq(player, slot))
                    {
                        continue;
                    }
                    if(hasCompletedCollectionSlot(player, slot))
                    {
                        continue;
                    }
                    modifyCollectionSlotValue(player, slot, 1);
                    messageTo(target, utils.getStringScriptVar(self, "collection.handleTarget"), null, 1f, false);
                    if(targetEffect != null && targetEffect.length() > 0)
                    {
                        playClientEffectObj(self, targetEffect, target, "root");
                    }
                    break;
                }
            }
        }
    }

    /**
     * Determine if we should delete the special collection object
     * based on our current player's context
     */
    public static boolean isObjectInvalidInContext(obj_id self) throws InterruptedException
    {
        final obj_id player = getOwner(self);
        if(isIdValid(player) && isPlayer(player))
        {
            final String collectionName = utils.getStringScriptVar(self, "collection.collectionName");
            final int questRequired = utils.getIntScriptVar(self, "collection.questRequired");
            if(questRequired > 0) // a quest is required to have this item
            {
                final String questName = utils.getStringScriptVar(self, "collection.questName");
                final String taskName = utils.getStringScriptVar(self, "taskName");
                if(questName != null && questName.length() > 0) // a specific quest must be active
                {
                    if(!groundquests.isQuestActive(player, questName))
                    {
                        return true;
                    }
                }
                // check if a specific task is *related* but don't specifically check
                // whether the task is active or not as this can pre-maturely delete the
                // object, e.g. if we were just given it and click on it while the
                // travel to whatever location task is still active before the actual required task
                if(taskName != null && taskName.length() > 0)
                {
                    if(groundquests.hasCompletedTask(player, questName, taskName))
                    {
                        return true;
                    }
                }
            }
            // if we've already completed the collection that is relevant
            // to this object, we don't need it anymore
            // note: player_collection also validates inventory contents for this
            // on collection slot modify trigger
            if(collectionName != null && collectionName.length() > 0)
            {
                return hasCompletedCollection(player, collectionName);
            }
            return false;
        }
        return true;
    }

    /**
     * Store all the crap we need as ScriptVars so we don't have to keep reading from a table
     */
    public int setupVars(obj_id self, dictionary params) throws InterruptedException
    {
        final String keyString = getStringObjVar(self, VAR_OBJECT_KEY);
        if(keyString == null || keyString.length() < 1)
        {
            WARNING("Bad object of collection_use_object_on_object -> missing Key String Var ("+self+")");
            messageTo(self, "onRequestDestroy", null, 0f, false);
            return SCRIPT_CONTINUE;
        }
        final dictionary d = dataTableGetRow(TABLE_COLLECTION_OBJECT, keyString);
        if(d == null || d.size() < 1)
        {
            WARNING("Bad object of collection_use_object_on_object -> Key String "+keyString+" is missing from collection object table ("+self+")");
            return SCRIPT_CONTINUE;
        }
        final String collectionName = d.getString("collectionName");
        if(collectionName == null || collectionName.length() < 1)
        {
            WARNING("Bad object of collection_use_object_on_object -> Collection Name is missing from collection table ("+self+")");
            return SCRIPT_CONTINUE;
        }
        else
        {
            final String[] slots = getAllCollectionSlotsInCollection(collectionName);
            if(slots == null || slots.length < 1)
            {
                WARNING("Bad object of collection_use_object_on_object -> Collection name is invalid or doesn't have any slots ("+self+")");
                return SCRIPT_CONTINUE;
            }
            else
            {
                utils.setScriptVar(self, "collection.collectionSlots", slots);
            }
        }
        utils.setScriptVar(self, "collection.collectionName", collectionName);
        utils.setScriptVar(self, "collection.questName", d.getString("questName"));
        utils.setScriptVar(self, "collection.taskName", d.getString("taskName"));
        utils.setScriptVar(self, "collection.handleTarget", d.getString("handleTarget"));
        utils.setScriptVar(self, "collection.targetContainedScript", d.getString("targetContainedScript"));
        utils.setScriptVar(self, "collection.targetCreature", d.getString("targetCreature"));
        utils.setScriptVar(self, "collection.questRequired", d.getInt("questRequired"));
        utils.setScriptVar(self, "collection.targetEffect", d.getString("targetEffect"));
        return SCRIPT_CONTINUE;
    }

    /**
     * We shouldn't have this item anymore, so destroy it
     */
    public int onRequestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if(hasScript(self, "item.special.nodestroy"))
        {
            detachScript(self, "item.special.nodestroy");
        }
        if(params.containsKey("player"))
        {
            final obj_id player = params.getObjId("player");
            if(isIdValid(player) && isPlayerConnected(player))
            {
                sendSystemMessage(player, SID_OBJECT_DESTROYED);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }

}
