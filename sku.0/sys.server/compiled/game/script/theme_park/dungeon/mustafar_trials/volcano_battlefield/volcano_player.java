package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.groundquests;

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
