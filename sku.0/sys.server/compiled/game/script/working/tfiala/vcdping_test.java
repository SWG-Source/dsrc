package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.utils;

public class vcdping_test extends script.base_script
{
    public vcdping_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(self);
        if (!isIdValid(controlDevice))
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("deleteVehicleCurrent"))
        {
            LOG("vcdping-debug", "vcdping-test.OnHearSpeech(): clearing callable.current");
            callable.setCallableCD(controlDevice, null);
        }
        return SCRIPT_CONTINUE;
    }
}
