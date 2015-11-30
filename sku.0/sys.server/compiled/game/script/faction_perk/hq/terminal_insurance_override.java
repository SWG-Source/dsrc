package script.faction_perk.hq;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.pclib;
import script.library.money;

public class terminal_insurance_override extends script.base_script
{
    public terminal_insurance_override()
    {
    }
    public int handleRequestedInsurance(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int retVal = money.getReturnCode(params);
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int total = params.getInt(money.DICT_TOTAL);
        if (retVal == money.RET_SUCCESS)
        {
            obj_id structure = getTopMostContainer(self);
            if (isIdValid(structure))
            {
                money.bankTo(self, structure, total);
            }
        }
        messageTo(player, "handleFinalizeBatchInsure", params, 0, true);
        return SCRIPT_OVERRIDE;
    }
}
