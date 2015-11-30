package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.instance;
import script.library.utils;

public class mtp_instance_exit extends script.base_script
{
    public mtp_instance_exit()
    {
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (getDistance(player, self) > 6.0f)
        {
            return SCRIPT_CONTINUE;
        }
        item.addRootMenu(menu_info_types.ITEM_USE, new string_id("building_name", "mtp_hideout_instance_exit"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (groundquests.isTaskActive(player, "mtp_hideout_instance_kill_all_droids", "mtp_quest_timer"))
            {
                groundquests.completeTask(player, "mtp_hideout_instance_kill_all_droids", "mtp_quest_timer");
            }
            if (groundquests.isTaskActive(player, "mtp_hideout_instance_kill_specific_droids", "mtp_quest_timer"))
            {
                groundquests.completeTask(player, "mtp_hideout_instance_kill_specific_droids", "mtp_quest_timer");
            }
            if (groundquests.isTaskActive(player, "mtp_hideout_instance_recover_supplies", "mtp_quest_timer"))
            {
                groundquests.completeTask(player, "mtp_hideout_instance_recover_supplies", "mtp_quest_timer");
            }
            if (groundquests.isTaskActive(player, "mtp_hideout_instance_escort_trapped_meatlump", "mtp_quest_timer"))
            {
                groundquests.completeTask(player, "mtp_hideout_instance_escort_trapped_meatlump", "mtp_quest_timer");
            }
            if (hasScript(player, "theme_park.meatlump.hideout.mtp_instance_player"))
            {
                detachScript(player, "theme_park.meatlump.hideout.mtp_instance_player");
            }
            instance.closeOwnedInstance(player, "mtp_hideout_instance");
            obj_id targetCell = utils.getObjIdScriptVar(player, "mtp_hideoutCell");
            utils.removeScriptVar(player, "mtp_hideoutCell");
            if (isIdValid(targetCell))
            {
                warpPlayer(player, "corellia", -473.0f, -70.0f, -4292.0f, targetCell, 43.7f, -60.0f, 156.9f, "nullCallback", false);
            }
            else 
            {
                instance.requestExitPlayer("mtp_hideout_instance", player);
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
