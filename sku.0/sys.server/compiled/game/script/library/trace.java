package script.library;

import script.obj_id;

public class trace extends script.base_script
{
    public trace()
    {
    }
    public static final int TL_DEBUG = 1;
    public static final int TL_ERROR_LOG = 2;
    public static final int TL_CS_LOG = 4;
    public static final int TL_WARNING = 8;
    public static final String DESIGNER_CS_DEBUG_LOG = "designer_debug";
    public static void log(String channel, String message) throws InterruptedException
    {
        log(channel, message, null);
    }
    public static void log(String channel, String message, obj_id logAbout) throws InterruptedException
    {
        log(channel, message, logAbout, TL_DEBUG);
    }
    public static boolean isFlagSet(int mask, int flag) throws InterruptedException
    {
        return (mask & flag) == flag;
    }
    public static void log(String channelOrLogFile, String message, obj_id logAbout, int traceLevelMask) throws InterruptedException
    {
        boolean logTxtDone = false;
        if (isFlagSet(traceLevelMask, TL_CS_LOG))
        {
            CustomerServiceLog(channelOrLogFile, message, logAbout);
        }
        if (isFlagSet(traceLevelMask, TL_ERROR_LOG))
        {
            LOG(channelOrLogFile, "|ERROR|:[" + logAbout + "]:" + message);
            logTxtDone = true;
        }
        if (isFlagSet(traceLevelMask, TL_WARNING) && (!logTxtDone))
        {
            LOG(channelOrLogFile, "|WARNING|:[" + logAbout + "]:" + message);
            logTxtDone = true;
        }
        if (isFlagSet(traceLevelMask, TL_DEBUG) && isDebugDefined())
        {
            if (pipeDebugToCsLog())
            {
                CustomerServiceLog(DESIGNER_CS_DEBUG_LOG, "[" + channelOrLogFile + "]: " + message, logAbout);
            }
            else if (!logTxtDone)
            {
                LOG(channelOrLogFile, "[" + logAbout + "]:" + message);
            }
        }
    }
    public static boolean isDebugDefined() throws InterruptedException
    {
        String flag = getConfigSetting("GameServer", "scriptDebugTrace");
        return !(flag == null || flag.length() < 1);
    }
    public static boolean pipeDebugToCsLog() throws InterruptedException
    {
        String flag = getConfigSetting("GameServer", "pipeScriptDebugToCsLog");
        return !(flag == null || flag.length() < 1);
    }
}
