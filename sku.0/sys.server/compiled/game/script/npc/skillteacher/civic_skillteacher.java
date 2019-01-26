package script.npc.skillteacher;

import script.library.city;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class civic_skillteacher extends script.base_script
{
    public civic_skillteacher()
    {
    }
    public static final string_id SID_MT_REMOVE = new string_id("city/city", "mt_remove");
    public static final string_id SID_MT_REMOVED = new string_id("city/city", "mt_removed");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int city_id = city.checkMayorCity(player, false);
        if (city_id == 0)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_MT_REMOVE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        int city_id = city.checkMayorCity(player, false);
        if (city_id == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id spawner = getObjIdObjVar(self, "spawner");
            sendSystemMessage(player, SID_MT_REMOVED);
            messageTo(spawner, "requestDestroy", null, 0.0f, false);
        }
        return SCRIPT_CONTINUE;
    }
}
