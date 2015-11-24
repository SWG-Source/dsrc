package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.trace;
import script.library.combat;
import script.library.vehicle;
import script.library.dot;
import script.library.ai_lib;

public class heavyweapons extends script.base_script
{
    public heavyweapons()
    {
    }
    public static final int TYPE_LAUNCHER_PISTOL = 1;
    public static final int TYPE_ROCKET_LAUNCHER = 2;
    public static final int TYPE_FLAME_THROWER = 3;
    public static final int TYPE_HEAVY_PARTICLE_BEAM = 4;
    public static final int TYPE_ACID_RIFLE = 5;
    public static final int TYPE_HEAVY_ACID_BEAM = 6;
    public static final int TYPE_LIGHTNING_BEAM_CANON = 7;
    public static final int TYPE_PROTON_RIFLE = 8;
    public static final int TYPE_HEAVY_FLAME_THROWER = 9;
    public static final int TYPE_LAVA_CANNON = 10;
    public static final int TYPE_MAX = 11;
    public static final float LAUNCHER_PISTOL_AOE_RADIUS = 10.0f;
    public static final float HVY_ACID_BEAM_AOE_RADIUS = 15.0f;
    public static final int HVY_ACID_BEAM_AOE_CHANCE = 25;
    public static final float HVY_ACID_BEAM_BLIND_DURATION = 20.0f;
    public static final float HVY_LIGHTNING_BEAM_AOE_RADIUS = 10.0f;
    public static final float FLAME_THROWER_CONE_ANGLE = 20.0f;
    public static final float HEAVY_FLAME_THROWER_CONE_ANGLE = 25.0f;
    public static final float LAVA_CANNON_AOE_RADIUS = 5.0f;
    public static final int FLAME_THROWER_DOT_CHANCE = 30;
    public static final float PARTICLE_BEAM_CONE_ANGLE = 1.0f;
    public static final int ACID_RIFLE_PUNISHMENT_CHANCE = 40;
    public static final float ACID_RIFLE_PUNISHMENT_DURATION = 15.0f;
    public static final int ACID_RIFLE_MID_BLIND_CHANCE = 40;
    public static final int ACID_RIFLE_MAX_BLIND_CHANCE = 20;
    public static final int LIGHTNING_BEAM_CANON_DOT_CHANCE = 20;
    public static final int LIGHTNING_BEAM_VEHICLE_SLOW_CHANCE = 100;
    public static final float LIGHTNING_BEAM_VEHICLE_SLOW_DURATION = 20.0f;
    public static final float LIGHTNING_BEAM_VEHICLE_SLOW_FACTOR = 0.1f;
    public static final float FOCUS_FIRE_CONE_REDUCTION = .75f;
    public static final float DEAD_BANG_RANGE_REDUCTION = .85f;
    public static final float DEMOLITION_DAM_MULTIPLIER = 1.4f;
    public static final float DEMOLITION_1_DAM_MULTIPLIER = 1.6f;
    public static final float DEMOLITION_2_DAM_MULTIPLIER = 1.8f;
    public static final String ATTACK_NAME_BASE_SINGLE = "co_hw_dot_";
    public static final String ATTACK_NAME_BASE_AREA = "co_ae_hw_dot_";
    public static int getHeavyWeaponType(obj_id weapon) throws InterruptedException
    {
        if (!hasObjVar(weapon, "intWeaponType"))
        {
            return -1;
        }
        int type = getIntObjVar(weapon, "intWeaponType");
        if (type >= TYPE_MAX)
        {
            return -1;
        }
        return type;
    }
    public static boolean fillHeavyWeaponData(weapon_data dat) throws InterruptedException
    {
        int weaponType = getHeavyWeaponType(dat.id);
        if (weaponType == -1)
        {
            return false;
        }
        float AOEDamagePercent = 0.0f;
        if (hasObjVar(dat.id, "intAOEDamagePercent"))
        {
            AOEDamagePercent = getFloatObjVar(dat.id, "intAOEDamagePercent");
        }
        if (AOEDamagePercent > 1.0f)
        {
            AOEDamagePercent = 1.0f;
        }
        else if (AOEDamagePercent < 0.0f)
        {
            AOEDamagePercent = 0.0f;
        }
        dat.hvyWeaponType = weaponType;
        dat.hvyWeaponAeDamageMod = AOEDamagePercent;
        return true;
    }
    public static boolean canFireHeavyWeapon(obj_id player, weapon_data weapon) throws InterruptedException
    {
        if (cybernetic.hasCommandoLegs(player))
        {
            return true;
        }
        return true;
    }
    public static float getDemolitionShotDamageMod(obj_id attacker, obj_id defender, String actionName) throws InterruptedException
    {
        float mult = 1.05f;
        if (isPlayer(defender) || ai_lib.isNpc(defender) || ai_lib.isAnimal(defender))
        {
            return mult;
        }
        actionName = toLower(actionName);
        if (actionName.equals("demolitionshot_2"))
        {
            mult = DEMOLITION_2_DAM_MULTIPLIER;
        }
        else if (actionName.equals("demolitionshot"))
        {
            mult = DEMOLITION_DAM_MULTIPLIER;
        }
        else if (actionName.equals("demolitionshot_1"))
        {
            mult = DEMOLITION_1_DAM_MULTIPLIER;
        }
        return mult;
    }
    public static obj_id[] getAllHeavyWeaponTargets(obj_id attacker, obj_id defender, weapon_data weapon, dictionary actionData) throws InterruptedException
    {
        float rangeModifier = 1f;
        float areaModifier = 1f;
        String actionName = toLower(actionData.getString("actionName"));
        if (actionName != null && actionName.length() > 0)
        {
            if (actionName.startsWith("demolition") || actionName.startsWith("overkill"))
            {
                return new obj_id[]
                {
                    defender
                };
            }
            if (actionName.startsWith("deadbang"))
            {
                rangeModifier = DEAD_BANG_RANGE_REDUCTION;
            }
            else if (actionName.startsWith("focusfire"))
            {
                areaModifier = FOCUS_FIRE_CONE_REDUCTION;
            }
        }
        obj_id[] defenders = null;
        switch (weapon.hvyWeaponType)
        {
            case TYPE_LAUNCHER_PISTOL:
            defenders = pvpGetTargetsInRange(attacker, defender, LAUNCHER_PISTOL_AOE_RADIUS * areaModifier);
            break;
            case TYPE_LAVA_CANNON:
            defenders = pvpGetTargetsInRange(attacker, defender, LAVA_CANNON_AOE_RADIUS * areaModifier);
            break;
            case TYPE_FLAME_THROWER:
            defenders = pvpGetTargetsInCone(attacker, attacker, defender, weapon.maxRange * rangeModifier, FLAME_THROWER_CONE_ANGLE * areaModifier);
            break;
            case TYPE_HEAVY_FLAME_THROWER:
            defenders = pvpGetTargetsInCone(attacker, attacker, defender, weapon.maxRange * rangeModifier, HEAVY_FLAME_THROWER_CONE_ANGLE * areaModifier);
            break;
            case TYPE_HEAVY_PARTICLE_BEAM:
            defenders = pvpGetTargetsInCone(attacker, attacker, defender, weapon.maxRange * rangeModifier, PARTICLE_BEAM_CONE_ANGLE * areaModifier);
            if (defenders != null && defenders.length > 1)
            {
                obj_id temp = defenders[0];
                defenders[0] = defenders[defenders.length - 1];
                defenders[defenders.length - 1] = temp;
            }
            break;
            case TYPE_HEAVY_ACID_BEAM:
            defenders = pvpGetTargetsInRange(attacker, defender, HVY_ACID_BEAM_AOE_RADIUS * areaModifier);
            break;
            case TYPE_LIGHTNING_BEAM_CANON:
            defenders = pvpGetTargetsInRange(attacker, defender, HVY_LIGHTNING_BEAM_AOE_RADIUS * areaModifier);
            break;
            default:
            defenders = new obj_id[]
            {
                defender
            };
            break;
        }
        return defenders;
    }
    public static void finalizeHeavyWeaponAttack(attacker_data attacker, defender_data[] defenders, weapon_data weapon) throws InterruptedException
    {
        effect_data dat = new effect_data();
        defender_data defender = defenders[0];
        switch (weapon.hvyWeaponType)
        {
            case TYPE_ACID_RIFLE:
            float distance = getDistance(attacker.id, defender.id);
            if (distance > (weapon.maxRange - weapon.minRange) / 2)
            {
                return;
            }
            if (rand(1, 100) <= ACID_RIFLE_PUNISHMENT_CHANCE)
            {
                sendSystemMessage(attacker.id, new string_id("cbt_spam", "acid_rifle_selfblind"));
            }
            break;
        }
        return;
    }
    public static effect_data doHeavyWeaponDefenderEffect(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        effect_data rslt = new effect_data();
        switch (weapon.hvyWeaponType)
        {
            case TYPE_LAUNCHER_PISTOL:
            rslt = _doLauncherPistolAttack(attacker, defender, weapon);
            break;
            case TYPE_ROCKET_LAUNCHER:
            rslt = _doRocketLauncherAttack(attacker, defender, weapon);
            break;
            case TYPE_FLAME_THROWER:
            case TYPE_HEAVY_FLAME_THROWER:
            rslt = _doFlameThrowerAttack(attacker, defender, weapon);
            break;
            case TYPE_LAVA_CANNON:
            rslt = _doLavaCannonAttack(attacker, defender, weapon);
            break;
            case TYPE_HEAVY_PARTICLE_BEAM:
            rslt = _doHeavyParticleBeamAttack(attacker, defender, weapon);
            break;
            case TYPE_ACID_RIFLE:
            rslt = _doAcidRifleAttack(attacker, defender, weapon);
            break;
            case TYPE_HEAVY_ACID_BEAM:
            rslt = _doHeavyAcidBeamAttack(attacker, defender, weapon);
            break;
            case TYPE_LIGHTNING_BEAM_CANON:
            rslt = _doLightningBeamCanonAttack(attacker, defender, weapon);
            break;
        }
        return rslt;
    }
    public static effect_data _doLauncherPistolAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        return new effect_data();
    }
    public static effect_data _doLavaCannonAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        return new effect_data();
    }
    public static effect_data _doRocketLauncherAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        effect_data dat = new effect_data();
        dat.posture = POSTURE_KNOCKED_DOWN;
        return dat;
    }
    public static effect_data _doFlameThrowerAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        if (rand(1, 100) <= FLAME_THROWER_DOT_CHANCE)
        {
            _applyHealthStatFireDot(attacker, defender, weapon);
        }
        return new effect_data();
    }
    public static effect_data _doHeavyParticleBeamAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        return new effect_data();
    }
    public static effect_data _doAcidRifleAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        effect_data dat = new effect_data();
        dat.states = new int[]
        {
            STATE_BLINDED
        };
        dat.durations = new float[]
        {
            ACID_RIFLE_PUNISHMENT_DURATION
        };
        float distance = getDistance(defender.id, attacker.id);
        if (distance <= (weapon.maxRange - weapon.minRange) / 2)
        {
            dat.stateChance = ACID_RIFLE_MID_BLIND_CHANCE;
        }
        else 
        {
            dat.stateChance = ACID_RIFLE_MAX_BLIND_CHANCE;
        }
        return dat;
    }
    public static effect_data _doHeavyAcidBeamAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        effect_data dat = new effect_data();
        dat.states = new int[]
        {
            STATE_BLINDED
        };
        dat.durations = new float[]
        {
            HVY_ACID_BEAM_BLIND_DURATION
        };
        dat.stateChance = HVY_ACID_BEAM_AOE_CHANCE;
        return dat;
    }
    public static effect_data _doLightningBeamCanonAttack(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        if (rand(1, 100) <= LIGHTNING_BEAM_CANON_DOT_CHANCE)
        {
            _applyHealthStatFireDot(attacker, defender, weapon);
            if (vehicle.isRidingVehicle(defender.id) && rand(1, 100) <= LIGHTNING_BEAM_VEHICLE_SLOW_CHANCE)
            {
                obj_id objVehicle = getMountId(defender.id);
                vehicle.doTempMaxSpeedReduction(objVehicle, LIGHTNING_BEAM_VEHICLE_SLOW_FACTOR, LIGHTNING_BEAM_VEHICLE_SLOW_DURATION);
                sendSystemMessage(defender.id, new string_id("cbt_spam", "vehicle_slow_lightning"));
            }
        }
        return new effect_data();
    }
    public static void _applyHealthStatFireDot(attacker_data attacker, defender_data defender, weapon_data weapon) throws InterruptedException
    {
        int duration = 0;
        int potency = 0;
        int strength = 0;
        switch (weapon.hvyWeaponType)
        {
            case TYPE_FLAME_THROWER:
            case TYPE_LIGHTNING_BEAM_CANON:
            
            {
                duration = rand(15, 45);
                potency = rand(125, 225);
                strength = rand(200, 400);
            }
            break;
            case TYPE_HEAVY_FLAME_THROWER:
            
            {
                duration = rand(15, 60);
                potency = rand(150, 275);
                strength = rand(300, 500);
            }
            break;
        }
        dot.applyDotEffect(defender.id, attacker.id, dot.DOT_FIRE, "hvyWeaponDot_" + HEALTH + "_" + attacker.id, HEALTH, potency, strength, duration, true, null);
    }
    public static String getHeavyWeaponDotName(obj_id player, obj_id weapon, boolean singleTarget) throws InterruptedException
    {
        int elementalDamageType = getWeaponElementalType(weapon);
        return getHeavyWeaponDotName(player, elementalDamageType, singleTarget);
    }
    public static String getHeavyWeaponDotName(obj_id player, weapon_data weaponData, boolean singleTarget) throws InterruptedException
    {
        int elementalDamageType = weaponData.elementalType;
        return getHeavyWeaponDotName(player, elementalDamageType, singleTarget);
    }
    public static String getHeavyWeaponDotName(obj_id player, int elementalDamageType, boolean singleTarget) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        if (elementalDamageType <= 0)
        {
            return null;
        }
        String attackNameBase = "";
        String attackNameType = "";
        String attackNameLevel = "";
        String finalAttackName = "";
        if (singleTarget)
        {
            attackNameBase = ATTACK_NAME_BASE_SINGLE;
        }
        else 
        {
            attackNameBase = ATTACK_NAME_BASE_AREA;
        }
        switch (elementalDamageType)
        {
            case DAMAGE_ENERGY:
            attackNameType = "energy_";
            break;
            case DAMAGE_KINETIC:
            attackNameType = "kinetic_";
            break;
            case DAMAGE_ELEMENTAL_HEAT:
            attackNameType = "fire_";
            break;
            case DAMAGE_ELEMENTAL_COLD:
            attackNameType = "cold_";
            break;
            case DAMAGE_ELEMENTAL_ACID:
            attackNameType = "acid_";
            break;
            case DAMAGE_ELEMENTAL_ELECTRICAL:
            attackNameType = "electrical_";
            break;
            default:
            break;
        }
        if (playerLevel == 90)
        {
            attackNameLevel = "5";
        }
        else if (playerLevel >= 80)
        {
            attackNameLevel = "4";
        }
        else if (playerLevel >= 70)
        {
            attackNameLevel = "3";
        }
        else if (playerLevel >= 60)
        {
            attackNameLevel = "2";
        }
        else if (playerLevel >= 50)
        {
            attackNameLevel = "1";
        }
        finalAttackName = attackNameBase + attackNameType + attackNameLevel;
        return finalAttackName;
    }
}
