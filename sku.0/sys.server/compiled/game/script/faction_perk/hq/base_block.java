package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;
import script.library.pclib;
import script.library.utils;

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
        location loc = params.getLocation("loc");
        obj_id player = params.getObjId("player");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (loc == null)
        {
            return SCRIPT_CONTINUE;
        }
        pclib.sendToAnyLocation(player, loc, null);
        return SCRIPT_CONTINUE;
    }
}
