package script.working.tfiala;

import script.obj_id;

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
