package script.theme_park.alderaan.act2;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class imperial_slicer_disk extends script.base_script
{
    public imperial_slicer_disk()
    {
    }
    public static final String IMPERIAL_STF = "theme_park/alderaan/act2/imperial_missions";
    public static final String IMPERIAL_SHARED_STF = "theme_park/alderaan/act2/shared_imperial_missions";
    public static final string_id DISK_NAME_SLICER = new string_id(IMPERIAL_SHARED_STF, "disk_name_slicer");
    public static final string_id SID_USE = new string_id(IMPERIAL_SHARED_STF, "use");
    public static final String SID_CLOSE = "@" + IMPERIAL_SHARED_STF + ":close";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, DISK_NAME_SLICER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            displayDialog(self, player);
        }
        return SCRIPT_OVERRIDE;
    }
    public void displayDialog(obj_id self, obj_id player) throws InterruptedException
    {
        String title = null;
        String text = null;
        string_id titleID = new string_id(IMPERIAL_STF, "disk_title");
        title = localize(titleID);
        string_id textID = new string_id(IMPERIAL_STF, "disk_text");
        text = localize(textID);
        createDialog(self, player, text, title);
    }
    public int createDialog(obj_id self, obj_id player, String text, String title) throws InterruptedException
    {
        if (player == null)
        {
            return -1;
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        sui.msgboxButtonSetup(pid, sui.OK_ONLY);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, SID_CLOSE);
        sui.showSUIPage(pid);
        return pid;
    }
}
