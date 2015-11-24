package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class player_performance extends script.base_script
{
    public player_performance()
    {
    }
    public static final String PERFORMANCE_OBJVAR = "quest.crowd_pleaser.performance";
    public static final String CONTROL_OBJVAR = PERFORMANCE_OBJVAR + ".control";
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        messageTo(control, "handleReceivePlayerFail", null, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnFlourish(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id control = utils.getObjIdScriptVar(self, CONTROL_OBJVAR);
        messageTo(control, "handleObservedFlourish", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
}
