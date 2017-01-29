package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.factions;
import script.library.utils;
import script.library.attrib;

public class tusken_raider extends script.base_script
{
    public tusken_raider()
    {
    }
    public static final String SOCIAL_VOLUME = "npc_socialization";
    public static final float SOCIAL_RANGE = 15.0f;
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public static final String CREATURE_TABLE = "datatables/mob/creatures.iff";
    public void initializeScript() throws InterruptedException
    {
        obj_id self = getSelf();
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::initializeScript() self(" + self + ":" + getName(self) + ")");
        createTriggerVolume(SOCIAL_VOLUME, SOCIAL_RANGE, false);
        cancelFollowing(self);
        setAttributeInterested(self, attrib.TUSKEN_RAIDER);
        setAttributeInterested(self, attrib.HERBIVORE);
        setAttributeAttained(self, attrib.TUSKEN_RAIDER);
        setAttributeAttained(self, attrib.THUG);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnAttach() self(" + self + ":" + getName(self) + ")");
        initializeScript();
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnAddedToWorld() self(" + self + ":" + getName(self) + ")");
        initializeScript();
        return SCRIPT_CONTINUE;
    }
    public int OnAiTetherStart(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnAiTetherStart() self(" + self + ":" + getName(self) + ")");
        removeTriggerVolume(SOCIAL_VOLUME);
        return SCRIPT_CONTINUE;
    }
    public int OnAiTetherComplete(obj_id self) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnAiTetherComplete() self(" + self + ":" + getName(self) + ")");
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(SOCIAL_VOLUME, SOCIAL_RANGE, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnTriggerVolumeEntered() self(" + self + ":" + getName(self) + ") volumeName(" + volumeName + ") breacher(" + breacher + ":" + getName(breacher) + ")");
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
        if (getIntObjVar(self, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME))
        {
            if (hasScript(breacher, "ai.tusken_raider"))
            {
                if (getIntObjVar(breacher, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL)
                {
                    return SCRIPT_CONTINUE;
                }
                if(hasTriggerVolume(self, SOCIAL_VOLUME)) {
                    addTriggerVolumeEventSource(SOCIAL_VOLUME, breacher);
                }
            }
            else 
            {
                boolean isBantha = false;
                final String creatureName = getCreatureName(breacher);
                if (creatureName != null)
                {
                    String species = dataTableGetString(CREATURE_TABLE, creatureName, "socialGroup");
                    if ((species != null) && species.equals("bantha"))
                    {
                        isBantha = true;
                    }
                }
                if (isBantha)
                {
                    takeBantha(self, breacher);
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        else if (volumeName.equals(SOCIAL_VOLUME))
        {
            if (!ai_lib.isInCombat(breacher))
            {
                if (getIntObjVar(breacher, "ai.defaultCalmBehavior") != ai_lib.BEHAVIOR_SENTINEL)
                {
                    initiateDialog(self, breacher);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnTriggerVolumeExited() self(" + self + ":" + getName(self) + ") volumeName(" + volumeName + ") breacher(" + breacher + ":" + getName(breacher) + ")");
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME))
        {
            if (hasScript(breacher, "ai.tusken_raider"))
            {
                removeTriggerVolumeEventSource(SOCIAL_VOLUME, breacher);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void initiateDialog(obj_id talker, obj_id listener) throws InterruptedException
    {
        if (!(isIdValid(talker) && isIdValid(listener)))
        {
            return;
        }
        if (talker.getValue() < listener.getValue())
        {
            return;
        }
        if (utils.hasScriptVar(talker, "ai.speaking") || utils.hasScriptVar(listener, "ai.speaking"))
        {
            return;
        }
        if (getBehavior(talker) >= BEHAVIOR_THREATEN || getBehavior(listener) >= BEHAVIOR_THREATEN)
        {
            return;
        }
        if (getIntObjVar(listener, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL || getIntObjVar(talker, "ai.defaultCalmBehavior") == ai_lib.BEHAVIOR_SENTINEL)
        {
            return;
        }
        location pathToLoc = getLocation(listener);
        location myLoc = getLocation(talker);
        if (pathToLoc == null || myLoc == null)
        {
            String msg = "ai.tusken_raider.initiateDialog(): ";
            String msg2 = "Null location for listener (obj_id: " + listener + " pathToLoc: " + pathToLoc + ") and/or talker (obj_id: " + talker + " myLoc: " + myLoc + ")";
            LOG("DESIGNER_FATAL", msg + msg2);
            return;
        }
        else 
        {
            utils.setScriptVar(talker, "ai.speaking", true);
            utils.setScriptVar(listener, "ai.speaking", true);
            stop(talker);
            stop(listener);
            faceTo(listener, talker);
            utils.setScriptVar(talker, "ai.pathingToSocialize", listener);
            utils.setScriptVar(listener, "ai.pathingToSocialize", talker);
            if (pathToLoc.x < myLoc.x)
            {
                pathToLoc.x += 1.5f;
            }
            else 
            {
                pathToLoc.x -= 1.5f;
            }
            if (pathToLoc.z < myLoc.z)
            {
                pathToLoc.z += 1.5f;
            }
            else 
            {
                pathToLoc.z -= 1.5f;
            }
            pathTo(talker, pathToLoc);
            messageTo(talker, "handleSocializingRecovery", null, 45, isObjectPersisted(talker));
            messageTo(listener, "handleSocializingRecovery", null, 45, isObjectPersisted(listener));
        }
    }
    public int handleSocializingRecovery(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            ai_lib.setMood(self, ai_lib.MOOD_CALM);
            utils.removeScriptVar(self, "ai.pathingToSocialize");
            utils.removeScriptVar(self, "ai.speaking");
            messageTo(self, "resumeLoitering", null, 1, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        faceTo(self, listener);
        faceTo(listener, self);
        ai_lib.doAction(self, "conversation_1");
        ai_lib.doAction(listener, "conversation_1");
        ai_lib.setMood(self, ai_lib.MOOD_CALM);
        messageTo(self, "handleEndSocializing", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleEndSocializing(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        if(listener != null)
            endSocializing(self, listener);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathNotFound(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.pathingToSocialize"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
        endSocializing(self, listener);
        return SCRIPT_CONTINUE;
    }
    public void endSocializing(obj_id talker, obj_id listener) throws InterruptedException
    {
        ai_lib.setMood(talker, ai_lib.MOOD_CALM);
        ai_lib.setMood(listener, ai_lib.MOOD_CALM);
        messageTo(talker, "resumeLoitering", null, 1, false);
        followTusken(listener, talker);
        utils.removeScriptVar(talker, "ai.pathingToSocialize");
        utils.removeScriptVar(listener, "ai.pathingToSocialize");
        utils.removeScriptVar(talker, "ai.speaking");
        removeTriggerVolumeEventSource(SOCIAL_VOLUME, listener);
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        LOGC(aiLoggingEnabled(self), "debug_ai", "tusken_raider::OnBehaviorChange() self(" + self + ":" + getName(self) + ")");
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
            if (utils.hasScriptVar(self, "ai.pathingToSocialize"))
            {
                obj_id listener = utils.getObjIdScriptVar(self, "ai.pathingToSocialize");
                endSocializing(self, listener);
            }
            if (newBehavior >= BEHAVIOR_ALERT && newBehavior < BEHAVIOR_ATTACK)
            {
                doAgitateBehavior(self, newBehavior);
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        if (newBehavior == BEHAVIOR_CALM)
        {
            if (utils.hasObjVar(self, "ai.isFollowing"))
            {
                followTusken(self, getObjIdObjVar(self, "ai.isFollowing"));
                return SCRIPT_OVERRIDE;
            }
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
        return;
    }
    public void followTusken(obj_id npc, obj_id target) throws InterruptedException
    {
        if (!isIncapacitated(npc))
        {
            removeTriggerVolumeEventSource(SOCIAL_VOLUME, target);
            if (!exists(target))
            {
                cancelFollowing(npc);
                return;
            }
            if (target.getValue() < npc.getValue())
            {
                return;
            }
            obj_id isFollowedBy = getObjIdObjVar(target, "ai.isFollowedBy");
            // Cekis: added check on the NPC to make sure it's a creature object... may need
            // to just return SCRIPT_CONTINUE if it's not a creature object.
            if ((isFollowedBy == null || isFollowedBy == npc) && isNpcCreature(npc))
            {
                setMovementWalk(npc);
                follow(npc, target, 2f, 5f);
                setObjVar(target, "ai.isFollowedBy", npc);
                setObjVar(npc, "ai.isFollowing", target);
            }
            else 
            {
                followTusken(npc, isFollowedBy);
            }
        }
    }
    public int resumeLoitering(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id isFollowing = getObjIdObjVar(self, "ai.isFollowing");
        if (isFollowing == null)
        {
            if (getBehavior(self) == BEHAVIOR_CALM)
            {
                messageTo(self, "resumeDefaultCalmBehavior", null, 1, false);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            followTusken(self, isFollowing);
            return SCRIPT_OVERRIDE;
        }
    }
    public void cancelFollowing(obj_id npc) throws InterruptedException
    {
        utils.removeScriptVar(npc, "ai.speaking");
        obj_id isFollowing = getObjIdObjVar(npc, "ai.isFollowing");
        if (isFollowing != null)
        {
            messageTo(isFollowing, "stopBeingFollowed", null, 0, false);
        }
        removeObjVar(npc, "ai.isFollowing");
    }
    public int stopBeingFollowed(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "ai.isFollowedBy");
        return SCRIPT_CONTINUE;
    }
    public int stopFollowing(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "ai.isFollowing");
        cancelFollowing(self);
        messageTo(self, "resumeLoitering", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowTargetLost(obj_id self, obj_id oldTarget) throws InterruptedException
    {
        cancelFollowing(self);
        messageTo(self, "resumeLoitering", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnFollowPathNotFound(obj_id self, obj_id target) throws InterruptedException
    {
        cancelFollowing(self);
        messageTo(self, "resumeLoitering", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public void takeBantha(obj_id tusken, obj_id bantha) throws InterruptedException
    {
        if (hasScript(bantha, "ai.tusken_bantha"))
        {
            return;
        }
        if (utils.hasScriptVar(tusken, "ai.bantha"))
        {
            return;
        }
        utils.setScriptVar(tusken, "ai.bantha", bantha);
        utils.setScriptVar(bantha, "ai.tusken", tusken);
        attachScript(bantha, "ai.tusken_bantha");
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        removeTriggerVolume(SOCIAL_VOLUME);
        return SCRIPT_CONTINUE;
    }
}
