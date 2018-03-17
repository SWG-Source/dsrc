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
import script.library.trial;

public class bug_spawner_tracker extends script.base_script
{
    public bug_spawner_tracker()
    {
    }
    public static final String PATH_MAX = "pathMax";
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id parent = utils.getObjIdScriptVar(self, trial.PARENT);
            int respawn = rand(30, 60);
            messageTo(parent, "droneDied", null, respawn, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroyRelayObject", null, 5, false);
        playClientEffectObj(self, trial.PRT_KUBAZA_WARNING, self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWayPoints(self);
        setInvulnerable(self, true);
        messageTo(self, "removeInvulnerable", null, 8, false);
        messageTo(self, "pathToNextPoint", null, 8, false);
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
        utils.setScriptVar(self, PATH_MAX, patrolPoints.length);
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        checkForRelayPoint(self);
        location here = getLocation(self);
        utils.setScriptVar(self, "stuckLoc", here);
        messageTo(self, "checkForStuck", null, 12, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForStuck(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        location last = utils.getLocationScriptVar(self, "stuckLoc");
        if (here.equals(last))
        {
            messageTo(self, "pathToNextPoint", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForRelayPoint(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 5f);
        if (objects == null || objects.length == 0)
        {
            messageTo(self, "pathToNextPoint", null, 0, false);
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            if ((getTemplateName(objects[i])).equals(trial.RELAY_OBJECT))
            {
                dictionary dict = new dictionary();
                dict.put("relay", objects[i]);
                messageTo(self, "destroyRelayObject", dict, 5, false);
                return;
            }
        }
        messageTo(self, "pathToNextPoint", null, 0, false);
    }
    public int destroyRelayObject(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id relay = params.getObjId("relay");
        if (!ai_lib.isInCombat(self))
        {
            obj_id[] targets = trial.getValidTargetsInRadius(self, 7.0f);
            playClientEffectLoc(self, trial.PRT_KUBAZA_EXPLODE, getLocation(self), 0.4f);
            destroyObject(self);
            destroyObject(relay);
            if (targets == null || targets.length == 0)
            {
                doLogging("nukeSelf", "No valid targets in blast radius");
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < targets.length; i++)
            {
                damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 500);
            }
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "pathToNextPoint", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        int pathIndex = rand(0, utils.getIntScriptVar(self, PATH_MAX) - 1);
        utils.setScriptVar(self, "pathIndex", pathIndex);
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
        ai_lib.aiPathTo(self, patrolPoints[pathIndex]);
        setHomeLocation(self, patrolPoints[pathIndex]);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/bug_spawner_tracker/" + section, message);
        }
    }
}
