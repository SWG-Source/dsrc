package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_create;
import script.library.space_quest;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.space_transition;
import script.library.utils;
import script.library.ship_ai;
import script.library.prose;

public class piracy extends script.base_script
{
    public piracy()
    {
    }
    public int startBeacon(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpaceScene())
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "cleanupPiracyEvent", null, 3600.f, false);
        if (!hasObjVar(self, "warmUpTimer"))
        {
            int warmUpTimer = rand(6, 8);
            setObjVar(self, "warmUpTimer", warmUpTimer);
        }
        int timer = getIntObjVar(self, "warmUpTimer");
        playClientEffectLoc(self, "appearance/pt_space_interdiction_burst.prt", getLocation(self), 0f);
        if (timer > 0)
        {
            timer--;
            messageTo(self, "startBeacon", null, 2.f, false);
            setObjVar(self, "warmUpTimer", timer);
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "startPiracyEvent", null, 2.f, false);
        return SCRIPT_CONTINUE;
    }
    public int startPiracyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("space_piracy", "Player " + player + " is starting a piracy event at location: " + getLocation(self) + " in zone: " + getCurrentSceneName());
        clearMissionCriticalObjects(player);
        generateShipList(self);
        return SCRIPT_CONTINUE;
    }
    public void generateShipList(obj_id self) throws InterruptedException
    {
        int difficulty = getIntObjVar(self, "difficulty");
        String faction = getStringObjVar(self, "faction");
        String targetShipString = "targetship";
        String bigEscortString = "bigescort";
        String mediumEscortString = "mediumescort";
        String smallEscortString = "smallescort";
        if (faction != null)
        {
            targetShipString = faction + targetShipString;
            bigEscortString = faction + bigEscortString;
            mediumEscortString = faction + mediumEscortString;
            smallEscortString = faction + smallEscortString;
        }
        String table = "/datatables/spacequest/piracy/piracy_spawn_table.iff";
        String[] targetShipArray = dataTableGetStringColumnNoDefaults(table, targetShipString);
        if (targetShipArray == null || targetShipArray.length <= 0)
        {
            return;
        }
        int i = rand(0, difficulty - 1);
        String targetShipType = targetShipArray[i];
        if (difficulty == 5)
        {
            difficulty++;
        }
        int numBigShips = 1 + (difficulty / 2);
        int numMediumShips = rand(0, (difficulty / 2));
        int numSmallShips = rand(1, 2) + (difficulty);
        int escortSize = numBigShips + numMediumShips + numSmallShips;
        String[] escortsArray = new String[escortSize];
        String[] bigShipArray = dataTableGetStringColumnNoDefaults(table, bigEscortString);
        String[] mediumShipArray = dataTableGetStringColumnNoDefaults(table, mediumEscortString);
        String[] smallShipArray = dataTableGetStringColumnNoDefaults(table, smallEscortString);
        int a = 0;
        int b = 0;
        int c = 0;
        for (a = 0; a < numBigShips; a++)
        {
            int r = rand(0, bigShipArray.length - 1);
            escortsArray[a] = bigShipArray[r];
        }
        for (b = 0; b < numMediumShips; b++)
        {
            int r = rand(0, mediumShipArray.length - 1);
            int s = a + b;
            escortsArray[s] = mediumShipArray[r];
        }
        for (c = 0; c < numSmallShips; c++)
        {
            int r = rand(0, smallShipArray.length - 1);
            int x = a + b + c;
            escortsArray[x] = smallShipArray[r];
        }
        setObjVar(self, "targetShipType", targetShipType);
        setObjVar(self, "escortArray", escortsArray);
        warpInTargetShip(self, targetShipType);
        warpInEscortShips(self, escortsArray);
    }
    public void warpInTargetShip(obj_id self, String targetShipType) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        obj_id playerShip = space_transition.getContainingShip(player);
        if (!isIdValid(playerShip) || !exists(playerShip))
        {
            return;
        }
        int targetSquad = ship_ai.squadCreateSquadId();
        setObjVar(self, "targetSquad", targetSquad);
        transform centerLoc = getTransform_o2p(self);
        transform spawnTargetLoc = space_quest.getRandomPositionInSphere(centerLoc, 200, 200);
        obj_id targetShip = space_create.createShipHyperspace(targetShipType, spawnTargetLoc);
        setObjVar(targetShip, "beacon", self);
        setObjVar(self, "targetShip", targetShip);
        setObjVar(targetShip, "player", player);
        int difficulty = getIntObjVar(self, "difficulty");
        if (difficulty <= 0 || difficulty > 5)
        {
            difficulty = 1;
        }
        setObjVar(targetShip, "difficulty", difficulty);
        if (!exists(targetShip) || !isIdValid(targetShip))
        {
            return;
        }
        CustomerServiceLog("space_piracy", " Warping in the TargetShip for player " + player + "'s Piracy Event. TargetShip OID: " + targetShip + " TargetShip type: " + targetShipType);
        ship_ai.unitSetLeashDistance(targetShip, 16000);
        ship_ai.unitSetSquadId(targetShip, targetSquad);
        setLookAtTarget(player, targetShip);
        attachScript(targetShip, "space.quest_logic.piracy_ship");
        ship_ai.unitSetAttackOrders(targetShip, ship_ai.ATTACK_ORDERS_RETURN_FIRE);
        ship_ai.unitAddExclusiveAggro(targetShip, player);
        addMissionCriticalObject(player, targetShip);
        setObjVar(targetShip, "objMissionOwner", player);
        setObjVar(targetShip, "piracyEventOwner", player);
        float maxspeed = getShipEngineSpeedMaximum(targetShip);
        if (maxspeed < 15.f)
        {
            setShipEngineSpeedMaximum(targetShip, 15.f + rand() * 10.f);
        }
        else if (maxspeed > 25.f)
        {
            setShipEngineSpeedMaximum(targetShip, 23.f + rand() * 5.f);
        }
        messageTo(self, "updateTargetWaypoint", null, 1.f, false);
        messageTo(self, "startMovement", null, 3.f, false);
        messageTo(self, "eventTimer", null, 1.f, false);
        return;
    }
    public void warpInEscortShips(obj_id self, String[] escortShips) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        obj_id playerShip = space_transition.getContainingShip(player);
        if (!isIdValid(playerShip) || !exists(playerShip))
        {
            return;
        }
        if (!hasObjVar(self, "targetSquad"))
        {
            return;
        }
        obj_id targetShip = getObjIdObjVar(self, "targetShip");
        if (!isIdValid(targetShip) || !exists(targetShip))
        {
            return;
        }
        int targetSquad = getIntObjVar(self, "targetSquad");
        int escortSquad = ship_ai.squadCreateSquadId();
        if (escortShips != null)
        {
            obj_id[] escortIdArray = new obj_id[escortShips.length];
            for (int i = 0; i < escortShips.length; i++)
            {
                if (escortShips[i] == null || escortShips[i].length() <= 0)
                {
                    continue;
                }
                transform t = getTransform_o2w(targetShip);
                transform spawnLoc = t.move_l(new vector(rand(-200, 200), rand(-200, 200), rand(-200, 200)));
                escortIdArray[i] = space_create.createShipHyperspace(escortShips[i], spawnLoc);
                ship_ai.unitSetLeashDistance(escortIdArray[i], 16000);
                ship_ai.unitSetSquadId(escortIdArray[i], escortSquad);
                ship_ai.unitAddExclusiveAggro(escortIdArray[i], player);
                addMissionCriticalObject(player, escortIdArray[i]);
                setObjVar(escortIdArray[i], "objMissionOwner", player);
                setObjVar(escortIdArray[i], "piracyEventOwner", player);
                ship_ai.unitSetAutoAggroImmuneTime(playerShip, 5.0f);
                setObjVar(escortIdArray[i], "beacon", self);
                attachScript(escortIdArray[i], "space.quest_logic.piracy_ship");
                CustomerServiceLog("space_piracy", " Warping in an Escort ship for player " + player + "'s Piracy Event. Ship OID: " + escortIdArray[i] + " TargetShip type: " + escortShips[i]);
            }
            setObjVar(self, "escortIdArray", escortIdArray);
            setObjVar(targetShip, "escortIdArray", escortIdArray);
        }
        obj_id[] squadList = ship_ai.squadGetUnitList(escortSquad);
        if (squadList != null)
        {
            ship_ai.squadSetLeader(escortSquad, squadList[0]);
        }
        ship_ai.squadFollow(escortSquad, targetShip, new vector(0, 0, -25.0f), 100.f);
        ship_ai.squadSetGuardTarget(escortSquad, targetSquad);
        ship_ai.squadSetAttackOrders(escortSquad, ship_ai.ATTACK_ORDERS_RETURN_FIRE);
        ship_ai.squadSetFormation(escortSquad, 5);
        return;
    }
    public int startMovement(obj_id self, dictionary params) throws InterruptedException
    {
        location o = new location(0.f, 0.f, 0.f);
        transform randomTransform = space_quest.getRandomPositionInSphere(transform.identity, 4000, 6000);
        obj_id targetShip = getObjIdObjVar(self, "targetShip");
        if (!exists(targetShip) || !isIdValid(targetShip))
        {
            messageTo(self, "cleanupPiracyEvent", null, 3.f, false);
            return SCRIPT_CONTINUE;
        }
        vector v = randomTransform.getPosition_p();
        location loc = new location(v.x, v.y, v.z);
        float dist = getDistance(loc, o);
        if (dist >= 7500)
        {
            messageTo(self, "startMovement", null, 0.f, false);
            return SCRIPT_CONTINUE;
        }
        ship_ai.unitMoveTo(targetShip, randomTransform);
        return SCRIPT_CONTINUE;
    }
    public int updateTargetWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        obj_id targetShip = getObjIdObjVar(self, "targetShip");
        obj_id waypoint = getObjIdObjVar(self, "spacepiracy.waypoint");
        location loc = getLocation(targetShip);
        if (!exists(player) || !isIdValid(player))
        {
            messageTo(self, "cleanupPiracyEvent", null, 0.f, false);
            return SCRIPT_CONTINUE;
        }
        if (!exists(targetShip) || !isIdValid(targetShip))
        {
            if (waypoint != null)
            {
                destroyWaypointInDatapad(waypoint, player);
            }
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(waypoint))
        {
            waypoint = createWaypointInDatapad(player, loc);
        }
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setWaypointLocation(waypoint, loc);
            setWaypointName(waypoint, "Convoy");
            setWaypointColor(waypoint, "space");
            setObjVar(self, "spacepiracy.waypoint", waypoint);
        }
        sendSystemMessage(player, "updating waypoint", null);
        messageTo(self, "updateTargetWaypoint", null, 30.f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupPiracyEvent(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetShip = getObjIdObjVar(self, "targetShip");
        obj_id waypoint = getObjIdObjVar(self, "spacepiracy.waypoint");
        obj_id player = getObjIdObjVar(self, "player");
        clearMissionCriticalObjects(player);
        if (exists(targetShip) && isIdValid(targetShip))
        {
            messageTo(targetShip, "hyperLeave", null, 0.f, false);
        }
        obj_id[] escortIdArray = utils.getObjIdArrayObjVar(self, "escortIdArray");
        if (escortIdArray != null)
        {
            for (int i = 0; i < escortIdArray.length; i++)
            {
                if (isIdValid(escortIdArray[i]) && exists(escortIdArray[i]))
                {
                    messageTo(escortIdArray[i], "hyperLeave", null, 0.f, false);
                }
            }
        }
        if (exists(player) && isIdValid(player))
        {
            utils.removeScriptVar(player, "interdiction_beacon");
        }
        if (isIdValid(waypoint) && exists(player) && isIdValid(player))
        {
            destroyWaypointInDatapad(waypoint, player);
        }
        CustomerServiceLog("space_piracy", "Cleaning up piracy event belongning to player " + player + " Controlling object: " + self);
        if (isIdValid(self) && exists(self) && isIdValid(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int eventTimer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id targetShip = getObjIdObjVar(self, "targetShip");
        if (!exists(targetShip) || !isIdValid(targetShip))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, "player");
        if (!exists(player) || !isIdValid(player))
        {
            messageTo(self, "cleanupPiracyEvent", null, 0.f, false);
            return SCRIPT_CONTINUE;
        }
        String dialog = "space/space_piracy_event";
        if (!utils.hasScriptVar(self, "hyperTimer"))
        {
            utils.setScriptVar(self, "hyperTimer", 0);
            string_id arrival = new string_id(dialog, "arrival");
            prose_package pp = prose.getPackage(arrival, 0);
            space_quest.groupTaunt(targetShip, player, pp);
        }
        if (hasObjVar(targetShip, "isDisabled"))
        {
            if (!utils.hasScriptVar(self, "disabledCounter"))
            {
                utils.setScriptVar(self, "disabledCounter", 0);
            }
            int disabledCounter = utils.getIntScriptVar(self, "disabledCounter");
            if (disabledCounter == 0)
            {
                string_id disabled1 = new string_id(dialog, "disabled1");
                prose_package pp = prose.getPackage(disabled1, 0);
                space_quest.groupTaunt(targetShip, player, pp);
            }
            if (disabledCounter == 2)
            {
                string_id disabled2 = new string_id(dialog, "disabled2");
                prose_package pp = prose.getPackage(disabled2, 0);
                space_quest.groupTaunt(targetShip, player, pp);
            }
            if (disabledCounter == 3)
            {
                dictionary dctParams = new dictionary();
                dctParams.put("objShip", targetShip);
                messageTo(targetShip, "megaDamage", dctParams, 0, false);
            }
            disabledCounter++;
            utils.setScriptVar(self, "disabledCounter", disabledCounter);
            messageTo(self, "eventTimer", null, 30.f, false);
            return SCRIPT_CONTINUE;
        }
        int hyperTimer = utils.getIntScriptVar(self, "hyperTimer");
        if (hyperTimer == 10)
        {
            string_id hyper1 = new string_id(dialog, "hyper1");
            prose_package pp = prose.getPackage(hyper1, 0);
            space_quest.groupTaunt(targetShip, player, pp);
        }
        if (hyperTimer == 50)
        {
            string_id hyper2 = new string_id(dialog, "hyper2");
            prose_package pp = prose.getPackage(hyper2, 0);
            space_quest.groupTaunt(targetShip, player, pp);
        }
        if (hyperTimer == 80)
        {
            string_id hyper3 = new string_id(dialog, "hyper3");
            prose_package pp = prose.getPackage(hyper3, 0);
            space_quest.groupTaunt(targetShip, player, pp);
        }
        if (hyperTimer >= 100 && !hasObjVar(targetShip, "isDisabled"))
        {
            string_id hyperfinished = new string_id(dialog, "hyperfinished");
            prose_package pp = prose.getPackage(hyperfinished, 0);
            space_quest.groupTaunt(targetShip, player, pp);
            CustomerServiceLog("space_piracy", "Time expired for piracy event started by " + player + " Starting cleanup.");
            sendSystemMessage(player, "You failed to disable your target in time. Your target is making the jump to hyperspace", null);
            messageTo(self, "cleanupPiracyEvent", null, 1.f, false);
        }
        hyperTimer = hyperTimer + 10;
        utils.setScriptVar(self, "hyperTimer", hyperTimer);
        messageTo(self, "eventTimer", null, 60.f, false);
        return SCRIPT_CONTINUE;
    }
}
