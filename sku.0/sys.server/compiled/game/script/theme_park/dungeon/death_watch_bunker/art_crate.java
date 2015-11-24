package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.pet_lib;

public class art_crate extends script.base_script
{
    public art_crate()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Art Crate");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid == null)
        {
            return SCRIPT_CONTINUE;
        }
        mid.setServerNotify(true);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            makeArt(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void makeArt(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inv = utils.getInventoryContainer(player);
        int whichArt = rand(1, 3);
        if (whichArt == 1)
        {
            createObject("object/tangible/furniture/all/frn_all_decorative_sm_s4.iff", inv, null);
        }
        if (whichArt == 2)
        {
            createObject("object/tangible/furniture/all/frn_all_decorative_lg_s1.iff", inv, null);
        }
        else 
        {
            createObject("object/tangible/furniture/all/frn_all_decorative_lg_s2.iff", inv, null);
        }
        destroyObject(self);
        return;
    }
}
