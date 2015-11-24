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

public class timer extends script.quest.task.ground.base_task
{
    public timer()
    {
    }
    public static final String dataTableColumnMinTime = "MIN_TIME";
    public static final String dataTableColumnMaxTime = "MAX_TIME";
    public static final String dataTableColumnVisible = "IS_VISIBLE";
    public static final String timeObjVar = "playedTimeEnd";
    public static final String taskType = "timer";
    public static final String dot = ".";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + "task activated.");
        float minTime = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMinTime);
        float maxTime = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMaxTime);
        int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
        if (minTime > maxTime)
        {
            float temp = minTime;
            minTime = maxTime;
            maxTime = temp;
        }
        float timerLength = rand(minTime, maxTime);
        float playerPlayedTimeWhenTimerEnds = (float)getPlayerPlayedTime(self) + timerLength;
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Setting timer for " + timerLength + " seconds.");
        dictionary params = new dictionary();
        params.put("questcrc", questCrc);
        params.put("taskid", taskId);
        messageTo(self, "QuestTimerTaskCompleted", params, timerLength, true);
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(self, baseObjVar + dot + timeObjVar, playerPlayedTimeWhenTimerEnds);
        if (isVisible != 0)
        {
            questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", (int)playerPlayedTimeWhenTimerEnds);
        }
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int QuestTimerTaskCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt("questcrc");
        int taskId = params.getInt("taskid");
        if (questIsTaskActive(questCrc, taskId, self))
        {
            String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
            float playerPlayedTimeWhenTimerEnds = getFloatObjVar(self, baseObjVar + dot + timeObjVar);
            float timeLeft = playerPlayedTimeWhenTimerEnds - (float)getPlayerPlayedTime(self);
            if (timeLeft <= 0)
            {
                questCompleteTask(questCrc, taskId, self);
            }
            else 
            {
                dictionary newParams = new dictionary();
                newParams.put("questcrc", questCrc);
                newParams.put("taskid", taskId);
                messageTo(self, "QuestTimerTaskCompleted", newParams, timeLeft, true);
            }
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
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + "task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int handleClientLogin(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
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
                    String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                    float playerPlayedTimeWhenTimerEnds = getFloatObjVar(self, baseObjVar + dot + timeObjVar);
                    if ((float)getPlayerPlayedTime(self) < playerPlayedTimeWhenTimerEnds)
                    {
                        int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
                        if (isVisible != 0)
                        {
                            questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", (int)playerPlayedTimeWhenTimerEnds);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
