package script.event.emp_day;

import script.dictionary;
import script.library.holiday;
import script.obj_id;

public class player_sign_eventually_destroyed extends script.base_script
{
    public player_sign_eventually_destroyed()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasScript(self, holiday.EVENT_TRACKER_SCRIPT))
        {
            attachScript(self, holiday.EVENT_TRACKER_SCRIPT);
        }
        int destroyTimer = rand(holiday.MIN_DESTROY_TIME, holiday.MAX_DESTROY_TIME);
        if (destroyTimer <= 0)
        {
            destroyTimer = 5;
        }
        messageTo(self, "destroySelf", null, destroyTimer, false);
        setObjVar(self, "destroySeconds", destroyTimer);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
