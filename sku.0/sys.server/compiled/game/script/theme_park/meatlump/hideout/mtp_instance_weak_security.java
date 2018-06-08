package script.theme_park.meatlump.hideout;

import script.dictionary;
import script.library.ai_lib;
import script.obj_id;

public class mtp_instance_weak_security extends script.base_script
{
    public mtp_instance_weak_security()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        setMaxAttrib(self, HEALTH, 150);
        return SCRIPT_CONTINUE;
    }
    public int mtpInstanceLevelChanged(obj_id self, dictionary params) throws InterruptedException
    {
        setMaxAttrib(self, HEALTH, 150);
        return SCRIPT_CONTINUE;
    }
}
