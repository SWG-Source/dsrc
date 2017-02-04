package script.systems.combat;

import script.*;
import script.combat_engine.*;
import script.library.*;

import java.util.Arrays;
import java.util.Vector;

public class combat_base extends script.base_script
{
    public combat_base()
    {
    }
    public static final float MAX_THROWING_DISTANCE = 30;
    public static final float PVP_DAMAGE_REDUCTION_VALUE = .75f;
    public static final float UNIVERSAL_ACTION_COST_MULTIPLIER = 1.0f;
    public static final String JEDI_COMBAT_DATATABLE = "datatables/jedi/jedi_combat_data.iff";
    public static final int MAX_TARGET_ARRAY_SIZE = 10;
    public static final int ALLOWED_LOS_FAILURES = 10;
    public static final int TARGET_COMMAND = -2;
    public static final int PRECHECK_SUCCESS = -1;
    public static final int PRECHECK_OVERRIDE = 0;
    public static final int PRECHECK_CONTINUE = 1;
    public static final int HIT_RESULT_MISS = 0;
    public static final int HIT_RESULT_DODGE = 1;
    public static final int HIT_RESULT_PARRY = 2;
    public static final int HIT_RESULT_GLANCING = 3;
    public static final int HIT_RESULT_BLOCK = 4;
    public static final int HIT_RESULT_CRITICAL = 5;
    public static final int HIT_RESULT_PUNISHING = 6;
    public static final int HIT_RESULT_HIT = 7;
    public static final int HIT_RESULT_EVADE = 8;
    public static final int HIT_RESULT_STRIKETHROUGH = 9;
    public static final String PROGRESSIVE_DAMAGE_COUNTER = "hitData.progressive.counter";
    public static final string_id SID_NONE = new string_id();
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, String params, String successHandler, String failHandler) throws InterruptedException
    {
        return combatStandardAction(actionName, self, target, getCurrentWeapon(self), params, null, false, false, 0);
    }
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, String params, String successHandler, String failHandler, int overloadDamage) throws InterruptedException
    {
        return combatStandardAction(actionName, self, target, getCurrentWeapon(self), params, null, false, false, overloadDamage);
    }
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, String params, String successHandler, String failHandler, boolean testPetBar) throws InterruptedException
    {
        return combatStandardAction(actionName, self, target, getCurrentWeapon(self), params, null, false, testPetBar, 0);
    }
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, String params) throws InterruptedException
    {
        return combatStandardAction(actionName, self, target, getCurrentWeapon(self), params, null, false, false, 0);
    }
    public void sendPerformSpam(obj_id actor, obj_id target, string_id message, String actionName, int spam_type) throws InterruptedException
    {
        string_id actionStringId = new string_id("cmd_n", actionName);
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, message);
        pp = prose.setTU(pp, actor);
        pp = prose.setTT(pp, target);
        pp = prose.setTO(pp, actionStringId);
        sendCombatSpamMessageProse(actor, target, pp, true, true, true, spam_type);
    }
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, obj_id objWeapon, String params, combat_data actionData, boolean isTangibleAttacking) throws InterruptedException
    {
        return combatStandardAction(actionName, self, target, objWeapon, params, actionData, isTangibleAttacking, false, 0);
    }
    public boolean combatStandardAction(String actionName, obj_id self, obj_id target, obj_id objWeapon, String params, combat_data actionData, boolean isTangibleAttacking, boolean testPetBar, int overloadDamage) throws InterruptedException
    {
        if (combat.isStunned(self))
        {
            return false;
        }
        boolean triggerPetBar = false;
        obj_id player = self;
        if (testPetBar && isPlayer(self))
        {
            obj_id beast = beast_lib.getBeastOnPlayer(self);
            if (!isIdValid(beast) || !exists(beast) || !beast_lib.canPerformCommand(self, beast, actionName))
            {
                return false;
            }
            target = isIdValid(getTarget(beast)) ? getTarget(beast) : getIntendedTarget(self);
            self = beast;
            objWeapon = getCurrentWeapon(self);
            triggerPetBar = true;
        }
        if (weapons.checkForIllegalStorytellerWeapon(self, objWeapon))
        {
            weapons.handleIllegalStorytellerWeapon(self, objWeapon, "combatStandardAction");
            utils.setScriptVar(objWeapon, "illegalStorytellerWeapon", true);
        }
        if (!storyteller.storytellerCombatCheck(self, target))
        {
            target = obj_id.NULL_ID;
        }
        boolean verbose = false;
        boolean isEggWeapon = utils.hasScriptVar(objWeapon, "objOwner");
        if (actionData == null)
        {
            actionData = combat_engine.getCombatData(actionName);
        }
        actionData = attackOverrideByBuff(self, actionData);
        if (actionData == null)
        {
            combat.combatLog(self, target, "combatStandardAction", "Attack Aborted - Not a valid combat action - " + actionName);
            return false;
        }
        if (isEggWeapon && actionName.equals("forceThrow"))
        {
            self = utils.getObjIdScriptVar(objWeapon, "objOwner");
            objWeapon = getCurrentWeapon(self);
        }
        combat.combatLog(self, target, "combatStandardAction", "who=" + getName(self) + ", actionName=" + actionName + ", target = " + target + ", params.length=" + params.length() + ", params=" + params + ", weapon=" + getName(objWeapon));
        boolean isGroundTarget = getWeaponType(objWeapon) == WEAPON_TYPE_GROUND_TARGETTING;
        boolean isDirectionTarget = getWeaponType(objWeapon) == WEAPON_TYPE_DIRECTIONAL;
        boolean isLocationTargetOk = actionData.delayAttackEggPosition == 3;
        float xTarget = -1;
        float yTarget = -1;
        float zTarget = -1;
        obj_id cellTarget = null;
        location targetLoc = null;
        boolean isAutoAiming = false;
        if (params != null && !params.equals(""))
        {
            actionData.params = params;
            java.util.StringTokenizer st = new java.util.StringTokenizer(params);
            int paramCount = st.countTokens();
            long cellId = 0;
            if (paramCount >= 7)
            {
                xTarget = Float.parseFloat(st.nextToken());
                yTarget = Float.parseFloat(st.nextToken());
                zTarget = Float.parseFloat(st.nextToken());
                cellId = Long.parseLong(st.nextToken());
                if (cellId != 0)
                {
                    cellTarget = obj_id.getObjId(cellId);
                    xTarget = Float.parseFloat(st.nextToken());
                    yTarget = Float.parseFloat(st.nextToken());
                    zTarget = Float.parseFloat(st.nextToken());
                }
                targetLoc = new location(xTarget, yTarget, zTarget, getCurrentSceneName(), cellTarget);
            }
            String lastArg = "";
            while (st.hasMoreTokens())
            {
                lastArg = st.nextToken();
            }
            if (lastArg.equals("a"))
            {
                isAutoAiming = true;
            }
        }
        if (params.equals("") && isLocationTargetOk)
        {
            targetLoc = getLocation(objWeapon);
        }
        if (utils.hasScriptVar(objWeapon, "isAutoAimed"))
        {
            isAutoAiming = utils.getBooleanScriptVar(objWeapon, "isAutoAimed");
        }
        boolean isSpecialAttack = actionData.commandType == combat.RIGHT_CLICK_SPECIAL;
        int hitType = actionData.hitType;
        boolean isDelayedAttack = false;
        if (!isTangibleAttacking)
        {
            isDelayedAttack = (hitType == combat.DELAY_ATTACK);
        }
        weapon_data weaponData = getOverloadedWeaponData(self, objWeapon, actionData);
        if (weaponData == null)
        {
            String logString = "getOverloadedWeaponData failed to return data for self: " + self + " " + " actionData: " + actionData;
            if (isIdValid(self))
            {
                logString += (isPlayer(self) ? " a player object" : " a non-player object");
            }
            if (isIdValid(objWeapon))
            {
                logString += " objWeapon: " + objWeapon + " getName(objWeapon): " + getName(objWeapon);
                if (!utils.hasScriptVar(objWeapon, "dctWeaponStats"))
                {
                    logString += " dctWeaponStats: null";
                }
                else 
                {
                    logString += " dctWeaponStats: exists";
                }
            }
            else 
            {
                logString += " objWeapon: null dctWeaponStats: null";
            }
            CustomerServiceLog("combat_errors: ", logString);
            return false;
        }
        if (!isTangibleAttacking)
        {
            if (!combat.canUseWeaponWithAbility(self, weaponData, actionData, verbose))
            {
                combat.combatLog(self, null, "combatStandardAction", "Aborting Attack - Not able to use this action (" + actionName + ") with the current weapon");
                return false;
            }
        }
        obj_id combatTarget = target;
        if (isTangibleAttacking)
        {
            actionData.validTarget = actionData.validEggTarget;
        }
        if (isSpecialAttack && !isTangibleAttacking)
        {
            combatTarget = getCorrectCombatTarget(self, target, actionData, verbose);
            if (!isIdValid(combatTarget))
            {
                combat.combatLog(self, target, "combatStandardAction", "Attack Failed - No valid combat target");
                return false;
            }
        }
        else if (!isTangibleAttacking && !isGroundTarget && !isDirectionTarget)
        {
            combatTarget = getCorrectCombatTarget(self, target, actionData, verbose);
            if (!isIdValid(combatTarget))
            {
                combat.combatLog(self, target, "combatStandardAction", "Attack Failed - No valid combat target");
                return false;
            }
        }
        attacker_data attackerData = new attacker_data();
        defender_data[] defenderData = null;
        if (isGroundTarget || isDirectionTarget)
        {
            actionData.targetLoc = targetLoc;
        }
        if (isLocationTargetOk && targetLoc != null)
        {
            actionData.targetLoc = targetLoc;
        }
        if (!isTangibleAttacking)
        {
            actionData = modifyActionDataByExpertise(self, actionData);
        }
        stealth.clearPreviousInvis(self);
        stealth.testInvisCombatAction(self, target, actionData);
        if (!isDelayedAttack)
        {
            obj_id[] defenders = getCombatDefenders(self, combatTarget, weaponData, actionData, isSpecialAttack);
            if (defenders == null || defenders.length == 0)
            {
                combat.combatLog(self, target, "combatStandardAction", "Attack Failed - No valid targets");
                if (weaponData.weaponType == WEAPON_TYPE_DIRECTIONAL && !isEggWeapon)
                {
                    attacker_results atkRslt = new attacker_results();
                    defender_results[] dfndRslt = new defender_results[0];
                    atkRslt.id = self;
                    atkRslt.weapon = weaponData.id;
                    atkRslt.actionName = getStringCrc(toLower(actionName));
                    atkRslt.useLocation = true;
                    if(targetLoc != null && isValidLocation(targetLoc)) {
                        atkRslt.targetLocation = new vector(targetLoc.x, targetLoc.y, targetLoc.z);
                        atkRslt.targetCell = targetLoc.cell;
                    }
                    else {
                        location tl = getLocation(target);
                        if(!isValidLocation(tl)){
                            LOG("combat","Could not identify the target's (" + target + ":" + getPlayerFullName(target) + ") location (" + targetLoc + ") to attack it.");
                        }
                        atkRslt.targetLocation = new vector(tl.x, tl.y, tl.z);
                        atkRslt.targetCell = tl.cell;
                    }
                    atkRslt.endPosture = (combat.isMeleeWeapon(weaponData.id) || combat.isLightsaberWeapon(weaponData.id)) ? POSTURE_UPRIGHT : getPosture(self);
                    String anim = actionData.animDefault;
                    stealth.testInvisCombatAction(self, target, actionData);
                    doCombatResults(anim, atkRslt, dfndRslt);
                    return true;
                }
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            weapon_data junkData = new weapon_data();
            defenderData = new defender_data[defenders.length];
            if (!getCombatData(self, defenders, attackerData, defenderData, junkData))
            {
                String msg = "ERROR::combatStandardAction:" + actionData.actionName + ") call to getCombatData returned false";
                CustomerServiceLog("combat_errors", msg);
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
        }
        else 
        {
            attackerData.id = self;
            defender_data dat = new defender_data();
            dat.id = target;
            defenderData = new defender_data[]
            {
                dat
            };
        }
        int[] actionCost = null;
        if (!isTangibleAttacking)
        {
            actionCost = combat.getActionCost(self, weaponData, actionData);
            if (!combat.canDrainCombatActionAttributes(self, actionCost))
            {
                showFlyTextPrivate(self, self, new string_id("combat_effects", "action_too_tired"), 1.5f, colors.GOLDENROD);
                if (verbose)
                {
                    combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "pool_drain_fail"));
                }
                combat.combatLog(self, self, "combatStandardAction", "Aborting Attack - Insufficient ability points for action");
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            int killMeterCost = (int)actionData.vigorCost;
            if (killMeterCost > 0)
            {
                if (!beast_lib.isBeast(attackerData.id) && isPlayer(attackerData.id))
                {
                    if (!combat.canDrainKillMeter(attackerData.id, killMeterCost))
                    {
                        showFlyTextPrivate(self, self, new string_id("combat_effects", "need_more_kills"), 1.5f, colors.FIREBRICK);
                        stealth.reinstateInvisFromCombat(self);
                        return false;
                    }
                    combat.drainKillMeter(attackerData.id, killMeterCost);
                }
            }
        }
        boolean isHeal = hitType == combat.HEAL;
        boolean isRevive = hitType == combat.REVIVE;
        if (isHeal || isRevive)
        {
            combat.combatLog(self, combatTarget, "combatStandardAction", "Heal Action - Branching off to healing functionality");
            if (!doHealingPreCheck(actionData, attackerData, defenderData, verbose))
            {
                combat.combatLog(self, combatTarget, "combatStandardAction", "Attack Failed - Healing precheck failed");
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            if (!combat.canDrainCombatActionAttributes(self, actionCost))
            {
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            if (actionData.performanceSpamStrId.isValid() && !isTangibleAttacking)
            {
                sendPerformSpam(self, combatTarget, actionData.performanceSpamStrId, actionName, COMBAT_RESULT_GENERIC);
            }
            boolean success = false;
            if (isHeal)
            {
                success = healing.performHealDamage(attackerData, defenderData, actionData);
            }
            if (isRevive)
            {
                success = healing.performRevivePlayer(attackerData, defenderData, actionData, actionData.addedDamage > 0);
            }
            if (success)
            {
                stealth.testInvisNonCombatAction(self, target, actionData);
            }
            if (success)
            {
                LOG("combat_base", "combatStandardAction 7 success " + actionName);
                if (!combat.drainCombatActionAttributes(self, actionCost))
                {
                    combat.combatLog(self, combatTarget, "combatStandardAction", "ERROR:Insufficient action not caught in canDrainCombatActionAttributes()");
                    stealth.reinstateInvisFromCombat(self);
                    return false;
                }
                String anim = actionData.animDefault;
                attacker_results cbtAttackerResults = new attacker_results();
                obj_id defender = self;
                if (isIdValid(target) && target != self && !isDead(target))
                {
                    defender = target;
                }
                defender_results[] cbtDefenderResults = new defender_results[1];
                cbtDefenderResults[0] = new defender_results();
                cbtDefenderResults[0].id = defender;
                cbtDefenderResults[0].endPosture = getPosture(defender);
                cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
                cbtAttackerResults.id = self;
                cbtAttackerResults.weapon = null;
                cbtAttackerResults.actionName = getStringCrc(toLower(actionName));
                cbtAttackerResults.endPosture = getPosture(self);
                doCombatResults(anim, cbtAttackerResults, cbtDefenderResults);
            }
            return success;
        }
        else if (!isTangibleAttacking)
        {
            if (!doCombatPreCheck(actionData, attackerData, weaponData, defenderData, verbose))
            {
                combat.combatLog(self, combatTarget, "combatStandardAction", "Attack Failed - Combat precheck failed");
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            boolean isSingleTargetAttack = actionData.hitType == -1 && actionData.attackType == combat.SINGLE_TARGET;
            if (!isSingleTargetAttack && !combat.drainCombatActionAttributes(self, actionCost))
            {
                showFlyTextPrivate(self, self, new string_id("combat_effects", "action_too_tired"), 1.5f, colors.GOLDENROD);
                combat.combatLog(self, combatTarget, "combatStandardAction", "ERROR:Insufficient action not caught in canDrainCombatActionAttributes()");
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
            if (actionData.performanceSpamStrId.isValid())
            {
                sendPerformSpam(self, combatTarget, actionData.performanceSpamStrId, actionName, COMBAT_RESULT_GENERIC);
            }
        }
        attacker_results attackerResults = new attacker_results();
        defender_results[] defenderResults = new defender_results[defenderData.length];
        if (!isDelayedAttack)
        {
            hit_result[] hitData = null;
            hitData = runHitEngine(attackerData, weaponData, defenderData, attackerResults, defenderResults, actionData, isTangibleAttacking, isAutoAiming, overloadDamage);
            if (hitData == null)
            {
                String msg = " Attack Failed - No hit data returned";
                CustomerServiceLog("combat_errors", "ERROR::combatStandardAction:" + actionData.actionName + " " + msg);
                combat.combatLog(self, combatTarget, "combatStandardAction", msg);
                stealth.reinstateInvisFromCombat(self);
                return false;
            }
        }
        else if (isGroundTarget && !isSpecialAttack)
        {
            attacker_results atkRslt = new attacker_results();
            defender_results[] dfndRslt = new defender_results[0];
            atkRslt.id = self;
            atkRslt.weapon = weaponData.id;
            atkRslt.actionName = getStringCrc(toLower(actionName));
            atkRslt.useLocation = true;
            atkRslt.targetLocation = new vector(targetLoc.x, targetLoc.y, targetLoc.z);
            atkRslt.targetCell = targetLoc.cell;
            atkRslt.endPosture = (combat.isMeleeWeapon(weaponData.id) || combat.isLightsaberWeapon(weaponData.id)) ? POSTURE_UPRIGHT : getPosture(self);
            String anim = actionData.animDefault;
            doCombatResults(anim, atkRslt, dfndRslt);
        }
        if ((isDelayedAttack) && (!isTangibleAttacking))
        {
            if (utils.hasScriptVar(self, combat.DIED_RECENTLY))
            {
                utils.removeScriptVar(self, combat.DIED_RECENTLY);
            }
            LOG("combat_base", "startDelayedAttack");
            startDelayedAttack(self, combatTarget, objWeapon, actionName, actionData, isGroundTarget, isAutoAiming);
        }
        if (beast_lib.isChargeAttack(actionName))
        {
            setLocation(self, getLocation(target));
        }
        if (!isPlayer(self) && !beast_lib.isBeast(self))
        {
            setCommandTimerValue(self, TIMER_COOLDOWN, 0f);
        }
        if (triggerPetBar)
        {
            combat_data cd = combat_engine.getCombatData(actionName);
            String cooldownGroup = cd.cooldownGroup;
            int groupCrc = getStringCrc(cooldownGroup);
            sendCooldownGroupTimingOnly(player, groupCrc, cd.cooldownTime);
        }
        return true;
    }
    public void startDelayedAttack(obj_id attacker, obj_id target, obj_id objWeapon, String attackName, combat_data actionData, boolean instantHit, boolean isAutoAimed) throws InterruptedException
    {
        int eggPosition = actionData.delayAttackEggPosition;
        location where = getLocation(attacker);
        if (eggPosition == 2)
        {
            where = getLocation(target);
        }
        else if (eggPosition == 3)
        {
            where = (location)actionData.targetLoc.clone();
        }
        obj_id egg = combat.makeTrackerEgg(attacker, where, actionData);
        if (!isIdValid(egg) || !exists(egg))
        {
            return;
        }
        dictionary dctWeaponStats = utils.getDictionaryScriptVar(objWeapon, "dctWeaponStats");
        if(dctWeaponStats != null)
            utils.setScriptVar(egg, "dctWeaponStats", dctWeaponStats);
        utils.setScriptVar(egg, "isAutoAimed", isAutoAimed);
        doStandardDelayedAction(egg, target, attackName, actionData, instantHit);
    }
    public void doStandardDelayedAction(obj_id objEgg, obj_id target, String attackName, combat_data actionData, boolean instantHit) throws InterruptedException
    {
        float delay = actionData.delayAttackInterval;
        String strEffect = actionData.delayAttackParticle;
        int intLoops = actionData.delayAttackLoops;
        boolean boolDestroy = true;
        if (intLoops > 1)
        {
            if (!utils.hasScriptVar(objEgg, "intLoopsLeft"))
            {
                utils.setScriptVar(objEgg, "intLoopsLeft", intLoops);
                boolDestroy = false;
            }
            else 
            {
                intLoops = utils.getIntScriptVar(objEgg, "intLoopsLeft");
                if (intLoops > 1)
                {
                    boolDestroy = false;
                }
            }
        }
        if (instantHit)
        {
            float initDelay = actionData.initialDelayAttackInterval;
            if (initDelay >= 0)
            {
                delay = initDelay;
            }
        }
        obj_id objOwner = utils.getObjIdScriptVar(objEgg, "objOwner");
        if (isIdValid(objOwner))
        {
            delay += (int)getSkillStatisticModifier(objOwner, "expertise_delay_line_" + actionData.specialLine);
            delay -= (int)getSkillStatisticModifier(objOwner, "expertise_delay_reduce_line_" + actionData.specialLine);
        }
        if (delay < 0)
        {
            delay = 0;
        }
        doDelayedParticle(objEgg, strEffect, delay, false);
        doDelayedAttack(objEgg, attackName, target, delay, "", boolDestroy);
        return;
    }
    public void doDelayedParticle(obj_id objEgg, String strEffect, float fltDelay, boolean boolCleanup) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("strEffect", strEffect);
        dctParams.put("boolCleanup", boolCleanup);
        utils.setScriptVar(objEgg, "handleDelayedParticle", dctParams);
        messageTo(objEgg, "handleDelayedParticle", dctParams, fltDelay, false);
        return;
    }
    public void doDelayedAttack(obj_id objEgg, String strAttack, obj_id objTarget, float fltDelay, String strEffect, boolean boolCleanup) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("strCommand", strAttack);
        dctParams.put("objTarget", objTarget);
        dctParams.put("boolCleanup", boolCleanup);
        dctParams.put("strEffect", strEffect);
        utils.setScriptVar(objEgg, "handleDelayedAttack", dctParams);
        messageTo(objEgg, "handleDelayedAttack", dctParams, fltDelay, false);
    }
    public obj_id getCorrectCombatTarget(obj_id self, obj_id curTarget, boolean verbose) throws InterruptedException
    {
        return getCorrectCombatTarget(self, curTarget, "", new combat_data(), verbose);
    }
    public obj_id getCorrectCombatTarget(obj_id self, obj_id curTarget, combat_data actionData, boolean verbose) throws InterruptedException
    {
        return getCorrectCombatTarget(self, curTarget, "", actionData, verbose);
    }
    public obj_id getCorrectCombatTarget(obj_id self, obj_id curTarget, String params, combat_data actionData, boolean verbose) throws InterruptedException
    {
        obj_id newTarget = curTarget;
        int validTargetType = actionData.validTarget;
        boolean isOffensive = (validTargetType != combat.VALID_TARGET_FRIEND && validTargetType != combat.VALID_TARGET_DEAD);
        if (actionData.actionName.equals("me_stasis_1") || actionData.actionName.startsWith("bm_mend_pet") || actionData.actionName.equals("bm_soothing_comfort_1") || actionData.actionName.startsWith("bm_revive"))
        {
            return newTarget;
        }
        if (validTargetType == -1)
        {
            return self;
        }
        if (newTarget == self)
        {
            int intHitType = actionData.hitType;
            if (isOffensive)
            {
                if (verbose)
                {
                    combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "shoot_self"));
                }
                combat.combatLog(self, curTarget, "getCorrectCombatTarget", "Cannot attack self - returning null target");
                return null;
            }
        }
        if (isOffensive && isDead(newTarget))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_target"));
            }
            combat.combatLog(self, newTarget, "getCorrectCombatTarget", "Invalid Target - Target is Dead");
            return null;
        }
        if (isOffensive && isIncapacitated(newTarget))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_target"));
            }
            combat.combatLog(self, newTarget, "getCorrectCombatTarget", "Invalid Target - Target is Incapped");
            return null;
        }
        if (!isOffensive && (newTarget != self) && (!isIdValid(newTarget) || pvpCanAttack(self, newTarget) || !pvpCanHelp(self, newTarget)))
        {
            newTarget = self;
        }
        if (!combat.validateTarget(newTarget, actionData.validTarget))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_target"));
            }
            combat.combatLog(self, curTarget, "getCorrectCombatTarget", "Invalid target as defined in datatable - returning null target");
            return null;
        }
        obj_id container = getContainedBy(newTarget);
        if (isPlayer(container))
        {
            String msg = "Cannot attack item contained by player - returning null target";
            CustomerServiceLog("combat_errors", "ERROR::getCorrectCombatTarget:" + msg);
            return null;
        }
        if ((!isOffensive && !pvpCanHelp(self, newTarget)) || (isOffensive && !pvpCanAttack(self, newTarget)))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_pvp_target"));
            }
            combat.combatLog(self, curTarget, "getCorrectCombatTarget", "Not a valid PvP target - returning null target");
            return null;
        }
        if (isOffensive && stealth.hasInvisibleBuff(newTarget))
        {
            int range = getPassiveRevealRange(newTarget, self);
            if (range >= 0)
            {
                stealth.checkForAndMakeVisible(newTarget);
            }
            else 
            {
                if (verbose)
                {
                    combat.sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_target"));
                }
                return null;
            }
        }
        return newTarget;
    }
    public boolean isInAttackRange(obj_id attacker, obj_id target, String actionName, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(attacker) || !exists(attacker) || !isIdValid(target) || !exists(target))
        {
            return false;
        }
        combat_data actionData = combat_engine.getCombatData(actionName);
        if (actionData == null)
        {
            return false;
        }
        obj_id weapon = getCurrentWeapon(attacker);
        if (!isIdValid(weapon) || !exists(weapon))
        {
            return false;
        }
        weapon_data weaponData = getOverloadedWeaponData(attacker, weapon, actionData);
        if (weaponData == null)
        {
            return false;
        }
        return isInAttackRange(attacker, target, actionData, weaponData, verbose);
    }
    public boolean isInAttackRange(obj_id attacker, obj_id target, combat_data actionData, weapon_data weaponData, boolean verbose) throws InterruptedException
    {
        float dist = getDistance(attacker, target);
        float adjustMin = 0.0f;
        float adjustMax = 0.0f;
        if (dist > combat_engine.getMaxCombatRange() && actionData.ignore_distance == 0)
        {
            if (verbose)
            {
                showFlyTextPrivate(attacker, attacker, new string_id("combat_effects", "range_too_far"), 1.5f, colors.MEDIUMTURQUOISE);
                combat.sendCombatSpamMessage(attacker, new string_id("cbt_spam", "out_of_range_far"), COMBAT_RESULT_OUT_OF_RANGE);
            }
            combat.combatLog(attacker, target, "doCombatPreCheck", "Aborting Attack - Target is further than max combat range");
            return false;
        }
        if (actionData.overloadWeaponType == WEAPON_TYPE_THROWN)
        {
            weaponData.maxRange = cybernetic.getThrowRangeMod(attacker, weaponData.maxRange);
        }
        else 
        {
            int intWeaponCategory = combat.getWeaponCategory(weaponData.weaponType);
            if (intWeaponCategory == combat.RANGED_WEAPON)
            {
                weaponData.maxRange = cybernetic.getRangedRangeMod(attacker, weaponData.maxRange);
            }
            else 
            {
                adjustMax = 6.0f;
            }
        }
        float rangeMod = getSkillStatisticModifier(attacker, "expertise_range_bonus_" + weaponData.weaponType);
        weaponData.maxRange += rangeMod;
        float actionMaxRange = actionData.maxRange;
        if (actionMaxRange > 0)
        {
            weaponData.maxRange = actionMaxRange;
        }
        rangeMod = getSkillStatisticModifier(attacker, "expertise_range_line_" + actionData.specialLine);
        if (rangeMod != 0)
        {
            weaponData.maxRange += rangeMod;
        }
        if (dist > (weaponData.maxRange + adjustMax) && actionData.ignore_distance == 0)
        {
            if (verbose)
            {
                showFlyTextPrivate(attacker, attacker, new string_id("combat_effects", "range_too_far"), 1.5f, colors.MEDIUMTURQUOISE);
                combat.sendCombatSpamMessage(attacker, new string_id("cbt_spam", "out_of_range_far"), COMBAT_RESULT_OUT_OF_RANGE);
            }
            combat.combatLog(attacker, target, "doCombatPreCheck", "Aborting Attak - Target is further than max range modified");
            return false;
        }
        return true;
    }
    public boolean doCombatPreCheck(combat_data actionData, attacker_data attackerData, weapon_data weaponData, defender_data[] defenderData, boolean verbose) throws InterruptedException
    {
        verbose = false;
        if ((isPlayer(attackerData.id) && isPlayer(defenderData[0].id)) && (attackerData.id != defenderData[0].id))
        {
            String setting = getConfigSetting("GameServer", "disablePvP");
            if (setting != null && (setting.equals("1") || setting.equals("true")))
            {
                if (verbose)
                {
                    return false;
                }
            }
        }
        if (isDead(attackerData.id))
        {
            if (verbose)
            {
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("combat_effects", "cant_attack_fly"), 1.5f, colors.WHITE);
                combat.sendCombatSpamMessage(attackerData.id, new string_id("cbt_spam", "attack_fail_dead"));
            }
            combat.combatLog(attackerData.id, defenderData[0].id, "doCombatPreCheck", "Aborting Attack - Attacker is dead");
            return false;
        }
        if (isIncapacitated(attackerData.id))
        {
            if (verbose)
            {
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("combat_effects", "cant_attack_fly"), 1.5f, colors.WHITE);
                combat.sendCombatSpamMessage(attackerData.id, new string_id("cbt_spam", "attack_fail_incap"));
            }
            combat.combatLog(attackerData.id, defenderData[0].id, "doCombatPreCheck", "Aborting Attack - Attacker is incapped");
            return false;
        }
        if (!isInAttackRange(attackerData.id, defenderData[0].id, actionData, weaponData, verbose))
        {
            return false;
        }
        boolean cansee = false;
        if (actionData.specialLine.equals("ignore_los") || actionData.specialLine.equals("co_remote_detonator"))
        {
            cansee = true;
        }
        else if (weaponData.weaponType == WEAPON_TYPE_GROUND_TARGETTING)
        {
            int isSpecialAttack = actionData.commandType;
            if (actionData.specialLine.equals("no_proc"))
            {
                cansee = true;
            }
            else if (isSpecialAttack == combat.RIGHT_CLICK_SPECIAL && combat.isHeavyWeapon(weaponData))
            {
                cansee = true;
            }
            else 
            {
                location where = (location)actionData.targetLoc.clone();
                where.y += 0.5f;
                cansee = canSee(attackerData.id, where);
            }
        }
        else if (weaponData.weaponType == WEAPON_TYPE_DIRECTIONAL)
        {
            cansee = true;
        }
        else 
        {
            cansee = canSee(attackerData.id, defenderData[0].id);
        }
        if (!cansee)
        {
            if (verbose)
            {
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("combat_effects", "cant_see"), 1.5f, colors.MEDIUMTURQUOISE);
                combat.sendCombatSpamMessage(attackerData.id, new string_id("cbt_spam", "out_of_range_close"), COMBAT_RESULT_OUT_OF_RANGE);
            }
            return false;
        }
        setState(attackerData.id, STATE_PEACE, false);
        return true;
    }
    public obj_id getNextHatedTarget(obj_id self, obj_id[] tempDefenders, obj_id currentTarget) throws InterruptedException
    {
        if (tempDefenders == null || tempDefenders.length == 0)
        {
            return null;
        }
        for (int i = 0; i < tempDefenders.length; i++)
        {
            if (tempDefenders[i] != currentTarget && isOnHateList(self, tempDefenders[i]))
            {
                return tempDefenders[i];
            }
        }
        return null;
    }
    public obj_id[] getCombatDefenders(obj_id self, obj_id target, weapon_data weaponData, combat_data actionData, boolean isSpecialAttack) throws InterruptedException
    {
        int validTargetType = actionData.validTarget;
        int attackType = actionData.attackType;
        int hitType = actionData.hitType;
        boolean isOffensive = true;
        int pvpOnly = actionData.pvp_only;
        if (validTargetType == combat.VALID_TARGET_FRIEND || validTargetType == combat.VALID_TARGET_DEAD || hitType == combat.HEAL || hitType == combat.REVIVE)
        {
            isOffensive = false;
        }
        boolean fromTrap = hitType == combat.DELAY_ATTACK;
        obj_id source = self;
        obj_id[] defenders = null;
        if (actionData.actionName.equals("me_stasis_1") || actionData.actionName.startsWith("bm_mend_pet") || actionData.actionName.equals("bm_soothing_comfort_1") || actionData.actionName.startsWith("bm_revive"))
        {
            isOffensive = false;
        }
        if ((!isOffensive || hitType == combat.NON_ATTACK) && validTargetType == combat.VALID_TARGET_NONE)
        {
            target = self;
        }
        if (validTargetType == -1 && attackType == combat.SINGLE_TARGET)
        {
            defenders = new obj_id[1];
            defenders[0] = target;
            return defenders;
        }
        if (attackType == combat.SINGLE_TARGET)
        {
            if (defenders == null || defenders.length == 0)
            {
                int rolls = actionData.attack_rolls == 1 ? 1 : actionData.attack_rolls - 1;
                defenders = new obj_id[rolls];
                for (int q = 0; q < defenders.length; q++)
                {
                    defenders[q] = target;
                }
            }
        }
        else if (attackType == combat.CONE)
        {
            float length = actionData.coneLength;
            float width = actionData.coneWidth;
            String actionName = actionData.actionName;
            String specialLine = actionData.specialLine;
            float expertiseConeLengthBonus = 0.0f;
            float expertiseConeWidthBonus = 0.0f;
            if (actionName != null && !actionName.equals(""))
            {
                expertiseConeLengthBonus = getSkillStatisticModifier(self, "expertise_cone_length_single_" + actionName);
            }
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseConeLengthBonus += getSkillStatisticModifier(self, "expertise_cone_length_line_" + specialLine);
            }
            if (specialLine != null && !actionName.equals(""))
            {
                expertiseConeWidthBonus += (float)getSkillStatisticModifier(self, "expertise_cone_width_line_" + specialLine);
            }
            length += expertiseConeLengthBonus;
            width += (expertiseConeWidthBonus / 2.0f);
            if (actionData.maxRange == 0)
            {
                length = weaponData.maxRange;
            }
            if (!isOffensive)
            {
                defenders = getObjectsInCone(fromTrap ? weaponData.id : self, target, length, width);
            }
            else 
            {
                if (weaponData.weaponType == WEAPON_TYPE_DIRECTIONAL)
                {
                    if(actionData != null && isValidLocation(actionData.targetLoc)) {
                        defenders = pvpGetTargetsInCone(self, self, actionData.targetLoc, length, width);
                    }
                    else{
                        defenders = pvpGetTargetsInCone(self, self, target, length, width);
                    }
                }
                else 
                {
                    defenders = pvpGetTargetsInCone(self, fromTrap ? weaponData.id : self, target, length, width);
                }
            }
            if (defenders == null || defenders.length < 1)
            {
                defenders = new obj_id[1];
                defenders[0] = target;
            }
            combat.combatLog(self, target, "getCombatDefenders", "CONE TARGET: Defender count = " + defenders.length);
        }
        else if (attackType == combat.AREA || attackType == combat.TARGET_AREA)
        {
            float area = actionData.coneLength;
            String actionName = actionData.actionName;
            String specialLine = actionData.specialLine;
            float expertiseConeLengthBonus = 0.0f;
            if (actionName != null && !actionName.equals(""))
            {
                expertiseConeLengthBonus = getSkillStatisticModifier(self, "expertise_area_size_single_" + actionName);
            }
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseConeLengthBonus += getSkillStatisticModifier(self, "expertise_area_size_line_" + specialLine);
            }
            area += expertiseConeLengthBonus;
            if (area <= 0)
            {
                area = weaponData.maxRange;
            }
            if (attackType == combat.AREA)
            {
                combat.combatLog(self, target, "getCombatDefenders", "Attack Type = combat.AREA");
                combat.combatLog(self, target, "getCombatDefenders", "Area Effect = " + area);
                if (!isOffensive)
                {
                    defenders = getCreaturesInRange(fromTrap ? weaponData.id : self, area);
                }
                else 
                {
                    defenders = pvpGetTargetsInRange(self, fromTrap ? weaponData.id : self, area);
                }
                combat.combatLog(self, target, "getCombatDefenders", "AREA SELF: self = " + self);
            }
            else 
            {
                combat.combatLog(self, target, "getCombatDefenders", "Attack Type = combat.TARGET_AREA");
                combat.combatLog(self, target, "getCombatDefenders", "Area Effect = " + area);
                if (!isOffensive)
                {
                    defenders = getCreaturesInRange(target, area);
                }
                else 
                {
                    defenders = pvpGetTargetsInRange(self, fromTrap ? weaponData.id : target, area);
                }
                source = target;
            }
            if (defenders == null || defenders.length < 1 && actionData.commandType == combat.LEFT_CLICK_DEFAULT)
            {
                defenders = new obj_id[1];
                defenders[0] = target;
            }
            combat.combatLog(self, target, "getCombatDefenders", "AREA EFFECT: Defender count = " + defenders.length);
        }
        else if (attackType == combat.RAMPAGE)
        {
            float area = actionData.coneLength;
            obj_id[] areaTargets = pvpGetTargetsInRange(self, self, area);
            float maxHate = 0;
            obj_id rampageTarget = null;
            for (int i = 0; i < areaTargets.length; i++)
            {
                if (areaTargets[i] == target)
                {
                    continue;
                }
                if (isOnHateList(self, areaTargets[i]))
                {
                    float targetHate = getHate(self, areaTargets[i]);
                    if (targetHate > maxHate && combat.cachedCanSee(self, areaTargets[i]))
                    {
                        rampageTarget = areaTargets[i];
                        maxHate = targetHate;
                    }
                }
            }
            if (isIdValid(rampageTarget))
            {
                defenders = new obj_id[1];
                target = rampageTarget;
                defenders[0] = rampageTarget;
            }
            else 
            {
                defenders = new obj_id[1];
                defenders[0] = target;
            }
            combat.combatLog(self, target, "getCombatDefenders", "RAMPAGE: Defender count = " + defenders.length);
        }
        else if (attackType == combat.RANDOM_HATE_TARGET)
        {
            obj_id[] potentialTargets = getHateList(self);
            float max_range = 64.0f;
            Vector validTargets = new Vector();
            validTargets.setSize(0);
            for (int i = 0; i < potentialTargets.length; i++)
            {
                if (potentialTargets[i] != target && combat.cachedCanSee(self, potentialTargets[i]) && !isDead(potentialTargets[i]))
                {
                    if (getDistance(self, potentialTargets[i]) < max_range)
                    {
                        utils.addElement(validTargets, potentialTargets[i]);
                    }
                }
            }
            if (validTargets == null || validTargets.size() == 0)
            {
                defenders = new obj_id[1];
                defenders[0] = target;
            }
            else 
            {
                target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
                defenders = new obj_id[1];
                defenders[0] = target;
            }
        }
        else if (attackType == combat.RANDOM_HATE_TARGET_CONE)
        {
            float length = actionData.coneLength;
            float width = actionData.coneWidth;
            float max_range = length;
            obj_id[] potentialTargets = getHateList(self);
            Vector validTargets = new Vector();
            validTargets.setSize(0);
            for (int i = 0; i < potentialTargets.length; i++)
            {
                if (potentialTargets[i] != target && combat.cachedCanSee(self, potentialTargets[i]) && !isDead(potentialTargets[i]))
                {
                    if (getDistance(self, potentialTargets[i]) < max_range)
                    {
                        utils.addElement(validTargets, potentialTargets[i]);
                    }
                }
            }
            if (validTargets == null || validTargets.size() == 0)
            {
                defenders = pvpGetTargetsInCone(self, self, target, length, width);
            }
            else 
            {
                target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
                defenders = pvpGetTargetsInCone(self, self, target, length, width);
            }
        }
        else if (attackType == combat.RANDOM_HATE_TARGET_CONE_TERMINUS)
        {
            float width = actionData.coneWidth;
            float max_range = 64.0f;
            obj_id[] potentialTargets = getHateList(self);
            Vector validTargets = new Vector();
            validTargets.setSize(0);
            for (int i = 0; i < potentialTargets.length; i++)
            {
                if (potentialTargets[i] != target && combat.cachedCanSee(self, potentialTargets[i]))
                {
                    if (getDistance(self, potentialTargets[i]) < max_range)
                    {
                        utils.addElement(validTargets, potentialTargets[i]);
                    }
                }
            }
            if (validTargets == null || validTargets.size() == 0)
            {
                defenders = pvpGetTargetsInCone(self, self, target, getDistance(self, target) + 5.0f, width);
            }
            else 
            {
                target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
                max_range = getDistance(self, target) + 5.0f;
                defenders = pvpGetTargetsInCone(self, self, target, max_range, width);
            }
        }
        else if (attackType == combat.HATE_LIST)
        {
            obj_id[] hateTargets = getHateList(self);
            Vector validTargets = new Vector();
            validTargets.setSize(0);
            int max_range = 90;
            for (int i = 0; i < hateTargets.length; i++)
            {
                if (hateTargets[i] != target && canSee(self, hateTargets[i]))
                {
                    if (getDistance(self, hateTargets[i]) < max_range)
                    {
                        utils.addElement(validTargets, hateTargets[i]);
                    }
                }
            }
            if (validTargets != null)
            {
                defenders = new obj_id[validTargets.size()];
                validTargets.toArray(defenders);

            }
        }
        else if (attackType == combat.RANDOM_HATE_MULTI)
        {
            obj_id[] hateTargets = getHateList(self);
            int max_targets = (int)actionData.coneLength;
            if (hateTargets.length == 1)
            {
                defenders = new obj_id[1];
                defenders[0] = target;
            }
            else 
            {
                obj_id[] randomTargets = new obj_id[max_targets];
                Vector targetList = new Vector(Arrays.asList((obj_id[])hateTargets.clone()));
                targetList = utils.shuffleArray(targetList);
                if (max_targets > hateTargets.length)
                {
                    max_targets = hateTargets.length;
                }
                for (int i = 0; i < max_targets; i++)
                {
                    randomTargets[i] = ((obj_id)targetList.get(i));
                }
                defenders = randomTargets;
            }
        }
        else if (attackType == combat.AREA_PROGRESSIVE)
        {
            float area = actionData.coneLength;
            if (area <= 0)
            {
                area = weaponData.maxRange;
            }
            defenders = pvpGetTargetsInRange(self, self, area);
        }
        else if (attackType == combat.SPLIT_DAMAGE_TARGET_AREA)
        {
            float area = actionData.coneLength;
            float targets = actionData.coneWidth;
            obj_id[] targetsInArea = pvpGetTargetsInRange(self, fromTrap ? weaponData.id : target, area);
            defenders = new obj_id[2];
            defenders[0] = target;
            for (int i = 0; i < targetsInArea.length; i++)
            {
                if (targetsInArea[i] != target)
                {
                    defenders[1] = targetsInArea[i];
                    break;
                }
            }
        }
        else if (attackType == combat.DISTANCE_FARTHEST)
        {
            obj_id[] hateList = getHateList(self);
            obj_id farthest = getHateTarget(self);
            float farDist = 0.0f;
            for (int i = 0; i < hateList.length; i++)
            {
                if (getDistance(self, hateList[i]) >= farDist)
                {
                    farthest = hateList[i];
                    farDist = getDistance(self, hateList[i]);
                }
            }
            defenders = new obj_id[1];
            defenders[0] = farthest;
            target = farthest;
        }
        else 
        {
            defenders = new obj_id[1];
            defenders[0] = target;
            combat.combatLog(self, target, "getCombatDefenders", "DEFAULT: Defender count = " + defenders.length);
        }
        defenders = validateDefenders(self, target, defenders, source, MAX_TARGET_ARRAY_SIZE, validTargetType, isOffensive, fromTrap ? weaponData.id : null, combat.isHeavyWeapon(weaponData), isSpecialAttack, actionData.specialLine.equals("ignore_los") ? true : false, pvpOnly, actionData);
        utils.setScriptVar(self, combat.CHARGE_TARGET, target);
        return defenders;
    }
    public hit_result[] runHitEngine(attacker_data attackerData, weapon_data weaponData, defender_data[] defenderData, attacker_results attackerResults, defender_results[] defenderResults, combat_data actionData, boolean isTangibleAttacking) throws InterruptedException
    {
        return runHitEngine(attackerData, weaponData, defenderData, attackerResults, defenderResults, actionData, isTangibleAttacking, true, 0);
    }
    public hit_result[] runHitEngine(attacker_data attackerData, weapon_data weaponData, defender_data[] defenderData, attacker_results attackerResults, defender_results[] defenderResults, combat_data actionData, boolean isTangibleAttacking, boolean isAutoAiming, int overloadDamage) throws InterruptedException
    {
        hit_result[] hitData = new hit_result[defenderData.length];
        obj_id[] defenders = new obj_id[defenderData.length];
        int[] results = new int[hitData.length];
        String[] playbackNames = new String[defenderData.length];
        String strAttack = actionData.actionName;
        attackerResults.id = attackerData.id;
        attackerResults.weapon = weaponData.id;
        attackerResults.endPosture = (!isTangibleAttacking && (combat.isMeleeWeapon(weaponData.id) || combat.isLightsaberWeapon(weaponData.id))) ? POSTURE_UPRIGHT : getPosture(attackerData.id);
        combat.applyAttackerCombatBuffs(attackerData.id, actionData);
        int hitType = actionData.hitType;
        if (hitType == combat.NON_ATTACK)
        {
            combat.combatLog(attackerData.id, defenderData[0].id, "runHitEngine", "Non-Combat action - Skipping hitEngine");
            attackerResults.endPosture = getPosture(attackerData.id);
            for (int i = 0; i < defenderData.length; i++)
            {
                hitData[i] = new hit_result();
                if (i == 0)
                {
                    hitData[i].success = true;
                }
                else 
                {
                    hitData[i].success = false;
                }
                if (actionData.validTarget == combat.VALID_TARGET_FRIEND && pvpCanHelp(attackerData.id, defenderData[i].id))
                {
                    pvpHelpPerformed(attackerData.id, defenderData[i].id);
                    combat.applyDefenderCombatBuffs(attackerData.id, defenderData[i].id, weaponData, actionData);
                }
                defenderResults[i] = new defender_results();
                defenderResults[i].id = defenderData[i].id;
                defenderResults[i].endPosture = getPosture(defenderData[i].id);
            }
            return hitData;
        }
        boolean isHeavyWeapon = combat.isHeavyWeapon(weaponData);
        int attackerLevel = getLevel(attackerData.id);
        boolean criticalHit = false;
        boolean seriesStrikethrough = false;
        attackerData = combat.fillAttackerCombatAttributes(attackerData);
        if (utils.hasScriptVar(attackerData.id, "nextCritHit") && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
        {
            actionData.increaseCritical += 100.0f;
            actionData.reduceParry += 100;
            actionData.reduceDodge += 100;
            actionData.reduceBlock += 100;
            actionData.reduceGlancing += 100;
            actionData.increaseStrikethrough -= 100;
        }
        utils.removeScriptVar(attackerData.id, PROGRESSIVE_DAMAGE_COUNTER);
        if (combat.isCommandoBonus(attackerData.id, weaponData, actionData.commandType))
        {
            float devastationBonus = combat.getDevastationChance(attackerData.id);
            if (rand(0.0f, 99.0f) < devastationBonus)
            {
                weaponData.minDamage = weaponData.maxDamage;
                weaponData.maxDamage *= 1.5f;
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("combat_effects", "devastation"), 1.5f, colors.VIOLETRED);
            }
            if (actionData.buffNameTarget.length() == 0 || actionData.buffNameTarget.equals(""))
            {
                int passiveDotBonus = 10;
                passiveDotBonus += getEnhancedSkillStatisticModifierUncapped(attackerData.id, "commando_passive_dot");
                if (rand(0, 99) < passiveDotBonus)
                {
                    String commandoDotName = heavyweapons.getHeavyWeaponDotName(attackerData.id, weaponData, true);
                    if (commandoDotName != null && !commandoDotName.equals(""))
                    {
                        actionData.buffNameTarget = commandoDotName;
                    }
                }
            }
        }
        boolean isPlayerAttacker = isPlayer(attackerData.id);
        int intWeaponAttackType = getWeaponAttackType(weaponData.id);
        boolean isRanged = (intWeaponAttackType == ATTACK_TYPE_RANGED);
        boolean specialAttack = actionData.commandType == combat.RIGHT_CLICK_SPECIAL;
        int critDamageIncrease = getEnhancedSkillStatisticModifierUncapped(attackerData.id, "expertise_critical_damage_increase");
        for (int i = 0; i < defenderData.length; i++)
        {
            float minDamage = (float)weaponData.minDamage;
            float maxDamage = (float)weaponData.maxDamage;
            hitData[i] = new hit_result();
            float distance = 0;
            boolean isPlayerDefender = isPlayer(defenderData[i].id);
            obj_id oldDefender = defenderData[i].id;
            if (hitType != combat.NON_DAMAGE_ATTACK)
            {
                obj_id newDefender = combat.directDamageToDifferentTarget(attackerData.id, defenderData[i].id);
                if (oldDefender != newDefender)
                {
                    defenderData[i].id = newDefender;
                }
            }
            defenderData[i] = combat.fillDefenderCombatAttributes(defenderData[i]);
            int defenderLevel = getLevel(defenderData[i].id);
            if (hitType == combat.NON_DAMAGE_ATTACK)
            {
                hitData[i].success = true;
            }
            else 
            {
                int defResult = getDefenderResult(attackerData, defenderData[i], actionData, isAutoAiming);
                int atkResult = getAttackerResult(attackerData, defenderData[i], actionData, isAutoAiming);
                switch (defResult)
                {
                    case HIT_RESULT_DODGE:
                    hitData[i].success = false;
                    hitData[i].dodge = true;
                    break;
                    case HIT_RESULT_PARRY:
                    hitData[i].success = false;
                    hitData[i].parry = true;
                    break;
                    case HIT_RESULT_GLANCING:
                    hitData[i].success = true;
                    hitData[i].glancing = true;
                    break;
                    case HIT_RESULT_BLOCK:
                    hitData[i].success = true;
                    hitData[i].blockResult = true;
                    break;
                    case HIT_RESULT_EVADE:
                    hitData[i].success = true;
                    hitData[i].evadeResult = true;
                    break;
                    case HIT_RESULT_HIT:
                    hitData[i].success = true;
                    break;
                    default:
                    hitData[i].success = true;
                    break;
                }
                if (hitData[i].success == true)
                {
                    switch (atkResult)
                    {
                        case HIT_RESULT_MISS:
                        hitData[i].success = false;
                        hitData[i].miss = true;
                        break;
                        case HIT_RESULT_CRITICAL:
                        hitData[i].success = true;
                        hitData[i].critical = true;
                        hitData[i].glancing = false;
                        break;
                        case HIT_RESULT_PUNISHING:
                        hitData[i].success = true;
                        hitData[i].crushing = true;
                        break;
                        case HIT_RESULT_STRIKETHROUGH:
                        hitData[i].success = true;
                        hitData[i].strikethrough = true;
                        hitData[i].glancing = false;
                        break;
                    }
                }
            }
            int[] actionCost = combat.getSuccessBasedSingleTargetActionCost(hitData[i], actionData, attackerData.id, weaponData);
            combat.drainCombatActionAttributes(attackerData.id, actionCost);
            int validTargetType = actionData.validTarget;
            if (validTargetType == combat.VALID_TARGET_FRIEND && pvpCanHelp(attackerData.id, defenderData[i].id))
            {
                pvpHelpPerformed(attackerData.id, defenderData[i].id);
            }
            else if (pvpCanAttack(attackerData.id, defenderData[i].id))
            {
                pvpAttackPerformed(attackerData.id, defenderData[i].id);
            }
            defenderResults[i] = new defender_results();
            defenderResults[i].id = defenderData[i].id;
            defenderResults[i].endPosture = getPosture(defenderData[i].id);
            if (validTargetType != combat.VALID_TARGET_FRIEND && defenderData[i].id != attackerData.id && hitData[i].success)
            {
                startCombat(defenderData[i].id, attackerData.id);
                if (oldDefender != defenderData[i].id)
                {
                    startCombat(oldDefender, attackerData.id);
                }
            }
            if (combat.isDamageImmune(attackerData.id, defenderData[i].id, actionData))
            {
                if (defenderData.length == 1)
                {
                    actionData.hitSpam = combat.NO_HIT_SPAM;
                }
                continue;
            }
            if (combat.hasPrescience(attackerData.id, defenderData[i].id, actionData))
            {
                if (defenderData.length == 1)
                {
                    actionData.hitSpam = combat.NO_HIT_SPAM;
                }
                continue;
            }
            if (!hitData[i].success)
            {
                target_dummy.sendTargetDummyCombatData(attackerData.id, defenderData[i].id, actionData, hitData[i]);
            }
            if (hitData[i].success)
            {
                defenderResults[i].result = COMBAT_RESULT_HIT;
                if (beast_lib.isBeast(attackerData.id))
                {
                    addHate(defenderData[i].id, getMaster(attackerData.id), 0);
                }
                if (hitData[i].strikethrough)
                {
                    hitData[i].strikethroughAmmount = combat.getStrikethroughValue(attackerData.id, defenderData[i].id);
                    seriesStrikethrough |= true;
                }
                if (hitData[i].evadeResult)
                {
                    hitData[i].evadeAmmount = combat.getEvasionRoll(defenderData[i].id);
                }
                if (hitData[i].blockResult)
                {
                    hitData[i].block = combat.getBlockAmmount(defenderData[i].id);
                }
                if (hitData[i].crushing)
                {
                    minDamage = maxDamage * 1.5f;
                    maxDamage = (maxDamage * 1.5f) + 1.0f;
                }
                dictionary rawDict = getRawDamage(attackerData.id, weaponData, actionData, hitData[i], minDamage, maxDamage, defenderData.length);
                minDamage = rawDict.getFloat("minDamage");
                maxDamage = rawDict.getFloat("maxDamage");
                if (hitData[i].glancing)
                {
                    minDamage *= 0.35f;
                    maxDamage *= 0.35f;
                    weaponData.elementalValue *= 0.35f;
                    hitData[i].critDamage = 1;
                    string_id critSpam = new string_id("combat_effects", "glancing_blow");
                    prose_package ppc = new prose_package();
                    ppc = prose.setStringId(ppc, critSpam);
                    showFlyTextPrivateProseWithFlags(defenderData[i].id, defenderData[i].id, ppc, 1.5f, colors.LIMEGREEN, FLY_TEXT_FLAG_IS_GLANCING_BLOW);
                    hasSkill(defenderData[i].id, "expertise_fs_general_alacrity_1");
                    playClientEffectObj(defenderData[i].id, "appearance/pt_jedi_alacrity.prt", defenderData[i].id, "");
                }
                if (hitData[i].critical)
                {
                    float damageMod = combat.doCriticalHitEffect(attackerData, defenderData[i], weaponData, hitData[i], actionData);
                    float critBonus = 1.0f + ((float)critDamageIncrease / 100.0f);
                    damageMod += (float)critDamageIncrease / 100.0f;
                    minDamage = maxDamage * critBonus;
                    maxDamage = maxDamage * damageMod;
                    criticalHit = true;
                }
                hitData[i].damage = rand((int)minDamage, (int)maxDamage);
                if (hitType != combat.NON_DAMAGE_ATTACK)
                {
                    hitData[i] = applyDamage(attackerData.id, defenderData[i].id, weaponData, hitData[i], actionData, (i == 0), overloadDamage);
                }
                else 
                {
                    hitData[i].damage = 0;
                }
                effect_data defenderEffect = new effect_data();
                combat.applyDefenderCombatBuffs(attackerData.id, defenderData[i].id, weaponData, actionData);
                beast_lib.checkForSkillAcquisition(attackerData.id, defenderData[i].id, actionData.actionName);
                if (!actionData.specialLine.equals("no_proc"))
                {
                    proc.executeProcEffects(attackerData.id, defenderData[i].id, actionData);
                }
                else 
                {
                    hitData[i].proc = true;
                }
                if (actionData.effectOnTarget != null && actionData.effectOnTarget != "")
                {
                    playClientEffectObj(defenderData[i].id, actionData.effectOnTarget, defenderData[i].id, "");
                }
                int luckyBreakChance = getSkillStatisticModifier(attackerData.id, "expertise_lucky_break_chance");
                if (luckyBreakChance > 0 && buff.hasBuff(attackerData.id, "sm_feeling_lucky") && !buff.hasBuff(attackerData.id, "sm_lucky_break") && !buff.hasBuff(attackerData.id, "sm_lucky_break_recourse") && rand(0, 99) < luckyBreakChance)
                {
                    buff.applyBuff(attackerData.id, "sm_lucky_break");
                    string_id luckySID = new string_id("cbt_spam", "sm_lucky_break_proc");
                    sendCombatSpamMessage(attackerData.id, defenderData[i].id, luckySID, true, true, true, COMBAT_RESULT_BUFF);
                }
                if (buff.hasBuff(attackerData.id, "sm_lucky_break"))
                {
                    int doubleHitChance = (int)getSkillStatisticModifier(attackerData.id, "expertise_double_hit_chance");
                    if (doubleHitChance > 0 && !buff.hasBuff(attackerData.id, "sm_double_hit") && !buff.hasBuff(attackerData.id, "sm_double_hit_recourse") && rand(0, 99) < doubleHitChance)
                    {
                        buff.applyBuff(attackerData.id, "sm_double_hit");
                        string_id doubleHitSID = new string_id("cbt_spam", "sm_double_hit_proc");
                        sendCombatSpamMessage(attackerData.id, defenderData[i].id, doubleHitSID, true, true, true, COMBAT_RESULT_BUFF);
                    }
                }
            }
            else if (hitData[i].dodge)
            {
                if (!hasScript(defenderData[i].id, "player.player_countdown") && !movement.hasMovementModifier(defenderData[i].id))
                {
                    defenderResults[i].result = COMBAT_RESULT_EVADE;
                }
                else 
                {
                    defenderResults[i].result = COMBAT_RESULT_MISS;
                }
            }
            else if (hitData[i].parry)
            {
                defenderResults[i].result = COMBAT_RESULT_BLOCK;
                weapon_data attackerWeapon = getWeaponData(getCurrentWeapon(attackerData.id));
                weapon_data defenderWeapon = getWeaponData(getCurrentWeapon(defenderData[i].id));
                boolean isRangedAttacker = false;
                boolean isLightSaber = false;
                if (attackerWeapon != null)
                {
                    isRangedAttacker = combat.isRangedWeapon(attackerWeapon.weaponType);
                }
                if (defenderWeapon != null)
                {
                    isLightSaber = jedi.isLightsaber(defenderWeapon.id);
                }
                if (isRangedAttacker && isLightSaber)
                {
                    defenderResults[i].result = COMBAT_RESULT_LIGHTSABER_BLOCK;
                }
                if (!actionData.specialLine.equals("no_proc"))
                {
                    proc.executeProcEffects(attackerData.id, defenderData[i].id, actionData);
                }
                else 
                {
                    hitData[i].proc = true;
                }
                if (isLightSaber && buff.hasBuff(defenderData[i].id, "fs_saber_reflect") && !strAttack.equals("fs_saber_reflect"))
                {
                    queueCommand(defenderData[i].id, (1345072218), attackerData.id, "", COMMAND_PRIORITY_DEFAULT);
                }
                int addDefenderDamageToAction = getSkillStatisticModifier(defenderData[i].id, "expertise_damage_add_to_action");
                if (addDefenderDamageToAction > 0)
                {
                    float halfOfMod = addDefenderDamageToAction / 2.0f;
                    int actionToAdd = Math.round((halfOfMod / 100.0f) * (float)getMaxAction(defenderData[i].id));
                    combat.gainCombatActionAttribute(defenderData[i].id, actionToAdd);
                }
            }
            else 
            {
                defenderResults[i].result = COMBAT_RESULT_MISS;
            }
            if (defenderResults[i].result != COMBAT_RESULT_TETHERED)
            {
                combat.addHateProcess(attackerData.id, defenderData[i].id, hitData[i], actionData);
            }
            playbackNames[i] = combat.getActionAnimation(actionData, combat.getWeaponStringType(weaponData.weaponType));
            if (hitType == combat.NON_DAMAGE_ATTACK && hasScript(defenderData[i].id, "player.player_logout"))
            {
                defenders[i] = null;
            }
            else 
            {
                defenders[i] = defenderData[i].id;
            }
            results[i] = defenderResults[i].result;
        }
        if (criticalHit && utils.hasScriptVar(attackerData.id, "critRemoveBuffNames") && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
        {
            utils.removeScriptVar(attackerData.id, "nextCritHit");
            Vector buffNames = utils.getResizeableStringArrayScriptVar(attackerData.id, "critRemoveBuffNames");
            buff.removeBuffs(attackerData.id, buffNames);
        }
        if (seriesStrikethrough)
        {
            int stproc = getEnhancedSkillStatisticModifierUncapped(attackerData.id, "expertise_onstrikethrough_proc_sp_resonance");
            if (stproc > 0)
            {
                buff.partyBuff(attackerData.id, "sp_critbuff_resonance");
            }
        }
        if (criticalHit)
        {
            int critproc = getEnhancedSkillStatisticModifierUncapped(attackerData.id, "expertise_oncrit_proc_sp_savagery");
            if (critproc > 0)
            {
                buff.partyBuff(attackerData.id, "sp_critbuff_savagery");
            }
        }
        if (utils.hasScriptVar(attackerData.id, buff.ON_ATTACK_REMOVE) && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
        {
            Vector buffList = utils.getResizeableStringArrayScriptVar(attackerData.id, buff.ON_ATTACK_REMOVE);
            for (int i = 0; i < buffList.size(); i++)
            {
                if (buff.hasBuff(attackerData.id, ((String)buffList.get(i))))
                {
                    buff.removeBuff(attackerData.id, ((String)buffList.get(i)));
                }
            }
        }
        if (actionData.hitSpam != combat.NO_HIT_SPAM)
        {
            obj_id currentWeapon = null;
            if (actionData.hitSpam == combat.ACTION_NAME)
            {
                string_id attackId = new string_id("cmd_n", actionData.actionName);
                combat.doBasicCombatSpam(attackId, SID_NONE, currentWeapon, attackerResults, defenderResults, hitData);
            }
            else if (actionData.hitSpam == combat.WEAPON_NAME)
            {
                if (weaponData.weaponName != null)
                {
                    combat.doBasicCombatSpam(SID_NONE, weaponData.weaponName, currentWeapon, attackerResults, defenderResults, hitData);
                }
                else if (isValidId(weaponData.id))
                {
                    combat.doBasicCombatSpam(SID_NONE, weaponData.weaponName, weaponData.id, attackerResults, defenderResults, hitData);
                }
                else if (isValidId(attackerResults.weapon))
                {
                    combat.doBasicCombatSpam(SID_NONE, weaponData.weaponName, attackerResults.weapon, attackerResults, defenderResults, hitData);
                }
                else if (isValidId(getCurrentWeapon(attackerResults.id)))
                {
                    combat.doBasicCombatSpam(SID_NONE, weaponData.weaponName, getCurrentWeapon(attackerResults.id), attackerResults, defenderResults, hitData);
                }
                else 
                {
                    combat.doBasicCombatSpam(SID_NONE, SID_NONE, currentWeapon, attackerResults, defenderResults, hitData);
                }
            }
            else 
            {
                combat.doBasicCombatSpam(SID_NONE, SID_NONE, currentWeapon, attackerResults, defenderResults, hitData);
            }
        }
        if (actionData.hitType != 6 || actionData.delayAttackLoopsDone == 0)
        {
            doMultiDefenderCombatResults(playbackNames, attackerResults, defenderResults, weaponData, actionData, hitData);
        }
        int rampageAttacks = getEnhancedSkillStatisticModifierUncapped(attackerData.id, "expertise_co_killing_spree_target");
        if (actionData.commandType == combat.LEFT_CLICK_DEFAULT && buff.hasBuff(attackerData.id, "co_killing_spree") && rampageAttacks > 0)
        {
            obj_id[] hateList = getHateList(attackerData.id);
            for (int q = 0; q < rampageAttacks; q++)
            {
                if (hateList != null && hateList.length > 0)
                {
                    queueCommand(attackerData.id, (-410474913), hateList[rand(0, hateList.length - 1)], "", COMMAND_PRIORITY_IMMEDIATE);
                }
            }
        }
        callDefenderCombatAction(defenders, results, attackerData.id, weaponData.id);
        return hitData;
    }
    public int getDefenderResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        if (combat.isAreaAttack(actionData.attackType))
        {
            return getAreaDefenderResult(attackerData, defenderData, actionData, autoAim);
        }
        else 
        {
            return getSingleTargetDefenderResult(attackerData, defenderData, actionData, autoAim);
        }
    }
    public int getAttackerResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        if (combat.isAreaAttack(actionData.attackType))
        {
            return getAreaAttackResult(attackerData, defenderData, actionData, autoAim);
        }
        else 
        {
            return getSingleTargetAttackResult(attackerData, defenderData, actionData, autoAim);
        }
    }
    public int getAreaDefenderResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        if (movement.hasStunEffect(defenderData.id))
        {
            return HIT_RESULT_HIT;
        }
        int glancingBlow = Math.round(10.0f * combat.getGlancingBlowChance(attackerData, defenderData, actionData));
        int evasionChance = Math.round(10.0f * combat.getEvasionChance(attackerData, defenderData));
        int blockChance = Math.round(10.0f * combat.getBlockChance(attackerData, defenderData, actionData));
        int loaded_dice = getEnhancedSkillStatisticModifierUncapped(defenderData.id, "hit_table_add_defender_position");
        int impossible_odds = getEnhancedSkillStatisticModifierUncapped(defenderData.id, "hit_table_defender_add_hit");
        glancingBlow = glancingBlow < 0 ? 0 : glancingBlow;
        evasionChance = evasionChance < 0 ? 0 : evasionChance;
        blockChance = blockChance < 0 ? 0 : blockChance;
        loaded_dice = loaded_dice < 0 ? 0 : loaded_dice;
        impossible_odds = impossible_odds < 0 ? 0 : impossible_odds;
        evasionChance = evasionChance >= 1000 ? 1000 : evasionChance;
        blockChance = blockChance + evasionChance < 1000 ? blockChance + evasionChance : 1000;
        glancingBlow = glancingBlow + blockChance < 1000 ? glancingBlow + blockChance : 1000;
        impossible_odds = blockChance + impossible_odds < 1000 + impossible_odds ? blockChance + impossible_odds : 1000 + impossible_odds;
        int defRollResult = rand(1, 1000) + loaded_dice;
        if (defRollResult <= evasionChance)
        {
            return HIT_RESULT_EVADE;
        }
        if (defRollResult <= blockChance)
        {
            return HIT_RESULT_BLOCK;
        }
        if (defRollResult <= glancingBlow)
        {
            return HIT_RESULT_GLANCING;
        }
        if (defRollResult <= impossible_odds)
        {
            return HIT_RESULT_HIT;
        }
        return HIT_RESULT_HIT;
    }
    public int getAreaAttackResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        boolean attackCanPunish = actionData.canBePunishing == 1;
        boolean mobCanPunish = utils.getIntScriptVar(attackerData.id, "combat.immune.no_punish") == 0 ? true : false;
        int punishingBlow = 0;
        if (attackCanPunish && mobCanPunish)
        {
            punishingBlow = Math.round(10.0f * combat.getPunishingBlowChance(attackerData, defenderData));
        }
        punishingBlow = punishingBlow < 0 ? 0 : punishingBlow;
        int criticalChance = Math.round(10.0f * combat.getCriticalHitChance(attackerData, defenderData, actionData, autoAim));
        criticalChance = criticalChance < 0 ? 0 : criticalChance;
        int length = 1000 - punishingBlow < 0 ? 0 : 1000 - punishingBlow;
        criticalChance = criticalChance >= length ? length : criticalChance;
        punishingBlow += criticalChance;
        int attackRollResult = rand(1, 1000);
        if (attackRollResult <= criticalChance)
        {
            return HIT_RESULT_CRITICAL;
        }
        if (attackRollResult <= punishingBlow)
        {
            return HIT_RESULT_PUNISHING;
        }
        return HIT_RESULT_HIT;
    }
    public int getSingleTargetDefenderResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        int loaded_dice = getEnhancedSkillStatisticModifierUncapped(defenderData.id, "hit_table_add_defender_position");
        int impossible_odds = getEnhancedSkillStatisticModifierUncapped(defenderData.id, "hit_table_defender_add_hit");
        int hit_always = getEnhancedSkillStatisticModifierUncapped(attacker, "hit_always");
        if (hit_always > 0)
        {
            return HIT_RESULT_HIT;
        }
        if (movement.hasStunEffect(defenderData.id))
        {
            return HIT_RESULT_HIT;
        }
        weapon_data attackerWeapon = getWeaponData(getCurrentWeapon(attacker));
        weapon_data defenderWeapon = getWeaponData(getCurrentWeapon(defender));
        boolean isRangedAttacker = false;
        boolean isRangedDefender = false;
        boolean isLightSaber = false;
        if (attackerWeapon != null)
        {
            isRangedAttacker = combat.isRangedWeapon(attackerWeapon.weaponType);
        }
        if (defenderWeapon != null)
        {
            isRangedDefender = combat.isRangedWeapon(defenderWeapon.weaponType);
            isLightSaber = jedi.isLightsaber(defenderWeapon.id);
        }
        int glancingBlow = Math.round(10.0f * combat.getGlancingBlowChance(attackerData, defenderData, actionData));
        glancingBlow = glancingBlow < 0 ? 0 : glancingBlow;
        int dodgeChance = Math.round(10.0f * combat.getDodgeChance(attackerData, defenderData, actionData));
        dodgeChance = dodgeChance < 0 ? 0 : dodgeChance;
        int parryChance = Math.round(10.0f * combat.getParryChance(attackerData, defenderData, actionData));
        parryChance = parryChance < 0 ? 0 : parryChance;
        int blockChance = Math.round(10.0f * combat.getBlockChance(attackerData, defenderData, actionData));
        blockChance = blockChance < 0 ? 0 : blockChance;
        if (isRangedDefender || (isRangedAttacker && !isLightSaber))
        {
            parryChance = 0;
        }
        dodgeChance = dodgeChance >= 1000 ? 1000 : dodgeChance;
        parryChance = dodgeChance + parryChance < 1000 ? dodgeChance + parryChance : 1000;
        blockChance = blockChance + parryChance < 1000 ? blockChance + parryChance : 1000;
        glancingBlow = glancingBlow + blockChance < 1000 ? glancingBlow + blockChance : 1000;
        impossible_odds = blockChance + impossible_odds < 1000 + impossible_odds ? blockChance + impossible_odds : 1000 + impossible_odds;
        int defRollResult = rand(1, 1000) + loaded_dice;
        if (defRollResult <= dodgeChance)
        {
            return HIT_RESULT_DODGE;
        }
        if (defRollResult <= parryChance)
        {
            return HIT_RESULT_PARRY;
        }
        if (defRollResult <= blockChance)
        {
            return HIT_RESULT_BLOCK;
        }
        if (defRollResult <= glancingBlow)
        {
            return HIT_RESULT_GLANCING;
        }
        if (defRollResult <= impossible_odds)
        {
            return HIT_RESULT_HIT;
        }
        return HIT_RESULT_HIT;
    }
    public int getSingleTargetAttackResult(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        int crit_always = getEnhancedSkillStatisticModifierUncapped(attacker, "crit_always");
        if (crit_always > 0)
        {
            return HIT_RESULT_CRITICAL;
        }
        weapon_data attackerWeapon = getWeaponData(getCurrentWeapon(attacker));
        weapon_data defenderWeapon = getWeaponData(getCurrentWeapon(defender));
        boolean isRangedAttacker = false;
        boolean isRangedDefender = false;
        boolean isLightSaber = false;
        if (attackerWeapon != null)
        {
            isRangedAttacker = combat.isRangedWeapon(attackerWeapon.weaponType);
        }
        if (defenderWeapon != null)
        {
            isRangedDefender = combat.isRangedWeapon(defenderWeapon.weaponType);
            isLightSaber = jedi.isLightsaber(defenderWeapon.id);
        }
        int missChance = Math.round(10.0f * combat.getMissChance(attackerData, defenderData, actionData, autoAim));
        missChance = missChance < 0 ? 0 : missChance;
        int punishingBlow = Math.round(10.0f * combat.getPunishingBlowChance(attackerData, defenderData));
        punishingBlow = punishingBlow < 0 ? 0 : punishingBlow;
        int criticalChance = Math.round(10.0f * combat.getCriticalHitChance(attackerData, defenderData, actionData, autoAim));
        criticalChance = criticalChance < 0 ? 0 : criticalChance;
        int strikethroughChance = Math.round(10.0f * combat.getStrikethroughChance(attackerData, defenderData, actionData));
        strikethroughChance = strikethroughChance < 0 ? 0 : strikethroughChance;
        int length = 1000 - punishingBlow < 0 ? 0 : 1000 - punishingBlow;
        missChance = missChance > length ? length : missChance;
        strikethroughChance = strikethroughChance + missChance < length ? strikethroughChance + missChance : length;
        criticalChance = criticalChance + strikethroughChance < length ? criticalChance + strikethroughChance : length;
        punishingBlow += criticalChance;
        int attackRollResult = rand(1, 1000);
        int stvalue = strikethroughChance - missChance;
        int critvalue = criticalChance - strikethroughChance;
        if (attackRollResult <= missChance)
        {
            return HIT_RESULT_MISS;
        }
        if (attackRollResult <= strikethroughChance)
        {
            return HIT_RESULT_STRIKETHROUGH;
        }
        if (attackRollResult <= criticalChance)
        {
            return HIT_RESULT_CRITICAL;
        }
        if (attackRollResult <= punishingBlow)
        {
            return HIT_RESULT_PUNISHING;
        }
        return HIT_RESULT_HIT;
    }
    public void displayHitTable(obj_id attacker, obj_id defender, int[] defTable, int[] atkTable, int defResult, int atkResult) throws InterruptedException
    {
        if (!isGod(attacker) && !isGod(defender))
        {
            return;
        }
        int miss = 0;
        int dodge = 0;
        int parry = 0;
        int glancing = 0;
        int block = 0;
        int critical = 0;
        int punishing = 0;
        int hit = 0;
        int evade = 0;
        int strikethrough = 0;
        for (int i = 0; i < defTable.length; i++)
        {
            switch (defTable[i])
            {
                case HIT_RESULT_MISS:
                miss++;
                break;
                case HIT_RESULT_DODGE:
                dodge++;
                break;
                case HIT_RESULT_PARRY:
                parry++;
                break;
                case HIT_RESULT_GLANCING:
                glancing++;
                break;
                case HIT_RESULT_BLOCK:
                block++;
                break;
                case HIT_RESULT_CRITICAL:
                critical++;
                break;
                case HIT_RESULT_PUNISHING:
                punishing++;
                break;
                case HIT_RESULT_HIT:
                hit++;
                break;
                case HIT_RESULT_EVADE:
                evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                strikethrough++;
                break;
            }
        }
        for (int i = 0; i < atkTable.length; i++)
        {
            switch (atkTable[i])
            {
                case HIT_RESULT_MISS:
                miss++;
                break;
                case HIT_RESULT_DODGE:
                dodge++;
                break;
                case HIT_RESULT_PARRY:
                parry++;
                break;
                case HIT_RESULT_GLANCING:
                glancing++;
                break;
                case HIT_RESULT_BLOCK:
                block++;
                break;
                case HIT_RESULT_CRITICAL:
                critical++;
                break;
                case HIT_RESULT_PUNISHING:
                punishing++;
                break;
                case HIT_RESULT_HIT:
                hit++;
                break;
                case HIT_RESULT_EVADE:
                evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                strikethrough++;
                break;
            }
        }
        if (isPlayer(attacker))
        {
            LOG("atkHitTable", "Forming Hit Table");
            LOG("atkHitTable", getName(attacker) + " Vs. " + getName(defender));
            LOG("atkHitTable", "Miss " + miss);
            LOG("atkHitTable", "Dodge " + dodge);
            LOG("atkHitTable", "Parry " + parry);
            LOG("atkHitTable", "Glancing " + glancing);
            LOG("atkHitTable", "Block " + block);
            LOG("atkHitTable", "Critical " + critical);
            LOG("atkHitTable", "Punishing " + punishing);
            LOG("atkHitTable", "Hit " + hit);
            LOG("atkHitTable", "Evade " + evade);
            LOG("atkHitTable", "Strikethrough " + strikethrough);
            LOG("atkHitTable", "Defender Result was " + getHitResultByName(defResult));
            LOG("atkHitTable", "Attacker Results was " + getHitResultByName(atkResult));
            LOG("atkHitTable", "xx");
        }
        if (!isPlayer(attacker))
        {
            LOG("defHitTabel", "Forming Hit Table");
            LOG("defHitTable", getName(attacker) + " Vs. " + getName(defender));
            LOG("defHitTable", "Miss " + miss);
            LOG("defHitTable", "Dodge " + dodge);
            LOG("defHitTable", "Parry " + parry);
            LOG("defHitTable", "Glancing " + glancing);
            LOG("defHitTable", "Block " + block);
            LOG("defHitTable", "Critical " + critical);
            LOG("defHitTable", "Punishing " + punishing);
            LOG("defHitTable", "Hit " + hit);
            LOG("defHitTable", "Evade " + evade);
            LOG("defHitTable", "Strikethrough " + strikethrough);
            LOG("defHitTable", "Defender Result was " + getHitResultByName(defResult));
            LOG("defHitTable", "Attacker Results was " + getHitResultByName(atkResult));
            LOG("defHitTable", "xx");
        }
        if (hasObjVar(attacker, "viewHitTable"))
        {
            sendSystemMessageTestingOnly(attacker, "Forming Hit Table");
            sendSystemMessageTestingOnly(attacker, getName(attacker) + " Attacking. " + getName(defender));
            sendSystemMessageTestingOnly(attacker, "Miss " + miss);
            sendSystemMessageTestingOnly(attacker, "Dodge " + dodge);
            sendSystemMessageTestingOnly(attacker, "Parry " + parry);
            sendSystemMessageTestingOnly(attacker, "Glancing " + glancing);
            sendSystemMessageTestingOnly(attacker, "Block " + block);
            sendSystemMessageTestingOnly(attacker, "Critical " + critical);
            sendSystemMessageTestingOnly(attacker, "Punishing " + punishing);
            sendSystemMessageTestingOnly(attacker, "Hit " + hit);
            sendSystemMessageTestingOnly(attacker, "Evade " + evade);
            sendSystemMessageTestingOnly(attacker, "Strikethrough " + strikethrough);
            sendSystemMessageTestingOnly(attacker, "" + getHitResultByName(defResult));
            sendSystemMessageTestingOnly(attacker, "" + getHitResultByName(atkResult));
            sendSystemMessageTestingOnly(attacker, "xx");
        }
        if (hasObjVar(defender, "viewHitTable"))
        {
            sendSystemMessageTestingOnly(defender, "Forming Hit Table");
            sendSystemMessageTestingOnly(defender, getName(attacker) + " Attacking. " + getName(defender));
            sendSystemMessageTestingOnly(defender, "Miss " + miss);
            sendSystemMessageTestingOnly(defender, "Dodge " + dodge);
            sendSystemMessageTestingOnly(defender, "Parry " + parry);
            sendSystemMessageTestingOnly(defender, "Glancing " + glancing);
            sendSystemMessageTestingOnly(defender, "Block " + block);
            sendSystemMessageTestingOnly(defender, "Critical " + critical);
            sendSystemMessageTestingOnly(defender, "Punishing " + punishing);
            sendSystemMessageTestingOnly(defender, "Hit " + hit);
            sendSystemMessageTestingOnly(defender, "Evade " + evade);
            sendSystemMessageTestingOnly(defender, "Strikethrough " + strikethrough);
            sendSystemMessageTestingOnly(defender, "" + getHitResultByName(defResult));
            sendSystemMessageTestingOnly(defender, "" + getHitResultByName(atkResult));
            sendSystemMessageTestingOnly(defender, "xx");
        }
    }
    public String getHitResultByName(int hitResult) throws InterruptedException
    {
        String hitType = "None";
        switch (hitResult)
        {
            case HIT_RESULT_MISS:
            hitType = "Miss";
            break;
            case HIT_RESULT_DODGE:
            hitType = "Dodge";
            break;
            case HIT_RESULT_PARRY:
            hitType = "Parry";
            break;
            case HIT_RESULT_GLANCING:
            hitType = "Glancing";
            break;
            case HIT_RESULT_BLOCK:
            hitType = "Block";
            break;
            case HIT_RESULT_CRITICAL:
            hitType = "Critical";
            break;
            case HIT_RESULT_PUNISHING:
            hitType = "Punishing";
            break;
            case HIT_RESULT_HIT:
            hitType = "Hit";
            break;
            case HIT_RESULT_EVADE:
            hitType = "Evade";
            break;
            case HIT_RESULT_STRIKETHROUGH:
            hitType = "Strikethrough";
            break;
        }
        return hitType;
    }
    public hit_result applyDamage(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, combat_data actionData, boolean primaryTarget) throws InterruptedException
    {
        return applyDamage(attacker, defender, weaponData, hitData, actionData, primaryTarget, 0);
    }
    public hit_result applyDamage(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, combat_data actionData, boolean primaryTarget, int overloadDamage) throws InterruptedException
    {
        doWrappedDamage(attacker, defender, weaponData, hitData, actionData, overloadDamage);
        return hitData;
    }
    public weapon_data getOverloadedWeaponData(obj_id self, obj_id objWeapon, combat_data actionData) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return null;
        }
        if (!isIdValid(objWeapon))
        {
            return getWeaponData(getCurrentWeapon(self));
        }
        if (actionData.overloadWeapon > 0)
        {
            return weapons.fillWeaponData(objWeapon, actionData);
        }
        weapon_data weaponData = weapons.getNewWeaponData(objWeapon);
        if (weaponData == null)
        {
            CustomerServiceLog("combat_errors: ", "getOverloadedWeaponData weaponData: null for objWeapon: " + objWeapon + " getName(objWeapon): " + getName(objWeapon));
            weaponData = getWeaponData(getCurrentWeapon(self));
        }
        else 
        {
            weaponData.elementalValue = isPlayer(self) ? weaponData.elementalValue * 2 : weaponData.elementalValue;
        }
        return weaponData;
    }
    public dictionary getRawDamage(obj_id attacker, weapon_data weaponData, combat_data actionData, hit_result hitData, float minDamage, float maxDamage, int numTargets) throws InterruptedException
    {
        int addedDamage = actionData.addedDamage;
        if (actionData.attackType == combat.AREA_PROGRESSIVE)
        {
            addedDamage += utils.getIntScriptVar(attacker, PROGRESSIVE_DAMAGE_COUNTER);
            utils.setScriptVar(attacker, PROGRESSIVE_DAMAGE_COUNTER, addedDamage);
        }
        if (actionData.attackType == combat.SPLIT_DAMAGE_TARGET_AREA)
        {
            addedDamage = Math.round((float)addedDamage / (float)numTargets);
        }
        float percentAddFromWeapon = actionData.percentAddFromWeapon;
        if (!combat.checkWeaponCerts(attacker, weaponData, false))
        {
            combat.combatLog(attacker, null, "getRawDamage", "Weapon Certification Failed.  Modifying damage to 5 min, 10 max.");
            minDamage = 5.0f;
            maxDamage = 10.0f;
        }
        if (beast_lib.isBeastMaster(attacker) || beast_lib.isBeast(attacker))
        {
            minDamage = minDamage + (minDamage * (beast_lib.getBeastModDamageBonus(attacker) / 100.0f));
            maxDamage = maxDamage + (maxDamage * (beast_lib.getBeastModDamageBonus(attacker) / 100.0f));
        }
        combat.combatLog(attacker, null, "getRawDamage", "RAW DAMAGE Damage = " + minDamage + "-" + maxDamage);
        float speed = weaponData.attackSpeed;
        if (speed == 0 || (isMob(attacker) && beast_lib.isBeast(attacker)))
        {
            speed = 1.0f;
        }
        minDamage *= percentAddFromWeapon;
        maxDamage *= percentAddFromWeapon;
        if (addedDamage > 0)
        {
            float dps = ((minDamage + maxDamage) / 2.0f) / speed;
            float totalAddedDamage = dps + addedDamage;
            float normalizedSpecialDmg = totalAddedDamage - ((minDamage + maxDamage) / 2);
            minDamage += normalizedSpecialDmg;
            maxDamage += normalizedSpecialDmg;
        }
        float damageAddFromLevel = (float)getEnhancedSkillStatisticModifierUncapped(attacker, "level_add_to_damage");
        if (damageAddFromLevel > 0.0f)
        {
            float levelDamageMod = getLevel(attacker) * (damageAddFromLevel / 100.0f);
            minDamage += levelDamageMod * speed;
            maxDamage += levelDamageMod * speed;
        }
        if (getWeaponType(weaponData.id) >= 0)
        {
            if (combat.isMeleeWeapon(weaponData.id) || combat.isLightsaberWeapon(weaponData.id))
            {
                int strengthBonus = 0;
                strengthBonus += Math.round((float)getEnhancedSkillStatisticModifierUncapped(attacker, "strength_modified") / 3.0f);
                strengthBonus += Math.round((float)getSkillStatisticModifier(attacker, "strength") / 3.0f);
                minDamage += strengthBonus;
                maxDamage += strengthBonus;
            }
        }
        float enragedMod = utils.getFloatScriptVar(attacker, "damageDealtMod.value");
        if (enragedMod > 0)
        {
            minDamage *= enragedMod;
            maxDamage *= enragedMod;
        }
        combat.combatLog(attacker, null, "getRawDamage", "Final Modified Damage = " + minDamage + "-" + maxDamage);
        if (minDamage > maxDamage - 1)
        {
            minDamage = maxDamage - 1;
        }
        if (minDamage < 1)
        {
            minDamage = 1;
        }
        if (percentAddFromWeapon == 0.0)
        {
            weaponData.elementalValue = 0;
        }
        if (utils.hasScriptVar(attacker, "private_damage_bonus"))
        {
            minDamage = minDamage * (1 + utils.getFloatScriptVar(attacker, "private_damage_bonus"));
            maxDamage = maxDamage * (1 + utils.getFloatScriptVar(attacker, "private_damage_bonus"));
        }
        dictionary dict = new dictionary();
        dict.put("minDamage", minDamage);
        dict.put("maxDamage", maxDamage);
        return dict;
    }
    public int expertiseDamageModify(obj_id attacker, obj_id defender, hit_result hitData, combat_data actionData) throws InterruptedException
    {
        int newDamage = hitData.damage;
        int damageDecrease = getSkillStatisticModifier(attacker, "expertise_damage_decrease_chance");
        if (damageDecrease > 0)
        {
            float spotASuckerDecreaseChance = rand(0, damageDecrease) * 0.01f;
            if (spotASuckerDecreaseChance > 1.0f)
            {
                spotASuckerDecreaseChance = 1.0f;
            }
            newDamage *= 1.0f - spotASuckerDecreaseChance;
        }
        float smugglerRankBonus = (float)getSkillStatisticModifier(attacker, "expertise_sm_rank_damage_bonus");
        if (smugglerRankBonus > 0)
        {
            smugglerRankBonus *= 0.01f;
            newDamage += newDamage * smugglerRankBonus;
        }
        int jediAnticipateAggression = getSkillStatisticModifier(defender, "expertise_damage_reduce_anticipate_aggression");
        if (jediAnticipateAggression > 0)
        {
            float conversionPercentage = jediAnticipateAggression / 100.0f;
            int oldDamage = hitData.damage;
            newDamage -= (int)(oldDamage * conversionPercentage);
            if (rand(0, 9) < 1)
            {
                playClientEffectObj(defender, "appearance/pt_jedi_anticipate_aggression.prt", defender, "root");
            }
        }
        damageDecrease = getSkillStatisticModifier(defender, "damage_decrease_percentage");
        if (damageDecrease > 0)
        {
            newDamage -= (int)(hitData.damage * (float)((float)damageDecrease / 100.0f));
        }
        int areaDamageDecrease = getSkillStatisticModifier(defender, "area_damage_decrease_percentage");
        if (areaDamageDecrease > 0 && combat.isAreaAttack(actionData.attackType))
        {
            newDamage -= (int)(hitData.damage * (float)((float)areaDamageDecrease / 100.0f));
        }
        int areaDamageResist = getSkillStatisticModifier(defender, "area_damage_resist_full_percentage");
        if (areaDamageResist > 0 && combat.isAreaAttack(actionData.attackType) && rand(0, 99) < areaDamageResist)
        {
            newDamage = 0;
        }
        if (hitData.critDamage > 0)
        {
            if (utils.hasScriptVar(attacker, "critDoubleDamage") && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
            {
                newDamage *= 2;
                playClientEffectObj(defender, "appearance/pt_smuggler_end_line.prt", defender, "");
            }
            if (utils.hasScriptVar(attacker, "critRoot") && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
            {
                buff.applyBuff(defender, attacker, "sm_nerf_herder_root");
            }
        }
        newDamage = ai_lib.triggerSharedHealthLink(defender, newDamage);
        return newDamage;
    }
    public void doWrappedDamage(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, combat_data actionData) throws InterruptedException
    {
        doWrappedDamage(attacker, defender, weaponData, hitData, actionData, 0);
    }
    public void clog(String message) throws InterruptedException
    {
        debugSpeakMsg(getSelf(), message);
    }
    public void doWrappedDamage(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, combat_data actionData, int overloadDamage) throws InterruptedException
    {
        boolean dmgSpecial = false;
        boolean evade = hitData.evadeResult;
        boolean block = hitData.blockResult;
        float armorBypass = actionData.bypassArmor;
        float siphonLife = actionData.convertDamageToHealth;
        hitData.rawDamage = hitData.damage;
        int maxDamage = hitData.rawDamage + weaponData.elementalValue;
        String dotType = actionData.dotType;
        if (utils.hasScriptVar(defender, "elemental_vulnerability.type_heat"))
        {
            float value = utils.getFloatScriptVar(defender, "elemental_vulnerability.type_heat.value");
            boolean exclusive = utils.getBooleanScriptVar(defender, "elemental_vulnerability.type_heat.exclusive");
            if (exclusive)
            {
                hitData.rawDamage = 0;
                maxDamage = 0;
            }
            if (weaponData.elementalType == DAMAGE_ELEMENTAL_HEAT)
            {
                weaponData.elementalValue *= value;
            }
            else if (exclusive)
            {
                weaponData.elementalValue *= 0.0f;
            }
        }
        if (utils.hasScriptVar(defender, "elemental_vulnerability.type_electrical"))
        {
            float value = utils.getFloatScriptVar(defender, "elemental_vulnerability.type_electrical.value");
            boolean exclusive = utils.getBooleanScriptVar(defender, "elemental_vulnerability.type_electrical.exclusive");
            if (exclusive)
            {
                hitData.rawDamage = 0;
                maxDamage = 0;
            }
            if (weaponData.elementalType == DAMAGE_ELEMENTAL_ELECTRICAL)
            {
                weaponData.elementalValue *= value;
            }
            else if (exclusive)
            {
                weaponData.elementalValue *= 0.0f;
            }
        }
        if (utils.hasScriptVar(defender, "elemental_vulnerability.type_cold"))
        {
            float value = utils.getFloatScriptVar(defender, "elemental_vulnerability.type_cold.value");
            boolean exclusive = utils.getBooleanScriptVar(defender, "elemental_vulnerability.type_cold.exclusive");
            if (exclusive)
            {
                hitData.rawDamage = 0;
                maxDamage = 0;
            }
            if (weaponData.elementalType == DAMAGE_ELEMENTAL_COLD)
            {
                weaponData.elementalValue *= value;
            }
            else if (exclusive)
            {
                weaponData.elementalValue *= 0.0f;
            }
        }
        if (utils.hasScriptVar(defender, "elemental_vulnerability.type_acid"))
        {
            float value = utils.getFloatScriptVar(defender, "elemental_vulnerability.type_acid.value");
            boolean exclusive = utils.getBooleanScriptVar(defender, "elemental_vulnerability.type_acid.exclusive");
            if (exclusive)
            {
                hitData.rawDamage = 0;
                maxDamage = 0;
            }
            if (weaponData.elementalType == DAMAGE_ELEMENTAL_ACID)
            {
                weaponData.elementalValue *= value;
            }
            else if (exclusive)
            {
                weaponData.elementalValue *= 0.0f;
            }
        }
        if (beast_lib.isBeastMaster(defender) || beast_lib.isBeast(defender))
        {
            hitData.rawDamage = hitData.rawDamage - (int)(hitData.rawDamage * (beast_lib.getBeastModDamageReduction(defender) / 100.0f));
        }
        int evadeReduction = 0;
        if (hitData.evadeResult)
        {
            float evadeValue = hitData.evadeAmmount;
            if (evadeValue == -1)
            {
                hitData.rawDamage = 0;
                weaponData.elementalValue = 0;
                hitData.evadeAmmount = 100.0f;
                dotType = null;
            }
            else 
            {
                float percentReduction = rand(evadeValue / 2.0f, evadeValue);
                evadeReduction = (int)(hitData.rawDamage * (percentReduction / 100.0f));
                weaponData.elementalValue = (int)(weaponData.elementalValue * (percentReduction / 100.0f));
                hitData.rawDamage -= evadeReduction;
                hitData.evadeAmmount = percentReduction;
            }
        }
        if (dotType != null && dotType.length() > 0)
        {
            int dotIntensity = actionData.dotIntensity;
            int dotDuration = actionData.dotDuration;
            if (dotIntensity == 0)
            {
                dotIntensity = (int)(hitData.rawDamage * actionData.percentAddFromWeapon);
            }
            String specialName = actionData.actionName;
            String specialLine = actionData.specialLine;
            int expertiseDotDurationBonus = 0;
            float expertiseDotDamageBonus = getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_damage_all");
            if (isMob(defender) && !isPlayer(defender) && !isIdValid(getMaster(defender)))
            {
                expertiseDotDamageBonus += getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_damage_npc");
            }
            if (specialLine != null && specialLine.length() > 0)
            {
                expertiseDotDurationBonus = getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_dot_duration_line_" + specialLine);
                expertiseDotDurationBonus += getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_dot_duration_single_" + specialName);
                expertiseDotDamageBonus += getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_dot_damage_line_" + specialLine);
            }
            float dotIntensityFloat = (float)dotIntensity * (1.0f + (expertiseDotDamageBonus / 100.0f));
            dotIntensity = (int)dotIntensityFloat;
            dotDuration += expertiseDotDurationBonus;
            int targetAttrib = dotType.equals("disease") ? ACTION : HEALTH;
            boolean rslt = dot.applyDotEffect(defender, attacker, dotType, dotType + "" + attacker, targetAttrib, 100, dotIntensity, dotDuration);
        }
        hitData.damage = (hitData.rawDamage);
        hitData.damageType = weaponData.damageType;
        if (actionData.overloadWeapon > 0)
        {
            hitData.damageType = actionData.overloadWeaponDamageType;
        }
        hitData.damage = expertiseDamageModify(attacker, defender, hitData, actionData);
        float expertiseDamageBonus = 0;
        obj_id expert = attacker;
        if (!isPlayer(attacker) && utils.hasScriptVar(attacker, "objOwner"))
        {
            expert = utils.getObjIdObjVar(attacker, "objOwner");
        }
        int killMeter = getKillMeter(expert);
        if (actionData.percentAddFromWeapon > 0.0f)
        {
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_weapon_all");
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_weapon_" + combat.getCorrectedWeaponType(weaponData.weaponType));
            expertiseDamageBonus += killMeter * getSkillStatisticModifier(expert, "kill_meter_damage_weapon_all");
        }
        expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_all");
        if (isMob(defender) && !isPlayer(defender) && !isIdValid(getMaster(defender)))
        {
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_damage_npc");
        }
        if (getWeaponType(weaponData.id) >= 0)
        {
            if ((combat.isMeleeWeapon(weaponData.id)) || (combat.isLightsaberWeapon(weaponData.id)))
            {
                expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_melee");
            }
            if (combat.isRangedWeapon(weaponData.id))
            {
                expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_ranged");
            }
        }
        String specialLine = actionData.specialLine;
        String actionName = actionData.actionName;
        if (specialLine != null && !specialLine.equals(""))
        {
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(defender, "expertise_damage_line_vulnerability_" + specialLine);
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_line_" + specialLine);
            expertiseDamageBonus += killMeter * getSkillStatisticModifier(expert, "kill_meter_damage_line_" + specialLine);
        }
        if (actionName != null && !actionName.equals(""))
        {
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_single_" + actionName);
        }
        if (actionName != null && !actionName.equals(""))
        {
            if (combat.isAreaAttack(actionData.attackType))
            {
                expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "expertise_damage_area_effect");
            }
        }
        if (hasObjVar(attacker, "specialNicheType"))
        {
            String subType = getStringObjVar(attacker, "specialNicheType");
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(defender, "increase_damage_taken_from_" + subType);
            expertiseDamageBonus -= getEnhancedSkillStatisticModifierUncapped(defender, "reduce_damage_taken_from_" + subType);
        }
        if (hasObjVar(defender, "specialNicheType"))
        {
            String subType = getStringObjVar(defender, "specialNicheType");
            expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(attacker, "increase_damage_dealt_to_" + subType);
            expertiseDamageBonus -= getEnhancedSkillStatisticModifierUncapped(attacker, "reduce_damage_dealt_to_" + subType);
        }
        if (overloadDamage > 0)
        {
            hitData.damage = overloadDamage;
            armorBypass = 100.0f;
        }
        if (actionData.scaleByDefenderWeaponSpeed > 0)
        {
            obj_id defenderWeapon = getCurrentWeapon(defender);
            if (isIdValid(defenderWeapon))
            {
                float defenderWeaponSpeed = getWeaponAttackSpeed(getCurrentWeapon(defender));
                hitData.damage *= actionData.scaleByDefenderWeaponSpeed * defenderWeaponSpeed;
            }
        }
        expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(expert, "combat_multiply_damage_dealt");
        expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(defender, "combat_multiply_damage_taken");
        float tempDamageFloat = (float)hitData.damage;
        tempDamageFloat = tempDamageFloat * (1.0f + ((float)expertiseDamageBonus / 100.0f));
        int expertiseDamageReduction = 0;
        expertiseDamageReduction += getEnhancedSkillStatisticModifierUncapped(expert, "combat_divide_damage_dealt");
        expertiseDamageReduction += getEnhancedSkillStatisticModifierUncapped(defender, "combat_divide_damage_taken");
        expertiseDamageReduction = expertiseDamageReduction > 100 ? 100 : expertiseDamageReduction;
        tempDamageFloat = tempDamageFloat * (1.0f - ((float)expertiseDamageReduction / 100.0f));
        hitData.damage = (int)tempDamageFloat;
        hitData.damage += getEnhancedSkillStatisticModifierUncapped(expert, "combat_add_damage_dealt");
        hitData.damage -= getEnhancedSkillStatisticModifierUncapped(expert, "combat_subtract_damage_dealt");
        hitData.damage += getEnhancedSkillStatisticModifierUncapped(defender, "combat_add_damage_taken");
        hitData.damage -= getEnhancedSkillStatisticModifierUncapped(defender, "combat_subtract_damage_taken");
        if (isPlayer(attacker))
        {
            session.logActivity(attacker, isPlayer(defender) ? session.ACTIVITY_PVP : session.ACTIVITY_PVE);
        }
        hitData.blockedDamage += combat.applyArmorProtection(attacker, defender, weaponData, hitData, armorBypass, expertiseDamageBonus);
        if (hitData.damage < 0)
        {
            hitData.damage = 0;
        }
        healing.applyLifeSiphonHeal(attacker, defender, hitData.damage, siphonLife, actionData.actionName);
        int addDefenderDamageToAction = getSkillStatisticModifier(defender, "expertise_damage_add_to_action");
        if (addDefenderDamageToAction > 0)
        {
            int actionToAdd = (int)(hitData.damage * (float)(addDefenderDamageToAction / 100.0f));
            combat.gainCombatActionAttribute(defender, actionToAdd);
            if (rand(0, 9) < 1)
            {
                playClientEffectObj(defender, "appearance/pt_jedi_reactive_response.prt", defender, "root");
            }
        }
        int addAttackerDamageToAction = getSkillStatisticModifier(attacker, "expertise_attacker_action_gain");
        if (addAttackerDamageToAction > 0)
        {
            int actionToAdd = (int)(hitData.damage * (float)(addAttackerDamageToAction / 100.0f));
            combat.gainCombatActionAttribute(attacker, actionToAdd);
            if (rand(0, 9) < 1)
            {
                playClientEffectObj(attacker, "appearance/pt_jedi_tempt_hatred.prt", attacker, "");
            }
        }
        target_dummy.sendTargetDummyCombatData(attacker, defender, actionData, hitData);
        doDamage(attacker, defender, hitData);
        int expertiseActionDamage = 0;
        if (specialLine != null && specialLine.length() > 0)
        {
            expertiseActionDamage = getSkillStatisticModifier(attacker, "expertise_action_damage_line_" + specialLine);
        }
        int flatActionDamage = getAction(defender) < actionData.flatActionDamage + expertiseActionDamage ? getAction(defender) : actionData.flatActionDamage + expertiseActionDamage;
        if (flatActionDamage > 0)
        {
            drainAttributes(defender, flatActionDamage, 0);
        }
        doKillMeterUpdate(attacker, defender, hitData.damage);
        if (isPlayer(attacker))
        {
            string_id strSpam = new string_id("combat_effects", "damage_fly");
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, strSpam);
            pp = prose.setDI(pp, -(hitData.damage + hitData.elementalDamage));
            String strName = actionData.actionName;
            float textSize = 1.5f;
            color combatColor = colors.RED;
            if ((strName.equals("meleeHit")) || (strName.equals("rangedShot")) || (strName.equals("saberHit")))
            {
                if (hitData.critDamage > 0 && hitData.critDamage < 7)
                {
                    string_id critSpam = new string_id("combat_effects", "critical_hit_" + hitData.critDamage);
                    prose_package ppc = new prose_package();
                    ppc = prose.setStringId(ppc, critSpam);
                    ppc = prose.setDI(ppc, -(hitData.damage + hitData.elementalDamage));
                    showFlyTextPrivateProseWithFlags(defender, attacker, ppc, 1.5f, combat.getCriticalColor(hitData.critDamage), FLY_TEXT_FLAG_IS_DAMAGE_FROM_PLAYER);
                }
                else 
                {
                    showFlyTextPrivateProseWithFlags(defender, attacker, pp, textSize, combatColor, FLY_TEXT_FLAG_IS_DAMAGE_FROM_PLAYER);
                }
            }
            else 
            {
                if (hitData.critDamage > 0 && hitData.critDamage < 7)
                {
                    string_id critSpam = new string_id("combat_effects", "critical_hit_" + hitData.critDamage);
                    prose_package ppc = new prose_package();
                    ppc = prose.setStringId(ppc, critSpam);
                    ppc = prose.setDI(ppc, -(hitData.damage + hitData.elementalDamage));
                    showFlyTextPrivateProseWithFlags(defender, attacker, ppc, 1.5f, combat.getCriticalColor(hitData.critDamage), FLY_TEXT_FLAG_IS_DAMAGE_FROM_PLAYER);
                }
                else 
                {
                    showFlyTextPrivateProseWithFlags(defender, attacker, pp, 1.5f, colors.DARKORANGE, FLY_TEXT_FLAG_IS_DAMAGE_FROM_PLAYER);
                }
            }
        }
        if (beast_lib.isBeast(defender) || pet_lib.isPet(defender))
        {
            obj_id master = getMaster(defender);
            if (isIdValid(master) && exists(master))
            {
                string_id strSpam = null;
                color combatHue = colors.YELLOW;
                if (hitData.critDamage > 0 && hitData.critDamage < 7)
                {
                    strSpam = new string_id("combat_effects", "critical_hit_" + hitData.critDamage);
                    combatHue = combat.getCriticalColor(hitData.critDamage);
                }
                else 
                {
                    strSpam = new string_id("combat_effects", "damage_fly");
                }
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, strSpam);
                pp = prose.setDI(pp, -(hitData.damage + hitData.elementalDamage));
                showFlyTextPrivate(defender, master, pp, 1.5f, combatHue);
            }
        }
        if (beast_lib.isBeast(attacker) || pet_lib.isPet(attacker))
        {
            obj_id master = getMaster(attacker);
            if (isIdValid(master) && exists(master))
            {
                string_id strSpam = null;
                color combatHue = colors.RED;
                if (hitData.critDamage > 0 && hitData.critDamage < 7)
                {
                    strSpam = new string_id("combat_effects", "critical_hit_" + hitData.critDamage);
                    combatHue = combat.getCriticalColor(hitData.critDamage);
                }
                else 
                {
                    strSpam = new string_id("combat_effects", "damage_fly");
                }
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, strSpam);
                pp = prose.setDI(pp, -(hitData.damage + hitData.elementalDamage));
                showFlyTextPrivate(defender, master, pp, 1.5f, combatHue);
            }
        }
        combat.assignDamageCredit(attacker, defender, weaponData, hitData.damage);
    }
    public obj_id[] truncateTargetArray(obj_id[] array, obj_id target, int size) throws InterruptedException
    {
        if (array.length <= size + 1)
        {
            return array;
        }
        obj_id[] truncArray = new obj_id[size];
        truncArray[0] = target;
        int source = 0;
        int dest = 1;
        while (source < size)
        {
            if (array[source] != target)
            {
                truncArray[dest] = array[source];
                dest++;
            }
            source++;
        }
        return truncArray;
    }
    public void sortDefendersByPlayer(obj_id[] defenders) throws InterruptedException
    {
        Vector playerList = new Vector();
        playerList.setSize(0);
        Vector objectList = new Vector();
        objectList.setSize(0);
        for (int i = 0; i < defenders.length; i++)
        {
            if (isPlayer(defenders[i]))
            {
                utils.addElement(playerList, defenders[i]);
            }
            else 
            {
                utils.addElement(objectList, defenders[i]);
            }
        }
        if ((playerList.size() + objectList.size()) == defenders.length)
        {
            obj_id[] tempPlayerList = new obj_id[0];
            if (playerList != null)
            {
                tempPlayerList = new obj_id[playerList.size()];
                playerList.toArray(tempPlayerList);
            }
            obj_id[] tempObjectList = new obj_id[0];
            if (objectList != null)
            {
                tempObjectList = new obj_id[objectList.size()];
                objectList.toArray(tempObjectList);
            }
            defenders = utils.concatArrays(tempPlayerList, tempObjectList);
        }
    }
    public obj_id[] validateDefenders(obj_id attacker, obj_id target, obj_id[] defenders, obj_id source, int maxDefenders, int validTargetType, boolean isOffensiveAction, obj_id losSourceObj, boolean isHeavyWeapon, boolean isSpecialAttack, boolean ignoreLOS, int pvpOnly, combat_data actionData) throws InterruptedException
    {
        Vector validDefenders = new Vector();
        validDefenders.setSize(0);
        if (defenders == null || defenders.length == 0)
        {
            obj_id[] _validDefenders = new obj_id[0];
            if (validDefenders != null)
            {
                _validDefenders = new obj_id[validDefenders.size()];
                validDefenders.toArray(_validDefenders);
            }
            return _validDefenders;
        }
        combat.combatLog(attacker, target, "validateDefenders", "Attempting to put target at start of list");
        if (isIdValid(target) && (!isOffensiveAction || attacker != target))
        {
            if (!factions.ignorePlayer(attacker, target))
            {
                obj_id pvpAttacker = attacker;
                obj_id pvpTarget = target;
                if (isMob(attacker) && !isPlayer(attacker) && isIdValid(getMaster(attacker)))
                {
                    pvpAttacker = getMaster(attacker);
                }
                if (isMob(target) && !isPlayer(target) && isIdValid(getMaster(target)))
                {
                    pvpTarget = getMaster(target);
                }
                if (!isOffensiveAction || pvpCanAttack(pvpAttacker, pvpTarget))
                {
                    validDefenders = utils.addElement(validDefenders, target);
                }
            }
            else 
            {
                combat.combatLog(attacker, target, "validateDefenders", "Invalid Target - Attacker or Target is flagged as playerIgnore");
            }
        }
        if (!isOffensiveAction)
        {
            sortDefendersByPlayer(defenders);
        }
        losSourceObj = losSourceObj != null ? losSourceObj : attacker;
        if (isIdValid(target) && target != attacker && (!combat.cachedCanSee(losSourceObj, target) && !ignoreLOS))
        {
            showFlyTextPrivate(attacker, attacker, new string_id("combat_effects", "cant_see"), 1.5f, colors.MEDIUMTURQUOISE);
            combat.combatLog(attacker, target, "validateDefenders", "Invalid Target - Fail LOS check");
            return null;
        }
        String attackerSocialGroup = null;
        if (!isPlayer(attacker) && !pet_lib.isPet(attacker))
        {
            attackerSocialGroup = ai_lib.getSocialGroup(attacker);
        }
        for (int i = 0; i < defenders.length; i++)
        {
            if (isInvulnerable(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Defender is invulnerable");
                continue;
            }
            if (defenders[i] == target && actionData.attack_rolls == 1)
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Skipping target");
                continue;
            }
            if (pvpOnly == 1 && !isPlayer(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Skipping target - is not a Player");
                continue;
            }
            if (!isIdValid(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Invalid ObjId");
                continue;
            }
            if (isGameObjectTypeOf(defenders[i], GOT_building))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Is a building");
                continue;
            }
            if (!isOffensiveAction && !isMob(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Not a mobile object");
                continue;
            }
            if (!isOffensiveAction && vehicle.isVehicle(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Is a vehicle");
                continue;
            }
            if (isOffensiveAction && isDead(defenders[i]) || getState(defenders[i], STATE_FEIGN_DEATH) > 0)
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Target is Dead");
                continue;
            }
            if (isOffensiveAction && isIncapacitated(defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Target is Incapped");
                continue;
            }
            if (isOffensiveAction)
            {
                obj_id pvpAttacker = attacker;
                obj_id pvpTarget = defenders[i];
                if (isMob(attacker) && !isPlayer(attacker) && isIdValid(getMaster(attacker)))
                {
                    pvpAttacker = getMaster(attacker);
                }
                if (isMob(defenders[i]) && !isPlayer(defenders[i]) && isIdValid(getMaster(defenders[i])))
                {
                    pvpTarget = getMaster(defenders[i]);
                }
                if (!pvpCanAttack(pvpAttacker, pvpTarget))
                {
                    combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Failed PvP check");
                    continue;
                }
            }
            if (!isOffensiveAction && (!pvpCanHelp(attacker, defenders[i]) || pvpCanAttack(attacker, defenders[i])))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Failed PvP check");
                continue;
            }
            if (isOffensiveAction && attackerSocialGroup != null && attackerSocialGroup.equals(ai_lib.getSocialGroup(defenders[i])))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Same AI social group");
                continue;
            }
            if (!combat.cachedCanSee(losSourceObj, defenders[i]) && !ignoreLOS)
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Fail LOS check");
                continue;
            }
            if (isOffensiveAction && factions.ignorePlayer(attacker, defenders[i]))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - Attacker or Defender is flagged as playerIgnore");
                continue;
            }
            if (!combat.validateTarget(defenders[i], validTargetType))
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - failed valid target check");
                continue;
            }
            if (pet_lib.getPetType(defenders[i]) == pet_lib.PET_TYPE_FAMILIAR)
            {
                combat.combatLog(attacker, defenders[i], "validateDefenders", "Invalid Target - familiar");
                continue;
            }
            combat.combatLog(attacker, defenders[i], "validateDefenders", "Valid Target - Adding Defender: " + defenders[i]);
            validDefenders = utils.addElement(validDefenders, defenders[i]);
            if (validDefenders.size() == maxDefenders)
            {
                break;
            }
        }
        combat.combatLog(attacker, defenders[0], "validateDefenders", "Valid defender count = " + validDefenders.size());
        obj_id[] _validDefenders = new obj_id[0];
        if (validDefenders != null)
        {
            _validDefenders = new obj_id[validDefenders.size()];
            validDefenders.toArray(_validDefenders);
        }
        return _validDefenders;
    }
    public obj_id[] validateDefenders(obj_id attacker, obj_id target, obj_id[] defenders, obj_id source, int maxDefenders, int validTargetType, boolean isOffensiveAction, obj_id losSourceObj, boolean isHeavyWeapon, boolean isSpecialAttack) throws InterruptedException
    {
        return validateDefenders(attacker, target, defenders, source, maxDefenders, validTargetType, isOffensiveAction, null, false, false, false, 0, null);
    }
    public obj_id[] validateDefenders(obj_id attacker, obj_id target, obj_id[] defenders, obj_id source, int maxDefenders, int validTargetType, boolean isOffensiveAction) throws InterruptedException
    {
        return validateDefenders(attacker, target, defenders, source, maxDefenders, validTargetType, isOffensiveAction, null, false, false);
    }
    public defender_results[] createDefenderResultsArray(int size) throws InterruptedException
    {
        defender_results[] defenderResults = new defender_results[size];
        for (int i = 0; i < size; i++)
        {
            defenderResults[i] = new defender_results();
        }
        return defenderResults;
    }
    public hit_result[] createHitResultsArray(int size) throws InterruptedException
    {
        hit_result[] hitData = new hit_result[size];
        for (int i = 0; i < size; i++)
        {
            hitData[i] = new hit_result();
        }
        return hitData;
    }
    public attacker_results[] createAttackerResultsArray(int size) throws InterruptedException
    {
        attacker_results[] attackerResults = new attacker_results[size];
        for (int i = 0; i < size; i++)
        {
            attackerResults[i] = new attacker_results();
        }
        return attackerResults;
    }
    public boolean shouldPlayAnimation(obj_id objAttacker, combat_data actionData) throws InterruptedException
    {
        return true;
    }
    public void doMultiDefenderCombatResults(String[] playbackNames, attacker_results attackerResults, defender_results[] defenderResults, weapon_data weaponData, combat_data actionData, hit_result[] cbtHitData) throws InterruptedException
    {
        int weaponTrail = actionData.intWeapon;
        int leftHandTrail = actionData.intLeftHand;
        int rightHandTrail = actionData.intRightHand;
        int leftFootTrail = actionData.intLeftFoot;
        int rightFootTrail = actionData.intRightFoot;
        if (playbackNames == null || playbackNames.length == 0 || attackerResults == null || defenderResults == null || defenderResults.length == 0 || weaponData == null || actionData == null || cbtHitData == null || cbtHitData.length == 0)
        {
            boolean pbnull = playbackNames == null;
            boolean pbzero = playbackNames.length == 0;
            boolean atkres = attackerResults == null;
            boolean defres = defenderResults == null;
            boolean deflen = defenderResults.length == 0;
            boolean weadat = weaponData == null;
            boolean actdat = actionData == null;
            boolean cbtdat = cbtHitData == null;
            CustomerServiceLog("COMBAT_exception", "Error in doMultiDefenderCombatResult: playbackNames == null : " + pbnull + ", playbackNames.length == 0: " + pbzero + ", attackerResults == null: " + atkres + ", defenderResults == null : " + defres + ", defenderResults.length == 0 : " + deflen + ", weaponData == null : " + weadat + ", actionData == null : " + actdat + ", cbtHitData = null : " + cbtdat);
            return;
        }
        boolean addDamageAmount = (cbtHitData.length == defenderResults.length);
        boolean boolPlayAnim = shouldPlayAnimation(attackerResults.id, actionData);
        if (weaponData.weaponType == WEAPON_TYPE_UNARMED)
        {
            if (leftHandTrail > 0)
            {
                attackerResults.setTrail(attacker_results.TRAIL_LHAND, true);
            }
            if (rightHandTrail > 0)
            {
                attackerResults.setTrail(attacker_results.TRAIL_RHAND, true);
            }
            if (leftFootTrail > 0)
            {
                attackerResults.setTrail(attacker_results.TRAIL_LFOOT, true);
            }
            if (rightFootTrail > 0)
            {
                attackerResults.setTrail(attacker_results.TRAIL_RFOOT, true);
            }
        }
        else 
        {
            if (weaponTrail > 0 || combat.isLightsaberWeapon(weaponData.weaponType))
            {
                attackerResults.setTrail(attacker_results.TRAIL_WEAPON, true);
            }
        }
        if (actionData.actionName == "" || actionData.actionName == null)
        {
            return;
        }
        attackerResults.actionName = getStringCrc(actionData.actionName.toLowerCase());
        if (defenderResults.length > 1)
        {
            if (defenderResults[0] == null || cbtHitData[0] == null)
            {
                return;
            }
            defender_results[] defenderResult = new defender_results[1];
            defenderResult[0] = defenderResults[0];
            if (addDamageAmount)
            {
                defenderResult[0].damageAmount = cbtHitData[0].damage;
            }
            if (boolPlayAnim)
            {
                if (playbackNames[0] != null && !playbackNames[0].equals(""))
                {
                    doCombatResults(playbackNames[0], attackerResults, defenderResult);
                }
            }
            attacker_results animationResults = new attacker_results();
            for (int i = 1; i < defenderResults.length; i++)
            {
                if (playbackNames[i] == null || playbackNames[i].equals("") || defenderResults[i] == null)
                {
                    continue;
                }
                defenderResult[0].endPosture = defenderResults[i].endPosture;
                defenderResult[0].id = defenderResults[i].id;
                if (defenderResults[i].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults(playbackNames[i], null, defenderResult);
                }
                else if (defenderResults[i].result == COMBAT_RESULT_EVADE)
                {
                    doCombatResults(playbackNames[i], null, defenderResult);
                }
                else if (defenderResults[i].result == COMBAT_RESULT_BLOCK)
                {
                    doCombatResults(playbackNames[i], null, defenderResult);
                }
                else if (defenderResults[i].result == COMBAT_RESULT_MISS)
                {
                    doCombatResults(playbackNames[i], null, defenderResult);
                }
                else if (defenderResults[i].result == COMBAT_RESULT_TETHERED)
                {
                    defenderResults[i].result = COMBAT_RESULT_MISS;
                    doCombatResults(playbackNames[i], null, defenderResult);
                    defenderResults[i].result = COMBAT_RESULT_TETHERED;
                }
            }
        }
        else 
        {
            if (playbackNames[0] != null && !playbackNames[0].equals("") && defenderResults[0] != null)
            {
                if (addDamageAmount)
                {
                    defenderResults[0].damageAmount = cbtHitData[0].damage + cbtHitData[0].elementalDamage;
                }
                if (defenderResults[0].result == COMBAT_RESULT_TETHERED)
                {
                    defenderResults[0].result = COMBAT_RESULT_MISS;
                    if (boolPlayAnim)
                    {
                        doCombatResults(playbackNames[0], attackerResults, defenderResults);
                    }
                    defenderResults[0].result = COMBAT_RESULT_TETHERED;
                }
                if (boolPlayAnim)
                {
                    doCombatResults(playbackNames[0], attackerResults, defenderResults);
                }
            }
        }
    }
    public void doBasicGrenadeCombatResults(String[] playbackNames, attacker_results attackerResults, defender_results[] defenderResults, weapon_data weaponData, combat_data actionData, hit_result[] hitData) throws InterruptedException
    {
        String attackAnim = "throw_grenade";
        String distance = "_near";
        float range = getDistance(attackerResults.id, defenderResults[0].id);
        String hitStrength = "_light";
        int avgDamage = (weaponData.minDamage + weaponData.maxDamage) / 2;
        String grenadeName = getGrenadeType(weaponData.id);
        attackAnim += distance + "_" + grenadeName;
        attackerResults.weapon = null;
        boolean addDamageAmount = (hitData.length == defenderResults.length);
        if (defenderResults.length > 1)
        {
            defender_results[] defenderResult = new defender_results[1];
            defenderResult[0] = defenderResults[0];
            if (addDamageAmount)
            {
                defenderResult[0].damageAmount = hitData[0].damage;
            }
            if (!playbackNames[0].equals(""))
            {
                doCombatResults(attackAnim, attackerResults, defenderResult);
            }
            attacker_results animationResults = new attacker_results();
            for (int i = 1; i < defenderResults.length; i++)
            {
                animationResults.endPosture = defenderResults[i].endPosture;
                animationResults.id = defenderResults[i].id;
                if (defenderResults[i].result == COMBAT_RESULT_HIT)
                {
                    if (hitData[i].damage > avgDamage)
                    {
                        hitStrength = "_medium";
                    }
                    else 
                    {
                        hitStrength = "_light";
                    }
                    doCombatResults("get_hit_grenade" + distance + hitStrength, animationResults, null);
                }
            }
        }
        else 
        {
            if (addDamageAmount)
            {
                defenderResults[0].damageAmount = hitData[0].damage;
            }
            doCombatResults(attackAnim, attackerResults, defenderResults);
        }
        return;
    }
    public boolean checkPosture(obj_id objAttacker, obj_id objDefender) throws InterruptedException
    {
        if (isDead(objDefender))
        {
            return false;
        }
        if (isIncapacitated(objDefender))
        {
            return false;
        }
        return true;
    }
    public boolean didHit(hit_result[] cbtHitData) throws InterruptedException
    {
        int intI = 0;
        while (intI < cbtHitData.length)
        {
            if (cbtHitData[intI].success == true)
            {
                return true;
            }
            intI = intI + 1;
        }
        return false;
    }
    public void finalizeMineDamage(obj_id objAttacker, weapon_data cbtWeaponData, defender_data[] cbtDefenderData, hit_result[] cbtHitData, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        obj_id[] objDefenders = new obj_id[cbtDefenderData.length];
        int[] intResults = new int[cbtHitData.length];
        int intI = 0;
        while (intI < cbtDefenderData.length)
        {
            if (cbtHitData[intI].success)
            {
                if (damage(cbtDefenderData[intI].id, cbtWeaponData.damageType, cbtHitData[intI].hitLocation, cbtHitData[intI].damage))
                {
                }
                else 
                {
                }
            }
            else 
            {
            }
            objDefenders[intI] = cbtDefenderData[intI].id;
            intResults[intI] = cbtDefenderResults[intI].result;
            intI = intI + 1;
        }
    }
    public color getHitColor(int intHitLocation) throws InterruptedException
    {
        switch (intHitLocation)
        {
            case HIT_LOCATION_BODY:
            return colors.RED;
            case HIT_LOCATION_HEAD:
            return colors.BLUE;
            case HIT_LOCATION_R_ARM:
            return colors.RED;
            case HIT_LOCATION_L_ARM:
            return colors.RED;
            case HIT_LOCATION_R_LEG:
            return colors.GREEN;
            case HIT_LOCATION_L_LEG:
            return colors.GREEN;
        }
        return colors.WHITE;
    }
    public void doCombatFlyText(defender_data[] defenderData, obj_id attacker, hit_result[] hitData, defender_results[] defenderResults) throws InterruptedException
    {
        float textSize = 1.5f;
        for (int i = 0; i < hitData.length; i++)
        {
            string_id strSpam = null;
            color textColor = colors.WHITE;
            if (defenderResults[i].result == COMBAT_RESULT_BLOCK || defenderResults[i].result == COMBAT_RESULT_LIGHTSABER_BLOCK)
            {
                strSpam = new string_id("combat_effects", "block");
            }
            else if (defenderResults[i].result == COMBAT_RESULT_EVADE)
            {
                strSpam = new string_id("combat_effects", "dodge");
            }
            else if (defenderResults[i].result == COMBAT_RESULT_COUNTER)
            {
                strSpam = new string_id("combat_effects", "counterattack");
            }
            else if (defenderResults[i].result == COMBAT_RESULT_MISS)
            {
                strSpam = new string_id("combat_effects", "miss");
            }
            else if (defenderResults[i].result == COMBAT_RESULT_TETHERED)
            {
                strSpam = new string_id("combat_effects", "tethered");
                textSize = 5f;
            }
            if (strSpam != null)
            {
                if (isPlayer(attacker) && isMob(defenderData[i].id) && (!vehicle.isDriveableVehicle(defenderData[i].id)))
                {
                    showCombatTextPrivate(defenderData[i].id, attacker, attacker, strSpam, textSize, textColor);
                }
                if (isPlayer(defenderData[i].id) && isMob(defenderData[i].id) && (!vehicle.isDriveableVehicle(defenderData[i].id)))
                {
                    showCombatTextPrivate(defenderData[i].id, attacker, defenderData[i].id, strSpam, textSize, textColor);
                }
            }
        }
    }
    public String getHitSpam(hit_result cbtHitData) throws InterruptedException
    {
        int intHitLocation = cbtHitData.hitLocation;
        String strId = "";
        switch (intHitLocation)
        {
            case HIT_LOCATION_BODY:
            return "body";
            case HIT_LOCATION_HEAD:
            return "head";
            case HIT_LOCATION_R_ARM:
            return "rarm";
            case HIT_LOCATION_L_ARM:
            return "larm";
            case HIT_LOCATION_R_LEG:
            return "rleg";
            case HIT_LOCATION_L_LEG:
            return "lleg";
        }
        return strId;
    }
    public boolean checkForFumbles(hit_result[] hitData) throws InterruptedException
    {
        return false;
    }
    public void doFumble(attacker_data cbtAttackerData, weapon_data cbtWeaponData, int intAttackerPosture, String strAnimationAction) throws InterruptedException
    {
        string_id strSpam = new string_id("cbt_spam", "fumble");
        int intFumbleDamage = rand(cbtWeaponData.minDamage, cbtWeaponData.maxDamage);
        return;
    }
    public boolean isImmuneToStateChange(obj_id target) throws InterruptedException
    {
        if (vehicle.isDriveableVehicle(target))
        {
            return true;
        }
        if (ai_lib.aiGetNiche(target) == NICHE_DROID)
        {
            return true;
        }
        if (ai_lib.aiGetNiche(target) == NICHE_VEHICLE)
        {
            return true;
        }
        if (ai_lib.isAndroid(target))
        {
            return true;
        }
        if (ai_lib.isTurret(target))
        {
            return true;
        }
        return false;
    }
    public void doWarcryCombatResults(String strAnimationAction, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        if (cbtDefenderResults.length > 1)
        {
            attacker_results cbtAnimationResults = new attacker_results();
            defender_results[] cbtDefenderResult = new defender_results[1];
            cbtDefenderResult[0] = cbtDefenderResults[0];
            doCombatResults(strAnimationAction, cbtAttackerResults, cbtDefenderResult);
            for (int i = 0; i < cbtDefenderResults.length; i++)
            {
                cbtAnimationResults.endPosture = cbtDefenderResults[i].endPosture;
                cbtAnimationResults.id = cbtDefenderResults[i].id;
                if (cbtDefenderResults[i].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults("get_hit_light", cbtAnimationResults, null);
                }
            }
        }
        else 
        {
            doCombatResults(strAnimationAction, cbtAttackerResults, cbtDefenderResults);
        }
    }
    public void doSuppressionFireResults(String strPlaybackName, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults, combat_data dctCombatInfo) throws InterruptedException
    {
        int intLeftFoot = dctCombatInfo.intLeftFoot;
        int intRightFoot = dctCombatInfo.intRightFoot;
        int intWeapon = dctCombatInfo.intWeapon;
        int intRightHand = dctCombatInfo.intRightHand;
        int intLeftHand = dctCombatInfo.intLeftHand;
        if (jedi.isLightsaber(cbtAttackerResults.weapon))
        {
            intWeapon = 1;
        }
        if (intLeftFoot > 0)
        {
            cbtAttackerResults.setTrail(attacker_results.TRAIL_LFOOT, true);
        }
        if (intRightFoot > 0)
        {
            cbtAttackerResults.setTrail(attacker_results.TRAIL_RFOOT, true);
        }
        if (intLeftHand > 0)
        {
            cbtAttackerResults.setTrail(attacker_results.TRAIL_LHAND, true);
        }
        if (intRightHand > 0)
        {
            cbtAttackerResults.setTrail(attacker_results.TRAIL_RHAND, true);
        }
        if (intWeapon > 0)
        {
            cbtAttackerResults.setTrail(attacker_results.TRAIL_WEAPON, true);
        }
        if (cbtDefenderResults.length > 1)
        {
            int intI = 1;
            attacker_results cbtAnimationResults = new attacker_results();
            defender_results[] cbtDefenderResult = new defender_results[1];
            cbtDefenderResult[0] = cbtDefenderResults[0];
            doCombatResults(strPlaybackName, cbtAttackerResults, cbtDefenderResult);
            while (intI < cbtDefenderResults.length)
            {
                cbtAnimationResults.endPosture = cbtDefenderResults[intI].endPosture;
                cbtAnimationResults.id = cbtDefenderResults[intI].id;
                if (cbtDefenderResults[intI].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults("posture_scramble", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_EVADE)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_BLOCK)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_MISS)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                else 
                {
                }
                intI = intI + 1;
            }
        }
        else 
        {
            doCombatResults(strPlaybackName, cbtAttackerResults, cbtDefenderResults);
        }
    }
    public void doChargeResults(String strPlaybackName, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        if (cbtDefenderResults.length > 1)
        {
            int intI = 1;
            attacker_results cbtAnimationResults = new attacker_results();
            defender_results[] cbtDefenderResult = new defender_results[1];
            cbtDefenderResult[0] = cbtDefenderResults[0];
            doCombatResults(strPlaybackName, cbtAttackerResults, cbtDefenderResult);
            while (intI < cbtDefenderResults.length)
            {
                cbtAnimationResults.endPosture = cbtDefenderResults[intI].endPosture;
                cbtAnimationResults.id = cbtDefenderResults[intI].id;
                if (cbtDefenderResults[intI].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults("change_posture", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_EVADE)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_BLOCK)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_MISS)
                {
                    doCombatResults("dodge", cbtAnimationResults, null);
                }
                intI = intI + 1;
            }
        }
        else 
        {
            doCombatResults(strPlaybackName, cbtAttackerResults, cbtDefenderResults);
        }
    }
    public String getPlaybackIntensity(String strPlaybackName, hit_result[] cbtHitData, weapon_data cbtWeaponData) throws InterruptedException
    {
        String strNewAction = new String();
        strNewAction = strPlaybackName;
        int intMidDamage = (cbtWeaponData.minDamage + cbtWeaponData.maxDamage) / 2;
        if (cbtHitData[0].damage > intMidDamage)
        {
            strNewAction = strNewAction + "_medium";
        }
        else 
        {
            strNewAction = strNewAction + "_light";
        }
        return strNewAction;
    }
    public String getThrownWeaponStringType(obj_id objWeapon) throws InterruptedException
    {
        String strType = "";
        String strTemplate = getGrenadeType(objWeapon);
        if (strTemplate == null)
        {
            strType = "";
        }
        strType = strType + strTemplate;
        return strType;
    }
    public String getHeavyWeaponStringType(obj_id objWeapon) throws InterruptedException
    {
        String strType = "heavy_";
        String strTemplate = getHeavyWeaponType(objWeapon);
        if (strTemplate == null)
        {
            strType = "";
        }
        strType = strType + strTemplate;
        return strTemplate;
    }
    public String[] makeStringArray(int intLength) throws InterruptedException
    {
        int intI = 0;
        String[] strArray = new String[intLength];
        while (intI < intLength)
        {
            strArray[intI] = "";
            intI = intI + 1;
        }
        return strArray;
    }
    public boolean checkWeaponData(obj_id objPlayer, obj_id objTarget, int intAttackCategory, weapon_data cbtWeaponData, String strCommand, String strWeaponType) throws InterruptedException
    {
        float fltDistance = getDistance(objPlayer, objTarget);
        if (fltDistance > cbtWeaponData.maxRange)
        {
            combat.sendCombatSpamMessage(objPlayer, new string_id("cbt_spam", "out_of_range"), COMBAT_RESULT_OUT_OF_RANGE);
            sendSystemMessage(objPlayer, new string_id("cbt_spam", "out_of_range_single"));
            return false;
        }
        if (intAttackCategory != combat.ALL_WEAPONS)
        {
            if ((intAttackCategory == combat.RANGED_WEAPON) || (intAttackCategory == combat.MELEE_WEAPON))
            {
                int intWeaponCategory = combat.getWeaponCategory(cbtWeaponData.weaponType);
                if (intWeaponCategory != intAttackCategory)
                {
                    string_id strSpam = new string_id("cbt_spam", "no_attack_wrong_weapon");
                    sendSystemMessage(objPlayer, strSpam);
                    return false;
                }
            }
            else if (intAttackCategory == combat.ALL_LIGHTSABERS)
            {
                if (!jedi.isLightsaber(cbtWeaponData.weaponType))
                {
                    string_id strSpam = new string_id("cbt_spam", "no_attack_wrong_weapon");
                    sendSystemMessage(objPlayer, strSpam);
                    return false;
                }
            }
            else if (intAttackCategory == combat.FORCE_POWER)
            {
                if (!jedi.isLightsaber(cbtWeaponData.weaponType))
                {
                    if (cbtWeaponData.weaponType != WEAPON_TYPE_UNARMED)
                    {
                    }
                }
            }
            else if (intAttackCategory != cbtWeaponData.weaponType)
            {
                string_id strSpam = new string_id("cbt_spam", "no_attack_wrong_weapon");
                sendSystemMessage(objPlayer, strSpam);
                return false;
            }
        }
        if (!strCommand.equals(""))
        {
            String strAbility = strWeaponType + strCommand;
            if (!hasCommand(objPlayer, strAbility))
            {
                string_id strSpam = new string_id("cbt_spam", "no_ability");
                sendSystemMessage(objPlayer, strSpam);
                return false;
            }
        }
        return true;
    }
    public boolean checkCommands(obj_id objPlayer, String strCommand, String strWeaponType) throws InterruptedException
    {
        if (!strCommand.equals(""))
        {
            String strAbility = strWeaponType + strCommand;
            if (!hasCommand(objPlayer, strAbility))
            {
                string_id strSpam = new string_id("cbt_spam", "no_ability");
                sendSystemMessage(objPlayer, strSpam);
                return false;
            }
        }
        return true;
    }
    public boolean checkForCombatActions(obj_id objPlayer) throws InterruptedException
    {
        if (queueHasCommandFromGroup(objPlayer, (-1170591580)))
        {
            return true;
        }
        if (queueHasCommandFromGroup(objPlayer, (391413347)))
        {
            return true;
        }
        if (queueHasCommandFromGroup(objPlayer, (-506878646)))
        {
            return true;
        }
        return false;
    }
    public String getGrenadeType(obj_id objGrenade) throws InterruptedException
    {
        String strTemplate = getTemplateName(objGrenade);
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_cryoban.iff"))
        {
            return "cryoban";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_fragmentation_light.iff"))
        {
            return "fragmentation";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_fragmentation.iff"))
        {
            return "fragmentation";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_glop.iff"))
        {
            return "glop";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_imperial_detonator.iff"))
        {
            return "imperial_detonator";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_proton.iff"))
        {
            return "proton";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_thermal_detonator.iff"))
        {
            return "thermal_detonator";
        }
        if (strTemplate.equals("object/weapon/ranged/grenade/grenade_bug_bomb.iff"))
        {
            return "bug_bomb";
        }
        return "fragmentation";
    }
    public String getHeavyWeaponType(obj_id objHeavyWeapon) throws InterruptedException
    {
        String strTemplate = getTemplateName(objHeavyWeapon);
        String hw = "heavyweapon";
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_rocket_laucher.iff"))
        {
            return hw;
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_lightning_beam.iff"))
        {
            return hw;
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_particle_beam.iff"))
        {
            return hw;
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_acid_beam.iff"))
        {
            return hw;
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_flame_thrower.iff"))
        {
            return "rifle";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_beam.iff"))
        {
            return "rifle_beam";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_acid_beam.iff"))
        {
            return "rifle";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_lightning.iff"))
        {
            return "carbine";
        }
        return hw;
    }
    public void clearQueue(obj_id objPlayer) throws InterruptedException
    {
        queueClearCommandsFromGroup(objPlayer, (-506878646));
        queueClearCommandsFromGroup(objPlayer, (-1170591580));
        queueClearCommandsFromGroup(objPlayer, (391413347));
    }
    public boolean callSupplyDrop(obj_id self, int supplyId) throws InterruptedException
    {
        location loc = getLocation(self);
        if (isIdValid(loc.cell))
        {
            sendSystemMessage(self, new string_id("combat_effects", "not_indoors"));
            return false;
        }
        obj_id shuttle = createObject("object/creature/npc/theme_park/lambda_shuttle.iff", loc);
        if (!isIdValid(shuttle))
        {
            return false;
        }
        dictionary d = new dictionary();
        d.put("owner", self);
        d.put("supplyId", supplyId);
        attachScript(shuttle, "systems.combat.combat_supply_drop_controller");
        messageTo(shuttle, "startLandingSequence", d, 5.0f, false);
        if (supplyId < 13)
        {
            prose_package pp = new prose_package();
            prose.setStringId(pp, new string_id("combat_effects", "supply_drop_comm"));
            commPlayers(self, "object/mobile/npe/npe_hutt_minion.iff", "sound/sys_comm_other.snd", 5f, self, pp);
        }
        else 
        {
            prose_package pp = new prose_package();
            prose.setStringId(pp, new string_id("spam", "officer_reinforcement_com"));
            commPlayers(self, "object/mobile/npe/npe_hutt_minion.iff", "sound/sys_comm_other.snd", 5f, self, pp);
        }
        return true;
    }
    public boolean canCallSupplyDrop(obj_id self) throws InterruptedException
    {
        location loc = getLocation(self);
        if (isIdValid(loc.cell))
        {
            sendSystemMessage(self, new string_id("combat_effects", "not_indoors"));
            return false;
        }
        return true;
    }
    public boolean callFavor(obj_id self, int which) throws InterruptedException
    {
        location loc = getLocation(self);
        String buddyObj = "summon_smuggler_buddy_";
        String buddyScript = "ai.smuggler_buddy";
        String buddyName = "smuggler_buddy_name";
        if (which == 1)
        {
            buddyObj = "summon_smuggler_medic_";
            buddyScript = "ai.smuggler_medic";
            buddyName = "smuggler_medic_name";
        }
        if (!isIdValid(loc.cell))
        {
            int x = rand(-3, 3);
            int z = rand(-3, 3);
            loc.x += x;
            loc.z += z;
        }
        int playerLevel = getLevel(self);
        int summonLevel = playerLevel / 10;
        if (summonLevel < 1)
        {
            summonLevel = 1;
        }
        else 
        {
            if (summonLevel > 9)
            {
                summonLevel = 9;
            }
        }
        buddyObj += summonLevel;
        obj_id target = getHateTarget(self);
        if (!isIdValid(target))
        {
            sendSystemMessage(self, new string_id("combat_effects", "no_target_for_buddy"));
            return false;
        }
        obj_id buddy = create.object(buddyObj, loc, getLevel(self));
        if (!isIdValid(buddy))
        {
            return false;
        }
        setName(buddy, new string_id("combat_effects", buddyName));
        attachScript(buddy, buddyScript);
        if (!isIdValid(loc.cell))
        {
            loc.x += 30;
            setHomeLocation(buddy, loc);
        }
        setMaster(buddy, self);
        startCombat(buddy, target);
        return true;
    }
    public boolean doHealingPreCheck(combat_data actionData, attacker_data attackerData, defender_data[] defenderData, boolean verbose) throws InterruptedException
    {
        if (vehicle.isRidingVehicle(attackerData.id))
        {
            if (verbose)
            {
                sendSystemMessage(attackerData.id, new string_id("cbt_spam", "attack_fail_vehicle_single"));
            }
            combat.combatLog(attackerData.id, defenderData[0].id, "doHealingPreCheck", "Aborting Action - Riding vehicle");
            return false;
        }
        obj_id mount = getMountId(attackerData.id);
        if (isIdValid(mount))
        {
            if (pet_lib.isGalloping(mount))
            {
                if (verbose)
                {
                    sendSystemMessage(attackerData.id, new string_id("cbt_spam", "attack_fail_gallop_single"));
                }
                combat.combatLog(attackerData.id, defenderData[0].id, "doHealingPreCheck", "Aborting Action - Riding galloping mount");
                return false;
            }
        }
        if (attackerData.posture == POSTURE_KNOCKED_DOWN)
        {
            if (verbose)
            {
                sendSystemMessage(attackerData.id, new string_id("cbt_spam", "attack_fail_knockdown_single"));
            }
            combat.combatLog(attackerData.id, defenderData[0].id, "doHealingPreCheck", "Aborting Action - Attacker id knocked down");
            return false;
        }
        float actionMaxRange = actionData.maxRange;
        float dist = getDistance(attackerData.id, defenderData[0].id);
        if (dist > actionMaxRange)
        {
            if (verbose)
            {
                sendSystemMessage(attackerData.id, new string_id("cbt_spam", "out_of_range_far_single"));
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("combat_effects", "range_too_far"), 1.0f, colors.MEDIUMTURQUOISE);
            }
            combat.combatLog(attackerData.id, defenderData[0].id, "doHealingPreCheck", "Aborting Action - Target is further than max range");
            return false;
        }
        if ((ai_lib.isDroid(defenderData[0].id) && !ai_lib.isDroid(attackerData.id)) || (ai_lib.isAndroid(defenderData[0].id) && !ai_lib.isAndroid(attackerData.id)))
        {
            if (verbose)
            {
                sendSystemMessage(attackerData.id, new string_id("cbt_spam", "cannot_heal_droid"));
                showFlyTextPrivate(attackerData.id, attackerData.id, new string_id("cbt_spam", "cannot_heal_droid"), 1.0f, colors.MEDIUMTURQUOISE);
            }
            return false;
        }
        return true;
    }
    public boolean hasFullActionPool(obj_id target) throws InterruptedException
    {
        int currentAction = getAction(target);
        int maxAction = getMaxAction(target);
        if (currentAction == maxAction)
        {
            return true;
        }
        return false;
    }
    public boolean healthToActionConversion(obj_id target, int healthCost, int actionGain) throws InterruptedException
    {
        int currentHealth = getHealth(target);
        int modifiedHealth = currentHealth - healthCost;
        attrib_mod[] healthMods = getHealthModifiers(target);
        int healthModsValue = 0;
        for (int i = 0; i < healthMods.length; i++)
        {
            healthModsValue += healthMods[i].getValue();
        }
        if (healthModsValue < 0)
        {
            sendSystemMessage(target, new string_id("spam", "not_well_enough"));
            return false;
        }
        if (modifiedHealth < 1)
        {
            showFlyTextPrivate(target, target, new string_id("spam", "low_health"), 1.0f, colors.RED);
            return false;
        }
        int currentAction = getAction(target);
        int maxAction = getMaxAction(target);
        int modifiedAction = currentAction + actionGain;
        int trueActionGained = 0;
        if (modifiedAction > maxAction)
        {
            setAction(target, maxAction);
            trueActionGained = maxAction - currentAction;
        }
        else 
        {
            setAction(target, modifiedAction);
            trueActionGained = actionGain;
        }
        setHealth(target, modifiedHealth);
        string_id actionStringId = new string_id("spam", "plus_action");
        prose_package pp = new prose_package();
        pp = prose.setStringId(pp, actionStringId);
        pp = prose.setDI(pp, trueActionGained);
        showFlyTextPrivate(target, target, pp, 1.1f, colors.YELLOW);
        playClientEffectObj(target, "clienteffect/medic_reckless_stimulation.cef", target, "root");
        return true;
    }
    public void doMedicEvasion(obj_id medic) throws InterruptedException
    {
        obj_id[] thoseWhoHateMe = getHateList(medic);
        int medicLevel = getLevel(medic);
        String evasionScriptVarName = "me_evasion." + medic;
        if (thoseWhoHateMe.length != 0 && thoseWhoHateMe != null)
        {
            for (int i = 0; i < thoseWhoHateMe.length; i++)
            {
                int haterLevel = getLevel(thoseWhoHateMe[i]);
                int levelSpread = medicLevel - haterLevel;
                int baseChance = 93;
                int difficultyClass = getIntObjVar(thoseWhoHateMe[i], "difficultyClass");
                if (difficultyClass > 1)
                {
                    baseChance = -45;
                }
                if (levelSpread < -24)
                {
                    levelSpread = -24;
                }
                int roll = rand(1, 100);
                if (roll + baseChance + (levelSpread * 3) > 100)
                {
                    removeHateTarget(medic, thoseWhoHateMe[i]);
                    removeHateTarget(thoseWhoHateMe[i], medic);
                    utils.setScriptVar(thoseWhoHateMe[i], evasionScriptVarName, 1);
                }
                else 
                {
                    float hate = getHate(medic, thoseWhoHateMe[i]);
                    float oldHate = hate;
                    hate /= 2;
                    setHate(medic, thoseWhoHateMe[i], hate);
                    utils.removeScriptVar(thoseWhoHateMe[i], evasionScriptVarName);
                }
            }
        }
        return;
    }
    public combat_data modifyActionDataByExpertise(obj_id self, combat_data actionData) throws InterruptedException
    {
        if (stealth.hasInvisibleBuff(self) || buff.hasBuff(self, "sp_smoke_mirrors"))
        {
            float expertise_defense_overpower = (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_avoidance_overpower");
            actionData.reduceParry += expertise_defense_overpower;
            actionData.reduceDodge += expertise_defense_overpower;
            actionData.reduceBlock += expertise_defense_overpower;
            actionData.reduceGlancing += expertise_defense_overpower;
        }
        float armorBypass = (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_armor_bypass_" + actionData.specialLine);
        if (armorBypass > 0.0f)
        {
            armorBypass = armorBypass / 100.0f;
            armorBypass += actionData.bypassArmor;
            armorBypass = armorBypass > 1.0f ? 1.0f : armorBypass;
            actionData.increaseStrikethrough = -1000.0f;
            actionData.bypassArmor = armorBypass;
        }
        float damToHeal = (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_damage_to_healing_" + actionData.specialLine);
        if (damToHeal > 0.0f && actionData.convertDamageToHealth > 0.0f)
        {
            damToHeal = damToHeal / 100.0f;
            actionData.convertDamageToHealth += damToHeal;
        }
        float flawlessBead = (float)getEnhancedSkillStatisticModifierUncapped(self, "flawless_bead");
        if (flawlessBead > 0 && actionData.commandType != combat.LEFT_CLICK_DEFAULT)
        {
            actionData.reduceParry += flawlessBead;
            actionData.reduceDodge += flawlessBead;
            actionData.reduceBlock += flawlessBead;
            actionData.reduceGlancing += flawlessBead;
        }
        return actionData;
    }
    public void runHitSimulator(obj_id self, int[] atkTable, int[] defTable) throws InterruptedException
    {
        if (!isGod(self) && !utils.hasObjVar(self, "runHitSimulator"))
        {
            return;
        }
        int hitLoops = 0;
        if (hasObjVar(self, "runHitSimulator"))
        {
            hitLoops = getIntObjVar(self, "runHitSimulator");
        }
        if (hitLoops < 1)
        {
            utils.removeObjVar(self, "runHitSimulator");
            return;
        }
        hitLoops--;
        utils.setObjVar(self, "runHitSimulator", hitLoops);
        if (utils.hasScriptVar(self, "hitpid"))
        {
            int bellpid = utils.getIntScriptVar(self, "hitpid");
            sui.forceCloseSUIPage(bellpid);
        }
        int hitNumber = 1000;
        int miss = 0;
        int dodge = 0;
        int parry = 0;
        int glancing = 0;
        int block = 0;
        int critical = 0;
        int punishing = 0;
        int dhit = 0;
        int ahit = 0;
        int hit = 0;
        int evade = 0;
        int strikethrough = 0;
        for (int i = 0; i < defTable.length; i++)
        {
            switch (defTable[i])
            {
                case HIT_RESULT_MISS:
                miss++;
                break;
                case HIT_RESULT_DODGE:
                dodge++;
                break;
                case HIT_RESULT_PARRY:
                parry++;
                break;
                case HIT_RESULT_GLANCING:
                glancing++;
                break;
                case HIT_RESULT_BLOCK:
                block++;
                break;
                case HIT_RESULT_CRITICAL:
                critical++;
                break;
                case HIT_RESULT_PUNISHING:
                punishing++;
                break;
                case HIT_RESULT_EVADE:
                evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                strikethrough++;
                break;
            }
        }
        for (int i = 0; i < atkTable.length; i++)
        {
            switch (atkTable[i])
            {
                case HIT_RESULT_MISS:
                miss++;
                break;
                case HIT_RESULT_DODGE:
                dodge++;
                break;
                case HIT_RESULT_PARRY:
                parry++;
                break;
                case HIT_RESULT_GLANCING:
                glancing++;
                break;
                case HIT_RESULT_BLOCK:
                block++;
                break;
                case HIT_RESULT_CRITICAL:
                critical++;
                break;
                case HIT_RESULT_PUNISHING:
                punishing++;
                break;
                case HIT_RESULT_HIT:
                if (defTable[i] == HIT_RESULT_HIT)
                {
                    hit++;
                }
                break;
                case HIT_RESULT_EVADE:
                evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                strikethrough++;
                break;
            }
        }
        int test_miss = 0;
        int test_dodge = 0;
        int test_parry = 0;
        int test_glancing = 0;
        int test_block = 0;
        int test_critical = 0;
        int test_punishing = 0;
        int test_hit = 0;
        int test_evade = 0;
        int test_strikethrough = 0;
        int[] atkResult = new int[hitNumber];
        int[] defResult = new int[hitNumber];
        for (int i = 0; i < hitNumber; i++)
        {
            defResult[i] = defTable[rand(0, defTable.length - 1)];
            atkResult[i] = atkTable[rand(0, atkTable.length - 1)];
        }
        for (int i = 0; i < defResult.length; i++)
        {
            switch (defResult[i])
            {
                case HIT_RESULT_MISS:
                test_miss++;
                break;
                case HIT_RESULT_DODGE:
                test_dodge++;
                break;
                case HIT_RESULT_PARRY:
                test_parry++;
                break;
                case HIT_RESULT_GLANCING:
                test_glancing++;
                break;
                case HIT_RESULT_BLOCK:
                test_block++;
                break;
                case HIT_RESULT_CRITICAL:
                test_critical++;
                break;
                case HIT_RESULT_PUNISHING:
                test_punishing++;
                break;
                case HIT_RESULT_EVADE:
                test_evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                test_strikethrough++;
                break;
            }
        }
        for (int i = 0; i < atkResult.length; i++)
        {
            switch (atkResult[i])
            {
                case HIT_RESULT_MISS:
                test_miss++;
                break;
                case HIT_RESULT_DODGE:
                test_dodge++;
                break;
                case HIT_RESULT_PARRY:
                test_parry++;
                break;
                case HIT_RESULT_GLANCING:
                test_glancing++;
                break;
                case HIT_RESULT_BLOCK:
                test_block++;
                break;
                case HIT_RESULT_CRITICAL:
                test_critical++;
                break;
                case HIT_RESULT_PUNISHING:
                test_punishing++;
                break;
                case HIT_RESULT_HIT:
                if (defResult[i] == HIT_RESULT_HIT)
                {
                    test_hit++;
                }
                break;
                case HIT_RESULT_EVADE:
                test_evade++;
                break;
                case HIT_RESULT_STRIKETHROUGH:
                test_strikethrough++;
                break;
            }
        }
        int pid = createSUIPage(sui.SUI_MSGBOX, self, self, "noHandler");
        setSUIProperty(pid, "", "Size", "650,375");
        setSUIProperty(pid, sui.LISTBOX_TITLE, sui.PROP_TEXT, "Hit Result Analysis");
        sui.listboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.LISTBOX_BTN_OK, sui.PROP_TEXT, "@cancel");
        int passedTotal = miss + dodge + parry + glancing + block + critical + punishing + evade + strikethrough;
        hit = 1000 - passedTotal > -1 ? 1000 - passedTotal : 0;
        int simTotal = test_miss + test_dodge + test_parry + test_glancing + test_block + test_critical + test_punishing + test_evade + test_strikethrough;
        test_hit = 1000 - simTotal > -1 ? 1000 - simTotal : 0;
        String resultString = "Defender and Attacker Hit Table Values \n ";
        resultString += "Passed hit_table Values \n ";
        resultString += "Defender Dodge: " + (float)dodge / 10.0f + " \n ";
        resultString += "Defender Parry: " + (float)parry / 10.0f + " \n ";
        resultString += "Defender Evade: " + (float)evade / 10.0f + " \n ";
        resultString += "Defender Block: " + (float)block / 10.0f + " \n ";
        resultString += "Defender Glance: " + (float)glancing / 10.0f + " \n\n ";
        resultString += "Attacker Miss: " + (float)miss / 10.0f + " \n ";
        resultString += "Attacker Strikethrough: " + (float)strikethrough / 10.0f + " \n ";
        resultString += "Attacker Critical: " + (float)critical / 10.0f + " \n ";
        resultString += "Attacker Punishing: " + (float)punishing / 10.0f + " \n ";
        resultString += "Attacker Hit: " + (float)hit / 10.0f + " \n\n\n";
        resultString += "Simulated hit_table rolls (" + hitNumber + ") itterations \n ";
        resultString += "Defender Dodge: " + (float)test_dodge / 10.0f + " \n ";
        resultString += "Defender Parry: " + (float)test_parry / 10.0f + " \n ";
        resultString += "Defender Evade: " + (float)test_evade / 10.0f + " \n ";
        resultString += "Defender Block: " + (float)test_block / 10.0f + " \n ";
        resultString += "Defender Glance: " + (float)test_glancing / 10.0f + " \n\n ";
        resultString += "Attacker Miss: " + (float)test_miss / 10.0f + " \n ";
        resultString += "Attacker Strikethrough: " + (float)test_strikethrough / 10.0f + " \n ";
        resultString += "Attacker Critical: " + (float)test_critical / 10.0f + " \n ";
        resultString += "Attacker Punishing: " + (float)test_punishing / 10.0f + " \n ";
        resultString += "Attacker Hit: " + (float)test_hit / 10.0f + " \n ";
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, resultString);
        showSUIPage(pid);
        flushSUIPage(pid);
        utils.setScriptVar(self, "hitpid", pid);
    }
    public combat_data attackOverrideByBuff(obj_id self, combat_data actionData) throws InterruptedException
    {
        int hasAO = getEnhancedSkillStatisticModifierUncapped(self, "attack_override_by_buff");
        if (hasAO < 1)
        {
            return actionData;
        }
        int[] allBuffs = buff.getAllBuffs(self);
        if (allBuffs == null || allBuffs.length == 0)
        {
            return actionData;
        }
        for (int i = 0; i < allBuffs.length; i++)
        {
            String buffName = buff.getBuffNameFromCrc(allBuffs[i]);
            if (buffName.length() != 0 && !buffName.equals("") && buffName.startsWith("attack_override_" + actionData.actionName))
            {
                String[] parse = split(buffName, '|');
                String newAttack = parse[1];
                return combat_engine.getCombatData(newAttack);
            }
        }
        return actionData;
    }
    public void doKillMeterUpdate(obj_id attacker, obj_id defender, int damage) throws InterruptedException
    {
        if (!utils.isProfession(attacker, utils.COMMANDO) && !utils.isProfession(defender, utils.COMMANDO) || damage < 1)
        {
            return;
        }
        if (utils.isProfession(attacker, utils.COMMANDO))
        {
            int damageInterval = utils.getIntScriptVar(attacker, "km.damage_done");
            damageInterval += damage;
            if (damageInterval >= getLevel(attacker) * (15 - Math.round(getKillMeter(attacker) / 5.0f)))
            {
                utils.setScriptVar(attacker, "km.damage_done", 0);
                combat.modifyKillMeter(attacker, 1);
            }
            else 
            {
                utils.setScriptVar(attacker, "km.damage_done", damageInterval);
            }
        }
        if (utils.isProfession(defender, utils.COMMANDO))
        {
            int damageInterval = utils.getIntScriptVar(defender, "km.damage_taken");
            damageInterval += damage;
            if (damageInterval >= getLevel(defender) * (25 - Math.round(getKillMeter(attacker) / 5.0f)))
            {
                utils.setScriptVar(defender, "km.damage_taken", 0);
                combat.modifyKillMeter(defender, 1);
            }
            else 
            {
                utils.setScriptVar(defender, "km.damage_taken", damageInterval);
            }
        }
    }
}
