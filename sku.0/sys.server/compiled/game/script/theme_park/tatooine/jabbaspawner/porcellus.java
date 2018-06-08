package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class porcellus extends script.base_script
{
    public porcellus()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "porcellusDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
