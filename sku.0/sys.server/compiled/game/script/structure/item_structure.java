package script.structure;

import script.menu_info;
import script.obj_id;

public class item_structure extends script.base_script
{
    public item_structure()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
