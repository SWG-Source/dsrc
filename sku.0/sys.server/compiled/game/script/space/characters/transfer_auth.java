package script.space.characters;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;

public class transfer_auth extends script.base_script
{
    public transfer_auth()
    {
    }
    public static final string_id SID_READ = new string_id("space/quest", "read");
    public static final string_id SID_SYS_NOT_IN_INV = new string_id("space/quest", "not_in_inv");
    public static final string_id SID_SIGNED = new string_id("space/quest", "signed");
    public static final String SID_SIGN = "@space/quest:sign";
    public static final String SID_CLOSE = "@space/quest:close";
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
        String text = "@space/quest:ta_text";
        String title = "@space/quest:ta_title";
        String signed = getStringObjVar(self, "signature");
        if (signed != null)
        {
            text += "\n Signed: " + signed + "\n";
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        sui.msgboxButtonSetup(pid, sui.OK_CANCEL);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, SID_SIGN);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, SID_CLOSE);
        sui.showSUIPage(pid);
        return;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            signForm(self, player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void signForm(obj_id self, obj_id player) throws InterruptedException
    {
        sui.inputbox(self, player, "@space/quest:ta_sign", sui.OK_CANCEL, "@space/quest:ta_sign_title", sui.MSG_NORMAL, null, "handleSignedForm", null);
    }
    public int handleSignedForm(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        String text = sui.getInputBoxText(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            setObjVar(self, "signature", text);
            sendSystemMessage(player, SID_SIGNED);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
