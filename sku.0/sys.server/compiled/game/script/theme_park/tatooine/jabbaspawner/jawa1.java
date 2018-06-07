package script.theme_park.tatooine.jabbaspawner;

import script.obj_id;

public class jawa1 extends script.base_script
{
    public jawa1()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "jawa1Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
