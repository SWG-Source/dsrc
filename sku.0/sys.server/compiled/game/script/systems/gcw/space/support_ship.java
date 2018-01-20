package script.systems.gcw.space;

import script.library.factions;
import script.library.space_utils;
import script.dictionary;
import script.obj_id;

public class support_ship extends script.space.combat.combat_ship {
    @Override
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException {
        if(getShipCurrentChassisHitPoints(self) < 0){
            sendSystemMessageTestingOnly(attacker, "You shot down a participating ship!");
            if(!space_utils.isPlayerControlledShip(attacker))
                return SCRIPT_CONTINUE;
            if(getShipFaction(attacker).equals(getShipFaction(self)))
                return SCRIPT_CONTINUE;
            obj_id motherShip = getObjIdObjVar(self, "motherShip");
            obj_id spawner = getObjIdObjVar(motherShip, "spawner");
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
        }
        super.OnShipWasHit(self, attacker, weaponIndex, isMissile, missileType, intSlot, fromPlayerAutoTurret, hitLocationX_o, hitLocationY_o, hitLocationZ_o);
        return SCRIPT_CONTINUE;
    }
    @Override
    public int OnDestroy(obj_id self) throws InterruptedException {
        obj_id motherShip = getObjIdObjVar(self, "motherShip");
        dictionary params = new dictionary();
        params.put("destroyedShip", self);
        messageTo(motherShip, "removeSupportShip", params, 0f, false);
        super.OnDestroy(self);
        return SCRIPT_CONTINUE;
    }
}
