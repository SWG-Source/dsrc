package script.city;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.location;
import script.obj_id;

public class droid_wander extends script.city.base.base_wander
{
    public droid_wander()
    {
        super.patrolPoints = patrolPoints;
    }
    public final String[] patrolPoints =
    {
        "droid1",
        "droid2",
        "droid3",
        "droid4",
        "droid5",
        "droid6"
    };
    public static final int PATH_VERIFICATION_TIME = 30;
    public static final int PATH_FAILURES_MAXIMUM = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.OnAttach enter");
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        utils.setScriptVar(self, "path.pathFailures", 0);
        return super.OnAttach(self);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.OnInitialize enter name: " + getName(self));
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        utils.setScriptVar(self, "path.pathFailures", 0);
        return super.OnAttach(self);
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.OnLoiterMoving enter name: " + getName(self));
        return super.OnLoiterMoving(self);
    }
    public int pathRandom(obj_id self, dictionary params) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.pathRandom enter name: " + getName(self));
        params = new dictionary();
        params.put("pathVerifyTime", getGameTime());
        messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
        if (hasObjVar(self, "ai.inFormation"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.pathRandom setting patrol points");
            ai_lib.setPatrolRandomNamedPath(self, patrolPoints);
        }
        return SCRIPT_CONTINUE;
    }
    public int pathVerify(obj_id self, dictionary params) throws InterruptedException
    {
        if (aiGetMovementState(self) != MOVEMENT_PATROL || hasCondition(self, CONDITION_HIBERNATING))
        {
            return SCRIPT_CONTINUE;
        }
        int timeCompleted = utils.getIntScriptVar(self, "path.timeCompleted");
        int lastVerify = utils.getIntScriptVar(self, "path.pathVerifyTime");
        int pathFailures = utils.getIntScriptVar(self, "path.pathFailures");

        location lastPathLocation = utils.getLocationScriptVar(self, "path.lastLocation");

        int gameTime = getGameTime();
        int pathVerifyTime = params.getInt("pathVerifyTime");
        String name = getName(self);

        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.pathVerify enter - name: " + name);
        if (lastVerify > pathVerifyTime)
        {
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "path.pathVerifyTime", pathVerifyTime);

        params = new dictionary();

        params.put("pathVerifyTime", gameTime);

        if (timeCompleted + PATH_VERIFICATION_TIME > gameTime)
        {
            messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
            return SCRIPT_CONTINUE;
        }

        float distance = utils.getDistance2D(getLocation(self), lastPathLocation);

        utils.setScriptVar(self, "path.lastLocation", getLocation(self));

        if (distance > 1.0f)
        {
            utils.setScriptVar(self, "path.timeCompleted", gameTime);
            LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.pathVerify() found the distance between now and last check to be " + distance + " meters. name: " + name);
            messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
            return SCRIPT_CONTINUE;
        }
        if (pathFailures > PATH_FAILURES_MAXIMUM)
        {
            LOG("droid_wander", "Patrol pathfinding failures exceeded (5). Stopping self: " + self + " getName: " + name + " at " + getLocation(self));
            stop(self);
            utils.setScriptVar(self, "path.timeCompleted", getGameTime());
            messageTo(self, "pathRandom", params, 600, false);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.inFormation"))
        {
            ai_lib.resumeFormationFollowing(self);
            return SCRIPT_CONTINUE;
        }
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.pathVerify() messaging pathRandom() for " + name + " pathFailures: " + pathFailures);
        pathFailures++;
        utils.setScriptVar(self, "path.pathFailures", pathFailures);
        messageTo(self, "pathRandom", params, 3, false);

        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.OnMovePathComplete enter name: " + getName(self));
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        return super.OnMovePathComplete(self);
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "droid_wander.OnMovePathNotFound enter name: " + getName(self));
        messageTo(self, "pathRandom", null, rand(30, 60), false);
        return SCRIPT_CONTINUE;
    }
    public int OnHibernateEnd(obj_id self) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("pathVerifyTime", getGameTime());
        messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
        return SCRIPT_CONTINUE;
    }
}
