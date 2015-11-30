package script.content_tools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sequencer;

public class sequencer_object extends script.base_script
{
    public sequencer_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "strSequenceIdentifier"))
        {
            setName(self, "Sequencer Name: " + getStringObjVar(self, "strSequenceIdentifier"));
            LOG("npe", "test2");
        }
        else 
        {
            setName(self, "No Sequence Identifier set");
        }
        messageTo(self, "doPreloadRequest", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "strSequenceIdentifier"))
        {
            setName(self, "Sequencer Name: " + getStringObjVar(self, "strSequenceIdentifier"));
            LOG("npe", "test2");
        }
        else 
        {
            setName(self, "No Sequence Identifier set");
        }
        messageTo(self, "doPreloadRequest", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int doPreloadRequest(obj_id self, dictionary params) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        sequencer.registerSequenceObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        sequencer.cleanUpSequenceObject(self);
        return SCRIPT_CONTINUE;
    }
}
