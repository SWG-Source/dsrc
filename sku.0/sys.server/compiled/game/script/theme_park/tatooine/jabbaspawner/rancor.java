package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class rancor extends script.base_script
{
    public rancor()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "rancorDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
