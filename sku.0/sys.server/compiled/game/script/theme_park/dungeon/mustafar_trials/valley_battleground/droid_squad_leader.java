package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class droid_squad_leader extends script.base_script
{
    public droid_squad_leader()
    {
    }
    public static final String SQUAD_MEMBER = "som_battlefield_droid_soldier";
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findWayPoints(self);
        messageTo(self, "pathToNextPoint", null, 2, false);
        trial.setInterest(self);
        messageTo(self, "spawnSquad", null, 2, false);
        trial.markAsDroidArmy(self);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.prepareCorpse(self);
        utils.setScriptVar(self, trial.BATTLEFIELD_DROID_CORPSE, true);
        return SCRIPT_CONTINUE;
    }
    public int spawnSquad(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] squad = new obj_id[4];
        location baseLoc = getLocation(self);
        setYaw(self, -250);
        for (int i = 0; i < squad.length; i++)
        {
            location spawnLoc = new location(baseLoc.x, baseLoc.y, baseLoc.z, baseLoc.area, baseLoc.cell);
            squad[i] = create.object(SQUAD_MEMBER, spawnLoc);
            if (isIdValid(squad[i]))
            {
                attachScript(squad[i], "theme_park.dungeon.mustafar_trials.valley_battleground.droid_squad_member");
                utils.setScriptVar(squad[i], trial.PARENT, self);
                utils.setScriptVar(squad[i], "patrolPoints", utils.getLocationArrayScriptVar(self, "patrolPoints"));
                trial.markAsDroidArmy(squad[i]);
            }
        }
        for (int j = 0; j < squad.length; j++)
        {
            messageTo(squad[j], "pathToNextPoint", null, j + 2, false);
        }
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
            LOG("logging/droid_squad_leader/" + section, message);
        }
    }
}
