package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.quests;
import script.library.utils;
import java.lang.Math;

public class patrol extends script.base_script
{
    public patrol()
    {
    }
    public static final boolean DEBUGGING = false;
    public int handleDestroyWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        int waypointNumber = params.getInt("waypointNumber");
        String questName = params.getString("questName");
        if (hasObjVar(self, "quest." + questName + ".waypoint" + waypointNumber))
        {
            obj_id wp = getObjIdObjVar(self, "quest." + questName + ".waypoint" + waypointNumber);
            destroyWaypointInDatapad(wp, self);
            if (hasObjVar(self, "quest." + questName + ".waypointcount"))
            {
                int waypointCount = getIntObjVar(self, "quest." + questName + ".waypointcount");
                waypointCount++;
                setObjVar(self, "quest." + questName + ".waypointcount", waypointCount);
            }
            else 
            {
                setObjVar(self, "quest." + questName + ".waypointcount", 1);
            }
            removeObjVar(self, "quest." + questName + ".waypoint" + waypointNumber);
        }
        return SCRIPT_CONTINUE;
    }
    public void completeTask(obj_id self, String questName, boolean succeeded) throws InterruptedException
    {
        if (hasObjVar(self, "quest." + questName))
        {
            for (int i = 0; i < 8; i++)
            {
                if (hasObjVar(self, "quest." + questName + ".waypoint" + i))
                {
                    obj_id waypoint = getObjIdObjVar(self, "quest." + questName + ".waypoint" + i);
                    if (isIdValid(waypoint))
                    {
                        destroyWaypointInDatapad(waypoint, self);
                    }
                    removeObjVar(self, "quest." + questName + ".waypoint" + i);
                }
            }
        }
        quests.complete(questName, self, succeeded);
    }
    public void waypointCleanUp(String questName, obj_id player) throws InterruptedException
    {
        for (int i = 0; i < 8; i++)
        {
            if (hasObjVar(player, "quest." + questName + ".waypoint" + i))
            {
                obj_id waypoint = getObjIdObjVar(player, "quest." + questName + ".waypoint" + i);
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, player);
                }
                removeObjVar(player, "quest." + questName + ".waypoint" + i);
            }
        }
    }
    public void setupPatrolWaypoint(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.patrol"))
        {
            location startLoc = getPatrolLocation(self, questRow);
            if (startLoc != null)
            {
                String questName = quests.getDataEntry(questRow, "NAME");
                if (questName != null && questName.length() > 0)
                {
                    float radius = 1024.0f;
                    if (hasObjVar(self, "quest." + questName + ".parameter"))
                    {
                        radius = getFloatObjVar(self, "quest." + questName + ".parameter");
                    }
                    else 
                    {
                        String parameterString = quests.getDataEntry(questRow, "PARAMETER");
                        if (parameterString != null && !parameterString.equals(""))
                        {
                            radius = utils.stringToFloat(parameterString);
                        }
                    }
                    String planetName = getCurrentSceneName();
                    for (int i = 0; i < 8; i++)
                    {
                        int offsetx = rand(-75, 75);
                        int offsetz = rand(-75, 75);
                        int offsettheta = rand(-2, 2);
                        double theta = (i * 45 + offsettheta) * 0.0174532925f;
                        float x = startLoc.x + offsetx;
                        float z = startLoc.z + offsetz;
                        location loc = new location();
                        loc.x = x + (radius * (float)Math.cos(theta));
                        loc.z = z + (radius * (float)Math.sin(theta));
                        loc.area = planetName;
                        if (DEBUGGING)
                        {
                            sendSystemMessageTestingOnly(self, "waypoint " + i + " x is: " + loc.x + " and z is: " + loc.z);
                        }
                        location tempLoc = null;
                        int attempts = 0;
                        while (tempLoc == null && attempts < 15)
                        {
                            ++attempts;
                            tempLoc = locations.getRandomGoodLocation(loc, 0.0f, 200.0f, 32.0f);
                        }
                        if (tempLoc == null)
                        {
                            tempLoc = loc;
                        }
                        obj_id wp = createWaypointInDatapad(self, tempLoc);
                        addLocationTarget(questName + ".waypoint." + i, tempLoc, 64.0f);
                        string_id messageId = new string_id("quest/force_sensitive/fs_patrol", "patrol_point");
                        setWaypointName(wp, localize(messageId));
                        setWaypointColor(wp, "yellow");
                        setWaypointActive(wp, true);
                        setObjVar(self, "quest." + questName + ".waypoint" + i, wp);
                    }
                }
            }
        }
    }
    public location getPatrolLocation(obj_id self, int questRow) throws InterruptedException
    {
        location result = null;
        String questName = quests.getDataEntry(questRow, "NAME");
        if (questName != null && questName.length() > 0 && quests.isActive(questName, self))
        {
            LOG("newquests", "initializing location task " + questName);
            float radius = 64.0f;
            boolean waitForPlanetWarp = false;
            if (hasObjVar(self, "quest." + questName + ".target"))
            {
                result = getLocationObjVar(self, "quest." + questName + ".target");
                if (result != null)
                {
                    LOG("newquests", "location target for " + questName + " is overridden by an object variable. new location is " + result);
                }
                else 
                {
                    LOG("newquests", "location target for " + questName + " was supposed to be overridden, but the location could not be retrieved from the location object variable quest." + questName + ".target");
                }
            }
            else 
            {
                String planetName = null;
                obj_id cell = null;
                boolean haveCell = false;
                float x = 0.0f;
                boolean haveX = false;
                float z = 0.0f;
                boolean haveZ = false;
                float parameter = 1024.0f;
                boolean haveParameter = false;
                boolean haveTarget = false;
                LOG("newquests", "location PARAMETER set to " + parameter);
                String locationString = quests.getDataEntry(questRow, "TARGET");
                if (locationString != null && locationString.length() > 0)
                {
                    haveTarget = true;
                    LOG("newquests", "location parsing TARGET \"" + locationString + "\"");
                    String[] tokens = split(locationString, ':');
                    if (tokens.length > 0)
                    {
                        LOG("newquests", "location parsing " + tokens.length + " tokens");
                        String s = tokens[0];
                        if (!Character.isDigit(s.charAt(0)))
                        {
                            planetName = tokens[0];
                            LOG("newquests", "location parsed " + tokens[0] + " as the target planet");
                            if (tokens.length > 2)
                            {
                                LOG("newquests", "location parsing " + tokens[1] + " as x coordinate");
                                x = utils.stringToFloat(tokens[1]);
                                haveX = true;
                                LOG("newquests", "location parsing " + tokens[2] + " as z coordinate");
                                z = utils.stringToFloat(tokens[2]);
                                haveZ = true;
                                if (tokens.length > 3)
                                {
                                    LOG("newquests", "location parsing " + tokens[3] + " as a cell ID");
                                    cell = utils.stringToObjId(tokens[3]);
                                    haveCell = true;
                                }
                            }
                        }
                        else 
                        {
                            LOG("newquests", "location first token is a number");
                            if (tokens.length > 1)
                            {
                                x = utils.stringToFloat(tokens[0]);
                                z = utils.stringToFloat(tokens[1]);
                            }
                        }
                    }
                }
                if (!haveTarget)
                {
                    result = getLocation(self);
                    LOG("newquests", "No target in Data table, returning current location " + result);
                }
                else if (planetName != null && haveX && haveZ)
                {
                    String currentScene = getCurrentSceneName();
                    if (currentScene != null && currentScene.equals(planetName))
                    {
                        result = new location();
                        result.x = x;
                        result.z = z;
                        result.area = planetName;
                        LOG("newquests", "Patrol Origin set location at " + result);
                    }
                    else 
                    {
                        waitForPlanetWarp = true;
                    }
                }
                else if (planetName != null && !haveX && !haveZ)
                {
                    String currentScene = getCurrentSceneName();
                    if (currentScene != null && currentScene.equals(planetName))
                    {
                        result = getLocation(self);
                    }
                    else 
                    {
                        waitForPlanetWarp = true;
                    }
                }
            }
            if (result != null)
            {
                setObjVar(self, "quest." + questName + ".selected_location", result);
                LOG("newquests", "location adding location target at " + result);
            }
            else if (waitForPlanetWarp == true)
            {
                setObjVar(self, "quest." + questName + ".generate", waitForPlanetWarp);
            }
        }
        return result;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int rows = dataTableGetNumRows("datatables/player/quests.iff");
        int iter = 0;
        for (iter = 0; iter < rows; ++iter)
        {
            String questName = quests.getDataEntry(iter, "NAME");
            if (quests.isMyQuest(iter, "quest.task.patrol"))
            {
                if (quests.isActive(questName, self))
                {
                    if (hasObjVar(self, "quest." + questName + ".generate"))
                    {
                        removeObjVar(self, "quest." + questName + ".generate");
                        setupPatrolWaypoint(self, iter);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "Patrol - OnQuestActivated(" + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.patrol"))
        {
            LOG("newquests", "Patrol attempting to generate waypoints");
            setupPatrolWaypoint(self, questRow);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (DEBUGGING)
        {
            sendSystemMessageTestingOnly(self, "triggering On Arrived At Location");
        }
        String[] tokens = split(locationName, '.');
        String questName = "";
        int waypointNumber = -1;
        if (tokens.length > 0)
        {
            if (DEBUGGING)
            {
                sendSystemMessageTestingOnly(self, "On Arrived At Location tokes >0 ->" + tokens.length);
            }
            LOG("newquests", "parsing arrive at location " + tokens.length + " tokens");
            String s = tokens[0];
            if (!Character.isDigit(s.charAt(0)))
            {
                questName = tokens[0];
                if (DEBUGGING)
                {
                    sendSystemMessageTestingOnly(self, "On Arrived At Location questName= " + questName);
                }
                LOG("newquests", "arrive at location parsed " + tokens[0] + " as the quest name");
                if (tokens.length > 2)
                {
                    if (DEBUGGING)
                    {
                        sendSystemMessageTestingOnly(self, "Tokens > 2");
                    }
                    String wpNum = tokens[2];
                    if (Character.isDigit(wpNum.charAt(0)))
                    {
                        waypointNumber = utils.stringToInt(tokens[2]);
                        LOG("newquests", "arrive at location parsed " + tokens[2] + " is waypoint Number");
                        if (DEBUGGING)
                        {
                            sendSystemMessageTestingOnly(self, "On Arrived At Location waypointNumber= " + waypointNumber);
                        }
                    }
                    else 
                    {
                        LOG("newquests", "Error parsing waypoint number, " + tokens[2] + " is not a Number");
                        if (DEBUGGING)
                        {
                            sendSystemMessageTestingOnly(self, "number not found= " + tokens[2]);
                        }
                    }
                }
            }
            else 
            {
                LOG("newquests", "Not enough Tokens to parse for good location number");
                if (DEBUGGING)
                {
                    sendSystemMessageTestingOnly(self, "On Arrived At Location Not Enough Tokens");
                }
            }
        }
        if (quests.isActive(questName, self) && waypointNumber != -1)
        {
            if (DEBUGGING)
            {
                sendSystemMessageTestingOnly(self, "Quest is Actives WP != -1");
            }
            LOG("newquests", "patrol arrived at waypoint " + waypointNumber);
            if (hasObjVar(self, "quest." + questName + ".waypoint" + waypointNumber))
            {
                dictionary params = new dictionary();
                params.put("waypointNumber", waypointNumber);
                params.put("questName", questName);
                messageTo(self, "handleDestroyWaypoint", params, 20.0f, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
