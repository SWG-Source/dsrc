package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.buff;
import script.library.trial;

public class decrepit_guardian extends script.base_script
{
    public decrepit_guardian()
    {
    }
    public static final float DEBUFF_RECAST = 60;
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        trial.setHp(self, trial.HP_DECREPIT_GUARDIAN);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        trial.setGuardianLockState(self, false);
        messageTo(top, "winTrial", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        startEventActions(self);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public void startEventActions(obj_id self) throws InterruptedException
    {
        messageTo(self, "doAEBurst", trial.getSessionDict(self), 4, false);
        messageTo(self, "doTankDebuff", trial.getSessionDict(self), 4, false);
    }
    public int doTankDebuff(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getTarget(self);
        if (target == null || isIncapacitated(target))
        {
            messageTo(self, "doTankDebuff", trial.getSessionDict(self), 10, false);
        }
        buff.applyBuff(target, "hunted");
        messageTo(self, "doTankDebuff", trial.getSessionDict(self), DEBUFF_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public int OnLostTarget(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        messageTo(self, "doTankDebuff", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int doAEBurst(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        String[] effects = 
        {
            "guardian_taint"
        };
        applyAEDebuffs(self, effects);
        messageTo(self, "doAEBurst", trial.getSessionDict(self), DEBUFF_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void applyAEDebuffs(obj_id self, String[] effects) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 100);
        if (players == null || players.length == 0)
        {
            doLogging("applyAEDebuffs", "Could find no valid players to afflict");
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            buff.applyBuff(players[i], effects);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/decrepit_guardian/" + section, message);
        }
    }
}
