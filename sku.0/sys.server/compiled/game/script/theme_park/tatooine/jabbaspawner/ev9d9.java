package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class ev9d9 extends script.base_script
{
    public ev9d9()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "ev9d9Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
