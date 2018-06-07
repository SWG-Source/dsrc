package script.theme_park.tatooine.sarlacc;

import script.obj_id;

public class sarlacc_death extends script.base_script
{
    public sarlacc_death()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "spawner");
        messageTo(spawner, "sarlaccDied", null, 5, true);
        return SCRIPT_CONTINUE;
    }
}
