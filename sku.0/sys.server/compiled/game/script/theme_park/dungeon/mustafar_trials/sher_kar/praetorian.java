package script.theme_park.dungeon.mustafar_trials.sher_kar;

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
import script.library.buff;

public class praetorian extends script.base_script
{
    public praetorian()
    {
    }
    public static final int RECAST = 8;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_SHER_KAR_PRAETORIAN);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int doAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInDungeon(trial.getTop(self));
        obj_id closest = trial.getClosest(self, players);
        startCombat(self, closest);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        messageTo(self, "doShockWave", trial.getSessionDict(self), 4, false);
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int doShockWave(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInRadius(self, 12.0f);
        if (players == null || players.length == 0)
        {
            messageTo(self, "doShockWave", trial.getSessionDict(self), RECAST, false);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            buff.applyBuff(players[i], "sk_shockWave");
        }
        messageTo(self, "doShockWave", trial.getSessionDict(self), RECAST, false);
        return SCRIPT_CONTINUE;
    }
}
