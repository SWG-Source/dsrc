package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.dictionary;
import script.library.groundquests;
import script.library.trial;
import script.obj_id;

public class volcano_player extends script.base_script
{
    public volcano_player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "sendEnterSignal", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int sendEnterSignal(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.sendSignal(self, trial.VOLCANO_ENTER_SIGNAL);
        return SCRIPT_CONTINUE;
    }
}
