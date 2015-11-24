package script.working.jfreeman;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class scriptvartest extends script.base_script
{
    public scriptvartest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!text.equals("scriptvartest"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getLookAtTarget(speaker);
        if (target == null)
        {
            debugSpeakMsg(self, "Look-at something and then say scriptvartest");
            return SCRIPT_CONTINUE;
        }
        else if (target == self)
        {
            debugSpeakMsg(self, "I know I can set scriptvars on myself.  Look at something else");
            return SCRIPT_CONTINUE;
        }
        deltadictionary targetScriptVars = target.getScriptVars();
        int testScriptVar = targetScriptVars.getInt("scriptvartest");
        debugSpeakMsg(self, "spriptvar test value on " + target + " is " + testScriptVar);
        testScriptVar++;
        targetScriptVars.put("scriptvartest", testScriptVar);
        deltadictionary newTargetScriptVars = target.getScriptVars();
        int newTestScriptVar = newTargetScriptVars.getInt("scriptvartest");
        debugSpeakMsg(self, "Immediately: spriptvar test value on " + target + " is " + newTestScriptVar);
        dictionary parms = new dictionary();
        parms.put("target", target);
        messageTo(self, "handleRecheck", parms, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRecheck(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = params.getObjId("target");
        if (target == null || target == obj_id.NULL_ID)
        {
            debugSpeakMsg(self, "target is fuxed");
            return SCRIPT_CONTINUE;
        }
        deltadictionary targetScriptVars = target.getScriptVars();
        int testScriptVar = targetScriptVars.getInt("scriptvartest");
        debugSpeakMsg(self, "After Call Back: spriptvar test value on " + target + " is " + testScriptVar);
        return SCRIPT_CONTINUE;
    }
}
