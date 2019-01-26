package script.quest.task;

import script.library.quests;
import script.location;
import script.obj_id;

public class find_theater extends script.base_script
{
    public find_theater()
    {
    }
    public void completeTask(obj_id self, String questName, boolean succeeded) throws InterruptedException
    {
        if (hasObjVar(self, "quest." + questName))
        {
            obj_id waypoint = getObjIdObjVar(self, "quest." + questName + ".waypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, self);
            }
        }
        if (hasTheaterAssigned(self))
        {
            unassignTheaterFromPlayer(self);
        }
        quests.complete(questName, self, succeeded);
    }
    public void setupTheater(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.find_theater"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            String script = null;
            int theaterLocationType = TLT_getGoodLocation;
            if (questName == null || questName.equals("") || !quests.isActive(questName, self))
            {
                return;
            }
            String parameterString = quests.getDataEntry(questRow, "PARAMETER");
            if (parameterString != null && !parameterString.equals(""))
            {
                String[] params = split(parameterString, ':');
                for (String param : params) {
                    if ("flat" == param) {
                        theaterLocationType = TLT_flatten;
                    } else if ("atloc" == param) {
                        theaterLocationType = TLT_none;
                    } else if (!Character.isDigit(param.charAt(0))) {
                        script = param;
                        LOG("newquests", "FIND THEATER: script parsed: " + script);
                    }
                }
            }
            String target = null;
            if (hasObjVar(self, "quest." + questName + ".target"))
            {
                target = getStringObjVar(self, "quest." + questName + ".target");
            }
            else 
            {
                target = quests.getDataEntry(questRow, "TARGET");
            }
            if (target == null || target.equals(""))
            {
                return;
            }
            boolean havePlanet = false;
            String[] tokens = split(target, ':');
            String datatable = null;
            if (tokens.length > 1)
            {
                havePlanet = true;
            }
            else 
            {
                datatable = tokens[0];
            }
            if (havePlanet)
            {
                if (!tokens[0].contains(".iff"))
                {
                    datatable = tokens[1];
                }
                else 
                {
                    datatable = tokens[0];
                }
            }
            location theaterLoc = new location();
            boolean hasLocInfo = false;
            int theaterTest = dataTableGetInt(datatable, 0, 0);
            if (theaterTest > 0)
            {
                if (theaterTest == (1606908893))
                {
                    float x = dataTableGetFloat(datatable, 0, "x");
                    float y = dataTableGetFloat(datatable, 0, "y");
                    float z = dataTableGetFloat(datatable, 0, "z");
                    String scene = dataTableGetString(datatable, 0, "scene");
                    if (scene == null || scene.equals(""))
                    {
                        scene = getCurrentSceneName();
                    }
                    theaterLoc = new location(x, y, z, scene);
                    hasLocInfo = true;
                }
            }
            boolean created = false;
            int attempts = 0;
            if (hasLocInfo)
            {
                created = assignTheaterToPlayer(self, datatable, theaterLocationType);
            }
            else 
            {
                theaterLoc = quests.getTheaterLocationTarget(self, questRow);
                if (theaterLoc != null)
                {
                    unassignTheaterFromPlayer(self);
                    created = assignTheaterToPlayer(self, datatable, theaterLoc, script, theaterLocationType);
                }
            }
            if (created)
            {
                if (isGod(self))
                {
                    sendSystemMessageTestingOnly(self, "Theater Location: " + theaterLoc.toString());
                }
                addLocationTarget(questName, theaterLoc, 32.0f);
                setupWaypoint(self, questRow, theaterLoc);
            }
            else 
            {
                if (hasObjVar(self, "quest." + questName + ".generate"))
                {
                    if (hasObjVar(self, "quest." + questName + ".waypoint"))
                    {
                        return;
                    }
                    location loc = getLocationObjVar(self, "quest." + questName + ".generate");
                    obj_id wp = createWaypointInDatapad(self, loc);
                    String summary = quests.getDataEntry(questName, "JOURNAL_ENTRY_SUMMARY");
                    if (summary != null && summary.length() > 0)
                    {
                        setWaypointName(wp, summary);
                    }
                    else 
                    {
                        setWaypointName(wp, "missing task summary for " + questName);
                    }
                    setWaypointColor(wp, "yellow");
                    setWaypointActive(wp, true);
                    setObjVar(self, "quest." + questName + ".waypoint", wp);
                }
                else 
                {
                    quests.complete(questName, self, false);
                }
            }
        }
    }
    public void setupWaypoint(obj_id self, int questRow, location loc) throws InterruptedException
    {
        if (loc != null)
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            if (hasObjVar(self, "quest." + questName + ".waypoint"))
            {
                obj_id waypoint = getObjIdObjVar(self, "quest." + questName + ".waypoint");
                if (isIdValid(waypoint))
                {
                    destroyWaypointInDatapad(waypoint, self);
                }
                removeObjVar(self, "quest." + questName + ".waypoint");
            }
            if (questName != null && questName.length() > 0)
            {
                int x_rand = rand(100, 250);
                int z_rand = rand(100, 250);
                int x_mod = rand(0, 1);
                int z_mod = rand(0, 1);
                if (x_mod == 0)
                {
                    x_rand *= -1;
                }
                if (z_mod == 0)
                {
                    z_rand *= -1;
                }
                loc.x += x_rand;
                loc.z += z_rand;
                obj_id wp = createWaypointInDatapad(self, loc);
                String summary = quests.getDataEntry(questName, "JOURNAL_ENTRY_SUMMARY");
                if (summary != null && summary.length() > 0)
                {
                    setWaypointName(wp, summary);
                }
                else 
                {
                    setWaypointName(wp, "missing task summary for " + questName);
                }
                setWaypointColor(wp, "yellow");
                setWaypointActive(wp, true);
                addLocationTarget(questName + "_waypoint", loc, 32.0f);
                setObjVar(self, "quest." + questName + ".waypoint", wp);
            }
        }
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int rows = dataTableGetNumRows("datatables/player/quests.iff");
        int iter = 0;
        for (iter = 0; iter < rows; ++iter)
        {
            String questName = quests.getDataEntry(iter, "NAME");
            if (quests.isMyQuest(iter, "quest.task.find_theater"))
            {
                if (quests.isActive(questName, self))
                {
                    if (hasObjVar(self, "quest." + questName + ".generate"))
                    {
                        removeObjVar(self, "quest." + questName + ".generate");
                        setupTheater(self, iter);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.find_theater"))
        {
            setupTheater(self, questRow);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (quests.isActive(locationName, self))
        {
            CustomerServiceLog("fs_quests", "DEBUG -- %TU arrived at location '" + locationName + "'. Preparing to mark find theater task as successfully completed.", self);
            if (hasObjVar(self, "quest." + locationName + ".selected_location"))
            {
                location expectedLoc = getLocationObjVar(self, "quest." + locationName + ".selected_location");
                CustomerServiceLog("fs_quests", "DEBUG -- %TU should be close to: " + expectedLoc.toString(), self);
            }
            location currentLoc = getLocation(self);
            CustomerServiceLog("fs_quests", "DEBUG -- %TU am currently at: " + currentLoc.toString(), self);
            if (hasTheaterAssigned(self))
            {
                CustomerServiceLog("fs_quests", "DEBUG -- %TU still has a theater assigned when task is successful. This should not be.", self);
            }
            completeTask(self, locationName, true);
        }
        return SCRIPT_CONTINUE;
    }
}
