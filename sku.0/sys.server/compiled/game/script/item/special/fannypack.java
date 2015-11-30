package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class fannypack extends script.base_script
{
    public fannypack()
    {
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (isIdValid(destContainer) && isPlayer(destContainer))
        {
            performUnequipBackSlotContainer(destContainer);
        }
        if (isIdValid(sourceContainer) && isPlayer(sourceContainer))
        {
            performUnequipBackSlotContainer(sourceContainer);
        }
        return SCRIPT_CONTINUE;
    }
    public void performUnequipBackSlotContainer(obj_id player) throws InterruptedException
    {
        if (isIdValid(player))
        {
            obj_id backpack = getObjectInSlot(player, "back");
            if (!isIdValid(backpack))
            {
                return;
            }
            int type = getGameObjectType(backpack);
            if (isGameObjectTypeOf(type, GOT_misc_container_wearable))
            {
                obj_id inv = utils.getInventoryContainer(player);
                putInOverloaded(backpack, inv);
            }
        }
        return;
    }
}
