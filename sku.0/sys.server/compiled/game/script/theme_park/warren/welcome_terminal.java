package script.theme_park.warren;

import script.library.sui;
import script.location;
import script.menu_info;
import script.obj_id;
import script.string_id;

public class welcome_terminal extends script.base_script
{
    public welcome_terminal()
    {
    }
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 10.0)
        {
            sendSystemMessage(player, new string_id(SYSTEM_MESSAGES, "elev_range"));
            return SCRIPT_CONTINUE;
        }
        sui.msgbox(player, new string_id(SYSTEM_MESSAGES, "welcome_terminal"));
        return SCRIPT_CONTINUE;
    }
}
