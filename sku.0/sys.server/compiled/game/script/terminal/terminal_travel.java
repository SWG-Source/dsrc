package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.travel;
import script.library.utils;
import script.library.player_structure;
import script.library.city;

public class terminal_travel extends script.base_script
{
    public terminal_travel()
    {
    }
    public static final string_id SID_TRAVEL_OPTIONS = new string_id("travel", "purchase_ticket");
    public static final string_id SID_BANNED_TICKET = new string_id("city/city", "banned_services");
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        obj_id starport = travel.getStarportFromTerminal(self);
        LOG("LOG_CHANNEL", "terminal_travel::OnUnloadedFromMemory -- " + self + " from starport " + starport);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data data = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (data != null)
        {
            data.setServerNotify(true);
        }
        else 
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_TRAVEL_OPTIONS);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            String planet = getCurrentSceneName();
            obj_id starport = travel.getStarportFromTerminal(self);
            String travel_point = travel.getTravelPointName(starport);
            if (player_structure.isCivic(starport))
            {
                int city_id = getCityAtLocation(getLocation(starport), 0);
                if (city.isCityBanned(player, city_id))
                {
                    sendSystemMessage(player, SID_BANNED_TICKET);
                    return SCRIPT_CONTINUE;
                }
            }
            LOG("LOG_CHANNEL", "player ->" + player + " planet ->" + planet + " travel_point ->" + travel_point);
            String config = getConfigSetting("GameServer", "disableTravelSystem");
            if (config != null)
            {
                if (config.equals("on"))
                {
                    return SCRIPT_CONTINUE;
                }
            }
            utils.setScriptVar(player, travel.SCRIPT_VAR_TERMINAL, self);
            enterClientTicketPurchaseMode(player, planet, travel_point, false);
        }
        return SCRIPT_CONTINUE;
    }
}
