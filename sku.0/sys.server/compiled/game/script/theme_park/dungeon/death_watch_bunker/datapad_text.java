package script.theme_park.dungeon.death_watch_bunker;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.pet_lib;

public class datapad_text extends script.base_script
{
    public datapad_text()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Data Storage Unit");
        if (!hasObjVar(self, "text_id"))
        {
            int book = rand(1, 10);
            setObjVar(self, "text_id", book);
        }
        return SCRIPT_CONTINUE;
    }
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
            testSui(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int testSui(obj_id self, obj_id player) throws InterruptedException
    {
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "readableText");
        string_id fallBackString = new string_id("BOOK", "wiped");
        string_id fallBackTitle = new string_id("BOOK", "no_title");
        if (hasObjVar(self, "text_id"))
        {
            int textNum = getIntObjVar(self, "text_id");
            fallBackString = new string_id("dungeon/death_watch_book_text", "text_" + textNum);
            fallBackTitle = new string_id("dungeon/death_watch_book_text", "title_" + textNum);
        }
        String text = getString(fallBackString);
        String title = getString(fallBackTitle);
        if (title == null || title.equals(""))
        {
            title = "NO TITLE";
        }
        if (text == null || text.equals(""))
        {
            text = "The information stored here has been removed.";
        }
        setSUIProperty(pid, "", "Size", "450,375");
        setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "CLOSE");
        sui.showSUIPage(pid);
        return pid;
    }
}
