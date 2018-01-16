package script.systems.gcw.space;

import script.dictionary;
import script.library.factions;
import script.library.space_utils;
import script.obj_id;

import java.util.Vector;

public class capital_ship extends script.space.combat.combat_ship_capital {
    @Override
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException {
        sendSystemMessageTestingOnly(attacker, "You hit the capital ship!");
        if(!space_utils.isPlayerControlledShip(attacker))
            return SCRIPT_CONTINUE;
        if(getShipFaction(attacker).equals(getShipFaction(self)))
            return SCRIPT_CONTINUE;
        obj_id spawner = getObjIdObjVar(self, "spawner");
        String battleType = getStringObjVar(spawner, "battleType");
        if(space_utils.isPobType(attacker)){
            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(getShipPilot(attacker)))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.pob.participant." + attacker, space_utils.getAllPlayersInShip(attacker));
        }
        else if(getShipChassisType(attacker).startsWith("player_gunship")){
            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(getShipPilot(attacker)))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.gunship.participant." + attacker, space_utils.getAllPlayersInShip(attacker));
        }
        else {
            obj_id player = getShipPilot(attacker);

            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(player))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.participant." + getStringObjVar(self, "battle_id") + "." + player, "1");
        }
        super.OnShipWasHit(self, attacker, weaponIndex, isMissile, missileType, intSlot, fromPlayerAutoTurret, hitLocationX_o, hitLocationY_o, hitLocationZ_o);
        return SCRIPT_CONTINUE;
    }
    @Override
    public int OnDestroy(obj_id self) throws InterruptedException{
        obj_id spawner = getObjIdObjVar(self, "spawner");
        dictionary params = new dictionary();
        params.put("losingFaction", getShipFaction(self));
        params.put("role", getObjVar(self, "role"));
        params.put("spawner", getObjVar(self, "spawner"));
        params.put("destroyedShip", self);
        space_utils.notifyObject(spawner, "captitalShipDestroyed", params);
        return SCRIPT_CONTINUE;
    }

    public int removeSupportShip(obj_id self, dictionary params) throws InterruptedException{
        obj_id destroyedShip = params.getObjId("destroyedShip");
        Vector<obj_id> spawnedShips = getResizeableObjIdArrayObjVar(self, "supportCraft");
        if(spawnedShips == null) return SCRIPT_CONTINUE;

        // remove destroyed ship
        spawnedShips.remove(
            spawnedShips.indexOf(
                    destroyedShip
            )
        );

        // create a new ship
        setObjVar(self, "supportCraft", battle_spawner.spawnSupportShips(self));
        return SCRIPT_CONTINUE;
    }
}
