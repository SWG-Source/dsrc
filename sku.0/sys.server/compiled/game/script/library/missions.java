package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class missions extends script.base_script
{
    public missions()
    {
    }
    public static final int STATE_DYNAMIC_PICKUP = 1;
    public static final int STATE_DYNAMIC_DROPOFF = 2;
    public static final int STATE_DYNAMIC_START = 3;
    public static final int STATE_DELIVER_PICKUP = 4;
    public static final int STATE_DELIVER_DROPOFF = 5;
    public static final int STATE_DEAD = 6;
    public static final int STATE_MISSION_COMPLETE = 7;
    public static final int BH_STAT_MIN = -1;
    public static final int WINS = 0;
    public static final int LOSSES = 1;
    public static final int ABORTS = 2;
    public static final int TIMEOUTS = 3;
    public static final int BH_STAT_MAX = 4;
    public static final int BOUNTY_FLAG_NONE = 0;
    public static final int BOUNTY_FLAG_SMUGGLER = 1;
    public static final int DAILY_MISSION_XP_REWARD = 10;
    public static final int DAILY_MISSION_CASH_REWARD = 15;
    public static final int DAILY_MISSION_XP_SANITY = 5;
    public static final int DAILY_MISSION_XP_LOW = 9;
    public static final int DAILY_MISSION_XP_MEDIUM = 14;
    public static final int DAILY_MISSION_XP_HIGH = 19;
    public static final String DAILY_MISSION_OBJVAR = "missions.daily";
    public static final String DAILY_MISSION_CLOCK_OBJVAR = "missions.dailyClock";
    public static final string_id DAILY_REWARD_XP = new string_id("base_player", "prose_mission_xp_amount");
    public static void sendBountyFail(obj_id hunter, obj_id target) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("target", target);
        setObjVar(hunter, "intState", STATE_MISSION_COMPLETE);
        messageTo(hunter, "bountyFailure", params, 0, true);
    }
    public static void sendBountySuccess(obj_id hunter, obj_id target) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("target", target);
        setObjVar(hunter, "intState", STATE_MISSION_COMPLETE);
        messageTo(hunter, "bountySuccess", params, 0, true);
    }
    public static void sendBountyIncomplete(obj_id hunter, obj_id target) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("target", target);
        setObjVar(hunter, "intState", STATE_MISSION_COMPLETE);
        messageTo(hunter, "bountyIncomplete", params, 0, true);
    }
    public static void increaseBountyJediKillTracking(obj_id objPlayer, int stat) throws InterruptedException
    {
        if (stat <= missions.BH_STAT_MIN || stat >= missions.BH_STAT_MAX)
        {
            return;
        }
        int[] killData = new int[missions.BH_STAT_MAX];
        if (!hasObjVar(objPlayer, "bounty_hunter.jedi_kill_tracker"))
        {
            setObjVar(objPlayer, "bounty_hunter.jedi_kill_tracker", killData);
        }
        killData = getIntArrayObjVar(objPlayer, "bounty_hunter.jedi_kill_tracker");
        if (killData.length != missions.BH_STAT_MAX)
        {
            return;
        }
        killData[stat]++;
        setObjVar(objPlayer, "bounty_hunter.jedi_kill_tracker", killData);
    }
    public static int getPlayerDailyCount(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0;
        }
        if (!hasObjVar(player, DAILY_MISSION_OBJVAR))
        {
            return 0;
        }
        return getIntObjVar(player, DAILY_MISSION_OBJVAR);
    }
    public static void incrementDaily(obj_id player) throws InterruptedException
    {
        int missionData = getPlayerDailyCount(player);
        if (!hasObjVar(player, DAILY_MISSION_CLOCK_OBJVAR))
        {
            int currentTime = getCalendarTime();
            int alarmTime = createDailyAlarmClock(player, "handleDailyMissionReset", null, 19, 0, 0);
            int alarmTimeObjVar = currentTime + alarmTime;
            setObjVar(player, DAILY_MISSION_CLOCK_OBJVAR, alarmTimeObjVar);
        }
        missionData++;
        setObjVar(player, DAILY_MISSION_OBJVAR, missionData);
    }
    public static boolean canEarnDailyMissionXp(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        int dailyData = getPlayerDailyCount(player);
        if (dailyData < 0)
        {
            return false;
        }
        if (utils.isProfession(player, utils.TRADER) || utils.isProfession(player, utils.ENTERTAINER))
        {
            return false;
        }
        if (dailyData < DAILY_MISSION_XP_REWARD)
        {
            return true;
        }
        return false;
    }
    public static float alterMissionPayoutDivisor(obj_id player, float divisor, int missionLevel) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        int levelDelta = Math.abs(missionLevel - playerLevel);
        divisor = alterMissionPayoutDivisorDaily(player, divisor);
        if (utils.isProfession(player, utils.ENTERTAINER))
        {
            divisor += 10.0f;
        }
        if (utils.isProfession(player, utils.TRADER))
        {
            divisor += 20.0f;
        }
        if (levelDelta >= 10)
        {
            divisor += (float)levelDelta / 2;
        }
        return divisor;
    }
    public static float alterMissionPayoutDivisorDaily(obj_id player, float divisor) throws InterruptedException
    {
        int missionsCompleted = getPlayerDailyCount(player);
        if (missionsCompleted > DAILY_MISSION_CASH_REWARD)
        {
            divisor += (float)missionsCompleted;
        }
        return divisor;
    }
    public static float alterMissionPayoutDivisorDaily(obj_id player) throws InterruptedException
    {
        return alterMissionPayoutDivisorDaily(player, 1.0f);
    }
    public static void initializeDailyOnLogin(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, DAILY_MISSION_CLOCK_OBJVAR))
        {
            int currentTime = getCalendarTime();
            int alarmTimeObjVar = getIntObjVar(player, missions.DAILY_MISSION_CLOCK_OBJVAR);
            if (currentTime >= alarmTimeObjVar)
            {
                clearDailyObjVars(player);
            }
            else 
            {
                int secondsUntil = alarmTimeObjVar - currentTime;
                messageTo(player, "handleDailyMissionReset", null, secondsUntil, false);
            }
        }
        else if (hasObjVar(player, DAILY_MISSION_OBJVAR))
        {
            int countObjVar = getIntObjVar(player, missions.DAILY_MISSION_OBJVAR);
            if (countObjVar > 0)
            {
                int currentTime = getCalendarTime();
                int alarmTime = createDailyAlarmClock(player, "handleDailyMissionReset", null, 19, 0, 0);
                int alarmTimeObjVar = currentTime + alarmTime;
                setObjVar(player, DAILY_MISSION_CLOCK_OBJVAR, alarmTimeObjVar);
            }
        }
    }
    public static void clearDailyObjVars(obj_id player) throws InterruptedException
    {
        setObjVar(player, DAILY_MISSION_OBJVAR, 0);
        removeObjVar(player, DAILY_MISSION_CLOCK_OBJVAR);
    }
    public static boolean isDestroyMission(obj_id objMissionData) throws InterruptedException
    {
        if (!isIdValid(objMissionData))
        {
            return false;
        }
        String strTest = getMissionType(objMissionData);
        if (strTest.equals("destroy"))
        {
            return true;
        }
        return false;
    }
}
