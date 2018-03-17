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
import script.library.cloninglib;

public class terminal_cloning_override extends script.base_script
{
    public terminal_cloning_override()
    {
    }
    public int handleRequestedClone(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int retVal = money.getReturnCode(params);
        obj_id player = params.getObjId(money.DICT_PLAYER_ID);
        if ((player == null) || (player == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        int total = params.getInt(money.DICT_TOTAL);
        if (retVal == money.RET_SUCCESS)
        {
            obj_id structure = getTopMostContainer(self);
            if (isIdValid(structure))
            {
                if (money.bankTo(self, structure, total))
                {
                    cloninglib.setBind(player, structure);
                }
            }
        }
        return SCRIPT_OVERRIDE;
    }
}
