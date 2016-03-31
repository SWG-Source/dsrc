package script.city;

import script.dictionary;
import script.obj_id;

public class city_mob_death_msg extends script.base_script
{
    public city_mob_death_msg()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id cityMobSpawner = getObjIdObjVar(self, "cityMobSpawner");
        dictionary gangsta = new dictionary();
        gangsta.put("deadNpc", self);
        messageTo(cityMobSpawner, "cityMobKilled", gangsta, 1800, false);
        return SCRIPT_CONTINUE;
    }
}
