package script.theme_park.gating.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.space_quest;

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
