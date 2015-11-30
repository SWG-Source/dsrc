package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.fs_dyn_village;
import script.library.ai_lib;
import script.library.turret;
import script.library.utils;
import script.library.fs_counterstrike;
import script.library.trace;
import script.library.chat;

public class fs_camp_guard_ai extends script.base_script
{
    public fs_camp_guard_ai()
    {
    }
    public static final String VAR_SPAWNED_BY = "quest_spawner.spawned_by";
    public void setPatrolPaths() throws InterruptedException
    {
        obj_id self = getSelf();
        if (!utils.hasScriptVar(self, "patrolTargets"))
        {
            destroyObject(self);
            return;
        }
        if (ai_lib.isInCombat(self))
        {
            messageTo(self, "tryPathAgain", null, 30.0f, false);
            return;
        }
        location[] entries = utils.getLocationArrayScriptVar(self, "patrolTargets");
        int winner = 0;
        location here = getLocation(self);
        for (int i = 0; i < entries.length; i++)
        {
            if (here.distance(entries[i]) < here.distance(entries[winner]))
            {
                winner = i;
            }
        }
        location targetLocation = entries[winner];
        setHomeLocation(self, targetLocation);
        setMovementRun(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        trace.log(fs_dyn_village.LOG_CHAN, "Sending camp defenders to " + targetLocation.toString());
        ai_lib.aiPathTo(self, targetLocation);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgSpoutOff", null, (float)rand(10, 30), false);
        setObjVar(self, turret.OBJVAR_TURRET_FRIEND, 1);
        messageTo(self, "msgSelfDestruct", null, (float)rand(110, 130), false);
        if (hasObjVar(self, VAR_SPAWNED_BY))
        {
            obj_id parent = getObjIdObjVar(self, VAR_SPAWNED_BY);
            if (isIdValid(parent))
            {
                obj_id campId = fs_counterstrike.getMyOutpostId(parent);
                if (isIdValid(campId))
                {
                    setObjVar(self, fs_counterstrike.OBJVAR_MY_CAMP_ID, campId);
                    location[] patrolLocs = new location[2];
                    if (hasObjVar(campId, fs_counterstrike.OBJVAR_CAMP_DOOR1_LOC))
                    {
                        patrolLocs[0] = getLocationObjVar(campId, fs_counterstrike.OBJVAR_CAMP_DOOR1_LOC);
                    }
                    if (hasObjVar(campId, fs_counterstrike.OBJVAR_CAMP_DOOR2_LOC))
                    {
                        patrolLocs[1] = getLocationObjVar(campId, fs_counterstrike.OBJVAR_CAMP_DOOR2_LOC);
                    }
                    location campLoc = getLocation(campId);
                    float z = getLocation(campId).z;
                    if (patrolLocs[0] != null)
                    {
                        z = patrolLocs[0].z;
                    }
                    if (patrolLocs[1] != null && patrolLocs[1].z > z)
                    {
                        z = patrolLocs[1].z;
                    }
                    z += 15;
                    utils.setScriptVar(self, "patrolTargets", new location[]
                    {
                        new location(campLoc.x, campLoc.y, z)
                    });
                    setPatrolPaths();
                }
            }
        }
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int msgSpoutOff(obj_id self, dictionary params) throws InterruptedException
    {
        if (rand(0, 100) > 75)
        {
            String file = "fs_quest_village";
            string_id id = new string_id(file, "camp_defender_" + rand(1, 10));
            chat.chat(self, id);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        obj_id camp = fs_counterstrike.getMyOutpostId(self);
        location home = getHomeLocation(camp);
        location here = getLocation(self);
        if (home.distance(here) < 35.0)
        {
            final float minDistance = 10.0f;
            final float maxDistance = 30.0f;
            final float minDelay = 3.0f;
            final float maxDelay = 8.0f;
            loiterLocation(self, here, minDistance, maxDistance, minDelay, maxDelay);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int tryPathAgain(obj_id self, dictionary params) throws InterruptedException
    {
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("fs_quest", "resumeDefaultCalmBehavior");
        setPatrolPaths();
        return SCRIPT_CONTINUE;
    }
    public int msgSelfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_OVERRIDE;
    }
}
