package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.groundquests;

public class decrepit_player extends script.base_script
{
    public decrepit_player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "sendEnterSignal", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int sendEnterSignal(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.sendSignal(self, trial.DECREPIT_ENTER_SIGNAL);
        return SCRIPT_CONTINUE;
    }
}
