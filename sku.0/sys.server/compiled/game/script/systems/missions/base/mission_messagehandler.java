package script.systems.missions.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mission_messagehandler extends script.base_script
{
    public mission_messagehandler()
    {
    }
    public int addListener(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objListener = params.getObjId("objListener");
        if (!hasObjVar(self, "objListeners"))
        {
            debugServerConsoleMsg(self, "no listener objvar");
            obj_id[] objListeners = new obj_id[1];
            objListeners[0] = objListener;
            setObjVar(self, "objListeners", objListeners);
            params.put("boolListenerStatus", true);
        }
        else 
        {
            debugServerConsoleMsg(self, "listener objVar found");
            obj_id[] objListeners = getObjIdArrayObjVar(self, "objListeners");
            debugServerConsoleMsg(self, "got objListeners array");
            if (objListeners == null)
            {
                debugServerConsoleMsg(self, "NULL OBJLISTENRES");
            }
            debugServerConsoleMsg(self, "objListeners is " + objListeners[0].toString());
            debugServerConsoleMsg(self, "objListeners Length is " + objListeners.length);
            obj_id[] objNewListeners = new obj_id[objListeners.length + 1];
            if (!utils.isObjIdInArray(objListeners, objListener))
            {
                debugServerConsoleMsg(self, "Not isInArray");
                objNewListeners = utils.copyObjIdArray(objListeners, objNewListeners);
                objNewListeners[objNewListeners.length - 1] = objListener;
                setObjVar(self, "objListeners", objNewListeners);
                params.put("boolListenerStatus", true);
            }
            else 
            {
                debugServerConsoleMsg(self, "Already in Array");
                params.put("boolListenerStatus", false);
            }
        }
        messageTo(self, "listener_Attached", params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int removeListener(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "objListeners"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id objListener = params.getObjId("objListener");
            obj_id[] objListeners = getObjIdArrayObjVar(self, "objListeners");
            boolean boolIsInArray;
            boolIsInArray = utils.isObjIdInArray(objListeners, objListener);
            if (objListeners.length > 1)
            {
                obj_id[] objNewListeners = new obj_id[objListeners.length - 1];
                if (!boolIsInArray)
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    debugServerConsoleMsg(self, "objListeners.length = " + objListeners.length);
                    int intI = 0;
                    int intNewCount = 0;
                    while (intI < objListeners.length)
                    {
                        debugServerConsoleMsg(self, "objListener is " + objListener.toString());
                        debugServerConsoleMsg(self, "objListeners [" + intI + "] is " + objListeners[intI].toString());
                        if (!(objListeners[intI] == objListener))
                        {
                            objNewListeners[intNewCount] = objListeners[intI];
                            intNewCount = intNewCount + 1;
                        }
                        intI = intI + 1;
                    }
                    if (objNewListeners.length > 0)
                    {
                        setObjVar(self, "objListeners", objNewListeners);
                    }
                    else 
                    {
                        if (hasObjVar(self, "objListeners"))
                        {
                            removeObjVar(self, "objListeners");
                        }
                    }
                }
            }
            else if (boolIsInArray)
            {
                removeObjVar(self, "objListeners");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
