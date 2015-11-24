package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class show_message_box extends script.quest.task.ground.base_task
{
    public show_message_box()
    {
    }
    public static final String dataTableColumnMessageBoxTitle = "MESSAGE_BOX_TITLE";
    public static final String dataTableColumnMessageBoxText = "MESSAGE_BOX_TEXT";
    public static final String dataTableColumnMessageBoxSound = "MESSAGE_BOX_SOUND";
    public static final String dataTableColumnMessageBoxSizeWidth = "MESSAGE_BOX_SIZE_WIDTH";
    public static final String dataTableColumnMessageBoxSizeHeight = "MESSAGE_BOX_SIZE_HEIGHT";
    public static final String dataTableColumnMessageBoxLocationX = "MESSAGE_BOX_LOCATION_X";
    public static final String dataTableColumnMessageBoxLocationY = "MESSAGE_BOX_LOCATION_Y";
    public static final String taskType = "show_message_box";
    public static final int defaultMessageBoxSizeWidth = 384;
    public static final int defaultMessageBoxSizeHeight = 256;
    public static final int defaultMessageBoxLocationX = 320;
    public static final int defaultMessageBoxLocationY = 256;
    public String getQuestCrcVarName(int pageId) throws InterruptedException
    {
        return taskType + ".questCrc." + pageId;
    }
    public String getTaskIdVarName(int pageId) throws InterruptedException
    {
        return taskType + ".taskId." + pageId;
    }
    public void displayMessageBox(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String messageBoxTitle = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMessageBoxTitle);
        String messageBoxText = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMessageBoxText);
        String messageBoxSound = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnMessageBoxSound);
        int messageBoxSizeWidth = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMessageBoxSizeWidth);
        int messageBoxSizeHeight = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMessageBoxSizeHeight);
        int messageBoxLocationX = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMessageBoxLocationX);
        int messageBoxLocationY = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnMessageBoxLocationY);
        int pageId = sui.createSUIPage(sui.SUI_MSGBOX, player, player, "QuestShowMessageBoxCompleted");
        sui.setAutosaveProperty(pageId, false);
        if (messageBoxSizeWidth != -1 && messageBoxSizeHeight != -1)
        {
            sui.setSizeProperty(pageId, messageBoxSizeWidth, messageBoxSizeHeight);
        }
        else 
        {
            sui.setSizeProperty(pageId, defaultMessageBoxSizeWidth, defaultMessageBoxSizeHeight);
        }
        if (messageBoxLocationX != -1 && messageBoxLocationY != -1)
        {
            sui.setLocationProperty(pageId, messageBoxLocationX, messageBoxLocationY);
        }
        else 
        {
            sui.setLocationProperty(pageId, defaultMessageBoxLocationX, defaultMessageBoxLocationY);
        }
        if (messageBoxSound != null && messageBoxSound.length() > 0)
        {
            sui.setSoundProperty(pageId, messageBoxSound);
        }
        setSUIProperty(pageId, sui.MSGBOX_TITLE, sui.PROP_TEXT, messageBoxTitle);
        setSUIProperty(pageId, sui.MSGBOX_PROMPT, sui.PROP_TEXT, messageBoxText);
        sui.msgboxButtonSetup(pageId, sui.OK_ONLY);
        sui.showSUIPage(pageId);
        utils.setScriptVar(player, getQuestCrcVarName(pageId), questCrc);
        utils.setScriptVar(player, getTaskIdVarName(pageId), taskId);
        groundquests.questOutputDebugLog(taskType, "displayMessageBox", "pageId = " + pageId);
    }
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + "task activated.");
        displayMessageBox(self, questCrc, taskId);
        groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int QuestShowMessageBoxCompleted(obj_id self, dictionary params) throws InterruptedException
    {
        int pageId = params.getInt("pageId");
        if (pageId < 0)
        {
            return SCRIPT_CONTINUE;
        }
        int questCrc = utils.getIntScriptVar(self, getQuestCrcVarName(pageId));
        int taskId = utils.getIntScriptVar(self, getTaskIdVarName(pageId));
        if (questIsTaskActive(questCrc, taskId, self))
        {
            questCompleteTask(questCrc, taskId, self);
        }
        utils.removeScriptVar(self, getQuestCrcVarName(pageId));
        utils.removeScriptVar(self, getTaskIdVarName(pageId));
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
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCleared", taskType + "task cleared.");
        return super.OnTaskCleared(self, questCrc, taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int handleClientLogin(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        groundquests.questOutputDebugLog(taskType, "handleClientLogin", "start");
        dictionary tasks = groundquests.getActiveTasksForTaskType(self, taskType);
        if ((tasks != null) && !tasks.isEmpty())
        {
            java.util.Enumeration keys = tasks.keys();
            groundquests.questOutputDebugLog(taskType, "handleClientLogin", "task count = " + tasks.size());
            while (keys.hasMoreElements())
            {
                String questCrcString = (String)keys.nextElement();
                int questCrc = utils.stringToInt(questCrcString);
                int[] tasksForCurrentQuest = tasks.getIntArray(questCrcString);
                for (int i = 0; i < tasksForCurrentQuest.length; ++i)
                {
                    int taskId = tasksForCurrentQuest[i];
                    displayMessageBox(self, questCrc, taskId);
                }
            }
        }
        groundquests.questOutputDebugLog(taskType, "handleClientLogin", "end");
        return SCRIPT_CONTINUE;
    }
}
