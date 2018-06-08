package script.item.container;

import script.obj_id;

public class loot_crate_opened extends script.base_script
{
    public loot_crate_opened()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
