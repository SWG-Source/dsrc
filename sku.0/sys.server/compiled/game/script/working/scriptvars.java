package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class scriptvars extends script.base_script
{
    public scriptvars()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        deltadictionary dctScriptVars = self.getScriptVars();
        debugSpeakMsg(self, "ScriptVars are " + dctScriptVars.toString());
        detachScript(self, "working.scriptvars");
        return SCRIPT_CONTINUE;
    }
}
