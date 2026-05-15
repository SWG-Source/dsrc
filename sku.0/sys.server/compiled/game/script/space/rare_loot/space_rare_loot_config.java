package script.space.rare_loot;

public class space_rare_loot_config extends script.base_script
{
    public space_rare_loot_config()
    {
    }

    public static final String CONFIG_SECTION = "SpaceRareLoot";
    public static final String KEY_BASE_CHANCE = "baseChance";
    public static final String KEY_DELAY_SECONDS = "delaySeconds";
    public static final String KEY_DEBUG_MESSAGES = "debugMessages";
    public static final String KEY_RARE_CHANCE = "rareChance";
    public static final String KEY_EXCEPTIONAL_CHANCE = "exceptionalChance";
    public static final String KEY_LEGENDARY_CHANCE = "legendaryChance";
    public static final String KEY_HIGHER_TIER_QUALITY_SHIFT = "higherTierQualityShift";
    public static final String KEY_HIGHER_TIER_EXCEPTIONAL_SHIFT_RATIO = "higherTierExceptionalShiftRatio";
    public static final String KEY_HIGHER_TIER_LEGENDARY_SHIFT_RATIO = "higherTierLegendaryShiftRatio";
    public static final String KEY_HIGHER_TIER_QUALITY_SHIFT_MAX_TIERS = "higherTierQualityShiftMaxTiers";
    public static final String KEY_RARE_SCALE = "rareScale";
    public static final String KEY_EXCEPTIONAL_SCALE = "exceptionalScale";
    public static final String KEY_LEGENDARY_SCALE = "legendaryScale";

    public static final int QUALITY_DEFAULT = 0;
    public static final int QUALITY_RARE = 1;
    public static final int QUALITY_EXCEPTIONAL = 2;
    public static final int QUALITY_LEGENDARY = 3;

    public static final int DEFAULT_DELAY_SECONDS = 3600;
    public static final double DEFAULT_RARE_CHANCE = 70.0d;
    public static final double DEFAULT_EXCEPTIONAL_CHANCE = 25.0d;
    public static final double DEFAULT_LEGENDARY_CHANCE = 5.0d;
    public static final double DEFAULT_HIGHER_TIER_QUALITY_SHIFT = 5.0d;
    public static final double DEFAULT_HIGHER_TIER_EXCEPTIONAL_SHIFT_RATIO = 0.80d;
    public static final double DEFAULT_HIGHER_TIER_LEGENDARY_SHIFT_RATIO = 0.20d;
    public static final int DEFAULT_HIGHER_TIER_QUALITY_SHIFT_MAX_TIERS = 3;
    public static final float DEFAULT_RARE_SCALE = 0.50f;
    public static final float DEFAULT_EXCEPTIONAL_SCALE = 1.00f;
    public static final float DEFAULT_LEGENDARY_SCALE = 1.50f;

    public static boolean isEnabled()
    {
        String baseChance = getConfigSetting(CONFIG_SECTION, KEY_BASE_CHANCE);
        if (baseChance == null)
        {
            return false;
        }
        return getBaseChance() > 0.0d;
    }

    public static double getBaseChance()
    {
        return getDoubleSetting(KEY_BASE_CHANCE, 0.0d);
    }

    public static int getDelaySeconds()
    {
        return getIntSetting(KEY_DELAY_SECONDS, DEFAULT_DELAY_SECONDS);
    }

    public static boolean debugMessagesEnabled()
    {
        return getBooleanSetting(KEY_DEBUG_MESSAGES, false);
    }

    public static double getRareChance()
    {
        return getNonNegativeDoubleSetting(KEY_RARE_CHANCE, DEFAULT_RARE_CHANCE);
    }

    public static double getExceptionalChance()
    {
        return getNonNegativeDoubleSetting(KEY_EXCEPTIONAL_CHANCE, DEFAULT_EXCEPTIONAL_CHANCE);
    }

    public static double getLegendaryChance()
    {
        return getNonNegativeDoubleSetting(KEY_LEGENDARY_CHANCE, DEFAULT_LEGENDARY_CHANCE);
    }

    public static double getHigherTierQualityShift()
    {
        return getNonNegativeDoubleSetting(KEY_HIGHER_TIER_QUALITY_SHIFT, DEFAULT_HIGHER_TIER_QUALITY_SHIFT);
    }

    public static double getHigherTierExceptionalShiftRatio()
    {
        return getNonNegativeDoubleSetting(KEY_HIGHER_TIER_EXCEPTIONAL_SHIFT_RATIO, DEFAULT_HIGHER_TIER_EXCEPTIONAL_SHIFT_RATIO);
    }

    public static double getHigherTierLegendaryShiftRatio()
    {
        return getNonNegativeDoubleSetting(KEY_HIGHER_TIER_LEGENDARY_SHIFT_RATIO, DEFAULT_HIGHER_TIER_LEGENDARY_SHIFT_RATIO);
    }

    public static int getHigherTierQualityShiftMaxTiers()
    {
        return getNonNegativeIntSetting(KEY_HIGHER_TIER_QUALITY_SHIFT_MAX_TIERS, DEFAULT_HIGHER_TIER_QUALITY_SHIFT_MAX_TIERS);
    }

    public static float getRareScale()
    {
        return getNonNegativeFloatSetting(KEY_RARE_SCALE, DEFAULT_RARE_SCALE);
    }

    public static float getExceptionalScale()
    {
        return getNonNegativeFloatSetting(KEY_EXCEPTIONAL_SCALE, DEFAULT_EXCEPTIONAL_SCALE);
    }

    public static float getLegendaryScale()
    {
        return getNonNegativeFloatSetting(KEY_LEGENDARY_SCALE, DEFAULT_LEGENDARY_SCALE);
    }

    public static float getQualityScale(int quality)
    {
        switch (quality)
        {
            case QUALITY_RARE:
                return getRareScale();
            case QUALITY_EXCEPTIONAL:
                return getExceptionalScale();
            case QUALITY_LEGENDARY:
                return getLegendaryScale();
            default:
                return 0.0f;
        }
    }

    private static boolean getBooleanSetting(String key, boolean defaultValue)
    {
        String value = getConfigSetting(CONFIG_SECTION, key);
        if (value == null)
        {
            return defaultValue;
        }
        return value.equals("1") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes");
    }

    private static int getIntSetting(String key, int defaultValue)
    {
        String value = getConfigSetting(CONFIG_SECTION, key);
        if (value == null)
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException err)
        {
            return defaultValue;
        }
    }

    private static int getNonNegativeIntSetting(String key, int defaultValue)
    {
        int value = getIntSetting(key, defaultValue);
        if (value < 0)
        {
            return 0;
        }
        return value;
    }

    private static float getFloatSetting(String key, float defaultValue)
    {
        String value = getConfigSetting(CONFIG_SECTION, key);
        if (value == null)
        {
            return defaultValue;
        }
        try
        {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException err)
        {
            return defaultValue;
        }
    }

    private static float getNonNegativeFloatSetting(String key, float defaultValue)
    {
        float value = getFloatSetting(key, defaultValue);
        if (value < 0.0f)
        {
            return 0.0f;
        }
        return value;
    }

    private static double getDoubleSetting(String key, double defaultValue)
    {
        String value = getConfigSetting(CONFIG_SECTION, key);
        if (value == null)
        {
            return defaultValue;
        }
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException err)
        {
            return defaultValue;
        }
    }

    private static double getNonNegativeDoubleSetting(String key, double defaultValue)
    {
        double value = getDoubleSetting(key, defaultValue);
        if (value < 0.0d)
        {
            return 0.0d;
        }
        return value;
    }
}
