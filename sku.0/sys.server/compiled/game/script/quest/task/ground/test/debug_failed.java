package script.quest.task.ground.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class debug_failed extends script.quest.task.ground.base_task
{
    public debug_failed()
    {
    }
    public int completeDebugQuest(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug_failed:completeDebugQuest(" + params + ")");
        if (params != null)
        {
            int questCrc = params.getInt("questCrc");
            int taskId = params.getInt("taskId");
            LOG("groundquests", "quest.task.ground.debug_failed:completeDebugQuest() - completing questCrc " + questCrc);
            questCompleteTask(questCrc, taskId, self);
        }
        else 
        {
            LOG("groundquests", "quest.task.ground.debug_failed:completeDebugQuest() - params is null!");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug_failed:OnTaskCompleted(" + questCrc + ", " + taskId + ")");
        questCompleteQuest(questCrc, self);
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        LOG("groundquests", "quest.task.ground.debug_failed:OnTaskActivated(" + questCrc + ", " + taskId + ")");
        dictionary params = new dictionary();
        params.put("questCrc", questCrc);
        params.put("taskId", taskId);
        messageTo(self, "completeDebugQuest", params, 5.0f, false);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
}
