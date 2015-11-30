package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npe;
import script.library.utils;

public class npe_station_alarm extends script.base_script
{
    public npe_station_alarm()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "doPreloadRequest", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "doPreloadRequest", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        LOG("npe_alarm", "on attach fired, and building is " + building);
        Vector objAlarms = new Vector();
        objAlarms.setSize(0);
        if (utils.hasScriptVar(building, "objAlarms"))
        {
            objAlarms = utils.getResizeableObjIdArrayScriptVar(building, "objAlarms");
        }
        objAlarms = utils.addElement(objAlarms, self);
        utils.setScriptVar(building, "objAlarms", objAlarms);
        return SCRIPT_CONTINUE;
    }
    public int doPreloadRequest(obj_id self, dictionary params) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
}
