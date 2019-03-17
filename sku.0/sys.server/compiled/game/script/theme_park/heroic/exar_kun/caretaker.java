package script.theme_park.heroic.exar_kun;

import script.dictionary;
import script.library.buff;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class caretaker extends script.base_script
{
    public caretaker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setMovementPercent(self, 0.0f);
        trial.setHp(self, 785005);
        setObjVar(self, "isImmobile", true);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        if (players == null || players.length == 0)
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "ct_reset");
            messageTo(trial.getTop(self), "triggerFired", dict, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        for (obj_id player : players) {
            startCombat(self, player);
        }
        buff.applyBuff(self, "caretaker_shield_reflect", 7200.0f);
        messageTo(self, "lightning", trial.getSessionDict(self, "lightning"), 10.0f, false);
        messageTo(self, "summonAdd", trial.getSessionDict(self, "adds"), 5.0f, false);
        messageTo(self, "execute", trial.getSessionDict(self, "execute"), 65.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int lightning(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "lightning"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] players = trial.getValidTargetsInCell(trial.getTop(self), "r4");
        if (players == null || players.length == 0)
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "ct_reset");
            messageTo(trial.getTop(self), "triggerFired", dict, 1.0f, false);
            return SCRIPT_CONTINUE;
        }
        float recast = hasObjVar(self, "p2") ? 2.0f : 10.0f;
        if (!canSee(self, getHateTarget(self)))
        {
            setLocation(getHateTarget(self), getLocation(self));
        }
        else 
        {
            queueCommand(self, (-1651783164), getHateTarget(self), "", COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "lightning", trial.getSessionDict(self, "lightning"), recast, false);
        return SCRIPT_CONTINUE;
    }
    public int start_phase_2(obj_id self, dictionary params) throws InterruptedException
    {
        trial.bumpSession(self, "lightning");
        trial.bumpSession(self, "execute");
        trial.bumpSession(self, "adds");
        buff.removeBuff(self, "caretaker_shield_reflect");
        messageTo(self, "lightning", trial.getSessionDict(self, "lightning"), 3.0f, false);
        setObjVar(self, "p2", 2);
        messageTo(self, "drain", null, 17.0f, false);
        messageTo(self, "vapor", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int drain(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!canSee(self, getHateTarget(self)))
        {
            setLocation(getHateTarget(self), getLocation(self));
        }
        else 
        {
            queueCommand(self, (-1207690240), getHateTarget(self), "", COMMAND_PRIORITY_DEFAULT);
        }
        messageTo(self, "drain", null, 35.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int vapor(obj_id self, dictionary params) throws InterruptedException
    {
        if (isDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        int vapors = utils.hasScriptVar(self, "num_vapor") ? 0 : utils.getIntScriptVar(self, "num_vapor");
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "spawn_vapor");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        float nextVapor = 20.0f;
        switch (vapors)
        {
            case 0:
            break;
            case 1:
            nextVapor = 15.0f;
            break;
            case 2:
            nextVapor = 10.0f;
            break;
            default:
            nextVapor = 20.0f;
        }
        vapors++;
        utils.setScriptVar(self, "num_vapor", vapors);
        messageTo(self, "vapor", null, nextVapor, false);
        return SCRIPT_CONTINUE;
    }
    public int summonAdd(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "adds"))
        {
            return SCRIPT_CONTINUE;
        }
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "spawn_ct_add");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        messageTo(self, "summonAdd", trial.getSessionDict(self, "adds"), 80.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int execute(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "execute"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] allNpc = trial.getObjectsInDungeonWithObjVar(trial.getTop(self), "spawn_id");
        obj_id guard1 = null;
        obj_id guard2 = null;
        obj_id guard3 = null;
        obj_id guard4 = null;
        for (obj_id obj_id : allNpc) {
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_2")) {
                guard1 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_1")) {
                guard2 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_3")) {
                guard3 = obj_id;
            }
            if ((getStringObjVar(obj_id, "spawn_id")).equals("ct_guard_4")) {
                guard4 = obj_id;
            }
        }
        if ((isIdValid(guard1) && exists(guard1) && !isDead(guard1)) || (isIdValid(guard2) && exists(guard2) && !isDead(guard2)))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "spawn_prisoner_one");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            messageTo(self, "execute", trial.getSessionDict(self, "execute"), 90.0f, false);
            return SCRIPT_CONTINUE;
        }
        if ((isIdValid(guard3) && exists(guard3) && !isDead(guard3)) || (isIdValid(guard4) && exists(guard4) && !isDead(guard4)))
        {
            dictionary dict = trial.getSessionDict(trial.getTop(self));
            dict.put("triggerType", "triggerId");
            dict.put("triggerName", "spawn_prisoner_two");
            messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
            messageTo(self, "execute", trial.getSessionDict(self, "execute"), 90.0f, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
