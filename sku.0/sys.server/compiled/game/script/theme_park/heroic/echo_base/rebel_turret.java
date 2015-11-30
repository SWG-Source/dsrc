package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.combat;
import script.library.factions;
import script.library.prose;
import script.library.trial;
import script.library.utils;

public class rebel_turret extends script.base_script
{
    public rebel_turret()
    {
    }
    public static final String HOTH_TURRET_DAMAGE_BUFF = "hoth_turret_damage_bonus";
    public static final String PHASE_3_TURRET = "p3_turret";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, PHASE_3_TURRET))
        {
            buff.applyBuff(self, HOTH_TURRET_DAMAGE_BUFF);
            trial.setHp(self, rand(80000, 100000));
            return SCRIPT_CONTINUE;
        }
        buff.applyBuff(self, HOTH_TURRET_DAMAGE_BUFF);
        trial.setHp(self, rand(100000, 150000));
        return SCRIPT_CONTINUE;
    }
    public int InstanceFaction(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int boredHothTurrets(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, PHASE_3_TURRET))
        {
            return SCRIPT_CONTINUE;
        }
        float minMessageTime = 2.5f;
        float maxMessageTime = 7.5f;
        if (hasObjVar(self, "hoth_turret"))
        {
            obj_id turretTarget = getTarget(self);
            if (turretTarget == null)
            {
                obj_id[] atats = trial.getObjectsInInstanceByObjVar(self, "isAtat");
                if (atats.length > 0)
                {
                    int attackChoice = rand(0, atats.length - 1);
                    if (isInvulnerable(atats[attackChoice]))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (isDead(atats[attackChoice]) || !canSee(self, atats[attackChoice]))
                    {
                        messageTo(self, "boredHothTurrets", null, 1.0f, false);
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        if (getDistance(self, atats[attackChoice]) > 700.0f)
                        {
                            messageTo(self, "boredHothTurrets", null, 10.0f, false);
                            return SCRIPT_CONTINUE;
                        }
                        else 
                        {
                            faceTo(self, atats[attackChoice]);
                            dictionary turretParams = new dictionary();
                            turretParams.put("target", atats[attackChoice]);
                            messageTo(self, "delayedHothTurretShot", turretParams, 1.0f, false);
                            messageTo(self, "boredHothTurrets", null, getRandomTriggerTime(minMessageTime, maxMessageTime), false);
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
            }
            else 
            {
                messageTo(self, "boredHothTurrets", null, getRandomTriggerTime(minMessageTime, maxMessageTime), false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int delayedHothTurretShot(obj_id self, dictionary params) throws InterruptedException
    {
        location loc = getLocation(self);
        obj_id player = getClosestPlayer(loc);
        if (!isIdValid(player))
        {
            player = self;
        }
        obj_id target = params.getObjId("target");
        play2dNonLoopingSound(player, "sound/hoth_turret_bolt.snd");
        createClientProjectileObjectToObject(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "muzzle", target, "root", 125.0f, 3.0f, false, 0, 0, 0, 0);
        hothTurretShotApplyDamage(self, target, player);
        return SCRIPT_CONTINUE;
    }
    public void hothTurretShotApplyDamage(obj_id turret, obj_id target, obj_id player) throws InterruptedException
    {
        if (!isIdValid(turret))
        {
            return;
        }
        if (!isIdValid(target))
        {
            return;
        }
        if (!isIdValid(player))
        {
            return;
        }
        if (isInvulnerable(target))
        {
            return;
        }
        int boltDamage = 2000;
        if (hasObjVar(turret, "isRebelInstance"))
        {
            boltDamage = rand(1500, 3000);
        }
        else 
        {
            boltDamage = rand(15000, 20000);
        }
        location loc = getLocation(turret);
        obj_id debugPlayer = getClosestPlayer(loc);
        if (isGod(debugPlayer) && hasObjVar(debugPlayer, "hoth.turretSpeak"))
        {
            sendSystemMessageTestingOnly(debugPlayer, "Turret (" + turret + ") hit ATAT (" + target + ") for: " + boltDamage + " points of damage.");
        }
        damage(target, DAMAGE_ENERGY, HIT_LOCATION_BODY, boltDamage);
    }
    public float getRandomTriggerTime(float minTime, float maxTime) throws InterruptedException
    {
        float triggerTime = rand(minTime, maxTime);
        return triggerTime;
    }
}
