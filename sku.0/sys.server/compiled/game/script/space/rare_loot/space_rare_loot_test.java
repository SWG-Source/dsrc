package script.space.rare_loot;

import script.library.utils;
import script.obj_id;

import java.util.StringTokenizer;

public class space_rare_loot_test extends script.base_script
{
    public space_rare_loot_test()
    {
    }

    public int cmdCreateSpaceRareLootCrate(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!canUseRareLootTestCommand(self))
        {
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = getTokenizer(params);
        if (st.countTokens() != 2)
        {
            sendCrateSyntax(self);
            return SCRIPT_CONTINUE;
        }

        int rewardTier = parseTier(st.nextToken());
        int rewardQuality = parseQuality(st.nextToken());
        if (!isValidTier(rewardTier) || !isValidQuality(rewardQuality))
        {
            sendCrateSyntax(self);
            return SCRIPT_CONTINUE;
        }

        obj_id inventory = utils.getInventoryContainer(self);
        if (!isIdValid(inventory) || getVolumeFree(inventory) <= 0)
        {
            sendSystemMessageTestingOnly(self, "Space rare loot crate test failed: inventory is full or invalid.");
            return SCRIPT_CONTINUE;
        }

        obj_id chest = space_rare_loot.createRareLootChest(inventory, rewardQuality, rewardTier);
        if (!isIdValid(chest))
        {
            sendSystemMessageTestingOnly(self, "Space rare loot crate test failed: could not create crate.");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test crate creation failed. Player=" + self + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
            return SCRIPT_CONTINUE;
        }

        sendSystemMessageTestingOnly(self, "Created space rare loot crate: tier " + rewardTier + ", quality " + space_rare_loot.getRewardQualityName(rewardQuality) + ".");
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test crate created. Player=" + self + " chest=" + chest + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
        return SCRIPT_CONTINUE;
    }

    public int cmdCreateSpaceRareLootItem(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!canUseRareLootTestCommand(self))
        {
            return SCRIPT_CONTINUE;
        }
        StringTokenizer st = getTokenizer(params);
        if (st.countTokens() != 3 && st.countTokens() != 4)
        {
            sendItemSyntax(self);
            return SCRIPT_CONTINUE;
        }

        int rewardTier = parseTier(st.nextToken());
        int rewardQuality = parseQuality(st.nextToken());
        String itemType = st.nextToken();
        String itemName = st.hasMoreTokens() ? st.nextToken() : null;
        if (!isValidTier(rewardTier) || !isValidQuality(rewardQuality) || itemType == null || itemType.length() < 1)
        {
            sendItemSyntax(self);
            return SCRIPT_CONTINUE;
        }

