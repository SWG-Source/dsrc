package script.event.emp_day;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.factions;

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
        int task7 = groundquests.getTaskId(questId, "toSolo");
        int task8 = groundquests.getTaskId(questId, "empDayComplete");
        int factionHashCode = factions.pvpGetAlignedFaction(item);
        String whichFaction = factions.getFactionNameByHashCode(factionHashCode);
        if (questIsTaskActive(questId, task8, item) || questIsTaskActive(questId, task7, item) || questIsQuestComplete(questId, item))
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
