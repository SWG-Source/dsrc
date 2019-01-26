package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.combat;
import script.library.trial;
import script.obj_id;

public class ct_random_add extends script.base_script
{
    public ct_random_add()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        for (obj_id player : players) {
            startCombat(self, player);
        }
        messageTo(self, "checkRestartCombat", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int checkRestartCombat(obj_id self, dictionary params) throws InterruptedException
    {
        if (combat.isInCombat(self))
        {
            messageTo(self, "checkRestartCombat", null, 10.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        for (obj_id player : players) {
            startCombat(self, player);
        }
        messageTo(self, "checkRestartCombat", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
}
