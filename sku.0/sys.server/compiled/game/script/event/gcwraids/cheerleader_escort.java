package script.event.gcwraids;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class cheerleader_escort extends script.base_script
{
    public cheerleader_escort()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        int myNumber = getIntObjVar(self, "event.gcwraids.myId");
        dictionary params = new dictionary();
        params.put("myId", myNumber);
        params.put("escort", self);
        obj_id mom = getObjIdObjVar(self, "event.gcwraids.mom");
        messageTo(mom, "escortDied", params, 1, false);
        return SCRIPT_CONTINUE;
    }
}
