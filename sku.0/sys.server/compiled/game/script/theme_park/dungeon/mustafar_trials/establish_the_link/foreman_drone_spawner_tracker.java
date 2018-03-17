package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.healing;
import script.library.trial;
import script.library.buff;

public class foreman_drone_spawner_tracker extends script.base_script
{
    public foreman_drone_spawner_tracker()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id parent = utils.getObjIdScriptVar(self, trial.PARENT);
            messageTo(parent, "droneDied", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "selfDestruct", null, 5, false);
        playClientEffectObj(self, trial.PRT_KUBAZA_WARNING, self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "chargeForeman", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public obj_id getForeman(obj_id self) throws InterruptedException
    {
        obj_id contents[] = utils.getSharedContainerObjects(self);
        if (contents == null || contents.length == 0)
        {
            doLogging("locateForeman", "Contents list was empty, exiting");
            return obj_id.NULL_ID;
        }
        for (int i = 0; i < contents.length; i++)
        {
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.foreman"))
            {
                return contents[i];
            }
        }
        doLogging("getForeman", "Unable to locate foreman");
        return obj_id.NULL_ID;
    }
    public int chargeForeman(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id foreman = getForeman(self);
        if (!isIdValid(foreman))
        {
            messageTo(self, "selfDestruct", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        location targetLoc = getLocation(foreman);
        ai_lib.aiPathTo(self, targetLoc);
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        obj_id foreman = getForeman(self);
        if (!isIdValid(foreman))
        {
            messageTo(self, "selfDestruct", null, 0, false);
            return SCRIPT_CONTINUE;
        }
        float distance = getDistance(self, foreman);
        if (distance < 7f)
        {
            healForeman(self, foreman);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            messageTo(self, "chargeForeman", null, 0, false);
            return SCRIPT_CONTINUE;
        }
    }
    public void healForeman(obj_id self, obj_id foreman) throws InterruptedException
    {
        messageTo(self, "selfDestruct", null, 0, false);
        healing.healDamage(foreman, 10000);
        if (!buff.hasBuff(foreman, "uplink_enrage"))
        {
            buff.applyBuff(foreman, "uplink_enrage");
        }
    }
    public int selfDestruct(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] targets = trial.getValidTargetsInRadius(self, 7.0f);
        playClientEffectLoc(self, trial.PRT_KUBAZA_EXPLODE, getLocation(self), 0.4f);
        destroyObject(self);
        if (targets == null || targets.length == 0)
        {
            doLogging("nukeSelf", "No valid targets in blast radius");
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 2500);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/foreman_drone_spawner_tracker/" + section, message);
        }
    }
}
