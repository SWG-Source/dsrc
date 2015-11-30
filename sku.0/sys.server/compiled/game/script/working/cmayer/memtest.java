package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class memtest extends script.base_script
{
    public memtest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            dictionary dict = new dictionary();
            if (!hasObjVar(self, "myTest"))
            {
                messageTo(self, "TestCallback", dict, 1, true);
                setObjVar(self, "myTest", 1);
            }
        }
        if (text.equals("end"))
        {
            removeObjVar(self, "myTest");
        }
        return SCRIPT_CONTINUE;
    }
    public int TestCallback(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "myTest"))
        {
            debugSpeakMsg(self, "bleh");
            Vector test = new Vector();
            test.setSize(0);
            for (int j = 0; j < 2000; ++j)
            {
                test = utils.addElement(test, j);
            }
            messageTo(self, "TestCallback", params, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
}
