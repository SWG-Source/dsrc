package script.theme_park.nightsister;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class guard_died extends script.base_script
{
    public guard_died()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id cave = getObjIdObjVar(self, "cave");
        int x = utils.getIntScriptVar(cave, "guardCounter");
        if (x > 0)
        {
            x--;
            utils.setScriptVar(cave, "guardCounter", x);
        }
        return SCRIPT_CONTINUE;
    }
}
