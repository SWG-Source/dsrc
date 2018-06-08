package script.item.special;

import script.library.utils;
import script.location;
import script.obj_id;
import script.string_id;

public class cannot_place_in_space extends script.base_script
{
    public cannot_place_in_space()
    {
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        location here = getLocation(self);
        String zone = here.area;
        if (zone.startsWith("space"))
        {
            if (!utils.isNestedWithinAPlayer(destContainer))
            {
                sendSystemMessage(transferer, new string_id("spam", "cannot_place_in_space"));
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
