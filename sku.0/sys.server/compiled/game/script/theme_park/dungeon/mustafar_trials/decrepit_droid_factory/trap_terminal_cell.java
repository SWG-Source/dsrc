package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.dictionary;
import script.library.buff;
import script.library.create;
import script.library.trial;
import script.location;
import script.obj_id;

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
            for (obj_id player1 : players) {
                buff.applyBuff(player1, "biological_suppression");
            }
            for (String guardDroid : guardDroids) {
                obj_id player = players[rand(0, players.length - 1)];
                location playerLoc = getLocation(player);
                obj_id guard = create.object(guardDroid, playerLoc);
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
