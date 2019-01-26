package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.dictionary;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class mde_fixer_one extends script.base_script
{
    public mde_fixer_one()
    {
    }
    public static final boolean LOGGING = false;
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id top = trial.getTop(self);
        dictionary dict = new dictionary();
        messageTo(top, "fixerOneKilled", dict, 0, false);
        utils.messagePlayer(self, trial.getPlayersInDungeon(self), trial.WORKING_MDE_DEFEATED, "object/mobile/pit_droid.iff");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_WORKING_FIXER_ONE);
        messageTo(self, "beginAttack", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int beginAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = trial.getPlayersInDungeon(self);
        if (players == null || players.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id prefered = obj_id.NULL_ID;
        for (obj_id player : players) {
            if (!isDead(player)) {
                if (prefered == obj_id.NULL_ID) {
                    prefered = player;
                } else {
                    if (getDistance(self, player) < getDistance(self, prefered)) {
                        prefered = player;
                    }
                }
            }
        }
        if (prefered == obj_id.NULL_ID)
        {
            return SCRIPT_CONTINUE;
        }
        startCombat(self, prefered);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/fixer_one/" + section, message);
        }
    }
}
