package script.working.tfiala;

import script.obj_id;

public class posture_test extends script.base_script
{
    public posture_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("posture_kneel"))
        {
            setPostureClientImmediate(self, POSTURE_CROUCHED);
        }
        else if (strText.equals("posture_stand"))
        {
            setPostureClientImmediate(self, POSTURE_UPRIGHT);
        }
        else if (strText.equals("posture_prone"))
        {
            setPostureClientImmediate(self, POSTURE_PRONE);
        }
        else if (strText.equals("posture_sit"))
        {
            setPostureClientImmediate(self, POSTURE_SITTING);
        }
        else if (strText.equals("posture_incapacitated"))
        {
            setPostureClientImmediate(self, POSTURE_INCAPACITATED);
        }
        return SCRIPT_CONTINUE;
    }
}
