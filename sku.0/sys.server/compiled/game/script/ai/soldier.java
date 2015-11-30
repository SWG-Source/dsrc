package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_aggro;
import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.attrib;
import script.library.factions;
import script.library.pet_lib;
import script.library.utils;

public class soldier extends script.base_script
{
    public soldier()
    {
    }
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "ai.diction"))
        {
            setObjVar(self, "ai.diction", "military");
        }
        setAttributeAttained(self, attrib.GUARD);
        setAttributeInterested(self, attrib.THUG);
        setAttributeInterested(self, attrib.HERBIVORE);
        setAttributeInterested(self, attrib.CARNIVORE);
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(ai_lib.ALERT_VOLUME_NAME, ai_lib.aiGetApproachTriggerRange(self), true);
        }
        setObjVar(self, "ai.rangedOnly", true);
        if (!hasObjVar(self, "ai.grenadeType"))
        {
            setObjVar(self, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
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
        if (hasObjVar(self, "ai.inFormation"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME))
        {
            final int reaction = factions.getFactionReaction(self, breacher);
            switch (reaction)
            {
                case factions.REACTION_NEGATIVE:
                if (!hasObjVar(self, "ai.inFormation"))
                {
                    ai_lib.barkString(self, "hi_mean");
                }
                break;
                case factions.REACTION_POSITIVE:
                if (!hasObjVar(self, "ai.inFormation"))
                {
                    ai_lib.barkString(self, "hi_nice");
                }
                break;
                default:
                if (!hasObjVar(self, "ai.inFormation"))
                {
                    ai_lib.barkString(self, "hi_mid");
                }
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME) && (isMob(breacher) && !ai_lib.isMonster(breacher)) && !ai_lib.isInCombat(self) && !ai_lib.isAiDead(breacher) && !hasObjVar(self, "ai.inFormation"))
        {
            int reaction = factions.getFactionReaction(self, breacher);
            switch (reaction)
            {
                case factions.REACTION_NEGATIVE:
                ai_lib.barkString(self, "bye_mean");
                break;
                case factions.REACTION_POSITIVE:
                ai_lib.barkString(self, "bye_nice");
                break;
                default:
                ai_lib.barkString(self, "bye_mid");
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (ai_lib.isAiDead(self))
        {
            return SCRIPT_OVERRIDE;
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
        if (getConfigSetting("GameServer", "disableAICombat") != null)
        {
            return;
        }
    }
}
