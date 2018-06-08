package script.theme_park.gating.jabba;

import script.library.groundquests;
import script.obj_id;
import script.string_id;

public class master_block extends script.base_script
{
    public master_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if ((getIntObjVar(item, "theme_park_jabba") > 27) || (getIntObjVar(item, "space_access_jabba") > 27))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.hasCompletedQuest(item, "quest/jabba_the_hutt") || groundquests.hasCompletedQuest(item, "quest/jabba_the_hutt_v2") || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.hasCompletedQuest(item, "ep3_clone_relics_clone_trooper_neutral"))
        {
            return SCRIPT_CONTINUE;
        }
        else if (groundquests.isQuestActive(item, "ep3_clone_relics_clone_trooper_neutral"))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("theme_park_jabba/warning", "jabba");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
