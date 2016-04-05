package script.event.emp_day;

import script.library.factions;
import script.library.groundquests;
import script.obj_id;
import script.string_id;

public class gating_solo extends script.base_script
{
    public gating_solo()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        int questId = questGetQuestId("quest/emp_day_rebel");
        String whichFaction = factions.getFactionNameByHashCode(factions.pvpGetAlignedFaction(item));
        if (questIsTaskActive(questId, groundquests.getTaskId(questId, "empDayComplete"), item) ||
            questIsTaskActive(questId, groundquests.getTaskId(questId, "toSolo"), item) ||
            questIsQuestComplete(questId, item))
        {
            return SCRIPT_CONTINUE;
        }
        if (whichFaction == null || whichFaction.equals("Imperial"))
        {
            sendSystemMessage(item, new string_id("event/empire_day", "no_entry"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
}
