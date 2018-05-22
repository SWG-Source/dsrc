package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class broadcast_test extends script.base_script
{
    public broadcast_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("broadcast"))
        {
            sendSystemMessageGalaxyTestingOnly("Broadcasting now, speaker is " + objSpeaker + ", self is " + self);
        }
        return SCRIPT_CONTINUE;
    }
}
