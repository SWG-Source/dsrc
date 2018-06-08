package script.theme_park.newbie_tutorial;

import script.menu_info;
import script.obj_id;

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
