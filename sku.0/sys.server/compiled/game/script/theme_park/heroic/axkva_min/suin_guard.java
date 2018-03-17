package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.trial;
import script.library.utils;

public class suin_guard extends script.base_script
{
    public suin_guard()
    {
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        String aspBuff = utils.getStringScriptVar(self, "aspect");
        buff.applyBuff(self, aspBuff);
        if (utils.hasScriptVar(self, "suin_dead"))
        {
            enrage();
        }
        return SCRIPT_CONTINUE;
    }
    public void enrage() throws InterruptedException
    {
        buff.applyBuff(getSelf(), "bm_enrage", -1);
        setMovementPercent(getSelf(), 300.0f);
    }
    public int OnExitedCombat(obj_id self) throws InterruptedException
    {
        obj_id suin = trial.getParent(self);
        if (isIdValid(suin) && exists(suin) && !ai_lib.isDead(suin) && !ai_lib.isInCombat(suin))
        {
            trial.cleanupObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int suin_died(obj_id self, dictionary params) throws InterruptedException
    {
        utils.setScriptVar(self, "suin_dead", 1);
        enrage();
        return SCRIPT_CONTINUE;
    }
}
