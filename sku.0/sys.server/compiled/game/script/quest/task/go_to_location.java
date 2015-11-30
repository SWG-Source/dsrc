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

public class go_to_location extends script.base_script
{
    public go_to_location()
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
        quests.complete(questName, self, succeeded);
    }
    public void setupWaypoint(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.go_to_location"))
        {
            location loc = quests.getLocationTarget(self, questRow);
            String questName = quests.getDataEntry(questRow, "NAME");
            if (loc != null)
            {
                if (questName != null && questName.length() > 0)
                {
                    String wp_objvar = "quest." + questName + ".waypoint";
                    if (hasObjVar(self, wp_objvar))
                    {
                        return;
                    }
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
                    setObjVar(self, wp_objvar, wp);
                }
            }
            else 
            {
                LOG("newquests", "failed to retrieve a location target for quest " + questName);
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
            if (quests.isMyQuest(iter, "quest.task.go_to_location"))
            {
                if (quests.isActive(questName, self))
                {
                    if (hasObjVar(self, "quest." + questName + ".generate"))
                    {
                        removeObjVar(self, "quest." + questName + ".generate");
                        setupWaypoint(self, iter);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        LOG("newquests", "go_to_location - OnQuestActivated(" + questRow + ")");
        if (quests.isMyQuest(questRow, "quest.task.go_to_location"))
        {
            LOG("newquests", "go_to_location attempting to generate waypoint");
            setupWaypoint(self, questRow);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        if (quests.isActive(locationName, self))
        {
            LOG("newquests", "go_to_location arrived at " + locationName);
            completeTask(self, locationName, true);
        }
        return SCRIPT_CONTINUE;
    }
}
