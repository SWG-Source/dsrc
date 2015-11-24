package script.money.pay;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.money;
import script.library.utils;
import script.library.prose;

public class actor extends script.base_script
{
    public actor()
    {
    }
    public static final String HANDLER_DETACH_ME = "handleDetachMe";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, money.SCRIPT_PAY_ACTOR);
        return SCRIPT_CONTINUE;
    }
    public int handleDetachMe(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, money.SCRIPT_PAY_ACTOR);
        return SCRIPT_CONTINUE;
    }
    public int handlePayDeposit(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        int total = params.getInt(money.DICT_TOTAL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        if (total < 1)
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        int retCode = money.getReturnCode(params);
        switch (retCode)
        {
            case money.RET_SUCCESS:
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                String acct = params.getString(money.DICT_ACCT_NAME);
                if ((acct == null) || (acct.equals("")))
                {
                    messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
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
            if ((target == null) || (target == obj_id.NULL_ID))
            {
                String acct = params.getString(money.DICT_ACCT_NAME);
                if ((acct == null) || (acct.equals("")))
                {
                    messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
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
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
    }
    public int handlePayPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
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
            if ((target == null) || (target == obj_id.NULL_ID))
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
                    prose_package pp = prose.getPackage(money.PROSE_PAY_SUCCESS, self, target, total);
                    sendSystemMessageProse(self, pp);
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
        messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
    public int handlePayFail(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        params.put(money.DICT_CODE, money.RET_FAIL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        if ((target == null) || (target == obj_id.NULL_ID))
        {
            messageTo(self, returnHandler, params, 0, true);
        }
        else 
        {
            messageTo(target, returnHandler, params, 0, isObjectPersisted(target));
        }
        messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
}
