package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class guardian_lock_terminal extends script.base_script
{
    public guardian_lock_terminal()
    {
    }
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
        boolean isUnlocked = trial.isCellPublic(self, trial.WORKING_ONE_TWO_STAIR);
        if (!isUnlocked)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.TWINS_LOCK_ACTIVE);
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
            if (trial.isAurekBeshKilled(self))
            {
                trial.makeCellPublic(self, trial.WORKING_ONE_TWO_STAIR);
                trial.sendInstanceSystemMessage(trial.getTop(self), trial.SID_ACCESS_GRANTED);
            }
            else 
            {
                trial.sendInstanceSystemMessage(trial.getTop(self), trial.TWINS_KILL_CLUE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
