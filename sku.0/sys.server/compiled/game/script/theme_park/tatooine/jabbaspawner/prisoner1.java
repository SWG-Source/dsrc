package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class prisoner1 extends script.base_script
{
    public prisoner1()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "prisoner1Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
