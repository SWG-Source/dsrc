package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class permissions extends script.base_script
{
    public permissions()
    {
    }
    public static final String VAR_PERMISSION_BASE = "permissions_list";
    public static final String VAR_PERMISSION_BANNED = "permissions_list.banned";
    public static final String VAR_PERMISSION_USERS = "permissions_list.users";
    public static final String VAR_PERMISSION_GROUPS = "permissions_list.groups";
    public static final String VAR_IS_LOCKED = "isLocked";
    public static final string_id SID_INSUFFICIENT_PERMISSIONS = new string_id("error_message", "insufficient_permissions");
    public static final string_id SID_NO_OPEN_PERMISSION = new string_id("error_message", "perm_no_open");
    public static final string_id SID_NO_CORPSE_PERMISSION = new string_id("error_message", "no_corpse_permission");
    public static final int PERMISSIONS_ARRAY_SIZE = 2;
    public static final int MAX_GROUPS = 7;
    public static final int MAX_GROUP_MEMBERS = 32;
    public static final int MAX_BANNED_MEMBERS = 32;
    public static final int canAdministrate = 0;
    public static final int canToggleOnOff = 1;
    public static final int canName = 2;
    public static final int canBan = 3;
    public static final int canMove = 4;
    public static final int canMaintain = 5;
    public static final int canRepair = 6;
    public static final int canItemSet = 7;
    public static final int canItemGet = 8;
    public static final int canHopperEmpty = 9;
    public static final int canOpen = 10;
    public static final int canLock = 11;
    public static final int canReTarget = 12;
    public static boolean createPermissionsGroup(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "createPermissionsGroup: target passed as null");
            return false;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "deletePermissionsGroup: invalid group name!");
            return false;
        }
        if (toLower(group_name) == "guest")
        {
            debugServerConsoleMsg(target, "createPermissionsGroup: guest is a reserved keyword");
            debugServerConsoleMsg(target, "createPermissionsGroup: unable to create group named guest");
            return false;
        }
        if (group_name != null && !group_name.equals(""))
        {
            String basePath = "permissions_list.groups." + group_name;
            setObjVarList(target, basePath + ".group_members");
            int[] permissionsArray = new int[PERMISSIONS_ARRAY_SIZE];
            setObjVar(target, basePath + ".permissions", permissionsArray);
            return true;
        }
        debugServerConsoleMsg(target, "createPermissionsGroup: cannot create group where group_name is null");
        return false;
    }
    public static boolean deletePermissionsGroup(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "deletePermissionsGroup: target passed as null");
            return false;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "deletePermissionsGroup: invalid group name!");
            return false;
        }
        if (toLower(group_name) == "guest")
        {
            debugServerConsoleMsg(target, "deletePermissionsGroup: guest is a reserved keyword");
            debugServerConsoleMsg(target, "deletePermissionsGroup: unable to delete group guest");
            return false;
        }
        if (group_name != null && !group_name.equals(""))
        {
            String groupPath = "permissions_list.groups." + group_name;
            String memberPath = groupPath + ".group_members";
            if (!hasObjVar(target, groupPath))
            {
                debugServerConsoleMsg(target, "deletePermissionsGroup: group doesnt exist - exiting...");
                return true;
            }
            obj_var_list permissionsGroup = getObjVarList(target, memberPath);
            if (permissionsGroup != null)
            {
                int numEntries = permissionsGroup.getNumItems();
                if (numEntries > 0)
                {
                    for (int i = 0; i < numEntries; i++)
                    {
                        obj_var groupMember = permissionsGroup.getObjVar(i);
                        obj_id player = utils.stringToObjId(groupMember.getName());
                        boolean litmus = deleteUserFromPermissionsGroup(target, player, group_name);
                        if (!litmus)
                        {
                            return false;
                        }
                    }
                }
            }
            removeObjVar(target, groupPath);
            return true;
        }
        debugServerConsoleMsg(target, "createPermissionsGroup: cannot create group where group_name is null");
        return false;
    }
    public static boolean addUserToPermissionsGroup(obj_id target, obj_id player, String player_name, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "addUserToPermissionsGroup: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "addUserToPermissionsGroup: player passed as null");
            return false;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "addUserToPermissionsGroup: group name passed as null");
            return false;
        }
        if ((player_name == null) || (player_name.equals("")))
        {
            player_name = getName(player);
        }
        if ((player != null) && (player_name != null) && (group_name != null))
        {
            String groupPath = "permissions_list.groups." + group_name;
            int memberCount = groupMemberCount(target, group_name);
            if (memberCount < 0)
            {
                return false;
            }
            if ((hasObjVar(target, groupPath)) && (memberCount < MAX_GROUP_MEMBERS))
            {
                setObjVar(target, groupPath + ".group_members." + toString(player), player_name);
                return addToUserTally(target, player, group_name);
            }
            if (!hasObjVar(target, groupPath))
            {
                debugServerConsoleMsg(target, "addUserToPermissionsGroup: group " + group_name + " does not exist");
            }
            if (groupMemberCount(target, group_name) >= MAX_GROUP_MEMBERS)
            {
                debugServerConsoleMsg(target, "addUserToPermissionsGroup: group membership has reached max capacity");
            }
            return false;
        }
        debugServerConsoleMsg(target, "addUserToPermissionsGroup: function was passed a null variable");
        return false;
    }
    public static boolean addUserToPermissionsGroup(obj_id target, obj_id player, String group_name) throws InterruptedException
    {
        return addUserToPermissionsGroup(target, player, getName(player), group_name);
    }
    public static boolean deleteUserFromPermissionsGroup(obj_id target, obj_id player, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: player passed as null");
            return false;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: group_name passed as null");
            return false;
        }
        String groupPath = "permissions_list.groups." + group_name;
        if (hasObjVar(target, groupPath))
        {
            String playerPath = groupPath + ".group_members." + toString(player);
            if (hasObjVar(target, playerPath))
            {
                removeObjVar(target, playerPath);
                debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: removed user from group");
                boolean litmus = updateUserTally(target, player);
                if (litmus = true)
                {
                    debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: updated user tally!");
                    return true;
                }
                else 
                {
                    debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: failed user update!");
                    return false;
                }
            }
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: userId " + toString(player) + " does not exist");
            return false;
        }
        debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: group " + group_name + " does not exist");
        return false;
    }
    public static boolean addUserToBanned(obj_id target, obj_id player, String player_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "addUserToBanned: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "addUserToBanned: player passed as null");
            return false;
        }
        if ((player_name == null) || (player_name.equals("")))
        {
            player_name = getName(player);
        }
        if (hasObjVar(target, "permissions_list.users." + toString(player)))
        {
            removeObjVar(target, "permissions_list.users." + toString(player));
            obj_var_list groupsList = getObjVarList(target, "permissions_list.groups");
            if (groupsList != null)
            {
                int numGroups = groupsList.getNumItems();
                for (int i = 0; i < numGroups; i++)
                {
                    obj_var tmpGroup = groupsList.getObjVar(i);
                    if (tmpGroup instanceof script.obj_var_list)
                    {
                        obj_var_list lstGroup = (obj_var_list)tmpGroup;
                        if (lstGroup.hasObjVar("group_members." + toString(player)))
                        {
                            String objVarToNuke = "permissions_list.groups." + toString(lstGroup.getName()) + ".group_members." + toString(player);
                            removeObjVar(target, objVarToNuke);
                        }
                    }
                }
            }
        }
        setObjVar(target, "permissions_list.banned." + toString(player), player_name);
        return true;
    }
    public static boolean addUserToBanned(obj_id target, obj_id player) throws InterruptedException
    {
        return addUserToBanned(target, player, getName(player));
    }
    public static boolean deleteUserFromBanned(obj_id target, obj_id player) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "deleteUserFromPermissionsGroup: target passed as null");
            return false;
        }
        if (hasObjVar(target, "permissions_list.banned." + toString(player)))
        {
            removeObjVar(target, "permissions_list.banned." + toString(player));
            return true;
        }
        debugServerConsoleMsg(target, "deleteUserToBanned: player " + toString(player) + " is not on the banned list");
        return false;
    }
    public static int[] getGroupPermissions(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "getGroupPermissions: target passed as null");
            return null;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "getGroupPermissions: group_name passed as null");
            return null;
        }
        String permissionsPath = "permissions_list.groups." + group_name + ".permissions";
        if (hasObjVar(target, permissionsPath))
        {
            return getIntArrayObjVar(target, permissionsPath);
        }
        return null;
    }
    public static int[] getUserPermissions(obj_id target, obj_id player) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "getUserPermissions: target passed as null");
            return null;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "getUserPermissions: player passed as null");
            return null;
        }
        String permissionsPath = "permissions_list.users." + toString(player);
        if (hasObjVar(target, permissionsPath))
        {
            return getIntArrayObjVar(target, permissionsPath);
        }
        return new int[PERMISSIONS_ARRAY_SIZE];
    }
    public static boolean grantGroupPermission(obj_id target, String group_name, int idx) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "grantGroupPermission: target passed as null");
            return false;
        }
        if ((idx < 0) || (idx > (utils.BIT_LIST_SIZE * PERMISSIONS_ARRAY_SIZE)))
        {
            debugServerConsoleMsg(target, "grantGroupPermission: passed idx is outside the valid range");
            return false;
        }
        if (group_name != null)
        {
            String groupPath = "permissions_list.groups." + group_name;
            int[] permissions = new int[PERMISSIONS_ARRAY_SIZE];
            permissions = getGroupPermissions(target, group_name);
            if (permissions != null)
            {
                int index = idx / 32;
                int bitPos = idx % 32;
                int bitVal = 0;
                int permissionsValue = utils.setBit(bitVal, bitPos);
                if (permissionsValue != 0)
                {
                    permissions[index] |= permissionsValue;
                }
                else 
                {
                    debugServerConsoleMsg(target, "grantGroupPermission: error returned from setBit!");
                }
                if (permissions != null && permissions.length > 0)
                {
                    setObjVar(target, groupPath + ".permissions", permissions);
                }
                else 
                {
                    debugServerConsoleMsg(target, "grantGroupPermission: permissions was being set as null in objvar " + groupPath + ".permissions");
                    return false;
                }
                obj_var_list lstMembers = getObjVarList(target, groupPath + ".group_members");
                if (lstMembers != null)
                {
                    int numMembers = lstMembers.getNumItems();
                    for (int i = 0; i < numMembers; i++)
                    {
                        obj_id player = utils.stringToObjId((lstMembers.getObjVar(i)).getName());
                        addToUserTally(target, player, group_name);
                    }
                }
                return true;
            }
            debugServerConsoleMsg(target, "grantGroupPermission: permissions was returned by getGroupPermissions as null");
            return false;
        }
        debugServerConsoleMsg(target, "grantGroupPermission: function was passed a null variable");
        return false;
    }
    public static boolean revokeGroupPermission(obj_id target, String group_name, int idx) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "revokeGroupPermission: target passed as null");
            return false;
        }
        if ((idx < 0) || (idx > (utils.BIT_LIST_SIZE * PERMISSIONS_ARRAY_SIZE)))
        {
            debugServerConsoleMsg(target, "revokeGroupPermission: passed idx is outside the valid range");
            return false;
        }
        if (group_name != null)
        {
            String groupPath = "permissions_list.groups." + group_name;
            int[] permissions = new int[PERMISSIONS_ARRAY_SIZE];
            permissions = getGroupPermissions(target, group_name);
            if (permissions != null && permissions.length > 0)
            {
                int index = idx / 32;
                int bitPos = idx % 32;
                permissions[index] = utils.clearBit(permissions[index], bitPos);
                if (permissions[index] == ~0)
                {
                    debugServerConsoleMsg(target, "revokeGroupPermission: error returned from setBit!");
                }
                setObjVar(target, "permissions_list.groups." + group_name + ".permissions", permissions);
                obj_var_list lstMembers = getObjVarList(target, groupPath + ".group_members");
                if (lstMembers != null)
                {
                    int numMembers = lstMembers.getNumItems();
                    for (int i = 0; i < numMembers; i++)
                    {
                        obj_id player = utils.stringToObjId((lstMembers.getObjVar(i)).getName());
                        updateUserTally(target, player);
                    }
                }
                return true;
            }
            debugServerConsoleMsg(target, "revokeGroupPermission: permissions was returned by getGroupPermissions as null");
            return false;
        }
        debugServerConsoleMsg(target, "revokeGroupPermission: function was passed a null variable");
        return false;
    }
    public static boolean checkGroupPermissionIndex(obj_id target, String group_name, int idx) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "checkGroupPermissionIndex: target passed as null");
            return false;
        }
        if ((idx < 0) || (idx > (utils.BIT_LIST_SIZE * PERMISSIONS_ARRAY_SIZE)))
        {
            debugServerConsoleMsg(target, "grantGroupPermission: passed idx is outside the valid range");
            return false;
        }
        if (group_name != null)
        {
            int[] permissions = new int[PERMISSIONS_ARRAY_SIZE];
            permissions = getGroupPermissions(target, group_name);
            if (permissions != null)
            {
                int index = idx / 32;
                int bitPos = idx % 32;
                return utils.checkBit(permissions[index], bitPos);
            }
            debugServerConsoleMsg(target, "checkGroupPermissionIndex: permissions were returned by getGroupPermissions as null");
            return false;
        }
        debugServerConsoleMsg(target, "checkGroupPermissionIndex: function was passed a null variable");
        return false;
    }
    public static boolean checkUserPermissionIndex(obj_id target, obj_id player, int idx) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "checkUserPermissionIndex: target passed as null");
            return false;
        }
        if ((idx < 0) || (idx > (utils.BIT_LIST_SIZE * PERMISSIONS_ARRAY_SIZE)))
        {
            debugServerConsoleMsg(target, "checkUserPermissionIndex: passed idx is outside the valid range");
            return false;
        }
        if (player != null)
        {
            int[] permissions = new int[PERMISSIONS_ARRAY_SIZE];
            permissions = getUserPermissions(target, player);
            if (permissions != null)
            {
                int index = idx / 32;
                int bitPos = idx % 32;
                int bitVal = (int)Math.pow(2, bitPos);
                if ((permissions[index] & bitVal) == bitVal)
                {
                    return true;
                }
                return false;
            }
            debugServerConsoleMsg(target, "checkUserPermissionIndex: permissions were returned by getUserPermissions as null");
            return false;
        }
        debugServerConsoleMsg(target, "checkUserPermissionIndex: function was passed a null variable");
        return false;
    }
    public static boolean hasPermission(obj_id target, String groupName, int idx) throws InterruptedException
    {
        return checkGroupPermissionIndex(target, groupName, idx);
    }
    public static boolean hasPermission(obj_id target, obj_id player, int idx) throws InterruptedException
    {
        if (isIdValid(player) && isGod(player))
        {
            return true;
        }
        return checkUserPermissionIndex(target, player, idx);
    }
    public static boolean addToUserTally(obj_id target, obj_id player, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "addToUserTally: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "addToUserTally: player passed as null");
            return false;
        }
        if ((group_name == null) || (group_name.equals("")))
        {
            debugServerConsoleMsg(target, "addToUserTally: group_name passed as null");
            return false;
        }
        int[] groupPermissions = new int[PERMISSIONS_ARRAY_SIZE];
        groupPermissions = getGroupPermissions(target, group_name);
        int[] userPermissions = new int[PERMISSIONS_ARRAY_SIZE];
        if (hasObjVar(target, "permissions_list.users." + toString(player)))
        {
            userPermissions = getUserPermissions(target, player);
        }
        if ((groupPermissions != null) && (userPermissions != null))
        {
            for (int i = 0; i < PERMISSIONS_ARRAY_SIZE; i++)
            {
                userPermissions[i] |= groupPermissions[i];
            }
            if (userPermissions.length > 0)
            {
                setObjVar(target, "permissions_list.users." + toString(player), userPermissions);
                return true;
            }
            else 
            {
                return false;
            }
        }
        debugServerConsoleMsg(target, "addToUserTally: at least one set of permissions was returned as null");
        return false;
    }
    public static boolean updateUserTally(obj_id target, obj_id player) throws InterruptedException
    {
        debugServerConsoleMsg(target, "updateUserTally: entering updateUserTally");
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "updateUserTally: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "updateUserTally: player passed as null");
            return false;
        }
        int[] userPermissions = new int[PERMISSIONS_ARRAY_SIZE];
        if (hasObjVar(target, "permissions_list.groups"))
        {
            obj_var_list groupsList = getObjVarList(target, "permissions_list.groups");
            int numGroups = 0;
            if (groupsList != null)
            {
                numGroups = groupsList.getNumItems();
            }
            if (numGroups == 0)
            {
                debugServerConsoleMsg(target, "updateUserTally: no groups exist - deleting user!");
                removeObjVar(target, "permissions_list.users");
                setObjVarList(target, "permissions_list.users");
                return true;
            }
            int inGroup = 0;
            for (int i = 0; i < numGroups; i++)
            {
                obj_var tmpGroup = groupsList.getObjVar(i);
                if (tmpGroup == null)
                {
                    debugServerConsoleMsg(target, "updateUserTally: was returned a null obj_var below groups!");
                }
                if (tmpGroup instanceof script.obj_var_list)
                {
                    obj_var_list lstGroup = (obj_var_list)tmpGroup;
                    if (lstGroup.hasObjVar("group_members." + toString(player)))
                    {
                        inGroup++;
                        obj_var groupPermissions = lstGroup.getObjVar("permissions");
                        if (groupPermissions == null)
                        {
                            debugServerConsoleMsg(target, "updateUserTally: a permissions array was returned as null");
                            return false;
                        }
                        int[] permissionsArray = new int[PERMISSIONS_ARRAY_SIZE];
                        permissionsArray = groupPermissions.getIntArrayData();
                        for (int n = 0; n < PERMISSIONS_ARRAY_SIZE; n++)
                        {
                            userPermissions[n] |= permissionsArray[n];
                            debugServerConsoleMsg(target, "updateUserTally: updated array[" + n + "] for group " + lstGroup.getName());
                        }
                    }
                }
            }
            if (inGroup > 0 && userPermissions.length > 0)
            {
                setObjVar(target, "permissions_list.users." + toString(player), userPermissions);
            }
            else 
            {
                removeObjVar(target, "permissions_list.users." + toString(player));
            }
            return true;
        }
        debugServerConsoleMsg(target, "updateUserTally: the target object lacks obj_var permissions_list.groups");
        return false;
    }
    public static boolean isBanned(obj_id target, obj_id player) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "checkBannedList: target passed as null");
            return false;
        }
        if (!isIdValid(player))
        {
            debugServerConsoleMsg(target, "checkBannedList: player passed as null");
            return false;
        }
        return hasObjVar(target, "permissions_list.banned." + toString(player));
    }
    public static String[] listGroupMemberNames(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "listGroupMemberNames: target passed as null");
            return null;
        }
        if (group_name != null)
        {
            if (hasObjVar(target, "permissions_list.groups." + group_name + ".group_members"))
            {
                obj_var_list groupMembers = getObjVarList(target, "permissions_list.groups." + group_name + ".group_members");
                if (groupMembers == null)
                {
                    return null;
                }
                int numMembers = groupMembers.getNumItems();
                String[] memberNames = new String[numMembers];
                for (int i = 0; i < numMembers; i++)
                {
                    memberNames[i] = (groupMembers.getObjVar(i)).getStringData();
                }
                return memberNames;
            }
            debugServerConsoleMsg(target, "listGroupMemberNames: members list for specified group does not exist");
            return null;
        }
        debugServerConsoleMsg(target, "listGroupMemberNames: function was passed a null group_name pointer");
        return null;
    }
    public static String[] listGroupMemberIds(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "listGroupMemberIds: target passed as null");
            return null;
        }
        if (group_name != null)
        {
            if (hasObjVar(target, "permissions_list.groups." + group_name + ".group_members"))
            {
                obj_var_list groupMembers = getObjVarList(target, "permissions_list.groups." + group_name + ".group_members");
                if (groupMembers == null)
                {
                    return null;
                }
                int numMembers = groupMembers.getNumItems();
                if (numMembers == 0)
                {
                    debugServerConsoleMsg(target, "obj_id " + toString(target) + " Specified group list is empty!");
                    return null;
                }
                String[] memberIds = new String[numMembers];
                for (int i = 0; i < numMembers; i++)
                {
                    memberIds[i] = (groupMembers.getObjVar(i)).getName();
                }
                return memberIds;
            }
            debugServerConsoleMsg(target, "listGroupMemberIds: members list for specified group does not exist");
            return null;
        }
        debugServerConsoleMsg(target, "listGroupMemberIds: function was passed a null group_name pointer");
        return null;
    }
    public static String[] listBannedIds(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "listBannedIds: target passed as null");
            return null;
        }
        if (hasObjVar(target, "permissions_list.banned"))
        {
            obj_var_list bannedMembers = getObjVarList(target, "permissions_list.banned");
            if (bannedMembers == null)
            {
                return null;
            }
            int numMembers = bannedMembers.getNumItems();
            if (numMembers == 0)
            {
                debugServerConsoleMsg(target, "obj_id " + toString(target) + " Banned list is empty!");
                return null;
            }
            String[] memberIds = new String[numMembers];
            for (int i = 0; i < numMembers; i++)
            {
                memberIds[i] = (bannedMembers.getObjVar(i)).getName();
            }
            return memberIds;
        }
        debugServerConsoleMsg(target, "listBannedIds: object does not have a banned list");
        return null;
    }
    public static int groupMemberCount(obj_id target, String group_name) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "groupMemberCount: target passed as null");
            return -1;
        }
        if ((group_name != null) && (!group_name.equals("")))
        {
            String gPath = "permissions_list.groups." + group_name;
            if (hasObjVar(target, gPath))
            {
                obj_var_list groupMembers = getObjVarList(target, gPath + ".group_members");
                if (groupMembers != null)
                {
                    return groupMembers.getNumItems();
                }
                else 
                {
                    return 0;
                }
            }
            debugServerConsoleMsg(target, "groupMemberCount: objvar list for specified group (" + group_name + ") does not exist");
            return -1;
        }
        debugServerConsoleMsg(target, "groupMemberCount: function was passed a null group_name pointer");
        return -1;
    }
    public static boolean initializePermissions(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            debugServerConsoleMsg(target, "initializePermissions: target passed as null");
            return false;
        }
        String basePath = "permissions_list.groups.guest.";
        setObjVarList(target, basePath + "group_members");
        int[] permissionsArray = new int[PERMISSIONS_ARRAY_SIZE];
        setObjVar(target, basePath + "permissions", permissionsArray);
        setObjVarList(target, "permissions_list.users");
        setObjVarList(target, "permissions_list.banned");
        return true;
    }
}
