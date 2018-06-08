package script.working.tfiala;

import script.obj_id;

public class wound_me extends script.base_script
{
    public wound_me()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
