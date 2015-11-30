package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trial;
import script.library.utils;

public class devistator_boss extends script.base_script
{
    public devistator_boss()
    {
    }
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        messageTo(top, "devistatorKilled", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id homeCell = getContainedBy(self);
        trial.setHp(self, trial.HP_WORKING_DEVISTATOR);
        utils.setScriptVar(self, "home", homeCell);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id homeCell = utils.getObjIdScriptVar(self, "home");
        if (homeCell != destContainer)
        {
            resetSelf(self);
            messageTo(self, "removeInvulnerable", null, 10, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        trial.setDevistatorEngaged(self, true);
        trial.bumpSession(trial.getTop(self), "devistator_control");
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.setDevistatorEngaged(self, false);
        return SCRIPT_CONTINUE;
    }
    public int removeInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!trial.isRruDeactivated(self))
        {
            int total = 0;
            for (int x = 0; x < damage.length; x++)
            {
                total += damage[x];
            }
            addToHealth(self, total);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/devistator_boss/" + section, message);
        }
    }
}
