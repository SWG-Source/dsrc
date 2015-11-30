package script.beta;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;

public class terminal_money extends script.terminal.base.terminal_add_use
{
    public terminal_money()
    {
    }
    public static final int CASH_AMOUNT = 10000;
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isGod(player) || hasObjVar(player, "beta.terminal_ok"))
        {
            if (item == menu_info_types.ITEM_USE)
            {
                if (getCashBalance(player) < 1000000)
                {
                    dictionary d = new dictionary();
                    d.put("payoutTarget", player);
                    money.systemPayout(money.ACCT_BETA_TEST, self, CASH_AMOUNT, "handlePayoutToPlayer", d);
                }
                else 
                {
                    sendSystemMessageTestingOnly(player, "You already have 1,000,000+ credits. Why do you need any more money?");
                }
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Only authorized users may access this terminal.");
            return SCRIPT_CONTINUE;
        }
    }
    public int handlePayoutToPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("payoutTarget");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int retCode = params.getInt(money.DICT_CODE);
        if (retCode == money.RET_SUCCESS)
        {
            transferCashTo(self, player, CASH_AMOUNT, "handlePayoutSuccess", "handlePayoutFail", params);
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "The system is unable to complete the transaction. Please try again later.");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePayoutSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("payoutTarget");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(player, "You recieve " + CASH_AMOUNT + " credits from " + getString(getNameStringId(self)));
        return SCRIPT_CONTINUE;
    }
    public int handlePayoutFail(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("payoutTarget");
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(player, "The system is unable to complete the transaction. Please try again later.");
        return SCRIPT_CONTINUE;
    }
}
