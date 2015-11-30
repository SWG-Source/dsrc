package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class container_broadcast_test extends script.base_script
{
    public container_broadcast_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("breakContainer"))
        {
            makeContainerStateInconsistentTestOnly(objSpeaker);
            sendSystemMessageGalaxyTestingOnly("Here's the OnHearSpeech system message.");
        }
        return SCRIPT_CONTINUE;
    }
}
