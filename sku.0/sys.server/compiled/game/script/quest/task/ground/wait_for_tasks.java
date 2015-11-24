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

public class wait_for_tasks extends script.quest.task.ground.base_task
{
    public wait_for_tasks()
    {
    }
    public static final String dataTableQuestNamePre = "TASK_QUEST_NAME_";
    public static final String dataTableTaskNamePre = "TASK_TASK_NAME_";
    public static final String dataTableDisplayStringIdPre = "TASK_DISPLAY_STRING_";
    public static final String questCrcsListObjVarName = "questCrcs";
    public static final String taskIdsListObjVarName = "taskIds";
    public static final String taskIndexesListObjVarName = "taskIndexes";
    public static final String completedListObjVarName = "completed";
    public static final String taskType = "wait_for_tasks";
    public static final String dot = ".";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        int waitForTaskIndex = 1;
        Vector questCrcsListResizable = new Vector();
        questCrcsListResizable.setSize(0);
        Vector taskIdsListResizable = new Vector();
        taskIdsListResizable.setSize(0);
        Vector taskIndexesListResizable = new Vector();
        taskIndexesListResizable.setSize(0);
        Vector completedListResizable = new Vector();
        completedListResizable.setSize(0);
        boolean done = false;
        while (!done)
        {
            String dataTableQuestName = dataTableQuestNamePre + waitForTaskIndex;
            String questName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableQuestName);
            if (questName != null && questName.length() > 0)
            {
                String dataTableTaskName = dataTableTaskNamePre + waitForTaskIndex;
                String taskName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableTaskName);
                if (taskName != null && taskName.length() > 0)
                {
                    String dataTableDisplayStringId = dataTableDisplayStringIdPre + waitForTaskIndex;
                    String displayStringId = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableDisplayStringId);
                    int taskQuestCrc = groundquests.getQuestIdFromString(questName);
                    int taskTaskId = groundquests.getTaskId(taskQuestCrc, taskName);
                    utils.addElement(questCrcsListResizable, taskQuestCrc);
                    utils.addElement(taskIdsListResizable, taskTaskId);
                    utils.addElement(taskIndexesListResizable, waitForTaskIndex);
                    utils.addElement(completedListResizable, 0);
                    ++waitForTaskIndex;
                    if (displayStringId != null && displayStringId.length() > 0)
                    {
                        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, displayStringId, 0, 1);
                    }
                }
                else 
                {
                    done = true;
                }
            }
            else 
            {
                done = true;
            }
        }
        if (questCrcsListResizable.size() == taskIdsListResizable.size())
        {
            if (questCrcsListResizable.size() > 0)
            {
                int[] questCrcsList = new int[0];
                if (questCrcsListResizable != null)
                {
                    questCrcsList = new int[questCrcsListResizable.size()];
                    for (int _i = 0; _i < questCrcsListResizable.size(); ++_i)
                    {
                        questCrcsList[_i] = ((Integer)questCrcsListResizable.get(_i)).intValue();
                    }
                }
                int[] taskIdsList = new int[0];
                if (taskIdsListResizable != null)
                {
                    taskIdsList = new int[taskIdsListResizable.size()];
                    for (int _i = 0; _i < taskIdsListResizable.size(); ++_i)
                    {
                        taskIdsList[_i] = ((Integer)taskIdsListResizable.get(_i)).intValue();
                    }
                }
                int[] taskIndexesList = new int[0];
                if (taskIndexesListResizable != null)
                {
                    taskIndexesList = new int[taskIndexesListResizable.size()];
                    for (int _i = 0; _i < taskIndexesListResizable.size(); ++_i)
                    {
                        taskIndexesList[_i] = ((Integer)taskIndexesListResizable.get(_i)).intValue();
                    }
                }
                int[] completedList = new int[0];
                if (completedListResizable != null)
                {
                    completedList = new int[completedListResizable.size()];
                    for (int _i = 0; _i < completedListResizable.size(); ++_i)
                    {
                        completedList[_i] = ((Integer)completedListResizable.get(_i)).intValue();
                    }
                }
                setObjVar(self, baseObjVar + dot + questCrcsListObjVarName, questCrcsList);
                setObjVar(self, baseObjVar + dot + taskIdsListObjVarName, taskIdsList);
                setObjVar(self, baseObjVar + dot + taskIndexesListObjVarName, taskIndexesList);
                setObjVar(self, baseObjVar + dot + completedListObjVarName, completedList);
            }
            else 
            {
                groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "[designer bug] No valid tasks to wait for found, are the task names valid names in the given quests?");
            }
        }
        else 
        {
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", "[programmer bug] Task arrays mismatched, this shouldn't happen.");
        }
        dictionary webster = new dictionary();
        webster.put("questCrc", questCrc);
        webster.put("taskId", taskId);
        messageTo(self, "checkForAlreadyCompletedTasks", webster, 1, false);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int checkForAlreadyCompletedTasks(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt("questCrc");
        int taskId = params.getInt("taskId");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        int waitForTaskIndex = 1;
        boolean done = false;
        while (!done)
        {
            String dataTableQuestName = dataTableQuestNamePre + waitForTaskIndex;
            String questName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableQuestName);
            if (questName != null && questName.length() > 0)
            {
                String dataTableTaskName = dataTableTaskNamePre + waitForTaskIndex;
                String taskName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableTaskName);
                if (taskName != null && taskName.length() > 0)
                {
                    String dataTableDisplayStringId = dataTableDisplayStringIdPre + waitForTaskIndex;
                    String displayStringId = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableDisplayStringId);
                    int taskQuestCrc = groundquests.getQuestIdFromString(questName);
                    int taskTaskId = groundquests.getTaskId(taskQuestCrc, taskName);
                    if (questIsTaskComplete(taskQuestCrc, taskTaskId, self))
                    {
                        handleSomeTaskFinished(self, taskQuestCrc, taskTaskId);
                    }
                    ++waitForTaskIndex;
                }
                else 
                {
                    done = true;
                }
            }
            else 
            {
                done = true;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSomeTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        handleSomeTaskFinished(self, questCrc, taskId);
        return SCRIPT_CONTINUE;
    }
    public int OnSomeTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        handleSomeTaskFinished(self, questCrc, taskId);
        return SCRIPT_CONTINUE;
    }
    public void handleSomeTaskFinished(obj_id player, int finishedTaskQuestCrc, int finishedTaskTaskId) throws InterruptedException
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
                for (int taskIndex = 0; taskIndex < tasksForCurrentQuest.length; ++taskIndex)
                {
                    int taskId = tasksForCurrentQuest[taskIndex];
                    String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
                    int[] questCrcsList = getIntArrayObjVar(player, baseObjVar + dot + questCrcsListObjVarName);
                    int[] taskIdsList = getIntArrayObjVar(player, baseObjVar + dot + taskIdsListObjVarName);
                    int[] taskIndexesList = getIntArrayObjVar(player, baseObjVar + dot + taskIndexesListObjVarName);
                    int[] completedList = getIntArrayObjVar(player, baseObjVar + dot + completedListObjVarName);
                    if (questCrcsList != null)
                    {
                        for (int i = 0; i < questCrcsList.length; ++i)
                        {
                            if ((finishedTaskQuestCrc == questCrcsList[i]) && (finishedTaskTaskId == taskIdsList[i]))
                            {
                                String dataTableDisplayStringId = dataTableDisplayStringIdPre + taskIndexesList[i];
                                String displayStringId = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableDisplayStringId);
                                questSetQuestTaskCounter(player, questGetQuestName(questCrc), taskId, displayStringId, 1, 1);
                                play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                                groundquests.playJournalUpdatedMusic(questCrc, taskId, player);
                                Vector questCrcsListResizable = new Vector();
                                questCrcsListResizable.setSize(0);
                                Vector taskIdsListResizable = new Vector();
                                taskIdsListResizable.setSize(0);
                                Vector taskIndexesListResizable = new Vector();
                                taskIndexesListResizable.setSize(0);
                                Vector completedListResizable = new Vector();
                                completedListResizable.setSize(0);
                                for (int l = 0; l < questCrcsList.length; ++l)
                                {
                                    utils.addElement(questCrcsListResizable, questCrcsList[l]);
                                    utils.addElement(taskIdsListResizable, taskIdsList[l]);
                                    utils.addElement(taskIndexesListResizable, taskIndexesList[l]);
                                    if (l == i)
                                    {
                                        utils.addElement(completedListResizable, 1);
                                    }
                                    else 
                                    {
                                        utils.addElement(completedListResizable, completedList[l]);
                                    }
                                }
                                boolean allTasksComplete = true;
                                for (int j = 0; j < completedListResizable.size(); ++j)
                                {
                                    if (((Integer)completedListResizable.get(j)).intValue() != 1)
                                    {
                                        allTasksComplete = false;
                                        break;
                                    }
                                }
                                if (allTasksComplete)
                                {
                                    questCompleteTask(questCrc, taskId, player);
                                    return;
                                }
                                else 
                                {
                                    int[] newQuestCrcsList = new int[0];
                                    if (questCrcsListResizable != null)
                                    {
                                        newQuestCrcsList = new int[questCrcsListResizable.size()];
                                        for (int _i = 0; _i < questCrcsListResizable.size(); ++_i)
                                        {
                                            newQuestCrcsList[_i] = ((Integer)questCrcsListResizable.get(_i)).intValue();
                                        }
                                    }
                                    int[] newTaskIdsList = new int[0];
                                    if (taskIdsListResizable != null)
                                    {
                                        newTaskIdsList = new int[taskIdsListResizable.size()];
                                        for (int _i = 0; _i < taskIdsListResizable.size(); ++_i)
                                        {
                                            newTaskIdsList[_i] = ((Integer)taskIdsListResizable.get(_i)).intValue();
                                        }
                                    }
                                    int[] newTaskIndexesList = new int[0];
                                    if (taskIndexesListResizable != null)
                                    {
                                        newTaskIndexesList = new int[taskIndexesListResizable.size()];
                                        for (int _i = 0; _i < taskIndexesListResizable.size(); ++_i)
                                        {
                                            newTaskIndexesList[_i] = ((Integer)taskIndexesListResizable.get(_i)).intValue();
                                        }
                                    }
                                    int[] newCompletedList = new int[0];
                                    if (completedListResizable != null)
                                    {
                                        newCompletedList = new int[completedListResizable.size()];
                                        for (int _i = 0; _i < completedListResizable.size(); ++_i)
                                        {
                                            newCompletedList[_i] = ((Integer)completedListResizable.get(_i)).intValue();
                                        }
                                    }
                                    setObjVar(player, baseObjVar + dot + questCrcsListObjVarName, newQuestCrcsList);
                                    setObjVar(player, baseObjVar + dot + taskIdsListObjVarName, newTaskIdsList);
                                    setObjVar(player, baseObjVar + dot + taskIndexesListObjVarName, newTaskIndexesList);
                                    setObjVar(player, baseObjVar + dot + completedListObjVarName, newCompletedList);
                                }
                            }
                        }
                    }
                }
            }
        }
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
                for (int j = 0; j < tasksForCurrentQuest.length; ++j)
                {
                    int taskId = tasksForCurrentQuest[j];
                    String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                    int[] taskIndexesList = getIntArrayObjVar(self, baseObjVar + dot + taskIndexesListObjVarName);
                    int[] completedList = getIntArrayObjVar(self, baseObjVar + dot + completedListObjVarName);
                    if (taskIndexesList != null)
                    {
                        for (int i = 0; i < taskIndexesList.length; ++i)
                        {
                            String dataTableDisplayStringId = dataTableDisplayStringIdPre + taskIndexesList[i];
                            String displayStringId = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableDisplayStringId);
                            questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, displayStringId, completedList[i], 1);
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        String legacyObjVarName = "quest.wait_for_tasks";
        Vector legacyObjVarValue = null;
        if (hasObjVar(self, legacyObjVarName))
        {
            legacyObjVarValue = getResizeableStringArrayObjVar(self, legacyObjVarName);
        }
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        if (!hasObjVar(self, legacyObjVarName) && legacyObjVarValue != null && legacyObjVarValue.size() > 0)
        {
            CustomerServiceLog("fs_quests", "restoring clobbered objvar " + legacyObjVarName + " to %TU", self, null);
            setObjVar(self, legacyObjVarName, legacyObjVarValue);
        }
        return SCRIPT_CONTINUE;
    }
}
