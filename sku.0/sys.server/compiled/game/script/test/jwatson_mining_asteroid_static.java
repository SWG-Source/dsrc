package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.sui;
import script.library.quests;
import script.library.ai_lib;
import script.library.money;
import script.library.chat;
import script.library.pclib;
import script.library.vehicle;
import script.library.ship_ai;

public class jwatson_mining_asteroid_static extends script.base_script
{
    public jwatson_mining_asteroid_static()
    {
    }
    public int OnShipInternalDamageOverTimeRemoved(obj_id self, int chassisSlot, float damageRate, float damageThreshold) throws InterruptedException
    {
        obj_id pilot = getPilotId(self);
        if (pilot != null)
        {
            sendSystemMessageTestingOnly(pilot, "jwatson_ship IDOT removed slot=" + chassisSlot + ", damageRate=" + damageRate + ", threshold=" + damageThreshold);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int chassisSlot, boolean isPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        obj_id attackingPilot = getPilotId(attacker);
        location attackingLocation = getLocation(attacker);
        vector attackingPosition_w = new vector(attackingLocation.x, attackingLocation.y, attackingLocation.z);
        transform selfTransform = getTransform_o2w(self);
        vector attackingLocation_o = selfTransform.rotateTranslate_p2l(attackingPosition_w);
        int maxHitpoints = getMaxHitpoints(self);
        int oldHitpoints = getHitpoints(self);
        setHitpoints(self, oldHitpoints - 3);
        int newHitpoints = getHitpoints(self);
        sendSystemMessageTestingOnly(attackingPilot, "hit asteroid static " + newHitpoints + "/" + maxHitpoints);
        if (newHitpoints <= 0)
        {
            sendSystemMessageTestingOnly(attackingPilot, "*** asteroid static DESTROYED");
            handleShipDestruction(self, 1.0f);
        }
        else 
        {
            vector hitLocation_o = new vector(hitLocationX_o, hitLocationY_o, hitLocationZ_o);
            notifyShipHit(self, attackingLocation_o, hitLocation_o, ship_hit_type.HT_chassis, 0.5f, 1.0f);
            int newDamageBracket = newHitpoints / 10;
            int oldDamageBracket = oldHitpoints / 10;
            int damageBracketDifference = oldDamageBracket - newDamageBracket;
            vector direction_o = attackingLocation_o.approximateNormalize();
            location selfLocation = getLocation(self);
            for (int i = 0; i < damageBracketDifference; ++i)
            {
                vector spawnDirection_o = new vector(direction_o.x * random.rand(), direction_o.y * random.rand(), direction_o.z * random.rand());
                spawnDirection_o = spawnDirection_o.approximateNormalize();
                spawnDirection_o = spawnDirection_o.multiply(40.0f + (random.rand() * 40.0f));
                obj_id spawnDynamicAsteroid = createObject("object/ship/asteroid/mining_asteroid_dynamic_default.iff", selfLocation);
                sendSystemMessageTestingOnly(attackingPilot, "*** mini-roid SPAWNED! vel=" + spawnDirection_o + ", mag=" + spawnDirection_o.magnitude());
                setDynamicMiningAsteroidVelocity(spawnDynamicAsteroid, spawnDirection_o);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
