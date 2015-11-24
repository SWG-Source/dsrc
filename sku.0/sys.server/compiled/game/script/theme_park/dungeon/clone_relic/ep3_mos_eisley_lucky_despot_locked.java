package script.theme_park.dungeon.clone_relic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class ep3_mos_eisley_lucky_despot_locked extends script.base_script
{
    public ep3_mos_eisley_lucky_despot_locked()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_4", "talkToSolo1") || groundquests.isTaskActive(item, "ep3_clone_relics_nym_starmap_4", "haveMoney"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "doneWithSolo"))
        {
            removeObjVar(item, "doneWithSolo");
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "gm") || isGod(item))
        {
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
