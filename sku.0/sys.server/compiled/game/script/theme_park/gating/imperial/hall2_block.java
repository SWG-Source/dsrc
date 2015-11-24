package script.theme_park.gating.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.groundquests;
import script.library.space_flags;
import script.library.space_quest;

public class hall2_block extends script.base_script
{
    public hall2_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        boolean hasSpaceAccess = false;
        if (space_quest.hasWonQuest(item, "destroy_surpriseattack", "naboo_station_emperors_access_quest_6"))
        {
            hasSpaceAccess = true;
        }
        if (factions.isImperial(item) && space_flags.hasCompletedTierThree(item))
        {
            hasSpaceAccess = true;
        }
        if (groundquests.isQuestActiveOrComplete(item, "itp_kaja_01") || hasSpaceAccess || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("theme_park_imperial/warning", "kaja_orzee");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
