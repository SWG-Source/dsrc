package script.theme_park.corellia.patrol;

import script.obj_id;

public class trooper4 extends script.base_script
{
    public trooper4()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id hideout = getObjIdObjVar(self, "hideout");
        messageTo(hideout, "trooper4Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
