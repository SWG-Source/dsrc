package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;

public class respec_voucher_deed extends script.base_script
{
    public respec_voucher_deed()
    {
    }
    public static final String STF_FILE = "veteran";
    public static final string_id RESPEC_VOUCHER = new string_id(STF_FILE, "respec_voucher");
    public static final string_id SID_RESPEC_VOUCHER_TITLE = new string_id(STF_FILE, "sui_respec_title");
    public static final string_id SID_RESPEC_VOUCHER_PROMPT = new string_id(STF_FILE, "sui_respec_prompt");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, RESPEC_VOUCHER);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String title = utils.packStringId(SID_RESPEC_VOUCHER_TITLE);
            String prompt = utils.packStringId(SID_RESPEC_VOUCHER_PROMPT);
            sui.msgbox(self, player, prompt, sui.YES_NO, title, "handleRespecChoice");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRespecChoice(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(player, "respec_voucher", 1);
        sendSystemMessage(player, new string_id(STF_FILE, "msg_respec_used"));
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
