package script.systems.crafting.droid;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.craftinglib;
import script.library.pet_lib;
import script.library.utils;

public class droid_battery extends script.base_script
{
    public droid_battery()
    {
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int count = getCount(self);
        if (getCount(self) > 1)
        {
            names[idx] = "charges";
            attribs[idx] = "" + count;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIncapacitated(player) || isDead(player))
            {
                return SCRIPT_CONTINUE;
            }
            obj_id pet = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
            if (!isIdValid(pet) || !exists(pet) || !pet_lib.isDroidPet(pet))
            {
                return SCRIPT_CONTINUE;
            }
            pet_lib.feedPet(pet, player);
        }
        return SCRIPT_CONTINUE;
    }
}
