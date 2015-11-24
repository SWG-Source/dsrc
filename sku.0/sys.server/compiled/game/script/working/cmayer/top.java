package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class top extends script.base_script
{
    public top()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            debugSpeakMsg(self, "TopScript");
        }
        return SCRIPT_CONTINUE;
    }
}
