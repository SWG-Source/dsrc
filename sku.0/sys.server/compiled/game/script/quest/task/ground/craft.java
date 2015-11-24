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

public class craft extends script.quest.task.ground.base_task
{
    public craft()
    {
    }
    public static final String dataTableColumnServerTemplate = "SERVER_OBJECT_TEMPLATE";
    public static final String dataTableColumnCount = "COUNT";
    public static final String objvarCount = "count";
    public static final String dot = ".";
    public static final String taskType = "craft";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(self, baseObjVar + dot + objvarCount, 0);
        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnCount);
        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:crafting_counter", 0, count);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnCraftedPrototype(obj_id self, obj_id prototypeObject, draft_schematic manufacturingSchematic) throws InterruptedException
    {
        int prototypeObjectTemplateCrc = getObjectTemplateCrc(getTemplateName(prototypeObject));
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
                                String craftedObjectTemplateName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnServerTemplate);
                                int objectTemplateCrc = getObjectTemplateCrc(craftedObjectTemplateName);
                                if (objectTemplateCrc != 0)
                                {
                                    if (objectTemplateCrc == prototypeObjectTemplateCrc)
                                    {
                                        String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                                        String countObjVar = baseObjVar + dot + objvarCount;
                                        int currentCount = getIntObjVar(self, countObjVar);
                                        ++currentCount;
                                        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnCount);
                                        if (currentCount >= count)
                                        {
                                            questCompleteTask(questCrc, taskId, self);
                                        }
                                        else 
                                        {
                                            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnCraftedPrototype", currentCount + "/" + count + " crafted.");
                                            setObjVar(self, countObjVar, currentCount);
                                            questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:crafting_counter", currentCount, count);
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
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
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
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int taskId = tasksForCurrentQuest[i];
                    String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                    String countObjVar = baseObjVar + dot + objvarCount;
                    if (hasObjVar(self, countObjVar))
                    {
                        int currentCount = getIntObjVar(self, countObjVar);
                        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnCount);
                        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:crafting_counter", 0, count);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
