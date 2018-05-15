package script.systems.missions.dynamic;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class bounty_seeker extends script.systems.missions.base.mission_dynamic_base
{
    public bounty_seeker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int takeOff(obj_id self, dictionary params) throws InterruptedException
    {
        doAnimationAction(self, "sp_13");
        messageTo(self, "destroySelf", null, 10.3f, true);
        utils.sendPostureChange(self, POSTURE_SITTING);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
