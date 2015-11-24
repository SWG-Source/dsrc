package script.working.cthurow;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class moneytest extends script.base_script
{
    public moneytest()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "money test script attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        dictionary d = new dictionary();
        if (text.equals("from_named"))
        {
            boolean boolReturn = transferBankCreditsFromNamedAccount("slush_fund", self, 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("to_named"))
        {
            boolean boolReturn = transferBankCreditsToNamedAccount(self, "slush_fund", 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("cash_to_object"))
        {
            boolean boolReturn = transferCashTo(self, obj_id.getObjId(200025), 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("bank_to_object"))
        {
            boolean boolReturn = transferBankCreditsTo(self, obj_id.getObjId(200025), 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("withdraw"))
        {
            boolean boolReturn = withdrawCashFromBank(self, 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("deposit"))
        {
            boolean boolReturn = depositCashToBank(self, 1000, null, null, null);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("from_named_callback"))
        {
            boolean boolReturn = transferBankCreditsFromNamedAccount("slush_fund", self, 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("to_named_callback"))
        {
            boolean boolReturn = transferBankCreditsToNamedAccount(self, "slush_fund", 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("cash_to_object_callback"))
        {
            boolean boolReturn = transferCashTo(self, obj_id.getObjId(200025), 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("bank_to_object_callback"))
        {
            boolean boolReturn = transferBankCreditsTo(self, obj_id.getObjId(200025), 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("withdraw_callback"))
        {
            boolean boolReturn = withdrawCashFromBank(self, 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        if (text.equals("deposit_callback"))
        {
            boolean boolReturn = depositCashToBank(self, 1000, "success", "fail", d);
            if (boolReturn)
            {
                debugSpeakMsg(self, "returned true.");
            }
            else 
            {
                debugSpeakMsg(self, "returned false.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int success(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Got success message.");
        return SCRIPT_CONTINUE;
    }
    public int fail(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "Got fail message.");
        return SCRIPT_CONTINUE;
    }
}
