package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.performance;
import script.library.utils;

public class perform extends script.quest.task.ground.base_task
{
    public perform()
    {
    }
    public static final String dataTableColumnPerformBuilding = "PERFORM_BUILDING";
    public static final String dataTableColumnPerformCellname = "PERFORM_CELLNAME";
    public static final String dataTableColumnPerformDuration = "PERFORM_DURATION";
    public static final String dataTableColumnPerformType = "PERFORM_TYPE";
    public static final String dataTableColumnVisible = "IS_VISIBLE";
    public static final String performEndTimeVarName = "performEndTime";
    public static final String performCurrentlyPerformingVarName = "performCurrentlyPerforming";
    public static final String taskType = "perform";
    public static final String dot = ".";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        clearPerformObjVars(self, questCrc, taskId);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        cleanup(self, questCrc, taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "OnLogout", "start");
        playerStoppedPerforming(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        playerStoppedPerforming(self, false);
        return SCRIPT_CONTINUE;
    }
    public int handleClientLogin(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "handleClientLogin", "start");
        dictionary tasks = groundquests.getActiveTasksForTaskType(self, taskType);
        if ((tasks != null) && !tasks.isEmpty())
        {
            java.util.Enumeration keys = tasks.keys();
            while (keys.hasMoreElements())
            {
                String questCrcString = (String)keys.nextElement();
                int questCrc = utils.stringToInt(questCrcString);
                int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int taskId = tasksForCurrentQuest[i];
                    groundquests.questOutputDebugLog(taskType, "handleClientLogin", "Processing task " + taskId);
                    String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                    boolean isCurrentlyPerforming = getBooleanObjVar(self, baseObjVar + dot + performCurrentlyPerformingVarName);
                    boolean isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible) != 0;
                    if (!isCurrentlyPerforming || !isVisible)
                    {
                        continue;
                    }
                    int endTime = getIntObjVar(self, baseObjVar + dot + performEndTimeVarName);
                    groundquests.questOutputDebugLog(taskType, "handleClientLogin", "Updating client journal timer: " + endTime);
                    questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", endTime);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "startPerform", "start");
        dictionary tasks = groundquests.getActiveTasksForTaskType(self, taskType);
        if ((tasks != null) && !tasks.isEmpty())
        {
            java.util.Enumeration keys = tasks.keys();
            while (keys.hasMoreElements())
            {
                String questCrcString = (String)keys.nextElement();
                int questCrc = utils.stringToInt(questCrcString);
                int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int taskId = tasksForCurrentQuest[i];
                    groundquests.questOutputDebugLog(taskType, "startPerform", "Processing task " + taskId);
                    String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                    int desiredPerformType = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnPerformType);
                    int currentPerformType = params.getInt(groundquests.PERFORM_TYPE);
                    boolean rightType = desiredPerformType == 0 || desiredPerformType == currentPerformType;
                    if (!rightType)
                    {
                        clearPerformObjVars(self, questCrc, taskId);
                        continue;
                    }
                    boolean rightLocation = true;
                    String cellname = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnPerformCellname);
                    if (cellname != null && cellname.length() != 0)
                    {
                        location playerLocation = getLocation(self);
                        rightLocation = (getCellName(playerLocation.cell)).equals(cellname);
                    }
                    else 
                    {
                        String building = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnPerformBuilding);
                        if (utils.stringToInt(building) != 0)
                        {
                            obj_id buildingObjId = utils.stringToObjId(building);
                            rightLocation = performance.isInRightBuilding(self, buildingObjId);
                        }
                    }
                    if (!rightLocation)
                    {
                        clearPerformObjVars(self, questCrc, taskId);
                        continue;
                    }
                    int performDuration = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnPerformDuration);
                    if (performDuration <= 0)
                    {
                        groundquests.questOutputDebugLog(taskType, "startPerform", "performDuration invalid, set to 10 seconds");
                        performDuration = 10;
                    }
                    int performEndTime = performDuration + performGetTime(self);
                    groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Setting performance end time to " + performEndTime + " seconds.");
                    setObjVar(self, baseObjVar + dot + performEndTimeVarName, performEndTime);
                    setObjVar(self, baseObjVar + dot + performCurrentlyPerformingVarName, true);
                    sendCheckPerformanceComplete(self, questCrc, taskId, performDuration);
                    int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
                    if (isVisible != 0)
                    {
                        questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", performEndTime);
                    }
                }
            }
        }
        groundquests.questOutputDebugLog(taskType, "startPerform", "end");
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "stopPerform", "start");
        playerStoppedPerforming(self, true);
        return SCRIPT_CONTINUE;
    }
    public int CheckPerformanceComplete(obj_id self, dictionary params) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "start");
        int questCrc = params.getInt("questcrc");
        int taskId = params.getInt("taskid");
        if (questIsTaskActive(questCrc, taskId, self))
        {
            groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "quest task is active");
            String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
            boolean isCurrentlyPerforming = getBooleanObjVar(self, baseObjVar + dot + performCurrentlyPerformingVarName);
            if (isCurrentlyPerforming)
            {
                int endTime = getIntObjVar(self, baseObjVar + dot + performEndTimeVarName);
                int remainingTime = endTime - performGetTime(self);
                groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "endTime = " + endTime);
                if (endTime > 0 && remainingTime <= 1)
                {
                    questCompleteTask(questCrc, taskId, self);
                }
                else 
                {
                    groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "not finished yet");
                    sendCheckPerformanceComplete(self, questCrc, taskId, remainingTime);
                }
            }
            else 
            {
                groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "player currently not performing");
            }
        }
        groundquests.questOutputDebugLog(taskType, "CheckPerformanceComplete", "end");
        return SCRIPT_CONTINUE;
    }
    public void clearPerformObjVars(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(player, baseObjVar + dot + performEndTimeVarName, 0);
        setObjVar(player, baseObjVar + dot + performCurrentlyPerformingVarName, false);
    }
    public void playerStoppedPerforming(obj_id player, boolean updateQuestTimer) throws InterruptedException
    {
        dictionary tasks = groundquests.getActiveTasksForTaskType(player, taskType);
        if ((tasks != null) && !tasks.isEmpty())
        {
            java.util.Enumeration keys = tasks.keys();
            while (keys.hasMoreElements())
            {
                String questCrcString = (String)keys.nextElement();
                int questCrc = utils.stringToInt(questCrcString);
                int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int taskId = tasksForCurrentQuest[i];
                    clearPerformObjVars(player, questCrc, taskId);
                    boolean isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible) != 0;
                    if (updateQuestTimer && isVisible)
                    {
                        questSetQuestTaskTimer(player, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", performGetTime(player));
                    }
                }
            }
        }
    }
    public int performGetTime(obj_id player) throws InterruptedException
    {
        return getPlayerPlayedTime(player);
    }
    public void sendCheckPerformanceComplete(obj_id player, int questCrc, int taskId, int delay) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "sendCheckPerformanceComplete", "sending check message for delay [" + delay + "]");
        dictionary params = new dictionary();
        params.put("questcrc", questCrc);
        params.put("taskid", taskId);
        messageTo(player, "CheckPerformanceComplete", params, delay, false);
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
}
