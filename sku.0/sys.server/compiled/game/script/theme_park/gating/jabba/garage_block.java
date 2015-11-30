package script.theme_park.gating.jabba;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class garage_block extends script.base_script
{
    public garage_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if ((getIntObjVar(item, "theme_park_jabba") > 11) || (getIntObjVar(item, "space_access_jabba") > 11) || (groundquests.hasCompletedQuest(item, "quest/jabba_porcellus")) || isGod(item))
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
            string_id warning = new string_id("theme_park_jabba/warning", "porcellus");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
