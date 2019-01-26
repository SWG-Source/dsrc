package script.working.cmayer;

import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class inttest extends script.base_script
{
    public inttest()
    {
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        switch (text) {
            case "test":
                debugConsoleMsg(speaker, "Yaw is " + getYaw(self));
                setYaw(self, 180);
                debugConsoleMsg(speaker, "Yaw is " + getYaw(self));
                break;
            case "test2":
                int x = dataTableSearchColumnForInt(2, 7, "datatables/combat/combat_data.iff");
                debugConsoleMsg(speaker, "Row is " + x);
                x = dataTableSearchColumnForInt(2, 8, "datatables/combat/combat_data.iff");
                debugConsoleMsg(speaker, "Invalid Column Row is " + x);
                x = dataTableSearchColumnForInt(2, 7, "datatables/combat/combats_data.iff");
                debugConsoleMsg(speaker, "Invalid Datatable Row is " + x);
                break;
            case "test3":
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
                break;
            case "test4":
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
                break;
            case "test5": {
                Vector ids = new Vector();
                ids.setSize(0);
                ids.clear();
                utils.addElement(ids, self);
                setObjVar(self, "testids", ids);
                break;
            }
            case "test6": {
                Vector ids = getResizeableObjIdArrayObjVar(self, "testids");
                int idx = utils.getElementPositionInArray(ids, self);
                if (idx > -1) {
                    ids = utils.removeElementAt(ids, idx);
                    setObjVar(self, "testids", ids);
                }
                break;
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
