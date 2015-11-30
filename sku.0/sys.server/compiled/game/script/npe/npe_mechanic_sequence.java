package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class npe_mechanic_sequence extends script.base_script
{
    public npe_mechanic_sequence()
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
        setName(self, "Idau");
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
