package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.player_structure;
import script.library.utils;

public class dungeon_timer extends script.base_script
{
    public dungeon_timer()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        int time = getGameTime();
        setObjVar(self, "groupTime", time);
        messageTo(self, "everyFiveMinutes", null, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int everyFiveMinutes(obj_id self, dictionary params) throws InterruptedException
    {
        int currentTime = getGameTime();
        int startTime = getIntObjVar(self, "groupTime");
        int timeLeft = currentTime - startTime;
        timeLeft = 3600 - timeLeft;
        timeLeft = timeLeft / 60;
        dictionary webster = new dictionary();
        webster.put("timeLeft", timeLeft);
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        int numInDungeon = players.length;
        broadcastMessage("fiveMinuteTimer", webster);
        if (timeLeft > 10)
        {
            messageTo(self, "everyFiveMinutes", null, 300, false);
        }
        else 
        {
            messageTo(self, "everyMinute", null, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int everyMinute(obj_id self, dictionary params) throws InterruptedException
    {
        int currentTime = getGameTime();
        int startTime = getIntObjVar(self, "groupTime");
        int timeLeft = currentTime - startTime;
        timeLeft = 3600 - timeLeft;
        timeLeft = timeLeft / 60;
        dictionary webster = new dictionary();
        webster.put("timeLeft", timeLeft);
        obj_id[] players = player_structure.getPlayersInBuilding(self);
        int numInDungeon = players.length;
        broadcastMessage("fiveMinuteTimer", webster);
        if (timeLeft <= 1)
        {
            broadcastMessage("dungeonEnds", webster);
            messageTo(self, "dungeonEnds", null, 50, false);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "everyMinute", null, 60, false);
        return SCRIPT_CONTINUE;
    }
    public int dungeonEnds(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "fuel"))
        {
            removeObjVar(self, "fuel");
        }
        if (hasObjVar(self, "hyperdrive"))
        {
            removeObjVar(self, "hyperdrive");
        }
        if (hasObjVar(self, "engine"))
        {
            removeObjVar(self, "engine");
        }
        if (self == null)
        {
            return SCRIPT_CONTINUE;
        }
        int x = 0;
        while (x < 200)
        {
            String variable = "spawned" + x;
            if (hasObjVar(self, variable))
            {
                obj_id thing = getObjIdObjVar(self, variable);
                destroyObject(thing);
            }
            x = x + 1;
        }
        obj_id meeting = getCellId(self, "meetingroom38");
        obj_id elevator = getCellId(self, "elevator57");
        obj_id bridge = getCellId(self, "bridge66");
        obj_id officer1 = getCellId(self, "officerquarters64");
        obj_id officer2 = getCellId(self, "officerquarters65");
        detachScript(meeting, "theme_park.dungeon.corvette.locked");
        detachScript(elevator, "theme_park.dungeon.corvette.locked");
        detachScript(officer2, "theme_park.dungeon.corvette.locked");
        detachScript(officer1, "theme_park.dungeon.corvette.locked");
        detachScript(bridge, "theme_park.dungeon.corvette.locked");
        removeObjVar(self, "current_players");
        if (isIdValid(self))
        {
            space_dungeon.endDungeonSession(self);
        }
        CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette ended because time ran out.  Dungeon ID was " + self + "");
        return SCRIPT_CONTINUE;
    }
}
