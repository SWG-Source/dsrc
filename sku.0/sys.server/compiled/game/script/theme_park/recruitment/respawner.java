package script.theme_park.recruitment;

import script.library.ai_lib;
import script.obj_id;

public class respawner extends script.base_script
{
    public respawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        stop(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        stop(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id master = getObjIdObjVar(self, "spawner");
        String job = getStringObjVar(self, "job");
        removeObjVar(master, job);
        String message = job + "Died";
        messageTo(master, message, null, 10, true);
        return SCRIPT_CONTINUE;
    }
}
