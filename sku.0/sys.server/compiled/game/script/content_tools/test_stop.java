package script.content_tools;

import script.library.sequencer;
import script.library.utils;
import script.obj_id;

public class test_stop extends script.base_script
{
    public test_stop()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("stop"))
        {
            sequencer.stopSequence(self);
            stop(self);
            debugSpeakMsg(self, "Stopping");
        }
        if (strText.equals("go"))
        {
            if (utils.hasScriptVar(self, "intSequenceStopped"))
            {
                utils.setScriptVar(self, "intSequenceContinue", 1);
            }
            else 
            {
                messageTo(self, "doEvents", null, 1, false);
            }
            debugSpeakMsg(self, "Going!");
        }
        return SCRIPT_CONTINUE;
    }
}
