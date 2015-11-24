package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.factions;
import script.library.chat;
import script.ai.ai_combat;
import script.library.pet_lib;
import script.library.colors;
import script.library.group;
import script.library.healing;

public class droid extends script.base_script
{
    public droid()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (newBehavior <= oldBehavior)
        {
            if (doCalmerBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
        else 
        {
            if (doAgitatedBehavior(self, newBehavior, oldBehavior) == SCRIPT_CONTINUE)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_OVERRIDE;
            }
        }
    }
    public int doCalmerBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            messageTo(npc, "resumeDefaultCalmBehavior", null, 5, false);
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int doAgitatedBehavior(obj_id npc, int newBehavior, int oldBehavior) throws InterruptedException
    {
        switch (newBehavior)
        {
            case BEHAVIOR_CALM:
            break;
            case BEHAVIOR_ALERT:
            break;
            case BEHAVIOR_THREATEN:
            break;
            case BEHAVIOR_FLEE:
            break;
            case BEHAVIOR_PANIC:
            break;
            case BEHAVIOR_ATTACK:
            break;
            case BEHAVIOR_FRENZY:
            break;
            default:
            break;
        }
        return SCRIPT_OVERRIDE;
    }
    public int lairThreatened(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
