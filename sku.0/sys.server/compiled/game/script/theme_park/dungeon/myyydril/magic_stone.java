package script.theme_park.dungeon.myyydril;

import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class magic_stone extends script.base_script
{
    public magic_stone()
    {
    }
    public static final string_id TELEPORT = new string_id("dungeon/myyydril", "teleport");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int teleport = mi.addRootMenu(menu_info_types.SERVER_MENU1, TELEPORT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id container = getTopMostContainer(self);
            String template = "";
            if (isIdValid(container))
            {
                template = getTemplateName(container);
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
            if (template.equals("object/building/kashyyyk/thm_kash_myyydril_caverns.iff"))
            {
                warpPlayer(player, "kashyyyk_main", -568, 0, -100, null, 0, 0, 0, null, false);
            }
            else 
            {
                string_id msg = new string_id("dungeon/myyydril", "cantusestone");
                sendSystemMessage(player, msg);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
