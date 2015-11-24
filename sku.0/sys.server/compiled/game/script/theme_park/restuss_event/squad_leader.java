package script.theme_park.restuss_event;

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
import script.library.create;
import script.library.restuss_event;

public class squad_leader extends script.base_script
{
    public squad_leader()
    {
    }
    public static final boolean LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!restuss_event.getIsStatic(self))
        {
            findWayPoints(self);
            messageTo(self, "pathToNextPoint", null, 2, false);
        }
        trial.setInterest(self);
        messageTo(self, "spawnSquad", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int spawnSquad(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] squad = new obj_id[4];
        location baseLoc = getLocation(self);
        String squadMember = "none";
        if (hasObjVar(self, "squad_member"))
        {
            squadMember = getStringObjVar(self, "squad_member");
        }
        if (squadMember.equals("none"))
        {
            return SCRIPT_CONTINUE;
        }
        if (dataTableGetString("datatables/mob/creatures.iff", squadMember, "creatureName") == null)
        {
            doLogging("spawnSquad", "Creature(" + squadMember + ") was not in the creatures datatable");
            return SCRIPT_CONTINUE;
        }
        boolean isStatic = restuss_event.getIsStatic(self);
        for (int i = 0; i < squad.length; i++)
        {
            location spawnLoc = new location(baseLoc.x + 20, baseLoc.y, baseLoc.z, baseLoc.area, baseLoc.cell);
            squad[i] = create.object(squadMember, spawnLoc);
            if (isIdValid(squad[i]))
            {
                attachScript(squad[i], "theme_park.restuss_event.squad_member");
                utils.setScriptVar(squad[i], trial.PARENT, self);
                ai_lib.followInFormation(squad[i], self, ai_lib.FORMATION_WEDGE, i);
                utils.setScriptVar(squad[i], restuss_event.PATROL_POINTS, utils.getLocationArrayScriptVar(self, restuss_event.PATROL_POINTS));
                trial.markAsTempObject(squad[i], true);
            }
        }
        if (isStatic)
        {
            ai_lib.establishAgroLink(self, squad);
        }
        else 
        {
            for (int j = 0; j < squad.length; j++)
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void findWayPoints(obj_id self) throws InterruptedException
    {
        Vector wayPoints = utils.getResizeableObjIdArrayScriptVar(self, restuss_event.MASTER_PATROL_ARRAY);
        if (wayPoints == null || wayPoints.size() == 0)
        {
            doLogging("findWayPoints", "Waypoint list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        int pathNum = 0;
        if (hasObjVar(self, "path"))
        {
            pathNum = getIntObjVar(self, "path");
        }
        else 
        {
            doLogging("findWayPoints", "I did not have the path ObjVar");
            trial.cleanupObject(self);
            return;
        }
        String path = dataTableGetString(restuss_event.STAGE_TWO_DATA, pathNum, "path");
        String[] pathList = split(path, ';');
        if (pathList == null || pathList.length == 0)
        {
            doLogging("findWayPoints", "Path list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        Vector myPath = new Vector();
        myPath.setSize(0);
        for (int i = 0; i < pathList.length; i++)
        {
            for (int k = 0; k < wayPoints.size(); k++)
            {
                if (hasObjVar(((obj_id)wayPoints.get(k)), "wp_name"))
                {
                    if (pathList[i].equals(getStringObjVar(((obj_id)wayPoints.get(k)), "wp_name")))
                    {
                        utils.addElement(myPath, getLocation(((obj_id)wayPoints.get(k))));
                    }
                }
            }
        }
        if (myPath == null)
        {
            doLogging("findWayPoints", "No waypoints were found, exiting");
            trial.cleanupObject(self);
            return;
        }
        location[] patrolPoints = new location[0];
        if (myPath != null)
        {
            patrolPoints = new location[myPath.size()];
            myPath.toArray(patrolPoints);
        }
        if (patrolPoints.length == 0)
        {
            doLogging("findWayPoints", "Patrol Point list was empty, exiting");
            trial.cleanupObject(self);
            return;
        }
        utils.setScriptVar(self, restuss_event.PATROL_POINTS, patrolPoints);
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        location[] patrolPoints = utils.getLocationArrayScriptVar(self, restuss_event.PATROL_POINTS);
        int patrolType = getIntObjVar(self, restuss_event.PATROL_TYPE);
        switch (patrolType)
        {
            case restuss_event.PATROL:
            ai_lib.setPatrolPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_ONCE:
            ai_lib.setPatrolOncePath(self, patrolPoints);
            break;
            case restuss_event.PATROL_FLIP:
            ai_lib.setPatrolFlipPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_FLIP_ONCE:
            ai_lib.setPatrolFlipOncePath(self, patrolPoints);
            break;
            case restuss_event.PATROL_RANDOM:
            ai_lib.setPatrolRandomPath(self, patrolPoints);
            break;
            case restuss_event.PATROL_RANDOM_ONCE:
            ai_lib.setPatrolRandomOncePath(self, patrolPoints);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("doLogging/event_squad_leader/" + section, message);
        }
    }
}
