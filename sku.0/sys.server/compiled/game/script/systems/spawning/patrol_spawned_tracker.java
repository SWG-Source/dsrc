package script.systems.spawning;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.location;
import script.obj_id;

public class patrol_spawned_tracker extends script.base_script
{
    public patrol_spawned_tracker()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "respawnAfterDestroy"))
        {
            obj_id objParent = utils.getObjIdScriptVar(self, "parent");
            float fltRespawnTime = utils.getFloatScriptVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id objAttacker) throws InterruptedException
    {
        if (!hasObjVar(self, "intCleaningUp"))
        {
            obj_id objParent = utils.getObjIdScriptVar(self, "parent");
            float fltRespawnTime = utils.getFloatScriptVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "pathToNextPoint", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        String patrolType = utils.getStringScriptVar(self, "patrolPathType");
        int pointNum = utils.getIntScriptVar(self, "startingPoint");
        if (patrolType.equals("cycle"))
        {
            ai_lib.setPatrolPath(self, patrolPoints, pointNum);
        }
        else 
        {
            ai_lib.setPatrolFlipPath(self, patrolPoints, pointNum);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/patrol_spawned_tracker/" + section, message);
        }
    }
}
