package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.beast_lib;
import script.library.instance;
import script.library.pclib;
import script.library.pet_lib;
import script.library.trial;
import script.library.utils;

public class at_at extends script.base_script
{
    public at_at()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.creature_combat");
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        setRegenRate(self, HEALTH, 0);
        setForceShowHam(self, true);
        obj_id parent = trial.getParent(self);
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        int team = instance.getInstanceTeam(parent);
        if (team == 1)
        {
            int currentHitpoints = getMaxHealth(self);
            int hitPointAdjust = currentHitpoints / 5;
            currentHitpoints += hitPointAdjust;
            trial.setHp(self, currentHitpoints);
        }
        messageTo(self, "findTarget", null, rand(5.0f, 10.0f), false);
        messageTo(self, "findVehicleTarget", null, rand(5.0f, 10.0f), false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id target) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(target) && !pet_lib.isPet(target) && !beast_lib.isBeast(target))
        {
            return SCRIPT_CONTINUE;
        }
        if (aiIsKiller(self))
        {
            if (pet_lib.isPet(target))
            {
                pet_lib.killPet(target);
            }
            else if (beast_lib.isBeast(target))
            {
                beast_lib.killBeast(target, self);
            }
            else 
            {
                pclib.coupDeGrace(target, self);
            }
        }
        trial.bumpSession(self, "target");
        return SCRIPT_CONTINUE;
    }
    public int findTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] allTar = getObjectsInCone(self, utils.findLocInFrontOfTarget(self, 96.0f), 96.0f, 30.0f);
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        if (allTar == null || allTar.length == 0)
        {
            messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < allTar.length; i++)
        {
            if (allTar[i] == self)
            {
                continue;
            }
            if (!pvpCanAttack(self, allTar[i]))
            {
                continue;
            }
            validTargets.add(allTar[i]);
        }
        if (validTargets == null || validTargets.size() == 0)
        {
            messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
            return SCRIPT_CONTINUE;
        }
        obj_id target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
        if (!isIdValid(target))
        {
            target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
        }
        if (!isIdValid(target) || !exists(target))
        {
            messageTo(self, "findTarget", null, rand(2.0f, 4.0f), false);
            return SCRIPT_CONTINUE;
        }
        String templateName = getTemplateName(self);
        if (templateName.equals("object/mobile/hailfire_droid.iff"))
        {
            queueCommand(self, (71122711), target, "", COMMAND_PRIORITY_DEFAULT);
        }
        else 
        {
            queueCommand(self, (-2138719921), target, "", COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "findTarget", null, rand(4.0f, 6.0f), false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        int time = utils.getIntScriptVar(self, "last_mine");
        int now = getGameTime();
        if (now > time)
        {
            queueCommand(self, (-391726601), self, "", COMMAND_PRIORITY_DEFAULT);
            utils.setScriptVar(self, "last_mine", now + 12);
        }
        return SCRIPT_CONTINUE;
    }
    public int atatGeneratorShoot(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] generator = trial.getObjectsInInstanceByObjVar(self, "hoth_generator");
        if (generator == null || generator.length < 0)
        {
            LOG("doLogging", "Main Generator does not exist - list is null or zero - no 'charged shot' will be fired.");
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        obj_id player = getClosestPlayer(loc);
        if (!isIdValid(player))
        {
            player = self;
        }
        if (generator.length > 1)
        {
            LOG("doLogging", "Mulitple Main Generators same instance shooting with ATAT(" + self + ") at Generator[0](" + generator[0] + ").");
        }
        else 
        {
            if (!isIdValid(generator[0]))
            {
                LOG("doLogging", "Generator obj_id is Invalid! did not exist in instance - can not blow up.");
                return SCRIPT_CONTINUE;
            }
            location generatorLoc = getLocation(generator[0]);
            LOG("doLogging", "ATAT(" + self + ") shoots and destroys Main Generator(" + generator[0] + ").");
            createClientProjectileObjectToLocation(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "muzzle", generatorLoc, 125.0f, 5.0f, false, 0, 0, 0, 0);
            createClientProjectileObjectToLocation(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "muzzle2", generatorLoc, 125.0f, 5.0f, false, 0, 0, 0, 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int findVehicleTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] allTar = getObjectsInCone(self, utils.findLocInFrontOfTarget(self, 96.0f), 96.0f, 30.0f);
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        if (allTar == null || allTar.length == 0)
        {
            messageTo(self, "findVehicleTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < allTar.length; i++)
        {
            if (isPlayer(allTar[i]) && pvpCanAttack(self, allTar[i]) && getState(allTar[i], STATE_RIDING_MOUNT) == 1)
            {
                LOG("maggie", "Player Found - Added and Exited");
                validTargets.add(allTar[i]);
                break;
            }
            if (ai_lib.isVehicle(allTar[i]) && allTar[i] != self && pvpCanAttack(self, allTar[i]) && !isDead(allTar[i]))
            {
                validTargets.add(allTar[i]);
            }
        }
        if (validTargets == null || validTargets.size() == 0)
        {
            messageTo(self, "findVehicleTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
        if (!isIdValid(target) || !exists(target))
        {
            messageTo(self, "findVehicleTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        obj_id player = getClosestPlayer(loc);
        if (!isIdValid(player))
        {
            player = self;
        }
        if (getDistance(self, target) < 20.0f)
        {
            messageTo(self, "findVehicleTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (!isDead(target) && exists(target))
        {
            queueCommand(self, (-2005662168), target, "", COMMAND_PRIORITY_DEFAULT);
            createClientProjectileObjectToObject(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "l_turretgun", target, "root", 125.0f, 3.0f, false, 0, 0, 0, 0);
            createClientProjectileObjectToObject(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "r_turretgun", target, "root", 125.0f, 3.0f, false, 0, 0, 0, 0);
        }
        messageTo(self, "findVehicleTarget", null, rand(2.0f, 4.0f), false);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        int advanceObjvar;
        if (hasObjVar(self, "advancePoint") && hasObjVar(self, "atatNumber"))
        {
            advanceObjvar = getIntObjVar(self, "advancePoint");
            trial.sendSequenceTrigger(getStringObjVar(self, "atatNumber") + "_spawnPoint_" + advanceObjvar);
            LOG("maggie", getStringObjVar(self, "atatNumber") + " has advanced to Point: " + advanceObjvar);
        }
        else 
        {
            String atatNumber = getStringObjVar(self, "atatNumber");
            trial.sendSequenceTrigger(atatNumber + "_respawn");
            LOG("maggie", getStringObjVar(self, "atatNumber") + " has defaulted to original spawn location.");
        }
        return SCRIPT_CONTINUE;
    }
    public int ATATforceCheck(obj_id self, dictionary params) throws InterruptedException
    {
        String atatNumber = "";
        int maxDeathCount = 5;
        int checkpoint = 0;
        int numDead = 0;
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "checkpoint") && hasObjVar(self, "forceDead"))
        {
            numDead = getIntObjVar(self, "forceDead");
            if (numDead >= maxDeathCount)
            {
                if (hasObjVar(self, "atatNumber"))
                {
                    atatNumber = getStringObjVar(self, "atatNumber");
                    if (hasObjVar(self, "checkpoint"))
                    {
                        checkpoint = getIntObjVar(self, "checkpoint");
                    }
                    trial.sendSequenceTrigger(atatNumber + "_spawnForce_" + checkpoint);
                    setObjVar(self, "forceDead", 0);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int ATATnumDeadIncrement(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "forceDead"))
        {
            int numDead = getIntObjVar(self, "forceDead");
            numDead++;
            setObjVar(self, "forceDead", numDead);
        }
        else 
        {
            setObjVar(self, "forceDead", 1);
        }
        return SCRIPT_CONTINUE;
    }
}
