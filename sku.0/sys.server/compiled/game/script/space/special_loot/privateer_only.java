package script.space.special_loot;

import script.obj_id;
import script.string_id;

public class privateer_only extends script.base_script
{
    public privateer_only()
    {
    }
    public static final string_id SID_ERROR = new string_id("space/space_loot", "privateer_only");
    public int OnAboutToBeTransferred(obj_id self, obj_id player, obj_id transferer) throws InterruptedException
    {
        if (!isPlayer(player))
        {
            obj_id appearInv = getAppearanceInventory(transferer);
            if (appearInv == player)
            {
                if (!hasSkill(transferer, "pilot_neutral_novice"))
                {
                    sendSystemMessage(transferer, SID_ERROR);
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (!hasSkill(player, "pilot_neutral_novice"))
        {
            sendSystemMessage(player, SID_ERROR);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String template = getTemplateName(self);
        if (template.endsWith("jacket_ace_rebel.iff"))
        {
            attachScript(self, "space.special_loot.rebel_only");
            detachScript(self, "space.special_loot.privateer_only");
        }
        return SCRIPT_CONTINUE;
    }
}
