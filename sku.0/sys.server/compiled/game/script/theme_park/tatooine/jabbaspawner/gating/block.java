package script.theme_park.tatooine.jabbaspawner.gating;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class block extends script.base_script
{
    public block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        int gating = getIntObjVar(item, "theme_park_jabba");
        if (gating < 6)
        {
            string_id warning = new string_id("theme_park_jabba/warning", "ree_yees");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
}
