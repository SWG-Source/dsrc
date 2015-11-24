package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class tcg_self_destruct extends script.base_script
{
    public tcg_self_destruct()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startTimer", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "startTimer", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int startTimer(obj_id self, dictionary params) throws InterruptedException
    {
        int timer = 0;
        if (!hasObjVar(self, "timer"))
        {
            timer = 25;
        }
        else 
        {
            timer = getIntObjVar(self, "timer");
            if (timer < 0)
            {
                timer = 25;
            }
        }
        messageTo(self, "selfDestruct", null, (float)timer, false);
        return SCRIPT_CONTINUE;
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
