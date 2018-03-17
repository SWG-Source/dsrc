package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class report_posture extends script.base_script
{
    public report_posture()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("report"))
        {
            debugSpeakMsg(self, "Posture is " + Integer.toString(getPosture(self)) + "Locomotion is " + Integer.toString(getLocomotion(self)));
        }
        return SCRIPT_CONTINUE;
    }
}
