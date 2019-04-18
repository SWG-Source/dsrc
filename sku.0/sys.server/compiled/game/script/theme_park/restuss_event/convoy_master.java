package script.theme_park.restuss_event;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.location;
import script.obj_id;

public class convoy_master extends script.base_script
{
    public convoy_master()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 180, false);
        messageTo(self, "findWayPoints", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int findWayPoints(obj_id self, dictionary params) throws InterruptedException
    {
        String faction = getStringObjVar(self, "alignment");
        debugSpeakMsg(self, "alignment is: " + faction);
        location[] patrolPoints = new location[4];
        if (faction.equals("imperial"))
        {
            obj_id[] points = getAllObjectsWithObjVar(getLocation(self), 600.0f, "restuss_imperial_ambush");
            if (points == null || points.length == 0)
            {
                debugSpeakMsg(self, "Points was null or zero");
            }
            for (obj_id point : points) {
                int value = getIntObjVar(point, "restuss_imperial_ambush");
                switch (value) {
                    case 1:
                        patrolPoints[0] = getLocation(point);
                        break;
                    case 2:
                        patrolPoints[1] = getLocation(point);
                        break;
                    case 3:
                        patrolPoints[2] = getLocation(point);
                        break;
                    case 4:
                        patrolPoints[3] = getLocation(point);
                }
            }
        }
        if (faction.equals("rebel"))
        {
            obj_id[] points = getAllObjectsWithObjVar(getLocation(self), 600.0f, "restuss_rebel_ambush");
            for (obj_id point : points) {
                int value = getIntObjVar(point, "restuss_rebel_ambush");
                switch (value) {
                    case 1:
                        patrolPoints[0] = getLocation(point);
                        break;
                    case 2:
                        patrolPoints[1] = getLocation(point);
                        break;
                    case 3:
                        patrolPoints[2] = getLocation(point);
                        break;
                    case 4:
                        patrolPoints[3] = getLocation(point);
                }
            }
        }
        ai_lib.setPatrolOncePath(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "destroySelf", null, 60, false);
            return SCRIPT_CONTINUE;
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
