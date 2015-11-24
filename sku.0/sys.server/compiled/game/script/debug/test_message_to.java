package script.debug;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class test_message_to extends script.base_script
{
    public test_message_to()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (!isGod(self))
        {
            return SCRIPT_CONTINUE;
        }
        boolean handled = true;
        if (text.equals("startmessagetest"))
        {
            obj_id target = getLookAtTarget(self);
            if (isIdValid(target))
            {
                if (!target.hasScript("debug.test_message_to"))
                {
                    attachScript(target, "debug.test_message_to");
                }
            }
            else 
            {
                target = self;
            }
            deltadictionary scriptVars = self.getScriptVars();
            scriptVars.put("messagetestcount", 0);
            scriptVars.put("messagetestspam", false);
            runTest(self, target, 1);
        }
        else if (text.equals("stopmessagetest"))
        {
            deltadictionary scriptVars = self.getScriptVars();
            scriptVars.remove("messagetestcount");
            scriptVars.remove("messagetesttest");
            scriptVars.remove("messagetestspam");
        }
        else if (text.equals("messagetestinfo"))
        {
            deltadictionary scriptVars = self.getScriptVars();
            debugServerConsoleMsg(null, "messagetest count = " + scriptVars.getInt("messagetestcount") + ", current test = " + scriptVars.getInt("messagetesttest"));
        }
        else if (text.equals("messagetestspam"))
        {
            deltadictionary scriptVars = self.getScriptVars();
            boolean spam = scriptVars.getBoolean("messagetestspam");
            scriptVars.put("messagetestspam", !spam);
        }
        else 
        {
            handled = false;
        }
        if (handled)
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean runTest(obj_id self, obj_id target, int test) throws InterruptedException
    {
        deltadictionary scriptVars = self.getScriptVars();
        if (!scriptVars.hasKey("messagetestcount"))
        {
            return true;
        }
        boolean spam = scriptVars.getBoolean("messagetestspam");
        if (spam)
        {
            debugServerConsoleMsg(null, "testing messageTo, running test " + test);
        }
        dictionary params = new dictionary();
        params.put("from", self);
        params.put("time", getGameTime());
        params.put("test", test);
        switch (test)
        {
            case 1:
            params.put("delay", 0.0f);
            params.put("persist", false);
            break;
            case 2:
            params.put("delay", 5.0f);
            params.put("persist", false);
            break;
            case 3:
            params.put("delay", 120.0f);
            params.put("persist", false);
            break;
            case 4:
            params.put("delay", 0.0f);
            params.put("persist", true);
            break;
            case 5:
            params.put("delay", 5.0f);
            params.put("persist", true);
            break;
            case 6:
            params.put("delay", 120.0f);
            params.put("persist", true);
            break;
            default:
            return false;
        }
        scriptVars.put("messagetestcount", scriptVars.getInt("messagetestcount") + 1);
        scriptVars.put("messagetesttest", test);
        messageTo(target, "testHandler", params, params.getFloat("delay"), params.getBoolean("persist"));
        return true;
    }
    public int testHandler(obj_id self, dictionary params) throws InterruptedException
    {
        int currentTime = getGameTime();
        float messageDelay = currentTime - params.getInt("time");
        if (params.getFloat("delay") > 0)
        {
            if (messageDelay / params.getFloat("delay") > 1.5f)
            {
                debugServerConsoleMsg(null, "WARNING: testing messageTo, test " + params.getInt("test") + " had a long delay of " + messageDelay + " secs, desired = " + params.getFloat("delay"));
            }
        }
        else if (messageDelay > 1.0f)
        {
            debugServerConsoleMsg(null, "WARNING: testing messageTo, test " + params.getInt("test") + " had a long delay of " + messageDelay + " secs, desired = 0");
        }
        if (self == params.getObjId("from"))
        {
            deltadictionary scriptVars = self.getScriptVars();
            boolean spam = scriptVars.getBoolean("messagetestspam");
            if (spam)
            {
                debugServerConsoleMsg(null, "testing messageTo, test " + params.getInt("test") + " completed with a delay of " + messageDelay);
            }
            if (!runTest(self, self, params.getInt("test") + 1))
            {
                runTest(self, self, 1);
            }
        }
        else 
        {
            dictionary newParams = new dictionary();
            newParams.put("target", self);
            newParams.put("test", params.getInt("test"));
            newParams.put("delay", messageDelay);
            messageTo(params.getObjId("from"), "nextTestHandler", newParams, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int nextTestHandler(obj_id self, dictionary params) throws InterruptedException
    {
        deltadictionary scriptVars = self.getScriptVars();
        boolean spam = scriptVars.getBoolean("messagetestspam");
        if (spam)
        {
            debugServerConsoleMsg(null, "testing messageTo, test " + params.getInt("test") + " completed with a delay of " + params.getFloat("delay"));
        }
        obj_id target = params.getObjId("target");
        if (!runTest(self, target, params.getInt("test") + 1))
        {
            runTest(self, target, 1);
        }
        return SCRIPT_CONTINUE;
    }
}
