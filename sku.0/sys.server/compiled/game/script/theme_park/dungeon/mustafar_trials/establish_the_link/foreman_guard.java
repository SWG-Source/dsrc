package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;

public class foreman_guard extends script.base_script
{
    public foreman_guard()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id controller = utils.getObjIdScriptVar(self, trial.PARENT);
            if (!isIdValid(controller))
            {
                doLogging("OnDestroy", "I do not have the parent script var");
                return SCRIPT_CONTINUE;
            }
            messageTo(controller, "guardDied", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] targets = trial.getValidTargetsInRadius(self, 7.0f);
        playClientEffectLoc(self, trial.PRT_KUBAZA_EXPLODE, getLocation(self), 0.4f);
        destroyObject(self);
        if (targets == null || targets.length == 0)
        {
            doLogging("nukeSelf", "No valid targets in blast radius");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 500);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "removeInvulnerable", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int removeInvulnerable(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/foreman_guard/" + section, message);
        }
    }
}
