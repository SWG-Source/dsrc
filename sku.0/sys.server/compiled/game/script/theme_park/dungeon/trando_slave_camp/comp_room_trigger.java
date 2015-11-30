package script.theme_park.dungeon.trando_slave_camp;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class comp_room_trigger extends script.base_script
{
    public comp_room_trigger()
    {
    }
    public static final String STF = "terminal_ui";
    public static final string_id ENTER_CODE = new string_id(STF, "security_code_enter");
    public static final string_id DISABLED = new string_id(STF, "security_disabled");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "ep3_slave_camp_control_room_access", "slaverDisableLocks"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ENTER_CODE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id powerRoom = getObjIdObjVar(getTopMostContainer(self), "powerRoom");
            permissionsMakePublic(powerRoom);
            sendSystemMessage(player, DISABLED);
        }
        return SCRIPT_CONTINUE;
    }
}
