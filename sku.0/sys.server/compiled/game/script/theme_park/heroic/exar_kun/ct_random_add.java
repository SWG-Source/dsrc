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

public class ct_random_add extends script.base_script
{
    public ct_random_add()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        for (int i = 0; i < players.length; i++)
        {
            startCombat(self, players[i]);
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
        for (int i = 0; i < players.length; i++)
        {
            startCombat(self, players[i]);
        }
        messageTo(self, "checkRestartCombat", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
}
