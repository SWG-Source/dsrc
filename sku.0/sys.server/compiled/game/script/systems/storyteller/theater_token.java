package script.systems.storyteller;

import script.library.storyteller;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class theater_token extends script.base_script
{
    public theater_token()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "deploy_mode"));
        mi.addRootMenu(menu_info_types.ITEM_USE_OTHER, new string_id("storyteller", "buildout_mode"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (storyteller.createTheaterObject(self, false) == null)
            {
                sendSystemMessage(player, new string_id("storyteller", "failed_to_deploy"));
            }
        }
        if (item == menu_info_types.ITEM_USE_OTHER)
        {
            if (storyteller.createTheaterObject(self, true) == null)
            {
                sendSystemMessage(player, new string_id("storyteller", "failed_to_deploy_buildout"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
