package script.item.travel_ticket;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.utils;
import script.library.player_structure;
import script.library.prose;
import script.library.travel;
import script.library.sui;

public class travel_ticket extends script.base_script
{
    public travel_ticket()
    {
    }
    public static final string_id SID_BANNED_TICKET = new string_id("city/city", "banned_services");
    public static final string_id SID_ON_PET_OR_VEHICLE = new string_id("travel/travel", "on_pet_or_vehicle");
    public static final string_id SID_TICKET_INVALID = new string_id("travel/travel", "ticket_invalid");
    public static final string_id SID_NO_SHUTTLE_NEARBY = new string_id("travel/travel", "no_shuttle_nearby");
    public static final string_id SID_NO_SHUTTLE_FOR_LOCATION = new string_id("travel/travel", "no_shuttle_for_location");
    public static final string_id SID_SUI_TICKET_DEPARTURE_POINT = new string_id("travel/travel", "sui_ticket_departure_point");
    public static final string_id SID_SUI_TICKET_ARRIVAL_POINT = new string_id("travel/travel", "sui_ticket_arrival_point");
    public static final string_id SID_SUI_TICKET_INFORMATION = new string_id("travel/travel", "sui_ticket_information");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (isIdValid(getMountId(player)))
            {
                LOG("LOG_CHANNEL", player + " -> TRAVEL_TICKET - OnObjectMenuSelect - USE - You cannot board the shuttle when mounted on a pet/vehicle?");
                sendSystemMessage(player, SID_ON_PET_OR_VEHICLE);
                return SCRIPT_CONTINUE;
            }
            String planet = travel.getTicketDeparturePlanet(self);
            String point = travel.getTicketDeparturePoint(self);
            if (planet == null)
            {
                sendSystemMessage(player, SID_TICKET_INVALID);
                return SCRIPT_CONTINUE;
            }
            if (point == null)
            {
                sendSystemMessage(player, SID_TICKET_INVALID);
                return SCRIPT_CONTINUE;
            }
            obj_id shuttle = travel.getTravelShuttle(player);
            if (shuttle == null)
            {
                LOG("LOG_CHANNEL", player + " ->There is no shuttle nearby.");
                sendSystemMessage(player, SID_NO_SHUTTLE_NEARBY);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                obj_id starport = travel.getStarportFromTerminal(shuttle);
                if (player_structure.isCivic(starport))
                {
                    int city_id = getCityAtLocation(getLocation(starport), 0);
                    if (city.isCityBanned(player, city_id))
                    {
                        sendSystemMessage(player, SID_BANNED_TICKET);
                        return SCRIPT_CONTINUE;
                    }
                }
                if (starport == null)
                {
                    LOG("LOG_CHANNEL", player + " ->There is no shuttle nearby for that ticket's departure location.");
                    sendSystemMessage(player, SID_NO_SHUTTLE_FOR_LOCATION);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String starport_point = travel.getTravelPointName(starport);
                    if (!starport_point.equals(point))
                    {
                        LOG("LOG_CHANNEL", player + " ->There is no shuttle nearby for that ticket's departure location.");
                        sendSystemMessage(player, SID_NO_SHUTTLE_FOR_LOCATION);
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (travel.isTravelBlocked(player, false))
            {
                return SCRIPT_CONTINUE;
            }
            if (travel.isInShuttleBoardingRange(player, shuttle))
            {
                queueCommand(player, (1573732770), shuttle, self.toString(), COMMAND_PRIORITY_DEFAULT);
            }
            else 
            {
                LOG("LOG_CHANNEL", player + " ->There is no shuttle nearby for that ticket's departure location.");
                sendSystemMessage(player, SID_NO_SHUTTLE_FOR_LOCATION);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "travel_departure_planet";
        String departure_planet = travel.getTicketDeparturePlanet(self);
        if (departure_planet == null)
        {
            return SCRIPT_CONTINUE;
        }
        departure_planet = (departure_planet.substring(0, 1)).toUpperCase() + (departure_planet.substring(1)).toLowerCase();
        attribs[idx] = departure_planet;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "travel_departure_point";
        attribs[idx] = travel.getTicketDeparturePoint(self);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "travel_arrival_planet";
        String arrival_planet = travel.getTicketArrivalPlanet(self);
        if (arrival_planet == null)
        {
            return SCRIPT_CONTINUE;
        }
        arrival_planet = (arrival_planet.substring(0, 1)).toUpperCase() + (arrival_planet.substring(1)).toLowerCase();
        attribs[idx] = arrival_planet;
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "travel_arrival_point";
        attribs[idx] = travel.getTicketArrivalPoint(self);
        return SCRIPT_CONTINUE;
    }
}
