package script.theme_park.heroic.axkva_min;

import script.dictionary;
import script.library.trial;
import script.obj_id;

public class kimaru extends script.base_script
{
    public kimaru()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_AXKVA_KIMARU);
        return SCRIPT_CONTINUE;
    }
    public int spawnForceStorms(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            addHate(self, player, 1);
        }
        return SCRIPT_CONTINUE;
    }
}
