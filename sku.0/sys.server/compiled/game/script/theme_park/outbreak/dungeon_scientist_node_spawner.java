package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.attrib;
import script.library.create;
import script.library.groundquests;
import script.library.stealth;
import script.library.utils;

public class dungeon_scientist_node_spawner extends script.base_script
{
    public dungeon_scientist_node_spawner()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_trigger";
    public static final String NODE_SPAWN_STRING = "scientist_spawn_point";
    public static final String NODE_PATH_DELAY = "scientist_delay";
    public static final String NODE_SCIENTIST_PATH = "scientist_rescue";
    public static final String NODE_STORMTROOPER_PATH = "stormtrooper_guard";
    public static final String STORMTROOPER_GUARD_CREATURE = "outbreak_facility_stormtrooper_survivor";
    public static final float SEARCH_RADIUS = 100f;
    public int spawnRescuedActor(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() Initialized Message Handler for node: " + self);
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        obj_id owner = params.getObjId("owner");
        if (!isValidId(owner) || !exists(owner))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() Owner param invalid.");
            return SCRIPT_CONTINUE;
        }
        String creatureType = getStringObjVar(self, NODE_SPAWN_STRING);
        if (creatureType == null || creatureType.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC creature_type was not valid");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC creature_type is: " + creatureType);
        location curLoc = getLocation(self);
        if (curLoc == null)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the Node location was somehow invalid.");
            return SCRIPT_CONTINUE;
        }
        obj_id mob = create.object(creatureType, curLoc);
        if (!isValidId(mob) || !exists(mob))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor()  Mob: " + mob + " could not be created!");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor()  Mob: " + mob + " WAS SICCESSFULLY CREATED.");
        String objVarSearch = NODE_SCIENTIST_PATH;
        if (creatureType.equals(STORMTROOPER_GUARD_CREATURE))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC is a stormtrooper guard. He gets different nodes.");
            objVarSearch = NODE_STORMTROOPER_PATH;
        }
        obj_id[] wayPointList = getAllObjectsWithObjVar(getLocation(self), SEARCH_RADIUS, objVarSearch);
        if (wayPointList == null || wayPointList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC failed to find any objects within " + SEARCH_RADIUS + " of " + getLocation(self));
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.startSurvivorPathing() wayPointList received");
        location[] wayPtLocs = new location[wayPointList.length];
        for (int i = 0; i < wayPointList.length; i++)
        {
            int orderNumber = getIntObjVar(wayPointList[i], objVarSearch) - 1;
            if (orderNumber < 0)
            {
                CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC waypoint location was invalid for waypoint: " + wayPointList[i]);
                continue;
            }
            wayPtLocs[orderNumber] = getLocation(wayPointList[i]);
        }
        int delay = getIntObjVar(self, NODE_PATH_DELAY);
        if (delay < 0)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC path delay was not found for node spawn location: " + self);
            delay = 1;
        }
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.startSurvivorPathing() the NPC delay is: " + delay);
        if (wayPtLocs == null || wayPtLocs.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() unable to get a list of locations for hte NPC");
            return SCRIPT_CONTINUE;
        }
        setMovementRun(mob);
        setBaseRunSpeed(mob, (getBaseRunSpeed(mob) - 8));
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.startSurvivorPathing() the NPC has run speed: " + getBaseRunSpeed(self));
        setObjVar(mob, "owner", owner);
        setInvulnerable(mob, false);
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC is no longer invulnerable for player.");
        utils.setScriptVar(mob, ai_lib.SCRIPTVAR_CACHED_PATROL_NAMED_PATH, wayPtLocs);
        utils.setScriptVar(mob, ai_lib.SCRIPTVAR_CACHED_PATROL_TYPE, 0);
        clearCondition(mob, CONDITION_CONVERSABLE);
        setAttributeAttained(mob, attrib.OUTBREAK_SURVIVOR);
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.spawnRescuedActor() the NPC is successfully pathing to the next node");
        dictionary parms = new dictionary();
        parms.put("owner", owner);
        parms.put("mob", mob);
        parms.put("wayPtLocs", wayPtLocs);
        messageTo(self, "startMovingAfterDelay", parms, delay, false);
        messageTo(mob, "cleanUpNpcTimer", parms, 300, false);
        return SCRIPT_CONTINUE;
    }
    public int startMovingAfterDelay(obj_id self, dictionary params) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() Initalized Message Handler.");
        if ((params == null) || (params.isEmpty()))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() Params invalid.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("mob"))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() Mob key not found.");
            return SCRIPT_CONTINUE;
        }
        if (!params.containsKey("wayPtLocs"))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() wayPtLocs key not found.");
            return SCRIPT_CONTINUE;
        }
        obj_id mob = params.getObjId("mob");
        if (!isValidId(mob) || !exists(mob))
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() mob obj_id invalid.");
            return SCRIPT_CONTINUE;
        }
        location[] wayPtLocs = params.getLocationArray("wayPtLocs");
        if (wayPtLocs == null || wayPtLocs.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() wayPtLocs array invalid.");
            return SCRIPT_CONTINUE;
        }
        blog("mob: " + mob);
        blog("location 1" + wayPtLocs[0]);
        CustomerServiceLog("outbreak_themepark", "dungeon_scientist_node_spawners.startMovingAfterDelay() All params look good, telling mob to path.");
        patrolOnce(mob, wayPtLocs, 0);
        setObjVar(mob, "givenPath", 1);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
