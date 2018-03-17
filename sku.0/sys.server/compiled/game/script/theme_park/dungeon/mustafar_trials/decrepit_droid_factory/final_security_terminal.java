package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;
import script.library.utils;

public class final_security_terminal extends script.base_script
{
    public final_security_terminal()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_SECURITY_LOCK_ACTIVE = new string_id(DECREPIT_STF, "guardian_lock_on");
    public static final string_id SID_DISABLE_LOCK = new string_id(DECREPIT_STF, "disable_guardian_lock");
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
        boolean guardianLockActive = trial.isGuardianLockInPlace(self);
        boolean isFinalCellPublic = trial.isCellPublic(self, trial.DECREPIT_FINAL_ROOM);
        if (guardianLockActive)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_SECURITY_LOCK_ACTIVE);
        }
        else if (!guardianLockActive && !isFinalCellPublic)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_DISABLE_LOCK);
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
            if (!trial.isGuardianLockInPlace(self) && !trial.isCellPublic(self, trial.DECREPIT_FINAL_ROOM))
            {
                trial.makeCellPublic(self, trial.DECREPIT_FINAL_ROOM);
                utils.sendSystemMessagePob(trial.getTop(self), trial.SID_ACCESS_GRANTED);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
