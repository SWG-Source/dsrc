package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.combat;
import script.library.trial;

public class minder_add extends script.base_script
{
    public minder_add()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        buff.applyBuff(self, "minder_add_debuff");
        obj_id[] targets = trial.getValidTargetsInCell(trial.getTop(self), "r3");
        if (targets == null || targets.length == 0)
        {
            trial.cleanupObject(self);
        }
        startCombat(self, targets[rand(0, targets.length - 1)]);
        return SCRIPT_CONTINUE;
    }
}
