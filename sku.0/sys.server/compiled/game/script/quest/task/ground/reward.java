package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.factions;
import script.library.loot;
import script.library.money;
import script.library.prose;
import script.library.static_item;
import script.library.utils;
import script.library.weapons;
import script.library.xp;

public class reward extends script.quest.task.ground.base_task
{
    public reward()
    {
    }
    public static final String taskType = "reward";
    public int OnTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskActivated", taskType + " task activated.");
        questCompleteTask(questCrc, taskId, self);
        return super.OnTaskActivated(self, questCrc, taskId);
    }
    public int OnTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        groundquests.questOutputDebugInfo(self, questCrc, taskId, taskType, "OnTaskCompleted", taskType + " task completed.");
        String experienceType = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnExperienceType);
        int experienceAmount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnExperienceAmount);
        String factionName = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnFactionName);
        int factionAmount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnFactionAmount);
        int bankCredits = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnBankCredits);
        String item = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnItem);
        int itemCount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnCount);
        String weapon = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnWeapon);
        int weaponCount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnCountWeapon);
        float weaponSpeed = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnSpeed));
        float weaponDamage = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnDamage));
        float weaponEfficiency = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnEfficiency));
        float weaponElementalValue = utils.stringToFloat(groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnElementalValue));
        String armor = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnArmor);
        int armorCount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnCountArmor);
        int armorQuality = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnQuality);
        String lootName = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnLootName);
        int lootCount = groundquests.getTaskIntDataEntry(questCrc, taskId, groundquests.dataTableColumnLootCount);
        String badge = groundquests.getTaskStringDataEntry(questCrc, taskId, groundquests.dataTableColumnBadge);
        String[] lootNames = 
        {
            lootName
        };
        int[] lootCounts = 
        {
            lootCount
        };
        groundquests.grantQuestReward(self, questCrc, -1, -1, experienceType, experienceAmount, factionName, factionAmount, false, bankCredits, item, itemCount, weapon, weaponCount, weaponSpeed, weaponDamage, weaponEfficiency, weaponElementalValue, armor, armorCount, armorQuality, lootNames, lootCounts, null, 1, badge, (questIsQuestForceAccept(questCrc) || !questDoesUseAcceptanceUI(questCrc)));
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
