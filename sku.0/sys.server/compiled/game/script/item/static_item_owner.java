package script.item;

import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class static_item_owner extends script.base_script
{
    public static_item_owner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            static_item.origOwnerCheckStamp(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (isIdValid(player))
        {
            static_item.origOwnerCheckStamp(self, player);
        }
        return SCRIPT_CONTINUE;
    }
}
