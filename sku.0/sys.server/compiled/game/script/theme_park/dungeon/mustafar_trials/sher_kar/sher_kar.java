package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.trial;
import script.library.space_dungeon;
import script.library.combat;
import script.library.prose;
import script.library.buff;

public class sher_kar extends script.base_script
{
    public sher_kar()
    {
    }
    public static final int AE_RECAST = 32;
    public static final int AE_DAMAGE = 600;
    public static final float HEALTH_TRIGGER_1 = 0.95f;
    public static final float HEALTH_TRIGGER_2 = 0.80f;
    public static final float HEALTH_TRIGGER_3 = 0.65f;
    public static final float HEALTH_TRIGGER_4 = 0.50f;
    public static final float HEALTH_TRIGGER_5 = 0.35f;
    public static final float HEALTH_TRIGGER_6 = 0.20f;
    public static final float HEALTH_TRIGGER_7 = 0.05f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_SHER_KAR);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.bumpSession(self);
        messageTo(trial.getTop(self), "sherKarDied", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "performAe", trial.getSessionDict(self), 0, false);
        messageTo(self, "doEnrage", trial.getSessionDict(self), 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        if (!isDead(self))
        {
            resetSelf(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        verifyHealth(self);
        return SCRIPT_CONTINUE;
    }
    public int doEnrage(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "enrageWarning", null, 0, false);
        messageTo(self, "enrageBuff", trial.getSessionDict(self), 5, false);
        return SCRIPT_CONTINUE;
    }
    public int enrageWarning(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        utils.sendSystemMessage(players, trial.MONSTER_ENRAGE_WARNING);
        return SCRIPT_CONTINUE;
    }
    public int enrageBuff(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        buff.applyBuff(self, "sher_kar_rage");
        messageTo(self, "doEnrage", trial.getSessionDict(self), 75, false);
        return SCRIPT_CONTINUE;
    }
    public int performAe(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = null;
        if (trial.getTop(self) == self)
        {
            targets = trial.getValidTargetsInRadius(self, 96.0f);
        }
        else 
        {
            targets = trial.getPlayersInDungeon(trial.getTop(self));
        }
        if (targets == null && targets.length == 0)
        {
            trial.bumpSession(self);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            int damage = AE_DAMAGE;
            prose_package pp = new prose_package();
            pp.stringId = new string_id("cbt_spam", "sk_cave_in");
            pp.actor.set(self);
            pp.target.set(targets[i]);
            pp.digitInteger = damage;
            combat.sendCombatSpamMessageProse(targets[i], self, pp, true, true, false, COMBAT_RESULT_HIT);
            playClientEffectLoc(targets[i], trial.PRT_SHER_KAR_CAVE_IN, getLocation(targets[i]), 14.0f);
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, damage);
        }
        messageTo(self, "performAe", trial.getSessionDict(self), AE_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        ai_lib.clearCombatData();
        trial.healAssemblyUnit(self);
        String[] healthObjVar = 
        {
            "stage1",
            "stage2",
            "stage3",
            "stage4",
            "stage5",
            "stage6",
            "stage7"
        };
        utils.removeObjVarList(self, healthObjVar);
    }
    public void verifyHealth(obj_id self) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= HEALTH_TRIGGER_1)
        {
            if (ratio <= HEALTH_TRIGGER_2)
            {
                if (ratio <= HEALTH_TRIGGER_3)
                {
                    if (ratio <= HEALTH_TRIGGER_4)
                    {
                        if (ratio <= HEALTH_TRIGGER_5)
                        {
                            if (ratio <= HEALTH_TRIGGER_6)
                            {
                                if (ratio <= HEALTH_TRIGGER_7)
                                {
                                    doHealthEvent(self, 7);
                                    return;
                                }
                                doHealthEvent(self, 6);
                                return;
                            }
                            doHealthEvent(self, 5);
                            return;
                        }
                        doHealthEvent(self, 4);
                        return;
                    }
                    doHealthEvent(self, 3);
                    return;
                }
                doHealthEvent(self, 2);
                return;
            }
            doHealthEvent(self, 1);
            return;
        }
    }
    public void doHealthEvent(obj_id self, int value) throws InterruptedException
    {
        String stage = "stage" + Integer.toString(value);
        if (!hasObjVar(self, stage))
        {
            setObjVar(self, stage, true);
            switch (value)
            {
                case 1:
                messageTo(trial.getTop(self), "spawnAdd", null, 0, false);
                return;
                case 2:
                messageTo(trial.getTop(self), "spawnAdd", null, 0, false);
                return;
                case 3:
                messageTo(trial.getTop(self), "spawnAdd", null, 0, false);
                return;
                case 4:
                messageTo(trial.getTop(self), "doMidEvent", null, 0, false);
                return;
                case 5:
                messageTo(trial.getTop(self), "spawnAdd", null, 0, false);
                return;
                case 6:
                messageTo(trial.getTop(self), "spawnAdd", null, 0, false);
                return;
                case 7:
                messageTo(trial.getTop(self), "doEndEvent", null, 0, false);
            }
        }
    }
}
