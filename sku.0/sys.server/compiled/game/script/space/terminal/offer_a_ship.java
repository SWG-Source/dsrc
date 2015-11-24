package script.space.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;

public class offer_a_ship extends script.base_script
{
    public offer_a_ship()
    {
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (!space_utils.hasShip(player))
            {
                if (utils.hasScriptVar(player, "offer_a_ship.openSui"))
                {
                    int oldSui = utils.getIntScriptVar(player, "offer_a_ship.openSui");
                    utils.removeScriptVar(player, "offer_a_ship.openSui");
                    if (oldSui > -1)
                    {
                        forceCloseSUIPage(oldSui);
                    }
                }
                string_id title = new string_id("new_player", "offer_a_ship_title");
                string_id textMsg = new string_id("new_player", "offer_a_ship_msg");
                string_id okButton = new string_id("new_player", "default_okay_button");
                string_id cancelButton = new string_id("new_player", "default_cancel_button");
                String TITLE_MSG = utils.packStringId(title);
                String TEXT_MSG = utils.packStringId(textMsg);
                String OK_BUTTON = utils.packStringId(okButton);
                String CANCEL_BUTTON = utils.packStringId(cancelButton);
                int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleSpaceTerminalOfferAShip");
                setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_MSG);
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT_MSG);
                sui.msgboxButtonSetup(pid, sui.YES_NO);
                setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
                setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
                utils.setScriptVar(player, "offer_a_ship.openSui", pid);
                sui.showSUIPage(pid);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpaceTerminalOfferAShip(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            space_quest.grantNewbieShip(player, "neutral");
            break;
            case sui.BP_CANCEL:
            break;
        }
        return SCRIPT_CONTINUE;
    }
}
