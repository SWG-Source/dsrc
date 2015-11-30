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

public class security extends script.base_script
{
    public security()
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
        if (item == menu_info_types.ITEM_USE)
        {
            setObjVar(player, "corl_corvette.meetingroom38", 1);
            string_id unlockMeeting = new string_id(MSGS, "unlock_meeting");
            sendSystemMessage(player, unlockMeeting);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Security Terminal");
        return SCRIPT_CONTINUE;
    }
}
