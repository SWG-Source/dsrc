package script.space.rare_loot;

import script.library.luck;
import script.library.static_item;
import script.library.space_flags;
import script.library.space_utils;
import script.library.utils;
import script.obj_id;

public class space_rare_loot extends script.base_script
{
    public space_rare_loot()
    {
    }

    public static final String LOG_CHANNEL = "space_rare_loot";    
    public static final String VAR_LAST_CHEST_AWARD_TIME = "loot.space_rare.lastChestAwardTime";
    public static final String VAR_CHEST_REWARD_QUALITY = "loot.space_rare.rewardQuality";
    public static final String VAR_CHEST_REWARD_TIER = "loot.space_rare.rewardTier";
    public static final String VAR_ITEM_IS_RARE_SPACE_LOOT = "loot.space_rare.item.isRareSpaceLoot";
    public static final String VAR_ITEM_REWARD_QUALITY = "loot.space_rare.item.rewardQuality";
    public static final String VAR_ITEM_REWARD_QUALITY_NAME = "loot.space_rare.item.rewardQualityName";
    public static final String VAR_ITEM_REWARD_TIER = "loot.space_rare.item.rewardTier";
    public static final String SPACE_RLS_TABLE_LOCATION = "datatables/space_loot/space_rls";
    public static final String REWARD_TABLE_BASE =  SPACE_RLS_TABLE_LOCATION + "/rewards_tier_";
    public static final String REWARD_TABLE_SUFFIX = ".iff";
    public static final String REWARD_TABLE_COLUMN_QUALITY = "strQuality";
    public static final String REWARD_TABLE_COLUMN_ITEM_NAME = "strItemName";
    public static final String REWARD_TABLE_COLUMN_ITEM_TYPE = "strItemType";
    public static final String REWARD_TABLE_COLUMN_ENABLED = "enabled";
    public static final String REWARD_TABLE_QUALITY_ALL = "all";
    public static final String CHEST_STATIC_ITEM_BASE = "space_rare_loot_chest_quality_";
    public static final String SCRIPT_GROUND_RARE_LOOT_CHEST = "systems.loot.rare_loot_chest";
    public static final String SCRIPT_SPACE_RARE_LOOT_CHEST = "space.rare_loot.space_rare_loot_chest";
    public static final int MIN_REWARD_TIER = 1;
    public static final int MAX_REWARD_TIER = 5;
    public static final float LUCK_QUALITY_UPGRADE_MODIFIER = 0.10f;

