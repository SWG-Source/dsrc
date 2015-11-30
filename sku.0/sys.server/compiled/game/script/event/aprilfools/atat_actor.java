package script.event.aprilfools;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class atat_actor extends script.base_script
{
    public atat_actor()
    {
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id[] attackerList = utils.getObjIdBatchScriptVar(self, "creditForKills.attackerList.attackers");
        if (attackerList == null || attackerList.length <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < attackerList.length; ++i)
        {
            if (utils.getDistance2D(self, attackerList[i]) < 200.0)
            {
                if (!hasCompletedCollectionSlot(attackerList[i], "toy_recaller"))
                {
                    modifyCollectionSlotValue(attackerList[i], "toy_recaller", 1);
                    sendSystemMessage(attackerList[i], new string_id("spam", "you_got_april_fools_09_title"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
