package script.systems.elevator;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;

public class elevator extends script.base_script
{
    public elevator()
    {
    }
    public static final String STF_FILE = "elevator_text";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.ELEVATOR_UP, new string_id(STF_FILE, "up"));
        int mnu2 = mi.addRootMenu(menu_info_types.ELEVATOR_DOWN, new string_id(STF_FILE, "down"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 3.0)
        {
            sendSystemMessage(player, new string_id(STF_FILE, "too_far"));
            return SCRIPT_OVERRIDE;
        }
        if (item == menu_info_types.ELEVATOR_UP)
        {
            playClientEffectObj(player, "clienteffect/elevator_rise.cef", player, null);
            if (elevatorMove(player, 1) == 0)
            {
                sendSystemMessage(player, new string_id(STF_FILE, "highest_floor"));
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.ELEVATOR_DOWN)
        {
            playClientEffectObj(player, "clienteffect/elevator_descend.cef", player, null);
            if (elevatorMove(player, -1) == 0)
            {
                sendSystemMessage(player, new string_id(STF_FILE, "lowest_floor"));
                return SCRIPT_OVERRIDE;
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
