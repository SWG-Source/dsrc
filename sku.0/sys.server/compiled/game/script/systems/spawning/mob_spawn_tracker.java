package script.systems.spawning;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class mob_spawn_tracker extends script.base_script
{
    public mob_spawn_tracker()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id objParent = utils.getObjIdScriptVar(self, "objParent");
        float fltRespawnTime = utils.getFloatScriptVar(self, "fltRespawnTime");
        messageTo(objParent, "respawnMob", null, fltRespawnTime, false);
        return SCRIPT_CONTINUE;
    }
}
