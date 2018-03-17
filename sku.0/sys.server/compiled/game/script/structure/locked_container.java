package script.structure;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class locked_container extends script.base_script
{
    public locked_container()
    {
    }
    public static final string_id SID_LOCK_MENU = new string_id("spam", "sid_lock_menu");
    public static final string_id SID_MENU_REMOVE_LOCK = new string_id("spam", "sid_menu_lock_remove");
    public static final string_id SID_MENU_ADD_ACCESS_PLAYER = new string_id("spam", "sid_menu_lock_add_player");
    public static final string_id SID_MENU_REMOVE_ACCESS_PLAYER = new string_id("spam", "sid_menu_lock_remove_player");
    public static final string_id SID_MENU_ADD_ACCESS_GUILD = new string_id("spam", "sid_menu_lock_add_guild");
    public static final string_id SID_MENU_REMOVE_ACCESS_GUILD = new string_id("spam", "sid_menu_lock_remove_guild");
    public static final string_id SID_LOCK_VIEW_ACCESS_LIST = new string_id("spam", "sid_menu_lock_view_access_list");
    public static final string_id SID_LOCK_ADDED_PLAYER = new string_id("spam", "sid_lock_added_player");
    public static final string_id SID_LOCK_ADDED_GUILD = new string_id("spam", "sid_lock_added_guild");
    public static final string_id SID_LOCK_GUILD_ALREADY_ADDED = new string_id("spam", "sid_lock_guild_already_added");
    public static final string_id SID_LOCK_REMOVED_PLAYER = new string_id("spam", "sid_lock_removed_player");
    public static final string_id SID_LOCK_REMOVED_GUILD = new string_id("spam", "sid_lock_removed_guild");
    public static final string_id SID_LOCK_GUILD_NOT_ADDED = new string_id("spam", "sid_lock_guild_not_added");
    public static final string_id SID_LOCK_GUILD_NOT_FOUND = new string_id("spam", "sid_lock_guild_not_found");
    public static final string_id SID_LOCK_REMOVED = new string_id("spam", "sid_lock_removed");
    public static final String SID_SUI_ADD_PLAYER_PROMPT = "@spam:sid_sui_add_player_prompt";
    public static final String SID_SUI_ADD_PLAYER_TITLE = "@spam:sid_sui_add_player_title";
    public static final String SID_SUI_ADD_GUILD_PROMPT = "@spam:sid_sui_add_guild_prompt";
    public static final String SID_SUI_ADD_GUILD_TITLE = "@spam:sid_sui_add_guild_title";
    public static final String SID_SUI_REMOVE_GUILD_PROMPT = "@spam:sid_sui_remove_guild_prompt";
    public static final String SID_SUI_REMOVE_GUILD_TITLE = "@spam:sid_sui_remove_guild_title";
    public static final String SID_SUI_REMOVE_PLAYER_PROMPT = "@spam:sid_sui_remove_player_prompt";
    public static final String SID_SUI_REMOVE_PLAYER_TITLE = "@spam:sid_sui_remove_player_title";
    public static final String SID_SUI_LOCK_ACCESSORS_PROMPT = "@spam:sid_sui_lock_accessors_prompt";
    public static final String SID_SUI_LOCK_ACCESSORS_TITLE = "@spam:sid_sui_lock_accessors_title";
    public static final string_id SID_LOCK_ADD_ACCESSOR_NOT_FOUND = new string_id("spam", "sid_lock_add_accessor_not_found");
    public static final string_id SID_LOCK_REMOVE_ACCESSOR_NOT_FOUND = new string_id("spam", "sid_lock_remove_accessor_not_found");
    public static final String OBJVAR_PLAYER_ACCESS_LIST = "tangible_object.accessList";
    public static final String OBJVAR_GUILD_ACCESS_LIST = "tangible_object.guildAccessList";
    public void addGuildToAccess(obj_id container, int guildId) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container) || guildId == 0)
        {
            return;
        }
        addGuildToAccessList(container, guildId);
    }
    public void removeGuildFromAccess(obj_id container, int guildId) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container) || guildId == 0)
        {
            return;
        }
        removeGuildFromAccessList(container, guildId);
    }
    public void clearGuildAccess(obj_id container) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container))
        {
            return;
        }
        clearGuildAccessList(container);
    }
    public int[] getGuildAccess(obj_id container) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container))
        {
            return null;
        }
        return getIntArrayObjVar(container, OBJVAR_GUILD_ACCESS_LIST);
    }
    public void addPlayerToAccess(obj_id container, obj_id player) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container) || !isIdValid(player) || !exists(player))
        {
            return;
        }
        addUserToAccessList(container, player);
    }
    public void removePlayerFromAccess(obj_id container, obj_id player) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container) || !isIdValid(player) || !exists(player))
        {
            return;
        }
        removeUserFromAccessList(container, player);
    }
    public void clearPlayerAccess(obj_id container) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container))
        {
            return;
        }
        clearUserAccessList(container);
    }
    public obj_id[] getPlayerAccess(obj_id container) throws InterruptedException
    {
        if (!isIdValid(container) || !exists(container))
        {
            return null;
        }
        return getObjIdArrayObjVar(container, OBJVAR_PLAYER_ACCESS_LIST);
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
        String name = getStringObjVar(self, "lock_owner_name");
        if (name != null && name.length() > 0)
        {
            names[idx] = "locked_by";
            attribs[idx] = name;
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        obj_id lockedBy = getObjIdObjVar(self, "lock_owner");
        if (isIdValid(lockedBy) && player == lockedBy)
        {
            int lockMenu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_LOCK_MENU);
            mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU2, SID_MENU_REMOVE_LOCK);
            mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU3, SID_MENU_ADD_ACCESS_PLAYER);
            obj_id[] accessors = getUserAccessList(self);
            boolean anyAccess = false;
            if (accessors != null && accessors.length > 0)
            {
                mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU4, SID_MENU_REMOVE_ACCESS_PLAYER);
                anyAccess = true;
            }
            int guildId = getGuildId(player);
            int[] guildsWithAccess = getGuildAccess(self);
            if (guildsWithAccess != null && guildsWithAccess.length > 0)
            {
                anyAccess = true;
            }
            mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU5, SID_MENU_ADD_ACCESS_GUILD);
            if (guildsWithAccess != null && guildsWithAccess.length > 0)
            {
                mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU6, SID_MENU_REMOVE_ACCESS_GUILD);
            }
            if (anyAccess)
            {
                mi.addSubMenu(lockMenu, menu_info_types.SERVER_MENU7, SID_LOCK_VIEW_ACCESS_LIST);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id lockedBy = getObjIdObjVar(self, "lock_owner");
        if (isIdValid(lockedBy) && player == lockedBy && item == menu_info_types.SERVER_MENU2)
        {
            obj_id container = utils.getInventoryContainer(player);
            obj_id craftedItem = makeCraftedItem("object/draft_schematic/furniture/furniture_house_container_lock.iff", 100.0f, container);
            if (isIdValid(craftedItem))
            {
                removeObjVar(self, "lock_owner");
                removeObjVar(self, "lock_owner_name");
                clearCondition(self, CONDITION_LOCKED);
                clearPlayerAccess(self);
                clearGuildAccess(self);
                sendSystemMessage(player, SID_LOCK_REMOVED);
                detachScript(self, "structure.locked_container");
            }
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(lockedBy) && player == lockedBy && item == menu_info_types.SERVER_MENU3)
        {
            int pid = sui.inputbox(self, player, SID_SUI_ADD_PLAYER_PROMPT, sui.OK_CANCEL, SID_SUI_ADD_PLAYER_TITLE, sui.INPUT_NORMAL, null, "lockAddPlayer");
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(lockedBy) && player == lockedBy && item == menu_info_types.SERVER_MENU4)
        {
            int pid = sui.inputbox(self, player, SID_SUI_REMOVE_PLAYER_PROMPT, sui.OK_CANCEL, SID_SUI_REMOVE_PLAYER_TITLE, sui.INPUT_NORMAL, null, "lockRemovePlayer");
            sendSystemMessage(player, SID_MENU_REMOVE_ACCESS_PLAYER);
            return SCRIPT_CONTINUE;
        }
        int guildId = getGuildId(player);
        int[] guildsWithAccess = getGuildAccess(self);
        if (isIdValid(lockedBy) && player == lockedBy && item == menu_info_types.SERVER_MENU5)
        {
            int pid = sui.inputbox(self, player, SID_SUI_ADD_GUILD_PROMPT, sui.OK_CANCEL, SID_SUI_ADD_GUILD_TITLE, sui.INPUT_NORMAL, null, "lockAddGuild");
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(lockedBy) && player == lockedBy && guildsWithAccess != null && guildsWithAccess.length > 0 && item == menu_info_types.SERVER_MENU6)
        {
            int pid = sui.inputbox(self, player, SID_SUI_REMOVE_GUILD_PROMPT, sui.OK_CANCEL, SID_SUI_REMOVE_GUILD_TITLE, sui.INPUT_NORMAL, null, "lockRemoveGuild");
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(lockedBy) && player == lockedBy && item == menu_info_types.SERVER_MENU7)
        {
            obj_id[] accessors = getPlayerAccess(self);
            Vector accessList = new Vector();
            accessList.setSize(0);
            int accessorsCount = 0;
            if (accessors != null)
            {
                accessorsCount = accessors.length;
            }
            int guildsCount = 0;
            if (guildsWithAccess != null)
            {
                guildsCount = guildsWithAccess.length;
            }
            if (guildsWithAccess != null && guildsWithAccess.length > 0)
            {
                for (int i = 0, j = guildsWithAccess.length; i < j; i++)
                {
                    utils.addElement(accessList, "Guild: " + guildGetName(guildsWithAccess[i]));
                }
            }
            if (accessors != null && accessors.length > 0)
            {
                for (int i = 0, j = accessors.length; i < j; i++)
                {
                    if (isIdValid(accessors[i]))
                    {
                        utils.addElement(accessList, "" + getPlayerFullName(accessors[i]));
                    }
                }
            }
            int pid = sui.listbox(player, player, SID_SUI_LOCK_ACCESSORS_PROMPT, sui.OK_CANCEL, SID_SUI_LOCK_ACCESSORS_TITLE, accessList, "onLockAccessList", false, true);
            sui.showSUIPage(pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int lockAddPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String accessorName = sui.getInputBoxText(params);
            obj_id target = utils.getNearbyPlayerByName(player, accessorName);
            if (!isIdValid(target))
            {
                target = getPlayerIdFromFirstName(accessorName);
            }
            if (isIdValid(target))
            {
                addPlayerToAccess(self, target);
                sendSystemMessage(player, SID_LOCK_ADDED_PLAYER);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.actor.set(accessorName);
                pp.stringId = SID_LOCK_ADD_ACCESSOR_NOT_FOUND;
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int lockRemovePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        obj_id lockedBy = getObjIdObjVar(self, "lock_owner");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String accessorName = sui.getInputBoxText(params);
            obj_id target = utils.getNearbyPlayerByName(player, accessorName);
            if (!isIdValid(target))
            {
                target = getPlayerIdFromFirstName(accessorName);
            }
            if (lockedBy == target)
            {
                return SCRIPT_CONTINUE;
            }
            if (isIdValid(target))
            {
                removePlayerFromAccess(self, target);
                sendSystemMessage(player, SID_LOCK_REMOVED_PLAYER);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.actor.set(accessorName);
                pp.stringId = SID_LOCK_REMOVE_ACCESSOR_NOT_FOUND;
                sendSystemMessageProse(player, pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int lockAddGuild(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String accessorName = sui.getInputBoxText(params);
            int guildId = findGuild(accessorName);
            if (guildId != 0)
            {
                int[] guildsOnContainer = getGuildAccess(self);
                boolean addable = true;
                if (guildsOnContainer != null && guildsOnContainer.length > 0)
                {
                    for (int i = 0, j = guildsOnContainer.length; i < j; i++)
                    {
                        if (guildsOnContainer[i] == guildId)
                        {
                            addable = false;
                        }
                    }
                }
                if (addable)
                {
                    addGuildToAccess(self, guildId);
                    sendSystemMessage(player, SID_LOCK_ADDED_GUILD);
                }
                else 
                {
                    sendSystemMessage(player, SID_LOCK_GUILD_ALREADY_ADDED);
                }
            }
            else 
            {
                sendSystemMessage(player, SID_LOCK_GUILD_NOT_FOUND);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int lockRemoveGuild(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (sui.getIntButtonPressed(params) == sui.BP_OK)
        {
            String accessorName = sui.getInputBoxText(params);
            int guildId = findGuild(accessorName);
            if (guildId != 0)
            {
                int[] guildsOnContainer = getGuildAccess(self);
                boolean removable = false;
                if (guildsOnContainer != null && guildsOnContainer.length > 0)
                {
                    for (int i = 0, j = guildsOnContainer.length; i < j; i++)
                    {
                        if (guildsOnContainer[i] == guildId)
                        {
                            removable = true;
                        }
                    }
                }
                if (removable)
                {
                    removeGuildFromAccess(self, guildId);
                    sendSystemMessage(player, SID_LOCK_REMOVED_GUILD);
                }
                else 
                {
                    sendSystemMessage(player, SID_LOCK_GUILD_NOT_ADDED);
                }
            }
            else 
            {
                sendSystemMessage(player, SID_LOCK_GUILD_NOT_FOUND);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
