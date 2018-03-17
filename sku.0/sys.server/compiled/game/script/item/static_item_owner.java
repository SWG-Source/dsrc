package script.item;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;

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
