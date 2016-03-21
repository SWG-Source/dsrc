package script.city.bestine;

import script.obj_id;

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
        setObjVar(self, "alreadySentDeathMessage", 1);
        messageTo(getObjIdObjVar(self, "objRaidSpawner"), "elementDestroyed", null, 1, false);
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
