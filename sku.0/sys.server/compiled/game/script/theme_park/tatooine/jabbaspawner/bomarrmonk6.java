package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class bomarrmonk6 extends script.base_script
{
    public bomarrmonk6()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "bomarrMonk6Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
