package script.library;

import script.*;

import java.util.HashSet;

public class npe extends script.base_script
{
    public npe()
    {
    }
    public static final String SCRIPT_PUBLIC_TRAVEL = "npe.npe_instance_travel_player";
    public static final String SCRIPT_SPACE_TRAVEL = "npe.travel_player_space_dungeon_falcon";
    public static final String DUNGEON_PUBLIC_MANAGER_NAME = "npe_public_instances";
    public static final String DUNGEON_HANGAR = "npe_hangar";
    public static final String DUNGEON_MILLENIUM_FALCON = "npe_space";
    public static final String DUNGEON_SPACE_STATION = "npe_space_station";
    public static final String DUNGEON_ORD_SPACE_STATION = "npe_space_dungeon";
    public static final String BUILDOUT_NAME_SHARED_STATION = "npe_shared_station";
    public static final String BUILDOUT_NAME_SPACE_DUNGEON = "npe_dungeon";
    public static final String SCENE_SPACE_ORD_MANTELL = "space_ord_mantell";
    public static final String SCENE_SPACE_NPE_FALCON = "space_npe_falcon";
    public static final String VAR_NPE_PHASE = "npe.phase_number";
    public static final String VAR_ORD_SCENE_NAME = "npe.ord_scene_name";
    public static final String SCRIPT_VAR_FROM_ORD_SPACE = "npe.coming_from_ord_space";
    public static final String SCRIPT_VAR_ORD_SPACE_DESTINATION = "npe.ord_space_destination";
    public static final String SCRIPT_VAR_INSTANCE_OVERRIDE = "npe.instance_override";
    public static final String SCRIPT_VAR_DO_HYPERSPACE = "npe.do_hyperspace";
    public static final String FROM_ORD_SPACE_TO_STATION_CELL = "hangarbay1";
    public static final float FROM_ORD_SPACE_TO_STATION_X = 47.0f;
    public static final float FROM_ORD_SPACE_TO_STATION_Y = 0.8f;
    public static final float FROM_ORD_SPACE_TO_STATION_Z = 34.1f;
    public static final float PLAYER_PLACEMENT_RANGE = 3.0f;
    public static final float SPACE_PLAYER_PLACEMENT_RANGE = 100.0f;
    public static final String FINISH_PLANET = "tatooine";
    public static final float FINISH_X = 3528.0f;
    public static final float FINISH_Z = -4804.0f;
    public static final int QUEST_ENUMERATION = 1;
    public static final String QUEST_REWORK_VAR = "npe.questRegrant";
    public static final String QUEST_REWORK_TABLE = "datatables/npe/reworked_quests.iff";
    public static final String GROUND_QUESTS = "strGroundQuests";
    public static final String SPACE_QUEST_TYPE = "strSpaceType";
    public static final String SPACE_QUEST_NAME = "strSpaceName";
    public static final string_id NOT_FROM_SPACE = new string_id("npe", "gamma_travel_not_from_space");
    public static final int LEVEL_CAP = 10;
    public static void setResetDungeonObjvar(obj_id player) throws InterruptedException
    {
        if (isIdValid(space_dungeon.getDungeonIdForPlayer(player)))
        {
            setObjVar(player, space_dungeon.VAR_RESET_DUNGEON, space_dungeon.getDungeonIdForPlayer(player));
            space_dungeon.cleanupPlayerDungeonObjvars(player);
        }
    }
    public static void randomizeLocation(location loc) throws InterruptedException
    {
        loc.x += ((-1.0f + (2.0f * random.rand())) * PLAYER_PLACEMENT_RANGE);
        loc.z += ((-1.0f + (2.0f * random.rand())) * PLAYER_PLACEMENT_RANGE);
    }
    public static void randomizeSpaceLocation(location loc) throws InterruptedException
    {
        loc.x += ((-1.0f + (2.0f * random.rand())) * SPACE_PLAYER_PLACEMENT_RANGE);
        loc.y += ((-1.0f + (2.0f * random.rand())) * SPACE_PLAYER_PLACEMENT_RANGE);
        loc.z += ((-1.0f + (2.0f * random.rand())) * SPACE_PLAYER_PLACEMENT_RANGE);
    }
    public static boolean performTransitionFromClusterWideData(obj_id player, dictionary[] stations, String station) throws InterruptedException
    {
        dictionary info;
        dictionary station_info;
        obj_id station_id;
        String planet;
        location world_loc;
        String cell;
        location cell_loc;
        switch (station) {
            case DUNGEON_SPACE_STATION + "*":
                int instance_index = getBestStationInstanceIndex(player, stations);
                if (instance_index == -1 || instance_index >= stations.length) {
                    return false;
                }
                station_info = stations[instance_index];
                if (station_info == null) {
                    return false;
                }
                info = getNpeInstanceInfo(DUNGEON_SPACE_STATION);
                if (utils.hasScriptVar(player, SCRIPT_VAR_FROM_ORD_SPACE)) {
                    removeObjVar(player, SCRIPT_VAR_FROM_ORD_SPACE);
                    utils.removeScriptVar(player, SCRIPT_VAR_FROM_ORD_SPACE);
                    cell_loc = new location(FROM_ORD_SPACE_TO_STATION_X, FROM_ORD_SPACE_TO_STATION_Y, FROM_ORD_SPACE_TO_STATION_Z);
                    info.put("cell", FROM_ORD_SPACE_TO_STATION_CELL);
                    info.put("cell_loc", cell_loc);
                }
                world_loc = new location(station_info.getFloat("world_location.x"), station_info.getFloat("world_location.y"), station_info.getFloat("world_location.z"));
                station_id = station_info.getObjId("building_id");
                planet = info.getString("planet");
                cell = info.getString("cell");
                cell_loc = info.getLocation("cell_loc");
                randomizeLocation(cell_loc);
                LIVE_LOG("npe", "Found " + stations.length + " buildings, moving " + player + " to " + station_id + " s:" + planet + " c:" + cell + " w:" + world_loc.x + " " + world_loc.y + " " + world_loc.z + " l:" + cell_loc.x + " " + cell_loc.y + " " + cell_loc.z);
                if (utils.hasScriptVar(player, SCRIPT_VAR_DO_HYPERSPACE)) {
                    utils.removeScriptVar(player, SCRIPT_VAR_DO_HYPERSPACE);
                    hyperspacePlayerToLocation(player, planet, world_loc.x, world_loc.y, world_loc.z, station_id, cell, cell_loc.x, cell_loc.y, cell_loc.z, "msgNpeInstanceTravelComplete", false);
                } else {
                    warpPlayer(player, planet, world_loc.x, world_loc.y, world_loc.z, station_id, cell, cell_loc.x, cell_loc.y, cell_loc.z, "msgNpeInstanceTravelComplete", false);
                }
                return true;
            case DUNGEON_ORD_SPACE_STATION + "*":
                if (utils.hasScriptVar(player, SCRIPT_VAR_ORD_SPACE_DESTINATION)) {
                    if (!isIdValid(getPlanetByName(SCENE_SPACE_ORD_MANTELL))) {
                        return false;
                    }
                    utils.removeScriptVar(player, SCRIPT_VAR_ORD_SPACE_DESTINATION);
                    obj_id ship = setupLaunchToSpace(player);
                    if (!isIdValid(ship)) {
                        return false;
                    }
                    location new_loc = utils.getLocationScriptVar(player, SCRIPT_VAR_ORD_SPACE_DESTINATION);
                    if(new_loc == null){
                        return false;
                    }
                    new_loc.area = SCENE_SPACE_ORD_MANTELL;
                    location ground_loc = getWorldLocation(player);
                    obj_id[] members = new obj_id[0];
                    randomizeSpaceLocation(new_loc);
                    space_transition.launch(player, ship, members, new_loc, ground_loc);
                    return true;
                } else {
                    String ord_scene = getCurrentSceneName();
                    station_info = getBestOrdSpaceStation(player, stations, ord_scene);
                    if (station_info == null) {
                        return false;
                    }
                    info = getNpeInstanceInfo(DUNGEON_ORD_SPACE_STATION);
                    setObjVar(player, npe.VAR_ORD_SCENE_NAME, ord_scene);
                    station_id = station_info.getObjId("building_id");
                    planet = info.getString("planet");
                    world_loc = new location(station_info.getFloat("world_location.x"), station_info.getFloat("world_location.y"), station_info.getFloat("world_location.z"));
                    cell = info.getString("cell");
                    cell_loc = info.getLocation("cell_loc");
                    randomizeLocation(cell_loc);
                    LIVE_LOG("npe", "Found " + stations.length + " buildings, moving " + player + " to " + station_id + " s:" + planet + " c:" + cell + " w:" + world_loc.x + " " + world_loc.y + " " + world_loc.z + " l:" + cell_loc.x + " " + cell_loc.y + " " + cell_loc.z);
                    if (utils.hasScriptVar(player, SCRIPT_VAR_DO_HYPERSPACE)) {
                        utils.removeScriptVar(player, SCRIPT_VAR_DO_HYPERSPACE);
                        hyperspacePlayerToLocation(player, planet, world_loc.x, world_loc.y, world_loc.z, station_id, cell, cell_loc.x, cell_loc.y, cell_loc.z, "msgNpeInstanceTravelComplete", false);
                    } else {
                        warpPlayer(player, planet, world_loc.x, world_loc.y, world_loc.z, station_id, cell, cell_loc.x, cell_loc.y, cell_loc.z, "msgNpeInstanceTravelComplete", false);
                    }
                    return true;
                }
            default:
                LIVE_LOG("npe", "Player is trying to perform a cluster wide transition, but is going to an unsupported station " + station + " should be " + DUNGEON_SPACE_STATION + "*, " + DUNGEON_ORD_SPACE_STATION + "*");
                return false;
        }
    }
    public static dictionary getNpeInstanceInfo(String row) throws InterruptedException
    {
        String table = "datatables/travel/zone_transition.iff";
        String column = "destination";
        String destination = dataTableGetString(table, row, column);
        String[] parsed_list = split(destination, ':');
        dictionary ret = new dictionary();
        ret.put("planet", parsed_list[2]);
        ret.put("cell", parsed_list[3]);
        location cell_loc = new location(utils.stringToFloat(parsed_list[4]), utils.stringToFloat(parsed_list[5]), utils.stringToFloat(parsed_list[6]));
        ret.put("cell_loc", cell_loc);
        return ret;
    }
    public static int getMinInstancePopulation() throws InterruptedException
    {
        return utils.stringToInt(getConfigSetting("GameServer", "npeMinInstancePopulation"));
    }
    public static int getMaxInstancePopulation() throws InterruptedException
    {
        return utils.stringToInt(getConfigSetting("GameServer", "npeMaxInstancePopulation"));
    }
    public static int getBestStationInstanceIndex(obj_id player, dictionary[] stations) throws InterruptedException
    {
        if (stations == null || stations.length == 0)
        {
            LOG("npe", "getBestSpaceStation called with null stations array!");
            return -1;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE))
        {
            int index = utils.getIntScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            utils.removeScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            if (index < -1 || index >= stations.length)
            {
                sendSystemMessageTestingOnly(player, "You have specified an invalid Tansarii station index override (max of " + (stations.length - 1) + "). Sending to default station");
                if (stations[0] != null)
                {
                    return 0;
                }
            }
            if (stations[index] != null)
            {
                return index;
            }
            sendSystemMessageTestingOnly(player, "The Tansarii station index override you specified is null! Defaulting to normal behavior");
        }
        int return_index = -1;
        int population = -1;
        for (int i = 0; i < stations.length; ++i)
        {
            if (stations[i] == null)
            {
                LOG("npe", "getBestStationInstanceIndex: Got a null entry in stations array");
                continue;
            }
            int new_pop = stations[i].getInt("population_num");
            if (!isIdValid(stations[i].getObjId("building_id")))
            {
                LOG("npe", "getBestStationInstanceIndex: stations array passed into getBestStationInstanceIndex contained a null or invalid station id");
                continue;
            }
            if (return_index == -1 || isSecondInstanceBetter(population, new_pop))
            {
                return_index = i;
                population = new_pop;
            }
        }
        if (return_index == -1)
        {
            LOG("npe", "getBestStationInstanceIndex: Was not able to find a space station in getBestStationInstanceIndex!");
        }
        return return_index;
    }
    public static int getBestDungeonInstanceIndex(obj_id player, dictionary[] dungeons) throws InterruptedException
    {
        if (dungeons == null || dungeons.length == 0)
        {
            LOG("npe", "getBestDungeonInstanceIndex called with null dungeons array!");
            return -1;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE))
        {
            int index = utils.getIntScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            utils.removeScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            if (index < -1 || index >= dungeons.length)
            {
                sendSystemMessageTestingOnly(player, "You have specified an invalid station Gamma index override (max of " + (dungeons.length - 1) + "). Sending to default station");
                if (dungeons[0] != null)
                {
                    return 0;
                }
            }
            if (dungeons[index] != null)
            {
                return index;
            }
            sendSystemMessageTestingOnly(player, "The station Gamma index override you specified is null! Defaulting to normal behavior");
        }
        int return_index = -1;
        int population = -1;
        for (int i = 0; i < dungeons.length && i < getNumberOfOrdSpaceScenes(); ++i)
        {
            if (dungeons[i] == null)
            {
                LOG("npe", "getBestDungeonInstanceIndex: Got a null entry in dungeons array");
                continue;
            }
            obj_id new_station = dungeons[i].getObjId("building_id");
            int new_pop = dungeons[i].getInt("population_num");
            if (!isIdValid(new_station))
            {
                LOG("npe", "getBestDungeonInstanceIndex: dungeons array passed into getBestDungeonInstanceIndex contained a null or invalid station id");
                continue;
            }
            String ordSpaceScene = SCENE_SPACE_ORD_MANTELL;
            if (i > 0)
            {
                ordSpaceScene += ("_" + (i + 1));
            }
            if (isAreaTooFullForTravel(ordSpaceScene, 0, 0))
            {
                LOG("npe", "getBestDungeonInstanceIndex: skipping ord scene " + ordSpaceScene + " because it is full");
                continue;
            }
            if (return_index == -1 || isSecondInstanceBetter(population, new_pop))
            {
                return_index = i;
                population = new_pop;
            }
        }
        if (return_index == -1)
        {
            LOG("npe", "getBestDungeonInstanceIndex: Was not able to find a station Gamma in getBestDungeonInstanceIndex!");
        }
        return return_index;
    }
    public static boolean isSecondInstanceBetter(int pop, int new_pop) throws InterruptedException
    {
        if (pop != 0 && pop < getMinInstancePopulation() && (new_pop == 0 || pop <= new_pop))
        {
            return false;
        }
        if (new_pop != 0 && new_pop < getMinInstancePopulation() && (pop == 0 || new_pop <= pop))
        {
            return true;
        }
        return new_pop < pop;
    }
    public static dictionary getBestOrdSpaceStation(obj_id player, dictionary[] stations, String ord_scene) throws InterruptedException
    {
        if (stations == null || stations.length == 0)
        {
            LOG("npe", "getBestOrdSpaceStation called with null stations array!");
            return null;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE))
        {
            int index = utils.getIntScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            utils.removeScriptVar(player, SCRIPT_VAR_INSTANCE_OVERRIDE);
            if (index < -1 || index >= stations.length)
            {
                sendSystemMessageTestingOnly(player, "You have specified an invalid station Gamma index override (max of " + (stations.length - 1) + "). Sending to default station");
                if (stations[0] != null)
                {
                    return stations[0];
                }
            }
            if (stations[index] != null)
            {
                return stations[index];
            }
            sendSystemMessageTestingOnly(player, "The station Gamma index override you specified is null! Defaulting to normal behavior");
        }
        int ord_num_ind = utils.stringToInt(ord_scene.substring(ord_scene.length() - 1));
        --ord_num_ind;
        if (ord_num_ind > 0 && ord_num_ind < stations.length && stations[ord_num_ind] != null && isIdValid(stations[ord_num_ind].getObjId("building_id")))
        {
            return stations[ord_num_ind];
        }
        for (dictionary station : stations) {
            if (station != null && isIdValid(station.getObjId("building_id"))) {
                return station;
            }
        }
        return null;
    }
    public static int getNumberOfOrdSpaceScenes() throws InterruptedException
    {
        if (!isIdValid(getPlanetByName(SCENE_SPACE_ORD_MANTELL)))
        {
            return 0;
        }
        int maxIndex = 1;
        for (int i = 2; i < 10; ++i)
        {
            if (!isIdValid(getPlanetByName(SCENE_SPACE_ORD_MANTELL + "_" + i)))
            {
                break;
            }
            maxIndex = i;
        }
        return maxIndex;
    }
    public static String getOpenOrdMantellSpaceZone() throws InterruptedException
    {
        int returnIndex = rand(1, getNumberOfOrdSpaceScenes());
        if (returnIndex == 1)
        {
            return SCENE_SPACE_ORD_MANTELL;
        }
        return (SCENE_SPACE_ORD_MANTELL + "_" + returnIndex);
    }
    public static obj_id setupLaunchToSpace(obj_id player) throws InterruptedException
    {
        obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(player);
        if (shipControlDevices == null || shipControlDevices.length == 0)
        {
            LOG("npe", "Player " + player + " has no ship control devices");
            return null;
        }
        obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevices[0]);
        if (!space_utils.isShipUsable(ship, player))
        {
            LOG("npe", "Ship: " + ship + " is not useable");
            return null;
        }
        if (!isIdValid(ship))
        {
            LOG("npe", "Can't launch to space, player " + player + " does not have a valid ship");
            return null;
        }
        return ship;
    }
    public static void resetPlayerForNpe(obj_id player) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        detachScript(player, SCRIPT_PUBLIC_TRAVEL);
    }
    public static boolean movePlayerFromHangarToFalcon(obj_id player) throws InterruptedException
    {
        setObjVar(player, VAR_NPE_PHASE, 2);
        setResetDungeonObjvar(player);
        attachScript(player, SCRIPT_SPACE_TRAVEL);
        return space_dungeon.sendGroupToDungeonWithoutTicketCollector(player, DUNGEON_MILLENIUM_FALCON, "quest_type");
    }
    public static boolean movePlayerFromFalconToSharedStation(obj_id player) throws InterruptedException
    {
        setObjVar(player, VAR_NPE_PHASE, 3);
        removeObjVar(player, space_dungeon.VAR_EJECT_OVERRIDE);
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        setResetDungeonObjvar(player);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_SPACE_STATION + "*", false, player);
        return true;
    }
    public static boolean movePlayerFromInstanceToInstance(obj_id player, obj_id desired_station, location world_location, String instance_name, int cluster_wide_index) throws InterruptedException
    {
        if (!instance_name.equals(DUNGEON_SPACE_STATION) && !instance_name.equals(DUNGEON_ORD_SPACE_STATION))
        {
            LIVE_LOG("npe", "Can't send player " + player + " to " + desired_station + " because the instance_name " + instance_name + " is not valid");
            return false;
        }
        if (instance_name.equals(DUNGEON_ORD_SPACE_STATION))
        {
            String ordSpaceScene = SCENE_SPACE_ORD_MANTELL;
            if (cluster_wide_index > 0)
            {
                ordSpaceScene += ("_" + (cluster_wide_index + 1));
            }
            setObjVar(player, VAR_ORD_SCENE_NAME, ordSpaceScene);
        }
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        dictionary info = getNpeInstanceInfo(instance_name);
        String planet = info.getString("planet");
        String cell = info.getString("cell");
        location loc = new location(info.getFloat("locx"), info.getFloat("locy"), info.getFloat("locz"));
        randomizeLocation(loc);
        warpPlayer(player, planet, world_location.x, world_location.y, world_location.z, desired_station, cell, loc.x, loc.y, loc.z, "msgNpeInstanceTravelComplete", false);
        return true;
    }
    public static boolean movePlayerFromSharedStationToOrdMantellSpace(obj_id player, location new_loc) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        utils.setScriptVar(player, SCRIPT_VAR_ORD_SPACE_DESTINATION, new_loc);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_ORD_SPACE_STATION + "*", false, player);
        return transferPlayerToOrdMantellSpace(player, new_loc);
    }
    public static boolean movePlayerFromOrdMantellSpaceToSharedStation(obj_id player) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        utils.setScriptVar(player, SCRIPT_VAR_FROM_ORD_SPACE, 1);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_SPACE_STATION + "*", false, player);
        return true;
    }
    public static boolean movePlayerFromSharedStationToOrdMantellDungeon(obj_id player) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_ORD_SPACE_STATION + "*", false, player);
        return true;
    }
    public static boolean movePlayerFromOrdMantellDungeonToSharedStation(obj_id player) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        utils.setScriptVar(player, SCRIPT_VAR_FROM_ORD_SPACE, 1);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_SPACE_STATION + "*", false, player);

        return movePlayerFromOrdMantellSpaceToSharedStation(player);
    }
    public static boolean movePlayerFromOrdMantellSpaceToOrdMantellDungeon(obj_id player) throws InterruptedException
    {
        detachScript(player, SCRIPT_SPACE_TRAVEL);
        attachScript(player, SCRIPT_PUBLIC_TRAVEL);
        getClusterWideData(DUNGEON_PUBLIC_MANAGER_NAME, DUNGEON_ORD_SPACE_STATION + "*", false, player);
        return true;
    }
    public static boolean movePlayerFromOrdMantellDungeonToOrdMantellSpace(obj_id player, location new_loc) throws InterruptedException
    {
        return transferPlayerToOrdMantellSpace(player, new_loc);
    }

    private static boolean transferPlayerToOrdMantellSpace(obj_id player, location new_loc) throws InterruptedException
    {
        String scene = getStringObjVar(player, VAR_ORD_SCENE_NAME);
        if (scene == null || scene.equals("") || !scene.equals("space_ord_mantell"))
        {
            LOG("npe", "transferPlayerToOrdMantellSpace: empty scene id found: " + player);
            scene = "space_ord_mantell";
        }
        if (isAreaTooFullForTravel(scene, 0, 0))
        {
            scene = getOpenOrdMantellSpaceZone();
            if (scene == null || scene.equals(""))
            {
                LIVE_LOG("npe", "transferPlayerToOrdMantellSpace: player " + player + " can't be moved to ord mantell, all scenes are full");
                return false;
            }
        }
        obj_id ship = setupLaunchToSpace(player);
        if (!isIdValid(ship))
        {
            return false;
        }
        removeObjVar(player, VAR_ORD_SCENE_NAME);
        new_loc.area = scene;
        location ground_loc = getWorldLocation(player);
        obj_id[] members = new obj_id[0];
        randomizeSpaceLocation(new_loc);
        space_transition.launch(player, ship, members, new_loc, ground_loc);
        return true;
    }
    public static boolean movePlayerFromSharedStationToFinishLocation(obj_id player) throws InterruptedException
    {
        removeObjVar(player, "npe");
        detachScript(player, SCRIPT_PUBLIC_TRAVEL);
        detachScript(player, "npe.trigger_journal");
        removeObjVar(player, VAR_NPE_PHASE);
        removeObjVar(player, VAR_ORD_SCENE_NAME);
        newbieTutorialEnableHudElement(player, "radar", true, 0);
        attachScript(player, "npe.handoff_to_tatooine");
        setCompletedTutorial(player, true);
        setObjVar(player, "comingFromTutorial", 1);
        warpPlayer(player, FINISH_PLANET, FINISH_X, 0, FINISH_Z, null, 0.0f, 0.0f, 0.0f, null, false);
        return true;
    }
    public static boolean teleportPlayerToLaunchLoc(obj_id player, boolean hyperspace) throws InterruptedException
    {
        String scene = getCurrentSceneName();
        if (scene == null)
        {
            movePlayerFromOrdMantellSpaceToSharedStation(player);
        }
        else if (scene.startsWith(SCENE_SPACE_ORD_MANTELL))
        {
            if (hyperspace)
            {
                utils.setScriptVar(player, SCRIPT_VAR_DO_HYPERSPACE, 1);
            }
            location launchLocation = getLocationObjVar(player, "space.launch.worldLoc");
            String launchBuildoutName = BUILDOUT_NAME_SHARED_STATION;
            if (launchLocation != null)
            {
                String buildoutName = getBuildoutAreaName(launchLocation.x, launchLocation.z, launchLocation.area);
                if (buildoutName != null && !buildoutName.equals(""))
                {
                    launchBuildoutName = buildoutName;
                }
            }
            if (launchBuildoutName.equals(BUILDOUT_NAME_SPACE_DUNGEON))
            {
                movePlayerFromOrdMantellSpaceToOrdMantellDungeon(player);
            }
            else 
            {
                if (!launchBuildoutName.equals(BUILDOUT_NAME_SHARED_STATION))
                {
                    LIVE_LOG("npe", "Player (" + player + ") did not have a valid launch location (" + launchLocation + ") or a valid launch buildout name (" + launchBuildoutName + ") sending to npe_shared_station instead");
                }
                movePlayerFromOrdMantellSpaceToSharedStation(player);
            }
        }
        else if (scene.startsWith(SCENE_SPACE_NPE_FALCON))
        {
            sendPlayerToTutorial(player);
        }
        else if (isFreeTrialAccount(player))
        {
            LIVE_LOG("npe", "Player(" + player + ") is a free trial player in an invalid space zone(" + scene + "), sending to tutorial start");
            sendPlayerToTutorial(player);
        }
        else 
        {
            return false;
        }
        return true;
    }
    public static obj_id[] grantNewbArmor(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        int pSpecies = getSpecies(player);
        int pGender = getGender(player);
        HashSet theSet = new HashSet();
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            theSet.add(static_item.createNewItemFunction("item_npe_fs_robe_02_01", pInv));
        }
        else if (pSpecies == SPECIES_WOOKIEE)
        {
            theSet.add(static_item.createNewItemFunction("item_wookiee_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_wookiee_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_wookiee_hat_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_wookiee_gloves_02_01", pInv));
        }
        else if (pSpecies == SPECIES_ITHORIAN)
        {
            theSet.add(static_item.createNewItemFunction("item_ithorian_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_ithorian_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_ithorian_vest_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_ithorian_gloves_02_01", pInv));
        }
        else if (utils.isProfession(player, utils.SMUGGLER))
        {
            theSet.add(static_item.createNewItemFunction("item_smuggler_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_smuggler_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_smuggler_vest_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_smuggler_boots_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.BOUNTY_HUNTER))
        {
            theSet.add(static_item.createNewItemFunction("item_bounty_hunter_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_bounty_hunter_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_bounty_hunter_vest_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_bounty_hunter_boots_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.OFFICER))
        {
            theSet.add(static_item.createNewItemFunction("item_officer_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_officer_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_officer_belt_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_officer_boots_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.COMMANDO))
        {
            theSet.add(static_item.createNewItemFunction("item_commando_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_commando_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_commando_vest_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_commando_boots_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.MEDIC))
        {
            theSet.add(static_item.createNewItemFunction("item_medic_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_medic_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_medic_vest_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_medic_shoes_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.SPY))
        {
            theSet.add(static_item.createNewItemFunction("item_spy_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_spy_pants_02_01", pInv));
            if (pSpecies != SPECIES_MON_CALAMARI && pSpecies != SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_spy_gloves_02_01", pInv));
            }
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_spy_boots_02_01", pInv));
            }
        }
        else if (utils.isProfession(player, utils.ENTERTAINER))
        {
            if (pGender == GENDER_MALE)
            {
                theSet.add(static_item.createNewItemFunction("item_entertainer_shirt_02_01", pInv));
                theSet.add(static_item.createNewItemFunction("item_entertainer_pants_02_01", pInv));
                theSet.add(static_item.createNewItemFunction("item_entertainer_hat_02_01", pInv));
                if (pSpecies == SPECIES_TRANDOSHAN)
                {
                    theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
                }
                else 
                {
                    theSet.add(static_item.createNewItemFunction("item_entertainer_boots_02_01", pInv));
                }
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_entertainer_shirt_02_02", pInv));
                theSet.add(static_item.createNewItemFunction("item_entertainer_skirt_02_01", pInv));
                if (pSpecies != SPECIES_MON_CALAMARI && pSpecies != SPECIES_TRANDOSHAN)
                {
                    theSet.add(static_item.createNewItemFunction("item_entertainer_gloves_02_01", pInv));
                }
                if (pSpecies == SPECIES_TRANDOSHAN)
                {
                    theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
                }
                else 
                {
                    theSet.add(static_item.createNewItemFunction("item_entertainer_shoes_02_01", pInv));
                }
            }
        }
        else if (utils.isProfession(player, utils.TRADER))
        {
            theSet.add(static_item.createNewItemFunction("item_trader_shirt_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_trader_pants_02_01", pInv));
            theSet.add(static_item.createNewItemFunction("item_trader_vest_02_01", pInv));
            if (pSpecies == SPECIES_TRANDOSHAN)
            {
                theSet.add(static_item.createNewItemFunction("item_npe_trando_necklace_01_01", pInv));
            }
            else 
            {
                theSet.add(static_item.createNewItemFunction("item_trader_shoes_02_01", pInv));
            }
        }
        obj_id[] items = new obj_id[theSet.size()];
        theSet.toArray(items);
        showLootBox(player, items);
        return items;
    }
    public static obj_id grantNpeResourceStack(obj_id player, String strResourceType, int intAmount) throws InterruptedException
    {
        obj_id[] objResourceIds = getResourceTypes(strResourceType);
        obj_id objResourceId = null;
        if ((objResourceIds != null) && (objResourceIds.length > 0))
        {
            objResourceId = objResourceIds[rand(0, objResourceIds.length - 1)];
        }
        if (!isIdValid(objResourceId))
        {
            return null;
        }
        obj_id objContainer = utils.getInventoryContainer(player);
        obj_id objStack = getResourceStack(objContainer, objResourceId);
        if (isIdValid(objStack))
        {
            int intCount = getResourceContainerQuantity(objStack);
            int maxResource = 1000000;
            intCount += intAmount;
            if (intCount > maxResource)
            {
                intCount -= maxResource;
                intAmount -= intCount;
            }
            addResourceToContainer(objStack, objResourceId, intAmount, null);
        }
        else 
        {
            objStack = createResourceCrate(objResourceId, intAmount, objContainer);
            if (isIdValid(objStack))
            {
                return objStack;
            }
        }
        return null;
    }
    public static int harvestNpeResourceStack(obj_id player, String strResourceType, int intAmount) throws InterruptedException
    {
        obj_id[] objResourceIds = getResourceTypes(strResourceType);
        obj_id objResourceId = null;
        if ((objResourceIds != null) && (objResourceIds.length > 0))
        {
            objResourceId = objResourceIds[0];
        }
        if (!isIdValid(objResourceId))
        {
            return 0;
        }
        obj_id objContainer = utils.getInventoryContainer(player);
        obj_id objStack = getResourceStack(objContainer, objResourceId);
        if (isIdValid(objStack))
        {
            int maxResource = 1000000;
            int intCount = getResourceContainerQuantity(objStack);
            intCount += intAmount;
            if (intCount > maxResource)
            {
                intCount -= maxResource;
                intAmount -= intCount;
            }
            addResourceToContainer(objStack, objResourceId, intAmount, null);
            utils.setScriptVar(player, "resource.lastAmt", intAmount);
            return intAmount;
        }
        else 
        {
            objStack = createResourceCrate(objResourceId, intAmount, objContainer);
            if (isIdValid(objStack))
            {
                utils.setScriptVar(player, "resource.lastAmt", intAmount);
                return intAmount;
            }
        }
        return 0;
    }
    public static obj_id getResourceStack(obj_id objContainer, obj_id objResource) throws InterruptedException
    {
        if (!isIdValid(objContainer))
        {
            return null;
        }
        obj_id[] objContents = getContents(objContainer);
        if (objContents == null)
        {
            return null;
        }
        int maxResource = 1000000;
        for (obj_id objContent : objContents) {
            if (getResourceContainerResourceType(objContent) == objResource) {
                if (getResourceContainerQuantity(objContent) < maxResource) {
                    return objContent;
                }
            }
        }
        return null;
    }
    public static boolean giveCreditPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.credits") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_credits"), "sound/c3po_29.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.credits", 1);
            return true;
        }
        return false;
    }
    public static boolean giveAutoPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "npe.pop_auto") == 0;
    }
    public static boolean giveEquipPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.pop_equip") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 3, new string_id("npe", "pop_equip"), "sound/c3po_31.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.pop_equip", 1);
            return true;
        }
        return false;
    }
    public static boolean giveInvPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.pop_inv") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 3, new string_id("npe", "pop_inventory"), "sound/c3po_43.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.pop_inv", 1);
            return true;
        }
        return false;
    }
    public static boolean giveAttackPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.pop_attack") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 4, new string_id("npe", "pop_attack"), "sound/c3po_21.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.pop_attack", 1);
            return true;
        }
        return false;
    }
    public static boolean giveTargetPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.pop_target") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_target"), "sound/c3po_51.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.pop_target", 1);
            return true;
        }
        return false;
    }
    public static boolean giveMapPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.map") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_map"), "sound/c3po_46.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.map", 1);
            return true;
        }
        return false;
    }
    public static boolean giveJournPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 12, new string_id("npe", "pop_journal"), "sound/c3po_44.snd", "object/mobile/c_3po.iff");
        utils.setScriptVar(player, "npe.journal", 1);
        return true;
    }
    public static boolean giveQuestHelperPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 7, new string_id("npe", "pop_quest_helper"), "sound/c3po_50b.snd", "object/mobile/c_3po.iff");
        utils.setScriptVar(player, "npe.quest_helper", 1);
        return true;
    }
    public static boolean giveQuestHelperPopUp2(obj_id player) throws InterruptedException
    {
        utils.setScriptVar(player, "npe.quest_helper", 1);
        return true;
    }
    public static boolean giveCraftPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.craft") == 0)
        {
            obj_id droid = utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis");
            commTutorialPlayer(droid, player, 15, new string_id("npe", "pop_craft"), "sound/c3po_26.snd", "object/mobile/c_3po.iff");
            commTutorialPlayer(droid, player, 12, new string_id("npe", "pop_craft2"), "sound/c3po_27.snd", "object/mobile/c_3po.iff");
            commTutorialPlayer(droid, player, 14, new string_id("npe", "pop_craft3"), "sound/c3po_28.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.craft", 1);
            return true;
        }
        return false;
    }
    public static boolean givePistolPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_pistol") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_equip_pistol"), "sound/c3po_83.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_pistol", 1);
            return true;
        }
        return false;
    }
    public static boolean giveCarbinePopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_carbine") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_equip_carbine"), "sound/c3po_82.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_carbine", 1);
            return true;
        }
        return false;
    }
    public static boolean giveRiflePopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_rifle") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_equip_rifle"), "sound/c3po_84.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_rifle", 1);
            return true;
        }
        return false;
    }
    public static boolean give1hPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_sword") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_equip_sword"), "sound/c3po_86.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_sword", 1);
            return true;
        }
        return false;
    }
    public static boolean give2hPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_axe") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 8, new string_id("npe", "pop_equip_axe"), "sound/c3po_81.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_axe", 1);
            return true;
        }
        return false;
    }
    public static boolean givePolePopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_staff") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 9, new string_id("npe", "pop_equip_staff"), "sound/c3po_85.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_staff", 1);
            return true;
        }
        return false;
    }
    public static boolean giveUnarmPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.equip_unarmed") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 11, new string_id("npe", "pop_equip_unarmed"), "sound/c3po_87.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.equip_unarmed", 1);
            return true;
        }
        return false;
    }
    public static boolean giveHealPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.heal") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 7, new string_id("npe", "pop_heal"), "sound/c3po_90.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.heal", 1);
            return true;
        }
        return false;
    }
    public static boolean givePerformPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.perform") == 0)
        {
            obj_id droid = utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis");
            commTutorialPlayer(droid, player, 16, new string_id("npe", "pop_perform"), "sound/c3po_93.snd", "object/mobile/c_3po.iff");
            commTutorialPlayer(droid, player, 13, new string_id("npe", "pop_flourish"), "sound/c3po_88.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.perform", 1);
            return true;
        }
        return false;
    }
    public static boolean giveGrenadePopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_grenade"), "", "object/mobile/dressed_npe_commando.iff");
        return true;
    }
    public static boolean giveChatPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_chat"), "sound/dro_r2_3_danger.snd", "object/mobile/r2.iff");
        return true;
    }
    public static boolean giveGroupPopUp1(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_group"), "sound/dro_r2_3_danger.snd", "object/mobile/r2.iff");
        return true;
    }
    public static boolean giveGroupPopUp2(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 20, new string_id("npe", "pop_group2"), "sound/dro_r2_3_danger.snd", "object/mobile/r2.iff");
        return true;
    }
    public static boolean giveEscPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_escape"), "sound/vo_c3po_xtra2.snd", "object/mobile/c_3po.iff");
        return true;
    }
    public static boolean giveEntXpPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_entxp"), "sound/dro_r2_3_danger.snd", "object/mobile/r2.iff");
        return true;
    }
    public static boolean giveTraderXpPopUp(obj_id player) throws InterruptedException
    {
        commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 10, new string_id("npe", "pop_tradexp"), "sound/dro_r2_3_danger.snd", "object/mobile/r2.iff");
        return true;
    }
    public static boolean giveStationWaypoint(obj_id player) throws InterruptedException
    {
        boolean theyHave = false;
        String myName;
        for (obj_id waypoint1 : getWaypointsInDatapad(player)) {
            myName = getWaypointName(waypoint1);
            if (myName.equals("Station Gamma")) {
                theyHave = true;
                break;
            }
        }
        if (!theyHave)
        {
            obj_id waypoint = createWaypointInDatapad(player, new location(663, -1002, 2039, "space_ord_mantell", null));
            setWaypointName(waypoint, "Station Gamma");
            setWaypointColor(waypoint, "space");
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            return true;
        }
        return false;
    }
    public static boolean giveHarvestPopUp(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.getIntScriptVar(player, "npe.harvest") == 0)
        {
            commTutorialPlayer(utils.getObjIdScriptVar(getTopMostContainer(player), "objDroidInvis"), player, 6, new string_id("npe", "pop_harvest"), "sound/c3po_89.snd", "object/mobile/c_3po.iff");
            utils.setScriptVar(player, "npe.harvest", 1);
            return true;
        }
        return false;
    }
    public static void npeNpcVendor(obj_id player, obj_id npc) throws InterruptedException
    {
        String[] options = new String[2];
        string_id[] items = new string_id[2];
        items[0] = new string_id("npe", "store_item3");
        items[1] = new string_id("npe", "store_item1");
        for (int i = 0; i < items.length; i++)
        {
            options[i] = utils.packStringId(items[i]);
        }
        sui.showSUIPage(sui.listbox(
                npc,
                player,
                utils.packStringId(new string_id("npe", "store_prompt")),
                sui.OK_CANCEL,
                utils.packStringId(new string_id("npe", "store_title")),
                options,
                "npeHandleBuy",
                false,
                false
        ));
    }
    public static void giveTemplatePointer(obj_id player) throws InterruptedException
    {
        if (utils.isProfession(player, utils.BOUNTY_HUNTER))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_artisan");
        }
        else if (utils.isProfession(player, utils.COMMANDO))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_commando_template");
        }
        else if (utils.isProfession(player, utils.ENTERTAINER))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_entertainer_template");
        }
        else if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_force_template");
        }
        else if (utils.isProfession(player, utils.MEDIC))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_med_template");
        }
        else if (utils.isProfession(player, utils.OFFICER))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_officer_template");
        }
        else if (utils.isProfession(player, utils.SPY))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_spy_template");
        }
        else if (utils.isProfession(player, utils.TRADER))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_trader_template");
        }
        else if (utils.isProfession(player, utils.SMUGGLER))
        {
            groundquests.sendSignal(player, "npe_solo_profession_2_end");
            groundquests.grantQuest(player, "npe_pointer_smuggler_template");
        }
    }
    public static void commTutorialPlayer(obj_id owner, obj_id player, float duration, string_id text, String sfx, String appearance) throws InterruptedException
    {
        commPlayers(owner, appearance, sfx, duration, player, prose.getPackage(text));
    }
    public static void sendDelayed3poPopup(obj_id player, int timeDelay, int duration, String soundFile, String strFile, String strMessage, String scriptVarName) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("strFile", strFile);
        d.put("strMessage", strMessage);
        d.put("scriptVarName", scriptVarName);
        d.put("player", player);
        d.put("duration", duration);
        d.put("soundFile", soundFile);
        messageTo(player, "doDelayed3POMessage", d, timeDelay, false);
    }
    public static void removeAllQuests(obj_id player) throws InterruptedException
    {
        for (int quest : questGetAllActiveQuestIds(player)) {
            questClearQuest(quest, player);
        }
    }
    public static obj_id[] giveProfessionWeapon(obj_id player) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        HashSet theSet = new HashSet();
        if (utils.isProfession(player, utils.SMUGGLER))
        {
            theSet.add(static_item.createNewItemFunction("item_npe_smuggler_han_solo_gun", pInv));
        }
        else if (utils.isProfession(player, utils.BOUNTY_HUNTER))
        {
            theSet.add(static_item.createNewItemFunction("weapon_npe_carbine_bh_03_01", pInv));
        }
        else if (utils.isProfession(player, utils.OFFICER))
        {
            theSet.add(static_item.createNewItemFunction("item_npe_officer_sidearm", pInv));
        }
        else if (utils.isProfession(player, utils.COMMANDO))
        {
            theSet.add(static_item.createNewItemFunction("weapon_npe_commando_carbine_03_01", pInv));
        }
        else if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            theSet.add(static_item.createNewItemFunction("weapon_polearm_02_03", pInv));
        }
        else if (utils.isProfession(player, utils.MEDIC))
        {
            theSet.add(static_item.createNewItemFunction("weapon_npe_medic_pistol_03_01", pInv));
        }
        else if (utils.isProfession(player, utils.SPY))
        {
            theSet.add(static_item.createNewItemFunction("weapon_npe_carbine_spy_03_01", pInv));
        }
        else if (utils.isProfession(player, utils.ENTERTAINER))
        {
            theSet.add(static_item.createNewItemFunction("item_npe_dance_prop_l_entertainer_02_01", pInv));
        }
        else if (utils.isProfession(player, utils.TRADER))
        {
            theSet.add(static_item.createNewItemFunction("item_npe_gen_craft_tool_trader_03_01", pInv));
        }
        obj_id[] items = new obj_id[theSet.size()];
        theSet.toArray(items);
        showLootBox(player, items);
        return items;
    }
    public static void reGrantReWorkedQuests(obj_id player) throws InterruptedException
    {
        for (String quest : dataTableGetStringColumn(QUEST_REWORK_TABLE, GROUND_QUESTS)) {
            if (groundquests.isQuestActive(player, quest)) {
                groundquests.clearQuest(player, quest);
                if (!quest.equals("npe_scout_1a") && !quest.equals("npe_brawler_2a")) {
                    groundquests.grantQuestNoAcceptUI(player, quest, false);
                }
            }
        }
        setObjVar(player, QUEST_REWORK_VAR, QUEST_ENUMERATION);
    }
    public static void clearActiveSpaceQuests(obj_id player) throws InterruptedException
    {
        String[] questTypeList = dataTableGetStringColumn(QUEST_REWORK_TABLE, SPACE_QUEST_TYPE);
        String[] questNameList = dataTableGetStringColumn(QUEST_REWORK_TABLE, SPACE_QUEST_NAME);
        int typeListLength = questTypeList.length;
        int nameListLength = questNameList.length;
        if (typeListLength != nameListLength)
        {
            return;
        }
        for (int i = 0; i < nameListLength; i++)
        {
            if (!space_quest.hasReceivedReward(player, "destroy", "npe_hard_main_3"))
            {
                space_quest.clearQuestFlags(player, questTypeList[i], questNameList[i]);
            }
        }
        setObjVar(player, QUEST_REWORK_VAR, QUEST_ENUMERATION);
    }
    public static boolean hasReachedMaxTutorialLevel(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "npe"))
        {
            if (getLevel(player) >= LEVEL_CAP)
            {
                return true;
            }
        }
        return false;
    }
}
