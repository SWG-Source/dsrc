package script.systems.instance;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.gm;
import script.library.instance;
import script.library.locations;
import script.library.trial;
import script.library.utils;

public class instance_manager extends script.base_script
{
    public instance_manager()
    {
    }
    public static boolean LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        instance.registerInstance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        instance.registerInstance(self);
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String name, int request_id, String[] element_name_list, dictionary[] data, int lock_key) throws InterruptedException
    {
        if (manage_name.startsWith("instance_manager-" + instance.getInstanceName(self)))
        {
            dictionary dict = new dictionary();
            dict.put("instance_id", self);
            dict.put("instance_name", instance.getInstanceName(self));
            dict.put("owner", instance.getInstanceOwner(self));
            dict.put("groupOne", instance.getPlayerList(self, 1));
            dict.put("groupTwo", instance.getPlayerList(self, 2));
            dict.put("buildout_area", locations.getBuildoutAreaName(self));
            dict.put("buildout_row", locations.getBuildoutAreaRow(self));
            dict.put("start_time", instance.getInstanceStartTime(self));
            dict.put("team", instance.getInstanceTeam(self));
            location loc = getLocation(self);
            dict.put("scene", loc.area);
            dict.put("position_x", loc.x);
            dict.put("position_y", loc.y);
            dict.put("position_z", loc.z);
            name = instance.getInstanceName(self) + "_" + self;
            obj_id planetObject = getPlanetByName(getLocation(self).area);
            utils.setScriptVar(planetObject, "" + locations.getBuildoutAreaName(self) + "_" + locations.getBuildoutAreaRow(self), self);
            replaceClusterWideData(manage_name, name, dict, true, lock_key);
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        return SCRIPT_CONTINUE;
    }
    public int requestEnterPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-had null params, exiting scripts.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int team = params.getInt("team");
        obj_id passed_owner = params.getObjId("owner");
        if (instance.testNextPlayerInstanceCap(self, team))
        {
            dictionary dict = new dictionary();
            dict.put("fail_reason", instance.FAIL_INSTANCE_FULL);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-testNextPlayerInstanceCap return true for player " + getFirstName(player) + "(" + player + ") sending messageTo 'instanceFailureMessage' to player.");
            messageTo(player, "instanceFailureMessage", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-!isIdValid(instance.getInstanceOwner(self)) for player " + getFirstName(player) + "(" + player + ") was " + (!isIdValid(instance.getInstanceOwner(self))) + " if this is TRUE we fail movement");
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-instance.getInstanceOwner(self) == self for player " + getFirstName(player) + "(" + player + ") was " + (instance.getInstanceOwner(self) == self) + " if this is TRUE we fail movement");
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-passed_owner != instance.getInstanceOwner(self) for player " + getFirstName(player) + "(" + player + ") was " + (passed_owner != instance.getInstanceOwner(self)) + " if this is TRUE we fail movement");
        if (!isIdValid(instance.getInstanceOwner(self)) || instance.getInstanceOwner(self) == self || passed_owner != instance.getInstanceOwner(self))
        {
            dictionary dict = new dictionary();
            dict.put("fail_reason", instance.FAIL_INSTANCE_CLOSED);
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-one of the cases were true for player " + getFirstName(player) + "(" + player + ") sending messageTo 'instanceFailureMessage'");
            messageTo(player, "instanceFailureMessage", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        instance.addToPlayerList(self, player, team);
        params.put("location", getLocation(self));
        params.put("instance_id", self);
        params.put("instance_name", instance.getInstanceName(self));
        params.put("owner", instance.getInstanceOwner(self));
        params.put("start_time", instance.getInstanceStartTime(self));
        params.put("join_code", 1);
        params.put("debug_playerId", player);
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-player " + getFirstName(player) + "(" + player + ") passed all checks, calling messageTo 'movePlayerToInstance'. Params = " + params);
        if (!messageTo(player, "movePlayerToInstance", params, 0.0f, false, self, "debugMovePlayerToInstanceHandler"))
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "requestEnterPlayer-player " + getFirstName(player) + "(" + player + ") messageTo 'movePlayerToInstance' returned false.");
        }
        return SCRIPT_CONTINUE;
    }
    public int debugMovePlayerToInstanceHandler(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "debugMovePlayerToInstanceHandler-had null params, exiting scripts.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("debug_playerId");
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "debugMovePlayerToInstanceHandler-messageTo 'movePlayerToInstance' couldnt find player " + getFirstName(player) + "(" + player + "). This means we died without sending the player to the instance.");
        return SCRIPT_CONTINUE;
    }
    public int startNewInstance(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "startNewInstance-has a null params, exiting now.");
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int team = params.getInt("team");
        utils.setScriptVar(self, instance.INSTANCE_OWNER, player);
        utils.setScriptVar(self, instance.INSTANCE_START_TIME, getGameTime());
        instance.startInstanceTimer(self);
        instance.addToPlayerList(self, player, team);
        params.put("location", getLocation(self));
        params.put("instance_id", self);
        params.put("instance_name", instance.getInstanceName(self));
        params.put("owner", instance.getInstanceOwner(self));
        params.put("start_time", instance.getInstanceStartTime(self));
        params.put("join_code", 0);
        utils.setScriptVar(self, instance.INSTANCE_TEAM, team);
        CustomerServiceLog(instance.INSTANCE_DEBUG_LOG, "startNewInstance-player " + getFirstName(player) + "(" + player + ") passed checks and messageTo 'movePlayerToInstance' is being sent to them");
        messageTo(player, "movePlayerToInstance", params, 0.0f, false);
        messageTo(self, "beginSpawn", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int endInstanceSession(obj_id self, dictionary params) throws InterruptedException
    {
        String groupOne = instance.getPlayerList(self, 1);
        String groupTwo = instance.getPlayerList(self, 2);
        obj_id[] players = instance.getPlayersInPlayerList(groupOne);
        obj_id[] playersTwo = instance.getPlayersInPlayerList(groupTwo);
        instance.requestExitPlayer(self, players, 1);
        instance.requestExitPlayer(self, playersTwo, 2);
        utils.setScriptVar(self, instance.INSTANCE_OWNER, self);
        utils.setScriptVar(self, instance.INSTANCE_START_TIME, -1);
        utils.setScriptVar(self, instance.PLAYER_LIST + "_" + 1, "none");
        utils.setScriptVar(self, instance.PLAYER_LIST + "_" + 2, "none");
        utils.setScriptVar(self, instance.INSTANCE_TEAM, -1);
        utils.removeScriptVar(self, "failed_min_player_check");
        trial.bumpSession(self, "clock");
        utils.setScriptVar(self, instance.INSTANCE_TEAM, -1);
        instance.registerInstance(self);
        messageTo(self, "cleanupSpawn", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int removePlayerFromList(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player_id");
        return SCRIPT_CONTINUE;
    }
    public int startClock(obj_id self, dictionary params) throws InterruptedException
    {
        int instance_time = instance.getInstanceDuration(self);
        dictionary dict = trial.getSessionDict(self, "clock");
        dict.put("instance_time", instance_time);
        if (instance_time <= 300)
        {
            messageTo(self, "handleClockTic", dict, 0.0f, false);
        }
        else 
        {
            dict.put("instance_time", instance_time - 300);
            messageTo(self, "handleClockTic", dict, 300.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int clockOverride(obj_id self, dictionary params) throws InterruptedException
    {
        int new_time = params.getInt("new_time");
        trial.bumpSession(self, "clock");
        dictionary dict = trial.getSessionDict(self, "clock");
        messageTo(self, "handleClockTic", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleClockTic(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "clock"))
        {
            return SCRIPT_CONTINUE;
        }
        int time = params.getInt("instance_time");
        obj_id[] players = instance.getPlayersInInstanceArea(self);
        dictionary dict = trial.getSessionDict(self, "clock");
        if (time < 1)
        {
            dict.put("fail_reason", instance.INSTANCE_TIMEOUT);
            if (players != null && players.length > 0)
            {
                utils.messageTo(players, "instanceFailureMessage", dict, 0.0f, false);
            }
            instance.closeInstance(self);
            return SCRIPT_CONTINUE;
        }
        if(players != null)
            dict.put("player", players);
        messageTo(self, "validatePlayer", dict, 0.0f, false);
        int minPlayers = instance.getMinPlayers(self);
        int numPlayers = instance.getCurrentPopulation(self);
        if (numPlayers < minPlayers)
        {
            boolean godPresent = false;
            if (players != null && players.length > 0)
            {
                for (int i = 0; i < players.length; i++)
                {
                    godPresent |= isGod(players[i]);
                    godPresent |= hasObjVar(players[i], "testingHoth");
                }
            }
            int lastCheck = utils.getIntScriptVar(self, "failed_min_player_check");
            if (!godPresent)
            {
                dict.put("player_low_limit", minPlayers);
                dict.put("fail_reason", instance.FAIL_WARN_TOO_FEW);
                utils.messageTo(players, "instanceFailureMessage", dict, 0.0f, false);
                lastCheck++;
                utils.setScriptVar(self, "failed_min_player_check", lastCheck);
            }
            else 
            {
                utils.removeScriptVar(self, "failed_min_player_check");
            }
            if (lastCheck >= 3)
            {
                dict.put("fail_reason", instance.FAIL_INSTANCE_FEW_PLAYERS);
                utils.messageTo(players, "instanceFailureMessage", dict, 0.0f, false);
                instance.closeInstance(self);
                return SCRIPT_CONTINUE;
            }
        }
        dictionary clockData = instance.getNextClockTic(time);
        int nextTic = clockData.getInt("nextTic");
        int timeRemaining = clockData.getInt("timeRemaining");
        if (timeRemaining == 0)
        {
            instance.sendSoonToCloseWarining(self, nextTic, players);
        }
        dict.put("instance_time", timeRemaining);
        messageTo(self, "handleClockTic", dict, nextTic, false);
        return SCRIPT_CONTINUE;
    }
    public int validatePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        boolean validated = true;
        int reason = -1;
        if (!isIdValid(player) || !exists(player))
        {
            doLogging("validatePlayer", "player is invalid or does not exist");
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(instance.getInstanceOwner(self)) || instance.getInstanceOwner(self) == self)
        {
            validated = false;
            reason = instance.FAIL_INSTANCE_CLOSED;
        }
        if (!instance.isPlayerInPlayerList(player, instance.getPlayerList(self)) && !instance.isPlayerInPlayerList(player, instance.getPlayerList(self, 2)))
        {
            validated = false;
            reason = instance.FAIL_NOT_MEMBER;
        }
        if (instance.getCurrentPopulation(self) > instance.getInstancePlayerCap(self))
        {
            validated = false;
            reason = instance.FAIL_INSTANCE_FULL;
        }
        if (utils.hasScriptVar(player, gm.INSTANCE_AUTH))
        {
            validated = true;
            return SCRIPT_CONTINUE;
        }
        if (!validated)
        {
            dictionary dict = new dictionary();
            dict.put("fail_reason", reason);
            messageTo(player, "instanceFailureMessage", dict, 0.0f, false);
            instance.requestExitPlayer(instance.getInstanceName(self), player, 1);
            instance.removeFromPlayerIdList(self, player);
        }
        else 
        {
            instance.addToPlayerIdList(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/instance_manager/" + section, message);
        }
    }
}
