package script.library;

import script.*;
import script.combat_engine.*;

import java.util.Vector;

public class combat extends script.base_script
{
    public combat()
    {
    }
    public static final float IN_COMBAT_REGEN_MULT = 1.0f;
    public static final float OUT_COMBAT_REGEN_MULT = 4.0f;
    public static final int PLAYER_COMBAT_BASE_DAMAGE = 60;
    public static final int PLAYER_ATTACKER_DAMAGE_LEVEL_MULTIPLIER = 5;
    public static final int RANGED_WEAPON = -1;
    public static final int MELEE_WEAPON = -2;
    public static final int ALL_WEAPONS = -3;
    public static final int ALL_LIGHTSABERS = -4;
    public static final int FORCE_POWER = -5;
    public static final int ALL_BUT_HEAVY = -6;
    public static final int WEAPON_TYPE_FORCE_POWER = -1;
    public static final int LEFT_CLICK_DEFAULT = 1;
    public static final int RIGHT_CLICK_SPECIAL = 2;
    public static final int B_RIFLE = 0x00000001;
    public static final int B_CARBINE = 0x00000002;
    public static final int B_PISTOL = 0x00000004;
    public static final int B_HEAVY = 0x00000008;
    public static final int B_1HAND_MELEE = 0x00000010;
    public static final int B_2HAND_MELEE = 0x00000020;
    public static final int B_UNARMED = 0x00000040;
    public static final int B_POLEARM = 0x00000080;
    public static final int B_THROWN = 0x00000100;
    public static final int B_1HAND_LIGHTSABER = 0x00000200;
    public static final int B_2HAND_LIGHTSABER = 0x00000400;
    public static final int B_POLEARM_LIGHTSABER = 0x00000800;
    public static final int B_GROUND_TARGETTING = 0x00001000;
    public static final int B_DIRECTIONAL = 0x00002000;
    public static final int B_RANGED = 0x08000000;
    public static final int B_MELEE = 0x10000000;
    public static final int B_ALL = 0x20000000;
    public static final int B_ALL_LIGHTSABERS = 0x40000000;
    public static final int[] weaponTypeToBitValueMap = 
    {
        B_ALL | B_RANGED | B_RIFLE,
        B_ALL | B_RANGED | B_CARBINE,
        B_ALL | B_RANGED | B_PISTOL,
        B_ALL | B_RANGED | B_HEAVY,
        B_ALL | B_MELEE | B_1HAND_MELEE,
        B_ALL | B_MELEE | B_2HAND_MELEE,
        B_ALL | B_MELEE | B_UNARMED,
        B_ALL | B_MELEE | B_POLEARM,
        B_ALL | B_RANGED | B_THROWN,
        B_ALL | B_ALL_LIGHTSABERS | B_1HAND_LIGHTSABER,
        B_ALL | B_ALL_LIGHTSABERS | B_2HAND_LIGHTSABER,
        B_ALL | B_ALL_LIGHTSABERS | B_POLEARM_LIGHTSABER,
        B_ALL | B_RANGED | B_HEAVY | B_GROUND_TARGETTING,
        B_ALL | B_RANGED | B_HEAVY | B_DIRECTIONAL
    };
    public static final int EFFECT_MAX_DURATION = 180;
    public static final float DELAY_EFFECT_TIMER = 30f;
    public static final int TKA_JEDI_ARMOR_HARD_CAP = 9000;
    public static final int MAX_RE_EXOTIC_ELEMENTAL_PENETRATION_SKILLMOD = 10;
    public static final int MOVEMENT_MODIFIER_REUSE_TIME = 30;
    public static final int AI_LEVEL_ACCURACY_BONUS = 65;
    public static final int AI_LEVEL_DEFENSE_BONUS = 65;
    public static final int AI_LEVEL_DAMAGE_BONUS = 40;
    public static final int AI_LEVEL_DAMAGE_PENALTY = 25;
    public static final int AI_LEVEL_MIN_DAMAGE_CAP = 50;
    public static final int AI_LEVEL_MAX_DAMAGE_CAP = 75;
    public static final float AI_CON_SCALE_MULT = 8f;
    public static final int VALID_TARGET_NONE = -1;
    public static final int VALID_TARGET_STANDARD = 0;
    public static final int VALID_TARGET_MOB = 1;
    public static final int VALID_TARGET_CREATURE = 2;
    public static final int VALID_TARGET_NPC = 3;
    public static final int VALID_TARGET_DROID = 4;
    public static final int VALID_TARGET_PVP = 5;
    public static final int VALID_TARGET_JEDI = 6;
    public static final int VALID_TARGET_DEAD = 7;
    public static final int VALID_TARGET_FRIEND = 8;
    public static final int NON_ATTACK = 0;
    public static final int NON_DAMAGE_ATTACK = 4;
    public static final int HEAL = 5;
    public static final int DELAY_ATTACK = 6;
    public static final int REVIVE = 7;
    public static final int ACTION_NAME = 0;
    public static final int WEAPON_NAME = 1;
    public static final int NO_ATTACK_NAME = 2;
    public static final int NO_HIT_SPAM = 3;
    public static final float ARMOR_BREAK_REDUCTION = 0.5f;
    public static final String ATTACKER_DATA = "cached_attacker_data";
    public static final String ATTACKER_BASE_CRIT = ATTACKER_DATA + ".base_crit";
    public static final String ATTACKER_PVP_CRIT = ATTACKER_DATA + ".pvp_crit";
    public static final String ATTACKER_DROID_CRIT = ATTACKER_DATA + ".droid_crit";
    public static final String ATTACKER_NPC_CRIT = ATTACKER_DATA + ".npc_crit";
    public static final String ATTACKER_CREATURE_CRIT = ATTACKER_DATA + ".creature_crit";
    public static final String ATTACKER_HIT_BONUS = ATTACKER_DATA + ".hit_bonus";
    public static final String ATTACKER_STRIKETHROUGH = ATTACKER_DATA + ".strikethrough_chance";
    public static final String ATTACKER_REDUCE_PARRY = ATTACKER_DATA + ".reduce_parry";
    public static final String ATTACKER_REDUCE_BLOCK = ATTACKER_DATA + ".reudce_block";
    public static final String ATTACKER_REDUCE_DODGE = ATTACKER_DATA + ".reduce_dodge";
    public static final String ATTACKER_REDUCE_GLANCING = ATTACKER_DATA + ".reduce_glancing";
    public static final String ATTACKER_REDUCE_EVADE = ATTACKER_DATA + ".reduce_evade";
    public static final String DEFENDER_DATA = "cached_defender_data";
    public static final String DEFENDER_REDUCE_CRIT = DEFENDER_DATA + ".crit_reduce";
    public static final String DEFENDER_INCREASE_MISS = DEFENDER_DATA + ".increase_miss";
    public static final String DEFENDER_REDUCE_STRIKETHROUGH = DEFENDER_DATA + ".reduce_strikethrough";
    public static final String DEFENDER_REDUCE_PUNISH = DEFENDER_DATA + ".reduce_punish";
    public static final String DEFENDER_GLANCING = DEFENDER_DATA + ".increase_glancing";
    public static final String DEFENDER_PARRY = DEFENDER_DATA + ".parry_chance";
    public static final String DEFENDER_DODGE = DEFENDER_DATA + ".dodge_chance";
    public static final String DEFENDER_EVADE = DEFENDER_DATA + ".evade_chance";
    public static final String DEFENDER_BLOCK = DEFENDER_DATA + ".block_chance";
    public static final String[][] ARMOR_SLOT_LOCATIONS = 
    {
        
        {
            "chest2",
            "chest2"
        },
        
        {
            "hat",
            "hat"
        },
        
        {
            "bicep_r",
            "bracer_upper_r"
        },
        
        {
            "bicep_l",
            "bracer_upper_l"
        },
        
        {
            "pants2",
            "pants2"
        },
        
        {
            "pants2",
            "pants2"
        }
    };
    public static final String PSG_SLOT_LOCATION = "utility_belt";
    public static final float PSG_BASE_DMG_PER_HIT = 50.0f;
    public static final float PSG_DAMAGE_SCALE = 200.0f;
    public static final int CONE = 0;
    public static final int SINGLE_TARGET = 1;
    public static final int AREA = 2;
    public static final int TARGET_AREA = 3;
    public static final int DUAL_WIELD = 4;
    public static final int RAMPAGE = 5;
    public static final int RANDOM_HATE_TARGET = 6;
    public static final int RANDOM_HATE_TARGET_CONE = 7;
    public static final int RANDOM_HATE_TARGET_CONE_TERMINUS = 8;
    public static final int HATE_LIST = 9;
    public static final int RANDOM_HATE_MULTI = 10;
    public static final int AREA_PROGRESSIVE = 11;
    public static final int SPLIT_DAMAGE_TARGET_AREA = 12;
    public static final int DISTANCE_FARTHEST = 13;
    public static final String NONCOMBAT_DATATABLE = "datatables/combat/non_combat_data.iff";
    public static final String WEAPON_LEVEL_TABLE = "datatables/combat/weapon_level.iff";
    public static final int[] RANGED_WEAPONS = 
    {
        WEAPON_TYPE_RIFLE,
        WEAPON_TYPE_LIGHT_RIFLE,
        WEAPON_TYPE_PISTOL,
        WEAPON_TYPE_HEAVY
    };
    public static final int[] MELEE_WEAPONS = 
    {
        WEAPON_TYPE_1HAND_MELEE,
        WEAPON_TYPE_2HAND_MELEE,
        WEAPON_TYPE_UNARMED,
        WEAPON_TYPE_POLEARM,
        WEAPON_TYPE_WT_1HAND_LIGHTSABER,
        WEAPON_TYPE_WT_2HAND_LIGHTSABER,
        WEAPON_TYPE_WT_POLEARM_LIGHTSABER
    };
    public static final String VAR_VOLLEY_GROUPS = "squadleader.volleyGroups";
    public static final String ID_VOLLEY_FIRE_PARTICLE = "squadleader.volleyFire";
    public static final int VOLLEY_INTERVAL = 30;
    public static final String VAR_GROUP_VOLLEY_TARGET = "squadleader.curVolleyTarget";
    public static final String VAR_GROUP_LAST_VOLLEY_TIME = "squadleader.lastVolleyTime";
    public static final string_id SID_NONE = new string_id();
    public static final String CHARGE_TARGET = "charge_target";
    public static final String DIED_RECENTLY = "combat.died_recently";
    public static final float RECENT_DEATH_EXPIRATION = 60.0f;
    public static final String DAMAGE_REDIRECT = "damage_redirect";
    public static final String DO_ONCE_ATTACK_OVERRIDE = "default.do_once_attack_override";
    public static final String DEFAULT_ATTACK_OVERRIDE = "default.default_attack_override";
    public static final int FS_TAUNT_HATE_INCREASE_LOW = 750;
    public static final int FS_TAUNT_HATE_INCREASE_MID = 1200;
    public static final int FS_TAUNT_HATE_INCREASE_HIGH = 2000;
    public static final int FS_TAUNT_LOW_LEVEL = 64;
    public static final int FS_TAUNT_MID_LEVEL = 77;
    public static final int BH_TAUNT_HATE_INCREASE_LOW = 300;
    public static final int BH_TAUNT_HATE_INCREASE_MID = 750;
    public static final int BH_TAUNT_HATE_INCREASE_HIGH = 1200;
    public static final int BH_TAUNT_LOW_LEVEL = 43;
    public static final int BH_TAUNT_MID_LEVEL = 66;
    public static final String TEMP_COMBAT_BLOCK = "combat_debuff.all_actions_blocked";
    public static final String PERSIST_COMBAT = "persist_combat";
    public static final String BUFF_HATE_XFER = "aggroChannelself";
    public static final int EGG_AURA_TRIGGER_MIN_RANGE = 15;
    public static final int EGG_AURA_TRIGGER_MAX_RANGE = 100;
    public static final int EGG_AURA_DURATION = 20;
    public static final int DR_ATTACKER_CRITICAL = 0;
    public static final int DR_ATTACKER_STRIKETHROUGH = 1;
    public static final int DR_DEFENDER_BLOCK = 2;
    public static final int DR_DEFENDER_DODGE = 3;
    public static final int DR_DEFENDER_PARRY = 4;
    public static final int DR_DEFENDER_GLANCE = 5;
    public static final int DR_DEFENDER_EVADE = 6;
    public static final int DR_ATTACKER_CRITICAL_2 = 7;
    public static void sendCombatSpam(obj_id attacker, obj_id defender, obj_id weapon, combat_engine.hit_result result, string_id attackName, boolean sendToAttacker, boolean sendToDefender, boolean sendToBystanders) throws InterruptedException
    {
        sendCombatSpam(attacker, defender, weapon, result, attackName, sendToAttacker, sendToDefender, sendToBystanders, COMBAT_RESULT_GENERIC);
    }
    public static void sendCombatSpam(obj_id attacker, obj_id defender, string_id weapon, combat_engine.hit_result result, string_id attackName, boolean sendToAttacker, boolean sendToDefender, boolean sendToBystanders) throws InterruptedException
    {
        sendCombatSpam(attacker, defender, weapon, result, attackName, sendToAttacker, sendToDefender, sendToBystanders, COMBAT_RESULT_GENERIC);
    }
    public static void sendCombatSpamMessage(obj_id player, string_id message) throws InterruptedException
    {
        sendCombatSpamMessage(player, message, COMBAT_RESULT_GENERIC);
    }
    public static void sendCombatSpamMessage(obj_id attacker, obj_id defender, string_id message, boolean sendToAttacker, boolean sendToDefender, boolean sendToBystanders) throws InterruptedException
    {
        sendCombatSpamMessage(attacker, defender, message, sendToAttacker, sendToDefender, sendToBystanders, COMBAT_RESULT_GENERIC);
    }
    public static void sendCombatSpamMessageProse(obj_id player, prose_package pp) throws InterruptedException
    {
        sendCombatSpamMessageProse(player, pp, COMBAT_RESULT_GENERIC);
    }
    public static void sendCombatSpamMessageProse(obj_id attacker, obj_id defender, prose_package pp, boolean sendToAttacker, boolean sendToDefender, boolean sendToBystanders) throws InterruptedException
    {
        sendCombatSpamMessageProse(attacker, defender, pp, sendToAttacker, sendToDefender, sendToBystanders, COMBAT_RESULT_GENERIC);
    }
    public static void doBasicCombatSpam(String attackName, attacker_results attackerResults, defender_results[] defenderResults, hit_result[] hitData) throws InterruptedException
    {
        string_id attackId = new string_id("cmd_n", attackName);
        doBasicCombatSpam(attackId, SID_NONE, null, attackerResults, defenderResults, hitData);
    }
    public static void doBasicCombatSpam(string_id attackId, string_id weaponId, obj_id weapon, attacker_results attackerResults, defender_results[] defenderResults, hit_result[] hitData) throws InterruptedException
    {
        if (isIdValid(weapon))
        {
            for (int i = 0; i < defenderResults.length; ++i)
            {
                sendCombatSpam(attackerResults.id, defenderResults[i].id, weapon, hitData[i], attackId, true, true, true, defenderResults[i].result);
            }
        }
        else 
        {
            for (int i = 0; i < defenderResults.length; ++i)
            {
                sendCombatSpam(attackerResults.id, defenderResults[i].id, weaponId, hitData[i], attackId, true, true, true, defenderResults[i].result);
            }
        }
    }
    public static boolean isAreaAttack(int attackType) throws InterruptedException
    {
        if (attackType == CONE || attackType == AREA || attackType == TARGET_AREA)
        {
            return true;
        }
        return false;
    }
    public static boolean breakMez(obj_id defender) throws InterruptedException
    {
        int[] buffs = buff.getAllBuffs(defender);
        if (buffs == null || buffs.length == 0)
        {
            return true;
        }
        for (int i = 0; i < buffs.length; i++)
        {
            for (int j = 1; j <= buff.MAX_EFFECTS; j++)
            {
                String effect = buff.getEffectParam(buffs[i], j);
                if (effect.equals("delay_attack"))
                {
                    buff.removeBuff(defender, buffs[i]);
                }
            }
        }
        return true;
    }
    public static int getAiLevelDiff(obj_id attacker, obj_id defender) throws InterruptedException
    {
        obj_id master = null;
        if (isPlayer(attacker))
        {
            return 0;
        }
        if (!isPlayer(defender) && !isMob(defender))
        {
            return 0;
        }
        if (isMob(attacker))
        {
            master = getMaster(attacker);
            if (!isPlayer(attacker) && isIdValid(master))
            {
                return 0;
            }
        }
        if (isMob(defender))
        {
            master = getMaster(defender);
            if (!isPlayer(defender) && !isIdValid(master))
            {
                return 0;
            }
        }
        int aiLevel = getLevel(attacker);
        int defenderLevel = getLevel(defender);
        if (!isMob(attacker))
        {
            aiLevel = getIntObjVar(attacker, "intCombatDifficulty");
        }
        obj_id defenderGroup = getGroupObject(defender);
        if (group.isGroupObject(defenderGroup))
        {
            defenderLevel = getGroupObjectLevel(defenderGroup);
        }
        int buffBonus = getEnhancedSkillStatisticModifier(defender, "private_relative_level_mod");
        defenderLevel += buffBonus;
        return (aiLevel - defenderLevel);
    }
    public static float getConBalanceScalar(int levelDiff) throws InterruptedException
    {
        float scale = (Math.abs(levelDiff) + AI_CON_SCALE_MULT) / (10f + AI_CON_SCALE_MULT);
        return scale;
    }
    public static int getWeaponDamage(obj_id objAttacker, obj_id objDefender, weapon_data cbtWeaponData, int intAttackerRoll, int intDefenderRoll) throws InterruptedException
    {
        int intFinalDamage = 0;
        float fltMinDamage = (float)cbtWeaponData.minDamage;
        float fltMaxDamage = (float)cbtWeaponData.maxDamage;
        if (!isPlayer(objAttacker))
        {
            if (intAttackerRoll > intDefenderRoll)
            {
                return getAiDamage(objAttacker, objDefender, fltMinDamage, fltMaxDamage);
            }
            else 
            {
                return (int)rand(fltMinDamage, fltMinDamage + (fltMaxDamage - fltMinDamage));
            }
        }
        if (intAttackerRoll > intDefenderRoll)
        {
            int intDamage = (int)rand(fltMinDamage + (fltMaxDamage - fltMinDamage), fltMaxDamage);
            return intDamage;
        }
        else 
        {
            int intDamage = (int)(rand(fltMinDamage, fltMinDamage + (fltMaxDamage - fltMinDamage)));
            return intDamage;
        }
    }
    public static int getAiDamage(obj_id attacker, obj_id defender, float minDamage, float maxDamage) throws InterruptedException
    {
        if (!isPlayer(attacker))
        {
            int levelDiff = getAiLevelDiff(attacker, defender);
            combatLog(attacker, defender, "modifyAiRawDamage", "AI Level diff = " + levelDiff);
            float levelDiffMod = levelDiff * getConBalanceScalar(levelDiff);
            if (levelDiffMod < 0)
            {
                float damageMod = 1f + (levelDiffMod / 10f);
                combatLog(attacker, defender, "modifyAiRawDamage", "AI Damage Mod = " + damageMod);
                float minCap = minDamage / 2f;
                float maxCap = maxDamage / 2f;
                combatLog(attacker, defender, "modifyAiRawDamage", "Min Cap = " + minCap);
                combatLog(attacker, defender, "modifyAiRawDamage", "Max Cap = " + maxCap);
                minDamage *= damageMod;
                maxDamage *= damageMod;
                if (minDamage < minCap)
                {
                    minDamage = minCap;
                }
                if (maxDamage < maxCap)
                {
                    maxDamage = maxCap;
                }
            }
            else 
            {
                float maxDmgMod = 1f + (levelDiffMod / 10f);
                combatLog(attacker, defender, "modifyAiRawDamage", "AI Max Damage Mod = " + maxDmgMod);
                maxDamage *= maxDmgMod;
                maxDamage += levelDiff;
                float minDmgMod = 1f + (levelDiffMod / (10f / levelDiff));
                combatLog(attacker, defender, "modifyAiRawDamage", "AI Min Damage Mod = " + minDmgMod);
                minDamage *= minDmgMod;
                float minCap = maxDamage / 2f;
                if (minDamage > minCap)
                {
                    minDamage = minCap;
                }
            }
            combatLog(attacker, defender, "modifyAiRawDamage", "New Min Damage = " + minDamage);
            combatLog(attacker, defender, "modifyAiRawDamage", "New Max Damage = " + maxDamage);
        }
        if (utils.checkConfigFlag("ScriptFlags", "e3Demo"))
        {
            if (isPlayer(defender))
            {
                int intCurrentHP = getHealth(defender);
                int intMaxHP = getMaxHealth(defender);
                if ((minDamage > intMaxHP) || (maxDamage > intMaxHP) || (intCurrentHP < (intMaxHP / 2)))
                {
                    minDamage = 1;
                    maxDamage = 5;
                }
            }
        }
        int intDamage = (int)rand(minDamage, maxDamage);
        return intDamage;
    }
    public static void assignDamageCredit(obj_id attacker, obj_id defender, weapon_data weaponData, int damage) throws InterruptedException
    {
        String xpType = xp.getWeaponXpType(weaponData.weaponType);
        if (!isPlayer(attacker))
        {
            if (pet_lib.isPet(attacker))
            {
                obj_id master = getMaster(attacker);
                if (ai_lib.isMonster(attacker) && isIdValid(master) && hasSkill(master, "outdoors_creaturehandler_novice"))
                {
                    xp.updateCombatXpList(defender, master, xp.CREATUREHANDLER, damage);
                    xp.updateCombatXpList(defender, attacker, xp.CREATUREHANDLER, damage);
                    return;
                }
                else if (isIdValid(master) && !beast_lib.isBeast(attacker))
                {
                    xp.updateCombatXpList(defender, master, xp.PET_DAMAGE, damage);
                    xp.updateCombatXpList(defender, attacker, xpType, damage);
                    return;
                }
            }
        }
        xp.updateCombatXpList(defender, attacker, xpType, damage);
    }
    public static boolean expertiseRandomBuffChance(obj_id attacker, combat_data actionData) throws InterruptedException
    {
        String specialLine = actionData.specialLine;
        if (getSkillStatisticModifier(attacker, "expertise_use_buff_chance_line_" + specialLine) > 0 || getSkillStatisticModifier(attacker, "private_use_buff_chance_line_" + specialLine) > 0)
        {
            int chance = getSkillStatisticModifier(attacker, "expertise_buff_chance_line_" + specialLine);
            if (rand(0, 99) > chance)
            {
                return false;
            }
        }
        return true;
    }
    public static boolean applyDefenderCombatBuffs(obj_id attacker, obj_id defender, weapon_data weaponData, combat_data actionData) throws InterruptedException
    {
        String actionName = actionData.actionName;
        String buffName = actionData.buffNameTarget;
        float buffStrength = actionData.buffStrengthTarget;
        float buffDuration = actionData.buffDurationTarget;
        if (buffName == null || buffName.equals(""))
        {
            return false;
        }
        if (!expertiseRandomBuffChance(attacker, actionData))
        {
            return false;
        }
        combatLog(attacker, defender, "applyDefenderCombatBuffs", "Applying defender buff - " + buffName);
        String effectName = buff.getEffectParam(buffName, 2);
        if (effectName.equals("dot"))
        {
            effectName = buff.getEffectParam(buffName, 1);
            String type = effectName.substring((effectName.lastIndexOf("_") + 1), effectName.length());
            if (dot.attemptDotResist(defender, type, 100, true))
            {
                return false;
            }
            if (!dot.canApplyDotType(defender, type))
            {
                return false;
            }
        }
        buffDuration += getExpertiseBuffDurationMods(attacker, actionData, buffName, buffDuration);
        return buff.applyBuff(defender, attacker, buffName, buffDuration, buffStrength);
    }
    public static boolean applyAttackerCombatBuffs(obj_id attacker, combat_data actionData) throws InterruptedException
    {
        String actionName = actionData.actionName;
        String buffName = actionData.buffNameSelf;
        float buffStrength = actionData.buffStrengthSelf;
        float buffDuration = actionData.buffDurationSelf;
        if (buffName == null || buffName.equals(""))
        {
            return false;
        }
        if (!expertiseRandomBuffChance(attacker, actionData))
        {
            return false;
        }
        combatLog(attacker, null, "applyAttackerCombatBuffs", "Applying attacker buff - " + buffName);
        buffDuration += getExpertiseBuffDurationMods(attacker, actionData, buffName, buffDuration);
        return buff.applyBuff(attacker, attacker, buffName, buffDuration, buffStrength);
    }
    public static float getExpertiseBuffDurationMods(obj_id buffCaster, combat_data actionData, String buffName, float buffDuration) throws InterruptedException
    {
        float expertiseBuffDurationMod = 0.0f;
        dictionary dic = dataTableGetRow(buff.BUFF_TABLE, buffName);
        if (dic == null)
        {
            return expertiseBuffDurationMod;
        }
        String buffGroupName = dic.getString("GROUP1");
        if (buffDuration == 0)
        {
            buffDuration = dic.getFloat("DURATION");
        }
        String specialLine = actionData.specialLine;
        expertiseBuffDurationMod = buffDuration + getEnhancedSkillStatisticModifierUncapped(buffCaster, "expertise_buff_duration_line_" + specialLine);
        expertiseBuffDurationMod += getEnhancedSkillStatisticModifierUncapped(buffCaster, "expertise_buff_duration_group_" + toLower(buffGroupName));
        expertiseBuffDurationMod += getEnhancedSkillStatisticModifierUncapped(buffCaster, "expertise_buff_duration_single_" + toLower(buffName));
        return expertiseBuffDurationMod;
    }
    public static boolean applyCombatMovementModifier(obj_id attacker, obj_id defender, String actionName) throws InterruptedException
    {
        boolean isRoot = movement.isRoot(actionName);
        boolean isSnare = movement.isSnare(actionName);
        boolean isStun = movement.isStunEffect(actionName);
        prose_package pp = new prose_package();
        String movementType = "";
        if (isRoot)
        {
            movementType = "root";
        }
        if (isSnare)
        {
            movementType = "snare";
        }
        int lastMovementMod;
        if (isPlayer(defender))
        {
            lastMovementMod = utils.getIntScriptVar(defender, "combat.movement." + movementType + ".time");
        }
        else 
        {
            lastMovementMod = utils.getIntScriptVar(defender, "combat.movement." + movementType + "." + attacker + ".time");
        }
        if (lastMovementMod + MOVEMENT_MODIFIER_REUSE_TIME > getGameTime())
        {
            showCombatText(defender, attacker, new string_id("combat_effects", "no_effect"), 1.5f, colors.WHITE);
            return false;
        }
        if (buff.hasBuff(defender, actionName) || !buff.canApplyBuff(defender, actionName))
        {
            showCombatText(defender, attacker, new string_id("combat_effects", "no_effect"), 1.5f, colors.WHITE);
            return false;
        }
        if (isStun)
        {
            pp = prose.setStringId(pp, new string_id("combat_effects", "go_stunned"));
            showFlyTextPrivateProseWithFlags(defender, defender, pp, 1.5f, colors.LAWNGREEN, FLY_TEXT_FLAG_IS_SNARE);
            playClientEffectObj(defender, "appearance/pt_state_cantmove.prt", defender, "", null, "state_stunned");
            playClientEffectObj(defender, "sound/sta_rooted_on.snd", defender, "");
        }
        else if (isRoot)
        {
            pp = prose.setStringId(pp, new string_id("combat_effects", "go_rooted"));
            showFlyTextPrivateProseWithFlags(defender, defender, pp, 1.5f, colors.LAWNGREEN, FLY_TEXT_FLAG_IS_SNARE);
            playClientEffectObj(defender, "appearance/pt_state_cantmove.prt", defender, "", null, "state_rooted");
            playClientEffectObj(defender, "sound/sta_rooted_on.snd", defender, "");
        }
        else if (isSnare)
        {
            pp = prose.setStringId(pp, new string_id("combat_effects", "go_snare"));
            showFlyTextPrivateProseWithFlags(defender, defender, pp, 1.5f, colors.LAWNGREEN, FLY_TEXT_FLAG_IS_SNARE);
            playClientEffectObj(defender, "appearance/pt_state_slowedmove.prt", defender, "", null, "state_snared");
            playClientEffectObj(defender, "sound/sta_snared_on.snd", defender, "");
        }
        if (isPlayer(defender))
        {
            utils.setScriptVar(defender, "combat.movement." + movementType + ".time", getGameTime());
        }
        else 
        {
            utils.setScriptVar(defender, "combat.movement." + movementType + "." + attacker + ".time", getGameTime());
        }
        return true;
    }
    public static void removeCombatMovementModifierEffect(obj_id self, String buffName) throws InterruptedException
    {
        if (buffName == null || buffName.equals(""))
        {
            return;
        }
        boolean isVisible = true;
        dictionary buffData = dataTableGetRow("datatables/buff/buff.iff", buffName);
        if (buffData != null)
        {
            isVisible = buffData.getInt("VISIBLE") == 1;
        }
        prose_package pp = new prose_package();
        if (movement.isStunEffect(buffName))
        {
            if (isVisible)
            {
                pp = prose.setStringId(pp, new string_id("combat_effects", "no_stunned"));
                showFlyTextPrivateProseWithFlags(self, self, pp, 1.5f, colors.TOMATO, FLY_TEXT_FLAG_IS_SNARE);
            }
            stopClientEffectObjByLabel(self, "state_stunned");
            playClientEffectObj(self, "sound/sta_rooted_off.snd", self, "");
        }
        else if (movement.isRoot(buffName))
        {
            if (isVisible)
            {
                pp = prose.setStringId(pp, new string_id("combat_effects", "no_rooted"));
                showFlyTextPrivateProseWithFlags(self, self, pp, 1.5f, colors.TOMATO, FLY_TEXT_FLAG_IS_SNARE);
            }
            stopClientEffectObjByLabel(self, "state_rooted");
            playClientEffectObj(self, "sound/sta_rooted_off.snd", self, "");
        }
        else if (movement.isSnare(buffName) && buff.getDuration(buffName) > 0f)
        {
            if (isVisible)
            {
                pp = prose.setStringId(pp, new string_id("combat_effects", "no_snare"));
                showFlyTextPrivateProseWithFlags(self, self, pp, 1.5f, colors.TOMATO, FLY_TEXT_FLAG_IS_SNARE);
            }
            stopClientEffectObjByLabel(self, "state_snared");
            playClientEffectObj(self, "sound/sta_snared_off.snd", self, "");
        }
    }
    public static void removeCombatBuffEffect(obj_id self, String buffName) throws InterruptedException
    {
        for (int i = 1; i <= buff.MAX_EFFECTS; i++)
        {
            int effectType = getStringCrc(buff.getEffectParam(buffName, i));
            float effectValue = buff.getEffectValue(buffName, i);
            if (effectType == (-1161461455))
            {
                stopClientEffectObjByLabel(self, "state_slowattack");
                playClientEffectObj(self, "sound/sta_slow_off.snd", self, "");
            }
        }
    }
    public static boolean gainCombatActionAttribute(obj_id self, int action) throws InterruptedException
    {
        if (getMaxAction(self) == getAction(self))
        {
            return false;
        }
        int newAction = getAction(self) + action;
        if (newAction > getMaxAction(self))
        {
            newAction = getMaxAction(self);
        }
        setAction(self, newAction);
        return true;
    }
    public static boolean drainCombatActionAttributes(obj_id self, int[] actionCost) throws InterruptedException
    {
        if (!(drainAttributes(self, actionCost[1], actionCost[2])))
        {
            return false;
        }
        return true;
    }
    public static boolean canDrainCombatActionAttributes(obj_id self, int actionCost) throws InterruptedException
    {
        int[] tempArray = 
        {
            0,
            actionCost,
            0
        };
        return canDrainCombatActionAttributes(self, tempArray);
    }
    public static boolean canDrainCombatActionAttributes(obj_id self, int[] actionCost) throws InterruptedException
    {
        if (actionCost[1] > 0)
        {
            if (testDrainAttribute(self, ACTION, actionCost[1]) < 0)
            {
                return false;
            }
        }
        return true;
    }
    public static final int ACTION_SUCCESS = 0;
    public static final int ACTION_INVALID_DATA = 1;
    public static final int ACTION_INVALID_WEAPON = 2;
    public static final int ACTION_TOO_TIRED = 3;
    public static int canPerformAction(String actionName, obj_id self) throws InterruptedException
    {
        actionName = getBestAction(self, actionName);
        combat_data actionData = combat_engine.getCombatData(actionName);
        if (actionData == null)
        {
            return ACTION_INVALID_DATA;
        }
        weapon_data weaponData = new weapon_data();
        weaponData = weapons.getNewWeaponData(getCurrentWeapon(self));
        if (!canUseWeaponWithAbility(self, weaponData, actionData, true))
        {
            return ACTION_INVALID_WEAPON;
        }
        int[] actionCost = getActionCost(self, weaponData, actionData);
        if (!canDrainCombatActionAttributes(self, actionCost))
        {
            return ACTION_TOO_TIRED;
        }
        return ACTION_SUCCESS;
    }
    public static boolean isCommandoBonus(obj_id self, weapon_data weaponData, int commandType) throws InterruptedException
    {
        return isPlayer(self) && utils.isProfession(self, utils.COMMANDO) && isHeavyWeapon(weaponData) && (commandType == LEFT_CLICK_DEFAULT);
    }
    public static int[] getActionCost(obj_id self, weapon_data weaponData, dictionary actionData) throws InterruptedException
    {
        int[] cost = new int[3];
        float healthCost = 0;
        float actionCost;
        float mindCost;
        actionCost = actionData.getFloat("actionCost");
        if (isCommandoBonus(self, weaponData, actionData.getInt("commandType")))
        {
            cost[0] = (int)(0);
            cost[1] = (int)(0);
            cost[2] = (int)(0);
            return cost;
        }
        String specialLine = actionData.getString("specialLine");
        String actionName = actionData.getString("actionName");
        int freeshotChance = getEnhancedSkillStatisticModifierUncapped(self, "expertise_freeshot_" + specialLine);
        prose_package pp = new prose_package();
        if (freeshotChance > 0)
        {
            int roll = rand(1, 100) + freeshotChance;
            if (roll > 100)
            {
                actionCost = 0;
                pp = prose.setStringId(pp, new string_id("spam", "freeshot"));
                showFlyTextPrivateProseWithFlags(self, self, pp, 1.5f, colors.LEMONCHIFFON, FLY_TEXT_FLAG_IS_FREESHOT);
                pp = prose.setStringId(pp, new string_id("cbt_spam", "freeshot"));
                pp = prose.setTU(pp, self);
                sendCombatSpamMessageProse(self, self, pp, true, true, true, COMBAT_RESULT_GENERIC);
                cost[0] = (int)(0);
                cost[1] = (int)(0);
                cost[2] = (int)(0);
                return cost;
            }
        }
        if (actionCost > 0)
        {
            float expertiseActionCostMod = 0;
            float percentAddFromWeapon = actionData.getFloat("percentAddFromWeapon");
            if (percentAddFromWeapon > 0.0f)
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_" + weaponData.weaponType);
                if (isMeleeWeapon(weaponData.id))
                {
                    expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_melee");
                }
                if (isRangedWeapon(weaponData.id))
                {
                    expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_ranged");
                }
            }
            expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_all");
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_line_" + specialLine);
            }
            if (actionName != null && !actionName.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_single_" + actionName);
            }
            switch (weaponData.weaponType)
            {
                case WEAPON_TYPE_PISTOL:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_pistol");
                break;
                case WEAPON_TYPE_RIFLE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_rifle");
                break;
                case WEAPON_TYPE_LIGHT_RIFLE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_carbine");
                break;
                case WEAPON_TYPE_HEAVY:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_GROUND_TARGETTING:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_DIRECTIONAL:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_1HAND_MELEE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_1h");
                break;
                case WEAPON_TYPE_2HAND_MELEE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_2h");
                break;
                case WEAPON_TYPE_UNARMED:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_unarmed");
                break;
                case WEAPON_TYPE_POLEARM:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_polearm");
                break;
                case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_lightsaber");
                break;
            }
            actionCost = actionCost - (actionCost * (expertiseActionCostMod / 100.0f));
            if (actionCost < 0)
            {
                actionCost = 0;
            }
        }
        if (actionCost < 0)
        {
            float expertiseActionCostMod = 0;
            int actionTotal = getLevel(self) * 100;
            actionCost = actionTotal * (float)(-1.0f * ((float)actionCost / 100.0f));
            expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_all");
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_line_" + specialLine);
            }
            if (actionName != null && !actionName.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_single_" + actionName);
            }
            actionCost = actionCost * (1 - (expertiseActionCostMod / 100));
        }
        if (utils.hasScriptVar(self, "buff.action_burn.value") && actionCost > 0)
        {
            int rightNow = getGameTime();
            int timestamp = utils.getIntScriptVar(self, "buff.action_burn.timestamp");
            if (rightNow > timestamp)
            {
                utils.setScriptVar(self, "buff.action_burn.timestamp", rightNow);
                float burnRatio = utils.getFloatScriptVar(self, "buff.action_burn.value");
                float actionBurn = (float)actionCost * (burnRatio / 100);
                int currentHealth = getHealth(self);
                int modifiedHealth = currentHealth - (int)actionBurn;
                if (modifiedHealth < 1)
                {
                    modifiedHealth = 1;
                }
                int[] burnBuffs = buff.getAllBuffsByEffect(self, "action_burn");
                obj_id burnBuffOwner = self;
                if (burnBuffs.length > 0 && utils.hasScriptVar(self, "buffOwner." + burnBuffs[0]))
                {
                    burnBuffOwner = utils.getObjIdScriptVar(self, "buffOwner." + burnBuffs[0]);
                }
                setHealth(self, modifiedHealth);
                pp = prose.setStringId(pp, new string_id("spam", "crippling_pain"));
                showFlyTextPrivateProseWithFlags(self, burnBuffOwner, pp, 1.5f, colors.ORANGERED, FLY_TEXT_FLAG_IS_CRITICAL_HIT);
                pp = prose.setStringId(pp, new string_id("cbt_spam", "crippling_pain"));
                pp = prose.setTU(pp, self);
                pp = prose.setDI(pp, (int)actionBurn);
                sendCombatSpamMessageProse(self, burnBuffOwner, pp, true, true, true, COMBAT_RESULT_DEBUFF);
            }
        }
        cost[0] = (int)(0);
        cost[1] = (int)(actionCost);
        cost[2] = (int)(0);
        return cost;
    }
    public static int[] getActionCost(obj_id self, weapon_data weaponData, combat_data actionData) throws InterruptedException
    {
        int[] cost = new int[3];
        float healthCost = 0;
        float actionCost;
        float mindCost;
        actionCost = actionData.actionCost;
        if (isCommandoBonus(self, weaponData, actionData.commandType))
        {
            cost[0] = (int)(0);
            cost[1] = (int)(0);
            cost[2] = (int)(0);
            return cost;
        }
        if (beast_lib.isBeast(self))
        {
            cost[0] = 0;
            cost[1] = (int)actionData.vigorCost;
            cost[2] = 0;
            return cost;
        }
        String specialLine = actionData.specialLine;
        String actionName = actionData.actionName;
        int freeshotChance = getEnhancedSkillStatisticModifierUncapped(self, "expertise_freeshot_" + specialLine);
        prose_package pp = new prose_package();
        if (freeshotChance > 0)
        {
            int roll = rand(1, 100) + freeshotChance;
            if (roll > 100)
            {
                actionCost = 0;
                pp = prose.setStringId(pp, new string_id("spam", "freeshot"));
                showFlyTextPrivateProseWithFlags(self, self, pp, 1.5f, colors.LEMONCHIFFON, FLY_TEXT_FLAG_IS_FREESHOT);
                pp = prose.setStringId(pp, new string_id("cbt_spam", "freeshot"));
                pp = prose.setTU(pp, self);
                sendCombatSpamMessageProse(self, self, pp, true, true, true, COMBAT_RESULT_GENERIC);
                cost[0] = (int)(0);
                cost[1] = (int)(0);
                cost[2] = (int)(0);
                return cost;
            }
        }
        if (actionCost > 0)
        {
            float expertiseActionCostMod = 0;
            if (actionData.percentAddFromWeapon > 0.0f)
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_" + weaponData.weaponType);
                if (isMeleeWeapon(weaponData.id))
                {
                    expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_melee");
                }
                if (isRangedWeapon(weaponData.id))
                {
                    expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_weapon_ranged");
                }
            }
            if (actionData.hitType == HEAL)
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "exotic_heal_action_reduction");
            }
            expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_all");
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_line_" + specialLine);
            }
            if (actionName != null && !actionName.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_single_" + actionName);
            }
            switch (weaponData.weaponType)
            {
                case WEAPON_TYPE_PISTOL:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_pistol");
                break;
                case WEAPON_TYPE_RIFLE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_rifle");
                break;
                case WEAPON_TYPE_LIGHT_RIFLE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_carbine");
                break;
                case WEAPON_TYPE_HEAVY:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_GROUND_TARGETTING:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_DIRECTIONAL:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_heavy");
                break;
                case WEAPON_TYPE_1HAND_MELEE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_1h");
                break;
                case WEAPON_TYPE_2HAND_MELEE:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_2h");
                break;
                case WEAPON_TYPE_UNARMED:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_unarmed");
                break;
                case WEAPON_TYPE_POLEARM:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_polearm");
                break;
                case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
                expertiseActionCostMod += (float)getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_lightsaber");
                break;
            }
            actionCost = actionCost - (actionCost * (expertiseActionCostMod / 100.0f));
            if (actionCost < 0)
            {
                actionCost = 0;
            }
        }
        if (actionCost < 0)
        {
            float expertiseActionCostMod = 0;
            int actionTotal = getLevel(self) * 100;
            actionCost = actionTotal * (float)(-1.0f * ((float)actionCost / 100.0f));
            expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_all");
            if (specialLine != null && !specialLine.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_line_" + specialLine);
            }
            if (actionName != null && !actionName.equals(""))
            {
                expertiseActionCostMod += getEnhancedSkillStatisticModifierUncapped(self, "expertise_action_single_" + actionName);
            }
            actionCost = actionCost * (1 - (expertiseActionCostMod / 100));
        }
        if (utils.hasScriptVar(self, "buff.action_burn.value") && actionCost > 0)
        {
            int rightNow = getGameTime();
            int timestamp = utils.getIntScriptVar(self, "buff.action_burn.timestamp");
            if (rightNow > timestamp)
            {
                utils.setScriptVar(self, "buff.action_burn.timestamp", rightNow);
                float burnRatio = utils.getFloatScriptVar(self, "buff.action_burn.value");
                float actionBurn = (float)actionCost * (burnRatio / 100);
                int currentHealth = getHealth(self);
                int modifiedHealth = currentHealth - (int)actionBurn;
                if (modifiedHealth < 1)
                {
                    modifiedHealth = 1;
                }
                setHealth(self, modifiedHealth);
                int[] burnBuffs = buff.getAllBuffsByEffect(self, "action_burn");
                obj_id burnBuffOwner = self;
                if (burnBuffs.length > 0 && utils.hasScriptVar(self, "buffOwner." + burnBuffs[0]))
                {
                    burnBuffOwner = utils.getObjIdScriptVar(self, "buffOwner." + burnBuffs[0]);
                }
                pp = prose.setStringId(pp, new string_id("spam", "crippling_pain"));
                showFlyTextPrivateProseWithFlags(self, burnBuffOwner, pp, 1.5f, colors.ORANGERED, FLY_TEXT_FLAG_IS_CRITICAL_HIT);
                pp = prose.setStringId(pp, new string_id("cbt_spam", "crippling_pain"));
                pp = prose.setTU(pp, self);
                pp = prose.setDI(pp, (int)actionBurn);
                sendCombatSpamMessageProse(self, burnBuffOwner, pp, true, true, true, COMBAT_RESULT_DEBUFF);
            }
        }
        cost[0] = (int)(0);
        cost[1] = (int)(actionCost);
        cost[2] = (int)(0);
        combatLog(self, null, "getActionCost", "Final Action cost = [" + cost[0] + ", " + cost[1] + ", " + cost[2] + "]");
        return cost;
    }
    public static int getForceCost(obj_id self, weapon_data weaponData, dictionary actionData) throws InterruptedException
    {
        return 0;
    }
    public static int getForceCost(obj_id self, weapon_data weaponData, combat_data actionData) throws InterruptedException
    {
        return 0;
    }
    public static boolean validateTarget(obj_id target, int targetCheck) throws InterruptedException
    {
        obj_id self = getSelf();
        if (!storyteller.storytellerCombatCheck(self, target))
        {
            return false;
        }
        switch (targetCheck)
        {
            case VALID_TARGET_MOB:
            return isMob(target) && !vehicle.isVehicle(target);
            case VALID_TARGET_CREATURE:
            return ai_lib.isMonster(target);
            case VALID_TARGET_NPC:
            return ai_lib.isNpc(target);
            case VALID_TARGET_DROID:
            return ai_lib.isDroid(target) || ai_lib.isAndroid(target);
            case VALID_TARGET_PVP:
            return pvpCanAttack(self, target);
            case VALID_TARGET_JEDI:
            if (isPlayer(target))
            {
                return isJedi(target);
            }
            else 
            {
                return jedi.isLightsaber(getCurrentWeapon(target));
            }
            case VALID_TARGET_DEAD:
            return isDead(target);
            case VALID_TARGET_FRIEND:
            return pvpCanHelp(self, target);
        }
        return true;
    }
    public static String getCreatureAnimationName(obj_id self, String playbackName, hit_result hitData, weapon_data weaponData) throws InterruptedException
    {
        String animName = "";
        int niche = ai_lib.aiGetNiche(self);
        if (niche == NICHE_VEHICLE)
        {
            animName = "droid_attack";
        }
        else 
        {
            if (playbackName.endsWith("ranged"))
            {
                animName = "creature_attack_ranged";
            }
            else if (playbackName.endsWith("melee"))
            {
                animName = "creature_attack";
            }
            else 
            {
                animName = "creature_attack_special_" + rand(1, 2);
            }
        }
        int avgDamage = (weaponData.minDamage + weaponData.maxDamage) / 2;
        if (hitData.damage > avgDamage)
        {
            animName += "_medium";
        }
        else 
        {
            animName += "_light";
        }
        return animName;
    }
    public static boolean checkWeaponCerts(obj_id player, weapon_data weaponData) throws InterruptedException
    {
        return checkWeaponCerts(player, weaponData, true);
    }
    public static boolean checkWeaponCerts(obj_id player, weapon_data weaponData, boolean verbose) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return true;
        }
        if (!combat.hasCertification(player, weaponData.id, verbose))
        {
            return false;
        }
        if (utils.isProfession(player, utils.FORCE_SENSITIVE))
        {
            if (jedi.isLightsaber(weaponData.weaponType))
            {
                if (!jedi.hasColorCrystal(weaponData.id))
                {
                    return false;
                }
                if (!jedi.validateCrystalCount(weaponData.id))
                {
                    return false;
                }
            }
            if (buff.hasBuff(player, "forceRun_1") || buff.hasBuff(player, "forceRun_2"))
            {
                return false;
            }
        }
        return true;
    }
    public static int applyArmorProtection(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, float bypassArmor) throws InterruptedException
    {
        return applyArmorProtection(attacker, defender, weaponData, hitData, bypassArmor, 0f);
    }
    public static int applyArmorProtection(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, float bypassArmor, float expertiseDamageBonus) throws InterruptedException
    {
        float baseDamage = hitData.damage;
        float elementalDamage = weaponData.elementalValue;
        float baseProtection = 0f;
        float elementalProtection = 0f;
        float basePsgProtection = 0f;
        float elementalPsgProtection = 0f;
        boolean strikethrough = hitData.strikethrough;
        int armor_neglect = getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_armor_neglect");
        int player_armor_reduction = getEnhancedSkillStatisticModifierUncapped(defender, "expertise_innate_reduction_all_player");
        float neglectMod = 1.0f - ((float)armor_neglect / 100.0f);
        float playerMod = 1.0f - ((float)player_armor_reduction / 100.0f);
        obj_id armorPiece = null;
        obj_id psg = null;
        if (isPlayer(defender))
        {
            boolean isRanged = isRangedWeapon(weaponData.weaponType);
            psg = getPsgArmor(defender);
            if (isIdValid(defender) || isIdValid(psg))
            {
                float[] psgProtectionReference = new float[1];
                baseProtection = getArmorProtection(defender, defender, psg, weaponData.damageType, psgProtectionReference);
                baseProtection *= neglectMod;
                baseProtection *= playerMod;
                if (isRanged)
                {
                    basePsgProtection = psgProtectionReference[0];
                }
                elementalProtection = getArmorProtection(defender, defender, psg, weaponData.elementalType, psgProtectionReference, attacker);
                if (isRanged)
                {
                    elementalPsgProtection = psgProtectionReference[0];
                }
                if (isRanged)
                {
                    if (basePsgProtection > 0 || elementalPsgProtection > 0)
                    {
                        int psgLevel = armor.getArmorLevel(psg);
                        if (psgLevel < 0 || psgLevel >= armor.PSG_HIT_EFFECTS.length)
                        {
                            psgLevel = 0;
                        }
                        playClientEffectObj(defender, armor.PSG_HIT_EFFECTS[psgLevel], defender, "", transform.identity);
                    }
                }
            }
        }
        else 
        {
            baseProtection = getNpcArmorProtection(defender, weaponData.damageType);
            baseProtection *= neglectMod;
            elementalProtection = getNpcArmorProtection(defender, weaponData.elementalType);
        }
        if (bypassArmor == 0 && strikethrough)
        {
            float strikethroughRating = (float)(getStrikethroughValue(attacker, defender) / 100.0f);
            bypassArmor = rand(strikethroughRating / 2.0f, strikethroughRating);
            hitData.strikethroughAmmount = bypassArmor * 100.0f;
        }
        if (bypassArmor > 0)
        {
            if (bypassArmor > 1.0)
            {
                bypassArmor = 1f;
            }
            if (bypassArmor < 0.0)
            {
                bypassArmor = 0f;
            }
        }
        baseProtection *= 1.0 - bypassArmor;
        elementalProtection *= 1.0 - bypassArmor;
        if (basePsgProtection > 0)
        {
            basePsgProtection = scalePsgProtectionByBaseProtection(basePsgProtection, baseProtection);
        }
        int baseDmgAbsorbed = (int)(baseDamage * (baseProtection + basePsgProtection));
        baseDamage -= baseDmgAbsorbed;
        int blockValue = 0;
        if (hitData.blockResult)
        {
            blockValue = combat.getBlockAmmount(defender);
            if (baseDamage <= blockValue)
            {
                hitData.block = (int)baseDamage;
                baseDamage = 0.0f;
            }
            else 
            {
                baseDamage -= blockValue;
                hitData.block = blockValue;
            }
            string_id critSpam = new string_id("combat_effects", "block");
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, critSpam);
            pp = prose.setDI(pp, hitData.block);
            showFlyTextPrivateProseWithFlags(defender, defender, pp, 1.5f, colors.LIMEGREEN, FLY_TEXT_FLAG_IS_GLANCING_BLOW);
            showFlyTextPrivateProseWithFlags(defender, attacker, pp, 1.5f, colors.LIMEGREEN, FLY_TEXT_FLAG_IS_GLANCING_BLOW);
        }
        if (baseDamage < 0)
        {
            baseDmgAbsorbed += baseDamage;
            baseDamage = 0;
        }
        if (expertiseDamageBonus != 0)
        {
            elementalDamage = elementalDamage * (1.0f + (expertiseDamageBonus / 100.0f));
        }
        int elementalDmgAbsorbed = (int)(elementalDamage * (elementalProtection));
        elementalDamage -= elementalDmgAbsorbed;
        if (elementalDamage < 0)
        {
            elementalDmgAbsorbed += elementalDamage;
            elementalDamage = 0;
        }
        if ((basePsgProtection > 0 || elementalPsgProtection > 0) && isIdValid(psg))
        {
            decayArmorPiece(psg, baseDamage * basePsgProtection + elementalDamage * elementalPsgProtection);
        }
        hitData.elementalDamage = (int)elementalDamage;
        hitData.elementalDamageType = weaponData.elementalType;
        hitData.damage = (int)baseDamage;
        if ((baseProtection > 0 || elementalProtection > 0) && isIdValid(armorPiece))
        {
            hitData.blockingArmor = armorPiece;
        }
        else if ((basePsgProtection > 0 || elementalPsgProtection > 0) && isIdValid(psg))
        {
            hitData.blockingArmor = psg;
        }
        return (baseDmgAbsorbed + elementalDmgAbsorbed);
    }
    public static float scalePsgProtectionByBaseProtection(float basePsgProtection, float baseProtection) throws InterruptedException
    {
        if (baseProtection < .2f)
        {
            baseProtection = .2f;
        }
        basePsgProtection *= 10000;
        basePsgProtection = (basePsgProtection / 100) / (100 + ((basePsgProtection / 100))) / (baseProtection * 4);
        return basePsgProtection;
    }
    public static obj_id getArmorPieceHit(obj_id defender, int hitLocation) throws InterruptedException
    {
        String[] armorSlots = ARMOR_SLOT_LOCATIONS[hitLocation];
        String slotHit = armorSlots[rand(0, armorSlots.length - 1)];
        obj_id armorPiece = getObjectInSlot(defender, slotHit);
        int armorType = getGameObjectType(armorPiece);
        if (isGameObjectTypeOf(armorType, GOT_armor))
        {
            return armorPiece;
        }
        return null;
    }
    public static obj_id getPsgArmor(obj_id defender) throws InterruptedException
    {
        obj_id psg = getObjectInSlot(defender, PSG_SLOT_LOCATION);
        if (armor.isPsg(psg))
        {
            return psg;
        }
        return null;
    }
    public static float getNpcArmorProtection(obj_id defender, int damageType) throws InterruptedException
    {
        int generalProtection = 0;
        dictionary protections = null;
        generalProtection = armor.getArmorGeneralProtection(defender);
        generalProtection += getEnhancedSkillStatisticModifier(defender, "private_armor_bonus");
        generalProtection -= getEnhancedSkillStatisticModifierUncapped(defender, "expertise_innate_reduction_all_mob");
        if (generalProtection == 0 && protections == null)
        {
            return 0f;
        }
        if (utils.hasScriptVar(defender, "ai.combat.genProtectionBonus"))
        {
            generalProtection += (int)utils.getFloatScriptVar(defender, "ai.combat.genProtectionBonus");
        }
        float armorProtection = convertProtectionToPercent(generalProtection);
        return armorProtection;
    }
    public static float getArmorProtection(obj_id defender, obj_id armorPiece, obj_id psg, int damageType, float[] psgProtectionResult) throws InterruptedException
    {
        return getArmorProtection(defender, armorPiece, psg, damageType, psgProtectionResult, obj_id.NULL_ID);
    }
    public static float getArmorProtection(obj_id defender, obj_id armorPiece, obj_id psg, int damageType, float[] psgProtectionResult, obj_id attacker) throws InterruptedException
    {
        int generalProtection = 0;
        int armorBonus = getEnhancedSkillStatisticModifier(defender, "private_armor_bonus");
        generalProtection += armorBonus;
        dictionary protections = null;
        dictionary psgProtections = null;
        if (isIdValid(armorPiece))
        {
            generalProtection = armor.getCombatArmorGeneralProtection(armorPiece);
            protections = armor.getCombatArmorSpecialProtections(armorPiece);
        }
        if (isIdValid(psg))
        {
            psgProtections = armor.getPsgSpecialProtections(psg);
        }
        if (generalProtection == 0 && protections == null && psgProtections == null)
        {
            return 0f;
        }
        String damageString = null;
        switch (damageType)
        {
            case DAMAGE_KINETIC:
            damageString = "kinetic";
            break;
            case DAMAGE_ENERGY:
            damageString = "energy";
            break;
            case DAMAGE_BLAST:
            damageString = "blast";
            break;
            case DAMAGE_STUN:
            damageString = "stun";
            break;
            case DAMAGE_RESTRAINT:
            damageString = "lightsaber";
            break;
            case DAMAGE_ELEMENTAL_HEAT:
            damageString = "heat";
            break;
            case DAMAGE_ELEMENTAL_COLD:
            damageString = "cold";
            break;
            case DAMAGE_ELEMENTAL_ACID:
            damageString = "acid";
            break;
            case DAMAGE_ELEMENTAL_ELECTRICAL:
            damageString = "electricity";
            break;
            case DAMAGE_ENVIRONMENTAL_ELECTRICAL:
            damageString = "electricity";
            break;
            default:
            break;
        }
        float value = generalProtection;
        float psgValue = 0;
        if (damageString != null)
        {
            if (protections != null)
            {
                value += protections.getFloat(damageString);
            }
            if (psgProtections != null)
            {
                psgValue = psgProtections.getFloat(damageString);
                if (psgValue != 0)
                {
                    if (psgProtectionResult != null && psgProtectionResult.length > 0)
                    {
                        psgProtectionResult[0] = psgValue / 10000f;
                    }
                }
            }
        }
        if (isIdValid(attacker) && damageString != null && damageString.length() > 0)
        {
            float elementalPenetration = getElementalPenetration(attacker, damageString);
            if (elementalPenetration > 0)
            {
                value *= elementalPenetration;
            }
        }
        value = convertProtectionToPercent(value);
        return value;
    }
    public static float getTerasKasiProtection(obj_id defender) throws InterruptedException
    {
        if (utils.getIntScriptVar(defender, armor.SCRIPTVAR_ARMOR_COUNT) > 0)
        {
            return 0f;
        }
        float protection = 0f;
        float tkaArmor = 0f;
        float jediArmor = 0f;
        protection = getEnhancedSkillStatisticModifier(defender, "clothing_armor");
        if (hasSkill(defender, "combat_unarmed_novice"))
        {
            tkaArmor = getSkillStatisticModifier(defender, "tka_armor");
        }
        if (tkaArmor > protection)
        {
            protection = tkaArmor;
        }
        if (hasSkill(defender, "force_title_jedi_novice"))
        {
            jediArmor = getSkillStatisticModifier(defender, "jedi_armor");
        }
        if (jediArmor > protection)
        {
            protection = jediArmor;
        }
        protection *= 100f;
        int armorBonus = getEnhancedSkillStatisticModifier(defender, "private_armor_bonus");
        protection += armorBonus;
        return protection;
    }
    public static float convertProtectionToPercent(float protection) throws InterruptedException
    {
        final int reductionThreshold = 2000;
        if (protection <= reductionThreshold && protection >= (-1 * reductionThreshold))
        {
            return protection / 10000f;
        }
        if (protection < 0)
        {
            protection += reductionThreshold;
            protection = protection / (100f - (protection / 100f));
            protection = -0.2f + (protection / 100f);
        }
        else 
        {
            protection -= reductionThreshold;
            protection = protection / (100f + (protection / 100f));
            protection = 0.2f + (protection / 100f);
        }
        return protection;
    }
    public static float getArmorDecayPercentage(obj_id armorPiece) throws InterruptedException
    {
        float ARMOR_REDUCTION_THRESHOLD = 0.5f;
        float reduction = 1.0f;
        float hp = getHitpoints(armorPiece);
        float maxhp = getMaxHitpoints(armorPiece);
        float decayLevel = hp / maxhp;
        if (hasObjVar(armorPiece, "slice.resilience"))
        {
            int resilience = getIntObjVar(armorPiece, "slice.resilience");
            ARMOR_REDUCTION_THRESHOLD -= resilience / 100.f;
        }
        if (decayLevel < ARMOR_REDUCTION_THRESHOLD)
        {
            reduction = 1f - (ARMOR_REDUCTION_THRESHOLD - decayLevel);
        }
        return reduction;
    }
    public static float reduceArmorProtectionFromDecay(obj_id armorPiece, float value) throws InterruptedException
    {
        float ARMOR_REDUCTION_THRESHOLD = 0.5f;
        float hp = getHitpoints(armorPiece);
        float maxhp = getMaxHitpoints(armorPiece);
        float decayLevel = hp / maxhp;
        if (hasObjVar(armorPiece, "slice.resilience"))
        {
            int resilience = getIntObjVar(armorPiece, "slice.resilience");
            ARMOR_REDUCTION_THRESHOLD -= resilience / 100.f;
        }
        if (decayLevel < ARMOR_REDUCTION_THRESHOLD)
        {
            float reduction = 1f - (ARMOR_REDUCTION_THRESHOLD - decayLevel);
            value *= reduction;
        }
        return value;
    }
    public static void decayArmorPiece(obj_id armorPiece, float newDamage) throws InterruptedException
    {
        if (armor.isPsg(armorPiece))
        {
            dictionary protections = armor.getArmorSpecialProtections(armorPiece);
            float maxEffectiveness = (((Float)(((protections.values()).iterator()).next()))).floatValue();
            if (maxEffectiveness > 0)
            {
                float effectivenessReduction = PSG_BASE_DMG_PER_HIT + (newDamage / PSG_DAMAGE_SCALE);
                float efficiencyReduction = effectivenessReduction / maxEffectiveness;
                float currentEfficiency = armor.getPsgEfficiency(armorPiece);
                currentEfficiency -= efficiencyReduction;
                armor.setPsgEfficiency(armorPiece, currentEfficiency);
            }
        }
        if (utils.isAntiDecay(armorPiece))
        {
            return;
        }
        if (static_item.isStaticItem(armorPiece))
        {
            return;
        }
    }
    public static boolean getGrenadeData(obj_id player, weapon_data weaponData, String params, boolean verbose) throws InterruptedException
    {
        Long lngId;
        try
        {
            lngId = new Long(params);
        }
        catch(NumberFormatException err)
        {
            if (verbose)
            {
                sendSystemMessage(player, new string_id("cbt_spam", "grenade_bad_param"));
            }
            return false;
        }
        obj_id grenade = obj_id.getObjId(lngId.longValue());
        if (!isIdValid(grenade))
        {
            if (verbose)
            {
                sendSystemMessage(player, new string_id("cbt_spam", "grenade_invalid"));
            }
            return false;
        }
        if (getFirstParentInWorld(grenade) != player)
        {
            if (verbose)
            {
                sendSystemMessage(player, new string_id("cbt_spam", "grenade_not_owned"));
            }
            removeObjVar(grenade, "intUsed");
            return false;
        }
        weaponData.id = grenade;
        weaponData.weaponName = getNameStringId(grenade);
        weaponData.minDamage = getWeaponMinDamage(grenade);
        weaponData.maxDamage = getWeaponMaxDamage(grenade);
        weaponData.weaponType = WEAPON_TYPE_THROWN;
        weaponData.attackType = ATTACK_TYPE_THROWN;
        weaponData.damageType = getWeaponDamageType(grenade);
        weaponData.elementalType = getWeaponElementalType(grenade);
        weaponData.elementalValue = getWeaponElementalValue(grenade);
        weaponData.attackSpeed = getWeaponAttackSpeed(grenade);
        weaponData.woundChance = getWeaponWoundChance(grenade);
        range_info rng = getWeaponRangeInfo(grenade);
        weaponData.minRange = rng.minRange;
        weaponData.maxRange = rng.maxRange;
        weaponData.accuracy = getWeaponAccuracy(grenade);
        weaponData.damageRadius = getWeaponDamageRadius(grenade);
        weaponData.attackCost = getWeaponAttackCost(grenade);
        return true;
    }
    public static boolean canPaintTarget(obj_id squadLeader, obj_id target) throws InterruptedException
    {
        if (!isValidId(target))
        {
            return false;
        }
        if (!pvpCanAttack(squadLeader, target))
        {
            return false;
        }
        obj_id groupId = getGroupObject(squadLeader);
        if (!isIdValid(groupId))
        {
            return false;
        }
        if (utils.hasScriptVar(groupId, VAR_GROUP_LAST_VOLLEY_TIME))
        {
            int lastVolley = utils.getIntScriptVar(groupId, VAR_GROUP_LAST_VOLLEY_TIME);
            int now = getGameTime();
            int interval = now - lastVolley;
            if (interval < VOLLEY_INTERVAL)
            {
                return false;
            }
        }
        obj_id[] groupMembers = getGroupMemberIds(groupId);
        if (groupMembers == null || groupMembers.length < 2)
        {
            return false;
        }
        return true;
    }
    public static void doPaintTarget(obj_id squadLeader, obj_id target) throws InterruptedException
    {
        obj_id groupId = getGroupObject(squadLeader);
        obj_id[] groupMembers = getGroupMemberIds(groupId);
        if (!hasScript(target, "systems.combat.volleytarget"))
        {
            attachScript(target, "systems.combat.volleytarget");
        }
        utils.setScriptVar(groupId, VAR_GROUP_VOLLEY_TARGET, target);
        utils.setScriptVar(groupId, VAR_GROUP_LAST_VOLLEY_TIME, getGameTime());
        dictionary parms = new dictionary();
        parms.put("objGroup", groupId);
        messageTo(target, "msgMarkedByGroup", parms, 0, false);
        messageTo(target, "msgUnmarkedByGroup", parms, VOLLEY_INTERVAL * 0.7f, false);
        playClientEffectObj(groupMembers, "appearance/pt_special_attack_volley_fire.prt", target, "", new transform(), ID_VOLLEY_FIRE_PARTICLE);
    }
    public static boolean canUseWeaponWithAbility(obj_id self, weapon_data weaponData, combat_data actionData, boolean verbose) throws InterruptedException
    {
        if (!isIdValid(weaponData.id))
        {
            return false;
        }
        if (((actionData.invalidWeapon & weaponTypeToBitValueMap[weaponData.weaponType]) == 0) && ((actionData.validWeapon & weaponTypeToBitValueMap[weaponData.weaponType]) != 0))
        {
            return true;
        }
        if (verbose)
        {
            sendCombatSpamMessage(self, new string_id("cbt_spam", "invalid_weapon"));
            sendSystemMessage(self, new string_id("cbt_spam", "invalid_weapon_single"));
        }
        return false;
    }
    public static boolean canUseWeaponWithAbility(obj_id self, weapon_data weaponData, String actionName, boolean verbose) throws InterruptedException
    {
        return canUseWeaponWithAbility(self, weaponData, combat_engine.getCombatData(actionName), verbose);
    }
    public static void addHateProcess(obj_id attacker, obj_id defender, hit_result hitData, combat_data actionData) throws InterruptedException
    {
        if (defender == attacker)
        {
            return;
        }
        float hateMod = actionData.hateDamageModifier;
        int maxHate = actionData.maxHate;
        int hateAdd = actionData.hateAdd;
        int hateAddTime = actionData.hateAddTime;
        int hateReduction = actionData.hateReduce;
        if (hitData.success)
        {
            if (hateReduction < 1)
            {
                if (maxHate > 0)
                {
                    final float maxHateDamage = getMaxHate(defender);
                    setHate(defender, attacker, maxHateDamage + maxHate);
                }
                if (hateAdd > 0 && hateAddTime > 0)
                {
                    addHateDot(defender, attacker, hateAdd, hateAddTime);
                }
                float totalHateDamage = hitData.damage * hateMod;
                if (totalHateDamage > 0.0f)
                {
                    int skillBonus = getEnhancedSkillStatisticModifier(attacker, "private_taunt_bonus");
                    totalHateDamage += totalHateDamage * (skillBonus / 100f);
                    if (utils.hasScriptVar(attacker, "ai.combat.aggroBonus"))
                    {
                        totalHateDamage += totalHateDamage * utils.getFloatScriptVar(attacker, "ai.combat.aggroBonus");
                    }
                    if (utils.hasScriptVar(attacker, "ai.combat.aggroBonusFlat"))
                    {
                        totalHateDamage += utils.getFloatScriptVar(attacker, "ai.combat.aggroBonusFlat");
                    }
                    if (utils.hasScriptVar(attacker, "ai.combat.aggroReduction"))
                    {
                        totalHateDamage -= totalHateDamage * utils.getFloatScriptVar(attacker, "ai.combat.aggroReduction");
                    }
                    if (buff.hasBuff(attacker, BUFF_HATE_XFER))
                    {
                        int hateTransferBonus = getEnhancedSkillStatisticModifier(attacker, "expertise_aggro_channel");
                        float hateTransfered = totalHateDamage * (hateTransferBonus / 100f);
                        totalHateDamage -= hateTransfered;
                        obj_id transferTo = utils.getObjIdScriptVar(attacker, buff.AGGRO_TRANSFER_TO);
                        addHate(defender, transferTo, hateTransfered);
                    }
                    addHate(defender, attacker, totalHateDamage);
                }
            }
            else 
            {
                if ((isPlayer(attacker) || beast_lib.isBeast(attacker)) && (isMob(defender) && !beast_lib.isBeast(defender)))
                {
                    float currentHate = getHate(defender, attacker);
                    currentHate -= hateReduction;
                    currentHate = currentHate < 1.0f ? 1.0f : currentHate;
                    setHate(defender, attacker, currentHate);
                }
                else if ((isMob(attacker) && !beast_lib.isBeast(attacker)) && (isPlayer(defender) || beast_lib.isBeast(defender)))
                {
                    float currentHate = getHate(attacker, defender);
                    currentHate -= hateReduction;
                    currentHate = currentHate < 1.0f ? 1.0f : currentHate;
                    setHate(attacker, defender, currentHate);
                }
            }
        }
        else 
        {
            addHate(defender, attacker, 1.0f);
        }
        if (isPlayer(attacker))
        {
            addHate(attacker, defender, 0.0f);
            
            {
                obj_id[] hateList = getHateList(attacker);
                for (int i = 0; i < hateList.length; ++i)
                {
                    obj_id hateTarget = hateList[i];
                    if (!isPlayer(hateTarget) && isTangible(hateTarget))
                    {
                        if (getHateTarget(hateTarget) == attacker)
                        {
                            resetHateTimer(hateTarget);
                        }
                    }
                }
            }
        }
    }
    public static boolean cachedIsRidingVehicle(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.ridingVehicle";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        boolean ridingVehicle = vehicle.isRidingVehicle(attacker);
        setCachedCombatValue(attacker, cacheId, ridingVehicle);
        return ridingVehicle;
    }
    public static boolean cachedIsGalloping(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.galloping";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        obj_id mount = getMountId(attacker);
        boolean galloping = false;
        if (isIdValid(mount))
        {
            galloping = pet_lib.isGalloping(mount);
        }
        setCachedCombatValue(attacker, cacheId, galloping);
        return galloping;
    }
    public static boolean cachedIsDead(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.isDead";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        boolean dead = isDead(attacker);
        setCachedCombatValue(attacker, cacheId, dead);
        return dead;
    }
    public static boolean cachedIsIncapacitated(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.isIncapped";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        boolean incapped = isIncapacitated(attacker);
        setCachedCombatValue(attacker, cacheId, incapped);
        return incapped;
    }
    public static int cachedGetLocomotion(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.locomotion";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueInt(attacker, cacheId);
        }
        int locomotion = getLocomotion(attacker);
        setCachedCombatValue(attacker, cacheId, locomotion);
        return locomotion;
    }
    public static float cachedGetMovementSpeed(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.moveSpeed";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueFloat(attacker, cacheId);
        }
        float moveSpeed = getMovementSpeed(attacker);
        setCachedCombatValue(attacker, cacheId, moveSpeed);
        return moveSpeed;
    }
    public static float cachedGetRunSpeed(obj_id attacker) throws InterruptedException
    {
        String cacheId = "combat.cache.runSpeed";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueFloat(attacker, cacheId);
        }
        float runSpeed = getRunSpeed(attacker);
        setCachedCombatValue(attacker, cacheId, runSpeed);
        return runSpeed;
    }
    public static float cachedGetDistance(obj_id attacker, obj_id defender) throws InterruptedException
    {
        String cacheId = "combat.cache." + defender + ".distance";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueFloat(attacker, cacheId);
        }
        float dist = getDistance(attacker, defender);
        setCachedCombatValue(attacker, cacheId, dist);
        return dist;
    }
    public static float cachedGetSpeedAdjustMin(obj_id attacker, obj_id defender) throws InterruptedException
    {
        String cacheId = "combat.cache." + defender + ".speedAdjustMin";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueFloat(attacker, cacheId);
        }
        float adjustMin = 0.0f;
        if (cachedGetLocomotion(attacker) == LOCOMOTION_RUNNING)
        {
            location defenderLocation = getLocation(defender);
            defenderLocation.y = getLocation(attacker).y;
            if (!isFacing(attacker, defenderLocation))
            {
                adjustMin -= cachedGetMovementSpeed(attacker) * 0.6f;
            }
        }
        if (cachedGetLocomotion(defender) == LOCOMOTION_RUNNING)
        {
            location attackerLocation = getLocation(attacker);
            attackerLocation.y = getLocation(defender).y;
            if (isFacing(defender, attackerLocation))
            {
                adjustMin -= cachedGetMovementSpeed(defender) * 0.6f;
            }
        }
        float maxAdjustValue = (cachedGetRunSpeed(attacker) + cachedGetRunSpeed(defender)) * 0.75f;
        if (adjustMin < -maxAdjustValue)
        {
            adjustMin = -maxAdjustValue;
        }
        setCachedCombatValue(attacker, cacheId, adjustMin);
        return adjustMin;
    }
    public static float cachedGetSpeedAdjustMax(obj_id attacker, obj_id defender) throws InterruptedException
    {
        String cacheId = "combat.cache." + defender + ".speedAdjustMax";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueFloat(attacker, cacheId);
        }
        float adjustMax = 0.0f;
        if (cachedGetLocomotion(attacker) == LOCOMOTION_RUNNING)
        {
            location defenderLocation = getLocation(defender);
            defenderLocation.y = getLocation(attacker).y;
            if (isFacing(attacker, defenderLocation))
            {
                adjustMax += cachedGetMovementSpeed(attacker) * 0.6f;
            }
        }
        if (cachedGetLocomotion(defender) == LOCOMOTION_RUNNING)
        {
            location attackerLocation = getLocation(attacker);
            attackerLocation.y = getLocation(defender).y;
            if (!isFacing(defender, attackerLocation))
            {
                adjustMax += cachedGetMovementSpeed(defender) * 0.6f;
            }
        }
        float maxAdjustValue = (cachedGetRunSpeed(attacker) + cachedGetRunSpeed(defender)) * 0.75f;
        if (adjustMax > maxAdjustValue)
        {
            adjustMax = maxAdjustValue;
        }
        setCachedCombatValue(attacker, cacheId, adjustMax);
        return adjustMax;
    }
    public static boolean cachedCanSee(obj_id attacker, obj_id defender) throws InterruptedException
    {
        return canSee(attacker, defender);
    }
    public static boolean cachedPvpCanAttack(obj_id attacker, obj_id defender) throws InterruptedException
    {
        String cacheId = "combat.cache." + defender + ".pvpCanAttack";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        boolean attackerCanAttack = pvpCanAttack(attacker, defender);
        setCachedCombatValue(attacker, cacheId, attackerCanAttack);
        return attackerCanAttack;
    }
    public static boolean cachedIsSameSocialGroup(obj_id attacker, obj_id defender) throws InterruptedException
    {
        String cacheId = "combat.cache." + defender + ".sameSocialGroup";
        if (hasCachedCombatValue(attacker, cacheId))
        {
            return getCachedCombatValueBool(attacker, cacheId);
        }
        String attackerSocialGroup = null;
        if (!isPlayer(attacker) && !pet_lib.isPet(attacker) && !pet_lib.isPet(defender))
        {
            attackerSocialGroup = ai_lib.getSocialGroup(attacker);
        }
        boolean sameSocialGroup = (attackerSocialGroup != null && attackerSocialGroup.equals(ai_lib.getSocialGroup(defender)));
        setCachedCombatValue(attacker, cacheId, sameSocialGroup);
        return sameSocialGroup;
    }
    public static boolean isCachedServerFrame(obj_id attacker) throws InterruptedException
    {
        int serverFrame = utils.getIntScriptVar(attacker, "combat.cache.serverFrame");
        int currentFrame = getServerFrame();
        return (serverFrame == currentFrame);
    }
    public static void setCachedServerFrame(obj_id attacker) throws InterruptedException
    {
        if (!isCachedServerFrame(attacker))
        {
            utils.removeScriptVarTree(attacker, "combat.cache");
            int currentFrame = getServerFrame();
            utils.setScriptVar(attacker, "combat.cache.serverFrame", currentFrame);
        }
    }
    public static boolean hasCachedCombatValue(obj_id attacker, String cacheId) throws InterruptedException
    {
        if (isCachedServerFrame(attacker))
        {
            return (utils.hasScriptVar(attacker, "combat.cache." + cacheId));
        }
        return false;
    }
    public static int getCachedCombatValueInt(obj_id attacker, String cacheId) throws InterruptedException
    {
        if (isCachedServerFrame(attacker))
        {
            return (utils.getIntScriptVar(attacker, "combat.cache." + cacheId));
        }
        return 0;
    }
    public static float getCachedCombatValueFloat(obj_id attacker, String cacheId) throws InterruptedException
    {
        if (isCachedServerFrame(attacker))
        {
            return (utils.getFloatScriptVar(attacker, "combat.cache." + cacheId));
        }
        return 0f;
    }
    public static boolean getCachedCombatValueBool(obj_id attacker, String cacheId) throws InterruptedException
    {
        if (isCachedServerFrame(attacker))
        {
            return (utils.getBooleanScriptVar(attacker, "combat.cache." + cacheId));
        }
        return false;
    }
    public static void setCachedCombatValue(obj_id attacker, String cacheId, int value) throws InterruptedException
    {
        setCachedServerFrame(attacker);
        utils.setScriptVar(attacker, "combat.cache." + cacheId, value);
    }
    public static void setCachedCombatValue(obj_id attacker, String cacheId, float value) throws InterruptedException
    {
        setCachedServerFrame(attacker);
        utils.setScriptVar(attacker, "combat.cache." + cacheId, value);
    }
    public static void setCachedCombatValue(obj_id attacker, String cacheId, boolean value) throws InterruptedException
    {
        setCachedServerFrame(attacker);
        utils.setScriptVar(attacker, "combat.cache." + cacheId, value);
    }
    public static void combatLog(obj_id attacker, obj_id defender, String logName, String logMsg) throws InterruptedException
    {
        
    }
    public static int getWeaponCategory(String weaponType) throws InterruptedException
    {
        if (weaponType.equals("rifle"))
        {
            return RANGED_WEAPON;
        }
        if (weaponType.equals("carbine"))
        {
            return RANGED_WEAPON;
        }
        if (weaponType.equals("pistol"))
        {
            return RANGED_WEAPON;
        }
        if (weaponType.equals("heavyweapon"))
        {
            return RANGED_WEAPON;
        }
        if (weaponType.equals("onehandmelee"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("twohandmelee"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("unarmed"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("polearm"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("thrown"))
        {
            return RANGED_WEAPON;
        }
        if (weaponType.equals("onehandlightsaber"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("twohandlightsaber"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("polearmlightsaber"))
        {
            return MELEE_WEAPON;
        }
        if (weaponType.equals("force"))
        {
            return FORCE_POWER;
        }
        return 0;
    }
    public static int getWeaponCategory(int weaponType) throws InterruptedException
    {
        if (weaponType == WEAPON_TYPE_RIFLE)
        {
            return RANGED_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_LIGHT_RIFLE)
        {
            return RANGED_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_PISTOL)
        {
            return RANGED_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_HEAVY)
        {
            return RANGED_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_1HAND_MELEE)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_2HAND_MELEE)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_UNARMED)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_POLEARM)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_THROWN)
        {
            return RANGED_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_WT_1HAND_LIGHTSABER)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_WT_2HAND_LIGHTSABER)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_WT_POLEARM_LIGHTSABER)
        {
            return MELEE_WEAPON;
        }
        if (weaponType == WEAPON_TYPE_FORCE_POWER)
        {
            return FORCE_POWER;
        }
        return 0;
    }
    public static String getWeaponStringCategory(int weaponType) throws InterruptedException
    {
        int weaponCategory = getWeaponCategory(weaponType);
        if (weaponCategory == RANGED_WEAPON)
        {
            return "ranged";
        }
        if (weaponCategory == MELEE_WEAPON)
        {
            return "melee";
        }
        if (weaponCategory == FORCE_POWER)
        {
            return "ranged";
        }
        return null;
    }
    public static String getWeaponStringCategory(String weaponType) throws InterruptedException
    {
        int weaponCategory = getWeaponCategory(weaponType);
        if (weaponCategory == RANGED_WEAPON)
        {
            return "ranged";
        }
        if (weaponCategory == MELEE_WEAPON)
        {
            return "melee";
        }
        if (weaponCategory == FORCE_POWER)
        {
            return "ranged";
        }
        return null;
    }
    public static int getWeaponType(String weaponType) throws InterruptedException
    {
        if (weaponType.equals("rifle"))
        {
            return WEAPON_TYPE_RIFLE;
        }
        if (weaponType.equals("carbine"))
        {
            return WEAPON_TYPE_LIGHT_RIFLE;
        }
        if (weaponType.equals("pistol"))
        {
            return WEAPON_TYPE_PISTOL;
        }
        if (weaponType.equals("heavyweapon"))
        {
            return WEAPON_TYPE_HEAVY;
        }
        if (weaponType.equals("onehandmelee"))
        {
            return WEAPON_TYPE_1HAND_MELEE;
        }
        if (weaponType.equals("twohandmelee"))
        {
            return WEAPON_TYPE_2HAND_MELEE;
        }
        if (weaponType.equals("unarmed"))
        {
            return WEAPON_TYPE_UNARMED;
        }
        if (weaponType.equals("polearm"))
        {
            return WEAPON_TYPE_POLEARM;
        }
        if (weaponType.equals("thrown"))
        {
            return WEAPON_TYPE_THROWN;
        }
        if (weaponType.equals("onehandlightsaber"))
        {
            return WEAPON_TYPE_WT_1HAND_LIGHTSABER;
        }
        if (weaponType.equals("twohandlightsaber"))
        {
            return WEAPON_TYPE_WT_2HAND_LIGHTSABER;
        }
        if (weaponType.equals("polearmlightsaber"))
        {
            return WEAPON_TYPE_WT_POLEARM_LIGHTSABER;
        }
        return 0;
    }
    public static int getCorrectedWeaponType(int passedType) throws InterruptedException
    {
        switch (passedType)
        {
            case WEAPON_TYPE_GROUND_TARGETTING:
            return WEAPON_TYPE_HEAVY;
            case WEAPON_TYPE_DIRECTIONAL:
            return WEAPON_TYPE_HEAVY;
        }
        return passedType;
    }
    public static String getWeaponStringType(int weaponType) throws InterruptedException
    {
        switch (weaponType)
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
            return "twohandlightsaber";
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return "polearmlightsaber";
            case WEAPON_TYPE_FORCE_POWER:
            return "force";
        }
        return "";
    }
    public static boolean isMeleeWeapon(obj_id weapon) throws InterruptedException
    {
        int weaponType = getWeaponType(weapon);
        return isMeleeWeapon(weaponType);
    }
    public static boolean isMeleeWeapon(int weaponType) throws InterruptedException
    {
        return hasBit(weaponType, B_MELEE);
    }
    public static boolean isRangedWeapon(obj_id weapon) throws InterruptedException
    {
        int weaponType = getWeaponType(weapon);
        return isRangedWeapon(weaponType);
    }
    public static boolean isHeavyWeapon(int weaponType) throws InterruptedException
    {
        return hasBit(weaponType, B_HEAVY);
    }
    public static boolean isHeavyWeapon(obj_id weapon) throws InterruptedException
    {
        int weaponType = getWeaponType(weapon);
        return hasBit(weaponType, B_HEAVY);
    }
    public static boolean isRangedWeapon(int weaponType) throws InterruptedException
    {
        return hasBit(weaponType, B_RANGED);
    }
    public static boolean isLightsaberWeapon(obj_id weapon) throws InterruptedException
    {
        int weaponType = getWeaponType(weapon);
        return isLightsaberWeapon(weaponType);
    }
    public static boolean isLightsaberWeapon(int weaponType) throws InterruptedException
    {
        return hasBit(weaponType, B_ALL_LIGHTSABERS);
    }
    public static boolean isGrenade(weapon_data weaponData) throws InterruptedException
    {
        return hasBit(weaponData.weaponType, B_THROWN);
    }
    public static boolean isHeavyWeapon(weapon_data weaponData) throws InterruptedException
    {
        return hasBit(weaponData.weaponType, B_HEAVY);
    }
    public static boolean canApplyCombatEffect(obj_id objPlayer) throws InterruptedException
    {
        if (!isIdValid(objPlayer))
        {
            return false;
        }
        if (ai_lib.aiGetNiche(objPlayer) == NICHE_DROID || vehicle.isVehicle(objPlayer) || ai_lib.isTurret(objPlayer) || ai_lib.isAndroid(objPlayer))
        {
            return false;
        }
        return true;
    }
    public static boolean hasCertification(obj_id objPlayer, obj_id objWeapon) throws InterruptedException
    {
        return hasCertification(objPlayer, objWeapon, true);
    }
    public static boolean hasCertification(obj_id objPlayer, obj_id objWeapon, boolean verbose) throws InterruptedException
    {
        boolean hasCert = true;
        if (utils.hasScriptVar(objPlayer, "combat.weaponCertified"))
        {
            obj_id lastCertified = utils.getObjIdScriptVar(objPlayer, "combat.weaponCertified");
            if (lastCertified == objWeapon)
            {
                return true;
            }
        }
        String template = getTemplateName(objWeapon);
        if (template == null)
        {
            return false;
        }
        if (template.equals("object/weapon/melee/unarmed/unarmed_default_player.iff"))
        {
            return true;
        }
        int speciesRequired = dataTableGetInt(combat.WEAPON_LEVEL_TABLE, template, "species_restriction");
        if (speciesRequired >= 0)
        {
            int playerSpecies = getSpecies(objPlayer);
            if (playerSpecies != speciesRequired)
            {
                hasCert = false;
            }
        }
        if (hasObjVar(objWeapon, "factionrestricted.rebel"))
        {
            if (!factions.isRebel(objPlayer))
            {
                hasCert = false;
            }
        }
        if (hasObjVar(objWeapon, "factionrestricted.imperial"))
        {
            if (!factions.isImperial(objPlayer))
            {
                hasCert = false;
            }
        }
        String classTemplate = getSkillTemplate(objPlayer);
        if (isLightsaberWeapon(objWeapon) && !utils.isProfession(objPlayer, utils.FORCE_SENSITIVE))
        {
            hasCert = false;
        }
        if (static_item.isDynamicItem(objWeapon))
        {
            int levelRequired = getIntObjVar(objWeapon, "dynamic_item.intLevelRequired");
            int playerLevel = getLevel(objPlayer);
            if (playerLevel < levelRequired)
            {
                hasCert = false;
            }
        }
        else if (static_item.isStaticItem(objWeapon))
        {
            dictionary itemData = static_item.getMasterItemDictionary(objWeapon);
            String skillRequired = itemData.getString("required_skill");
            if (skillRequired != null && !skillRequired.equals(""))
            {
                if (classTemplate != null && !classTemplate.equals(""))
                {
                    if (!classTemplate.startsWith(skillRequired))
                    {
                        hasCert = false;
                    }
                }
            }
            int levelRequired = itemData.getInt("required_level");
            int playerLevel = getLevel(objPlayer);
            if (playerLevel < levelRequired)
            {
                hasCert = false;
            }
        }
        else 
        {
            String skillRequired = dataTableGetString(WEAPON_LEVEL_TABLE, template, "secondary_restriction");
            if (skillRequired != null && !skillRequired.equals(""))
            {
                if (classTemplate != null && !classTemplate.equals(""))
                {
                    if (!classTemplate.startsWith(skillRequired))
                    {
                        hasCert = false;
                    }
                }
            }
            int levelRequired = -1;
            int playerLevel = getLevel(objPlayer);
            if (hasObjVar(objWeapon, weapons.OBJVAR_WP_LEVEL))
            {
                levelRequired = getIntObjVar(objWeapon, weapons.OBJVAR_WP_LEVEL);
            }
            else 
            {
                levelRequired = dataTableGetInt(WEAPON_LEVEL_TABLE, template, "weapon_level");
            }
            if (playerLevel < levelRequired)
            {
                hasCert = false;
            }
        }
        if (!hasCert && isGod(objPlayer))
        {
            hasCert = true;
        }
        if (hasCert)
        {
            utils.setScriptVar(objPlayer, "combat.weaponCertified", objWeapon);
        }
        return hasCert;
    }
    public static void applyCombatSpeedDelay(obj_id target, float delay, int duration) throws InterruptedException
    {
        int delayStampStart = getGameTime();
        int delayStampEnd = delayStampStart + duration;
        utils.setScriptVar(target, "speedDelayEffectStart", delayStampStart);
        utils.setScriptVar(target, "speedDelayEffectEnd", delayStampEnd);
        utils.setScriptVar(target, "speedDelayPotency", delay);
        return;
    }
    public static void doCombatDebuffs(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "fltNonCombatHealthRegen"))
        {
            float fltHealthRegen = getHealthRegenRate(self);
            utils.setScriptVar(self, "fltNonCombatHealthRegen", fltHealthRegen);
            setRegenRate(self, HEALTH, 0);
        }
    }
    public static boolean clearCombatDebuffs(obj_id self) throws InterruptedException
    {
        if (getGameTime() > utils.getIntScriptVar(self, "incap.timeStamp"))
        {
            if (utils.hasScriptVar(self, "fltNonCombatHealthRegen"))
            {
                float fltHealthRegen = utils.getFloatScriptVar(self, "fltNonCombatHealthRegen");
                setRegenRate(self, HEALTH, fltHealthRegen);
                utils.removeScriptVar(self, "fltNonCombatHealthRegen");
                return true;
            }
        }
        return false;
    }
    public static boolean isInCombat(obj_id objTarget) throws InterruptedException
    {
        return getState(objTarget, STATE_COMBAT) > 0 ? true : false;
    }
    public static String getAttackName(obj_id self) throws InterruptedException
    {
        String actionName = null;
        obj_id weapon = getCurrentWeapon(self);
        int weaponType = getWeaponType(weapon);
        if (utils.hasScriptVar(self, DO_ONCE_ATTACK_OVERRIDE))
        {
            actionName = utils.getStringScriptVar(self, DO_ONCE_ATTACK_OVERRIDE);
            utils.removeScriptVar(self, DO_ONCE_ATTACK_OVERRIDE);
            return actionName;
        }
        if (utils.hasScriptVar(self, DEFAULT_ATTACK_OVERRIDE))
        {
            return utils.getStringScriptVar(self, DEFAULT_ATTACK_OVERRIDE);
        }
        if (hasObjVar(self, DEFAULT_ATTACK_OVERRIDE))
        {
            LOG("combat.scriptlib", "gcw getStringObjVar(self, DEFAULT_ATTACK_OVERRIDE): " + getStringObjVar(self, DEFAULT_ATTACK_OVERRIDE));
            return getStringObjVar(self, DEFAULT_ATTACK_OVERRIDE);
        }
        if ((getTemplateName(weapon)).equals("object/weapon/ranged/droid/droid_flamethrower.iff"))
        {
            actionName = "ig88_flame_thrower";
        }
        else if ((getTemplateName(weapon)).equals("object/weapon/ranged/droid/droid_flamethrower_nomuzzle.iff"))
        {
            actionName = "ig88_flame_thrower_vertical";
        }
        else if ((getTemplateName(weapon)).equals("object/weapon/ranged/droid/droid_rocket_launcher.iff"))
        {
            actionName = "ig88_rocket_launcher";
        }
        else if (combat.isRangedWeapon(weaponType))
        {
            if (ai_lib.isHumanoid(self))
            {
                actionName = "rangedShot";
            }
            else 
            {
                actionName = "creatureRangedAttack";
            }
        }
        else if (combat.isMeleeWeapon(weaponType))
        {
            if (ai_lib.isHumanoid(self))
            {
                actionName = "meleeHit";
            }
            else 
            {
                actionName = "creatureMeleeAttack";
            }
        }
        else if (combat.isLightsaberWeapon(weaponType))
        {
            if (ai_lib.isHumanoid(self))
            {
                actionName = "saberHit";
            }
            else 
            {
                actionName = "creatureMeleeAttack";
            }
        }
        return actionName;
    }
    public static String getBestAction(obj_id player, String actionName) throws InterruptedException
    {
        for (int i = 2; i > 0; i--)
        {
            String testCmd = actionName + "_" + i;
            if (hasCommand(player, testCmd))
            {
                return testCmd;
            }
        }
        return actionName;
    }
    public static String getActionAnimation(combat_data actionData, String weaponType) throws InterruptedException
    {
        String animation = "";
        if (weaponType.equals("unarmed"))
        {
            animation = actionData.anim_unarmed;
        }
        else if (weaponType.equals("onehandmelee"))
        {
            animation = actionData.anim_onehandmelee;
        }
        else if (weaponType.equals("twohandmelee"))
        {
            animation = actionData.anim_twohandmelee;
        }
        else if (weaponType.equals("polearm"))
        {
            animation = actionData.anim_polearm;
        }
        else if (weaponType.equals("lightRifle"))
        {
            animation = actionData.anim_lightRifle;
        }
        else if (weaponType.equals("pistol"))
        {
            animation = actionData.anim_pistol;
        }
        else if (weaponType.equals("carbine"))
        {
            animation = actionData.anim_carbine;
        }
        else if (weaponType.equals("rifle"))
        {
            animation = actionData.anim_rifle;
        }
        else if (weaponType.equals("heavyweapon"))
        {
            animation = actionData.anim_heavyweapon;
        }
        else if (weaponType.equals("thrown"))
        {
            animation = actionData.anim_thrown;
        }
        else if (weaponType.equals("thrown"))
        {
            animation = actionData.anim_thrown;
        }
        else if (weaponType.equals("onehandlightsaber"))
        {
            animation = actionData.anim_onehandlightsaber;
        }
        else if (weaponType.equals("twohandlightsaber"))
        {
            animation = actionData.anim_twohandlightsaber;
        }
        else if (weaponType.equals("polearmlightsaber"))
        {
            animation = actionData.anim_polearmlightsaber;
        }
        if (animation.length() < 1)
        {
            animation = actionData.animDefault;
        }
        if (animation.startsWith("*creature_attack"))
        {
            return getCreatureAnimationName(animation);
        }
        String[] anims = split(animation, ',');
        if (anims.length > 1)
        {
            animation = anims[rand(0, anims.length - 1)];
        }
        return animation;
    }
    public static String getCreatureAnimationName(String playbackName) throws InterruptedException
    {
        obj_id self = getSelf();
        String animName = "";
        int niche = ai_lib.aiGetNiche(self);
        if (niche == NICHE_VEHICLE)
        {
            animName = "droid_attack";
        }
        else 
        {
            if (playbackName.endsWith("ranged"))
            {
                animName = "creature_attack_ranged";
            }
            else if (playbackName.endsWith("melee"))
            {
                if (rand(1, 2) == 1)
                {
                    animName = "creature_attack";
                }
                else 
                {
                    animName = "creature_attack_special_1";
                }
                if (rand(1, 2) == 1)
                {
                    animName += "_light";
                }
                else 
                {
                    animName += "_heavy";
                }
                return animName;
            }
            else 
            {
                animName = "creature_attack_special_" + rand(1, 2);
            }
        }
        animName += "_medium";
        return animName;
    }
    public static obj_id makeTrackerEgg(obj_id objPlayer, location locTest, combat_data actionData) throws InterruptedException
    {
        obj_id objEgg = createObject(actionData.delayAttackEggTemplate, locTest);
        if (!isIdValid(objEgg))
        {
            return null;
        }
        dictionary combatDict = combat_engine.getCombatDataDictionary(actionData);
        utils.setScriptVar(objEgg, "combatDataDict", combatDict);
        utils.setScriptVar(objEgg, "objOwner", objPlayer);
        utils.setScriptVar(objPlayer, "objEgg", objEgg);
        attachScript(objEgg, "systems.combat.combat_delayed_tracker");
        return objEgg;
    }
    public static int makeBit(int bit) throws InterruptedException
    {
        return (1 << (bit - 1));
    }
    public static boolean hasBit(int weaponType, int bitValue) throws InterruptedException
    {
        if (weaponType == -1)
        {
            return false;
        }
        return ((weaponTypeToBitValueMap[weaponType] & bitValue) != 0);
    }
    public static float doCriticalHitEffect(attacker_data attackerData, defender_data defenderData, weapon_data weaponData, hit_result hitData, combat_data actionData) throws InterruptedException
    {
        int elementalType = weaponData.elementalType;
        float elementalDamage = weaponData.elementalValue;
        float damageMod = 1.5f;
        hitData.critDamage = 2;
        int critResist = 0;
        float heatResist = 0;
        float coldResist = 0;
        float acidResist = 0;
        float electricResist = 0;
        int resistRoll = rand(1, 10000);
        if (actionData.percentAddFromWeapon > 0.0f)
        {
            if (utils.hasScriptVar(defenderData.id, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION))
            {
                critResist += utils.getIntScriptVar(defenderData.id, armor.SCRIPTVAR_CACHED_GENERAL_PROTECTION);
            }
            if (utils.hasScriptVar(defenderData.id, armor.SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS))
            {
                dictionary protections = utils.getDictionaryScriptVar(defenderData.id, armor.SCRIPTVAR_CACHED_SPECIAL_PROTECTIONS);
                heatResist = protections.getFloat("heat");
                coldResist = protections.getFloat("cold");
                acidResist = protections.getFloat("acid");
                electricResist = protections.getFloat("electricity");
            }
            switch (elementalType)
            {
                case DAMAGE_ELEMENTAL_HEAT:
                if (resistRoll > critResist + heatResist)
                {
                    int level = getLevel(attackerData.id);
                    if (level == 90)
                    {
                        buff.applyBuff(defenderData.id, attackerData.id, "crit_fire_dot_4");
                    }
                    else if (level >= 75)
                    {
                        buff.applyBuff(defenderData.id, attackerData.id, "crit_fire_dot_3");
                    }
                    else if (level >= 50)
                    {
                        buff.applyBuff(defenderData.id, attackerData.id, "crit_fire_dot_2");
                    }
                    else if (level >= 25)
                    {
                        buff.applyBuff(defenderData.id, attackerData.id, "crit_fire_dot_1");
                    }
                    damageMod = 1.5f;
                    hitData.critDamage = 3;
                    return damageMod;
                }
                break;
                case DAMAGE_ELEMENTAL_COLD:
                if (resistRoll > critResist + coldResist && !buff.hasBuff(defenderData.id, "criticalImmunity"))
                {
                    if (!buff.hasBuff(defenderData.id, "criticalColdEffect"))
                    {
                        buff.applyBuff(defenderData.id, "criticalColdEffect", 6, (float)elementalDamage / 10.0f);
                    }
                    damageMod = 1.5f;
                    hitData.critDamage = 4;
                    return damageMod;
                }
                break;
                case DAMAGE_ELEMENTAL_ACID:
                if (resistRoll > critResist + acidResist && !buff.hasBuff(defenderData.id, "criticalAcidEffect") && !buff.hasBuff(defenderData.id, "criticalImmunity"))
                {
                    buff.applyBuff(defenderData.id, "criticalAcidEffect");
                    damageMod = 1.5f;
                    hitData.critDamage = 5;
                    return damageMod;
                }
                break;
                case DAMAGE_ELEMENTAL_ELECTRICAL:
                if (resistRoll > critResist + electricResist && !buff.hasBuff(defenderData.id, "criticalElectricEffect") && !buff.hasBuff(defenderData.id, "criticalImmunity"))
                {
                    buff.applyBuff(defenderData.id, "criticalElectricEffect");
                    damageMod = 1.5f;
                    hitData.critDamage = 6;
                    return damageMod;
                }
                break;
            }
        }
        return damageMod;
    }
    public static color getCriticalColor(int hitDataCritDamage) throws InterruptedException
    {
        color criticalColor = colors.MEDIUMVIOLETRED;
        switch (hitDataCritDamage)
        {
            case 1:
            criticalColor = colors.SLATEGREY;
            break;
            case 3:
            criticalColor = colors.ORANGERED;
            break;
            case 4:
            criticalColor = colors.LIGHTSKYBLUE;
            break;
            case 5:
            criticalColor = colors.DARKOLIVEGREEN;
            break;
            case 6:
            criticalColor = colors.SILVER;
            break;
        }
        return criticalColor;
    }
    public static obj_id directDamageToDifferentTarget(obj_id attacker, obj_id defender) throws InterruptedException
    {
        if (buff.hasBuff(defender, "bm_shield_master_player"))
        {
            obj_id guardian = beast_lib.getBeastOnPlayer(defender);
            if (isIdValid(guardian) && exists(guardian) && !isDead(guardian) && !isIncapacitated(guardian) && getEnhancedSkillStatisticModifierUncapped(defender, "damage_immune") <= 0)
            {
                return guardian;
            }
        }
        if (utils.hasScriptVar(defender, DAMAGE_REDIRECT))
        {
            obj_id guardian = utils.getObjIdScriptVar(defender, DAMAGE_REDIRECT);
            if (isIdValid(guardian) && exists(guardian) && !isMob(guardian) && getEnhancedSkillStatisticModifierUncapped(defender, "damage_immune") <= 0)
            {
                return guardian;
            }
            if (isIdValid(guardian) && exists(guardian) && isMob(guardian) && !isDead(guardian) && !isIncapacitated(guardian) && getEnhancedSkillStatisticModifierUncapped(defender, "damage_immune") <= 0 && pvpCanAttack(attacker, guardian))
            {
                return guardian;
            }
            else 
            {
                utils.removeScriptVar(defender, DAMAGE_REDIRECT);
            }
        }
        return defender;
    }
    public static float getMissChance(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        float missChance = isPlayer(attacker) ? 0.0f : 5.0f;
        if (autoAim && isRangedWeapon(getCurrentWeapon(attacker)))
        {
            missChance += 5.0f;
            boolean attackerMovePenalty = (getLocomotion(attacker) == LOCOMOTION_RUNNING && isRangedWeapon(getCurrentWeapon(attacker)));
            boolean defenderMovePenalty = (getLocomotion(defender) == LOCOMOTION_RUNNING && isRangedWeapon(getCurrentWeapon(defender)));
            if (isPlayer(attacker) && attackerMovePenalty)
            {
                missChance += 5.0f;
            }
            if (defenderMovePenalty)
            {
                missChance += 3.0f;
            }
        }
        if (isRangedWeapon(getCurrentWeapon(attackerData.id)))
        {
            missChance += ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_ranged_attack_avoidance") / 10.0f);
            missChance -= ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_ranged_attack_vulnerability") / 10.0f);
        }
        if (isMeleeWeapon(getCurrentWeapon(attackerData.id)) || isLightsaberWeapon(getCurrentWeapon(attackerData.id)))
        {
            missChance += ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_melee_attack_avoidance") / 10.0f);
            missChance -= ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_melee_attack_vulnerability") / 10.0f);
        }
        missChance += getAttackerMissChance(attackerData, actionData, autoAim);
        missChance += getDefenderMissChance(defenderData, actionData, autoAim);
        missChance -= attackerData.hitChance;
        missChance += defenderData.increaseMiss;
        return missChance;
    }
    public static float getAttackerMissChance(attacker_data attackerData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        float attackerMissChance = 0.0f;
        if (isRangedWeapon(getCurrentWeapon(attackerData.id)))
        {
            attackerMissChance += ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_ranged_attack_miss") / 10.0f);
            attackerMissChance -= ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_ranged_attack_miss_reduction") / 10.0f);
        }
        if (isMeleeWeapon(getCurrentWeapon(attackerData.id)) || isLightsaberWeapon(getCurrentWeapon(attackerData.id)))
        {
            attackerMissChance += ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_melee_attack_miss") / 10.0f);
            attackerMissChance -= ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_melee_attack_miss_reduction") / 10.0f);
        }
        attackerMissChance += ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_all_attack_miss") / 10.0f);
        attackerMissChance -= ((float)getEnhancedSkillStatisticModifierUncapped(attackerData.id, "combat_all_attack_miss_reduction") / 10.0f);
        return attackerMissChance;
    }
    public static float getDefenderMissChance(defender_data defenderData, combat_data actionData, boolean autoAim) throws InterruptedException
    {
        float defenderMissChance = 0.0f;
        defenderMissChance += ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_all_attack_avoidance") / 10.0f);
        defenderMissChance -= ((float)getEnhancedSkillStatisticModifierUncapped(defenderData.id, "combat_all_attack_miss_vulnerability") / 10.0f);
        return defenderMissChance;
    }
    public static float getGlancingBlowChance(attacker_data attackerData, defender_data defenderData, combat_data actionData) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        float glancingChance = 0.0f;
        if (isMob(defender) && beast_lib.isBeast(defender))
        {
            glancingChance += (int)beast_lib.getBeastGlanceChance(defender);
        }
        if ((isPlayer(attacker) || beast_lib.isBeast(attacker)) && (!isPlayer(defender) && !beast_lib.isBeast(defender)))
        {
            int attackerLevel = getLevel(attacker);
            int defenderLevel = getLevel(defender);
            int difficultyClass = getIntObjVar(defender, "difficultyClass");
            if (attackerLevel < defenderLevel)
            {
                glancingChance = 5.0f * (float)(defenderLevel - attackerLevel);
            }
            switch (difficultyClass)
            {
                case 1:
                if (glancingChance < 5.0f)
                {
                    glancingChance = 5.0f;
                }
                break;
                case 2:
                if (glancingChance < 15.0f)
                {
                    glancingChance = 15.0f;
                }
            }
        }
        if ((!isPlayer(attacker) && !beast_lib.isBeast(attacker)) && (isPlayer(defender) || beast_lib.isBeast(defender)))
        {
            int attackerLevel = getLevel(attacker);
            int defenderLevel = getLevel(defender);
            defenderLevel = Math.round((float)defenderLevel * 0.75f);
            if (attackerLevel < defenderLevel)
            {
                glancingChance = 2.0f * (float)(defenderLevel - attackerLevel);
            }
        }
        glancingChance += defenderData.glancingChance;
        glancingChance -= attackerData.reduceGlancing;
        boolean isRangedAttacker = isRangedWeapon(getCurrentWeapon(attacker));
        boolean isRangedDefender = isRangedWeapon(getCurrentWeapon(defender));
        if (isRangedAttacker)
        {
            glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_glancing_blow_ranged");
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_glancing_blow_ranged_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        if (!isRangedAttacker)
        {
            glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_glancing_blow_melee");
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_glancing_blow_melee_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        glancingChance -= actionData.reduceGlancing;
        if (beast_lib.isBeast(defender))
        {
            LOG("beastDamage", "glancingChance: " + glancingChance);
        }
        return glancingChance;
    }
    public static float getDefenderGlancingBlowChance(obj_id defender) throws InterruptedException
    {
        float glancingChance = 0.0f;
        boolean isRangedDefender = isRangedWeapon(getCurrentWeapon(defender));
        if (!isRangedDefender)
        {
            glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_glancing_blow_melee_defense");
        }
        glancingChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_glancing_blow_all");
        glancingChance += (float)utils.getIntScriptVar(defender, "expertise_stance_glance");
        glancingChance += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(defender, "combat_glancing"), DR_DEFENDER_GLANCE) / 10.0f);
        return glancingChance;
    }
    public static float getAttackerGlancingReduction(obj_id attacker) throws InterruptedException
    {
        float glancingReduce = 0.0f;
        if (beast_lib.isBeastMaster(attacker) || beast_lib.isBeast(attacker))
        {
            glancingReduce += beast_lib.getBeastModGlancingReduction(attacker);
        }
        glancingReduce += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "combat_glancing_blow_reduction") / 7.0f);
        glancingReduce += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_glancing_blow_reduction"));
        glancingReduce -= (float)getEnhancedSkillStatisticModifierUncapped(attacker, "glancing_blow_vulnerable");
        return glancingReduce;
    }
    public static float getPunishingBlowChance(attacker_data attackerData, defender_data defenderData) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        float punishingChance = 0.0f;
        if ((!isPlayer(attacker) && !beast_lib.isBeast(attacker)) && (isPlayer(defender) || beast_lib.isBeast(defender)))
        {
            int attackerLevel = getLevel(attacker);
            int defenderLevel = getLevel(defender);
            if (attackerLevel > defenderLevel)
            {
                punishingChance = 5.0f * (float)(attackerLevel - defenderLevel);
            }
            int difficultyClass = getIntObjVar(attacker, "difficultyClass");
            switch (difficultyClass)
            {
                case 1:
                if (punishingChance < 5.0f)
                {
                    punishingChance = 5.0f;
                }
                break;
                case 2:
                if (punishingChance < 15.0f)
                {
                    punishingChance = 15.0f;
                }
            }
        }
        punishingChance -= defenderData.reducePunishing;
        return punishingChance;
    }
    public static float getDefenderPunishingBlowReduction(obj_id defender) throws InterruptedException
    {
        float punishingChance = 0.0f;
        if (beast_lib.isBeastMaster(defender) || beast_lib.isBeast(defender))
        {
            punishingChance += beast_lib.getBeastModPunishingReduction(defender);
        }
        return punishingChance;
    }
    public static float getDodgeChance(attacker_data attackerData, defender_data defenderData, combat_data actionData) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        float dodgeChance = 0.0f;
        dodgeChance += defenderData.dodgeChance;
        dodgeChance -= attackerData.reduceDodge;
        dodgeChance -= actionData.reduceDodge;
        boolean isRangedAttacker = isRangedWeapon(getCurrentWeapon(attacker));
        if (isRangedAttacker)
        {
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                dodgeChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_ranged_dodge_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        if (!isRangedAttacker)
        {
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                dodgeChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_melee_dodge_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        return dodgeChance;
    }
    public static float getDefenderDodgeChance(obj_id player) throws InterruptedException
    {
        float dodgeChance = isPlayer(player) || beast_lib.isBeast(player) ? 5.0f : (float)getLevel(player) * 0.10f;
        dodgeChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "agility_modified") / 100.0f);
        dodgeChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "agility") / 100.0f);
        dodgeChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck_modified") / 300.0f);
        dodgeChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck") / 300.0f);
        dodgeChance += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_dodge"), DR_DEFENDER_DODGE) / 10.0f);
        dodgeChance += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_dodge");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            dodgeChance += beast_lib.getBeastDodgeChance(player);
        }
        return dodgeChance;
    }
    public static float getAttackerDodgeReduction(obj_id attacker) throws InterruptedException
    {
        float dodgeReduction = 0.0f;
        dodgeReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_dodge_reduction"));
        dodgeReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "exotic_dodge_reduction") / 7.0f);
        dodgeReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "combat_dodge_reduction") / 7.0f);
        return dodgeReduction;
    }
    public static float getParryChance(attacker_data attackerData, defender_data defenderData, combat_data actionData) throws InterruptedException
    {
        float parryChance = 0.0f;
        parryChance += defenderData.parryChance;
        parryChance -= attackerData.reduceParry;
        parryChance -= actionData.reduceParry;
        return parryChance;
    }
    public static float getDefenderParryChance(obj_id player) throws InterruptedException
    {
        float parrySkill = isPlayer(player) || beast_lib.isBeast(player) ? 5.0f : (float)getLevel(player) * 0.09f;
        parrySkill += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_parry"), DR_DEFENDER_PARRY) / 10.0f);
        parrySkill += ((float)getEnhancedSkillStatisticModifierUncapped(player, "precision_modified") / 200.0f);
        parrySkill += ((float)getSkillStatisticModifier(player, "precision") / 200.0f);
        parrySkill += ((float)getEnhancedSkillStatisticModifierUncapped(player, "agility_modified") / 200.0f);
        parrySkill += ((float)getSkillStatisticModifier(player, "agility") / 200.0f);
        parrySkill += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_parry");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            parrySkill += beast_lib.getBeastParryChance(player);
        }
        parrySkill += (float)getSkillStatisticModifier(player, "expertise_stance_saber_block");
        boolean isSaberBlocking = buff.hasBuff(player, "saberBlock");
        if (isSaberBlocking)
        {
            float enhancedSaberBlockSkillMod = (float)getSkillStatisticModifier(player, "expertise_saber_block");
            parrySkill += enhancedSaberBlockSkillMod;
        }
        parrySkill = parrySkill > 75.0f ? 75.0f : parrySkill;
        return parrySkill;
    }
    public static float getAttackerParryReduction(obj_id attacker) throws InterruptedException
    {
        float parryReduction = 0.0f;
        parryReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_parry_reduction"));
        parryReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "exotic_parry_reduction") / 7.0f);
        parryReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "combat_parry_reduction") / 7.0f);
        return parryReduction;
    }
    public static float getBlockChance(attacker_data attackerData, defender_data defenderData, combat_data actionData) throws InterruptedException
    {
        float blockChance = 0.0f;
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        blockChance += defenderData.blockChance;
        blockChance -= attackerData.reduceBlock;
        blockChance -= actionData.reduceBlock;
        boolean isRangedAttacker = isRangedWeapon(getCurrentWeapon(attacker));
        if (isRangedAttacker)
        {
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                blockChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_ranged_block_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        if (!isRangedAttacker)
        {
            if (armor.hasExpertiseArmorSetBonus(defender))
            {
                blockChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_armorset_melee_block_" + armor.getExpertiseArmorSetId(defender));
            }
        }
        return blockChance;
    }
    public static float getDefenderBlockChance(obj_id player) throws InterruptedException
    {
        float blockChance = isPlayer(player) ? 5.0f : (float)getLevel(player) * 0.14f;
        blockChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "precision_modified") / 200.0f);
        blockChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "precision") / 200.0f);
        blockChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "strength_modified") / 200.0f);
        blockChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "strength") / 200.0f);
        blockChance += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_block_chance"), DR_DEFENDER_BLOCK) / 5.0f);
        blockChance += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_block_chance");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            blockChance += beast_lib.getBeastBlockChance(player);
        }
        if (blockChance > 30.0f)
        {
            int nextBlockLog = utils.getIntScriptVar(player, "nextBlockLog");
            if (getGameTime() > nextBlockLog)
            {
                float fromBlockChance = (float)getEnhancedSkillStatisticModifierUncapped(player, "combat_block_chance") / 5.0f;
                float fromBlockChanceDiminished = (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_block_chance"), DR_DEFENDER_BLOCK) / 5.0f);
                float fromPreMod = (float)getEnhancedSkillStatisticModifierUncapped(player, "precision_modified") / 200.0f;
                float fromPre = (float)getEnhancedSkillStatisticModifierUncapped(player, "precision") / 200.0f;
                float fromStrMod = (float)getEnhancedSkillStatisticModifierUncapped(player, "strength_modified") / 200.0f;
                float fromStr = (float)getEnhancedSkillStatisticModifierUncapped(player, "strength") / 200.0f;
                float fromExChance = (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_block_chance");
                float fromBeastMod = 0.0f;
                if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
                {
                    fromBeastMod += beast_lib.getBeastBlockChance(player);
                }
                utils.setScriptVar(player, "nextBlockLog", getGameTime() + 600);
                CustomerServiceLog("BLOCK_CHANCE", "Player %TU total block chance: 5% + " + blockChance + " = combat_block_chance(" + fromBlockChance + "/ DRF: " + fromBlockChanceDiminished + ") + precision_modified(" + fromPreMod + ") + precision(" + fromPre + ") + strength_modified(" + fromStrMod + ") + strength(" + fromStr + ") + Expertise Block(" + fromExChance + ") + beast_mod(" + fromBeastMod + ")", player);
            }
        }
        return blockChance;
    }
    public static float getAttackerBlockReduction(obj_id attacker) throws InterruptedException
    {
        float blockReduction = 0.0f;
        blockReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_block_reduction"));
        blockReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "exotic_block_reduction") / 5.0f);
        blockReduction += (float)(getEnhancedSkillStatisticModifierUncapped(attacker, "combat_block_reduction") / 5.0f);
        return blockReduction;
    }
    public static int getBlockAmmount(obj_id player) throws InterruptedException
    {
        int blockAmmount = isPlayer(player) || beast_lib.isBeast(player) ? 25 : Math.round((getLevel(player) * 2));
        float blockStat = 0.0f;
        blockStat += (float)getEnhancedSkillStatisticModifierUncapped(player, "strength_modified");
        blockStat += (float)getSkillStatisticModifier(player, "strength");
        blockStat += (float)getEnhancedSkillStatisticModifierUncapped(player, "combat_block_value");
        blockStat = Math.round((float)blockStat * 0.5f);
        blockAmmount += blockStat;
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            blockAmmount += beast_lib.getBeastBlockValue(player);
        }
        return blockAmmount;
    }
    public static float getEvasionChance(attacker_data attackerData, defender_data defenderData) throws InterruptedException
    {
        float evasionChance = 0.0f;
        evasionChance += defenderData.evadeChance;
        evasionChance -= attackerData.reduceEvade;
        return evasionChance;
    }
    public static float getDefenderEvasionChance(obj_id player) throws InterruptedException
    {
        float evasionChance = isPlayer(player) || beast_lib.isBeast(player) ? 5.0f : ((float)getLevel(player) * 0.2f);
        evasionChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "agility_modified") / 100.0f);
        evasionChance += ((float)getSkillStatisticModifier(player, "agility") / 100.0f);
        evasionChance += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck_modified") / 300.0f);
        evasionChance += ((float)getSkillStatisticModifier(player, "luck") / 300.0f);
        evasionChance += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_evasion_chance"), DR_DEFENDER_EVADE) / 10.0f);
        evasionChance += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_evasion_chance");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            evasionChance += beast_lib.getBeastEvadeChance(player);
        }
        return evasionChance;
    }
    public static float getAttackerEvasionReduction(obj_id attacker) throws InterruptedException
    {
        return 0.0f;
    }
    public static float getHitReductionChance(obj_id player) throws InterruptedException
    {
        return getEnhancedSkillStatisticModifierUncapped(player, "expertise_critical_hit_reduction");
    }
    public static float getPvPHitReductionChance(obj_id player) throws InterruptedException
    {
        return getEnhancedSkillStatisticModifierUncapped(player, "expertise_critical_hit_pvp_reduction");
    }
    public static float getEvasionRoll(obj_id player) throws InterruptedException
    {
        float evasionRoll = 0.0f;
        evasionRoll += ((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_evasion_value") / 4.0f);
        if (evasionRoll < 0)
        {
            return -1.0f;
        }
        evasionRoll += 10.0f;
        evasionRoll += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck_modified") / 10.0f);
        evasionRoll += ((float)getSkillStatisticModifier(player, "luck") / 10.0f);
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            evasionRoll += beast_lib.getBeastEvadeValue(player);
        }
        return evasionRoll > 99.0f ? 99.0f : evasionRoll;
    }
    public static float getCriticalHitChance(attacker_data attackerData, defender_data defenderData, combat_data actionData, boolean autoAiming) throws InterruptedException
    {
        obj_id attacker = attackerData.id;
        obj_id defender = defenderData.id;
        float critChance = attackerData.critChance;
        float altCritChance = 0.0f;
        String specialLine = actionData.specialLine;
        critChance += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_line_" + specialLine);
        if (!autoAiming && isPlayer(attacker))
        {
            critChance += 3.0f;
        }
        float specificDefenderMod = 0.0f;
        if (isPlayer(defender))
        {
            altCritChance += attackerData.pvpCrit;
        }
        else if (ai_lib.isDroid(defender) || ai_lib.isAndroid(defender))
        {
            altCritChance += attackerData.droidCrit;
        }
        else if (ai_lib.isNpc(defender))
        {
            altCritChance += attackerData.npcCrit;
        }
        else if (ai_lib.isMonster(defender))
        {
            altCritChance += attackerData.creatureCrit;
        }
        altCritChance = getDiminishedReturnValue(altCritChance, DR_ATTACKER_CRITICAL_2);
        critChance -= defenderData.reduceCritical;
        if (isPlayer(defender) && (isPlayer(attacker) || (isMob(attacker) && isIdValid(getMaster(attacker)) && isPlayer(getMaster(attacker)))))
        {
            critChance -= (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_critical_hit_pvp_reduction");
        }
        critChance += actionData.increaseCritical;
        critChance += altCritChance;
        return critChance;
    }
    public static float getAttackerCritMod(obj_id attacker) throws InterruptedException
    {
        float critMod = isPlayer(attacker) ? 5.0f : (float)getLevel(attacker) * 0.15f;
        float dr_critMod = 0.0f;
        float dr_critMod2 = 0.0f;
        critMod += (float)utils.getIntScriptVar(attacker, "expertise_stance_critical");
        critMod += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_niche_all");
        critMod += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(attacker, "combat_critical_hit"), DR_ATTACKER_CRITICAL) / 10.0f);
        if (beast_lib.isBeastMaster(attacker) || beast_lib.isBeast(attacker))
        {
            critMod += beast_lib.getBeastCritChance(attacker);
        }
        critMod += ((float)getEnhancedSkillStatisticModifierUncapped(attacker, "precision_modified") / 100.0f);
        critMod += ((float)getSkillStatisticModifier(attacker, "precision") / 100.0f);
        critMod += ((float)getEnhancedSkillStatisticModifierUncapped(attacker, "luck_modified") / 300.0f);
        critMod += ((float)getSkillStatisticModifier(attacker, "luck") / 300.0f);
        obj_id weapon = getCurrentWeapon(attacker);
        if (!isIdValid(weapon))
        {
            return critMod;
        }
        weapon_data weaponData = weapons.getNewWeaponData(weapon);
        if (weaponData == null)
        {
            CustomerServiceLog("COMBAT_exception", "getAttackerCritMod: Player %TU caused an exception with bad cached data on weapon " + weapon + "/" + getTemplateName(weapon), attacker);
        }
        boolean isRangedAttacker = isRangedWeapon(weapon);
        if (isRangedAttacker)
        {
            dr_critMod2 += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_ranged");
            dr_critMod += ((float)getEnhancedSkillStatisticModifierUncapped(attacker, "combat_critical_ranged") / 10.0f);
            critMod += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_ranged");
        }
        else 
        {
            dr_critMod2 += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_melee");
            dr_critMod += ((float)getEnhancedSkillStatisticModifierUncapped(attacker, "combat_critical_melee") / 10.0f);
            critMod += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_melee");
        }
        if(weaponData != null) {
            switch (weaponData.weaponType) {
                case WEAPON_TYPE_PISTOL:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_pistol");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_pistol");
                    break;
                case WEAPON_TYPE_RIFLE:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_rifle");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_rifle");
                    break;
                case WEAPON_TYPE_LIGHT_RIFLE:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_carbine");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_carbine");
                    break;
                case WEAPON_TYPE_HEAVY:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_heavy");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_heavy");
                    break;
                case WEAPON_TYPE_GROUND_TARGETTING:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_heavy");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_heavy");
                    break;
                case WEAPON_TYPE_DIRECTIONAL:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_heavy");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_heavy");
                    break;
                case WEAPON_TYPE_1HAND_MELEE:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_1h");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_1h");
                    break;
                case WEAPON_TYPE_2HAND_MELEE:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_2h");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_2h");
                    break;
                case WEAPON_TYPE_UNARMED:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_unarmed");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_unarmed");
                    break;
                case WEAPON_TYPE_POLEARM:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_polearm");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_polearm");
                    break;
                case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
                case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
                    dr_critMod2 += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_lightsaber");
                    critMod += (float) getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_undiminished_critical_lightsaber");
                    break;
            }
        }
        dr_critMod = getDiminishedReturnValue(dr_critMod, DR_ATTACKER_CRITICAL);
        dr_critMod2 = getDiminishedReturnValue(dr_critMod2, DR_ATTACKER_CRITICAL_2);
        critMod += dr_critMod;
        critMod += dr_critMod2;
        return critMod;
    }
    public static float getDefenderCriticalChance(obj_id defender) throws InterruptedException
    {
        float critChance = 0.0f;
        critChance -= (float)getEnhancedSkillStatisticModifierUncapped(defender, "critical_hit_vulnerable");
        critChance += ((float)getEnhancedSkillStatisticModifierUncapped(defender, "combat_critical_hit_reduction") / 3.0f);
        critChance += (float)getEnhancedSkillStatisticModifierUncapped(defender, "expertise_critical_hit_reduction");
        return critChance;
    }
    public static float getStrikethroughChance(attacker_data attackerData, defender_data defenderData, combat_data actionData) throws InterruptedException
    {
        float strikethroughChance = 0.0f;
        strikethroughChance += attackerData.strikethroughChance;
        strikethroughChance -= defenderData.reduceStrikethrough;
        strikethroughChance += actionData.increaseStrikethrough;
        return strikethroughChance;
    }
    public static float getAttackerStrikethroughChance(obj_id player) throws InterruptedException
    {
        float strikethrough = isPlayer(player) || beast_lib.isBeast(player) ? 5.0f : (float)getLevel(player) * 0.08f;
        strikethrough += ((float)getEnhancedSkillStatisticModifierUncapped(player, "precision_modified") / 200.0f);
        strikethrough += ((float)getSkillStatisticModifier(player, "precision") / 200.0f);
        strikethrough += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck_modified") / 200.0f);
        strikethrough += ((float)getSkillStatisticModifier(player, "luck") / 200.0f);
        strikethrough += (getDiminishedReturnValue((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_strikethrough_chance"), DR_ATTACKER_STRIKETHROUGH) / 10.0f);
        strikethrough += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_strikethrough_chance");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            strikethrough += beast_lib.getBeastStrikethroughChance(player);
        }
        return strikethrough;
    }
    public static float getDefenderStrikethroughReduction(obj_id player) throws InterruptedException
    {
        float strikethroughChance = 0.0f;
        strikethroughChance -= (float)getEnhancedSkillStatisticModifierUncapped(player, "strikethrough_vulnerable");
        return strikethroughChance;
    }
    public static float getStrikethroughValue(obj_id attacker, obj_id defender) throws InterruptedException
    {
        return getAttackerStrikethroughValue(attacker);
    }
    public static float getAttackerStrikethroughValue(obj_id player) throws InterruptedException
    {
        float strikethroughValue = isPlayer(player) ? 10.0f : 35.0f;
        strikethroughValue += ((float)getEnhancedSkillStatisticModifierUncapped(player, "luck_modified") / 10.0f);
        strikethroughValue += ((float)getSkillStatisticModifier(player, "luck") / 10.0f);
        strikethroughValue += ((float)getEnhancedSkillStatisticModifierUncapped(player, "combat_strikethrough_value") / 2.0f);
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            strikethroughValue += beast_lib.getBeastStrikethroughValue(player);
        }
        return strikethroughValue > 99.0f ? 99.0f : strikethroughValue;
    }
    public static float getToHitBonus(obj_id player) throws InterruptedException
    {
        float hitBonus = 0.0f;
        hitBonus += ((float)getEnhancedSkillStatisticModifierUncapped(player, "strength_modified") / 100.0f);
        hitBonus += ((float)getSkillStatisticModifier(player, "strength") / 100.0f);
        hitBonus += (float)getEnhancedSkillStatisticModifierUncapped(player, "expertise_to_hit");
        if (beast_lib.isBeastMaster(player) || beast_lib.isBeast(player))
        {
            hitBonus += beast_lib.getBeastHitChance(player);
        }
        return hitBonus;
    }
    public static float getDefenderHitReduction(obj_id player) throws InterruptedException
    {
        float increaseMiss = 0.0f;
        return increaseMiss;
    }
    public static attacker_data fillAttackerCombatAttributes(attacker_data attackerData) throws InterruptedException
    {
        if (!utils.hasScriptVar(attackerData.id, ATTACKER_DATA))
        {
            cacheAttackerData(attackerData.id);
        }
        attackerData.critChance = utils.getFloatScriptVar(attackerData.id, ATTACKER_BASE_CRIT);
        attackerData.pvpCrit = utils.getFloatScriptVar(attackerData.id, ATTACKER_PVP_CRIT);
        attackerData.npcCrit = utils.getFloatScriptVar(attackerData.id, ATTACKER_NPC_CRIT);
        attackerData.droidCrit = utils.getFloatScriptVar(attackerData.id, ATTACKER_DROID_CRIT);
        attackerData.creatureCrit = utils.getFloatScriptVar(attackerData.id, ATTACKER_CREATURE_CRIT);
        attackerData.hitChance = utils.getFloatScriptVar(attackerData.id, ATTACKER_HIT_BONUS);
        attackerData.strikethroughChance = utils.getFloatScriptVar(attackerData.id, ATTACKER_STRIKETHROUGH);
        attackerData.reduceParry = utils.getFloatScriptVar(attackerData.id, ATTACKER_REDUCE_PARRY);
        attackerData.reduceBlock = utils.getFloatScriptVar(attackerData.id, ATTACKER_REDUCE_BLOCK);
        attackerData.reduceDodge = utils.getFloatScriptVar(attackerData.id, ATTACKER_REDUCE_DODGE);
        attackerData.reduceGlancing = utils.getFloatScriptVar(attackerData.id, ATTACKER_REDUCE_GLANCING);
        attackerData.reduceEvade = utils.getFloatScriptVar(attackerData.id, ATTACKER_REDUCE_EVADE);
        return attackerData;
    }
    public static defender_data fillDefenderCombatAttributes(defender_data defenderData) throws InterruptedException
    {
        if (!utils.hasScriptVar(defenderData.id, DEFENDER_DATA))
        {
            cacheDefenderData(defenderData.id);
        }
        defenderData.reduceCritical = utils.getFloatScriptVar(defenderData.id, DEFENDER_REDUCE_CRIT);
        defenderData.increaseMiss = utils.getFloatScriptVar(defenderData.id, DEFENDER_INCREASE_MISS);
        defenderData.reduceStrikethrough = utils.getFloatScriptVar(defenderData.id, DEFENDER_REDUCE_STRIKETHROUGH);
        defenderData.reducePunishing = utils.getFloatScriptVar(defenderData.id, DEFENDER_REDUCE_PUNISH);
        defenderData.glancingChance = utils.getFloatScriptVar(defenderData.id, DEFENDER_GLANCING);
        defenderData.parryChance = utils.getFloatScriptVar(defenderData.id, DEFENDER_PARRY);
        defenderData.dodgeChance = utils.getFloatScriptVar(defenderData.id, DEFENDER_DODGE);
        defenderData.evadeChance = utils.getFloatScriptVar(defenderData.id, DEFENDER_EVADE);
        defenderData.blockChance = utils.getFloatScriptVar(defenderData.id, DEFENDER_BLOCK);
        return defenderData;
    }
    public static void cacheCombatData(obj_id self) throws InterruptedException
    {
        cacheAttackerData(self);
        cacheDefenderData(self);
    }
    public static void clearCachedCombatData(obj_id self) throws InterruptedException
    {
        utils.removeScriptVarTree(self, ATTACKER_DATA);
        utils.removeScriptVarTree(self, DEFENDER_DATA);
    }
    public static void cacheAttackerData(obj_id attacker) throws InterruptedException
    {
        utils.setScriptVar(attacker, ATTACKER_BASE_CRIT, getAttackerCritMod(attacker));
        utils.setScriptVar(attacker, ATTACKER_PVP_CRIT, (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_niche_pvp"));
        utils.setScriptVar(attacker, ATTACKER_DROID_CRIT, (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_niche_droid"));
        utils.setScriptVar(attacker, ATTACKER_NPC_CRIT, (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_niche_npc"));
        utils.setScriptVar(attacker, ATTACKER_CREATURE_CRIT, (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_critical_niche_creature"));
        utils.setScriptVar(attacker, ATTACKER_HIT_BONUS, getToHitBonus(attacker));
        utils.setScriptVar(attacker, ATTACKER_STRIKETHROUGH, getAttackerStrikethroughChance(attacker));
        utils.setScriptVar(attacker, ATTACKER_REDUCE_PARRY, getAttackerParryReduction(attacker));
        utils.setScriptVar(attacker, ATTACKER_REDUCE_BLOCK, getAttackerBlockReduction(attacker));
        utils.setScriptVar(attacker, ATTACKER_REDUCE_DODGE, getAttackerDodgeReduction(attacker));
        utils.setScriptVar(attacker, ATTACKER_REDUCE_GLANCING, getAttackerGlancingReduction(attacker));
        utils.setScriptVar(attacker, ATTACKER_REDUCE_EVADE, getAttackerEvasionReduction(attacker));
    }
    public static void cacheDefenderData(obj_id defender) throws InterruptedException
    {
        utils.setScriptVar(defender, DEFENDER_REDUCE_CRIT, getDefenderCriticalChance(defender));
        utils.setScriptVar(defender, DEFENDER_INCREASE_MISS, getDefenderHitReduction(defender));
        utils.setScriptVar(defender, DEFENDER_REDUCE_STRIKETHROUGH, getDefenderStrikethroughReduction(defender));
        utils.setScriptVar(defender, DEFENDER_REDUCE_PUNISH, getDefenderPunishingBlowReduction(defender));
        utils.setScriptVar(defender, DEFENDER_GLANCING, getDefenderGlancingBlowChance(defender));
        utils.setScriptVar(defender, DEFENDER_PARRY, getDefenderParryChance(defender));
        utils.setScriptVar(defender, DEFENDER_DODGE, getDefenderDodgeChance(defender));
        utils.setScriptVar(defender, DEFENDER_EVADE, getDefenderEvasionChance(defender));
        utils.setScriptVar(defender, DEFENDER_BLOCK, getDefenderBlockChance(defender));
    }
    public static obj_id getRandomHateTarget(obj_id self) throws InterruptedException
    {
        obj_id[] potentialTargets = getHateList(self);
        if (potentialTargets == null || potentialTargets.length == 0)
        {
            return null;
        }
        float max_range = 64.0f;
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        for (int i = 0; i < potentialTargets.length; i++)
        {
            if (isIdValid(potentialTargets[i]) && exists(potentialTargets[i]) && canSee(self, potentialTargets[i]) && !stealth.hasInvisibleBuff(potentialTargets[i]) && getMaster(potentialTargets[i]) == null && !isDead(potentialTargets[i]))
            {
                if (getDistance(self, potentialTargets[i]) < max_range)
                {
                    utils.addElement(validTargets, potentialTargets[i]);
                }
            }
        }
        if (validTargets == null || validTargets.size() == 0)
        {
            return null;
        }
        return ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
    }
    public static boolean doBhTaunt(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return false;
        }
        if (!exists(player) || !exists(target))
        {
            return false;
        }
        obj_id[] enemies = getHateList(target);
        if (enemies == null || enemies.length == 0)
        {
            return false;
        }
        obj_id topHateTarget = getHateTarget(target);
        float topHateAmount = getHate(target, topHateTarget);
        int level = getLevel(player);
        float increasedHate = topHateAmount;
        if (level <= BH_TAUNT_LOW_LEVEL)
        {
            increasedHate += BH_TAUNT_HATE_INCREASE_LOW;
        }
        else if (level <= BH_TAUNT_MID_LEVEL)
        {
            increasedHate += BH_TAUNT_HATE_INCREASE_MID;
        }
        else 
        {
            increasedHate += BH_TAUNT_HATE_INCREASE_HIGH;
        }
        addHate(target, player, increasedHate);
        return true;
    }
    public static boolean dsFsTaunt(obj_id player, obj_id target) throws InterruptedException
    {
        int stanceBuff = buff.getBuffOnTargetFromGroup(player, "fsStance");
        if (stanceBuff == 0)
        {
            return false;
        }
        if (!isIdValid(player) || !isIdValid(target))
        {
            return false;
        }
        if (!exists(player) || !exists(target))
        {
            return false;
        }
        obj_id[] enemies = getHateList(target);
        if (enemies == null || enemies.length == 0)
        {
            return false;
        }
        obj_id topHateTarget = getHateTarget(target);
        float topHateAmount = getHate(target, topHateTarget);
        int level = getLevel(player);
        float increasedHate = topHateAmount;
        if (level <= FS_TAUNT_LOW_LEVEL)
        {
            increasedHate += FS_TAUNT_HATE_INCREASE_LOW;
        }
        else if (level <= FS_TAUNT_MID_LEVEL)
        {
            increasedHate += FS_TAUNT_HATE_INCREASE_MID;
        }
        else 
        {
            increasedHate += FS_TAUNT_HATE_INCREASE_HIGH;
        }
        addHate(target, player, increasedHate);
        return true;
    }
    public static boolean isStunned(obj_id target) throws InterruptedException
    {
        return utils.hasScriptVar(target, TEMP_COMBAT_BLOCK);
    }
    public static int[] getSuccessBasedSingleTargetActionCost(hit_result hitData, combat_data actionData, obj_id attacker, weapon_data weaponData) throws InterruptedException
    {
        boolean isSingleTargetAttack = actionData.hitType == -1 && actionData.attackType == combat.SINGLE_TARGET && actionData.commandType != combat.LEFT_CLICK_DEFAULT;
        int[] freeShot = 
        {
            0,
            0,
            0
        };
        if (!isSingleTargetAttack)
        {
            return freeShot;
        }
        int freeMiss = getEnhancedSkillStatisticModifierUncapped(attacker, "freeshot_case_miss");
        int freeDodge = getEnhancedSkillStatisticModifierUncapped(attacker, "freeshot_case_dodge");
        int freeParry = getEnhancedSkillStatisticModifierUncapped(attacker, "freeshot_case_parry");
        int freeCrit = getEnhancedSkillStatisticModifierUncapped(attacker, "freeshot_case_crit");
        int freeStrike = getEnhancedSkillStatisticModifierUncapped(attacker, "freeshot_case_strikethrough");
        boolean isFreeshot = false;
        if (hitData.success == false)
        {
            if (freeMiss > 0)
            {
                isFreeshot = true;
            }
            if (hitData.dodge == true && freeDodge > 0)
            {
                isFreeshot = true;
            }
            if (hitData.parry == true && freeParry > 0)
            {
                isFreeshot = true;
            }
        }
        if (hitData.success == true)
        {
            if (hitData.critical == true && freeCrit > 0)
            {
                isFreeshot = true;
            }
            if (hitData.strikethrough == true && freeStrike > 0)
            {
                isFreeshot = true;
            }
        }
        if (isFreeshot)
        {
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, new string_id("spam", "freeshot"));
            showFlyTextPrivateProseWithFlags(attacker, attacker, pp, 1.5f, colors.LEMONCHIFFON, FLY_TEXT_FLAG_IS_FREESHOT);
            return freeShot;
        }
        int[] shotCost = getActionCost(attacker, weaponData, actionData);
        return shotCost;
    }
    public static void setPersistCombatMode(obj_id target, boolean state) throws InterruptedException
    {
        if (!isMob(target))
        {
            return;
        }
        if (!state)
        {
            if (utils.hasScriptVar(target, combat.PERSIST_COMBAT))
            {
                utils.removeScriptVar(target, combat.PERSIST_COMBAT);
            }
            return;
        }
        utils.setScriptVar(target, combat.PERSIST_COMBAT, 1);
        return;
    }
    public static boolean isPersistCombatMode(obj_id self) throws InterruptedException
    {
        return utils.hasScriptVar(self, combat.PERSIST_COMBAT);
    }
    public static boolean isValidPersistCombat(obj_id self) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return false;
        }
        if (!getCreatureCoverVisibility(self))
        {
            return false;
        }
        if (isDead(self))
        {
            return false;
        }
        obj_id[] hateList = getHateList(self);
        if (hateList == null || hateList.length == 0)
        {
            return false;
        }
        boolean hasLos = true;
        for (int i = 0; i < hateList.length; i++)
        {
            hasLos |= canSee(self, hateList[i]);
        }
        if (!hasLos)
        {
            return false;
        }
        return true;
    }
    public static boolean isDamageImmune(obj_id attacker, obj_id defender, combat_data actionData) throws InterruptedException
    {
        boolean attackerImmune = getEnhancedSkillStatisticModifierUncapped(attacker, "damage_immune") > 0;
        boolean defenderImmune = getEnhancedSkillStatisticModifierUncapped(defender, "damage_immune") > 0;
        prose_package pp = new prose_package();
        boolean isBeneficial = (actionData.hitType == NON_ATTACK || actionData.hitType == NON_DAMAGE_ATTACK) && pvpCanHelp(attacker, defender);
        if (attackerImmune && !isBeneficial)
        {
            pp.stringId = new string_id("cbt_spam", "immune_hit_other");
            pp.target.set(defender);
            pp.other.set(new string_id("cmd_n", actionData.actionName));
            sendCombatSpamMessageProse(attacker, pp);
            pp.target.set(attacker);
            pp.stringId = new string_id("cbt_spam", "immune_target_hit_other");
            sendCombatSpamMessageProse(defender, pp);
        }
        if (defenderImmune && !isBeneficial)
        {
            pp.stringId = new string_id("cbt_spam", "immune_get_hit");
            pp.target.set(attacker);
            pp.other.set(new string_id("cmd_n", actionData.actionName));
            sendCombatSpamMessageProse(defender, pp);
            pp.target.set(defender);
            pp.stringId = new string_id("cbt_spam", "immune_target_hit");
            sendCombatSpamMessageProse(attacker, pp);
        }
        return (attackerImmune || defenderImmune) && !isBeneficial;
    }
    public static float getDiminishedReturnValue(float value, int modType) throws InterruptedException
    {
        if (value < 0.0f)
        {
            return value;
        }
        float[] seedList = 
        {
            500.0f,
            500.0f,
            500.0f,
            500.0f,
            500.0f,
            500.0f,
            500.0f,
            50.0f
        };
        switch (modType)
        {
            case DR_ATTACKER_CRITICAL:
            return (value * (1.0f - (value / (value + seedList[DR_ATTACKER_CRITICAL]))));
            case DR_ATTACKER_STRIKETHROUGH:
            return (value * (1.0f - (value / (value + seedList[DR_ATTACKER_STRIKETHROUGH]))));
            case DR_DEFENDER_BLOCK:
            return (value * (1.0f - (value / (value + seedList[DR_DEFENDER_BLOCK]))));
            case DR_DEFENDER_DODGE:
            return (value * (1.0f - (value / (value + seedList[DR_DEFENDER_DODGE]))));
            case DR_DEFENDER_PARRY:
            return (value * (1.0f - (value / (value + seedList[DR_DEFENDER_PARRY]))));
            case DR_DEFENDER_GLANCE:
            return (value * (1.0f - (value / (value + seedList[DR_DEFENDER_GLANCE]))));
            case DR_DEFENDER_EVADE:
            return (value * (1.0f - (value / (value + seedList[DR_DEFENDER_EVADE]))));
            case DR_ATTACKER_CRITICAL_2:
            return (value * (1.0f - (value / (value + seedList[DR_ATTACKER_CRITICAL_2]))));
        }
        return value;
    }
    public static void mirrorArmor(obj_id commando) throws InterruptedException
    {
        obj_id[] haters = getHateList(commando);
        if (haters != null || haters.length > 0)
        {
            for (int i = 0; i < haters.length; i++)
            {
                float myHate = getHate(haters[i], commando);
                if (myHate <= 0.0f)
                {
                    continue;
                }
                myHate /= 2.0f;
                setHate(haters[i], commando, myHate);
            }
        }
    }
    public static boolean setKillMeter(obj_id player, int value) throws InterruptedException
    {
        int current = getKillMeter(player);
        int delta = value - current;
        incrementKillMeter(player, delta);
        return true;
    }
    public static boolean modifyKillMeter(obj_id player, int value) throws InterruptedException
    {
        int current = getKillMeter(player);
        int delta = current + value;
        if (delta < 0)
        {
            delta = 0;
        }
        if (delta > 50)
        {
            delta = 50;
        }
        setKillMeter(player, delta);
        return true;
    }
    public static boolean canDrainKillMeter(obj_id player, int value) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return true;
        }
        int current = getKillMeter(player);
        int delta = current - value;
        return delta < 0 ? false : true;
    }
    public static boolean drainKillMeter(obj_id player, int value) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            return true;
        }
        if (!canDrainKillMeter(player, value))
        {
            return false;
        }
        return modifyKillMeter(player, -1 * value);
    }
    public static location getCommandGroundTargetLocation(String params) throws InterruptedException
    {
        String[] stringLocationParams = split(params, ' ');
        if (stringLocationParams == null || stringLocationParams.length <= 0)
        {
            return null;
        }
        location eggLoc = new location();
        boolean inCell = false;
        if (isIdValid(utils.stringToObjId(stringLocationParams[3])))
        {
            inCell = true;
        }
        if (!inCell)
        {
            eggLoc.x = utils.stringToFloat(stringLocationParams[0]);
            eggLoc.y = utils.stringToFloat(stringLocationParams[1]);
            eggLoc.z = utils.stringToFloat(stringLocationParams[2]);
        }
        else 
        {
            eggLoc.cell = utils.stringToObjId(stringLocationParams[3]);
            eggLoc.x = utils.stringToFloat(stringLocationParams[4]);
            eggLoc.y = utils.stringToFloat(stringLocationParams[5]);
            eggLoc.z = utils.stringToFloat(stringLocationParams[6]);
        }
        return eggLoc;
    }
    public static float getDevastationChance(obj_id attacker) throws InterruptedException
    {
        float devastationBonus = 2.0f;
        devastationBonus += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "commando_devastation");
        devastationBonus += (float)getEnhancedSkillStatisticModifierUncapped(attacker, "expertise_devastation_bonus") / 10.0f;
        return devastationBonus;
    }
    public static float getElementalPenetration(obj_id attacker, String damageString) throws InterruptedException
    {
        float elementalPenetrationAmount = 0.0f;
        if (isIdValid(attacker) && damageString != null && damageString.length() > 0)
        {
            String elementalPenetrationSkillModName = "exotic_" + damageString + "_penetration";
            float elementalPenetrationSkillMod = (float)getEnhancedSkillStatisticModifierUncapped(attacker, elementalPenetrationSkillModName);
            if (elementalPenetrationSkillMod > MAX_RE_EXOTIC_ELEMENTAL_PENETRATION_SKILLMOD)
            {
                elementalPenetrationSkillMod = MAX_RE_EXOTIC_ELEMENTAL_PENETRATION_SKILLMOD;
            }
            elementalPenetrationAmount = 100.0f - (elementalPenetrationSkillMod * 2.5f);
            elementalPenetrationAmount *= 0.01f;
        }
        return elementalPenetrationAmount;
    }
    public static boolean hasPrescience(obj_id attacker, obj_id defender, combat_data actionData) throws InterruptedException
    {
        if (!buff.hasBuff(defender, "bh_prescience"))
        {
            return false;
        }
        obj_id owner = buff.getBuffCaster(defender, "bh_prescience");
        if (attacker == owner)
        {
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "bh_prescience_attacker");
            pp.target.set(defender);
            pp.other.set(new string_id("cmd_n", actionData.actionName));
            sendCombatSpamMessageProse(attacker, pp);
            pp.target.set(attacker);
            pp.stringId = new string_id("cbt_spam", "bh_prescience_defender");
            sendCombatSpamMessageProse(defender, pp);
            return true;
        }
        return false;
    }
}
