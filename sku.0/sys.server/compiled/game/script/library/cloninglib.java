package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.instance;
import script.library.pclib;
import script.library.regions;
import script.library.space_dungeon;

public class cloninglib extends script.base_script
{
    public cloninglib()
    {
    }
    public static final String SCRIPT_CLONING_FACILITY = "structure.municipal.cloning_facility";
    public static final String SCRVAR_CLONE_COUPON = "clone_coupon";
    public static final String DATATABLE_CLONE_SPAWN = "datatables/structure/municipal/cloning_facility_respawn.iff";
    public static final String COL_SPAWN_STRUCTURE = "STRUCTURE";
    public static final String COL_SPAWN_X = "X";
    public static final String COL_SPAWN_Y = "Y";
    public static final String COL_SPAWN_Z = "Z";
    public static final String COL_SPAWN_CELLNAME = "CELL";
    public static final String DATATABLE_CLONE_MAPPING = "datatables/cloning/clone_mapping.iff";
    public static final String DATATABLE_CLONING_COST = "datatables/cloning/clone_cost.iff";
    public static final string_id SID_BIND_SUCCESSFUL = new string_id("base_player", "clone_success");
    public static final string_id SID_BAD_CLONE_LOCATION = new string_id("error_message", "bad_clone_location");
    public static final string_id SID_NO_CLONING_FACILITIES = new string_id("error_message", "no_cloning_facilities");
    public static final string_id SID_RESPAWN_CURRENT_LOCATION = new string_id("error_message", "respawn_current_location");
    public static final string_id SID_RESPAWN_CLOSEST_FACILITY = new string_id("error_message", "respawn_closest_facility");
    public static final string_id SID_RESPAWN_JEDI_FAILED = new string_id("error_message", "respawn_jedi_failed");
    public static final string_id SID_NSF_CLONE = new string_id("error_message", "nsf_clone");
    public static final string_id SID_NSF_CLONE1 = new string_id("error_message", "nsf_clone1");
    public static final String VAR_PLANET_CLONE_ID = "cloningFacilities.id";
    public static final String VAR_PLANET_CLONE_NAME = "cloningFacilities.name";
    public static final String VAR_PLANET_CLONE_AREA = "cloningFacilities.area";
    public static final String VAR_PLANET_CLONE_AREA_ID = "cloningFacilities.areaId";
    public static final String VAR_PLANET_CLONE_LOC = "cloningFacilities.loc";
    public static final String VAR_PLANET_CLONE_RESPAWN = "cloningFacilities.respawn";
    public static final String VAR_PLANET_CLONE_TYPE = "cloningFacilities.type";
    public static final int CLONE_TYPE_STANDARD = 0;
    public static final int CLONE_TYPE_PLAYER_CITY = 1;
    public static final int CLONE_TYPE_JEDI_ONLY = 2;
    public static final int CLONE_TYPE_LIGHT_JEDI_ONLY = 3;
    public static final int CLONE_TYPE_DARK_JEDI_ONLY = 4;
    public static final int CLONE_TYPE_RESTRICTED = 5;
    public static final int CLONE_TYPE_FACTION_IMPERIAL = 6;
    public static final int CLONE_TYPE_FACTION_REBEL = 7;
    public static final int CLONE_TYPE_PVP_REGION_ADVANCED_IMPERIAL = 8;
    public static final int CLONE_TYPE_PVP_REGION_ADVANCED_REBEL = 9;
    public static final int CLONE_TYPE_CAMP = 10;
    public static final int CLONE_TYPE_PRIVATE_INSTANCE = 11;
    public static final String VAR_BIND_BASE = "bind";
    public static final String VAR_BIND_FACILITY = "bind.facility";
    public static final String VAR_BIND_LOCATION = "bind.location";
    public static final String VAR_BIND_SPAWN_LOC = "bind.spawn";
    public static final String VAR_BIND_CITY_NAME = "bind.city_name";
    public static final String VAR_BIND_FACILITY_TEMPLATE = "bind.facility_template";
    public static final String VAR_BIND_FACILITY_CELL = "bind.cell";
    public static final int CLONE_COST_BASE = 1000;
    public static final int CLONE_DAMAGE_LOW = 2;
    public static final int CLONE_DAMAGE_HIGH = 10;
    public static final float DEFAULT_REPAIR_RATIO = 10.0f;
    public static dictionary getCloningAreas(String planet, String area) throws InterruptedException
    {
        dictionary data = new dictionary();
        String[] sceneNames = dataTableGetStringColumn(DATATABLE_CLONE_MAPPING, "clone_scene");
        String[] areaNames = dataTableGetStringColumn(DATATABLE_CLONE_MAPPING, "clone_area");
        int idx = 0;
        for (int i = 0; i < sceneNames.length; i++)
        {
            if (sceneNames[i].equals(planet))
            {
                String cloneScene = dataTableGetString(DATATABLE_CLONE_MAPPING, i, "scene");
                String cloneArea = dataTableGetString(DATATABLE_CLONE_MAPPING, i, "area");
                if (cloneArea == null)
                {
                    cloneArea = "";
                }
                if (areaNames[i] == null || areaNames[i].equals("") || areaNames[i].equals(area))
                {
                    data.put("scene_" + idx, cloneScene);
                    data.put("area_" + idx, cloneArea);
                    idx++;
                }
            }
        }
        return data;
    }
    public static location getCloneSpawnLocation(obj_id facility) throws InterruptedException
    {
        return getCloneSpawnLocation(getLocation(facility), facility);
    }
    public static location getCloneSpawnLocation(location baseLoc, obj_id facility) throws InterruptedException
    {
        String facilityTemplate = getTemplateName(facility);
        if (facilityTemplate == null)
        {
            LOG("DESIGNER_FATAL", "WARNING: getCloneSpawnLocation(1) no template for facility " + facility);
            return null;
        }
        dictionary facilityData = new dictionary();
        if (dataTableOpen(DATATABLE_CLONE_SPAWN))
        {
            String[] templates = dataTableGetStringColumn(DATATABLE_CLONE_SPAWN, COL_SPAWN_STRUCTURE);
            int pos = utils.getElementPositionInArray(templates, facilityTemplate);
            if (pos > -1)
            {
                facilityData = dataTableGetRow(DATATABLE_CLONE_SPAWN, pos);
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
        return getCloneSpawnLocation(baseLoc, facilityData);
    }
    public static location getCloneSpawnLocation(location baseLoc, dictionary facilityData) throws InterruptedException
    {
        float x = facilityData.getFloat(COL_SPAWN_X);
        float y = facilityData.getFloat(COL_SPAWN_Y);
        float z = facilityData.getFloat(COL_SPAWN_Z);
        String cellName = facilityData.getString(COL_SPAWN_CELLNAME);
        if (cellName == null || cellName.equals("") || cellName.equals("none"))
        {
            x += baseLoc.x;
            y += baseLoc.y;
            z += baseLoc.z;
            return new location(x, y, z, baseLoc.area);
        }
        else 
        {
            obj_id cloner = getTopMostContainer(getSelf());
            if (!isIdValid(cloner) || !exists(cloner))
            {
                cloner = getSelf();
            }
            obj_id cellId = getCellId(cloner, cellName);
            if (cellId != null)
            {
                return new location(x, y, z, baseLoc.area, cellId);
            }
        }
        return null;
    }
    public static String getSpawnCellName(obj_id facility) throws InterruptedException
    {
        return getSpawnCellName(getTemplateName(facility));
    }
    public static String getSpawnCellName(String facilityTemplate) throws InterruptedException
    {
        if (facilityTemplate == null)
        {
            return null;
        }
        if (dataTableOpen(DATATABLE_CLONE_SPAWN))
        {
            String[] templates = dataTableGetStringColumn(DATATABLE_CLONE_SPAWN, COL_SPAWN_STRUCTURE);
            int pos = utils.getElementPositionInArray(templates, facilityTemplate);
            if (pos > -1)
            {
                dictionary d = dataTableGetRow(DATATABLE_CLONE_SPAWN, pos);
                return d.getString(COL_SPAWN_CELLNAME);
            }
            else 
            {
                debugServerConsoleMsg(null, "WARNING: getCloneSpawnLocation cannot find template " + facilityTemplate + " in datatable " + DATATABLE_CLONE_SPAWN);
                return null;
            }
        }
        else 
        {
            debugServerConsoleMsg(null, "WARNING: getCloneSpawnLocation cannot find datatable " + DATATABLE_CLONE_SPAWN);
            return null;
        }
    }
    public static String getCloneFacilityName(obj_id facility) throws InterruptedException
    {
        String planetName = getCurrentSceneName();
        obj_id planet = getPlanetByName(planetName);
        obj_id[] idList = utils.getObjIdArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_ID);
        String[] nameList = utils.getStringArrayScriptVar(planet, cloninglib.VAR_PLANET_CLONE_NAME);
        int pos = utils.getElementPositionInArray(idList, facility);
        if (pos >= 0)
        {
            return nameList[pos];
        }
        return "@base_player:clone_location_unknown";
    }
    public static boolean canUseBindFacility(String planet, String area) throws InterruptedException
    {
        dictionary data = new dictionary();
        String[] sceneNames = dataTableGetStringColumn(DATATABLE_CLONE_MAPPING, "clone_scene");
        String[] areaNames = dataTableGetStringColumn(DATATABLE_CLONE_MAPPING, "clone_area");
        int idx = 0;
        for (int i = 0; i < sceneNames.length; i++)
        {
            if (sceneNames[i].equals(planet))
            {
                if (areaNames[i] == null || areaNames[i].equals("") || areaNames[i].equals(area))
                {
                    int restrict = dataTableGetInt(DATATABLE_CLONE_MAPPING, i, "restrict_bind_facility");
                    if (restrict != 0)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean requestBind(obj_id player, obj_id terminal) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        if (utils.hasScriptVar(player, SCRVAR_CLONE_COUPON))
        {
            dictionary webster = new dictionary();
            webster.put(money.DICT_PLAYER_ID, player);
            messageTo(terminal, "handleRequestedClone", webster, 1, false);
            return true;
        }
        int cost = getRegisterCloneCost(player);
        int total = getTotalMoney(player);
        obj_id facility = structure.getContainingBuilding(player);
        if (!isIdValid(facility) || !hasScript(facility, cloninglib.SCRIPT_CLONING_FACILITY))
        {
            sendSystemMessage(player, cloninglib.SID_BAD_CLONE_LOCATION);
            return false;
        }
        if (verifyFundsForCloning(player))
        {
            return money.pay(player, terminal, cost, "handleRequestedClone", null, true);
        }
        return false;
    }
    public static boolean setBind(obj_id player, obj_id cloningFacility) throws InterruptedException
    {
        if ((player == null) || (!isPlayer(player)))
        {
            return false;
        }
        if (!isIdValid(cloningFacility) || !hasScript(cloningFacility, SCRIPT_CLONING_FACILITY))
        {
            sendSystemMessage(player, SID_BAD_CLONE_LOCATION);
            return false;
        }
        location cloneLoc = getLocation(cloningFacility);
        location spawnLoc = getCloneSpawnLocation(cloningFacility);
        String cloneName = getCloneFacilityName(cloningFacility);
        setObjVar(player, VAR_BIND_FACILITY, cloningFacility);
        setObjVar(player, VAR_BIND_CITY_NAME, cloneName);
        setObjVar(player, VAR_BIND_LOCATION, cloneLoc);
        setObjVar(player, VAR_BIND_SPAWN_LOC, spawnLoc);
        sendSystemMessage(player, SID_BIND_SUCCESSFUL);
        return true;
    }
    public static boolean verifyFundsForCloning(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int cost = getRegisterCloneCost(player);
        int total = getTotalMoney(player);
        if (total < cost)
        {
            int diff = cost - total;
            if (diff == 1)
            {
                sendSystemMessage(player, SID_NSF_CLONE1);
            }
            else 
            {
                prose_package ppNsf = prose.getPackage(SID_NSF_CLONE, diff);
                sendSystemMessageProse(player, ppNsf);
            }
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static int getRegisterCloneCost(obj_id player) throws InterruptedException
    {
        location playerLoc = getWorldLocation(player);
        String planetName = playerLoc.area;
        String areaName = getBuildoutAreaName(playerLoc.x, playerLoc.z);
        String cityName = planetary_map.getCityRegionName(playerLoc);
        if (cityName != null && cityName.startsWith("@"))
        {
            string_id sidCity = utils.unpackString(cityName);
            if (sidCity != null)
            {
                cityName = sidCity.getAsciiId();
            }
        }
        int cost = CLONE_COST_BASE;
        int row = -1;
        if (cityName != null)
        {
            row = dataTableSearchColumnForString(cityName, 0, DATATABLE_CLONING_COST);
            if (row > -1)
            {
                cost = dataTableGetInt(DATATABLE_CLONING_COST, row, "clone_cost");
                return cost;
            }
        }
        if (areaName != null)
        {
            row = dataTableSearchColumnForString(areaName, 0, DATATABLE_CLONING_COST);
            if (row > -1)
            {
                cost = dataTableGetInt(DATATABLE_CLONING_COST, row, "clone_cost");
                return cost;
            }
        }
        if (planetName != null)
        {
            row = dataTableSearchColumnForString(planetName, 0, DATATABLE_CLONING_COST);
            if (row > -1)
            {
                cost = dataTableGetInt(DATATABLE_CLONING_COST, row, "clone_cost");
                return cost;
            }
        }
        return cost;
    }
    public static Vector getAvailableCloningFacilities(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            return null;
        }
        location playerLoc = pclib.getEffectiveDeathLocation(player);
        String planetName = playerLoc.area;
        if (planetName == null)
        {
            return null;
        }
        obj_id planet = getPlanetByName(planetName);
        if (!isIdValid(planet))
        {
            return null;
        }
        float x = playerLoc.x;
        float z = playerLoc.z;
        obj_id container = getTopMostContainer(player);
        location worldLoc = getWorldLocation(container);
        String area = getBuildoutAreaName(worldLoc.x, worldLoc.z);
        if (area == null)
        {
            area = "";
        }
        Vector idList = utils.getResizeableObjIdArrayScriptVar(planet, VAR_PLANET_CLONE_ID);
        Vector nameList = utils.getResizeableStringArrayScriptVar(planet, VAR_PLANET_CLONE_NAME);
        Vector areaList = utils.getResizeableStringArrayScriptVar(planet, VAR_PLANET_CLONE_AREA);
        Vector areaIdList = utils.getResizeableObjIdArrayScriptVar(planet, VAR_PLANET_CLONE_AREA_ID);
        Vector locList = utils.getResizeableLocationArrayScriptVar(planet, VAR_PLANET_CLONE_LOC);
        Vector respawnList = utils.getResizeableLocationArrayScriptVar(planet, VAR_PLANET_CLONE_RESPAWN);
        Vector cloneTypeList = utils.getResizeableIntArrayScriptVar(planet, VAR_PLANET_CLONE_TYPE);
        if (idList == null || nameList == null || areaList == null || areaIdList == null || locList == null || respawnList == null || cloneTypeList == null || idList.size() == 0 || nameList.size() == 0 || areaList.size() == 0 || areaIdList.size() == 0 || locList.size() == 0 || respawnList.size() == 0 || cloneTypeList.size() == 0)
        {
            sendSystemMessage(player, SID_NO_CLONING_FACILITIES);
            return null;
        }
        Vector facilityList = new Vector();
        obj_id registeredFacility = getObjIdObjVar(player, VAR_BIND_FACILITY);
        obj_id playerFacility = null;
        String playerFacilityName = null;
        location playerFacilityLoc = null;
        location playerFacilitySpawnLoc = null;
        float playerCityDist = Float.MAX_VALUE;
        obj_id factionFacility = null;
        String factionFacilityName = null;
        location factionFacilityLoc = null;
        location factionFacilitySpawnLoc = null;
        float factionFacilityDist = Float.MAX_VALUE;
        obj_id pvpAdvancedFacility = null;
        String pvpAdvancedFacilityName = null;
        location pvpAdvancedFacilityLoc = null;
        location pvpAdvancedFacilitySpawnLoc = null;
        float pvpAdvancedFacilityDist = Float.MAX_VALUE;
        obj_id playerCamp = null;
        String playerCampName = null;
        location playerCampLoc = null;
        location playerCampSpawnLoc = null;
        float playerCampDist = 500f;
        obj_id instance_controller = instance.getAreaInstanceController(player);
        obj_id playerPob = space_dungeon.getDungeonIdForPlayer(player);
        if (!isIdValid(playerPob))
        {
            playerPob = getTopMostContainer(player);
        }
        if (playerPob == player)
        {
            playerPob = obj_id.NULL_ID;
        }
        for (int i = 0; i < idList.size(); i++)
        {
            if (idList.size() <= i || nameList.size() <= i || areaList.size() <= i || areaIdList.size() <= i || locList.size() <= i || respawnList.size() <= i || cloneTypeList.size() <= i)
            {
                break;
            }
            if (((String)areaList.get(i)) != null && area != null)
            {
                if (!((String)areaList.get(i)).equals("") && !((String)areaList.get(i)).equals(area))
                {
                    continue;
                }
                if (isIdValid(playerPob) && isIdValid(((obj_id)areaIdList.get(i))) && playerPob != ((obj_id)areaIdList.get(i)))
                {
                    continue;
                }
            }
            if (!isIdValid(((obj_id)idList.get(i))))
            {
                continue;
            }
            float dist = getDistance(worldLoc, ((location)locList.get(i)));
            if (dist == -1)
            {
                dist = Float.MAX_VALUE;
            }
            if (((obj_id)idList.get(i)) == registeredFacility)
            {
                dist = -1;
            }
            region[] deathLocRegions = new region[0];
            switch (((Integer)cloneTypeList.get(i)).intValue())
            {
                case CLONE_TYPE_STANDARD:
                facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                break;
                case CLONE_TYPE_PLAYER_CITY:
                if (dist < playerCityDist && !city.isCityBanned(player, ((obj_id)idList.get(i))))
                {
                    playerFacility = ((obj_id)idList.get(i));
                    playerFacilityName = ((String)nameList.get(i));
                    playerFacilityLoc = (location)((location)locList.get(i)).clone();
                    playerFacilitySpawnLoc = (location)((location)respawnList.get(i)).clone();
                    playerCityDist = dist;
                }
                break;
                case CLONE_TYPE_JEDI_ONLY:
                if (isJedi(player))
                {
                    facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                }
                break;
                case CLONE_TYPE_LIGHT_JEDI_ONLY:
                if (isJedi(player) && hasSkill(player, "force_rank_light_novice"))
                {
                    facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                }
                break;
                case CLONE_TYPE_DARK_JEDI_ONLY:
                if (isJedi(player) && hasSkill(player, "force_rank_dark_novice"))
                {
                    facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                }
                break;
                case CLONE_TYPE_RESTRICTED:
                if (isIdValid(playerPob) && playerPob == ((obj_id)areaIdList.get(i)))
                {
                    facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                }
                break;
                case CLONE_TYPE_FACTION_IMPERIAL:
                if (factions.isImperial(player))
                {
                    if (dist < factionFacilityDist)
                    {
                        factionFacility = ((obj_id)idList.get(i));
                        factionFacilityName = ((String)nameList.get(i));
                        factionFacilityLoc = (location)((location)locList.get(i)).clone();
                        factionFacilitySpawnLoc = (location)((location)respawnList.get(i)).clone();
                        factionFacilityDist = dist;
                    }
                }
                break;
                case CLONE_TYPE_FACTION_REBEL:
                if (factions.isRebel(player))
                {
                    if (dist < factionFacilityDist)
                    {
                        factionFacility = ((obj_id)idList.get(i));
                        factionFacilityName = ((String)nameList.get(i));
                        factionFacilityLoc = (location)((location)locList.get(i)).clone();
                        factionFacilitySpawnLoc = (location)((location)respawnList.get(i)).clone();
                        factionFacilityDist = dist;
                    }
                }
                break;
                case CLONE_TYPE_PVP_REGION_ADVANCED_IMPERIAL:
                if (!factions.isImperial(player))
                {
                    break;
                }
                deathLocRegions = getRegionsWithPvPAtPoint(worldLoc, regions.PVP_REGION_TYPE_ADVANCED);
                if (deathLocRegions == null || deathLocRegions.length == 0)
                {
                    break;
                }
                if (dist < pvpAdvancedFacilityDist)
                {
                    pvpAdvancedFacility = ((obj_id)idList.get(i));
                    pvpAdvancedFacilityName = ((String)nameList.get(i));
                    pvpAdvancedFacilityLoc = (location)((location)locList.get(i)).clone();
                    pvpAdvancedFacilitySpawnLoc = (location)((location)respawnList.get(i)).clone();
                    pvpAdvancedFacilityDist = dist;
                }
                break;
                case CLONE_TYPE_PVP_REGION_ADVANCED_REBEL:
                if (!factions.isRebel(player))
                {
                    break;
                }
                deathLocRegions = getRegionsWithPvPAtPoint(worldLoc, regions.PVP_REGION_TYPE_ADVANCED);
                if (deathLocRegions == null || deathLocRegions.length == 0)
                {
                    break;
                }
                if (dist < pvpAdvancedFacilityDist)
                {
                    pvpAdvancedFacility = ((obj_id)idList.get(i));
                    pvpAdvancedFacilityName = ((String)nameList.get(i));
                    pvpAdvancedFacilityLoc = (location)((location)locList.get(i)).clone();
                    pvpAdvancedFacilitySpawnLoc = (location)((location)respawnList.get(i)).clone();
                    pvpAdvancedFacilityDist = dist;
                }
                break;
                case CLONE_TYPE_CAMP:
                if (dist < playerCampDist)
                {
                    playerCamp = ((obj_id)idList.get(i));
                    playerCampName = ((String)nameList.get(i));
                    playerCampLoc = (location)((location)locList.get(i)).clone();
                    playerCampSpawnLoc = (location)((location)respawnList.get(i)).clone();
                    playerCampDist = dist;
                }
                break;
                case CLONE_TYPE_PRIVATE_INSTANCE:
                if (((obj_id)idList.get(i)) == instance_controller)
                {
                    facilityList = addAvailableCloningFacility(facilityList, ((obj_id)idList.get(i)), ((location)locList.get(i)), ((location)respawnList.get(i)), ((String)nameList.get(i)), dist);
                }
                break;
                default:
                break;
            }
        }
        if (playerFacility != null)
        {
            facilityList = addAvailableCloningFacility(facilityList, playerFacility, playerFacilityLoc, playerFacilitySpawnLoc, playerFacilityName, playerCityDist);
        }
        if (factionFacility != null)
        {
            facilityList = addAvailableCloningFacility(facilityList, factionFacility, factionFacilityLoc, factionFacilitySpawnLoc, factionFacilityName, factionFacilityDist);
        }
        if (pvpAdvancedFacility != null)
        {
            facilityList = addAvailableCloningFacility(facilityList, pvpAdvancedFacility, pvpAdvancedFacilityLoc, pvpAdvancedFacilitySpawnLoc, pvpAdvancedFacilityName, pvpAdvancedFacilityDist);
        }
        if (playerCamp != null)
        {
            facilityList = addAvailableCloningFacility(facilityList, playerCamp, playerCampLoc, playerCampSpawnLoc, playerCampName, playerCampDist);
        }
        return facilityList;
    }
    public static Vector addAvailableCloningFacility(Vector facilityList, obj_id facility, location facilityLoc, location spawnLoc, String name, float dist) throws InterruptedException
    {
        dictionary facilityData = new dictionary();
        facilityData.put("faciltyId", facility);
        facilityData.put("cloneName", name);
        facilityData.put("facilityLoc", facilityLoc);
        facilityData.put("spawnLoc", spawnLoc);
        facilityData.put("distance", dist);
        if (facilityList.size() < 1)
        {
            facilityList.add(facilityData);
        }
        else 
        {
            for (int i = 0; i < facilityList.size(); i++)
            {
                dictionary compareData = (dictionary)(facilityList.get(i));
                float compareDist = compareData.getFloat("distance");
                if (dist < compareDist)
                {
                    facilityList.add(i, facilityData);
                    break;
                }
                if (i == facilityList.size() - 1)
                {
                    facilityList.add(facilityData);
                    break;
                }
            }
        }
        return facilityList;
    }
    public static boolean isDamagedOnClone(obj_id player, obj_id item) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(item))
        {
            return false;
        }
        if (pclib.isContainedByPlayer(player, item) && !isUninsurable(item))
        {
            if (isDamagedOnCloneGOT(getGameObjectType(item)))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isDamagedOnCloneGOT(int got) throws InterruptedException
    {
        if (isGameObjectTypeOf(got, GOT_armor) || isGameObjectTypeOf(got, GOT_clothing) || isGameObjectTypeOf(got, GOT_weapon) || isGameObjectTypeOf(got, GOT_tool) || isGameObjectTypeOf(got, GOT_jewelry) || isGameObjectTypeOf(got, GOT_cybernetic))
        {
            return true;
        }
        return false;
    }
    public static void damageItemsOnClone(obj_id player, int damage) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        float percent = damage / 100f;
        obj_id[] eq = getInventoryAndEquipment(player);
        if (eq == null || eq.length == 0)
        {
            return;
        }
        for (int i = 0; i < eq.length; i++)
        {
            if (isIdValid(eq[i]) && !isAutoInsured(eq[i]) && isDamagedOnCloneGOT(getGameObjectType(eq[i])))
            {
                pclib.damageAndDecayItem(eq[i], percent);
            }
        }
    }
    public static obj_id[] getAllRepairItems(obj_id player) throws InterruptedException
    {
        Vector repairList = new Vector();
        repairList.setSize(0);
        obj_id[] eq = getInventoryAndEquipment(player);
        if (eq == null || eq.length == 0)
        {
            return null;
        }
        for (int i = 0; i < eq.length; i++)
        {
            int damage = getItemDamageAmount(eq[i]);
            if (damage > 0)
            {
                repairList = utils.addElement(repairList, eq[i]);
            }
        }
        obj_id[] _repairList = new obj_id[0];
        if (repairList != null)
        {
            _repairList = new obj_id[repairList.size()];
            repairList.toArray(_repairList);
        }
        return _repairList;
    }
    public static int getItemRepairCost(obj_id item) throws InterruptedException
    {
        int repairAmount = getItemDamageAmount(item);
        if (repairAmount < 1)
        {
            return 0;
        }
        float repairRatio = getItemRepairRatio(item);
        int repairCost = (int)(repairAmount * repairRatio);
        return repairCost;
    }
    public static int getTotalRepairCost(obj_id player) throws InterruptedException
    {
        obj_id[] repairList = getAllRepairItems(player);
        return getTotalRepairCost(player, repairList);
    }
    public static int getTotalRepairCost(obj_id player, obj_id[] repairList) throws InterruptedException
    {
        if (repairList == null || repairList.length == 0)
        {
            return 0;
        }
        int totalCost = 0;
        for (int i = 0; i < repairList.length; i++)
        {
            int cost = getItemRepairCost(repairList[i]);
            if (cost > 0)
            {
                totalCost += cost;
            }
        }
        return totalCost;
    }
    public static int[] getItemRepairCostList(obj_id player, obj_id[] list) throws InterruptedException
    {
        int[] costList = new int[list.length];
        for (int i = 0; i < list.length; i++)
        {
            costList[i] = getItemRepairCost(list[i]);
        }
        return costList;
    }
    public static int getItemDamageAmount(obj_id item) throws InterruptedException
    {
        int maxHp = getMaxHitpoints(item);
        int curHp = getHitpoints(item);
        int damage = maxHp - curHp;
        return damage;
    }
    public static float getStaticItemRepairModifier(obj_id item) throws InterruptedException
    {
        return 1f;
    }
    public static float getItemRepairRatio(obj_id item) throws InterruptedException
    {
        float staticItemModifier = 1.0f;
        if (static_item.isStaticItem(item))
        {
            staticItemModifier = getStaticItemRepairModifier(item);
        }
        int got = getGameObjectType(item);
        String template = getTemplateName(item);
        String gotType = getGameObjectTypeName(got);
        String gotParent = getGameObjectTypeName(got & 0xffffff00);
        int row = dataTableSearchColumnForString(template, 0, "datatables/cloning/repair_costs.iff");
        if (row == -1)
        {
            row = dataTableSearchColumnForString(gotType, 0, "datatables/cloning/repair_costs.iff");
        }
        if (row == -1)
        {
            row = dataTableSearchColumnForString(gotParent, 0, "datatables/cloning/repair_costs.iff");
        }
        if (row == -1)
        {
            return DEFAULT_REPAIR_RATIO;
        }
        float repairRatio = 1.0f;
        if (static_item.isStaticItem(item))
        {
            repairRatio = dataTableGetFloat("datatables/cloning/repair_costs.iff", row, "staticItemCost");
            repairRatio *= staticItemModifier;
        }
        else 
        {
            repairRatio = dataTableGetFloat("datatables/cloning/repair_costs.iff", row, "standardItemCost");
        }
        return repairRatio;
    }
    public static boolean payRepairFee(obj_id player, obj_id terminal, int cost) throws InterruptedException
    {
        int playerMoney = getTotalMoney(player);
        if (playerMoney < cost)
        {
            sendSystemMessage(player, new string_id("error_message", "insufficient_funds"));
            return false;
        }
        dictionary data = new dictionary();
        data.put(money.DICT_OTHER, getGameTime());
        return money.pay(player, terminal, cost, "handlePayRepairSuccess", data, true);
    }
    public static void repairItems(obj_id player, obj_id[] repairList) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (repairList == null || repairList.length == 0)
        {
            return;
        }
        for (int i = 0; i < repairList.length; i++)
        {
            int maxHp = getMaxHitpoints(repairList[i]);
            setInvulnerableHitpoints(repairList[i], maxHp);
        }
    }
}
