package script.event.aprilfools;

import script.library.utils;
import script.obj_id;
import script.string_id;

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
        for (obj_id anAttackerList : attackerList) {
            if (utils.getDistance2D(self, anAttackerList) < 200.0) {
                if (!hasCompletedCollectionSlot(anAttackerList, "toy_recaller")) {
                    modifyCollectionSlotValue(anAttackerList, "toy_recaller", 1);
                    sendSystemMessage(anAttackerList, new string_id("spam", "you_got_april_fools_09_title"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
