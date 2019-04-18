package script.systems.combat;

import script.dictionary;
import script.library.*;
import script.obj_id;

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
            playClientEffectLoc(groupMembers, prtName, getLocation(self), 0.0f, buffName + "_" + prtName + "_" + owner);
        }
        else 
        {
            playClientEffectLoc(singleGroupMember, prtName, getLocation(self), 0.0f, buffName + "_" + prtName + "_" + owner);
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
        for (obj_id content : contents) {
            if (buff.hasBuff(content, buffName)) {
                if (self == buff.getBuffCaster(content, buffName)) {
                    buff.removeBuff(content, buffName);
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
