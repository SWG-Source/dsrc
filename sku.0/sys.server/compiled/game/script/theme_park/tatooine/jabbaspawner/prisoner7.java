package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class prisoner7 extends script.base_script
{
    public prisoner7()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "prisoner7Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
