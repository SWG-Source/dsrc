package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class axkva_shade extends script.base_script
{
    public axkva_shade()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai");
        detachScript(self, "ai.creature_combat");
        attachScript(self, "player.yavin_e3");
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        setCreatureCoverVisibility(self, false);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < players.length; i++)
        {
            addPassiveReveal(self, players[i], 1);
        }
        return SCRIPT_CONTINUE;
    }
}
