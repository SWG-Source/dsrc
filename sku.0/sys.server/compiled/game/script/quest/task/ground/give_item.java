package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.prose;
import script.library.static_item;
import script.library.travel;
import script.library.utils;

public class give_item extends script.quest.task.ground.base_task
{
    public give_item()
    {
    }
    public static final String dataTableNumToGive = "NUM_TO_GIVE";
    public static final String dataTableItemToGive = "ITEM_TO_GIVE";
    public static final String dataTableCreateItem = "CREATE_ITEM";
    public static final String dataTableDestroyItem = "DESTROY_ITEM";
    public static final String dataTableFailOnLogout = "FAIL_ON_LOGOUT";
    public static final String dataTableTravelBlock = "TRAVEL_BLOCK";
    public static final String dataTableTravelBlockAllowLaunch = "TRAVEL_BLOCK_ALLOW_LAUNCH";
    public static final String dataTableTextType = "TEXT_TYPE";
    public static final String objVarCount = "count";
    public static final String objVarCreatedItem = "createdItem";
    public static final String taskType = "give_item";
    public static final int textGive = 0;
    public static final int textDeliver = 1;
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableNumToGive);
        setObjVar(self, baseObjVar + "." + objVarCount, count);
        int textType = Math.max(0, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableTextType));
        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, getCounterText(textType), 0, count);
        boolean createItem = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableCreateItem, false);
        if (createItem)
        {
            String itemName = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableItemToGive);
            if (itemName != null && itemName.length() > 0)
            {
                itemName = split(itemName, ',')[0];
                if (itemName != null && itemName.length() > 0)
                {
                    obj_id newItem;
                    if (static_item.isStaticItem(itemName))
                    {
                        newItem = static_item.createNewItemFunction(itemName, self);
                    }
                    else 
                    {
                        newItem = createObjectInInventoryAllowOverload(itemName, self);
                    }
                    if (isValidId(newItem))
                    {
                        groundquests.sendPlacedInInventorySystemMessage(self, newItem, itemName);
                        CustomerServiceLog("QUEST", self + " given item '" + itemName + "' (" + newItem + ")");
                        setObjVar(self, baseObjVar + "." + objVarCreatedItem, newItem);
                    }
                }
            }
        }
        boolean travelBlock = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableTravelBlock, false);
        if (travelBlock)
        {
            boolean allowLaunch = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableTravelBlockAllowLaunch, false);
            travel.setTravelBlock(self, allowLaunch);
        }
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int itemGivenToNpc(obj_id self, dictionary params) throws InterruptedException
    {
        String questName = params.getString("questName");
        String taskName = params.getString("taskName");
        String itemName = params.getString("itemName");
        groundquests.questOutputDebugLog(taskType, "giveItem", "start");
        int questCrc = groundquests.getQuestIdFromString(questName);
        int taskId = groundquests.getTaskId(questCrc, taskName);
        if (questIsTaskActive(questCrc, taskId, self))
        {
            String item = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableItemToGive);
            if (item != null && item.length() > 0)
            {
                String[] itemsNeeded = split(item, ',');
                int textType = Math.max(0, groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableTextType));
                for (int i = 0; i < itemsNeeded.length; ++i)
                {
                    if (itemsNeeded[i] != null && itemsNeeded[i].equals(itemName))
                    {
                        String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                        String objvarNameCount = baseObjVar + "." + objVarCount;
                        if (hasObjVar(self, objvarNameCount))
                        {
                            int giveCount = getIntObjVar(self, objvarNameCount);
                            --giveCount;
                            if (giveCount < 0)
                            {
                                giveCount = 0;
                            }
                            int countMax = groundquests.getTaskIntDataEntry(questCrc, taskId, dataTableNumToGive);
                            string_id message = new string_id("quest/groundquests", getMultipleSuccessTextKey(textType));
                            prose_package pp = prose.getPackage(message, self, self);
                            prose.setDI(pp, giveCount);
                            sendSystemMessageProse(self, pp);
                            if (giveCount <= 0)
                            {
                                questCompleteTask(questCrc, taskId, self);
                            }
                            else 
                            {
                                setObjVar(self, objvarNameCount, giveCount);
                                questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, getCounterText(textType), countMax - giveCount, countMax);
                                play2dNonLoopingSound(self, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                                groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "itemGivenToNpc", "Gave a " + itemName + ", " + giveCount + " left.");
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
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        failIfFailOnLogout(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        failIfFailOnLogout(self);
        return SCRIPT_CONTINUE;
    }
    public void cleanup(obj_id player, int questCrc, int taskId) throws InterruptedException
    {
        String baseObjVar = groundquests.setBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
        if (hasObjVar(player, baseObjVar + "." + objVarCreatedItem))
        {
            obj_id createdItem = getObjIdObjVar(player, baseObjVar + "." + objVarCreatedItem);
            if (isIdValid(createdItem) && exists(createdItem))
            {
                boolean destroyItem = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableDestroyItem, false);
                if (destroyItem)
                {
                    groundquests.sendRemovedFromInventorySystemMessage(player, createdItem);
                    destroyObject(createdItem);
                    CustomerServiceLog("QUEST", player + " destroyed item (" + createdItem + ")");
                }
            }
        }
        boolean travelBlock = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableTravelBlock, false);
        if (travelBlock)
        {
            travel.clearTravelBlock(player);
        }
        groundquests.clearBaseObjVar(player, taskType, questGetQuestName(questCrc), taskId);
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        removeObjVar(self, groundquests.getTaskTypeObjVar(self, taskType));
        return SCRIPT_CONTINUE;
    }
    public void failIfFailOnLogout(obj_id player) throws InterruptedException
    {
        dictionary tasks = groundquests.getActiveTasksForTaskType(player, taskType);
        if (tasks != null && !tasks.isEmpty())
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
                    boolean shouldFailOnLogout = groundquests.getTaskBoolDataEntry(questCrc, taskId, dataTableFailOnLogout, false);
                    if (shouldFailOnLogout)
                    {
                        LOG("QUEST_LOG", "Quest task failed due to log out.");
                        questFailTask(questCrc, taskId, player);
                    }
                }
            }
        }
    }
    public String getCounterText(int textType) throws InterruptedException
    {
        switch (textType)
        {
            case textDeliver:
            return "quest/groundquests:deliver_counter";
            case textGive:
            default:
            return "quest/groundquests:give_counter";
        }
    }
    public String getMultipleSuccessTextKey(int textType) throws InterruptedException
    {
        switch (textType)
        {
            case textDeliver:
            return "deliver_to_npc_multiple_success";
            case textGive:
            default:
            return "give_item_to_npc_multiple_success";
        }
    }
}
