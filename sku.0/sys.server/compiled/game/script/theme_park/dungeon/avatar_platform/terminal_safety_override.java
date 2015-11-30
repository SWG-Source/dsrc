package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class terminal_safety_override extends script.base_script
{
    public terminal_safety_override()
    {
    }
    public static final String STF = "dungeon/avatar_platform";
    public static final string_id SAFETY_STATUS = new string_id(STF, "safety_status");
    public static final string_id SHIELD_OFF = new string_id(STF, "shield_off");
    public static final string_id NOT_NEEDED = new string_id(STF, "not_needed");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, SAFETY_STATUS);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "ep3_avatar_self_destruct", "safetyShield"))
            {
                groundquests.sendSignal(player, "magneticShieldOff");
                sendSystemMessage(player, SHIELD_OFF);
            }
            else 
            {
                sendSystemMessage(player, NOT_NEEDED);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
