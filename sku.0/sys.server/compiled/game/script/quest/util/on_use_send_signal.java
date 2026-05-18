package script.quest.util;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class on_use_send_signal extends script.base_script
{
    public on_use_send_signal()
    {
    }
    public static string_id SID_MNU_USE = new string_id("pet/pet_menu", "menu_store");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "string_to_use") && hasObjVar(self, "string_to_use_1"))
        {
            SID_MNU_USE = new string_id(getStringObjVar(self, "string_to_use"), getStringObjVar(self, "string_to_use_1"));
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_MNU_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "quest_to_have") && hasObjVar(self, "signal_to_send") && hasObjVar(self, "task_to_have"))
        {
            if (groundquests.isTaskActive(player, getStringObjVar(self, "quest_to_have"), getStringObjVar(self, "task_to_have")))
            {
                groundquests.sendSignal(player, getStringObjVar(self, "signal_to_send"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
