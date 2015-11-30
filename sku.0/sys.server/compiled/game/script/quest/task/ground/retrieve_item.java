package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.groundquests;
import script.library.prose;
import script.library.utils;

public class retrieve_item extends script.quest.task.ground.base_task
{
    public retrieve_item()
    {
    }
    public static final String dataTableColumnServerTemplate = "SERVER_TEMPLATE";
    public static final String dataTableColumnNumRequired = "NUM_REQUIRED";
    public static final String dataTableColumnDropPercent = "DROP_PERCENT";
    public static final String dataTableColumnItemName = "ITEM_NAME";
    public static final String objvarCount = "count";
    public static final String objvarRetrieved = "retrieved_items";
    public static final String taskType = "retrieve_item";
    public static final String dot = ".";
    public static final String RETRIEVE_ITEM_MUSIC = "sound/ui_received_quest_item.snd";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(self, baseObjVar + dot + objvarCount, 0);
        groundquests.setGuaranteedSuccessTarget(self, questCrc, taskId, baseObjVar);
        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnNumRequired);
        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:retrieve_item_counter", 0, count);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int questRetrieveItemObjectFound(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id source = params.getObjId("source");
        if (!groundquests.playerNeedsToRetrieveThisItem(self, source))
        {
            groundquests.questOutputDebugInfo(self, taskType, "questRetrieveItemObjectFound", "Player [" + self + "] Retrieved item [" + source + "] that we've already retrieved, this shouldn't happen.");
            return SCRIPT_CONTINUE;
        }
        String sourceTemplateName = getTemplateName(source);
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
                    String retrieveTemplateName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnServerTemplate);
                    if (sourceTemplateName.equals(retrieveTemplateName))
                    {
                        Vector retrievedItemsResizable = new Vector();
                        retrievedItemsResizable.setSize(0);
                        String objvarRetrievedFull = baseObjVar + dot + objvarRetrieved;
                        if (hasObjVar(self, objvarRetrievedFull))
                        {
                            obj_id[] itemsAlreadyRetrieved = getObjIdArrayObjVar(self, objvarRetrievedFull);
                            for (int k = 0; k < itemsAlreadyRetrieved.length; ++k)
                            {
                                utils.addElement(retrievedItemsResizable, itemsAlreadyRetrieved[k]);
                            }
                        }
                        utils.addElement(retrievedItemsResizable, source);
                        setObjVar(self, objvarRetrievedFull, retrievedItemsResizable);
                        int dropPercent = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnDropPercent);
                        int roll = rand(1, 100);
                        boolean guaranteedSuccess = groundquests.checkForGuaranteedSuccess(self, baseObjVar);
                        if (roll <= dropPercent || guaranteedSuccess)
                        {
                            String objvarNameCount = baseObjVar + dot + objvarCount;
                            if (hasObjVar(self, objvarNameCount))
                            {
                                int lootedCount = getIntObjVar(self, objvarNameCount);
                                ++lootedCount;
                                int itemsTotal = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnNumRequired);
                                if (lootedCount > itemsTotal)
                                {
                                    lootedCount = itemsTotal;
                                }
                                if (groundquests.isTaskVisible(questCrc, taskId))
                                {
                                    String itemName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnItemName);
                                    string_id message = new string_id("quest/groundquests", "retrieve_item_success");
                                    prose_package pp = prose.getPackage(message, self, self);
                                    if (itemName != null && itemName.length() > 0)
                                    {
                                        message = new string_id("quest/groundquests", "retrieve_item_success_named");
                                        pp = prose.getPackage(message, self, self);
                                        prose.setTO(pp, itemName);
                                    }
                                    prose.setDI(pp, itemsTotal - lootedCount);
                                    sendSystemMessageProse(self, pp);
                                    play2dNonLoopingSound(self, RETRIEVE_ITEM_MUSIC);
                                }
                                if (lootedCount == itemsTotal)
                                {
                                    questCompleteTask(questCrc, taskId, self);
                                }
                                else 
                                {
                                    questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:retrieve_item_counter", lootedCount, itemsTotal);
                                    play2dNonLoopingSound(self, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                                    setObjVar(self, objvarNameCount, lootedCount);
                                    groundquests.setGuaranteedSuccessTarget(self, questCrc, taskId, baseObjVar);
                                    groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "questRetrieveItemObjectFound", "Retrieved " + lootedCount + "/" + itemsTotal);
                                }
                            }
                        }
                        else 
                        {
                            groundquests.questOutputDebugInfo(self, taskType, "questRetrieveItemObjectFound", "Didn't find one.");
                            if (groundquests.isTaskVisible(questCrc, taskId))
                            {
                                String itemName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableColumnItemName);
                                string_id message = new string_id("quest/groundquests", "retrieve_item_fail");
                                prose_package pp = prose.getPackage(message, self, self);
                                sendSystemMessageProse(self, pp);
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
                    String objvarNameCount = baseObjVar + dot + objvarCount;
                    if (hasObjVar(self, objvarNameCount))
                    {
                        int lootedCount = getIntObjVar(self, objvarNameCount);
                        int itemsTotal = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableColumnNumRequired);
                        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:retrieve_item_counter", lootedCount, itemsTotal);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
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
