package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class gamguard11 extends script.base_script
{
    public gamguard11()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "gamGuard11Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
