package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.instance;
import script.library.trial;

public class exit_terminal extends script.base_script
{
    public exit_terminal()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_EXIT_TRIAL = new string_id(DECREPIT_STF, "decrepit_exit");
    public static final string_id SID_EXIT_TRIAL_CONFIRM = new string_id(DECREPIT_STF, "decrepit_exit_confirm");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_EXIT_TRIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            instance.requestExitPlayer(instance.getInstanceName(trial.getTop(self)), player);
        }
        return SCRIPT_CONTINUE;
    }
}
