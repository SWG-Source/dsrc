package script.faction_perk.hq;

import script.dictionary;
import script.library.money;
import script.obj_id;

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
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int total = params.getInt(money.DICT_TOTAL);
        if (money.getReturnCode(params) == money.RET_SUCCESS)
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
