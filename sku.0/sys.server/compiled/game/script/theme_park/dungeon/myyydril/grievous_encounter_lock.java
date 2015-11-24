package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.pclib;
import script.library.groundquests;

public class grievous_encounter_lock extends script.base_script
{
    public grievous_encounter_lock()
    {
    }
    public static final String start_cell = "hall55";
    public static final String entry_cell = "hall56";
    public static final String exit_cell = "hall59";
    public static final String[] encounter_cells = 
    {
        "hall56",
        "hall57",
        "hall58"
    };
    public static final String encounter_active = "grievous_lock.isActive";
    public static final String encounter_player = "grievous_lock.isPlayer";
    public static final String encounter_session_current = "grievous_lock.encounter_session_current";
    public static final String encounter_session_count = "grievous_lock.encounter_session_count";
    public static final String encounter_manager = "object/tangible/theme_park/myyydril/myyydril_grievous_encounter_manager.iff";
    public static final String encounter_start = "grievous_lock.encounter_start";
    public static final String encounter_end = "grievous_lock.encounter_end";
    public static final String encounter_lockout_end = "instance_lockout.myyydril_grievous.lockout_end";
    public static final String grace_period_end = "grievous_lock.grace_period_end";
    public static final String god_authorized = "grievous_lock.god_authorized";
    public static final int grace_period = 300;
    public static final int encounter_length = 1860;
    public static final String STF = "dungeon/myyydril";
    public static final string_id not_available = new string_id(STF, "encounter_not_available");
    public static final string_id encounter_begins = new string_id(STF, "encounter_begin");
    public static final string_id thirty_minute_warning = new string_id(STF, "thirty_minute_warning");
    public static final string_id five_minute_warning = new string_id(STF, "five_minute_warning");
    public static final string_id one_minute_warning = new string_id(STF, "one_minute_warning");
    public static final string_id encounter_ending = new string_id(STF, "encounter_end_soon");
    public static final string_id encounter_ended = new string_id(STF, "encounter_ended");
    public static final boolean doLogging = false;
    public void clearEncounterCells(obj_id dungeon) throws InterruptedException
    {
        obj_id[] cellIds = getEncounterCellIds(dungeon);
        for (int i = 0; i < cellIds.length; i++)
        {
            obj_id[] cellContents = getContents(cellIds[i]);
            if (cellContents != null && cellContents.length > 0)
            {
                for (int k = 0; k < cellContents.length; k++)
                {
                    if (isPlayer(cellContents[k]))
                    {
                        ejectPlayersFromEncounter(cellContents[k], dungeon);
                    }
                    if ((getTemplateName(cellContents[k])).equals(encounter_manager))
                    {
                        messageTo(cellContents[k], "handleEndEncounter", null, 0, false);
                    }
                }
            }
        }
    }
    public obj_id[] getPlayersInEncounterArea(obj_id dungeon) throws InterruptedException
    {
        obj_id[] cellIds = getEncounterCellIds(dungeon);
        Vector players = new Vector();
        players.setSize(0);
        for (int i = 0; i < cellIds.length; i++)
        {
            obj_id[] cellContents = getContents(cellIds[i]);
            if (cellContents != null && cellContents.length > 0)
            {
                for (int k = 0; k < cellContents.length; k++)
                {
                    if (isPlayer(cellContents[k]))
                    {
                        players = utils.addElement(players, cellContents[k]);
                    }
                }
            }
        }
        if (players == null)
        {
            return null;
        }
        obj_id[] convertedArray = new obj_id[0];
        if (players != null)
        {
            convertedArray = new obj_id[players.size()];
            players.toArray(convertedArray);
        }
        if (convertedArray.length > 0)
        {
            return convertedArray;
        }
        else 
        {
            return null;
        }
    }
    public obj_id[] getEventPlayersInDungeon(obj_id dungeon) throws InterruptedException
    {
        obj_id[] cellIds = getCellIds(dungeon);
        Vector eventPlayers = new Vector();
        eventPlayers.setSize(0);
        for (int i = 0; i < cellIds.length; i++)
        {
            obj_id[] contents = getContents(cellIds[i]);
            if (contents != null && contents.length > 0)
            {
                for (int k = 0; k < contents.length; k++)
                {
                    if (isPlayer(contents[k]) && hasScript(contents[k], "theme_park.dungeon.myyydril.grievous_player"))
                    {
                        if (validatePlayerSessionId(contents[k], dungeon))
                        {
                            eventPlayers = utils.addElement(eventPlayers, contents[k]);
                        }
                    }
                }
            }
        }
        if (eventPlayers == null)
        {
            return null;
        }
        obj_id[] convertedArray = new obj_id[0];
        if (eventPlayers != null)
        {
            convertedArray = new obj_id[eventPlayers.size()];
            eventPlayers.toArray(convertedArray);
        }
        if (convertedArray.length > 0)
        {
            return convertedArray;
        }
        else 
        {
            return null;
        }
    }
    public obj_id[] getEncounterCellIds(obj_id dungeon) throws InterruptedException
    {
        obj_id[] cells = new obj_id[encounter_cells.length];
        for (int i = 0; i < encounter_cells.length; i++)
        {
            cells[i] = getCellId(dungeon, encounter_cells[i]);
        }
        return cells;
    }
    public void ejectPlayersFromEncounter(obj_id player, obj_id dungeon) throws InterruptedException
    {
        pclib.resurrectPlayer(player, false);
        if (hasScript(player, "theme_park.dungeon.myyydril.grievous_player"))
        {
            detachScript(player, "theme_park.dungeon.myyydril.grievous_player");
        }
        int x = -163;
        int y = -88;
        int z = 127;
        doLogging("ejectPlayersFromEncounter", "Removing player(" + getName(player) + "/" + player + ") from encounter area");
        warpPlayer(player, "kashyyyk_pob_dungeons", x, y, z, dungeon, exit_cell, x, y, z, "nullCallBack", true);
    }
    public void moveSinglePlayerIntoEncounter(obj_id player, obj_id dungeon, int sessionId) throws InterruptedException
    {
        int x = -280;
        int y = -108;
        int z = -18;
        attachScript(player, "theme_park.dungeon.myyydril.grievous_player");
        utils.setScriptVar(dungeon, encounter_session_current, sessionId);
        utils.setScriptVar(player, encounter_session_current, sessionId);
        setObjVar(player, encounter_lockout_end, getGameTime() + 1800);
        doLogging("moveSinglePlayerIntoEncounter", "Adding player(" + getName(player) + "/" + player + ") to encounter area");
        warpPlayer(player, "kashyyyk_pob_dungeons", x, y, z, dungeon, entry_cell, x, y, z, "nullCallBack", false);
    }
    public void moveGroupIntoEncounter(obj_id player, obj_id dungeon, int sessionId) throws InterruptedException
    {
        obj_id cell = getCellId(dungeon, start_cell);
        obj_id groupObject = getGroupObject(player);
        doLogging("moveGroupIntoEncounter", "Group Object is " + groupObject);
        if (!isIdValid(groupObject))
        {
            if (isIdValid(player) && getContainedBy(player) == cell)
            {
                moveSinglePlayerIntoEncounter(player, dungeon, sessionId);
            }
        }
        else 
        {
            obj_id[] groupMembers = getGroupMemberIds(groupObject);
            for (int i = 0; i < groupMembers.length; i++)
            {
                if (isIdValid(groupMembers[i]) && getContainedBy(groupMembers[i]) == cell)
                {
                    moveSinglePlayerIntoEncounter(groupMembers[i], dungeon, sessionId);
                }
            }
        }
    }
    public boolean isSessionAvailable(obj_id dungeon, boolean godOverride) throws InterruptedException
    {
        if (godOverride)
        {
            doLogging("isSessionAvailable", "Session overridden by god request");
            messageTo(dungeon, "resetEncounterLocks", null, 0, false);
            return true;
        }
        if (utils.hasScriptVar(dungeon, encounter_session_current))
        {
            if (utils.getIntScriptVar(dungeon, encounter_session_current) != -1)
            {
                doLogging("isSessionAvailable", "Current session != -1, validate session");
                return validateEncounterSession(dungeon);
            }
        }
        if (!hasObjVar(dungeon, encounter_active))
        {
            doLogging("isSessionAvailable", "Dungeon lacks encounter active objVar, returning true");
            messageTo(dungeon, "resetEncounterLocks", null, 0, false);
            return true;
        }
        if (getIntObjVar(dungeon, encounter_active) == 1)
        {
            doLogging("isSessionAvailable", "Dungeon has session active objvar. Validating session");
            return validateEncounterSession(dungeon);
        }
        messageTo(dungeon, "resetEncounterLocks", null, 0, false);
        return true;
    }
    public boolean validateEncounterSession(obj_id dungeon) throws InterruptedException
    {
        obj_id[] players = getPlayersInEncounterArea(dungeon);
        if (utils.hasScriptVar(dungeon, grace_period_end))
        {
            if (getGameTime() < utils.getIntScriptVar(dungeon, grace_period_end))
            {
                doLogging("validateEncounterSession", "Grace period has not elapsed, returning false for is available check");
                return false;
            }
        }
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (!validatePlayerSessionId(players[i], dungeon))
                {
                    ejectPlayersFromEncounter(players[i], dungeon);
                }
            }
        }
        players = getPlayersInEncounterArea(dungeon);
        if (players == null || players.length == 0)
        {
            doLogging("validateEncounterSession", "No valid players in the encounter area, send reset request");
            messageTo(dungeon, "resetEncounterLocks", null, 0, false);
            return true;
        }
        return false;
    }
    public int getSessionId(obj_id target) throws InterruptedException
    {
        if (utils.hasScriptVar(target, encounter_session_current))
        {
            return utils.getIntScriptVar(target, encounter_session_current);
        }
        else 
        {
            return -1;
        }
    }
    public boolean validatePlayerSessionId(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (utils.hasScriptVar(player, god_authorized))
        {
            doLogging("validatePlayerSessionId", "Player(" + getName(player) + "/" + player + ") is god authoratative");
            return true;
        }
        if (hasObjVar(dungeon, encounter_active))
        {
            if (getIntObjVar(dungeon, encounter_active) == 1)
            {
                return (getSessionId(player) == getSessionId(dungeon));
            }
            else 
            {
                return false;
            }
        }
        return false;
    }
    public int setSessionIds(obj_id dungeon) throws InterruptedException
    {
        int sessionId = 0;
        if (utils.hasScriptVar(dungeon, encounter_session_count))
        {
            sessionId = utils.getIntScriptVar(dungeon, encounter_session_count);
        }
        utils.setScriptVar(dungeon, encounter_session_current, sessionId);
        int nextSession = sessionId + 1;
        utils.setScriptVar(dungeon, encounter_session_count, nextSession);
        doLogging("setSessionIds", "Session Id set to: " + sessionId);
        return sessionId;
    }
    public void startEventTimer(obj_id dungeon, int sessionId) throws InterruptedException
    {
        int currentTime = getGameTime();
        int endTime = currentTime + encounter_length;
        int endGracePeriod = currentTime + grace_period;
        utils.setScriptVar(dungeon, encounter_start, currentTime);
        utils.setScriptVar(dungeon, encounter_end, endTime);
        utils.setScriptVar(dungeon, grace_period_end, endGracePeriod);
        dictionary dict = new dictionary();
        dict.put("sessionId", sessionId);
        messageTo(dungeon, "handleSessionTimerUpdate", dict, 0, false);
    }
    public String getDungeonRemainingTimeString(obj_id dungeon) throws InterruptedException
    {
        int currentTime = getGameTime();
        int sessionEnd = utils.getIntScriptVar(dungeon, encounter_end);
        int sessionTimeRemaining = sessionEnd - currentTime;
        doLogging("getDungeonRemainingTimeString", "sessionTimeRemaining: " + sessionTimeRemaining);
        if (sessionTimeRemaining < 1)
        {
            doLogging("getDungeonRemainingTimeString", "sessionTimeRemaining less than one, calling End Dungeon Session");
            messageTo(dungeon, "resetEncounterLocks", null, 0, false);
        }
        return utils.formatTimeVerbose(sessionTimeRemaining);
    }
    public int calculateNextMessage(obj_id dungeon) throws InterruptedException
    {
        int currentTime = getGameTime();
        int sessionEnd = utils.getIntScriptVar(dungeon, encounter_end);
        int sessionTimeRemaining = sessionEnd - currentTime;
        doLogging("calculateNextMessage", "SessionTimeRemaining = sessionEnd - currentTime");
        doLogging("calculateNextMessage", sessionTimeRemaining + " = " + sessionEnd + " - " + currentTime);
        if (sessionTimeRemaining > 10)
        {
            if (sessionTimeRemaining > 60)
            {
                if (sessionTimeRemaining > 300)
                {
                    if (sessionTimeRemaining > 1800)
                    {
                        return ((sessionEnd - 1800) - currentTime);
                    }
                    return ((sessionEnd - 300) - currentTime);
                }
                return ((sessionEnd - 60) - currentTime);
            }
            return ((sessionEnd - 10) - currentTime);
        }
        return sessionEnd - currentTime;
    }
    public void devalidateEventPlayersInDungeon(obj_id dungeon) throws InterruptedException
    {
        obj_id[] players = getEventPlayersInDungeon(dungeon);
        if (players != null && players.length > 0)
        {
            utils.messageTo(players, "cleanupEjectedPlayers", null, 0, false);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/grievous_encounter_lock/" + section, message);
        }
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "resetEncounterLocks", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id container, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = getPlayersInEncounterArea(self);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (players[i] == item)
                {
                    doLogging("OnRecievedItem", "Player(" + getName(item) + "/" + item + ") has entered encounter area, validating");
                    messageTo(self, "validatePlayersInEvent", null, 0, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int resetEncounterLocks(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, encounter_active, 0);
        utils.setScriptVar(self, encounter_session_current, -1);
        devalidateEventPlayersInDungeon(self);
        clearEncounterCells(self);
        return SCRIPT_CONTINUE;
    }
    public int beginEncounter(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        boolean overrideLock = isGod(player);
        if (!isSessionAvailable(self, overrideLock))
        {
            sendSystemMessage(player, not_available);
            return SCRIPT_CONTINUE;
        }
        int sessionId = setSessionIds(self);
        moveGroupIntoEncounter(player, self, sessionId);
        setObjVar(self, encounter_active, 1);
        groundquests.sendSignal(player, "signalCompleteGrievousPrequest");
        startEventTimer(self, sessionId);
        return SCRIPT_CONTINUE;
    }
    public int handleSessionTimerUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        int sessionId = getSessionId(self);
        int passedSessionId = params.getInt("sessionId");
        doLogging("handleSessionTimerUpdate", "Session ID vs Passed ID: " + sessionId + ", " + passedSessionId);
        if (sessionId == passedSessionId)
        {
            int nextMessage = calculateNextMessage(self);
            doLogging("handleSessionTimerUpdate", "nextMessage > -1 precheck: " + nextMessage);
            if (nextMessage > 0)
            {
                String timeRemaining = getDungeonRemainingTimeString(self);
                obj_id[] players = getEventPlayersInDungeon(self);
                if (players != null && players.length > 0)
                {
                    doLogging("handleSessionTimerUpdate", "Time remaining: " + timeRemaining + ", players to notify: " + players.length);
                    params.put("message", timeRemaining);
                    utils.messageTo(players, "handleGrievousTimerUpdate", params, 0.0f, false);
                }
                dictionary dict = new dictionary();
                dict.put("sessionId", sessionId);
                doLogging("handleSessionTimerUpdate", "Sending next message in (" + nextMessage + ") seconds");
                messageTo(self, "handleSessionTimerUpdate", dict, nextMessage, false);
            }
            else 
            {
                doLogging("handleSessionTimerUpdate", "Next message was less than 1, ending encounter");
                messageTo(self, "resetEncounterLocks", null, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int validatePlayersInEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = getPlayersInEncounterArea(self);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (!validatePlayerSessionId(players[i], self))
                {
                    ejectPlayersFromEncounter(players[i], self);
                }
            }
        }
        players = getPlayersInEncounterArea(self);
        if (players == null || players.length == 0)
        {
            doLogging("validatePlayersInEvent", "No players remain in the encounter area, send reset request");
            messageTo(self, "resetEncounterLocks", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
