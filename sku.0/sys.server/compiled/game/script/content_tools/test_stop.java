package script.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sequencer;
import script.library.chat;
import script.library.utils;

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
