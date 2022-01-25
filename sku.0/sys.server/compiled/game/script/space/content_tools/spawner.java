package script.space.content_tools;

import script.*;
import script.library.*;

import java.util.Vector;

public class spawner extends script.base_script
{
    public spawner()
    {
    }
    public int getSpawnerData(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "strAsteroidType"))
        {
            if (utils.checkConfigFlag("ScriptFlags", "spawnersOn"))
            {
                messageTo(self, "startSpawning", null, 3, false);
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "strDefaultBehavior"))
        {
            return SCRIPT_CONTINUE;
        }
        String strDefaultBehavior = getStringObjVar(self, "strDefaultBehavior");
        if ((strDefaultBehavior.equals("patrol")) || (strDefaultBehavior.equals("patrolNoRecycle")))
        {
            String[] strPatrolPoints = objvar_mangle.getMangledStringArrayObjVar(self, "strPatrolPoints");
            if (strPatrolPoints == null)
            {
                return SCRIPT_CONTINUE;
            }
            Vector<transform> trPatrolPoints = new Vector<>(0);

            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/tangible/space/content_infrastructure/basic_patrol_point.iff");
            if (objTestObjects == null)
            {
                return SCRIPT_CONTINUE;
            }
            for (String strPatrolPoint : strPatrolPoints) {
                for (int intJ = 0; intJ < objTestObjects.length; intJ++) {
                    if (isIdValid(objTestObjects[intJ]) && hasObjVar(objTestObjects[intJ], "strName")) {
                        String strName = getStringObjVar(objTestObjects[intJ], "strName");
                        if (strName.equals(strPatrolPoint)) {
                            trPatrolPoints.add(getTransform_o2w(objTestObjects[intJ]));
                            intJ = objTestObjects.length + 10;
                        }
                    }
                }
            }
            utils.setScriptVar(self, "trPatrolPoints", trPatrolPoints);
        }
        if (utils.checkConfigFlag("ScriptFlags", "spawnersOn"))
        {
            messageTo(self, "startSpawning", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        requestPreloadCompleteTrigger(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary outparams = new dictionary();
        outparams.put("point", self);
        outparams.put("type", "spawner");
        space_utils.notifyObject(questManager, "registerQuestLocation", outparams);
        space_utils.notifyObject(self, "getSpawnerData", null);
        if (hasObjVar(self, "strDockingStation"))
        {
            String strDockingStation = getStringObjVar(self, "strDockingStation");
            obj_id[] objStations = getAllObjectsWithObjVar(getLocation(self), 320000, "intDockable");
            if (objStations != null)
            {
                for (int intI = 0; intI < objStations.length; intI++)
                {
                    if (hasObjVar(objStations[intI], "strStationName"))
                    {
                        String strStationName = getStringObjVar(objStations[intI], "strStationName");
                        if (strStationName.equals(strDockingStation))
                        {
                            utils.setLocalVar(self, "objDockingStation", objStations[intI]);
                            intI = objStations.length + 1;
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isSpawnerActivated(obj_id self) throws InterruptedException
    {
        obj_id objManager = space_battlefield.getManagerObject();
        if (hasObjVar(objManager, "intSpawnersDeactivated") && hasObjVar(self, "intActivationPhase"))
        {
            int intPhase = getIntObjVar(objManager, "intPhase");
            int intActivationPhase = getIntObjVar(self, "intActivationPhase");
            return intPhase == intActivationPhase || intActivationPhase == -1;
        }
        return true;
    }
    public int startSpawning(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "strSpawnerType"))
        {
            debugServerConsoleMsg(self, "**********************************************************");
            debugServerConsoleMsg(self, "BAD SPAWNER " + getName(self) + " at " + getLocation(self));
            debugServerConsoleMsg(self, "**********************************************************");
            return SCRIPT_CONTINUE;
        }
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String strSpawnerType = getStringObjVar(self, "strSpawnerType");
        switch (strSpawnerType) {
            case "generic": {
                String[] strSpawns = objvar_mangle.getMangledStringArrayObjVar(self, "strSpawns");
                if (strSpawns == null) {
                    return SCRIPT_CONTINUE;
                }
                int intSpawnCount = getIntObjVar(self, "intSpawnCount");
                float fltMinSpawnTime = getFloatObjVar(self, "fltMinSpawnTime");
                float fltMaxSpawnTime = getFloatObjVar(self, "fltMaxSpawnTime");
                float fltDelay;
                String[] strLoopSpawns = new String[1];

                for (int intI = 0; intI < intSpawnCount; intI++) {
                    fltDelay = rand(fltMinSpawnTime, fltMaxSpawnTime);
                    strLoopSpawns[0] = strSpawns[rand(0, strSpawns.length - 1)];
                    dictionary dctParams = new dictionary();
                    dctParams.put("strSpawns", strLoopSpawns);
                    messageTo(self, "createSpawns", dctParams, fltDelay * intI, false);
                }
                break;
            }
            case "asteroid": {
                String strAsteroidType = getStringObjVar(self, "strAsteroidType");
                if (strAsteroidType.equals("")) {
                    return SCRIPT_CONTINUE;
                }

                int intMinResourcePool = getIntObjVar(self, "intMinResourcePool");
                int intMaxResourcePool = getIntObjVar(self, "intMaxResourcePool");
                int intDangerLevel = getIntObjVar(self, "intDangerLevel");
                int intDangerPct = getIntObjVar(self, "intDangerPct");

                dictionary dctParams = new dictionary();
                dctParams.put("strAsteroidType", strAsteroidType);
                dctParams.put("intMinResourcePool", intMinResourcePool);
                dctParams.put("intMaxResourcePool", intMaxResourcePool);
                dctParams.put("intDangerLevel", intDangerLevel);
                dctParams.put("intDangerPct", intDangerPct);
                messageTo(self, "createAsteroidSpawn", dctParams, 1.0f, false);

                break;
            }
            case "wave": {
                utils.setScriptVar(self, "intWave", 0);

                String[] strWaves = objvar_mangle.getMangledStringArrayObjVar(self, "strWaves");
                if(strWaves == null || strWaves.length == 0) {
                    return SCRIPT_CONTINUE;
                }

                String[] strSpawns = dataTableGetStringColumnNoDefaults("datatables/space_content/spawners/waves.iff", strWaves[0]);
                if (strSpawns == null) {
                    return SCRIPT_CONTINUE;
                }
                String strSpawn = strSpawns[0];

                float fltMinSpawnDistance = getFloatObjVar(self, "fltMinSpawnDistance");
                float fltMaxSpawnDistance = getFloatObjVar(self, "fltMaxSpawnDistance");

                float fltMinSpawnTime = getFloatObjVar(self, "fltMinSpawnTime");
                float fltMaxSpawnTime = getFloatObjVar(self, "fltMaxSpawnTime");

                if (!isSquad(strSpawn)) {
                    obj_id objShip = createGenericSpawn(self, strSpawn, fltMinSpawnDistance, fltMaxSpawnDistance, false);
                    if (isIdValid(objShip)) {
                        setupSpawnerSpawn(objShip, self);
                    }
                } else {
                    Vector<obj_id> objMembers = space_create.createSquadHyperspace(self, strSpawn, getTransform_o2p(self), 100, null);
                    if (objMembers != null) {
                        setupSpawnerSpawn(objMembers, self);
                    }
                }
                String[] strLoopSpawns = new String[1];

                for (int intI = 1; intI < strSpawns.length; intI++) {
                    float fltDelay = rand(fltMinSpawnTime, fltMaxSpawnTime);
                    strLoopSpawns[0] = strSpawns[intI];
                    dictionary dctParams = new dictionary();
                    dctParams.put("strSpawns", strLoopSpawns);
                    messageTo(self, "createSpawns", dctParams, fltDelay * intI, false);
                }
                utils.setScriptVar(self, "intWavePopulation", strSpawns.length);
                break;
            }
            default:
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnNextWave(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strWaves = objvar_mangle.getMangledStringArrayObjVar(self, "strWaves");
        if(strWaves == null || strWaves.length == 0) {
            return SCRIPT_CONTINUE;
        }

        int intWave = utils.getIntScriptVar(self, "intWave");

        String[] strSpawns = dataTableGetStringColumnNoDefaults("datatables/space_content/spawners/waves.iff", strWaves[intWave]);
        if (strSpawns == null)
        {
            return SCRIPT_CONTINUE;
        }

        float fltMinSpawnDistance = getFloatObjVar(self, "fltMinSpawnDistance");
        float fltMaxSpawnDistance = getFloatObjVar(self, "fltMaxSpawnDistance");

        String strSpawn = strSpawns[0];
        if (!isSquad(strSpawn))
        {
            obj_id objShip = createGenericSpawn(self, strSpawn, fltMinSpawnDistance, fltMaxSpawnDistance, false);
            if (isIdValid(objShip))
            {
                setupSpawnerSpawn(objShip, self);
            }
        }
        else 
        {
            Vector<obj_id> objMembers = space_create.createSquadHyperspace(self, strSpawn, getTransform_o2p(self), 100, null);
            if (objMembers != null)
            {
                setupSpawnerSpawn(objMembers, self);
            }
        }
        String[] strLoopSpawns = new String[1];

        for (int intI = 1; intI < strSpawns.length; intI++)
        {
            strLoopSpawns[0] = strSpawns[intI];
            dictionary dctParams = new dictionary();
            dctParams.put("strSpawns", strLoopSpawns);
            messageTo(self, "createSpawns", dctParams, intI, false);
        }
        utils.setScriptVar(self, "intWavePopulation", strSpawns.length);
        return SCRIPT_CONTINUE;
    }
    public int createSpawns(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        float fltMinSpawnDistance = getFloatObjVar(self, "fltMinSpawnDistance");
        float fltMaxSpawnDistance = getFloatObjVar(self, "fltMaxSpawnDistance");
        String[] strSpawns = params.getStringArray("strSpawns");
        for (String strSpawn : strSpawns) {
            if (!isSquad(strSpawn)) {
                obj_id objShip = createGenericSpawn(self, strSpawn, fltMinSpawnDistance, fltMaxSpawnDistance, false);
                if (isIdValid(objShip)) {
                    setupSpawnerSpawn(objShip, self);
                }
            } else {
                Vector<obj_id> objMembers = space_create.createSquadHyperspace(self, strSpawn, getTransform_o2p(self), 100, null);
                if (objMembers != null) {
                    setupSpawnerSpawn(objMembers, self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int createAsteroidSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String strAsteroidType = params.getString("strAsteroidType");
        int intMinResourcePool = params.getInt("intMinResourcePool");
        int intMaxResourcePool = params.getInt("intMaxResourcePool");
        int intDangerLevel = params.getInt("intDangerLevel");
        int intDangerPct = params.getInt("intDangerPct");
        createAsteroidSpawn(self, strAsteroidType, intMinResourcePool, intMaxResourcePool, intDangerLevel, intDangerPct);
        return SCRIPT_CONTINUE;
    }
    public void setupSpawnerSpawn(obj_id objShip, obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "intSpawnsAllowed"))
        {
            if ((getIntObjVar(self, "intSpawnsAllowed") - 1) <= 0)
            {
                removeObjVar(self, "intSpawnsAllowed");
                space_utils.notifyObject(self, "spawnCountReached", null);
            }
        }
        String strDefaultBehavior = getStringObjVar(self, "strDefaultBehavior");
        setObjVar(objShip, "objParent", self);
        switch (strDefaultBehavior) {
            case "loiter":
                ship_ai.spaceLoiter(
                        objShip,
                        getTransform_o2w(self),
                        getFloatObjVar(self, "fltMinLoiterDistance"),
                        getFloatObjVar(self, "fltMaxLoiterDistance")
                );
                break;
            case "patrol": {
                ship_ai.spacePatrol(
                        objShip,
                        utils.getTransformArrayScriptVar(self, "trPatrolPoints")
                );
                break;
            }
            case "patrolNoRecycle": {
                transform[] trPatrolPoints = utils.getTransformArrayScriptVar(self, "trPatrolPoints");
                transform trTest = trPatrolPoints[trPatrolPoints.length - 1];
                location locTest = utils.getLocationFromTransform(trTest);
                addLocationTarget3d(objShip, "spawnerArrival", locTest, 64);
                if (utils.hasLocalVar(self, "objDockingStation")) {
                    setObjVar(objShip, "objDockingStation", utils.getObjIdLocalVar(self, "objDockingStation"));
                }
                ship_ai.spaceMoveTo(objShip, trPatrolPoints);
                break;
            }
            case "patrolFixedCircle": {
                float fltCircleDistance = rand(getFloatObjVar(self, "fltMinCircleDistance"), getFloatObjVar(self, "fltMaxCircleDistance"));
                transform trTest = getTransform_o2p(self);
                transform[] trPatrolPoints = ship_ai.createPatrolPathCircle(trTest.getPosition_p(), fltCircleDistance);
                ship_ai.spacePatrol(objShip, trPatrolPoints);
                break;
            }
            case "patrolRandomPath": {
                transform[] trPatrolPoints = ship_ai.createPatrolPathLoiter(getTransform_o2p(self), getFloatObjVar(self, "fltMinCircleDistance"), getFloatObjVar(self, "fltMaxCircleDistance"));
                ship_ai.spacePatrol(objShip, trPatrolPoints);
                break;
            }
            default:
                break;
        }
        if (hasObjVar(self, "objAttackTarget"))
        {
            dictionary dctParams = new dictionary();
            dctParams.put("objShip", objShip);
            space_utils.notifyObject(self, "singleAttackerSpawned", dctParams);
        }
    }
    public void setupSpawnerSpawn(Vector<obj_id> objMembers, obj_id self) throws InterruptedException
    {
        for (obj_id objMember : objMembers) {
            setObjVar(objMember, "objParent", self);
        }
        if (hasObjVar(self, "intSpawnsAllowed"))
        {
            if ((getIntObjVar(self, "intSpawnsAllowed") - objMembers.size()) <= 0)
            {
                removeObjVar(self, "intSpawnsAllowed");
                space_utils.notifyObject(self, "spawnCountReached", null);
            }
        }
        String strDefaultBehavior = getStringObjVar(self, "strDefaultBehavior");
        transform trMyLocation = getTransform_o2w(self);
        int intSquadId = ship_ai.unitGetSquadId(((obj_id)objMembers.get(0)));
        switch (strDefaultBehavior) {
            case "loiter":
                ship_ai.squadLoiter(
                        intSquadId,
                        trMyLocation,
                        getFloatObjVar(self, "fltMinLoiterDistance"),
                        getFloatObjVar(self, "fltMaxLoiterDistance")
                );
                break;
            case "patrol": {
                ship_ai.squadAddPatrolPath(intSquadId, utils.getTransformArrayScriptVar(self, "trPatrolPoints"));
                break;
            }
            case "patrolNoRecycle": {
                transform[] trPatrolPoints = utils.getTransformArrayScriptVar(self, "trPatrolPoints");
                transform trTest = trPatrolPoints[trPatrolPoints.length - 1];
                location locTest = utils.getLocationFromTransform(trTest);
                addLocationTarget3d(objMembers.get(0), "leaderSpawnerArrival", locTest, 16);
                ship_ai.squadMoveTo(intSquadId, trPatrolPoints);
                break;
            }
            case "patrolFixedCircle": {
                float fltCircleDistance = rand(getFloatObjVar(self, "fltMinCircleDistance"), getFloatObjVar(self, "fltMaxCircleDistance"));
                transform[] trPatrolPoints = ship_ai.createPatrolPathCircle(getTransform_o2p(self).getPosition_p(), fltCircleDistance);
                ship_ai.squadAddPatrolPath(intSquadId, trPatrolPoints);
                break;
            }
            case "patrolRandomPath": {
                transform[] trPatrolPoints = ship_ai.createPatrolPathLoiter(getTransform_o2p(self), getFloatObjVar(self, "fltMinCircleDistance"), getFloatObjVar(self, "fltMaxCircleDistance"));
                ship_ai.squadAddPatrolPath(intSquadId, trPatrolPoints);
                break;
            }
            default:
                break;
        }
        if (hasObjVar(self, "objAttackTarget"))
        {
            dictionary dctParams = new dictionary();
            dctParams.put("objMembers", objMembers);
            space_utils.notifyObject(self, "squadAttackerSpawned", dctParams);
        }
    }
    public int childDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String strSpawnerType = getStringObjVar(self, "strSpawnerType");
        switch (strSpawnerType) {
            case "generic":
                float fltRespawnTime = rand(getFloatObjVar(self, "fltMinSpawnTime"), getFloatObjVar(self, "fltMaxSpawnTime"));
                messageTo(self, "respawnShip", null, fltRespawnTime, false);
                break;
            case "asteroid":
                messageTo(self, "respawnAsteroid", null, 3, false);
                break;
            case "wave":
                int intWavePopulation = utils.getIntScriptVar(self, "intWavePopulation") - 1;
                if (intWavePopulation <= 0) {
                    int intWave = utils.getIntScriptVar(self, "intWave");
                    String[] strWaves = objvar_mangle.getMangledStringArrayObjVar(self, "strWaves");
                    intWave++;
                    if (intWave >= strWaves.length) {
                        utils.setScriptVar(self, "intWave", 0);
                        float fltTime = rand(getFloatObjVar(self, "fltMinResetTime"), getFloatObjVar(self, "fltMaxResetTime"));
                        messageTo(self, "spawnNextWave", null, fltTime, false);
                    } else {
                        utils.setScriptVar(self, "intWave", intWave);
                        float fltTime = rand(getFloatObjVar(self, "fltMinWaveSpawnTime"), getFloatObjVar(self, "fltMaxWaveSpawnTime"));
                        messageTo(self, "spawnNextWave", null, fltTime, false);
                    }
                } else {
                    utils.setScriptVar(self, "intWavePopulation", intWavePopulation);
                }
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int respawnAsteroid(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String strAsteroidType = getStringObjVar(self, "strAsteroidType");
        int intMinResourcePool = getIntObjVar(self, "intMinResourcePool");
        int intMaxResourcePool = getIntObjVar(self, "intMaxResourcePool");
        int intDangerLevel = getIntObjVar(self, "intDangerLevel");
        int intDangerPct = getIntObjVar(self, "intDangerPct");
        createAsteroidSpawn(self, strAsteroidType, intMinResourcePool, intMaxResourcePool, intDangerLevel, intDangerPct);
        return SCRIPT_CONTINUE;
    }
    public int respawnShip(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isSpawnerActivated(self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] strSpawns = objvar_mangle.getMangledStringArrayObjVar(self, "strSpawns");
        if (strSpawns == null || strSpawns.length == 0) {
            return SCRIPT_CONTINUE;
        }

        float fltMinSpawnDistance = getFloatObjVar(self, "fltMinSpawnDistance");
        float fltMaxSpawnDistance = getFloatObjVar(self, "fltMaxSpawnDistance");
        String strSpawn = strSpawns[rand(0, strSpawns.length - 1)];
        if (!isSquad(strSpawn))
        {
            obj_id objShip = createGenericSpawn(self, strSpawn, fltMinSpawnDistance, fltMaxSpawnDistance, false);
            if (isIdValid(objShip))
            {
                setupSpawnerSpawn(objShip, self);
            }
        }
        else 
        {
            Vector<obj_id> objMembers = space_create.createSquadHyperspace(self, strSpawn, getTransform_o2p(self), 100, null);
            if (objMembers != null)
            {
                setupSpawnerSpawn(objMembers, self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id createGenericSpawn(obj_id objSpawner, String strShipType, float fltMinDistance, float fltMaxDistance, boolean boolRandomizeOrientation) throws InterruptedException
    {
        obj_id objShip = null;
        if (hasObjVar(objSpawner, "intLaunchFromDockingPoint"))
        {
            objShip = space_create.createShip(strShipType, getTransform_o2p(objSpawner), null);
            transform trTest = ship_ai.unitGetDockTransform(objSpawner, objShip);
            setTransform_o2p(objShip, trTest);
        }
        else 
        {
            transform trSpawnLocation = space_utils.getRandomPositionInSphere(getTransform_o2w(objSpawner), fltMinDistance, fltMaxDistance, boolRandomizeOrientation);
            objShip = space_create.createShipHyperspace(strShipType, trSpawnLocation, null);
        }
        if (isIdValid(objShip))
        {
            setObjVar(objShip, "intNoDump", 1);
            return objShip;
        }
        return null;
    }
    public obj_id createAsteroidSpawn(obj_id objSpawner, String strAsteroidType, int intMinResourcePool, int intMaxResourcePool, int intDangerLevel, int intDangerPct) throws InterruptedException
    {
        String strAsteroidTable = "datatables/space_mining/mining_asteroids.iff";
        transform trSpawnLocation = space_utils.getRandomPositionInSphere(getTransform_o2w(objSpawner), 0, 1, true);
        obj_id objAsteroid;
        switch (strAsteroidType) {
            case "iron":
            case "silicaceous":
            case "carbonaceous":
            case "ice":
            case "obsidian":
            case "diamond":
            case "crystal":
            case "petrochem":
            case "acid":
            case "cyanomethanic":
            case "sulfuric":
            case "methane":
            case "organometallic": {
                int choice = rand(1, 2);
                String template = dataTableGetString(strAsteroidTable, strAsteroidType, choice);
                objAsteroid = createObject(template, trSpawnLocation, null);
                break;
            }
            default:
                objAsteroid = null;
                break;
        }
        setObjVar(objAsteroid, "objParent", objSpawner);
        setObjVar(objAsteroid, "strAsteroidType", strAsteroidType);
        setObjVar(objAsteroid, "intMinResourcePool", intMinResourcePool);
        setObjVar(objAsteroid, "intMaxResourcePool", intMaxResourcePool);
        setObjVar(objAsteroid, "intDangerLevel", intDangerLevel);
        setObjVar(objAsteroid, "intDangerPct", intDangerPct);
        setObjVar(objAsteroid, "intNoDump", 1);
        int resourcePool = rand(intMinResourcePool, intMaxResourcePool);
        setMaxHitpoints(objAsteroid, resourcePool);
        setHitpoints(objAsteroid, resourcePool);
        return objAsteroid;
    }
    public int destroyableCreated(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void addDestroyable(obj_id objParent, obj_id objDestroyable) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objParent", getSelf());
        dctParams.put("objDestroyable", objDestroyable);
        messageTo(getObjIdObjVar(objParent, "objParent"), "destroyableCreated", dctParams, 1, false);
    }
    public void RemoveDestroyable(obj_id objParent, obj_id objDestroyable) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objParent", getSelf());
        dctParams.put("objDestroyable", objDestroyable);
        messageTo(getObjIdObjVar(objParent, "objParent"), "destroyableDestroyed", dctParams, 1, false);
    }
    public void writeSpawner(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "Writing spawner");
        obj_id objSpawner = createObject("object/tangible/space/content_infrastructure/basic_iff", getTransform_o2w(self), null);
        String strSpawnerType = utils.getStringScriptVar(self, "strSpawnerType");
        if (strSpawnerType.equals("generic"))
        {
            String[] strSpawns = utils.getStringArrayScriptVar(self, "strSpawns");
            float fltMinSpawnDistance = utils.getFloatScriptVar(self, "fltMinSpawnDistance");
            float fltMaxSpawnDistance = utils.getFloatScriptVar(self, "fltMaxSpawnDistance");
            float fltMinSpawnTime = utils.getFloatScriptVar(self, "fltMinSpawnTime");
            float fltMaxSpawnTime = utils.getFloatScriptVar(self, "fltMaxSpawnTime");

            setObjVar(objSpawner, "strSpawnerType", strSpawnerType);
            setObjVar(objSpawner, "fltMinSpawnDistance", fltMinSpawnDistance);
            setObjVar(objSpawner, "fltMaxSpawnDistance", fltMaxSpawnDistance);
            setObjVar(objSpawner, "fltMinSpawnTime", fltMinSpawnTime);
            setObjVar(objSpawner, "fltMaxSpawnTime", fltMaxSpawnTime);
            setObjVar(objSpawner, "strSpawns", strSpawns);
        }
        String strDefaultBehavior = utils.getStringScriptVar(self, "strDefaultBehavior");
        setObjVar(objSpawner, "strDefaultBehavior", strDefaultBehavior);
        if (strDefaultBehavior.equals("loiter"))
        {
            setObjVar(objSpawner, "fltMinLoiterDistance", utils.getFloatScriptVar(self, "fltMinLoiterDistance"));
            setObjVar(objSpawner, "fltMaxLoiterDistance", utils.getFloatScriptVar(self, "fltMaxLoiterDistance"));
        }
        else if (strDefaultBehavior.equals("patrol"))
        {
            String strPatrolPointName = utils.getStringScriptVar(self, "strPatrolPointName");
            Vector<String> strPatrolPoints = new Vector<>(0);
            obj_id[] objPatrolPoints = utils.getObjIdArrayScriptVar(self, "objPatrolPoints");
            String strTest;
            int patrolPointsSize = objPatrolPoints.length;
            for (int intI = 0; intI < patrolPointsSize; intI++)
            {
                strTest = strPatrolPointName + "_" + (intI + 1);
                strPatrolPoints.add(strTest);
                setObjVar(objPatrolPoints[intI], "strName", strTest);
            }
            if (strPatrolPoints.size() > 0)
            {
                setObjVar(objSpawner, "strPatrolPoints", strPatrolPoints);
            }
            utils.setScriptVar(objSpawner, "objPatrolPoints", objPatrolPoints);
        }
    }
    public boolean isSquad(String strSpawn) throws InterruptedException
    {
        return strSpawn.contains("squad_");
    }
}
