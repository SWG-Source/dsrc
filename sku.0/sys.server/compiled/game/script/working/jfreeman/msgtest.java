package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class msgtest extends script.base_script
{
    public msgtest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleTest", null, 0, false);
        messageTo(self, "handleOtherTest", null, 20, true);
        messageTo(self, "handleLastTest", null, 21, false);
        return SCRIPT_CONTINUE;
    }
    public int handleTest(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "received non-persisted zero-length message");
        return SCRIPT_CONTINUE;
    }
    public int handleOtherTest(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "received persisted 20-second delay message");
        return SCRIPT_CONTINUE;
    }
    public int handleLastTest(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "received non-persisted 21-second delay message");
        return SCRIPT_CONTINUE;
    }
}
