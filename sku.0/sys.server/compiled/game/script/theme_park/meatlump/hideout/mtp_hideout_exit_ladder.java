package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.groundquests;

public class mtp_hideout_exit_ladder extends script.base_script
{
    public mtp_hideout_exit_ladder()
    {
    }
    public static final String STF_FILE = "elevator_text";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "mtp_ladder_climb_up"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float range = getDistance(here, term);
        if (range > 6.0)
        {
            sendSystemMessage(player, new string_id(STF_FILE, "too_far"));
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            location entranceLoc = new location(-516.0f, 28.0f, -4448.0f, "corellia", null);
            String entranceTemplate = "object/building/content/meatlump/meatlump_hideout_entrance.iff";
            obj_id entrance = getFirstObjectWithTemplate(entranceLoc, 2.0f, entranceTemplate);
            if (isIdValid(entrance))
            {
                obj_id targetCell = getCellId(entrance, "bunker");
                if (isIdValid(targetCell))
                {
                    playClientEffectObj(player, "clienteffect/elevator_rise.cef", player, null);
                    warpPlayer(player, "corellia", -515.0f, 7.3f, -4422.3f, targetCell, 1.6f, -20.8f, 26.6f, "nullCallback", false);
                }
                else 
                {
                    sendSystemMessage(player, new string_id(STF_FILE, "mtp_no_target_cell"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF_FILE, "mtp_no_entrance"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
