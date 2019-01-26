package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.buff;
import script.library.trial;
import script.obj_id;

public class exar_harmony extends script.base_script
{
    public exar_harmony()
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
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        trial.bumpSession(self, "zap");
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "kun_order_set");
        buff.applyBuff(self, "kun_twin_ranged_shield");
        buff.applyBuff(self, "mind_trick_immune");
        messageTo(self, "order_zap", trial.getSessionDict(self, "zap"), 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int order_zap(obj_id self, dictionary params) throws InterruptedException
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
        for (obj_id player : players) {
            queueCommand(self, (1874028555), player, "", COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "order_zap", trial.getSessionDict(self, "zap"), 8.0f, false);
        return SCRIPT_CONTINUE;
    }
}
