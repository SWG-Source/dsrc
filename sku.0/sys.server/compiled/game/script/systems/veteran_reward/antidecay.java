package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.respec;
import script.library.veteran_deprecated;

public class antidecay extends script.base_script
{
    public antidecay()
    {
    }
    public static final String OBJVAR_GROUP_REWARDED = "rewarded";
    public static final String VETERAN_STRING_TABLE = "veteran_new";
    public static final String VETERAN_CSLOG = "veteran";
    public static final String NOMOVE_SCRIPT = "item.special.nomove";
    public static final string_id SID_APPLY_ANTI_DECAY = new string_id("ui_radial", "apply_anti_decay");
    public static final string_id SID_RETRIEVE_ITEMS_FROM_ANTI_DECAY_KIT = new string_id("ui_radial", "retrieve_items_from_anti_decay_kit");
    public static final string_id SID_ERROR_KIT_ALREADY_USED = new string_id(VETERAN_STRING_TABLE, "error_kit_already_used");
    public static final string_id SID_ERROR_ITEM_NOT_IN_PLAYER_INVENTORY = new string_id(VETERAN_STRING_TABLE, "error_item_not_in_player_inventory");
    public static final string_id SID_ERROR_KIT_NOT_IN_PLAYER_INVENTORY = new string_id(VETERAN_STRING_TABLE, "error_kit_not_in_player_inventory");
    public static final string_id SID_ERROR_KIT_ALREADY_HAS_ITEM = new string_id(VETERAN_STRING_TABLE, "error_kit_already_has_item");
    public static final string_id SID_ERROR_ITEM_NOT_VALID_FOR_ANTI_DECAY = new string_id(VETERAN_STRING_TABLE, "error_item_not_valid_for_anti_decay");
    public static final string_id SID_ERROR_KIT_HAS_TOO_MANY_ITEMS = new string_id(VETERAN_STRING_TABLE, "error_kit_has_too_many_items");
    public static final string_id SID_ERROR_ITEM_IS_ALREADY_ANTI_DECAY = new string_id(VETERAN_STRING_TABLE, "error_item_is_already_anti_decay");
    public static final string_id SID_SUCCESSFULLY_USED_ANTI_DECAY_KIT = new string_id(VETERAN_STRING_TABLE, "successfully_used_anti_decay_kit");
    public static final string_id SID_FAILED_ITEM_CANNOT_BE_PLACED_IN_INVENTORY = new string_id(VETERAN_STRING_TABLE, "failed_item_cannot_be_placed_in_inventory");
    public static final string_id SID_FAILED_ITEM_NOT_MADE_ANTI_DECAY = new string_id(VETERAN_STRING_TABLE, "failed_item_not_made_anti_decay");
    public static final string_id SID_ERROR_KIT_NOT_CONTAINER = new string_id(VETERAN_STRING_TABLE, "error_kit_not_container");
    public static final string_id SID_USE_KIT_CONFIRM_PROMPT_ONE = new string_id(VETERAN_STRING_TABLE, "use_kit_confirm_prompt_one");
    public static final string_id SID_USE_KIT_CONFIRM_PROMPT_TWO = new string_id(VETERAN_STRING_TABLE, "use_kit_confirm_prompt_two");
    public static final string_id SID_USE_KIT_CONFIRM_TITLE = new string_id(VETERAN_STRING_TABLE, "use_kit_confirm_title");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            messageTo(self, "handleVeteranAntidecayDestroy", null, 5, false);
        }
        setName(self, "");
        boolean isEmpty = false;
        obj_id[] kitContents = getContents(self);
        if (kitContents == null || kitContents.length < 1)
        {
            isEmpty = true;
        }
        obj_id player = utils.getContainingPlayer(self);
        if (!isEmpty && isIdValid(player))
        {
            putItemsInKitIntoPlayerInventory(self, player);
        }
        if (isEmpty && hasScript(self, NOMOVE_SCRIPT))
        {
            detachScript(self, NOMOVE_SCRIPT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            sendSystemMessage(transferer, SID_ERROR_KIT_ALREADY_USED);
            messageTo(self, "handleVeteranAntidecayDestroy", null, 5, false);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasScript(self, NOMOVE_SCRIPT))
        {
            detachScript(self, NOMOVE_SCRIPT);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        item.addRootMenu(menu_info_types.ITEM_USE, null);
        setName(self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id[] kitContents = getContents(self);
            if (kitContents != null && kitContents.length > 0)
            {
                if (!putItemsInKitIntoPlayerInventory(self, player))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (canManipulate(player, self, false, true, 0, true))
            {
                respec.startNpcRespec(player, self, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        obj_id[] kitContents = getContents(self);
        if (isIdValid(player))
        {
            if (!putItemsInKitIntoPlayerInventory(self, player))
            {
                return SCRIPT_OVERRIDE;
            }
        }
        if (!hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            if (isIdValid(player))
            {
                CustomerServiceLog(VETERAN_CSLOG, "Anti Decay Kit " + self + " was destroyed by player %TU without being used.", player);
            }
            else 
            {
                CustomerServiceLog(VETERAN_CSLOG, "Anti Decay Kit " + self + " was destroyed without being used. Player unknown.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVeteranAntidecayDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, OBJVAR_GROUP_REWARDED))
        {
            obj_id player = utils.getContainingPlayer(self);
            obj_id[] kitContents = getContents(self);
            if (isIdValid(player))
            {
                if (!putItemsInKitIntoPlayerInventory(self, player))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            CustomerServiceLog(VETERAN_CSLOG, "Anti Decay Kit " + self + " is being destroyed after successfully being used.");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean putItemsInKitIntoPlayerInventory(obj_id kit, obj_id player) throws InterruptedException
    {
        if (!isIdValid(kit))
        {
            return true;
        }
        if (!isIdValid(player))
        {
            return true;
        }
        obj_id[] kitContents = getContents(kit);
        if (kitContents != null && kitContents.length > 0)
        {
            for (int i = 0; i < kitContents.length; ++i)
            {
                if (!utils.putInPlayerInventory(player, kitContents[i]))
                {
                    sendSystemMessage(player, SID_FAILED_ITEM_CANNOT_BE_PLACED_IN_INVENTORY);
                    return false;
                }
            }
        }
        return true;
    }
}
