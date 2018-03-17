package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class fs_quests_sad extends script.base_script
{
    public fs_quests_sad()
    {
    }
    public static final String SAD_OBJVAR_TASK_ACTIVE = "fs_quest_sad.task_active";
    public static final String SAD_OBJVAR_TIMESTAMP = "fs_quest_sad.timestamp";
    public static final String SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP = "fs_quest_sad.taken_since_timestamp";
    public static final int SAD_MAX_IN_TIME_PERIOD = 3;
    public static final String SAD_OBJVAR_WAYPOINT = "fs_quest_sad.waypoint";
    public static final String SAD_OBJVAR_MASTER_THEATER = "fs_quest_sad.theater";
    public static final String SAD_OBJVAR_KILLABLE = "fs_quest_sad.quantity_killable";
    public static int getTimePeriod() throws InterruptedException
    {
        int timePeriod;
        String setting = getConfigSetting("GameServer", "forceSensitiveSadTimeRestrict");
        if (setting != null)
        {
            timePeriod = Integer.parseInt(setting);
        }
        else 
        {
            timePeriod = 24 * 60 * 60;
        }
        return timePeriod;
    }
    public static boolean hasTask(obj_id player) throws InterruptedException
    {
        for (int i = 0; i < 8; i++)
        {
            if (quests.isActive("fs_quests_sad_task" + i, player))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasTask2(obj_id player) throws InterruptedException
    {
        for (int i = 0; i < 8; i++)
        {
            if (quests.isActive("fs_quests_sad2_task" + i, player))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean hasExceededTaskLimit(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, SAD_OBJVAR_TIMESTAMP))
        {
            int lastTimestamp = utils.getIntObjVar(player, SAD_OBJVAR_TIMESTAMP);
            int takenSinceTimestamp = utils.getIntObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP);
            int curTime = getGameTime();
            if ((curTime - lastTimestamp < getTimePeriod()) && (takenSinceTimestamp >= SAD_MAX_IN_TIME_PERIOD))
            {
                return true;
            }
        }
        return false;
    }
    public static void recordTookTask(obj_id player) throws InterruptedException
    {
        LOG("newquests", "recordTookTask");
        int curTime = getGameTime();
        if (hasObjVar(player, SAD_OBJVAR_TIMESTAMP))
        {
            int lastTimestamp = utils.getIntObjVar(player, SAD_OBJVAR_TIMESTAMP);
            int takenSinceTimestamp = utils.getIntObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP);
            if (curTime - lastTimestamp > getTimePeriod())
            {
                utils.setObjVar(player, SAD_OBJVAR_TIMESTAMP, curTime);
                utils.setObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP, 1);
            }
            else 
            {
                utils.setObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP, ++takenSinceTimestamp);
            }
        }
        else 
        {
            utils.setObjVar(player, SAD_OBJVAR_TIMESTAMP, curTime);
            utils.setObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP, 1);
        }
        return;
    }
    public static void recordTaskSuccess(obj_id player) throws InterruptedException
    {
        LOG("newquests", "recordTaskSuccess");
        return;
    }
    public static void recordTaskFailure(obj_id player) throws InterruptedException
    {
        LOG("newquests", "recordTaskFailure");
        return;
    }
    public static boolean hasCompletedAllTasks(obj_id player) throws InterruptedException
    {
        LOG("newquests", "hasCompletedAllTasks");
        return (quests.isComplete("fs_quests_sad_finish", player));
    }
    public static boolean hasCompletedAllTasks2(obj_id player) throws InterruptedException
    {
        LOG("newquests", "hasCompletedAllTasks");
        return (quests.isComplete("fs_quests_sad2_finish", player));
    }
    public static int getNumberTasksCompleted(obj_id player) throws InterruptedException
    {
        LOG("newquests", "getNumberTasksCompleted");
        if (quests.isComplete("fs_quests_sad_task8", player))
        {
            return 7;
        }
        else if (quests.isComplete("fs_quests_sad_task7", player))
        {
            return 6;
        }
        else if (quests.isComplete("fs_quests_sad_task6", player))
        {
            return 5;
        }
        else if (quests.isComplete("fs_quests_sad_task5", player))
        {
            return 4;
        }
        else if (quests.isComplete("fs_quests_sad_task4", player))
        {
            return 3;
        }
        else if (quests.isComplete("fs_quests_sad_task3", player))
        {
            return 2;
        }
        else if (quests.isComplete("fs_quests_sad_task2", player))
        {
            return 1;
        }
        else if (quests.isComplete("fs_quests_sad_task1", player))
        {
            return 0;
        }
        else 
        {
            return -1;
        }
    }
    public static int getNumberTasksCompleted2(obj_id player) throws InterruptedException
    {
        LOG("newquests", "getNumberTasksCompleted");
        if (quests.isComplete("fs_quests_sad2_task8", player))
        {
            return 7;
        }
        else if (quests.isComplete("fs_quests_sad2_task7", player))
        {
            return 6;
        }
        else if (quests.isComplete("fs_quests_sad2_task6", player))
        {
            return 5;
        }
        else if (quests.isComplete("fs_quests_sad2_task5", player))
        {
            return 4;
        }
        else if (quests.isComplete("fs_quests_sad2_task4", player))
        {
            return 3;
        }
        else if (quests.isComplete("fs_quests_sad2_task3", player))
        {
            return 2;
        }
        else if (quests.isComplete("fs_quests_sad2_task2", player))
        {
            return 1;
        }
        else if (quests.isComplete("fs_quests_sad2_task1", player))
        {
            return 0;
        }
        else 
        {
            return -1;
        }
    }
    public static void performEndOfQuest(obj_id player) throws InterruptedException
    {
        cleanup(player);
        fs_quests.unlockBranch(player, "force_sensitive_combat_prowess_melee_speed");
        fs_quests.setQuestCompleted(player, "force_sensitive_combat_prowess_melee_speed");
    }
    public static void performEndOfQuest2(obj_id player) throws InterruptedException
    {
        cleanup(player);
        fs_quests.unlockBranch(player, "force_sensitive_combat_prowess_ranged_defense");
        fs_quests.setQuestCompleted(player, "force_sensitive_combat_prowess_ranged_defense");
    }
    public static void cleanup(obj_id player) throws InterruptedException
    {
        int x = 0;
        for (x = 1; x < 9; x++)
        {
            if (hasObjVar(player, "quest.fs_quests_sad2_return" + x + ".waypoint"))
            {
                obj_id point = getObjIdObjVar(player, "quest.fs_quests_sad2_return" + x + ".waypoint");
                destroyWaypointInDatapad(point, player);
                removeObjVar(player, "quest.fs_quests_sad2_return" + x + ".waypoint");
            }
        }
        for (x = 1; x < 9; x++)
        {
            if (hasObjVar(player, "quest.fs_quests_sad_return" + x + ".waypoint"))
            {
                obj_id point2 = getObjIdObjVar(player, "quest.fs_quests_sad_return" + x + ".waypoint");
                destroyWaypointInDatapad(point2, player);
                removeObjVar(player, "quest.fs_quests_sad_return" + x + ".waypoint");
            }
        }
        if (hasObjVar(player, SAD_OBJVAR_WAYPOINT))
        {
            obj_id wp = getObjIdObjVar(player, SAD_OBJVAR_WAYPOINT);
            destroyWaypointInDatapad(wp, player);
            removeObjVar(player, SAD_OBJVAR_WAYPOINT);
        }
        if (hasObjVar(player, SAD_OBJVAR_TASK_ACTIVE))
        {
            removeObjVar(player, SAD_OBJVAR_TASK_ACTIVE);
        }
        if (hasObjVar(player, SAD_OBJVAR_TIMESTAMP))
        {
            removeObjVar(player, SAD_OBJVAR_TIMESTAMP);
        }
        if (hasObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP))
        {
            removeObjVar(player, SAD_OBJVAR_TAKEN_SINCE_TIMESTAMP);
        }
        if (hasObjVar(player, SAD_OBJVAR_KILLABLE))
        {
            removeObjVar(player, SAD_OBJVAR_KILLABLE);
        }
        if (hasObjVar(player, geiger.OBJVAR_GEIGER_FACTOR))
        {
            removeObjVar(player, geiger.OBJVAR_GEIGER_FACTOR);
        }
        if (hasObjVar(player, geiger.OBJVAR_GEIGER_PID))
        {
            removeObjVar(player, geiger.OBJVAR_GEIGER_PID);
        }
        if (utils.hasScriptVar(player, geiger.SCRIPTVAR_GEIGER_LAST))
        {
            utils.removeScriptVar(player, geiger.SCRIPTVAR_GEIGER_LAST);
        }
        if (hasObjVar(player, SAD_OBJVAR_MASTER_THEATER))
        {
            removeObjVar(player, SAD_OBJVAR_MASTER_THEATER);
        }
        if (hasObjVar(player, geiger.OBJVAR_GEIGER_LOCATION))
        {
            removeObjVar(player, geiger.OBJVAR_GEIGER_LOCATION);
        }
        if (hasObjVar(player, geiger.OBJVAR_GEIGER_PID))
        {
            removeObjVar(player, geiger.OBJVAR_GEIGER_PID);
        }
        if (hasObjVar(player, geiger.OBJVAR_GEIGER_OBJECT))
        {
            removeObjVar(player, geiger.OBJVAR_GEIGER_OBJECT);
        }
        geiger.removeGeiger(player);
    }
}
