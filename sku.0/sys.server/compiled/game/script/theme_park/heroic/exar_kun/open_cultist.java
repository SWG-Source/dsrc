package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.trial;
import script.obj_id;

public class open_cultist extends script.base_script
{
    public open_cultist()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int activateSelf(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id target = trial.getClosest(self, trial.getValidTargetsInCell(trial.getTop(self), "r2"));
        startCombat(self, target);
        return SCRIPT_CONTINUE;
    }
    public int handle_drain_anim(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
