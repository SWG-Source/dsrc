package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.create;
import script.library.trial;
import script.library.utils;

public class bleach_vat extends script.base_script
{
    public bleach_vat()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id BLEACH_SKULL = new string_id(STF, "jundak_skull_bleach");
    public static final string_id GET_SKULL = new string_id(STF, "jundak_skull_retrieve");
    public static final string_id BLEACH_DONE = new string_id(STF, "jundak_skull_bleach_done");
    public static final string_id SKULL_VAT = new string_id(STF, "jundak_skull_vat");
    public static final string_id REMOVE_SKULL = new string_id(STF, "jundak_skull_remove");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_jundak_skull", "jundak_skull_two"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, BLEACH_SKULL);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        if (groundquests.isTaskActive(player, "som_jundak_skull", "jundak_skull_three"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, GET_SKULL);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "som_jundak_skull", "jundak_skull_two"))
            {
                sendSystemMessage(player, SKULL_VAT);
                dictionary dict = new dictionary();
                dict.put("target", player);
                messageTo(self, "handleBleachSkull", dict, 10, false);
                playClientEffectObj(self, "clienteffect/must_bleach_vat.cef", self, "");
                return SCRIPT_CONTINUE;
            }
            if (groundquests.isTaskActive(player, "som_jundak_skull", "jundak_skull_three"))
            {
                sendSystemMessage(player, REMOVE_SKULL);
                groundquests.sendSignal(player, "jundak_skull_signal_two");
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBleachSkull(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("target");
        sendSystemMessage(player, BLEACH_DONE);
        groundquests.sendSignal(player, "jundak_skull_signal_one");
        return SCRIPT_CONTINUE;
    }
}
