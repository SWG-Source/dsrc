package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.instance;
import script.library.create;
import script.library.trial;
import script.library.badge;

public class link_event_manager extends script.base_script
{
    public link_event_manager()
    {
    }
    public static final boolean LOGGING = false;
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        clearEventArea(self);
        spawnActors(self, 1);
        messageTo(self, "beginEvent", null, 20, false);
        trial.setUplinkActiveState(self, true);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setUplinkActiveState(self, false);
        clearEventArea(self);
        trial.setUplinkVictoryState(self, false);
        return SCRIPT_CONTINUE;
    }
    public void clearEventArea(obj_id dungeon) throws InterruptedException
    {
        obj_id cell = getCellId(dungeon, trial.UPLINK_ROOM);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            doLogging("clearEventArea", "Dungeon was empty, return");
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (isPlayer(contents[i]))
            {
            }
            else 
            {
                if (isMob(contents[i]) || trial.isTempObject(contents[i]))
                {
                    doLogging("xx", "Cleanup performed on " + getName(contents[i]));
                    trial.cleanupNpc(contents[i]);
                }
            }
        }
    }
    public void spawnActors(obj_id dungeon, int stage) throws InterruptedException
    {
        int rows = dataTableGetNumRows(trial.UPLINK_DATA);
        if (rows == 0)
        {
            doLogging("spawnActors", "The datatable is empty");
            return;
        }
        for (int i = 0; i < rows; i++)
        {
            dictionary dict = dataTableGetRow(trial.UPLINK_DATA, i);
            if (dict.getInt("stage") == stage)
            {
                int locx = dict.getInt("locx");
                int locy = dict.getInt("locy");
                int locz = dict.getInt("locz");
                String scene = getCurrentSceneName();
                obj_id cell = getCellId(dungeon, trial.UPLINK_ROOM);
                float yaw = dict.getFloat("yaw");
                String script = dict.getString("script");
                String wpName = dict.getString("wp_name");
                location spawnLoc = new location(locx, locy, locz, scene, cell);
                String object = dict.getString("object");
                if (object.startsWith("object/"))
                {
                    obj_id item = createObjectInCell(object, dungeon, trial.UPLINK_ROOM, spawnLoc);
                    if (!isIdValid(item))
                    {
                        doLogging("spawnActors", "Tried to create invalid item(" + object + ")");
                        return;
                    }
                    setYaw(item, yaw);
                    if (!script.equals("none"))
                    {
                        attachScript(item, script);
                    }
                    if (!wpName.equals("none"))
                    {
                        utils.setScriptVar(item, trial.WP_NAME, wpName);
                    }
                    trial.markAsTempObject(item, false);
                }
                else 
                {
                    obj_id creature = create.object(object, spawnLoc);
                    if (!isIdValid(creature))
                    {
                        doLogging("spawnActors", "Tried to create invalid creature(" + object + ")");
                        return;
                    }
                    if (!script.equals("none"))
                    {
                        attachScript(creature, script);
                    }
                }
            }
        }
    }
    public int beginEvent(obj_id self, dictionary params) throws InterruptedException
    {
        spawnActors(self, 2);
        return SCRIPT_CONTINUE;
    }
    public int remoteCommand(obj_id self, dictionary params) throws InterruptedException
    {
        String command = "null";
        command = params.getString("command");
        if (command.equals("clearEventArea"))
        {
            clearEventArea(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int validateRelays(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id cell = getCellId(self, trial.UPLINK_ROOM);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        int relayCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if ((getTemplateName(contents[i])).equals(trial.RELAY_OBJECT))
            {
                relayCount++;
            }
            if (relayCount >= 11)
            {
                doLogging("validateRelays", "Target relay count reached, spawning foreman");
                spawnActors(self, 3);
                deactivateDroid(self);
                stopRandomSoldiers(self);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void deactivateDroid(obj_id self) throws InterruptedException
    {
        obj_id cell = getCellId(self, trial.UPLINK_ROOM);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return;
        }
        int relayCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.droid_patrol_script"))
            {
                stop(contents[i]);
                setInvulnerable(contents[i], true);
            }
            if ((getTemplateName(contents[i])).equals(trial.RELAY_OBJECT))
            {
                location playLoc = getLocation(contents[i]);
                playClientEffectLoc(contents[i], trial.PRT_RELAY_ACTIVATE, playLoc, 3f);
            }
        }
    }
    public void stopRandomSoldiers(obj_id self) throws InterruptedException
    {
        obj_id cell = getCellId(self, trial.UPLINK_ROOM);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return;
        }
        int relayCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.soldier_spawner"))
            {
                destroyObject(contents[i]);
            }
        }
    }
    public void stopForemanDrones(obj_id self) throws InterruptedException
    {
        obj_id cell = getCellId(self, trial.UPLINK_ROOM);
        obj_id[] contents = getContents(cell);
        if (contents == null || contents.length == 0)
        {
            return;
        }
        int relayCount = 0;
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.foreman_drone_spawner"))
            {
                destroyObject(contents[i]);
            }
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.foreman_drone_spawner_tracker"))
            {
                destroyObject(contents[i]);
            }
        }
        return;
    }
    public int linkEventWon(obj_id self, dictionary params) throws InterruptedException
    {
        stopForemanDrones(self);
        trial.setUplinkVictoryState(self, true);
        trial.sendCompletionSignal(self, trial.UPLINK_WIN_SIGNAL);
        obj_id[] players = trial.getPlayersInDungeon(self);
        badge.grantBadge(players, "bdg_must_victory_kubaza");
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/link_event_manager/" + section, message);
        }
    }
}
