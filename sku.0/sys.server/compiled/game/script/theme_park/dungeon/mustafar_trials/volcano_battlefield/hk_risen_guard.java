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

public class hk_risen_guard extends script.base_script
{
    public hk_risen_guard()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginAttack", trial.getSessionDict(self), 3, false);
        messageTo(self, "healBoss", trial.getSessionDict(self), 10, false);
        trial.setHp(self, trial.HP_VOLCANO_HK_RISEN_GUARD);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int healBoss(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id boss = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("guard", self);
        messageTo(boss, "performGuardHeal", dict, 0, false);
        messageTo(self, "healBoss", trial.getSessionDict(self), 10, false);
        return SCRIPT_CONTINUE;
    }
    public int beginAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 90.0f);
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
        startCombat(self, toAttack);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/hk_risen_guard/" + section, message);
        }
    }
}
