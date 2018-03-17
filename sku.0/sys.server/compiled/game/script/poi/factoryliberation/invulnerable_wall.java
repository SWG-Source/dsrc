package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class invulnerable_wall extends script.base_script
{
    public invulnerable_wall()
    {
    }
    public static final String LOG_NAME = "poiFactoryLiberation Invulnerable Wall";
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int max_hitpoints = getMaxHitpoints(self);
        setHitpoints(self, max_hitpoints);
        return SCRIPT_CONTINUE;
    }
}
