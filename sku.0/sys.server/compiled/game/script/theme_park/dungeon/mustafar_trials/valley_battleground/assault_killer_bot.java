package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class assault_killer_bot extends script.base_script
{
    public assault_killer_bot()
    {
    }
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.prepareCorpse(self);
        utils.setScriptVar(self, trial.BATTLEFIELD_DROID_CORPSE, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWayPoints(self);
        messageTo(self, "pathToNextPoint", null, 2, false);
        trial.setInterest(self);
        trial.markAsDroidArmy(self);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public void findWayPoints(obj_id self) throws InterruptedException
    {
        obj_id objects[] = getObjectsInRange(self, 400);
        if (objects == null || objects.length == 0)
        {
            doLogging("findWayPoints", "Contents list was empty, exiting");
            return;
        }
        int pathNum = 0;
        if (utils.hasScriptVar(self, "path"))
        {
            pathNum = utils.getIntScriptVar(self, "path");
        }
        else 
        {
            String[] paths = dataTableGetStringColumn(trial.VALLEY_DATA, "path");
            pathNum = rand(0, paths.length - 1);
        }
        String path = dataTableGetString(trial.VALLEY_DATA, pathNum, "path");
        String[] pathList = split(path, ';');
        if (pathList == null || pathList.length == 0)
        {
            doLogging("findWayPoints", "Path list was empty, exiting");
            return;
        }
        Vector waypoints = new Vector();
        waypoints.setSize(0);
        for (String s : pathList) {
            for (obj_id object : objects) {
                if (hasObjVar(object, "wp_name")) {
                    if (s.equals(getStringObjVar(object, "wp_name"))) {
                        utils.addElement(waypoints, getLocation(object));
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
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, "patrolPoints");
        ai_lib.setPatrolOncePath(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/assault_killer_bot/" + section, message);
        }
    }
}
