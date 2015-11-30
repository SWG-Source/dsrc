package script.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

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
