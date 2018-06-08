package script.working;

import script.deltadictionary;
import script.obj_id;

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
