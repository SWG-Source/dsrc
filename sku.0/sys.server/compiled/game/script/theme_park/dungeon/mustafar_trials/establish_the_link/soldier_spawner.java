package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.trial;

public class soldier_spawner extends script.base_script
{
    public soldier_spawner()
    {
    }
    public static final String BUG_COUNT = "spawning.bugCount";
    public static final int BUG_MAX = 3;
    public static final String SOLDIER = "som_link_lava_beetle_soldier";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnNewBug", null, 5, false);
        locatePatrolPoints(self);
        return SCRIPT_CONTINUE;
    }
    public void locatePatrolPoints(obj_id self) throws InterruptedException
    {
        obj_id contents[] = utils.getSharedContainerObjects(self);
        if (contents == null || contents.length == 0)
        {
            doLogging("findWayPoints", "Contents list was empty, exiting");
            return;
        }
        Vector waypoints = new Vector();
        waypoints.setSize(0);
        for (int i = 0; i < contents.length; i++)
        {
            if (utils.hasScriptVar(contents[i], trial.WP_NAME))
            {
                utils.addElement(waypoints, getLocation(contents[i]));
            }
        }
        if (waypoints == null)
        {
            doLogging("findWayPoints", "No waypoints were found, exiting");
            return;
        }
        location[] patrolPoints = new location[0];
        if (waypoints != null)
        {
            patrolPoints = new location[waypoints.size()];
            waypoints.toArray(patrolPoints);
        }
        if (patrolPoints.length == 0)
        {
            doLogging("findWayPoints", "Patrol Point list was empty, exiting");
            return;
        }
        utils.setScriptVar(self, "patrolPoints", patrolPoints);
    }
    public int spawnNewBug(obj_id self, dictionary params) throws InterruptedException
    {
        int randX = rand(-6, 6);
        int randZ = rand(-6, 6);
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        location spawnLoc = patrolPoints[rand(0, patrolPoints.length - 1)];
        spawnLoc.x += randX;
        spawnLoc.z += randZ;
        obj_id creature = create.object(SOLDIER, spawnLoc);
        attachScript(creature, "theme_park.dungeon.mustafar_trials.establish_the_link.soldier_spawner_tracker");
        utils.setScriptVar(creature, trial.PARENT, self);
        incrementBugCount(self);
        if (canSpawnMoreBugs(self))
        {
            messageTo(self, "spawnNewBug", null, 20, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int incrementBugCount(obj_id self) throws InterruptedException
    {
        int count = 0;
        if (utils.hasScriptVar(self, BUG_COUNT))
        {
            count = utils.getIntScriptVar(self, BUG_COUNT);
        }
        count += 1;
        utils.setScriptVar(self, BUG_COUNT, count);
        return count;
    }
    public int decrementBugCount(obj_id self) throws InterruptedException
    {
        int count = 0;
        if (utils.hasScriptVar(self, BUG_COUNT))
        {
            count = utils.getIntScriptVar(self, BUG_COUNT);
        }
        count = count - 1;
        if (count < 0)
        {
            count = 0;
        }
        utils.setScriptVar(self, BUG_COUNT, count);
        return count;
    }
    public boolean canSpawnMoreBugs(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, BUG_COUNT))
        {
            return true;
        }
        int currentCount = utils.getIntScriptVar(self, BUG_COUNT);
        if (currentCount < BUG_MAX)
        {
            return true;
        }
        doLogging("canSpawnMoreBugs", "Bug max exceeded, stop spawning bugs");
        return false;
    }
    public int soldierDied(obj_id self, dictionary params) throws InterruptedException
    {
        decrementBugCount(self);
        if (canSpawnMoreBugs(self))
        {
            messageTo(self, "spawnNewBug", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            doLogging("soldierDied", "Cannot spawn more bugs because limit has been reached");
            return SCRIPT_CONTINUE;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/soldier_spawner/" + section, message);
        }
    }
}
