package script.theme_park.dungeon.clone_relic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.space_flags;
import script.library.space_quest;
import script.library.utils;

public class ep3_imperial_bunker_kashyyyk_locked extends script.base_script
{
    public ep3_imperial_bunker_kashyyyk_locked()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (space_quest.hasFailedQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_4") || space_quest.hasAbortedQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_4") || space_quest.hasFailedQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_5") || space_quest.hasAbortedQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_5"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (utils.hasScriptVar(item, "limitedVaderPass"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "doneWithVader"))
        {
            removeObjVar(item, "doneWithVader");
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_jedi_starfighter_4"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (space_quest.hasWonQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_5") && !space_quest.hasReceivedReward(item, "space_battle", "ep3_clone_relics_jedi_starfighter_5"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (space_quest.hasQuest(item, "space_battle", "ep3_clone_relics_jedi_starfighter_4"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "gm") || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("ep3/clone_relic_locks", "access_denied");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
