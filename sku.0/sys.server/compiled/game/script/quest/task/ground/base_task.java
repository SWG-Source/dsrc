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

public class base_task extends script.base.remote_object_requester
{
    public base_task()
    {
    }
    public static final String taskType = "base_task";
    public static final String dataTableColumnQuestOnComplete = "GRANT_QUEST_ON_COMPLETE";
    public static final String dataTableColumnQuestOnFail = "GRANT_QUEST_ON_FAIL";
    public static final String dataTableColumnMusicOnActivate = "MUSIC_ON_ACTIVATE";
    public static final String dataTableColumnMusicOnComplete = "MUSIC_ON_COMPLETE";
    public static final String dataTableColumnMusicOnFail = "MUSIC_ON_FAIL";
    public static final String dataTableColumnQuestOnCompleteShowSystemMessage = "GRANT_QUEST_ON_COMPLETE_SHOW_SYSTEM_MESSAGE";
    public static final String dataTableColumnQuestOnFailShowSystemMessage = "GRANT_QUEST_ON_FAIL_SHOW_SYSTEM_MESSAGE";
    public static final String dataTableColumnTimerAmount = "TIMER_AMOUNT";
    public static final String dataTableColumnVisible = "IS_VISIBLE";
    public static final String dataTableColumnSignalsOnComplete = "SIGNALS_ON_COMPLETE";
    public static final String dataTableColumnSignalsOnFail = "SIGNALS_ON_FAIL";
    public static final String MUSIC_TASK_COMPLETED = "sound/music_themequest_victory_rebel.snd";
    public static final String MUSIC_TASK_FAILED = "sound/music_themequest_fail_criminal.snd";
    public static final String dot = ".";
    public static final String objvarWaypoint = "waypoint";
    public static final String objvarEntranceWaypoint = "entranceWaypoint";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        int isVisible = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnVisible);
        groundquests.createQuestWaypoints(questCrc, taskId, self);
        if (groundquests.isTaskVisible(questCrc, taskId))
        {
            String musicOnActivate = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMusicOnActivate);
            if (musicOnActivate != null && musicOnActivate.length() > 0)
            {
                play2dNonLoopingMusic(self, musicOnActivate);
            }
        }
        int timerLength = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnTimerAmount);
        if (timerLength > 0)
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "Setting timer for " + timerLength + " seconds.");
            final float playerPlayedTimeWhenTimerEnds = (float)getPlayerPlayedTime(self) + (float)timerLength;
            final String objVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId) + dot + groundquests.timeObjVar;
            setObjVar(self, objVar, playerPlayedTimeWhenTimerEnds);
            dictionary params = new dictionary();
            params.put("questcrc", questCrc);
            params.put("taskid", taskId);
            params.put("endTime", (int)getFloatObjVar(self, objVar));
            messageTo(self, "QuestBaseTaskTimerTaskCompleted", params, timerLength, true);
            if (isVisible != 0)
            {
                questSetQuestTaskTimer(self, questGetQuestName(questCrc), taskId, "quest/groundquests:timer_timertext", (int)playerPlayedTimeWhenTimerEnds);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int QuestBaseTaskTimerTaskCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        final int questCrc = params.getInt("questcrc");
        final int taskId = params.getInt("taskid");
        if (questIsTaskActive(questCrc, taskId, self))
        {
            final String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
            final float playerPlayedTimeWhenTimerEnds = getFloatObjVar(self, baseObjVar + dot + groundquests.timeObjVar);
            if (params.containsKey("endTime"))
            {
                final int endTime = params.getInt("endTime");
                if (endTime != (int)playerPlayedTimeWhenTimerEnds)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            final float timeLeft = playerPlayedTimeWhenTimerEnds - (float)getPlayerPlayedTime(self);
            if (timeLeft <= 0)
            {
                questFailTask(questCrc, taskId, self);
            }
            else 
            {
                dictionary newParams = new dictionary();
                newParams.put("questcrc", questCrc);
                newParams.put("taskid", taskId);
                newParams.put("endTime", (int)playerPlayedTimeWhenTimerEnds);
                messageTo(self, "QuestBaseTaskTimerTaskCompleted", newParams, timeLeft, true);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        String questOnComplete = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnQuestOnComplete);
        if (questOnComplete != null && questOnComplete.length() > 0)
        {
            boolean showSystemMessage = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnQuestOnCompleteShowSystemMessage, true);
            groundquests.grantQuestNoAcceptUI(self, questOnComplete, showSystemMessage);
        }
        if (groundquests.isTaskVisible(questCrc, taskId))
        {
            groundquests.sendTaskCompletedSystemMessage(questCrc, taskId, self);
            String musicOnComplete = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMusicOnComplete);
            if (musicOnComplete != null && musicOnComplete.length() > 0)
            {
                play2dNonLoopingMusic(self, musicOnComplete);
            }
        }
        sendTaskSignals(self, questCrc, taskId, dataTableColumnSignalsOnComplete);
        baseCleanup(self, questCrc, taskId);
        return SCRIPT_CONTINUE;
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        String questOnFail = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnQuestOnFail);
        if (questOnFail != null && questOnFail.length() > 0)
        {
            boolean showSystemMessage = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableColumnQuestOnFailShowSystemMessage, true);
            groundquests.grantQuest(self, questOnFail, showSystemMessage);
        }
        if (groundquests.isTaskVisible(questCrc, taskId))
        {
            groundquests.sendTaskFailedSystemMessage(questCrc, taskId, self);
            String musicOnFail = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMusicOnFail);
            if (musicOnFail != null && musicOnFail.length() > 0)
            {
                play2dNonLoopingMusic(self, musicOnFail);
            }
        }
        sendTaskSignals(self, questCrc, taskId, dataTableColumnSignalsOnFail);
        baseCleanup(self, questCrc, taskId);
        return SCRIPT_CONTINUE;
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        baseCleanup(self, questCrc, taskId);
        return SCRIPT_CONTINUE;
    }
    public void baseCleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        obj_id waypoint = getObjIdObjVar(player, baseObjVar + dot + objvarWaypoint);
        if (isIdValid(waypoint))
        {
            groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "baseCleanup", taskType + " destroying waypoint: " + waypoint);
            destroyWaypointInDatapad(waypoint, player);
        }
        obj_id entranceWaypoint = getObjIdObjVar(player, baseObjVar + dot + objvarEntranceWaypoint);
        if (isIdValid(entranceWaypoint))
        {
            groundquests.questOutputDebugInfo(player, questCrc, taskId, taskType, "baseCleanup", taskType + " destroying entranceWaypoint: " + entranceWaypoint);
            destroyWaypointInDatapad(entranceWaypoint, player);
        }
        removeObjVar(player, baseObjVar);
    }
    public void sendTaskSignals(obj_id player, int questCrc, int taskId, String dataTableColumnName) throws InterruptedException
    {
        String signals = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnName);
        if (signals != null && signals.length() > 0)
        {
            groundquests.sendSignals(player, split(signals, ','));
        }
    }
    public int OnLogin(obj_id self) throws InterruptedException
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
                    if (hasObjVar(self, baseObjVar + dot + groundquests.objvarWaypointInActive))
                    {
                        if (hasObjVar(self, baseObjVar + dot + groundquests.objvarEntranceWaypoint))
                        {
                            obj_id entranceWaypoint = getObjIdObjVar(self, baseObjVar + dot + groundquests.objvarEntranceWaypoint);
                            if (isIdValid(entranceWaypoint))
                            {
                                groundquests.setQuestWaypointActive(entranceWaypoint, self, baseObjVar);
                            }
                        }
                        if (hasObjVar(self, baseObjVar + dot + groundquests.objvarWaypoint))
                        {
                            obj_id waypoint = getObjIdObjVar(self, baseObjVar + dot + groundquests.objvarWaypoint);
                            if (isIdValid(waypoint))
                            {
                                groundquests.setQuestWaypointActive(waypoint, self, baseObjVar);
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
