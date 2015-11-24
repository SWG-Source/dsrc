package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

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
