package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ai_lib;

public class npe_medbay_sequence extends script.base_script
{
    public npe_medbay_sequence()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "npeSetName", null, 1, false);
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
    public int npeSetName(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "strSequenceIdentifier"))
        {
            String myName = utils.getStringObjVar(self, "strSequenceIdentifier");
            if (myName != null || !myName.equals(""))
            {
                string_id stIdName = new string_id("npe", myName);
                String stfName = utils.packStringId(stIdName);
                if (myName.startsWith("sick"))
                {
                    setAnimationMood(self, "npc_sitting_chair");
                    stIdName = new string_id("npe", "patient");
                    stfName = utils.packStringId(stIdName);
                }
                setName(self, stfName);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
