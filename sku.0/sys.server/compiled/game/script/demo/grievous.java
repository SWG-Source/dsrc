package script.demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class grievous extends script.base_script
{
    public grievous()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id player) throws InterruptedException
    {
        messageTo(self, "destroyCorpse", null, 5.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        int number = getIntObjVar(self, "demoNumber");
        if (number == 1)
        {
            messageTo(player, "resetDemo1", null, 1.0f, true);
        }
        if (number == 2)
        {
            messageTo(player, "resetDemo2", null, 1.0f, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroyCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
