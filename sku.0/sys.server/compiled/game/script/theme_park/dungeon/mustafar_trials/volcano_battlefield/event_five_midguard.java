package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.dictionary;
import script.library.trial;
import script.obj_id;

public class event_five_midguard extends script.base_script
{
    public event_five_midguard()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        beginAttack(self);
        trial.setHp(self, trial.HP_VOLCANO_FIVE_MIDGUARD);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "midguard");
        if (isIdValid(parent))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void beginAttack(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInRadius(self, 150.0f);
        if (players == null || players.length == 0)
        {
            doLogging("beginAttack", "Found no players to attack");
            return;
        }
        obj_id toAttack = trial.getClosest(self, players);
        if (!isIdValid(toAttack))
        {
            doLogging("beginAttack", "player toAttack, was invalid");
            return;
        }
        startCombat(self, toAttack);
        return;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_four_guard/" + section, message);
        }
    }
}
