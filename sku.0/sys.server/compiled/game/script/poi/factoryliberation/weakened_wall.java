package script.poi.factoryliberation;

import script.dictionary;
import script.location;
import script.obj_id;

public class weakened_wall extends script.base_script
{
    public weakened_wall()
    {
    }
    public static final String LOG_NAME = "poiFactoryLiberation Weakened Wall";
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id poiMaster = getObjIdObjVar(self, "poi.baseObject");
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(poiMaster, "wallDestroyed", null, 0, false);
        location loc = getLocation(self);
        obj_id players[] = getPlayerCreaturesInRange(loc, 40);
        for (obj_id player : players) {
            if ((player != null) && (player != obj_id.NULL_ID)) {
                playClientEffectLoc(player, "clienteffect/combat_grenade_large_01.cef", loc, 0);
            }
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int hitpoints = getHitpoints(self);
        hitpoints -= 200;
        setHitpoints(self, hitpoints);
        int max_hitpoints = getMaxHitpoints(self);
        float percent_hp = (float)hitpoints / max_hitpoints;
        boolean wallDamaged = getBooleanObjVar(self, "wallDamaged");
        if (!wallDamaged)
        {
            setObjVar(self, "wallDamaged", true);
            obj_id poiMaster = getObjIdObjVar(self, "poi.baseObject");
            if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
            {
                return SCRIPT_CONTINUE;
            }
            dictionary params = new dictionary();
            params.put("attacker", attacker);
            messageTo(poiMaster, "wallDamaged", params, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
