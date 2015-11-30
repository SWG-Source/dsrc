package script.systems.combat;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.attrib;
import script.library.buff;
import script.library.combat;
import script.library.group;
import script.library.utils;

public class combat_trigger_area extends script.base_script
{
    public combat_trigger_area()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String buffName = utils.getStringScriptVar(self, "triggerBuff");
        String prtName = utils.getStringScriptVar(self, "triggerPrt");
        obj_id owner = utils.getObjIdScriptVar(self, "objOwner");
        obj_id[] singleGroupMember = 
        {
            owner
        };
        int expireTime = utils.getIntScriptVar(self, "triggerDuration");
        createTriggerVolume("trigger_" + buffName + "_" + owner, combat.EGG_AURA_TRIGGER_MIN_RANGE, true);
        setAttributeInterested(self, attrib.ALL);
        setAttributeInterested(self, attrib.BEAST);
        if (group.isGrouped(owner))
        {
            obj_id groupId = getGroupObject(owner);
            obj_id[] groupMembers = getGroupMemberIds(groupId);
            playClientEffectLoc(groupMembers, prtName, getLocation(self), 0f, buffName + "_" + prtName + "_" + owner);
        }
        else 
        {
            playClientEffectLoc(singleGroupMember, prtName, getLocation(self), 0f, buffName + "_" + prtName + "_" + owner);
        }
        messageTo(self, "cleanUpTriggerBuff", null, expireTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        String buffName = utils.getStringScriptVar(self, "triggerBuff");
        obj_id owner = utils.getObjIdScriptVar(self, "objOwner");
        if (!volumeName.equals("trigger_" + buffName + "_" + owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (group.inSameGroup(owner, whoTriggeredMe))
        {
            buff.applyBuff(whoTriggeredMe, self, buffName);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        String buffName = utils.getStringScriptVar(self, "triggerBuff");
        obj_id owner = utils.getObjIdScriptVar(self, "objOwner");
        if (!volumeName.equals("trigger_" + buffName + "_" + owner))
        {
            return SCRIPT_CONTINUE;
        }
        if (buff.hasBuff(whoTriggeredMe, buffName) && buff.getBuffCaster(whoTriggeredMe, buffName) == self)
        {
            buff.removeBuff(whoTriggeredMe, buffName);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUpTriggerBuff(obj_id self, dictionary params) throws InterruptedException
    {
        String buffName = utils.getStringScriptVar(self, "triggerBuff");
        String prtName = utils.getStringScriptVar(self, "triggerPrt");
        obj_id owner = utils.getObjIdScriptVar(self, "objOwner");
        String triggerName = "trigger_" + buffName + "_" + owner;
        obj_id[] singleGroupMember = 
        {
            owner
        };
        obj_id[] contents = getTriggerVolumeContents(self, triggerName);
        for (int i = 0; i < contents.length; ++i)
        {
            if (buff.hasBuff(contents[i], buffName))
            {
                if (self == buff.getBuffCaster(contents[i], buffName))
                {
                    buff.removeBuff(contents[i], buffName);
                }
            }
        }
        if (group.isGrouped(owner))
        {
            obj_id groupId = getGroupObject(owner);
            obj_id[] groupMembers = getGroupMemberIds(groupId);
            stopClientEffectObjByLabel(groupMembers, self, buffName + "_" + prtName + "_" + owner, false);
        }
        else 
        {
            stopClientEffectObjByLabel(singleGroupMember, self, buffName + "_" + prtName + "_" + owner, false);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
