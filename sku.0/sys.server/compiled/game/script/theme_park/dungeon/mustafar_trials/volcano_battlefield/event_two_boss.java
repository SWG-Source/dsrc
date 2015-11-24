package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.buff;
import script.library.combat;
import script.library.trial;
import script.library.dot;

public class event_two_boss extends script.base_script
{
    public event_two_boss()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_VOLCANO_TWO_BOSS);
        trial.bumpSession(self);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        trial.bumpSession(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        startAECycle(self);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        endAECycle(self);
        if (!isIncapacitated(self))
        {
            resetEncounter(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void resetEncounter(obj_id self) throws InterruptedException
    {
        clearAllAdds(self);
        respawnAdds(self);
        resetSelf(self);
    }
    public void clearAllAdds(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        obj_id[] objects = utils.getObjIdArrayScriptVar(parent, "guardList");
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
    public void respawnAdds(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        messageTo(parent, "spawnGuards", null, 2, false);
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
    }
    public void startAECycle(obj_id self) throws InterruptedException
    {
        messageTo(self, "doAEBurst", trial.getSessionDict(self), 0, false);
    }
    public void endAECycle(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
    }
    public String chooseAEType() throws InterruptedException
    {
        String[] types = 
        {
            "wave",
            "airfall",
            "cone"
        };
        return types[rand(0, types.length - 1)];
    }
    public void cycleNextAE(obj_id self) throws InterruptedException
    {
        messageTo(self, "doAEBurst", trial.getSessionDict(self), 18, false);
    }
    public int doAEBurst(obj_id self, dictionary params) throws InterruptedException
    {
        String type = chooseAEType();
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        if (type.equals("wave"))
        {
            messageTo(self, "doWavePreBurst", trial.getSessionDict(self), 0, false);
            messageTo(self, "doWaveBurst", trial.getSessionDict(self), 4, false);
            return SCRIPT_CONTINUE;
        }
        if (type.equals("airfall"))
        {
            messageTo(self, "doAirfallPreBurst", trial.getSessionDict(self), 0, false);
            messageTo(self, "doAirfallBurst", trial.getSessionDict(self), 4, false);
            return SCRIPT_CONTINUE;
        }
        if (type.equals("cone"))
        {
            messageTo(self, "doConePreBurst", trial.getSessionDict(self), 0, false);
            messageTo(self, "doConeBurst", trial.getSessionDict(self), 4, false);
            return SCRIPT_CONTINUE;
        }
        doLogging("doAEBurst", "Invalid type passed. Performing airburst");
        messageTo(self, "doAirfallPreBurst", trial.getSessionDict(self), 0, false);
        messageTo(self, "doAirfallBurst", trial.getSessionDict(self), 4, false);
        return SCRIPT_CONTINUE;
    }
    public int doWavePreBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_WAVE_PRE, self, "root");
        return SCRIPT_CONTINUE;
    }
    public int doWaveBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 96.0f);
        if (targets == null)
        {
            doLogging("doWaveBurst", "No valid targets in area");
            cycleNextAE(self);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_WAVE_EXE, self, "root");
        for (int i = 0; i < targets.length; i++)
        {
            if (targets[i] == getTarget(self))
            {
                int tankDamage = 2000;
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_wave_hit");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = tankDamage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                dot.applyDotEffect(targets[i], self, dot.DOT_FIRE, "blast_wave_dot", HEALTH, -1, tankDamage / 10, 60, true, null);
                damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, tankDamage);
            }
            else 
            {
                float distance = getDistance(self, targets[i]);
                int damage = Math.round(30000.0f / distance);
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_wave_hit");
                pp.actor.set(self);
                pp.target.set(targets[i]);
                pp.digitInteger = damage;
                combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                dot.applyDotEffect(targets[i], self, dot.DOT_FIRE, "blast_wave_dot", HEALTH, -1, damage / 10, 300, true, null);
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
        if (targets == null)
        {
            doLogging("doAirfallBurst", "No valid targets in area");
            cycleNextAE(self);
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, trial.PRT_VOLCANO_AIR_EXE, getLocation(self), 4.0f);
        for (int i = 0; i < targets.length; i++)
        {
            float distance = getDistance(self, targets[i]);
            float modDistance = (distance / 20.0f);
            if (modDistance == 0.0)
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
    public int doConePreBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_CONE_PRE, self, "muzzle");
        return SCRIPT_CONTINUE;
    }
    public int doConeBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getTarget(self);
        obj_id[] targetsInCone = trial.getValidTargetsInCone(self, target, 96, 30);
        if (targetsInCone == null)
        {
            doLogging("doConeBurst", "There were no valid targets in cone");
            cycleNextAE(self);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_VOLCANO_CONE_EXE, self, "muzzle");
        for (int i = 0; i < targetsInCone.length; i++)
        {
            if (targetsInCone[i] == target)
            {
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_cone_hit");
                pp.actor.set(self);
                pp.target.set(targetsInCone[i]);
                pp.digitInteger = 2500;
                combat.sendCombatSpamMessageProse(targetsInCone[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(targetsInCone[i], DAMAGE_ELEMENTAL_COLD, HIT_LOCATION_BODY, 2500);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = new string_id("cbt_spam", "blast_cone_hit");
                pp.actor.set(self);
                pp.target.set(targetsInCone[i]);
                pp.digitInteger = 3400;
                combat.sendCombatSpamMessageProse(targetsInCone[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
                damage(targetsInCone[i], DAMAGE_ELEMENTAL_COLD, HIT_LOCATION_BODY, 8500);
            }
        }
        cycleNextAE(self);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_two_boss/" + section, message);
        }
    }
}
