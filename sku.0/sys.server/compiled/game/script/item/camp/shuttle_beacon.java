package script.item.camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.travel;

public class shuttle_beacon extends script.base_script
{
    public shuttle_beacon()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleInitializeShuttleBeacon", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleInitializeShuttleBeacon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, "theater.parent");
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        String name = getName(parent);
        setObjVar(self, travel.VAR_TRAVEL_POINT_NAME, name);
        setObjVar(self, travel.VAR_TRAVEL_COST, 500);
        attachScript(self, "structure.municipal.starport");
        return SCRIPT_CONTINUE;
    }
}
