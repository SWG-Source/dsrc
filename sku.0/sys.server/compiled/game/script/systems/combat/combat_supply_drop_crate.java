package script.systems.combat;

import script.library.utils;
import script.obj_id;
import script.string_id;

public class combat_supply_drop_crate extends script.base_script
{
    public combat_supply_drop_crate()
    {
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        obj_id owner = utils.getObjIdScriptVar(self, "supply_drop.crateOwner");
        obj_id ownerGroup = getGroupObject(owner);
        obj_id[] ownerGroupMembers = null;
        if (isIdValid(ownerGroup))
        {
            ownerGroupMembers = getGroupMemberIds(ownerGroup);
        }
        if (transferer == owner)
        {
            return SCRIPT_CONTINUE;
        }
        if (transferer != owner && isIdValid(ownerGroup) && ownerGroupMembers != null)
        {
            for (obj_id ownerGroupMember : ownerGroupMembers) {
                if (ownerGroupMember == transferer) {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        sendSystemMessage(transferer, new string_id("spam", "no_access_not_in_group"));
        return SCRIPT_OVERRIDE;
    }
}
