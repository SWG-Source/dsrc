package script.space.ai;

import script.dictionary;
import script.library.attrib;
import script.library.ship_ai;
import script.library.space_utils;
import script.library.utils;
import script.obj_id;

public class space_ai extends script.base_script
{
    public space_ai()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setupShip(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setupShip(self);
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id attacker, int weaponIndex, boolean isMissile, int missileType, int intSlot, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        int lastBroadcast = utils.getIntScriptVar(self, "ship.lastHitBroadcast");
        int timeNow = getGameTime();
        if ((lastBroadcast + 10) > timeNow)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "ship.lastHitBroadcast", timeNow);
        dictionary parms = new dictionary();
        parms.put("attacker", attacker);
        broadcastMessage("handleGuardedTargetHit", parms);
        return SCRIPT_CONTINUE;
    }
    public int OnShipIsHitting(obj_id self, obj_id target, int weaponIndex, int intSlot) throws InterruptedException
    {
        if (ship_ai.isShipDead(target))
        {
            // get my target list and only attempt to remove the target if it's on my list.
            obj_id[] targetList = ship_ai.unitGetAttackTargetList(self);
            for(obj_id listMember : targetList){
                if(listMember.equals(target)){
                    ship_ai.spaceStopAttack(self, target);
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipBehaviorChanged(obj_id self, int oldBehavior, int newBehavior) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void setupShip(obj_id ship) throws InterruptedException
    {
        if (utils.hasScriptVar(ship, "ship.setupComplete"))
        {
            return;
        }
        if (ship_ai.isShipAggro(ship))
        {
            setCondition(ship, CONDITION_AGGRESSIVE);
        }
        setAttributeAttained(ship, attrib.SPACE_SHIP);
        utils.setScriptVar(ship, "ship.setupComplete", true);
    }
    public int OnArrivedAtLocation(obj_id self, String strName) throws InterruptedException
    {
        if (strName.equals("spawnerArrival"))
        {
            if (hasObjVar(self, "objDockingStation"))
            {
                obj_id objStation = getObjIdObjVar(self, "objDockingStation");
                ship_ai.unitDock(self, objStation);
            }
            else if (hasObjVar(self, "objAttackTarget"))
            {
                obj_id objAttackTarget = getObjIdObjVar(self, "objAttackTarget");
                ship_ai.unitAddDamageTaken(self, objAttackTarget, 100.0f);
            }
            else 
            {
                setObjVar(self, "intHyperspacing", 1);
                destroyObjectHyperspace(self);
            }
        }
        if (strName.equals("leaderSpawnerArrival"))
        {
            obj_id[] objMembers = ship_ai.squadGetUnitList(ship_ai.unitGetSquadId(self));
            if (hasObjVar(self, "objAttackTarget"))
            {
                obj_id objAttackTarget = getObjIdObjVar(self, "objAttackTarget");
                for (obj_id objMember : objMembers) {
                    ship_ai.unitAddDamageTaken(objMember, objAttackTarget, 100.0f);
                }
            }
            else 
            {
                for (obj_id objMember : objMembers) {
                    if (objMember != self) {
                        setObjVar(objMember, "intCleaningUp", 1);
                        setObjVar(objMember, "intHyperspacing", 1);
                        destroyObjectHyperspace(objMember);
                    }
                }
                setObjVar(self, "intHyperspacing", 1);
                destroyObjectHyperspace(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpaceUnitDocked(obj_id self, obj_id objStation) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objShip", self);
        space_utils.notifyObject(objStation, "objectDocked", dctParams);
        return SCRIPT_CONTINUE;
    }
    public int handleUnitIncreaseHate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id unitToHate = params.getObjId("unitToHate");
        float amountToHate = params.getFloat("amountToHate");
        float hateDelay = params.getFloat("hateDelay");
        int maxRecursions = params.getInt("maxRecursions");
        ship_ai.unitIncreaseHate(self, unitToHate, amountToHate, hateDelay, maxRecursions);
        return SCRIPT_CONTINUE;
    }
}
