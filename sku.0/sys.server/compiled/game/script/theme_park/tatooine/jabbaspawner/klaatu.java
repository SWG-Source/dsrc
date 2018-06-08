package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class klaatu extends script.base_script
{
    public klaatu()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "klaatuDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
