package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.dictionary;
import script.library.trial;
import script.obj_id;

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
