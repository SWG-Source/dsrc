package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class house_container_lock extends script.base_script
{
    public house_container_lock()
    {
    }
    public static final string_id SID_LOCK_BAD_STRUCTURE = new string_id("spam", "sid_lock_bad_structure");
    public static final string_id SID_LOCK_BAD_LOCK_TARGET = new string_id("spam", "sid_lock_bad_lock_target");
    public static final string_id SID_LOCK_BAD_ALREADY_LOCKED = new string_id("spam", "sid_lock_bad_already_locked");
    public static final string_id SID_LOCK_BAD_INVENTORY_LOCATION = new string_id("spam", "sid_lock_bad_inventory_location");
    public static final string_id SID_LOCK_BAD_LOCK_CONTAINER = new string_id("spam", "sid_lock_bad_lock_container");
    public static final string_id SID_LOCK_APPLIED = new string_id("spam", "sid_lock_applied");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = getTopMostContainer(self);
        obj_id containedBy = utils.getContainingPlayer(self);
        if (!isIdValid(containedBy) || containedBy != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(structure) || !exists(structure) || structure == player || getOwner(structure) != player)
        {
            sendSystemMessage(player, SID_LOCK_BAD_STRUCTURE);
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id target = getIntendedTarget(player);
            if (!isIdValid(target))
            {
                sendSystemMessage(player, SID_LOCK_BAD_LOCK_TARGET);
                return SCRIPT_CONTINUE;
            }
            if (hasScript(target, "structure.locked_container"))
            {
                sendSystemMessage(player, SID_LOCK_BAD_ALREADY_LOCKED);
                return SCRIPT_CONTINUE;
            }
            if (!isGameObjectTypeOf(target, GOT_misc_container) && !isGameObjectTypeOf(target, GOT_misc_container_wearable))
            {
                sendSystemMessage(player, SID_LOCK_BAD_LOCK_TARGET);
                return SCRIPT_CONTINUE;
            }
            if (getOwner(target) != player)
            {
                sendSystemMessage(player, SID_LOCK_BAD_LOCK_TARGET);
                return SCRIPT_CONTINUE;
            }
            setObjVar(target, "lock_owner", player);
            setObjVar(target, "lock_owner_name", getName(player));
            clearUserAccessList(target);
            clearGuildAccessList(target);
            addUserToAccessList(target, player);
            attachScript(target, "structure.locked_container");
            sendSystemMessage(player, SID_LOCK_APPLIED);
            messageTo(self, "destroySelf", null, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
