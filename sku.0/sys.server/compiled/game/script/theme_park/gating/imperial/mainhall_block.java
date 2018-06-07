package script.theme_park.gating.imperial;

import script.library.factions;
import script.library.space_quest;
import script.obj_id;
import script.string_id;

public class mainhall_block extends script.base_script
{
    public mainhall_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (factions.isImperial(item) || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else if (space_quest.hasWonQuest(item, "destroy_surpriseattack", "naboo_station_emperors_access_quest_6"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("theme_park_imperial/warning", "not_imperial");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
