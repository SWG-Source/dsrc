package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.quests;
import script.library.create;
import script.library.groundquests;
import script.library.locations;
import script.library.utils;

public class assassinate extends script.quest.task.ground.base_task
{
    public assassinate()
    {
    }
    public static final String dataTableColumnServerTemplate = "SERVER_TEMPLATE";
    public static final String dataTableColumnDestinationNodeName = "DESTINATION_NODE_NAME";
    public static final String dataTableColumnMovementType = "MOVEMENT_TYPE";
    public static final String objvarAsassinateTarget = "target";
    public static final String taskType = "assassinate";
    public static final String dot = ".";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        String serverTemplate = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnServerTemplate);
        location l = groundquests.getRandom2DLocationAroundPlayer(self, 5, 15);
        obj_id assassinateTarget = create.createCreature(serverTemplate, l, true);
        groundquests.addDestroyNotification(assassinateTarget, self);
        setObjVar(self, baseObjVar + dot + objvarAsassinateTarget, assassinateTarget);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int destroyNotification(obj_id self, dictionary params) throws InterruptedException
    {
        if (params != null)
        {
            obj_id source = params.getObjId("source");
            String taskObjVarName = groundquests.getTaskTypeObjVar(self, taskType);
            if (taskObjVarName != null)
            {
                obj_var_list questList = getObjVarList(self, taskObjVarName);
                if (questList != null)
                {
                    int countQuests = questList.getNumItems();
                    for (int i = 0; i < countQuests; ++i)
                    {
                        obj_var ov = questList.getObjVar(i);
                        String questName = ov.getName();
                        int questCrc = questGetQuestId(questName);
                        String questObjVarName = taskObjVarName + dot + questName;
                        if (hasObjVar(self, questObjVarName))
                        {
                            obj_var_list taskList = getObjVarList(self, questObjVarName);
                            if (taskList != null)
                            {
                                int countTasks = taskList.getNumItems();
                                for (int j = 0; j < countTasks; ++j)
                                {
                                    obj_var ov2 = taskList.getObjVar(j);
                                    String taskName = ov2.getName();
                                    int taskId = utils.stringToInt(taskName);
                                    String assassinateTargetObjVarName = questObjVarName + dot + taskName + dot + objvarAsassinateTarget;
                                    if (hasObjVar(self, assassinateTargetObjVarName))
                                    {
                                        obj_id assassinateTarget = getObjIdObjVar(self, assassinateTargetObjVarName);
                                        if (source == assassinateTarget)
                                        {
                                            questCompleteTask(questCrc, taskId, self);
                                        }
                                    }
                                }
                            }
                        }
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
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.getBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        String assassinateTargetObjVarName = baseObjVar + dot + objvarAsassinateTarget;
        obj_id assassinateTarget = getObjIdObjVar(player, assassinateTargetObjVarName);
        if (assassinateTarget != null)
        {
            removeObjVar(player, assassinateTargetObjVarName);
            if (!ai_lib.isAiDead(assassinateTarget))
            {
                destroyObject(assassinateTarget);
            }
        }
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        groundquests.failAllActiveTasksOfType(self, taskType);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
}
