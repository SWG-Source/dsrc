package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.dot;

public class heat_trap_dot extends script.base_script
{
    public heat_trap_dot()
    {
    }
    public static final int DAMAGE = 2000;
    public static final int DURATION = 5;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateBurn", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        if (top == null)
        {
            detachScript(self, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.heat_trap_dot");
        }
        String template = getTemplateName(top);
        if (template.indexOf("decrepit") < 0)
        {
            detachScript(self, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.heat_trap_dot");
        }
        return SCRIPT_CONTINUE;
    }
    public int validateBurn(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id top = getTopMostContainer(self);
        String or5_script = "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.colonel_or5";
        obj_id[] withScript = trial.getObjectsInDungeonWithScript(trial.getTop(self), or5_script);
        obj_id or_5 = null;
        if (withScript == null || withScript.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            or_5 = withScript[0];
        }
        if (top == null)
        {
            detachScript(self, "theme_park.dungeon.mustafar_trials.decrepit_droid_factory.heat_trap_dot");
            return SCRIPT_CONTINUE;
        }
        if (canSee(self, or_5) && !isDead(or_5))
        {
            if (!dot.isOnFire(self))
            {
                dot.applyDotEffect(self, or_5, dot.DOT_FIRE, "ddf_fire_cell_dot", HEALTH, -1, DAMAGE, DURATION, true, null);
            }
        }
        messageTo(self, "validateBurn", null, DURATION - 1, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/heat_trap_dot/" + section, message);
        }
    }
}
