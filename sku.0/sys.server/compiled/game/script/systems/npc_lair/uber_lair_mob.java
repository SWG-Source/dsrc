package script.systems.npc_lair;

import script.dictionary;
import script.location;
import script.obj_id;

public class uber_lair_mob extends script.base_script
{
    public uber_lair_mob()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id objParent = getObjIdObjVar(self, "uberlair.objParent");
        dictionary dctParams = new dictionary();
        String strType = getStringObjVar(self, "uberlair.strType");
        location locSpawnLocation = getLocationObjVar(self, "uberlair.locSpawnLocation");
        String strScript = getStringObjVar(self, "uberlair.strScript");
        dctParams.put("locSpawnLocation", locSpawnLocation);
        dctParams.put("strType", strType);
        dctParams.put("strScript", strScript);
        messageTo(objParent, "mobDestroyed", dctParams, 3, false);
        return SCRIPT_CONTINUE;
    }
}
