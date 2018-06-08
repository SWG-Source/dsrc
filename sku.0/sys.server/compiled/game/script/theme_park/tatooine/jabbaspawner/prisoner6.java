package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class prisoner6 extends script.base_script
{
    public prisoner6()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "prisoner6Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
