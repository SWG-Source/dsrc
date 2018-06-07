package script.quest.task.ground.test;

import script.dictionary;
import script.obj_id;

public class debug extends script.quest.task.ground.base_task
{
    public debug()
    {
    }
    public int completeDebugTask(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug:completeDebugTask(" + params + ")");
        if (params != null)
        {
            int questCrc = params.getInt("questCrc");
            int taskId = params.getInt("taskId");
            LOG("groundquests", "quest.task.ground.debug:completeDebugTask() - completing task " + taskId + " for questCrc " + questCrc);
            questCompleteTask(questCrc, taskId, self);
        }
        else 
        {
            LOG("groundquests", "quest.task.ground.debug:completeDebugTask() - params is null!");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug:OnTaskActivated(" + questCrc + ", " + taskId + ")");
        dictionary params = new dictionary();
        params.put("questCrc", questCrc);
        params.put("taskId", taskId);
        messageTo(self, "completeDebugTask", params, 5.0f, false);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
}
