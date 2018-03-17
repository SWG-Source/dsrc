package script.event.gcwraids;

import script.dictionary;
import script.obj_id;

public class cheerleader_escort extends script.base_script
{
    public cheerleader_escort()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("myId", getIntObjVar(self, "event.gcwraids.myId"));
        params.put("escort", self);
        messageTo(getObjIdObjVar(self, "event.gcwraids.mom"), "escortDied", params, 1, false);
        return SCRIPT_CONTINUE;
    }
}
