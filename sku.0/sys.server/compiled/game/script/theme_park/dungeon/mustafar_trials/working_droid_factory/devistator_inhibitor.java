package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class devistator_inhibitor extends script.base_script
{
    public devistator_inhibitor()
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
        if (!trial.isRruDeactivated(self) && trial.isDevistatorEngaged(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_INHIBITOR_COLLECT);
        }
        else if (!trial.isRruDeactivated(self) && !trial.isDevistatorEngaged(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_INHIBITOR_INACTIVE);
        }
        else if (trial.isRruDeactivated(self) && !trial.isDevistatorKilled(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_INHIBITOR_RECHARGE);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, trial.WORKING_INHIBITOR_INACTIVE);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!trial.isRruDeactivated(self) && trial.isDevistatorEngaged(self))
            {
                utils.setScriptVar(player, trial.WORKING_INHIBITOR_TRACKER, trial.getSession(trial.getTop(self), "devistator_control"));
                sendSystemMessage(player, trial.WORKING_INHIBITOR_TAKEN);
            }
            else if (!trial.isRruDeactivated(self) && !trial.isDevistatorEngaged(self))
            {
                sendSystemMessage(player, trial.WORKING_INHIBITOR_INACTIVE);
            }
            else if (trial.isRruDeactivated(self) && !trial.isDevistatorKilled(self))
            {
                sendSystemMessage(player, trial.WORKING_INHIBITOR_RECHARGE);
            }
            else 
            {
                sendSystemMessage(player, trial.WORKING_INHIBITOR_INACTIVE);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
