package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.groundquests;
import script.library.money;
import script.library.prose;
import script.library.utils;
import script.library.xp;

public class destroy_multi_and_loot extends script.quest.task.ground.base_task
{
    public destroy_multi_and_loot()
    {
    }
    public static final String taskType = "destroy_multiple_and_loot";
    public static final String dot = ".";
    public static final String objvarCount = "lootedItems";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        int count = groundquests.getTaskIntDataEntry(questCrc, taskId, "LOOT_ITEMS_REQUIRED");
        String baseObjVar = groundquests.setBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
        setObjVar(self, baseObjVar + dot + objvarCount, 0);
        groundquests.setGuaranteedSuccessTarget(self, questCrc, taskId, baseObjVar);
        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:destroy_and_loot_counter", 0, count);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int receiveCreditForKill(obj_id self, dictionary params) throws InterruptedException
    {
        String killedCreatureType = params.getString("creatureName");
        String killedCreatureSocialGroup = params.getString("socialGroup");
        location creatureLoc = params.getLocation("location");
        float distance = getDistance(getLocation(self), creatureLoc);
        if (distance == -1.0f || distance > xp.MAX_DISTANCE)
        {
            return SCRIPT_CONTINUE;
        }
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
                                String requiredRegion = groundquests.getTaskStringDataEntry(questCrc, taskId, "REQUIRED_REGION");
                                boolean regionAcceptable = groundquests.isInNamedRegion(self, requiredRegion);
                                String creatureType = groundquests.getTaskStringDataEntry(questCrc, taskId, "TARGET_SERVER_TEMPLATE");
                                boolean creatureAcceptable = false;
                                if (creatureType != null && killedCreatureType != null && creatureType.equals(killedCreatureType))
                                {
                                    creatureAcceptable = true;
                                }
                                String creatureSocialGroup = groundquests.getTaskStringDataEntry(questCrc, taskId, "SOCIAL_GROUP");
                                if (creatureSocialGroup != null && killedCreatureSocialGroup != null && creatureSocialGroup.equals(killedCreatureSocialGroup))
                                {
                                    creatureAcceptable = true;
                                }
                                if (creatureAcceptable)
                                {
                                    if (regionAcceptable)
                                    {
                                        String baseObjVar = groundquests.getBaseObjVar(self, taskType, questGetQuestName(questCrc), taskId);
                                        String itemName = groundquests.getTaskStringDataEntry(questCrc, taskId, "LOOT_ITEM_NAME");
                                        int dropPercent = groundquests.getTaskIntDataEntry(questCrc, taskId, "LOOT_DROP_PERCENT");
                                        int roll = rand(1, 100);
                                        boolean guaranteedSuccess = groundquests.checkForGuaranteedSuccess(self, baseObjVar);
                                        if (roll <= dropPercent || guaranteedSuccess)
                                        {
                                            String objvarNameCount = baseObjVar + dot + objvarCount;
                                            if (hasObjVar(self, objvarNameCount))
                                            {
                                                int lootedCount = getIntObjVar(self, objvarNameCount);
                                                ++lootedCount;
                                                int itemsTotal = groundquests.getTaskIntDataEntry(questCrc, taskId, "LOOT_ITEMS_REQUIRED");
                                                if (lootedCount > itemsTotal)
                                                {
                                                    lootedCount = itemsTotal;
                                                }
                                                string_id message = new string_id("quest/groundquests", "destroy_multiple_and_loot_success");
                                                prose_package pp = prose.getPackage(message, self, self, itemsTotal - lootedCount);
                                                prose.setTO(pp, itemName);
                                                prose.setDI(pp, itemsTotal - lootedCount);
                                                sendSystemMessageProse(self, pp);
                                                if (lootedCount >= itemsTotal)
                                                {
                                                    questCompleteTask(questCrc, taskId, self);
                                                }
                                                else 
                                                {
                                                    setObjVar(self, objvarNameCount, lootedCount);
                                                    questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:destroy_and_loot_counter", lootedCount, itemsTotal);
                                                    play2dNonLoopingSound(self, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                                                    groundquests.playJournalUpdatedMusic(questCrc, taskId, self);
                                                    groundquests.setGuaranteedSuccessTarget(self, questCrc, taskId, baseObjVar);
                                                    groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "receiveCreditForKill", "Looted " + lootedCount + "/" + itemsTotal + " " + itemName + "(s)");
                                                }
                                            }
                                        }
                                        else 
                                        {
                                            string_id message = new string_id("quest/groundquests", "destroy_multiple_and_loot_fail");
                                            prose_package pp = prose.getPackage(message, self, self, 0);
                                            prose.setTO(pp, itemName);
                                            sendSystemMessageProse(self, pp);
                                        }
                                    }
                                    else 
                                    {
                                        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "receiveCreditForKill", "Killed a " + killedCreatureType + ", of social group " + killedCreatureSocialGroup + ", but not in required region " + requiredRegion + ".");
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
        int bankCredits = groundquests.getTaskIntDataEntry(questCrc, taskId, "REWARD_CREDITS");
        if (bankCredits > 0)
        {
            money.bankTo(money.ACCT_NEW_PLAYER_QUESTS, self, bankCredits);
            groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", "Deposited " + bankCredits + " credits into the bank.");
        }
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
                        int itemsTotal = groundquests.getTaskIntDataEntry(questCrc, taskId, "LOOT_ITEMS_REQUIRED");
                        questSetQuestTaskCounter(self, questGetQuestName(questCrc), taskId, "quest/groundquests:destroy_and_loot_counter", lootedCount, itemsTotal);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
