package script.theme_park.imperial;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.locations;
import script.library.groundquests;

public class itp_emperor_merc_elevator_down extends script.base_script
{
    public itp_emperor_merc_elevator_down()
    {
    }
    public static final String STF_FILE = "elevator_text";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "down"));
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
        if (item == menu_info_types.ITEM_USE)
        {
            playClientEffectObj(player, "clienteffect/elevator_descend.cef", player, null);
            if (groundquests.isQuestActiveOrComplete(player, "itp_emperor_03"))
            {
                obj_id bunker = getTopMostContainer(self);
                if (isIdValid(bunker))
                {
                    obj_id targetCell = getCellId(bunker, "elevator2");
                    warpPlayer(player, "endor", 3562.9f, 251.3f, 7441.3f, targetCell, 73.7f, -50.0f, 59.2f, "nullCallback", false);
                }
            }
            else 
            {
                if (elevatorMove(player, -1) == 0)
                {
                    sendSystemMessage(player, new string_id(STF_FILE, "lowest_floor"));
                    return SCRIPT_OVERRIDE;
                }
            }
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
