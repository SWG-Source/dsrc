package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.prose;
import script.library.vehicle;
import script.library.utils;
import script.library.combat;
import script.library.ai_lib;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Vector;
import script.library.colors;
import script.library.beast_lib;
import script.library.skill;
import script.library.trial;

public class dot extends script.base_script
{
    public dot()
    {
    }
    public static final String DOT_BLEEDING = "bleeding";
    public static final String DOT_POISON = "poison";
    public static final String DOT_DISEASE = "disease";
    public static final String DOT_FIRE = "fire";
    public static final String DOT_ACID = "acid";
    public static final String DOT_ENERGY = "energy";
    public static final String DOT_COLD = "cold";
    public static final String DOT_ELECTRICITY = "electricity";
    public static final String DOT_KINETIC = "kinetic";
    public static final String SCRIPT_PLAYER_DOT = "player.player_dot";
    public static final String VAR_DOT = "dot";
    public static final String VAR_DOT_ROOT = "dot.";
    public static final String VAR_TYPE = ".type";
    public static final String VAR_ATTRIBUTE = ".attribute";
    public static final String VAR_POTENCY = ".potency";
    public static final String VAR_STRENGTH = ".strength";
    public static final String VAR_DURATION = ".duration";
    public static final String VAR_TIME_START = ".time_start";
    public static final String VAR_LAST_PULSE = ".last_pulse";
    public static final String VAR_HANDLER = ".handler";
    public static final String VAR_ATTACKER = ".attacker";
    public static final String VAR_USES = ".uses";
    public static final int DOT_MAXIMUM_COUNT = 3;
    public static final int BASE_RESISTANCE = 100;
    public static final int BLEEDING_PULSE = 1;
    public static final int POISON_PULSE = 1;
    public static final int DISEASE_PULSE = 1;
    public static final int FIRE_PULSE = 1;
    public static final int ACID_PULSE = 1;
    public static final int ENERGY_PULSE = 1;
    public static final int DOT_GRACE_PERIOD = 60;
    public static final int MOD_DIVISOR = 3;
    public static final int DOT_ARMOR_MITIGATION_PERCENT = 50;
    public static final float VAR_EFFECT_DISPLAY_RADIUS = 45.0f;
    public static final String SCRIPT_VAR_DOT_GRACE = "dot.dot_grace";
    public static final String[] HIT_LOCATIONS = 
    {
        "body",
        "head",
        "right_arm",
        "left_arm",
        "right_leg",
        "left_leg"
    };
    public static final int[] HIT_LOCATION_ATTRIBUTE = 
    {
        HEALTH,
        MIND,
        HEALTH,
        HEALTH,
        ACTION,
        ACTION
    };
    public static final string_id SID_BLEEDING = new string_id("dot_message", "start_bleeding");
    public static final string_id SID_POISONED = new string_id("dot_message", "start_poisoned");
    public static final string_id SID_DISEASED = new string_id("dot_message", "start_diseased");
    public static final string_id SID_FIRE = new string_id("dot_message", "start_fire");
    public static final string_id SID_ACID = new string_id("dot_message", "start_acid");
    public static final string_id SID_ENERGY = new string_id("dot_message", "start_energy");
    public static final string_id SID_COLD = new string_id("dot_message", "start_cold");
    public static final string_id SID_ELECTRICITY = new string_id("dot_message", "start_electricity");
    public static final string_id SID_KINETIC = new string_id("dot_message", "start_kinetic");
    public static final string_id SID_BLEEDING_INCREASE = new string_id("dot_message", "increase_bleeding");
    public static final string_id SID_POISONED_INCREASE = new string_id("dot_message", "increase_poisoned");
    public static final string_id SID_DISEASED_INCREASE = new string_id("dot_message", "increase_diseased");
    public static final string_id SID_FIRE_INCREASE = new string_id("dot_message", "increase_fire");
    public static final string_id SID_ACID_INCREASE = new string_id("dot_message", "increase_acid");
    public static final string_id SID_ENERGY_INCREASE = new string_id("dot_message", "increase_energy");
    public static final string_id SID_COLD_INCREASE = new string_id("dot_message", "increase_cold");
    public static final string_id SID_ELECTRICITY_INCREASE = new string_id("dot_message", "increase_electricity");
    public static final string_id SID_KINETIC_INCREASE = new string_id("dot_message", "increase_kinetic");
    public static final string_id SID_BLEEDING_DECREASE = new string_id("dot_message", "decrease_bleeding");
    public static final string_id SID_POISONED_DECREASE = new string_id("dot_message", "decrease_poisoned");
    public static final string_id SID_DISEASED_DECREASE = new string_id("dot_message", "decrease_diseased");
    public static final string_id SID_FIRE_DECREASE = new string_id("dot_message", "decrease_fire");
    public static final string_id SID_ACID_DECREASE = new string_id("dot_message", "decrease_acid");
    public static final string_id SID_ENERGY_DECREASE = new string_id("dot_message", "decrease_energy");
    public static final string_id SID_COLD_DECREASE = new string_id("dot_message", "decrease_cold");
    public static final string_id SID_ELECTRICITY_DECREASE = new string_id("dot_message", "decrease_electricity");
    public static final string_id SID_KINETIC_DECREASE = new string_id("dot_message", "decrease_kinetic");
    public static final string_id SID_BLEEDING_STOP = new string_id("dot_message", "stop_bleeding");
    public static final string_id SID_POISONED_STOP = new string_id("dot_message", "stop_poisoned");
    public static final string_id SID_DISEASED_STOP = new string_id("dot_message", "stop_diseased");
    public static final string_id SID_FIRE_STOP = new string_id("dot_message", "stop_fire");
    public static final string_id SID_ACID_STOP = new string_id("dot_message", "stop_acid");
    public static final string_id SID_ENERGY_STOP = new string_id("dot_message", "stop_energy");
    public static final string_id SID_COLD_STOP = new string_id("dot_message", "stop_cold");
    public static final string_id SID_ELECTRICITY_STOP = new string_id("dot_message", "stop_electricity");
    public static final string_id SID_KINETIC_STOP = new string_id("dot_message", "stop_kinetic");
    public static final string_id SID_DOT_RESISTED = new string_id("dot_message", "dot_resisted");
    public static final string_id SID_DOT_RESISTED_SPAM = new string_id("dot_message", "spam_dot_resisted");
    public static final string_id SID_BLEED_DMG = new string_id("dot_message", "bleed_dmg");
    public static final string_id SID_BLEED_DMG_ATKR = new string_id("dot_message", "bleed_dmg_atkr");
    public static final string_id SID_POISON_DMG = new string_id("dot_message", "poison_dmg");
    public static final string_id SID_POISON_DMG_ATKR = new string_id("dot_message", "poison_dmg_atkr");
    public static final string_id SID_FIRE_DMG = new string_id("dot_message", "fire_dmg");
    public static final string_id SID_FIRE_DMG_ATKR = new string_id("dot_message", "fire_dmg_atkr");
    public static final string_id SID_DISEASE_DMG = new string_id("dot_message", "disease_dmg");
    public static final string_id SID_DISEASE_DMG_ATKR = new string_id("dot_message", "disease_dmg_atkr");
    public static final string_id SID_ACID_DMG = new string_id("dot_message", "acid_dmg");
    public static final string_id SID_ACID_DMG_ATKR = new string_id("dot_message", "acid_dmg_atkr");
    public static final string_id SID_ENERGY_DMG = new string_id("dot_message", "energy_dmg");
    public static final string_id SID_ENERGY_DMG_ATKR = new string_id("dot_message", "energy_dmg_atkr");
    public static final string_id SID_COLD_DMG = new string_id("dot_message", "cold_dmg");
    public static final string_id SID_COLD_DMG_ATKR = new string_id("dot_message", "cold_dmg_atkr");
    public static final string_id SID_ELECTRICITY_DMG = new string_id("dot_message", "electricity_dmg");
    public static final string_id SID_ELECTRICITY_DMG_ATKR = new string_id("dot_message", "electricity_dmg_atkr");
    public static final string_id SID_KINETIC_DMG = new string_id("dot_message", "kinetic_dmg");
    public static final string_id SID_KINETIC_DMG_ATKR = new string_id("dot_message", "kinetic_dmg_atkr");
    public static boolean applyDotEffect(obj_id target, obj_id attacker, String type, String dot_id, int attribute, int potency, int strength, int duration, boolean verbose, String handler) throws InterruptedException
    {
        if (!isIdValid(attacker))
        {
            attacker = getSelf();
            if (!isIdValid(attacker))
            {
                return false;
            }
        }
        else 
        {
            if (!isMob(target))
            {
                return false;
            }
        }
        if (type == null)
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (attribute < 0 || attribute > 8)
        {
            return false;
        }
        if (potency != -1 && potency < 1)
        {
            return false;
        }
        if (strength < 1)
        {
            return false;
        }
        if (duration < 1)
        {
            return false;
        }
        if (!canApplyDotType(target, type))
        {
            return false;
        }
        if (isIdValid(attacker) && isMob(attacker))
        {
            addHate(target, attacker, 0.0f);
            addHate(attacker, target, 0.0f);
        }
        if (potency != -1)
        {
            if (attemptDotResist(target, type, potency, true))
            {
                return false;
            }
        }
        boolean stacking = false;
        boolean new_dot_type = false;
        String[] dot_types = getAllDotsType(target, type);
        if (dot_types == null)
        {
            new_dot_type = true;
        }
        if (hasDotId(target, dot_id))
        {
            int old_strength = getDotStrength(target, dot_id);
            if (old_strength >= strength)
            {
                return false;
            }
            else 
            {
                stacking = true;
            }
        }
        float expertiseDotIncrease = getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_dot_increase");
        if (expertiseDotIncrease > 0.0f)
        {
            strength += (int)((float)strength * (expertiseDotIncrease / 100.0f));
        }
        int dissipation_mod;
        if (type.equals(DOT_BLEEDING))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_bleeding");
        }
        else if (type.equals(DOT_POISON))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_poison");
        }
        else if (type.equals(DOT_DISEASE))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_disease");
        }
        else if (type.equals(DOT_FIRE))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_fire");
        }
        else if (type.equals(DOT_ACID))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_acid");
        }
        else if (type.equals(DOT_ENERGY))
        {
            dissipation_mod = getEnhancedSkillStatisticModifier(target, "dissipation_energy");
        }
        else 
        {
            return false;
        }
        if (dissipation_mod > 0)
        {
            if (dissipation_mod > 90)
            {
                dissipation_mod = 90;
            }
            duration = (int)(duration * (1.0f - (dissipation_mod / 100.0f)));
            if (duration < 1)
            {
                duration = 1;
            }
        }
        capDots(attacker, target, strength);
        utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_TYPE, type);
        utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_ATTRIBUTE, attribute);
        utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_STRENGTH, strength);
        utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_DURATION, duration);
        utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_TIME_START, getGameTime());
        if (handler != null)
        {
            utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_HANDLER, handler);
        }
        if (isIdValid(attacker))
        {
            utils.setScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_ATTACKER, attacker);
        }
        int pulse;
        string_id old_effect_string;
        string_id new_effect_string;
        if (type.equals(DOT_BLEEDING))
        {
            new_effect_string = SID_BLEEDING;
            old_effect_string = SID_BLEEDING_INCREASE;
            pulse = BLEEDING_PULSE;
            if (!buff.hasBuff(target, "bleeding"))
            {
                buff.applyBuff(target, attacker, "bleeding");
                setState(target, STATE_BLEEDING, true);
                playClientEffectObj(target, "appearance/pt_state_bleeding.prt", target, "spine3", null, "state_bleeding");
                playClientEffectObj(target, "sound/sta_bleeding_on.snd", target, "");
            }
        }
        else if (type.equals(DOT_POISON))
        {
            old_effect_string = SID_POISONED_INCREASE;
            new_effect_string = SID_POISONED;
            pulse = POISON_PULSE;
            if (!buff.hasBuff(target, "poisoned"))
            {
                buff.applyBuff(target, attacker, "poisoned");
                setState(target, STATE_POISONED, true);
                playClientEffectObj(target, "appearance/pt_state_poisoned.prt", target, "", null, "state_poisoned");
                playClientEffectObj(target, "sound/sta_poisoned_on.snd", target, "");
            }
        }
        else if (type.equals(DOT_DISEASE))
        {
            old_effect_string = SID_DISEASED_INCREASE;
            new_effect_string = SID_DISEASED;
            pulse = DISEASE_PULSE;
            if (!buff.hasBuff(target, "diseased"))
            {
                buff.applyBuff(target, attacker, "diseased");
                setState(target, STATE_DISEASED, true);
                playClientEffectObj(target, "appearance/pt_state_diseased.prt", target, "", null, "state_diseased");
                playClientEffectObj(target, "sound/sta_diseased_on.snd", target, "");
            }
        }
        else if (type.equals(DOT_FIRE))
        {
            if (getState(target, STATE_SWIMMING) != 1)
            {
                pulse = FIRE_PULSE;
                old_effect_string = SID_FIRE_INCREASE;
                new_effect_string = SID_FIRE;
                if (!buff.hasBuff(target, "onfire"))
                {
                    new_dot_type = true;
                    buff.applyBuff(target, attacker, "onfire");
                    setState(target, STATE_ON_FIRE, true);
                    playClientEffectObj(target, "appearance/pt_state_onfire.prt", target, "", null, "state_onfire");
                    playClientEffectObj(target, "sound/sta_onfire_on.snd", target, "");
                }
            }
            else 
            {
                return false;
            }
        }
        else if (type.equals(DOT_ACID))
        {
            old_effect_string = SID_ACID_INCREASE;
            new_effect_string = SID_ACID;
            pulse = ACID_PULSE;
            if (!buff.hasBuff(target, "acid"))
            {
                buff.applyBuff(target, attacker, "acid");
                playClientEffectObj(target, "appearance/pt_state_acid.prt", target, "", null, "state_acid");
                playClientEffectObj(target, "sound/wep_heavy_acid_launcher_hit.snd", target, "");
            }
        }
        else if (type.equals(DOT_ENERGY))
        {
            old_effect_string = SID_ENERGY_INCREASE;
            new_effect_string = SID_ENERGY;
            pulse = ENERGY_PULSE;
            if (!buff.hasBuff(target, "energy"))
            {
                buff.applyBuff(target, attacker, "energy");
                playClientEffectObj(target, "appearance/pt_state_energy.prt", target, "", null, "state_energy");
                playClientEffectObj(target, "sound/item_electronics_break.snd", target, "");
            }
        }
        else 
        {
            return false;
        }
        if (utils.hasScriptVar(target, SCRIPT_VAR_DOT_GRACE))
        {
            utils.removeScriptVar(target, SCRIPT_VAR_DOT_GRACE);
        }
        if (!hasScript(target, SCRIPT_PLAYER_DOT))
        {
            attachScript(target, SCRIPT_PLAYER_DOT);
        }
        if (type.equals(DOT_DISEASE))
        {
            _applyDiseaseDamage(target, dot_id);
        }
        if (!stacking)
        {
            dictionary d = new dictionary();
            d.put("dot_id", dot_id);
            d.put("pulse", pulse);
            d.put("attacker", attacker);
            messageTo(target, "OnDotPulse", d, pulse, false);
        }
        applyDotDamage(target, dot_id);
        if (verbose)
        {
            if (new_dot_type)
            {
                combat.sendCombatSpamMessage(target, new_effect_string);
            }
            else 
            {
                combat.sendCombatSpamMessage(target, old_effect_string);
            }
        }
        return true;
    }
    public static boolean canApplyDotType(obj_id target, String type) throws InterruptedException
    {
        int niche = ai_lib.aiGetNiche(target);
        if (!isIdValid(target))
        {
            return false;
        }
        if (vehicle.isVehicle(target))
        {
            return false;
        }
        prose_package pp = new prose_package();
        if (niche == NICHE_DROID || niche == NICHE_ANDROID)
        {
            if (type.equals(DOT_POISON) || type.equals(DOT_DISEASE))
            {
                pp = prose.setStringId(pp, new string_id("spam", "unaffected"));
                showFlyTextPrivateProseWithFlags(target, target, pp, 1.5f, colors.MEDIUMPURPLE, FLY_TEXT_FLAG_IS_DOT);
                return false;
            }
        }
        if (niche == NICHE_VEHICLE)
        {
            if (type.equals(DOT_POISON) || type.equals(DOT_DISEASE))
            {
                pp = prose.setStringId(pp, new string_id("spam", "unaffected"));
                showFlyTextPrivateProseWithFlags(target, target, pp, 1.5f, colors.MEDIUMPURPLE, FLY_TEXT_FLAG_IS_DOT);
                return false;
            }
        }
        if (ai_lib.isTurret(target))
        {
            if (type.equals(DOT_POISON) || type.equals(DOT_DISEASE))
            {
                pp = prose.setStringId(pp, new string_id("spam", "unaffected"));
                showFlyTextPrivateProseWithFlags(target, target, pp, 1.5f, colors.MEDIUMPURPLE, FLY_TEXT_FLAG_IS_DOT);
                return false;
            }
        }
        if (checkForDotImmunity(target, type))
        {
            return false;
        }
        return true;
    }
    public static boolean applyDotEffect(obj_id target, String type, String dot_id, int strength, int duration) throws InterruptedException
    {
        return applyDotEffect(target, null, type, dot_id, HEALTH, 100, strength, duration, true, null);
    }
    public static boolean applyDotEffect(obj_id target, String type, String dot_id, int attribute, int potency, int strength, int duration, boolean verbose) throws InterruptedException
    {
        return applyDotEffect(target, null, type, dot_id, attribute, potency, strength, duration, verbose, null);
    }
    public static boolean applyDotEffect(obj_id target, String type, String dot_id, int attribute, int potency, int strength, int duration) throws InterruptedException
    {
        return applyDotEffect(target, null, type, dot_id, attribute, potency, strength, duration, true, null);
    }
    public static boolean applyBleedingEffect(obj_id target, String dot_id, int attribute, int strength, int duration) throws InterruptedException
    {
        if (!isPlayer(target))
        {
            if ((ai_lib.aiGetNiche(target) == NICHE_DROID) || ai_lib.isAndroid(target) || (ai_lib.aiGetNiche(target) == NICHE_VEHICLE) || (vehicle.isDriveableVehicle(target)))
            {
                return false;
            }
        }
        return applyDotEffect(target, null, DOT_BLEEDING, dot_id, attribute, 150, strength, duration, true, "bleedingStopped");
    }
    public static boolean applyFireEffect(obj_id target, String dot_id, int strength, int duration) throws InterruptedException
    {
        return applyDotEffect(target, null, DOT_FIRE, dot_id, HEALTH, 150, strength, duration, true, "bleedingStopped");
    }
    public static boolean applyDotEffect(obj_id target, obj_id attacker, String type, String dot_id, int attribute, int potency, int strength, int duration) throws InterruptedException
    {
        return applyDotEffect(target, attacker, type, dot_id, attribute, potency, strength, duration, true, null);
    }
    public static boolean applyBleedingEffect(obj_id target, obj_id attacker, String dot_id, int attribute, int strength, int duration) throws InterruptedException
    {
        if (!isPlayer(target))
        {
            if ((ai_lib.aiGetNiche(target) == NICHE_DROID) || ai_lib.isAndroid(target) || (ai_lib.aiGetNiche(target) == NICHE_VEHICLE) || (vehicle.isDriveableVehicle(target)))
            {
                return false;
            }
        }
        return applyDotEffect(target, attacker, DOT_BLEEDING, dot_id, attribute, 150, strength, duration, true, "bleedingStopped");
    }
    public static boolean applyFireEffect(obj_id target, obj_id attacker, String dot_id, int strength, int duration) throws InterruptedException
    {
        return applyDotEffect(target, attacker, DOT_FIRE, dot_id, HEALTH, 150, strength, duration, true, "bleedingStopped");
    }
    public static boolean removeDotEffect(obj_id target, String dot_id, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (!hasDotId(target, dot_id))
        {
            return false;
        }
        else 
        {
            String type = getDotType(target, dot_id);
            String handler = getDotHandler(target, dot_id);
            utils.removeScriptVarTree(target, getDotScriptVarName(dot_id));
            String[] dots = getAllDots(target);
            if (dots == null)
            {
                utils.removeScriptVarTree(target, VAR_DOT_ROOT);
                int[] dotBuffs = buff.getAllBuffsByEffect(target, "dot");
                if (dotBuffs == null && hasScript(target, SCRIPT_PLAYER_DOT))
                {
                    detachScript(target, SCRIPT_PLAYER_DOT);
                }
            }
            if (handler != null)
            {
                dictionary d = new dictionary();
                messageTo(target, handler, d, 0, true);
            }
            String[] dots_type = getAllDotsType(target, type);
            if (dots_type == null)
            {
                if (type.equals(DOT_BLEEDING))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_BLEEDING_STOP);
                    }
                    setState(target, STATE_BLEEDING, false);
                    stopClientEffectObjByLabel(target, "state_bleeding");
                    playClientEffectObj(target, "sound/sta_bleeding_off.snd", target, "");
                    if (!isBleeding(target))
                    {
                        buff.removeBuff(target, "bleeding");
                    }
                }
                else if (type.equals(DOT_POISON))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_POISONED_STOP);
                    }
                    setState(target, STATE_POISONED, false);
                    stopClientEffectObjByLabel(target, "state_poisoned");
                    playClientEffectObj(target, "sound/sta_poisoned_off.snd", target, "");
                    if (!isPoisoned(target))
                    {
                        buff.removeBuff(target, "poisoned");
                    }
                }
                else if (type.equals(DOT_DISEASE))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_DISEASED_STOP);
                    }
                    setState(target, STATE_DISEASED, false);
                    stopClientEffectObjByLabel(target, "state_diseased");
                    playClientEffectObj(target, "sound/sta_diseased_off.snd", target, "");
                    if (hasAttribModifier(target, "dot.disease." + dot_id))
                    {
                        removeAttribOrSkillModModifier(target, "dot.disease." + dot_id);
                    }
                    if (!isDiseased(target))
                    {
                        buff.removeBuff(target, "diseased");
                    }
                }
                else if (type.equals(DOT_FIRE))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_FIRE_STOP);
                    }
                    setState(target, STATE_ON_FIRE, false);
                    stopClientEffectObjByLabel(target, "state_onfire");
                    playClientEffectObj(target, "sound/sta_onfire_off.snd", target, "");
                    if (!isOnFire(target))
                    {
                        buff.removeBuff(target, "onfire");
                    }
                }
                else if (type.equals(DOT_ACID))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_ACID_STOP);
                    }
                    stopClientEffectObjByLabel(target, "state_acid");
                    if (!isAcid(target))
                    {
                        buff.removeBuff(target, "acid");
                    }
                }
                else if (type.equals(DOT_ENERGY))
                {
                    if (verbose)
                    {
                        combat.sendCombatSpamMessage(target, SID_ENERGY_STOP);
                    }
                    stopClientEffectObjByLabel(target, "state_energy");
                    if (!isEnergy(target))
                    {
                        buff.removeBuff(target, "energy");
                    }
                }
            }
            else 
            {
                if (verbose)
                {
                    if (type.equals(DOT_BLEEDING))
                    {
                        combat.sendCombatSpamMessage(target, SID_BLEEDING_DECREASE);
                    }
                    else if (type.equals(DOT_POISON))
                    {
                        combat.sendCombatSpamMessage(target, SID_POISONED_DECREASE);
                    }
                    else if (type.equals(DOT_DISEASE))
                    {
                        combat.sendCombatSpamMessage(target, SID_DISEASED_DECREASE);
                    }
                    else if (type.equals(DOT_FIRE))
                    {
                        combat.sendCombatSpamMessage(target, SID_FIRE_DECREASE);
                    }
                    else if (type.equals(DOT_ACID))
                    {
                        combat.sendCombatSpamMessage(target, SID_ACID_DECREASE);
                    }
                    else if (type.equals(DOT_ENERGY))
                    {
                        combat.sendCombatSpamMessage(target, SID_ENERGY_DECREASE);
                    }
                }
            }
            return true;
        }
    }
    public static boolean removeDotEffect(obj_id target, String dot_id) throws InterruptedException
    {
        return removeDotEffect(target, dot_id, true);
    }
    public static boolean removeAllDots(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDots(target);
        if (dots != null)
        {
            for (int i = 0; i < dots.length; i++)
            {
                removeDotEffect(target, dots[i], false);
            }
        }
        return true;
    }
    public static boolean removeDotsOfType(obj_id target, String type) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, type);
        if (dots != null)
        {
            for (int i = 0; i < dots.length; i++)
            {
                removeDotEffect(target, dots[i], false);
            }
            return true;
        }
        return false;
    }
    public static boolean _applyDiseaseDamage(obj_id target, String dot_id) throws InterruptedException
    {
        int strength = utils.getIntScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_STRENGTH);
        int absorption_mod = getEnhancedSkillStatisticModifier(target, "absorption_disease");
        if (absorption_mod > 0)
        {
            if (absorption_mod > 90)
            {
                absorption_mod = 90;
            }
            strength = (int)(strength * (1.0f - (absorption_mod / 100.0f)));
        }
        float duration = (float)utils.getIntScriptVar(target, VAR_DOT_ROOT + dot_id + VAR_DURATION);
        float up = duration * 0.75f;
        float down = duration * 0.25f;
        if (addAttribModifier(target, "dot.disease." + dot_id, ACTION, (-1 * strength), duration, up, down, false, true, false))
        {
            displayDiseaseEffect(getLocation(target));
            obj_id attacker = dot.getDotAttacker(target, dot_id);
            if (isIdValid(attacker) && exists(attacker))
            {
                xp.updateCombatXpList(target, attacker, xp.PERMISSIONS_ONLY, strength);
                if (!isPlayer(target))
                {
                    addHate(target, attacker, strength);
                    addHate(attacker, target, 0.0f);
                }
            }
            else 
            {
                xp.updateCombatXpList(target, target, xp.PERMISSIONS_ONLY, strength);
            }
        }
        else 
        {
            return false;
        }
        return true;
    }
    public static boolean applyDotDamage(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (!hasDotId(target, dot_id))
        {
            return false;
        }
        String type = dot.getDotType(target, dot_id);
        if (type == null)
        {
            return false;
        }
        int strength = dot.getDotStrength(target, dot_id);
        int duration = dot.getDotDuration(target, dot_id);
        String dotScriptVar = getDotScriptVarName(dot_id);
        if (!utils.hasScriptVar(target, SCRIPT_VAR_DOT_GRACE))
        {
            if (isDead(target) || isIncapacitated(target))
            {
                return true;
            }
        }
        else 
        {
            return true;
        }
        int absorption_mod = 0;
        int vulnerability_mod = (int)getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_all");
        dictionary protDic = armor.getCombatArmorSpecialProtections(target);
        float resistance = 0.0f;
        if (isPlayer(target))
        {
            resistance = (float)armor.getCombatArmorGeneralProtection(target);
        }
        if (type.equals(DOT_BLEEDING))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_bleeding");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_bleed");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("cold");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_POISON))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_poison");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_poison");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("acid");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_DISEASE))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_disease");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_disease");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("cold");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_FIRE))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_fire");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_fire");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("heat");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_ACID))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_acid");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_acid");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("acid");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_ENERGY))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_energy");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_energy");
            if (isPlayer(target) && protDic != null)
            {
                resistance += protDic.getFloat("energy");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else 
        {
            return false;
        }
        int damageAbsorbed = 0;
        if (absorption_mod > 0)
        {
            if (absorption_mod > 50)
            {
                absorption_mod = 50;
            }
            damageAbsorbed = (int)(strength * (absorption_mod / 100.0f));
            strength = (int)(strength * (1.0f - (absorption_mod / 100.0f)));
        }
        if (vulnerability_mod > 0)
        {
            strength = (int)(strength * (1.0f + (vulnerability_mod / 100.0f)));
        }
        int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(target, "combat_multiply_damage_taken");
        float tempDamageFloat = (float)strength;
        tempDamageFloat = tempDamageFloat * (1.0f + ((float)expertiseDamageBonus / 100.0f));
        int expertiseDamageReduction = getEnhancedSkillStatisticModifierUncapped(target, "combat_divide_damage_taken");
        expertiseDamageReduction = expertiseDamageReduction > 100 ? 100 : expertiseDamageReduction;
        tempDamageFloat = tempDamageFloat * (1.0f - ((float)expertiseDamageReduction / 100.0f));
        strength = (int)tempDamageFloat;
        int current_attrib = getAttrib(target, HEALTH);
        if (type.equals(DOT_DISEASE))
        {
            current_attrib = getAttrib(target, ACTION);
            strength = Math.round((float)strength / 3.0f);
        }
        obj_id attacker = dot.getDotAttacker(target, dot_id);
        if ((attacker != target) && !pvpCanAttack(attacker, target))
        {
            removeDotEffect(target, dot_id);
            return false;
        }
        int damageResisted = 0;
        if (attemptDotResist(target, type, 100, false))
        {
            int divisor = rand(2, 25);
            int oldStrength = strength;
            strength /= divisor;
            damageResisted = oldStrength - strength;
        }
        String mitigationString = "(" + damageAbsorbed + getString(new string_id("dot_message", "generic_absorbed")) + damageResisted + getString(new string_id("dot_message", "generic_resisted")) + ")";
        if (strength > 0)
        {
            hit_result hit = new hit_result();
            hit.damage = strength;
            hit.success = true;
            string_id strSpam = new string_id("combat_effects", "damage_fly");
            prose_package ppFly = new prose_package();
            ppFly = prose.setStringId(ppFly, strSpam);
            ppFly = prose.setDI(ppFly, -strength);
            prose_package ppSpam = new prose_package();
            if (type.equals(dot.DOT_BLEEDING))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_BLEED_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.INDIANRED, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_BLEED_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.INDIANRED, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_POISON))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_POISON_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.YELLOWGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_POISON_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.YELLOWGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_DISEASE))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_DISEASE_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.LIMEGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_DISEASE_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.LIMEGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_FIRE))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_FIRE_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.FIREBRICK, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_FIRE_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.FIREBRICK, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_ACID))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ACID_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.OLIVEDRAB, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ACID_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.OLIVEDRAB, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_ENERGY))
            {
                if (isIdValid(attacker) && !isDead(attacker))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ENERGY_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, attacker);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(attacker, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, attacker, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ENERGY_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else 
            {
                return false;
            }
            if (!type.equals(DOT_DISEASE))
            {
                doDamage(attacker, target, hit);
            }
            else 
            {
                drainAttributes(target, strength, 0);
            }
            if (isIdValid(attacker) && isIdValid(target) && exists(attacker) && exists(target))
            {
                startCombat(attacker, target);
                startCombat(target, attacker);
            }
            if (isIdValid(attacker) && !isDead(attacker))
            {
                obj_id myWeapon = getCurrentWeapon(attacker);
                if (isIdValid(myWeapon))
                {
                    xp.updateCombatXpList(target, attacker, myWeapon, strength);
                }
                else 
                {
                    xp.updateCombatXpList(target, attacker, xp.COMBAT_RANGEDSPECIALIZE_RIFLE, strength);
                }
            }
            else 
            {
                xp.updateCombatXpList(target, target, xp.PERMISSIONS_ONLY, strength);
            }
            if (!isPlayer(target))
            {
                if (isIdValid(attacker) && exists(attacker))
                {
                    addHate(target, attacker, strength);
                    addHate(attacker, target, 0.0f);
                }
            }
        }
        int pulse = getDotPulse(target, dot_id);
        duration = duration - pulse;
        if (duration < 1)
        {
            removeDotEffect(target, dot_id);
            return false;
        }
        else 
        {
            if (hasDotId(target, dot_id))
            {
                utils.setScriptVar(target, dotScriptVar + VAR_DURATION, duration);
            }
            return true;
        }
    }
    public static boolean applyCombatDots(obj_id attacker, obj_id defender, hit_result results, weapon_data weapon, float bleeding_mult) throws InterruptedException
    {
        if (!isIdValid(attacker))
        {
            return false;
        }
        if (!isIdValid(defender))
        {
            return false;
        }
        if (results == null)
        {
            return false;
        }
        if (weapon == null)
        {
            return false;
        }
        int hit_location = results.hitLocation;
        float damage = (float)results.damage;
        float wound_rating = weapon.woundChance;
        if (damage < 1)
        {
            return false;
        }
        float attacker_mod = getEnhancedSkillStatisticModifier(attacker, "combat_bleeding_attack");
        float defender_mod = getEnhancedSkillStatisticModifier(attacker, "combat_bleeding_defense");
        float bleeding_base = damage * (1.0f + (attacker_mod - defender_mod) / 100.0f) / 50.0f;
        float bleeding_chance = bleeding_base + wound_rating;
        boolean dot_applied = false;
        if (rand(1, 10000) <= bleeding_chance)
        {
            float strength = bleeding_base / 30;
            float duration = rand(120, 300) * (1.0f + (attacker_mod - defender_mod) / 100.0f);
            String hit_location_str = "combat_bleeding";
            int attribute = HIT_LOCATION_ATTRIBUTE[hit_location];
            if (strength > 0)
            {
                applyBleedingEffect(defender, attacker, hit_location_str, attribute, (int)strength, (int)duration);
            }
            dot_applied = true;
        }
        obj_id weapon_id = weapon.id;
        if (combat.hasCertification(attacker, weapon_id))
        {
            if (utils.hasScriptVarTree(weapon_id, VAR_DOT))
            {
                String[] weapon_dots = getAllDots(weapon_id);
                if (weapon_dots != null)
                {
                    for (int i = 0; i < weapon_dots.length; i++)
                    {
                        String type = getDotType(weapon_id, weapon_dots[i]);
                        int attribute = getDotAttribute(weapon_id, weapon_dots[i]);
                        int potency = getDotPotency(weapon_id, weapon_dots[i]);
                        int strength = getDotStrength(weapon_id, weapon_dots[i]);
                        int duration = getDotDuration(weapon_id, weapon_dots[i]);
                        if (canApplyDotType(defender, type))
                        {
                            applyDotEffect(defender, attacker, type, weapon_dots[i], attribute, potency, strength, duration, true, null);
                            decrementDotUses(weapon_id, weapon_dots[i]);
                            dot_applied = true;
                        }
                    }
                }
            }
        }
        return dot_applied;
    }
    public static boolean modifyDotEffectStrength(obj_id target, String dot_id, int strength, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (!hasDotId(target, dot_id))
        {
            return false;
        }
        int current_strength = getDotStrength(target, dot_id);
        String type = getDotType(target, dot_id);
        if (current_strength == -1)
        {
            return false;
        }
        current_strength = current_strength + strength;
        if (current_strength < 1)
        {
            removeDotEffect(target, dot_id, verbose);
        }
        else 
        {
            utils.setScriptVar(target, getDotScriptVarName(dot_id) + VAR_STRENGTH, current_strength);
            if (verbose)
            {
                if (type.equals(DOT_BLEEDING))
                {
                    combat.sendCombatSpamMessage(target, SID_BLEEDING_DECREASE);
                }
                else if (type.equals(DOT_POISON))
                {
                    combat.sendCombatSpamMessage(target, SID_POISONED_DECREASE);
                }
                else if (type.equals(DOT_DISEASE))
                {
                    combat.sendCombatSpamMessage(target, SID_DISEASED_DECREASE);
                }
                else if (type.equals(DOT_FIRE))
                {
                    combat.sendCombatSpamMessage(target, SID_FIRE_DECREASE);
                }
                else if (type.equals(DOT_ACID))
                {
                    combat.sendCombatSpamMessage(target, SID_FIRE_DECREASE);
                }
                else if (type.equals(DOT_ENERGY))
                {
                    combat.sendCombatSpamMessage(target, SID_FIRE_DECREASE);
                }
            }
        }
        return true;
    }
    public static int reduceDotTypeStrength(obj_id target, String type, int strength) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (!isMob(target))
        {
            return -1;
        }
        if (type == null)
        {
            return -1;
        }
        if (strength < 1)
        {
            return -1;
        }
        String[] dots = getAllDotsType(target, type);
        if (dots == null)
        {
            return -1;
        }
        int remaining_strength = strength;
        for (int i = 0; i < dots.length; i++)
        {
            int dot_strength = getDotStrength(target, dots[i]);
            if (dot_strength <= remaining_strength)
            {
                remaining_strength = remaining_strength - dot_strength;
                if (remaining_strength < 1 || i == dots.length - 1)
                {
                    removeDotEffect(target, dots[i], true);
                }
                else 
                {
                    removeDotEffect(target, dots[i], false);
                }
            }
            else 
            {
                dot_strength = dot_strength - remaining_strength;
                modifyDotEffectStrength(target, dots[i], -remaining_strength, true);
                remaining_strength = 0;
                break;
            }
        }
        return strength - remaining_strength;
    }
    public static boolean attemptDotResist(obj_id target, String type, int potency, boolean showResistFlytext) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (type == null)
        {
            return false;
        }
        if (isIncapacitated(target) || isDead(target))
        {
            return true;
        }
        if (potency < 0)
        {
            if (potency == -1)
            {
                return false;
            }
            else 
            {
                return false;
            }
        }
        int resistance_mod = 0;
        if (type.equals(DOT_BLEEDING))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_bleeding");
        }
        else if (type.equals(DOT_POISON))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_poison");
        }
        else if (type.equals(DOT_DISEASE))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_disease");
        }
        else if (type.equals(DOT_FIRE))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_fire");
        }
        else if (type.equals(DOT_ACID))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_acid");
        }
        else if (type.equals(DOT_ENERGY))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_energy");
        }
        else if (type.equals(DOT_ELECTRICITY))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_eletricity");
        }
        else if (type.equals(DOT_COLD))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_cold");
        }
        else if (type.equals(DOT_KINETIC))
        {
            resistance_mod = getEnhancedSkillStatisticModifier(target, "resistance_kinetic");
        }
        else 
        {
            return false;
        }
        resistance_mod /= MOD_DIVISOR;
        float resist_scale = (0.4f / ((100f + resistance_mod) / 200f));
        float to_hit_chance = potency * resist_scale;
        if (to_hit_chance < 5)
        {
            to_hit_chance = 5;
        }
        if (to_hit_chance > 95)
        {
            to_hit_chance = 95;
        }
        if ((int)rand(1, 100) < to_hit_chance)
        {
            return false;
        }
        else 
        {
            if (showResistFlytext)
            {
                combat.sendCombatSpamMessage(target, SID_DOT_RESISTED_SPAM);
                prose_package pp = new prose_package();
                pp = prose.setStringId(pp, new string_id("spam", "unaffected"));
                showFlyTextPrivateProseWithFlags(target, target, pp, 1.5f, colors.MEDIUMPURPLE, FLY_TEXT_FLAG_IS_DOT);
            }
            return true;
        }
    }
    public static boolean hasDotId(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        String scriptvar_name = getDotScriptVarName(dot_id);
        if (utils.hasScriptVarTree(target, scriptvar_name))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static String getDotScriptVarName(String dot_id) throws InterruptedException
    {
        return VAR_DOT_ROOT + dot_id;
    }
    public static String[] getAllDots(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        HashSet dots_found = new HashSet();
        deltadictionary dd = target.getScriptVars();
        Enumeration keys = dd.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)(keys.nextElement());
            if (key.startsWith(VAR_DOT_ROOT))
            {
                int dotIndex = key.indexOf(".");
                if (dotIndex > -1)
                {
                    String dotItem = key.substring(dotIndex + 1);
                    dotIndex = dotItem.indexOf(".");
                    if (dotIndex > -1)
                    {
                        String dotId = dotItem.substring(0, dotIndex);
                        dots_found.add(dotId);
                    }
                }
            }
        }
        if (dots_found.size() < 1)
        {
            return null;
        }
        else 
        {
            String[] dot_string = new String[dots_found.size()];
            dots_found.toArray(dot_string);
            return dot_string;
        }
    }
    public static String[] getAllDotsType(obj_id target, String type) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (type == null)
        {
            return null;
        }
        Vector dots_found = new Vector();
        String[] dots = getAllDots(target);
        if (dots != null)
        {
            for (int i = 0; i < dots.length; ++i)
            {
                String scriptVarName = VAR_DOT_ROOT + dots[i] + VAR_TYPE;
                String scriptVarValue = utils.getStringScriptVar(target, scriptVarName);
                if ((scriptVarValue != null) && scriptVarValue.equals(type))
                {
                    dots_found.addElement(dots[i]);
                }
            }
        }
        if (dots_found.size() < 1)
        {
            return null;
        }
        else 
        {
            String[] dot_string = new String[dots_found.size()];
            dots_found.toArray(dot_string);
            return dot_string;
        }
    }
    public static String[] getAllDotsOwner(obj_id owner, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (!isIdValid(target))
        {
            return null;
        }
        Vector dots_found = new Vector();
        String[] dots = getAllDots(target);
        if (dots != null)
        {
            for (int i = 0; i < dots.length; ++i)
            {
                obj_id who = getDotAttacker(target, dots[i]);
                if (isIdValid(who) && owner == who)
                {
                    dots_found.addElement(dots[i]);
                }
            }
        }
        if (dots_found.size() < 1)
        {
            return null;
        }
        else 
        {
            String[] dot_string = new String[dots_found.size()];
            dots_found.toArray(dot_string);
            return dot_string;
        }
    }
    public static void removeAllDotsOwner(obj_id owner, obj_id target) throws InterruptedException
    {
        String[] dots = getAllDotsOwner(owner, target);
        if (dots == null || dots.length == 0)
        {
            return;
        }
        for (int i = 0; i < dots.length; i++)
        {
            removeDotEffect(target, dots[i], false);
        }
    }
    public static boolean isBleeding(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_BLEEDING);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isPoisoned(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_POISON);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isDiseased(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_DISEASE);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isOnFire(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_FIRE);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isAcid(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_ACID);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isEnergy(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        String[] dots = getAllDotsType(target, DOT_ENERGY);
        if (dots != null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static String getDotType(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (dot_id == null)
        {
            return null;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getStringScriptVar(target, getDotScriptVarName(dot_id) + VAR_TYPE);
        }
        else 
        {
            return null;
        }
    }
    public static int getDotAttribute(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getIntScriptVar(target, getDotScriptVarName(dot_id) + VAR_ATTRIBUTE);
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotPotency(obj_id source, String dot_id) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(source, dot_id))
        {
            return utils.getIntScriptVar(source, getDotScriptVarName(dot_id) + VAR_POTENCY);
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotStrength(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getIntScriptVar(target, getDotScriptVarName(dot_id) + VAR_STRENGTH);
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotDuration(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getIntScriptVar(target, getDotScriptVarName(dot_id) + VAR_DURATION);
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotStartTime(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getIntScriptVar(target, getDotScriptVarName(dot_id) + VAR_TIME_START);
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotLastPulse(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getIntScriptVar(target, getDotScriptVarName(dot_id) + VAR_LAST_PULSE);
        }
        else 
        {
            return -1;
        }
    }
    public static String getDotHandler(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (dot_id == null)
        {
            return null;
        }
        if (hasDotId(target, dot_id))
        {
            return utils.getStringScriptVar(target, getDotScriptVarName(dot_id) + VAR_HANDLER);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getDotAttacker(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return null;
        }
        if (dot_id == null)
        {
            return null;
        }
        if (hasDotId(target, dot_id))
        {
            String scriptVarName = getDotScriptVarName(dot_id) + VAR_ATTACKER;
            if (utils.hasScriptVar(target, scriptVarName))
            {
                return utils.getObjIdScriptVar(target, scriptVarName);
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static int getDotPulse(obj_id target, String dot_id) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        String type = getDotType(target, dot_id);
        if (type == null)
        {
            return -1;
        }
        if (type.equals(dot.DOT_BLEEDING))
        {
            return dot.BLEEDING_PULSE;
        }
        else if (type.equals(dot.DOT_POISON))
        {
            return dot.POISON_PULSE;
        }
        else if (type.equals(dot.DOT_DISEASE))
        {
            return dot.DISEASE_PULSE;
        }
        else if (type.equals(dot.DOT_FIRE))
        {
            return dot.FIRE_PULSE;
        }
        else if (type.equals(dot.DOT_ACID))
        {
            return dot.ACID_PULSE;
        }
        else if (type.equals(dot.DOT_ENERGY))
        {
            return dot.ENERGY_PULSE;
        }
        else 
        {
            return -1;
        }
    }
    public static int getDotUses(obj_id source, String dot_id) throws InterruptedException
    {
        if (!isIdValid(source))
        {
            return -1;
        }
        if (dot_id == null)
        {
            return -1;
        }
        if (hasDotId(source, dot_id))
        {
            return utils.getIntScriptVar(source, getDotScriptVarName(dot_id) + VAR_USES);
        }
        else 
        {
            return -1;
        }
    }
    public static boolean addDotAttackToItem(obj_id item, String type, String dot_id, int attribute, int potency, int strength, int duration, boolean stackable, int uses) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (type == null)
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (attribute < 0 || attribute > 8)
        {
            return false;
        }
        if (potency != -1 && potency < 1)
        {
            return false;
        }
        if (strength < 1)
        {
            return false;
        }
        if (duration < 1)
        {
            return false;
        }
        if (uses != -1 && uses < 1)
        {
            return false;
        }
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_TYPE, type);
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_ATTRIBUTE, attribute);
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_POTENCY, potency);
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_STRENGTH, strength);
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_DURATION, duration);
        utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_USES, uses);
        return true;
    }
    public static boolean decrementDotUses(obj_id item, String dot_id) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (!hasDotId(item, dot_id))
        {
            return false;
        }
        int uses = getDotUses(item, dot_id);
        if (uses != -1)
        {
            uses = uses - 1;
            if (uses < 1)
            {
                utils.removeScriptVarTree(item, getDotScriptVarName(dot_id));
            }
            else 
            {
                utils.setScriptVar(item, VAR_DOT_ROOT + dot_id + VAR_USES, uses);
            }
        }
        return true;
    }
    public static void displayBleedingEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_bleeding.cef", loc, 0);
            }
        }
        return;
    }
    public static void displayPoisonEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_poisoned.cef", loc, 0);
            }
        }
        return;
    }
    public static void displayDiseaseEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_diseased.cef", loc, 0);
            }
        }
        return;
    }
    public static void displayFireEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_fire.cef", loc, 0);
            }
        }
        return;
    }
    public static void displayAcidEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_fire.cef", loc, 0);
            }
        }
        return;
    }
    public static void displayEnergyEffect(location loc) throws InterruptedException
    {
        if (loc == null)
        {
            return;
        }
        obj_id[] players = getAllPlayers(loc, VAR_EFFECT_DISPLAY_RADIUS);
        if (players != null)
        {
            for (int i = 0; i < players.length; i++)
            {
                playClientEffectLoc(players[i], "clienteffect/dot_fire.cef", loc, 0);
            }
        }
        return;
    }
    public static boolean checkForDotImmunity(obj_id target, String type) throws InterruptedException
    {
        int resistVar = getEnhancedSkillStatisticModifierUncapped(target, "dot_resist_" + type);
        int universal = getEnhancedSkillStatisticModifierUncapped(target, "dot_resist_all");
        int elemental = getElementalGroupResist(target, type);
        int natural = getNaturalGroupResist(target, type);
        int applyCheck = rand(0, 99);
        boolean wasResisted = false;
        if (applyCheck < resistVar)
        {
            wasResisted = true;
        }
        if (applyCheck < universal)
        {
            wasResisted = true;
        }
        if (applyCheck < elemental)
        {
            wasResisted = true;
        }
        if (applyCheck < natural)
        {
            wasResisted = true;
        }
        return wasResisted;
    }
    public static int getElementalGroupResist(obj_id target, String type) throws InterruptedException
    {
        if (!type.equals(DOT_FIRE) && !type.equals(DOT_ACID) && !type.equals(DOT_COLD) && !type.equals(DOT_ELECTRICITY) && !type.equals(DOT_ENERGY) && !type.equals(DOT_KINETIC))
        {
            return 0;
        }
        return getEnhancedSkillStatisticModifierUncapped(target, "dot_resist_elemental");
    }
    public static int getNaturalGroupResist(obj_id target, String type) throws InterruptedException
    {
        if (!type.equals(DOT_POISON) && !type.equals(DOT_DISEASE) && !type.equals(DOT_BLEEDING))
        {
            return 0;
        }
        return getEnhancedSkillStatisticModifierUncapped(target, "dot_resist_natural");
    }
    public static void capDots(obj_id attacker, obj_id target, int strength) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(attacker) || !isPlayer(target) || !isPlayer(attacker))
        {
            return;
        }
        if (!isPlayer(attacker) && (isIdValid(getMaster(attacker)) && !isPlayer(getMaster(attacker))))
        {
            return;
        }
        String[] allDots = getAllDotsOwner(attacker, target);
        if (allDots == null)
        {
            return;
        }
        if (allDots.length >= DOT_MAXIMUM_COUNT)
        {
            int oldestDot = 0;
            for (int i = 1; i < allDots.length; i++)
            {
                if (getDotStartTime(target, allDots[oldestDot]) > getDotStartTime(target, allDots[i]))
                {
                    oldestDot = i;
                }
            }
            removeDotEffect(target, allDots[oldestDot], true);
        }
    }
    public static int getDotResistanceMod(String type, obj_id target) throws InterruptedException
    {
        int dotResistanceMod = 0;
        if (isPlayer(target))
        {
            if (utils.hasScriptVar(target, "armor.armor_type_tally"))
            {
                int[] armorTypeTally = utils.getIntArrayScriptVar(target, "armor.armor_type_tally");
                if (armorTypeTally.length != 3)
                {
                    return dotResistanceMod;
                }
                if (type.equals(DOT_BLEEDING))
                {
                    dotResistanceMod = armorTypeTally[0] * armorTypeTally[0] * 2;
                }
                else if (type.equals(DOT_POISON))
                {
                    dotResistanceMod = armorTypeTally[1] * armorTypeTally[1] * 2;
                }
                else if (type.equals(DOT_DISEASE))
                {
                    dotResistanceMod = armorTypeTally[0] * armorTypeTally[0] * 2;
                }
                else if (type.equals(DOT_FIRE))
                {
                    dotResistanceMod = armorTypeTally[2] * armorTypeTally[2] * 2;
                }
                else if (type.equals(DOT_ACID))
                {
                    dotResistanceMod = armorTypeTally[2] * armorTypeTally[2] * 2;
                }
                else if (type.equals(DOT_ENERGY))
                {
                    dotResistanceMod = armorTypeTally[1] * armorTypeTally[1] * 2;
                }
            }
        }
        else if (beast_lib.isBeast(target))
        {
            float resilience = beast_lib.getBeastCustomMod(target, beast_lib.MOD_RESILIENCE);
            float survival = beast_lib.getBeastCustomMod(target, beast_lib.MOD_SURVIVAL);
            int beastResistMod = Math.round((resilience * resilience) + (survival * survival));
            dotResistanceMod = beastResistMod;
        }
        if (dotResistanceMod > 100)
        {
            dotResistanceMod = 100;
        }
        return dotResistanceMod;
    }
    public static boolean applyBuffDotEffect(obj_id target, obj_id attacker, String type, String dot_id, long stack, int attribute, int potency, int strength, int duration, boolean verbose, String buffName) throws InterruptedException
    {
        if (!isIdValid(attacker))
        {
            attacker = getSelf();
            if (!isIdValid(attacker))
            {
                return false;
            }
        }
        else 
        {
            if (!isMob(target))
            {
                return false;
            }
        }
        if (type == null)
        {
            return false;
        }
        if (dot_id == null)
        {
            return false;
        }
        if (attribute < 0 || attribute > 8)
        {
            return false;
        }
        if (potency != -1 && potency < 1)
        {
            return false;
        }
        if (strength < 1)
        {
            return false;
        }
        if (duration < 1)
        {
            return false;
        }
        if (!canApplyDotType(target, type))
        {
            return false;
        }
        if (isIdValid(attacker) && isMob(attacker) && !target.equals(attacker))
        {
            addHate(target, attacker, 0.0f);
            addHate(attacker, target, 0.0f);
        }
        string_id old_effect_string;
        string_id new_effect_string;
        if (type.equals(DOT_FIRE))
        {
            if (getState(target, STATE_SWIMMING) != 1)
            {
                old_effect_string = SID_FIRE_INCREASE;
                new_effect_string = SID_FIRE;
                if (stack == 1)
                {
                    playClientEffectObj(target, "appearance/pt_state_onfire.prt", target, "", null, "state_onfire");
                }
                playClientEffectObj(target, "sound/sta_onfire_on.snd", target, "");
            }
            else 
            {
                buff.removeBuff(target, buffName);
                return false;
            }
        }
        else if (type.equals(DOT_ACID))
        {
            old_effect_string = SID_ACID_INCREASE;
            new_effect_string = SID_ACID;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_acid.prt", target, "", null, "state_acid");
            }
            playClientEffectObj(target, "sound/wep_heavy_acid_launcher_hit.snd", target, "");
        }
        else if (type.equals(DOT_ENERGY))
        {
            old_effect_string = SID_ENERGY_INCREASE;
            new_effect_string = SID_ENERGY;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_energy.prt", target, "", null, "state_energy");
            }
            playClientEffectObj(target, "sound/item_electronics_break.snd", target, "");
        }
        else if (type.equals(DOT_COLD))
        {
            old_effect_string = SID_COLD_INCREASE;
            new_effect_string = SID_COLD;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_energy.prt", target, "", null, "state_cold");
            }
            playClientEffectObj(target, "sound/item_electronics_break.snd", target, "");
        }
        else if (type.equals(DOT_ELECTRICITY))
        {
            old_effect_string = SID_ELECTRICITY_INCREASE;
            new_effect_string = SID_ELECTRICITY;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_energy.prt", target, "", null, "state_electricity");
            }
            playClientEffectObj(target, "sound/item_electronics_break.snd", target, "");
        }
        else if (type.equals(DOT_KINETIC))
        {
            old_effect_string = SID_KINETIC_INCREASE;
            new_effect_string = SID_KINETIC;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_energy.prt", target, "", null, "state_kinetic");
            }
            playClientEffectObj(target, "sound/item_electronics_break.snd", target, "");
        }
        else if (type.equals(DOT_BLEEDING))
        {
            new_effect_string = SID_BLEEDING;
            old_effect_string = SID_BLEEDING_INCREASE;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_bleeding.prt", target, "spine3", null, "state_bleeding");
            }
            playClientEffectObj(target, "sound/sta_bleeding_on.snd", target, "");
        }
        else if (type.equals(DOT_POISON))
        {
            old_effect_string = SID_POISONED_INCREASE;
            new_effect_string = SID_POISONED;
            if (stack == 1)
            {
                playClientEffectObj(target, "appearance/pt_state_poisoned.prt", target, "", null, "state_poisoned");
            }
            playClientEffectObj(target, "sound/sta_poisoned_on.snd", target, "");
        }
        else 
        {
            buff.removeBuff(target, buffName);
            return false;
        }
        if (!hasScript(target, SCRIPT_PLAYER_DOT))
        {
            attachScript(target, SCRIPT_PLAYER_DOT);
        }
        if (stack == 1)
        {
            trial.bumpSession(target, buffName);
            dictionary d = trial.getSessionDict(target, buffName);
            d.put("buffName", buffName);
            d.put("caster", attacker);
            d.put("strength", strength);
            d.put("type", type);
            messageTo(target, "OnBuffDotPulse", d, buff.BUFF_DOT_TICK, false);
        }
        if (verbose)
        {
            if (stack == 1)
            {
                combat.sendCombatSpamMessage(target, new_effect_string);
            }
            else 
            {
                combat.sendCombatSpamMessage(target, old_effect_string);
            }
        }
        return true;
    }
    public static boolean applyBuffDotDamage(obj_id target, obj_id caster, String buffName, int strength, String type) throws InterruptedException
    {
        if (!isIdValid(target) || !isIdValid(caster))
        {
            return false;
        }
        int absorption_mod = 0;
        int vulnerability_mod = (int)getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_all");
        dictionary protDic = armor.getCombatArmorSpecialProtections(target);
        float resistance = 0.0f;
        if (isPlayer(target))
        {
            resistance = (float)armor.getCombatArmorGeneralProtection(target);
        }
        if (type.equals(DOT_FIRE))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_fire");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_fire");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("heat");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_ACID))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_acid");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_acid");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("acid");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_COLD))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_cold");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_cold");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("cold");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_ELECTRICITY))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_electricity");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_electricity");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("electricity");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_ENERGY))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_energy");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_energy");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("energy");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_KINETIC))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_kinetic");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_kinetic");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("kinetic");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_BLEEDING))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_bleeding");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_bleed");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("cold");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else if (type.equals(DOT_POISON))
        {
            absorption_mod += getEnhancedSkillStatisticModifier(target, "absorption_poison");
            vulnerability_mod += getEnhancedSkillStatisticModifierUncapped(target, "dot_vulnerability_poison");
            if (isPlayer(target))
            {
                resistance += protDic.getFloat("acid");
            }
            else 
            {
                resistance += (float)utils.getIntScriptVar(target, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            absorption_mod /= MOD_DIVISOR;
            absorption_mod += (int)getSkillStatisticModifier(target, "expertise_dot_absorption_all");
            absorption_mod += (int)(combat.convertProtectionToPercent(resistance) * DOT_ARMOR_MITIGATION_PERCENT);
        }
        else 
        {
            return false;
        }
        int damageAbsorbed = 0;
        if (absorption_mod > 0)
        {
            if (absorption_mod > 50)
            {
                absorption_mod = 50;
            }
            damageAbsorbed = (int)(strength * (absorption_mod / 100.0f));
            strength = (int)(strength * (1.0f - (absorption_mod / 100.0f)));
        }
        if (vulnerability_mod > 0)
        {
            strength = (int)(strength * (1.0f + (vulnerability_mod / 100.0f)));
        }
        int expertiseDamageBonus = getEnhancedSkillStatisticModifierUncapped(caster, "combat_multiply_damage_dealt");
        expertiseDamageBonus += getEnhancedSkillStatisticModifierUncapped(target, "combat_multiply_damage_taken");
        float tempDamageFloat = (float)strength;
        tempDamageFloat = tempDamageFloat * (1.0f + ((float)expertiseDamageBonus / 100.0f));
        int expertiseDamageReduction = getEnhancedSkillStatisticModifierUncapped(caster, "combat_divide_damage_dealt");
        expertiseDamageReduction += getEnhancedSkillStatisticModifierUncapped(target, "combat_divide_damage_taken");
        expertiseDamageReduction = expertiseDamageReduction > 100 ? 100 : expertiseDamageReduction;
        tempDamageFloat = tempDamageFloat * (1.0f - ((float)expertiseDamageReduction / 100.0f));
        strength = (int)tempDamageFloat;
        int current_attrib = getAttrib(target, HEALTH);
        if ((caster != target) && !pvpCanAttack(caster, target))
        {
            buff.removeBuff(target, buffName);
            return false;
        }
        String mitigationString = "(" + damageAbsorbed + getString(new string_id("dot_message", "generic_absorbed_buff")) + ")";
        if (strength > 0)
        {
            hit_result hit = new hit_result();
            hit.damage = strength;
            hit.success = true;
            string_id strSpam = new string_id("combat_effects", "damage_fly");
            prose_package ppFly = new prose_package();
            ppFly = prose.setStringId(ppFly, strSpam);
            ppFly = prose.setDI(ppFly, -strength);
            prose_package ppSpam = new prose_package();
            if (type.equals(DOT_FIRE))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_FIRE_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.FIREBRICK, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_FIRE_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.FIREBRICK, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(DOT_ACID))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ACID_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.OLIVEDRAB, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ACID_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.OLIVEDRAB, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(DOT_ENERGY))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ENERGY_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ENERGY_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(DOT_COLD))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_COLD_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_COLD_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(DOT_ELECTRICITY))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ELECTRICITY_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_ELECTRICITY_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(DOT_KINETIC))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_KINETIC_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_KINETIC_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.DEEPSKYBLUE, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_BLEEDING))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_BLEED_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.INDIANRED, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_BLEED_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.INDIANRED, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else if (type.equals(dot.DOT_POISON))
            {
                if (isIdValid(caster) && !isDead(caster))
                {
                    ppSpam = prose.setStringId(ppSpam, SID_POISON_DMG_ATKR);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setTO(ppSpam, caster);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(caster, target, ppSpam, true, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, caster, ppFly, 1f, colors.YELLOWGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
                else 
                {
                    ppSpam = prose.setStringId(ppSpam, SID_POISON_DMG);
                    ppSpam = prose.setTT(ppSpam, target);
                    ppSpam = prose.setDI(ppSpam, strength);
                    ppSpam = prose.setTU(ppSpam, mitigationString);
                    sendCombatSpamMessageProse(target, target, ppSpam, false, true, true, COMBAT_RESULT_DEBUFF);
                    showFlyTextPrivateProseWithFlags(target, target, ppFly, 1f, colors.YELLOWGREEN, FLY_TEXT_FLAG_IS_DOT);
                }
            }
            else 
            {
                return false;
            }
            doDamage(caster, target, hit);
            // Cekis: added check (!caster.equals(target)) to make sure caster doesn't start combat with itself (that would be dumb).
            if (isIdValid(caster) && isIdValid(target) && exists(caster) && exists(target) && !caster.equals(target))
            {
                startCombat(caster, target);
                startCombat(target, caster);
            }
            if (isIdValid(caster) && !isDead(caster))
            {
                obj_id myWeapon = getCurrentWeapon(caster);
                if (isIdValid(myWeapon))
                {
                    xp.updateCombatXpList(target, caster, myWeapon, strength);
                }
                else 
                {
                    xp.updateCombatXpList(target, caster, xp.COMBAT_RANGEDSPECIALIZE_RIFLE, strength);
                }
            }
            else 
            {
                xp.updateCombatXpList(target, target, xp.PERMISSIONS_ONLY, strength);
            }
            if (!isPlayer(target))
            {
                if (isIdValid(caster) && exists(caster) && !target.equals(caster))
                {
                    addHate(target, caster, strength);
                    addHate(caster, target, 0.0f);
                }
            }
        }
        return true;
    }
    public static boolean removeBuffDotEffect(obj_id target, String type, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return false;
        }
        int[] dotBuffs = buff.getAllBuffsByEffect(target, "dot");
        if (dotBuffs == null || dotBuffs.length <= 0)
        {
            String[] dots = getAllDots(target);
            if (dots == null && hasScript(target, SCRIPT_PLAYER_DOT))
            {
                detachScript(target, SCRIPT_PLAYER_DOT);
            }
        }
        if (type.equals(DOT_FIRE))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_FIRE_STOP);
            }
            setState(target, STATE_ON_FIRE, false);
            stopClientEffectObjByLabel(target, "state_onfire");
            playClientEffectObj(target, "sound/sta_onfire_off.snd", target, "");
        }
        else if (type.equals(DOT_ACID))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_ACID_STOP);
            }
            stopClientEffectObjByLabel(target, "state_acid");
        }
        else if (type.equals(DOT_ENERGY))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_ENERGY_STOP);
            }
            stopClientEffectObjByLabel(target, "state_energy");
        }
        else if (type.equals(DOT_COLD))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_COLD_STOP);
            }
            stopClientEffectObjByLabel(target, "state_cold");
        }
        else if (type.equals(DOT_ELECTRICITY))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_ELECTRICITY_STOP);
            }
            stopClientEffectObjByLabel(target, "state_electricity");
        }
        else if (type.equals(DOT_KINETIC))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_ELECTRICITY_STOP);
            }
            stopClientEffectObjByLabel(target, "state_kinetic");
        }
        else if (type.equals(DOT_POISON))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_POISONED_STOP);
            }
            stopClientEffectObjByLabel(target, "state_poisoned");
        }
        else if (type.equals(DOT_BLEEDING))
        {
            if (verbose)
            {
                combat.sendCombatSpamMessage(target, SID_BLEEDING_STOP);
            }
            stopClientEffectObjByLabel(target, "state_bleeding");
        }
        return true;
    }
}
