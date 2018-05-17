package script.quest.som;

import script.library.groundquests;
import script.*;

public class hk47_final_message extends script.base_script
{
    public hk47_final_message()
    {
    }
    public static final String STF = "som/som_quest";
    public static final string_id ACTIVATE = new string_id(STF, "comm_console_message");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_five"))
        {
            int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACTIVATE);
            menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
            if (mid == null)
            {
                return SCRIPT_CONTINUE;
            }
            mid.setServerNotify(true);
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_03", "volcano_arena_five"))
            {
                groundquests.sendSignal(player, "hk47_final_goodbye");
                return SCRIPT_CONTINUE;
            }
        }
        sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_no_interest"));
        return SCRIPT_CONTINUE;
    }
}
