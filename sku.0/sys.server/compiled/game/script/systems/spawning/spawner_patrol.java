package script.systems.spawning;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.spawning;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class spawner_patrol extends script.base_script
{
    public spawner_patrol()
    {
    }
    public static final int LOCATION_SEARCH_RADIUS = 1000;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        start(self);
        return SCRIPT_CONTINUE;
    }
    public int startTheaterFromBuildout(obj_id self, dictionary params) throws InterruptedException
    {
        start(self);
        return SCRIPT_CONTINUE;
    }
    public void start(obj_id self) throws InterruptedException
    {
        messageTo(self, "setLocationArray", null, 5, false);
        if (!hasObjVar(self, "registerWithController"))
        {
            setObjVar(self, "registerWithController", 1);
        }
        if (canSpawnByConfigSetting())
        {
            dictionary dict = new dictionary();
            dict.put("spawnNum", 0);
            messageTo(self, "doInitialSpawn", dict, 10, false);
        }
    }
    public int doInitialSpawn(obj_id self, dictionary params) throws InterruptedException
    {
        if (!spawning.checkSpawnCount(self))
        {
            return SCRIPT_CONTINUE;
        }
        String strSpawnType = getStringObjVar(self, "strSpawns");
        String[] strSpawns = 
        {
            strSpawnType
        };
        float[] fltSizes = 
        {
            8.0f
        };
        String strFileName = "datatables/spawning/ground_spawning/types/" + strSpawnType + ".iff";
        if (dataTableOpen(strFileName))
        {
            strSpawns = dataTableGetStringColumnNoDefaults(strFileName, "strItem");
            fltSizes = dataTableGetFloatColumn(strFileName, "fltSize");
            if ((strSpawns == null) || (strSpawns.length == 0))
            {
                LOG("spawning", "NO SPAWNS IN FILE " + strFileName);
                setName(self, "Mangled spawner: strFileName is " + strFileName + " I couldnt find any spawns in that file.");
                return SCRIPT_CONTINUE;
            }
            if (fltSizes.length == 0)
            {
                LOG("spawning", "BAD NUMBER OF SIZES !@!@@ IN FILE " + strFileName);
                setName(self, "Mangled spawner: BAD NUMBER OF SIZES !@!@@ IN FILE " + strFileName);
                return SCRIPT_CONTINUE;
            }
            if (fltSizes.length != strSpawns.length)
            {
                LOG("spawning", "Missing either spawns or sizes in " + strFileName);
                setName(self, "Mangled spawner: Missing either spawns or sizes in " + strFileName);
                return SCRIPT_CONTINUE;
            }
        }
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolArray");
        if (patrolPoints == null)
        {
            LOG("spawning", "ERROR:  patrolArray script var not defined.");
            setName(self, "Mangled spawner: no patrol points could be identified via patrolArray.");
            return SCRIPT_CONTINUE;
        }
        int maxSpawns = getIntObjVar(self, "intSpawnCount");
        if (maxSpawns >= patrolPoints.length)
        {
            LOG("spawning", "ERROR:  Attempting to spawn more creatures than patrol points.");
            setName(self, "Mangled spawner: there are more creatures than patrol points.");
            return SCRIPT_CONTINUE;
        }
        Vector goodSpawns = new Vector();
        for (int i = 0; i < fltSizes.length; ++i)
        {
            goodSpawns.addElement(i);
        }
        int spawnCount = 0;
        while (spawnCount < maxSpawns)
        {
            int roll = rand(0, goodSpawns.size() - 1);
            int rowIndex = (Integer) goodSpawns.get(roll);
            String spawn = strSpawns[rowIndex];
            location locTest = patrolPoints[spawnCount];
            if (createMob(spawn, null, locTest, 0.0f, self, false, spawnCount))
            {
                ++spawnCount;
            }
            else 
            {
                LOG("spawning", "Unable to create creature " + spawn + ".  Check data.");
                utils.removeElementAt(goodSpawns, roll);
                if (goodSpawns.size() == 0)
                {
                    LOG("spawning", "All entries in table " + strFileName + " are bad!!!");
                    setName(self, "Mangled spawner: No valid spawners!");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int doSpawnEvent(obj_id self, dictionary params) throws InterruptedException
    {
        if (!spawning.checkSpawnCount(self))
        {
            LOG("spawning", "No count 1");
            return SCRIPT_CONTINUE;
        }
        int intGoodLocationSpawner = getIntObjVar(self, "intGoodLocationSpawner");
        float fltSize;
        String strSpawn = getStringObjVar(self, "strSpawns");
        if (strSpawn == null || strSpawn.length() < 1)
        {
            setName(self, "Mangled spawner: strSpawn is null or empty.");
            return SCRIPT_CONTINUE;
        }
        String strFileName = "datatables/spawning/ground_spawning/types/" + strSpawn + ".iff";
        if (dataTableOpen(strFileName))
        {
            String[] strSpawns = dataTableGetStringColumnNoDefaults(strFileName, "strItem");
            float[] fltSizes = dataTableGetFloatColumn(strFileName, "fltSize");
            if ((strSpawns == null) || (strSpawns.length == 0))
            {
                setName(self, "Mangled spawner: strFileName is " + strFileName + " I couldn't find any spawns in that file.");
                return SCRIPT_CONTINUE;
            }
            else if (fltSizes.length == 0)
            {
                setName(self, "Mangled spawner: BAD NUMBER OF SIZES !@!@@ IN FILE " + strFileName);
                return SCRIPT_CONTINUE;
            }
            else if (fltSizes.length != strSpawns.length)
            {
                setName(self, "Mangled spawner: Missing either spawns or sizes in " + strFileName);
                return SCRIPT_CONTINUE;
            }
            int intRoll = rand(0, strSpawns.length - 1);
            fltSize = fltSizes[intRoll];
            strSpawn = strSpawns[intRoll];
        }
        else
        {
            setName(self, "Mangled spawner: strSpawn is " + strSpawn + " and datatable could not be opened.");
            return SCRIPT_CONTINUE;
        }
        float fltRadius = getFloatObjVar(self, "fltRadius");
        location locTest = spawning.getRandomLocationInCircle(getLocation(self), fltRadius);
        fltSize = getClosestSize(fltSize);
        if (intGoodLocationSpawner > 0)
        {
            requestLocation(self, strSpawn, locTest, rand(100, 200), fltSize, true, true);
        }
        else 
        {
            createMob(strSpawn, null, locTest, fltRadius, self);
        }
        return SCRIPT_CONTINUE;
    }
    public float getClosestSize(float fltOriginalSize) throws InterruptedException
    {
        if (fltOriginalSize <= 4.0f)
        {
            return 4.0f;
        }
        if (fltOriginalSize <= 8.0f)
        {
            return 8.0f;
        }
        if (fltOriginalSize <= 12.0f)
        {
            return 12.0f;
        }
        if (fltOriginalSize <= 16.0f)
        {
            return 16.0f;
        }
        if (fltOriginalSize <= 32.0f)
        {
            return 32.0f;
        }
        else if (fltOriginalSize <= 48.0f)
        {
            return 48.0f;
        }
        else if (fltOriginalSize <= 64.0f)
        {
            return 64.0f;
        }
        else if (fltOriginalSize <= 80.0f)
        {
            return 80f;
        }
        else if (fltOriginalSize <= 96.0f)
        {
            return 96f;
        }
        return 32f;
    }
    public boolean createMob(String creature, obj_id locationObject, location spawnLoc, float spawnRadius, obj_id self) throws InterruptedException
    {
        return createMob(creature, locationObject, spawnLoc, spawnRadius, self, true, 0);
    }
    public boolean createMob(String strId, obj_id objLocationObject, location locLocation, float fltRadius, obj_id self, boolean doCallBack, int startingPoint) throws InterruptedException
    {
        if (!spawning.checkSpawnCount(self))
        {
            return false;
        }
        int intIndex = strId.indexOf(".iff");
        float fltMinSpawnTime = getFloatObjVar(self, "fltMinSpawnTime");
        float fltMaxSpawnTime = getFloatObjVar(self, "fltMaxSpawnTime");
        float fltRespawnTime = rand(fltMinSpawnTime, fltMaxSpawnTime);
        if (intIndex > -1)
        {
            obj_id objTemplate = createObject(strId, locLocation);
            if (isIdValid(objLocationObject))
            {
                destroyObject(objLocationObject);
            }
            if (!isIdValid(objTemplate))
            {
                return false;
            }
            spawning.incrementSpawnCount(self);
            utils.setScriptVar(objTemplate, "parent", self);
            utils.setScriptVar(objTemplate, "fltRespawnTime", fltRespawnTime);
            utils.setScriptVar(objTemplate, "patrolPoints", utils.getLocationArrayScriptVar(self, "patrolArray"));
            utils.setScriptVar(objTemplate, "patrolPathType", getStringObjVar(self, "patrolPathType"));
            utils.setScriptVar(objTemplate, "startingPoint", startingPoint);
            attachScript(objTemplate, "systems.spawning.patrol_spawned_tracker");
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
                return false;
            }
            int intBehavior = getIntObjVar(self, "intDefaultBehavior");
            ai_lib.setDefaultCalmBehavior(objMob, intBehavior);
            spawning.incrementSpawnCount(self);
            utils.setScriptVar(objMob, "parent", self);
            utils.setScriptVar(objMob, "fltRespawnTime", fltRespawnTime);
            utils.setScriptVar(objMob, "patrolPoints", utils.getLocationArrayScriptVar(self, "patrolArray"));
            utils.setScriptVar(objMob, "patrolPathType", getStringObjVar(self, "patrolPathType"));
            utils.setScriptVar(objMob, "startingPoint", startingPoint);
            attachScript(objMob, "systems.spawning.patrol_spawned_tracker");
        }
        if (doCallBack)
        {
            if (spawning.checkSpawnCount(self))
            {
                messageTo(self, "doSpawnEvent", null, fltRespawnTime, false);
            }
        }
        return true;
    }
    public int setLocationArray(obj_id self, dictionary params) throws InterruptedException
    {
        String[] patrolPointString = utils.getStringArrayObjVar(self, "strPatrolPointNames");
        if (patrolPointString == null || patrolPointString.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        Vector patrolPointLocation = new Vector();
        patrolPointLocation.setSize(0);
        obj_id[] objects = getObjectsInRange(self, 700);
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        patrolPointLocation = utils.addElement(patrolPointLocation, getLocation(getSelf()));
        boolean isInInstance = false;
        obj_id dungeonController = obj_id.NULL_ID;
        if (hasObjVar(self, "dungeonController"))
        {
            isInInstance = true;
        }
        if (isInInstance)
        {
            dungeonController = getObjIdObjVar(self, "dungeonController");
        }
        setName(self, getStringObjVar(self, "strName"));
        for (String aPatrolPointString : patrolPointString) {
            for (obj_id object : objects) {
                if (!isIdValid(object)) {
                    continue;
                }
                if (hasObjVar(object, "pointName")) {
                    if ((getStringObjVar(object, "pointName")).equals(aPatrolPointString)) {
                        if (isInInstance) {
                            if (getObjIdObjVar(object, "dungeonController") == dungeonController) {
                                setName(object, getStringObjVar(object, "pointName"));
                                patrolPointLocation = utils.addElement(patrolPointLocation, getLocation(object));
                            }
                        } else {
                            setName(object, getStringObjVar(object, "pointName"));
                            patrolPointLocation = utils.addElement(patrolPointLocation, getLocation(object));
                        }
                    }
                }
            }
        }
        location[] patrolArray = new location[0];
        if (patrolPointLocation != null)
        {
            patrolArray = new location[patrolPointLocation.size()];
            patrolPointLocation.toArray(patrolArray);
        }
        utils.setScriptVar(self, "patrolArray", patrolArray);
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String strId, obj_id objLocationObject, location locLocation, float fltRadius) throws InterruptedException
    {
        if (isIdValid(objLocationObject))
        {
            createMob(strId, objLocationObject, locLocation, fltRadius, self);
        }
        else 
        {
            float fltMinSpawnTime = getFloatObjVar(self, "fltMinSpawnTime");
            float fltMaxSpawnTime = getFloatObjVar(self, "fltMaxSpawnTime");
            float fltRespawnTime = rand(fltMinSpawnTime, fltMaxSpawnTime);
            messageTo(self, "doSpawnEvent", null, fltRespawnTime, false);
        }
        return SCRIPT_CONTINUE;
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
        messageTo(self, "doSpawnEvent", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public boolean canSpawnByConfigSetting() throws InterruptedException {
        String disableSpawners = getConfigSetting("GameServer", "disablePatrolSpawners");
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
