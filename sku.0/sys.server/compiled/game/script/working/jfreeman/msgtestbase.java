package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class msgtestbase extends script.base_script
{
    public msgtestbase()
    {
    }
    public int handleTest(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "msg test base received message");
        debugSpeakMsg(self, "this should not have happened, if this script was attached first.");
        return SCRIPT_CONTINUE;
    }
}
