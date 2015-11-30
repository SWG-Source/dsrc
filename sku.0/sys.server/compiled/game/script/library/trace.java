package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import java.lang.reflect.*;
import java.lang.Thread.*;

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
        if ((mask & flag) == flag)
        {
            return true;
        }
        return false;
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
        return;
    }
    public static boolean isDebugDefined() throws InterruptedException
    {
        String flag = getConfigSetting("GameServer", "scriptDebugTrace");
        if (flag == null || flag.length() < 1)
        {
            return false;
        }
        return true;
    }
    public static boolean pipeDebugToCsLog() throws InterruptedException
    {
        String flag = getConfigSetting("GameServer", "pipeScriptDebugToCsLog");
        if (flag == null || flag.length() < 1)
        {
            return false;
        }
        return true;
    }
}
