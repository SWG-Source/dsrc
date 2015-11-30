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

public class event_five_boss extends script.base_script
{
    public event_five_boss()
    {
    }
    public static final String GUARD = "som_volcano_five_septipod";
    public static final String MIDGUARD = "som_volcano_five_midguard";
    public static final int SWITCH_RECAST = 18;
    public static final int DISTRACTION_RECAST = 24;
    public static final int DEBUFF_RECAST = 30;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        trial.setHp(self, trial.HP_VOLCANO_FIVE_BOSS);
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        trial.bumpSession(self);
        obj_id parent = trial.getParent(self);
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
        startEventActions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        if (!isDead(self))
        {
            verifyHealthReset(self);
        }
        endEventActions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLostTarget(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        messageTo(self, "applyDistraction", trial.getSessionDict(self), 3, false);
        return SCRIPT_CONTINUE;
    }
    public int activate(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        return SCRIPT_CONTINUE;
    }
    public void startEventActions(obj_id self) throws InterruptedException
    {
        messageTo(self, "doAEBurst", trial.getSessionDict(self), 4, false);
        messageTo(self, "switchTarget", trial.getSessionDict(self), 24, false);
        messageTo(self, "applyDistraction", trial.getSessionDict(self), 3, false);
    }
    public void endEventActions(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= 0.8)
        {
            if (ratio <= 0.6)
            {
                if (ratio <= 0.5)
                {
                    if (ratio <= 0.4)
                    {
                        if (ratio <= 0.2)
                        {
                            spawnTrioAdd(self, 20);
                            return SCRIPT_CONTINUE;
                        }
                        spawnTrioAdd(self, 40);
                        return SCRIPT_CONTINUE;
                    }
                    spawnMidGuard(self);
                    return SCRIPT_CONTINUE;
                }
                spawnTrioAdd(self, 60);
                return SCRIPT_CONTINUE;
            }
            spawnTrioAdd(self, 80);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void verifyHealthReset(obj_id self) throws InterruptedException
    {
        if (isInvulnerable(self))
        {
            doLogging("verifyHealthReset", "Out of combat due to invulnerability, do not remove triggers");
            return;
        }
        else 
        {
            doLogging("verifyHealthReset", "I am out of combat and not invulnerable, heal and remove triggers");
            int max = getMaxHealth(self);
            int current = getHealth(self);
            int toHeal = max - current;
            addToHealth(self, toHeal);
            String[] healthObjVar = 
            {
                "spawned80",
                "spawned60",
                "spawnedMidguard",
                "spawned40",
                "spawned20",
                "deadMidguard"
            };
            utils.removeObjVarList(self, healthObjVar);
            clearAllAdds(self);
            ai_lib.clearCombatData();
            setInvulnerable(self, true);
        }
    }
    public int eventMobDied(obj_id self, dictionary params) throws InterruptedException
    {
        String type = params.getString("type");
        if (type.equals("trio"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (type.equals("midguard"))
        {
            int count = 0;
            if (hasObjVar(self, "deadMidguard"))
            {
                count = getIntObjVar(self, "deadMidguard");
            }
            count += 1;
            if (count == 6)
            {
                resumeAttack(self);
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "deadMidguard", count);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void resumeAttack(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] players = trial.getValidTargetsInRadius(self, 200.0f);
        if (players == null || players.length == 0)
        {
            verifyHealthReset(self);
            return;
        }
        obj_id toAttack = trial.getClosest(self, players);
        startCombat(self, toAttack);
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
    public void spawnTrioAdd(obj_id self, int value) throws InterruptedException
    {
        String spawned = "spawned" + Integer.toString(value);
        if (!hasObjVar(self, spawned))
        {
            messageTo(self, "spawnTrioAdd", null, 0, false);
            setObjVar(self, spawned, true);
            return;
        }
    }
    public void spawnMidGuard(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "spawnedMidguard"))
        {
            setInvulnerable(self, true);
            clearHateList(self);
            stopCombat(self);
            setObjVar(self, "spawnedMidguard", true);
            messageTo(self, "spawnMidGuard", null, 5, false);
            return;
        }
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
    public int spawnTrioAdd(obj_id self, dictionary params) throws InterruptedException
    {
        location[] spawnLocs = getAddSpawnLocations(self, 400, "trioAddSpawn");
        obj_id[] players = trial.getValidTargetsInRadius(self, 150.0f);
        boolean notify = true;
        if (players == null || players.length == 0)
        {
            notify = false;
        }
        if (spawnLocs == null || spawnLocs.length == 0)
        {
            doLogging("spawnTrioAdd", "Could not find a valid spawn location");
            return SCRIPT_CONTINUE;
        }
        for (int k = 0; k < spawnLocs.length; k++)
        {
            obj_id beetle = create.object(GUARD, spawnLocs[k]);
            if (!isIdValid(beetle))
            {
                doLogging("spawnTrioAdd", "Attemplted to create beetle but failed");
                return SCRIPT_CONTINUE;
            }
            setYaw(beetle, rand(0, 360));
            attachScript(beetle, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_five_guard");
            trial.setParent(self, beetle, false);
        }
        if (notify)
        {
            prose_package pp = prose.getPackage(trial.VOLCANO_OPP_ADD_NOTIFY, self);
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessageProse(players[i], pp);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spawnMidGuard(obj_id self, dictionary params) throws InterruptedException
    {
        location[] spawnLocs = getAddSpawnLocations(self, 400, "midguardSpawn");
        obj_id[] players = trial.getValidTargetsInRadius(self, 150.0f);
        boolean notify = true;
        if (players == null || players.length == 0)
        {
            notify = false;
        }
        if (spawnLocs == null || spawnLocs.length == 0)
        {
            doLogging("spawnTrioAdd", "Could not find a valid spawn location");
            return SCRIPT_CONTINUE;
        }
        for (int k = 0; k < spawnLocs.length; k++)
        {
            obj_id beetle = create.object(MIDGUARD, spawnLocs[k]);
            if (!isIdValid(beetle))
            {
                doLogging("spawnTrioAdd", "Attemplted to create beetle but failed");
                return SCRIPT_CONTINUE;
            }
            setYaw(beetle, rand(0, 360));
            attachScript(beetle, "theme_park.dungeon.mustafar_trials.volcano_battlefield.event_five_midguard");
            trial.setParent(self, beetle, false);
        }
        if (notify)
        {
            prose_package pp = prose.getPackage(trial.VOLCANO_OPP_MIDGUARD, self);
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
    public String[] getDebuffEffects(obj_id self) throws InterruptedException
    {
        String[] hamTypes = 
        {
            "bio_etheric_shock",
            "torpor",
            "vacuity"
        };
        String[] debuffTypes = 
        {
            "lethargy",
            "wavering",
            "toxic_dissolution"
        };
        String[] skillTypes = 
        {
            "obfuscation",
            "confusion",
            "corrosion"
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
    public int doAEBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        String[] effects = getDebuffEffects(self);
        applyAEDebuffs(self, effects);
        messageTo(self, "doAEBurst", trial.getSessionDict(self), DEBUFF_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void applyAEDebuffs(obj_id self, String[] effects) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 400.0f);
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
            messageTo(self, "switchTarget", trial.getSessionDict(self), SWITCH_RECAST, false);
            return SCRIPT_CONTINUE;
        }
        obj_id target = getTarget(self);
        buff.applyBuff(target, "enfeeble");
        setHate(self, target, 1f);
        messageTo(self, "switchTarget", trial.getSessionDict(self), SWITCH_RECAST, false);
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
