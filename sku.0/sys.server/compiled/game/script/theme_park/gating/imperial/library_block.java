package script.theme_park.gating.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class library_block extends script.base_script
{
    public library_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if ((getIntObjVar(item, "theme_park_imperial") > 9) || (getIntObjVar(item, "space_access_imperial") > 9))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            string_id warning = new string_id("theme_park_imperial/warning", "loam_redge");
            sendSystemMessage(item, warning);
            return SCRIPT_OVERRIDE;
        }
    }
}
