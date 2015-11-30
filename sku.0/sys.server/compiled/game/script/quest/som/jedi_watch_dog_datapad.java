package script.quest.som;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;

public class jedi_watch_dog_datapad extends script.base_script
{
    public jedi_watch_dog_datapad()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id LISTEN = new string_id(STF, "jedi_datapad_listen");
    public static final string_id CLICK = new string_id(STF, "jedi_datapad_click");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, LISTEN);
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
            if (!groundquests.isQuestActiveOrComplete(player, "som_jedi_dog"))
            {
                groundquests.grantQuest(player, "som_jedi_dog");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                sendSystemMessage(player, CLICK);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
