package script.theme_park.newbie_tutorial;

import script.menu_info;
import script.obj_id;

public class bank2 extends script.theme_park.newbie_tutorial.tutorial_base
{
    public bank2()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        sysMessage(player, "wrong_bank");
        return SCRIPT_OVERRIDE;
    }
}
