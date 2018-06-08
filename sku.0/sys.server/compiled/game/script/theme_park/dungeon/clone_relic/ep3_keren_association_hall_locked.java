package script.theme_park.dungeon.clone_relic;

import script.library.groundquests;
import script.obj_id;
import script.string_id;

public class ep3_keren_association_hall_locked extends script.base_script
{
    public ep3_keren_association_hall_locked()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.isTaskActive(item, "ep3_clone_relics_clone_trooper_mort_imperial", "talkToEmperor"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (hasObjVar(item, "doneWithEmperor"))
        {
            removeObjVar(item, "doneWithEmperor");
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
