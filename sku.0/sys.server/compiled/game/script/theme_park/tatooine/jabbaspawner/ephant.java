package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class ephant extends script.base_script
{
    public ephant()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "ephantDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
