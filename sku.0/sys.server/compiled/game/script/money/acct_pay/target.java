package script.money.acct_pay;

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
    public static final String HANDLER_DETACH_ME = "handleDetachMe";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, money.SCRIPT_ACCT_PAY);
        return SCRIPT_CONTINUE;
    }
    public int handleDetachMe(obj_id self, dictionary params) throws InterruptedException
    {
        detachScript(self, money.SCRIPT_ACCT_PAY);
        return SCRIPT_CONTINUE;
    }
    public int acctToPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        int amt = params.getInt(money.DICT_AMOUNT);
        if (amt < 1)
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        obj_id target = params.getObjId(money.DICT_TARGET_ID);
        if (!isIdValid(target))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        withdrawCashFromBank(target, amt, "withdrawPass", "xferFail", params);
        return SCRIPT_CONTINUE;
    }
    public int withdrawPass(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        params.put(money.DICT_CODE, money.RET_SUCCESS);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
    public int xferFail(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
            return SCRIPT_CONTINUE;
        }
        params.put(money.DICT_CODE, money.RET_FAIL);
        String returnHandler = params.getString(money.DICT_PAY_HANDLER);
        messageTo(self, returnHandler, params, 0, true);
        messageTo(self, HANDLER_DETACH_ME, null, 0, isObjectPersisted(self));
        return SCRIPT_CONTINUE;
    }
}
