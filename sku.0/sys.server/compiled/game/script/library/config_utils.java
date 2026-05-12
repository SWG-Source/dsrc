package script.library;

import script.*;

public class config_utils extends script.base_script
{
    public config_utils()
    {
    }

    public static String getStringConfig(String section, String key) throws InterruptedException
    {
        return getStringConfig(section, key, null);
    }

    public static String getStringConfig(String section, String key, String defaultValue) throws InterruptedException
    {
        if (section == null || section.length() <= 0 || key == null || key.length() <= 0)
        {
            return defaultValue;
        }

        String value = getConfigSetting(section, key);

        if (value == null || value.length() <= 0)
        {
            return defaultValue;
        }

        return value;
    }

    public static boolean getBooleanConfig(String section, String key) throws InterruptedException
    {
        return getBooleanConfig(section, key, false);
    }

    public static boolean getBooleanConfig(String section, String key, boolean defaultValue) throws InterruptedException
    {
        String value = getStringConfig(section, key, null);

        if (value == null)
        {
            return defaultValue;
        }

        value = value.toLowerCase();

        if (value.equals("true") || value.equals("on") || value.equals("1") || value.equals("yes"))
        {
            return true;
        }

        if (value.equals("false") || value.equals("off") || value.equals("0") || value.equals("no"))
        {
            return false;
        }

        return defaultValue;
    }

    public static int getIntConfig(String section, String key) throws InterruptedException
    {
        return getIntConfig(section, key, 0);
    }

    public static int getIntConfig(String section, String key, int defaultValue) throws InterruptedException
    {
        String value = getStringConfig(section, key, null);

        if (value == null)
        {
            return defaultValue;
        }

        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    public static long getLongConfig(String section, String key) throws InterruptedException
    {
        return getLongConfig(section, key, 0);
    }

    public static long getLongConfig(String section, String key, long defaultValue) throws InterruptedException
    {
        String value = getStringConfig(section, key, null);

        if (value == null)
        {
            return defaultValue;
        }

        try
        {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    public static float getFloatConfig(String section, String key) throws InterruptedException
    {
        return getFloatConfig(section, key, 0.0f);
    }

    public static float getFloatConfig(String section, String key, float defaultValue) throws InterruptedException
    {
        String value = getStringConfig(section, key, null);

        if (value == null)
        {
            return defaultValue;
        }

        try
        {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    public static double getDoubleConfig(String section, String key) throws InterruptedException
    {
        return getDoubleConfig(section, key, 0.0);
    }

    public static double getDoubleConfig(String section, String key, double defaultValue) throws InterruptedException
    {
        String value = getStringConfig(section, key, null);

        if (value == null)
        {
            return defaultValue;
        }

        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            return defaultValue;
        }
    }

    public static int getPercentConfig(String section, String key) throws InterruptedException
    {
        return getPercentConfig(section, key, 0);
    }

    public static int getPercentConfig(String section, String key, int defaultValue) throws InterruptedException
    {
        return clampInt(getIntConfig(section, key, defaultValue), 0, 100);
    }

    public static int getIntConfigClamped(String section, String key, int defaultValue, int minValue, int maxValue) throws InterruptedException
    {
        return clampInt(getIntConfig(section, key, defaultValue), minValue, maxValue);
    }

    public static int clampInt(int value, int minValue, int maxValue)
    {
        if (value < minValue)
        {
            return minValue;
        }

        if (value > maxValue)
        {
            return maxValue;
        }

        return value;
    }
}
