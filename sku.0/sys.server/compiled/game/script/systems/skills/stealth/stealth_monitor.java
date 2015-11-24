package script.systems.skills.stealth;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.buff;
import script.library.group;
import script.library.stealth;
import script.library.sui;

public class stealth_monitor extends script.base_script
{
    public stealth_monitor()
    {
    }
    public int OnLocomotionChanged(obj_id self, int newLocomotion, int oldLocomotion) throws InterruptedException
    {
        stealth.OnLocomotionChange(self, oldLocomotion, newLocomotion);
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        stealth.OnPostureChanged(self, before, after);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        String invis = stealth.getInvisBuff(self);
        if (invis != null)
        {
            buff.removeBuff(self, invis);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (sui.hasEventFlag(self, sui.CD_EVENT_STEALTHED))
        {
            sui.cancelCountdownTimer(self, sui.CD_EVENT_STEALTHED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (sui.hasEventFlag(self, sui.CD_EVENT_STEALTHED))
        {
            sui.cancelCountdownTimer(self, sui.CD_EVENT_STEALTHED);
        }
        return SCRIPT_CONTINUE;
    }
    public int updatePassiveRevealList(obj_id self, dictionary params) throws InterruptedException
    {
        String invis = stealth.getInvisBuff(self);
        if (beast_lib.isBeast(self))
        {
            invis = "invis";
        }
        if (invis == null || invis.length() < 1)
        {
            removeTriggerVolume(stealth.INVIS_BREAK_RADIUS_FAR);
            removeTriggerVolume(stealth.INVIS_BREAK_RADIUS_NEAR);
            detachScript(self, "systems.skills.stealth.stealth_monitor");
            return SCRIPT_CONTINUE;
        }
        if (beast_lib.isBeast(self))
        {
            createTriggerVolume(stealth.INVIS_BREAK_RADIUS_FAR, stealth.INVIS_BREAK_NEAR_DISTANCE + 1.0f, true);
            createTriggerVolume(stealth.INVIS_BREAK_RADIUS_NEAR, stealth.INVIS_BREAK_NEAR_DISTANCE, true);
        }
        messageTo(self, "updatePassiveRevealList", null, 10, false);
        dictionary revealList = getPassiveRevealList(self);
        if (revealList == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] ids = revealList.getObjIdArray("id");
        int[] range = revealList.getIntArray("range");
        if ((ids != null) && (ids.length > 0) && (range != null) && (range.length > 0) && (ids.length == range.length))
        {
            int currentRange = 0;
            for (int i = 0; i < ids.length; ++i)
            {
                if (isIdValid(ids[i]))
                {
                    if (!exists(ids[i]))
                    {
                        removePassiveReveal(self, ids[i]);
                        continue;
                    }
                    if (group.inSameGroup(self, ids[i]))
                    {
                        continue;
                    }
                    location here = getLocation(self);
                    location there = getLocation(ids[i]);
                    currentRange = (int)getDistance(here, there);
                    int revealRange = range[i];
                    if (currentRange <= revealRange)
                    {
                        continue;
                    }
                    else if (currentRange <= getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_NEAR))
                    {
                        continue;
                    }
                    else if (currentRange <= getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR))
                    {
                        if (stealth.testBubbleBreached(self, ids[i], stealth.PASSIVE_BREACH_FAR))
                        {
                            addPassiveReveal(self, ids[i], (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR));
                        }
                        else 
                        {
                            removePassiveReveal(self, ids[i]);
                        }
                    }
                    else 
                    {
                        removePassiveReveal(self, ids[i]);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        String invis = stealth.getInvisBuff(self);
        if (beast_lib.isBeast(self))
        {
            invis = "invis";
        }
        if (invis == null || invis.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (invis.startsWith("invis_sp_buff_invis_notrace"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(breacher) || isIncapacitated(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (group.inSameGroup(self, breacher))
        {
            addPassiveReveal(self, breacher, 1);
            return SCRIPT_CONTINUE;
        }
        int breacherRevealRange = getPassiveRevealRange(self, breacher);
        if (volumeName.equals(stealth.INVIS_BREAK_RADIUS_FAR))
        {
            if (breacherRevealRange < 0)
            {
                if (stealth.testBubbleBreached(self, breacher, stealth.PASSIVE_BREACH_FAR))
                {
                    addPassiveReveal(self, breacher, (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR));
                }
            }
        }
        if (volumeName.equals(stealth.INVIS_BREAK_RADIUS_NEAR))
        {
            if (breacherRevealRange < 0)
            {
                if (stealth.testBubbleBreached(self, breacher, stealth.PASSIVE_BREACH_NEAR))
                {
                    addPassiveReveal(self, breacher, (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_NEAR));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        String invis = stealth.getInvisBuff(self);
        if (beast_lib.isBeast(self))
        {
            invis = "invis";
        }
        if (invis == null || invis.length() < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (invis.equals("invis_sp_buff_invis_notrace"))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(breacher) || isIncapacitated(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (group.inSameGroup(self, breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(stealth.INVIS_BREAK_RADIUS_FAR))
        {
            removeTriggerVolumeEventSource(stealth.INVIS_BREAK_RADIUS_NEAR, breacher);
            int breacherRevealRange = getPassiveRevealRange(self, breacher);
            if (breacherRevealRange < 0)
            {
                return SCRIPT_CONTINUE;
            }
            if (breacherRevealRange <= (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR))
            {
                removePassiveReveal(self, breacher);
                return SCRIPT_CONTINUE;
            }
        }
        if (volumeName.equals(stealth.INVIS_BREAK_RADIUS_NEAR))
        {
            int breacherRevealRange = getPassiveRevealRange(self, breacher);
            if (breacherRevealRange < (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_NEAR))
            {
                return SCRIPT_CONTINUE;
            }
            location here = getLocation(self);
            location there = getLocation(breacher);
            float range = getDistance(here, there);
            if (range < getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR))
            {
                if (stealth.testBubbleBreached(self, breacher, stealth.PASSIVE_BREACH_FAR))
                {
                    addPassiveReveal(self, breacher, (int)getTriggerVolumeRadius(self, stealth.INVIS_BREAK_RADIUS_FAR));
                }
                else 
                {
                    removePassiveReveal(self, breacher);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
