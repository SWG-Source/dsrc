package script.space.special_loot;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;

public class story_loot extends script.base_script
{
    public story_loot()
    {
    }
    public static final string_id SID_READ = new string_id("space/quest", "read");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("space/quest", "not_in_inv");
    public static final String DATATABLE_NAME = "datatables/space_loot/story_loot.iff";
    public static final String STF_TITLE = "space/story_loot_n";
    public static final String STF_TEXT = "space/story_loot_d";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_READ);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            displayDialog(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public void displayDialog(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return;
        }
        String template = getTemplateName(self);
        int row = dataTableSearchColumnForString(template, 0, DATATABLE_NAME);
        if (row < 0)
        {
            return;
        }
        String title_id = dataTableGetString(DATATABLE_NAME, row, 1);
        String text_id = dataTableGetString(DATATABLE_NAME, row, 2);
        String title = "@" + STF_TITLE + ":" + title_id;
        String text = "@" + STF_TEXT + ":" + text_id;
        int pid = sui.msgbox(self, player, text, sui.OK_ONLY, title, "handleStoryLootSui");
    }
}
