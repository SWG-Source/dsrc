package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class gcw_mini_turret extends script.base_script
{
    public gcw_mini_turret()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 60.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
