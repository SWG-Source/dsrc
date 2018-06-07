package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class malakili extends script.base_script
{
    public malakili()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "malakiliDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
