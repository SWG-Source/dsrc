package script.theme_park.dungeon.clone_relic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class ep3_narmle_hotel_locked extends script.base_script
{
    public ep3_narmle_hotel_locked()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedQuest(item, "ep3_clone_relics_nym_starmap_1"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.hasCompletedQuest(item, "ep3_clone_relics_nym_starmap_2"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.hasCompletedQuest(item, "ep3_clone_relics_nym_starmap_3"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_2", "foundSecondMap"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_3", "foundThirdMap"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_nym_starmap_1"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_nym_starmap_2"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_nym_starmap_3"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_nym_starmap_4"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_1", "talkToNym1"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_1", "foundFirstMap"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_4", "talkedToSolo1"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_4", "foundFourthMap"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "gm") || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "doneWithNym"))
        {
            removeObjVar(item, "doneWithNym");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("ep3/clone_relic_locks", "cantina_denied");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
