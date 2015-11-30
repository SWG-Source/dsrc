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
import script.library.space_dungeon;
import script.library.combat;
import script.library.prose;

public class karling extends script.base_script
{
    public karling()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_SHER_KARLING);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(trial.getTop(self), "praetorianDied", null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int doAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInDungeon(trial.getTop(self));
        obj_id closest = trial.getClosest(self, players);
        startCombat(self, closest);
        return SCRIPT_CONTINUE;
    }
}
