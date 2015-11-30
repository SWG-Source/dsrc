package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.chat;
import script.library.trial;

public class ct_executioner extends script.base_script
{
    public ct_executioner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.creature_combat");
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        setMovementWalk(self);
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int kill_prisoner(obj_id self, dictionary params) throws InterruptedException
    {
        setInvulnerable(self, true);
        messageTo(self, "handleDelayedExecute", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDelayedExecute(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] prisoner = trial.getObjectsInDungeonWithScript(trial.getTop(self), "theme_park.heroic.exar_kun.ct_prisoner");
        for (int i = 0; i < prisoner.length; i++)
        {
            kill(prisoner[i]);
        }
        messageTo(self, "cleanup", null, 5.0f, false);
        chat.chat(self, "Where is your god now!");
        queueCommand(self, (1279137413), prisoner[0], "", COMMAND_PRIORITY_IMMEDIATE);
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "spawn_executioner");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (hasObjVar(self, "stop_recording"))
        {
            return SCRIPT_CONTINUE;
        }
        float max = (float)getMaxHealth(self);
        float current = (float)getHealth(self);
        float ratio = current / max;
        if (ratio <= 0.2)
        {
            failExecute(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public void failExecute(obj_id self) throws InterruptedException
    {
        chat.chat(self, "I have failed the master!");
        setInvulnerable(self, true);
        stop(self);
        messageTo(self, "cleanup", null, 3.0f, false);
        dictionary dict = trial.getSessionDict(trial.getTop(self));
        dict.put("triggerType", "triggerId");
        dict.put("triggerName", "fail_execute");
        messageTo(trial.getTop(self), "triggerFired", dict, 0.0f, false);
    }
}
