package script.theme_park.dungeon.mustafar_trials.volcano_battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.utils;
import script.library.create;
import script.library.buff;
import script.library.trial;

public class event_five_guard extends script.base_script
{
    public event_five_guard()
    {
    }
    public static final boolean LOGGING = false;
    public static final int BUFF_STRIP_RECAST = 16;
    public static final string_id REMOVED_BUFF = new string_id("mustafar/volcano_battlefield", "buff_removed");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        beginAttack(self);
        trial.setHp(self, trial.HP_VOLCANO_FIVE_GUARD);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        messageTo(self, "destroySelf", null, 5, false);
        trial.bumpSession(self);
        return SCRIPT_CONTINUE;
    }
    public int destroySelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = trial.getParent(self);
        dictionary dict = new dictionary();
        dict.put("type", "trio");
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
        messageTo(self, "doBuffStrip", trial.getSessionDict(self), BUFF_STRIP_RECAST, false);
        return;
    }
    public int doBuffStrip(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params))
        {
            return SCRIPT_CONTINUE;
        }
        stripBuff(self);
        messageTo(self, "doBuffStrip", trial.getSessionDict(self), BUFF_STRIP_RECAST, false);
        return SCRIPT_CONTINUE;
    }
    public void stripBuff(obj_id self) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            return;
        }
        obj_id target = getTarget(self);
        if (isIdValid(target) && !isIncapacitated(target))
        {
            queueCommand(self, (1679682244), target, "", COMMAND_PRIORITY_FRONT);
        }
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.VOLCANO_LOGGING)
        {
            LOG("logging/event_four_guard/" + section, message);
        }
    }
}
