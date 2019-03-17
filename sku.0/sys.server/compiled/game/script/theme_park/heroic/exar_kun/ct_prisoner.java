package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.trial;
import script.obj_id;

public class ct_prisoner extends script.base_script
{
    public ct_prisoner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.creature_combat");
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        messageTo(self, "cleanup", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int stop_guard(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        obj_id[] allNpc = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        obj_id guard1 = null;
        obj_id guard2 = null;
        obj_id guard3 = null;
        obj_id guard4 = null;
        for (obj_id obj_id : allNpc) {
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_1")) {
                guard1 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_2")) {
                guard2 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_3")) {
                guard3 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_4")) {
                guard4 = obj_id;
            }
        }
        if (isIdValid(guard1) && exists(guard1) && !isDead(guard1))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "pathto_guard_one");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(guard2) && exists(guard2) && !isDead(guard2))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "pathto_guard_two");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(guard3) && exists(guard3) && !isDead(guard3))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "pathto_guard_three");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        if (isIdValid(guard4) && exists(guard4) && !isDead(guard4))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "pathto_guard_four");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
