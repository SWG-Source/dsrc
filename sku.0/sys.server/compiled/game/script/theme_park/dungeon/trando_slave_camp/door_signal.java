package script.theme_park.dungeon.trando_slave_camp;

import script.library.groundquests;
import script.obj_id;

public class door_signal extends script.base_script
{
    public door_signal()
    {
    }
    public int OnReceivedItem(obj_id self, obj_id cell, obj_id world, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(getTopMostContainer(self), "toskKilled"))
        {
            if (!groundquests.isQuestActive(player, "ep3_slave_camp_control_room_access"))
            {
                groundquests.grantQuest(player, "ep3_slave_camp_control_room_access");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
