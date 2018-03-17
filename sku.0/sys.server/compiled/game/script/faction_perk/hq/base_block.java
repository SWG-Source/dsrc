package script.faction_perk.hq;

import script.dictionary;
import script.library.pclib;
import script.library.utils;
import script.location;
import script.obj_id;

public class base_block extends script.base_script
{
    public base_block()
    {
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id destinationCell, obj_id transferrer, obj_id item) throws InterruptedException
    {
        if (!isPlayer(item))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.isNestedWithin(destinationCell, self))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int ejectPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = params.getLocation("loc");
        if (loc == null)
        {
            return SCRIPT_CONTINUE;
        }
        pclib.sendToAnyLocation(player, loc, null);
        return SCRIPT_CONTINUE;
    }
}
