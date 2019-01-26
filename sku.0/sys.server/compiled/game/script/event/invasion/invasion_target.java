package script.event.invasion;

import script.dictionary;
import script.library.badge;
import script.library.create;
import script.library.locations;
import script.library.utils;
import script.location;
import script.obj_id;

public class invasion_target extends script.base_script
{
    public invasion_target()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float timeStamp = getGameTime();
        setObjVar(self, "event.invasion.timeStamp", timeStamp);
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
        sendSystemMessage(coordinator, "Starting...", null);
        dictionary params = new dictionary();
        messageTo(self, "launchEvent", null, 1, false);
        messageTo(self, "cleanUp", null, 36000, false);
        return SCRIPT_CONTINUE;
    }
    public int launchEvent(obj_id self, dictionary params) throws InterruptedException
    {
        int numWaves = getIntObjVar(self, "event.invasion.waves.numWaves");
        int timeBetweenSpawns = getIntObjVar(self, "event.invasion.waves.timeBetweenSpawns");
        int timeBetweenWaves = getIntObjVar(self, "event.invasion.waves.timeBetweenWaves");
        int totalEventTime = 1;
        int useKeyObject = getIntObjVar(self, "event.invasion.keyObject.useKeyObject");
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
        if (useKeyObject == 1)
        {
            messageTo(self, "spawnKeyObject", null, 1, false);
        }
        for (int i = 0; i < numWaves; i++)
        {
            params.put("wave", i + 1);
            messageTo(self, "startNextWave", params, i * timeBetweenWaves * 30 + 1, false);
        }
        String broadcast = getStringObjVar(self, "event.invasion.broadcasts.startBroadcast");
        params.put("broadcast", broadcast);
        messageTo(self, "playAreaBroadcast", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int startNextWave(obj_id self, dictionary params) throws InterruptedException
    {
        int wave = params.getInt("wave");
        int timeBetweenSpawns = getIntObjVar(self, "event.invasion.waves.timeBetweenSpawns");
        int numCreatureTypes = getIntObjVar(self, "event.invasion.creature.numCreatureTypes");
        int numWaves = getIntObjVar(self, "event.invasion.waves.numWaves");
        int specialBroadcastWave = getIntObjVar(self, "event.invasion.broadcasts.specialBroadcastWave");
        if (specialBroadcastWave == wave)
        {
            String broadcast = getStringObjVar(self, "event.invasion.broadcasts.specialBroadcast");
            params.put("broadcast", broadcast);
            messageTo(self, "playAreaBroadcast", params, 1, false);
        }
        if (wave == numWaves)
        {
            int awardBadge = getIntObjVar(self, "event.invasion.misc.awardBadge");
            if (awardBadge == 1)
            {
                obj_id[] objPlayers = getPlayerCreaturesInRange(self, 255);
                if (objPlayers != null && objPlayers.length > 0)
                {
                    for (obj_id objPlayer : objPlayers) {
                        badge.grantBadge(objPlayer, "bdg_accolade_live_event");
                    }
                }
            }
        }
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
        for (int i = 0; i < numCreatureTypes; i++)
        {
            int numCreatures = getIntObjVar(self, "event.invasion.waves.wave" + wave + ".creature" + (i + 1));
            for (int j = 0; j < numCreatures; j++)
            {
                String spawnName = getStringObjVar(self, "event.invasion.creature.creatureType" + (i + 1));
                params.put("spawnName", spawnName);
                messageTo(self, "spawnNextCreature", params, timeBetweenSpawns * j + 1, false);
                int adjNumCreatureTypes = numCreatureTypes - 1;
                int adjNumCreature = numCreatures - 1;
                if (adjNumCreatureTypes < 0)
                {
                    adjNumCreatureTypes = 0;
                }
                if (adjNumCreature < 0)
                {
                    adjNumCreature = 0;
                }
                if (wave == numWaves && i == adjNumCreatureTypes && j == adjNumCreature)
                {
                    messageTo(self, "cleanUp", null, timeBetweenSpawns * j + 30, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnNextCreature(obj_id self, dictionary params) throws InterruptedException
    {
        String spawnName = params.getString("spawnName");
        int method = getIntObjVar(self, "event.invasion.spawning.method");
        if (method == 0)
        {
            float minRadius = getFloatObjVar(self, "event.invasion.spawning.minRadius");
            float maxRadius = getFloatObjVar(self, "event.invasion.spawning.maxRadius");
            location center = getLocation(self);
            location spawnLoc = null;
            int x = 0;
            while (x < 10)
            {
                location loc = utils.getRandomLocationInRing(center, minRadius, maxRadius);
                spawnLoc = locations.getGoodLocationAroundLocation(loc, 5.0f, 5.0f, 50.0f, 50.0f);
                if (spawnLoc != null)
                {
                    break;
                }
                x++;
            }
            obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
            obj_id critter = create.object(spawnName, spawnLoc);
            setObjVar(critter, "event.invasion.target", self);
            setObjVar(critter, "event.invasion.coordinator", coordinator);
            attachScript(critter, "event.invasion.invader");
            return SCRIPT_CONTINUE;
        }
        if (method == 1)
        {
            int numSpawnPoints = getIntObjVar(self, "event.invasion.spawning.numSpawnPoints");
            int randomSpawnPoint = rand(1, numSpawnPoints);
            location spawnPoint = getLocationObjVar(self, "event.invasion.spawning.location" + randomSpawnPoint);
            if (spawnPoint != null)
            {
                obj_id critter = create.object(spawnName, spawnPoint);
                setObjVar(critter, "event.invasion.target", self);
                obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
                setObjVar(critter, "event.invasion.coordinator", coordinator);
                attachScript(critter, "event.invasion.invader");
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int playAreaBroadcast(obj_id self, dictionary params) throws InterruptedException
    {
        String areaBroadcast = params.getString("broadcast");
        obj_id[] objPlayers = getPlayerCreaturesInRange(self, 255);
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (obj_id objPlayer : objPlayers) {
                sendSystemMessage(objPlayer, areaBroadcast, null);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float myCreationTime = getFloatObjVar(self, "event.invasion.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - myCreationTime;
        if (delta > 60 * 60 * 10)
        {
            destroyObject(self);
        }
        return;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnKeyObject(obj_id self, dictionary params) throws InterruptedException
    {
        String name = getStringObjVar(self, "event.invasion.keyObject.name");
        float expirationTime = getFloatObjVar(self, "event.invasion.keyObject.expirationTime");
        location whereIAmNow = getLocation(self);
        float timeStamp = getGameTime();
        obj_id keyObject = create.object(name, whereIAmNow);
        setObjVar(keyObject, "event.invasion.keyObject.expirationTime", expirationTime);
        setObjVar(keyObject, "event.invasion.keyObject.timeStamp", timeStamp);
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.eventCoordinator");
        setObjVar(keyObject, "event.invasion.target", self);
        setObjVar(keyObject, "event.invasion.coordinator", coordinator);
        sendSystemMessage(coordinator, "Created key object with OID " + keyObject, null);
        attachScript(keyObject, "event.invasion.key_object");
        return SCRIPT_CONTINUE;
    }
}
