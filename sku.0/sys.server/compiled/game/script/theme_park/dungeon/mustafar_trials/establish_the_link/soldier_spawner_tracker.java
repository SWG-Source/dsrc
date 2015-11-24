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
import script.library.trial;

public class soldier_spawner_tracker extends script.base_script
{
    public soldier_spawner_tracker()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (trial.isUplinkActive(self))
        {
            obj_id parent = utils.getObjIdScriptVar(self, trial.PARENT);
            int respawn = rand(75, 100);
            messageTo(parent, "soldierDied", null, respawn, false);
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
        findTargets(self);
        setInvulnerable(self, true);
        messageTo(self, "removeInvulnerable", null, 8, false);
        messageTo(self, "engageTarget", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public void findTargets(obj_id self) throws InterruptedException
    {
        obj_id contents[] = utils.getSharedContainerObjects(self);
        if (contents == null || contents.length == 0)
        {
            doLogging("findTargets", "Contents list was empty, exiting");
            return;
        }
        Vector targets = new Vector();
        targets.setSize(0);
        for (int i = 0; i < contents.length; i++)
        {
            if (isPlayer(contents[i]))
            {
                utils.addElement(targets, contents[i]);
            }
            if (hasScript(contents[i], "theme_park.dungeon.mustafar_trials.establish_the_link.droid_patrol_script"))
            {
                utils.addElement(targets, contents[i]);
            }
        }
        if (targets == null)
        {
            doLogging("findTargets", "No targets were found, exiting");
            return;
        }
        obj_id[] targetList = new obj_id[0];
        if (targets != null)
        {
            targetList = new obj_id[targets.size()];
            targets.toArray(targetList);
        }
        if (targetList.length == 0)
        {
            doLogging("findTargets", "Target list was empty, exiting");
            return;
        }
        utils.setScriptVar(self, "targetList", targetList);
    }
    public int engageTarget(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] targets = utils.getObjIdArrayScriptVar(self, "targetList");
        obj_id randomTarget = targets[rand(0, targets.length - 1)];
        startCombat(self, randomTarget);
        return SCRIPT_CONTINUE;
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
            damage(targets[i], DAMAGE_ELEMENTAL_HEAT, HIT_LOCATION_BODY, 500);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.UPLINK_LOGGING)
        {
            LOG("logging/soldier_spawner_tracker/" + section, message);
        }
    }
}
