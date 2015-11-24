package script.theme_park.meatlump.hideout;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.locations;
import script.library.utils;

public class mtp_hideout_entrance_ladder extends script.base_script
{
    public mtp_hideout_entrance_ladder()
    {
    }
    public static final String STF_FILE = "elevator_text";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "mtp_ladder_climb_down"));
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
            if (groundquests.hasCompletedQuest(player, "mtp_hideout_access_07") || groundquests.hasCompletedQuest(player, "mtp_hideout_access_high_07") || isGod(player))
            {
                if (utils.hasScriptVar(player, "being_scanned"))
                {
                }
                location hideoutLoc = new location(-516.0f, -10.0f, -4448.0f, "corellia", null);
                String hideoutTemplate = "object/building/content/meatlump/meatlump_hideout_main.iff";
                obj_id hideout = getFirstObjectWithTemplate(hideoutLoc, 4.0f, hideoutTemplate);
                if (isIdValid(hideout))
                {
                    obj_id targetCell = getCellId(hideout, "rightguardroom");
                    if (isIdValid(targetCell))
                    {
                        playClientEffectObj(player, "clienteffect/elevator_descend.cef", player, null);
                        warpPlayer(player, "corellia", -577.3f, -46.0f, -4337.4f, targetCell, -60.6f, -36.0f, 111.5f, "nullCallback", false);
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id(STF_FILE, "mtp_no_target_cell"));
                    }
                }
                else 
                {
                    sendSystemMessage(player, new string_id(STF_FILE, "mtp_no_hideout"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id(STF_FILE, "mtp_unable_to_descend"));
            }
        }
        return SCRIPT_CONTINUE;
    }
}
