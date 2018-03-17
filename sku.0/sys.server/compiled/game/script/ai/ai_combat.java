package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;
import script.library.colors;
import script.library.combat;
import script.library.combat_consts;
import script.library.dot;
import script.library.factions;
import script.library.group;
import script.library.locations;
import script.library.pet_lib;
import script.library.pet_lib;
import script.library.posture;
import script.library.scout;
import script.library.utils;

public class ai_combat extends script.base_script
{
    public ai_combat()
    {
    }
    public static void doAttack(obj_id ai, obj_id target) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_combat::doAttack() ai(" + ai + ")");
        startCombat(ai, target);
    }
    public static void attackBestTarget(obj_id ai, obj_id target) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_combat::attackBestTarget() ai(" + ai + ")");
        startCombat(ai, target);
    }
    public static void equipRangedWeapon(obj_id ai) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_combat::equipRangedWeapon() ai(" + ai + ")");
        aiEquipPrimaryWeapon(ai);
    }
    public static void equipMeleeWeapon(obj_id ai) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(ai), "debug_ai", "ai_combat::equipMeleeWeapon() ai(" + ai + ")");
        aiEquipPrimaryWeapon(ai);
    }
    public static boolean goodFaction(obj_id npc, obj_id target) throws InterruptedException
    {
        PROFILER_START("goodFaction");
        if (!pvpCanAttack(npc, target))
        {
            PROFILER_STOP("goodFaction");
            return true;
        }
        if (isPlayer(target))
        {
            PROFILER_STOP("goodFaction");
            return false;
        }
        else 
        {
            String npcFaction = factions.getFaction(npc);
            String targetFaction = factions.getFaction(target);
            if (npcFaction != null && targetFaction != null)
            {
                if (npcFaction.equals(targetFaction))
                {
                    PROFILER_STOP("goodFaction");
                    return true;
                }
            }
        }
        if (factions.getFactionReaction(npc, target) == factions.REACTION_POSITIVE)
        {
            PROFILER_STOP("goodFaction");
            return true;
        }
        PROFILER_STOP("goodFaction");
        return false;
    }
    public static boolean isGoodTarget(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (ai_lib.isAiDead(target))
        {
            if (isPlayer(target))
            {
                listenToMessage(target, "handleSawRecapacitation");
                if (isObjectPersisted(npc))
                {
                    dictionary parms = new dictionary();
                    parms.put("player", target);
                    messageTo(npc, "handleStopListening", parms, 1200, false);
                }
            }
            return false;
        }
        if (!exists(target) || getDistance(npc, target) > combat_engine.getMaxCombatRange() || !isInWorld(target))
        {
            return false;
        }
        if (pet_lib.isPet(npc) && pet_lib.isMyPet(npc, target))
        {
            return false;
        }
        if (goodFaction(npc, target) || target == npc)
        {
            return false;
        }
        if (!isPlayer(target))
        {
            obj_id myCell = (getLocation(npc)).cell;
            if (isIdValid(myCell))
            {
                obj_id defenderCell = (getLocation(target)).cell;
                if (!isIdValid(defenderCell))
                {
                    return false;
                }
            }
        }
        if (!pvpCanAttack(npc, target))
        {
            debugSpeakMsg(npc, "pvpCannotAttack");
            return false;
        }
        return true;
    }
    public static void moveRandom(obj_id npc, obj_id target) throws InterruptedException
    {
        if (isIndoors(npc))
        {
            return;
        }
        obj_id weapon = getCurrentWeapon(npc);
        int weaponType = getWeaponType(weapon);
        int weaponCat = combat.getWeaponCategory(weaponType);
        if (weaponCat != combat.RANGED_WEAPON)
        {
            PROFILER_STOP("moveRandom");
            return;
        }
        int currentPosture = getPosture(npc);
        if (currentPosture == POSTURE_KNOCKED_DOWN)
        {
            removeObjVar(npc, "ai.combat.moveMode");
            PROFILER_STOP("moveRandom");
            return;
        }
        else if (currentPosture != POSTURE_UPRIGHT)
        {
            stop(npc);
            posture.stand(npc);
            setObjVar(npc, "ai.combat.moveMode", ai_combat_movement.MOVEMODE_PREPARING_TO_STAND);
            PROFILER_STOP("moveRandom");
            return;
        }
        location npcLoc = new location(getLocation(npc));
        location targetLoc = getLocation(target);
        if (targetLoc.x > npcLoc.x)
        {
            npcLoc.x -= rand(5, 15);
        }
        else 
        {
            npcLoc.x += rand(5, 15);
        }
        if (targetLoc.z > npcLoc.z)
        {
            npcLoc.z -= rand(5, 15);
        }
        else 
        {
            npcLoc.z += rand(5, 15);
        }
        setObjVar(npc, "ai.combat.moveMode", ai_combat_movement.MOVEMODE_RANDOM);
        messageTo(npc, "handleMoveRandomClear", null, 30, false);
        PROFILER_START("pathTo");
        pathTo(npc, npcLoc);
        PROFILER_STOP("pathTo");
        PROFILER_STOP("moveRandom");
    }
    public static void setCombatLocation(obj_id npc) throws InterruptedException
    {
        setObjVar(npc, "ai.combat.combatStartLoc", getLocation(npc));
    }
    public static location getCombatLocation(obj_id npc) throws InterruptedException
    {
        return getLocationObjVar(npc, "ai.combat.combatStartLoc");
    }
    public static boolean attackInCircleFormation(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!hasObjVar(npc, "ai.inFormation") && (!hasObjVar(npc, "ai.combat.tempFormationCombat")))
        {
            return false;
        }
        if (hasObjVar(npc, "ai.combat.noRangedWeapon"))
        {
            return false;
        }
        obj_id weapon = getCurrentWeapon(npc);
        int weaponType = getWeaponType(weapon);
        int weaponCat = combat.getWeaponCategory(weaponType);
        if (weaponCat != combat.RANGED_WEAPON)
        {
            if (getHateTarget(target) != npc)
            {
            }
        }
        weapon = getCurrentWeapon(npc);
        float maxRange = getMaxRange(weapon) / 2.0f;
        float offset = 20.0f;
        if (maxRange < 20.0f)
        {
            offset = maxRange;
        }
        swarm(npc, target, offset);
        setObjVar(npc, "ai.combat.moveMode", ai_combat_movement.MOVEMODE_FOLLOW_FORMATION);
        return true;
    }
    public static boolean hasRangedWeapon(obj_id npc) throws InterruptedException
    {
        obj_id weapon = getCurrentWeapon(npc);
        int weaponType = getWeaponType(weapon);
        int weaponCat = combat.getWeaponCategory(weaponType);
        return (weaponCat == combat.RANGED_WEAPON);
    }
    public static void aiCombatFlee(obj_id npc, obj_id target, float minRange, float maxRange) throws InterruptedException
    {
        if (!isIdValid(npc) || !exists(npc))
        {
            return;
        }
        if (!isIdValid(target) || !exists(npc))
        {
            return;
        }
        location myLoc = new location(getLocation(npc));
        if (myLoc == null)
        {
            return;
        }
        if (isIdValid(myLoc.cell))
        {
            return;
        }
        location destLoc = new location(myLoc);
        location targetLoc = new location(getLocation(target));
        if (targetLoc == null)
        {
            return;
        }
        if (targetLoc.x < destLoc.x)
        {
            destLoc.x += rand(minRange, maxRange);
        }
        else 
        {
            destLoc.x -= rand(minRange, maxRange);
        }
        if (targetLoc.z < destLoc.z)
        {
            destLoc.z += rand(minRange, maxRange);
        }
        else 
        {
            destLoc.z -= rand(minRange, maxRange);
        }
        pathTo(npc, destLoc);
    }
    public static boolean switchToFormationCombat(obj_id npc, obj_id target) throws InterruptedException
    {
        if (hasObjVar(npc, "ai.combat.tempFormationCombat"))
        {
            return true;
        }
        if (!isPlayer(target))
        {
            return false;
        }
        int combatRound = utils.getIntScriptVar(npc, "aiCombatRoundCounter");
        if (combatRound < 5)
        {
            return false;
        }
        else if (combatRound > 10)
        {
            return false;
        }
        obj_id[] attackerList = getWhoIsTargetingMe(target);
        if (attackerList == null || attackerList.length < 2)
        {
            return false;
        }
        int niche = ai_lib.aiGetNiche(npc);
        if (niche != NICHE_DROID)
        {
            chat.setAngryMood(npc);
            ai_lib.barkString(npc, "assist");
        }
        for (int i = 0; i < attackerList.length; i++)
        {
            if (!isPlayer(attackerList[i]))
            {
                removeObjVar(attackerList[i], "ai.combat.moveMode");
                setObjVar(attackerList[i], "ai.combat.tempFormationCombat", i);
            }
        }
        return true;
    }
    public static boolean doHealing(obj_id npc, obj_id target) throws InterruptedException
    {
        utils.setScriptVar(npc, "ai.nextHeal", (getGameTime() + 20));
        obj_id[] myFriends = getWhoIsTargetingMe(target);
        if (myFriends == null || myFriends.length == 0)
        {
            return false;
        }
        obj_id healTarget = getHealTarget(npc, myFriends);
        if (!isIdValid(healTarget))
        {
            return false;
        }
        if (healTarget == npc)
        {
            executeHealingMove(npc, npc);
        }
        else if (getDistance(npc, healTarget) > 2.0f)
        {
            setObjVar(npc, "ai.combat.ignoreCombat", true);
            utils.setScriptVar(npc, "ai.combat.pathingToHeal", healTarget);
            follow(npc, healTarget, 1, 1.5f);
            setObjVar(npc, "ai.combat.moveMode", ai_combat_movement.MOVEMODE_MOVING_TO_HEAL);
        }
        return true;
    }
    public static obj_id getHealTarget(obj_id npc, obj_id[] myFriends) throws InterruptedException
    {
        for (int i = 0; i < myFriends.length; i++)
        {
            if (getDistance(npc, myFriends[i]) < 60.0f)
            {
                int healthDamage = getAttribDamage(myFriends[i], HEALTH);
                if (healthDamage < (getMaxAttrib(myFriends[i], HEALTH) / 2))
                {
                    healthDamage = 0;
                }
                int actionDamage = getAttribDamage(myFriends[i], ACTION);
                if (actionDamage < (getMaxAttrib(myFriends[i], ACTION) / 2))
                {
                    actionDamage = 0;
                }
                if (healthDamage != 0 || actionDamage != 0)
                {
                    return myFriends[i];
                }
            }
        }
        return null;
    }
    public static void executeHealingMove(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIdValid(npc) || ai_lib.aiIsDead(npc))
        {
            return;
        }
        if ((!isIdValid(target)) || ai_lib.aiIsDead(target))
        {
            removeObjVar(npc, "ai.combat.ignoreCombat");
            utils.removeScriptVar(npc, "ai.combat.pathingToHeal");
            stop(npc);
            removeObjVar(npc, "ai.combat.moveMode");
            return;
        }
        removeObjVar(npc, "ai.combat.moveMode");
        stop(npc);
        utils.removeScriptVar(npc, "ai.combat.pathingToHeal");
        utils.setScriptVar(npc, "ai.nextHeal", (getGameTime() + 20));
        if (ai_lib.aiIsDead(target) || ai_lib.aiIsDead(npc))
        {
            stop(npc);
            removeObjVar(npc, "ai.combat.ignoreCombat");
            utils.removeScriptVar(npc, "ai.combat.pathingToHeal");
            return;
        }
        if (getDistance(npc, target) > 2.0f)
        {
            setObjVar(npc, "ai.combat.ignoreCombat", true);
            utils.setScriptVar(npc, "ai.combat.pathingToHeal", target);
            setObjVar(npc, "ai.combat.moveMode", ai_combat_movement.MOVEMODE_MOVING_TO_HEAL);
            follow(npc, target, 1, 2);
            return;
        }
        removeObjVar(npc, "ai.combat.ignoreCombat");
        if (target == npc)
        {
            doAnimationAction(npc, "heal_self");
        }
        else 
        {
            doAnimationAction(npc, "heal_other");
        }
        location loc = getLocation(target);
        obj_id[] players = getAllPlayers(loc, 45.0f);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/healing_healdamage.cef", loc, 0);
            }
        }
        int healthDamage = getAttribDamage(target, HEALTH);
        int actionDamage = getAttribDamage(target, ACTION);
        int healAmt = (ai_lib.getLevel(npc) * 20);
        if (healAmt < healthDamage)
        {
            addToAttrib(target, HEALTH, healAmt);
        }
        else 
        {
            setAttrib(target, HEALTH, getMaxAttrib(target, HEALTH));
        }
        if (healAmt < actionDamage)
        {
            addToAttrib(target, ACTION, healAmt);
        }
        else 
        {
            setAttrib(target, ACTION, getMaxAttrib(target, ACTION));
        }
        utils.setScriptVar(npc, "ai.combat.movePause", 2);
    }
    public static boolean fleeFromHouse(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIdValid(npc) || !isIdValid(target))
        {
            return false;
        }
        if (pet_lib.isPet(npc))
        {
            return false;
        }
        location myLoc = getLocation(npc);
        if (myLoc == null)
        {
            return false;
        }
        location yourLoc = getLocation(target);
        if (yourLoc == null)
        {
            return false;
        }
        obj_id myCell = myLoc.cell;
        obj_id yourCell = yourLoc.cell;
        if (isIdValid(myCell) == false && isIdValid(yourCell))
        {
            int niche = ai_lib.aiGetNiche(npc);
            if (niche == NICHE_NPC || niche == NICHE_DROID || niche == NICHE_ANDROID)
            {
                if (permissionsIsAllowed(yourCell, npc))
                {
                    return false;
                }
            }
            pathTo(npc, aiGetHomeLocation(npc));
            messageTo(npc, "redoYaw", null, 30, false);
            setAttrib(npc, HEALTH, getUnmodifiedMaxAttrib(npc, HEALTH));
            setAttrib(npc, ACTION, getUnmodifiedMaxAttrib(npc, ACTION));
            if (isPlayer(target))
            {
                if (getHateTarget(target) == npc)
                {
                    
                }
                queueCommand(target, (1098448234), npc, "", COMMAND_PRIORITY_IMMEDIATE);
            }
            return true;
        }
        return false;
    }
    public static boolean doInteriorPathFinding(obj_id npc, obj_id target) throws InterruptedException
    {
        location npcLoc = getLocation(npc);
        if (!isIdValid(npcLoc.cell))
        {
            return false;
        }
        return (getDistance(npc, target) > 6.0f);
    }
    public static boolean isIndoors(obj_id npc) throws InterruptedException
    {
        location npcLoc = getLocation(npc);
        return (isIdValid(npcLoc.cell));
    }
    public static boolean isInCombat(obj_id ai) throws InterruptedException
    {
        boolean result = (getState(ai, STATE_COMBAT) == 1);
        if (!result)
        {
            deltadictionary dict = ai.getScriptVars();
            result = (dict.getObjVar("ai.combat.pendingCombat") != null);
        }
        return result;
    }
}
