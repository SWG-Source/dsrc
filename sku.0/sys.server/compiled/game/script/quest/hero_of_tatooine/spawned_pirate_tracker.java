package script.quest.hero_of_tatooine;

import script.library.utils;
import script.obj_id;

public class spawned_pirate_tracker extends script.base_script
{
    public spawned_pirate_tracker()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id objAttacker) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            obj_id objParent = getObjIdObjVar(self, "objParent");
            float fltRespawnTime = getFloatObjVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            obj_id objParent = getObjIdObjVar(self, "objParent");
            float fltRespawnTime = getFloatObjVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
