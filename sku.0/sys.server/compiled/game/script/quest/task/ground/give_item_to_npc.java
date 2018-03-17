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
import script.library.static_item;

public class give_item_to_npc extends script.base_script
{
    public give_item_to_npc()
    {
    }
    public static final String dataTableItemToGive = "ITEM_TO_GIVE";
    public static final string_id noGiveDuringCombat = new string_id("quest/ground/system_message", "no_give_during_combat");
    public int OnGiveItem(obj_id self, obj_id item, obj_id giver) throws InterruptedException
    {
        String questName = getStringObjVar(self, "quest_name");
        String taskName = getStringObjVar(self, "task_name");
        String itemName = getTemplateName(item);
        if (static_item.isStaticItem(item))
        {
            itemName = getStaticItemName(item);
        }
        int questCrc = groundquests.getQuestIdFromString(questName);
        int taskId = groundquests.getTaskId(questCrc, taskName);
        if(taskId == -1){
            LOG("groundquests","Task could not be found for this quest.");
            return SCRIPT_CONTINUE;
        }
        if (!questIsTaskActive(questCrc, taskId, giver))
        {
            if (hasObjVar(self, "alternate_quest_name"))
            {
                questName = getStringObjVar(self, "alternate_quest_name");
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
            questCrc = groundquests.getQuestIdFromString(questName);
            if (!questIsTaskActive(questCrc, taskId, giver))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (groundquests.isTimeRemainingBeforeCompletion(giver, questCrc, taskId))
        {
            return SCRIPT_CONTINUE;
        }
        String itemToGive = groundquests.getTaskStringDataEntry(questCrc, taskId, dataTableItemToGive);
        if (itemToGive != null && itemToGive.length() > 0)
        {
            String[] itemsNeeded = split(itemToGive, ',');
            for (int i = 0; i < itemsNeeded.length; ++i)
            {
                if (itemsNeeded[i].equals(itemName))
                {
                    if (ai_lib.isInCombat(giver))
                    {
                        sendQuestSystemMessage(giver, noGiveDuringCombat);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        dictionary webster = new dictionary();
                        webster.put("questName", questName);
                        webster.put("taskName", taskName);
                        webster.put("itemName", itemName);
                        messageTo(giver, "itemGivenToNpc", webster, 0, false);
                        return SCRIPT_OVERRIDE;
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
