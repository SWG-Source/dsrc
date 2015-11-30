package script.working.cmayer;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class inttest extends script.base_script
{
    public inttest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("test"))
        {
            debugConsoleMsg(speaker, "Yaw is " + getYaw(self));
            setYaw(self, 180);
            debugConsoleMsg(speaker, "Yaw is " + getYaw(self));
        }
        else if (text.equals("test2"))
        {
            int x = dataTableSearchColumnForInt(2, 7, "datatables/combat/combat_data.iff");
            debugConsoleMsg(speaker, "Row is " + x);
            x = dataTableSearchColumnForInt(2, 8, "datatables/combat/combat_data.iff");
            debugConsoleMsg(speaker, "Invalid Column Row is " + x);
            x = dataTableSearchColumnForInt(2, 7, "datatables/combat/combats_data.iff");
            debugConsoleMsg(speaker, "Invalid Datatable Row is " + x);
        }
        else if (text.equals("test3"))
        {
            location loc = getLocation(self);
            setObjVar(self, "test1", loc);
            setObjVar(self, "test2", loc);
            setObjVar(self, "test3", loc);
            setObjVar(self, "test4", loc);
            setObjVar(self, "test5", loc);
            setObjVar(self, "test6", loc);
            setObjVar(self, "test7", loc);
            setObjVar(self, "test8", loc);
            setObjVar(self, "test9", loc);
            setObjVar(self, "test10", loc);
        }
        else if (text.equals("test4"))
        {
            removeObjVar(self, "test1");
            removeObjVar(self, "test2");
            removeObjVar(self, "test3");
            removeObjVar(self, "test4");
            removeObjVar(self, "test5");
            removeObjVar(self, "test6");
            removeObjVar(self, "test7");
            removeObjVar(self, "test8");
            removeObjVar(self, "test9");
            removeObjVar(self, "test10");
        }
        else if (text.equals("test5"))
        {
            Vector ids = new Vector();
            ids.setSize(0);
            ids.clear();
            utils.addElement(ids, self);
            setObjVar(self, "testids", ids);
        }
        else if (text.equals("test6"))
        {
            Vector ids = getResizeableObjIdArrayObjVar(self, "testids");
            int idx = utils.getElementPositionInArray(ids, self);
            if (idx > -1)
            {
                ids = utils.removeElementAt(ids, idx);
                setObjVar(self, "testids", ids);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (text.equals("shirt"))
        {
            obj_id target = getLookAtTarget(self);
            boolean retval = equipOverride(target, self);
            debugServerConsoleMsg(self, "Retval is " + retval);
        }
        return SCRIPT_CONTINUE;
    }
}
