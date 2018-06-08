package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class pdroid extends script.base_script
{
    public pdroid()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "pdroidDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
