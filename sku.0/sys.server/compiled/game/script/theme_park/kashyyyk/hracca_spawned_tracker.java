package script.theme_park.kashyyyk;

import script.dictionary;
import script.obj_id;

public class hracca_spawned_tracker extends script.base_script
{
    public hracca_spawned_tracker()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (!hasObjVar(self, "cleaningUp"))
        {
            obj_id objParent = getObjIdObjVar(self, "objParent");
            dictionary webster = new dictionary();
            webster.put("deadNpc", self);
            messageTo(objParent, "spawnDestroyed", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "cleaningUp"))
        {
            obj_id objParent = getObjIdObjVar(self, "objParent");
            dictionary webster = new dictionary();
            webster.put("destroyedNpc", self);
            messageTo(objParent, "spawnDestroyed", webster, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
