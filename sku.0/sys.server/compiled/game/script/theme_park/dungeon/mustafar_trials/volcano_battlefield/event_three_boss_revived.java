package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.trial;

public class event_three_boss_revived extends script.base_script
{
    public event_three_boss_revived()
    {
    }
    public static final boolean LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "beginAttack", null, 3, false);
        trial.setHp(self, trial.HP_VOLCANO_THREE_RISEN);
        trial.markAsVolcanoCommander(self);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "boss");
        if (isIdValid(parent) && !hasObjVar(self, "reset"))
        {
            messageTo(parent, "eventMobDied", dict, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int beginAttack(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id[] players = trial.getValidTargetsInRadius(self, 100.0f);
        if (players == null || players.length == 0)
        {
            doLogging("beginAttack", "Found no players to attack");
            return SCRIPT_CONTINUE;
        }
        obj_id toAttack = trial.getClosest(self, players);
        if (!isIdValid(toAttack))
        {
            doLogging("beginAttack", "player toAttack, was invalid");
            return SCRIPT_CONTINUE;
        }
        startCombat(self, toAttack);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_three_boss_revived/" + section, message);
        }
    }
}
