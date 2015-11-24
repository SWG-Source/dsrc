package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.player_structure;
import script.library.pet_lib;
import script.library.create;
import script.library.utils;
import java.lang.Math;

public class battlefield extends script.base_script
{
    public battlefield()
    {
    }
    public static final String BATTLEFIELD_DATATABLE = "datatables/battlefield/battlefield.iff";
    public static final String BATTLEFIELD_MARKER_DATATABLE = "datatables/battlefield/marker.iff";
    public static final String BUILDABLE_STRUCTURE_DATATABLE = "datatables/battlefield/buildable_structure.iff";
    public static final String SPAWNER_DATATABLE = "datatables/battlefield/spawner.iff";
    public static final String BATTLEFIELD_MARKER = "object/static/structure/tatooine/antenna_tatt_style_1.iff";
    public static final String DATATABLE_COL_NAME = "battlefield";
    public static final String DATATABLE_COL_AREA = "area";
    public static final String DATATABLE_COL_TYPE = "type";
    public static final String DATATABLE_COL_X1 = "x1";
    public static final String DATATABLE_COL_Y1 = "y1";
    public static final String DATATABLE_COL_Z1 = "z1";
    public static final String DATATABLE_COL_RADIUS = "radius";
    public static final String DATATABLE_COL_GAME_TYPE = "gameType";
    public static final String DATATABLE_COL_RESTART_MIN = "restartMin";
    public static final String DATATABLE_COL_RESTART_MAX = "restartMax";
    public static final String DATATABLE_COL_GAME_TIME = "gameTime";
    public static final String DATATABLE_COL_IMPERIAL = "Imperial";
    public static final String DATATABLE_COL_REBEL = "Rebel";
    public static final String DATATABLE_COL_HUTT = "Hutt";
    public static final String DATATABLE_COL_OBJECT = "object";
    public static final String DATATABLE_COL_OBJECT_X = "object_x";
    public static final String DATATABLE_COL_OBJECT_Y = "object_y";
    public static final String DATATABLE_COL_OBJECT_Z = "object_z";
    public static final String DATATABLE_COL_OBJECT_DELTA = "object_delta";
    public static final String DATATABLE_COL_HEADING = "heading";
    public static final String DATATABLE_COL_FACTION = "faction";
    public static final String DATATABLE_COL_STRUCTURE_TYPE = "structure_type";
    public static final String DATATABLE_COL_CRITICAL = "gameCritical";
    public static final String DATATABLE_COL_VALUE1 = "value1";
    public static final String DATATABLE_COL_VALUE2 = "value2";
    public static final String DATATABLE_COL_SCRIPT = "script";
    public static final String DATATABLE_COL_BUILDING_NAME = "name";
    public static final String DATATABLE_COL_TEMPLATE = "template";
    public static final String DATATABLE_COL_BUILD_COST = "cost";
    public static final String DATATABLE_COL_SPAWNER_TYPE = "type";
    public static final String DATATABLE_COL_SPAWNER_PULSE = "pulse";
    public static final String DATATABLE_COL_SPAWNER_MAX_SPAWN = "max_spawn";
    public static final String DATATABLE_COL_SPAWNER_MAX_POPULATION = "max_population";
    public static final String DATATABLE_COL_SPAWNER_TEMPLATE = "spawn_template";
    public static final String DATATABLE_COL_SPAWNER_MIN_NUMBER = "min_number";
    public static final String DATATABLE_COL_SPAWNER_MAX_NUMBER = "max_number";
    public static final String DATATABLE_COL_SPAWNER_WEIGHT = "weight";
    public static final String SCRIPT_BATTLEFIELD_REGION = "systems.battlefield.battlefield_region";
    public static final String SCRIPT_PLAYER_BATTLEFIELD = "systems.battlefield.player_battlefield";
    public static final String SCRIPT_BATTLEFIELD_OBJECT = "systems.battlefield.battlefield_object";
    public static final String SCRIPT_DESTRUCTIBLE_BUILDING = "systems.battlefield.destructible_building";
    public static final String SCRIPT_DESTROY_OBJECTIVE = "systems.battlefield.destroy_objective";
    public static final String SCRIPT_BATTLEFIELD_CONSTRUCTOR = "systems.battlefield.battlefield_constructor";
    public static final String SCRIPT_BARRACKS = "systems.battlefield.barracks";
    public static final String SCRIPT_BATTLEFIELD_SPAWNER = "systems.battlefield.battlefield_spawner";
    public static final String SCRIPT_FACTION_ITEM = "npc.faction_recruiter.faction_item";
    public static final String VAR_BATTLEFIELD = "battlefield";
    public static final String VAR_NAME = "battlefield.name";
    public static final String VAR_PLANET = "battlefield.planet";
    public static final String VAR_VERSION = "battlefield.version";
    public static final String VAR_MASTER_OBJECT = "battlefield.master_object";
    public static final String VAR_EXTENT = "battlefield.extent";
    public static final String VAR_MARKERS = "battlefield.markers";
    public static final String VAR_RESTART_TIME_MIN = "battlefield.restart_time_min";
    public static final String VAR_RESTART_TIME_MAX = "battlefield.restart_time_max";
    public static final String VAR_GAME_DURATION = "battlefield.game_duration";
    public static final String VAR_NEXT_GAME = "battlefield.next_game";
    public static final String VAR_GAME_TYPE = "battlefield.game_type";
    public static final String VAR_FACTIONS_ALLOWED = "battlefield.factions_allowed";
    public static final String VAR_FACTIONS_AI_ALLOWED = "battlefield.factions_ai_allowed";
    public static final String VAR_START_LOCATIONS = "battlefield.start_locations";
    public static final String VAR_BASE_OBJECTS = "battlefield.base_objects";
    public static final String VAR_PLAYERS_IN_BATTLEFIELD = "battlefield.player_in_battlefield";
    public static final String VAR_WAYPOINTS = "battlefield.waypoints";
    public static final String VAR_GAME = "battlefield.game";
    public static final String VAR_TIME_REMAINING = "battlefield.game.time_remaining";
    public static final String VAR_PARTICIPANTS = "battlefield.game.participants";
    public static final String VAR_FACTIONS_REMAINING = "battlefield.game.factions_remaining";
    public static final String VAR_FACTIONS_AI_REMAINING = "battlefield.game.factions_ai_remaining";
    public static final String VAR_BUILD_POINTS = "battlefield.game.build_points_";
    public static final String VAR_KILLS = "battlefield.game.stats.kills_";
    public static final String VAR_DEATHS = "battlefield.game.stats.deaths_";
    public static final String VAR_STAT_ROOT = "battlefield.stats";
    public static final String VAR_STAT_TYPE = "battlefield.stats.type";
    public static final String VAR_STAT_TIME = "battlefield.stats.time";
    public static final String VAR_STAT_WINNER = "battlefield.stats.winner";
    public static final String VAR_STAT_PLAYERS = "battlefield.stats.players";
    public static final String VAR_STAT_KILLS = "battlefield.stats.kills_";
    public static final String VAR_STAT_DEATHS = "battlefield.stats.deaths_";
    public static final String VAR_BATTLEFIELD_ENTERED = "battlefield.battlefield_entered";
    public static final String VAR_BATTLEFIELD_TO_ENTER = "battlefield.battlefield_to_enter";
    public static final String VAR_TEAM_FACTION = "battlefield.team_faction";
    public static final String VAR_TIME_ENTERED = "battlefield.time_entered";
    public static final String VAR_TIME_EXITED = "battlefield.time_exited";
    public static final String VAR_CHATROOM_ALL = "battlefield.chatroom_all";
    public static final String VAR_CHATROOM_FACTION = "battlefield.chatroom_faction";
    public static final String VAR_EXPELLED = "battlefield.expelled";
    public static final String VAR_SELECTING_FACTION = "battlefield.selecting_faction";
    public static final String VAR_SPAWNER_TYPE = "battlefield.spawner.type";
    public static final String VAR_SPAWNER_PULSE = "battlefield.spawner.pulse";
    public static final String VAR_SPAWNER_MAX = "battlefield.spawner.maximum_spawn";
    public static final String VAR_SPAWNER_CURRENT = "battlefield.spawner.current_spawn";
    public static final String VAR_SPAWNER_CURRENT_POPULATION = "battlefield.spawner.current_population";
    public static final String VAR_SPAWNER_MAX_POPULATION = "battlefield.spawner.max_population";
    public static final String VAR_SPAWNED_BY = "battlefield.spawned_by";
    public static final String VAR_SPAWNED_BATTLEFIELD = "battlefield.spawned_battlefield";
    public static final String VAR_GAME_CRITICAL = "battlefield.game_critical";
    public static final String VAR_VALUE1 = "battlefield.value1";
    public static final String VAR_VALUE2 = "battlefield.value2";
    public static final String VAR_COMBAT_DESTROYED = "battlefield.combat_destroyed";
    public static final String VAR_BUILDTIME = "battlefield.buildtime";
    public static final String VAR_TIMESTAMP = "battlefield.timestamp";
    public static final String VAR_HEIGHT = "battlefield.height";
    public static final String VAR_CONSTRUCTED = "battlefield.constructed";
    public static final String VAR_REPAIRING = "battlefield.repairing";
    public static final String VAR_REPAIR_COST = "battlefield.repair_cost";
    public static final String VAR_LAST_BUILD = "battlefield.last_build";
    public static final String VAR_BUILD_RATE = "battlefield.build_rate";
    public static final String VAR_REINFORCEMENT_TEMPLATE = "battlefield.reinforcement_template";
    public static final String VAR_REINFORCEMENT_NAME = "battlefield.reinforcement_name";
    public static final String VAR_REINFORCEMENT_COST = "battlefield.reinforcement_cost";
    public static final String VAR_DECLARED = "faction_recruiter.declared";
    public static final float MARKER_SPACING = 4.0f;
    public static final int GAME_TIME_PULSE = 5;
    public static final float OUTER_PERIMETER_WIDTH = 15.0f;
    public static final float MIN_START_LOCATION_TOLERANCE = 1.0f;
    public static final float MAX_START_LOCATION_TOLERANCE = 5.0f;
    public static final int CONFINEMENT_TIME = 0;
    public static final float DEATH_TIME = 15.0f;
    public static final int STARTING_BUILD_POINTS = 500;
    public static final float BUILD_POINT_RECHARGE_PULSE = 30.0f;
    public static final int BUILD_POINT_RECHARGE_AMOUNT = 10;
    public static final float CONSTRUCTOR_RANGE = 60.0f;
    public static final float REINFORCEMENT_RANGE = 10.0f;
    public static final float REPAIR_RANGE = 10.0f;
    public static final float REPAIR_PULSE = 5.0f;
    public static final int MAXIMUM_POPULATION = 50;
    public static final int MAXIMUM_FACTION_SIZE_DIFFERENCE = 5;
    public static final int CURRENT_VERSION = 3;
    public static final String STRING_TRIG_BOUNDARY = "battlefield_boundary";
    public static final String STRING_TRIG_OUTER_PERIMETER = "battlefield_outer_perimeter";
    public static final String GAME_SCRIPT_PATH = "systems.battlefield.game_";
    public static void createBattlefieldRegions(String area) throws InterruptedException
    {
        int numItems = dataTableGetNumRows(BATTLEFIELD_DATATABLE);
        int numMarkers = dataTableGetNumRows(BATTLEFIELD_MARKER_DATATABLE);
        for (int i = 0; i < numItems; i++)
        {
            dictionary row = dataTableGetRow(BATTLEFIELD_DATATABLE, i);
            String name = row.getString(DATATABLE_COL_NAME);
            String bf_area = row.getString(DATATABLE_COL_AREA);
            if (area.equals(bf_area))
            {
                String type = row.getString(DATATABLE_COL_TYPE);
                float x1 = row.getFloat(DATATABLE_COL_X1);
                float y1 = row.getFloat(DATATABLE_COL_Y1);
                float z1 = row.getFloat(DATATABLE_COL_Z1);
                float radius = row.getFloat(DATATABLE_COL_RADIUS);
                String game_type = row.getString(DATATABLE_COL_GAME_TYPE);
                int restart_min = row.getInt(DATATABLE_COL_RESTART_MIN);
                int restart_max = row.getInt(DATATABLE_COL_RESTART_MAX);
                int game_time = row.getInt(DATATABLE_COL_GAME_TIME);
                int imperial = row.getInt(DATATABLE_COL_IMPERIAL);
                int rebel = row.getInt(DATATABLE_COL_REBEL);
                int hutt = row.getInt(DATATABLE_COL_HUTT);
                LOG("LOG_CHANNEL", "   type ->" + type + "  " + x1 + "/" + y1 + "/" + z1 + "/" + radius);
                if (type == null)
                {
                    continue;
                }
                int regionPvpType;
                if (type.equals("pvp"))
                {
                    regionPvpType = regions.PVP_REGION_TYPE_BATTLEFIELD_PVP;
                }
                else if (type.equals("pve"))
                {
                    regionPvpType = regions.PVP_REGION_TYPE_BATTLEFIELD_PVE;
                }
                else 
                {
                    LOG("LOG_CHANNEL", "   Unsupported pvp type " + type + " read, skipping");
                    continue;
                }
                location loc = new location(x1, y1, z1, bf_area);
                String regionName = "@battlefield:" + name;
                if (getRegionsWithPvPAtPoint(loc, regionPvpType) != null)
                {
                    LOG("LOG_CHANNEL", "   " + type.toUpperCase() + " Battlefield found, skipping...");
                }
                else 
                {
                    int geography = regions.GEO_PLAINS;
                    region testRegion = getSmallestRegionAtPoint(loc);
                    if (testRegion != null)
                    {
                        geography = testRegion.getGeographicalType();
                    }
                    createCircleRegion(loc, radius, regionName, regionPvpType, regions.BUILD_TRUE, regions.MUNI_FALSE, geography, 0, 0, regions.SPAWN_FALSE, regions.MISSION_NONE, true, false);
                }
                String markerName = null;
                float bestRadius = 0.0f;
                for (int j = 0; j < numMarkers; ++j)
                {
                    dictionary markerRow = dataTableGetRow(BATTLEFIELD_MARKER_DATATABLE, j);
                    float markerRadius = markerRow.getFloat(DATATABLE_COL_RADIUS);
                    if (Math.abs(markerRadius - radius) < Math.abs(bestRadius - radius))
                    {
                        bestRadius = markerRadius;
                        markerName = markerRow.getString(DATATABLE_COL_TEMPLATE);
                    }
                }
                if (markerName != null)
                {
                    obj_id marker = createObject(markerName, loc);
                    if (isIdValid(marker))
                    {
                        setBattlefieldMarkerRegionName(marker, regionName);
                        if (exists(marker))
                        {
                            persistObject(marker);
                        }
                    }
                }
            }
        }
        return;
    }
    public static void destroyBattlefieldRegions(String area) throws InterruptedException
    {
        if (area == null)
        {
            return;
        }
        int numItems = dataTableGetNumRows(BATTLEFIELD_DATATABLE);
        for (int i = 0; i < numItems; i++)
        {
            dictionary row = dataTableGetRow(BATTLEFIELD_DATATABLE, i);
            String name = row.getString(DATATABLE_COL_NAME);
            String bf_area = row.getString(DATATABLE_COL_AREA);
            String type = row.getString(DATATABLE_COL_TYPE);
            if (type == null || type.length() < 1)
            {
                continue;
            }
            if (bf_area != null && bf_area.length() > 0)
            {
                if (area.equals(bf_area))
                {
                    LOG("LOG_CHANNEL", "battlefield::destroyBattlefieldRegions -- " + name);
                    float x1 = row.getFloat(DATATABLE_COL_X1);
                    float y1 = row.getFloat(DATATABLE_COL_Y1);
                    float z1 = row.getFloat(DATATABLE_COL_Z1);
                    location loc = new location(x1, y1, z1, bf_area);
                    int regionPvpType;
                    if (type.equals("pvp"))
                    {
                        regionPvpType = regions.PVP_REGION_TYPE_BATTLEFIELD_PVP;
                    }
                    else if (type.equals("pve"))
                    {
                        regionPvpType = regions.PVP_REGION_TYPE_BATTLEFIELD_PVE;
                    }
                    else 
                    {
                        LOG("LOG_CHANNEL", "   Unsupported pvp type " + type + " read, skipping");
                        continue;
                    }
                    region[] regs = getRegionsWithPvPAtPoint(loc, regionPvpType);
                    if (regs != null && regs.length > 0)
                    {
                        for (int j = 0; j < regs.length; j++)
                        {
                            obj_id bf_object = getMasterObjectFromRegion(regs[j]);
                            if (isIdValid(bf_object))
                            {
                                destroyBattlefield(bf_object);
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean updateBattlefieldData(obj_id master_object, region bf) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield::updateBattlefield");
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "battlefield.updateBattlefield -- the battlefield object is null.");
            debugServerConsoleMsg(null, "battlefield.updateBattlefield -- the battlefield object is null.");
            return false;
        }
        if (bf == null)
        {
            bf = getBattlefield(master_object);
            if (bf == null)
            {
                LOG("LOG_CHANNEL", "battlefield.updateBattlefield -- the region is null.");
                debugServerConsoleMsg(null, "battlefield.updateBattlefield -- the region is null.");
                return false;
            }
        }
        String name = getBattlefieldName(bf);
        int idx = dataTableSearchColumnForString(name, DATATABLE_COL_NAME, BATTLEFIELD_DATATABLE);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "battlefield::getSpawnerData -- unable to find an entry for " + name);
            debugServerConsoleMsg(null, "battlefield::getSpawnerData -- unable to find an entry for " + name);
            return false;
        }
        dictionary row = dataTableGetRow(BATTLEFIELD_DATATABLE, idx);
        String type = row.getString(DATATABLE_COL_TYPE);
        float x1 = row.getFloat(DATATABLE_COL_X1);
        float y1 = row.getFloat(DATATABLE_COL_Y1);
        float z1 = row.getFloat(DATATABLE_COL_Z1);
        float radius = row.getFloat(DATATABLE_COL_RADIUS);
        String game_type = row.getString(DATATABLE_COL_GAME_TYPE);
        int restart_min = row.getInt(DATATABLE_COL_RESTART_MIN);
        int restart_max = row.getInt(DATATABLE_COL_RESTART_MAX);
        int game_time = row.getInt(DATATABLE_COL_GAME_TIME);
        int imperial = row.getInt(DATATABLE_COL_IMPERIAL);
        int rebel = row.getInt(DATATABLE_COL_REBEL);
        int hutt = row.getInt(DATATABLE_COL_HUTT);
        setObjVar(master_object, VAR_NAME, bf.getName());
        setObjVar(master_object, VAR_PLANET, bf.getPlanetName());
        setObjVar(master_object, VAR_EXTENT, radius);
        setObjVar(master_object, VAR_GAME_TYPE, game_type);
        setObjVar(master_object, VAR_RESTART_TIME_MIN, restart_min);
        setObjVar(master_object, VAR_RESTART_TIME_MAX, restart_max);
        setObjVar(master_object, VAR_GAME_DURATION, game_time);
        Vector factions_allowed = new Vector();
        factions_allowed.setSize(0);
        Vector factions_ai = new Vector();
        factions_ai.setSize(0);
        LOG("LOG_CHANNEL", "imperial ->" + imperial + " rebel ->" + rebel);
        if (imperial == 1)
        {
            factions_allowed = utils.addElement(factions_allowed, factions.FACTION_IMPERIAL);
        }
        else if (imperial == 2)
        {
            factions_ai = utils.addElement(factions_ai, factions.FACTION_IMPERIAL);
        }
        if (rebel == 1)
        {
            factions_allowed = utils.addElement(factions_allowed, factions.FACTION_REBEL);
        }
        else if (rebel == 2)
        {
            factions_ai = utils.addElement(factions_ai, factions.FACTION_REBEL);
        }
        if (hutt == 1)
        {
            factions_allowed = utils.addElement(factions_allowed, factions.FACTION_HUTT);
        }
        else if (hutt == 2)
        {
            factions_ai = utils.addElement(factions_ai, factions.FACTION_HUTT);
        }
        if (factions_allowed.size() > 0)
        {
            setObjVar(master_object, VAR_FACTIONS_ALLOWED, factions_allowed);
            location[] start_locations = new location[factions_allowed.size()];
            if (start_locations.length > 0)
            {
                setObjVar(master_object, VAR_START_LOCATIONS, start_locations);
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::updateBattlefield -- there are no allowed factions for " + master_object);
            return false;
        }
        if (factions_ai.size() > 0)
        {
            setObjVar(master_object, VAR_FACTIONS_AI_ALLOWED, factions_ai);
        }
        setObjVar(master_object, VAR_VERSION, CURRENT_VERSION);
        return true;
    }
    public static boolean markBattlefield(region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        float radius = getBattlefieldExtent(r) + 2.0f;
        location bf_loc = getBattlefieldLocation(r);
        float degree_incr = 360.0f * MARKER_SPACING / radius;
        if (degree_incr > 90)
        {
            degree_incr = 90;
        }
        float radian_incr = (float)Math.toRadians(degree_incr);
        Vector markers = new Vector();
        markers.setSize(0);
        for (float angle = 0.0f; angle < 2 * Math.PI; angle = angle + radian_incr)
        {
            float x = radius * (float)Math.sin(angle) + bf_loc.x;
            float z = radius * (float)Math.cos(angle) + bf_loc.z;
            float y = getHeightAtLocation(x, z);
            location loc = new location(x, y, z, getCurrentSceneName());
            obj_id marker = createObject(BATTLEFIELD_MARKER, loc);
            persistObject(marker);
            markers = utils.addElement(markers, marker);
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        if (markers.size() > 0)
        {
            setObjVar(master_object, VAR_MARKERS, markers);
        }
        return true;
    }
    public static region getBattlefield(location loc) throws InterruptedException
    {
        region[] rPvP = getRegionsWithPvPAtPoint(loc, regions.PVP_REGION_TYPE_BATTLEFIELD_PVP);
        if (rPvP != null)
        {
            return rPvP[0];
        }
        else 
        {
            region[] rPvE = getRegionsWithPvPAtPoint(loc, regions.PVP_REGION_TYPE_BATTLEFIELD_PVE);
            if (rPvE != null)
            {
                return rPvE[0];
            }
        }
        return null;
    }
    public static region getBattlefield(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return null;
        }
        location loc = getLocation(object);
        return getBattlefield(loc);
    }
    public static boolean isBattlefieldPvP(region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        int pvpType = r.getPvPType();
        if (pvpType == regions.PVP_REGION_TYPE_BATTLEFIELD_PVP)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isBattlefieldPvE(region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        int pvpType = r.getPvPType();
        if (pvpType == regions.PVP_REGION_TYPE_BATTLEFIELD_PVE)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isBattlefieldActive(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        int time = getGameTimeRemaining(master_object);
        if (time == -1)
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static boolean isBattlefieldActive(region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return isBattlefieldActive(master_object);
    }
    public static boolean isFactionTeamMember(obj_id player, String faction, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        obj_id[] faction_team = getFactionTeam(master_object, faction);
        if (faction_team != null)
        {
            for (int i = 0; i < faction_team.length; i++)
            {
                if (player == faction_team[i])
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isFactionAllowed(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (faction == null)
        {
            return false;
        }
        String[] factions_allowed = getFactionsAllowed(master_object);
        if (factions_allowed == null)
        {
            return false;
        }
        int idx = utils.getElementPositionInArray(factions_allowed, faction);
        if (idx == -1)
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public static boolean isFactionRemaining(obj_id master_object, String faction) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield::isFactionRemaining -- " + master_object + " " + faction);
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (!isBattlefieldActive(master_object))
        {
            return false;
        }
        if (!hasObjVar(master_object, VAR_FACTIONS_REMAINING))
        {
            return false;
        }
        else 
        {
            String[] factions_remaining = getFactionsRemaining(master_object);
            int idx = utils.getElementPositionInArray(factions_remaining, faction);
            if (idx == -1)
            {
                return false;
            }
            else 
            {
                return true;
            }
        }
    }
    public static boolean isPlayerFaction(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String[] factions_allowed = getFactionsAllowed(master_object);
        if (factions_allowed != null)
        {
            int idx = utils.getElementPositionInArray(factions_allowed, faction);
            if (idx != -1)
            {
                LOG("LOG_CHANNEL", "battlefield::isPlayerFaction -- true");
                return true;
            }
            else 
            {
                LOG("LOG_CHANNEL", "battlefield::isPlayerFaction -- false");
                return false;
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::isPlayerFaction -- factions_allowed is null.");
            return false;
        }
    }
    public static boolean isAIFaction(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String[] ai_factions = getAIFactionsAllowed(master_object);
        if (ai_factions != null)
        {
            int idx = utils.getElementPositionInArray(ai_factions, faction);
            if (idx != -1)
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::isAIFaction -- ai_factions is null.");
            return false;
        }
    }
    public static boolean hasSelectFactionSui(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("LOG_CHANNEL", "battlefield::hasSelectFactionSui -- " + player + " (false)");
            return false;
        }
        if (utils.hasScriptVar(player, VAR_SELECTING_FACTION))
        {
            LOG("LOG_CHANNEL", "battlefield::hasSelectFactionSui -- " + player + " (true)");
            return true;
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::hasSelectFactionSui -- " + player + " (false)");
            return false;
        }
    }
    public static boolean canJoinFaction(obj_id master_object, obj_id player, String faction) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.canJoinFaction -- " + master_object + "/" + player + "/" + faction);
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String[] factions_allowed = getFactionsAllowed(master_object);
        int total_population = 0;
        obj_id[] players = getParticipantsOnBattlefield(master_object);
        if (players != null)
        {
            total_population = players.length;
        }
        if (total_population >= MAXIMUM_POPULATION)
        {
            LOG("LOG_CHANNEL", player + " ->There are too many combatants currently within the battlefield.");
            sendSystemMessageTestingOnly(player, "There are too many combatants currently within the battlefield.");
            return false;
        }
        Vector factions_allowed_sizes = new Vector();
        factions_allowed_sizes.setSize(0);
        int number_factions = factions_allowed.length;
        int faction_size = 0;
        if (factions_allowed != null)
        {
            for (int i = 0; i < factions_allowed.length; i++)
            {
                obj_id[] faction_team = getFactionTeam(master_object, factions_allowed[i]);
                int size;
                if (faction_team != null)
                {
                    size = faction_team.length;
                }
                else 
                {
                    size = 0;
                }
                factions_allowed_sizes = utils.addElement(factions_allowed_sizes, size);
                if (factions_allowed[i].equals(faction))
                {
                    faction_size = size;
                }
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield.canJoinFaction -- factions_allowed is null for " + master_object);
            return false;
        }
        int max_faction_pop = MAXIMUM_POPULATION / number_factions;
        if (faction_size >= max_faction_pop)
        {
            LOG("LOG_CHANNEL", player + " ->There are too many combantants on the " + faction + " faction to join at this list.");
            sendSystemMessageTestingOnly(player, "There are too many combantants on the " + faction + " faction to join at this list.");
            return false;
        }
        for (int i = 0; i < factions_allowed_sizes.size(); i++)
        {
            if (!factions_allowed[i].equals(faction))
            {
                if (faction_size - ((Integer)factions_allowed_sizes.get(i)).intValue() > MAXIMUM_FACTION_SIZE_DIFFERENCE)
                {
                    LOG("LOG_CHANNEL", player + " ->There are too many combantants on the " + faction + " faction to join at this list.");
                    sendSystemMessageTestingOnly(player, "There are too many combantants on the " + faction + " faction to join at this list.");
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isNearBattlefieldConstructor(obj_id master_object, location loc, String faction) throws InterruptedException
    {
        if (loc.y == 0.0f)
        {
            loc.y = getElevation(loc);
        }
        obj_id[] objects = getObjectsInRange(loc, CONSTRUCTOR_RANGE);
        if (objects != null)
        {
            for (int i = 0; i < objects.length; i++)
            {
                if (hasScript(objects[i], SCRIPT_BATTLEFIELD_CONSTRUCTOR))
                {
                    region bf = getRegionFromMasterObject(master_object);
                    int obj_faction_id = pvpBattlefieldGetFaction(objects[i], bf);
                    String obj_faction = factions.getFactionNameByHashCode(obj_faction_id);
                    if (faction.equals(obj_faction))
                    {
                        LOG("LOG_CHANNEL", "constructor ->" + objects[i]);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isBattlefieldConstructed(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(object, VAR_CONSTRUCTED))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isBattlefieldSpawned(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(object, VAR_SPAWNED_BY))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isSameBattlefieldFaction(obj_id object1, obj_id object2) throws InterruptedException
    {
        if (object1 == null || object1 == obj_id.NULL_ID)
        {
            return false;
        }
        if (object2 == null || object2 == obj_id.NULL_ID)
        {
            return false;
        }
        region bf = battlefield.getBattlefield(object1);
        region bf2 = battlefield.getBattlefield(object2);
        if (bf == null || bf2 == null)
        {
            return false;
        }
        if (!(bf.getName()).equals(bf2.getName()))
        {
            return false;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        int object1_id = pvpBattlefieldGetFaction(object1, bf);
        int object2_id = pvpBattlefieldGetFaction(object2, bf);
        if (object1_id == object2_id)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean canJoinBattlefield(obj_id master_object, obj_id player, boolean verbose) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (!isBattlefieldActive(master_object))
        {
            return true;
        }
        if (pvpHasAnyTempEnemyFlags(player))
        {
            if (verbose)
            {
                LOG("LOG_CHANNEL", player + " ->You cannot enter the battlefield as long as you have temporary enemies.");
                sendSystemMessageTestingOnly(player, "You cannot enter the battlefield as long as you have temporary enemies.");
            }
            return false;
        }
        if (hasObjVar(player, VAR_BATTLEFIELD_ENTERED))
        {
            if (master_object == getObjIdObjVar(player, VAR_BATTLEFIELD_ENTERED))
            {
                if (verbose)
                {
                    LOG("LOG_CHANNEL", player + " ->You must wait for the current battle to end before reentering.");
                    sendSystemMessageTestingOnly(player, "You must wait for the current battle to end before reentering.");
                }
                return false;
            }
            else 
            {
                return true;
            }
        }
        else 
        {
            return true;
        }
    }
    public static boolean canJoinBattlefield(obj_id master_object, obj_id player) throws InterruptedException
    {
        return canJoinBattlefield(master_object, player, false);
    }
    public static boolean canJoinBattlefield(region r, obj_id player) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return canJoinBattlefield(master_object, player);
    }
    public static boolean canBuildBattlefieldStructure(obj_id master_object, obj_id player) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        location loc = getLocation(player);
        region bf = getRegionFromMasterObject(master_object);
        int faction_id = pvpBattlefieldGetFaction(player, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        if (!utils.isProfession(player, utils.TRADER))
        {
            LOG("LOG_CHANNEL", player + " ->You must have skill as an artisan in order to use the battlefield constructor.");
            sendSystemMessageTestingOnly(player, "You must have skill as an artisan in order to use the battlefield constructor.");
            return false;
        }
        if (!isNearBattlefieldConstructor(master_object, loc, faction))
        {
            LOG("LOG_CHANNEL", player + " ->You must be near a battlefield constructor owned by your faction.");
            sendSystemMessageTestingOnly(player, "You must be near a battlefield constructor owned by your faction.");
            return false;
        }
        if (!isBattlefieldActive(master_object))
        {
            LOG("LOG_CHANNEL", player + " ->You can only build in an active battlefield.");
            sendSystemMessageTestingOnly(player, "You can only build in an active battlefield.");
            return false;
        }
        return true;
    }
    public static boolean canBuildReinforcement(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        if (!hasScript(structure, SCRIPT_BARRACKS))
        {
            return false;
        }
        else 
        {
            if (hasObjVar(structure, VAR_LAST_BUILD))
            {
                int time = getGameTime();
                int build_rate = getIntObjVar(structure, VAR_BUILD_RATE);
                if (time - getIntObjVar(structure, VAR_LAST_BUILD) <= build_rate)
                {
                    return false;
                }
                else 
                {
                    return true;
                }
            }
            else 
            {
                return true;
            }
        }
    }
    public static boolean canEnterBattlefield(obj_id master_object, obj_id player) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (!isBattlefieldActive(master_object))
        {
            return true;
        }
        if (hasObjVar(player, VAR_TEAM_FACTION))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean canEnterBattlefield(region r, obj_id player) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return canEnterBattlefield(master_object, player);
    }
    public static boolean canExitBattlefield(obj_id master_object, obj_id player, boolean verbose) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(player, VAR_TIME_ENTERED))
        {
            int time_played = getGameTime() - getIntObjVar(player, VAR_TIME_ENTERED);
            if (time_played < CONFINEMENT_TIME)
            {
                if (verbose)
                {
                    int time_left = CONFINEMENT_TIME - time_played;
                    int[] conv_time = player_structure.convertSecondsTime(time_left);
                    String time_str = player_structure.assembleTimeRemaining(conv_time);
                    LOG("LOG_CHANNEL", player + " ->You may not leave the battlefield until you have played for another " + time_str);
                    sendSystemMessageTestingOnly(player, "You may not leave the battlefield until you have played for another " + time_str);
                }
                return false;
            }
            else 
            {
                return true;
            }
        }
        else 
        {
            return true;
        }
    }
    public static obj_id addBattlefieldObject(obj_id master_object, String template, location loc, float yaw, String faction) throws InterruptedException
    {
        obj_id object = createObject(template, loc);
        if (object == null)
        {
            LOG("LOG_CHANNEL", "battlefield::addBattlefieldObject -- unable to create " + template + " in " + master_object);
            return null;
        }
        setYaw(object, yaw);
        attachScript(object, SCRIPT_BATTLEFIELD_OBJECT);
        int faction_id = getFactionId(faction);
        region bf = getRegionFromMasterObject(master_object);
        pvpBattlefieldSetParticipant(object, bf, faction_id);
        return object;
    }
    public static boolean removeBattlefieldObject(obj_id master_object, obj_id object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        region bf = getRegionFromMasterObject(master_object);
        pvpBattlefieldSetParticipant(object, bf, 0);
        messageTo(object, "msgDestroyBattlefieldObject", null, 0.0f, true);
        return true;
    }
    public static boolean removeBattlefieldObject(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        region bf = getBattlefield(object);
        obj_id master_object = getMasterObjectFromRegion(bf);
        if (master_object == null)
        {
            return false;
        }
        return removeBattlefieldObject(master_object, object);
    }
    public static boolean removeFromBattlefieldObjectList(obj_id master_object, obj_id object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        region bf = getRegionFromMasterObject(master_object);
        pvpBattlefieldSetParticipant(object, bf, 0);
        return true;
    }
    public static obj_id addBattlefieldMob(obj_id master_object, String name, location loc, String faction) throws InterruptedException
    {
        obj_id mob = create.createCreature(name, loc, true);
        if (mob == null)
        {
            LOG("LOG_CHANNEL", "battlefield::addBattlefieldMob -- unable to create " + name + " in " + master_object);
            return null;
        }
        attachScript(mob, SCRIPT_BATTLEFIELD_OBJECT);
        int faction_id = getFactionId(faction);
        region bf = getRegionFromMasterObject(master_object);
        pvpBattlefieldSetParticipant(mob, bf, faction_id);
        return mob;
    }
    public static obj_id addBattlefieldWaypoint(obj_id player, obj_id structure) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return null;
        }
        obj_id waypoint = createWaypointInDatapad(player, structure);
        if (!isIdValid(waypoint))
        {
            return null;
        }
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
        setWaypointName(waypoint, getName(structure));
        Vector waypoint_list = new Vector();
        waypoint_list.setSize(0);
        if (hasObjVar(player, VAR_WAYPOINTS))
        {
            waypoint_list = getResizeableObjIdArrayObjVar(player, VAR_WAYPOINTS);
        }
        waypoint_list = utils.addElement(waypoint_list, waypoint);
        if (waypoint_list.size() > 0)
        {
            setObjVar(player, VAR_WAYPOINTS, waypoint_list);
        }
        LOG("LOG_CHANNEL", "battlefield::AddBattlefieldWaypoint -- adding waypoint(" + waypoint + ") to player " + player + " for " + structure);
        return waypoint;
    }
    public static boolean removeBattlefieldWaypoint(obj_id player, obj_id waypoint) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (waypoint == null || waypoint == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(player, VAR_WAYPOINTS))
        {
            Vector waypoint_list = getResizeableObjIdArrayObjVar(player, VAR_WAYPOINTS);
            int idx = utils.getElementPositionInArray(waypoint_list, waypoint);
            if (idx != -1)
            {
                waypoint_list = utils.removeElementAt(waypoint_list, idx);
                if (waypoint_list.size() < 1)
                {
                    removeObjVar(player, VAR_WAYPOINTS);
                }
                else 
                {
                    setObjVar(player, VAR_WAYPOINTS, waypoint_list);
                }
            }
        }
        destroyObject(waypoint);
        return true;
    }
    public static boolean destroyBattlefield(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        LOG("LOG_CHANNEL", "destroying " + master_object);
        region r = getRegionFromMasterObject(master_object);
        deleteRegion(r);
        if (hasObjVar(master_object, VAR_MARKERS))
        {
            obj_id[] markers = getObjIdArrayObjVar(master_object, VAR_MARKERS);
            for (int i = 0; i < markers.length; i++)
            {
                destroyObject(markers[i]);
            }
        }
        destroyBaseObjects(master_object);
        destroyObject(master_object);
        return true;
    }
    public static boolean destroyBattlefield(region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return destroyBattlefield(master_object);
    }
    public static boolean destroyBaseObjects(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        region reg = getRegionFromMasterObject(master_object);
        obj_id[] base_objects = pvpBattlefieldGetParticipantsForFaction(reg, 0);
        if (base_objects != null)
        {
            for (int i = 0; i < base_objects.length; i++)
            {
                if (hasScript(base_objects[i], SCRIPT_BATTLEFIELD_OBJECT))
                {
                    messageTo(base_objects[i], "msgDestroyBattlefieldObject", null, 30.0f, false);
                }
            }
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static region getRegionFromMasterObject(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String regionPlanet = getStringObjVar(master_object, VAR_PLANET);
        String regionName = getStringObjVar(master_object, VAR_NAME);
        return getRegion(regionPlanet, regionName);
    }
    public static obj_id getMasterObjectFromRegion(region battlefield) throws InterruptedException
    {
        if (battlefield == null)
        {
            return null;
        }
        return getBattlefieldRegionMasterObject(battlefield);
    }
    public static String getBattlefieldName(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String name = getStringObjVar(master_object, VAR_NAME);
        if (name.startsWith("@"))
        {
            name = name.substring(name.indexOf(":") + 1);
        }
        return name;
    }
    public static String getBattlefieldName(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        String name = r.getName();
        if (name.startsWith("@"))
        {
            name = name.substring(name.indexOf(":") + 1);
        }
        return name;
    }
    public static String getBattlefieldLocalizedName(obj_id master_object) throws InterruptedException
    {
        if (!isIdValid(master_object))
        {
            return null;
        }
        String name = (getBattlefieldName(master_object)).toLowerCase();
        string_id name_id = utils.unpackString("@battlefield:" + name);
        return localize(name_id);
    }
    public static float getBattlefieldExtent(region r) throws InterruptedException
    {
        if (r == null)
        {
            return -1.0f;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getFloatObjVar(master_object, VAR_EXTENT);
    }
    public static float getBattlefieldExtent(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1.0f;
        }
        return getFloatObjVar(master_object, VAR_EXTENT);
    }
    public static location getBattlefieldLocation(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        return getLocation(master_object);
    }
    public static location getBattlefieldLocation(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getBattlefieldLocation(master_object);
    }
    public static int getBattlefieldVersion(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(master_object, VAR_VERSION))
        {
            return getIntObjVar(master_object, VAR_VERSION);
        }
        else 
        {
            return -1;
        }
    }
    public static int getGameTimeRemaining(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(master_object, VAR_TIME_REMAINING))
        {
            return getIntObjVar(master_object, VAR_TIME_REMAINING);
        }
        else 
        {
            return -1;
        }
    }
    public static int getGameTimeRemaining(region r) throws InterruptedException
    {
        if (r == null)
        {
            return -1;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getGameTimeRemaining(master_object);
    }
    public static String getBattlefieldGameType(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        return getStringObjVar(master_object, VAR_GAME_TYPE);
    }
    public static String getBattlefieldGameType(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getBattlefieldGameType(master_object);
    }
    public static int getNextGameTime(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(master_object, VAR_NEXT_GAME))
        {
            return getIntObjVar(master_object, VAR_NEXT_GAME);
        }
        else 
        {
            return -1;
        }
    }
    public static int getNextGameTime(region r) throws InterruptedException
    {
        if (r == null)
        {
            return -1;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getNextGameTime(master_object);
    }
    public static int getGameDuration(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        return getIntObjVar(master_object, VAR_GAME_DURATION);
    }
    public static int getGameDuration(region r) throws InterruptedException
    {
        if (r == null)
        {
            return -1;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getGameDuration(master_object);
    }
    public static int getTimeSpentInBattlefield(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return -1;
        }
        region bf = getBattlefield(player);
        if (bf == null)
        {
            return -1;
        }
        int time_entered = getIntObjVar(player, VAR_TIME_ENTERED);
        int time_exited = getGameTime();
        if (hasObjVar(player, VAR_TIME_EXITED))
        {
            time_exited = getIntObjVar(player, VAR_TIME_EXITED);
        }
        return time_exited - time_entered;
    }
    public static String[] getBuildableStructures(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        int idx = getBattlefieldIndex(master_object, BUILDABLE_STRUCTURE_DATATABLE);
        if (idx == -1)
        {
            return null;
        }
        Vector buildable_objects = new Vector();
        buildable_objects.setSize(0);
        int num_items = dataTableGetNumRows(BUILDABLE_STRUCTURE_DATATABLE);
        if (num_items > idx + 1)
        {
            for (int i = idx + 1; i < num_items; i++)
            {
                dictionary row = dataTableGetRow(BUILDABLE_STRUCTURE_DATATABLE, i);
                String bf_name = row.getString(DATATABLE_COL_NAME);
                String structure_faction = row.getString(DATATABLE_COL_FACTION);
                if (bf_name.length() > 0)
                {
                    String[] _buildable_objects = new String[0];
                    if (buildable_objects != null)
                    {
                        _buildable_objects = new String[buildable_objects.size()];
                        buildable_objects.toArray(_buildable_objects);
                    }
                    return _buildable_objects;
                }
                if (structure_faction.equals(faction) || structure_faction.equals("ALL") || faction.equals("ANY"))
                {
                    String structure_name = row.getString(DATATABLE_COL_BUILDING_NAME);
                    buildable_objects = utils.addElement(buildable_objects, structure_name);
                }
            }
        }
        if (buildable_objects.size() > 0)
        {
            String[] _buildable_objects = new String[0];
            if (buildable_objects != null)
            {
                _buildable_objects = new String[buildable_objects.size()];
                buildable_objects.toArray(_buildable_objects);
            }
            return _buildable_objects;
        }
        else 
        {
            return null;
        }
    }
    public static dictionary getBuildableStructureStats(obj_id master_object, int index) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        int bf_idx = getBattlefieldIndex(master_object, BUILDABLE_STRUCTURE_DATATABLE);
        if (bf_idx == -1)
        {
            return null;
        }
        String[] buildable_structures = getBuildableStructures(master_object, "ANY");
        if (index > buildable_structures.length)
        {
            return null;
        }
        int structure_idx = bf_idx + index + 1;
        int num_items = dataTableGetNumRows(BUILDABLE_STRUCTURE_DATATABLE);
        if (num_items > structure_idx)
        {
            return dataTableGetRow(BUILDABLE_STRUCTURE_DATATABLE, structure_idx);
        }
        else 
        {
            return null;
        }
    }
    public static dictionary getBuildableStructureStats(obj_id master_object, String template) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        int bf_idx = getBattlefieldIndex(master_object, BUILDABLE_STRUCTURE_DATATABLE);
        if (bf_idx == -1)
        {
            return null;
        }
        String[] structures = getBuildableStructures(master_object, "ANY");
        if (structures == null)
        {
            return null;
        }
        for (int i = 0; i < structures.length; i++)
        {
            dictionary stats = dataTableGetRow(BUILDABLE_STRUCTURE_DATATABLE, i + 1 + bf_idx);
            if ((stats.getString("template")).equals(template))
            {
                return stats;
            }
            else if ((stats.getString("name")).equals(template))
            {
                return stats;
            }
        }
        return null;
    }
    public static boolean attachGameScript(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String script = GAME_SCRIPT_PATH + (getBattlefieldGameType(master_object)).toLowerCase();
        LOG("LOG_CHANNEL", "battlefield::attachGameScript -- attaching " + script + " to " + master_object);
        if (!hasScript(master_object, script))
        {
            attachScript(master_object, script);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean detachGameScript(obj_id master_object) throws InterruptedException
    {
        if (!isIdValid(master_object))
        {
            LOG("LOG_CHANNEL", "battlefield::detachGameScript -- master_object is null");
            return false;
        }
        String script;
        String game_type = getBattlefieldGameType(master_object);
        if (game_type != null)
        {
            script = GAME_SCRIPT_PATH + game_type.toLowerCase();
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::detachGameScript -- game_type is null for " + master_object);
            return false;
        }
        LOG("LOG_CHANNEL", "battlefield::detachGameScript -- detaching " + script + " to " + master_object);
        if (hasScript(master_object, script))
        {
            detachScript(master_object, script);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static String getChatRoomName(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String bf_name = getBattlefieldName(master_object);
        String chat_name = "SWG." + getGalaxyName() + "." + getCurrentSceneName() + ".battlefield." + bf_name;
        return chat_name;
    }
    public static String getChatRoomNameAllFactions(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        return getChatRoomName(master_object) + ".allFactions";
    }
    public static String getChatRoomNameFaction(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        return getChatRoomName(master_object) + "." + faction;
    }
    public static int getFactionKills(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        String objVar_team_kills = VAR_KILLS + faction;
        if (hasObjVar(master_object, objVar_team_kills))
        {
            return getIntObjVar(master_object, objVar_team_kills);
        }
        else 
        {
            return 0;
        }
    }
    public static int getFactionDeaths(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        String objVar_team_deaths = VAR_DEATHS + faction;
        if (hasObjVar(master_object, objVar_team_deaths))
        {
            return getIntObjVar(master_object, objVar_team_deaths);
        }
        else 
        {
            return 0;
        }
    }
    public static obj_id[] getParticipantsOnBattlefield(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(master_object, VAR_PARTICIPANTS))
        {
            return utils.getObjIdBatchObjVar(master_object, VAR_PARTICIPANTS);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getParticipantsOnBattlefield(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getParticipantsOnBattlefield(master_object);
    }
    public static obj_id[] getFactionTeam(obj_id master_object, String faction) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield.getFactionTeam");
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        obj_id[] players = getPlayersOnBattlefield(master_object);
        Vector faction_team = new Vector();
        faction_team.setSize(0);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                String player_faction = getPlayerTeamFaction(players[i]);
                if (player_faction != null)
                {
                    if (player_faction.equals(faction))
                    {
                        faction_team = utils.addElement(faction_team, players[i]);
                    }
                }
            }
        }
        else 
        {
            return null;
        }
        obj_id[] _faction_team = new obj_id[0];
        if (faction_team != null)
        {
            _faction_team = new obj_id[faction_team.size()];
            faction_team.toArray(_faction_team);
        }
        return _faction_team;
    }
    public static obj_id[] getFactionTeam(region r, String faction) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getFactionTeam(master_object, faction);
    }
    public static String[] getFactionsAllowed(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        return getStringArrayObjVar(master_object, VAR_FACTIONS_ALLOWED);
    }
    public static String[] getFactionsAllowed(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getFactionsAllowed(master_object);
    }
    public static String[] getFactionsRemaining(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(master_object, VAR_FACTIONS_REMAINING))
        {
            return getStringArrayObjVar(master_object, VAR_FACTIONS_REMAINING);
        }
        else 
        {
            return null;
        }
    }
    public static String[] getAIFactionsAllowed(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(master_object, VAR_FACTIONS_AI_ALLOWED))
        {
            return getStringArrayObjVar(master_object, VAR_FACTIONS_AI_ALLOWED);
        }
        else 
        {
            return null;
        }
    }
    public static String[] getAIFactionsRemaining(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(master_object, VAR_FACTIONS_AI_REMAINING))
        {
            return getStringArrayObjVar(master_object, VAR_FACTIONS_AI_REMAINING);
        }
        else 
        {
            return null;
        }
    }
    public static String[] getAllFactionsRemaining(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String[] factions_remaining_array = getFactionsRemaining(master_object);
        Vector factions_remaining = new Vector(Arrays.asList(factions_remaining_array));
        String[] ai_factions = getAIFactionsRemaining(master_object);
        if (factions_remaining != null && ai_factions != null)
        {
            factions_remaining = utils.concatArrays(factions_remaining, ai_factions);
            String[] _factions_remaining = new String[0];
            if (factions_remaining != null)
            {
                _factions_remaining = new String[factions_remaining.size()];
                factions_remaining.toArray(_factions_remaining);
            }
            return _factions_remaining;
        }
        else if (ai_factions == null)
        {
            String[] _factions_remaining = new String[0];
            if (factions_remaining != null)
            {
                _factions_remaining = new String[factions_remaining.size()];
                factions_remaining.toArray(_factions_remaining);
            }
            return _factions_remaining;
        }
        else if (factions_remaining == null)
        {
            return ai_factions;
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::getAllFactionsRemaining -- there are no factions remaining for " + master_object);
            return null;
        }
    }
    public static String[] getAllFactionsAllowed(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String[] factions_allowed_array = getFactionsAllowed(master_object);
        Vector factions_allowed = new Vector(Arrays.asList(factions_allowed_array));
        String[] ai_factions = getAIFactionsAllowed(master_object);
        if (factions_allowed != null && ai_factions != null)
        {
            factions_allowed = utils.concatArrays(factions_allowed, ai_factions);
            String[] _factions_allowed = new String[0];
            if (factions_allowed != null)
            {
                _factions_allowed = new String[factions_allowed.size()];
                factions_allowed.toArray(_factions_allowed);
            }
            return _factions_allowed;
        }
        else if (ai_factions == null)
        {
            String[] _factions_allowed = new String[0];
            if (factions_allowed != null)
            {
                _factions_allowed = new String[factions_allowed.size()];
                factions_allowed.toArray(_factions_allowed);
            }
            return _factions_allowed;
        }
        else if (factions_allowed == null)
        {
            return ai_factions;
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield::getAllFactionsRemaining -- there are no factions remaining for " + master_object);
            return null;
        }
    }
    public static int getFactionId(String faction) throws InterruptedException
    {
        int faction_num = factions.getFactionNumber(faction);
        if (faction_num == -1)
        {
            return 0;
        }
        int faction_id = dataTableGetInt("datatables/faction/faction.iff", faction_num, "pvpFaction");
        return faction_id;
    }
    public static int getGameRestartMinimum(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        return getIntObjVar(master_object, VAR_RESTART_TIME_MIN);
    }
    public static int getGameRestartMaximum(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        return getIntObjVar(master_object, VAR_RESTART_TIME_MAX);
    }
    public static int getFactionBuildPoints(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        String objVar_name = VAR_BUILD_POINTS + faction;
        if (hasObjVar(master_object, objVar_name))
        {
            return getIntObjVar(master_object, objVar_name);
        }
        else 
        {
            return -1;
        }
    }
    public static obj_id[] getGameCriticalObjects(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        Vector critical_items = new Vector();
        critical_items.setSize(0);
        region bf = getRegionFromMasterObject(master_object);
        obj_id[] items = pvpBattlefieldGetParticipantsForFaction(bf, 0);
        if (items != null)
        {
            for (int i = 0; i < items.length; i++)
            {
                if (hasObjVar(items[i], VAR_GAME_CRITICAL))
                {
                    critical_items = utils.addElement(critical_items, items[i]);
                }
            }
        }
        if (critical_items.size() > 0)
        {
            obj_id[] _critical_items = new obj_id[0];
            if (critical_items != null)
            {
                _critical_items = new obj_id[critical_items.size()];
                critical_items.toArray(_critical_items);
            }
            return _critical_items;
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getPlayersOnBattlefieldComplete(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        float radius = getBattlefieldExtent(master_object);
        location loc = getLocation(master_object);
        Vector players = new Vector();
        players.setSize(0);
        obj_id[] items = getObjectsInRange(loc, radius);
        if (items != null)
        {
            for (int i = 0; i < items.length; i++)
            {
                if (isPlayer(items[i]))
                {
                    players = utils.addElement(players, items[i]);
                }
                else if (player_structure.isBuilding(items[i]))
                {
                    obj_id[] player_building = player_structure.getPlayersInBuilding(items[i]);
                    if (player_building != null)
                    {
                        players = utils.concatArrays(players, player_building);
                    }
                }
            }
        }
        obj_id[] _players = new obj_id[0];
        if (players != null)
        {
            _players = new obj_id[players.size()];
            players.toArray(_players);
        }
        return _players;
    }
    public static obj_id[] getPlayersOnBattlefield(obj_id master_object) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD))
        {
            obj_id[] players = utils.getObjIdBatchObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD);
            if (players == null || players.length < 1)
            {
                LOG("LOG_CHANNEL", "battlefield.getPlayersOnBattlefield -- players is null.");
                return null;
            }
            Vector loaded_players = new Vector();
            loaded_players.setSize(0);
            for (int i = 0; i < players.length; i++)
            {
                LOG("LOG_CHANNEL", "battlefield::getPlayersOnBattlefield -- players[" + i + "] ->" + players[i]);
                if (players[i].isLoaded())
                {
                    loaded_players = utils.addElement(loaded_players, players[i]);
                }
            }
            if (loaded_players.size() < 0)
            {
                return null;
            }
            else 
            {
                obj_id[] _loaded_players = new obj_id[0];
                if (loaded_players != null)
                {
                    _loaded_players = new obj_id[loaded_players.size()];
                    loaded_players.toArray(_loaded_players);
                }
                return _loaded_players;
            }
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] getPlayersOnBattlefield(region r) throws InterruptedException
    {
        if (r == null)
        {
            return null;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return getPlayersOnBattlefield(master_object);
    }
    public static String getPlayerTeamFaction(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(player, VAR_TEAM_FACTION))
        {
            return getStringObjVar(player, VAR_TEAM_FACTION);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getBattlefieldEntered(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(player, VAR_BATTLEFIELD_ENTERED))
        {
            return getObjIdObjVar(player, VAR_BATTLEFIELD_ENTERED);
        }
        else 
        {
            return null;
        }
    }
    public static int getBattlefieldIndex(obj_id master_object, String datatable) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        int numItems = dataTableGetNumRows(datatable);
        String bf_name = getBattlefieldName(master_object);
        String bf_area = getCurrentSceneName();
        for (int i = 0; i < numItems; i++)
        {
            String name = dataTableGetString(datatable, i, DATATABLE_COL_NAME);
            if (datatable.equals(BATTLEFIELD_DATATABLE))
            {
                String area = dataTableGetString(datatable, i, DATATABLE_COL_AREA);
                if (area.equals(bf_area))
                {
                    if (name.equals(bf_name))
                    {
                        return i;
                    }
                }
            }
            else 
            {
                if (name.equals(bf_name))
                {
                    return i;
                }
            }
        }
        return -1;
    }
    public static location getFactionStartLocation(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return null;
        }
        String[] factions_allowed = getFactionsAllowed(master_object);
        if (faction == null)
        {
            return null;
        }
        int idx = utils.getElementPositionInArray(factions_allowed, faction);
        if (idx == -1)
        {
            return null;
        }
        location[] start_locations = getLocationArrayObjVar(master_object, VAR_START_LOCATIONS);
        location loc = (location)start_locations[idx].clone();
        if (loc.x == 0.0f && loc.y == 0.0f && loc.z == 0.0f)
        {
            return null;
        }
        else 
        {
            return loc;
        }
    }
    public static dictionary getSpawnerData(String spawner_name) throws InterruptedException
    {
        if (spawner_name == null)
        {
            LOG("LOG_CHANNEL", "battlefield::getSpawnerData -- spawner_name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(spawner_name, DATATABLE_COL_SPAWNER_TYPE, SPAWNER_DATATABLE);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "battlefield::getSpawnerData -- unable to find an entry for " + spawner_name);
            return null;
        }
        int num_items = dataTableGetNumRows(SPAWNER_DATATABLE);
        Vector spawn_template = new Vector();
        spawn_template.setSize(0);
        Vector min_number = new Vector();
        min_number.setSize(0);
        Vector max_number = new Vector();
        max_number.setSize(0);
        Vector weight = new Vector();
        weight.setSize(0);
        for (int i = idx + 1; i < num_items; i++)
        {
            dictionary row = dataTableGetRow(SPAWNER_DATATABLE, i);
            if ((row.getString(DATATABLE_COL_SPAWNER_TYPE)).length() > 0)
            {
                break;
            }
            else 
            {
                spawn_template = utils.addElement(spawn_template, row.getString(DATATABLE_COL_SPAWNER_TEMPLATE));
                min_number = utils.addElement(min_number, row.getInt(DATATABLE_COL_SPAWNER_MIN_NUMBER));
                max_number = utils.addElement(max_number, row.getInt(DATATABLE_COL_SPAWNER_MAX_NUMBER));
                weight = utils.addElement(weight, row.getInt(DATATABLE_COL_SPAWNER_WEIGHT));
            }
        }
        dictionary spawn_data = new dictionary();
        spawn_data.put("spawn_template", spawn_template);
        spawn_data.put("min_number", min_number);
        spawn_data.put("max_number", max_number);
        spawn_data.put("weight", weight);
        return spawn_data;
    }
    public static obj_id[] createRandomSpawn(obj_id master_object, obj_id spawner) throws InterruptedException
    {
        if (spawner == null || spawner == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- spawner is null.");
            return null;
        }
        int max_population = getIntObjVar(spawner, VAR_SPAWNER_MAX_POPULATION);
        int max_spawn = getIntObjVar(spawner, VAR_SPAWNER_MAX);
        int current_population = 0;
        if (hasObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION))
        {
            current_population = getIntObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION);
        }
        int current_spawn = 0;
        if (hasObjVar(spawner, VAR_SPAWNER_CURRENT))
        {
            current_spawn = getIntObjVar(spawner, VAR_SPAWNER_CURRENT);
        }
        if (current_population >= max_population)
        {
            return null;
        }
        if (current_spawn >= max_spawn)
        {
            return null;
        }
        String spawner_type = getStringObjVar(spawner, VAR_SPAWNER_TYPE);
        dictionary spawn_data = getSpawnerData(spawner_type);
        if (spawn_data == null)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- spawn_data is null for " + spawner);
            return null;
        }
        String[] spawn_template = spawn_data.getStringArray("spawn_template");
        int[] min_number = spawn_data.getIntArray("min_number");
        int[] max_number = spawn_data.getIntArray("max_number");
        int[] weight = spawn_data.getIntArray("weight");
        if (spawn_template == null || min_number == null || max_number == null || weight == null)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- spawn data contains a null array.");
            return null;
        }
        int total_weight = 0;
        for (int i = 0; i < weight.length; i++)
        {
            total_weight = total_weight + weight[i];
        }
        if (total_weight < 1)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- total probility weight is < 1 for " + spawner);
            return null;
        }
        int weight_index = rand(1, total_weight);
        int array_index = -1;
        for (int i = 0; i < weight.length; i++)
        {
            weight_index = weight_index - weight[i];
            if (weight_index < 1)
            {
                array_index = i;
                break;
            }
        }
        if (array_index == -1)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- unable to find an array index for " + spawner);
            return null;
        }
        int num_spawn = rand(min_number[array_index], max_number[array_index]);
        String template = spawn_template[array_index];
        location spawner_loc = getLocation(spawner);
        region bf = getRegionFromMasterObject(master_object);
        int faction_id = pvpBattlefieldGetFaction(spawner, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        Vector spawned_mobs = new Vector();
        spawned_mobs.setSize(0);
        while (num_spawn > 0)
        {
            if (max_population > current_population)
            {
                location spawn_loc = utils.getRandomAwayLocation(spawner_loc, 5.0f, 10.0f);
                obj_id mob = addBattlefieldMob(master_object, template, spawn_loc, faction);
                wander(mob);
                spawned_mobs = utils.addElement(spawned_mobs, mob);
                setObjVar(mob, battlefield.VAR_CONSTRUCTED, 1);
                setObjVar(mob, VAR_SPAWNED_BY, spawner);
                setObjVar(mob, VAR_SPAWNED_BATTLEFIELD, master_object);
                current_population++;
                num_spawn--;
            }
            else 
            {
                break;
            }
        }
        if (spawned_mobs.size() < 1)
        {
            LOG("LOG_CHANNEL", "battlefield::createRandomSpawn -- unable to create spawn for " + spawner);
            return null;
        }
        else 
        {
            setObjVar(spawner, VAR_SPAWNER_CURRENT_POPULATION, current_population);
            setObjVar(spawner, VAR_SPAWNER_CURRENT, current_spawn + 1);
            obj_id[] _spawned_mobs = new obj_id[0];
            if (spawned_mobs != null)
            {
                _spawned_mobs = new obj_id[spawned_mobs.size()];
                spawned_mobs.toArray(_spawned_mobs);
            }
            return _spawned_mobs;
        }
    }
    public static boolean sendBattlefieldMessage(obj_id master_object, String text) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String chatroom = getChatRoomNameAllFactions(master_object);
        LOG("LOG_CHANNEL", "chatroom ->" + chatroom);
        chatSendToRoom(chatroom, text, "");
        LOG("LOG_CHANNEL", master_object + " (Battlefield Message) ->" + text);
        return true;
    }
    public static boolean sendBattlefieldMessage(region r, String text) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return sendBattlefieldMessage(master_object, text);
    }
    public static boolean sendFactionMessage(obj_id master_object, String faction, String text) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String[] factions_allowed = getFactionsAllowed(master_object);
        int idx = utils.getElementPositionInArray(factions_allowed, faction);
        if (idx != -1)
        {
            String chatroom = getChatRoomNameFaction(master_object, faction);
            chatSendToRoom(chatroom, text, "");
            LOG("LOG_CHANNEL", master_object + " (Battlefield " + faction + " Message) ->" + text);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean sendFactionMessage(region r, String faction, String text) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return sendFactionMessage(master_object, faction, text);
    }
    public static boolean registerBattlefieldKill(obj_id killer, obj_id victim, obj_id master_object) throws InterruptedException
    {
        if (killer == null || killer == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (victim == null || victim == obj_id.NULL_ID)
        {
            return false;
        }
        if (!isBattlefieldActive(master_object))
        {
            return false;
        }
        region bf = getRegionFromMasterObject(master_object);
        if (isMob(killer) && isMob(victim))
        {
            if (killer != victim)
            {
                int faction_id = pvpBattlefieldGetFaction(killer, bf);
                String faction = factions.getFactionNameByHashCode(faction_id);
                if (faction == null)
                {
                    return false;
                }
                int faction_kills = 0;
                String objVar_team_kills = VAR_KILLS + faction;
                if (hasObjVar(master_object, objVar_team_kills))
                {
                    faction_kills = getIntObjVar(master_object, objVar_team_kills);
                }
                faction_kills++;
                setObjVar(master_object, objVar_team_kills, faction_kills);
            }
        }
        if (isMob(victim))
        {
            int faction_id = pvpBattlefieldGetFaction(victim, bf);
            String faction = factions.getFactionNameByHashCode(faction_id);
            if (faction == null)
            {
                return false;
            }
            int faction_deaths = 0;
            String objVar_team_deaths = VAR_DEATHS + faction;
            if (hasObjVar(master_object, objVar_team_deaths))
            {
                faction_deaths = getIntObjVar(master_object, objVar_team_deaths);
            }
            faction_deaths++;
            setObjVar(master_object, objVar_team_deaths, faction_deaths);
        }
        dictionary params = new dictionary();
        params.put("killer", killer);
        params.put("victim", victim);
        messageTo(master_object, "msgBattlefieldKill", params, 0.0f, true);
        return true;
    }
    public static boolean addPlayerToPlayerList(obj_id player, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        Vector players = new Vector();
        players.setSize(0);
        if (hasObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD))
        {
            players = utils.getResizeableObjIdBatchObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD);
        }
        int idx = utils.getElementPositionInArray(players, player);
        if (idx != -1)
        {
            return false;
        }
        else 
        {
            players = utils.addElement(players, player);
            removeObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD);
            utils.setResizeableBatchObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD, players);
        }
        return true;
    }
    public static boolean removePlayerFromPlayerList(obj_id player, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (!hasObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD))
        {
            return false;
        }
        Vector players = utils.getResizeableObjIdBatchObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD);
        int idx = utils.getElementPositionInArray(players, player);
        if (idx == -1)
        {
            return false;
        }
        else 
        {
            players = utils.removeElementAt(players, idx);
            removeObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD);
            utils.setResizeableBatchObjVar(master_object, VAR_PLAYERS_IN_BATTLEFIELD, players);
            return true;
        }
    }
    public static boolean addPlayerToTeam(obj_id player, String faction, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String player_faction = getPlayerTeamFaction(player);
        if (player_faction != null)
        {
            return false;
        }
        Vector participants = new Vector();
        participants.setSize(0);
        if (hasObjVar(master_object, battlefield.VAR_PARTICIPANTS))
        {
            participants = utils.getResizeableObjIdBatchObjVar(master_object, battlefield.VAR_PARTICIPANTS);
        }
        int part_idx = utils.getElementPositionInArray(participants, player);
        if (part_idx == -1)
        {
            participants = utils.addElement(participants, player);
        }
        String[] factions_allowed = getStringArrayObjVar(master_object, VAR_FACTIONS_ALLOWED);
        int idx = utils.getElementPositionInArray(factions_allowed, faction);
        if (idx == -1)
        {
            return false;
        }
        utils.setResizeableBatchObjVar(master_object, VAR_PARTICIPANTS, participants);
        setObjVar(player, VAR_TEAM_FACTION, faction);
        if (movePlayerToStartLocation(player, master_object))
        {
            LOG("LOG_CHANNEL", player + " ->You have been moved to your faction's starting location. Good luck!");
            sendSystemMessageTestingOnly(player, "You have been moved to your faction's starting location. Good luck!");
            sendSystemMessageTestingOnly(player, "Note: use /battlefieldStatus to check on your faction's progress and /placeBattlefieldStructure to build.");
        }
        region reg = getRegionFromMasterObject(master_object);
        pvpBattlefieldSetParticipant(player, reg, getFactionId(faction));
        dictionary params = new dictionary();
        String chat_room = battlefield.getChatRoomNameAllFactions(master_object);
        params.put("chat_room", chat_room);
        messageTo(player, "msgJoinBattlefieldChat", params, 0.0f, false);
        if (factions_allowed.length > 1)
        {
            dictionary params_team = new dictionary();
            String chat_room_team = battlefield.getChatRoomNameFaction(master_object, faction);
            params_team.put("chat_room", chat_room_team);
            messageTo(player, "msgJoinBattlefieldChat", params_team, 0.0f, false);
        }
        sendFactionMessage(master_object, faction, getFirstName(player) + " has joined the team.");
        dictionary params_player = new dictionary();
        params_player.put("player", player);
        params_player.put("faction", faction);
        messageTo(master_object, "msgAddPlayerToBattlefield", params_player, 3.0f, false);
        LOG("LOG_CHANNEL", "battlefield::addPlayerToTeam -- added " + player);
        return true;
    }
    public static boolean addPlayerToTeam(obj_id player, String faction, region r) throws InterruptedException
    {
        if (r == null)
        {
            return false;
        }
        obj_id master_object = getMasterObjectFromRegion(r);
        return addPlayerToTeam(player, faction, master_object);
    }
    public static boolean movePlayerToStartLocation(obj_id player, obj_id master_object) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "movePlayerToStartLocation --" + player + "   " + master_object);
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String faction = getPlayerTeamFaction(player);
        location start_loc = getFactionStartLocation(master_object, faction);
        if (start_loc == null)
        {
            return false;
        }
        location rand_loc = utils.getRandomAwayLocation(start_loc, MIN_START_LOCATION_TOLERANCE, MAX_START_LOCATION_TOLERANCE);
        setLocation(player, rand_loc);
        return true;
    }
    public static boolean expelPlayerFromBattlefield(obj_id player, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        setObjVar(player, VAR_EXPELLED, 1);
        expelFromTriggerVolume(master_object, STRING_TRIG_OUTER_PERIMETER, player);
        return true;
    }
    public static boolean removeFactionItems(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        int pvp_type = pvpGetType(player);
        if (pvp_type == PVPTYPE_DECLARED)
        {
            return true;
        }
        factions.unequipFactionEquipment(player, true);
        return true;
    }
    public static int setGameTimer(obj_id master_object, boolean reset) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return -1;
        }
        if (reset)
        {
            int game_time = getGameDuration(master_object);
            setObjVar(master_object, VAR_TIME_REMAINING, game_time);
            return game_time;
        }
        else 
        {
            if (hasObjVar(master_object, VAR_TIME_REMAINING))
            {
                int time_remaining = getIntObjVar(master_object, VAR_TIME_REMAINING);
                time_remaining = time_remaining - GAME_TIME_PULSE;
                setObjVar(master_object, VAR_TIME_REMAINING, time_remaining);
                if (time_remaining < 1)
                {
                    return 0;
                }
                else 
                {
                    return time_remaining;
                }
            }
            else 
            {
                return 0;
            }
        }
    }
    public static boolean setBattlefieldEntered(obj_id player, obj_id master_object) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        setObjVar(player, VAR_BATTLEFIELD_ENTERED, master_object);
        setObjVar(player, VAR_TIME_ENTERED, getGameTime());
        return true;
    }
    public static boolean decrementFactionBuildPoints(obj_id master_object, String faction, int amt) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        int build_points = getFactionBuildPoints(master_object, faction);
        if (amt > build_points)
        {
            return false;
        }
        build_points = build_points - amt;
        String objVar_name = VAR_BUILD_POINTS + faction;
        setObjVar(master_object, objVar_name, build_points);
        return true;
    }
    public static boolean incrementFactionBuildPoints(obj_id master_object, String faction, int amt) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        int build_points = getFactionBuildPoints(master_object, faction);
        if (build_points == -1)
        {
            return false;
        }
        build_points = build_points + amt;
        String objVar_name = VAR_BUILD_POINTS + faction;
        setObjVar(master_object, objVar_name, build_points);
        return true;
    }
    public static boolean spendFactionPoints(obj_id player, float amt) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (amt < 1)
        {
            return false;
        }
        String faction = getPlayerTeamFaction(player);
        if (faction == null)
        {
            return false;
        }
        float points = factions.getFactionStanding(player, faction);
        int declared_faction = pvpGetAlignedFaction(player);
        if (declared_faction != 0)
        {
            points = points - factions.FACTION_RATING_DECLARABLE_MIN;
        }
        if (amt > points)
        {
            LOG("LOG_CHANNEL", player + " ->You do not have enough faction points remaining.");
            sendSystemMessageTestingOnly(player, "You do not have enough faction points remaining.");
            if (declared_faction != 0)
            {
                sendSystemMessageTestingOnly(player, "Note that your faction may not go below the " + factions.FACTION_RATING_DECLARABLE_MIN + " declared minimum.");
            }
            return false;
        }
        else 
        {
            factions.addFactionStanding(player, faction, amt * -1);
            return true;
        }
    }
    public static boolean eliminateFaction(obj_id master_object, String faction) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        String[] factions_remaining_array = getFactionsRemaining(master_object);
        Vector factions_remaining = new Vector(Arrays.asList(factions_remaining_array));
        if (factions_remaining == null)
        {
            return false;
        }
        int idx = utils.getElementPositionInArray(factions_remaining, faction);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "battlefield.eliminateFaction -- eliminate ai faction " + faction);
            String[] ai_factions_array = getAIFactionsRemaining(master_object);
            Vector ai_factions = new Vector(Arrays.asList(ai_factions_array));
            int ai_idx = utils.getElementPositionInArray(ai_factions, faction);
            if (ai_idx == -1)
            {
                LOG("LOG_CHANNEL", "battlefield::eliminateFaction -- cannot find " + faction + " in " + master_object);
                return false;
            }
            else 
            {
                ai_factions = utils.removeElementAt(ai_factions, idx);
                if (ai_factions.size() > 0)
                {
                    setObjVar(master_object, VAR_FACTIONS_AI_REMAINING, ai_factions);
                }
                else 
                {
                    if (hasObjVar(master_object, VAR_FACTIONS_AI_REMAINING))
                    {
                        removeObjVar(master_object, VAR_FACTIONS_AI_REMAINING);
                    }
                }
                region bf = getRegionFromMasterObject(master_object);
                obj_id[] base_objects = pvpBattlefieldGetParticipantsForFaction(bf, 0);
                if (base_objects != null)
                {
                    for (int i = 0; i < base_objects.length; i++)
                    {
                        if (isBattlefieldSpawned(base_objects[i]))
                        {
                            destroyObject(base_objects[i]);
                        }
                    }
                }
                sendBattlefieldMessage(master_object, "The " + faction + " faction has been eliminated from the battle.");
                return true;
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "battlefield.eliminateFaction -- eliminate player faction " + faction);
            factions_remaining = utils.removeElementAt(factions_remaining, idx);
            if (factions_remaining.size() > 0)
            {
                setObjVar(master_object, VAR_FACTIONS_REMAINING, factions_remaining);
            }
            else 
            {
                if (hasObjVar(master_object, VAR_FACTIONS_REMAINING))
                {
                    removeObjVar(master_object, VAR_FACTIONS_REMAINING);
                }
            }
            sendFactionMessage(master_object, faction, "Your faction has been eliminated from the battle.");
            obj_id[] faction_team = getFactionTeam(master_object, faction);
            if (faction_team != null)
            {
                for (int i = 0; i < faction_team.length; i++)
                {
                    expelPlayerFromBattlefield(faction_team[i], master_object);
                }
            }
            sendBattlefieldMessage(master_object, "The " + faction + " faction has been eliminated from the battle.");
            return true;
        }
    }
    public static boolean startBattlefield(obj_id master_object) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "Starting battlefield game for " + master_object);
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        setGameTimer(master_object, true);
        obj_id[] players = getPlayersOnBattlefield(master_object);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                expelPlayerFromBattlefield(players[i], master_object);
            }
        }
        int idx = getBattlefieldIndex(master_object, BATTLEFIELD_DATATABLE);
        if (idx == -1)
        {
            LOG("LOG_CHANNEL", "battlefield::startBattlefield -- Unable to find the index for " + master_object);
            return false;
        }
        createBattlefieldObjects(master_object, idx);
        String[] factions_allowed = getFactionsAllowed(master_object);
        if (factions_allowed != null && factions_allowed.length > 0)
        {
            setObjVar(master_object, VAR_FACTIONS_REMAINING, factions_allowed);
        }
        String[] factions_ai = getAIFactionsAllowed(master_object);
        if (factions_ai != null && factions_ai.length > 0)
        {
            setObjVar(master_object, VAR_FACTIONS_AI_REMAINING, factions_ai);
        }
        for (int i = 0; i < factions_allowed.length; i++)
        {
            String objVar_name = VAR_BUILD_POINTS + factions_allowed[i];
            setObjVar(master_object, objVar_name, STARTING_BUILD_POINTS);
        }
        messageTo(master_object, "msgGameTimePulse", null, (int)GAME_TIME_PULSE, false);
        attachGameScript(master_object);
        return true;
    }
    public static boolean endBattlefield(obj_id master_object, boolean restart) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "Ending battlefield game for " + master_object);
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        obj_id[] participants = getParticipantsOnBattlefield(master_object);
        if (participants != null)
        {
            region bf = getRegionFromMasterObject(master_object);
            for (int i = 0; i < participants.length; i++)
            {
                dictionary end_params = new dictionary();
                String chatroom = getChatRoomNameAllFactions(master_object);
                end_params.put("chatroom", chatroom);
                int faction_id = pvpBattlefieldGetFaction(participants[i], bf);
                String faction = factions.getFactionNameByHashCode(faction_id);
                if (faction != null)
                {
                    String chatroom_faction = getChatRoomNameFaction(master_object, faction);
                    end_params.put("chatroom_faction", chatroom_faction);
                }
                end_params.put("master_object", master_object);
                messageTo(participants[i], "msgEndBattlefieldGame", end_params, 1.0f, true);
            }
        }
        if (hasObjVar(master_object, VAR_GAME))
        {
            removeObjVar(master_object, VAR_GAME);
        }
        setObjVar(master_object, VAR_NEXT_GAME, getGameTime());
        destroyBaseObjects(master_object);
        region reg = getRegionFromMasterObject(master_object);
        LOG("LOG_CHANNEL", "battlefield::endBattlefield -- reg ->" + reg);
        pvpBattlefieldClearParticipants(reg);
        if (restart)
        {
            int restart_min = getGameRestartMinimum(master_object);
            int restart_max = getGameRestartMaximum(master_object);
            int restart_time = rand(restart_min, restart_max);
            setObjVar(master_object, VAR_NEXT_GAME, getGameTime() + restart_time);
            messageTo(master_object, "msgStartGame", null, (float)restart_time, true);
        }
        else 
        {
            setObjVar(master_object, VAR_NEXT_GAME, -9999);
        }
        detachGameScript(master_object);
        return true;
    }
    public static boolean endBattlefield(obj_id master_object) throws InterruptedException
    {
        return endBattlefield(master_object, true);
    }
    public static void createBattlefieldObjects(obj_id master_object, int idx) throws InterruptedException
    {
        int num_items = dataTableGetNumRows(BATTLEFIELD_DATATABLE);
        if (num_items > idx + 1)
        {
            for (int i = idx + 1; i < num_items; i++)
            {
                String bf_name = dataTableGetString(BATTLEFIELD_DATATABLE, i, DATATABLE_COL_NAME);
                if (bf_name.length() > 0)
                {
                    return;
                }
                String planet = getCurrentSceneName();
                dictionary object_row = dataTableGetRow(BATTLEFIELD_DATATABLE, i);
                String obj_template = object_row.getString(DATATABLE_COL_OBJECT);
                float x = object_row.getFloat(DATATABLE_COL_OBJECT_X);
                float y = object_row.getFloat(DATATABLE_COL_OBJECT_Y);
                float z = object_row.getFloat(DATATABLE_COL_OBJECT_Z);
                int delta = object_row.getInt(DATATABLE_COL_OBJECT_DELTA);
                float heading = object_row.getFloat(DATATABLE_COL_HEADING);
                String faction = object_row.getString(DATATABLE_COL_FACTION);
                String structure_type = object_row.getString(DATATABLE_COL_STRUCTURE_TYPE);
                int game_critical = object_row.getInt(DATATABLE_COL_CRITICAL);
                String script = object_row.getString(DATATABLE_COL_SCRIPT);
                float value1 = object_row.getFloat(DATATABLE_COL_VALUE1);
                float value2 = object_row.getFloat(DATATABLE_COL_VALUE2);
                if (obj_template.equals("START_LOC"))
                {
                    int jidx = utils.getElementPositionInArray(getFactionsAllowed(master_object), faction);
                    if (jidx != -1)
                    {
                        location faction_start;
                        if (delta == 1)
                        {
                            location bf_loc = getLocation(master_object);
                            faction_start = new location(bf_loc.x - x, bf_loc.y - y, bf_loc.z - z, planet);
                        }
                        else 
                        {
                            faction_start = new location(x, y, z, planet);
                        }
                        location[] start_locations = getLocationArrayObjVar(master_object, VAR_START_LOCATIONS);
                        if (start_locations != null && start_locations.length > 0)
                        {
                            start_locations[jidx] = (location)faction_start.clone();
                            setObjVar(master_object, VAR_START_LOCATIONS, start_locations);
                        }
                    }
                    continue;
                }
                location obj_loc;
                if (delta == 1)
                {
                    location bf_loc = getLocation(master_object);
                    obj_loc = new location(bf_loc.x - x, bf_loc.y - y, bf_loc.z - z, planet);
                }
                else 
                {
                    obj_loc = new location(x, y, z, planet);
                }
                obj_id object = createObject(obj_template, obj_loc);
                if (obj_loc == null)
                {
                    LOG("LOG_CHANNEL", "Unable to create " + obj_template);
                }
                else 
                {
                    if (heading != 0.0f)
                    {
                        
                    }
                    setYaw(object, heading);
                    region reg = getRegionFromMasterObject(master_object);
                    int faction_id = getFactionId(faction);
                    if (faction_id != 0)
                    {
                        pvpBattlefieldSetParticipant(object, reg, faction_id);
                    }
                    if (structure_type.length() > 0)
                    {
                        if (structure_type.equals("healing"))
                        {
                            setObjVar(object, "healing.canhealwound", 1);
                        }
                        if (structure_type.startsWith("spawner"))
                        {
                            int spawn_idx = dataTableSearchColumnForString(structure_type, DATATABLE_COL_SPAWNER_TYPE, SPAWNER_DATATABLE);
                            if (spawn_idx == -1)
                            {
                                LOG("LOG_CHANNEL", "battlefield::createBattlefieldObjects -- Invalid spawner type for " + object + " in battlefield " + master_object);
                            }
                            else 
                            {
                                LOG("LOG_CHANNEL", "spawn_idx ->" + spawn_idx);
                                dictionary spawn_row = dataTableGetRow(SPAWNER_DATATABLE, spawn_idx);
                                String spawner_type = spawn_row.getString(DATATABLE_COL_SPAWNER_TYPE);
                                int pulse = spawn_row.getInt(DATATABLE_COL_SPAWNER_PULSE);
                                int spawner_max = spawn_row.getInt(DATATABLE_COL_SPAWNER_MAX_SPAWN);
                                int population_max = spawn_row.getInt(DATATABLE_COL_SPAWNER_MAX_POPULATION);
                                setObjVar(object, VAR_SPAWNER_TYPE, spawner_type);
                                setObjVar(object, VAR_SPAWNER_PULSE, pulse);
                                setObjVar(object, VAR_SPAWNER_MAX, spawner_max);
                                setObjVar(object, VAR_SPAWNER_MAX_POPULATION, population_max);
                                attachScript(object, SCRIPT_BATTLEFIELD_SPAWNER);
                                messageTo(object, "msgBattlefieldSpawn", null, pulse, false);
                            }
                        }
                    }
                    if (game_critical == 1)
                    {
                        setObjVar(object, VAR_GAME_CRITICAL, 1);
                        if (value1 != 0.0f)
                        {
                            setObjVar(object, VAR_VALUE1, value1);
                        }
                        if (value2 != 0.0f)
                        {
                            setObjVar(object, VAR_VALUE2, value2);
                        }
                        if (script.length() > 0)
                        {
                            attachScript(object, script);
                        }
                    }
                    attachScript(object, SCRIPT_BATTLEFIELD_OBJECT);
                }
            }
        }
        return;
    }
    public static void startBuildingConstruction(obj_id master_object, obj_id player, location position, int rotation, dictionary stats) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "battlefield::startBuildingConstruction --" + stats);
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return;
        }
        String name = stats.getString("name");
        int time = stats.getInt("construction_time");
        String template = stats.getString("construction_template");
        int cost = stats.getInt("cost");
        float height = stats.getFloat("height");
        float yaw = (float)(rotation * 90);
        region bf = getRegionFromMasterObject(master_object);
        int faction_id = pvpBattlefieldGetFaction(player, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        int repair_cost = cost / 10;
        if (repair_cost < 1)
        {
            repair_cost = 1;
        }
        obj_id structure = addBattlefieldObject(master_object, template, position, yaw, faction);
        LOG("LOG_CHANNEL", "structure ->" + structure + " template ->" + template);
        if (structure == null)
        {
            return;
        }
        setObjVar(structure, VAR_BUILDTIME, time);
        setObjVar(structure, VAR_TIMESTAMP, getGameTime());
        setObjVar(structure, VAR_NAME, name);
        setObjVar(structure, VAR_REPAIR_COST, repair_cost);
        setObjVar(structure, VAR_HEIGHT, height);
        stats.put("player", player);
        messageTo(structure, "msgConstructionComplete", stats, time, true);
        sendFactionMessage(master_object, faction, getFirstName(player) + " started construction on " + name);
        return;
    }
    public static obj_id buildReinforcement(obj_id master_object, obj_id structure, obj_id player) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return null;
        }
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        String template = getStringObjVar(structure, VAR_REINFORCEMENT_TEMPLATE);
        if (template == null)
        {
            return null;
        }
        String name = getStringObjVar(structure, VAR_REINFORCEMENT_NAME);
        float cost = getFloatObjVar(structure, VAR_REINFORCEMENT_COST);
        if (spendFactionPoints(player, cost))
        {
            location loc = getLocation(structure);
            location spawn_loc = utils.getRandomAwayLocation(loc, 5.0f, 10.0f);
            String faction = getPlayerTeamFaction(player);
            obj_id reinforcement = addBattlefieldMob(master_object, template, spawn_loc, faction);
            LOG("LOG_CHANNEL", "battlefield.buildReinforcement -- created " + reinforcement);
            pet_lib.makePet(reinforcement, player);
            pet_lib.setupDefaultCommands(reinforcement);
            setObjVar(reinforcement, battlefield.VAR_CONSTRUCTED, 1);
            setObjVar(structure, battlefield.VAR_LAST_BUILD, getGameTime());
            sendFactionMessage(master_object, faction, getName(player) + " has acquired a reinforcement " + name + ".");
            return reinforcement;
        }
        else 
        {
            return null;
        }
    }
    public static boolean repairBattlefieldStructure(obj_id master_object, obj_id player, obj_id structure) throws InterruptedException
    {
        if (master_object == null || master_object == obj_id.NULL_ID)
        {
            return false;
        }
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        if (!hasScript(structure, SCRIPT_DESTRUCTIBLE_BUILDING) && !hasScript(structure, SCRIPT_DESTROY_OBJECTIVE))
        {
            LOG("LOG_CHANNEL", player + " ->You cannot repair that.");
            sendSystemMessageTestingOnly(player, "You cannot repair that.");
            return false;
        }
        if (getHitpoints(structure) == getMaxHitpoints(structure))
        {
            LOG("LOG_CHANNEL", player + " ->That does not need repairing.");
            sendSystemMessageTestingOnly(player, "That does not need repairing.");
            return false;
        }
        int repair_cost = getIntObjVar(structure, VAR_REPAIR_COST);
        String faction = getPlayerTeamFaction(player);
        if (decrementFactionBuildPoints(master_object, faction, repair_cost))
        {
            setObjVar(structure, VAR_REPAIRING, 1);
            messageTo(structure, "msgRepairPulse", null, REPAIR_PULSE, true);
            LOG("LOG_CHANNEL", player + " ->Repairs initiated.");
            sendSystemMessageTestingOnly(player, "Repairs initiated.");
            return true;
        }
        else 
        {
            LOG("LOG_CHANNEL", player + " ->You do not have the " + repair_cost + " build points to initiate repair.");
            sendSystemMessageTestingOnly(player, "You do not have the " + repair_cost + " build points to initiate repair.");
            return false;
        }
    }
}
