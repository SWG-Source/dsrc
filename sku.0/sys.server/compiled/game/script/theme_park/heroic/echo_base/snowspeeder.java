package script.theme_park.heroic.echo_base;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;

import java.util.Vector;

public class snowspeeder extends script.base_script
{
    public snowspeeder()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.creature_combat");
        detachScript(self, "systems.vehicle_system.vehicle_ping");
        detachScript(self, "systems.vehicle_system.vehicle_base");
        removeTriggerVolume(ai_lib.ALERT_VOLUME_NAME);
        removeTriggerVolume(ai_lib.AGGRO_VOLUME_NAME);
        setRegenRate(self, HEALTH, 0);
        trial.setHp(self, rand(400000, 500000));
        messageTo(self, "findTarget", null, rand(10.0f, 20.0f), false);
        messageTo(self, "snowspeederHeight", null, rand(5.0f, 8.0f), false);
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
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] allTar = getObjectsInCone(self, utils.findLocInFrontOfTarget(self, 96.0f), 96.0f, 30.0f);
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        if (allTar == null || allTar.length <= 0)
        {
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (obj_id obj_id : allTar) {
            if (obj_id == self) {
                continue;
            }
            if (!pvpCanAttack(self, obj_id)) {
                continue;
            }
            if (isDead(obj_id)) {
                continue;
            }
            validTargets.add(obj_id);
        }
        if (validTargets == null || validTargets.size() == 0)
        {
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id target = ((obj_id)validTargets.get(rand(0, validTargets.size() - 1)));
        String social = ai_lib.getSocialGroup(target);
        if (social == null)
        {
            LOG("logMillbarge", "Creature's Social Group was Null - Bailing");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (!exists(target))
        {
            LOG("logMillbarge", "Target does not exist!");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdNull(target))
        {
            LOG("logMillbarge", "Target ID is NULL!");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(target))
        {
            LOG("logMillbarge", "Object ID is INVALID!!!");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (isDead(target))
        {
            LOG("logMillbarge", "TARGET is DEAD!!!!");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (social.equals("tauntaun"))
        {
            LOG("logMillbarge", "IT IS A TAUNTAUN!");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (!canSee(self, target))
        {
            LOG("logMillbarge", "Can not see TARGET! - Re-Rolling");
            messageTo(self, "findTarget", null, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(self);
        obj_id player = getClosestPlayer(loc);
        if (!isIdValid(player))
        {
            player = self;
        }
        createClientProjectileObjectToObject(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "muzzle", target, "root", 125.0f, 3.0f, false, 0, 0, 0, 0);
        createClientProjectileObjectToObject(player, "object/weapon/ranged/turret/shared_turret_energy.iff", self, "muzzle2", target, "root", 125.0f, 3.0f, false, 0, 0, 0, 0);
        queueCommand(self, (1891091261), target, "", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "findTarget", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int snowspeederHeight(obj_id self, dictionary params) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDisabled(self))
        {
            vehicle.setHoverHeight(self, 1.0f);
            destroySnowSpeeder(self);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "speederHeight"))
        {
            monitorAtatProgress(self);
            float newHeight = rand(8.0f, 16.0f);
            vehicle.setHoverHeight(self, newHeight);
            setObjVar(self, "speederHeight", newHeight);
            messageTo(self, "snowspeederHeight", null, rand(5.0f, 8.0f), false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        if (hasObjVar(self, "speederHeight"))
        {
            vehicle.setHoverHeight(self, 0.2f);
            destroySnowSpeeder(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void destroySnowSpeeder(obj_id disabledSnowspeeder) throws InterruptedException
    {
        setInvulnerable(disabledSnowspeeder, true);
        location loc = getLocation(disabledSnowspeeder);
        obj_id player = getClosestPlayer(loc);
        playClientEffectObj(player, "appearance/pt_aerialstrike_explosion.prt", disabledSnowspeeder, "root");
        dictionary disabledAiSpeeder = new dictionary();
        disabledAiSpeeder.put("disabledHothSpeeder", disabledSnowspeeder);
        messageTo(disabledSnowspeeder, "deleteDisabledSnowspeeder", disabledAiSpeeder, 1.0f, false);
    }
    public int deleteDisabledSnowspeeder(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id disabledHothSpeeder = params.getObjId("disabledHothSpeeder");
        destroyObject(disabledHothSpeeder);
        return SCRIPT_CONTINUE;
    }
    public void monitorAtatProgress(obj_id snowspeeder) throws InterruptedException
    {
        if (!isIdValid(snowspeeder) || !exists(snowspeeder))
        {
            return;
        }
        obj_id[] atats = trial.getObjectsInInstanceByObjVar(snowspeeder, "isAtat");
        if (atats == null || atats.length == 0)
        {
            return;
        }
        for (obj_id atat : atats) {
            if (isInvulnerable(atat)) {
                setObjVar(snowspeeder, "respawn", -1);
            }
        }
    }
}
