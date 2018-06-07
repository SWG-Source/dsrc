package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class jawa5 extends script.base_script
{
    public jawa5()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "jawa5Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
