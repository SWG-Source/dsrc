package script.space.special_loot;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.smuggler;
import script.library.static_item;
import script.library.utils;

public class space_duty_crate extends script.base_script
{
    public space_duty_crate()
    {
    }
    public static final string_id SID_OPEN_CRATE = new string_id("spam", "open_crate");
    public static final String LOOT_LIST = "datatables/space_loot/piracy_loot/crate_list.iff";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(SID_OPEN_CRATE));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            int difficulty = getIntObjVar(self, "difficulty");
            if (difficulty <= 0 || difficulty > 5)
            {
                return SCRIPT_CONTINUE;
            }
            obj_id inv = utils.getInventoryContainer(player);
            int freeSpace = getVolumeFree(inv);
            if (freeSpace < 15)
            {
                sendSystemMessage(player, "You must have room for at least 15 items in your inventory to open this container.", null);
                return SCRIPT_CONTINUE;
            }
            createLoot(self, player, difficulty);
        }
        return SCRIPT_CONTINUE;
    }
    public void createLoot(obj_id self, obj_id player, int difficulty) throws InterruptedException
    {
        obj_id inventory = utils.getInventoryContainer(player);
        int itemCount = ((difficulty + rand(1, 2)) * 2);
        for (int i = 0; i < itemCount; i++)
        {
            int lootRoll = rand(1, 100);
            if (lootRoll > 0 && lootRoll <= 60)
            {
                String regItem = generateRegularItem(player);
                if (regItem.length() > 0)
                {
                    createObject(regItem, inventory, "");
                }
            }
            if (lootRoll > 60 && lootRoll <= 65)
            {
                String flightPlan = generateRareItem(player, "flightPlan");
                if (flightPlan.length() > 0)
                {
                    static_item.createNewItemFunction(flightPlan, inventory);
                }
            }
            if (lootRoll > 65 && lootRoll <= 75)
            {
                String rewardItem = generateRareItem(player, "rewardItems");
                if (rewardItem.length() > 0)
                {
                    createObject(rewardItem, inventory, "");
                }
            }
            if (lootRoll > 75 && lootRoll <= 85)
            {
                String schematicItem = generateRareItem(player, "schematics");
                if (schematicItem.length() > 0)
                {
                    static_item.createNewItemFunction(schematicItem, inventory);
                }
            }
            if (lootRoll > 85)
            {
                String rareItem = generateRareItem(player, "rareItems");
                if (rareItem.length() > 0)
                {
                    createObject(rareItem, inventory, "");
                }
            }
        }
        if (utils.isProfession(player, utils.SMUGGLER))
        {
            int smugglerItems = rand(1, 5);
            for (int i = 0; i < smugglerItems; i++)
            {
                int smugglerRoll = rand(1, 100);
                if (smugglerRoll <= 50)
                {
                    smuggler.createRandomContrabandTier(player, difficulty);
                }
                if (smugglerRoll > 50)
                {
                    obj_id module = utils.getStaticItemInInventory(player, "item_reward_modify_pistol_01_01");
                    if (isIdValid(module))
                    {
                        incrementCount(module, rand(1, 2));
                    }
                    else 
                    {
                        static_item.createNewItemFunction("item_reward_modify_pistol_01_01", player);
                        module = utils.getStaticItemInInventory(player, "item_reward_modify_pistol_01_01");
                        incrementCount(module, rand(0, 1));
                    }
                }
            }
        }
        destroyObject(self);
    }
    public String generateRegularItem(obj_id player) throws InterruptedException
    {
        String[] compType = dataTableGetStringColumnNoDefaults(LOOT_LIST, "componentType");
        if (compType == null)
        {
            return null;
        }
        int realCompTypeLength = 0;
        for (int i = 0; i < compType.length; i++)
        {
            if (compType[i] != null && compType[i].length() > 0)
            {
                realCompTypeLength++;
            }
        }
        String component = compType[rand(0, realCompTypeLength - 1)];
        int level = rand(1, 10);
        String col = "level" + level;
        String table = "datatables/item/vendor/space_duty/" + component + ".iff";
        String[] compList = dataTableGetStringColumnNoDefaults(table, col);
        if (compList == null)
        {
            return null;
        }
        String item = compList[rand(0, (compList.length - 1))];
        return item;
    }
    public String generateRareItem(obj_id player, String type) throws InterruptedException
    {
        String[] compList = dataTableGetStringColumnNoDefaults(LOOT_LIST, type);
        if (compList == null)
        {
            return null;
        }
        String component = compList[rand(0, (compList.length - 1))];
        return component;
    }
}
