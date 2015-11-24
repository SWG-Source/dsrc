package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.groundquests;
import script.library.utils;
import script.library.space_quest;

public class grant_space_quest extends script.quest.task.ground.base_task
{
    public grant_space_quest()
    {
    }
    public static final int winner = 1;
    public static final int failure = 2;
    public static final int split = 3;
    public static final String spaceQuestIdentifier = ".spaceQuestIdentifier";
    public static final String spaceQuestName = "SPACE_QUEST_NAME";
    public static final String spaceQuestType = "SPACE_QUEST_TYPE";
    public static final String ignoreSplit = "IGNORE_SPLIT";
    public static final String taskType = "grantSpaceQuest";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        String spaceName = groundquests.getTaskStringDataEntry(questCrc, taskId, spaceQuestName);
        String spaceType = groundquests.getTaskStringDataEntry(questCrc, taskId, spaceQuestType);
        setObjVar(self, baseObjVar + ".spaceQuestIdentifier", spaceName);
        space_quest.grantQuest(self, spaceType, spaceName);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int groundSpaceWinner(obj_id self, dictionary params) throws InterruptedException
    {
        handleSpaceQuestMessaging(self, params, winner);
        return SCRIPT_CONTINUE;
    }
    public int groundSpaceSplit(obj_id self, dictionary params) throws InterruptedException
    {
        handleSpaceQuestMessaging(self, params, split);
        return SCRIPT_CONTINUE;
    }
    public int groundSpaceFailed(obj_id self, dictionary params) throws InterruptedException
    {
        handleSpaceQuestMessaging(self, params, failure);
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
    public void handleSpaceQuestMessaging(obj_id self, dictionary params, int spaceQuestValue) throws InterruptedException
    {
        String spaceQuestName = params.getString("questName");
        String splitQuestName = "";
        if (spaceQuestValue == split)
        {
            splitQuestName = params.getString("splitQuestName");
        }
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
                    String spaceQuestIdentifierVariableName = baseObjVar + spaceQuestIdentifier;
                    if (hasObjVar(self, spaceQuestIdentifierVariableName))
                    {
                        String spaceQuest = getStringObjVar(self, spaceQuestIdentifierVariableName);
                        if (spaceQuest.equals(spaceQuestName))
                        {
                            if (spaceQuestValue == winner)
                            {
                                questCompleteTask(questCrc, taskId, self);
                            }
                            else if (spaceQuestValue == failure)
                            {
                                questFailTask(questCrc, taskId, self);
                            }
                            else if (spaceQuestValue == split)
                            {
                                setObjVar(self, spaceQuestIdentifierVariableName, splitQuestName);
                            }
                            groundquests.questOutputDebugLog(taskType, "grantSpaceQuest", "start");
                        }
                    }
                }
            }
        }
    }
}
