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
import script.library.sui;

public class trap_terminal_master extends script.base_script
{
    public trap_terminal_master()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_OPEN_THIRD_FLOOR = new string_id(DECREPIT_STF, "open_third_floor");
    public static final string_id SID_TRAP_ACTIVE = new string_id(DECREPIT_STF, "trap_active");
    public static final string_id SID_INVALID_CODE = new string_id(DECREPIT_STF, "invalid_code");
    public static final string_id SID_TRAP_TRIGGERED = new string_id(DECREPIT_STF, "trap_triggered");
    public static boolean LOGGING = false;
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
        boolean isUnlocked = trial.isCellPublic(self, trial.DECREPIT_TWO_THREE_STAIR);
        if (!isUnlocked)
        {
            if (!trial.isTrapActive(self))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_OPEN_THIRD_FLOOR);
            }
            else 
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_TRAP_ACTIVE);
            }
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
            if (!trial.isCellPublic(self, trial.DECREPIT_TWO_THREE_STAIR))
            {
                if (!trial.isTrapActive(self) && !trial.isTrapPrimed(self))
                {
                    trial.makeCellPublic(self, trial.DECREPIT_TWO_THREE_STAIR);
                    utils.sendSystemMessagePob(trial.getTop(self), trial.SID_ACCESS_GRANTED);
                }
                else if (trial.isTrapActive(self))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    activateTrap(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void activateTrap(obj_id terminal) throws InterruptedException
    {
        obj_id cell = getContainedBy(terminal);
        String[] cells = 
        {
            trial.DECREPIT_TRAP_ROOM,
            trial.DECREPIT_PRE_TRAP_ROOM
        };
        obj_id[] players = trial.getPlayersInCellList(terminal, cells);
        utils.sendSystemMessagePob(trial.getTop(terminal), SID_TRAP_TRIGGERED);
        messageTo(cell, "triggerTrap", null, 2, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/trap_terminal_master/" + section, message);
        }
    }
}
