package script.poi.factoryliberation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.poi;

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
        for (int i = 0; i < players.length; i++)
        {
            if ((players[i] != null) && (players[i] != obj_id.NULL_ID))
            {
                playClientEffectLoc(players[i], "clienteffect/combat_grenade_large_01.cef", loc, 0);
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
        float percent_hp = (float)hitpoints / (float)max_hitpoints;
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
