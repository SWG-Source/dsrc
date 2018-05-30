package script.working.tfiala;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class sit_test extends script.base_script
{
    public sit_test()
    {
    }
    public String[] strSplit(String strText) throws InterruptedException
    {
        obj_id self = null;
        int intIndex = 0;
        int intI = 0;
        intIndex = strText.indexOf(" ");
        String strText1 = "";
        String strText2 = "";
        if (intIndex < 0)
        {
            String[] strReturn = new String[1];
            strReturn[0] = strText;
            return strReturn;
        }
        else 
        {
            while (intI < strText.length())
            {
                if (intI < intIndex)
                {
                    strText1 = strText1 + strText.charAt(intI);
                }
                if (intI > intIndex)
                {
                    strText2 = strText2 + strText.charAt(intI);
                }
                intI = intI + 1;
            }
        }
        String[] strReturn = new String[2];
        strReturn[0] = strText1;
        strReturn[1] = strText2;
        return strReturn;
    }
    public obj_id getObjectIdFromString(String objectIdAsString) throws InterruptedException
    {
        Long lngId = null;
        try
        {
            lngId = new Long(objectIdAsString);
        }
        catch(NumberFormatException err)
        {
            return null;
        }
        return obj_id.getObjId(lngId.longValue());
    }
    public void dumpStringArray(obj_id id, String[] array) throws InterruptedException
    {
        if (array == null)
        {
            debugServerConsoleMsg(id, "string array is null.");
        }
        else 
        {
            debugServerConsoleMsg(id, "string entry count: " + array.length);
            for (int i = 0; i < array.length; ++i)
            {
                debugServerConsoleMsg(id, "string [" + i + "]: [" + array[i] + "].");
            }
        }
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (strText.equals("sit_version"))
        {
            debugSpeakMsg(self, "sit_test: version 0.1");
        }
        else 
        {
            String[] strings = strSplit(strText);
            dumpStringArray(self, strings);
            if ((strings.length == 2) && (strings[0].equals("sitOn")))
            {
                obj_id chairId = getObjectIdFromString(strings[1]);
                if (chairId != null)
                {
                    debugServerConsoleMsg(self, "calling sitOnObject() C code.");
                    sitOnObject(objSpeaker, chairId, 0);
                }
                else 
                {
                    debugServerConsoleMsg(self, "objectId retrieval failed for object id [" + strings[1] + "].");
                }
            }
            else 
            {
                debugServerConsoleMsg(self, "sitOn search criteria not met [" + ((strings.length == 2) ? "true" : "false") + "] [" + ((strings[0].equals("sitOn")) ? "true" : "false") + "].");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