    public static boolean checkAwardEligibility(obj_id playerShip, obj_id targetShip) throws InterruptedException
    {
        if (!space_rare_loot_config.isEnabled())
        {
            return false;
        }
        if (!isIdValid(playerShip) || !exists(playerShip))
        {
            return false;
        }
        obj_id player = getPilotId(playerShip);
        if (!isIdValid(player) || !exists(player))
        {
            return false;
        }
        if (!isIdValid(targetShip) || !exists(targetShip))
        {
            debug(player, "blocked, target ship is invalid.");
            return false;
        }
        if (space_utils.isShipWithInterior(playerShip))
        {
            debug(player, "blocked, POB ships are excluded.");
            return false;
        }
        int playerTier = getPilotTier(player);
        int targetTier = getTargetTier(targetShip);
        if (targetTier <= 0)
        {
            debug(player, "blocked, could not determine target tier from difficulty '" + getShipDifficulty(targetShip) + "'.");
            return false;
        }
        int effectivePlayerTier = getEffectivePilotTier(playerTier, targetTier);
        if (effectivePlayerTier != playerTier)
        {
            debug(player, "treating pilot tier " + playerTier + " as target tier " + targetTier + " for lower-tier target.");
        }
        int delaySeconds = space_rare_loot_config.getDelaySeconds();
        if (delaySeconds > 0 && hasObjVar(player, VAR_LAST_CHEST_AWARD_TIME))
        {
            int lastAwardTime = getIntObjVar(player, VAR_LAST_CHEST_AWARD_TIME);
            int elapsedTime = getGameTime() - lastAwardTime;
            if (elapsedTime < delaySeconds)
            {
                debug(player, "blocked, cooldown " + elapsedTime + "/" + delaySeconds + " seconds elapsed.");
                return false;
            }
        }
        int tierDelta = targetTier - effectivePlayerTier;
        if (tierDelta <= -2)
        {
            debug(player, "blocked, target tier " + targetTier + " is too low for pilot tier " + effectivePlayerTier + ".");
            return false;
        }
        double adjustedChance = getAdjustedBaseChance(effectivePlayerTier, targetTier);
        if (adjustedChance <= 0.0d)
        {
            debug(player, "blocked, adjusted chance is zero.");
            return false;
        }
        double roll = Math.random() * 100.0d;
        if (roll > adjustedChance)
        {
            debug(player, "eligible, roll " + roll + " > chance " + adjustedChance + " failed.");
            return false;
        }
        double rareChance = space_rare_loot_config.getRareChance();
        double exceptionalChance = space_rare_loot_config.getExceptionalChance();
        double legendaryChance = space_rare_loot_config.getLegendaryChance();
        int higherTierDelta = targetTier - effectivePlayerTier;
        double adjustedRareChance = getAdjustedRareChance(rareChance, higherTierDelta);
        double shiftedChance = rareChance - adjustedRareChance;
        double adjustedExceptionalChance = exceptionalChance + (shiftedChance * space_rare_loot_config.getHigherTierExceptionalShiftRatio());
        double adjustedLegendaryChance = legendaryChance + (shiftedChance * space_rare_loot_config.getHigherTierLegendaryShiftRatio());
        double rewardChanceTotal = adjustedRareChance + adjustedExceptionalChance + adjustedLegendaryChance;
        double rewardRoll = rewardChanceTotal > 0.0d ? Math.random() * rewardChanceTotal : 0.0d;
        int rewardQuality = determineRewardQuality(rewardRoll, adjustedRareChance, adjustedExceptionalChance, adjustedLegendaryChance);
        int originalRewardQuality = rewardQuality;
        boolean luckyQualityUpgrade = luck.isLucky(player, LUCK_QUALITY_UPGRADE_MODIFIER);
        if (luckyQualityUpgrade)
        {
            rewardQuality = upgradeRewardQuality(rewardQuality);
        }
        String rewardQualityName = getRewardQualityName(rewardQuality);
        float rewardQualityModifier = getRewardQualityModifier(rewardQuality);
        int rewardTier = getRewardTier(targetTier);
        obj_id lootContainer = getLootContainer(playerShip, player);
        if (!isIdValid(lootContainer))
        {
            debug(player, "blocked, could not resolve loot container.");
            return false;
        }
        obj_id chest = createRareLootChest(lootContainer, rewardQuality, rewardTier);
        if (!isIdValid(chest))
        {
            debug(player, "blocked, failed to create " + rewardQualityName + " chest in container " + lootContainer + ".");
            CustomerServiceLog(LOG_CHANNEL, "Space rare loot award failed. Player=" + player + " playerShip=" + playerShip + " targetShip=" + targetShip + " lootContainer=" + lootContainer + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")");
            return false;
        }
        setObjVar(player, VAR_LAST_CHEST_AWARD_TIME, getGameTime());
        debug(player, "eligible, roll " + roll + " <= chance " + adjustedChance + " succeeded. Created " + rewardQualityName + " chest " + chest + ".");
        CustomerServiceLog(LOG_CHANNEL, "Space rare loot award success. Player=" + player + " playerTier=" + playerTier + " effectivePlayerTier=" + effectivePlayerTier + " playerShip=" + playerShip + " targetShip=" + targetShip + " targetTier=" + targetTier + " lootContainer=" + lootContainer + " chest=" + chest + " rewardTier=" + rewardTier + " higherTierDelta=" + higherTierDelta + " chance=" + adjustedChance + " roll=" + roll + " rewardQuality=" + rewardQualityName + "(" + rewardQuality + ")" + " originalRewardQuality=" + getRewardQualityName(originalRewardQuality) + "(" + originalRewardQuality + ")" + " luckyQualityUpgrade=" + luckyQualityUpgrade + " rewardQualityModifier=" + rewardQualityModifier + " rewardRoll=" + rewardRoll + " rewardChanceTotal=" + rewardChanceTotal + " baseRareChance=" + rareChance + " baseExceptionalChance=" + exceptionalChance + " baseLegendaryChance=" + legendaryChance + " adjustedRareChance=" + adjustedRareChance + " adjustedExceptionalChance=" + adjustedExceptionalChance + " adjustedLegendaryChance=" + adjustedLegendaryChance + " shiftedChance=" + shiftedChance);
        return true;
    }

    public static int upgradeRewardQuality(int rewardQuality)
    {
        if (rewardQuality == space_rare_loot_config.QUALITY_RARE)
        {
            return space_rare_loot_config.QUALITY_EXCEPTIONAL;
        }
        if (rewardQuality == space_rare_loot_config.QUALITY_EXCEPTIONAL)
        {
            return space_rare_loot_config.QUALITY_LEGENDARY;
        }
        return rewardQuality;
    }

    public static int getEffectivePilotTier(int playerTier, int targetTier)
    {
        if (targetTier > 0 && targetTier < playerTier)
        {
            return targetTier;
        }
        return playerTier;
    }

