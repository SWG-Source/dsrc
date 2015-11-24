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
import script.library.utils;

public class tusken_bantha extends script.base_script
{
    public tusken_bantha()
    {
    }
    public static final String SOCIAL_VOLUME = "npc_socialization";
    public static final float SOCIAL_RANGE = 15f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.tusken"))
        {
            cancelFollowing(self);
            messageTo(self, "resumeLoitering", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        followTusken(self, utils.getObjIdScriptVar(self, "ai.tusken"));
        factions.setFaction(self, "tusken");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        factions.setFaction(self, "bantha");
        return SCRIPT_CONTINUE;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.tusken"))
        {
            cancelFollowing(self);
            messageTo(self, "resumeLoitering", null, 3, false);
            return SCRIPT_CONTINUE;
        }
        followTusken(self, utils.getObjIdScriptVar(self, "ai.tusken"));
        return SCRIPT_CONTINUE;
    }
    public int OnBehaviorChange(obj_id self, int newBehavior, int oldBehavior, int[] changeFlags) throws InterruptedException
    {
        if (isIncapacitated(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (newBehavior < oldBehavior)
        {
            if (!utils.hasScriptVar(self, "ai.tusken"))
            {
                cancelFollowing(self);
                messageTo(self, "resumeLoitering", null, 3, false);
                return SCRIPT_CONTINUE;
            }
            followTusken(self, utils.getObjIdScriptVar(self, "ai.tusken"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public void followTusken(obj_id bantha, obj_id target) throws InterruptedException
    {
        obj_id isFollowedBy = getObjIdObjVar(target, "ai.isFollowedBy");
        if (isFollowedBy == null || isFollowedBy == bantha)
        {
            setMovementWalk(bantha);
            follow(bantha, target, 2f, 5f);
            setObjVar(target, "ai.isFollowedBy", bantha);
            utils.setScriptVar(bantha, "ai.tusken", target);
            setObjVar(bantha, "ai.isFollowing", target);
        }
        else 
        {
            followTusken(bantha, isFollowedBy);
        }
    }
    public int resumeLoitering(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "ai.tusken"))
        {
            detachScript(self, "ai.tusken_bantha");
            return SCRIPT_CONTINUE;
        }
        followTusken(self, utils.getObjIdScriptVar(self, "ai.tusken"));
        return SCRIPT_OVERRIDE;
    }
    public void cancelFollowing(obj_id npc) throws InterruptedException
    {
        utils.removeScriptVar(npc, "ai.tusken");
        obj_id isFollowing = getObjIdObjVar(npc, "ai.isFollowing");
        if (isFollowing != null)
        {
            messageTo(isFollowing, "stopBeingFollowed", null, 0, isObjectPersisted(isFollowing));
        }
        removeObjVar(npc, "ai.isFollowing");
        if (hasObjVar(npc, "ai.isFollowedBy"))
        {
            obj_id isFollowedBy = getObjIdObjVar(npc, "ai.isFollowedBy");
            messageTo(isFollowedBy, "stopFollowing", null, 0, isObjectPersisted(isFollowedBy));
        }
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
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME))
        {
            if (hasScript(breacher, "ai.tusken_raider"))
            {
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals(ai_lib.ALERT_VOLUME_NAME))
        {
            ignoreCreatureMovement(self, breacher);
        }
        return SCRIPT_OVERRIDE;
    }
}
