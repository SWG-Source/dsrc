package script.city;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.location;
import script.obj_id;

public class guard_wander extends script.city.base.base_wander
{
    public static final String[] patrolPoints =
            {
                    "patrol1",
                    "patrol2",
                    "patrol3",
                    "patrol4",
                    "patrol5",
                    "patrol6"
            };
    public guard_wander()
    {
        super.patrolPoints = patrolPoints;
    }

    public static final int PATH_VERIFICATION_TIME = 30;
    public static final int PATH_FAILURES_MAXIMUM = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.OnAttach enter");
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        utils.setScriptVar(self, "path.pathFailures", 0);
        removeObjVar(self, "combat.intCombatXP");
        return super.OnAttach(self);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.OnInitialize enter name: " + getName(self));
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        utils.setScriptVar(self, "path.pathFailures", 0);
        return super.OnInitialize(self);
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.OnLoiterMoving enter name: " + getName(self));
        return super.OnLoiterMoving(self);
    }
    public int pathRandom(obj_id self, dictionary params) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.pathRandom enter name: " + getName(self));
        params = new dictionary();
        params.put("pathVerifyTime", getGameTime());
        messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
        if (hasObjVar(self, "ai.inFormation"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.pathRandom setting patrol points");
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
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.pathVerify enter name: " + getName(self));
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
            utils.setScriptVar(self, "path.timeCompleted", getGameTime());
            utils.setScriptVar(self, "path.pathFailures", 0);
            LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.pathVerify() found the distance between now and last check to be " + distance + " meters. name: " + getName(self));
            messageTo(self, "pathVerify", params, PATH_VERIFICATION_TIME - 1, false);
            return SCRIPT_CONTINUE;
        }
        if (pathFailures > PATH_FAILURES_MAXIMUM)
        {
            LOG("guard_wander", "Patrol pathfinding failures exceeded (5). Stopping self: " + self + " getName: " + getName(self) + " at " + getLocation(self));
            stop(self);
            utils.setScriptVar(self, "path.timeCompleted", getGameTime());
            utils.setScriptVar(self, "path.pathFailures", 0);
            messageTo(self, "pathRandom", params, 600, false);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.inFormation"))
        {
            ai_lib.resumeFormationFollowing(self);
            return SCRIPT_CONTINUE;
        }
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.pathVerify() messaging pathRandom() for " + getName(self) + " pathFailures: " + pathFailures);
        pathFailures++;
        utils.setScriptVar(self, "path.pathFailures", pathFailures);
        messageTo(self, "pathRandom", params, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.OnMovePathComplete enter name: " + getName(self));
        utils.setScriptVar(self, "path.timeCompleted", getGameTime());
        utils.setScriptVar(self, "path.pathFailures", 0);
        messageTo(self, "pathRandom", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "guard_wander.OnMovePathNotFound enter name: " + getName(self));
        messageTo(self, "pathRandom", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("GO") && isGod(speaker))
        {
            messageTo(self, "pathRandom", null, 3, false);
        }
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
