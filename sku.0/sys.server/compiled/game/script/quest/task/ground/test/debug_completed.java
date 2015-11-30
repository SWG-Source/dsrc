package script.quest.task.ground.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class debug_completed extends script.quest.task.ground.base_task
{
    public debug_completed()
    {
    }
    public int failedDebugTask(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug_completed:failedDebugTask(" + params + ")");
        if (params != null)
        {
            int questCrc = params.getInt("questCrc");
            int taskId = params.getInt("taskId");
            LOG("groundquests", "quest.task.ground.debug_completed:failedDebugTask() - failing task " + taskId + " for questCrc " + questCrc);
            questFailTask(questCrc, taskId, self);
        }
        else 
        {
            LOG("groundquests", "quest.task.ground.debug_completed:failedDebugTask() - params is null!");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug_completed:OnTaskActivated(" + questCrc + ", " + taskId + ")");
        dictionary params = new dictionary();
        params.put("questCrc", questCrc);
        params.put("taskId", taskId);
        messageTo(self, "failedDebugTask", params, 5.0f, false);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
}
