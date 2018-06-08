package script.quest.force_sensitive;

import script.menu_info;
import script.menu_info_types;
import script.obj_id;

public class fs_force_crystal extends script.base_script
{
    public fs_force_crystal()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            sendConsoleCommand("/ui action questJournal", player);
        }
        return SCRIPT_CONTINUE;
    }
}
