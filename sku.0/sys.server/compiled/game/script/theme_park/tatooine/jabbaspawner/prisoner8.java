package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class prisoner8 extends script.base_script
{
    public prisoner8()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "prisoner8Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
