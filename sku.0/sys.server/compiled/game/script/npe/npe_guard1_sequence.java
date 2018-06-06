package script.npe;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class npe_guard1_sequence extends script.base_script
{
    public npe_guard1_sequence()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "npeSetName", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int npeSetName(obj_id self, dictionary params) throws InterruptedException
    {
        setName(self, "Car'das Guard");
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        boolean setting = utils.checkConfigFlag("ScriptFlags", "npeSequencersActive");
        if (setting == true)
        {
            messageTo(self, "doEvents", null, 60, false);
        }
        return SCRIPT_CONTINUE;
    }
}
