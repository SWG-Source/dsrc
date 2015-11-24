package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.ship_ai;
import script.library.sui;
import java.lang.Math;
import script.library.prose;

public class space_battlefield extends script.base_script
{
    public space_battlefield()
    {
    }
    public static final float RESET_TIME = 90;
    public static final int STATE_IMPERIAL = 1;
    public static final int STATE_REBEL = 0;
    public static final int PHASE_ESCORT = 0;
    public static final int PHASE_CONTROL = 1;
    public static final int PHASE_DESTROY = 2;
    public static final int[] PHASE_DURATIONS = 
    {
        9600,
        9600,
        -1
    };
    public static final int POINTS_PER_TRANSPORT = 100;
    public static final int POINTS_PER_KILL = 1;
    public static final int POINTS_FOR_STARDESTROYER = 100000;
    public static void battlefieldCompleted(obj_id objManager, int intCompletionId) throws InterruptedException
    {
        CustomerServiceLog("battlefield", "Battlefield Completed and resetting with id " + intCompletionId);
        obj_id[] objShips = getObjectsInRange(getLocation(objManager), 320000);
        for (int intI = 0; intI < objShips.length; intI++)
        {
            if (!space_utils.isPlayerControlledShip(objShips[intI]))
            {
                if (hasObjVar(objShips[intI], "ship.shipName"))
                {
                    if (!hasObjVar(objShips[intI], "intNoCleanup"))
                    {
                        float fltTime = rand(1, 120);
                        messageTo(objShips[intI], "destroySelf", null, fltTime, false);
                    }
                }
            }
            else 
            {
            }
        }
        return;
    }
    public static void resetSpaceBattlefield(obj_id objManager) throws InterruptedException
    {
        LOG("space", "RESETTING");
        utils.removeScriptVar(objManager, "intResetting");
        obj_id objSpaceStations[] = getAllObjectsWithScript(getLocation(objManager), 320000, "space.battlefields.battlefield_station");
        if (objSpaceStations != null)
        {
            for (int intI = 0; intI < objSpaceStations.length; intI++)
            {
                setObjVar(objSpaceStations[intI], "intCleaningUp", 1);
                detachScript(objSpaceStations[intI], "space.battlefields.battlefield_spawner");
                detachScript(objSpaceStations[intI], "space.content_tools.spawner");
                destroyObject(objSpaceStations[intI]);
            }
        }
        obj_id objDestroyers[] = getAllObjectsWithScript(getLocation(objManager), 320000, "space.battlefields.battlefield_star_destroyer");
        if (objDestroyers != null)
        {
            for (int intI = 0; intI < objDestroyers.length; intI++)
            {
                setObjVar(objDestroyers[intI], "intCleaningUp", 1);
                destroyObject(objDestroyers[intI]);
            }
        }
        obj_id objStationEggs[] = getAllObjectsWithObjVar(getLocation(objManager), 320000, "intStationRespawner");
        if (objStationEggs != null)
        {
            for (int intI = 0; intI < objStationEggs.length; intI++)
            {
                String strObjVars = utils.getStringLocalVar(objStationEggs[intI], "strObjVars");
                String strScripts = utils.getStringLocalVar(objStationEggs[intI], "strScripts");
                String strTemplate = utils.getStringLocalVar(objStationEggs[intI], "strTemplate");
                LOG("space", "making " + strTemplate + " from object " + objStationEggs[intI]);
                transform trTest = getTransform_o2p(objStationEggs[intI]);
                obj_id objStation = createObject(strTemplate, trTest, null);
                LOG("space", "MADE STATION " + objStation);
                setPackedObjvars(objStation, strObjVars);
                space_create.setupShipFromObjVars(objStation);
                if (hasObjVar(objStation, "intCleaningUp"))
                {
                    removeObjVar(objStation, "intCleaningUp");
                }
                if (!strScripts.equals(""))
                {
                    String[] strScriptArray = split(strScripts, ',');
                    for (int intJ = 0; intJ < strScriptArray.length; intJ++)
                    {
                        String script = strScriptArray[intJ];
                        if (script.indexOf("script.") > -1)
                        {
                            script = script.substring(7);
                        }
                        if (!script.equals(""))
                        {
                            if (!hasScript(objStation, script))
                            {
                                attachScript(objStation, script);
                            }
                        }
                        setObjVar(objStation, "intNoCleanup", 1);
                    }
                }
                destroyObject(objStationEggs[intI]);
            }
        }
        else 
        {
            LOG("space", "NO EGGS");
        }
        setObjVar(objManager, "intPhase", -1);
        dictionary dctParams = new dictionary();
        LOG("space", "RESETTING on " + objManager);
        messageTo(objManager, "nextPhase", dctParams, 1, false);
        resetCounters();
        return;
    }
    public static void doBattleFieldTransition(obj_id objShip, obj_id objStation) throws InterruptedException
    {
        LOG("space", "Transition 1");
        String strName = getStringObjVar(objStation, "strName");
        doBattleFieldTransition(objShip, objStation, strName);
    }
    public static void doBattleFieldTransition(obj_id objShip, obj_id objStation, String strOverloadName) throws InterruptedException
    {
        LOG("space", "Transition 2");
        if (canTransitionToBattlefield(objShip, objStation))
        {
            LOG("space", "Transition 3");
            String strRealName = getStringObjVar(objStation, "strName");
            dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strRealName);
            int intPointCost = dctStationInfo.getInt("intPointCost");
            String strType = dctStationInfo.getString("strPrestigeType");
            int intTotalCost = getPrestigeCostForTransition(objShip, objStation, intPointCost);
            if (!strOverloadName.equals(strRealName))
            {
                dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strOverloadName);
            }
            String strArea = dctStationInfo.getString("strArea");
            LOG("testing", "area is " + strArea);
            float fltX = dctStationInfo.getFloat("fltX");
            float fltY = dctStationInfo.getFloat("fltY");
            float fltZ = dctStationInfo.getFloat("fltZ");
            transform trTest = space_combat.getClosestStationRespawnLocation(objShip);
            location locTest = new location();
            if (trTest != null)
            {
                locTest = space_utils.getLocationFromTransform(trTest);
                setObjVar(objShip, "battlefield.locRespawnLocation", locTest);
            }
            else 
            {
                setObjVar(objShip, "battlefield.locRespawnLocation", getLocation(objShip));
            }
            setObjVar(objShip, "battlefield.locEntryLocation", getLocation(objShip));
            locTest = new location();
            locTest.x = fltX;
            locTest.y = fltY;
            locTest.z = fltZ;
            locTest.x = locTest.x + rand(-200, 200);
            locTest.y = locTest.y + rand(-200, 200);
            locTest.z = locTest.z + rand(-200, 200);
            locTest.area = strArea;
            LOG("testing", "setting location to " + locTest);
            CustomerServiceLog("battlefield", "%TU " + objShip + " is Entering the Deep Space zone from station at " + getLocation(objStation), getOwner(objShip));
            warpPlayer(getPilotId(objShip), strArea, locTest.x, locTest.y, locTest.z, null, locTest.x, locTest.y, locTest.z);
        }
        else 
        {
            LOG("space", "Can't transition");
        }
        return;
    }
    public static void doKesselTransition(obj_id objShip, obj_id objStation) throws InterruptedException
    {
        if (space_utils.isBasicShip(objShip))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_basics");
            space_utils.sendSystemMessageShip(objShip, strSpam, true, false, false, false);
            return;
        }
        LOG("space", "GOING TO KESSEL");
        String strName = getStringObjVar(objStation, "strName");
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strName);
        float fltKesselX = dctStationInfo.getFloat("fltKesselX");
        float fltKesselY = dctStationInfo.getFloat("fltKesselY");
        float fltKesselZ = dctStationInfo.getFloat("fltKesselZ");
        transform trTest = space_combat.getClosestStationRespawnLocation(objShip);
        location locTest = space_utils.getLocationFromTransform(trTest);
        setObjVar(objShip, "battlefield.locEntryLocation", getLocation(objShip));
        setObjVar(objShip, "battlefield.locRespawnLocation", locTest);
        CustomerServiceLog("battlefield", objShip + " is Entering the Kessel space zone from station at " + getLocation(objStation));
        locTest.x = fltKesselX;
        locTest.y = fltKesselY;
        locTest.z = fltKesselZ;
        locTest.x = locTest.x + rand(-200, 200);
        locTest.y = locTest.y + rand(-200, 200);
        locTest.z = locTest.z + rand(-200, 200);
        locTest.area = "space_light1";
        warpPlayer(getPilotId(objShip), locTest.area, locTest.x, locTest.y, locTest.z, null, locTest.x, locTest.y, locTest.z);
        return;
    }
    public static void doBattleFieldCommandTransition(obj_id objShip, obj_id player, String zone, String factionEntry) throws InterruptedException
    {
        String strX = "fltX";
        String strY = "fltY";
        String strZ = "fltZ";
        if (zone.equals("light1"))
        {
            strX = "fltKesselX";
            strY = "fltKesselY";
            strZ = "fltKesselZ";
        }
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", factionEntry);
        float fltX = dctStationInfo.getFloat(strX);
        float fltY = dctStationInfo.getFloat(strY);
        float fltZ = dctStationInfo.getFloat(strZ);
        transform trTest = space_combat.getClosestStationRespawnLocation(objShip);
        location locTest = new location();
        if (trTest != null)
        {
            locTest = space_utils.getLocationFromTransform(trTest);
            setObjVar(objShip, "battlefield.locRespawnLocation", locTest);
        }
        else 
        {
            setObjVar(objShip, "battlefield.locRespawnLocation", getLocation(objShip));
        }
        setObjVar(objShip, "battlefield.locEntryLocation", getLocation(objShip));
        locTest = new location();
        locTest.x = fltX;
        locTest.y = fltY;
        locTest.z = fltZ;
        locTest.x = locTest.x + rand(-200, 200);
        locTest.y = locTest.y + rand(-200, 200);
        locTest.z = locTest.z + rand(-200, 200);
        locTest.area = zone;
        warpPlayer(getPilotId(objShip), zone, locTest.x, locTest.y, locTest.z, null, locTest.x, locTest.y, locTest.z);
        return;
    }
    public static boolean isInBattlefield(obj_id objShip) throws InterruptedException
    {
        return isSpaceBattlefieldZone();
    }
    public static void cleanupBattlefieldMember(obj_id objShip) throws InterruptedException
    {
        setObjVar(objShip, "locEntryLocation", getLocation(objShip));
        location locEntryLocation = getLocationObjVar(objShip, "locEntryLocation");
        removeObjVar(objShip, "locEntryLocation");
        setLocation(objShip, locEntryLocation);
        return;
    }
    public static boolean canTransitionToBattlefield(obj_id objShip, obj_id objStation) throws InterruptedException
    {
        if (space_utils.isBasicShip(objShip))
        {
            string_id strSpam = new string_id("space/space_interaction", "no_basics");
            space_utils.sendSystemMessageShip(objShip, strSpam, true, false, false, false);
            return false;
        }
        float fltDistance = getDistance(objShip, objStation);
        if (fltDistance > 300)
        {
            string_id strSpam = new string_id("space/space_interaction", "too_far");
            space_utils.sendSystemMessageShip(objShip, strSpam, true, false, false, false);
            return false;
        }
        if (isCorrectFactionForStation(objShip, objStation))
        {
            return true;
        }
        else 
        {
            LOG("testing", "badFaction");
            Vector objPlayers = space_transition.getContainedPlayers(objShip, null);
            if (objPlayers.size() > 1)
            {
                string_id strSpam = new string_id("space/space_battlefields", "not_correct_faction_group");
                space_utils.sendSystemMessageShip(objShip, strSpam, true, false, false, false);
            }
            else 
            {
                string_id strSpam = new string_id("space/space_battlefields", "not_correct_faction_single");
                space_utils.sendSystemMessageShip(objShip, strSpam, true, false, false, false);
            }
        }
        return false;
    }
    public static boolean isCorrectFactionForStation(obj_id objShip, obj_id objStation) throws InterruptedException
    {
        return true;
    }
    public static int canAffordPrestigePointCost(obj_id objShip, obj_id objStation, String strType) throws InterruptedException
    {
        String strName = getStringObjVar(objStation, "strName");
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strName);
        int intPointCost = dctStationInfo.getInt("intPointCost");
        obj_id objPilot = getPilotId(objShip);
        int intCurrentXp = getExperiencePoints(objPilot, strType);
        int intTotalCost = getPrestigeCostForTransition(objShip, objStation, intPointCost);
        if (intCurrentXp >= Math.abs(intTotalCost))
        {
            return intTotalCost * -1;
        }
        else 
        {
            return 0;
        }
    }
    public static int canAffordPrestigePointCost(obj_id objShip, obj_id objStation) throws InterruptedException
    {
        String strName = getStringObjVar(objStation, "strName");
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strName);
        String strType = dctStationInfo.getString("strPrestigeType");
        return canAffordPrestigePointCost(objShip, objStation, strType);
    }
    public static int getPrestigeCostForTransition(obj_id objPlayer, obj_id objStation) throws InterruptedException
    {
        obj_id objShip = getPilotedShip(objPlayer);
        String strName = getStringObjVar(objStation, "strName");
        dictionary dctStationInfo = dataTableGetRow("datatables/space_content/battlefields/entry_stations.iff", strName);
        int intPointCost = dctStationInfo.getInt("intPointCost");
        return intPointCost;
    }
    public static int getPrestigeCostForTransition(obj_id objShip, obj_id objStation, int intPointCost) throws InterruptedException
    {
        return intPointCost;
    }
    public static boolean isInRebelShip(obj_id objPlayer) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(objPlayer);
        if (!isIdValid(objShip))
        {
            return false;
        }
        int intTest = shipGetSpaceFaction(objShip);
        if (intTest == (370444368))
        {
            return true;
        }
        return false;
    }
    public static boolean isInNeutralShip(obj_id objPlayer) throws InterruptedException
    {
        if (!isInImperialShip(objPlayer) && (!isInRebelShip(objPlayer)))
        {
            return true;
        }
        return false;
    }
    public static boolean isInImperialShip(obj_id objPlayer) throws InterruptedException
    {
        obj_id objShip = space_transition.getContainingShip(objPlayer);
        if (!isIdValid(objShip))
        {
            return false;
        }
        int intTest = shipGetSpaceFaction(objShip);
        if (intTest == (-615855020))
        {
            LOG("testing", intTest + " vs " + (-615855020));
            return true;
        }
        LOG("testing", "Returning ");
        return false;
    }
    public static obj_id getManagerObject() throws InterruptedException
    {
        return getNamedObject("content_manager");
    }
    public static void sendNextPhaseNotification(obj_id self) throws InterruptedException
    {
        int intPhase = getIntObjVar(self, "intPhase");
        int intPhaseStart = getIntObjVar(self, "intPhaseStart");
        int intDuration = PHASE_DURATIONS[intPhase];
        LOG("space", "phase is " + intPhase);
        LOG("space", "start is " + intPhaseStart);
        if (intPhaseStart == 0)
        {
            intPhaseStart = getGameTime();
        }
        LOG("space", "Duration is " + intDuration);
        if (intDuration > 0)
        {
            int intCurrentTime = getGameTime();
            int intDifference = intCurrentTime - intPhaseStart;
            LOG("space", "difference is" + intDifference);
            if (intDifference > intDuration)
            {
                dictionary dctParams = new dictionary();
                space_utils.notifyObject(self, "nextPhase", dctParams);
                LOG("space", "Notifying of nerxtphase");
                return;
            }
            else 
            {
                dictionary dctParams = new dictionary();
                LOG("space", "notifying of next phase with delay " + (intDuration - intDifference));
                messageTo(self, "nextPhase", dctParams, intDuration - intDifference, false);
            }
        }
        return;
    }
    public static void doDestroyerPhase(obj_id objManager) throws InterruptedException
    {
        obj_id[] objStations = getAllObjectsWithScript(getLocation(objManager), 320000, "space.battlefields.battlefield_station");
        if (objStations != null)
        {
            removeObjVar(objStations[0], "intInvincible");
            int intCount = 1000;
            setShipMaximumChassisHitPoints(objStations[0], intCount * 1000);
            setShipCurrentChassisHitPoints(objStations[0], intCount * 1000);
            attachScript(objStations[0], "space.battlefields.battlefield_spawner");
            attachScript(objStations[0], "space.content_tools.spawner");
            messageTo(objStations[0], "startSpawning", null, 4, false);
        }
        obj_id[] objDestroyerEgg = getAllObjectsWithObjVar(getLocation(objManager), 320000, "strStarDestroyerAttackStation");
        if (objDestroyerEgg == null)
        {
            LOG("space", "NO STAR DESTROYER EGG");
            return;
        }
        transform trDestroyerLocation = getTransform_o2p(objDestroyerEgg[0]);
        LOG("space", "Egg is at " + getLocation(objDestroyerEgg[0]));
        LOG("space", "Creating stardestroyer at " + trDestroyerLocation);
        obj_id objStarDestroyer = space_create.createShip("star_destroyer", trDestroyerLocation);
        LOG("space", "+objStarDestroyer is at " + getLocation(objStarDestroyer));
        attachScript(objStarDestroyer, "space.battlefields.battlefield_star_destroyer");
        space_combat.setupCapitalShipFromTurretDefinition(objStarDestroyer, "star_destroyer");
        setObjVar(objStarDestroyer, "intNoCleanup", 1);
        CustomerServiceLog("battlefield", objStarDestroyer + " :Star Destroyer Spawned!");
        return;
    }
    public static void resetCounters() throws InterruptedException
    {
        obj_id objContentManager = space_battlefield.getManagerObject();
        removeObjVar(objContentManager, "intImperialEscortsCompleted");
        removeObjVar(objContentManager, "intRebelEscortsCompleted");
        removeObjVar(objContentManager, "intImperialShipCount");
        return;
    }
}
