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
import script.library.colors;

public class trap_terminal_scanner extends script.base_script
{
    public trap_terminal_scanner()
    {
    }
    public static final String DECREPIT_STF = "mustafar/decrepit_droid_factory";
    public static final string_id SID_SENSOR_OBSCURE = new string_id(DECREPIT_STF, "sensor_obscure");
    public static final string_id SID_SENSOR_RESET = new string_id(DECREPIT_STF, "sensor_reset");
    public static final string_id SID_SENSOR_OBSCURED = new string_id(DECREPIT_STF, "sensor_obscured");
    public static final boolean doLogging = false;
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
        boolean isUnlocked = trial.isCellPublic(self, trial.DECREPIT_ONE_TWO_STAIR);
        if (isActive(self))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_SENSOR_OBSCURE);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_SENSOR_OBSCURED);
        }
        sendDirtyObjectMenuNotification(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (isActive(self))
            {
                obscureSensor(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isActive(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, trial.SENSOR_STATE))
        {
            return utils.getBooleanScriptVar(self, trial.SENSOR_STATE);
        }
        utils.setScriptVar(self, trial.SENSOR_STATE, true);
        return true;
    }
    public void obscureSensor(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, trial.SENSOR_STATE, false);
        messageTo(self, "clearSensor", null, trial.SENSOR_RESET_TIME, false);
    }
    public int clearSensor(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, trial.SENSOR_STATE, true);
        showFlyText(self, SID_SENSOR_RESET, 1.0f, colors.RED);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("travis/code_terminal_slave/" + section, message);
        }
    }
}
