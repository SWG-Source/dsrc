package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.prose;
import script.library.space_transition;
import script.library.sui;
import script.library.utils;

public class terminal_pob_ship extends script.base_script
{
    public terminal_pob_ship()
    {
    }
    public static final string_id SID_TERMINAL_PERMISSIONS = new string_id("player_structure", "permissions");
    public static final string_id SID_MOVE_FIRST_ITEM = new string_id("player_structure", "move_first_item");
    public static final string_id SID_MOVED_FIRST_ITEM = new string_id("player_structure", "moved_first_item_pob");
    public static final string_id SID_DELETE_ALL_ITEMS = new string_id("player_structure", "delete_all_items");
    public static final string_id SID_ITEMS_DELETED = new string_id("player_structure", "items_deleted_pob_ship");
    public static final string_id SID_ROOT_ITEM_MENU = new string_id("player_structure", "find_items_root_menu");
    public static final string_id SID_FIND_ALL_HOUSE_ITEMS = new string_id("player_structure", "find_items_find_all_house_items");
    public static final string_id SID_SEARCH_FOR_HOUSE_ITEMS = new string_id("player_structure", "find_items_search_for_house_items");
    public static final string_id SID_TERMINAL_REDEED_STORAGE = new string_id("player_structure", "redeed_storage");
    public static final string_id SID_STORAGE_INCREASE_REDEED_TITLE = new string_id("player_structure", "sui_storage_redeed_title");
    public static final string_id SID_STORAGE_INCREASE_REDEED_PROMPT = new string_id("player_structure", "sui_storage_redeed_prompt");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship) && getOwner(ship) == player)
        {
            int rootItemMenu = mi.addRootMenu(menu_info_types.SERVER_MENU10, SID_ROOT_ITEM_MENU);
            mi.addSubMenu(rootItemMenu, menu_info_types.SERVER_MENU11, SID_FIND_ALL_HOUSE_ITEMS);
            mi.addSubMenu(rootItemMenu, menu_info_types.SERVER_MENU12, SID_SEARCH_FOR_HOUSE_ITEMS);
            mi.addSubMenu(rootItemMenu, menu_info_types.SERVER_MENU2, SID_MOVE_FIRST_ITEM);
            mi.addSubMenu(rootItemMenu, menu_info_types.SERVER_MENU3, SID_DELETE_ALL_ITEMS);
            if (hasObjVar(ship, player_structure.OBJVAR_STRUCTURE_STORAGE_INCREASE))
            {
                mi.addRootMenu(menu_info_types.DICE_ROLL, SID_TERMINAL_REDEED_STORAGE);
            }
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TERMINAL_PERMISSIONS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(self);
        if (isIdValid(ship) && getOwner(ship) == player)
        {
            if (item == menu_info_types.SERVER_MENU1)
            {
                queueCommand(player, (1768087594), self, "admin", COMMAND_PRIORITY_DEFAULT);
            }
            else if (item == menu_info_types.SERVER_MENU2)
            {
                sui.msgbox(self, player, "@player_structure:move_first_item_d", sui.OK_CANCEL, "@player_structure:move_first_item", sui.MSG_QUESTION, "handleMoveFirstItem");
            }
            else if (item == menu_info_types.SERVER_MENU3)
            {
                sui.msgbox(self, player, "@player_structure:delete_all_items_d", sui.OK_CANCEL, "@player_structure:delete_all_items", sui.MSG_QUESTION, "handleDeleteSecondConfirm");
            }
            else if (item == menu_info_types.DICE_ROLL)
            {
                if (!hasObjVar(ship, player_structure.OBJVAR_STRUCTURE_STORAGE_INCREASE))
                {
                    return SCRIPT_CONTINUE;
                }
                player_structure.displayAvailableNonGenericStorageTypes(player, self, ship);
            }
            else if (item == menu_info_types.SERVER_MENU11)
            {
                int lockoutEnds = -1;
                if (hasObjVar(self, "findItems.lockout"))
                {
                    lockoutEnds = getIntObjVar(self, "findItems.lockout");
                }
                int currentTime = getGameTime();
                if (currentTime > lockoutEnds || isGod(player))
                {
                    player_structure.initializeFindAllItemsInHouse(self, player);
                    setObjVar(self, "findItems.lockout", currentTime + player_structure.HOUSE_ITEMS_SEARCH_LOCKOUT);
                }
                else 
                {
                    string_id message = new string_id("player_structure", "find_items_locked_out");
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, utils.formatTimeVerbose(lockoutEnds - currentTime));
                    sendSystemMessageProse(player, pp);
                }
            }
            else if (item == menu_info_types.SERVER_MENU12)
            {
                int lockoutEnds = -1;
                if (hasObjVar(self, "findItems.lockout"))
                {
                    lockoutEnds = getIntObjVar(self, "findItems.lockout");
                }
                int currentTime = getGameTime();
                if (currentTime > lockoutEnds || isGod(player))
                {
                    player_structure.initializeItemSearchInHouse(self, player);
                    setObjVar(self, "findItems.lockout", currentTime + player_structure.HOUSE_ITEMS_SEARCH_LOCKOUT);
                }
                else 
                {
                    string_id message = new string_id("player_structure", "find_items_locked_out");
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, utils.formatTimeVerbose(lockoutEnds - currentTime));
                    sendSystemMessageProse(player, pp);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStorageRedeedChoice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String accessFee = sui.getInputBoxText(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id objShip = space_transition.getContainingShip(self);
        if (!isIdValid(objShip) || getOwner(objShip) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(objShip, player_structure.OBJVAR_STRUCTURE_STORAGE_INCREASE))
        {
            int storageRedeedSelected = 0;
            if (params.containsKey(sui.LISTBOX_LIST + "." + sui.PROP_SELECTEDROW))
            {
                storageRedeedSelected = sui.getListboxSelectedRow(params);
                if (storageRedeedSelected < 0)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (player_structure.decrementStorageAmount(player, objShip, self, storageRedeedSelected))
            {
                sendSystemMessage(player, new string_id("player_structure", "storage_increase_redeeded"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMoveFirstItem(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (sui.getIntButtonPressed(params) != sui.BP_CANCEL)
        {
            obj_id ship = space_transition.getContainingShip(self);
            if (isIdValid(ship) && getOwner(ship) == player)
            {
                moveHouseItemToPlayer(ship, player, 0);
                sendSystemMessage(player, SID_MOVED_FIRST_ITEM);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteSecondConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (sui.getIntButtonPressed(params) != sui.BP_CANCEL)
        {
            sui.msgbox(self, player, "@player_structure:delete_all_items_second_d_pob_ship", sui.OK_CANCEL, "@player_structure:delete_all_items", sui.MSG_QUESTION, "handleDeleteItems");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleDeleteItems(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (sui.getIntButtonPressed(params) != sui.BP_CANCEL)
        {
            obj_id ship = space_transition.getContainingShip(self);
            if (isIdValid(ship) && getOwner(ship) == player)
            {
                deleteAllHouseItems(ship, player);
                fixHouseItemLimit(ship);
                sendSystemMessage(player, SID_ITEMS_DELETED);
                CustomerServiceLog("playerStructure", "deleteAllItems (Deleting all objects in ship by player's request.) Player: " + player + " (" + getName(player) + ") Ship: " + ship);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerStructureFindItemsListResponse(obj_id self, dictionary params) throws InterruptedException
    {
        player_structure.handleFindItemsListResponse(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerStructureFindItemsPageResponse(obj_id self, dictionary params) throws InterruptedException
    {
        player_structure.handleFindItemsChangePageResponse(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerStructureSearchItemsGetKeyword(obj_id self, dictionary params) throws InterruptedException
    {
        player_structure.handleSearchItemsGetKeyword(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayerStructureSearchItemsSelectedResponse(obj_id self, dictionary params) throws InterruptedException
    {
        player_structure.handleSearchItemsSelectedResponse(self, params);
        return SCRIPT_CONTINUE;
    }
}
