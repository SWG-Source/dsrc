package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class crumb extends script.base_script
{
    public crumb()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "crumbDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
