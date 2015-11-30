package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bazaar extends script.theme_park.newbie_tutorial.tutorial_base
{
    public bazaar()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        messageTo(player, "handleBazaarInfoSpam", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
