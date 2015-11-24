package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_aggro;
import script.library.ai_lib;
import script.library.attrib;
import script.library.factions;
import script.library.utils;

public class thug extends script.base_script
{
    public thug()
    {
    }
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setAttributeAttained(self, attrib.THUG);
        setAttributeInterested(self, attrib.VICTIM);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAttributeAttained(self, attrib.THUG);
        setAttributeInterested(self, attrib.VICTIM);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isMob(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isMonster(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.AGGRO_VOLUME_NAME))
        {
            int reaction = factions.getFactionReaction(self, breacher);
            if (hasAttributeAttained(breacher, attrib.VICTIM))
            {
                reaction = factions.REACTION_NEGATIVE;
            }
            else if (reaction != factions.REACTION_NEGATIVE)
            {
                reaction = factions.REACTION_LIKE;
            }
            switch (reaction)
            {
                case factions.REACTION_NEGATIVE:
                
                {
                    ai_lib.barkString(self, "hi_mean");
                    ai_aggro.requestAggroCheck(breacher);
                    return SCRIPT_OVERRIDE;
                }
                case factions.REACTION_POSITIVE:
                case factions.REACTION_LIKE:
                
                {
                    ai_lib.barkString(self, "hi_nice");
                }
                break;
                default:
                
                {
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.AGGRO_VOLUME_NAME))
        {
            if (isMob(breacher) && !ai_lib.isMonster(breacher))
            {
                int reaction = factions.getFactionReaction(self, breacher);
                if (hasAttributeAttained(breacher, attrib.VICTIM))
                {
                    reaction = factions.REACTION_NEGATIVE;
                }
                else if (reaction != factions.REACTION_NEGATIVE)
                {
                    reaction = factions.REACTION_LIKE;
                }
                switch (reaction)
                {
                    case factions.REACTION_NEGATIVE:
                    
                    {
                        ai_lib.barkString(self, "bye_mean");
                        if (rand(1, 2) == 1)
                        {
                            ai_aggro.requestAggroCheck(breacher);
                            return SCRIPT_OVERRIDE;
                        }
                    }
                    case factions.REACTION_POSITIVE:
                    case factions.REACTION_LIKE:
                    
                    {
                        ai_lib.barkString(self, "bye_nice");
                    }
                    break;
                    default:
                    
                    {
                        ai_lib.barkString(self, "bye_mean");
                    }
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (newBehavior > oldBehavior)
        {
            if (newBehavior >= BEHAVIOR_THREATEN && newBehavior < BEHAVIOR_ATTACK)
            {
                doAgitateBehavior(self, newBehavior);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        else if (newBehavior == BEHAVIOR_CALM)
        {
            ai_lib.barkString(self, "calm");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void doAgitateBehavior(obj_id npc, int behavior) throws InterruptedException
    {
        if (isInvulnerable(npc))
        {
            return;
        }
    }
}
