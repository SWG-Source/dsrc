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

public class trade_in extends script.base_script
{
    public trade_in()
    {
    }
    public static final string_id REWARD_TRADE_IN = new string_id("veteran_new", "trade_in_reward");
    public static final String SCRIPTVAR_REWARD_TRADE_IN_SUI_ID = "rewardTradeInSuiId";
    public static final String SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID = "rewardTradeInItemId";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.REWARD_TRADE_IN, REWARD_TRADE_IN);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.REWARD_TRADE_IN)
        {
            if (veteranCanTradeInReward(player, self))
            {
                if (utils.hasScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID))
                {
                    int savedPageId = utils.getIntScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID);
                    utils.removeScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID);
                    utils.removeScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID);
                    forceCloseSUIPage(savedPageId);
                }
                int pid = sui.msgbox(player, player, "@veteran_new:trade_in_confirmation", sui.OK_CANCEL, "Reward Trade In Confirmation", "handleRewardTradeInConfirmation");
                sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "No");
                sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Yes");
                utils.setScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_SUI_ID, pid);
                utils.setScriptVar(player, SCRIPTVAR_REWARD_TRADE_IN_ITEM_ID, self);
                showSUIPage(pid);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
