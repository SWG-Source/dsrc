package script.space.special_loot;

import script.obj_id;
import script.string_id;

public class rebel_only extends script.base_script
{
    public rebel_only()
    {
    }
    public static final string_id SID_ERROR = new string_id("space/space_loot", "rebel_only");
    public int OnAboutToBeTransferred(obj_id self, obj_id player, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            obj_id appearInv = getAppearanceInventory(transferer);
            if (appearInv == player)
            {
                if (!hasSkill(transferer, "pilot_rebel_navy_novice"))
                {
                    sendSystemMessage(transferer, SID_ERROR);
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasSkill(player, "pilot_rebel_navy_novice"))
        {
            sendSystemMessage(player, SID_ERROR);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
