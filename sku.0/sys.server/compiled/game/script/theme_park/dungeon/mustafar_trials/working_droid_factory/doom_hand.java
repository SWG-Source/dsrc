package script.theme_park.dungeon.mustafar_trials.working_droid_factory;

import script.dictionary;
import script.library.ai_lib;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class doom_hand extends script.base_script
{
    public doom_hand()
    {
    }
    public static final boolean LOGGING = true;
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, trial.HP_WORKING_DOOM_HAND);
        setInvulnerable(self, true);
        messageTo(self, "setFacing", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int setFacing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objects = utils.getSharedContainerObjects(self);
        for (obj_id object : objects) {
            if ((getTemplateName(object)).contains("radioactive")) {
                faceTo(self, object);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            messageTo(self, "setFacing", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int deactivate(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        ai_lib.clearCombatData();
        return SCRIPT_CONTINUE;
    }
    public int activate(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, false);
        obj_id target = utils.getObjIdScriptVar(self, trial.WORKING_HOD_TARGET);
        addHate(self, target, 100);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (!trial.isPrimaryHand(self))
        {
            int total = 0;
            for (int i : damage) {
                total += i;
            }
            addToHealth(self, total);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING || trial.WORKING_LOGGING)
        {
            LOG("logging/doom_hand/" + section, message);
        }
    }
}
