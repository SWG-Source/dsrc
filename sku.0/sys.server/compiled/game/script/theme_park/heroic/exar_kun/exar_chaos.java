package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.chat;
import script.library.combat;
import script.library.healing;
import script.library.trial;
import script.library.utils;

public class exar_chaos extends script.base_script
{
    public exar_chaos()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 525000);
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        setMovementPercent(self, 0.8f);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "kun_chaos_set");
        buff.applyBuff(self, "kun_twin_ranged_shield");
        messageTo(self, "chaos_zap", trial.getSessionDict(self, "zap"), 20.0f, false);
        messageTo(self, "distance_buff", trial.getSessionDict(self, "distance"), 3.0f, false);
        obj_id harmony = getFirstObjectWithScript(getLocation(self), 100.0f, "theme_park.heroic.exar_kun.exar_harmony");
        setObjVar(self, "twin", harmony);
        buff.applyBuff(self, "mind_trick_immune");
        return SCRIPT_CONTINUE;
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self, "zap");
        trial.bumpSession(self, "distance");
        return SCRIPT_CONTINUE;
    }
    public int chaos_zap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "zap"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r7");
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            queueCommand(self, (-50743325), players[i], "", COMMAND_PRIORITY_DEFAULT);
        }
        executeHateSwap(self);
        messageTo(self, "chaos_zap", trial.getSessionDict(self, "zap"), 15.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void executeHateSwap(obj_id self) throws InterruptedException
    {
        obj_id twin = getObjIdObjVar(self, "twin");
        if (!isIdValid(twin) || !exists(twin) || isDead(twin))
        {
            return;
        }
        obj_id selfTarget = getHateTarget(self);
        float selfHate = getMaxHate(self);
        obj_id twinTarget = getHateTarget(twin);
        float twinHate = getMaxHate(twin);
        startCombat(self, twinTarget);
        startCombat(twin, selfTarget);
        setHate(self, twinTarget, selfHate + 10000.0f);
        setHate(twin, selfTarget, twinHate + 10000.0f);
        return;
    }
    public int distance_buff(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "distance"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id twin = getObjIdObjVar(self, "twin");
        if (isDead(self) || !isIdValid(twin) || !exists(twin) || isDead(twin))
        {
            if (buff.hasBuff(self, "kun_twin_bonus"))
            {
                buff.removeBuff(self, "kun_twin_bonus");
            }
            if (isIdValid(twin) && exists(twin))
            {
                buff.removeBuff(twin, "kun_twin_bonus");
            }
            return SCRIPT_CONTINUE;
        }
        float distance = getDistance(self, twin);
        if (distance <= 10.0f)
        {
            if (!buff.hasBuff(self, "kun_twin_bonus"))
            {
                buff.applyBuff(self, "kun_twin_bonus");
            }
            if (!buff.hasBuff(twin, "kun_twin_bonus"))
            {
                buff.applyBuff(twin, "kun_twin_bonus");
            }
            messageTo(self, "distance_buff", trial.getSessionDict(self, "distance"), 3.0f, false);
        }
        else 
        {
            if (buff.hasBuff(self, "kun_twin_bonus"))
            {
                buff.removeBuff(self, "kun_twin_bonus");
            }
            if (buff.hasBuff(twin, "kun_twin_bonus"))
            {
                buff.removeBuff(twin, "kun_twin_bonus");
            }
            messageTo(self, "distance_buff", trial.getSessionDict(self, "distance"), 3.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
