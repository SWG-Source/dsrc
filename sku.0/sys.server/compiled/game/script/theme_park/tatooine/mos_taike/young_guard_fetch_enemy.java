package script.theme_park.tatooine.mos_taike;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class young_guard_fetch_enemy extends script.base_script
{
    public young_guard_fetch_enemy()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        String datatable = "datatables/theme_park/mos_taike_young_guard.iff";
        obj_id player = getObjIdObjVar(self, "player");
        int questNum = getIntObjVar(player, "mos_taike.young_guard_quest");
        String reward = dataTableGetString(datatable, 3, questNum - 1);
        if (reward.equals("credits"))
        {
            setObjVar(player, "mos_taike.young_guard.questDone", 1);
            messageTo(player, "finishQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            obj_id playerInv = utils.getInventoryContainer(player);
            createObject(reward, playerInv, "");
            messageTo(player, "finishQuest", null, 0, true);
            return SCRIPT_CONTINUE;
        }
    }
}
