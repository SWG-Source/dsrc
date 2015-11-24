package script.theme_park.dungeon.myyydril;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class machine extends script.base_script
{
    public machine()
    {
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        obj_id contentManager = getObjIdObjVar(self, "grievous_encounter.contentManager");
        int mhp = getMaxHitpoints(self);
        int hp = getHitpoints(self);
        if (hp > (.75 * mhp))
        {
            messageTo(contentManager, "handleSpawnSpeedFast", null, 1.0f, false);
        }
        if (hp > (.50 * mhp) && hp < (.75 * mhp))
        {
            messageTo(contentManager, "handleSpawnSpeedMed", null, 1.0f, false);
        }
        if (hp > (.25 * mhp) && hp < (.50 * mhp))
        {
            messageTo(contentManager, "handleSpawnSpeedSlow", null, 1.0f, false);
        }
        if (hp > (0) && hp < (.25 * mhp))
        {
            messageTo(contentManager, "handleSpawnSpeedReallySlow", null, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
