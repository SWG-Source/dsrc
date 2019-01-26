package script.working.jfreeman;

import script.dictionary;
import script.obj_id;

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
