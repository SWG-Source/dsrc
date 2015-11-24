package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.money;

public class player_money extends script.base_script
{
    public player_money()
    {
    }
    public int handlePayDeposit(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        int total = params.getInt(money.DICT_TOTAL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        if (total < 1)
        {
            return SCRIPT_CONTINUE;
        }
        int retCode = money.getReturnCode(params);
        switch (retCode)
        {
            case money.RET_SUCCESS:
            if (!isIdValid(target))
            {
                String acct = params.getString(money.DICT_ACCT_NAME);
                if ((acct == null) || (acct.equals("")))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    transferBankCreditsToNamedAccount(self, acct, total, money.HANDLER_PAY_PASS, money.HANDLER_PAY_FAIL, params);
                    utils.moneyOutMetric(self, acct, total);
                }
            }
            else 
            {
                transferBankCreditsTo(self, target, total, money.HANDLER_PAY_PASS, money.HANDLER_PAY_FAIL, params);
            }
            return SCRIPT_CONTINUE;
            default:
            if (!isIdValid(target))
            {
                String acct = params.getString(money.DICT_ACCT_NAME);
                if ((acct == null) || (acct.equals("")))
                {
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    prose_package pp = prose.getPackage(money.PROSE_PAY_FAIL, acct, total);
                    sendSystemMessageProse(self, pp);
                    messageTo(self, returnHandler, params, 0, true);
                }
            }
            else 
            {
                String targetName = params.getString(money.DICT_TARGET_NAME);
                if (targetName != null && !targetName.equals(""))
                {
                    prose_package pp = prose.getPackage(money.PROSE_PAY_FAIL, self, null, null, target, targetName, null, null, null, null, total, 0f);
                    sendSystemMessageProse(self, pp);
                }
                else 
                {
                    prose_package pp = prose.getPackage(money.PROSE_PAY_FAIL, self, target, total);
                    sendSystemMessageProse(self, pp);
                }
                messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
            }
            return SCRIPT_CONTINUE;
        }
    }
    public int handlePayPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        money.decrementPayTally(self, params);
        int retCode = money.getReturnCode(params);
        switch (retCode)
        {
            case -1:
            params.put(money.DICT_CODE, money.RET_SUCCESS);
            break;
            case money.RET_FAIL:
            messageTo(self, money.HANDLER_PAY_FAIL, params, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        int total = params.getInt(money.DICT_TOTAL);
        boolean notify = params.getBoolean(money.DICT_NOTIFY);
        if (notify)
        {
            if (!isIdValid(target))
            {
                String acct = params.getString(money.DICT_ACCT_NAME);
                if ((acct == null) || (acct.equals("")))
                {
                }
                else 
                {
                    string_id sid_acct = new string_id(money.STF_ACCT_N, toLower(acct));
                    if (sid_acct != null)
                    {
                        prose_package ppSidAcct = prose.getPackage(money.PROSE_PAY_ACCT_SUCCESS, sid_acct, total);
                        sendSystemMessageProse(self, ppSidAcct);
                    }
                    else 
                    {
                        prose_package ppAcct = prose.getPackage(money.PROSE_PAY_ACCT_SUCCESS, acct, total);
                        sendSystemMessageProse(self, ppAcct);
                    }
                }
            }
            else 
            {
                String targetName = params.getString(money.DICT_TARGET_NAME);
                if (targetName != null && !targetName.equals(""))
                {
                    prose_package pp = prose.getPackage(money.PROSE_PAY_SUCCESS, self, null, null, target, targetName, null, null, null, null, total, 0f);
                    sendSystemMessageProse(self, pp);
                }
                else 
                {
                    if (exists(target))
                    {
                        prose_package pp = prose.getPackage(money.PROSE_PAY_SUCCESS, self, target, total);
                        sendSystemMessageProse(self, pp);
                    }
                    else 
                    {
                        prose_package pp = prose.getPackage(money.PROSE_PAY_SUCCESS_NO_TARGET, self, target, total);
                        sendSystemMessageProse(self, pp);
                    }
                }
            }
        }
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        int msgPayer = params.getInt(money.DICT_MSG_PAYER);
        if ((target != null) && (target != obj_id.NULL_ID) && (msgPayer == 0))
        {
            messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
        }
        else 
        {
            messageTo(self, returnHandler, params, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePayFail(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        money.decrementPayTally(self, params);
        params.put(money.DICT_CODE, money.RET_FAIL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        if (!isIdValid(target))
        {
            messageTo(self, returnHandler, params, 0, true);
        }
        else 
        {
            messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
        }
        return SCRIPT_CONTINUE;
    }
    public int acctToPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt(money.DICT_AMOUNT);
        if (amt < 1)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        if (!isIdValid(target))
        {
            return SCRIPT_CONTINUE;
        }
        withdrawCashFromBank(target, amt, "withdrawPass", "xferFail", params);
        return SCRIPT_CONTINUE;
    }
    public int withdrawPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        params.put(money.DICT_CODE, money.RET_SUCCESS);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int xferFail(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        params.put(money.DICT_CODE, money.RET_FAIL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        return SCRIPT_CONTINUE;
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
        return SCRIPT_CONTINUE;
    }
    public int handleDepositWithdraw(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "bankMenu");
        if (params == null)
        {
            messageTo(self, money.HANDLER_BANK_UNKNOWN_ERROR, null, 0, true);
            return SCRIPT_CONTINUE;
        }
        int btnPressed = sui.getIntButtonPressed(params);
        switch (btnPressed)
        {
            case sui.BP_CANCEL:
            return SCRIPT_CONTINUE;
        }
        int cashIn = getCashBalance(self);
        int cashOut = sui.getTransferInputFrom(params);
        int bankIn = getBankBalance(self);
        int bankOut = sui.getTransferInputTo(params);
        if ((cashOut < 0) || (bankOut < 0))
        {
            return SCRIPT_OVERRIDE;
        }
        int totalIn = cashIn + bankIn;
        int totalOut = cashOut + bankOut;
        if (totalIn != totalOut)
        {
            return SCRIPT_OVERRIDE;
        }
        int amt = 0;
        dictionary d = new dictionary();
        if (cashIn > cashOut)
        {
            amt = cashIn - cashOut;
            if (!money.deposit(self, amt))
            {
            }
        }
        else if (cashIn < cashOut)
        {
            amt = cashOut - cashIn;
            if (!money.withdraw(self, amt))
            {
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBankSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        money.bankSuccess(self, params);
        return SCRIPT_CONTINUE;
    }
    public int handleBankUnknownError(obj_id self, dictionary params) throws InterruptedException
    {
        money.bankError(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBankWithdrawError(obj_id self, dictionary params) throws InterruptedException
    {
        money.bankWithdrawError(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBankDepositError(obj_id self, dictionary params) throws InterruptedException
    {
        money.bankDepositError(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBankTransferError(obj_id self, dictionary params) throws InterruptedException
    {
        money.bankTransferError(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCashTransferError(obj_id self, dictionary params) throws InterruptedException
    {
        money.cashTransferError(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCovertTransactionPass(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("money", self + " covert transaction succeeded");
        params.put(money.DICT_CODE, money.RET_SUCCESS);
        String returnHandler = params.getString(money.DICT_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int handleCovertTransactionFail(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("money", self + " covert transaction FAILED");
        params.put(money.DICT_CODE, money.RET_FAIL);
        String returnHandler = params.getString(money.DICT_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int handlePaymentRequest(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt(money.DICT_AMOUNT);
        String returnHandler = params.getString(money.DICT_HANDLER);
        boolean notify = params.getBoolean(money.DICT_NOTIFY);
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        if (target == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (target == obj_id.NULL_ID)
        {
            String acct = params.getString(money.DICT_ACCT_NAME);
            if ((acct == null) || (acct.equals("")))
            {
                return SCRIPT_CONTINUE;
            }
            money.pay(self, acct, amt, returnHandler, params, notify);
        }
        else 
        {
            money.pay(self, target, amt, returnHandler, params, notify);
        }
        return SCRIPT_CONTINUE;
    }
}
