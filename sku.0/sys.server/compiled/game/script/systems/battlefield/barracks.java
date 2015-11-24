package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class barracks extends script.base_script
{
    public barracks()
    {
    }
    public static final string_id SID_BUILD_REINFORCEMENT = new string_id("battlefield", "build_reinforcement");
    public static final string_id SID_BUILD_REINFORCEMENT_STATUS = new string_id("battlefield", "build_reinforcement_status");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (battlefield.isSameBattlefieldFaction(player, self))
        {
            if (battlefield.canBuildReinforcement(self))
            {
                int battlefield_root = mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_BUILD_REINFORCEMENT);
            }
            int status_root = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_BUILD_REINFORCEMENT_STATUS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU2)
        {
            LOG("LOG_CHANNEL", "battlefield_barracks::OnPurchaseReinforcement");
            queueCommand(player, (-983635556), null, "", COMMAND_PRIORITY_DEFAULT);
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            String reinforce_name = getStringObjVar(self, battlefield.VAR_REINFORCEMENT_NAME);
            float cost = getFloatObjVar(self, battlefield.VAR_REINFORCEMENT_COST);
            Vector dsrc = new Vector();
            dsrc.setSize(0);
            dsrc = utils.addElement(dsrc, "Reinforcement Produced: " + reinforce_name);
            dsrc = utils.addElement(dsrc, "Faction Point Cost: " + cost);
            dsrc = utils.addElement(dsrc, "");
            if (!battlefield.canBuildReinforcement(self))
            {
                int time = getGameTime();
                int build_rate = getIntObjVar(self, battlefield.VAR_BUILD_RATE);
                int last_build = 0;
                if (hasObjVar(self, battlefield.VAR_LAST_BUILD))
                {
                    last_build = getIntObjVar(self, battlefield.VAR_LAST_BUILD);
                }
                int time_remaining = last_build + build_rate - time;
                if (time_remaining > 0)
                {
                    int[] conv_time = player_structure.convertSecondsTime(time_remaining);
                    String time_str = player_structure.assembleTimeRemaining(conv_time);
                    dsrc = utils.addElement(dsrc, "Time until next reinforcement: " + time_str);
                }
            }
            else 
            {
                dsrc = utils.addElement(dsrc, "Ready to issue more reinforcements.");
            }
            sui.listbox(player, "Reinforcement Status", "Reinforcement Status", dsrc);
        }
        return SCRIPT_CONTINUE;
    }
}
