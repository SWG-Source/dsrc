package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.trial;

public class exit_door extends script.base_script
{
    public exit_door()
    {
    }
    public static final string_id SID_EXIT_TRIAL = new string_id(trial.UPLINK_STF, "bunker_exit");
    public static final string_id SID_EXIT_TRIAL_CONFIRM = new string_id(trial.UPLINK_STF, "bunker_exit_confirm");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (getDistance(self, player) > 6.0f)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_EXIT_TRIAL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            instance.requestExitPlayer("uplink_cave", player);
        }
        return SCRIPT_CONTINUE;
    }
}
