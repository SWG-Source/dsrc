package script.working.tfiala;

import script.obj_id;

public class posture_test extends script.base_script
{
    public posture_test()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        switch (strText) {
            case "posture_kneel":
                setPostureClientImmediate(self, POSTURE_CROUCHED);
                break;
            case "posture_stand":
                setPostureClientImmediate(self, POSTURE_UPRIGHT);
                break;
            case "posture_prone":
                setPostureClientImmediate(self, POSTURE_PRONE);
                break;
            case "posture_sit":
                setPostureClientImmediate(self, POSTURE_SITTING);
                break;
            case "posture_incapacitated":
                setPostureClientImmediate(self, POSTURE_INCAPACITATED);
                break;
        }
        return SCRIPT_CONTINUE;
    }
}