        if (itemName == null || itemName.length() < 1)
        {
            String rewardTable = space_rare_loot.getRewardTable(rewardTier);
            String rewardQualityName = space_rare_loot.getRewardQualityName(rewardQuality);
            int rewardRow = getRandomRewardRowForItemType(rewardTable, rewardQualityName, itemType);
            if (rewardRow < 0)
            {
                sendSystemMessageTestingOnly(self, "Space rare loot item test failed: could not find enabled " + rewardQualityName + " " + itemType + " reward in tier " + rewardTier + " table.");
                CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test item creation failed, no matching reward row. Player=" + self + " itemType=" + itemType + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardTable=" + rewardTable);
                return SCRIPT_CONTINUE;
            }
            itemName = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_NAME);
            itemType = dataTableGetString(rewardTable, rewardRow, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_TYPE);
            if (itemName == null || itemName.length() < 1 || itemType == null || itemType.length() < 1)
            {
                sendSystemMessageTestingOnly(self, "Space rare loot item test failed: selected reward row is missing item data.");
                CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test item creation failed, selected reward row missing item data. Player=" + self + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality + " rewardTable=" + rewardTable + " rewardRow=" + rewardRow + " itemName=" + itemName + " itemType=" + itemType);
                return SCRIPT_CONTINUE;
            }
        }

        obj_id inventory = utils.getInventoryContainer(self);
        if (!isIdValid(inventory) || getVolumeFree(inventory) <= 0)
        {
            sendSystemMessageTestingOnly(self, "Space rare loot item test failed: inventory is full or invalid.");
            return SCRIPT_CONTINUE;
        }

        space_rare_loot_chest chestScript = new space_rare_loot_chest();
        obj_id item = chestScript.createSpaceRareLootItem(self, itemName, itemType, rewardQuality, rewardTier, space_rare_loot.getRewardQualityModifier(rewardQuality));
        if (!isIdValid(item))
        {
            sendSystemMessageTestingOnly(self, "Space rare loot item test failed: could not create " + itemName + " as " + itemType + ".");
            CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test item creation failed. Player=" + self + " itemName=" + itemName + " itemType=" + itemType + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
            return SCRIPT_CONTINUE;
        }

        sendSystemMessageTestingOnly(self, "Created space rare loot item: " + itemName + " (" + itemType + "), tier " + rewardTier + ", quality " + space_rare_loot.getRewardQualityName(rewardQuality) + ".");
        CustomerServiceLog(space_rare_loot.LOG_CHANNEL, "Space rare loot test item created. Player=" + self + " item=" + item + " itemName=" + itemName + " itemType=" + itemType + " rewardTier=" + rewardTier + " rewardQuality=" + rewardQuality);
        return SCRIPT_CONTINUE;
    }

    public int getRandomRewardRowForItemType(String rewardTable, String rewardQuality, String itemType) throws InterruptedException
    {
        int rowCount = dataTableGetNumRows(rewardTable);
        int matchingRows = 0;
        for (int i = 0; i < rowCount; i++)
        {
            if (isRewardRowMatchForItemType(rewardTable, i, rewardQuality, itemType))
            {
                matchingRows++;
            }
        }
        if (matchingRows <= 0)
        {
            return -1;
        }
        int selectedMatch = rand(1, matchingRows);
        int currentMatch = 0;
        for (int i = 0; i < rowCount; i++)
        {
            if (isRewardRowMatchForItemType(rewardTable, i, rewardQuality, itemType))
            {
                currentMatch++;
                if (currentMatch == selectedMatch)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isRewardRowMatchForItemType(String rewardTable, int row, String rewardQuality, String itemType) throws InterruptedException
    {
        if (!space_rare_loot.isRewardRowMatch(rewardTable, row, rewardQuality))
        {
            return false;
        }
        String rowItemType = dataTableGetString(rewardTable, row, space_rare_loot.REWARD_TABLE_COLUMN_ITEM_TYPE);
        return rowItemType != null && itemType != null && rowItemType.equalsIgnoreCase(itemType);
    }

    public boolean canUseRareLootTestCommand(obj_id self) throws InterruptedException
    {
        return isPlayer(self) && isGod(self) && getGodLevel(self) >= 40;
    }

    public StringTokenizer getTokenizer(String params)
    {
        if (params == null)
        {
            params = "";
        }
        return new StringTokenizer(params);
    }

    public int parseTier(String value) throws InterruptedException
    {
        int rewardTier = utils.stringToInt(value);
        if (!isValidTier(rewardTier))
        {
            return -1;
        }
        return rewardTier;
    }

    public int parseQuality(String value) throws InterruptedException
    {
        if (value == null)
        {
            return -1;
        }
        if (value.equalsIgnoreCase("rare"))
        {
            return space_rare_loot_config.QUALITY_RARE;
        }
        if (value.equalsIgnoreCase("exceptional"))
        {
            return space_rare_loot_config.QUALITY_EXCEPTIONAL;
        }
        if (value.equalsIgnoreCase("legendary"))
        {
            return space_rare_loot_config.QUALITY_LEGENDARY;
        }
        int rewardQuality = utils.stringToInt(value);
        if (isValidQuality(rewardQuality))
        {
            return rewardQuality;
        }
        return -1;
    }

    public boolean isValidTier(int rewardTier)
    {
        return rewardTier >= space_rare_loot.MIN_REWARD_TIER && rewardTier <= space_rare_loot.MAX_REWARD_TIER;
    }

    public boolean isValidQuality(int rewardQuality)
    {
        return rewardQuality >= space_rare_loot_config.QUALITY_RARE && rewardQuality <= space_rare_loot_config.QUALITY_LEGENDARY;
    }

    public void sendCrateSyntax(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "format: /createSpaceRareLootCrate <tier 1-5> <rare|exceptional|legendary>");
    }

    public void sendItemSyntax(obj_id self) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, "format: /createSpaceRareLootItem <tier 1-5> <rare|exceptional|legendary> <itemType> [itemName]");
    }
}
