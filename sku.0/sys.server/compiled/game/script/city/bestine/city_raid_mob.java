package script.city.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class city_raid_mob extends script.base_script
{
    public city_raid_mob()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id objRaidSpawner = getObjIdObjVar(self, "objRaidSpawner");
        setObjVar(self, "alreadySentDeathMessage", 1);
        messageTo(objRaidSpawner, "elementDestroyed", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "alreadySentDeathMessage"))
        {
            obj_id objRaidSpawner = getObjIdObjVar(self, "objRaidSpawner");
            messageTo(objRaidSpawner, "elementDestroyed", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
}
