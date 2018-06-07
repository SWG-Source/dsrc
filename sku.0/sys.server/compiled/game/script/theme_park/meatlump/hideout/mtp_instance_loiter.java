package script.theme_park.meatlump.hideout;

import script.library.ai_lib;
import script.obj_id;

public class mtp_instance_loiter extends script.base_script
{
    public mtp_instance_loiter()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        return SCRIPT_CONTINUE;
    }
}
