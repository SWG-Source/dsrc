package script.hnguyen;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.StringTokenizer;

public class scriptvar_test extends script.base_script
{
    public scriptvar_test()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.startsWith("ahh"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String id = st.nextToken();
                obj_id target = utils.stringToObjId(id);
                if (isIdValid(target))
                {
                    if (exists(target))
                    {
                        dictionary d = target.getScriptDictionary();
                        sendSystemMessageTestingOnly(self, "scriptDictionary for " + target + " are: " + d.toString());
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "ahh " + target + " - no object");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "ahh " + target + " - bad id");
                }
            }
        }
        else if (strText.startsWith("ohh"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                String id = st.nextToken();
                String var = st.nextToken();
                String val = st.nextToken();
                obj_id target = utils.stringToObjId(id);
                if (isIdValid(target))
                {
                    if (exists(target))
                    {
                        dictionary d = target.getScriptDictionary();
                        d.put(var, val);
                        sendSystemMessageTestingOnly(self, "Setting scriptDictionary for " + target + " " + var + "=" + val);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "ohh " + target + " - no object");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "ohh " + target + " - bad id");
                }
            }
        }
        else if (strText.startsWith("ah"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 2)
            {
                String command = st.nextToken();
                String id = st.nextToken();
                obj_id target = utils.stringToObjId(id);
                if (isIdValid(target))
                {
                    if (exists(target))
                    {
                        deltadictionary dctScriptVars = target.getScriptVars();
                        sendSystemMessageTestingOnly(self, "Scriptvars for " + target + " are: " + dctScriptVars.toString());
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "ah " + target + " - no object");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "ah " + target + " - bad id");
                }
            }
        }
        else if (strText.startsWith("oh"))
        {
            StringTokenizer st = new StringTokenizer(strText);
            if (st.countTokens() == 4)
            {
                String command = st.nextToken();
                String id = st.nextToken();
                String var = st.nextToken();
                String val = st.nextToken();
                obj_id target = utils.stringToObjId(id);
                if (isIdValid(target))
                {
                    if (exists(target))
                    {
                        utils.setScriptVar(target, var, val);
                        sendSystemMessageTestingOnly(self, "Setting scriptvar for " + target + " " + var + "=" + val);
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "oh " + target + " - no object");
                    }
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "oh " + target + " - bad id");
                }
            }
        }
        else if (strText.equals("gc"))
        {
            sendSystemMessageTestingOnly(self, "telling Java to do garbage collection");
            System.gc();
        }
        else if (strText.equals("getGameTime"))
        {
            sendSystemMessageTestingOnly(self, "getGameTime() returns " + getGameTime());
        }
        return SCRIPT_CONTINUE;
    }
}
