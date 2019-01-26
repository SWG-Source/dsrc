package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.dictionary;
import script.library.ai_lib;
import script.library.create;
import script.library.trial;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Vector;

public class forward_commander extends script.base_script
{
    public forward_commander()
    {
    }
    public static final String SQUAD_MEMBER = "som_battlefield_elite_guard";
    public static final boolean LOGGING = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.bumpSession(self);
        obj_id top = utils.getObjIdScriptVar(self, trial.PARENT);
        messageTo(top, "commanderDied", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        findWayPoints(self);
        trial.setInterest(self);
        trial.markAsDroidArmy(self);
        messageTo(self, "pathToNextPoint", null, 2, false);
        messageTo(self, "spawnEliteGuard", null, 2, false);
        messageTo(self, "performRez", trial.getSessionDict(self), trial.BATTLEFIELD_COMM_REZ_DELAY, false);
        setHibernationDelay(self, 7200);
        return SCRIPT_CONTINUE;
    }
    public int spawnEliteGuard(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] squad = new obj_id[6];
        location baseLoc = getLocation(self);
        setYaw(self, -250);
        for (int i = 0; i < squad.length; i++)
        {
            location spawnLoc = new location(baseLoc.x, baseLoc.y, baseLoc.z, baseLoc.area, baseLoc.cell);
            squad[i] = create.object(SQUAD_MEMBER, spawnLoc);
            if (isIdValid(squad[i]))
            {
                attachScript(squad[i], "theme_park.dungeon.mustafar_trials.valley_battleground.elite_guard");
                utils.setScriptVar(squad[i], trial.PARENT, self);
                ai_lib.setPatrolOncePath(squad[i], utils.getLocationArrayScriptVar(self, "patrolPoints"));
                trial.markAsDroidArmy(squad[i]);
            }
        }
        ai_lib.establishAgroLink(self, squad);
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
        if (waypoints == null || waypoints.size() == 0)
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
    public int performRez(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] corpses = trial.getObjectsInRangeWithScriptVar(self, trial.BATTLEFIELD_DROID_CORPSE, 22.0f);
        if (corpses == null || corpses.length == 0)
        {
            messageTo(self, "performRez", trial.getSessionDict(self), trial.BATTLEFIELD_COMM_REZ_DELAY, false);
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, trial.PRT_DROID_REVIVE, getLocation(self), 4.0f);
        for (int i = 0; i < corpses.length && i < 3; i++)
        {
            rezCorpse(corpses[i], getLocation(corpses[i]));
        }
        messageTo(self, "performRez", trial.getSessionDict(self), trial.BATTLEFIELD_COMM_REZ_DELAY, false);
        return SCRIPT_CONTINUE;
    }
    public void rezCorpse(obj_id corpse, location corpseLoc) throws InterruptedException
    {
        String template = getTemplateName(corpse);
        location[] patrolPoints = utils.getLocationArrayScriptVar(getSelf(), "patrolPoints");
        int type = 0;
        if (template.contains("cww"))
        {
            type = 1;
        }
        if (template.contains("union"))
        {
            type = 2;
        }
        switch (type)
        {
            case 0:
            obj_id hk = create.object("som_battlefield_droid_soldier", corpseLoc);
            playClientEffectLoc(hk, trial.PRT_DROID_REVIVE, corpseLoc, 4.0f);
            attachScript(hk, "theme_park.dungeon.mustafar_trials.valley_battleground.droid_squad_member");
            messageTo(hk, "pathToNextPoint", null, 3, false);
            utils.setScriptVar(hk, "patrolPoints", patrolPoints);
            destroyObject(corpse);
            trial.markAsDroidArmy(hk);
            return;
            case 1:
            obj_id cww = create.object("som_battlefield_ak_3", corpseLoc);
            playClientEffectLoc(cww, trial.PRT_DROID_REVIVE, corpseLoc, 4.0f);
            attachScript(cww, "theme_park.dungeon.mustafar_trials.valley_battleground.assault_killer_bot");
            messageTo(cww, "pathToNextPoint", null, 3, false);
            utils.setScriptVar(cww, "patrolPoints", patrolPoints);
            destroyObject(corpse);
            trial.markAsDroidArmy(cww);
            return;
            case 2:
            obj_id union = create.object("som_battlefield_gk_5", corpseLoc);
            playClientEffectLoc(union, trial.PRT_DROID_REVIVE, corpseLoc, 4.0f);
            attachScript(union, "theme_park.dungeon.mustafar_trials.valley_battleground.assault_killer_bot");
            messageTo(union, "pathToNextPoint", null, 3, false);
            utils.setScriptVar(union, "patrolPoints", patrolPoints);
            destroyObject(corpse);
            trial.markAsDroidArmy(union);
            return;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VALLEY_LOGGING)
        {
            LOG("logging/forward_commander/" + section, message);
        }
    }
}
