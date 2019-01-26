package script.theme_park.gating.syren4;

import script.library.groundquests;
import script.library.group;
import script.obj_id;
import script.string_id;

public class reb_safehouse extends script.base_script
{
    public reb_safehouse()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item) || isIdValid(destinationCell))
        {
            return SCRIPT_CONTINUE;
        }
        string_id warning = new string_id("theme_park/messages", "access_denied");
        if (group.isGrouped(item))
        {
            obj_id groupObj = getGroupObject(item);
            if (isIdValid(groupObj))
            {
                obj_id[] groupMembers = getGroupMemberIds(groupObj);
                int numGroupMembers = groupMembers.length;
                for (obj_id groupie : groupMembers) {
                    if (isIdValid(groupie)) {
                        if (groundquests.isQuestActive(item, "c_story1_4b_rebel") && !groundquests.hasCompletedTask(groupie, "c_story1_4b_rebel", "reb_choice")) {
                            return SCRIPT_CONTINUE;
                        }
                    }
                }
                sendSystemMessage(item, warning);
                return SCRIPT_OVERRIDE;
            }
        }
        else if (groundquests.isQuestActive(item, "c_story1_4b_rebel") && !groundquests.hasCompletedTask(item, "c_story1_4b_rebel", "reb_choice"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
