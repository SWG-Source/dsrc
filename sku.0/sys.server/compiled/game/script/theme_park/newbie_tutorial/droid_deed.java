package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.pet_lib;

public class droid_deed extends script.base_script
{
    public droid_deed()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenuOrServerNotify(menu_info_types.ITEM_USE, null);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            createDroid(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void createDroid(obj_id self, obj_id player) throws InterruptedException
    {
        location here = getLocation(player);
        here.x = here.x + 1;
        obj_id pet = create.object("tutorial_droid", here);
        pet_lib.makePet(pet, player);
        destroyObject(self);
        return;
    }
}
