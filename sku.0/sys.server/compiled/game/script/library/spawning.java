package script.library;

import script.dictionary;
import script.location;
import script.obj_id;

import java.util.Vector;

public class spawning extends script.base_script
{
    public spawning()
    {
    }
    public static void activateSpawnerHack(obj_id objPlayer) throws InterruptedException
    {
        obj_id[] objSpawners = getAllObjectsWithObjVar(getLocation(objPlayer), 24000, "intSpawnSystem");
        if (objSpawners.length == 0)
        {
            return;
        }
        messageTo(objSpawners[rand(0, objSpawners.length - 1)], "doSpawnEvent", null, 0, false);
    }
    public static location getRandomLocationInCircle(location locTest, float fltSize) throws InterruptedException
    {
        locTest.x = locTest.x + rand(-1 * fltSize, fltSize);
        locTest.z = locTest.z + rand(-1 * fltSize, fltSize);
        return locTest;
    }
    public static location getRandomLocationAtDistance(location locTest, float fltSize) throws InterruptedException
    {
        int position = 1;
        if (rand(0, 1) == 0)
        {
            position = -1;
        }
        if (rand(0, 1) == 1)
        {
            locTest.x = locTest.x + rand(-1 * fltSize, fltSize);
            locTest.z = locTest.z + fltSize * position;
        }
        else 
        {
            locTest.x = locTest.x + fltSize * position;
            locTest.z = locTest.z + rand(-1 * fltSize, fltSize);
        }
        return locTest;
    }
    public static boolean checkSpawnCount(obj_id self) throws InterruptedException
    {
        int intSpawnCount = getIntObjVar(self, "intSpawnCount");
        int intCurrentSpawnCount = utils.getIntScriptVar(self, "intCurrentSpawnCount");
        return intCurrentSpawnCount < intSpawnCount;
    }
    public static void incrementSpawnCount(obj_id self) throws InterruptedException
    {
        int intCurrentSpawnCount = utils.getIntScriptVar(self, "intCurrentSpawnCount");
        int intSpawnCount = getIntObjVar(self, "intSpawnCount");
        intCurrentSpawnCount = intCurrentSpawnCount + 1;
        if (intCurrentSpawnCount <= intSpawnCount)
        {
            utils.setScriptVar(self, "intCurrentSpawnCount", intCurrentSpawnCount);
        }
    }
    public static void addToSpawnDebugList(obj_id self, obj_id spawned) throws InterruptedException
    {
        Vector debugSpawnList = new Vector();
        debugSpawnList.setSize(0);
        if (utils.hasScriptVar(self, "debugSpawnList"))
        {
            debugSpawnList = utils.getResizeableObjIdArrayScriptVar(self, "debugSpawnList");
        }
        debugSpawnList = utils.addElement(debugSpawnList, spawned);
        utils.setScriptVar(self, "debugSpawnList", debugSpawnList);
    }
    public static Vector getAllObjectsWithObjVar(location locTest, String strObjVarName) throws InterruptedException
    {
        Vector objArray = new Vector();
        objArray.setSize(0);
        return objArray;
    }
    public static Vector getObjectsWithObjVar(obj_id objParent, String strObjVarName, Vector objArray) throws InterruptedException
    {
        if (hasObjVar(objParent, strObjVarName))
        {
            objArray = utils.addElement(objArray, objParent);
        }
        obj_id[] objContents = getContents(objParent);
        if ((objContents != null) && (objContents.length > 0))
        {
            for (obj_id objContent : objContents) {
                getObjectsWithObjVar(objContent, strObjVarName, objArray);
            }
        }
        return objArray;
    }
    public static obj_id[] getAllContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        obj_id[] objTestContents;
        for (obj_id objCell : objCells) {
            objTestContents = getContents(objCell);
            if ((objTestContents != null) && (objTestContents.length > 0)) {
                for (obj_id objTestContent : objTestContents) {
                    objContents = utils.addElement(objContents, objTestContent);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
    public static void planetSpawnersCreatureDied(obj_id spawner, obj_id deadGuy) throws InterruptedException
    {
        if (!isIdValid(spawner))
        {
            CustomerServiceLog("SPAWNER_OVERLOAD", "Spawner " + spawner + " is invalid");
            return;
        }
        int count = utils.getIntScriptVar(spawner, "count");
        count = count - 1;
        if (count < 0)
        {
            CustomerServiceLog("SPAWNER_OVERLOAD", "Count went below 0 on " + spawner + " on Rori. Rori_npc_medium script.");
            count = 0;
        }
        utils.setScriptVar(spawner, "count", count);
        Vector spawnedList = utils.getResizeableObjIdArrayScriptVar(spawner, "myCreations");
        for (int i = 0; i < spawnedList.size(); i++)
        {
            if (spawnedList.get(i) == deadGuy)
            {
                spawnedList.remove(spawnedList.get(i));
                continue;
            }
            if (spawnedList.get(i) == null)
            {
                spawnedList.remove(spawnedList.get(i));
            }
        }
        utils.setScriptVar(spawner, "myCreations", spawnedList);
    }
    public static obj_id createSpawnInLegacyCell(obj_id dungeon, location creatureLocation, String creatureName) throws InterruptedException
    {
        if (!isValidId(dungeon))
        {
            CustomerServiceLog("bad_spawner_data", "createSpawnInLegacyCell - Dungeon passed to function was invalid.");
            return null;
        }
        if (creatureLocation == null)
        {
            CustomerServiceLog("bad_spawner_data", "createSpawnInLegacyCell - Location passed to function was invalid for dungeon: " + dungeon);
            return null;
        }
        if (creatureName == null || creatureName.length() <= 0)
        {
            CustomerServiceLog("bad_spawner_data", "createSpawnInLegacyCell - Creature Name passed to function was invalid for dungeon: " + dungeon);
            return null;
        }
        obj_id creature = create.object(creatureName, creatureLocation);
        if (!isValidId(creature))
        {
            CustomerServiceLog("bad_spawner_data", "createSpawnInLegacyCell - Creature could not be created for dungeon: " + dungeon);
            return null;
        }
        setObjVar(creature, "dungeon", dungeon);
        create.addDestroyMessage(creature, creatureName + "Dead", 300.0f, dungeon);
        return creature;
    }
    public static boolean spawnObjectsInDungeonFromTable(obj_id dungeon, String planet, String table) throws InterruptedException
    {
        if (!isValidId(dungeon))
        {
            return false;
        }
        if (table == null || table.length() <= 0)
        {
            return false;
        }
        if (planet == null || planet.length() <= 0)
        {
            return false;
        }
        int numberOfObjectsToSpawn = dataTableGetNumRows(table);
        if (numberOfObjectsToSpawn <= 0)
        {
            return false;
        }
        dictionary objToSpawn;
        String object;
        String spawnRoom;
        obj_id room;
        obj_id objectCreated;
        String script;
        String[] scripts;
        String objVars;
        String objName;

        for (int i = 0; i < numberOfObjectsToSpawn; i++)
        {
            objToSpawn = dataTableGetRow(table, i);
            if (objToSpawn == null)
            {
                continue;
            }
            object = objToSpawn.getString("object");
            if (object == null || object.length() <= 0)
            {
                continue;
            }
            float xCoord = objToSpawn.getFloat("loc_x");
            float yCoord = objToSpawn.getFloat("loc_y");
            float zCoord = objToSpawn.getFloat("loc_z");
            float yaw = objToSpawn.getFloat("yaw");
            spawnRoom = objToSpawn.getString("room");
            if (spawnRoom == null || spawnRoom.length() <= 0)
            {
                continue;
            }
            room = getCellId(dungeon, spawnRoom);
            if (!isValidId(room))
            {
                continue;
            }
            objectCreated = createObject(object, new location(xCoord, yCoord, zCoord, planet, room));
            if (!isValidId(objectCreated))
            {
                continue;
            }
            setYaw(objectCreated, yaw);
            script = objToSpawn.getString("script");
            if (script != null && script.length() > 0)
            {
                scripts = split(script, ',');
                for (String script1 : scripts) {
                    if (!hasScript(objectCreated, script1)) {
                        attachScript(objectCreated, script1);
                    }
                }
            }
            objVars = objToSpawn.getString("objvar");
            if (objVars != null && objVars.length() > 0)
            {
                utils.setObjVarsListUsingSemiColon(objectCreated, objVars);
            }
            objName = objToSpawn.getString("name");
            if (objName != null && objName.length() > 0)
            {
                setName(objectCreated, objName);
            }
        }

        return true;
    }
}
