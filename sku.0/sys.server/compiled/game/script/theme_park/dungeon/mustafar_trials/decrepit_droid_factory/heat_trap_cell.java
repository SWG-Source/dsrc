package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class heat_trap_cell extends script.base_script
{
    public heat_trap_cell()
    {
    }
    public static final String DOT_SCRIPT = "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.heat_trap_dot";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setFireCellState(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        String or5_script = "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.colonel_or5";
        obj_id[] withScript = trial.getObjectsInDungeonWithScript(trial.getTop(self), or5_script);
        obj_id or_5 = null;
        if (withScript == null || withScript.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        triggerFireCell(item);
        return SCRIPT_CONTINUE;
    }
    public void triggerFireCell(obj_id player) throws InterruptedException
    {
        if (!hasScript(player, DOT_SCRIPT))
        {
            attachScript(player, DOT_SCRIPT);
        }
        return;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/heat_trap_cell/" + section, message);
        }
    }
}
