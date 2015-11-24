package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.buff;
import script.library.dot;
import script.library.utils;
import script.library.vehicle;

public class grenade extends script.base_script
{
    public grenade()
    {
    }
    public static final string_id SID_THROW_DELAY = new string_id("grenade", "throw_delay");
    public static final string_id SID_THROW_CLASS_DELAY = new string_id("grenade", "throw_class_delay");
    public static boolean canThrowGrenade(obj_id thrower, obj_id grenade, boolean verbose) throws InterruptedException
    {
        if (!combat.hasCertification(thrower, grenade, verbose))
        {
            sendSystemMessage(thrower, new string_id("spam", "no_cert_grenade"));
            return false;
        }
        return true;
    }
    public static boolean hasThrownGrenade(obj_id thrower, obj_id grenade) throws InterruptedException
    {
        String grenadeEffectClass = getStringObjVar(grenade, "effect_class");
        return true;
    }
    public static effect_data applyGrenadeEffects(obj_id defender, obj_id grenade, float range) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "APPLYING GRENADE EFFECTS ON " + getName(defender) + "(" + defender + ")" + " WITH " + getName(grenade) + "(" + grenade + ")");
        effect_data resultData = new effect_data();
        int currentPosture = getPosture(defender);
        resultData.posture = currentPosture;
        String grenadeEffectClass = getStringObjVar(grenade, "effect_class");
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying grenade effects of class [" + grenadeEffectClass + "] to " + defender);
        if (grenadeEffectClass.equals("fragmentation"))
        {
            resultData = applyFragmentationEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("cryoban"))
        {
            resultData = applyCryobanEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("glop"))
        {
            resultData = applyGlopEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("imperial_detonator"))
        {
            resultData = applyImperialDetonatorEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("proton"))
        {
            resultData = applyProtonEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("thermal_detonator"))
        {
            resultData = applyThermalDetonatorEffects(defender, grenade, range, resultData);
        }
        else if (grenadeEffectClass.equals("bug"))
        {
            resultData = applyBugBombEffects(defender, grenade, range, resultData);
        }
        else 
        {
        }
        if (resultData.posture != currentPosture)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Recommending a new posture of " + resultData.posture + " be set on " + defender);
        }
        return resultData;
    }
    public static effect_data applyFragmentationEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying fragmentation effects.");
        return resultData;
    }
    public static effect_data applyCryobanEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying cryoban effects.");
        float maxRange = getMaxRange(grenade);
        if (range > maxRange)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range. (snare)");
            return resultData;
        }
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is snared.");
        buff.applyBuff(defender, "cryobanSnare");
        float midRange = (getMaxRange(grenade) - getMinRange(grenade)) / 2;
        if (range > midRange)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range. (slow)");
            return resultData;
        }
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is combat slowed.");
        int slowDuration = getIntObjVar(grenade, "slowDuration");
        int slowIntensity = getIntObjVar(grenade, "slowIntensity");
        addSkillModModifier(defender, "cryobanSlow", "combat_slow", slowIntensity, slowDuration, false, true);
        return resultData;
    }
    public static effect_data applyGlopEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying glop effects.");
        float midRange = (getMaxRange(grenade) - getMinRange(grenade)) / 2;
        if (range > midRange)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range.");
            return resultData;
        }
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Blinding the target: " + defender);
        resultData.states = new int[1];
        resultData.durations = new float[1];
        resultData.states[0] = STATE_BLINDED;
        resultData.durations[0] = getIntObjVar(grenade, "blindDuration");
        resultData.stateChance = getIntObjVar(grenade, "blindChance");
        return resultData;
    }
    public static effect_data applyImperialDetonatorEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying imperial detonator effects.");
        if (range > 20)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range.");
            return resultData;
        }
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Knocking down the target: " + defender);
        resultData.posture = POSTURE_KNOCKED_DOWN;
        return resultData;
    }
    public static effect_data applyProtonEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying proton effects.");
        if (range > 30)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range. (vehicle disable)");
            return resultData;
        }
        if (vehicle.isRidingVehicle(defender) && rand(1, 100) <= 50)
        {
            obj_id objVehicle = getMountId(defender);
            vehicle.doTempMaxSpeedReduction(objVehicle, 0.1f, 30);
            sendSystemMessage(defender, new string_id("cbt_spam", "vehicle_slow_lightning"));
        }
        if (range > 20)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range. (DOT)");
            return resultData;
        }
        int burnIntensity = getIntObjVar(grenade, "burnIntensity");
        int burnDuration = getIntObjVar(grenade, "burnDuration");
        dot.applyBleedingEffect(defender, grenade, "protonBurn", HEALTH, burnIntensity, burnDuration);
        return resultData;
    }
    public static effect_data applyThermalDetonatorEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying thermal detonator effects.");
        if (range > 20)
        {
            combat.combatLog(grenade, defender, "applyGrenadeEffects", "Target is out of special effect range. (DOT)");
            return resultData;
        }
        int burnIntensity = getIntObjVar(grenade, "burnIntensity");
        int burnDuration = getIntObjVar(grenade, "burnDuration");
        dot.applyFireEffect(defender, grenade, "thermalBurn", burnIntensity, burnDuration);
        return resultData;
    }
    public static effect_data applyBugBombEffects(obj_id defender, obj_id grenade, float range, effect_data resultData) throws InterruptedException
    {
        combat.combatLog(grenade, defender, "applyGrenadeEffects", "Applying bug bomb effects.");
        return resultData;
    }
}
