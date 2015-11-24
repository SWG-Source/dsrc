package script.structure.municipal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.planetary_map;
import script.library.structure;
import script.library.city;
import script.library.cloninglib;
import script.library.regions;
import script.library.space_dungeon;
import script.library.utils;
import java.util.Enumeration;

public class cloning_base extends script.base_script
{
    public cloning_base()
    {
    }
    public static final String SCRIPT_CLONING_FACILITY = "structure.municipal.cloning_facility";
    public static final String VAR_NUM_RESPAWN_TUBES = "numRespawnTubes";
    public static final String VAR_CURRENT_RESPAWN_TUBE = "currentTube";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (player_structure.isCivic(self))
        {
            int city_id = getCityAtLocation(getLocation(self), 0);
            city.setCloneInfo(city_id, getLocation(self), cloninglib.getCloneSpawnLocation(self), self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        obj_id planet = getPlanetByName(getCurrentSceneName());
        if (isIdValid(planet))
        {
            dictionary params = new dictionary();
            params.put("id", self);
            messageTo(planet, "unregisterCloningFacility", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!requestCloningFacilityRegistration(self))
        {
            CustomerServiceLog("Cloning_Center", "The cloning center " + self + " registration reports a failure. Attempted to register cloning center(" + getTemplateName(self) + "/" + self + ") but this action failed.");
        }
        int numberOfRetry = 0;
        dictionary params = new dictionary();
        params.put("number_of_retry", numberOfRetry);
        messageTo(self, "retryInitializeCloningFacility", params, 120.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (player_structure.isCivic(self))
        {
            int city_id = getLocatedInCityId(self);
            city.setCloneInfo(city_id, null, null, null);
            return SCRIPT_CONTINUE;
        }
        obj_id planet = getPlanetByName(getCurrentSceneName());
        if (isIdValid(planet))
        {
            dictionary params = new dictionary();
            params.put("id", self);
            messageTo(planet, "unregisterCloningFacility", params, 1.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetRespawnLocation(obj_id self, modifiable_int tubeIndex) throws InterruptedException
    {
        deltadictionary scriptvars = self.getScriptVars();
        int numTubes = scriptvars.getInt(VAR_NUM_RESPAWN_TUBES);
        if (numTubes > 0)
        {
            int currentTube = scriptvars.getInt(VAR_CURRENT_RESPAWN_TUBE);
            tubeIndex.set(currentTube++);
            if (currentTube == numTubes)
            {
                currentTube = 0;
            }
            scriptvars.put(VAR_CURRENT_RESPAWN_TUBE, currentTube);
        }
        return SCRIPT_CONTINUE;
    }
    public int registerCloningFacility(obj_id self, dictionary params) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        dictionary facilityData = new dictionary();
        if (params.containsKey("facilityData"))
        {
            facilityData = params.getDictionary("facilityData");
        }
        else 
        {
            facilityData = dataTableGetRow(structure.DATATABLE_CLONING_FACILITY_RESPAWN, myTemplate);
        }
        String planetName = getCurrentSceneName();
        obj_id planet = getPlanetByName(planetName);
        if (isIdValid(planet))
        {
            location spawnLoc = cloninglib.getCloneSpawnLocation(getLocation(self), facilityData);
            if (spawnLoc != null)
            {
                if (regions.isInPvPRegion(self))
                {
                    CustomerServiceLog("Cloning_Center", "Attempted to register cloning center(" + getTemplateName(self) + "/" + self + ") but was within a PvP region.)");
                    return SCRIPT_CONTINUE;
                }
                int cloneType = 0;
                if (facilityData != null)
                {
                    cloneType = facilityData.getInt("CLONE_TYPE");
                }
                dictionary data = new dictionary();
                data.put("id", self);
                location facilityLoc = getLocation(self);
                data.put("loc", facilityLoc);
                data.put("respawn", spawnLoc);
                data.put("type", cloneType);
                String cloneName = getCloneName(cloneType, facilityData.getString("NAME"));
                if (cloneName == null)
                {
                    CustomerServiceLog("Cloning_Center", "Unable to determine facility name for Cloning Facility(" + getTemplateName(self) + "/" + self + ") at location(" + getLocation(self) + ") - Waiting...");
                    messageTo(self, "registerCloningFacility", params, 180.0f, false);
                    return SCRIPT_CONTINUE;
                }
                data.put("name", cloneName);
                obj_id pobId = null;
                if (space_dungeon.isSpaceDungeon(self) || hasObjVar(self, "isControllerObject"))
                {
                    pobId = self;
                }
                else 
                {
                    pobId = getTopMostContainer(self);
                    if (pobId == self)
                    {
                        pobId = obj_id.NULL_ID;
                    }
                }
                data.put("areaId", pobId);
                location facilityWorldLoc = getWorldLocation(self);
                String buildoutName = cloninglib.getBuildoutAreaName(facilityWorldLoc.x, facilityWorldLoc.z);
                dictionary cloneAreaList = cloninglib.getCloningAreas(planetName, buildoutName);
                CustomerServiceLog("Cloning_Center", "Registering Cloning Facility(" + getTemplateName(self) + "/" + self + ") at location(" + getLocation(self) + ") spawnLoc(" + spawnLoc + ") cloneType(" + cloneType + ")");
                int idx = 0;
                while (cloneAreaList.containsKey("scene_" + idx))
                {
                    String cloneScene = cloneAreaList.getString("scene_" + idx);
                    String cloneArea = cloneAreaList.getString("area_" + idx);
                    data.put("buildout", cloneArea);
                    obj_id scene = getPlanetByName(cloneScene);
                    if (isIdValid(scene))
                    {
                        messageTo(scene, "registerCloningFacility", data, 1.0f, false);
                        CustomerServiceLog("Cloning_Center", "Mapping Cloning Region(" + getTemplateName(self) + "/" + self + ") to " + cloneScene + "(" + cloneArea + ")");
                    }
                    else 
                    {
                        CustomerServiceLog("Cloning_Center", "Failed to map clone region(" + getTemplateName(self) + "/" + self + ") invalid scene name " + cloneScene);
                    }
                    idx++;
                }
            }
            else 
            {
                LOG("DESIGNER_FATAL", "************* registerCloningFacility ****************");
                LOG("DESIGNER_FATAL", "spawnLoc was returned as null & therefore aborted facility registration");
                LOG("DESIGNER_FATAL", "facility = " + self + " on planet = " + getCurrentSceneName());
                LOG("DESIGNER_FATAL", "facility location = " + (getLocation(self)).toString());
                LOG("DESIGNER_FATAL", "facility template = " + getTemplateName(self));
                CustomerServiceLog("Cloning_Center", "Attempted to register cloning center but spawn location was null: (" + getTemplateName(self) + "/" + self + ") failed to register on planet(" + getCurrentSceneName() + ")");
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            CustomerServiceLog("Cloning_Center", "Attempted to register cloning center(" + getTemplateName(self) + "/" + self + ") but could not find planet object for(" + getCurrentSceneName() + ")");
        }
        return SCRIPT_CONTINUE;
    }
    public String getCloneName(obj_id self) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        dictionary facilityData = dataTableGetRow(structure.DATATABLE_CLONING_FACILITY_RESPAWN, myTemplate);
        if (facilityData == null)
        {
            return "@base_player:clone_location_unknown";
        }
        return getCloneName(facilityData.getInt("CLONE_TYPE"), facilityData.getString("NAME"));
    }
    public String getCloneName(int cloneType, String nameOverride) throws InterruptedException
    {
        obj_id self = getSelf();
        String cloneName = "@base_player:clone_location_unknown";
        switch (cloneType)
        {
            case cloninglib.CLONE_TYPE_STANDARD:
            case cloninglib.CLONE_TYPE_PLAYER_CITY:
            case cloninglib.CLONE_TYPE_RESTRICTED:
            map_location cl = getPlanetaryMapLocation(self);
            if (cl != null)
            {
                cloneName = cl.getLocationName();
            }
            else if (nameOverride != null && !nameOverride.equals(""))
            {
                cloneName = nameOverride;
            }
            else 
            {
                cloneName = null;
            }
            break;
            case cloninglib.CLONE_TYPE_JEDI_ONLY:
            location spawnLoc = cloninglib.getCloneSpawnLocation(self);
            cloneName = "@base_player:clone_location_force_shrine (" + (int)spawnLoc.x + ", " + (int)spawnLoc.z + ")";
            break;
            case cloninglib.CLONE_TYPE_LIGHT_JEDI_ONLY:
            case cloninglib.CLONE_TYPE_DARK_JEDI_ONLY:
            cloneName = "@base_player:clone_location_jedi_enclave";
            break;
            case cloninglib.CLONE_TYPE_FACTION_IMPERIAL:
            cloneName = nameOverride;
            break;
            case cloninglib.CLONE_TYPE_FACTION_REBEL:
            cloneName = nameOverride;
            break;
            case cloninglib.CLONE_TYPE_PVP_REGION_ADVANCED_IMPERIAL:
            cloneName = nameOverride;
            break;
            case cloninglib.CLONE_TYPE_PVP_REGION_ADVANCED_REBEL:
            cloneName = nameOverride;
            break;
            case cloninglib.CLONE_TYPE_CAMP:
            obj_id parent = getObjIdObjVar(self, "theater.parent");
            if (isIdValid(parent))
            {
                String name = getName(parent);
                if (name != null && !name.equals(""))
                {
                    cloneName = name;
                }
                else 
                {
                    cloneName = nameOverride;
                }
            }
            else 
            {
                cloneName = nameOverride;
            }
            break;
            case cloninglib.CLONE_TYPE_PRIVATE_INSTANCE:
            cloneName = nameOverride;
            break;
        }
        return cloneName;
    }
    public int requestCloningData(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id requester = params.getObjId("requester");
        dictionary data = new dictionary();
        String cloneName = getCloneName(self);
        location facilityLoc = getLocation(self);
        location spawnLoc = cloninglib.getCloneSpawnLocation(self);
        data.put("faciltyId", self);
        data.put("cloneName", cloneName);
        data.put("facilityLoc", facilityLoc);
        data.put("spawnLoc", spawnLoc);
        messageTo(requester, "handleCloningDataUpdate", data, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int retryInitializeCloningFacility(obj_id self, dictionary params) throws InterruptedException
    {
        int numberOfRetry = params.getInt("number_of_retry");
        if (numberOfRetry >= 5)
        {
            return SCRIPT_CONTINUE;
        }
        String planetName = getCurrentSceneName();
        if (planetName == null || planetName.length() <= 0)
        {
            CustomerServiceLog("Cloning_Center", "The cloning center " + self + " registration reports a failure. Attempted to register cloning center but could not get a valid planet name! Aborting registration.");
            return SCRIPT_CONTINUE;
        }
        obj_id planet = getPlanetByName(planetName);
        if (!isValidId(planet))
        {
            CustomerServiceLog("Cloning_Center", "The cloning center " + self + " registration reports a failure. Attempted to register cloning center but could not get a valid planet OID for planet: " + planetName + ". Aborting registration.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] idList = utils.getObjIdArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_ID);
        if (idList != null && idList.length > 0)
        {
            for (int i = 0; i < idList.length - 1; i++)
            {
                if (idList[i] != self)
                {
                    continue;
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (!requestCloningFacilityRegistration(self))
        {
            CustomerServiceLog("Cloning_Center", "The cloning center " + self + " registration reports a failure. Attempted to register cloning center(" + getTemplateName(self) + "/" + self + ") but this action failed.");
        }
        ++numberOfRetry;
        if (numberOfRetry < 5)
        {
            params.put("number_of_retry", numberOfRetry);
            messageTo(self, "retryInitializeCloningFacility", params, 120.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean requestCloningFacilityRegistration(obj_id cloningFacility) throws InterruptedException
    {
        if (!isValidId(cloningFacility))
        {
            return false;
        }
        if (player_structure.isCivic(cloningFacility))
        {
            planetary_map.addMapLocation(cloningFacility);
            int city_id = getCityAtLocation(getLocation(cloningFacility), 0);
            if (city_id < 0)
            {
                CustomerServiceLog("Cloning_Center", "The cloning center (which is  " + cloningFacility + ") could not continue the planet registration process because the city_id was invalid. Registeration of cloning center(" + getTemplateName(cloningFacility) + "/" + cloningFacility + ") aborted.");
                return false;
            }
            city.setCloneInfo(city_id, getLocation(cloningFacility), cloninglib.getCloneSpawnLocation(cloningFacility), cloningFacility);
        }
        String myTemplate = getTemplateName(cloningFacility);
        if (myTemplate == null || myTemplate.length() <= 0)
        {
            CustomerServiceLog("Cloning_Center", "The cloning center which is  " + cloningFacility + " could not continue the planet registration process because the object template for the cloning center was invalid. Registeration of cloning center(" + getTemplateName(cloningFacility) + "/" + cloningFacility + ") aborted.");
            return false;
        }
        dictionary facilityData = dataTableGetRow(structure.DATATABLE_CLONING_FACILITY_RESPAWN, myTemplate);
        if (facilityData == null)
        {
            CustomerServiceLog("Cloning_Center", "The cloning center (which is  " + cloningFacility + " of template: " + myTemplate + ") was not found in the datatables/structure/municipal/cloning_facility_respawn.iff table. Registeration aborted.");
            return false;
        }
        dictionary dict = new dictionary();
        dict.put("facilityData", facilityData);
        int numTubes = 0;
        if (facilityData != null)
        {
            numTubes = facilityData.getInt(structure.DATATABLE_COL_TUBES);
        }
        if (numTubes > 0)
        {
            deltadictionary scriptvars = cloningFacility.getScriptVars();
            scriptvars.put(VAR_NUM_RESPAWN_TUBES, numTubes);
            scriptvars.put(VAR_CURRENT_RESPAWN_TUBE, 0);
        }
        messageTo(cloningFacility, "registerCloningFacility", dict, 60.0f, false);
        return true;
    }
}
