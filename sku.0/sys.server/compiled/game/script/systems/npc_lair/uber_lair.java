package script.systems.npc_lair;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.ai_lib;
import script.library.factions;
import script.library.regions;
import script.library.uberlair;

public class uber_lair extends script.theme_park.poi.base
{
    public uber_lair()
    {
    }
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        initializePoi(self);
        messageTo(self, "handleNpcLairDecay", null, 604800.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        initializePoi(self);
        return SCRIPT_CONTINUE;
    }
    public void initializePoi(obj_id poiBaseObject) throws InterruptedException
    {
        LOG("uber", "INITIALIZEPOI FUNCTION");
        String lairType = getStringObjVar(poiBaseObject, "spawning.lairType");
        String strFaction = utils.getFactionSubString(lairType);
        if (strFaction != null)
        {
            setObjVar(poiBaseObject, factions.FACTION, strFaction);
        }
        createTheater(poiBaseObject, lairType);
    }
    public int handleTargetDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        poiComplete(POI_SUCCESS);
        return SCRIPT_CONTINUE;
    }
    public int handleTheaterComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("uber", "HANDLETHEATERCOMPLETE MESSAGEHANDLER ");
        obj_id objTheater = params.getObjId("master");
        if (objTheater == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, factions.FACTION))
        {
            String strFaction = getStringObjVar(self, factions.FACTION);
            obj_id[] objChildren = getObjIdArrayObjVar(objTheater, "theater.children");
            if (objChildren != null)
            {
                for (int intI = 0; intI < objChildren.length; intI++)
                {
                    factions.setFaction(objChildren[intI], strFaction);
                }
            }
        }
        setObjVar(self, "theater.objTheater", objTheater);
        obj_id[] objLargeMobSpawners = getObjIdArrayObjVar(objTheater, "theater.objLargeMobSpawners");
        uberlair.spawnBossMobiles(self, objLargeMobSpawners);
        messageTo(self, "spawnMobiles", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public void createTheater(obj_id poiBaseObject, String lairType) throws InterruptedException
    {
        LOG("uber", "CREATE THEATER FUNCTION");
        String buildingToSpawn = null;
        String lairDatatable = "datatables/uberlair/" + lairType + ".iff";
        String[] buildings = dataTableGetStringColumnNoDefaults(lairDatatable, "strTheater");
        if (buildings == null)
        {
            LOG("uberlair", "no building");
            return;
        }
        if (buildings.length == 0)
        {
            LOG("uberlair", "no building");
            return;
        }
        buildingToSpawn = buildings[rand(0, buildings.length - 1)];
        setObjVar(poiBaseObject, "spawning.buildingType", buildingToSpawn);
        obj_id mainBuilding = poiCreateObject(buildingToSpawn, 0f, 0f);
        String[] strOverLoadRegions = dataTableGetStringColumnNoDefaults(lairDatatable, "strOverloadRegions");
        float[] fltOverLoadRegionSizes = dataTableGetFloatColumnNoDefaults(lairDatatable, "fltRegionSizes");
        int[] intMinRegionDifficulties = dataTableGetIntColumnNoDefaults(lairDatatable, "intMinRegionDifficulty");
        int[] intMaxRegionDifficulties = dataTableGetIntColumnNoDefaults(lairDatatable, "intMaxRegionDifficulty");
        if (strOverLoadRegions.length > 0)
        {
            if (!hasObjVar(poiBaseObject, "uberlair.strRegionName"))
            {
                int intRoll = rand(0, strOverLoadRegions.length - 1);
                String strRegionName = strOverLoadRegions[intRoll];
                strRegionName = strRegionName + "-" + poiBaseObject.toString();
                float fltRegionSize = fltOverLoadRegionSizes[intRoll];
                int intMinRegionDifficulty = intMinRegionDifficulties[intRoll];
                int intMaxRegionDifficulty = intMaxRegionDifficulties[intRoll];
                createCircleRegion(getLocation(poiBaseObject), fltRegionSize, strRegionName, regions.PVP_REGION_TYPE_NORMAL, regions.BUILD_TRUE, regions.MUNI_FALSE, regions.GEO_OVERLOAD, intMinRegionDifficulty, intMaxRegionDifficulty, regions.SPAWN_TRUE, regions.MISSION_NONE, false, false);
                setObjVar(poiBaseObject, "uberlair.strRegionName", strRegionName);
            }
        }
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        String strName = getStringObjVar(self, "uberlair.strRegionName");
        location locTest = getLocation(self);
        String strPlanet = locTest.area;
        region rgnOverLoadRegion = getRegion(strPlanet, strName);
        if (rgnOverLoadRegion == null)
        {
            LOG("DESIGNER_FATAL", "Missing region of name " + strName + " on planet " + strPlanet);
            return SCRIPT_CONTINUE;
        }
        deleteRegion(rgnOverLoadRegion);
        return SCRIPT_CONTINUE;
    }
    public int mobDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        location locSpawnLocation = params.getLocation("locSpawnLocation");
        String strType = params.getString("strType");
        String strScript = params.getString("strScript");
        LOG("uber", "params is " + params.toString());
        uberlair.respawnMobile(self, locSpawnLocation, strType, strScript);
        return SCRIPT_CONTINUE;
    }
    public int spawnMobiles(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("uber", "SPAWN_NPC_LAIR_MOBILS MESSAGEHANDLER");
        obj_id objTheater = getObjIdObjVar(self, "theater.objTheater");
        obj_id[] objMobSpawners = getObjIdArrayObjVar(objTheater, "theater.objMobSpawners");
        uberlair.spawnNpcLairMobiles(self, objMobSpawners);
        return SCRIPT_CONTINUE;
    }
    public int spawnFormations(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("uber", "SPAWN_formations MESSAGEHANDLER");
        uberlair.createFormations(self);
        return SCRIPT_CONTINUE;
    }
}