    public static int getPilotTier(obj_id player) throws InterruptedException
    {
        int playerTier = space_flags.getPilotTier(player);
        if (playerTier < MIN_REWARD_TIER)
        {
            return MIN_REWARD_TIER;
        }
        if (playerTier > MAX_REWARD_TIER)
        {
            return MAX_REWARD_TIER;
        }
        return playerTier;
    }

    public static obj_id getLootContainer(obj_id playerShip, obj_id player) throws InterruptedException
    {
        if (space_utils.isShipWithInterior(playerShip))
        {
            return getObjIdObjVar(playerShip, "objLootBox");
        }
        if (!isIdValid(player))
        {
            return null;
        }
        return utils.getInventoryContainer(player);
    }

    public static obj_id createRareLootChest(obj_id lootContainer, int rewardQuality, int rewardTier) throws InterruptedException
    {
        if (!isIdValid(lootContainer))
        {
            return null;
        }
        if (rewardQuality < space_rare_loot_config.QUALITY_RARE || rewardQuality > space_rare_loot_config.QUALITY_LEGENDARY)
        {
            rewardQuality = space_rare_loot_config.QUALITY_RARE;
        }
        rewardTier = getClampedRewardTier(rewardTier);
        obj_id chest = static_item.createNewItemFunction(CHEST_STATIC_ITEM_BASE + rewardQuality, lootContainer);
        if (isIdValid(chest))
        {
            setObjVar(chest, VAR_CHEST_REWARD_QUALITY, rewardQuality);
            setObjVar(chest, VAR_CHEST_REWARD_TIER, rewardTier);
            if (hasScript(chest, SCRIPT_GROUND_RARE_LOOT_CHEST))
            {
                detachScript(chest, SCRIPT_GROUND_RARE_LOOT_CHEST);
            }
            if (!hasScript(chest, SCRIPT_SPACE_RARE_LOOT_CHEST))
            {
                attachScript(chest, SCRIPT_SPACE_RARE_LOOT_CHEST);
            }
        }
        return chest;
    }

    public static int getClampedRewardTier(int rewardTier)
    {
        if (rewardTier < MIN_REWARD_TIER)
        {
            return MIN_REWARD_TIER;
        }
        if (rewardTier > MAX_REWARD_TIER)
        {
            return MAX_REWARD_TIER;
        }
        return rewardTier;
    }

    public static float getRewardQualityModifier(int rewardQuality)
    {
        return space_rare_loot_config.getQualityScale(rewardQuality);
    }

    public static float getEffectiveStatModifier(float statModifier, int rewardQuality)
    {
        return statModifier * getRewardQualityModifier(rewardQuality);
    }

    public static double getAdjustedBaseChance(int playerTier, int targetTier) throws InterruptedException
    {
        double chance = space_rare_loot_config.getBaseChance();
        if (targetTier - playerTier == -1)
        {
            chance = chance / 2.0d;
        }
        else if (targetTier - playerTier <= -2)
        {
            chance = 0.0d;
        }
        return chance;
    }

    public static int getTargetTier(obj_id targetShip) throws InterruptedException
    {
        String difficulty = getShipDifficulty(targetShip);
        return parseTier(difficulty);
    }

    public static int getRewardTier(int targetTier) throws InterruptedException
    {
        int maxRewardTier = getClampedRewardTier(targetTier);
        if (maxRewardTier <= MIN_REWARD_TIER)
        {
            return MIN_REWARD_TIER;
        }
        int totalWeight = 0;
        int overCapTierCount = targetTier - MAX_REWARD_TIER;
        if (overCapTierCount < 0)
        {
            overCapTierCount = 0;
        }
        for (int rewardTier = MIN_REWARD_TIER; rewardTier <= maxRewardTier; rewardTier++)
        {
            totalWeight += getRewardTierWeight(rewardTier, overCapTierCount);
        }
        int roll = rand(1, totalWeight);
        int currentWeight = 0;
        for (int rewardTier = MIN_REWARD_TIER; rewardTier <= maxRewardTier; rewardTier++)
        {
            currentWeight += getRewardTierWeight(rewardTier, overCapTierCount);
            if (roll <= currentWeight)
            {
                return rewardTier;
            }
        }
        return maxRewardTier;
    }

    public static int getRewardTierWeight(int rewardTier, int overCapTierCount)
    {
        return rewardTier + (overCapTierCount * rewardTier * rewardTier);
    }

    public static String getRewardTable(int rewardTier)
    {
        return REWARD_TABLE_BASE + rewardTier + REWARD_TABLE_SUFFIX;
    }

