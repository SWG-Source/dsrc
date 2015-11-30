package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class spawned_pirate_tracker extends script.base_script
{
    public spawned_pirate_tracker()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id objAttacker) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            obj_id objParent = getObjIdObjVar(self, "objParent");
            float fltRespawnTime = getFloatObjVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "intCleanedUp"))
        {
            utils.setScriptVar(self, "intCleanedUp", 1);
            obj_id objParent = getObjIdObjVar(self, "objParent");
            float fltRespawnTime = getFloatObjVar(self, "fltRespawnTime");
            messageTo(objParent, "spawnDestroyed", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
