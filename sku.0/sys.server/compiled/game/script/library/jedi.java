package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.armor;
import script.library.factions;
import script.library.utils;
import script.library.skill;
import script.library.money;
import script.library.player_version;
import script.library.buff;
import script.library.combat;
import script.library.group;
import script.library.stealth;

public class jedi extends script.base_script
{
    public jedi()
    {
    }
    public static final String JEDI_ACTIONS_FILE = "datatables/jedi/jedi_actions.iff";
    public static final String JEDI_RANK_DATA = "datatables/jedi/jedi_rank_data.iff";
    public static final String JEDI_CRYSTAL_COLOR_TABLE = "datatables/jedi/crystal_color_damage.iff";
    public static final String TEMPLATE_FORCE_CRYSTAL = "object/tangible/component/weapon/lightsaber/lightsaber_module_force_crystal.iff";
    public static final String TEMPLATE_KRAYT_DRAGON_PEARL = "object/tangible/component/weapon/lightsaber/lightsaber_module_krayt_dragon_pearl.iff";
    public static final String JEDI_CLOAK_LIGHT_HOOD_UP = "item_jedi_robe_06_03";
    public static final String JEDI_CLOAK_DARK_HOOD_UP = "item_jedi_robe_06_04";
    public static final String JEDI_CLOAK_LIGHT_HOOD_DOWN = "item_jedi_robe_06_05";
    public static final String JEDI_CLOAK_DARK_HOOD_DOWN = "item_jedi_robe_06_06";
    public static final String JEDI_STANCE = "fs_buff_def_1_1";
    public static final String JEDI_FOCUS = "fs_buff_ca_1";
    public static final int JEDI_NEUTRAL = 0;
    public static final int JEDI_LIGHT_SIDE = 1;
    public static final int JEDI_DARK_SIDE = 2;
    public static final int BOUNTY_VISIBILITY_THRESHHOLD = 2000;
    public static final int NO_BOUNTY_VISIBLITY_THRESHOLD = 500;
    public static final int JEDI_DEATH_DECAY_TIME = 7 * 24 * 60 * 60;
    public static final int BOUNTY_SPLOIT_INTERVAL = 60 * 24 * 3;
    public static final int MAX_JEDI_DEATHS_PADAWAN = 3;
    public static final int MAX_JEDI_DEATHS_KNIGHT = 4;
    public static final int MAX_JEDI_DEATHS_MASTER = 5;
    public static final int MAX_CRYSTAL_COUNT = 6;
    public static final String VAR_JEDI_RESTART = "jedi_restart";
    public static final String VAR_JEDI_DEATHS_OLD = "jedi.deaths";
    public static final String VAR_JEDI_DEATHS = "jedi.deathCount";
    public static final String VAR_JEDI_SKILL_LOSS = "jedi.skillLoss";
    public static final String VAR_JEDI_LAST_DEATH = "jedi.lastDeath";
    public static final String VAR_JEDI_SKILL = "jedi.skill_required";
    public static final String VAR_SABER_BASE = "jedi.saber";
    public static final String VAR_SABER_DEFAULT_STATS = VAR_SABER_BASE + ".base_stats";
    public static final String VAR_CRYSTAL_BASE = "jedi.crystal";
    public static final String VAR_CRYSTAL_STATS = VAR_CRYSTAL_BASE + ".stats";
    public static final String VAR_CRYSTAL_OWNER = VAR_CRYSTAL_BASE + ".owner";
    public static final String VAR_CRYSTAL_OWNER_ID = VAR_CRYSTAL_BASE + ".owner.id";
    public static final String VAR_CRYSTAL_OWNER_NAME = VAR_CRYSTAL_BASE + ".owner.name";
    public static final String VAR_MIN_DMG = "min_dmg";
    public static final String VAR_MAX_DMG = "max_dmg";
    public static final String VAR_SPEED = "speed";
    public static final String VAR_WOUND = "wound";
    public static final String VAR_ATTACK_COST = "attack_cost";
    public static final String VAR_ACCURACY = "accuracy";
    public static final String VAR_RADIUS = "damage";
    public static final String VAR_FORCE = "force";
    public static final String VAR_COLOR = "color";
    public static final String VAR_LEVEL = "level";
    public static final String VAR_HP = "hp";
    public static final String VAR_HP_MAX = "hpMax";
    public static final String VAR_ELEMENTAL_DAM_TYPE = "e_damage_type";
    public static final String VAR_ELEMENTAL_DAM_AMNT = "e_damage_amount";
    public static final String VAR_SHADER = "shader";
    public static final String VAR_QUALITY = "quality";
    public static final int CRYSTAL_POOR = 0;
    public static final int CRYSTAL_FAIR = 1;
    public static final int CRYSTAL_GOOD = 2;
    public static final int CRYSTAL_QUALITY = 3;
    public static final int CRYSTAL_SELECT = 4;
    public static final int CRYSTAL_PREMIUM = 5;
    public static final int CRYSTAL_FLAWLESS = 6;
    public static final int MIN_CRYSTAL_TUNE_PLAYER_LEVEL = 20;
    public static final int MIN_NON_FR_JEDI_BOUNTY = 25000;
    public static final int PER_JEDI_SKILL_PT_COST = 1000;
    public static final int MIN_FR_JEDI_BOUNTY = 50000;
    public static final int PER_FORCE_RANK_COST = 100000;
    public static final string_id SID_REGEN_TEMPORARILY_REDUCED = new string_id("jedi_spam", "regen_temporarily_reduced");
    public static final string_id SID_UNABLE_TO_RETURN_PEARLS_INVENTORY = new string_id("jedi_spam", "unable_to_return_pearls_inventory");
    public static final string_id SID_MAKE_SURE_INVENTORY_SPACE_AVAILABLE = new string_id("jedi_spam", "make_sure_inventory_space_available");
    public static final string_id SID_WILL_ATTEMPT_NEXT_TIME = new string_id("jedi_spam", "will_attempt_next_time");
    public static final string_id SID_ALL_PEARLS_RESTORED = new string_id("jedi_spam", "all_pearls_restored");
    public static final string_id SID_LOST_JEDI_XP = new string_id("jedi_spam", "lost_jedi_xp");
    public static final String[] JEDI_ENEMY_FACTIONS = 
    {
        factions.FACTION_IMPERIAL
    };
    public static final String[] JEDI_NEUTRAL_FACTIONS = 
    {
        factions.FACTION_NEUTRAL
    };
    public static final float ENEMY_VISIBILITY_MULTIPLIER = 1.0f;
    public static final float NEUTRAL_VISIBILITY_MULTIPLIER = 0.5f;
    public static int getJediActionVisibilityValue(obj_id objPlayer, int intActionVisibility, int intActionRange) throws InterruptedException
    {
        float fltActionRange = (float)intActionRange;
        float fltBaseVisibility = (float)intActionVisibility;
        float fltActualVisibility = 0;
        obj_id[] objNPCs = getNPCsInRange(objPlayer, fltActionRange);
        obj_id[] objPlayers = getPlayerCreaturesInRange(objPlayer, fltActionRange);
        if ((objNPCs != null) && (objNPCs.length > 0))
        {
            for (int intI = 0; intI < objNPCs.length; intI++)
            {
                if (ai_lib.isNpc(objNPCs[intI]) || ai_lib.isAndroid(objNPCs[intI]))
                {
                    String strFaction = factions.getFaction(objNPCs[intI]);
                    if (strFaction != null)
                    {
                        int intIndex = utils.getElementPositionInArray(JEDI_ENEMY_FACTIONS, strFaction);
                        if (intIndex > -1)
                        {
                            float fltTempVisibility = fltBaseVisibility * ENEMY_VISIBILITY_MULTIPLIER;
                            if (fltTempVisibility > fltActualVisibility)
                            {
                                fltActualVisibility = fltTempVisibility;
                            }
                        }
                        else 
                        {
                            float fltTempVisibility = fltBaseVisibility * NEUTRAL_VISIBILITY_MULTIPLIER;
                            if (fltTempVisibility > fltActualVisibility)
                            {
                                fltActualVisibility = fltTempVisibility;
                            }
                        }
                    }
                    else 
                    {
                        float fltTempVisibility = fltBaseVisibility * 0.25f;
                        if (fltTempVisibility > fltActualVisibility)
                        {
                            fltActualVisibility = fltTempVisibility;
                        }
                    }
                }
            }
        }
        else 
        {
        }
        if (objPlayers != null && objPlayers.length > 0)
        {
            for (int intI = 0; intI < objPlayers.length; intI++)
            {
                if ((!group.inSameGroup(objPlayer, objPlayers[intI])) && (objPlayers[intI] != objPlayer) && (!isGod(objPlayers[intI])))
                {
                    if (factions.pvpDoAllowedAttackCheck(objPlayer, objPlayers[intI]))
                    {
                        fltActualVisibility += (fltBaseVisibility * ENEMY_VISIBILITY_MULTIPLIER);
                    }
                    else if (factions.isNeutral(objPlayers[intI]))
                    {
                        fltActualVisibility += (fltBaseVisibility * NEUTRAL_VISIBILITY_MULTIPLIER);
                    }
                    else 
                    {
                        fltActualVisibility += (fltBaseVisibility * 0.25f);
                    }
                }
            }
        }
        else 
        {
        }
        int intActualVisibility = (int)fltActualVisibility;
        return intActualVisibility;
    }
    public static void jediActionPerformed(obj_id objPlayer, int intActionVisibility, int intActionRadius) throws InterruptedException
    {
        return;
    }
    public static void setJediVisibilityTimeStamp(obj_id self) throws InterruptedException
    {
        final int JEDI_BOUNTY_TIMER = 60 * 60 * 24 * 7;
        int intTimeStamp = getGameTime();
        intTimeStamp = intTimeStamp + JEDI_BOUNTY_TIMER;
        setObjVar(self, "jedi.intVisibilityTime", intTimeStamp);
    }
    public static void checkJediPenalties(obj_id objPlayer, int intVisiblity) throws InterruptedException
    {
        if (intVisiblity < NO_BOUNTY_VISIBLITY_THRESHOLD)
        {
            clearJediBounties(objPlayer);
        }
        return;
    }
    public static void doJediTEF(obj_id objPlayer) throws InterruptedException
    {
        String strTest = factions.getFaction(objPlayer);
        if (strTest == null)
        {
            return;
        }
        String strImperial = factions.FACTION_IMPERIAL;
        String strRebel = factions.FACTION_REBEL;
        if (strTest.equals(strRebel))
        {
            pvpSetFactionEnemyFlag(objPlayer, (-615855020));
        }
        else if (strTest.equals(strImperial))
        {
            pvpSetFactionEnemyFlag(objPlayer, (370444368));
        }
        else 
        {
            pvpSetFactionEnemyFlag(objPlayer, (-615855020));
        }
    }
    public static void clearJediBounties(obj_id objPlayer) throws InterruptedException
    {
        obj_id[] hunters = getJediBounties(objPlayer);
        if (hunters != null)
        {
            dictionary params = new dictionary();
            params.put("target", objPlayer);
            for (int i = 0; i < hunters.length; i++)
            {
                if (isIdValid(hunters[i]))
                {
                    messageTo(hunters[i], "bountyIncomplete", params, 1.0f, true);
                }
            }
            removeAllJediBounties(objPlayer);
        }
    }
    public static boolean hasForcePower(obj_id objPlayer, int intForcePowerCost) throws InterruptedException
    {
        int intCurrentPower = getForcePower(objPlayer);
        if ((intCurrentPower - intForcePowerCost > 0) || (!isPlayer(objPlayer)))
        {
            return true;
        }
        else 
        {
            string_id strSpam = new string_id("jedi_spam", "no_force_power");
            sendSystemMessage(objPlayer, strSpam);
            return false;
        }
    }
    public static boolean drainForcePower(obj_id objPlayer, int intForcePowerCost) throws InterruptedException
    {
        return drainForcePower(objPlayer, intForcePowerCost, true);
    }
    public static boolean drainForcePower(obj_id objPlayer, int intForcePowerCost, boolean verbose) throws InterruptedException
    {
        if (utils.checkConfigFlag("ScriptFlags", "e3Demo"))
        {
            intForcePowerCost = 0;
        }
        int intCurrentPower = getForcePower(objPlayer);
        if (intCurrentPower - intForcePowerCost >= 0)
        {
            int intActualCost = -1 * intForcePowerCost;
            alterForcePower(objPlayer, intActualCost);
            return true;
        }
        else 
        {
            if (verbose)
            {
                string_id strSpam = new string_id("jedi_spam", "no_force_power");
                sendSystemMessage(objPlayer, strSpam);
            }
            return false;
        }
    }
    public static void grantJediXP(obj_id objPlayer, int intForcePowerCost) throws InterruptedException
    {
        location locTest = getLocation(objPlayer);
        obj_id objBuilding = getTopMostContainer(objPlayer);
        if (isIdValid(objBuilding))
        {
            if (hasScript(objBuilding, "structure.permanent_structure"))
            {
                return;
            }
        }
    }
    public static void doJediNonCombatAnimation(obj_id objPlayer, obj_id objTarget, dictionary dctJediInfo) throws InterruptedException
    {
        if (objTarget != null)
        {
        }
        else 
        {
        }
    }
    public static boolean isJediTrainerForPlayer(obj_id objPlayer, obj_id objNPC) throws InterruptedException
    {
        if (!isJedi(objPlayer))
        {
            return false;
        }
        location locTrainerLocation = getLocationObjVar(objPlayer, "jedi.locTrainerLocation");
        if (locTrainerLocation == null)
        {
            return false;
        }
        location locNPCLocation = getWorldLocation(objNPC);
        locNPCLocation.y = 0;
        locTrainerLocation.y = 0;
        float fltDistance = getDistance(locTrainerLocation, locNPCLocation);
        if (fltDistance != -1 && fltDistance < 3)
        {
            return true;
        }
        return false;
    }
    public static boolean isLightsaber(obj_id objWeapon) throws InterruptedException
    {
        return isLightsaber(getWeaponType(objWeapon));
    }
    public static boolean isLightsaber(int intWeaponType) throws InterruptedException
    {
        switch (intWeaponType)
        {
            case WEAPON_TYPE_WT_1HAND_LIGHTSABER:
            return true;
            case WEAPON_TYPE_WT_2HAND_LIGHTSABER:
            return true;
            case WEAPON_TYPE_WT_POLEARM_LIGHTSABER:
            return true;
        }
        return false;
    }
    public static int getJediAlignment(obj_id objJedi) throws InterruptedException
    {
        return getSkillStatisticModifier(objJedi, "private_jedi_alignment");
    }
    public static float getJediAlignmentModifier(obj_id objPlayer, dictionary dctJediInfo) throws InterruptedException
    {
        return 1.0f;
    }
    public static int modifyForcePower(obj_id objPlayer, int intForcePowerToDrain, dictionary dctJediInfo) throws InterruptedException
    {
        float fltAlignmentModifier = getJediAlignmentModifier(objPlayer, dctJediInfo);
        float fltForcePower = (float)intForcePowerToDrain;
        fltForcePower = fltForcePower - (fltForcePower * (1.0f - fltAlignmentModifier));
        intForcePowerToDrain = (int)fltForcePower;
        return intForcePowerToDrain;
    }
    public static float getJediActionTime(obj_id player, dictionary actionData) throws InterruptedException
    {
        float timeDelay = actionData.getFloat("baseTimeMod");
        int rankMod = getEnhancedSkillStatisticModifier(player, "dark_jedi_power");
        if (rankMod > 0)
        {
            timeDelay += rankMod * actionData.getFloat("darkTimeBonus");
        }
        else 
        {
            rankMod = getEnhancedSkillStatisticModifier(player, "light_jedi_power");
            if (rankMod > 0)
            {
                timeDelay += rankMod * actionData.getFloat("lightTimeBonus");
            }
        }
        return timeDelay;
    }
    public static float getJediDamageModifier(obj_id player, dictionary actionData) throws InterruptedException
    {
        float damageMod = actionData.getFloat("baseDamageMod");
        int rankMod = getEnhancedSkillStatisticModifier(player, "dark_jedi_power");
        if (rankMod > 0)
        {
            damageMod += rankMod * actionData.getFloat("darkDamageMod");
        }
        else 
        {
            rankMod = getEnhancedSkillStatisticModifier(player, "light_jedi_power");
            if (rankMod > 0)
            {
                damageMod += rankMod * actionData.getFloat("lightDamageMod");
            }
        }
        return damageMod;
    }
    public static void recalculateForcePower(obj_id player) throws InterruptedException
    {
        int maxPower = getSkillStatisticModifier(player, "jedi_force_power_max");
        int baseRegenRate = getSkillStatisticModifier(player, "jedi_force_power_regen");
        int modRegenRate = (getEnhancedSkillStatisticModifier(player, "jedi_force_power_regen") - baseRegenRate);
        if (modRegenRate > 15)
        {
            modRegenRate = 15;
        }
        baseRegenRate = baseRegenRate + modRegenRate;
        float regenRate = (float)baseRegenRate;
        regenRate = regenRate / 10f;
        if (hasSkill(player, "force_rank_light_novice"))
        {
            int force_power = getSkillStatisticModifier(player, "force_power_light");
            int force_control = getSkillStatisticModifier(player, "force_control_light");
            int force_manipulation = getSkillStatisticModifier(player, "force_manipulation_light");
            maxPower += (force_power + force_control) * 10;
            regenRate += (force_control + force_manipulation) / 100f;
        }
        else if (hasSkill(player, "force_rank_dark_novice"))
        {
            int force_power = getSkillStatisticModifier(player, "force_power_dark");
            int force_control = getSkillStatisticModifier(player, "force_control_dark");
            int force_manipulation = getSkillStatisticModifier(player, "force_manipulation_dark");
            maxPower += (force_power + force_control) * 10;
            regenRate += (force_control + force_manipulation) / 100f;
        }
        int meditateRate = 1;
        if (hasObjVar(player, "jedi.meditate"))
        {
            meditateRate = getIntObjVar(player, "jedi.meditate");
        }
        regenRate *= meditateRate;
        int regenPenalty = utils.getIntScriptVar(player, "jedi.regenPenalty");
        if (regenPenalty < 1)
        {
            regenPenalty = 1;
        }
        regenRate /= (float)regenPenalty;
        setMaxForcePower(player, maxPower);
        setForcePowerRegenRate(player, regenRate);
        return;
    }
    public static boolean postponeGrantJedi() throws InterruptedException
    {
        String enabled = toLower(getConfigSetting("GameServer", "postponeJediGrant"));
        if (enabled == null)
        {
            return false;
        }
        if (enabled.equals("true") || enabled.equals("1"))
        {
            return true;
        }
        return false;
    }
    public static boolean doNonCombatActionPrecheck(obj_id player, obj_id target, dictionary actionData) throws InterruptedException
    {
        int forceCost = actionData.getInt("intJediPowerCost");
        float range = actionData.getFloat("fltRange");
        float actionTime = actionData.getInt("fltActionTime");
        if (isIdValid(player) && isIdValid(target) && exists(target))
        {
            if (player != target)
            {
                float dist = getDistance(target, player);
                if (dist > range)
                {
                    sendSystemMessage(player, new string_id("cbt_spam", "out_of_range_single"));
                    return false;
                }
            }
        }
        else 
        {
            return false;
        }
        forceCost = jedi.getModifiedForceCost(player, forceCost, actionData.getString("actionName"));
        actionData.put("intJediPowerCost", forceCost);
        if (!jedi.hasForcePower(player, forceCost))
        {
            return false;
        }
        return true;
    }
    public static obj_id getCorrectFriendlyTarget(obj_id player, obj_id target, dictionary actionData) throws InterruptedException
    {
        float range = actionData.getFloat("fltRange");
        if (range < 0)
        {
            return player;
        }
        obj_id newTarget = null;
        if (!isIdValid(target))
        {
            newTarget = player;
        }
        else 
        {
            if (!factions.pvpDoAllowedHelpCheck(player, target))
            {
                newTarget = getLookAtTarget(player);
            }
            else 
            {
                newTarget = target;
            }
        }
        if (!factions.pvpDoAllowedHelpCheck(player, newTarget) || isDead(newTarget))
        {
            sendSystemMessage(player, new string_id("jedi_spam", "no_help_target"));
            return null;
        }
        if (player != newTarget && (isIdValid(newTarget) && utils.hasScriptVar(newTarget, "noBeneficialJediHelp")))
        {
            sendSystemMessage(player, new string_id("jedi_spam", "no_help_target"));
            return null;
        }
        if (newTarget != player && !canSee(player, newTarget))
        {
            showFlyTextPrivate(player, player, new string_id("combat_effects", "cant_see"), 1.5f, colors.MEDIUMTURQUOISE);
            return null;
        }
        return newTarget;
    }
    public static void playJediActionEffect(obj_id objPlayer, obj_id objTarget, dictionary actionData) throws InterruptedException
    {
        playJediActionEffect(objPlayer, objTarget, actionData, false);
    }
    public static void playJediActionEffect(obj_id objPlayer, obj_id objTarget, dictionary actionData, boolean labelEffect) throws InterruptedException
    {
        String combatAnim = actionData.getString("combatActionAnimation");
        String selfAnim = actionData.getString("selfActionAnimation");
        String clientEffect = actionData.getString("clientEffectFile");
        if (objTarget == objPlayer)
        {
            if (selfAnim != null && !selfAnim.equals(""))
            {
                doAnimationAction(objPlayer, selfAnim);
            }
            if (clientEffect != null && !clientEffect.equals(""))
            {
                if (labelEffect)
                {
                    playClientEffectObj(objPlayer, clientEffect, objPlayer, "", null, actionData.getString("actionName"));
                }
                else 
                {
                    playClientEffectObj(objPlayer, clientEffect, objPlayer, "");
                }
            }
            return;
        }
        else 
        {
            if (combatAnim != null && !combatAnim.equals(""))
            {
                defender_results[] cbtDefenderResults = new defender_results[1];
                cbtDefenderResults[0] = new defender_results();
                attacker_results cbtAttackerResults = new attacker_results();
                cbtDefenderResults[0].id = objTarget;
                cbtDefenderResults[0].endPosture = getPosture(objTarget);
                cbtDefenderResults[0].result = COMBAT_RESULT_HIT;
                cbtAttackerResults.id = objPlayer;
                cbtAttackerResults.weapon = null;
                doCombatResults(combatAnim, cbtAttackerResults, cbtDefenderResults);
            }
            if (clientEffect != null && !clientEffect.equals(""))
            {
                dictionary dctParams = new dictionary();
                dctParams.put("strEffect", clientEffect);
                dctParams.put("objTarget", objTarget);
                dctParams.put("labelEffect", labelEffect);
                messageTo(objTarget, "playDelayedClientEffect", dctParams, 8f, false);
            }
        }
        return;
    }
    public static boolean performJediBuffCommand(obj_id player, obj_id target, dictionary actionData) throws InterruptedException
    {
        String actionName = actionData.getString("actionName");
        String buffName = actionData.getString("buffName");
        LOG("jedi", "performJediBuffCommand::buff name (" + buffName + ")");
        if (!buffName.startsWith("avoidIncap"))
        {
            if (actionData.getInt("toggleBuff") == 1)
            {
                if (buff.hasBuff(target, buffName))
                {
                    buff.removeBuff(target, buffName);
                    sendSystemMessage(player, new string_id("jedi_spam", "remove_" + actionName));
                    return false;
                }
            }
            if (!buff.canApplyBuff(target, buffName))
            {
                LOG("jedi", "1:performJediBuffCommand::buff already present - aborting");
                sendSystemMessage(player, new string_id("jedi_spam", "force_buff_present"));
                return false;
            }
        }
        else 
        {
            final int MAX_STACK = 5;
            String newBuffName = "";
            for (int i = 1; i < MAX_STACK + 1; i++)
            {
                newBuffName = buffName + "_" + i;
                if (!buff.hasBuff(target, newBuffName))
                {
                    buffName = newBuffName;
                    break;
                }
                else 
                {
                    if (i == MAX_STACK)
                    {
                        LOG("jedi", "2:performJediBuffCommand::buff already present - aborting");
                        sendSystemMessage(player, new string_id("jedi_spam", "force_buff_present"));
                        return false;
                    }
                }
            }
        }
        int minHeal = actionData.getInt("intMinHealthHeal");
        int maxHeal = actionData.getInt("intMaxHealthHeal");
        int duration = (int)actionData.getFloat("buffDuration");
        if (minHeal > 0 && maxHeal > 0 && buff.hasBuff(target, buffName))
        {
            sendSystemMessage(player, new string_id("jedi_spam", "force_buff_present"));
            return false;
        }
        if (buff.applyBuff(target, buffName))
        {
            if (minHeal > 0 && maxHeal > 0)
            {
                healing.startHealOverTime(target, duration, 4, rand(minHeal, maxHeal));
            }
            sendSystemMessage(player, new string_id("jedi_spam", "apply_" + actionName));
            playJediActionEffect(player, target, actionData, true);
            if (!buffName.equals("invis_forceCloak"))
            {
                stealth.testInvisNonCombatAction(player, target);
            }
        }
        return true;
    }
    public static boolean doBuffAction(obj_id self, obj_id target, String actionName) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        if (buff.isParalyzed(self))
        {
            return false;
        }
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            LOG("DESIGNER_FATAL", "Buff Action " + actionName + " has no entry in the jedi actions datatable");
            return false;
        }
        target = getCorrectFriendlyTarget(self, target, actionData);
        if (!isIdValid(target))
        {
            return false;
        }
        if (!doNonCombatActionPrecheck(self, target, actionData))
        {
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.hasForcePower(self, forceCost))
        {
            return false;
        }
        weapon_data weaponData = new weapon_data();
        weaponData = getWeaponData(getCurrentWeapon(self));
        int[] actionCost = combat.getActionCost(self, weaponData, actionData);
        if (!combat.canDrainCombatActionAttributes(self, actionCost))
        {
            return false;
        }
        if (performJediBuffCommand(self, target, actionData))
        {
            jedi.drainForcePower(self, forceCost);
            combat.drainCombatActionAttributes(self, actionCost);
            int visValue = actionData.getInt("intVisibilityValue");
            int visRange = actionData.getInt("intVisibilityRange");
            jedi.jediActionPerformed(self, visValue, visRange);
            int xpOverride = actionData.getInt("xpOverride");
            if (xpOverride < 0)
            {
                xpOverride = forceCost;
            }
            jedi.grantJediXP(self, xpOverride);
        }
        else 
        {
            return false;
        }
        return true;
    }
    public static boolean doForceRun(obj_id self, obj_id target, String actionName) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            LOG("DESIGNER_FATAL", "Action " + actionName + " has no entry in the jedi actions datatable");
            return false;
        }
        target = getCorrectFriendlyTarget(self, target, actionData);
        if (!isIdValid(target))
        {
            return false;
        }
        if (!doNonCombatActionPrecheck(self, target, actionData))
        {
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.hasForcePower(self, forceCost))
        {
            return false;
        }
        weapon_data weaponData = new weapon_data();
        weaponData = getWeaponData(getCurrentWeapon(self));
        int[] actionCost = combat.getActionCost(self, weaponData, actionData);
        if (!combat.canDrainCombatActionAttributes(self, actionCost))
        {
            return false;
        }
        String buffName = actionData.getString("buffName");
        if (!buff.canApplyBuff(self, buffName))
        {
            sendSystemMessage(self, new string_id("jedi_spam", "force_run_wont_stack"));
            return false;
        }
        if (doBuffAction(self, target, actionName))
        {
            combat.drainCombatActionAttributes(self, actionCost);
            jedi.drainForcePower(self, forceCost);
            messageTo(self, "handleForceRunCost", null, 10, true);
        }
        else 
        {
            return false;
        }
        return true;
    }
    public static boolean regainConsciousness(obj_id self, obj_id target, String actionName) throws InterruptedException
    {
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            LOG("DESIGNER_FATAL", "Action " + actionName + " has no entry in the jedi actions datatable");
            return false;
        }
        target = self;
        if (!isIdValid(target))
        {
            return false;
        }
        if (!doNonCombatActionPrecheck(self, target, actionData))
        {
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.drainForcePower(self, forceCost))
        {
            return false;
        }
        pclib.clearEffectsForDeath(self);
        setAttrib(self, HEALTH, 200);
        int regenPenalty = utils.getIntScriptVar(self, "jedi.regenPenalty");
        if (regenPenalty < 1)
        {
            regenPenalty = 1;
        }
        regenPenalty *= 2;
        utils.setScriptVar(self, "jedi.regenPenalty", regenPenalty);
        recalculateForcePower(self);
        messageTo(self, "clearRegenPenalty", null, 30 * 60, false);
        messageTo(self, "handlePlayerResuscitated", null, 0, true);
        sendSystemMessage(self, SID_REGEN_TEMPORARILY_REDUCED);
        int visValue = actionData.getInt("intVisibilityValue");
        int visRange = actionData.getInt("intVisibilityRange");
        jedi.jediActionPerformed(self, visValue, visRange);
        playJediActionEffect(self, target, actionData);
        int xpOverride = actionData.getInt("xpOverride");
        if (xpOverride < 0)
        {
            xpOverride = forceCost;
        }
        jedi.grantJediXP(self, xpOverride);
        return true;
    }
    public static boolean transferForce(obj_id self, obj_id target, String actionName) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        if (buff.isParalyzed(self))
        {
            return false;
        }
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            return false;
        }
        target = getCorrectFriendlyTarget(self, target, actionData);
        if (!isIdValid(target))
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_this_target"));
            return false;
        }
        if (target == self || !isPlayer(target) || !isJedi(target))
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_this_target"));
            return false;
        }
        if (!doNonCombatActionPrecheck(self, target, actionData))
        {
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.drainForcePower(self, forceCost))
        {
            return false;
        }
        weapon_data weaponData = new weapon_data();
        weaponData = getWeaponData(getCurrentWeapon(self));
        int[] actionCost = combat.getActionCost(self, weaponData, actionData);
        if (!combat.canDrainCombatActionAttributes(self, actionCost))
        {
            return false;
        }
        int targetPower = rand(actionData.getInt("minForceHeal"), actionData.getInt("maxForceHeal"));
        alterForcePower(target, targetPower);
        combat.drainCombatActionAttributes(self, actionCost);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "forcetransfer_hit");
        pp.actor.set(self);
        pp.target.set(target);
        pp.digitInteger = targetPower;
        combat.sendCombatSpamMessageProse(self, target, pp, true, true, false);
        int visValue = actionData.getInt("intVisibilityValue");
        int visRange = actionData.getInt("intVisibilityRange");
        jedi.jediActionPerformed(self, visValue, visRange);
        healing._addMedicalHate(self, target, targetPower);
        playJediActionEffect(self, target, actionData);
        int xpOverride = actionData.getInt("xpOverride");
        if (xpOverride < 0)
        {
            xpOverride = forceCost;
        }
        jedi.grantJediXP(self, xpOverride);
        return true;
    }
    public static boolean channelForce(obj_id self, obj_id target, String actionName) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        if (buff.isParalyzed(self))
        {
            return false;
        }
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            return false;
        }
        target = getCorrectFriendlyTarget(self, target, actionData);
        if (!isIdValid(target))
        {
            return false;
        }
        if (!doNonCombatActionPrecheck(self, target, actionData))
        {
            return false;
        }
        int minHealth = actionData.getInt("intMinHealthHeal");
        int maxHealth = actionData.getInt("intMaxHealthHeal");
        int minAction = (int)(getMaxAttrib(self, ACTION) * (actionData.getInt("intMinActionHeal") / 100f));
        int maxAction = (int)(getMaxAttrib(self, ACTION) * (actionData.getInt("intMaxActionHeal") / 100f));
        attribute[] curAttribs = getAttribs(self);
        if (curAttribs[HEALTH].getValue() < maxHealth || !combat.canDrainCombatActionAttributes(self, new int[]
        {
            0,
            maxAction,
            0
        }))
        {
            sendSystemMessage(self, new string_id("jedi_spam", "channel_ham_too_low"));
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.drainForcePower(self, forceCost))
        {
            return false;
        }
        int newPower = rand(actionData.getInt("minForceHeal"), actionData.getInt("maxForceHeal"));
        alterForcePower(self, newPower);
        float duration = actionData.getFloat("buffDuration") * 60;
        addAttribModifier(self, HEALTH, (rand(minHealth, maxHealth) * -1), 0, 0, duration);
        combat.drainCombatActionAttributes(self, new int[]
        {
            0,
            rand(minAction, maxAction),
            0
        });
        int visValue = actionData.getInt("intVisibilityValue");
        int visRange = actionData.getInt("intVisibilityRange");
        jedi.jediActionPerformed(self, visValue, visRange);
        playJediActionEffect(self, target, actionData);
        int xpOverride = actionData.getInt("xpOverride");
        if (xpOverride < 0)
        {
            xpOverride = forceCost;
        }
        jedi.grantJediXP(self, xpOverride);
        return true;
    }
    public static boolean forceMeditate(obj_id self, String actionName) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(self, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "not_with_armor"));
            return false;
        }
        if (buff.isParalyzed(self))
        {
            return false;
        }
        actionName = combat.getBestAction(self, actionName);
        dictionary actionData = dataTableGetRow(JEDI_ACTIONS_FILE, actionName);
        if (actionData == null)
        {
            return false;
        }
        if (!doNonCombatActionPrecheck(self, self, actionData))
        {
            return false;
        }
        int forceCost = actionData.getInt("intJediPowerCost");
        if (!jedi.drainForcePower(self, forceCost))
        {
            return false;
        }
        weapon_data weaponData = new weapon_data();
        weaponData = getWeaponData(getCurrentWeapon(self));
        int[] actionCost = combat.getActionCost(self, weaponData, actionData);
        if (!combat.canDrainCombatActionAttributes(self, actionCost))
        {
            return false;
        }
        if (getState(self, STATE_MEDITATE) == 1)
        {
            sendSystemMessage(self, new string_id("jedi_spam", "already_in_meditative_state"));
            return false;
        }
        setState(self, STATE_MEDITATE, true);
        chat.setTempAnimationMood(self, "meditating");
        buff.applyBuff(self, "forceMeditate");
        combat.drainCombatActionAttributes(self, actionCost);
        int meditateRate = actionData.getInt("maxForceHeal");
        setObjVar(self, "jedi.meditate", meditateRate);
        recalculateForcePower(self);
        int visValue = actionData.getInt("intVisibilityValue");
        int visRange = actionData.getInt("intVisibilityRange");
        jedi.jediActionPerformed(self, visValue, visRange);
        playJediActionEffect(self, self, actionData);
        messageTo(self, "forceMeditateEffect", null, 5, false);
        int xpOverride = actionData.getInt("xpOverride");
        if (xpOverride < 0)
        {
            xpOverride = forceCost;
        }
        jedi.grantJediXP(self, xpOverride);
        return true;
    }
    public static void stopForceMeditate(obj_id self) throws InterruptedException
    {
        if (getState(self, STATE_MEDITATE) == 1)
        {
            setState(self, STATE_MEDITATE, false);
            chat.resetTempAnimationMood(self);
        }
        stopClientEffectObjByLabel(self, "forceMeditate");
        removeObjVar(self, "jedi.meditate");
        buff.removeBuff(self, "forceMeditate");
        recalculateForcePower(self);
    }
    public static void applyFeedbackDamage(obj_id player, obj_id target, float damage) throws InterruptedException
    {
        damage *= 1.0f - (combat.convertProtectionToPercent(jedi.applyForceShield(target, (int)damage)));
        int defense = getEnhancedSkillStatisticModifier(target, "force_defense");
        if (defense > 0)
        {
            damage = damage * (1f / (1f + (defense / 100f)));
        }
        if (damage < 1)
        {
            return;
        }
        addAttribModifier(target, HEALTH, (int)damage * -1, 0f, 0f, MOD_POOL);
        prose_package pp = new prose_package();
        pp.stringId = new string_id("cbt_spam", "forcefeedback_hit");
        pp.actor.set(player);
        pp.target.set(target);
        pp.digitInteger = (int)damage;
        combat.sendCombatSpamMessageProse(player, target, pp, true, true, true, COMBAT_RESULT_HIT);
    }
    public static int applyForceShield(obj_id defender, int damage) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(defender, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            return 0;
        }
        dictionary actionData = null;
        if (buff.hasBuff(defender, "forceShield"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceShield");
        }
        else if (buff.hasBuff(defender, "forceShield_1"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceShield_1");
        }
        else if (buff.hasBuff(defender, "forceProtection"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceProtection");
        }
        float forceShield = 0;
        if (actionData != null)
        {
            forceShield = getEnhancedSkillStatisticModifier(defender, "force_shield") * 100f;
            int forceCost = (int)(damage * getModifiedExtraForceCost(defender, actionData.getFloat("extraForceCost"), actionData.getString("actionName")));
            if (!jedi.drainForcePower(defender, forceCost))
            {
                buff.removeBuff(defender, actionData.getString("actionName"));
            }
            else 
            {
                playClientEffectObj(defender, "clienteffect/pl_force_shield_hit.cef", defender, "");
            }
        }
        return (int)forceShield;
    }
    public static int applyForceArmor(obj_id defender, int damage) throws InterruptedException
    {
        int armorCount = utils.getIntScriptVar(defender, armor.SCRIPTVAR_ARMOR_COUNT);
        if (armorCount > 0)
        {
            return 0;
        }
        dictionary actionData = null;
        if (buff.hasBuff(defender, "forceArmor"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceArmor");
        }
        else if (buff.hasBuff(defender, "forceArmor_1"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceArmor_1");
        }
        else if (buff.hasBuff(defender, "forceProtection"))
        {
            actionData = dataTableGetRow(JEDI_ACTIONS_FILE, "forceProtection");
        }
        float forceArmor = 0;
        if (actionData != null)
        {
            forceArmor = getEnhancedSkillStatisticModifier(defender, "force_armor") * 100f;
            int forceCost = (int)(damage * getModifiedExtraForceCost(defender, actionData.getFloat("extraForceCost"), actionData.getString("actionName")));
            if (!jedi.drainForcePower(defender, forceCost))
            {
                buff.removeBuff(defender, actionData.getString("actionName"));
            }
            else 
            {
                playClientEffectObj(defender, "clienteffect/pl_force_armor_hit.cef", defender, "");
            }
        }
        return (int)forceArmor;
    }
    public static void performJediReactiveDefense(obj_id attacker, obj_id defender, weapon_data weaponData, hit_result hitData, dictionary actionData) throws InterruptedException
    {
        if (weaponData.weaponType == combat.WEAPON_TYPE_FORCE_POWER && getEnhancedSkillStatisticModifier(defender, "force_absorb") > 0)
        {
            float forceAbsorb = 0f;
            if (buff.hasBuff(defender, "forceAbsorb"))
            {
                forceAbsorb = getModifiedExtraForceCost(defender, dataTableGetFloat(JEDI_ACTIONS_FILE, "forceAbsorb", "extraForceCost"), "forceAbsorb");
            }
            else 
            {
                forceAbsorb = getModifiedExtraForceCost(defender, dataTableGetFloat(JEDI_ACTIONS_FILE, "forceAbsorb_1", "extraForceCost"), "forceAbsorb_1");
            }
            int forceCost = combat.getForceCost(attacker, weaponData, actionData);
            forceAbsorb *= forceCost;
            alterForcePower(defender, (int)forceAbsorb);
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "forceabsorb_hit");
            pp.actor.set(defender);
            pp.digitInteger = (int)forceAbsorb;
            combat.sendCombatSpamMessageProse(defender, pp, COMBAT_RESULT_HIT);
            dictionary data = new dictionary();
            data.put("effect", "clienteffect/pl_force_absorb_hit.cef");
            data.put("target", defender);
            messageTo(attacker, "playDelayedClientEffect", data, 1, false);
        }
        if (weaponData.weaponType == combat.WEAPON_TYPE_FORCE_POWER && getEnhancedSkillStatisticModifier(defender, "force_feedback") > 0)
        {
            float forceFeedback = getEnhancedSkillStatisticModifier(defender, "force_feedback") / 100f;
            float feedbackDamage = forceFeedback * hitData.damage;
            jedi.applyFeedbackDamage(defender, attacker, feedbackDamage);
            dictionary data = new dictionary();
            data.put("effect", "clienteffect/pl_force_feedback_hit.cef");
            data.put("target", attacker);
            messageTo(attacker, "playDelayedClientEffect", data, 1, false);
        }
    }
    public static boolean isAvoidIncap(obj_id player) throws InterruptedException
    {
        if (buff.hasBuff(player, "avoidIncapacitation"))
        {
            return true;
        }
        if (buff.hasBuff(player, "avoidIncapacitation_1"))
        {
            return true;
        }
        if (buff.hasBuff(player, "avoidIncapacitation_2"))
        {
            return true;
        }
        if (buff.hasBuff(player, "avoidIncapacitation_3"))
        {
            return true;
        }
        if (buff.hasBuff(player, "avoidIncapacitation_4"))
        {
            return true;
        }
        if (buff.hasBuff(player, "avoidIncapacitation_5"))
        {
            return true;
        }
        return false;
    }
    public static int getModifiedForceCost(obj_id self, int baseForce, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_manipulation = getSkillStatisticModifier(self, "force_manipulation_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "forceModLight");
                return baseForce + (int)((force_manipulation * rank_mod) + 0.5);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_manipulation = getSkillStatisticModifier(self, "force_manipulation_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "forceModDark");
                return baseForce + (int)((force_manipulation * rank_mod) + 0.5);
            }
        }
        return baseForce;
    }
    public static int getModifiedMinDamage(obj_id self, int baseDamage, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_power = getSkillStatisticModifier(self, "force_power_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "minDmgModLight");
                return baseDamage + (int)((force_power * rank_mod) + 0.5);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_power = getSkillStatisticModifier(self, "force_power_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "minDmgModDark");
                return baseDamage + (int)((force_power * rank_mod) + 0.5);
            }
        }
        return baseDamage;
    }
    public static int getModifiedMaxDamage(obj_id self, int baseDamage, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_power = getSkillStatisticModifier(self, "force_power_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "maxDmgModLight");
                return baseDamage + (int)((force_power * rank_mod) + 0.5);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_power = getSkillStatisticModifier(self, "force_power_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "maxDmgModDark");
                return baseDamage + (int)((force_power * rank_mod) + 0.5);
            }
        }
        return baseDamage;
    }
    public static float getModifiedActionSpeed(obj_id self, float baseSpeed, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "delayModLight");
                return baseSpeed + (force_control * rank_mod);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "delayModDark");
                return baseSpeed + (force_control * rank_mod);
            }
        }
        return baseSpeed;
    }
    public static float getModifiedExtraForceCost(obj_id self, float baseForce, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "extraForceModLight");
                return baseForce + (force_control * rank_mod);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "extraForceModDark");
                return baseForce + (force_control * rank_mod);
            }
        }
        return baseForce;
    }
    public static int getModifiedBuffAmount(obj_id self, int baseAmount, int modNum, String actionName) throws InterruptedException
    {
        int row = dataTableSearchColumnForString(actionName, 0, JEDI_RANK_DATA);
        if (row != -1)
        {
            if (hasSkill(self, "force_rank_light_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_light");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "buffModLight" + modNum);
                return baseAmount + (int)((force_control * rank_mod) + 0.5);
            }
            else if (hasSkill(self, "force_rank_dark_novice"))
            {
                int force_control = getSkillStatisticModifier(self, "force_control_dark");
                float rank_mod = dataTableGetFloat(JEDI_RANK_DATA, row, "buffModDark" + modNum);
                return baseAmount + (int)((force_control * rank_mod) + 0.5);
            }
        }
        return baseAmount;
    }
    public static void jediSaberPearlRestore(obj_id player) throws InterruptedException
    {
    }
    public static int getMaxJediDeaths(obj_id objPlayer) throws InterruptedException
    {
        if (hasSkill(objPlayer, "jedi_light_side_master_novice") || hasSkill(objPlayer, "jedi_dark_side_master_novice"))
        {
            return MAX_JEDI_DEATHS_MASTER;
        }
        if (hasSkill(objPlayer, "jedi_light_side_journeyman_novice") || hasSkill(objPlayer, "jedi_dark_side_journeyman_novice"))
        {
            return MAX_JEDI_DEATHS_KNIGHT;
        }
        return MAX_JEDI_DEATHS_PADAWAN;
    }
    public static void jediDeathExperienceLoss(obj_id player) throws InterruptedException
    {
        jediDeathExperienceLoss(player, 1f);
    }
    public static void jediDeathExperienceLoss(obj_id player, float modifier) throws InterruptedException
    {
        final int JEDI_MIN_XP_CAP = -10000000;
        final float XP_LOSS_PERCENTAGE = -0.05f;
        final String[] JEDI_XP_TYPES = 
        {
            "jedi_general"
        };
        for (int i = 0; i < JEDI_XP_TYPES.length; i++)
        {
            int xpCap = getExperienceCap(player, JEDI_XP_TYPES[i]);
            int xpLoss = (int)(xpCap * XP_LOSS_PERCENTAGE * modifier);
            int curXp = getExperiencePoints(player, "jedi_general");
            if (curXp + xpLoss < JEDI_MIN_XP_CAP)
            {
                xpLoss = JEDI_MIN_XP_CAP - curXp;
            }
            grantExperiencePoints(player, JEDI_XP_TYPES[i], xpLoss);
            prose_package ppLostXP = prose.getPackage(SID_LOST_JEDI_XP);
            prose.setDI(ppLostXP, xpLoss);
            sendSystemMessageProse(player, ppLostXP);
        }
    }
    public static float getSaberForceCost(obj_id saber) throws InterruptedException
    {
        float forceCost = 0;
        if (hasObjVar(saber, VAR_SABER_BASE))
        {
            forceCost = getFloatObjVar(saber, VAR_SABER_BASE + "." + VAR_FORCE);
        }
        else 
        {
            forceCost = getFloatObjVar(saber, "crafting_components.forceCost");
        }
        if (forceCost < 2)
        {
            forceCost = 2;
        }
        return forceCost;
    }
    public static boolean isCrystalTuned(obj_id crystal) throws InterruptedException
    {
        if (!isIdValid(crystal) || !exists(crystal))
        {
            return false;
        }
        if (hasObjVar(crystal, VAR_CRYSTAL_OWNER))
        {
            return true;
        }
        return false;
    }
    public static boolean isCrystalOwner(obj_id player, obj_id crystal) throws InterruptedException
    {
        if (!isCrystalTuned(crystal))
        {
            return false;
        }
        obj_id owner = getObjIdObjVar(crystal, VAR_CRYSTAL_OWNER_ID);
        if (owner == player)
        {
            return true;
        }
        return false;
    }
    public static boolean doesCrystalMatchSaber(obj_id saber, obj_id crystal) throws InterruptedException
    {
        if (!isCrystalTuned(crystal))
        {
            return false;
        }
        obj_id saberOwner = getCrafter(saber);
        if (static_item.isStaticItem(saber))
        {
            
        }
        saberOwner = utils.getContainingPlayer(saber);
        obj_id owner = getObjIdObjVar(crystal, VAR_CRYSTAL_OWNER_ID);
        if (owner == saberOwner)
        {
            return true;
        }
        return false;
    }
    public static void resetSaberStats(obj_id saber) throws InterruptedException
    {
        if (!hasObjVar(saber, VAR_SABER_DEFAULT_STATS))
        {
            return;
        }
        initSaberBaseStats(saber);
        obj_id inv = getObjectInSlot(saber, "saber_inv");
        obj_id[] contents = getContents(inv);
        if (contents == null || contents.length == 0)
        {
            return;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (!isDisabled(contents[i]))
            {
                addCrystalStats(saber, contents[i]);
            }
        }
    }
    public static void initSaberBaseStats(obj_id saber) throws InterruptedException
    {
        int minDmg = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_MIN_DMG);
        int maxDmg = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_MAX_DMG);
        setWeaponMinDamage(saber, minDmg);
        setWeaponMaxDamage(saber, maxDmg);
        float speed = getFloatObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_SPEED);
        setWeaponAttackSpeed(saber, speed);
        float wound = getFloatObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_WOUND);
        setWeaponWoundChance(saber, wound);
        float radius = getFloatObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_RADIUS);
        setWeaponDamageRadius(saber, radius);
        int attackCost = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_ATTACK_COST);
        setWeaponAttackCost(saber, attackCost);
        int eDamageType = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_ELEMENTAL_DAM_TYPE);
        setWeaponElementalType(saber, eDamageType);
        int eDamageAmount = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_ELEMENTAL_DAM_AMNT);
        setWeaponElementalValue(saber, eDamageAmount);
    }
    public static void setSaberColorCrystalDamage(obj_id saber) throws InterruptedException
    {
        int color = getIntObjVar(saber, VAR_SABER_BASE + "." + VAR_COLOR);
        int colorRow = dataTableSearchColumnForInt(color, "color", JEDI_CRYSTAL_COLOR_TABLE);
        if (color == 31 || colorRow < 0)
        {
            setWeaponElementalType(saber, DAMAGE_NONE);
            setWeaponElementalValue(saber, 0);
            return;
        }
        int damageType = dataTableGetInt(JEDI_CRYSTAL_COLOR_TABLE, colorRow, "elemental_type");
        float damageAmt = dataTableGetFloat(JEDI_CRYSTAL_COLOR_TABLE, colorRow, "damage_amount");
        setWeaponElementalType(saber, damageType);
        setWeaponElementalValue(saber, (int)((getWeaponMaxDamage(saber) * damageAmt) + 1));
    }
    public static void setSaberColor(obj_id saber, int color, int shader) throws InterruptedException
    {
        String varName = "private/index_color_blade";
        custom_var myVar = getCustomVarByName(saber, varName);
        if (myVar != null && myVar.isPalColor())
        {
            palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
            if (pcVar != null)
            {
                pcVar.setValue(color);
                setObjVar(saber, VAR_SABER_BASE + "." + VAR_COLOR, color);
            }
        }
        String shaderName = "private/alternate_shader_blade";
        custom_var shaderVar = getCustomVarByName(saber, shaderName);
        if (shaderVar != null)
        {
            ranged_int_custom_var riVar = (ranged_int_custom_var)shaderVar;
            if (riVar != null)
            {
                riVar.setValue(shader);
                setObjVar(saber, VAR_SABER_BASE + "." + VAR_SHADER, shader);
            }
        }
    }
    public static void addCrystalStats(obj_id saber, obj_id crystal) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(crystal, VAR_CRYSTAL_STATS);
        if (ovl != null)
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String name = ov.getName();
                LOG("saber_test", "Processing Crystal Stat: " + name);
                if (name.equals(VAR_MIN_DMG))
                {
                    int minDmg = getWeaponMinDamage(saber);
                    LOG("saber_test", "Old Min Damage = " + minDmg);
                    minDmg += getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_MIN_DMG);
                    LOG("saber_test", "New Min Damage = " + minDmg);
                    if (!setWeaponMinDamage(saber, minDmg))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Min Damage");
                    }
                }
                else if (name.equals(VAR_MAX_DMG))
                {
                    int maxDmg = getWeaponMaxDamage(saber);
                    maxDmg += getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_MAX_DMG);
                    setWeaponMaxDamage(saber, maxDmg);
                }
                else if (name.equals(VAR_SPEED))
                {
                    float speed = getWeaponAttackSpeed(saber);
                    speed += getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_SPEED);
                    setWeaponAttackSpeed(saber, speed);
                }
                else if (name.equals(VAR_WOUND))
                {
                    float wound = getWeaponWoundChance(saber);
                    wound += getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_WOUND);
                    setWeaponWoundChance(saber, wound);
                }
                else if (name.equals(VAR_RADIUS))
                {
                    float radius = getWeaponDamageRadius(saber);
                    radius += getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_RADIUS);
                    setWeaponDamageRadius(saber, radius);
                }
                else if (name.equals(VAR_ATTACK_COST))
                {
                    int cost = getWeaponAttackCost(saber);
                    cost += getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_ATTACK_COST);
                    setWeaponAttackCost(saber, cost);
                }
                else if (name.equals(VAR_FORCE))
                {
                    float force = getFloatObjVar(saber, VAR_SABER_BASE + "." + VAR_FORCE);
                    force += getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_FORCE);
                    setObjVar(saber, VAR_SABER_BASE + "." + VAR_FORCE, force);
                }
                else if (name.equals(VAR_COLOR))
                {
                    int color = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR);
                    int shader = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_SHADER);
                    setSaberColor(saber, color, shader);
                }
            }
        }
        int minDmg = getWeaponMinDamage(saber);
        int maxDmg = getWeaponMaxDamage(saber);
        if ((minDmg - 10) >= maxDmg)
        {
            minDmg = maxDmg - 10;
            setWeaponMinDamage(saber, minDmg);
        }
        setSaberColorCrystalDamage(saber);
        weapons.setWeaponData(saber);
    }
    public static void removeCrystalStats(obj_id saber, obj_id crystal) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(crystal, VAR_CRYSTAL_STATS);
        if (ovl != null)
        {
            int numItems = ovl.getNumItems();
            for (int i = 0; i < numItems; i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String name = ov.getName();
                LOG("saber_test", "Processing Crystal Stat: " + name);
                if (name.equals(VAR_MIN_DMG))
                {
                    int minDmg = getWeaponMinDamage(saber);
                    LOG("saber_test", "Old Min Damage = " + minDmg);
                    minDmg -= getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_MIN_DMG);
                    LOG("saber_test", "New Min Damage = " + minDmg);
                    if (!setWeaponMinDamage(saber, minDmg))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Min Damage");
                    }
                }
                else if (name.equals(VAR_MAX_DMG))
                {
                    int maxDmg = getWeaponMaxDamage(saber);
                    LOG("saber_test", "Old Max Damage = " + maxDmg);
                    maxDmg -= getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_MAX_DMG);
                    LOG("saber_test", "New Max Damage = " + maxDmg);
                    if (!setWeaponMaxDamage(saber, maxDmg))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Max Damage");
                    }
                }
                else if (name.equals(VAR_SPEED))
                {
                    float speed = getWeaponAttackSpeed(saber);
                    LOG("saber_test", "Old Speed = " + speed);
                    speed -= getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_SPEED);
                    LOG("saber_test", "New Speed = " + speed);
                    if (!setWeaponAttackSpeed(saber, speed))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Speed");
                    }
                }
                else if (name.equals(VAR_WOUND))
                {
                    float wound = getWeaponWoundChance(saber);
                    LOG("saber_test", "Old Wound = " + wound);
                    wound -= getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_WOUND);
                    LOG("saber_test", "New Wound = " + wound);
                    if (!setWeaponWoundChance(saber, wound))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Wound");
                    }
                }
                else if (name.equals(VAR_RADIUS))
                {
                    float radius = getWeaponDamageRadius(saber);
                    LOG("saber_test", "Old Radius = " + radius);
                    radius -= getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_RADIUS);
                    LOG("saber_test", "New Radius = " + radius);
                    if (!setWeaponDamageRadius(saber, radius))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Radius");
                    }
                }
                else if (name.equals(VAR_ATTACK_COST))
                {
                    int cost = getWeaponAttackCost(saber);
                    LOG("saber_test", "Old Attack Cost = " + cost);
                    cost -= getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_ATTACK_COST);
                    LOG("saber_test", "New Attack Cost = " + cost);
                    if (!setWeaponAttackCost(saber, cost))
                    {
                        LOG("saber_test", "ERROR: Failed Setting Attack Cost");
                    }
                }
                else if (name.equals(VAR_FORCE))
                {
                    float force = getFloatObjVar(saber, VAR_SABER_BASE + "." + VAR_FORCE);
                    force -= getFloatObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_FORCE);
                    setObjVar(saber, VAR_SABER_BASE + "." + VAR_FORCE, force);
                }
                else if (name.equals(VAR_COLOR))
                {
                    int color = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_COLOR);
                    int shader = getIntObjVar(saber, VAR_SABER_DEFAULT_STATS + "." + VAR_SHADER);
                    setSaberColor(saber, color, shader);
                    removeObjVar(saber, VAR_SABER_BASE + "." + VAR_COLOR);
                    removeObjVar(saber, VAR_SABER_BASE + "." + VAR_SHADER);
                }
            }
        }
        setSaberColorCrystalDamage(saber);
        weapons.setWeaponData(saber);
    }
    public static boolean isColorCrystal(obj_id crystal) throws InterruptedException
    {
        return (hasObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR));
    }
    public static boolean hasColorCrystal(obj_id saber) throws InterruptedException
    {
        return (hasObjVar(saber, VAR_SABER_BASE + "." + VAR_COLOR));
    }
    public static boolean validateCrystalCount(obj_id saber) throws InterruptedException
    {
        obj_id inv = getObjectInSlot(saber, "saber_inv");
        obj_id[] contents = getContents(inv);
        return (contents.length <= MAX_CRYSTAL_COUNT);
    }
    public static int getCrystalQuality(int level) throws InterruptedException
    {
        if (level < 40)
        {
            return CRYSTAL_POOR;
        }
        else if (level < 70)
        {
            return CRYSTAL_FAIR;
        }
        else if (level < 100)
        {
            return CRYSTAL_GOOD;
        }
        else if (level < 140)
        {
            return CRYSTAL_QUALITY;
        }
        else if (level < 220)
        {
            return CRYSTAL_SELECT;
        }
        else if (level < 330)
        {
            return CRYSTAL_PREMIUM;
        }
        else 
        {
            return CRYSTAL_FLAWLESS;
        }
    }
    public static int getStaticCrystalQuality(int level) throws InterruptedException
    {
        switch (level)
        {
            case 0:
            return CRYSTAL_POOR;
            case 1:
            return CRYSTAL_FAIR;
            case 2:
            return CRYSTAL_GOOD;
            case 3:
            return CRYSTAL_QUALITY;
            case 4:
            return CRYSTAL_SELECT;
            case 5:
            return CRYSTAL_PREMIUM;
            case 6:
            return CRYSTAL_FLAWLESS;
            default:
            break;
        }
        return CRYSTAL_GOOD;
    }
    public static boolean forceCreateColorCrystal(obj_id player, int color) throws InterruptedException
    {
        obj_id crystal = createObjectInInventoryAllowOverload(TEMPLATE_FORCE_CRYSTAL, player);
        setAsColorCrystal(crystal, color);
        return isIdValid(crystal);
    }
    public static boolean createColorCrystal(obj_id inventory, int color) throws InterruptedException
    {
        obj_id crystal = static_item.createNewItemFunction(mapOldColorToNewColor(color), inventory);
        return isIdValid(crystal);
    }
    public static boolean createPowerCrystal(obj_id inventory, int level) throws InterruptedException
    {
        obj_id crystal = static_item.createNewItemFunction(mapOldPowerCrystalLevelToNew(level), inventory);
        return isIdValid(crystal);
    }
    public static void setAsColorCrystal(obj_id crystal, int color, int hp, int hpMax) throws InterruptedException
    {
        if (isIdValid(crystal))
        {
            setAsColorCrystal(crystal, color);
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_HP, hp);
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_HP_MAX, hpMax);
        }
    }
    public static void setAsColorCrystal(obj_id crystal, int color) throws InterruptedException
    {
        if (isIdValid(crystal))
        {
            custom_var myVar = getCustomVarByName(crystal, "private/index_color_1");
            if (myVar != null && myVar.isPalColor())
            {
                palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                pcVar.setValue(color);
            }
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_COLOR, color);
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_LEVEL, -1);
        }
    }
    public static void setAsPowerCrystal(obj_id crystal, int level, int hp, int hpMax) throws InterruptedException
    {
        if (isIdValid(crystal))
        {
            setAsPowerCrystal(crystal, level);
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_HP, hp);
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_HP_MAX, hpMax);
        }
    }
    public static void setAsPowerCrystal(obj_id crystal, int level) throws InterruptedException
    {
        if (isIdValid(crystal))
        {
            custom_var myVar = getCustomVarByName(crystal, "private/index_color_1");
            if (myVar != null && myVar.isPalColor())
            {
                palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                pcVar.setValue(31);
                setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_COLOR, -1);
            }
            setObjVar(crystal, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_LEVEL, level);
        }
    }
    public static boolean checkRobePermission(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isPlayer(player))
        {
            LOG("jedi", "library.jedi.robeRemove -- player is invalid");
            return false;
        }
        obj_id[] items = getContents(player);
        if (items == null)
        {
            LOG("jedi", "library.jedi.robeRemove -- " + player + "'s contents are null.");
            return false;
        }
        for (int i = 0; i < items.length; i++)
        {
            if (hasObjVar(items[i], VAR_JEDI_SKILL))
            {
                String jediSkill = getStringObjVar(items[i], VAR_JEDI_SKILL);
                if (!hasSkill(player, jediSkill))
                {
                    obj_id inv = getObjectInSlot(player, "inventory");
                    putInOverloaded(items[i], inv);
                }
            }
        }
        return true;
    }
    public static void setupJediTrainer(obj_id self) throws InterruptedException
    {
        final String[] TRAINER_TYPES = 
        {
            "trainer_brawler",
            "trainer_artisan",
            "trainer_scout",
            "trainer_marksman",
            "trainer_entertainer",
            "trainer_medic"
        };
        location locTest = getLocation(self);
        if (locTest.area.equals("tutorial"))
        {
            return;
        }
        String strPrimaryCategory = "trainer";
        String strSecondaryCategory = TRAINER_TYPES[rand(0, TRAINER_TYPES.length - 1)];
        map_location[] rawMapLocations = getPlanetaryMapLocations(strPrimaryCategory, strSecondaryCategory);
        Vector mapLocations = new Vector();
        mapLocations.setSize(0);
        location testloc = new location();
        for (int i = 0; i < rawMapLocations.length; i++)
        {
            testloc.x = rawMapLocations[i].getX();
            testloc.z = rawMapLocations[i].getY();
            testloc.area = getLocation(self).area;
            if (getCityAtLocation(testloc, 0) > 0)
            {
                continue;
            }
            mapLocations = utils.addElement(mapLocations, rawMapLocations[i]);
        }
        if ((mapLocations == null) || (mapLocations.size() == 0))
        {
            locTest = getLocation(self);
            LOG("DESIGNER_FATAL", "JEDI : For planet " + locTest.area + ", primary category : " + strPrimaryCategory + " and secondary category " + strSecondaryCategory + " No planetary map location was found");
            return;
        }
        int intRoll = rand(0, mapLocations.size() - 1);
        location locHome = getLocation(self);
        locTest = new location();
        locTest.x = ((map_location)mapLocations.get(intRoll)).getX();
        locTest.z = ((map_location)mapLocations.get(intRoll)).getY();
        locTest.area = locHome.area;
        setObjVar(self, "jedi.locTrainerLocation", locTest);
        return;
    }
    public static void clickyCombatPearlFix(obj_id item) throws InterruptedException
    {
        float damage = 0.0f;
        String template = getTemplateName(item);
        String itemName = "";
        boolean tunedCrystal = isCrystalTuned(item);
        if (hasObjVar(item, "crafting_components"))
        {
            damage = getFloatObjVar(item, "crafting_components.minDamage");
        }
        else 
        {
            damage = (getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_MAX_DMG));
        }
        if (template.endsWith("krayt_dragon_pearl.iff"))
        {
            if (tunedCrystal)
            {
                obj_id owner = getObjIdObjVar(item, VAR_CRYSTAL_OWNER_ID);
                if (damage < 20)
                {
                    itemName = "item_krayt_pearl_04_04";
                }
                else if (damage < 30)
                {
                    itemName = "item_krayt_pearl_04_05";
                }
                else if (damage < 40)
                {
                    itemName = "item_krayt_pearl_04_06";
                }
                else if (damage < 50)
                {
                    itemName = "item_krayt_pearl_04_08";
                }
                else if (damage < 60)
                {
                    itemName = "item_krayt_pearl_04_09";
                }
                else if (damage < 70)
                {
                    itemName = "item_krayt_pearl_04_11";
                }
                else if (damage < 80)
                {
                    itemName = "item_krayt_pearl_04_13";
                }
                else if (damage < 90)
                {
                    itemName = "item_krayt_pearl_04_14";
                }
                else if (damage < 100)
                {
                    itemName = "item_krayt_pearl_04_15";
                }
                else if (damage < 110)
                {
                    itemName = "item_krayt_pearl_04_16";
                }
                else if (damage < 120)
                {
                    itemName = "item_krayt_pearl_04_17";
                }
                else if (damage >= 120)
                {
                    itemName = "item_krayt_pearl_04_18";
                }
                jediComponentStatSetter(owner, item, itemName);
                CustomerServiceLog("jedi_saber", "Converting Old Tuned Pearl (" + item + ") - Damage:" + damage + " - to a new Static Item Pearl - " + itemName);
                return;
            }
            else 
            {
                int level = getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_LEVEL);
                if (level > 0)
                {
                    if (level < 20)
                    {
                        itemName = "item_krayt_pearl_04_02";
                    }
                    else if (level < 40)
                    {
                        itemName = "item_krayt_pearl_04_04";
                    }
                    else if (level < 60)
                    {
                        itemName = "item_krayt_pearl_04_05";
                    }
                    else if (level < 80)
                    {
                        itemName = "item_krayt_pearl_04_06";
                    }
                    else if (level < 100)
                    {
                        itemName = "item_krayt_pearl_04_07";
                    }
                    else if (level < 120)
                    {
                        itemName = "item_krayt_pearl_04_10";
                    }
                    else if (level < 150)
                    {
                        itemName = "item_krayt_pearl_04_13";
                    }
                    else if (level < 200)
                    {
                        itemName = "item_krayt_pearl_04_14";
                    }
                    else if (level < 250)
                    {
                        itemName = "item_krayt_pearl_04_16";
                    }
                    else if (level < 330)
                    {
                        itemName = "item_krayt_pearl_04_17";
                    }
                    else if (level >= 330)
                    {
                        itemName = "item_krayt_pearl_04_18";
                    }
                    jediComponentStatSetter(null, item, itemName);
                    CustomerServiceLog("jedi_saber", "Converting Old Untuned Pearl (" + item + ") - Level:" + level + " - to a new Static Item Pearl - " + itemName);
                    return;
                }
                else 
                {
                    itemName = "item_krayt_pearl_04_09";
                    jediComponentStatSetter(null, item, itemName);
                    CustomerServiceLog("jedi_saber", "Converting Old Untuned Pearl Missing Level (" + item + ") - Level:" + level + " - to a new Static Item Pearl - " + itemName);
                    return;
                }
            }
        }
        else if (template.endsWith("lance_module_force_crystal.iff"))
        {
            int color = getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_COLOR);
            String colorName = mapOldColorToNewColor(item);
            if (tunedCrystal)
            {
                obj_id owner = getObjIdObjVar(item, VAR_CRYSTAL_OWNER_ID);
                jediComponentStatSetter(owner, item, colorName);
                CustomerServiceLog("jedi_saber", "Converting Old Tuned Lance Color Crystal (" + item + ") - OLD COLOR:" + color + " - to a new Static Item Color Crystal - " + colorName);
                return;
            }
            else 
            {
                jediComponentStatSetter(null, item, colorName);
                CustomerServiceLog("jedi_saber", "Converting Old Untuned Lance Color Crystal (" + item + ") - OLD COLOR:" + color + " - to a new Static Item Color Crystal - " + colorName);
                return;
            }
        }
        else if (template.endsWith("lightsaber_module_force_crystal.iff"))
        {
            if (hasObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_COLOR))
            {
                int color = getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_COLOR);
                String colorName = mapOldColorToNewColor(item);
                if (tunedCrystal)
                {
                    obj_id owner = getObjIdObjVar(item, VAR_CRYSTAL_OWNER_ID);
                    jediComponentStatSetter(owner, item, colorName);
                    CustomerServiceLog("jedi_saber", "Converting Old Tuned Color Crystal (" + item + ") - OLD COLOR:" + color + " - to a new Static Item Color Crystal - " + colorName);
                    return;
                }
                else 
                {
                    jediComponentStatSetter(null, item, colorName);
                    CustomerServiceLog("jedi_saber", "Converting Old Untuned Color Crystal (" + item + ") - OLD COLOR:" + color + " - to a new Static Item Color Crystal - " + colorName);
                    return;
                }
            }
            else 
            {
                if (tunedCrystal)
                {
                    obj_id owner = getObjIdObjVar(item, VAR_CRYSTAL_OWNER_ID);
                    if (damage < 20)
                    {
                        itemName = "item_power_crystal_04_04";
                    }
                    else if (damage < 30)
                    {
                        itemName = "item_power_crystal_04_05";
                    }
                    else if (damage < 40)
                    {
                        itemName = "item_power_crystal_04_06";
                    }
                    else if (damage < 50)
                    {
                        itemName = "item_power_crystal_04_08";
                    }
                    else if (damage < 60)
                    {
                        itemName = "item_power_crystal_04_09";
                    }
                    else if (damage < 70)
                    {
                        itemName = "item_power_crystal_04_11";
                    }
                    else if (damage < 80)
                    {
                        itemName = "item_power_crystal_04_13";
                    }
                    else if (damage < 90)
                    {
                        itemName = "item_power_crystal_04_14";
                    }
                    else if (damage < 100)
                    {
                        itemName = "item_power_crystal_04_15";
                    }
                    else if (damage < 110)
                    {
                        itemName = "item_power_crystal_04_16";
                    }
                    else if (damage < 120)
                    {
                        itemName = "item_power_crystal_04_17";
                    }
                    else if (damage >= 120)
                    {
                        itemName = "item_power_crystal_04_18";
                    }
                    jediComponentStatSetter(owner, item, itemName);
                    CustomerServiceLog("jedi_saber", "Converting Old Tuned Power Crystal (" + item + ") - Damage:" + damage + " - to a new Static Item Power Crystal - " + itemName);
                    return;
                }
                else 
                {
                    int level = getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_LEVEL);
                    if (level > 0)
                    {
                        itemName = mapOldPowerCrystalLevelToNew(level);
                        jediComponentStatSetter(null, item, itemName);
                        CustomerServiceLog("jedi_saber", "Converting Old Untuned Power Crystal (" + item + ") - Level:" + level + " - to a new Static Item Crystal - " + itemName);
                        return;
                    }
                    else 
                    {
                        itemName = "item_power_crystal_04_09";
                        jediComponentStatSetter(null, item, itemName);
                        CustomerServiceLog("jedi_saber", "Converting Old Untuned Power Crystal Missing Level (" + item + ") - Level:" + level + " - to a new Static Item Crystal - " + itemName);
                        return;
                    }
                }
            }
        }
        return;
    }
    public static void jediComponentStatSetter(obj_id owner, obj_id item, String itemName) throws InterruptedException
    {
        if (itemName != null && !itemName.equals(""))
        {
            removeAllObjVars(item);
            removeObjVar(item, jedi.VAR_CRYSTAL_STATS);
            detachAllScripts(item);
            setStaticItemName(item, itemName);
            dictionary itemData = static_item.getMasterItemDictionary(itemName);
            setStaticItemVersion(item, itemData.getInt("version"));
            setObjVar(item, jedi.VAR_CRYSTAL_STATS + "." + jedi.VAR_LEVEL, -1);
            attachScript(item, "item.static_item_base");
            if (owner != null)
            {
                setObjVar(item, VAR_CRYSTAL_OWNER_ID, owner);
                setObjVar(item, jedi.VAR_CRYSTAL_OWNER_NAME, getName(owner));
            }
            LOG("jedi_saber", "Converted old Pearl or Crystal to " + itemName);
            static_item.initializeObject(item, itemData);
        }
        else 
        {
            CustomerServiceLog("jedi_saber", "Could not convert item(" + item + ") Bad itemName passed in" + itemName);
        }
        return;
    }
    public static String mapOldColorToNewColor(obj_id item) throws InterruptedException
    {
        return mapOldColorToNewColor(getIntObjVar(item, VAR_CRYSTAL_STATS + "." + VAR_COLOR));
    }
    public static String mapOldColorToNewColor(int color) throws InterruptedException
    {
        String colorMap[] = 
        {
            "item_color_crystal_02_00",
            "item_color_crystal_02_01",
            "item_color_crystal_02_02",
            "item_color_crystal_02_03",
            "item_color_crystal_02_04",
            "item_color_crystal_02_05",
            "item_color_crystal_02_06",
            "item_color_crystal_02_07",
            "item_color_crystal_02_08",
            "item_color_crystal_02_09",
            "item_color_crystal_02_10",
            "item_color_crystal_02_11",
            "item_color_crystal_02_12",
            "item_color_crystal_02_13",
            "item_color_crystal_02_14",
            "item_color_crystal_02_15",
            "item_color_crystal_02_16",
            "item_color_crystal_02_17",
            "item_color_crystal_02_18",
            "item_color_crystal_02_19",
            "item_color_crystal_02_20",
            "item_color_crystal_02_21",
            "item_color_crystal_02_22",
            "item_color_crystal_02_23",
            "item_color_crystal_02_24",
            "item_color_crystal_02_25",
            "item_color_crystal_02_26",
            "item_color_crystal_02_27",
            "item_color_crystal_02_28",
            "item_color_crystal_02_29",
            "item_color_crystal_02_30",
            "item_color_crystal_02_00"
        };
        return colorMap[color];
    }
    public static String mapOldPowerCrystalLevelToNew(int level) throws InterruptedException
    {
        if (level < 40)
        {
            return "item_power_crystal_04_03";
        }
        else if (level < 60)
        {
            return "item_power_crystal_04_04";
        }
        else if (level < 80)
        {
            return "item_power_crystal_04_06";
        }
        else if (level < 100)
        {
            return "item_power_crystal_04_07";
        }
        else if (level < 120)
        {
            return "item_power_crystal_04_08";
        }
        else if (level < 140)
        {
            return "item_power_crystal_04_09";
        }
        else if (level < 150)
        {
            return "item_power_crystal_04_11";
        }
        else if (level < 200)
        {
            return "item_power_crystal_04_15";
        }
        else if (level < 250)
        {
            return "item_power_crystal_04_16";
        }
        else if (level < 330)
        {
            return "item_power_crystal_04_17";
        }
        else if (level >= 330)
        {
            return "item_power_crystal_04_18";
        }
        else 
        {
            return "item_power_crystal_04_13";
        }
    }
    public static void initializeCrystal(obj_id crystal, int level) throws InterruptedException
    {
        if (hasObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_LEVEL))
        {
            LOG("jedi", "level already set, initializing");
            level = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_LEVEL);
        }
        LOG("jedi", "Current Crystal level = " + level);
        setObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_LEVEL, level);
        if (hasObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP) && hasObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP_MAX))
        {
            LOG("jedi", "hp already set, initializing");
            int hp = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP);
            setInvulnerableHitpoints(crystal, hp);
            removeObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP);
            int hpMax = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP_MAX);
            setMaxHitpoints(crystal, hpMax);
            removeObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_HP_MAX);
        }
        else 
        {
            final int min = 650;
            final int max = 1250;
            int range = max - min - level;
            if (range < 0)
            {
                range = 0;
                level = max - min;
            }
            int hp = min + level + rand(0, range);
            setMaxHitpoints(crystal, hp);
            setInvulnerableHitpoints(crystal, hp);
        }
        String template = getTemplateName(crystal);
        if (template.endsWith("module_force_crystal.iff"))
        {
            int color = rand(0, 11);
            if (hasObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR))
            {
                color = getIntObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR);
            }
            boolean setColor = true;
            if (color == -1)
            {
                setColor = false;
                removeObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR);
            }
            if (setColor && rand(25, 100) > level)
            {
                setObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_COLOR, color);
                setObjVar(crystal, VAR_CRYSTAL_STATS + "." + VAR_LEVEL, -1);
                custom_var myVar = getCustomVarByName(crystal, "private/index_color_1");
                if (myVar.isPalColor())
                {
                    palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                    pcVar.setValue(color);
                }
            }
            else 
            {
                custom_var myVar = getCustomVarByName(crystal, "private/index_color_1");
                if (myVar.isPalColor())
                {
                    palcolor_custom_var pcVar = (palcolor_custom_var)myVar;
                    pcVar.setValue(31);
                }
            }
        }
    }
    public static boolean isForceSensitive(obj_id player) throws InterruptedException
    {
        String classTemplate = getSkillTemplate(player);
        if (classTemplate != null && classTemplate.startsWith("force"))
        {
            return true;
        }
        return false;
    }
    public static boolean isForceSensitiveLevelRequired(obj_id player, int requiredLevel) throws InterruptedException
    {
        int playerLevel = getLevel(player);
        if (isForceSensitive(player) && playerLevel >= requiredLevel)
        {
            return true;
        }
        return false;
    }
    public static boolean hasAnyUltraCloak(obj_id player) throws InterruptedException
    {
        if (utils.playerHasStaticItemInBankOrInventory(player, JEDI_CLOAK_LIGHT_HOOD_UP))
        {
            return true;
        }
        if (utils.playerHasStaticItemInBankOrInventory(player, JEDI_CLOAK_DARK_HOOD_UP))
        {
            return true;
        }
        if (utils.playerHasStaticItemInBankOrInventory(player, JEDI_CLOAK_LIGHT_HOOD_DOWN))
        {
            return true;
        }
        if (utils.playerHasStaticItemInBankOrInventory(player, JEDI_CLOAK_DARK_HOOD_DOWN))
        {
            return true;
        }
        if (utils.playerHasStaticItemInAppearanceInventory(player, JEDI_CLOAK_LIGHT_HOOD_UP))
        {
            return true;
        }
        if (utils.playerHasStaticItemInAppearanceInventory(player, JEDI_CLOAK_DARK_HOOD_UP))
        {
            return true;
        }
        if (utils.playerHasStaticItemInAppearanceInventory(player, JEDI_CLOAK_LIGHT_HOOD_DOWN))
        {
            return true;
        }
        if (utils.playerHasStaticItemInAppearanceInventory(player, JEDI_CLOAK_DARK_HOOD_DOWN))
        {
            return true;
        }
        return false;
    }
}
