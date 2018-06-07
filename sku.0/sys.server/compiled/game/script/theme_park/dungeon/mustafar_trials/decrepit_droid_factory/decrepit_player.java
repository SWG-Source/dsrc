package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.dictionary;
import script.library.groundquests;
import script.library.trial;
import script.obj_id;

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
