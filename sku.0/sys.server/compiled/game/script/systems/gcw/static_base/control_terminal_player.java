package script.systems.gcw.static_base;

import script.dictionary;
import script.library.utils;
import script.obj_id;

public class control_terminal_player extends script.base_script
{
    public control_terminal_player()
    {
    }
    public static final String SCRIPT_VAR_ATTEMPT_ID = "gcw.static_base.control_terminal.attempt_id";
    public static final String SCRIPT_VAR_TERMINAL = "gcw.static_base.control_terminal.terminal";
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        cancelControlAttempt(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLocomotionChanged(obj_id self, int newLocomotion, int oldLocomotion) throws InterruptedException
    {
        if (newLocomotion != LOCOMOTION_STANDING && newLocomotion != LOCOMOTION_KNEELING && newLocomotion != LOCOMOTION_PRONE)
        {
            cancelControlAttempt(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        if (after != POSTURE_UPRIGHT && after != POSTURE_CROUCHED && after != POSTURE_PRONE)
        {
            cancelControlAttempt(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void cancelControlAttempt(obj_id self) throws InterruptedException
    {
        obj_id terminal = utils.getObjIdScriptVar(self, SCRIPT_VAR_TERMINAL);
        int attemptId = utils.getIntScriptVar(self, SCRIPT_VAR_ATTEMPT_ID);
        if (!isIdValid(terminal))
        {
            return;
        }
        dictionary d = new dictionary();
        d.put("player", self);
        d.put("id", attemptId);
        messageTo(terminal, "handleControlAttemptCancel", d, 0.0f, false);
    }
}
