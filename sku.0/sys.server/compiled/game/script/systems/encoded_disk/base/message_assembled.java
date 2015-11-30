package script.systems.encoded_disk.base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.locations;
import script.library.utils;
import script.library.scenario;
import script.library.create;
import script.library.ai_lib;

public class message_assembled extends script.base_script
{
    public message_assembled()
    {
    }
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("encoded_disk/message_fragment", "sys_not_in_inv");
    public static final string_id SID_USE = new string_id("treasure_map/treasure_map", "use");
    public static final String SID_CLOSE = "@treasure_map/treasure_map:close";
    public static final String MESSAGE_TABLE = "datatables/encoded_disk/message_fragment.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String scenarioName = getStringObjVar(self, "scenarioName");
        string_id nameId = new string_id("encoded_disk/message_fragment", "title_" + scenarioName);
        setName(self, nameId);
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
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, SID_SYS_NOT_IN_INV);
            return;
        }
        String entryName = getStringObjVar(self, "scenarioName");
        string_id textID = new string_id("encoded_disk/message_fragment", "complete_" + entryName);
        String text = localize(textID);
        string_id titleID = new string_id("encoded_disk/message_fragment", "name_" + entryName);
        String title = localize(titleID);
        if (hasObjVar(self, "specString"))
        {
            string_id fragID = new string_id("encoded_disk/message_fragment", "frag_" + entryName + "_" + getIntObjVar(self, "specString"));
            String frag = localize(fragID);
            text += "\n" + frag;
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
}
