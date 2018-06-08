package script.systems.gcw.space;

import script.dictionary;
import script.library.space_combat;
import script.library.space_utils;
import script.obj_id;

public class support_ship extends script.space.combat.combat_ship {
    public int OnDestroy(obj_id self) throws InterruptedException {
        dictionary dict = space_combat.getKillPercentages(self);
        obj_id playerShip = dict.getObjId("objWinner");

        obj_id motherShip = getObjIdObjVar(self, "motherShip");

        if (space_utils.isPlayerControlledShip(playerShip)){
            obj_id player = getOwner(playerShip);

            obj_id spawner = getObjIdObjVar(motherShip, "spawner");
            String battleType = getStringObjVar(spawner, "battle_type");
            String battleId = getStringObjVar(spawner, "battle_id");
            obj_id controller = getObjIdObjVar(spawner, "controller");

            if (getIntObjVar(controller, "space_gcw." + spawner + ".active") == 0)
                return SCRIPT_CONTINUE;

            if (space_utils.isPobType(playerShip)) {
                if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                    if (!(pvpGetType(playerShip) == PVPTYPE_DECLARED))
                        return SCRIPT_CONTINUE;
                }
                LOG("space_gcw", "Ship \"" + playerShip + "\" (" + playerShip + ") has qualified for space battle " + battleId + " by destroying a support ship.");
                setObjVar(spawner, "space_gcw.pob.participant." + battleId + "." + playerShip, space_utils.getAllPlayersInShip(playerShip));
            } else if (getShipChassisType(playerShip).startsWith("player_gunship")) {
                if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                    if (!(pvpGetType(playerShip) == PVPTYPE_DECLARED))
                        return SCRIPT_CONTINUE;
                }
                LOG("space_gcw", "Ship \"" + playerShip + "\" (" + playerShip + ") has qualified for space battle " + battleId + " by destroying a support ship.");
                setObjVar(spawner, "space_gcw.gunship.participant." + battleId + "." + playerShip, space_utils.getAllPlayersInShip(playerShip));
            } else {
                if (battleType.equals(battle_spawner.BATTLE_TYPE_PVP)) {
                    if (!(pvpGetType(playerShip) == PVPTYPE_DECLARED))
                        return SCRIPT_CONTINUE;
                }
                LOG("space_gcw", "Ship \"" + playerShip + "\" (" + playerShip + ") has qualified for space battle " + battleId + " by destroying a support ship.");
                setObjVar(spawner, "space_gcw.participant." + battleId + "." + playerShip, space_utils.getAllPlayersInShip(playerShip));
            }
        }

        dictionary params = new dictionary();
        params.put("destroyedShip", self);
        messageTo(motherShip, "removeSupportShip", params, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
}
