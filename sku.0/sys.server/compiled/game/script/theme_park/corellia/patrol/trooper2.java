package script.theme_park.corellia.patrol;

import script.obj_id;

public class trooper2 extends script.base_script
{
    public trooper2()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id hideout = getObjIdObjVar(self, "hideout");
        messageTo(hideout, "trooper2Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
