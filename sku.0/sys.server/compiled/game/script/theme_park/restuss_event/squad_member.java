package script.theme_park.restuss_event;

import script.dictionary;
import script.library.ai_lib;
import script.library.restuss_event;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

public class squad_member extends script.base_script
{
    public squad_member()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setInterest(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, restuss_event.PATROL_POINTS);
        ai_lib.setPatrolOncePath(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/event_squad_member/" + section, message);
        }
    }
}
