package script.theme_park.dungeon.mustafar_trials.mining_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.badge;

public class obi_wan_prelude_quest_grant extends script.base_script
{
    public obi_wan_prelude_quest_grant()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (!badge.hasBadge(item, "exp_must_mustafar_found"))
        {
            badge.grantBadge(item, "exp_must_mustafar_found");
        }
        if (groundquests.hasCompletedQuest(item, "som_kenobi_main_quest_3") || groundquests.hasCompletedQuest(item, "som_kenobi_main_quest_3_b"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (!hasScript(item, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor"))
            {
                attachScript(item, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor");
            }
            return SCRIPT_CONTINUE;
        }
    }
}
