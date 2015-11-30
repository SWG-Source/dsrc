package script.money.clear;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;

public class target extends script.base_script
{
    public target()
    {
    }
    public int handleCovertDepositReturn(obj_id self, dictionary params) throws InterruptedException
    {
        int cash = getCashBalance(self);
        if (cash > 0)
        {
            money.covertDeposit(self, cash, "handleCovertDepositReturn", params);
            return SCRIPT_CONTINUE;
        }
        int bank = getBankBalance(self);
        if (bank < 1)
        {
            return SCRIPT_CONTINUE;
        }
        String acct = params.getString("acct");
        transferBankCreditsToNamedAccount(self, acct, bank, "finishClear", "finishClear", params);
        return SCRIPT_CONTINUE;
    }
    public int finishClear(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, "money.clear.target");
        return SCRIPT_CONTINUE;
    }
}
