package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.dictionary;
import script.library.ai_lib;
import script.library.factions;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class droid_patrol_script extends script.base_script
{
    public droid_patrol_script()
    {
    }
    public static final string_id[] DROID_RESPONSE = 
    {
        new string_id(trial.UPLINK_STF, "oww"),
        new string_id(trial.UPLINK_STF, "quit_it"),
        new string_id(trial.UPLINK_STF, "honestly"),
        new string_id(trial.UPLINK_STF, "same_side"),
        new string_id(trial.UPLINK_STF, "just_a_scratch"),
        new string_id(trial.UPLINK_STF, "new_paint"),
        new string_id(trial.UPLINK_STF, "watch_your_aim"),
        new string_id(trial.UPLINK_STF, "hey"),
        new string_id(trial.UPLINK_STF, "turn_around"),
        new string_id(trial.UPLINK_STF, "help_help"),
        new string_id(trial.UPLINK_STF, "amatures"),
        new string_id(trial.UPLINK_STF, "stormtrooper"),
        new string_id(trial.UPLINK_STF, "ouch"),
        new string_id(trial.UPLINK_STF, "not_the_face")
    };
    public static final boolean LOGGING = true;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "handleBotDeath", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id parent = trial.getParent(self);
            messageTo(parent, "droidDied", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWayPoints(self);
        factions.setIgnorePlayer(self);
        messageTo(self, "pathToNextPoint", null, 25, false);
        detachScript(self, "ai.creature_combat");
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            stop(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBotDeath(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void findWayPoints(obj_id self) throws InterruptedException
    {
        obj_id contents[] = utils.getSharedContainerObjects(self);
        if (contents == null || contents.length == 0)
        {
            doLogging("findWayPoints", "Contents list was empty, exiting");
            return;
        }
        String path = dataTableGetString(trial.UPLINK_DATA, 0, "path");
        String[] pathList = split(path, ':');
        if (pathList == null || pathList.length == 0)
        {
            doLogging("findWayPoints", "Path list was empty, exiting");
            return;
        }
        Vector waypoints = new Vector();
        waypoints.setSize(0);
        for (obj_id content : contents) {
            if (utils.hasScriptVar(content, trial.WP_NAME)) {
                for (String s : pathList) {
                    if (s.equals(utils.getStringScriptVar(content, trial.WP_NAME))) {
                        utils.addElement(waypoints, getLocation(content));
                    }
                }
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
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 1.0f);
        
        {
            if (objects == null || objects.length == 0)
            {
                return SCRIPT_CONTINUE;
            }
            for (obj_id object : objects) {
                if ((getTemplateName(object)).equals(trial.WP_OBJECT)) {
                    createNewRelay(self);
                    messageTo(self, "pathToNextPoint", null, 0, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void createNewRelay(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 5.0f);
        obj_id top = trial.getTop(self);
        if (objects == null || objects.length == 0)
        {
            location playLoc = getLocation(self);
            obj_id relay = createObject(trial.RELAY_OBJECT, playLoc);
            playClientEffectLoc(relay, trial.PRT_RELAY_CREATE, playLoc, 0.5f);
            if (!isIdValid(relay))
            {
                doLogging("createNewRelay", "Failed to create relay object");
            }
            trial.markAsTempObject(relay, true);
            messageTo(top, "validateRelays", null, 0, false);
            return;
        }
        for (obj_id object : objects) {
            if ((getTemplateName(object)).equals(trial.RELAY_OBJECT)) {
                messageTo(top, "validateRelays", null, 0, false);
                return;
            }
        }
        location playLoc = getLocation(self);
        obj_id newRelay = createObject(trial.RELAY_OBJECT, playLoc);
        playClientEffectLoc(newRelay, trial.PRT_RELAY_CREATE, playLoc, 0.5f);
        messageTo(top, "validateRelays", null, 0, false);
        trial.markAsTempObject(newRelay, true);
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        int pathIndex = 0;
        if (utils.hasScriptVar(self, "pathIndex"))
        {
            pathIndex = utils.getIntScriptVar(self, "pathIndex");
        }
        if (!utils.hasScriptVar(self, "patrolPoints"))
        {
            doLogging("pathToNextPoint", "I do not have the patrolArray scriptvar");
            return SCRIPT_CONTINUE;
        }
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        if (patrolPoints == null || patrolPoints.length == 0)
        {
            doLogging("pathToNextPoint", "Patrol points was null or length 0: ");
            return SCRIPT_CONTINUE;
        }
        if (pathIndex > patrolPoints.length - 1)
        {
            handlePatrolTypeReset(self, patrolPoints);
            return SCRIPT_CONTINUE;
        }
        ai_lib.aiPathTo(self, patrolPoints[pathIndex]);
        setHomeLocation(self, patrolPoints[pathIndex]);
        pathIndex += 1;
        utils.setScriptVar(self, "pathIndex", pathIndex);
        return SCRIPT_CONTINUE;
    }
    public void handlePatrolTypeReset(obj_id self, location[] patrolPoints) throws InterruptedException
    {
        location[] reversedList = new location[patrolPoints.length];
        int k = patrolPoints.length - 1;
        for (int i = 0; i < patrolPoints.length; i++)
        {
            reversedList[i] = patrolPoints[k];
            k--;
        }
        utils.setScriptVar(self, "patrolPoints", reversedList);
        utils.setScriptVar(self, "pathIndex", 1);
        messageTo(self, "pathToNextPoint", null, 25, false);
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/patrol_spawned_tracker/" + section, message);
        }
    }
}