    public static int getRandomRewardRow(String rewardTable, String rewardQuality) throws InterruptedException
    {
        int rowCount = dataTableGetNumRows(rewardTable);
        int matchingRows = 0;
        for (int i = 0; i < rowCount; i++)
        {
            if (isRewardRowMatch(rewardTable, i, rewardQuality))
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
            if (isRewardRowMatch(rewardTable, i, rewardQuality))
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

    public static boolean isRewardRowMatch(String rewardTable, int row, String rewardQuality) throws InterruptedException
    {
        if (dataTableGetInt(rewardTable, row, REWARD_TABLE_COLUMN_ENABLED) == 0)
        {
            return false;
        }
        String quality = dataTableGetString(rewardTable, row, REWARD_TABLE_COLUMN_QUALITY);
        return isRewardQualityMatch(quality, rewardQuality);
    }

    public static boolean isRewardQualityMatch(String rowQuality, String rewardQuality)
    {
        if (rowQuality == null || rewardQuality == null)
        {
            return false;
        }
        String[] qualityList = split(rowQuality, '|');
        if (qualityList == null || qualityList.length == 0)
        {
            return false;
        }
        for (String quality : qualityList) {
            quality = quality.trim();
            if (quality.equalsIgnoreCase(REWARD_TABLE_QUALITY_ALL) || quality.equalsIgnoreCase(rewardQuality))
            {
                return true;
            }
        }
        return false;
    }

    public static int determineRewardQuality()
    {
        double rareChance = space_rare_loot_config.getRareChance();
        double exceptionalChance = space_rare_loot_config.getExceptionalChance();
        double legendaryChance = space_rare_loot_config.getLegendaryChance();
        double totalChance = rareChance + exceptionalChance + legendaryChance;
        double roll = totalChance > 0.0d ? Math.random() * totalChance : 0.0d;
        return determineRewardQuality(roll, rareChance, exceptionalChance, legendaryChance);
    }

    public static double getAdjustedRareChance(double rareChance, int higherTierDelta)
    {
        if (higherTierDelta <= 0)
        {
            return rareChance;
        }
        int shiftTierCount = higherTierDelta;
        int maxShiftTiers = space_rare_loot_config.getHigherTierQualityShiftMaxTiers();
        if (shiftTierCount > maxShiftTiers)
        {
            shiftTierCount = maxShiftTiers;
        }
        double shiftedChance = shiftTierCount * space_rare_loot_config.getHigherTierQualityShift();
        if (shiftedChance > rareChance)
        {
            shiftedChance = rareChance;
        }
        return rareChance - shiftedChance;
    }

    public static int determineRewardQuality(double roll, double rareChance, double exceptionalChance, double legendaryChance)
    {
        double totalChance = rareChance + exceptionalChance + legendaryChance;
        if (totalChance <= 0.0d)
        {
            return space_rare_loot_config.QUALITY_RARE;
        }
        if (roll < legendaryChance)
        {
            return space_rare_loot_config.QUALITY_LEGENDARY;
        }
        if (roll < legendaryChance + exceptionalChance)
        {
            return space_rare_loot_config.QUALITY_EXCEPTIONAL;
        }
        return space_rare_loot_config.QUALITY_RARE;
    }

    public static String getRewardQualityName(int rewardQuality)
    {
        switch (rewardQuality)
        {
            case space_rare_loot_config.QUALITY_LEGENDARY:
                return "legendary";
            case space_rare_loot_config.QUALITY_EXCEPTIONAL:
                return "exceptional";
            case space_rare_loot_config.QUALITY_RARE:
                return "rare";
            default:
                return "unknown";
        }
    }

    public static int parseTier(String value)
    {
        if (value == null)
        {
            return 0;
        }
        String lowered = value.toLowerCase();
        int tierIndex = lowered.indexOf("tier");
        if (tierIndex > -1)
        {
            return parseFirstInt(lowered.substring(tierIndex + 4));
        }
        return parseFirstInt(lowered);
    }

    public static int parseFirstInt(String value)
    {
        if (value == null)
        {
            return 0;
        }
        String digits = "";
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            if (c >= '0' && c <= '9')
            {
                digits += c;
            }
            else if (digits.length() > 0)
            {
                break;
            }
        }
        if (digits.length() == 0)
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(digits);
        }
        catch (NumberFormatException err)
        {
            return 0;
        }
    }

    public static void debug(obj_id player, String message) throws InterruptedException
    {
        if (!space_rare_loot_config.debugMessagesEnabled())
        {
            return;
        }
        if (!isIdValid(player) || !exists(player))
        {
            return;
        }
        sendSystemMessageTestingOnly(player, "Space rare loot: " + message);
    }
}
