package script.theme_park.newbie_tutorial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class bank extends script.theme_park.newbie_tutorial.tutorial_base
{
    public bank()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String bankName = getStringObjVar(self, "banking_bankid");
        if (!hasObjVar(player, "banking_bankid"))
        {
            joinBank(player, bankName);
        }
        else 
        {
            String joinedBankName = getStringObjVar(player, "banking_bankid");
            if (joinedBankName == null)
            {
                joinBank(player, bankName);
            }
            else if (!joinedBankName.equals(bankName))
            {
                joinBank(player, bankName);
            }
        }
        removeStaticWaypoint(self);
        messageTo(player, "handleBankInfoSpam", null, 1, false);
        return SCRIPT_CONTINUE;
    }
}
