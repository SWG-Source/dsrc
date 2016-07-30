package script.theme_park;

import script.dictionary;
import script.library.ai_lib;
import script.location;
import script.obj_id;

public class patrol_member extends script.base_script
{
    public patrol_member(){}
    public void startPatrol(obj_id self, dictionary params) throws InterruptedException{
        if(!isValidId(self) || !exists(self) || !getBooleanObjVar(self, "isLeader")) return;
        location[] patrolPts = params.getLocationArray("patrolPoints");

        if(params.getBoolean("flipPaths")){
            ai_lib.setPatrolFlipPath(self, patrolPts);
        }
        else {
            ai_lib.setPatrolPath(self, patrolPts);
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id spawner = getObjIdObjVar(self, "spawner");

        dictionary dict = new dictionary();
        dict.put("isLeader", getBooleanObjVar(self, "isLeader"));
        dict.put("self", self);

        messageTo(spawner, "patrolMemberDied", dict, 20, true);
        return SCRIPT_CONTINUE;
    }
}
