package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.buff;
import script.library.chat;
import script.library.create;
import script.library.groundquests;
import script.library.group;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class boss_fight_functionality extends script.base_script
{
    public boss_fight_functionality()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_trigger";
    public static final String FAIL_SIGNAL = "fail_signal";
    public static final String DISTANCE_CHECK = "distance_check";
    public static final String WAVE_GROUP_ID = "waveEventGroupId";
    public static final int RADIUS = 100;
    public static final int MIN_DIST = 50;
    public static final string_id BOSS_LEAVING = new string_id("theme_park/outbreak/outbreak", "boss_leaving");
    public static final string_id BOSS_BEGIN = new string_id("theme_park/outbreak/outbreak", "boss_begin");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnAttach() trigger initialized.");
        moveCreatureToWaypoint(self);
        trial.setHp(self, trial.HP_UNCLE_JOE);
        applySkillStatisticModifier(self, "expertise_glancing_blow_reduction", 100);
        messageTo(self, "warnPlayerTimerBegin", null, 1, false);
        messageTo(self, "warnPlayerTimerEnd", null, 540, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitated() Boss Mob " + self + " incapacitated by: " + killer);
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitated() Mob did not find the player owner.");
            return SCRIPT_CONTINUE;
        }
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitated() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] groupMembersInRange = groundquests.getGroupMembersInRange(parent, playerEnemy);
        if (groupMembersInRange == null || groupMembersInRange.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "rancor_boss_fight_controller.OnIncapacitated() groupMembersInRange was NULL. for player: " + playerEnemy);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < groupMembersInRange.length; i++)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitated() A player, " + groupMembersInRange[i] + ", was found in player group of playerEnemy: " + playerEnemy);
            if (!isIdValid(groupMembersInRange[i]) && !exists(groupMembersInRange[i]) || isIncapacitated(groupMembersInRange[i]) || !isDead(groupMembersInRange[i]))
            {
                CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitated() A player, " + groupMembersInRange[i] + ", in the group of " + playerEnemy + " was dead, incapacitated or in some way not found.");
                continue;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.setDynamicDataOnMob() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        if (getRandomCombatTarget(self, parent))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitateTarget() Boss Mob has found someone in the player's group.");
            return SCRIPT_CONTINUE;
        }
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (ai_lib.isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnExitedCombat() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        if (getRandomCombatTarget(self, parent))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "open_balance_buff", -1.0f);
        int maxDist = getIntObjVar(self, DISTANCE_CHECK);
        if (maxDist <= 0)
        {
            maxDist = MIN_DIST;
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.handleBossDistanceCheck() maximum distance from creation location for boss mob (" + self + ") was invalid or NULL.");
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() Mob found player but player was not part of owners group.");
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = getPlayerCreaturesInRange(self, maxDist);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (!isIdValid(players[i]) && !exists(players[i]) || isIncapacitated(players[i]) || !isDead(players[i]))
                {
                    continue;
                }
                if (!group.inSameGroup(players[i], playerEnemy))
                {
                    continue;
                }
                addHate(self, players[i], 1000.0f);
                startCombat(self, players[i]);
            }
        }
        messageTo(self, "handleBossDistanceCheck", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handleBossDistanceCheck(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        location currentLoc = getLocation(self);
        if (currentLoc == null)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.handleBossDistanceCheck() current boss mob (" + self + ") location NULL.");
            return SCRIPT_CONTINUE;
        }
        location creationLoc = aiGetHomeLocation(self);
        if (creationLoc == null)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.handleBossDistanceCheck() creation location for boss mob (" + self + ") was NULL.");
            return SCRIPT_CONTINUE;
        }
        float distanceCheck = utils.getDistance2D(currentLoc, creationLoc);
        int maxDist = getIntObjVar(self, DISTANCE_CHECK);
        if (maxDist <= 0)
        {
            maxDist = MIN_DIST;
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.handleBossDistanceCheck() maximum distance from creation location for boss mob (" + self + ") was invalid or NULL.");
        }
        obj_id parent = getObjIdObjVar(self, trial.PARENT);
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("quest", "dynamic_mob_opponent.OnExitedCombat() FAILED to find parent object.");
            return SCRIPT_CONTINUE;
        }
        if (distanceCheck > maxDist)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.handleBossDistanceCheck() boss mob (" + self + ") has moved too far from creation location. Moving back to creation location.");
            dictionary webster = trial.getSessionDict(parent);
            messageTo(parent, "defaultEventReset", webster, 2, false);
        }
        else 
        {
            messageTo(self, "handleBossDistanceCheck", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean moveCreatureToWaypoint(obj_id creature) throws InterruptedException
    {
        if (!isValidId(creature) || !exists(creature))
        {
            return false;
        }
        String creatureType = getStringObjVar(creature, "creature_type");
        if (creatureType == null || creatureType.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.moveCreatureToWaypoint() the boss creature_type was not valid");
            return false;
        }
        CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.moveCreatureToWaypoint() the boss creature_type is: " + creatureType);
        obj_id[] wayPointList = getAllObjectsWithObjVar(getLocation(creature), RADIUS, creatureType);
        if (wayPointList == null || wayPointList.length <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.moveCreatureToWaypoint() the boss creature failed to find any objects within " + RADIUS + " of " + getLocation(creature));
            return false;
        }
        location wyPt = getLocation(wayPointList[0]);
        setLocation(creature, wyPt);
        aiSetHomeLocation(creature, wyPt);
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.moveCreatureToWaypoint() unable to get a location for the boss creature: " + creature);
        return true;
    }
    public boolean getRandomCombatTarget(obj_id self, obj_id parent) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() FAILED to find self.");
            return false;
        }
        if (!isIdValid(parent) || !exists(parent))
        {
            destroyObject(self);
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() FAILED to find parent object.");
            return false;
        }
        int maxDist = getIntObjVar(self, DISTANCE_CHECK);
        if (maxDist <= 0)
        {
            maxDist = MIN_DIST;
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() maximum distance from creation location for boss mob (" + self + ") was invalid or NULL.");
        }
        obj_id[] targets = trial.getValidTargetsInRadiusIgnoreLOS(self, maxDist);
        if (targets == null || targets.length == 0)
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() no targets for boss (" + self + ") is: " + maxDist);
            dictionary webster = trial.getSessionDict(parent);
            messageTo(parent, "defaultEventReset", webster, 2, false);
            return false;
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget() Mob found player but player was not part of owners group.");
            return false;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (!isIdValid(targets[i]))
            {
                continue;
            }
            if (!group.inSameGroup(targets[i], playerEnemy))
            {
                CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget(): Player " + targets[i] + " was found as an invalid target.");
                continue;
            }
            startCombat(self, targets[i]);
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.getRandomCombatTarget(): Player " + targets[i] + " was found as a valid target. Boss Mob: " + self + " attacking player.");
            return true;
        }
        CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.OnIncapacitateTarget(): Boss Mob: " + self + " failed to find a player to attack from player: " + playerEnemy + " group.");
        dictionary webster = trial.getSessionDict(parent);
        messageTo(parent, "defaultEventReset", webster, 2, false);
        return false;
    }
    public int warnPlayerTimerBegin(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.warnPlayerTimerBegin() Mob found player but player was not part of owners group.");
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(playerEnemy))
        {
            obj_id groupId = group.getGroupObject(playerEnemy);
            if (!isValidId(groupId) || !exists(groupId))
            {
                CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.warnPlayerTimerBegin() No player group found.");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(groupId);
            for (int x = 0; x < objMembersWhoExist.length; x++)
            {
                sendSystemMessage(objMembersWhoExist[x], BOSS_BEGIN);
            }
        }
        else 
        {
            sendSystemMessage(playerEnemy, BOSS_BEGIN);
        }
        return SCRIPT_CONTINUE;
    }
    public int warnPlayerTimerEnd(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id playerEnemy = utils.getObjIdScriptVar(self, "waveEventPlayer");
        if (!isIdValid(playerEnemy) || !exists(playerEnemy))
        {
            CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.warnPlayerTimerEnd() Mob found player but player was not part of owners group.");
            return SCRIPT_CONTINUE;
        }
        if (group.isGrouped(playerEnemy))
        {
            obj_id groupId = group.getGroupObject(playerEnemy);
            if (!isValidId(groupId) || !exists(groupId))
            {
                CustomerServiceLog("outbreak_themepark", "boss_fight_functionality.warnPlayerTimerEnd() No player group found.");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objMembersWhoExist = utils.getLocalGroupMemberIds(groupId);
            for (int x = 0; x < objMembersWhoExist.length; x++)
            {
                sendSystemMessage(objMembersWhoExist[x], BOSS_LEAVING);
            }
        }
        else 
        {
            sendSystemMessage(playerEnemy, BOSS_LEAVING);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
