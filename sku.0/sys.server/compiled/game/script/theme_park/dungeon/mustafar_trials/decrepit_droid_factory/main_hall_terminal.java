package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.space_dungeon;
import script.library.instance;

public class main_hall_terminal extends script.base_script
{
    public main_hall_terminal()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_OPEN_MAIN_HALL = new string_id(DECREPIT_STF, "decrepit_open_main_hall");
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
        boolean isUnlocked = trial.isCellPublic(self, trial.DECREPIT_MAIN_HALL);
        if (!isUnlocked)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN_MAIN_HALL);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.SID_NOT_LOCKED);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isCellPublic(self, trial.DECREPIT_MAIN_HALL))
            {
                trial.makeCellPublic(self, trial.DECREPIT_MAIN_HALL);
                instance.sendInstanceSystemMessage(trial.getTop(self), trial.SID_ACCESS_GRANTED);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
