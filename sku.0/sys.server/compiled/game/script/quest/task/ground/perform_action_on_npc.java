package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class perform_action_on_npc extends script.quest.task.ground.base_task
{
    public perform_action_on_npc()
    {
    }
    public static final String dataTableColumnActionName = "ACTION_NAME";
    public static final String taskType = "perform_action_on_npc";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
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
    public int questActionCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        int questCrc = params.getInt(groundquests.QUEST_CRC);
        int taskId = params.getInt(groundquests.TASK_ID);
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "questActionCompleted", taskType + " quest actionc complete received.");
        if (questIsTaskActive(questCrc, taskId, self))
        {
            groundquests.questOutputDebugLog(taskType, "questActionCompleted", "Action completed successfully. Quest complete.");
            questCompleteTask(questCrc, taskId, self);
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
}
