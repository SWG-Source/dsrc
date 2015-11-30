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

public class dead_eye_disk extends script.base_script
{
    public dead_eye_disk()
    {
    }
    public static final String REBEL_STF = "theme_park/alderaan/act2/rebel_missions";
    public static final String REBEL_SHARED_STF = "theme_park/alderaan/act2/shared_rebel_missions";
    public static final string_id DISK_NAME_DECODED = new string_id(REBEL_SHARED_STF, "disk_name_decoded");
    public static final string_id DISK_NAME_ENCODED = new string_id(REBEL_SHARED_STF, "disk_name_encoded");
    public static final string_id DECODED_DATA_DISK = new string_id(REBEL_SHARED_STF, "decoded_data_disk");
    public static final string_id DECODE_FAILED = new string_id(REBEL_SHARED_STF, "decode_failed");
    public static final string_id SID_USE = new string_id(REBEL_SHARED_STF, "use");
    public static final string_id SID_DECODE = new string_id(REBEL_SHARED_STF, "decode");
    public static final String SID_CLOSE = "@" + REBEL_SHARED_STF + ":close";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "diskDecoded"))
        {
            setName(self, DISK_NAME_DECODED);
        }
        else 
        {
            setName(self, DISK_NAME_ENCODED);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
        if (!hasObjVar(self, "diskDecoded"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_DECODE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            displayDialog(self, player);
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            decodeDisk(self, player);
        }
        return SCRIPT_OVERRIDE;
    }
    public void displayDialog(obj_id self, obj_id player) throws InterruptedException
    {
        String title = null;
        String text = null;
        if (hasObjVar(self, "diskDecoded"))
        {
            string_id titleID = new string_id(REBEL_STF, "disk_decoded_title");
            title = localize(titleID);
            string_id textID = new string_id(REBEL_STF, "disk_decoded_text");
            text = localize(textID);
        }
        else 
        {
            string_id titleID = new string_id(REBEL_STF, "disk_encoded_title");
            title = localize(titleID);
            string_id textID = new string_id(REBEL_STF, "disk_encoded_text");
            text = localize(textID);
        }
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
    public void decodeDisk(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.playerHasItemByTemplate(player, "object/tangible/encoded_disk/dead_eye_decoder.iff"))
        {
            setObjVar(self, "diskDecoded", 1);
            setObjVar(player, "coa2.progress", 3);
            sendSystemMessage(player, DECODED_DATA_DISK);
            setName(self, DISK_NAME_DECODED);
        }
        else 
        {
            sendSystemMessage(player, DECODE_FAILED);
        }
    }
}
