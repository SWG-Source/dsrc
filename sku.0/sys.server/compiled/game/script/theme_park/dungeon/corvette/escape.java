package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.space_dungeon;
import script.library.create;
import script.library.utils;
import script.library.permissions;

public class escape extends script.terminal.base.base_terminal
{
    public escape()
    {
    }
    public static final String MSGS = "dungeon/corvette";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Escape Pod Controls");
        createTriggerVolume("escape_pod", 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id dungeon = getTopMostContainer(self);
            if (hasObjVar(player, "corl_corvette.mission_complete"))
            {
                messageTo(player, "rewardTime", null, 3, false);
                setObjVar(player, "corl_corvette.got_reward", 1);
            }
            else 
            {
                CustomerServiceLog("DUNGEON_CorellianCorvette", "*Corvette Escaped: %TU has left the Corvette via the escape pod.", player);
                space_dungeon.ejectPlayerFromDungeon(player);
            }
            dictionary webster = new dictionary();
            int session = space_dungeon.getDungeonSessionId(dungeon);
            webster.put("session", session);
            messageTo(self, "playersLeavingDungeon", webster, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanOutDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (hasObjVar(top, "fuel"))
        {
            removeObjVar(top, "fuel");
        }
        if (hasObjVar(top, "hyperdrive"))
        {
            removeObjVar(top, "hyperdrive");
        }
        if (hasObjVar(top, "engine"))
        {
            removeObjVar(top, "engine");
        }
        if (top == null)
        {
            return SCRIPT_CONTINUE;
        }
        int x = 0;
        while (x < 200)
        {
            String variable = "spawned" + x;
            if (hasObjVar(top, variable))
            {
                obj_id thing = getObjIdObjVar(top, variable);
                destroyObject(thing);
            }
            x = x + 1;
        }
        obj_id meeting = getCellId(top, "meetingroom38");
        obj_id elevator = getCellId(top, "elevator57");
        obj_id bridge = getCellId(top, "bridge66");
        obj_id officer1 = getCellId(top, "officerquarters64");
        obj_id officer2 = getCellId(top, "officerquarters65");
        detachScript(meeting, "theme_park.dungeon.corvette.locked");
        detachScript(elevator, "theme_park.dungeon.corvette.locked");
        detachScript(officer2, "theme_park.dungeon.corvette.locked");
        detachScript(officer1, "theme_park.dungeon.corvette.locked");
        detachScript(bridge, "theme_park.dungeon.corvette.locked");
        removeObjVar(top, "current_players");
        if (isIdValid(top))
        {
            space_dungeon.endDungeonSession(top);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volume, obj_id player) throws InterruptedException
    {
        if (volume.equals("escape_pod"))
        {
            string_id escapeMessage = new string_id(MSGS, "escape_pod_message");
            sendSystemMessage(player, escapeMessage);
        }
        return SCRIPT_CONTINUE;
    }
    public int playersLeavingDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        int sentSession = params.getInt("session");
        int thisSession = space_dungeon.getDungeonSessionId(dungeon);
        if (sentSession == thisSession)
        {
            obj_id[] playersLeft = player_structure.getPlayersInBuilding(dungeon);
            if (playersLeft == null)
            {
                messageTo(self, "cleanOutDungeon", null, 1, false);
                space_dungeon.endDungeonSession(dungeon);
                LOG("space_dungeon", "corvette.escape.playersLeavingDungeon Says the players left in dungeon was NULL");
            }
            int numInDungeon = playersLeft.length;
            LOG("space_dungeon", "corvette.escape.playersLeavingDungeon Says there are " + numInDungeon + " players left in the dungeon (" + dungeon + ")");
            if (numInDungeon == 0)
            {
                messageTo(self, "cleanOutDungeon", null, 1, false);
                space_dungeon.endDungeonSession(dungeon);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
