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
import script.library.trial;

public class event_one_guard extends script.base_script
{
    public event_one_guard()
    {
    }
    public static final String SHIELD = "effect_shield";
    public static final boolean doLogging = false;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        stopClientEffectObjByLabel(self, SHIELD);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_VOLCANO_ONE_GUARD);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, "parent");
        obj_id boss = getObjIdObjVar(self, "boss");
        dictionary dict = new dictionary();
        dict.put("type", "guard");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        if (isIdValid(boss))
        {
            messageTo(boss, "guardDied", null, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int beginAttack(obj_id self, dictionary params) throws InterruptedException
    {
        stopClientEffectObjByLabel(self, SHIELD);
        obj_id[] players = trial.getValidTargetsInRadius(self, 80);
        if (players == null || players.length == 0)
        {
            doLogging("beginAttack", "Found no players to attack");
            return SCRIPT_CONTINUE;
        }
        obj_id toAttack = trial.getClosest(self, players);
        if (!isIdValid(toAttack))
        {
            doLogging("beginAttack", "player toAttack, was invalid");
            return SCRIPT_CONTINUE;
        }
        setInvulnerable(self, false);
        startCombat(self, toAttack);
        return SCRIPT_CONTINUE;
    }
    public int stopAttack(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        playClientEffectObj(self, trial.PRT_INVULN_SHIELD, self, "", null, SHIELD);
        clearHateList(self);
        stopCombat(self);
        return SCRIPT_CONTINUE;
    }
    public int activateShield(obj_id self, dictionary params) throws InterruptedException
    {
        playClientEffectObj(self, trial.PRT_INVULN_SHIELD, self, "", null, SHIELD);
        return SCRIPT_CONTINUE;
    }
    public int healBoss(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "boss"))
        {
            doLogging("healBoss", "Did not have the boss objvar");
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id boss = getObjIdObjVar(self, "boss");
        dictionary dict = new dictionary();
        dict.put("guard", self);
        messageTo(boss, "performGuardHeal", dict, 0, false);
        messageTo(self, "healBoss", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_one_guard/" + section, message);
        }
    }
}
