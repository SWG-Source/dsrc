package script.theme_park.dungeon.trando_slave_camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class power_terminal extends script.base_script
{
    public power_terminal()
    {
    }
    public static final String STF = "terminal_ui";
    public static final string_id POWER_TOGGLE = new string_id(STF, "power_toggle");
    public static final string_id UNLOCKED = new string_id(STF, "power_security_off");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_slave_camp_control_room_access", "slaverDisableLocks"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, POWER_TOGGLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            groundquests.sendSignal(player, "signalSlaverDisableLocks");
            permissionsMakePublic(getObjIdObjVar(getTopMostContainer(self), "sequencer"));
            sendSystemMessage(player, UNLOCKED);
        }
        return SCRIPT_CONTINUE;
    }
}
