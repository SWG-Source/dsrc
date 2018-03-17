package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.create;

public class debris_terminal extends script.base_script
{
    public debris_terminal()
    {
    }
    public static final String MSGS = "dungeon/death_watch";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Clearance Terminal");
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
            destroyDebris(self);
        }
        return SCRIPT_CONTINUE;
    }
    public void destroyDebris(obj_id self) throws InterruptedException
    {
        obj_id debris = getObjIdObjVar(self, "mom");
        destroyObject(debris);
        messageTo(self, "cleanUpTime", null, 30, false);
        return;
    }
    public int cleanUpTime(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
