package script.theme_park.corellia.patrol;

import script.obj_id;

public class trooper3 extends script.base_script
{
    public trooper3()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id hideout = getObjIdObjVar(self, "hideout");
        messageTo(hideout, "trooper3Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
