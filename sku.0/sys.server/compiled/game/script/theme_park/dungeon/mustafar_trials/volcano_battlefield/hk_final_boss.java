package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.*;

public class hk_final_boss extends script.base_script
{
    public hk_final_boss()
    {
    }
    public static final String GUARD = "som_volcano_final_risen_sustainer";
    public static final String KUBAZA = "som_volcano_final_lava_beetle";
    public static final String AKBOT = "som_volcano_final_walker";
    public static final String GKBOT = "som_volcano_final_septipod";
    public static final int DISTRACTION_RECAST = 24;
    public static final int SWITCH_RECAST = 24;
    public static final int DEBUFF_RECAST = 45;
    public static final int POISON_RECAST = 40;
    public static final int RAISE_RECAST = 48;
    public static final int DISEASE_RECAST = 120;
    public static final int AE_NUKE_RECAST = 50;
    public static final int FORCE_DRAIN_RECAST = 34;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        trial.setHp(self, trial.HP_VOLCANO_HK47);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        endEventActions(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (isIdValid(corpseInventory)) {
            int x = rand(1, 100);
            if (x <= 15) {  // 15% chance at dropping bonus loot Mustafar Bunker
                createObject("object/building/player/player_mustafar_house_lg.iff", corpseInventory, "");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        startEventActions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isDead(self))
        {
            endEventActions(self);
            verifyHealthReset(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostTarget(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        messageTo(self, "applyDistraction", trial.getSessionDict(self), 3, false);
        return SCRIPT_CONTINUE;
    }
    public void startEventActions(obj_id self) throws InterruptedException
    {
        messageTo(self, "startAECycle", trial.getSessionDict(self), 1, false);
        messageTo(self, "raiseGuard", trial.getSessionDict(self), 14, false);
        messageTo(self, "switchTarget", trial.getSessionDict(self), 24, false);
        messageTo(self, "applyDistraction", trial.getSessionDict(self), 3, false);
    }
    public void endEventActions(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
    }
    public int activate(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] targets = trial.getValidTargetsInRadius(self, 90.0f);
        obj_id toAttack = trial.getClosest(self, targets);
        startCombat(self, toAttack);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= 0.8)
        {
            if (ratio <= 0.5)
            {
                if (ratio <= 0.2)
                {
                    summonAdd(self, 20);
                    return SCRIPT_CONTINUE;
                }
                summonAdd(self, 50);
                return SCRIPT_CONTINUE;
            }
            summonAdd(self, 80);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void verifyHealthReset(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        String[] eventObjVar = 
        {
            "spawned80",
            "spawned50",
            "spawned20",
            "ytSummoned",
            "ytStrafing",
            "stopRezEffect"
        };
        utils.removeObjVarList(self, eventObjVar);
        clearAllAdds(self);
        obj_id parent = trial.getParent(self);
        messageTo(parent, "spawnSquadLeaders", null, 0, false);
        setInvulnerable(self, true);
        trial.bumpSession(self);
    }
    public void callYT(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "ytSummoned"))
        {
            return;
        }
        obj_id parent = trial.getParent(self);
        messageTo(parent, "landYT", null, 0, false);
        setObjVar(self, "ytSummoned", true);
    }
    public dictionary getAddType(obj_id self, int level) throws InterruptedException
    {
        dictionary dict = new dictionary();
        if (level == 80)
        {
            dict.put("type", KUBAZA);
            dict.put("wp", "hk_beetle");
            return dict;
        }
        if (level == 50)
        {
            dict.put("type", GKBOT);
            dict.put("wp", "hk_septipod");
            return dict;
        }
        if (level == 20)
        {
            dict.put("type", AKBOT);
            dict.put("wp", "hk_walker");
            return dict;
        }
        return dict;
    }
    public void clearAllAdds(obj_id self) throws InterruptedException
    {
        obj_id[] objects = trial.getChildrenInRange(self, self, 200.0f);
        if (objects == null || objects.length == 0)
        {
            doLogging("clearAllAdds", "There are no objects in range");
            return;
        }
        for (int i = 0; i < objects.length; i++)
        {
            trial.cleanupNpc(objects[i]);
        }
    }
    public void summonAdd(obj_id self, int value) throws InterruptedException
    {
        String spawned = "spawned" + Integer.toString(value);
        if (!hasObjVar(self, spawned))
        {
            dictionary dict = new dictionary();
            dict.put("level", value);
            messageTo(self, "summonAdd", dict, 1, false);
            setObjVar(self, spawned, true);
            return;
        }
    }
    public int summonAdd(obj_id self, dictionary params) throws InterruptedException
    {
        int level = params.getInt("level");
        dictionary spawnData = getAddType(self, level);
        if (spawnData == null)
        {
            doLogging("summonAdd", "getAddType returned null");
            return SCRIPT_CONTINUE;
        }
        String toSpawn = spawnData.getString("type");
        String wp = spawnData.getString("wp");
        location[] spawnLocs = getAddSpawnLocations(self, 400, wp);
        obj_id[] players = trial.getValidTargetsInRadius(self, 400.0f);
        boolean notify = true;
        if (players == null || players.length == 0)
        {
            notify = false;
        }
        if (spawnLocs == null || spawnLocs.length == 0)
        {
            doLogging("summonAdd", "Could not find a valid spawn location");
            return SCRIPT_CONTINUE;
        }
        for (int k = 0; k < spawnLocs.length; k++)
        {
            obj_id spawned = create.object(toSpawn, spawnLocs[k]);
            if (!isIdValid(spawned))
            {
                doLogging("summonAdd", "Attemplted to create " + toSpawn + " but failed");
                return SCRIPT_CONTINUE;
            }
            messageTo(spawned, "beginAttack", null, 2, false);
            setYaw(spawned, rand(0, 360));
            trial.setParent(self, spawned, false);
            if (toSpawn.equals(KUBAZA))
            {
                attachScript(spawned, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_beetle");
            }
            if (toSpawn.equals(AKBOT))
            {
                attachScript(spawned, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_ak_guardian");
            }
            if (toSpawn.equals(GKBOT))
            {
                attachScript(spawned, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_gk_septipod");
            }
        }
        if (notify)
        {
            prose_package pp = prose.getPackage(trial.VOLCANO_CYM_BEETLE_NOTIFY, self);
            if (toSpawn.equals(KUBAZA))
            {
                pp = prose.getPackage(trial.VOLCANO_CYM_BEETLE_NOTIFY, self);
            }
            if (toSpawn.equals(AKBOT))
            {
                pp = prose.getPackage(trial.VOLCANO_HK_SUMMON_AK, self);
            }
            if (toSpawn.equals(GKBOT))
            {
                pp = prose.getPackage(trial.VOLCANO_OPP_ADD_NOTIFY, self);
            }
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageProse(players[i], pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public location[] getAddSpawnLocations(obj_id self, float range, String type) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, range);
        if (objects == null || objects.length == 0)
        {
            doLogging("getTrioSpawnLocations", "There were no objects in range");
            return null;
        }
        Vector validLoc = new Vector();
        validLoc.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], type))
            {
                utils.addElement(validLoc, getLocation(objects[i]));
            }
        }
        if (validLoc == null || validLoc.size() == 0)
        {
            doLogging("getAddSpawnLocations", "The specified spawn locations could not be found");
            return null;
        }
        location[] goodLoc = new location[0];
        if (validLoc != null)
        {
            goodLoc = new location[validLoc.size()];
            validLoc.toArray(goodLoc);
        }
        return goodLoc;
    }
    public int raiseGuard(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        location here = getLocation(self);
        obj_id[] objects = trial.getObjectsInRangeWithScriptVar(here, trial.VOLCANO_FINAL_IS_CORPSE, 200.0f);
        if (objects == null || objects.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        performRez(self, objects);
        return SCRIPT_CONTINUE;
    }
    public void performRez(obj_id self, obj_id[] corpseList) throws InterruptedException
    {
        obj_id corpseToRez = corpseList[rand(0, corpseList.length - 1)];
        playClientEffectLoc(self, trial.PRT_DROID_REVIVE, getLocation(self), 4.0f);
        location corpseLoc = getLocation(corpseToRez);
        playClientEffectLoc(corpseToRez, trial.PRT_DROID_REVIVE, corpseLoc, 4.0f);
        destroyObject(corpseToRez);
        obj_id eventGuard = create.object(GUARD, corpseLoc);
        if (!isIdValid(eventGuard))
        {
            doLogging("performRez", "Failed to generate a resurrected boss");
            return;
        }
        setYaw(eventGuard, rand(0, 360));
        attachScript(eventGuard, "theme_park.dungeon.mustafar_trials.volcano_battlefield.hk_risen_guard");
        ai_lib.setDefaultCalmBehavior(eventGuard, ai_lib.BEHAVIOR_SENTINEL);
        trial.setParent(self, eventGuard, false);
        messageTo(self, "raiseGuard", trial.getSessionDict(self), RAISE_RECAST, false);
    }
    public int performGuardHeal(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id healingGuard = params.getObjId("guard");
        if (!isIdValid(healingGuard) || !exists(healingGuard))
        {
            doLogging("performGuardHeal", "The guard that tried to heal me was invalid");
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(healingGuard, trial.PRT_DROID_HEAL, healingGuard, "");
        playClientEffectObj(self, trial.PRT_DROID_HEAL, self, "");
        addToHealth(self, 1000);
        return SCRIPT_CONTINUE;
    }
    public int startAECycle(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "performDebuffAe", trial.getSessionDict(self), 15, false);
        messageTo(self, "performPoisonAe", trial.getSessionDict(self), 27, false);
        messageTo(self, "performDamageAe", trial.getSessionDict(self), 35, false);
        return SCRIPT_CONTINUE;
    }
    public int performDebuffAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        String[] effects = getDebuffEffects(self);
        applyAEDebuffs(self, effects);
        messageTo(self, "performDebuffAe", trial.getSessionDict(self), DEBUFF_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public String[] getDebuffEffects(obj_id self) throws InterruptedException
    {
        String[] hamTypes = 
        {
            "torpor",
            "vacuity"
        };
        String[] debuffTypes = 
        {
            "lethargy",
            "toxic_dissolution"
        };
        String[] skillTypes = 
        {
            "obfuscation",
            "confusion"
        };
        String hamType = getAEType(self, "debuff.ham", hamTypes);
        String debuffType = getAEType(self, "debuff.debuff", debuffTypes);
        String skillType = getAEType(self, "debuff.skill", skillTypes);
        String[] returnTypes = 
        {
            hamType,
            debuffType,
            skillType
        };
        return returnTypes;
    }
    public String getAEType(obj_id self, String tracking, String[] choices) throws InterruptedException
    {
        int idx = 0;
        if (utils.hasScriptVar(self, tracking))
        {
            idx = utils.getIntScriptVar(self, tracking);
        }
        String nextDebuff = choices[idx];
        idx += 1;
        if (idx > choices.length - 1)
        {
            idx = 0;
        }
        utils.setScriptVar(self, tracking, idx);
        return nextDebuff;
    }
    public void applyAEDebuffs(obj_id self, String[] effects) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 200.0f);
        if (players == null || players.length == 0)
        {
            doLogging("applyAEDebuffs", "Could find no valid players to afflict");
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            for (int k = 0; k < effects.length; k++)
            {
                buff.applyBuff(players[i], effects[k]);
            }
        }
    }
    public String chooseAEType() throws InterruptedException
    {
        String[] types = 
        {
            "wave",
            "wave"
        };
        return types[rand(0, types.length - 1)];
    }
    public int performDamageAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        String type = chooseAEType();
        if (type.equals("wave"))
        {
            messageTo(self, "doWavePreBurst", trial.getSessionDict(self), 3, false);
            messageTo(self, "doWaveBurst", trial.getSessionDict(self), 7, false);
            return SCRIPT_CONTINUE;
        }
        if (type.equals("airfall"))
        {
            messageTo(self, "doAirfallPreBurst", trial.getSessionDict(self), 3, false);
            messageTo(self, "doAirfallBurst", trial.getSessionDict(self), 7, false);
            return SCRIPT_CONTINUE;
        }
        doLogging("performDamageAe", "Invalid type passed. Performing airburst");
        messageTo(self, "doAirfallPreBurst", trial.getSessionDict(self), 3, false);
        messageTo(self, "doAirfallBurst", trial.getSessionDict(self), 7, false);
        return SCRIPT_CONTINUE;
    }
    public int doWavePreBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_WAVE_PRE, self, "");
        return SCRIPT_CONTINUE;
    }
    public int doWaveBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 96.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("doWaveBurst", "No valid targets in area");
            cycleNextAE(self);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_WAVE_EXE, self, "");
        for (int i = 0; i < targets.length; i++)
        {
            if (targets[i] == getTarget(self))
            {
                int tankDamage = 400;
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_wave_hit");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = tankDamage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 1500);
            }
            else 
            {
                float distance = getDistance(self, targets[i]);
                int damage = Math.round(15000.0f / distance);
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_wave_hit");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = damage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                dot.applyDotEffect(targets[i], self, dot.DOT_FIRE, "blast_wave_dot", HEALTH, -1, damage / 10, 60, true, null);
                damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, damage);
            }
        }
        cycleNextAE(self);
        return SCRIPT_CONTINUE;
    }
    public int doAirfallPreBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, trial.PRT_VOLCANO_AIR_PRE, getLocation(self), 4.0f);
        return SCRIPT_CONTINUE;
    }
    public int doAirfallBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 96.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("doAirfallBurst", "No valid targets in area");
            cycleNextAE(self);
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, trial.PRT_VOLCANO_AIR_EXE, getLocation(self), 4.0f);
        for (int i = 0; i < targets.length; i++)
        {
            float distance = getDistance(self, targets[i]);
            float modDistance = (distance / 20);
            if (modDistance == 0)
            {
                modDistance = 0.1f;
            }
            int damage = (int)(modDistance * 3000.0f);
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "airburst_hit");
            pp.actor.set(self);
            pp.target.set(targets[i]);
            pp.digitInteger = damage;
            combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
            damage(targets[i], DAMAGE_ELEMENTAL_ELECTRICAL, HIT_LOCATION_BODY, damage);
        }
        cycleNextAE(self);
        return SCRIPT_CONTINUE;
    }
    public void cycleNextAE(obj_id self) throws InterruptedException
    {
        messageTo(self, "performDamageAe", trial.getSessionDict(self), AE_NUKE_RECAST, false);
    }
    public int performPoisonAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 200.0f);
        if (targets == null)
        {
            doLogging("performPoisonAe", "No valid targets in area");
            messageTo(self, "performPoisonAe", trial.getSessionDict(self), POISON_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_CYM_POISON, self, "");
        for (int i = 0; i < targets.length; i++)
        {
            dot.applyDotEffect(targets[i], self, dot.DOT_POISON, "volcano_boss_poison_cloud", HEALTH, 125, 235, 30, true, null);
            playClientEffectObj(targets[i], trial.PRT_CYM_POISON, targets[i], "");
        }
        messageTo(self, "performPoisonAe", trial.getSessionDict(self), POISON_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int performDiseaseAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 300.0f);
        if (targets == null)
        {
            doLogging("performDiseaseAe", "No valid targets in area");
            messageTo(self, "performDiseaseAe", trial.getSessionDict(self), DISEASE_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_CYM_DISEASE, self, "");
        for (int i = 0; i < targets.length; i++)
        {
            dot.applyDotEffect(targets[i], self, dot.DOT_DISEASE, "volcano_boss_disease_cloud", HEALTH, -1, 1200, 54, true, null);
            playClientEffectObj(targets[i], trial.PRT_CYM_DISEASE, targets[i], "");
        }
        messageTo(self, "performDiseaseAe", trial.getSessionDict(self), DISEASE_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int performForceDrainAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 300.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("performForceDrainAe", "No valid targets in area");
            messageTo(self, "performForceDrainAe", trial.getSessionDict(self), FORCE_DRAIN_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (isJedi(targets[i]))
            {
                int forceDamage = 210;
                int currentForce = getForcePower(targets[i]);
                if (forceDamage > currentForce)
                {
                    forceDamage = currentForce;
                }
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "forcedrain_hit");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = forceDamage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                alterForcePower(targets[i], (-1 * forceDamage));
                String effect = "clienteffect/pl_force_channel_self.cef";
                playClientEffectObj(self, effect, targets[i], "");
            }
        }
        messageTo(self, "performForceDrainAe", trial.getSessionDict(self), FORCE_DRAIN_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int switchTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] enemies = getHateList(self);
        if (enemies == null || enemies.length == 0)
        {
            doLogging("switchTarget", "I dont' hate anyone");
            return SCRIPT_CONTINUE;
        }
        if (enemies.length == 1)
        {
            doLogging("switchTarget", "I have a solo target at the moment, will stay on him");
            messageTo(self, "switchTarget", trial.getSessionDict(self), SWITCH_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        obj_id target = getTarget(self);
        buff.applyBuff(target, "enfeeble");
        removeHateTarget(self, target);
        messageTo(self, "switchTarget", trial.getSessionDict(self), SWITCH_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int applyDistraction(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getTarget(self);
        buff.applyBuff(target, "distraction");
        messageTo(self, "applyDistraction", trial.getSessionDict(self), DISTRACTION_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_five_boss/" + section, message);
        }
    }
}
