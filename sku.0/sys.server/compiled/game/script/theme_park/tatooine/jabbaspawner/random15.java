package script.theme_park.tatooine.jabbaspawner;

import script.dictionary;
import script.obj_id;

public class random15 extends script.base_script
{
    public random15()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "random15Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int doFacing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id faceTarget = getObjIdObjVar(self, "facer");
        faceTo(self, faceTarget);
        return SCRIPT_CONTINUE;
    }
}
