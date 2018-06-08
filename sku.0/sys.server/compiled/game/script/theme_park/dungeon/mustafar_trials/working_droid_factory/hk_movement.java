package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.dictionary;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class hk_movement extends script.base_script
{
    public hk_movement()
    {
    }
    public int setRun(obj_id self, dictionary params) throws InterruptedException
    {
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int doTaunt(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(trial.getTop(self));
        utils.messagePlayer(self, players, trial.WORKING_HK47_TAUNT);
        messageTo(self, "destroySelf", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupNpc(self);
        return SCRIPT_CONTINUE;
    }
}
