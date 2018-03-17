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
import script.library.dot;
import script.library.combat;
import script.library.prose;
import script.library.trial;

public class event_four_boss extends script.base_script
{
    public event_four_boss()
    {
    }
    public static final String GUARD = "som_volcano_four_lava_beetle";
    public static final int BEETLE_RESPAWN = 31;
    public static final int POISON_RECAST = 35;
    public static final int DISEASE_RECAST = 60;
    public static final int FORCE_DRAIN_RECAST = 22;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        trial.setHp(self, trial.HP_VOLCANO_FOUR_BOSS);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int activate(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        startAECycle(self);
        startAddCycle(self);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
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
        resetSelf(self);
    }
    public void clearAllAdds(obj_id self) throws InterruptedException
    {
        obj_id[] objects = trial.getObjectsInRangeWithScriptVar(self, trial.VOLCANO_FOUR_IS_BEETLE, 400.0f);
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
    public void resetSelf(obj_id self) throws InterruptedException
    {
        int max = getMaxHealth(self);
        int current = getHealth(self);
        int toHeal = max - current;
        addToHealth(self, toHeal);
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
        setObjVar(self, "eventActive", false);
    }
    public void startAECycle(obj_id self) throws InterruptedException
    {
        messageTo(self, "doAEBurst", trial.getSessionDict(self), 4, false);
    }
    public void startAddCycle(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnAdd", trial.getSessionDict(self), 6, false);
    }
    public boolean isEventActive(obj_id self) throws InterruptedException
    {
        return getBooleanObjVar(self, "eventActive");
    }
    public int spawnAdd(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        location[] spawnLocs = getValidSpawnLocations(self);
        obj_id[] players = getPlayerCreaturesInRange(self, 150);
        boolean notify = true;
        if (players == null || players.length == 0)
        {
            notify = false;
        }
        if (spawnLocs == null || spawnLocs.length == 0)
        {
            doLogging("spawnAdd", "Could not find a valid spawn location");
            return SCRIPT_CONTINUE;
        }
        for (int k = 0; k < spawnLocs.length; k++)
        {
            obj_id beetle = create.object(GUARD, spawnLocs[k]);
            if (!isIdValid(beetle))
            {
                doLogging("spawnAdd", "Attemplted to create beetle but failed");
                return SCRIPT_CONTINUE;
            }
            setYaw(beetle, rand(0, 360));
            attachScript(beetle, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_four_guard");
            setObjVar(beetle, "boss", self);
        }
        if (notify)
        {
            prose_package pp = prose.getPackage(trial.VOLCANO_CYM_BEETLE_NOTIFY, self);
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageProse(players[i], pp);
            }
        }
        messageTo(self, "spawnAdd", trial.getSessionDict(self), BEETLE_RESPAWN, false);
        return SCRIPT_CONTINUE;
    }
    public location[] getValidSpawnLocations(obj_id self) throws InterruptedException
    {
        obj_id[] objects = getObjectsInRange(self, 400);
        if (objects == null || objects.length == 0)
        {
            doLogging("getValidSpawnLocations", "There were no objects in range");
            return null;
        }
        Vector validLoc = new Vector();
        validLoc.setSize(0);
        for (int i = 0; i < objects.length; i++)
        {
            if (hasObjVar(objects[i], "event_5_spawn_point"))
            {
                utils.addElement(validLoc, getLocation(objects[i]));
            }
        }
        if (validLoc == null || validLoc.size() == 0)
        {
            doLogging("getValidSpawnLocations", "The spawn objects could not be found");
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
    public int doAEBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "doPoisonAE", trial.getSessionDict(self), 1, false);
        messageTo(self, "doForceDrainAE", trial.getSessionDict(self), 8, false);
        return SCRIPT_CONTINUE;
    }
    public int doPoisonAE(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 200.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("doPoisonAE", "No valid targets in area");
            messageTo(self, "doPoisonAE", trial.getSessionDict(self), POISON_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_CYM_POISON, self, "");
        for (int i = 0; i < targets.length; i++)
        {
            dot.applyDotEffect(targets[i], self, dot.DOT_POISON, "volcano_boss_poison_cloud", HEALTH, 125, 455, 30, true, null);
            playClientEffectObj(targets[i], trial.PRT_CYM_POISON, targets[i], "");
        }
        messageTo(self, "doPoisonAE", trial.getSessionDict(self), POISON_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int doDiseaseAE(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 200.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("doDiseaseAE", "No valid targets in area");
            messageTo(self, "doDiseaseAE", trial.getSessionDict(self), DISEASE_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        playClientEffectObj(self, trial.PRT_CYM_DISEASE, self, "");
        for (int i = 0; i < targets.length; i++)
        {
            dot.applyDotEffect(targets[i], self, dot.DOT_DISEASE, "volcano_boss_disease_cloud", HEALTH, 125, 600, 54, true, null);
            playClientEffectObj(targets[i], trial.PRT_CYM_DISEASE, targets[i], "");
        }
        messageTo(self, "doDiseaseAE", trial.getSessionDict(self), DISEASE_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int doForceDrainAE(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] targets = trial.getValidTargetsInRadius(self, 200.0f);
        if (targets == null || targets.length == 0)
        {
            doLogging("doForceDrainAE", "No valid targets in area");
            messageTo(self, "doForceDrainAE", trial.getSessionDict(self), FORCE_DRAIN_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < targets.length; i++)
        {
            if (true)
            {
                drainAttributes(targets[i], 1000, 0);
                String effect = "clienteffect/pl_force_channel_self.cef";
                playClientEffectObj(self, effect, targets[i], "");
            }
        }
        messageTo(self, "doForceDrainAE", trial.getSessionDict(self), FORCE_DRAIN_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_four_boss/" + section, message);
        }
    }
}
