package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class travel_bag extends script.base_script
{
    public travel_bag()
    {
    }
    public static final String DATATABLE_START_LOCATIONS = "datatables/creation/starting_locations.iff";
    public static final String SCRIPT_TRAVEL_BAG = "item.container.travel_bag";
    public static final string_id SID_TRAVEL_TATOOINE = new string_id("travel", "travel_tatooine");
    public static final string_id SID_TRAVEL_NABOO = new string_id("travel", "travel_naboo");
    public static final string_id SID_TRAVEL_CORELLIA = new string_id("travel", "travel_corellia");
    public static final string_id SID_TRAVEL_LOK = new string_id("travel", "travel_lok");
    public static final string_id SID_TRAVEL_DANTOOINE = new string_id("travel", "travel_dantooine");
    public static final string_id SID_TRAVEL_TALUS = new string_id("travel", "travel_talus");
    public static final string_id SID_TRAVEL_RORI = new string_id("travel", "travel_rori");
    public static final string_id SID_NO_LOCATION_FOUND = new string_id("travel", "no_location_found");
    public static final string_id SID_ALREADY_THERE = new string_id("travel", "already_there");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int root_menu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_TRAVEL_TATOOINE);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU2, SID_TRAVEL_NABOO);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU3, SID_TRAVEL_CORELLIA);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU4, SID_TRAVEL_LOK);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU5, SID_TRAVEL_DANTOOINE);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU6, SID_TRAVEL_TALUS);
        mi.addSubMenu(root_menu, menu_info_types.SERVER_MENU7, SID_TRAVEL_RORI);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        String travel_point;
        if (item == menu_info_types.SERVER_MENU1)
        {
            travel_point = "mos_eisley";
        }
        else if (item == menu_info_types.SERVER_MENU2)
        {
            travel_point = "theed";
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            travel_point = "coronet";
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            travel_point = "lok";
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            travel_point = "dantooine";
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            travel_point = "dearic";
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            travel_point = "narmle";
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
        int idx = dataTableSearchColumnForString(travel_point, "location", DATATABLE_START_LOCATIONS);
        float x = 0.0f;
        float z = 0.0f;
        String planet = "none";
        if (idx > -1)
        {
            dictionary row = dataTableGetRow(DATATABLE_START_LOCATIONS, idx);
            x = row.getFloat("x");
            z = row.getFloat("z");
            planet = row.getString("planet");
        }
        else 
        {
            if (travel_point.equals("lok"))
            {
                planet = "lok";
                x = 108;
                z = 320;
            }
            else if (travel_point.equals("dantooine"))
            {
                planet = "dantooine";
                x = -617;
                z = 2478;
            }
            else 
            {
                sendSystemMessage(player, SID_NO_LOCATION_FOUND);
                return SCRIPT_CONTINUE;
            }
        }
        String current_planet = getCurrentSceneName();
        if (current_planet.equals(planet))
        {
            sendSystemMessage(player, SID_ALREADY_THERE);
            return SCRIPT_CONTINUE;
        }
        detachScript(self, SCRIPT_TRAVEL_BAG);
        warpPlayer(player, planet, x, 0.0f, z, null, 0.0f, 0.0f, 0.0f);
        return SCRIPT_CONTINUE;
    }
}
