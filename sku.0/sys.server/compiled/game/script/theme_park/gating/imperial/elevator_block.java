package script.theme_park.gating.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class elevator_block extends script.base_script
{
    public elevator_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (groundquests.hasCompletedQuest(item, "itp_veers_02") || isGod(item))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("theme_park_imperial/warning", "veers");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
