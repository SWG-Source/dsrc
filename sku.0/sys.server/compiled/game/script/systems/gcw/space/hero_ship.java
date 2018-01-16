package script.systems.gcw.space;

import script.obj_id;
import script.dictionary;

public class hero_ship extends support_ship {
    @Override
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException {
        float hitpoints = getShipCurrentChassisHitPoints(self);
        if(hitpoints < 2000) {
            obj_id motherShip = getObjIdObjVar(self, "motherShip");
            dictionary params = new dictionary();
            params.put("destroyedShip", self);
            setInvulnerable(self, true);

            // TODO: send taunt to player before hyperspacing out.

            messageTo(motherShip, "removeHeroShip", params, 0.0f, false);
            destroyObjectHyperspace(self);
        }

        return super.OnShipWasHit(self, attacker, weaponIndex, isMissile, missileType, intSlot, fromPlayerAutoTurret, hitLocationX_o, hitLocationY_o, hitLocationZ_o);
    }
}
