package script.library;

import script.*;

public class session extends script.base_script
{
    public session()
    {
    }
    public static final int ACTIVITY_NONE = 0;
    public static final int ACTIVITY_PVE = 1;
    public static final int ACTIVITY_PVP = 2;
    public static final int ACTIVITY_CRAFTING = 4;
    public static final int ACTIVITY_ENTERTAINED = 8;
    public static final int ACTIVITY_BEEN_ENTERTAINED = 16;
    public static final int ACTIVITY_ACCESS_BAZAAR = 32;
    public static final int ACTIVITY_MISSION_TERMINAL = 64;
    public static final int ACTIVITY_SPACE_LAUNCH = 128;
    public static final int ACTIVITY_DECORATE = 256;
    public static final int ACTIVITY_OWNER_ACCESS_VENDOR = 512;
    public static final int ACTIVITY_OTHER_ACCESS_VENDOR = 1024;
    public static final int ACTIVITY_ACCESS_HARVESTER = 2048;
    public static final int ACTIVITY_ACCESS_FACTORY = 4096;
    public static final int ACTIVITY_ACCESS_STRUCTURE = 8192;
    public static final int ACTIVITY_FISHING = 16384;
    public static void logActivity(obj_id player, int activityType) throws InterruptedException
    {
        _logActivity(player, activityType);
    }
}
