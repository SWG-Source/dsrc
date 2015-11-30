package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class go_to_location extends script.quest.task.ground.base_task
{
    public go_to_location()
    {
    }
    public static final String dataTableColumnPlanetName = "PLANET_NAME";
    public static final String dataTableColumnLocationX = "LOCATION_X";
    public static final String dataTableColumnLocationY = "LOCATION_Y";
    public static final String dataTableColumnLocationZ = "LOCATION_Z";
    public static final String dataTableColumnSphereSize = "RADIUS";
    public static final String objvarBase = "quest.go_to_location";
    public static final String objvarLocation = "location";
    public static final String objvarQuestName = "quest_name";
    public static final String objvarTaskId = "taskId";
    public static final String dot = ".";
    public static final String taskType = "go_to_location";
    public static final String JOURNAL_UPDATED_MUSIC = "sound/ui_objective_reached.snd";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + "task activated.");
        String planetName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnPlanetName);
        float locationX = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationX));
        float locationY = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationY));
        float locationZ = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnLocationZ));
        float sphereSize = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnSphereSize));
        String questName = questGetQuestName(questCrc);
        String objvarQuestName = objvarBase + dot + questName;
        String objvarTaskIdName = objvarQuestName + dot + objvarTaskId;
        setObjVar(self, objvarTaskIdName, taskId);
        location loc = new location(locationX, locationY, locationZ, planetName, null);
        addLocationTarget(questName + ":" + taskId, loc, sphereSize);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Must reach within " + sphereSize + " meters of " + locationX + ", " + locationY + ", " + locationZ + " on " + planetName + ".");
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnArrivedAtLocation(obj_id self, String locationName) throws InterruptedException
    {
        String questName = locationName;
        int taskId = -1;
        int index = locationName.lastIndexOf(":");
        if (index != -1)
        {
            questName = locationName.substring(0, index);
            taskId = utils.stringToInt(locationName.substring(index + 1));
        }
        String objvarName = objvarBase + dot + questName;
        boolean result = hasObjVar(self, objvarName);
        if (result)
        {
            if (taskId == -1)
            {
                String objvarQuestName = objvarBase + dot + questName;
                String objvarTaskIdName = objvarQuestName + dot + objvarTaskId;
                taskId = getIntObjVar(self, objvarTaskIdName);
            }
            int questCrc = questGetQuestId(questName);
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnArrivedAtLocation", "Arrived at location for go_to_location quest.  Location name is " + questName);
            removeLocationTarget(locationName);
            if (groundquests.isTaskVisible(questCrc, taskId))
            {
                play2dNonLoopingSound(self, JOURNAL_UPDATED_MUSIC);
            }
            questCompleteTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + "task completed.");
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + "task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
}
