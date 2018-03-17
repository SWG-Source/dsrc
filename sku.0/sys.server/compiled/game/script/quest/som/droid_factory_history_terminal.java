package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class droid_factory_history_terminal extends script.base_script
{
    public droid_factory_history_terminal()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ACCESS = new string_id(STF, "df_terminal_use");
    public static final string_id SLICE = new string_id(STF, "df_terminal_slice");
    public static final string_id USE_OVERRIDE = new string_id(STF, "df_terminal_use_override");
    public static final string_id CHOOSE = new string_id(STF, "df_terminal_choose");
    public static final string_id DATAPAD = new string_id(STF, "df_terminal_datapad");
    public static final string_id UNSKILLED = new string_id(STF, "df_terminal_unskilled");
    public static final string_id NOT_READY = new string_id(STF, "df_terminal_not_ready");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACCESS);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_droidfactory_terminal"))
            {
                groundquests.sendSignal(player, "mustafar_droidfactory_open");
                sendSystemMessage(player, DATAPAD);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, NOT_READY);
                return SCRIPT_CONTINUE;
            }
        }
        else 
        {
            sendSystemMessage(player, CHOOSE);
        }
        return SCRIPT_CONTINUE;
    }
}
