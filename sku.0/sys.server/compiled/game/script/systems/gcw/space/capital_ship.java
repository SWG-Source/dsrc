package script.systems.gcw.space;

import script.dictionary;
import script.library.factions;
import script.library.space_utils;
import script.obj_id;

import java.util.Vector;

public class capital_ship extends script.space.combat.combat_space_base {
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException {
        if(!space_utils.isPlayerControlledShip(attacker) || getShipFaction(attacker).equals(getShipFaction(self))) {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(attacker, "You hit the capital ship!");
        obj_id spawner = getObjIdObjVar(self, "spawner");
        String battleType = getStringObjVar(spawner, "battle_type");
        String battleId = getStringObjVar(spawner, "battle_id");
        if(space_utils.isPobType(attacker)){
            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(getShipPilot(attacker)))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.pob.participant." + battleId + "." + attacker, space_utils.getAllPlayersInShip(attacker));
        }
        else if(getShipChassisType(attacker).startsWith("player_gunship")){
            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(getShipPilot(attacker)))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.gunship.participant." + battleId + "." + attacker, space_utils.getAllPlayersInShip(attacker));
        }
        else {
            obj_id player = getShipPilot(attacker);

            if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                if (!factions.isDeclared(player))
                    return SCRIPT_CONTINUE;
            }
            setObjVar(spawner, "space_gcw.participant." + battleId + "." + player, "1");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException{
        // make sure battle is active because it won't be if we're just cleaning up and destroying the capital ships.
        obj_id spawner = getObjIdObjVar(self, "spawner");
        if(isValidId(spawner)) {
            obj_id controller = getObjIdObjVar(spawner, "controller");
            if (controller != null && getIntObjVar(controller, "space_gcw." + spawner + ".active") != 1)
                return SCRIPT_CONTINUE;
        }
        dictionary params = new dictionary();
        params.put("role", getObjVar(self, "role"));
        params.put("spawner", getObjVar(self, "spawner"));
        params.put("destroyedShip", self);
        params.put("losingFaction", getShipFaction(self));
        messageTo(spawner, "capitalShipDestroyed", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }

    public int removeSupportShip(obj_id self, dictionary params) throws InterruptedException{
        obj_id destroyedShip = params.getObjId("destroyedShip");
        Vector spawnedShips = getResizeableObjIdArrayObjVar(self, "supportCraft");
        if(spawnedShips == null) return SCRIPT_CONTINUE;
        if(hasObjVar(destroyedShip, "ace_pilot"))
            removeObjVar(self, "heroSpawned");

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
