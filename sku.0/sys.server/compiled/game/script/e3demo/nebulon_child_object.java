package script.e3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.space_combat;
import script.library.space_create;

public class nebulon_child_object extends script.base_script
{
    public nebulon_child_object()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "intReset"))
        {
            obj_id objNebulon = getObjIdObjVar(self, "objNebulon");
            dictionary dctParams = new dictionary();
            dctParams.put("strType", getStringObjVar(self, "strType"));
            LOG("space", "Destroying " + getStringObjVar(self, "strType") + "and notifying " + objNebulon);
            dctParams.put("intAttacker", getIntObjVar(self, "intAttacker"));
            dctParams.put("trSpawnLocation", getTransformObjVar(self, "trSpawnLocation"));
            space_utils.notifyObject(objNebulon, "shipDestroyed", dctParams);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
