package script.theme_park.dungeon.corvette;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.permissions;

public class meetingroomopen extends script.base_script
{
    public meetingroomopen()
    {
    }
    public static final String MSGS = "dungeon/corvette";
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
        obj_id top = getTopMostContainer(self);
        if (item == menu_info_types.ITEM_USE)
        {
            if (!hasObjVar(player, "corl_corvette.meetingroom38"))
            {
                setObjVar(player, "corl_corvette.meetingroom38", 1);
                string_id open = new string_id(MSGS, "unlock_meeting");
                obj_id room = getCellId(top, "meetingroom38");
                permissionsMakePublic(room);
                sendSystemMessage(player, open);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                string_id already = new string_id(MSGS, "already_open");
                sendSystemMessage(player, already);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
