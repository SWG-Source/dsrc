package script.library;

import script.dictionary;
import script.location;
import script.obj_id;

public class uberlair extends script.base_script
{
    public uberlair()
    {
    }
    public static void spawnBossMobiles(obj_id poiBaseObject, obj_id[] objLargeMobSpawners) throws InterruptedException
    {
        LOG("uber", "SPAWN BOSS MOBILES FUNCTION");
        String lairType = getStringObjVar(poiBaseObject, "spawning.lairType");
        String lairDatatable = "datatables/uberlair/" + lairType + ".iff";
        String[] strLargeMobs = dataTableGetStringColumnNoDefaults(lairDatatable, "strLargeMobs");
        String[] strLargeMobScript = dataTableGetStringColumnNoDefaults(lairDatatable, "strLargeMobScripts");
        LOG("uberlairs", "objLargeMobSpawners length is " + objLargeMobSpawners.length);
        String strMobToSpawn;
        location locSpawnLocation;
        obj_id objMob;

        for (obj_id objLargeMobSpawner : objLargeMobSpawners) {
            if (strLargeMobs.length == 0) {
                LOG("DESIGNER_FATAL", lairType + " has large mob spawners but no large mobs!");
                break;
            }
            strMobToSpawn = strLargeMobs[rand(0, strLargeMobs.length - 1)];
            locSpawnLocation = getLocation(objLargeMobSpawner);
            objMob = poi.createObject(poiBaseObject, strMobToSpawn, locSpawnLocation);
            ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_SENTINEL);
            if (strLargeMobScript.length > 0) {
                attachScript(objMob, strLargeMobScript[0]);
                setObjVar(objMob, "uberlair.strScript", strLargeMobScript[0]);
            }
            setObjVar(objMob, "uberlair.strType", strMobToSpawn);
            setObjVar(objMob, "uberlair.locSpawnLocation", locSpawnLocation);
            setObjVar(objMob, "uberlair.objParent", poiBaseObject);
        }
    }
    public static void spawnNpcLairMobiles(obj_id poiBaseObject, obj_id[] objMobSpawners) throws InterruptedException
    {
        final int SPAWNS_PER_EVENT = 3;
        String lairType = getStringObjVar(poiBaseObject, "spawning.lairType");
        LOG("uber", "SPAWN_NPC_LAIR_MOBILS");
        if (objMobSpawners == null)
        {
            LOG("DESIGNER_FATAL", "lair of " + lairType + " has no mob spawners in the theater it uses");
            return;
        }
        String lairDatatable = "datatables/uberlair/" + lairType + ".iff";
        String[] strMobs = dataTableGetStringColumnNoDefaults(lairDatatable, "strMobs");
        String[] strMobScript = dataTableGetStringColumnNoDefaults(lairDatatable, "strMobScripts");
        int intMaxSpawns[] = dataTableGetIntColumnNoDefaults(lairDatatable, "intMobCount");
        int intMobCount = intMaxSpawns[0];
        intMaxSpawns = dataTableGetIntColumnNoDefaults(lairDatatable, "intTotalMobs");
        int intTotalMobs = intMaxSpawns[0];
        setObjVar(poiBaseObject, "intTotalMobs", intTotalMobs);
        int intIndex = getIntObjVar(poiBaseObject, "intSpawnIndex");
        int intCounter = 0;
        String strMobToSpawn;
        location locSpawnLocation;
        obj_id objMob;
        for (int intI = intIndex; intI < intMobCount; intI++)
        {
            if (intCounter > SPAWNS_PER_EVENT)
            {
                setObjVar(poiBaseObject, "intSpawnIndex", intI);
                messageTo(poiBaseObject, "spawnMobiles", null, 3, false);
                return;
            }
            if (strMobs.length == 0)
            {
                LOG("DESIGNER_FATAL", lairType + " has large mob spawners but no large mobs!");
                break;
            }
            strMobToSpawn = strMobs[rand(0, strMobs.length - 1)];
            locSpawnLocation = getLocation(objMobSpawners[rand(0, objMobSpawners.length - 1)]);
            locSpawnLocation.x = locSpawnLocation.x + rand(-2, 2);
            locSpawnLocation.z = locSpawnLocation.z + rand(-2, 2);
            objMob = poi.createObject(poiBaseObject, strMobToSpawn, locSpawnLocation);
            ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_SENTINEL);
            if (strMobScript.length > 0)
            {
                attachScript(objMob, strMobScript[0]);
                setObjVar(objMob, "uberlair.strScript", strMobScript[0]);
            }
            setObjVar(objMob, "uberlair.strType", strMobToSpawn);
            setObjVar(objMob, "uberlair.locSpawnLocation", locSpawnLocation);
            setObjVar(objMob, "uberlair.objParent", poiBaseObject);
            intCounter = intCounter + 1;
        }
        setObjVar(poiBaseObject, "intSpawnCount", intMobCount);
        removeObjVar(poiBaseObject, "intSpawnIndex");
        messageTo(poiBaseObject, "spawnFormations", null, 3, false);
    }
    public static void respawnMobile(obj_id poiBaseObject, location locSpawnLocation, String strMobToSpawn, String strScript) throws InterruptedException
    {
        int intTotalMobs = getIntObjVar(poiBaseObject, "intTotalMobs");
        int intCurrentSpawnCount = getIntObjVar(poiBaseObject, "intSpawnCount");
        intCurrentSpawnCount = intCurrentSpawnCount + 1;
        if (intCurrentSpawnCount > intTotalMobs)
        {
            return;
        }
        obj_id objMob = poi.createObject(poiBaseObject, strMobToSpawn, locSpawnLocation);
        ai_lib.setDefaultCalmBehavior(objMob, ai_lib.BEHAVIOR_SENTINEL);
        if (strScript != null)
        {
            attachScript(objMob, strScript);
        }
        setObjVar(poiBaseObject, "intSpawnCount", intCurrentSpawnCount);
    }
    public static void createFormations(obj_id objParent) throws InterruptedException
    {
        LOG("uber", "SPAWN_formations FUNCTION");
        String lairType = getStringObjVar(objParent, "spawning.lairType");
        String lairDatatable = "datatables/uberlair/" + lairType + ".iff";
        LOG("uber", "lairDatatable is " + lairDatatable);
        String[] strFormations = dataTableGetStringColumnNoDefaults(lairDatatable, "strFormations");
        location[] locOffsets = getPatrolOffsets(getLocation(objParent), 50);
        String strFormationDataTable;
        String[] strMobs;
        String[] strLeader;
        obj_id objLeader;
        dictionary dctParams;
        for (int intI = 0; intI < strFormations.length; intI++)
        {
            strFormationDataTable = "datatables/uberlair/formations/" + strFormations[intI] + ".iff";
            LOG("uber", "formation datatable is " + strFormationDataTable);
            strMobs = dataTableGetStringColumnNoDefaults(strFormationDataTable, "strMobs");
            strLeader = dataTableGetStringColumnNoDefaults(strFormationDataTable, "strLeader");
            if (strMobs.length == 0)
            {
                LOG("DESIGNER_FATAL", "mobs in a formation file of " + strFormationDataTable + " is blank");
                return;
            }
            if (strLeader.length == 0)
            {
                LOG("DESIGNER_FATAL", "leader in a formation file of " + strFormationDataTable + " is blank");
                return;
            }
            objLeader = poi.createObject(objParent, strLeader[0], locOffsets[intI]);
            LOG("uber", "created leader at " + locOffsets[intI]);
            if (objLeader != null)
            {
                attachScript(objLeader, "systems.npc_lair.uber_lair_formation_leader");
                dctParams = new dictionary();
                dctParams.put("locWaypoints", locOffsets);
                dctParams.put("strFormationMembers", strMobs);
                dctParams.put("intOffset", intI);
                LOG("uber", "messaging " + objLeader + " with createFormation command");
                setObjVar(objLeader, "objParent", objParent);
                messageTo(objLeader, "createFormation", dctParams, 3, false);
            }
        }
    }
    public static location[] getPatrolOffsets(location locParentLocation, float fltDistance) throws InterruptedException
    {
        location[] locWaypoints = new location[4];
        locWaypoints[0] = new location(locParentLocation);
        locWaypoints[0].x = locWaypoints[0].x - fltDistance;
        locWaypoints[0].z = locWaypoints[0].z + fltDistance;
        LOG("uber", "distance is " + fltDistance);
        LOG("uber", "start is " + locParentLocation);
        LOG("uber", "waypoint 0 is " + locWaypoints[0]);
        locWaypoints[1] = new location(locParentLocation);
        locWaypoints[1].x = locWaypoints[1].x + fltDistance;
        locWaypoints[1].z = locWaypoints[1].z + fltDistance;
        LOG("uber", "distance is " + fltDistance);
        LOG("uber", "start is " + locParentLocation);
        LOG("uber", "waypoint 1 is " + locWaypoints[1]);
        locWaypoints[2] = new location(locParentLocation);
        locWaypoints[2].x = locWaypoints[2].x + fltDistance;
        locWaypoints[2].z = locWaypoints[2].z - fltDistance;
        LOG("uber", "distance is " + fltDistance);
        LOG("uber", "start is " + locParentLocation);
        LOG("uber", "waypoint 2 is " + locWaypoints[2]);
        locWaypoints[3] = new location(locParentLocation);
        locWaypoints[3].x = locWaypoints[3].x - fltDistance;
        locWaypoints[3].z = locWaypoints[3].z - fltDistance;
        LOG("uber", "distance is " + fltDistance);
        LOG("uber", "start is " + locParentLocation);
        LOG("uber", "waypoint 3 is " + locWaypoints[3]);
        return locWaypoints;
    }
}
