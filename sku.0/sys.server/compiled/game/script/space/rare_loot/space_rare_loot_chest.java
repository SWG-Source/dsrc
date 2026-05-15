package script.space.rare_loot;

import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class space_rare_loot_chest extends script.base_script
{
    public static final String ITEM_TYPE_WEAPON = "weapon";
    public static final String ITEM_TYPE_ENGINE = "engine";
    public static final String ITEM_TYPE_BOOSTER = "booster";
    public static final String ITEM_TYPE_REACTOR = "reactor";
    public static final String ITEM_TYPE_SHIELD = "shield";
    public static final String ITEM_TYPE_CAPACITOR = "capacitor";
    public static final String ITEM_TYPE_ARMOR = "armor";
    public static final String ITEM_TYPE_DROID_INTERFACE = "droid_interface";

    public space_rare_loot_chest()
    {
    }

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) == player)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("npe", "crate_use"));
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, space_rare_loot.VAR_CHEST_REWARD_QUALITY) || !hasObjVar(self, space_rare_loot.VAR_CHEST_REWARD_TIER))
        {
            space_rare_loot.debug(player, "space rare loot chest is missing reward objvars.");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, missing reward objvars. Player=" + player + " chest=" + self);
            return SCRIPT_CONTINUE;
        }
        int rewardQuality = getIntObjVar(self, space_rare_loot.VAR_CHEST_REWARD_QUALITY);
        int rewardTier = getIntObjVar(self, space_rare_loot.VAR_CHEST_REWARD_TIER);
        rewardTier = space_rare_loot.getClampedRewardTier(rewardTier);
        String rewardQualityName = space_rare_loot.getRewardQualityName(rewardQuality);
        float rewardQualityModifier = space_rare_loot.getRewardQualityModifier(rewardQuality);
        String rewardTable = space_rare_loot.getRewardTable(rewardTier);
        int rewardRow = space_rare_loot.getRandomRewardRow(rewardTable, rewardQualityName);
        if (rewardRow < 0)
        {
            space_rare_loot.debug(player, "opened chest " + self + " but found no enabled " + rewardQualityName + " or all reward rows in " + rewardTable + ".");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, no reward row. Player=" + player + " chest=" + self + " rewardTier=" + rewardTier + " rewardTable=" + rewardTable + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")");
            return SCRIPT_CONTINUE;
        }
        String itemName = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_NAME);
        String itemType = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_TYPE);

        space_rare_loot.debug(player, "opened chest " + self + " with reward tier " + rewardTier + ", quality " + rewardQualityName + ", row " + rewardRow + ", item " + itemName + ".");
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest opened. Player=" + player + " chest=" + self + " rewardTier=" + rewardTier + " rewardTable=" + rewardTable + " rewardRow=" + rewardRow + " itemName=" + itemName + " itemType=" + itemType + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")" + " rewardQualityModifier=" + rewardQualityModifier);
        return SCRIPT_CONTINUE;
    }

    public obj_id createSpaceRareLootItem(obj_id player, String itemName, String itemType, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        if (itemType == null)
        {
            return null;
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_WEAPON))
        {
            return createWeaponReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_ENGINE))
        {
            return createEngineReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_BOOSTER))
        {
            return createBoosterReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_REACTOR))
        {
            return createReactorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_SHIELD))
        {
            return createShieldReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_CAPACITOR))
        {
            return createCapacitorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_ARMOR))
        {
            return createArmorReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        if (itemType.equalsIgnoreCase(ITEM_TYPE_DROID_INTERFACE))
        {
            return createDroidInterfaceReward(player, itemName, rewardQuality, rewardTier, rewardQualityModifier);
        }
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot chest open failed, unknown item type. Player=" + player + " itemName=" + itemName + " itemType=" + itemType + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
        return null;
    }

    public obj_id createWeaponReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createEngineReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createBoosterReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createReactorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createShieldReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createCapacitorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createArmorReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }

    public obj_id createDroidInterfaceReward(obj_id player, String itemName, int rewardQuality, int rewardTier, float rewardQualityModifier) throws InterruptedException
    {
        return null;
    }
}
