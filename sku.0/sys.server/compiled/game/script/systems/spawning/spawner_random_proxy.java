package script.systems.spawning;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.spawning;
import script.library.utils;
import script.location;
import script.obj_id;

public class spawner_random_proxy extends script.base_script
{
    public spawner_random_proxy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("holidaySpawner", "spawner_random.OnAttach: Initialized for: " + self);
        if (!hasObjVar(self, "registerWithController"))
        {
            setObjVar(self, "registerWithController", 1);
        }
        if (hasObjVar(self, "strName"))
        {
            setName(self, getStringObjVar(self, "strName"));
        }
        if (canSpawnByConfigSetting())
        {
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("holidaySpawner", "spawner_random.OnAttach: Initialized for: " + self);
        if (!hasObjVar(self, "registerWithController"))
        {
            setObjVar(self, "registerWithController", 1);
        }
        if (hasObjVar(self, "strName"))
        {
            setName(self, getStringObjVar(self, "strName"));
        }
        if (canSpawnByConfigSetting())
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.OnAttach: CAN SPAWN for: " + self);
            messageTo(self, "doSpawnEvent", null, 20, false);
        }
        else 
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.OnAttach: CANNOT SPAWN for: " + self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (!isGod(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getIntendedTarget(speaker);
        if (target != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (text.equals("reset"))
        {
            if (!hasObjVar(self, "registerWithController"))
            {
                setObjVar(self, "registerWithController", 1);
            }
            if (hasObjVar(self, "strName"))
            {
                setName(self, getStringObjVar(self, "strName"));
            }
            if (canSpawnByConfigSetting())
            {
                messageTo(self, "doSpawnEvent", null, 20, false);
            }
            debugSpeakMsg(speaker, "reset");
        }
        return SCRIPT_CONTINUE;
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: Initialized for: " + self);
        String datatable = getStringObjVar(self, "strSpawnTable");
        if (datatable == null || datatable.equals(""))
        {
            debugSpeakMsg(self, "I need a spawn table under objvar strSpawnTable");
            LOG("spawning", "No spawn datatable set, necessary for random spawners");
            return SCRIPT_CONTINUE;
        }
        int numberOfLocations = dataTableGetNumRows(datatable);
        if (numberOfLocations <= 0)
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: Spawn table has no rows. Exiting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: There are " + numberOfLocations + " number of rows for the spawner to select from. Random Spawner: " + self);
        int numberOfSpawns = getIntObjVar(self, "intSpawnCount");
        if (numberOfSpawns <= 0)
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: Spawner has no spawn count. Exiting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: There are " + numberOfSpawns + " spawns needed for this spawner to work. Random Spawner: " + self);
        if (numberOfSpawns >= numberOfLocations)
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: There are more or equal the number of spawns to number of locations. Need to be less. Exiting.");
            return SCRIPT_CONTINUE;
        }
        if (!spawning.checkSpawnCount(self))
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: spawning.checkSpawnCount(self) reports that it cannot spawn any more. Exiting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: We have not reached max spawns for event per spawning.checkSpawnCount library. Spawner: " + self);
        String strSpawnType = getStringObjVar(self, "strSpawns");
        if (strSpawnType == null || strSpawnType.equals(""))
        {
            CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent:The strSpawns objvar was invalid. Exiting.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: strSpawns: " + strSpawnType);
        String strSpawn = strSpawnType;
        if (!strSpawnType.contains(".iff"))
        {
            String strFileName = "datatables/spawning/ground_spawning/types/" + strSpawnType + ".iff";
            if (dataTableOpen(strFileName))
            {
                CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: strSpawns datatable found and opened");
                String[] strSpawns = dataTableGetStringColumnNoDefaults(strFileName, "strItem");
                if (strSpawns == null || strSpawns.length == 0)
                {
                    CustomerServiceLog("holidaySpawner", "spawner_random.doSpawnEvent: no strItem or corrupt data found for strSpawns datatable.");
                    setName(self, "Mangled spawner. strFileName is " + strFileName + " I couldnt find any spawns in that file.");
                }
                int intRoll = rand(0, strSpawns.length - 1);
                strSpawn = strSpawns[intRoll];
            }
        }
        if (strSpawn == null || strSpawn.length() < 1)
        {
            setName(self, "Mangled spawner. strSpawn is " + strSpawn + ".");
            return SCRIPT_CONTINUE;
        }
        int x = rand(0, numberOfLocations - 1);
        float xCoord = dataTableGetFloat(datatable, x, "loc_x");
        float yCoord = dataTableGetFloat(datatable, x, "loc_y");
        float zCoord = dataTableGetFloat(datatable, x, "loc_z");
        float yaw = dataTableGetFloat(datatable, x, "yaw");
        location myself = getLocation(self);
        String planet = myself.area;
        location locLocation = new location(xCoord, yCoord, zCoord, planet);
        if (!utils.hasScriptVar(self, "spawnedObjectList.spawnLoc" + locLocation))
        {
            if (utils.hasScriptVar(self, "locationFindTries"))
            {
                utils.removeScriptVar(self, "locationFindTries");
            }
            createMob(strSpawn, null, locLocation, yaw, self);
        }
        else 
        {
            if (utils.hasScriptVar(self, "locationFindTries"))
            {
                int nrTries = utils.getIntScriptVar(self, "locationFindTries");
                utils.setScriptVar(self, "locationFindTries", nrTries + 1);
            }
            else 
            {
                utils.setScriptVar(self, "locationFindTries", 1);
            }
            if (utils.getIntScriptVar(self, "locationFindTries") < 501)
            {
                messageTo(self, "doSpawnEvent", null, 3, false);
            }
            else 
            {
                LOG("spawning", "Random spawner: " + self + " named: " + getStringObjVar(self, "strName") + " has tried to find a good location for a spawn 500 times. Now giving up");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createMob(String strId, obj_id objLocationObject, location locLocation, float yaw, obj_id self) throws InterruptedException
    {
        if (!spawning.checkSpawnCount(self))
        {
            return;
        }
        int intIndex = strId.indexOf(".iff");
        float fltMinSpawnTime = getFloatObjVar(self, "fltMinSpawnTime");
        float fltMaxSpawnTime = getFloatObjVar(self, "fltMaxSpawnTime");
        float fltRespawnTime = rand(fltMinSpawnTime, fltMaxSpawnTime);
        float fltLifeTime = 0;
        if (hasObjVar(self, "fltMinLifeTime"))
        {
            if (hasObjVar(self, "fltMaxLifeTime"))
            {
                float fltMinLifeTime = getFloatObjVar(self, "fltMinLifeTime");
                float fltMaxLifeTime = getFloatObjVar(self, "fltMaxLifeTime");
                if (fltMinLifeTime < fltMaxLifeTime)
                {
                    fltLifeTime = rand(fltMinLifeTime, fltMaxLifeTime);
                }
            }
        }
        if (intIndex > -1)
        {
            obj_id objTemplate = createObject(strId, locLocation);
            if (isIdValid(objLocationObject))
            {
                destroyObject(objLocationObject);
            }
            if (!isIdValid(objTemplate))
            {
                return;
            }
            spawning.incrementSpawnCount(self);
            spawning.addToSpawnDebugList(self, objTemplate);
            setObjVar(objTemplate, "objParent", self);
            setObjVar(objTemplate, "fltRespawnTime", fltRespawnTime);
            utils.setScriptVar(self, "spawnedObjectList.spawnLoc" + locLocation, objTemplate);
            utils.setScriptVar(objTemplate, "deathTracker", locLocation);
            attachScript(objTemplate, "systems.spawning.spawned_tracker");
            if (fltLifeTime != 0)
            {
                messageTo(objTemplate, "selfDestruct", null, fltLifeTime, false);
            }
            setYaw(objTemplate, yaw);
            if (hasObjVar(self, "strQuest"))
            {
                String strQuest = getStringObjVar(self, "strQuest");
                if (strQuest != null && strQuest.length() > 0)
                {
                    setObjVar(objTemplate, "strQuest", strQuest);
                }
            }
            if (hasObjVar(self, "strSignal"))
            {
                String strSignal = getStringObjVar(self, "strSignal");
                if (strSignal != null && strSignal.length() > 0)
                {
                    setObjVar(objTemplate, "strSignal", strSignal);
                }
            }
            if (hasObjVar(self, "strFailSignal"))
            {
                String strFailSignal = getStringObjVar(self, "strFailSignal");
                if (strFailSignal != null && strFailSignal.length() > 0)
                {
                    setObjVar(objTemplate, "strFailSignal", strFailSignal);
                }
            }
            if (hasObjVar(self, "strCollection"))
            {
                String strCollection = getStringObjVar(self, "strCollection");
                if (strCollection != null && strCollection.length() > 0)
                {
                    setObjVar(objTemplate, "strCollection", strCollection);
                }
            }
            setObjVar(objTemplate, "locationObjParent", getLocation(self));
            if (hasObjVar(self, "strQuestBetsy"))
            {
                String strQuestBetsy = getStringObjVar(self, "strQuestBetsy");
                if (strQuestBetsy != null && strQuestBetsy.length() > 0)
                {
                    setObjVar(objTemplate, "strQuestBetsy", strQuestBetsy);
                }
            }
            if (hasObjVar(self, "strQuestPerfume"))
            {
                String strQuestPerfume = getStringObjVar(self, "strQuestPerfume");
                if (strQuestPerfume != null && strQuestPerfume.length() > 0)
                {
                    setObjVar(objTemplate, "strQuestPerfume", strQuestPerfume);
                }
            }
            if (hasObjVar(self, "strQuestStunBaton"))
            {
                String strQuestStunBaton = getStringObjVar(self, "strQuestStunBaton");
                if (strQuestStunBaton != null && strQuestStunBaton.length() > 0)
                {
                    setObjVar(objTemplate, "strQuestStunBaton", strQuestStunBaton);
                }
            }
            if (hasObjVar(self, "strQuestBomberHelmet"))
            {
                String strQuestBomberHelmet = getStringObjVar(self, "strQuestBomberHelmet");
                if (strQuestBomberHelmet != null && strQuestBomberHelmet.length() > 0)
                {
                    setObjVar(objTemplate, "strQuestBomberHelmet", strQuestBomberHelmet);
                }
            }
            if (hasObjVar(self, "strQuestScoutCamera"))
            {
                String strQuestScoutCamera = getStringObjVar(self, "strQuestScoutCamera");
                if (strQuestScoutCamera != null && strQuestScoutCamera.length() > 0)
                {
                    setObjVar(objTemplate, "strQuestScoutCamera", strQuestScoutCamera);
                }
            }
        }
        else 
        {
            if (isIdValid(objLocationObject))
            {
                destroyObject(objLocationObject);
            }
            obj_id objMob = create.object(strId, locLocation);
            if (!isIdValid(objMob))
            {
                setName(self, "BAD MOB OF TYPE " + strId);
                return;
            }
            int intBehavior = getIntObjVar(self, "intDefaultBehavior");
            ai_lib.setDefaultCalmBehavior(objMob, intBehavior);
            spawning.incrementSpawnCount(self);
            spawning.addToSpawnDebugList(self, objMob);
            setObjVar(objMob, "objParent", self);
            setObjVar(objMob, "fltRespawnTime", fltRespawnTime);
            utils.setScriptVar(self, "spawnedObjectList.spawnLoc" + locLocation, objMob);
            utils.setScriptVar(objMob, "deathTracker", locLocation);
            attachScript(objMob, "systems.spawning.spawned_tracker");
            if (fltLifeTime != 0)
            {
                messageTo(objMob, "selfDestruct", null, fltLifeTime, false);
            }
            setYaw(objMob, yaw);
        }
        if (!spawning.checkSpawnCount(self))
        {
            return;
        }
        messageTo(self, "doSpawnEvent", null, fltRespawnTime, false);
    }
    public int spawnDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        int intCurrentSpawnCount = utils.getIntScriptVar(self, "intCurrentSpawnCount");
        intCurrentSpawnCount = intCurrentSpawnCount - 1;
        if (intCurrentSpawnCount > -1)
        {
            utils.setScriptVar(self, "intCurrentSpawnCount", intCurrentSpawnCount);
        }
        else 
        {
            utils.setScriptVar(self, "intCurrentSpawnCount", 0);
        }
        location loc = params.getLocation("loc");
        if (utils.hasScriptVar(self, "spawnedObjectList.spawnLoc" + loc))
        {
            utils.removeScriptVar(self, "spawnedObjectList.spawnLoc" + loc);
        }
        messageTo(self, "doSpawnEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException {
        String disableSpawners = getConfigSetting("GameServer", "disableAreaSpawners");
        return disableSpawners == null || !(disableSpawners.equals("true") || disableSpawners.equals("1"));
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "debugSpawnList"))
        {
            obj_id[] spawns = utils.getObjIdArrayScriptVar(self, "debugSpawnList");
            if (spawns == null || spawns.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (obj_id spawn : spawns) {
                if (isIdValid(spawn) && exists(spawn)) {
                    messageTo(spawn, "selfDestruct", null, 5, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
