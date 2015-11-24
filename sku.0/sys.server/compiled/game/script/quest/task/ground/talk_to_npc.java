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

public class talk_to_npc extends script.quest.task.ground.base_task
{
    public talk_to_npc()
    {
    }
    public static final String dataTableColumnExperienceType = "NPC_NAME";
    public static final String taskType = "talk_to_npc";
    public static final String dot = ".";
    public static final String objvarNPCName = "npcName";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + "task activated.");
        String npcName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnExperienceType);
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(self, baseObjVar + dot + objvarNPCName, npcName);
        groundquests.questOutputDebugInfo(self, taskType, "OnStartConversation", "Player must talk to " + npcName + ".");
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnStartConversation(obj_id self, obj_id conversant) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, taskType, "OnStartConversation", "Checking for talk_to_npc quest task completion.");
        String name = getName(conversant);
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
                                String nameObjVarName = questObjVarName + dot + taskName + dot + objvarNPCName;
                                if (hasObjVar(self, nameObjVarName))
                                {
                                    String npcName = getStringObjVar(self, nameObjVarName);
                                    if (name.equals(npcName))
                                    {
                                        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnStartConversation", "Target NPC " + npcName + " found.");
                                        questCompleteTask(questGetQuestId(questName), utils.stringToInt(taskName), self);
                                    }
                                    else 
                                    {
                                        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnStartConversation", "NPC spoken to (" + name + ") does not match target (" + npcName + ").");
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
}
