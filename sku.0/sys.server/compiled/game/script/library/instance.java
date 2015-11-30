package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.prose;
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;

public class instance extends script.base_script
{
    public instance()
    {
    }
    public static final String INSTANCE_DATATABLE = "datatables/instance/instance_datatable.iff";
    public static final String REQUEST_TYPE = "instance.request_name";
    public static final String REQUEST_TEAM = "instance.request_team";
    public static final String REQUEST_TRIGGER = "instance.request_trigger";
    public static final String INSTANCE_OWNER = "instance_data.instance_owner";
    public static final String INSTANCE_START_TIME = "instance_data.start_time";
    public static final String PLAYER_LIST = "instance_data.player_list";
    public static final String PLAYER_ID_LIST = "instance_data.player_obj_id_list";
    public static final String INSTANCE_TEAM = "instance_data.team";
    public static final String MIN_PLAYERS = "instance_data.min_players";
    public static final String PLAYER_INSTANCE = "instance_player_data";
    public static final String PLAYER_INSTANCE_PROTECTED = "instance_player_protected_data";
    public static final String REQUEST_BUFF = "instance_request_pending";
    public static final String INSTANCE_DEBUG_LOG = "instance-debug";
    public static final int REQUEST_INVALID = -1;
    public static final int REQUEST_MOVETO = 0;
    public static final int REQUEST_CLOSE = 1;
    public static final int RESET_NONE = 0;
    public static final int RESET_DAILY = 1;
    public static final int FAIL_INSTANCE_FULL = 0;
    public static final int FAIL_NOT_MEMBER = 1;
    public static final int INSTANCE_TIMEOUT = 2;
    public static final int FAIL_INVALID_LOCKOUT = 3;
    public static final int FAIL_INSTANCE_CLOSED = 4;
    public static final int FAIL_INSTANCE_FEW_PLAYERS = 5;
    public static final int FAIL_WARN_TOO_FEW = 6;
    public static final string_id SID_UNDER_FIVE_MINUTE_WARNING = new string_id("instance", "five_minute_warning");
    public static void registerInstance(obj_id instance_id) throws InterruptedException
    {
        registerInstance(instance_id, getInstanceName(instance_id));
    }
    public static void registerInstance(obj_id instance_id, String instanceName) throws InterruptedException
    {
        getClusterWideData("instance_manager-" + instanceName, instanceName + "_" + instance_id, true, instance_id);
    }
    public static boolean isInstanceActive(obj_id instance_id) throws InterruptedException
    {
        return false;
    }
    public static boolean isInInstanceArea(obj_id subject) throws InterruptedException
    {
        String area_name = locations.getBuildoutAreaName(subject);
        int area_row = locations.getBuildoutAreaRow(subject);
        obj_id planet = getPlanetByName(getLocation(subject).area);
        String instance_controller = "" + area_name + "_" + area_row;
        obj_id areaInstanceController = utils.hasScriptVar(planet, instance_controller) ? utils.getObjIdScriptVar(planet, instance_controller) : null;
        if (!isIdValid(areaInstanceController))
        {
            validateInstanceScriptsOnPlayer(subject);
            return false;
        }
        return true;
    }
    public static String getInstanceName(obj_id instance_id) throws InterruptedException
    {
        return getStringObjVar(instance_id, "instance_name");
    }
    public static int getInstancePlayerCap(obj_id instance_id) throws InterruptedException
    {
        return getInstancePlayerCap(getInstanceName(instance_id));
    }
    public static int getInstancePlayerCap(String instance_name) throws InterruptedException
    {
        return dataTableGetInt(INSTANCE_DATATABLE, instance_name, "max_players");
    }
    public static int getMinPlayers(obj_id instance_id) throws InterruptedException
    {
        return getMinPlayers(getInstanceName(instance_id));
    }
    public static int getMinPlayers(String instance_name) throws InterruptedException
    {
        return dataTableGetInt(INSTANCE_DATATABLE, instance_name, "min_players");
    }
    public static obj_id getInstanceOwner(obj_id instance_id) throws InterruptedException
    {
        return utils.hasScriptVar(instance_id, INSTANCE_OWNER) ? utils.getObjIdScriptVar(instance_id, INSTANCE_OWNER) : instance_id;
    }
    public static String getPlayerList(obj_id instance_id) throws InterruptedException
    {
        return getPlayerList(instance_id, 1);
    }
    public static String getPlayerList(obj_id instance_id, int groupNumber) throws InterruptedException
    {
        return utils.hasScriptVar(instance_id, PLAYER_LIST + "_" + groupNumber) ? utils.getStringScriptVar(instance_id, PLAYER_LIST + "_" + groupNumber) : "none";
    }
    public static int getInstanceCloseTime(obj_id instance_id) throws InterruptedException
    {
        return getInstanceStartTime(instance_id) + getInstanceDuration(instance_id);
    }
    public static int getInstanceStartTime(obj_id instance_id) throws InterruptedException
    {
        return utils.hasScriptVar(instance_id, INSTANCE_START_TIME) ? utils.getIntScriptVar(instance_id, INSTANCE_START_TIME) : -1;
    }
    public static int getInstanceTeam(obj_id instance_id) throws InterruptedException
    {
        return utils.hasScriptVar(instance_id, INSTANCE_TEAM) ? utils.getIntScriptVar(instance_id, INSTANCE_TEAM) : -1;
    }
    public static int getCurrentPopulation(obj_id instance_id) throws InterruptedException
    {
        obj_id[] players = utils.getPlayersInBuildoutRow(getLocation(instance_id).area, locations.getBuildoutAreaRow(instance_id));
        if (players == null || players.length == 0)
        {
            return 0;
        }
        return players.length;
    }
    public static obj_id[] getPlayersInInstanceArea(obj_id instance_id) throws InterruptedException
    {
        String instance_area = getLocation(instance_id).area;
        if (utils.hasScriptVar(instance_id, "buildoutCoordinates"))
        {
            dictionary coords = utils.getDictionaryScriptVar(instance_id, "buildoutCoordinates");
            if (coords == null)
            {
                utils.removeScriptVar(instance_id, "buildoutCoordinates");
            }
            else 
            {
                return utils.getPlayersInBuildoutDimensions(instance_area, coords.getFloat("x1"), coords.getFloat("x2"), coords.getFloat("z1"), coords.getFloat("z2"));
            }
        }
        int row = locations.getBuildoutAreaRow(instance_id);
        dictionary coords = utils.getCoordinatesInBuildoutRow(instance_area, row);
        if (coords != null)
        {
            utils.setScriptVar(instance_id, "buildoutCoordinates", coords);
        }
        return utils.getPlayersInBuildoutDimensions(instance_area, coords.getFloat("x1"), coords.getFloat("x2"), coords.getFloat("z1"), coords.getFloat("z2"));
    }
    public static obj_id[] getPlayersInInstanceArea(String area, int row) throws InterruptedException
    {
        return utils.getPlayersInBuildoutRow(area, row);
    }
    public static obj_id[] getPlayersByTeamList(obj_id[] allPlayers, String groupList) throws InterruptedException
    {
        obj_id[] listPlayers = getPlayersInPlayerList(groupList);
        Vector validPlayers = new Vector();
        validPlayers.setSize(0);
        for (int i = 0; i < allPlayers.length; i++)
        {
            for (int k = 0; k < listPlayers.length; k++)
            {
                if (allPlayers[i] == listPlayers[k])
                {
                    utils.addElement(validPlayers, allPlayers[i]);
                }
            }
        }
        if (validPlayers == null || validPlayers.size() == 0)
        {
            return null;
        }
        obj_id[] _validPlayers = new obj_id[0];
        if (validPlayers != null)
        {
            _validPlayers = new obj_id[validPlayers.size()];
            validPlayers.toArray(_validPlayers);
        }
        return _validPlayers;
    }
    public static boolean testNextPlayerInstanceCap(obj_id instance_id) throws InterruptedException
    {
        return testNextPlayerInstanceCap(instance_id, 1);
    }
    public static boolean testNextPlayerInstanceCap(obj_id instance_id, int team) throws InterruptedException
    {
        String groupList = getPlayerList(instance_id, team);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-groupList for instance_id (" + instance_id + ") was " + groupList);
        obj_id[] allPlayers = getPlayersInInstanceArea(instance_id);
        if (allPlayers == null || allPlayers.length == 0)
        {
            doLogging("No players currently in instance");
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-array allPlayers returned null or length of 0 for instance_id (" + instance_id + ") this should mean we have no players in the instance. Returning false");
            return false;
        }
        int playerLimit = getInstancePlayerCap(instance_id);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-playerLimit for instance for instance_id (" + instance_id + ") was " + playerLimit);
        int projectedPop = allPlayers.length + 1;
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-projectedPop for instance for instance_id (" + instance_id + ") was " + projectedPop);
        if (projectedPop <= playerLimit)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-projectedPop was less than or equal to playerLimit for instance_id (" + instance_id + "), returning false");
            return false;
        }
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "testNextPlayerInstanceCap-projectedPop was GREATUER than to playerLimit for instance_id (" + instance_id + "), returning TRUE. Which means the player will not get in.");
        return true;
    }
    public static boolean isFlaggedForInstance(obj_id player, dictionary data) throws InterruptedException
    {
        String instance_key = data.getString("key_required");
        if (instance_key.equals("none"))
        {
            return true;
        }
        return hasObjVar(player, PLAYER_INSTANCE_PROTECTED + "." + instance_key);
    }
    public static boolean isFlaggedForInstance(obj_id player, String instance_key) throws InterruptedException
    {
        dictionary dict = dataTableGetRow("datatables/instance/instance_datatable.iff", instance_key);
        if (dict == null || dict.isEmpty())
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isFlaggedForInstance-player " + getFirstName(player) + "(" + player + ") passed in an invalid instance key (" + instance_key + "). Leaving a null dictionary. Returning false");
            return false;
        }
        String key = dict.getString("key_required");
        if (key == null || key.length() <= 0)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isFlaggedForInstance-player " + getFirstName(player) + "(" + player + ") got a null key_required from the datatable");
            return false;
        }
        if (key.equals("none"))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isFlaggedForInstance-player " + getFirstName(player) + "(" + player + ") got a 'none' key_required from the datatable");
            return true;
        }
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isFlaggedForInstance-player " + getFirstName(player) + "(" + player + ") passed all checks returning " + (hasObjVar(player, PLAYER_INSTANCE_PROTECTED + "." + instance_key)));
        return hasObjVar(player, PLAYER_INSTANCE_PROTECTED + "." + instance_key);
    }
    public static void flagPlayerForInstance(obj_id player, String instance_key) throws InterruptedException
    {
        setObjVar(player, PLAYER_INSTANCE_PROTECTED + "." + instance_key, 1);
    }
    public static void removePlayerFlagForInstance(obj_id player, String instance_key) throws InterruptedException
    {
        utils.removeObjVar(player, PLAYER_INSTANCE_PROTECTED + "." + instance_key);
    }
    public static boolean requestInstanceMovement(obj_id player, String instanceName) throws InterruptedException
    {
        return requestInstanceMovement(player, instanceName, 1, "default");
    }
    public static boolean requestInstanceMovement(obj_id player, String instanceName, String defaultTrigger) throws InterruptedException
    {
        return requestInstanceMovement(player, instanceName, 1, defaultTrigger);
    }
    public static boolean requestInstanceMovement(obj_id player, String instanceName, int team, String defaultTrigger) throws InterruptedException
    {
        if (!instance.isFlaggedForInstance(player, instanceName))
        {
            String instance = "@" + new string_id("instance", instanceName);
            prose_package pp = new prose_package();
            pp.other.set(instance);
            pp.stringId = new string_id("instance", "no_key");
            sendSystemMessageProse(player, pp);
            return false;
        }
        if (isSpaceScene())
        {
            obj_id ship = trial.getTop(player);
            obj_id pilot = space_utils.getPilotId(ship);
            if (pilot == player)
            {
                obj_id[] crew = space_utils.getAllPlayersInShip(ship);
                if (crew.length > 1)
                {
                    sendSystemMessage(player, new string_id("instance", "nomove_ship_full"));
                    return false;
                }
            }
        }
        if (isRequestPending(player))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-player " + getFirstName(player) + "(" + player + ") has a request pending already");
            sendSystemMessage(player, new string_id("instance", "instance_request_pending"));
            return false;
        }
        dictionary lockoutData = getLockoutData(player, instanceName);
        if (lockoutData != null && !lockoutData.isEmpty())
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-LockoutData was NOT empty for player " + getFirstName(player) + "(" + player + ")");
            int endTime = lockoutData.getInt("time");
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-endTime for player " + getFirstName(player) + "(" + player + ") is " + endTime);
            obj_id instance_id = lockoutData.getObjId("instance_id");
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-instance_id for player " + getFirstName(player) + "(" + player + ") is " + instance_id);
            obj_id owner = lockoutData.getObjId("owner");
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-owner for player " + getFirstName(player) + "(" + player + ") is " + owner);
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-Trying to bypass entry for " + getName(player) + " to " + instanceName + ". Owner/Team: " + owner + "/" + team);
            movePlayerToInstance(player, instance_id, instanceName, owner, false, team);
            LOG("doLogging", "Trying to bypass entry for " + getName(player) + " to " + instanceName + ". Owner/Team: " + owner + "/" + team);
            return true;
        }
        utils.setScriptVar(player, REQUEST_TYPE, REQUEST_MOVETO);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-setting script var '" + REQUEST_TYPE + "' for player " + getFirstName(player) + "(" + player + ") to " + REQUEST_MOVETO);
        utils.setScriptVar(player, REQUEST_TEAM, team);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-setting script var '" + REQUEST_TEAM + "' for player " + getFirstName(player) + "(" + player + ") to " + team);
        utils.setScriptVar(player, REQUEST_TRIGGER, defaultTrigger);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-setting script var '" + REQUEST_TRIGGER + "' for player " + getFirstName(player) + "(" + player + ") to " + defaultTrigger);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "requestInstanceMovement-LockoutData WAS empty for player " + getFirstName(player) + "(" + player + "). After setting appropriate scriptvars we are now requesting clusterWide Data");
        getClusterWideData("instance_manager-" + instanceName, "*", true, player);
        setRequestPending(player);
        return true;
    }
    public static int getInstanceRequestType(obj_id player) throws InterruptedException
    {
        return utils.hasScriptVar(player, REQUEST_TYPE) ? utils.getIntScriptVar(player, REQUEST_TYPE) : REQUEST_INVALID;
    }
    public static int getInstanceRequestTeam(obj_id player) throws InterruptedException
    {
        return utils.hasScriptVar(player, REQUEST_TEAM) ? utils.getIntScriptVar(player, REQUEST_TEAM) : REQUEST_INVALID;
    }
    public static boolean isPlayerOwnerGroupList(obj_id player, obj_id owner, obj_id instance_id, String groupList, int team, int instance_team) throws InterruptedException
    {
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList-Start for player " + getFirstName(player) + "(" + player + ").");
        if (team != instance_team)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList- (team != instance_team) for player " + getFirstName(player) + "(" + player + "). Returning False");
            return false;
        }
        if (!isIdValid(owner) || owner == instance_id)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList- (!isIdValid(owner) || owner == instance_id) for player " + getFirstName(player) + "(" + player + "). Returning false");
            return false;
        }
        if (player == owner)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList- (player == owner) for player " + getFirstName(player) + "(" + player + "). Returning false");
            return true;
        }
        obj_id groupId = getGroupObject(player);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList- groupId for player " + getFirstName(player) + "(" + player + ") is " + groupId + ".");
        if (isIdValid(groupId))
        {
            obj_id[] members = getGroupMemberIds(groupId);
            for (int i = 0; i < members.length; i++)
            {
                CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList-members[" + i + "] for player " + getFirstName(player) + "(" + player + ")'s group is " + getFirstName(members[i]) + "(" + members[i] + ").");
                if (isIdValid(members[i]) && (members[i] == owner || isPlayerInPlayerList(members[i], groupList)))
                {
                    CustomerServiceLog(INSTANCE_DEBUG_LOG, "isPlayerOwnerGroupList-members[" + i + "] for player " + getFirstName(player) + "(" + player + ")'s group is valid and is either owner(" + (members[i] == owner) + ") or isPlayerInPlayerList(" + (isPlayerInPlayerList(members[i], groupList)) + "). Returning TRUE.");
                    return true;
                }
            }
        }
        return false;
    }
    public static void movePlayerToInstance(obj_id player, obj_id dungeon, String instance_name, obj_id owner, boolean startInstance) throws InterruptedException
    {
        movePlayerToInstance(player, dungeon, instance_name, owner, startInstance, 1);
    }
    public static void movePlayerToInstance(obj_id player, obj_id instance_id, String instance_name, obj_id owner, boolean startInstance, int team) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("team", team);
        String defaultTrigger = utils.hasScriptVar(player, REQUEST_TRIGGER) ? utils.getStringScriptVar(player, REQUEST_TRIGGER) : "default";
        dict.put("defaultTrigger", defaultTrigger);
        dict.put("owner", owner);
        if (!isValidatedByLockoutData(player, instance_id, owner, instance_name))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "movePlayerToInstance-isValidatedByLockoutData returned false for player " + getFirstName(player) + "(" + player + "). Sending messageTo 'instanceFailureMessage'");
            dict.put("fail_reason", instance.FAIL_INVALID_LOCKOUT);
            messageTo(player, "instanceFailureMessage", dict, 0.0f, false);
            return;
        }
        if (startInstance)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "movePlayerToInstance-isValidatedByLockoutData passed for player " + getFirstName(player) + "(" + player + "). Sending messageTo 'startNewInstance' to instance_id " + instance_id);
            messageTo(instance_id, "startNewInstance", dict, 0.0f, false);
        }
        else 
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "movePlayerToInstance-isValidatedByLockoutData passed for player " + getFirstName(player) + "(" + player + "). Sending messageTo 'requestEnterPlayer' to instance_id " + instance_id);
            messageTo(instance_id, "requestEnterPlayer", dict, 0.0f, false);
        }
    }
    public static void addToPlayerList(obj_id instance, obj_id player) throws InterruptedException
    {
        addToPlayerList(instance, player, 1);
    }
    public static void addToPlayerList(obj_id instance, obj_id player, int group) throws InterruptedException
    {
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "addToPlayerList-Start for player " + getFirstName(player) + "(" + player + ").");
        String playerList = getPlayerList(instance, group);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "addToPlayerList-playerList for player " + getFirstName(player) + "(" + player + ") " + playerList);
        if (playerList.equals("none"))
        {
            utils.setScriptVar(instance, PLAYER_LIST + "_" + group, "" + player);
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "addToPlayerList-playerList was 'none' for player " + getFirstName(player) + "(" + player + "). setting scriptvar and bailing.");
            return;
        }
        if (!isPlayerInPlayerList(player, playerList))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "addToPlayerList-Player " + getFirstName(player) + "(" + player + ") was not in the player list. Adding them to scriptvar.");
            utils.setScriptVar(instance, PLAYER_LIST + "_" + group, playerList.concat("_" + player));
        }
    }
    public static void removeFromPlayerList(obj_id instance_id, obj_id player) throws InterruptedException
    {
        removeFromPlayerList(instance_id, player, 1);
    }
    public static void removeFromPlayerList(obj_id instance_id, obj_id player, int group) throws InterruptedException
    {
        String playerList = getPlayerList(instance_id, group);
        if (playerList.equals("none"))
        {
            return;
        }
        String[] groupSplit = split(playerList, '_');
        String newList = "";
        for (int i = 0; i < groupSplit.length; i++)
        {
            if (groupSplit[i].equals("" + player))
            {
                continue;
            }
            newList = newList.concat("_" + groupSplit);
        }
        if (newList.equals(""))
        {
            utils.setScriptVar(instance_id, PLAYER_LIST + "_" + group, "none");
            return;
        }
        if (newList.startsWith("_"))
        {
            newList = newList.substring(1);
        }
        utils.setScriptVar(instance_id, PLAYER_LIST + "_" + group, newList);
    }
    public static boolean isPlayerInPlayerList(obj_id player, String groupList) throws InterruptedException
    {
        if (groupList.equals("none"))
        {
            return false;
        }
        String[] groupSplit = split(groupList, '_');
        for (int i = 0; i < groupSplit.length; i++)
        {
            if (utils.stringToObjId(groupSplit[i]) == player)
            {
                return true;
            }
        }
        return false;
    }
    public static obj_id[] getPlayersInPlayerList(String groupList) throws InterruptedException
    {
        if (groupList == null || groupList.equals("none"))
        {
            doLogging("groupList was invalid or none: " + groupList);
            return null;
        }
        String[] parse = split(groupList, '_');
        obj_id[] playerList = new obj_id[parse.length];
        for (int i = 0; i < parse.length; i++)
        {
            playerList[i] = utils.stringToObjId(parse[i]);
        }
        return playerList;
    }
    public static void sendToEnterOne(obj_id player, obj_id instanceId, location instanceLoc, dictionary data) throws InterruptedException
    {
        playerEnterInstance(player, instanceId, instanceLoc, data, "enter_one");
    }
    public static void sendToEnterTwo(obj_id player, obj_id instanceId, location instanceLoc, dictionary data) throws InterruptedException
    {
        playerEnterInstance(player, instanceId, instanceLoc, data, "enter_two");
    }
    public static void playerEnterInstance(obj_id player, obj_id instanceId, location instanceLoc, dictionary data, String enterString) throws InterruptedException
    {
        if (data == null || data.isEmpty())
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-data was null for player " + getFirstName(player) + "(" + player + "), we are returning here");
            return;
        }
        if (instanceLoc == null)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-instanceLoc was null for player " + getFirstName(player) + "(" + player + "), we are returning here");
            return;
        }
        if (enterString == null || enterString.length() <= 0)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-enterString was null for player " + getFirstName(player) + "(" + player + "), we are returning here");
            return;
        }
        String teleportToString = data.getString(enterString);
        String[] parse = split(teleportToString, ',');
        if (parse == null || parse.length < 4)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-parsed enterString was null or length (" + parse.length + ") was less than 4 for player " + getFirstName(player) + "(" + player + "), we are returning here");
            return;
        }
        float locX = utils.stringToFloat(parse[0]);
        float locY = utils.stringToFloat(parse[1]);
        float locZ = utils.stringToFloat(parse[2]);
        String cellName = parse[3];
        if (cellName.equals("none"))
        {
            vector post = new vector(instanceLoc.x + locX, instanceLoc.y + locY, instanceLoc.z + locZ);
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-calling warpPlayer(" + player + ", " + instanceLoc.area + ", " + post.x + ", " + post.y + ", " + post.z + ", obj_id.NULL_ID, " + 0.0f + " , " + 0.0f + " , " + 0.0f + " , 'noCallBack', true) for player " + getFirstName(player) + "(" + player + ")");
            warpPlayer(player, instanceLoc.area, post.x, post.y, post.z, obj_id.NULL_ID, 0.0f, 0.0f, 0.0f, "noCallBack", true);
        }
        else 
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "playerEnterInstance-calling warpPlayer(" + player + ", " + instanceLoc.area + ", " + instanceLoc.x + ", " + instanceLoc.y + ", " + instanceLoc.z + ", " + instanceId + ", " + cellName + ", " + locX + ", " + locY + ", " + locZ + ", 'noCallBack, true) for player " + getFirstName(player) + "(" + player + ")");
            warpPlayer(player, instanceLoc.area, instanceLoc.x, instanceLoc.y, instanceLoc.z, instanceId, cellName, locX, locY, locZ, "noCallBack", true);
        }
    }
    public static void requestExitPlayer(obj_id instance, obj_id[] players, int team) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            requestExitPlayer(instance, players[i], team);
        }
    }
    public static void requestExitPlayer(obj_id instance_id, obj_id player, int team) throws InterruptedException
    {
        String instance_name = instance.getInstanceName(instance_id);
        requestExitPlayer(instance_name, player, team);
    }
    public static void requestExitPlayer(String instance_name, obj_id player) throws InterruptedException
    {
        requestExitPlayer(instance_name, player, 1);
    }
    public static void requestExitPlayer(String instance_name, obj_id player, int team) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player) || !inSameInstanceType(instance_name, player))
        {
            return;
        }
        vehicle.checkForMountAndDismountPlayer(player);
        if (!dataTableOpen(instance.INSTANCE_DATATABLE))
        {
            doLogging("Could not open table");
            return;
        }
        dictionary data = dataTableGetRow(instance.INSTANCE_DATATABLE, instance_name);
        if (data == null)
        {
            doLogging("null data in table");
            return;
        }
        buff.applyBuff(player, "instance_exiting");
        removeInstanceScriptFromPlayer(player, data);
        if (team == 1)
        {
            instance.sendToExitOne(player, data);
        }
        if (team == 2)
        {
            instance.sendToExitTwo(player, data);
        }
    }
    public static boolean inSameInstanceType(String instance_name, obj_id player) throws InterruptedException
    {
        String test_name = locations.getBuildoutAreaName(player);
        if (test_name == null || test_name.equals(""))
        {
            return false;
        }
        return test_name.equals(instance_name);
    }
    public static void sendToExitOne(obj_id player, dictionary data) throws InterruptedException
    {
        String exitLoc = data.getString("exit_one");
        String[] parse = split(exitLoc, ',');
        float locX = utils.stringToFloat(parse[0]);
        float locY = utils.stringToFloat(parse[1]);
        float locZ = utils.stringToFloat(parse[2]);
        warpPlayer(player, parse[3], locX, locY, locZ, null, 0.0f, 0.0f, 0.0f, "noCallBack", true);
    }
    public static void sendToExitTwo(obj_id player, dictionary data) throws InterruptedException
    {
        String exitLoc = data.getString("exit_two");
        String[] parse = split(exitLoc, ',');
        float locX = utils.stringToFloat(parse[0]);
        float locY = utils.stringToFloat(parse[1]);
        float locZ = utils.stringToFloat(parse[2]);
        warpPlayer(player, parse[3], locX, locY, locZ, null, 0.0f, 0.0f, 0.0f, "noCallBack", true);
    }
    public static void startInstanceTimer(obj_id instance_id) throws InterruptedException
    {
        messageTo(instance_id, "startClock", null, 0.0f, false);
    }
    public static void closeOwnedInstance(obj_id player, String instanceName) throws InterruptedException
    {
        utils.setScriptVar(player, REQUEST_TYPE, REQUEST_CLOSE);
        getClusterWideData("instance_manager-" + instanceName, instanceName + "*", true, player);
    }
    public static void closeInstance(obj_id owner, dictionary data) throws InterruptedException
    {
        obj_id instance_id = data.getObjId("instance_id");
        messageTo(instance_id, "endInstanceSession", data, 0.0f, false);
    }
    public static void closeInstance(obj_id instance_id) throws InterruptedException
    {
        messageTo(instance_id, "endInstanceSession", null, 0.0f, false);
    }
    public static int getInstanceDuration(obj_id instance_id) throws InterruptedException
    {
        String instance_name = getInstanceName(instance_id);
        return getInstanceDuration(instance_name);
    }
    public static int getInstanceDuration(String instance_name) throws InterruptedException
    {
        dictionary data = dataTableGetRow(instance.INSTANCE_DATATABLE, instance_name);
        return data.getInt("time_limit");
    }
    public static void validateDungeonParticipants(obj_id instance_id) throws InterruptedException
    {
        int buildout_row = locations.getBuildoutAreaRow(instance_id);
        obj_id[] players = utils.getPlayersInBuildoutRow(getLocation(instance_id).area, buildout_row);
        if (players == null || players.length == 0)
        {
            return;
        }
        dictionary dict = new dictionary();
        for (int i = 0; i < players.length; i++)
        {
            dict.put("player", players[i]);
            messageTo(instance_id, "validatePlayer", dict, 0.0f, false);
        }
    }
    public static dictionary getNextClockTic(int time) throws InterruptedException
    {
        dictionary dict = new dictionary();
        float remainder = (float)time / 300.0f;
        if (remainder < 1.0f)
        {
            dict.put("nextTic", time);
            dict.put("timeRemaining", 0);
            return dict;
        }
        dict.put("nextTic", 300);
        dict.put("timeRemaining", time - 300);
        return dict;
    }
    public static void sendSoonToCloseWarining(obj_id instance_id, int timeLeft) throws InterruptedException
    {
        obj_id[] players = getPlayersInInstanceArea(instance_id);
        sendSoonToCloseWarining(instance_id, timeLeft, players);
    }
    public static void sendSoonToCloseWarining(obj_id instance, int timeLeft, obj_id[] players) throws InterruptedException
    {
        prose_package pp = prose.getPackage(SID_UNDER_FIVE_MINUTE_WARNING, timeLeft);
        utils.sendSystemMessageProse(players, pp);
    }
    public static void setClock(obj_id instance_id, int seconds) throws InterruptedException
    {
        trial.bumpSession(instance_id, "clock");
        dictionary dict = trial.getSessionDict(instance_id, "clock");
        dict.put("instance_time", seconds);
        messageTo(instance_id, "handleClockTic", dict, 0.0f, false);
    }
    public static boolean isExclusiveInstance(String instance_name) throws InterruptedException
    {
        dictionary dict = dataTableGetRow(INSTANCE_DATATABLE, instance_name);
        if (dict == null || dict.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "isExclusiveInstance-dictionary was null, we are returning false.");
            return false;
        }
        return dict.getInt("lockoutTimer") != RESET_NONE;
    }
    public static void setResetDataOnPlayer(obj_id player, obj_id instance_id, obj_id owner, String instance_name, int start_time) throws InterruptedException
    {
        dictionary dict = dataTableGetRow(INSTANCE_DATATABLE, instance_name);
        if (dict == null || dict.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "setResetDataOnPlayer-dictionary was null for player " + getFirstName(player) + "(" + player + "), we are returning here.");
            return;
        }
        int resetType = dict.getInt("lockoutTimer");
        switch (resetType)
        {
            case RESET_NONE:
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "setResetDataOnPlayer-resetType was RESET_NONE for player " + getFirstName(player) + "(" + player + "), doing nothing.");
            break;
            case RESET_DAILY:
            int resetAt = getCalendarTime() + secondsUntilNextDailyTime(6, 0, 0);
            setObjVar(player, PLAYER_INSTANCE + "." + instance_name, "" + resetAt + "_" + instance_id + "_" + owner + "_" + start_time);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "setResetDataOnPlayer-resetType was RESET_DAILY, setting objvar " + PLAYER_INSTANCE + "." + instance_name + " to " + resetAt + "_" + instance_id + "_" + owner + "_" + start_time + " on player " + getFirstName(player) + "(" + player + ").");
            break;
        }
    }
    public static boolean isValidatedByLockoutData(obj_id player, obj_id instance_id, obj_id owner, String instance_name) throws InterruptedException
    {
        if (!hasObjVar(player, PLAYER_INSTANCE + "." + instance_name))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-player " + getFirstName(player) + "(" + player + ") Doesnt have objvar " + PLAYER_INSTANCE + "." + instance_name + ". Returning TRUE");
            return true;
        }
        dictionary lockoutData = getLockoutData(player, instance_name);
        if (lockoutData == null)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-lockoutData returned null for player " + getFirstName(player) + "(" + player + "). Returning TRUE");
            return true;
        }
        int endTime = lockoutData.getInt("time");
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-lockoutData.endTime for player " + getFirstName(player) + "(" + player + ") was " + endTime);
        obj_id recInstance = lockoutData.getObjId("instance_id");
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-lockoutData.recInstance for player " + getFirstName(player) + "(" + player + ") was " + recInstance);
        obj_id recOwner = lockoutData.getObjId("owner");
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-lockoutData.recOwner for player " + getFirstName(player) + "(" + player + ") was " + recOwner);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-player " + getFirstName(player) + "(" + player + ") instance_id == recInstance was " + (instance_id == recInstance));
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-player " + getFirstName(player) + "(" + player + ") recOwner == owner was " + (recOwner == owner));
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "isValidatedByLockoutData-player " + getFirstName(player) + "(" + player + ") calling return (" + (instance_id == recInstance) + " && " + (recOwner == owner) + ")");
        return (instance_id == recInstance && recOwner == owner);
    }
    public static dictionary getLockoutData(obj_id player, String instance_name) throws InterruptedException
    {
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-started for player " + getFirstName(player) + "(" + player + "). instance_name was " + instance_name + ".");
        if (!hasObjVar(player, PLAYER_INSTANCE + "." + instance_name))
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") does not have the objvar '" + PLAYER_INSTANCE + "." + instance_name + "'. Returning null.");
            return null;
        }
        String lockoutString = getStringObjVar(player, PLAYER_INSTANCE + "." + instance_name);
        String[] parse = split(lockoutString, '_');
        if (parse.length < 4)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") Parse length on objvar was less than 4. Returning null.");
            return null;
        }
        int time = utils.stringToInt(parse[0]);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") parsed time was " + time + ".");
        obj_id instance_id = utils.stringToObjId(parse[1]);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") parsed instance_id was " + instance_id + ".");
        obj_id owner = utils.stringToObjId(parse[2]);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") parsed owner was " + owner + ".");
        int start_time = utils.stringToInt(parse[3]);
        CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") parsed start_time was " + start_time + ".");
        if (getCalendarTime() >= time)
        {
            CustomerServiceLog(INSTANCE_DEBUG_LOG, "getLockoutData-player " + getFirstName(player) + "(" + player + ") calander time was greater than or equal to parsed time. Removing objvar and returning null.");
            removeObjVar(player, PLAYER_INSTANCE + "." + instance_name);
            return null;
        }
        dictionary dict = new dictionary();
        dict.put("time", time);
        dict.put("instance_id", instance_id);
        dict.put("owner", owner);
        dict.put("instance_name", instance_name);
        dict.put("start_time", start_time);
        return dict;
    }
    public static dictionary[] getAllLockoutData(obj_id player) throws InterruptedException
    {
        int numRows = dataTableGetNumRows(INSTANCE_DATATABLE);
        Vector instanceData = new Vector();
        instanceData.setSize(0);
        for (int i = 0; i < numRows; i++)
        {
            String instanceName = dataTableGetString(INSTANCE_DATATABLE, i, "dungeon");
            if (hasObjVar(player, PLAYER_INSTANCE + "." + instanceName))
            {
                dictionary data = getLockoutData(player, instanceName);
                if (data != null)
                {
                    utils.addElement(instanceData, data);
                }
            }
        }
        if (instanceData == null || instanceData.size() == 0)
        {
            return null;
        }
        dictionary[] _instanceData = new dictionary[0];
        if (instanceData != null)
        {
            _instanceData = new dictionary[instanceData.size()];
            instanceData.toArray(_instanceData);
        }
        return _instanceData;
    }
    public static void attachInstanceScriptsOnPlayer(obj_id player, dictionary data) throws InterruptedException
    {
        if (data == null || data.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "attachInstanceScriptsOnPlayer-data dictionary was null or empty for player " + player + "(" + getPlayerName(player) + "). We are returning here.");
            return;
        }
        String scriptList = data.getString("player_script");
        if (scriptList.equals("none"))
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "attachInstanceScriptsOnPlayer-scriptList was 'none' player " + player + "(" + getPlayerName(player) + "). We are returning here.");
            return;
        }
        String[] parse = split(scriptList, ',');
        if (parse == null || parse.length == 0)
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "attachInstanceScriptsOnPlayer-script parse list was null for player " + player + "(" + getPlayerName(player) + "). We are returning here.");
            return;
        }
        for (int i = 0; i < parse.length; i++)
        {
            if (!hasScript(player, parse[i]))
            {
                CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "attachInstanceScriptsOnPlayer-player " + player + "(" + getPlayerName(player) + ") did not have the script '" + parse[i] + "'. It is being attached now.");
                attachScript(player, parse[i]);
            }
        }
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "attachInstanceScriptsOnPlayer-all scripts have been attached for player " + player + "(" + getPlayerName(player) + ") returning now.");
        return;
    }
    public static void removeInstanceScriptFromPlayer(obj_id player, dictionary data) throws InterruptedException
    {
        String scriptList = data.getString("player_script");
        if (scriptList.equals("none"))
        {
            return;
        }
        String[] parse = split(scriptList, ',');
        if (parse == null || parse.length == 0)
        {
            return;
        }
        for (int i = 0; i < parse.length; i++)
        {
            if (hasScript(player, parse[i]))
            {
                detachScript(player, parse[i]);
            }
        }
    }
    public static void validateInstanceScriptsOnPlayer(obj_id player) throws InterruptedException
    {
        String[] allScripts = dataTableGetStringColumn(INSTANCE_DATATABLE, "player_script");
        for (int i = 0; i < allScripts.length; i++)
        {
            if (allScripts[i].equals("none"))
            {
                continue;
            }
            String[] parse = split(allScripts[i], ',');
            for (int q = 0; q < parse.length; q++)
            {
                if (hasScript(player, parse[q]))
                {
                    detachScript(player, parse[q]);
                }
            }
        }
    }
    public static obj_id getAreaInstanceController(obj_id base_object) throws InterruptedException
    {
        String area_name = locations.getBuildoutAreaName(base_object);
        int area_row = locations.getBuildoutAreaRow(base_object);
        obj_id planet = getPlanetByName(getLocation(base_object).area);
        String instance_controller = "" + area_name + "_" + area_row;
        obj_id areaInstanceController = utils.hasScriptVar(planet, instance_controller) ? utils.getObjIdScriptVar(planet, instance_controller) : null;
        return areaInstanceController;
    }
    public static boolean[] requestInstancePobGroup(obj_id[] players, String instance_name) throws InterruptedException
    {
        obj_id ship = space_transition.getContainingShip(players[0]);
        obj_id pilot = space_utils.getPilotForRealsies(ship);
        boolean[] results = new boolean[players.length];
        int idx = 0;
        for (int i = 0; i < players.length; i++)
        {
            if (players[i] != pilot)
            {
                results[idx] = requestInstanceMovement(players[i], instance_name);
                idx++;
            }
        }
        dictionary dict = new dictionary();
        dict.put("instance_name", instance_name);
        messageTo(pilot, "delayInstanceRequest", dict, 10.0f, false);
        results[idx] = true;
        return results;
    }
    public static void playMusicInInstance(obj_id dungeon, String sound) throws InterruptedException
    {
        obj_id[] players = getPlayersInInstanceArea(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int r = 0; r < players.length; r++)
        {
            playMusic(players[r], sound);
        }
    }
    public static void sendInstanceSystemMessage(obj_id dungeon, string_id message) throws InterruptedException
    {
        obj_id[] players = getPlayersInInstanceArea(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        utils.sendSystemMessage(players, message);
    }
    public static boolean vehicleAllowedInInstance(obj_id instance_id) throws InterruptedException
    {
        return vehicleAllowedInInstance(getInstanceName(instance_id));
    }
    public static boolean vehicleAllowedInInstance(String instance_name) throws InterruptedException
    {
        return dataTableGetInt(INSTANCE_DATATABLE, instance_name, "vehicle_allowed") == 1;
    }
    public static obj_id[] addToPlayerIdList(obj_id instance_id, obj_id player) throws InterruptedException
    {
        Vector playerList = new Vector();
        playerList.setSize(0);
        if (utils.hasScriptVar(instance_id, PLAYER_ID_LIST))
        {
            playerList = utils.getResizeableObjIdArrayScriptVar(instance_id, PLAYER_ID_LIST);
        }
        if (playerList == null || playerList.size() == 0)
        {
            playerList.add(player);
            utils.setScriptVar(instance_id, PLAYER_ID_LIST, playerList);
            obj_id[] _playerList = new obj_id[0];
            if (playerList != null)
            {
                _playerList = new obj_id[playerList.size()];
                playerList.toArray(_playerList);
            }
            return _playerList;
        }
        if (!playerList.contains(player))
        {
            playerList.add(player);
        }
        utils.setScriptVar(instance_id, PLAYER_ID_LIST, playerList);
        obj_id[] _playerList = new obj_id[0];
        if (playerList != null)
        {
            _playerList = new obj_id[playerList.size()];
            playerList.toArray(_playerList);
        }
        return _playerList;
    }
    public static obj_id[] removeFromPlayerIdList(obj_id instance_id, obj_id player) throws InterruptedException
    {
        Vector playerList = new Vector();
        playerList.setSize(0);
        if (utils.hasScriptVar(instance_id, PLAYER_ID_LIST))
        {
            playerList = utils.getResizeableObjIdArrayScriptVar(instance_id, PLAYER_ID_LIST);
        }
        if (playerList == null || playerList.size() == 0)
        {
            return null;
        }
        if (playerList.contains(player))
        {
            playerList.remove(player);
        }
        utils.setScriptVar(instance_id, PLAYER_ID_LIST, playerList);
        obj_id[] _playerList = new obj_id[0];
        if (playerList != null)
        {
            _playerList = new obj_id[playerList.size()];
            playerList.toArray(_playerList);
        }
        return _playerList;
    }
    public static obj_id[] getPlayerIdList(obj_id instance_id) throws InterruptedException
    {
        Vector playerList = utils.hasScriptVar(instance_id, PLAYER_ID_LIST) ? utils.getResizeableObjIdArrayScriptVar(instance_id, PLAYER_ID_LIST) : null;
        obj_id[] newList = new obj_id[0];
        if (playerList != null)
        {
            newList = new obj_id[playerList.size()];
            playerList.toArray(newList);
        }
        obj_id[] _playerList = new obj_id[0];
        if (playerList != null)
        {
            _playerList = new obj_id[playerList.size()];
            playerList.toArray(_playerList);
        }
        return _playerList;
    }
    public static Vector getResizeablePlayerIdList(obj_id instance_id) throws InterruptedException
    {
        return utils.hasScriptVar(instance_id, PLAYER_ID_LIST) ? utils.getResizeableObjIdArrayScriptVar(instance_id, PLAYER_ID_LIST) : null;
    }
    public static boolean isPlayerInPlayerList(obj_id instance_id, obj_id player) throws InterruptedException
    {
        Vector playerList = getResizeablePlayerIdList(instance_id);
        return playerList.contains(player);
    }
    public static void setRequestPending(obj_id player) throws InterruptedException
    {
        buff.applyBuff(player, REQUEST_BUFF);
    }
    public static void setRequestResolved(obj_id player) throws InterruptedException
    {
        buff.removeBuff(player, REQUEST_BUFF);
    }
    public static boolean isRequestPending(obj_id player) throws InterruptedException
    {
        return buff.hasBuff(player, REQUEST_BUFF);
    }
    public static void doLogging(String message) throws InterruptedException
    {
        LOG("doLogging/library.instance", message);
    }
    public static void doLogging(String section, String message) throws InterruptedException
    {
        LOG("doLogging/library.instance/" + section, message);
    }
}
