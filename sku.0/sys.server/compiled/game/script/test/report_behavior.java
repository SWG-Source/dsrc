package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class report_behavior extends script.base_script
{
    public report_behavior()
    {
    }
    public void dumpState(obj_id self, String label, int behavior) throws InterruptedException
    {
        if (behavior < 0)
        {
            behavior = 0;
        }
        obj_id[] targets = getBehaviorTargets(self, behavior);
        String targetlist = "";
        int i;
        for (i = 0; i < targets.length; ++i)
        {
            targetlist += targets[i].toString() + "(";
            float fear = getMentalStateToward(self, targets[i], FEAR);
            float anger = getMentalStateToward(self, targets[i], ANGER);
            targetlist += Float.toString(fear) + "/" + Float.toString(anger) + ") ";
        }
        switch (behavior)
        {
            case BEHAVIOR_CALM:
            debugServerConsoleMsg(self, label + "CALM: " + targetlist);
            break;
            case BEHAVIOR_ALERT:
            debugServerConsoleMsg(self, label + "ALERT: " + targetlist);
            break;
            case BEHAVIOR_THREATEN:
            debugServerConsoleMsg(self, label + "THREATEN: " + targetlist);
            break;
            case BEHAVIOR_FLEE:
            debugServerConsoleMsg(self, label + "FLEE: " + targetlist);
            break;
            case BEHAVIOR_PANIC:
            debugServerConsoleMsg(self, label + "PANIC: " + targetlist);
            break;
            case BEHAVIOR_ATTACK:
            debugServerConsoleMsg(self, label + "ATTACK: " + targetlist);
            break;
            case BEHAVIOR_FRENZY:
            debugServerConsoleMsg(self, label + "FRENZY: " + targetlist);
            break;
        }
        
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        String targetlist = "";
        int i;
        for (i = 0; i <= BEHAVIOR_FRENZY; ++i)
        {
            if (i == newBehavior)
            {
                dumpState(self, "NOW ", i);
            }
            else if (i == oldBehavior)
            {
                dumpState(self, "WAS ", i);
            }
            else if (changeFlags[i] != 0)
            {
                dumpState(self, "", i);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorTargetChange(obj_id self, int b, int[] changeFlags) throws InterruptedException
    {
        int i;
        for (i = 0; i <= BEHAVIOR_FRENZY; ++i)
        {
            if (changeFlags[i] != 0)
            {
                dumpState(self, "", i);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
