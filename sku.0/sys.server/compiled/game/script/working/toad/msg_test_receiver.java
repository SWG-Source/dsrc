package script.working.toad;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class msg_test_receiver extends script.base_script
{
    public msg_test_receiver()
    {
    }
    public int immediate_message(obj_id self, dictionary params) throws InterruptedException
    {
        String message = params.getString("message");
        obj_id sender = params.getObjId("sender");
        debugSpeakMsg(self, message);
        messageTo(sender, "gotImmediate", null, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int delayed_message(obj_id self, dictionary params) throws InterruptedException
    {
        String message = params.getString("message");
        obj_id sender = params.getObjId("sender");
        debugSpeakMsg(self, "Despite the delay, I " + message);
        messageTo(sender, "gotDelayed", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
