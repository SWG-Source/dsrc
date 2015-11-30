package script.theme_park.heroic.ig88;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.trial;
import script.library.utils;

public class ig88_alarm extends script.base_script
{
    public ig88_alarm()
    {
    }
    public int alarm_on(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        setCondition(self, CONDITION_ON);
        return SCRIPT_CONTINUE;
    }
    public int alarm_off(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        clearCondition(self, CONDITION_ON);
        return SCRIPT_CONTINUE;
    }
}
