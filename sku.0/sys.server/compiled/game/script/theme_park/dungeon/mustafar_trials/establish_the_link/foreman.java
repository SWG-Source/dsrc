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

public class foreman extends script.base_script
{
    public foreman()
    {
    }
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id controller = utils.getObjIdScriptVar(self, trial.PARENT);
            if (!isIdValid(controller))
            {
                return SCRIPT_CONTINUE;
            }
            messageTo(controller, "foremanDied", null, 0, false);
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
            LOG("logging/foreman/" + section, message);
        }
    }
}
