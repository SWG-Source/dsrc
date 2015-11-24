package script.quest.task;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;

public class wait_for_tasks extends script.base_script
{
    public wait_for_tasks()
    {
    }
    public int OnForceSensitiveQuestCompleted(obj_id self, String questName, boolean succeeded) throws InterruptedException
    {
        if (quests.isMyQuest(quests.getQuestId(questName), "quest.task.wait_for_tasks"))
        {
            return SCRIPT_CONTINUE;
        }
        LOG("newquests", "wait_for_tasks:OnForceSensitiveQuestCompleted(" + questName + ", " + succeeded + ")");
        String objvarName = "quest.wait_for_tasks";
        if (quests.safeHasObjVar(self, objvarName))
        {
            Vector waiting = getResizeableStringArrayObjVar(self, objvarName);
            if (waiting != null && waiting.size() > 0)
            {
                int iter = 0;
                for (iter = 0; iter < waiting.size(); )
                {
                    String waitingTaskName = ((String)waiting.get(iter));
                    if (waitingTaskName != null && waitingTaskName.length() > 0)
                    {
                        String waitingObjVarName = "quest." + waitingTaskName + ".target";
                        if (hasObjVar(self, waitingObjVarName))
                        {
                            Vector tasks = getResizeableStringArrayObjVar(self, waitingObjVarName);
                            if (tasks != null && tasks.size() > 0)
                            {
                                if (tasks.contains(questName))
                                {
                                    tasks.removeElement(questName);
                                    String waitingStatusObjVarName = "quest." + waitingTaskName + ".status";
                                    if (tasks.size() < 1)
                                    {
                                        removeObjVar(self, waitingObjVarName);
                                        boolean finalResult = succeeded;
                                        if (hasObjVar(self, waitingStatusObjVarName))
                                        {
                                            if (succeeded)
                                            {
                                                if (!getBooleanObjVar(self, waitingStatusObjVarName))
                                                {
                                                    finalResult = false;
                                                }
                                            }
                                            removeObjVar(self, waitingStatusObjVarName);
                                        }
                                        waiting.removeElement(waitingTaskName);
                                        removeObjVar(self, waitingObjVarName);
                                        LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - completing task " + waitingTaskName);
                                        quests.complete(waitingTaskName, self, finalResult);
                                        if (waiting.size() < 1)
                                        {
                                            safeRemoveObjVar(self, objvarName);
                                            return SCRIPT_CONTINUE;
                                        }
                                    }
                                    else 
                                    {
                                        tasks.removeElement(questName);
                                        setObjVar(self, waitingObjVarName, tasks);
                                        if (!succeeded)
                                        {
                                            setObjVar(self, waitingStatusObjVarName, succeeded);
                                        }
                                        ++iter;
                                        LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - there are " + tasks.size() + " tasks remaining");
                                    }
                                }
                                else 
                                {
                                    ++iter;
                                    LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - the task was not found in the pending list, ignoring it");
                                }
                            }
                            else 
                            {
                                quests.complete(waitingTaskName, self, false);
                                waiting.removeElement(waitingTaskName);
                                LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - ERROR: no task list is associated with " + waitingTaskName);
                            }
                        }
                        else 
                        {
                            quests.complete(waitingTaskName, self, false);
                            waiting.removeElement(waitingTaskName);
                            LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - ERROR: the task is in an undefined state, missing the tracking objvar " + waitingObjVarName);
                        }
                    }
                    else 
                    {
                        waiting.removeElementAt(iter);
                        LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - ERROR: a null entry was in the task list");
                    }
                }
            }
            else 
            {
                safeRemoveObjVar(self, objvarName);
                detachScript(self, "quest.task.wait_for_tasks");
                LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - ERROR: there are no tasks in the task list " + objvarName);
            }
        }
        else 
        {
            detachScript(self, "quest.task.wait_for_tasks");
            LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() - ERROR: missing tracking objvar " + objvarName);
        }
        LOG("newquests", "wait_for_tasks: OnForceSensitiveQuestCompleted() returning");
        return SCRIPT_CONTINUE;
    }
    public int OnQuestActivated(obj_id self, int questRow) throws InterruptedException
    {
        if (quests.isMyQuest(questRow, "quest.task.wait_for_tasks"))
        {
            String questName = quests.getDataEntry(questRow, "NAME");
            String target = null;
            String[] tasks = null;
            String objvarName = "quest." + questName + ".target";
            if (hasObjVar(self, objvarName))
            {
                tasks = getStringArrayObjVar(self, objvarName);
            }
            else 
            {
                target = quests.getDataEntry(questRow, "TARGET");
                if (target != null && target.length() > 0)
                {
                    tasks = split(target, ';');
                }
            }
            if (tasks != null && tasks.length > 0)
            {
                setObjVar(self, objvarName, tasks);
                LOG("newquests", "wait_for_tasks: OnQuestActivated - set " + objvarName);
            }
            else 
            {
                LOG("newquests", "wait_for_tasks: OnQuestActivated - failed to set " + objvarName);
            }
            Vector waiting = null;
            objvarName = "quest.wait_for_tasks";
            if (hasObjVar(self, objvarName))
            {
                waiting = getResizeableStringArrayObjVar(self, objvarName);
                if (waiting == null)
                {
                    waiting = new Vector();
                }
            }
            else 
            {
                waiting = new Vector();
            }
            if (!waiting.contains(questName))
            {
                waiting.add(questName);
                if (waiting.size() > 0)
                {
                    setObjVar(self, objvarName, waiting);
                    LOG("newquests", "wait_for_tasks: OnQuestActivated - set " + objvarName);
                }
                else 
                {
                    LOG("newquests", "wait_for_tasks: OnQuestActivated - failed to set " + objvarName);
                }
            }
            else 
            {
                LOG("newquests", "wait_for_tasks: OnQuestActivated - " + objvarName + " already contains " + questName);
            }
        }
        else 
        {
            LOG("newquests", "wait_for_tasks: OnQuestActivated Ignoring OnQuestActivated for task that is not mine");
        }
        return SCRIPT_CONTINUE;
    }
    public void safeRemoveObjVar(obj_id self, String objvarName) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(self, objvarName);
        if (ovl != null)
        {
            if (ovl.getNumItems() > 0)
            {
                Vector emptyValue = new Vector();
                setObjVar(self, objvarName, emptyValue);
                CustomerServiceLog("fs_quests", "wait_for_tasks: safeRemoveObjvar(): setting " + objvarName + " to empty because obj_var_list of same name is in use (on %TU)", self, null);
            }
            else 
            {
                removeObjVar(self, objvarName);
            }
        }
    }
}
