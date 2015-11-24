package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.groundquests;
import script.library.prose;
import script.library.utils;

public class wave_event_player extends script.quest.task.ground.base_task
{
    public wave_event_player()
    {
    }
    public static final String dataTableColumnServerTemplate = "SERVER_TEMPLATE";
    public static final String dataTableColumnMenuText = "RETRIEVE_MENU_TEXT";
    public static final String taskType = "wave_event";
    public static final String dot = ".";
    public static final String RETRIEVE_ITEM_MUSIC = "sound/ui_received_quest_item.snd";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int questWaveEventCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id source = params.getObjId("source");
        if (!groundquests.playerNeedsToRetrieveThisItem(self, source))
        {
            groundquests.questOutputDebugInfo(self, taskType, "questWaveEventCompleted", "Player [" + self + "] didn't need wave event item [" + source + "].");
            return SCRIPT_CONTINUE;
        }
        String sourceTemplateName = getTemplateName(source);
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
                    String retrieveTemplateName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnServerTemplate);
                    if (sourceTemplateName.equals(retrieveTemplateName))
                    {
                        questCompleteTask(questCrc, taskId, self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
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
        return SCRIPT_CONTINUE;
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
