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
import script.ai.ai_combat;

public class ai_template extends script.base_script
{
    public ai_template()
    {
    }
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public static final float CORPSE_CLEANUP_DELAY = 30.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSetupNPC", null, 5, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleSetupNPC(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "ai.setupComplete"))
        {
            messageTo(self, "handleSetupNPC", null, 5, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int resumeDefaultCalmBehavior(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ai.conversing"))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self || ai_lib.isAiDead(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        int herding = dataTableGetInt("datatables/ai/species.iff", ai_lib.aiGetSpecies(self), "Herd");
        if (herding != 1)
        {
        }
        return SCRIPT_CONTINUE;
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
        return SCRIPT_CONTINUE;
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
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnDefenderCombatAction(obj_id self, obj_id attacker, obj_id weapon, int combatResult) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int lairThreatened(obj_id self, dictionary params) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "poi.baseObject"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myLair = getObjIdObjVar(self, "poi.baseObject");
        obj_id lair = params.getObjId("lairId");
        if (lair == null || lair == obj_id.NULL_ID)
        {
            return SCRIPT_CONTINUE;
        }
        if (lair == myLair)
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int corpseCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(speaker))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convo, obj_id player, string_id response) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnEndNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnFleeTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFleePathNotFound(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnFollowPathNotFound(obj_id self, obj_id target) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
