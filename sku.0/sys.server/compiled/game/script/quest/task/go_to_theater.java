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

public class go_to_theater extends script.base_script
{
    public go_to_theater()
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
        if (quests.isMyQuest(questRow, "quest.task.go_to_theater"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            if (questName == null || questName.equals("") || !quests.isActive(questName, self))
            {
                return;
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
                if ((tokens[0].indexOf('/') <= 0) || (tokens[0].indexOf('\\') <= 0))
                {
                    datatable = tokens[0];
                }
                else 
                {
                    datatable = tokens[1];
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
            int theaterLocationType = TLT_getGoodLocation;
            String parameterString = quests.getDataEntry(questRow, "PARAMETER");
            if (parameterString != null && !parameterString.equals(""))
            {
                String[] params = split(parameterString, ':');
                for (int i = 0; i < params.length; i++)
                {
                    if ("flat" == params[i])
                    {
                        theaterLocationType = TLT_flatten;
                        break;
                    }
                    else if ("atloc" == params[i])
                    {
                        theaterLocationType = TLT_none;
                        break;
                    }
                }
            }
            if (hasLocInfo)
            {
                created = assignTheaterToPlayer(self, datatable, theaterLocationType);
            }
            else 
            {
                theaterLoc = quests.getTheaterLocationTarget(self, questRow);
                if (theaterLoc != null)
                {
                    if (questName.equals("fs_theater_final"))
                    {
                        created = assignTheaterToPlayer(self, datatable, theaterLoc, "systems.fs_quest.exit_quest.final_battle_theater", theaterLocationType);
                    }
                    else if (questName.equals("fs_theater_camp"))
                    {
                        created = assignTheaterToPlayer(self, datatable, theaterLoc, "systems.fs_quest.camp_battle_theater", theaterLocationType);
                    }
                    else 
                    {
                        created = assignTheaterToPlayer(self, datatable, theaterLoc, "quest.task.theater_controller", theaterLocationType);
                    }
                }
            }
            if (created)
            {
                setupWaypoint(self, questRow, theaterLoc);
            }
            else 
            {
                LOG("quest", "!!!!!!!!!!!!!!( " + self + " )Can't create theater...");
                quests.complete(questName, self, false);
            }
        }
    }
    public void setupWaypoint(obj_id self, int questRow, location loc) throws InterruptedException
    {
        if (loc != null)
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            if (questName != null && questName.length() > 0)
            {
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
        }
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        int rows = dataTableGetNumRows("datatables/player/quests.iff");
        int iter = 0;
        for (iter = 0; iter < rows; ++iter)
        {
            String questName = quests.getDataEntry(iter, "NAME");
            if (quests.isMyQuest(iter, "quest.task.go_to_theater"))
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
        if (quests.isMyQuest(questRow, "quest.task.go_to_theater"))
        {
            setupTheater(self, questRow);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (quests.isActive(locationName, self))
        {
            completeTask(self, locationName, true);
        }
        return SCRIPT_CONTINUE;
    }
}
