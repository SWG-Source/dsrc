package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.vehicle;
import script.library.prose;
import script.library.jedi;
import script.library.pet_lib;
import script.library.dot;
import script.library.colors;
import script.library.utils;
import script.library.ai_lib;
import script.library.group;
import script.library.powerup;
import script.library.chat;
import script.library.skill;
import script.library.xp;
import script.library.city;
import script.library.metrics;

public class combat_base_old extends script.base_script
{
    public combat_base_old()
    {
    }
    public static final int CONE = 0;
    public static final int SINGLE_TARGET = 1;
    public static final int AREA = 2;
    public static final int TARGET_AREA = 3;
    public static final int DUAL_WIELD = 4;
    public static final float PVP_DAMAGE_VALUE = .25f;
    public static final int MIN_PVP_DAMAGE = 40;
    public static final float UNIVERSAL_ACTION_COST_MULTIPLIER = 1.0f;
    public static final String COMBAT_DATATABLE = "datatables/combat/combat_data.iff";
    public static final float KNOCKED_DOWN_DAMAGE_INCREASE = 1.50f;
    public static final String JEDI_COMBAT_DATATABLE = "datatables/jedi/jedi_combat_data.iff";
    public static final string_id IMMUNE_TO_EFFECT = new string_id("combat_effects", "immune_to_effect");
    public static final string_id EFFECT_NOT_HOLD = new string_id("combat_effects", "effect_not_hold");
    public static final string_id SID_CANNOT_USE_FORCE = new string_id("combat_effects", "cannot_use_force");
    public static final int MARKER_BLINDED = 2;
    public static final int MARKER_DAMAGED = 3;
    public static final int MARKER_DIZZIED = 4;
    public static final int MARKER_SUPPRESSIONFIRE = 5;
    public static final int MARKER_INTIMIDATED = 6;
    public static final int MARKER_KNOCKDOWN = 7;
    public static final int MARKER_STARTLE = 8;
    public static final int MARKER_STARTLED = 9;
    public static final int MARKER_STUNNED = 10;
    public static final int MARKER_SURPRISED = 11;
    public static final int MARKER_BODYSHOT = 12;
    public static final int MARKER_HEADSHOT = 13;
    public static final int MARKER_LEGSHOT = 14;
    public static final float POSTURE_CHANGE_DELAY = 2.5f;
    public static final float POSTURE_KNOCKED_DOWN_DELAY = 5.0f;
    public static final int WOUND_ROLL_VALUE = 95;
    public static final int MAX_TARGET_ARRAY_SIZE = 10;
    public static final int ALLOWED_LOS_FAILURES = 10;
    public static final float MAX_COMBAT_DISTANCE = 96.0f;
    public static final float MAX_COMBAT_RANGE = 96.0f;
    public static final float MELEE_TARGET_DISTANCE = 24;
    public static final float MAX_THROWING_DISTANCE = 48;
    public static final float CLOSE_THROWING_DISTANCE = 10f;
    public static final float MEDIUM_THROWING_DISTANCE = 20f;
    public static final float FAR_THROWING_DISTANCE = 32f;
    public static final int TARGET_COMMAND = -2;
    public static final int POSTURE_DOWN = -2;
    public static final int POSTURE_UP = -3;
    public static final int POSTURE_UPRIGHT_ACTIVE_DEFENSE_MOD = 10;
    public static final int POSTURE_CROUCHED_ACTIVE_DEFENSE_MOD = 0;
    public static final int PRONE_ACTIVE_DEFENSE_MOD = 0 - 20;
    public static final float TIMESLICE_VALUE = 1f;
    public static final int BASE_TO_HIT = 75;
    public static final int BASE_UNSKILLED = -50;
    public static final int TO_HIT_MAX = 100;
    public static final int TO_HIT_MIN = 0;
    public static final int UNSKILLED_PENALTY = -15;
    public static final int FUMBLE_VALUE = 1;
    public static final int COMBAT_ROLL_MIN = 1;
    public static final int COMBAT_ROLL_MAX = 200;
    public static final int DEFENDER_MOD_MAX = 125;
    public static final int DIZZY_CHECK_VALUE = 85;
    public static final float BLINDED_DEFENSE_MODIFIER = 0 - 60f;
    public static final float STUNNED_DEFENSE_MODIFIER = 0 - 50f;
    public static final float STUNNED_DAMAGE_MODIFIER = 0 - .20f;
    public static final float INTIMIDATED_DEFENSE_MODIFIER = 0 - 20f;
    public static final float INTIMIDATED_DAMAGE_MODIFIER = 0 - .50f;
    public static final float BERSERK_DEFENSE_MODIFIER = 0 - 60f;
    public static final float TUMBLING_DEFENSE_MODIFIER = 25f;
    public static final float NIGHT_TIME_DEFENSE_MODIFIER = 30f;
    public static final float FLARE_DEFENSE_MODIFIER = 0 - 30f;
    public static final float BLINDED_TO_HIT_MODIFIER = 0 - 60f;
    public static final float BERSERK_TO_HIT_MODIFIER = 20f;
    public static final float RALLIED_DEFENSE_MODIFIER = 30f;
    public static final float RALLIED_TO_HIT_MODIFIER = 50f;
    public static final float BERSERK_TIME_MODIFIER = 0;
    public static final float BERSERK_DAMAGE_MODIFIER = 75;
    public static final float FEIGN_DEATH_DAMAGE_MODIFIER = 1.25f;
    public static final float FEIGN_DEATH_PENALTY = -30;
    public static final int EVASIVE_EVADE_MOD = 25;
    public static final int AGGRESSIVE_COUNTERATTACK_MOD = 25;
    public static final float MELEE_WEAPON_DAMAGE_BONUS = 1.25f;
    public static final int COMBAT_ATTITUDE_NONE = 0;
    public static final int COMBAT_ATTITUDE_COUNTERATTACK = 1;
    public static final int COMBAT_ATTITUDE_BLOCK = 2;
    public static final int COMBAT_ATTITUDE_DODGE = 3;
    public static final int COMBAT_ATTITUDE_UNARMED = 4;
    public static final int CLOSE_QUARTER_FIGHTING_MOD = 0 - 20;
    public obj_id[] validateDefenders(obj_id attacker, obj_id[] objDefenders) throws InterruptedException
    {
        String attackerSocialGroup = null;
        if (!isPlayer(attacker) && !pet_lib.isPet(attacker))
        {
            attackerSocialGroup = ai_lib.getSocialGroup(attacker);
        }
        Vector objValidDefenders = new Vector();
        objValidDefenders.setSize(0);
        for (int intI = 0; intI < objDefenders.length; intI++)
        {
            if (!isIdValid(objDefenders[intI]))
            {
                continue;
            }
            if (isDead(objDefenders[intI]))
            {
                continue;
            }
            if (isIncapacitated(objDefenders[intI]))
            {
                continue;
            }
            if (attackerSocialGroup != null && attackerSocialGroup.equals(ai_lib.getSocialGroup(objDefenders[intI])))
            {
                continue;
            }
            objValidDefenders = utils.addElement(objValidDefenders, objDefenders[intI]);
        }
        obj_id[] _objValidDefenders = new obj_id[0];
        if (objValidDefenders != null)
        {
            _objValidDefenders = new obj_id[objValidDefenders.size()];
            objValidDefenders.toArray(_objValidDefenders);
        }
        return _objValidDefenders;
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
    public boolean checkTargets(obj_id objAttacker, obj_id objDefender) throws InterruptedException
    {
        obj_id objTarget = getTarget(objAttacker);
        if (objTarget == objDefender)
        {
            return true;
        }
        return false;
    }
    public int doCombatPreCheck(obj_id objPlayer, obj_id objTarget, int intCommand) throws InterruptedException
    {
        return doCombatPreCheck(objPlayer, objTarget, intCommand, true);
    }
    public int doCombatPreCheck(obj_id objPlayer, obj_id objTarget, int intCommand, boolean changeCombatTarget) throws InterruptedException
    {
        if (vehicle.isRidingVehicle(objPlayer))
        {
            return SCRIPT_OVERRIDE;
        }
        obj_id objMount = getMountId(objPlayer);
        if (objMount != null)
        {
            if (pet_lib.isGalloping(objMount))
            {
                return SCRIPT_OVERRIDE;
            }
        }
        if (changeCombatTarget && !(checkTargets(objPlayer, objTarget)))
        {
            if (checkPosture(objPlayer, objTarget))
            {
                queueCommand(objPlayer, (-390609650), objTarget, "", COMMAND_PRIORITY_FRONT);
                queueCommand(objPlayer, intCommand, null, "", COMMAND_PRIORITY_FRONT);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            if (!(checkPosture(objPlayer, objTarget)))
            {
                return SCRIPT_OVERRIDE;
            }
        }
        float fltDistance = getDistance(objPlayer, objTarget);
        if (fltDistance > MAX_COMBAT_RANGE)
        {
            if (isPlayer(objPlayer))
            {
                setTarget(objPlayer, null);
                clearQueue(objPlayer);
            }
            if (isPlayer(objTarget))
            {
                setTarget(objTarget, null);
                clearQueue(objTarget);
            }
            return SCRIPT_OVERRIDE;
        }
        if (fltDistance < 7)
        {
            int intPosture = getPosture(objPlayer);
            if (intPosture == POSTURE_PRONE)
            {
                obj_id objWeapon = getCurrentWeapon(objPlayer);
                if (objWeapon != null)
                {
                    int intWeaponCategory = combat.getWeaponCategory(getWeaponType(objWeapon));
                    if (intWeaponCategory == combat.RANGED_WEAPON)
                    {
                        if (!(objPlayer.hasScriptVar("minRangeWarning")))
                        {
                            deltadictionary dctVars = objPlayer.getScriptVars();
                            dctVars.put("minRangeWarning", 1);
                            string_id strSpam = new string_id("combat_effects", "prone_ranged_too_close");
                            sendSystemMessage(objPlayer, strSpam);
                        }
                        else 
                        {
                        }
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        if (objPlayer.hasScriptVar("minRangeWarning"))
                        {
                            deltadictionary dctVars = objPlayer.getScriptVars();
                            dctVars.remove("minRangeWarning");
                        }
                    }
                }
            }
            else 
            {
                if (objPlayer.hasScriptVar("minRangeWarning"))
                {
                    deltadictionary dctVars = objPlayer.getScriptVars();
                    dctVars.remove("minRangeWarning");
                }
            }
        }
        else 
        {
            if (objPlayer.hasScriptVar("minRangeWarning"))
            {
                deltadictionary dctVars = objPlayer.getScriptVars();
                dctVars.remove("minRangeWarning");
            }
        }
        return -1;
    }
    public boolean drainCombatActionAttributes(obj_id objPlayer, int intHealthCost, int intActionCost, int intMindCost, int intAction, float fltDelay) throws InterruptedException
    {
        if (!(drainAttributesFastRegen(objPlayer, intHealthCost, intActionCost, intMindCost)))
        {
            string_id strSpam = new string_id("cbt_spam", "pool_drain_fail");
            strSpam = new string_id("cbt_spam", "pool_drain_fail_single");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean drainAttributesFastRegen(obj_id objPlayer, int intHealthCost, int intActionCost, int intMindCost) throws InterruptedException
    {
        if (drainAttributes(objPlayer, intActionCost, intMindCost))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean checkAttributeCost(obj_id objTarget, dictionary dctCost) throws InterruptedException
    {
        int intHealthCost = dctCost.getInt("intHealthCost");
        int intActionCost = dctCost.getInt("intActionCost");
        int intMindCost = dctCost.getInt("intMindCost");
        int intCurrentHealth = getAttrib(objTarget, HEALTH);
        int intCurrentAction = getAttrib(objTarget, ACTION);
        int intCurrentMind = getAttrib(objTarget, MIND);
        if (intCurrentHealth < intHealthCost)
        {
            return false;
        }
        if (intCurrentAction < intActionCost)
        {
            return false;
        }
        if (intCurrentMind < intMindCost)
        {
            return false;
        }
        return true;
    }
    public dictionary getRawActionCost(obj_id objPlayer, String strWeaponType, String[] strCostMods, int intRawHealth, int intRawAction, int intRawMind, float fltHealthModifier, float fltActionModifier, float fltMindModifier) throws InterruptedException
    {
        intRawHealth = (int)(intRawHealth * UNIVERSAL_ACTION_COST_MULTIPLIER);
        intRawAction = (int)(intRawAction * UNIVERSAL_ACTION_COST_MULTIPLIER);
        intRawMind = (int)(intRawMind * UNIVERSAL_ACTION_COST_MULTIPLIER);
        int intHealthCost = (int)(intRawHealth * fltHealthModifier);
        int intActionCost = (int)(intRawAction * fltActionModifier);
        int intMindCost = (int)(intRawMind * fltMindModifier);
        float fltSkillCostMod = getSkillModifiers(objPlayer, strWeaponType + "_", strCostMods);
        intHealthCost = intHealthCost + (int)(intHealthCost * fltSkillCostMod);
        intActionCost = intActionCost + (int)(intActionCost * fltSkillCostMod);
        intMindCost = intMindCost + (int)(intMindCost * fltSkillCostMod);
        dictionary dctReturnValues = new dictionary();
        dctReturnValues.put("intHealthCost", intHealthCost);
        dctReturnValues.put("intActionCost", intActionCost);
        dctReturnValues.put("intMindCost", intMindCost);
        return dctReturnValues;
    }
    public float getSkillModifiers(obj_id objPlayer, String strAdditiveString, String[] strSkillMods) throws InterruptedException
    {
        int intI = 0;
        float fltSkillMod = 0;
        while (intI < strSkillMods.length)
        {
            fltSkillMod = fltSkillMod + getEnhancedSkillStatisticModifier(objPlayer, strAdditiveString + strSkillMods[intI]);
            intI = intI + 1;
        }
        return fltSkillMod;
    }
    public float calculateTime(obj_id objPlayer, String strWeaponType, float fltDefaultTime, String[] strTimeMods) throws InterruptedException
    {
        float fltTimeMod = getSkillModifiers(objPlayer, strWeaponType + "_", strTimeMods);
        fltTimeMod = fltTimeMod / 100;
        fltDefaultTime = fltDefaultTime - (fltTimeMod * fltDefaultTime);
        if (fltDefaultTime < TIMESLICE_VALUE)
        {
            fltDefaultTime = TIMESLICE_VALUE;
        }
        return fltDefaultTime;
    }
    public weapon_data getRawDamage(obj_id objPlayer, weapon_data cbtWeaponData, float fltDamageModifier, String[] strDamageMods, boolean boolDontIgnoreCerts) throws InterruptedException
    {
        obj_id objWeapon = cbtWeaponData.id;
        if (isPlayer(objPlayer))
        {
            if (boolDontIgnoreCerts)
            {
                if (!combat.hasCertification(objPlayer, objWeapon))
                {
                    cbtWeaponData.minDamage = 5;
                    cbtWeaponData.maxDamage = 10;
                }
            }
        }
        float fltMinDamage = (float)cbtWeaponData.minDamage;
        float fltMaxDamage = (float)cbtWeaponData.maxDamage;
        int intState = 0;
        if (intState > 0)
        {
            if (isMeleeWeapon(cbtWeaponData.id))
            {
                fltMinDamage = fltMinDamage + BERSERK_DAMAGE_MODIFIER;
                fltMaxDamage = fltMaxDamage + BERSERK_DAMAGE_MODIFIER;
            }
        }
        if (cbtWeaponData.weaponType == WEAPON_TYPE_UNARMED)
        {
            int intSkillMod = getSkillStatisticModifier(objPlayer, "unarmed_damage");
            fltMinDamage = fltMinDamage + intSkillMod;
            fltMaxDamage = fltMaxDamage + intSkillMod;
        }
        if (isPlayer(objPlayer))
        {
            fltMinDamage = fltMinDamage * 1.5f;
            fltMaxDamage = fltMaxDamage * 1.5f;
        }
        else 
        {
            fltMinDamage = fltMaxDamage / 2;
            fltMinDamage = fltMinDamage * 1.0f;
            fltMaxDamage = fltMaxDamage * 1.0f;
        }
        if ((cbtWeaponData.weaponType == WEAPON_TYPE_POLEARM) || (cbtWeaponData.weaponType == WEAPON_TYPE_1HAND_MELEE) || (cbtWeaponData.weaponType == WEAPON_TYPE_2HAND_MELEE))
        {
            fltMinDamage = fltMinDamage * 1.25f;
            fltMaxDamage = fltMaxDamage * 1.25f;
        }
        float fltStateMod = 0;
        if (fltStateMod < 0)
        {
            fltStateMod = 0;
        }
        fltMinDamage = fltMinDamage * fltDamageModifier;
        fltMaxDamage = fltMaxDamage * fltDamageModifier;
        fltMinDamage = fltMinDamage * fltStateMod;
        fltMaxDamage = fltMaxDamage * fltStateMod;
        cbtWeaponData.minDamage = (int)fltMinDamage;
        cbtWeaponData.maxDamage = (int)fltMaxDamage;
        if (cbtWeaponData.minDamage <= 0)
        {
            cbtWeaponData.minDamage = 1;
        }
        return cbtWeaponData;
    }
    public int getHitMod(obj_id objPlayer, String strWeaponType, String[] strToHitMods) throws InterruptedException
    {
        return getHitMod(objPlayer, strWeaponType, strToHitMods, null);
    }
    public int getHitMod(obj_id objPlayer, String strWeaponType, String[] strToHitMods, dictionary dctCombatInfo) throws InterruptedException
    {
        int intToHitBonus = 0;
        float fltToHitMod = 0;
        float fltStateMod = 0;
        if (!isPlayer(objPlayer))
        {
            fltToHitMod = (float)getMobHitMod(objPlayer);
        }
        else 
        {
            String strOverloadAccuracyMod = null;
            if (dctCombatInfo != null)
            {
                strOverloadAccuracyMod = dctCombatInfo.getString("strOverloadAccuracyMod");
            }
            if ((strOverloadAccuracyMod != null) && (!strOverloadAccuracyMod.equals("")))
            {
                fltToHitMod = getEnhancedSkillStatisticModifier(objPlayer, strOverloadAccuracyMod);
            }
            else 
            {
                fltToHitMod = 0;
                fltToHitMod = fltToHitMod + getSkillModifiers(objPlayer, strWeaponType + "_", strToHitMods);
                if (fltToHitMod == 0)
                {
                    fltToHitMod = fltToHitMod + (float)UNSKILLED_PENALTY;
                }
            }
        }
        intToHitBonus = (int)(fltToHitMod + fltStateMod);
        if (utils.hasScriptVar(objPlayer, "inAlignedStructure"))
        {
            if (strWeaponType.equals("pistol"))
            {
                if (hasSkill(objPlayer, "combat_pistol_novice"))
                {
                    float fltHitBonus = (float)intToHitBonus;
                    fltHitBonus = fltHitBonus * 1.15f;
                    intToHitBonus = (int)fltHitBonus;
                }
            }
            else if (strWeaponType.equals("carbine"))
            {
                if (hasSkill(objPlayer, "combat_carbine_novice"))
                {
                    float fltHitBonus = (float)intToHitBonus;
                    fltHitBonus = fltHitBonus * 1.15f;
                    intToHitBonus = (int)fltHitBonus;
                }
            }
        }
        return intToHitBonus;
    }
    public hit_result[] runHitEngine(attacker_data cbtAttackerData, weapon_data cbtWeaponData, defender_data[] cbtDefenderData) throws InterruptedException
    {
        return runHitEngine(cbtAttackerData, cbtWeaponData, cbtDefenderData, false);
    }
    public hit_result[] runHitEngine(attacker_data cbtAttackerData, weapon_data cbtWeaponData, defender_data[] cbtDefenderData, boolean boolAllowHitSelf) throws InterruptedException
    {
        float fltDefenseModifier = 0;
        hit_result[] cbtHitData = new hit_result[cbtDefenderData.length];
        boolean degradedWeapon = false;
        int degradeRoll = rand(1, 100);
        if (degradeRoll < 5)
        {
            if (damageWeapon(cbtAttackerData.id, cbtWeaponData.id, 1))
            {
                degradedWeapon = true;
            }
        }
        if (hasObjVar(cbtWeaponData.id, powerup.VAR_POWERUP_USES_LEFT))
        {
            powerup.decrementUseCounter(cbtWeaponData.id);
            if (!degradedWeapon && rand(1, 100) < 15)
            {
                damageWeapon(cbtAttackerData.id, cbtWeaponData.id, 1);
            }
        }
        int intOldMinDamage = cbtWeaponData.minDamage;
        int intOldMaxDamage = cbtWeaponData.maxDamage;
        for (int intI = 0; intI < cbtDefenderData.length; intI++)
        {
            if (!(isMob(cbtDefenderData[intI].id)))
            {
                cbtAttackerData.scriptMod = 500;
            }
            obj_id objTest = null;
            int intK = 0;
            int intState = 0;
            if (intState > 0)
            {
                int intPosture = cbtDefenderData[intI].posture;
                if (intPosture != POSTURE_INCAPACITATED)
                {
                    fltDefenseModifier = -100000f;
                }
                else 
                {
                    fltDefenseModifier = FEIGN_DEATH_PENALTY;
                }
            }
            else 
            {
                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "State Defense Mods = " + fltDefenseModifier);
            }
            int intWeaponCategory = combat.getWeaponCategory(getWeaponType(cbtWeaponData.id));
            String strDefenseBonus = "";
            String strMitigationMod = "";
            if (intWeaponCategory == combat.RANGED_WEAPON)
            {
                strDefenseBonus = "ranged_defense";
                strMitigationMod = "ranged_damage_mitigation";
            }
            else 
            {
                strMitigationMod = "melee_damage_mitigation";
                strDefenseBonus = "melee_defense";
            }
            int baseDefense = getEnhancedSkillStatisticModifier(cbtDefenderData[intI].id, strDefenseBonus);
            if (baseDefense > DEFENDER_MOD_MAX)
            {
                baseDefense = DEFENDER_MOD_MAX;
            }
            combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Base Defense = " + baseDefense);
            fltDefenseModifier += baseDefense;
            float hitBonus = getHitBonus(intWeaponCategory, cbtDefenderData[intI].id);
            combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "toHit Mod = -" + hitBonus);
            fltDefenseModifier -= hitBonus;
            cbtWeaponData = calculateDamageMitigation(cbtDefenderData[intI].id, strMitigationMod, cbtWeaponData, intOldMinDamage, intOldMaxDamage);
            if (isPlayer(cbtDefenderData[intI].id) && isPlayer(cbtAttackerData.id))
            {
                int city_id = city.checkCity(cbtDefenderData[intI].id, false);
                if (city_id > 0 && city.cityHasSpec(city_id, city.SF_SPEC_STRONGHOLD) && city.isMilitiaOfCity(cbtDefenderData[intI].id, city_id))
                {
                    combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Stronghold Bonus = +90");
                    fltDefenseModifier += 90;
                }
            }
            if (utils.hasScriptVar(cbtDefenderData[intI].id, "food.dodge_attack.eff"))
            {
                int eff = utils.getIntScriptVar(cbtDefenderData[intI].id, "food.dodge_attack.eff");
                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Food Dodge Buff = +" + eff);
                fltDefenseModifier += eff;
            }
            if (utils.hasScriptVar(cbtAttackerData.id, "food.attack_accuracy.eff"))
            {
                int eff = utils.getIntScriptVar(cbtAttackerData.id, "food.attack_accuracy.eff");
                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Food Accuracy Mod = -" + eff);
                fltDefenseModifier -= eff;
            }
            if (utils.hasScriptVar(cbtAttackerData.id, "dead_eye.active"))
            {
                int dea = utils.getIntScriptVar(cbtAttackerData.id, "dead_eye.active");
                float dur = utils.getFloatScriptVar(cbtAttackerData.id, "dead_eye.dur");
                if (getGameTime() - dea < dur)
                {
                    int eff = utils.getIntScriptVar(cbtAttackerData.id, "dead_eye.eff");
                    combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Dead Eye Mod = -" + eff);
                    fltDefenseModifier -= eff;
                }
            }
            obj_id checkId = cbtDefenderData[intI].id;
            if (isIdValid(checkId) && utils.hasScriptVar(checkId, "trapmod.enable"))
            {
                if (intWeaponCategory == combat.RANGED_WEAPON)
                {
                    if (utils.hasScriptVar(checkId, "trapmod.ranged_defense"))
                    {
                        int eff = utils.getIntScriptVar(checkId, "trapmod.ranged_defense");
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Trap Ranged Mod = -" + eff);
                        fltDefenseModifier -= eff;
                    }
                }
                else 
                {
                    if (utils.hasScriptVar(checkId, "trapmod.melee_defense"))
                    {
                        int eff = utils.getIntScriptVar(checkId, "trapmod.melee_defense");
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Trap Melee Mod = -" + eff);
                        fltDefenseModifier -= eff;
                    }
                }
            }
            if (isIdValid(checkId) && group.isGrouped(checkId))
            {
                obj_id groupLeader = group.getLeader(checkId);
                if (intWeaponCategory == combat.RANGED_WEAPON)
                {
                    int eff = getEnhancedSkillStatisticModifier(groupLeader, "group_ranged_defense");
                    combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "SL Ranged Bonus = +" + eff);
                    fltDefenseModifier += eff;
                }
                else 
                {
                    int eff = getEnhancedSkillStatisticModifier(groupLeader, "group_melee_defense");
                    combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "SL Melee Bonus = +" + eff);
                    fltDefenseModifier += eff;
                }
            }
            combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "runHitEngine", "Final Defense Mod = " + fltDefenseModifier);
            cbtDefenderData[intI].scriptMod = (int)fltDefenseModifier;
            cbtHitData[intI] = calculateHit(cbtAttackerData, cbtDefenderData[intI], cbtWeaponData);
            if (cbtAttackerData.id == cbtDefenderData[intI].id)
            {
                if (!boolAllowHitSelf)
                {
                    cbtHitData[intI].success = false;
                }
            }
            else 
            {
                if (pvpCanAttack(cbtAttackerData.id, cbtDefenderData[intI].id))
                {
                    pvpAttackPerformed(cbtAttackerData.id, cbtDefenderData[intI].id);
                }
            }
            obj_id objAttackerWeapon = cbtWeaponData.id;
            obj_id objDefenderWeapon = getCurrentWeapon(cbtDefenderData[intI].id);
            if (cbtHitData[intI].success)
            {
                int intDefenderWeaponType = getWeaponType(objDefenderWeapon);
                int intDefenderWeaponCategory = combat.getWeaponCategory(intDefenderWeaponType);
                int intAttackerWeaponCategory = combat.getWeaponCategory(cbtWeaponData.weaponType);
                if ((intAttackerWeaponCategory != combat.RANGED_WEAPON) && (intDefenderWeaponCategory == combat.RANGED_WEAPON))
                {
                    float fltDamageBonus = getDamageBonus(intDefenderWeaponType);
                    cbtHitData[intI].damage = (int)(cbtHitData[intI].damage * fltDamageBonus);
                }
                int intDefenderPosture = getPosture(cbtDefenderData[intI].id);
                if (intDefenderPosture == POSTURE_KNOCKED_DOWN)
                {
                    float fltDamage = cbtHitData[intI].damage;
                    fltDamage = fltDamage * KNOCKED_DOWN_DAMAGE_INCREASE;
                    cbtHitData[intI].damage = (int)fltDamage;
                }
            }
            if (intI == 0)
            {
                if (!(cbtHitData[intI].canSee))
                {
                    if (!hasObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed"))
                    {
                        int intLineOfSightFailed = 1;
                        setObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed", intLineOfSightFailed);
                        string_id strSpam = new string_id("cbt_spam", "los_fail");
                        sendSystemMessage(cbtAttackerData.id, strSpam);
                        strSpam = new string_id("cbt_spam", "los_recycle");
                        return null;
                    }
                    else 
                    {
                        int intLineOfSightFailed = getIntObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed");
                        intLineOfSightFailed = intLineOfSightFailed + 1;
                        if (intLineOfSightFailed >= ALLOWED_LOS_FAILURES)
                        {
                            string_id strSpam = new string_id("cbt_spam", "target_lost");
                            sendSystemMessage(cbtAttackerData.id, strSpam);
                            removeObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed");
                            clearQueue(cbtAttackerData.id);
                            setCombatTarget(cbtAttackerData.id, null);
                            return null;
                        }
                        else 
                        {
                            setObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed", intLineOfSightFailed);
                        }
                        string_id strSpam = new string_id("cbt_spam", "los_recycle");
                        return null;
                    }
                }
                else 
                {
                    if (hasObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed"))
                    {
                        removeObjVar(cbtAttackerData.id, "combat.intLineOfSightFailed");
                    }
                }
            }
            if (intState > 0)
            {
                if (cbtDefenderData[intI].posture != POSTURE_INCAPACITATED)
                {
                    cbtHitData[intI].damage = cbtHitData[intI].damage + (int)(cbtHitData[intI].damage * FEIGN_DEATH_DAMAGE_MODIFIER);
                    obj_id[] objEnemies = getWhoIsTargetingMe(cbtDefenderData[intI].id);
                    if (objEnemies != null)
                    {
                        int intJ = 0;
                        while (intJ < objEnemies.length)
                        {
                            if (isPlayer(objEnemies[intJ]))
                            {
                                setTarget(objEnemies[intJ], null);
                            }
                            queueClearCommandsFromGroup(objEnemies[intJ], (-506878646));
                            queueClearCommandsFromGroup(objEnemies[intJ], (-1170591580));
                            queueClearCommandsFromGroup(objEnemies[intJ], (391413347));
                            intJ = intJ + 1;
                        }
                    }
                }
                else 
                {
                }
            }
            obj_id objAttacker = cbtAttackerData.id;
            if (isPlayer(objAttacker))
            {
                if (isPlayer(cbtDefenderData[intI].id))
                {
                    float fltDamage = (float)cbtHitData[intI].damage;
                    if (fltDamage > MIN_PVP_DAMAGE)
                    {
                        fltDamage = fltDamage * PVP_DAMAGE_VALUE;
                        if (fltDamage < MIN_PVP_DAMAGE)
                        {
                            fltDamage = MIN_PVP_DAMAGE + rand(1, 4);
                        }
                        cbtHitData[intI].damage = (int)fltDamage;
                    }
                }
            }
        }
        return cbtHitData;
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
    public void finalizeDamage(obj_id objAttacker, weapon_data cbtWeaponData, defender_data[] cbtDefenderData, hit_result[] cbtHitData, defender_results[] cbtDefenderResults, dictionary dctCombatInfo) throws InterruptedException
    {
        obj_id[] objDefenders = new obj_id[cbtDefenderData.length];
        int[] intResults = new int[cbtHitData.length];
        for (int intI = 0; intI < cbtHitData.length; intI++)
        {
            if (cbtHitData[intI].success)
            {
                float fltDamageMultiplier = getFloatObjVar(cbtDefenderData[intI].id, "fltDamageModifier");
                if (fltDamageMultiplier > 0)
                {
                    cbtHitData[intI].damage = (int)(cbtHitData[intI].damage * fltDamageMultiplier);
                }
                int intWeaponCategory = combat.getWeaponCategory(cbtWeaponData.weaponType);
                if ((intWeaponCategory != combat.RANGED_WEAPON) || (isJedi(cbtDefenderData[intI].id)))
                {
                    obj_id objDefenderWeapon = getCurrentWeapon(cbtDefenderData[intI].id);
                    int intWeaponType = getWeaponType(objDefenderWeapon);
                    String strModName = getWeaponStringType(intWeaponType);
                    strModName = strModName + "_toughness";
                    int intMod = getSkillStatisticModifier(cbtDefenderData[intI].id, strModName);
                    float fltModifier = intMod;
                    if (fltModifier > 95)
                    {
                        fltModifier = 95;
                    }
                    fltModifier = fltModifier / 100;
                    cbtHitData[intI].damage = cbtHitData[intI].damage - (int)(cbtHitData[intI].damage * fltModifier);
                }
                if (dctCombatInfo == null)
                {
                    doWrappedDamage(objAttacker, cbtDefenderData[intI].id, cbtWeaponData.id, cbtHitData[intI], dctCombatInfo);
                    dot.applyCombatDots(objAttacker, cbtDefenderData[intI].id, cbtHitData[intI], cbtWeaponData, 1.0f);
                }
                else 
                {
                    int intHitHead = dctCombatInfo.getInt("intHitHead");
                    int intHitBody = dctCombatInfo.getInt("intHitBody");
                    int intHitLegs = dctCombatInfo.getInt("intHitLegs");
                    float fltBleedingValue = dctCombatInfo.getFloat("fltBleedingValue");
                    if ((intHitHead == 0) && (intHitBody == 0) && (intHitLegs == 0))
                    {
                        doWrappedDamage(objAttacker, cbtDefenderData[intI].id, cbtWeaponData.id, cbtHitData[intI], dctCombatInfo);
                        dot.applyCombatDots(objAttacker, cbtDefenderData[intI].id, cbtHitData[intI], cbtWeaponData, 1.0f);
                    }
                    else 
                    {
                        int intRawDamage = cbtHitData[intI].damage;
                        int intLocationTotals = intHitHead + intHitBody + intHitLegs;
                        if (intHitHead > 0)
                        {
                            int[] intHitLocations = 
                            {
                                HIT_LOCATION_HEAD
                            };
                            cbtHitData[intI].hitLocation = intHitLocations[rand(0, intHitLocations.length - 1)];
                            cbtHitData[intI].damage = intRawDamage / intLocationTotals;
                            doWrappedDamage(objAttacker, cbtDefenderData[intI].id, cbtWeaponData.id, cbtHitData[intI], dctCombatInfo);
                        }
                        if (intHitLegs > 0)
                        {
                            int[] intHitLocations = 
                            {
                                HIT_LOCATION_R_LEG,
                                HIT_LOCATION_L_LEG
                            };
                            cbtHitData[intI].hitLocation = intHitLocations[rand(0, intHitLocations.length - 1)];
                            cbtHitData[intI].damage = intRawDamage / intLocationTotals;
                            doWrappedDamage(objAttacker, cbtDefenderData[intI].id, cbtWeaponData.id, cbtHitData[intI], dctCombatInfo);
                        }
                        if (intHitBody > 0)
                        {
                            int[] intHitLocations = 
                            {
                                HIT_LOCATION_L_ARM,
                                HIT_LOCATION_R_ARM,
                                HIT_LOCATION_BODY
                            };
                            cbtHitData[intI].hitLocation = intHitLocations[rand(0, intHitLocations.length - 1)];
                            cbtHitData[intI].damage = intRawDamage / intLocationTotals;
                            doWrappedDamage(objAttacker, cbtDefenderData[intI].id, cbtWeaponData.id, cbtHitData[intI], dctCombatInfo);
                        }
                        cbtHitData[intI].damage = intRawDamage;
                    }
                    if (fltBleedingValue > 0)
                    {
                        int intBleedingDuration = dctCombatInfo.getInt("intBleedingDuration");
                        int intBleedMind = dctCombatInfo.getInt("intBleedMind");
                        int intBleedHealth = dctCombatInfo.getInt("intBleedHealth");
                        int intBleedAction = dctCombatInfo.getInt("intBleedAction");
                        int intBleedTotals = intBleedMind + intBleedAction + intBleedHealth;
                        float fltRawDamage = (float)cbtHitData[intI].damage;
                        fltRawDamage = fltRawDamage * fltBleedingValue;
                        fltRawDamage = fltRawDamage / intBleedTotals;
                        int intBleedDamage = (int)fltRawDamage;
                        if (intBleedMind > 0)
                        {
                            dot.applyBleedingEffect(cbtDefenderData[0].id, "bleedingMindSpecial" + objAttacker, MIND, intBleedDamage, intBleedingDuration);
                        }
                        if (intBleedHealth > 0)
                        {
                            dot.applyBleedingEffect(cbtDefenderData[0].id, "bleedingHealthSpecial" + objAttacker, HEALTH, intBleedDamage, intBleedingDuration);
                        }
                        if (intBleedAction > 0)
                        {
                            dot.applyBleedingEffect(cbtDefenderData[0].id, "bleedingActionSpecial" + objAttacker, ACTION, intBleedDamage, intBleedingDuration);
                        }
                    }
                }
            }
            else 
            {
            }
            objDefenders[intI] = cbtDefenderData[intI].id;
            intResults[intI] = cbtDefenderResults[intI].result;
        }
        doCombatFlyText(cbtDefenderData, objAttacker, cbtHitData, cbtDefenderResults);
        callDefenderCombatAction(objDefenders, intResults, objAttacker, cbtWeaponData.id);
        return;
    }
    public void doWrappedDamage(obj_id objAttacker, obj_id objDefender, obj_id objWeapon, hit_result cbtHitData, dictionary dctCombatInfo) throws InterruptedException
    {
        if (utils.hasScriptVar(objDefender, "food.mitigate_damage.eff"))
        {
            int eff = utils.getIntScriptVar(objDefender, "food.mitigate_damage.eff");
            int dur = utils.getIntScriptVar(objDefender, "food.mitigate_damage.dur");
            dur--;
            if (dur <= 0)
            {
                clearBuffIcon(objDefender, "food.mitigate_damage");
                utils.removeScriptVarTree(objDefender, "food.mitigate_damage");
            }
            else 
            {
                utils.setScriptVar(objDefender, "food.mitigate_damage.dur", dur);
            }
            int mitigated = (int)(cbtHitData.damage * (eff / 100.f));
            cbtHitData.damage -= mitigated;
            if (cbtHitData.damage < 0)
            {
                cbtHitData.damage = 0;
            }
        }
        if (dctCombatInfo == null)
        {
            doDamage(objAttacker, objDefender, objWeapon, cbtHitData);
            return;
        }
        else 
        {
            int intOverloadWeapon = dctCombatInfo.getInt("intOverloadWeapon");
            if (intOverloadWeapon > 0)
            {
                int intDamageType = dctCombatInfo.getInt("intDamageType");
                int intDamageRating = dctCombatInfo.getInt("intDamageRating");
                if ((intDamageType > -1) || (intDamageRating > -1))
                {
                    if (intDamageType < 0)
                    {
                        intDamageType = 0;
                    }
                    if (intDamageRating < 0)
                    {
                        intDamageRating = 0;
                    }
                    damage(objDefender, intDamageType, cbtHitData.hitLocation, cbtHitData.damage);
                    xp.updateCombatXpList(objDefender, objAttacker, "jedi_general", cbtHitData.damage);
                    return;
                }
                else 
                {
                    doDamage(objAttacker, objDefender, objWeapon, cbtHitData);
                    return;
                }
            }
            else 
            {
                doDamage(objAttacker, objDefender, objWeapon, cbtHitData);
                return;
            }
        }
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
        return;
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
    public void doCombatFlyText(defender_data[] cbtDefenderData, obj_id objAttacker, hit_result[] cbtHitData, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        for (int intI = 0; intI < cbtHitData.length; intI++)
        {
            string_id strSpam = null;
            color colFlyText = colors.WHITE;
            if (cbtHitData[intI].success)
            {
                String strId = getHitSpam(cbtHitData[intI]);
                strSpam = new string_id("combat_effects", "hit_" + strId);
                colFlyText = getHitColor(cbtHitData[intI].hitLocation);
            }
            else 
            {
                if (cbtDefenderResults[intI].result == COMBAT_RESULT_MISS)
                {
                    strSpam = new string_id("combat_effects", "miss");
                }
            }
            if (strSpam != null)
            {
                if (isMob(cbtDefenderData[intI].id) && (!vehicle.isDriveableVehicle(cbtDefenderData[intI].id)))
                {
                    showFlyTextPrivate(cbtDefenderData[intI].id, objAttacker, strSpam, 1.0f, colFlyText);
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
    public void setDefenderCombatResults(weapon_data cbtWeaponData, attacker_data cbtAttackerData, attacker_results cbtAttackerResults, hit_result[] cbtHitData, defender_data[] cbtDefenderData, defender_results[] cbtDefenderResults, String[] strBlockMods, int intBlockMod, String[] strEvadeMods, int intEvadeMod, String[] strCounterAttackMods, int intCounterAttackMod, boolean boolDefendable) throws InterruptedException
    {
        int intDefenderCombatState;
        int intI = 0;
        int intAttackerMod;
        String strAttackerWeaponName = getWeaponStringType(cbtWeaponData.weaponType);
        String strAttackerMod = "";
        String strDefenderMod = "";
        while (intI < cbtDefenderData.length)
        {
            if (cbtHitData[intI].success)
            {
                if (boolDefendable)
                {
                    obj_id objDefenderWeapon = getCurrentWeapon(cbtDefenderData[intI].id);
                    int intDefenderWeaponType = getWeaponType(objDefenderWeapon);
                    String strDefenderWeaponName = getWeaponStringType(intDefenderWeaponType);
                    String[] strSkillMods = null;
                    int intEventMod = 0;
                    int intDefenderMod = 0;
                    int intCombatAttitude = getCombatAttitude(cbtDefenderData[intI].id, intDefenderWeaponType);
                    if (intCombatAttitude > 0)
                    {
                        switch (intCombatAttitude)
                        {
                            case COMBAT_ATTITUDE_DODGE:
                            strSkillMods = strEvadeMods;
                            intEventMod = intEvadeMod;
                            intDefenderMod = EVASIVE_EVADE_MOD;
                            strAttackerMod = "anti_dodge";
                            strDefenderMod = "dodge";
                            break;
                            case COMBAT_ATTITUDE_BLOCK:
                            strSkillMods = strBlockMods;
                            intEventMod = intBlockMod;
                            intDefenderMod = 0;
                            strAttackerMod = "anti_block";
                            strDefenderMod = "block";
                            break;
                            case COMBAT_ATTITUDE_COUNTERATTACK:
                            strSkillMods = strCounterAttackMods;
                            intEventMod = intCounterAttackMod;
                            intDefenderMod = AGGRESSIVE_COUNTERATTACK_MOD;
                            strAttackerMod = "anti_counterattack";
                            strDefenderMod = "counterattack";
                            break;
                            case COMBAT_ATTITUDE_UNARMED:
                            int intRoll = rand(1, 3);
                            if (intRoll == 1)
                            {
                                strSkillMods = strEvadeMods;
                                intEventMod = intEvadeMod;
                                intDefenderMod = EVASIVE_EVADE_MOD;
                                strAttackerMod = "anti_dodge";
                                strDefenderMod = "private_unarmed_passive_defense";
                                intCombatAttitude = COMBAT_ATTITUDE_DODGE;
                                break;
                            }
                            if (intRoll == 2)
                            {
                                strSkillMods = strBlockMods;
                                intEventMod = intBlockMod;
                                intDefenderMod = 0;
                                strAttackerMod = "anti_block";
                                strDefenderMod = "private_unarmed_passive_defense";
                                intCombatAttitude = COMBAT_ATTITUDE_BLOCK;
                            }
                            if (intRoll == 3)
                            {
                                strSkillMods = strCounterAttackMods;
                                intEventMod = intCounterAttackMod;
                                intDefenderMod = AGGRESSIVE_COUNTERATTACK_MOD;
                                strAttackerMod = "anti_counterattack";
                                strDefenderMod = "private_unarmed_passive_defense";
                                intCombatAttitude = COMBAT_ATTITUDE_COUNTERATTACK;
                            }
                            break;
                        }
                        int intPostureDefenseMod = 0;
                        switch (cbtDefenderData[intI].posture)
                        {
                        }
                        int intAttackerRoll = getSkillStatisticModifier(cbtAttackerData.id, strAttackerMod);
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "(deprecated)Base Attack Roll = " + intAttackerRoll);
                        int intDefenderRoll = getSkillStatisticModifier(cbtDefenderData[intI].id, strDefenderMod);
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Base Defense Roll = " + intDefenderRoll);
                        if (intDefenderRoll > DEFENDER_MOD_MAX)
                        {
                            intDefenderRoll = DEFENDER_MOD_MAX;
                        }
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Modified Defense = " + intDefenderRoll);
                        intDefenderRoll = intDefenderRoll + intPostureDefenseMod;
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Posture Defense Mod = " + intPostureDefenseMod);
                        int defenseRoll = rand(COMBAT_ROLL_MIN, COMBAT_ROLL_MAX);
                        intDefenderRoll += defenseRoll;
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Rand Defense Roll = " + defenseRoll);
                        intAttackerRoll = cbtHitData[intI].finalRoll + intEventMod;
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "hitData Attack Roll = " + cbtHitData[intI].finalRoll);
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Event Modifier = " + intEventMod);
                        String strWeaponType = getWeaponStringType(getWeaponType(getCurrentWeapon(cbtDefenderData[intI].id)));
                        int intBonus = utils.getIntScriptVar(cbtDefenderData[intI].id, "COB_" + strWeaponType);
                        intDefenderRoll = intDefenderRoll + intBonus;
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "COB Modifier = " + intBonus);
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Final Defense Roll = " + intDefenderRoll);
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Final Attack Roll = " + intAttackerRoll);
                        if (intDefenderRoll >= intAttackerRoll)
                        {
                            if (intCombatAttitude == COMBAT_ATTITUDE_DODGE)
                            {
                                cbtDefenderResults[intI].result = COMBAT_RESULT_EVADE;
                                cbtHitData[intI].success = false;
                                string_id strSpam = new string_id("combat_effects", "dodge");
                                showFlyTextPrivate(cbtDefenderData[intI].id, cbtAttackerData.id, strSpam, 1.0f, colors.WHITE);
                                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Successful Dodge");
                            }
                            else if (intCombatAttitude == COMBAT_ATTITUDE_BLOCK)
                            {
                                cbtDefenderResults[intI].result = COMBAT_RESULT_BLOCK;
                                cbtHitData[intI].damage = cbtHitData[intI].damage / 2;
                                string_id strSpam = new string_id("combat_effects", "block");
                                showFlyTextPrivate(cbtDefenderData[intI].id, cbtAttackerData.id, strSpam, 1.0f, colors.WHITE);
                                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Successful Block");
                            }
                            else if (intCombatAttitude == COMBAT_ATTITUDE_COUNTERATTACK)
                            {
                                doCounterAttack(cbtAttackerData.id, cbtDefenderData[intI].id);
                                cbtDefenderResults[intI].result = COMBAT_RESULT_COUNTER;
                                string_id strSpam = new string_id("combat_effects", "counterattack");
                                showFlyTextPrivate(cbtDefenderData[intI].id, cbtAttackerData.id, strSpam, 1.0f, colors.WHITE);
                                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Successful Counterattack");
                                cbtHitData[intI].success = false;
                            }
                            else 
                            {
                                cbtDefenderResults[intI].result = COMBAT_RESULT_HIT;
                                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Attack Hit");
                            }
                        }
                        else 
                        {
                            cbtDefenderResults[intI].result = COMBAT_RESULT_HIT;
                            combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Attack Hit");
                        }
                    }
                    else 
                    {
                        cbtDefenderResults[intI].result = COMBAT_RESULT_HIT;
                        combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Attack Hit");
                    }
                }
                else 
                {
                    cbtDefenderResults[intI].result = COMBAT_RESULT_HIT;
                    combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Attack Hit");
                }
            }
            else 
            {
                cbtDefenderResults[intI].result = COMBAT_RESULT_MISS;
                combatLog(cbtAttackerData.id, cbtDefenderData[intI].id, "setDefenderCombatResults", "Attack Missed");
            }
            intBlockMod = getSkillStatisticModifier(cbtDefenderData[intI].id, "saber_block");
            if (intBlockMod > 0)
            {
                LOG("jedi", "Hasjedi block c0mmand");
                obj_id objDefenderWeapon = getCurrentWeapon(cbtDefenderData[intI].id);
                int intWeaponType = getWeaponType(objDefenderWeapon);
                LOG("jedi", "weapontType is " + intWeaponType);
                if (jedi.isLightsaber(intWeaponType))
                {
                    LOG("jedi", "it's a lightsaber");
                    int intWeaponCategory = combat.getWeaponCategory(getWeaponType(cbtWeaponData.id));
                    if (intWeaponCategory == combat.RANGED_WEAPON)
                    {
                        int intRoll = rand(0, 100);
                        if (intRoll < intBlockMod)
                        {
                            cbtDefenderResults[intI].result = COMBAT_RESULT_LIGHTSABER_BLOCK;
                            cbtHitData[intI].success = false;
                        }
                    }
                }
            }
            intI = intI + 1;
        }
    }
    public int getCombatAttitude(obj_id objPlayer, int intWeaponType) throws InterruptedException
    {
        if (vehicle.isVehicle(objPlayer))
        {
            return -1;
        }
        switch (intWeaponType)
        {
            case WEAPON_TYPE_RIFLE:
            return COMBAT_ATTITUDE_BLOCK;
            case WEAPON_TYPE_LIGHT_RIFLE:
            return COMBAT_ATTITUDE_COUNTERATTACK;
            case WEAPON_TYPE_PISTOL:
            return COMBAT_ATTITUDE_DODGE;
            case WEAPON_TYPE_HEAVY:
            return COMBAT_ATTITUDE_NONE;
            case WEAPON_TYPE_1HAND_MELEE:
            return COMBAT_ATTITUDE_DODGE;
            case WEAPON_TYPE_2HAND_MELEE:
            return COMBAT_ATTITUDE_COUNTERATTACK;
            case WEAPON_TYPE_UNARMED:
            return COMBAT_ATTITUDE_UNARMED;
            case WEAPON_TYPE_POLEARM:
            return COMBAT_ATTITUDE_BLOCK;
            case WEAPON_TYPE_THROWN:
            return COMBAT_ATTITUDE_NONE;
            case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
            return COMBAT_ATTITUDE_NONE;
            case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
            return COMBAT_ATTITUDE_NONE;
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return COMBAT_ATTITUDE_NONE;
        }
        return COMBAT_ATTITUDE_NONE;
    }
    public void applyPostures(attacker_data cbtAttackerData, attacker_results cbtAttackerResults, hit_result[] cbtHitData, defender_data[] cbtDefenderData, defender_results[] cbtDefenderResults, int intAttackerEndPosture, int intDefenderEndPosture, weapon_data cbtWeaponData) throws InterruptedException
    {
        int intState = 0;
        if (intDefenderEndPosture != POSTURE_NONE)
        {
            for (int intI = 0; intI < cbtDefenderResults.length; intI++)
            {
                if (cbtHitData[intI].success)
                {
                    if (intDefenderEndPosture == POSTURE_DOWN)
                    {
                        if (canChangeTargetPosture(cbtAttackerData.id, cbtDefenderData[intI].id, intDefenderEndPosture))
                        {
                            if (!isPlayer(cbtDefenderData[intI].id))
                            {
                                String strXPType = xp.getWeaponXpType(cbtWeaponData.weaponType);
                                xp.updateCombatXpList(cbtDefenderData[intI].id, cbtAttackerData.id, strXPType, 100);
                            }
                            int intNewPosture = getLowerPosture(cbtDefenderData[intI].posture);
                            cbtDefenderResults[intI].endPosture = intNewPosture;
                            if (cbtDefenderData[intI].posture != intNewPosture)
                            {
                                string_id strSpam = new string_id("cbt_spam", "force_posture_change_" + intNewPosture);
                                sendSystemMessage(cbtDefenderData[intI].id, strSpam);
                            }
                        }
                    }
                    else if (intDefenderEndPosture == POSTURE_UP)
                    {
                        if (canChangeTargetPosture(cbtAttackerData.id, cbtDefenderData[intI].id, intDefenderEndPosture))
                        {
                            if (!isPlayer(cbtDefenderData[intI].id))
                            {
                                String strXPType = xp.getWeaponXpType(cbtWeaponData.weaponType);
                                xp.updateCombatXpList(cbtDefenderData[intI].id, cbtAttackerData.id, strXPType, 100);
                            }
                            int intNewPosture = getHigherPosture(cbtDefenderData[intI].posture);
                            cbtDefenderResults[intI].endPosture = intNewPosture;
                            if (cbtDefenderData[intI].posture != intNewPosture)
                            {
                                string_id strSpam = new string_id("cbt_spam", "force_posture_change_" + intNewPosture);
                                sendSystemMessage(cbtDefenderData[intI].id, strSpam);
                            }
                        }
                    }
                    else if (intDefenderEndPosture == POSTURE_KNOCKED_DOWN)
                    {
                        if (canChangeTargetPosture(cbtAttackerData.id, cbtDefenderData[intI].id, intDefenderEndPosture))
                        {
                            if (!isPlayer(cbtDefenderData[intI].id))
                            {
                                String strXPType = xp.getWeaponXpType(cbtWeaponData.weaponType);
                                xp.updateCombatXpList(cbtDefenderData[intI].id, cbtAttackerData.id, strXPType, 150);
                            }
                            cbtDefenderResults[intI].endPosture = intDefenderEndPosture;
                            string_id strSpam = new string_id("cbt_spam", "posture_knocked_down");
                            sendSystemMessage(cbtDefenderData[intI].id, strSpam);
                        }
                    }
                    else 
                    {
                        cbtDefenderResults[intI].endPosture = cbtDefenderData[intI].posture;
                    }
                    if (intState > 0)
                    {
                        cbtDefenderResults[intI].endPosture = POSTURE_INCAPACITATED;
                    }
                }
                else 
                {
                    cbtDefenderResults[intI].endPosture = cbtDefenderData[intI].posture;
                }
                cbtDefenderResults[intI].id = cbtDefenderData[intI].id;
            }
        }
        else 
        {
            for (int intI = 0; intI < cbtHitData.length; intI++)
            {
                if (intState > 0)
                {
                    cbtDefenderResults[intI].endPosture = POSTURE_INCAPACITATED;
                }
                else 
                {
                    cbtDefenderResults[intI].endPosture = cbtDefenderData[intI].posture;
                }
                cbtDefenderResults[intI].id = cbtDefenderData[intI].id;
            }
        }
        if (intAttackerEndPosture != POSTURE_NONE)
        {
            if (intAttackerEndPosture == POSTURE_DOWN)
            {
                int intNewPosture = getLowerPosture(cbtAttackerData.posture);
                cbtAttackerResults.endPosture = intNewPosture;
            }
            else if (intAttackerEndPosture == POSTURE_UP)
            {
                int intNewPosture = getHigherPosture(cbtAttackerData.posture);
                cbtAttackerResults.endPosture = intNewPosture;
            }
            else 
            {
                cbtAttackerResults.endPosture = intAttackerEndPosture;
            }
            cbtAttackerResults.id = cbtAttackerData.id;
        }
        else 
        {
            cbtAttackerResults.endPosture = cbtAttackerData.posture;
            cbtAttackerResults.id = cbtAttackerData.id;
        }
        cbtAttackerResults.weapon = cbtWeaponData.id;
        return;
    }
    public void doFumble(attacker_data cbtAttackerData, weapon_data cbtWeaponData, int intAttackerPosture, String strAnimationAction) throws InterruptedException
    {
        string_id strSpam = new string_id("cbt_spam", "fumble");
        int intFumbleDamage = rand(cbtWeaponData.minDamage, cbtWeaponData.maxDamage);
        return;
    }
    public void doCounterAttack(obj_id objAttacker, obj_id objDefender) throws InterruptedException
    {
        if (isPlayer(objAttacker))
        {
            setObjVar(objAttacker, "combat.boolCounterAttack", true);
            queueCommand(objDefender, (1957054281), objAttacker, "", COMMAND_PRIORITY_FRONT);
        }
        else 
        {
            setObjVar(objAttacker, "combat.doCounterAttack", 1);
        }
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
    public boolean runDizzyCheck(obj_id objPlayer) throws InterruptedException
    {
        int intState = 0;
        if (intState > 0)
        {
            int intRoll = rand(1, 100);
            if (intRoll < DIZZY_CHECK_VALUE)
            {
                if (!isPlayer(objPlayer))
                {
                    messageTo(objPlayer, "dizzyCheckFailed", null, 1, false);
                    return false;
                }
                else 
                {
                    string_id strSpam = new string_id("cbt_spam", "dizzy_fall_down_single");
                    sendSystemMessage(objPlayer, strSpam);
                    setPosture(objPlayer, POSTURE_KNOCKED_DOWN);
                    return true;
                }
            }
        }
        return false;
    }
    public int getLowerPosture(int intPosture) throws InterruptedException
    {
        if (intPosture == POSTURE_CROUCHED)
        {
            return POSTURE_PRONE;
        }
        if (intPosture == POSTURE_UPRIGHT)
        {
            return POSTURE_CROUCHED;
        }
        if (intPosture == POSTURE_PRONE)
        {
            return POSTURE_PRONE;
        }
        return intPosture;
    }
    public int getHigherPosture(int intPosture) throws InterruptedException
    {
        if (intPosture == POSTURE_CROUCHED)
        {
            return POSTURE_UPRIGHT;
        }
        if (intPosture == POSTURE_UPRIGHT)
        {
            return POSTURE_UPRIGHT;
        }
        if (intPosture == POSTURE_PRONE)
        {
            return POSTURE_CROUCHED;
        }
        return intPosture;
    }
    public defender_results[] createDefenderResultsArray(int intSize) throws InterruptedException
    {
        int intI = 0;
        defender_results[] cbtDefenderResults = new defender_results[intSize];
        while (intI < intSize)
        {
            cbtDefenderResults[intI] = new defender_results();
            intI = intI + 1;
        }
        return cbtDefenderResults;
    }
    public hit_result[] createHitResultsArray(int intSize) throws InterruptedException
    {
        int intI = 0;
        hit_result[] cbtHitData = new hit_result[intSize];
        while (intI < intSize)
        {
            cbtHitData[intI] = new hit_result();
            intI = intI + 1;
        }
        return cbtHitData;
    }
    public attacker_results[] createAttackerResultsArray(int intSize) throws InterruptedException
    {
        int intI = 0;
        attacker_results[] cbtAttackerResults = new attacker_results[intSize];
        while (intI < intSize)
        {
            cbtAttackerResults[intI] = new attacker_results();
            intI = intI + 1;
        }
        return cbtAttackerResults;
    }
    public void doMultiDefenderCombatResults(String[] strPlaybackNames, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults, dictionary dctCombatInfo) throws InterruptedException
    {
        int intLeftFoot = dctCombatInfo.getInt("intLeftFoot");
        int intRightFoot = dctCombatInfo.getInt("intRightFoot");
        int intWeapon = dctCombatInfo.getInt("intWeapon");
        int intRightHand = dctCombatInfo.getInt("intRightHand");
        int intLeftHand = dctCombatInfo.getInt("intLeftHand");
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
        int intI = 1;
        if (cbtDefenderResults.length > 1)
        {
            attacker_results cbtAnimationResults = new attacker_results();
            defender_results[] cbtDefenderResult = new defender_results[1];
            cbtDefenderResult[0] = cbtDefenderResults[0];
            doCombatResults(strPlaybackNames[0], cbtAttackerResults, cbtDefenderResult);
            while (intI < cbtDefenderResults.length)
            {
                cbtAnimationResults.endPosture = cbtDefenderResults[intI].endPosture;
                cbtAnimationResults.id = cbtDefenderResults[intI].id;
                if (cbtDefenderResults[intI].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults("get_hit" + strPlaybackNames[intI], cbtAnimationResults, null);
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
            doCombatResults(strPlaybackNames[0], cbtAttackerResults, cbtDefenderResults);
        }
    }
    public void doWarcryCombatResults(String strAnimationAction, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        int intI = 1;
        if (cbtDefenderResults.length > 1)
        {
            attacker_results cbtAnimationResults = new attacker_results();
            defender_results[] cbtDefenderResult = new defender_results[1];
            cbtDefenderResult[0] = cbtDefenderResults[0];
            doCombatResults(strAnimationAction, cbtAttackerResults, cbtDefenderResult);
            while (intI < cbtDefenderResults.length)
            {
                cbtAnimationResults.endPosture = cbtDefenderResults[intI].endPosture;
                cbtAnimationResults.id = cbtDefenderResults[intI].id;
                if (cbtDefenderResults[intI].result == COMBAT_RESULT_HIT)
                {
                    doCombatResults("get_hit_light", cbtAnimationResults, null);
                }
                else if (cbtDefenderResults[intI].result == COMBAT_RESULT_MISS)
                {
                }
                else 
                {
                }
                intI = intI + 1;
            }
        }
        else 
        {
            doCombatResults(strAnimationAction, cbtAttackerResults, cbtDefenderResults);
        }
    }
    public String getActionName(String strActionName, int intAttackNumber, hit_result[] cbtHitData, weapon_data cbtWeaponData) throws InterruptedException
    {
        int intHitLocation = cbtHitData[0].hitLocation;
        String strNewAction = strActionName;
        if (intHitLocation == HIT_LOCATION_HEAD)
        {
            strNewAction = strNewAction + "_high";
            int intRoll = rand(1, 3);
            if (intRoll == 1)
            {
                strNewAction = strNewAction + "_left";
            }
            else if (intRoll == 2)
            {
                strNewAction = strNewAction + "_center";
            }
            else if (intRoll == 3)
            {
                strNewAction = strNewAction + "_right";
            }
        }
        else if (intHitLocation == HIT_LOCATION_BODY)
        {
            strNewAction = strNewAction + "_mid_center";
        }
        else if (intHitLocation == HIT_LOCATION_R_ARM)
        {
            strNewAction = strNewAction + "_mid_left";
        }
        else if (intHitLocation == HIT_LOCATION_L_ARM)
        {
            strNewAction = strNewAction + "_mid_right";
        }
        else if (intHitLocation == HIT_LOCATION_R_LEG)
        {
            strNewAction = strNewAction + "_mid_left";
        }
        else if (intHitLocation == HIT_LOCATION_L_LEG)
        {
            strNewAction = strNewAction + "_mid_right";
        }
        int intMidDamage = (cbtWeaponData.minDamage + cbtWeaponData.maxDamage) / 2;
        if (cbtHitData[0].damage > intMidDamage)
        {
            strNewAction = strNewAction + "_medium";
        }
        else 
        {
            strNewAction = strNewAction + "_light";
        }
        strNewAction = strNewAction + "_" + intAttackNumber;
        return strNewAction;
    }
    public String[] makePlaybackNames(String strAnimationName, hit_result[] cbtHitData, weapon_data cbtWeaponData) throws InterruptedException
    {
        String[] strPlaybackNames = new String[cbtHitData.length];
        int intDamage;
        int intMidpoint = (cbtWeaponData.minDamage + cbtWeaponData.maxDamage) / 2;
        intDamage = cbtHitData[0].damage;
        if (intDamage > intMidpoint)
        {
            strPlaybackNames[0] = strAnimationName + "_medium";
        }
        else 
        {
            strPlaybackNames[0] = strAnimationName + "_light";
        }
        int intI = 1;
        while (intI < cbtHitData.length)
        {
            intDamage = cbtHitData[intI].damage;
            if (intDamage > intMidpoint)
            {
                strPlaybackNames[intI] = strAnimationName + "_medium";
            }
            else 
            {
                strPlaybackNames[intI] = strAnimationName + "_light";
            }
            intI = intI + 1;
        }
        return strPlaybackNames;
    }
    public String[] makePlaybackNames(String strAnimationName, hit_result[] cbtHitData, weapon_data cbtWeaponData, defender_results[] cbtDefenderResults) throws InterruptedException
    {
        String[] strPlaybackNames = new String[cbtHitData.length];
        int intDamage = 0;
        int intMidpoint = (cbtWeaponData.minDamage + cbtWeaponData.maxDamage) / 2;
        for (int i = 0; i < cbtHitData.length; i++)
        {
            if (strAnimationName.startsWith("lower_posture"))
            {
                continue;
            }
            intDamage = cbtHitData[i].damage;
            
            {
                if (intDamage > intMidpoint)
                {
                    strPlaybackNames[i] = strAnimationName + "_medium";
                }
                else 
                {
                    strPlaybackNames[i] = strAnimationName + "_light";
                }
            }
        }
        return strPlaybackNames;
    }
    public void doSuppressionFireResults(String strPlaybackName, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults, dictionary dctCombatInfo) throws InterruptedException
    {
        int intLeftFoot = dctCombatInfo.getInt("intLeftFoot");
        int intRightFoot = dctCombatInfo.getInt("intRightFoot");
        int intWeapon = dctCombatInfo.getInt("intWeapon");
        int intRightHand = dctCombatInfo.getInt("intRightHand");
        int intLeftHand = dctCombatInfo.getInt("intLeftHand");
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
                    cbtAnimationResults.clientEffectId = MARKER_SUPPRESSIONFIRE;
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
        return strType;
    }
    public String getWeaponStringType(int intWeaponType) throws InterruptedException
    {
        switch (intWeaponType)
        {
            case WEAPON_TYPE_RIFLE:
            return "rifle";
            case WEAPON_TYPE_LIGHT_RIFLE:
            return "carbine";
            case WEAPON_TYPE_PISTOL:
            return "pistol";
            case WEAPON_TYPE_HEAVY:
            return "heavyweapon";
            case WEAPON_TYPE_1HAND_MELEE:
            return "onehandmelee";
            case WEAPON_TYPE_2HAND_MELEE:
            return "twohandmelee";
            case WEAPON_TYPE_UNARMED:
            return "unarmed";
            case WEAPON_TYPE_POLEARM:
            return "polearm";
            case WEAPON_TYPE_THROWN:
            return "thrown";
            case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
            return "onehandlightsaber";
            case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
            return "onehandlightsaber";
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return "polearmlightsaber";
        }
        return "";
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
    public obj_id getCorrectCombatTarget(obj_id objPlayer, obj_id objTarget, int intAction) throws InterruptedException
    {
        obj_id objActualTarget = getCorrectCombatTarget(objPlayer, objTarget, intAction, "");
        return objActualTarget;
    }
    public obj_id getCorrectCombatTarget(obj_id objPlayer, obj_id objTarget, int intAction, String strParams) throws InterruptedException
    {
        if (!isIdValid(objTarget))
        {
            objTarget = getTarget(objPlayer);
            if (!isIdValid(objTarget))
            {
                objTarget = getLookAtTarget(objPlayer);
                if (isIdValid(objTarget))
                {
                    queueCommand(objPlayer, (-390609650), objTarget, "", COMMAND_PRIORITY_FRONT);
                    queueCommand(objPlayer, intAction, objTarget, strParams, COMMAND_PRIORITY_FRONT);
                }
                return null;
            }
        }
        if (objTarget == objPlayer)
        {
            string_id strSpam = new string_id("cbt_spam", "shoot_self");
            return null;
        }
        obj_id objContainer = getContainedBy(objTarget);
        if (isPlayer(objContainer))
        {
            return null;
        }
        if (pvpCanAttack(objPlayer, objTarget))
        {
            return objTarget;
        }
        return null;
    }
    public boolean checkWeaponData(obj_id objPlayer, obj_id objTarget, int intAttackCategory, weapon_data cbtWeaponData, String strCommand, String strWeaponType) throws InterruptedException
    {
        float fltDistance = getDistance(objPlayer, objTarget);
        if (cbtWeaponData.maxRange < 10.0f)
        {
            cbtWeaponData.maxRange = 10;
        }
        if (fltDistance > cbtWeaponData.maxRange)
        {
            string_id strSpam = new string_id("cbt_spam", "out_of_range");
            strSpam = new string_id("cbt_spam", "out_of_range_single");
            sendSystemMessage(objPlayer, strSpam);
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
                        sendSystemMessage(objPlayer, SID_CANNOT_USE_FORCE);
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
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_rocket_laucher.iff"))
        {
            return "rocket_launcher";
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_lightning_beam.iff"))
        {
            return "lightning_beam";
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_particle_beam.iff"))
        {
            return "particle_beam";
        }
        if (strTemplate.equals("object/weapon/ranged/heavy/heavy_acid_beam.iff"))
        {
            return "acid_beam";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_flame_thrower.iff"))
        {
            return "flame_thrower";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_beam.iff"))
        {
            return "rifle_beam";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_acid_beam.iff"))
        {
            return "rifle_acid";
        }
        if (strTemplate.equals("object/weapon/ranged/rifle/rifle_lightning.iff"))
        {
            return "rifle_lightning";
        }
        return "rocket_launcher";
    }
    public void doBasicGrenadeCombatResults(attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults, hit_result[] cbtHitData, obj_id objTarget, weapon_data cbtWeaponData) throws InterruptedException
    {
        String strDefense = "get_hit_grenade";
        String strAttack = "throw_grenade";
        String strDistance = "_near";
        int intI = 1;
        float fltDistance = getDistance(cbtAttackerResults.id, objTarget);
        if (fltDistance < CLOSE_THROWING_DISTANCE)
        {
            strDistance = "_near";
        }
        else if (fltDistance < MEDIUM_THROWING_DISTANCE)
        {
            strDistance = "_medium";
        }
        else if (fltDistance < FAR_THROWING_DISTANCE)
        {
            strDistance = "_far";
        }
        String strGrenadeType = getGrenadeType(cbtWeaponData.id);
        strAttack = strAttack + strDistance + "_" + strGrenadeType;
        strDefense = strDefense + strDistance;
        String[] strPlaybackNames = makePlaybackNames(strDefense, cbtHitData, cbtWeaponData);
        defender_results[] cbtDefenderResult = new defender_results[1];
        cbtDefenderResult[0] = new defender_results();
        attacker_results cbtAttackerResult = new attacker_results();
        cbtDefenderResult[0].id = objTarget;
        cbtDefenderResult[0].endPosture = getPosture(objTarget);
        cbtAttackerResults.weapon = null;
        doCombatResults(strAttack, cbtAttackerResults, cbtDefenderResult);
        while (intI < strPlaybackNames.length)
        {
            if (cbtHitData[intI].success)
            {
                cbtAttackerResult.id = cbtDefenderResults[intI].id;
                cbtAttackerResult.endPosture = cbtDefenderResults[intI].endPosture;
                doCombatResults(strPlaybackNames[intI], cbtAttackerResult, null);
            }
            else 
            {
            }
            intI = intI + 1;
        }
        return;
    }
    public void doMineFieldResults(String strParticle, attacker_results cbtAttackerResults, defender_results[] cbtDefenderResults, hit_result[] cbtHitData, obj_id objTarget, weapon_data cbtWeaponData) throws InterruptedException
    {
        String[] strPlaybackNames = makePlaybackNames("get_hit", cbtHitData, cbtWeaponData);
        for (int intI = 0; intI < cbtHitData.length; intI++)
        {
            if (intI == 0)
            {
                location locParticleLocation = getLocation(cbtDefenderResults[0].id);
                playClientEffectLoc(cbtDefenderResults[0].id, strParticle, locParticleLocation, 0);
            }
            attacker_results cbtAnimationResults = new attacker_results();
            cbtAnimationResults.endPosture = cbtDefenderResults[intI].endPosture;
            cbtAnimationResults.id = cbtDefenderResults[intI].id;
            if (cbtDefenderResults[intI].result == COMBAT_RESULT_HIT)
            {
                doCombatResults(strPlaybackNames[intI], cbtAnimationResults, null);
            }
            else if (cbtDefenderResults[intI].result == COMBAT_RESULT_EVADE)
            {
                doCombatResults(strPlaybackNames[intI], cbtAnimationResults, null);
            }
            else if (cbtDefenderResults[intI].result == COMBAT_RESULT_BLOCK)
            {
                doCombatResults(strPlaybackNames[intI], cbtAnimationResults, null);
            }
            else if (cbtDefenderResults[intI].result == COMBAT_RESULT_MISS)
            {
                doCombatResults(strPlaybackNames[intI], cbtAnimationResults, null);
            }
            else 
            {
            }
        }
        return;
    }
    public void yellText(obj_id objSpeaker, String strText) throws InterruptedException
    {
        String strType = "none none " + strText;
        chat._chat(objSpeaker, null, chat.CHAT_YELL, null, strText, null, null);
        return;
    }
    public void clearQueue(obj_id objPlayer) throws InterruptedException
    {
        queueClearCommandsFromGroup(objPlayer, (-506878646));
        queueClearCommandsFromGroup(objPlayer, (-1170591580));
        queueClearCommandsFromGroup(objPlayer, (391413347));
    }
    public obj_id[] truncateTargetArray(obj_id[] objArray, obj_id objTarget, int intLength) throws InterruptedException
    {
        Vector objTruncatedArray = new Vector();
        objTruncatedArray.setSize(0);
        objTruncatedArray = utils.addElement(objTruncatedArray, objTarget);
        for (int i = 0; i <= intLength; i++)
        {
            if (i == objArray.length)
            {
                break;
            }
            if (objArray[i] != objTarget)
            {
                utils.addElement(objTruncatedArray, objArray[i]);
            }
        }
        obj_id[] _objTruncatedArray = new obj_id[0];
        if (objTruncatedArray != null)
        {
            _objTruncatedArray = new obj_id[objTruncatedArray.size()];
            objTruncatedArray.toArray(_objTruncatedArray);
        }
        return _objTruncatedArray;
    }
    public void clearFeignIncapacitation(obj_id objPlayer, int intPosture) throws InterruptedException
    {
        string_id strSpam = new string_id("cbt_spam", "stand");
        setPosture(objPlayer, intPosture);
        if (isPlayer(objPlayer))
        {
            dictionary parms = new dictionary();
            parms.put("player", objPlayer);
            broadcastMessage("handleSawRecapacitation", parms);
        }
    }
    public void setTurretTarget(obj_id objTurret, obj_id objTarget) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objTarget", objTarget);
        messageTo(objTurret, "setTarget", dctParams, 0, true);
        return;
    }
    public void startTurretAttack(obj_id objTurret, obj_id objTarget) throws InterruptedException
    {
        dictionary dctParams = new dictionary();
        dctParams.put("objTarget", objTarget);
        messageTo(objTurret, "startAttack", dctParams, 0, true);
    }
    public void applyWound(hit_result cbtHitData, defender_data cbtDefenderData, attacker_data cbtAttackerData) throws InterruptedException
    {
        return;
    }
    public String getStatName(int intStat) throws InterruptedException
    {
        switch (intStat)
        {
            case HEALTH:
            return "health";
            case CONSTITUTION:
            return "constitution";
            case ACTION:
            return "action";
            case STAMINA:
            return "stamina";
            case MIND:
            return "mind";
            case WILLPOWER:
            return "willpower";
        }
        return "";
    }
    public int getMobHitMod(obj_id objMob) throws InterruptedException
    {
        int intToHitChance = getSkillStatMod(objMob, "toHitChance");
        return intToHitChance;
    }
    public int checkTrapMod(int mod, String modName, obj_id checkId) throws InterruptedException
    {
        if (isIdValid(checkId) && utils.hasScriptVar(checkId, "trapmod.enable"))
        {
            if (utils.hasScriptVar(checkId, "trapmod.enable"))
            {
                if (modName.equals("stun_defense"))
                {
                    if (utils.hasScriptVar(checkId, "trapmod.stun_defense"))
                    {
                        mod -= utils.getIntScriptVar(checkId, "trapmod.stun_defense");
                    }
                }
                else if (modName.equals("dizzy_defense"))
                {
                    if (utils.hasScriptVar(checkId, "trapmod.dizzy_defense"))
                    {
                        mod -= utils.getIntScriptVar(checkId, "trapmod.dizzy_defense");
                    }
                }
                else if (modName.equals("intimidate_defense"))
                {
                    if (utils.hasScriptVar(checkId, "trapmod.intimidate_defense"))
                    {
                        mod -= utils.getIntScriptVar(checkId, "trapmod.intimidate_defense");
                    }
                }
            }
            return mod;
        }
        return 0;
    }
    public int[] getCombatEffectsFromDataTable(dictionary dctCombatInfo) throws InterruptedException
    {
        Vector intEffects = new Vector();
        intEffects.setSize(0);
        for (int intI = 0; intI < 3; intI++)
        {
            int intIndex = intI + 1;
            String strEffect = "intEffect" + intIndex;
            int intEffect = dctCombatInfo.getInt(strEffect);
            if (intEffect != -1)
            {
                intEffects = utils.addElement(intEffects, intEffect);
            }
        }
        for (int intI = 0; intI < intEffects.size(); intI++)
        {
        }
        int[] _intEffects = new int[0];
        if (intEffects != null)
        {
            _intEffects = new int[intEffects.size()];
            for (int _i = 0; _i < intEffects.size(); ++_i)
            {
                _intEffects[_i] = ((Integer)intEffects.get(_i)).intValue();
            }
        }
        return _intEffects;
    }
    public float[] getCombatEffectDurationsFromDataTable(dictionary dctCombatInfo, int[] intEffects) throws InterruptedException
    {
        Vector fltDurations = new Vector();
        fltDurations.setSize(0);
        for (int intI = 0; intI < intEffects.length; intI++)
        {
            String strTest = "fltEffectDuration" + (intI + 1);
            float fltDuration = dctCombatInfo.getFloat(strTest);
            fltDurations = utils.addElement(fltDurations, fltDuration);
        }
        float[] _fltDurations = new float[0];
        if (fltDurations != null)
        {
            _fltDurations = new float[fltDurations.size()];
            for (int _i = 0; _i < fltDurations.size(); ++_i)
            {
                _fltDurations[_i] = ((Float)fltDurations.get(_i)).floatValue();
            }
        }
        return _fltDurations;
    }
    public obj_id[] getCombatDefenders(obj_id objPlayer, obj_id objTarget, dictionary dctCombatInfo) throws InterruptedException
    {
        int intAttackType = dctCombatInfo.getInt("intAttackType");
        if (intAttackType == CONE)
        {
            float fltConeLength = dctCombatInfo.getFloat("fltConeLength");
            float fltConeWidth = dctCombatInfo.getFloat("fltConeWidth");
            obj_id[] objDefenders = pvpGetTargetsInCone(objPlayer, objPlayer, objTarget, fltConeLength, fltConeWidth);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Cone:Init Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            objDefenders = truncateTargetArray(objDefenders, objTarget, MAX_TARGET_ARRAY_SIZE);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Cone:Truncate Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            objDefenders = validateDefenders(objPlayer, objDefenders);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Cone:Validate Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            if (objDefenders == null)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            else if (objDefenders.length < 1)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            return objDefenders;
        }
        else if (intAttackType == AREA)
        {
            float fltConeLength = dctCombatInfo.getFloat("fltConeLength");
            float fltConeWidth = dctCombatInfo.getFloat("fltConeWidth");
            if (fltConeLength <= 0)
            {
                obj_id objWeapon = getCurrentWeapon(objPlayer);
                float fltDistance = getMaxRange(objWeapon);
                fltConeLength = fltDistance;
            }
            obj_id[] objDefenders = pvpGetTargetsInRange(objPlayer, objPlayer, fltConeLength);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Area:Init Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            objDefenders = truncateTargetArray(objDefenders, objTarget, MAX_TARGET_ARRAY_SIZE);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Area:Truncate Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            objDefenders = validateDefenders(objPlayer, objDefenders);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "Area:Validate Defender Array = " + objDefenders.length + "(" + listArrayContents(objDefenders) + ")");
            if (objDefenders == null)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            else if (objDefenders.length < 1)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            return objDefenders;
        }
        else if (intAttackType == TARGET_AREA)
        {
            float fltConeLength = dctCombatInfo.getFloat("fltConeLength");
            obj_id[] objDefenders = pvpGetTargetsInRange(objPlayer, objTarget, fltConeLength);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "TgtArea:Init Defender Array = " + objDefenders.length + "(" + objDefenders.toString() + ")");
            objDefenders = truncateTargetArray(objDefenders, objTarget, MAX_TARGET_ARRAY_SIZE);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "TgtArea:Truncate Defender Array = " + objDefenders.length + "(" + objDefenders.toString() + ")");
            objDefenders = validateDefenders(objPlayer, objDefenders);
            combatLog(objPlayer, objTarget, "getCombatDefenders", "TgtArea:Validate Defender Array = " + objDefenders.length + "(" + objDefenders.toString() + ")");
            if (objDefenders == null)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            else if (objDefenders.length < 1)
            {
                objDefenders = new obj_id[1];
                objDefenders[0] = objTarget;
            }
            return objDefenders;
        }
        else if (intAttackType == DUAL_WIELD)
        {
            obj_id[] objDefenders = new obj_id[2];
            objDefenders[0] = objTarget;
            obj_id objTest = getLookAtTarget(objPlayer);
            if (isIdValid(objTest))
            {
                if (pvpCanAttack(objPlayer, objTest))
                {
                    objDefenders[1] = objTest;
                }
                else 
                {
                    objDefenders[1] = objTarget;
                }
            }
            else 
            {
                objDefenders[1] = objTarget;
            }
            return objDefenders;
        }
        else 
        {
            obj_id[] objDefenders = new obj_id[1];
            objDefenders[0] = objTarget;
            return objDefenders;
        }
    }
    public float getDamageBonus(int intWeaponType) throws InterruptedException
    {
        switch (intWeaponType)
        {
            case WEAPON_TYPE_RIFLE:
            return 1.0f;
            case WEAPON_TYPE_LIGHT_RIFLE:
            return 1.0f;
            case WEAPON_TYPE_PISTOL:
            return 1.00f;
            case WEAPON_TYPE_HEAVY:
            return 1.0f;
            case WEAPON_TYPE_1HAND_MELEE:
            return 1.0f;
            case WEAPON_TYPE_2HAND_MELEE:
            return 1.0f;
            case WEAPON_TYPE_UNARMED:
            return 1.0f;
            case WEAPON_TYPE_POLEARM:
            return 1.0f;
            case WEAPON_TYPE_THROWN:
            return 1.0f;
            case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
            return 1.0f;
            case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
            return 1.0f;
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return 1.0f;
        }
        return 1.0f;
    }
    public boolean isMeleeWeapon(obj_id objWeapon) throws InterruptedException
    {
        int intWeaponType = getWeaponType(objWeapon);
        switch (intWeaponType)
        {
            case WEAPON_TYPE_RIFLE:
            return false;
            case WEAPON_TYPE_LIGHT_RIFLE:
            return false;
            case WEAPON_TYPE_PISTOL:
            return false;
            case WEAPON_TYPE_HEAVY:
            return false;
            case WEAPON_TYPE_1HAND_MELEE:
            return true;
            case WEAPON_TYPE_2HAND_MELEE:
            return true;
            case WEAPON_TYPE_UNARMED:
            return true;
            case WEAPON_TYPE_POLEARM:
            return true;
            case WEAPON_TYPE_THROWN:
            return false;
            case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
            return true;
            case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
            return true;
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return true;
        }
        return false;
    }
    public float getLungeRange(weapon_data cbtWeaponData) throws InterruptedException
    {
        float fltRange = cbtWeaponData.maxRange;
        if (fltRange < 10.0f)
        {
            fltRange = 10;
        }
        fltRange = fltRange * 3;
        if (fltRange > 20)
        {
            fltRange = 20.0f;
        }
        return fltRange;
    }
    public boolean damageWeapon(obj_id owner, obj_id weaponid, int amount) throws InterruptedException
    {
        if (!isPlayer(owner))
        {
            return false;
        }
        int hp = getHitpoints(weaponid);
        int maxhp = getMaxHitpoints(weaponid);
        if (hp > 0)
        {
            hp -= amount;
            setHitpoints(weaponid, hp);
            if ((hp + amount == maxhp))
            {
                metrics.logWeaponStatus(owner, weaponid);
            }
            else if ((hp < ((maxhp / 4) * 3)) && (hp + amount >= ((maxhp / 4) * 3)))
            {
                metrics.logWeaponStatus(owner, weaponid);
            }
            else if ((hp < maxhp / 2) && (hp + amount >= maxhp / 2))
            {
                string_id strSpam = new string_id("combat_effects", "weapon_half");
                sendSystemMessage(owner, strSpam);
                metrics.logWeaponStatus(owner, weaponid);
            }
            else if ((hp < maxhp / 4) && (hp + amount >= maxhp / 4))
            {
                string_id strSpam = new string_id("combat_effects", "weapon_quarter");
                sendSystemMessage(owner, strSpam);
                metrics.logWeaponStatus(owner, weaponid);
            }
            return true;
        }
        return false;
    }
    public boolean canChangeTargetPosture(obj_id objAttacker, obj_id objDefender, int intChangeType) throws InterruptedException
    {
        if (vehicle.isDriveableVehicle(objDefender))
        {
            return false;
        }
        if (ai_lib.aiGetNiche(objDefender) == NICHE_VEHICLE)
        {
            return false;
        }
        if (ai_lib.isTurret(objDefender))
        {
            return false;
        }
        int intPostureModifier = 0;
        intPostureModifier = getLevel(objDefender);
        intPostureModifier = intPostureModifier - 5;
        if (intPostureModifier < 0)
        {
            intPostureModifier = 0;
        }
        if (intChangeType == POSTURE_KNOCKED_DOWN)
        {
            int intPostureChangeMod = getSkillStatisticModifier(objDefender, "knockdown_defense");
            int intRoll = rand(0, 100);
            intPostureChangeMod = intPostureChangeMod + intPostureModifier;
            if (intPostureChangeMod > 90)
            {
                intPostureChangeMod = 90;
            }
            int intKnockDownTime = utils.getIntScriptVar(objDefender, "combat.intKnockdownTime");
            int intCurrentTime = getGameTime();
            int intDifference = intCurrentTime - intKnockDownTime;
            if (intDifference < 30)
            {
                intPostureChangeMod = 500;
            }
            if (intRoll > intPostureChangeMod)
            {
                utils.setScriptVar(objDefender, "combat.intKnockdownTime", intCurrentTime);
                return true;
            }
            string_id strSpam = new string_id("cbt_spam", "knockdown_fail");
            sendSystemMessage(objAttacker, strSpam);
            return false;
        }
        else if (intChangeType == POSTURE_UP)
        {
            int intPostureChangeMod = getSkillStatisticModifier(objDefender, "posture_change_up_defense");
            int intRoll = rand(0, 100);
            intPostureChangeMod = intPostureChangeMod + intPostureModifier;
            if (intPostureChangeMod > 90)
            {
                intPostureChangeMod = 90;
            }
            int intPostureChangeTime = utils.getIntScriptVar(objDefender, "combat.intPostureChangeTime");
            int intCurrentTime = getGameTime();
            int intDifference = intCurrentTime - intPostureChangeTime;
            if (intDifference < 30)
            {
                intPostureChangeMod = 500;
            }
            if (intRoll < intPostureChangeMod)
            {
                utils.setScriptVar(objDefender, "combat.intPostureChangeTime", getGameTime());
                return true;
            }
            string_id strSpam = new string_id("cbt_spam", "posture_change_fail");
            sendSystemMessage(objAttacker, strSpam);
            return false;
        }
        else if (intChangeType == POSTURE_DOWN)
        {
            int intPostureChangeMod = getSkillStatisticModifier(objDefender, "posture_change_down_defense");
            intPostureChangeMod = intPostureChangeMod + intPostureModifier;
            if (intPostureChangeMod > 95)
            {
                intPostureChangeMod = 96;
            }
            int intRoll = rand(0, 100);
            int intPostureChangeTime = utils.getIntScriptVar(objDefender, "combat.intPostureChangeTime");
            int intCurrentTime = getGameTime();
            int intDifference = intCurrentTime - intPostureChangeTime;
            if (intDifference < 30)
            {
                intPostureChangeMod = 500;
            }
            if (intRoll > intPostureChangeMod)
            {
                utils.setScriptVar(objDefender, "combat.intPostureChangeTime", getGameTime());
                return true;
            }
            string_id strSpam = new string_id("cbt_spam", "posture_change_fail");
            sendSystemMessage(objAttacker, strSpam);
            return false;
        }
        return false;
    }
    public boolean applyCombatEffectDelay(obj_id objAttacker, obj_id objTarget, float fltDelay, boolean showAttackerSuccessMessage, boolean showDefenderSuccessMessage, String appliedDelay) throws InterruptedException
    {
        int intLastDelay = utils.getIntScriptVar(objTarget, "combat.intLastDelay");
        int intCurrentTime = getGameTime();
        int intDifference = intCurrentTime - intLastDelay;
        if (intDifference < 30)
        {
            string_id strSpam = new string_id("combat_effects", "combat_delay_resist");
            if (isPlayer(objAttacker))
            {
                sendSystemMessage(objAttacker, strSpam);
            }
            return false;
        }
        else 
        {
            utils.setScriptVar(objTarget, "combat.intLastDelay", intCurrentTime);
            utils.setScriptVar(objTarget, appliedDelay, fltDelay);
            string_id strSpam = new string_id("combat_effects", "delay_applied_self");
            int intDelay = (int)fltDelay;
            prose_package proseTest = prose.getPackage(strSpam, intDelay);
            if (isPlayer(objTarget))
            {
                if (showDefenderSuccessMessage)
                {
                    sendSystemMessageProse(objTarget, proseTest);
                }
            }
            strSpam = new string_id("combat_effects", "delay_applied_other");
            proseTest = prose.getPackage(strSpam, objTarget, intDelay);
            if (isPlayer(objAttacker))
            {
                if (showAttackerSuccessMessage)
                {
                    sendSystemMessageProse(objAttacker, proseTest);
                }
            }
        }
        return true;
    }
    public void doCombatAnimationResults(String strPlaybackName, attacker_results cbtAttackerResults, hit_result[] cbtHitData, weapon_data cbtWeaponData, defender_results[] cbtDefenderResults, dictionary dctCombatInfo) throws InterruptedException
    {
        int intUseAbsoluteAnimation = dctCombatInfo.getInt("intUseAbsoluteAnimation");
        if (intUseAbsoluteAnimation > 0)
        {
            doSuppressionFireResults(strPlaybackName, cbtAttackerResults, cbtDefenderResults, dctCombatInfo);
        }
        else 
        {
            String[] strPlaybackNames = makePlaybackNames(strPlaybackName, cbtHitData, cbtWeaponData);
            doMultiDefenderCombatResults(strPlaybackNames, cbtAttackerResults, cbtDefenderResults, dctCombatInfo);
        }
        return;
    }
    public weapon_data getOverloadedWeaponData(weapon_data cbtWeaponData, dictionary dctCombatInfo) throws InterruptedException
    {
        int intOverloadWeapon = dctCombatInfo.getInt("intOverloadWeapon");
        if (intOverloadWeapon > 0)
        {
            cbtWeaponData.minDamage = dctCombatInfo.getInt("intMaxDamage");
            cbtWeaponData.minDamage = dctCombatInfo.getInt("intMaxDamage");
            cbtWeaponData.minRange = dctCombatInfo.getFloat("fltMinRange");
            cbtWeaponData.maxRange = dctCombatInfo.getFloat("fltMaxRange");
            cbtWeaponData.accuracy = dctCombatInfo.getInt("intAccuracy");
            cbtWeaponData.attackSpeed = dctCombatInfo.getFloat("fltAttackSpeed");
            cbtWeaponData.attackCost = dctCombatInfo.getInt("intAttackCost");
            cbtWeaponData.isDisabled = false;
            return cbtWeaponData;
        }
        else 
        {
            return cbtWeaponData;
        }
    }
    public void doForceChokeSpam(obj_id objTarget) throws InterruptedException
    {
        string_id strText = new string_id("combat_effects", "choke");
        string_id strSpam = new string_id("combat_effects", "choke_single");
        sendSystemMessage(objTarget, strSpam);
        color colTest = colors.RED;
        showFlyText(objTarget, strText, 1, colTest);
        return;
    }
    public void doForceWeaken(obj_id objTarget, obj_id objAttacker, float fltMultiplier) throws InterruptedException
    {
        for (int intI = 0; intI <= WILLPOWER; intI++)
        {
            int intBaseValue = rand(-200, -100);
            int intValue = (int)(intBaseValue * fltMultiplier);
            float fltBaseDuration = rand(15, 30);
            float fltDuration = fltBaseDuration * fltMultiplier;
            addAttribModifier(objTarget, intI, intValue, fltDuration, rand(15f, 30f), rand(15f, 30f));
        }
        return;
    }
    public weapon_data calculateDamageMitigation(obj_id objDefender, String strDefenderCommand, weapon_data cbtWeaponData, int intMinDamage, int intMaxDamage) throws InterruptedException
    {
        float fltMinDamage = (float)intMinDamage;
        float fltMaxDamage = (float)intMaxDamage;
        float fltLimit = 1.0f;
        if (hasCommand(objDefender, strDefenderCommand + "_1"))
        {
            fltLimit = 0.80f;
        }
        if (hasCommand(objDefender, strDefenderCommand + "_2"))
        {
            fltLimit = 0.60f;
        }
        if (hasCommand(objDefender, strDefenderCommand + "_3"))
        {
            fltLimit = 0.40f;
        }
        float fltDifference = fltMaxDamage - fltMinDamage;
        float fltDamage = fltDifference * fltLimit;
        fltMaxDamage = fltMinDamage + fltDamage;
        cbtWeaponData.maxDamage = (int)fltMaxDamage;
        return cbtWeaponData;
    }
    public float getHitBonus(int intWeaponCategory, obj_id objDefender) throws InterruptedException
    {
        int intWeaponType = getWeaponType(getCurrentWeapon(objDefender));
        if (intWeaponCategory == combat.RANGED_WEAPON)
        {
            switch (intWeaponType)
            {
                case WEAPON_TYPE_RIFLE:
                return 0f;
                case WEAPON_TYPE_LIGHT_RIFLE:
                return 0f;
                case WEAPON_TYPE_PISTOL:
                return 0f;
                case WEAPON_TYPE_HEAVY:
                return 0f;
                case WEAPON_TYPE_1HAND_MELEE:
                return 0f;
                case WEAPON_TYPE_2HAND_MELEE:
                return 0f;
                case WEAPON_TYPE_UNARMED:
                return 0f;
                case WEAPON_TYPE_POLEARM:
                return 0f;
                case WEAPON_TYPE_THROWN:
                return 0f;
                case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
                return 0f;
                case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
                return 0f;
                case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
                return 0f;
            }
        }
        else 
        {
            switch (intWeaponType)
            {
                case WEAPON_TYPE_RIFLE:
                return 100f;
                case WEAPON_TYPE_LIGHT_RIFLE:
                return 75f;
                case WEAPON_TYPE_PISTOL:
                return 20f;
                case WEAPON_TYPE_HEAVY:
                return 100f;
                case WEAPON_TYPE_1HAND_MELEE:
                return 0f;
                case WEAPON_TYPE_2HAND_MELEE:
                return 0f;
                case WEAPON_TYPE_UNARMED:
                return 0f;
                case WEAPON_TYPE_POLEARM:
                return 0f;
                case WEAPON_TYPE_THROWN:
                return 0f;
                case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
                return 0f;
                case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
                return 0f;
                case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
                return 0f;
            }
        }
        return 0f;
    }
    public void combatLog(obj_id attacker, obj_id defender, String logName, String logMsg) throws InterruptedException
    {
        
    }
    public String listArrayContents(obj_id[] array) throws InterruptedException
    {
        String output = "";
        for (int i = 0; i < array.length; i++)
        {
            output += array[i];
            if (i < array.length - 1)
            {
                output += ", ";
            }
        }
        return output;
    }
}
