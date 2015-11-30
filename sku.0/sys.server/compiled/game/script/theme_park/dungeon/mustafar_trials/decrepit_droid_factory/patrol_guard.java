package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;

public class patrol_guard extends script.base_script
{
    public patrol_guard()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "cleanup"))
        {
            obj_id top = getTopMostContainer(self);
            String datatable = getStringObjVar(top, "spawn_table");
            obj_id mom = getObjIdObjVar(self, "mom");
            if (mom == null)
            {
                return SCRIPT_OVERRIDE;
            }
            int spawnNum = getIntObjVar(self, "spawn_number");
            int respawnTime = dataTableGetInt(datatable, spawnNum, "respawn_time");
            if (respawnTime == 0)
            {
                respawnTime = 30;
            }
            dictionary info = new dictionary();
            info.put("spawnNumber", spawnNum);
            info.put("spawnMob", self);
            messageTo(mom, "tellingMomIDied", info, respawnTime, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "locatePatrolPoints", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public boolean isWithinHeightDifference(location selfLoc, location targetLoc) throws InterruptedException
    {
        float heightDifference = Math.abs(targetLoc.y - selfLoc.y);
        if (heightDifference < 6)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/patrol_guard/" + section, message);
        }
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int locatePatrolPoints(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = utils.getSharedContainerObjects(self);
        if (objects == null || objects.length == 0)
        {
            doLogging("locatePatrolPoints", "There are no objects in the shared cell");
            return SCRIPT_CONTINUE;
        }
        String patrolPath = getStringObjVar(self, "patrol_path");
        String[] parse = split(patrolPath, ';');
        location[] patrolPoints = new location[parse.length];
        for (int i = 0; i < parse.length; i++)
        {
            for (int k = 0; k < objects.length; k++)
            {
                if (hasObjVar(objects[k], "patrol_wp"))
                {
                    if (parse[i].equals(getStringObjVar(objects[k], "patrol_wp")))
                    {
                        patrolPoints[i] = getLocation(objects[k]);
                    }
                }
            }
        }
        if (patrolPoints == null || patrolPoints.length == 0)
        {
            doLogging("locatePatrolPoints", "No patrol points could be found");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, "patrolPoints", patrolPoints);
        messageTo(self, "pathToNextPoint", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int pathToNextPoint(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "patrolPoints"))
        {
            doLogging("pathToNextPoint", "I do not have hte patrolArray objVar");
        }
        location[] patrolPoints = getLocationArrayObjVar(self, "patrolPoints");
        patrol(self, patrolPoints);
        return SCRIPT_CONTINUE;
    }
}
