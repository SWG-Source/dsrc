package script.theme_park.wod;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.static_item;
import script.library.utils;

public class random_wod_token extends script.base_script
{
    public random_wod_token()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "createRandomObject", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        messageTo(self, "createRandomObject", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        messageTo(self, "createRandomObject", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int createRandomObject(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getContainedBy(self);
        if (!isPlayer(player))
        {
            player = getContainedBy(player);
            if (!isPlayer(player))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "wod.itemAlreadyCreated"))
        {
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(self) || !exists(self) || !isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int amountToCreate = getCount(self);
        if (amountToCreate == 0)
        {
            amountToCreate = 1;
        }
        utils.setObjVar(self, "wod.itemAlreadyCreated", 1);
        for (int i = 0; i < amountToCreate; i++)
        {
            static_item.createNewItemFunction("item_wod_token_" + rand(1, 5), utils.getInventoryContainer(player));
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
