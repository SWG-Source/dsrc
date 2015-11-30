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

public class bug_spawner extends script.base_script
{
    public bug_spawner()
    {
    }
    public static final String BUG_COUNT = "spawning.bugCount";
    public static final int BUG_MAX = 8;
    public static final String DRONE = "som_link_lava_beetle_drone";
    public static final String WORKER = "som_link_lava_beetle_worker";
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id dungeon = trial.getTop(self);
            messageTo(dungeon, "bugSpawnerDied", null, 10, false);
            location soldierSpawn = getLocation(self);
            obj_id item = createObjectInCell(trial.WP_OBJECT, dungeon, trial.UPLINK_ROOM, soldierSpawn);
            if (!isIdValid(item))
            {
                doLogging("OnDestroy", "Tried to create invalid item(" + trial.WP_OBJECT + ")");
                return SCRIPT_CONTINUE;
            }
            attachScript(item, "theme_park.dungeon.mustafar_trials.establish_the_link.soldier_spawner");
            trial.markAsTempObject(item, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnNewBug", null, 5, false);
        setMaxHitpoints(self, 50000);
        setHitpoints(self, 50000);
        messageTo(self, "beginRepair", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDamaged(obj_id self, obj_id attacker, obj_id weapon, int damage) throws InterruptedException
    {
        int curHP = getHitpoints(self);
        if (curHP <= 1)
        {
            location playLoc = getLocation(self);
            playClientEffectLoc(self, "clienteffect/combat_explosion_lair_large.cef", playLoc, 0.4f);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int beginRepair(obj_id self, dictionary params) throws InterruptedException
    {
        int maxHitPoints = getMaxHitpoints(self);
        int currentHitPoints = getHitpoints(self);
        if (currentHitPoints < maxHitPoints)
        {
            setHitpoints(self, currentHitPoints + 2000);
        }
        messageTo(self, "beginRepair", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnNewBug(obj_id self, dictionary params) throws InterruptedException
    {
        String toSpawn = DRONE;
        if (rand(0, 9) > 7)
        {
            toSpawn = WORKER;
        }
        obj_id creature = create.object(toSpawn, getLocation(self));
        attachScript(creature, "theme_park.dungeon.mustafar_trials.establish_the_link.bug_spawner_tracker");
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
            doLogging("canSpawnMoreBugs", "Lair does not have the script var, spawning more womprats");
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
    public int droneDied(obj_id self, dictionary params) throws InterruptedException
    {
        decrementBugCount(self);
        if (canSpawnMoreBugs(self))
        {
            messageTo(self, "spawnNewBug", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            doLogging("droneDied", "Cannot spawn more bugs because limit has been reached");
            return SCRIPT_CONTINUE;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/patrol_spawned/" + section, message);
        }
    }
}
