package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;
import script.library.badge;
import script.library.create;
import script.library.instance;

public class decrepit_controller extends script.base_script
{
    public decrepit_controller()
    {
    }
    public static final String[] LOCKED_ROOMS = 
    {
        "mainroom27",
        "hall2",
        "hall5",
        "smallroom12",
        "smallroom11",
        "hall13",
        "centralroom28"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int beginSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        setEventLocks(self);
        setEventStates(self);
        spawnOr5(self);
        return SCRIPT_CONTINUE;
    }
    public int remoteCommand(obj_id self, dictionary params) throws InterruptedException
    {
        String command = "null";
        command = params.getString("command");
        if (command.equals("setLock"))
        {
            setEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("removeLock"))
        {
            removeEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("lockCell"))
        {
            lockCell(self, params);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("deactivateTrap"))
        {
            deactivateTrap(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("setDefaultStates"))
        {
            setEventLocks(self);
            return SCRIPT_CONTINUE;
        }
        if (command.equals("removeEventStates"))
        {
            removeEventStates(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void setEventLocks(obj_id dungeon) throws InterruptedException
    {
        for (int i = 0; i < LOCKED_ROOMS.length; i++)
        {
            permissionsMakePrivate(getCellId(dungeon, LOCKED_ROOMS[i]));
        }
    }
    public void removeEventLocks(obj_id dungeon) throws InterruptedException
    {
        for (int i = 0; i < LOCKED_ROOMS.length; i++)
        {
            permissionsMakePublic(getCellId(dungeon, LOCKED_ROOMS[i]));
        }
    }
    public void setEventStates(obj_id dungeon) throws InterruptedException
    {
        trial.setTrapState(dungeon, false);
        trial.setFireCellState(dungeon, true);
        trial.setPowerCoreState(dungeon, false);
        trial.setGuardianLockState(dungeon, true);
        trial.setDecrepitTrialState(dungeon, false);
    }
    public void removeEventStates(obj_id dungeon) throws InterruptedException
    {
        trial.setTrapState(dungeon, false);
        trial.setFireCellState(dungeon, false);
        trial.setPowerCoreState(dungeon, true);
        trial.setGuardianLockState(dungeon, false);
        trial.setDecrepitTrialState(dungeon, true);
    }
    public void lockCell(obj_id dungeon, dictionary params) throws InterruptedException
    {
        String cell = params.getString("cell");
        trial.makeCellPrivate(dungeon, cell);
    }
    public void spawnOr5(obj_id self) throws InterruptedException
    {
        obj_id cell = getCellId(self, "mediumroom10");
        location spawnLoc = new location(63, -67, -50, getLocation(self).area, cell);
        obj_id colonel = create.object("som_decrepit_colonel_or5", spawnLoc);
        setYaw(colonel, 90);
        attachScript(colonel, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.colonel_or5");
    }
    public int codeResetOccured(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] masterTerminals = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.code_terminal_master");
        obj_id[] slaveTerminals = trial.getObjectsInDungeonWithScript(self, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.code_terminal_slave");
        if (masterTerminals != null && masterTerminals.length > 0)
        {
            utils.messageTo(masterTerminals, "handleResetTimer", null, 0, false);
        }
        if (slaveTerminals != null && slaveTerminals.length > 0)
        {
            utils.messageTo(slaveTerminals, "handleResetTimer", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int colonelDied(obj_id self, dictionary params) throws InterruptedException
    {
        trial.makeCellPublic(self, "hall13");
        return SCRIPT_CONTINUE;
    }
    public int winTrial(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setDecrepitTrialState(self, true);
        trial.setDungeonCleanOutTimer(self);
        trial.sendCompletionSignal(self, trial.DECREPIT_WIN_SIGNAL);
        obj_id[] players = trial.getPlayersInDungeon(self);
        badge.grantBadge(players, "bdg_must_victory_ddf");
        return SCRIPT_CONTINUE;
    }
    public void deactivateTrap(obj_id self) throws InterruptedException
    {
        trial.setTrapState(self, false);
    }
}
