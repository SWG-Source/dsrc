package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.buff;
import script.library.create;

public class trap_terminal_cell extends script.base_script
{
    public trap_terminal_cell()
    {
    }
    public static final boolean LOGGING = false;
    public int triggerTrap(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setTrapState(getContainedBy(self), true);
        String[] guardDroids = 
        {
            "som_decrepit_blastromech",
            "som_decrepit_blastromech",
            "som_decrepit_battle_droid"
        };
        String[] cells = 
        {
            trial.DECREPIT_TRAP_ROOM,
            trial.DECREPIT_PRE_TRAP_ROOM
        };
        obj_id[] players = trial.getPlayersInCellList(self, cells);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                buff.applyBuff(players[i], "biological_suppression");
            }
            for (int k = 0; k < guardDroids.length; k++)
            {
                obj_id player = players[rand(0, players.length - 1)];
                location playerLoc = getLocation(player);
                obj_id guard = create.object(guardDroids[k], playerLoc);
                startCombat(guard, player);
            }
            messageTo(self, "clearTrapTrigger", null, 45, false);
        }
        else 
        {
            trial.setTrapState(getContainedBy(self), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int clearTrapTrigger(obj_id self, dictionary params) throws InterruptedException
    {
        trial.setTrapState(getContainedBy(self), false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/trap_terminal_cell/" + section, message);
        }
    }
}
