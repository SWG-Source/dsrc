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

public class comm_player extends script.quest.task.ground.base_task
{
    public comm_player()
    {
    }
    public static final String dataTableColumnCommMessageText = "COMM_MESSAGE_TEXT";
    public static final String dataTableColumnNPCAppearanceServerTemplate = "NPC_APPEARANCE_SERVER_TEMPLATE";
    public static final String taskType = "comm_player";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        questCompleteTask(questCrc, taskId, self);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        String message = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnCommMessageText);
        String appearance = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnNPCAppearanceServerTemplate);
        prose_package pp = new prose_package();
        pp.stringId = utils.unpackString(message);
        commPlayer(self, self, pp, appearance);
        return super.OnTaskCompleted(self, questCrc, taskId);
    }
    public int OnTaskFailed(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskFailed", taskType + " task failed.");
        return super.OnTaskFailed(self, questCrc, taskId);
    }
    public int OnTaskCleared(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + " task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
}
