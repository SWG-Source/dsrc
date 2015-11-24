package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class npe_turret_ship extends script.base_script
{
    public npe_turret_ship()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = utils.getObjIdScriptVar(self, "objParent");
        messageTo(parent, "shipDied", null, 0, true);
        return SCRIPT_CONTINUE;
    }
}
