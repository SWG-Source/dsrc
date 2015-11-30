package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.utils;

public class ai_combat_movement extends script.base_script
{
    public ai_combat_movement()
    {
    }
    public static final int MOVEMODE_INVALID = 0;
    public static final int MOVEMODE_FOLLOW = 1;
    public static final int MOVEMODE_RANDOM = 2;
    public static final int MOVEMODE_PREPARING_TO_MOVE = 3;
    public static final int MOVEMODE_PATHING_HOME = 4;
    public static final int MOVEMODE_PREPARING_TO_STAND = 5;
    public static final int MOVEMODE_MOVING_TO_HEAL = 7;
    public static final int MOVEMODE_IDLE = 9;
    public static final int MOVEMODE_FLEE = 10;
    public static final int MOVEMODE_FOLLOW_FORMATION = 11;
    public static final int MOVEMODE_MOVING_TO_SEE = 12;
    public static final int MOVEMODE_MOVING_TO_DEATHBLOW = 13;
    public static final int MOVEMODE_SWARM = 14;
    public static final int MOVEMOVE_EVADE = 15;
    public static void aiClearMoveMode() throws InterruptedException
    {
        final obj_id self = getSelf();
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_INVALID);
    }
    public static void aiIdle() throws InterruptedException
    {
        if (aiIsIdle())
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_IDLE);
        stop(self);
    }
    public static boolean aiIsIdle() throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        return (dict.getInt("ai.combat.moveMode") == MOVEMODE_IDLE);
    }
    public static void aiMoveToSee(obj_id target) throws InterruptedException
    {
        if (aiIsMovingToSee(target))
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_MOVING_TO_SEE);
        dict.put("ai.combat.moveToSee.target", target);
        pathTo(self, getLocation(target));
    }
    public static boolean aiIsMovingToSee(obj_id target) throws InterruptedException
    {
        if (aiIsMovingToSee() && aiGetMoveToSeeTarget() == target)
        {
            return true;
        }
        return false;
    }
    public static boolean aiIsMovingToSee() throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        return (dict.getInt("ai.combat.moveMode") == MOVEMODE_MOVING_TO_SEE);
    }
    public static obj_id aiGetMoveToSeeTarget() throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        return dict.getObjId("ai.combat.moveToSee.target");
    }
    public static void aiMoveToDeathBlow(obj_id target) throws InterruptedException
    {
        if (aiIsMovingToDeathBlow(target) || !isIncapacitated(target))
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        LOGC(aiLoggingEnabled(self) && !isIncapacitated(target), "debug_ai", "ai_combat_movement::aiMoveToDeathBlow() self(" + self + ":" + getName(self) + ") target(" + target + ") Trying to death blow a non-incapacitated target.");
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_MOVING_TO_DEATHBLOW);
        dict.put("ai.combat.moveToDeathBlow.target", target);
        pathTo(self, getLocation(target));
    }
    public static boolean aiIsMovingToDeathBlow(obj_id target) throws InterruptedException
    {
        if ((aiIsMovingToDeathBlow()) && (aiGetMoveToDeathBlowTarget() == target))
        {
            return true;
        }
        return false;
    }
    public static boolean aiIsMovingToDeathBlow() throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        return (dict.getInt("ai.combat.moveMode") == MOVEMODE_MOVING_TO_DEATHBLOW);
    }
    public static obj_id aiGetMoveToDeathBlowTarget() throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        return dict.getObjId("ai.combat.moveToDeathBlow.target");
    }
    public static void aiFollow(obj_id target, float minDistance, float maxDistance) throws InterruptedException
    {
        if (aiIsFollowing(target, minDistance, maxDistance))
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_FOLLOW);
        dict.put("ai.combat.follow.target", target);
        dict.put("ai.combat.follow.minDistance", minDistance);
        dict.put("ai.combat.follow.maxDistance", maxDistance);
        follow(self, target, minDistance, maxDistance);
    }
    public static void aiSwarm(obj_id target, float minDistance, float maxDistance) throws InterruptedException
    {
        if (aiIsSwarming(target, minDistance, maxDistance))
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_SWARM);
        dict.put("ai.combat.follow.target", target);
        dict.put("ai.combat.follow.minDistance", minDistance);
        dict.put("ai.combat.follow.maxDistance", maxDistance);
        swarm(self, target);
    }
    public static boolean aiIsFollowing(obj_id target, float minDistance, float maxDistance) throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        if ((dict.getInt("ai.combat.moveMode") == MOVEMODE_FOLLOW) && (dict.getObjId("ai.combat.follow.target") == target) && (dict.getFloat("ai.combat.follow.minDistance") == minDistance) && (dict.getFloat("ai.combat.follow.maxDistance") == maxDistance))
        {
            return true;
        }
        return false;
    }
    public static boolean aiIsSwarming(obj_id target, float minDistance, float maxDistance) throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        if ((dict.getInt("ai.combat.moveMode") == MOVEMODE_SWARM) && (dict.getObjId("ai.combat.follow.target") == target) && (dict.getFloat("ai.combat.follow.minDistance") == minDistance) && (dict.getFloat("ai.combat.follow.maxDistance") == maxDistance))
        {
            return true;
        }
        return false;
    }
    public static float getActualEvadeChance(obj_id ai, float min, float max) throws InterruptedException
    {
        float rslt = max;
        float difference = max - min;
        float maxHealth = (float)getMaxHealth(ai);
        float curHealth = (float)getHealth(ai);
        rslt -= (difference * (curHealth / maxHealth));
        return rslt;
    }
    public static boolean aiEvade(obj_id target, obj_id ai, range_info aiWeaponRanges, float minChance, float maxChance) throws InterruptedException
    {
        obj_id primaryWeapon = aiGetPrimaryWeapon(ai);
        obj_id secondaryWeapon = aiGetSecondaryWeapon(ai);
        float primaryRange = 0;
        float secondaryRange = 0;
        obj_id master = getMaster(ai);
        if (isIdValid(master))
        {
            return false;
        }
        if (hasScript(ai, "systems.dungeon_sequencer.ai_controller"))
        {
            return false;
        }
        if (isIdValid(primaryWeapon) && exists(primaryWeapon))
        {
            final range_info primaryWeaponRange = aiGetWeaponRangeInfo(primaryWeapon);
            primaryRange = primaryWeaponRange.maxRange;
        }
        if (isIdValid(secondaryWeapon) && exists(secondaryWeapon))
        {
            final range_info secondaryWeaponRange = aiGetWeaponRangeInfo(secondaryWeapon);
            secondaryRange = secondaryWeaponRange.maxRange;
        }
        if (primaryRange <= 10f && secondaryRange <= 10f)
        {
            return false;
        }
        final int now = getGameTime();
        final int then = utils.getIntScriptVar(ai, "aiIsEvading");
        if (then != 0 && (now - then) < 15)
        {
            return false;
        }
        float minDistance = 0;
        float maxDistance = 1f;
        if (getTopMostContainer(ai) == ai)
        {
            minDistance = 15f;
            maxDistance = 20f;
        }
        else 
        {
            minDistance = 4f;
            maxDistance = 10f;
        }
        if (isPlayer(target))
        {
            float mod = 0;
            range_info playerRange = getWeaponRangeInfo(getCurrentWeapon(target));
            if (playerRange != null && playerRange.maxRange < 10)
            {
                if (minChance == 1f)
                {
                    mod = 0.985f;
                }
                else 
                {
                    mod = 0.9f;
                }
                minDistance = 5f;
                if (rand(1, 100) < 30)
                {
                    maxDistance = playerRange.maxRange + 5;
                }
                else 
                {
                    maxDistance = playerRange.maxRange - 1;
                }
                if (minDistance > maxDistance)
                {
                    minDistance = maxDistance;
                }
            }
            maxChance -= (maxChance * mod);
            if (minChance > maxChance)
            {
                minChance = maxChance;
            }
        }
        float chance = getActualEvadeChance(ai, minChance, maxChance);
        if (chance < 1f && rand(0.0f, 1.0f) > chance)
        {
            return false;
        }
        location evasionTargetLoc = getValidAwayLocation(target, minDistance, maxDistance);
        if (evasionTargetLoc == null || getLocation(ai).cell != evasionTargetLoc.cell)
        {
            return false;
        }
        if (!canSee(ai, evasionTargetLoc))
        {
            return false;
        }
        utils.setScriptVar(ai, "aiIsEvading", getGameTime());
        pathTo(ai, evasionTargetLoc);
        faceTo(ai, target);
        return true;
    }
    public static location getValidAwayLocation(obj_id mob, float minDistance, float maxDistance) throws InterruptedException
    {
        location from = getLocation(mob);
        location evasionTarget = utils.getRandomAwayLocation(from, minDistance, maxDistance);
        evasionTarget.y += 0.5f;
        if (!canSee(mob, evasionTarget))
        {
            return null;
        }
        evasionTarget.y -= 0.5f;
        return evasionTarget;
    }
    public static void aiFlee(obj_id target, float distance) throws InterruptedException
    {
        if (aiIsFleeing(target, distance))
        {
            return;
        }
        final obj_id self = getSelf();
        if (aiGetMovementState(self) == MOVEMENT_PATROL && !hasSuspendedMovement(self))
        {
            suspendMovement(self);
        }
        deltadictionary dict = self.getScriptVars();
        dict.put("ai.combat.moveMode", MOVEMODE_FLEE);
        dict.put("ai.combat.flee.target", target);
        dict.put("ai.combat.flee.distance", distance);
        flee(self, target, distance, distance);
        messageTo(self, "redoYaw", null, 30, false);
    }
    public static boolean aiIsFleeing(obj_id ai) throws InterruptedException
    {
        final deltadictionary dict = ai.getScriptVars();
        return dict.getInt("ai.combat.moveMode") == MOVEMODE_FLEE;
    }
    public static boolean aiIsFleeing(obj_id target, float distance) throws InterruptedException
    {
        final obj_id self = getSelf();
        final deltadictionary dict = self.getScriptVars();
        if ((dict.getInt("ai.combat.moveMode") == MOVEMODE_FLEE) && (dict.getObjId("ai.combat.flee.target") == target) && (dict.getFloat("ai.combat.flee.distance") == distance))
        {
            return true;
        }
        return false;
    }
    public static boolean isAiImmobile(obj_id subject) throws InterruptedException
    {
        return hasObjVar(subject, "isImmobile") ? getBooleanObjVar(subject, "isImmobile") : false;
    }
}
