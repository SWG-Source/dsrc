package script.player;

import script.*;
import script.library.*;

import java.util.Vector;

public class player_travel extends script.base_script
{
    public player_travel()
    {
    }
    public static final string_id SID_CANT_BOARD = new string_id("city/city", "city_cant_board");
    public static final string_id SID_CANT_BUY_TICKET = new string_id("city/city", "city_cant_purchase_ticket");
    public static final string_id SID_CITY_TICKET = new string_id("city/city", "city_ticket_pay");
    public static final string_id SID_NO_COLLECTOR = new string_id("dungeon/space_dungeon", "no_collector");
    public static final string_id SID_BANNED_FROM_THAT_CITY = new string_id("city/city", "banned_from_that_city");
    public static final string_id SID_INSTANT_GO_MOUNT_NO = new string_id("travel/travel", "on_pet_or_vehicle_instant_go");
    public static final string_id SID_INSTANT_MOUNT_NO = new string_id("travel/travel", "on_pet_or_vehicle_instant");
    public static final string_id SID_SUI_SELECT_DESTINATION_TITLE = new string_id("travel/travel", "sui_select_destination_title");
    public static final string_id SID_SUI_SELECT_DESTINATION_HEADER = new string_id("travel/travel", "sui_select_destination_header");
    public static final string_id SID_SUI_SELECT_DESTINATION_LOC = new string_id("travel/travel", "sui_select_destination_loc");
    public static final string_id SID_CALLING_FOR_PICKUP = new string_id("travel", "calling_for_pickup");
    public static final string_id SID_LOCATION_NOGOOD_FOR_PICKUP = new string_id("travel", "no_pickup_location");
    public static final string_id SID_LOCATION_INDOORS = new string_id("travel", "no_pickup_indoors");
    public static final string_id SID_ALREADY_OUT = new string_id("travel", "pickup_craft_already_out");
    public static final string_id SID_NO_PICKUP_IN_TOWN = new string_id("travel", "no_pickup_in_town");
    public static final string_id SID_INVALID_PICKUP_LOC = new string_id("travel", "invalid_pickup_loc");
    public static final string_id SID_IN_COMBAT = new string_id("travel", "in_combat");
    public static final string_id SID_NO_PICKUP_IN_PVP = new string_id("travel", "no_pickup_in_pvp");
    public static final string_id SID_PICKUP_CANCEL = new string_id("travel", "pickup_cancel");
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public static final String ITV_PICKUP_BUFF = "call_for_pickup";
    public static final String STF_FILE = "travel";
    public static final String SCRIPTVAR_ACCEPT_GCW_SURCHARGE_ACCEPTED = "travel.accept_gcw_surcharge_accepted";
    public static final String SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED = "travel.accept_gcw_skip_shuttle_wait_surcharge_accepted";
    public static final String SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID = "travel.accept_gcw_surcharge_sui_id";
    public static final String SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID = "travel.accept_gcw_surcharge_ticket_id";
    public static final String SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID = "travel.accept_gcw_surcharge_shuttle_id";
    public static final int SHIP_TYPE_INSTANT_XWING_TIE = 1;
    public static final int SHIP_TYPE_INSTANT_PRIVATEER = 2;
    public static final int SHIP_TYPE_INSTANT_ROYAL_SHIP = 3;
    public static final int SHIP_TYPE_INSTANT_JALOPY = 4;
    public static final int SHIP_TYPE_TCG_HOME_SHIP = 5;
    public static final int SHIP_TYPE_TCG_LOCATION_SHIP = 6;
    public static final int SHIP_TYPE_SNOWSPEEDER_SHIP = 7;
    public static final int SHIP_TYPE_TCG_SLAVE1_SHIP = 8;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel.OnInitialize");
        if (hasObjVar(self, travel.VAR_TRAVEL))
        {
            removeObjVar(self, travel.VAR_TRAVEL);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPurchaseTicket(obj_id self, obj_id player, String departPlanetName, String departTravelPointName, String arrivePlanetName, String arriveTravelPointName, boolean roundTrip) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::OnPurchaseTicket");
        obj_id terminal = utils.getObjIdScriptVar(player, travel.SCRIPT_VAR_TERMINAL);
        if (isIdValid(terminal))
        {
            float distance = getDistance(self, terminal);
            LOG("LOG_CHANNEL", "distance ->" + distance);
            if (distance > travel.MAXIMUM_TERMINAL_DISTANCE)
            {
                sendSystemMessage(self, new string_id(STF_FILE, "too_far"));
                return SCRIPT_CONTINUE;
            }
        }
        obj_id starport = travel.getStarportFromTerminal(terminal);
        if (arriveTravelPointName.contains("gcwstaticbase"))
        {
            if ((arriveTravelPointName.contains("rebel")) && !factions.isRebel(self))
            {
                sendSystemMessage(self, new string_id("gcw", "static_base_shuttle_beacon_cant_not_rebel"));
                return SCRIPT_CONTINUE;
            }
            else if ((arriveTravelPointName.contains("imperial")) && !factions.isImperial(self))
            {
                sendSystemMessage(self, new string_id("gcw", "static_base_shuttle_beacon_cant_not_imperial"));
                return SCRIPT_CONTINUE;
            }
        }
        int city_id = getCityAtLocation(getLocation(starport), 0);
        if (city.isCityBanned(self, city_id))
        {
            sendSystemMessage(self, SID_CANT_BUY_TICKET);
            return SCRIPT_CONTINUE;
        }
        if (!departPlanetName.equals(arrivePlanetName))
        {
            final String departPlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, departPlanetName);
            if ((departPlanetAvailableStarport != null) && (departPlanetAvailableStarport.length() > 0) && (!departPlanetAvailableStarport.equals(departTravelPointName)))
            {
                sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the departure planet, the only starport currently available for interplanetary travel on the departure planet is " + departPlanetAvailableStarport + ".", "");
                return SCRIPT_CONTINUE;
            }
            final String arrivePlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, arrivePlanetName);
            if ((arrivePlanetAvailableStarport != null) && (arrivePlanetAvailableStarport.length() > 0) && (!arrivePlanetAvailableStarport.equals(arriveTravelPointName)))
            {
                sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the arrival planet, the only starport currently available for interplanetary travel on the arrival planet is " + arrivePlanetAvailableStarport + ".", "");
                return SCRIPT_CONTINUE;
            }
        }
        travel.purchaseTicket(self, departPlanetName, departTravelPointName, arrivePlanetName, arriveTravelPointName, roundTrip, starport);
        return SCRIPT_CONTINUE;
    }
    public int OnPurchaseTicketInstantTravel(obj_id self, obj_id player, String departPlanetName, String departTravelPointName, String arrivePlanetName, String arriveTravelPointName, boolean roundTrip) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::OnPurchaseTicketInstantTravel");
        if (!utils.hasScriptVar(player, "instantTravel"))
        {
            return SCRIPT_CONTINUE;
        }
        else
        {
            utils.removeScriptVar(player, "instantTravel");
        }
        obj_id terminal = utils.getObjIdScriptVar(player, travel.SCRIPT_VAR_TERMINAL);
        if (isIdValid(terminal))
        {
            float distance = getDistance(self, terminal);
            LOG("LOG_CHANNEL", "distance ->" + distance);
            if (distance > travel.MAXIMUM_TERMINAL_DISTANCE || !exists(terminal))
            {
                sendSystemMessage(self, new string_id(STF_FILE, "too_far"));
                return SCRIPT_CONTINUE;
            }
        }
        obj_id playerCurrentMount = getMountId(player);
        if (isIdValid(playerCurrentMount))
        {
            sendSystemMessage(player, SID_INSTANT_GO_MOUNT_NO);
            return SCRIPT_OVERRIDE;
        }
        obj_id starport = travel.getStarportFromTerminal(terminal);
        int city_id = getCityAtLocation(getLocation(starport), 0);
        if (city.isCityBanned(self, city_id))
        {
            sendSystemMessage(self, SID_CANT_BUY_TICKET);
            return SCRIPT_CONTINUE;
        }
        travel.instantTravel(self, departPlanetName, departTravelPointName, arrivePlanetName, arriveTravelPointName, roundTrip, starport);
        if (buff.hasBuff(self, ITV_PICKUP_BUFF))
        {
            sendSystemMessage(self, SID_PICKUP_CANCEL);
            buff.removeBuff(self, ITV_PICKUP_BUFF);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToTravelToGroupPickupPoint(obj_id self) throws InterruptedException
    {
        if (callable.hasAnyCallable(self))
        {
            sendSystemMessage(self, new string_id("beast", "beast_cant_travel"));
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTravelToGroupPickupPoint(obj_id self, String planetName, String travelPointName) throws InterruptedException
    {
        travel.movePlayerToDestination(self, planetName, travelPointName, true);
        return SCRIPT_CONTINUE;
    }
    public int msgTicketPaymentFail(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", self + " ->You do not have enough money to complete the ticket purchase.");
        sendSystemMessage(self, new string_id(STF_FILE, "short_funds"));
        return SCRIPT_CONTINUE;
    }
    public int msgUseTravelCouponSui(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "travel_voucher.travelVoucherSui"))
        {
            utils.removeScriptVar(self, "travel_voucher.travelVoucherSui");
        }
        dictionary webster = utils.getDictionaryScriptVar(self, "travelTicketDict");
        obj_id starport = webster.getObjId("starport");
        int total_cost = webster.getInt("amount");
        int city_fee = webster.getInt("city_fee");
        obj_id travel_coupon = webster.getObjId("travel_coupon");
        boolean hasEnoughMoney = webster.getBoolean("hasEnoughMoney");
        obj_id terminal = utils.getObjIdScriptVar(self, travel.SCRIPT_VAR_TERMINAL);
        if (isIdValid(terminal))
        {
            float distance = getDistance(self, terminal);
            if (distance > travel.MAXIMUM_TERMINAL_DISTANCE)
            {
                sendSystemMessage(self, new string_id(STF_FILE, "too_far"));
                return SCRIPT_CONTINUE;
            }
        }
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_OK:
            if (isIdValid(travel_coupon))
            {
                destroyObject(travel_coupon);
                utils.setScriptVar(self, travel.VAR_PURCHASING_TICKET, 1);
                String custLogMsg = "New Player Rewards: %TU sucessfully used a travel voucher.";
                CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, self);
                messageTo(self, "msgTicketPaymentComplete", webster, 1, false);
            }
            else
            {
                sendSystemMessage(self, new string_id("new_player", "travel_coupon_invaild"));
            }
            break;
            case sui.BP_CANCEL:
            if (hasEnoughMoney)
            {
                if (money.pay(self, money.ACCT_TRAVEL, total_cost, "msgTicketPaymentComplete", webster, true))
                {
                    params.put("amount", city_fee);
                    if ((city_fee > 0) && !money.pay(self, city.getCityHall(starport), city_fee, "msgCityTicketPaymentComplete", webster, true))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    LOG("LOG_CHANNEL", "Deducting " + total_cost);
                    utils.setScriptVar(self, travel.VAR_PURCHASING_TICKET, 1);
                }
            }
            else
            {
                sendSystemMessage(self, new string_id("new_player", "travel_coupon_cancelled"));
            }
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int msgTicketPaymentComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::msgTicketPaymentComplete");
        if (utils.hasScriptVar(self, travel.VAR_PURCHASING_TICKET))
        {
            utils.removeScriptVar(self, travel.VAR_PURCHASING_TICKET);
        }
        String planet1 = params.getString("planet1");
        String point1 = params.getString("point1");
        String planet2 = params.getString("planet2");
        String point2 = params.getString("point2");
        boolean roundtrip = params.getBoolean("roundtrip");
        obj_id[] tickets = travel.createTicket(self, planet1, point1, planet2, point2, roundtrip);
        LOG("LOG_CHANNEL", self + " ->Ticket purchase complete.");
        sui.msgbox(self, new string_id(STF_FILE, "ticket_purchase_complete"));
        return SCRIPT_CONTINUE;
    }
    public int msgTravelComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::msgTravelComplete");
        String departure_planet;
        String arrival_planet;
        String departure_point;
        String arrival_point;
        if (utils.hasScriptVar(self, travel.VAR_DEPARTURE_PLANET))
        {
            departure_planet = utils.getStringScriptVar(self, travel.VAR_DEPARTURE_PLANET);
            utils.removeScriptVar(self, travel.VAR_DEPARTURE_PLANET);
        }
        else
        {
            CustomerServiceLog("travel", getFirstName(self) + " has an invalid departure planet. Unable to check for travel success.");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, travel.VAR_DEPARTURE_POINT))
        {
            departure_point = utils.getStringScriptVar(self, travel.VAR_DEPARTURE_POINT);
            utils.removeScriptVar(self, travel.VAR_DEPARTURE_POINT);
        }
        else
        {
            CustomerServiceLog("travel", getFirstName(self) + " has an invalid departure point. Unable to check for travel success.");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, travel.VAR_ARRIVAL_PLANET))
        {
            arrival_planet = utils.getStringScriptVar(self, travel.VAR_ARRIVAL_PLANET);
            utils.removeScriptVar(self, travel.VAR_ARRIVAL_PLANET);
        }
        else
        {
            CustomerServiceLog("travel", getFirstName(self) + " has an invalid arrival planet. Unable to check for travel success.");
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, travel.VAR_ARRIVAL_POINT))
        {
            arrival_point = utils.getStringScriptVar(self, travel.VAR_ARRIVAL_POINT);
            utils.removeScriptVar(self, travel.VAR_ARRIVAL_POINT);
        }
        else
        {
            CustomerServiceLog("travel", getFirstName(self) + " has an invalid arrival point. Unable to check for travel success.");
            return SCRIPT_CONTINUE;
        }
        location loc = getPlanetTravelPointLocation(arrival_planet, arrival_point);
        if (loc != null)
        {
            location player_loc = getLocation(self);
            if (loc.area.equals(player_loc.area))
            {
                if (player_loc.distance(loc) < 100)
                {
                    return SCRIPT_CONTINUE;
                }
            }
            if (travel.createTicket(self, departure_planet, departure_point, arrival_planet, arrival_point, false) == null)
            {
                CustomerServiceLog("travel", getFirstName(self) + " arrived at the incorrect travel location. Unable to recreate ticket.");
            }
        }
        else
        {
            CustomerServiceLog("travel", "Unable to find travel location for " + getFirstName(self) + ". Unable to check for travel success.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgBoardShuttle(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + params);
        if (isIdValid(getMountId(self)))
        {
            LOG("LOG_CHANNEL", self + " -> PLAYER_TRAVEL - msghndler msgBoardShuttle - You cannot board the shuttle when mounted on a pet/vehicle?");
            sendSystemMessage(self, new string_id(STF_FILE, "no_pets"));
            if (utils.hasScriptVar(self, "travel.hasTicketList"))
            {
                utils.removeScriptVar(self, "travel.hasTicketList");
            }
            return SCRIPT_CONTINUE;
        }
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            removeObjVar(self, travel.VAR_TRAVEL);
            utils.removeScriptVar(self, "travel.hasTicketList");
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected != -1)
        {
            if (hasObjVar(self, travel.VAR_VALID_TICKETS))
            {
                obj_id[] valid_tickets = getObjIdArrayObjVar(self, travel.VAR_VALID_TICKETS);
                final obj_id shuttle = getObjIdObjVar(self, travel.VAR_BOARDING_SHUTTLE);
                if (row_selected >= valid_tickets.length)
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- Row selected exceeded ticket length.");
                    removeObjVar(self, travel.VAR_TRAVEL);
                    utils.removeScriptVar(self, "travel.hasTicketList");
                    return SCRIPT_CONTINUE;
                }
                obj_id ticket = valid_tickets[row_selected];
                if (!ticket.isLoaded())
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + ticket + " can't be loaded.");
                    removeObjVar(self, travel.VAR_TRAVEL);
                    utils.removeScriptVar(self, "travel.hasTicketList");
                    return SCRIPT_CONTINUE;
                }
                if (!shuttle.isLoaded())
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + shuttle + " can't be loaded.");
                    removeObjVar(self, travel.VAR_TRAVEL);
                    utils.removeScriptVar(self, "travel.hasTicketList");
                    return SCRIPT_CONTINUE;
                }
                removeObjVar(self, travel.VAR_TRAVEL);
                utils.removeScriptVar(self, "travel.hasTicketList");
                if (!travel.isInShuttleBoardingRange(self, shuttle))
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + self + " is too far from shuttle " + shuttle);
                    sendSystemMessage(self, new string_id(STF_FILE, "boarding_too_far"));
                    return SCRIPT_CONTINUE;
                }
                final obj_id starport = travel.getStarportFromTerminal(shuttle);
                if (starport == null)
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- Unable to find starport.");
                    return SCRIPT_CONTINUE;
                }
                boolean qualifiesForGcwTravelPerks = false;
                if (!travel.isShuttleAvailable(starport))
                {
                    qualifiesForGcwTravelPerks = travel.qualifiesForGcwTravelPerks(self);
                    if (!qualifiesForGcwTravelPerks)
                    {
                        LOG("LOG_CHANNEL", self + " ->The shuttle is not available at this time.");
                        sendSystemMessage(self, new string_id(STF_FILE, "shuttle_not_available"));
                        return SCRIPT_CONTINUE;
                    }
                }
                String depart_planet = getCurrentSceneName();
                String depart_point = travel.getTravelPointName(starport);
                if (!travel.isTravelTicketValid(ticket, depart_planet, depart_point))
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + ticket + " is no longer valid.");
                    return SCRIPT_CONTINUE;
                }
                String arrival_planet = travel.getTicketArrivalPlanet(ticket);
                String arrival_point = travel.getTicketArrivalPoint(ticket);
                int city_id = findCityByName(arrival_point);
                if ((city_id != 0) && cityExists(city_id) && city.isCityBanned(self, city_id))
                {
                    sendSystemMessage(self, SID_BANNED_FROM_THAT_CITY);
                    return SCRIPT_CONTINUE;
                }
                LOG("LOG_CHANNEL", "arrival_planet ->" + arrival_planet + " arrival_point ->" + arrival_point);
                if (!travel.isValidRoute(depart_planet, depart_point, arrival_planet, arrival_point))
                {
                    LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- The route is no longer valid for " + ticket);
                    sendSystemMessage(self, new string_id(STF_FILE, "route_not_available"));
                    return SCRIPT_CONTINUE;
                }
                if (callable.hasAnyCallable(self))
                {
                    sendSystemMessage(self, new string_id("beast", "beast_cant_travel"));
                    return SCRIPT_CONTINUE;
                }
                if (!depart_planet.equals(arrival_planet))
                {
                    final String departPlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, depart_planet);
                    if ((departPlanetAvailableStarport != null) && (departPlanetAvailableStarport.length() > 0) && (!departPlanetAvailableStarport.equals(depart_point)))
                    {
                        sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the departure planet, the only starport currently available for interplanetary travel on the departure planet is " + departPlanetAvailableStarport + ".", "");
                        return SCRIPT_CONTINUE;
                    }
                    final String arrivePlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, arrival_planet);
                    if ((arrivePlanetAvailableStarport != null) && (arrivePlanetAvailableStarport.length() > 0) && (!arrivePlanetAvailableStarport.equals(arrival_point)))
                    {
                        sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the arrival planet, the only starport currently available for interplanetary travel on the arrival planet is " + arrivePlanetAvailableStarport + ".", "");
                        return SCRIPT_CONTINUE;
                    }
                }
                if (qualifiesForGcwTravelPerks)
                {
                    sendSystemMessage(self, "Bypassing the shuttle timer because you are a GCW officer and your faction currently controls the planet.", "");
                }
                if (travel.restrictedByGcwTravelRestrictions(self) && utils.hasScriptVar(starport, travel.SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT))
                {
                    int shuttleAvailableCount = utils.getIntScriptVar(starport, travel.SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT);
                    if ((shuttleAvailableCount % 2) == 1)
                    {
                        if (utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
                        {
                            final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                            utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                            forceCloseSUIPage(pid);
                        }
                        final int timeUntilCurrentShuttleDeparts = timeUntilMessageTo(shuttle, "msgShuttleTakeOff");
                        String announcement = "You must wait for the next shuttle because you are a GCW officer and the opposing faction currently controls the planet.\n\nOr you can pay a surcharge of 50000 credits to avoid the wait.\n\nWhat do you want to do?";
                        if (timeUntilCurrentShuttleDeparts >= 0)
                        {
                            announcement += "\n\n(The current shuttle will depart in " + (timeUntilCurrentShuttleDeparts / 60) + "m:" + (timeUntilCurrentShuttleDeparts % 60) + "s.)";
                        }
                        final int pid = sui.msgbox(self, self, announcement, sui.OK_CANCEL, "GCW Travel Restriction", sui.MSG_QUESTION, "handleConfirmGcwTravelRestrictionSkipShuttleWait");
                        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Wait");
                        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Pay");
                        showSUIPage(pid);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID, pid);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID, ticket);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID, shuttle);
                        return SCRIPT_CONTINUE;
                    }
                }
                final int gcwTravelRestrictionsSurcharge = travel.getGcwTravelRestrictionsSurcharge(self, travel.getTicketDeparturePlanet(ticket), arrival_planet);
                if (gcwTravelRestrictionsSurcharge > 0)
                {
                    if (utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
                    {
                        final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                        forceCloseSUIPage(pid);
                    }
                    final int pid = sui.msgbox(self, self, "You must pay a surcharge of " + gcwTravelRestrictionsSurcharge + " credits because you are a GCW officer and the opposing faction currently controls either the departure or the arrival planet.\n\nDo you accept the surcharge?", sui.YES_NO, "GCW Travel Surcharge", sui.MSG_QUESTION, "handleConfirmGcwTravelSurcharge");
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID, pid);
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID, ticket);
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID, shuttle);
                    return SCRIPT_CONTINUE;
                }
                utils.setScriptVar(self, travel.VAR_DEPARTURE_PLANET, travel.getTicketDeparturePlanet(ticket));
                utils.setScriptVar(self, travel.VAR_DEPARTURE_POINT, travel.getTicketDeparturePoint(ticket));
                utils.setScriptVar(self, travel.VAR_ARRIVAL_PLANET, arrival_planet);
                utils.setScriptVar(self, travel.VAR_ARRIVAL_POINT, arrival_point);
                destroyObject(ticket);
                travel.movePlayerToDestination(self, arrival_planet, arrival_point);
            }
        }
        else
        {
            if (hasObjVar(self, travel.VAR_TRAVEL))
            {
                removeObjVar(self, travel.VAR_TRAVEL);
            }
            if (utils.hasScriptVar(self, "travel.hasTicketList"))
            {
                utils.removeScriptVar(self, "travel.hasTicketList");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgTravelToStarport(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::msgTravelToStarport -- " + self + "  " + params);
        location loc = params.getLocation("location");
        warpPlayer(self, loc.area, loc.x, loc.y, loc.z, null, 0.0f, 0.0f, 0.0f);
        return SCRIPT_CONTINUE;
    }
    public int msgSelectDungeonTicket(obj_id self, dictionary params) throws InterruptedException
    {
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel") || button.equals("No"))
        {
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_VALID_TICKETS);
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected != -1)
        {
            obj_id[] valid_tickets = utils.getObjIdArrayScriptVar(self, space_dungeon.SCRIPT_VAR_VALID_TICKETS);
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_VALID_TICKETS);
            if (row_selected > valid_tickets.length)
            {
                LOG("space_dungeon", "player_travel.msgSelectDungeonTicket -- row_selected was greater than the length of valid_tickets.");
                return SCRIPT_CONTINUE;
            }
            obj_id ticket = valid_tickets[row_selected];
            obj_id ticket_collector = space_dungeon.getDungeonTicketCollector(self, space_dungeon.getTicketPointName(ticket));
            if (!isIdValid(ticket_collector))
            {
                sendSystemMessage(self, SID_NO_COLLECTOR);
                return SCRIPT_CONTINUE;
            }
            space_dungeon.activateDungeonTicket(self, ticket, ticket_collector);
        }
        else
        {
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_VALID_TICKETS);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonTravelComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- " + self + " :" + space_dungeon.getDungeonSessionId(self));
        obj_id ticket = getObjIdObjVar(self, space_dungeon.VAR_TICKET_USED);
        if (!isIdValid(ticket))
        {
            LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- the ticket for " + self + " is invalid.");
            CustomerServiceLog("travel", "Unable to claim dungeon ticket for " + getFirstName(self) + "(" + self + ").");
        }
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
        LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- dungeon ->" + dungeon + " (" + getTemplateName(dungeon) + ")");
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- dungeon is invalid, player not in a dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(self);
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = space_dungeon.getDungeonName(dungeon);
        if (dungeon_name == null || dungeon_name.length() < 1)
        {
            LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- dungeon " + dungeon + " has no name. This may not be a dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(self);
            return SCRIPT_CONTINUE;
        }
        String player_script = space_dungeon_data.getDungeonPlayerScript(dungeon_name);
        LOG("space_dungeon", "player_travel.msgDungeonTravelComplete -- player_script :" + player_script);
        if (player_script != null)
        {
            attachScript(self, player_script);
        }
        if (null != ticket)
        {
            String ticket_dungeon_name = space_dungeon.getTicketDungeonName(ticket);
            if (dungeon_name.equals(ticket_dungeon_name))
            {
                if (!ticket.equals(self))
                {
                    LOG("space_dungeon", self + " has reached dungeon " + dungeon + "(" + dungeon_name + ").  Deleting ticket " + ticket);
                    destroyObject(ticket);
                }
            }
        }
        space_dungeon.cleanupPlayerTicketObjvars(self);
        CustomerServiceLog("travel", getFirstName(self) + " (" + self + ") has traveled to dungeon " + dungeon_name);
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonTravelConfirmed(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_PENDING) || !utils.hasScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_ID_PENDING))
        {
            if (hasObjVar(self, space_dungeon.VAR_SESSION_ID))
            {
                removeObjVar(self, space_dungeon.VAR_SESSION_ID);
            }
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = utils.getStringScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_PENDING);
        obj_id dungeon = utils.getObjIdScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_ID_PENDING);
        utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_PENDING);
        utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_ID_PENDING);
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            if (hasObjVar(self, space_dungeon.VAR_SESSION_ID))
            {
                removeObjVar(self, space_dungeon.VAR_SESSION_ID);
            }
            return SCRIPT_CONTINUE;
        }
        location dungeon_position = new location();
        if (utils.hasScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_POSITION))
        {
            dungeon_position = utils.getLocationScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_POSITION);
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_POSITION);
        }
        CustomerServiceLog("travel", getFirstName(self) + " (" + self + ") has initiated travel to dungeon " + dungeon_name);
        space_dungeon.moveSinglePlayerIntoDungeon(self, dungeon, dungeon_name, dungeon_position);
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonGroupTravelComplete(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("space_dungeon", "player_travel.msgDungeonGroupTravelComplete -- " + self + " :" + space_dungeon.getDungeonSessionId(self));
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "player_travel.msgDungeonGroupTravelComplete -- top most container is invalid, player not in a dungeon.");
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = space_dungeon.getDungeonName(dungeon);
        if (dungeon_name == null || dungeon_name.length() < 1)
        {
            LOG("space_dungeon", "player_travel.msgDungeonGroupTravelComplete -- dungeon " + dungeon + " has no name. This may not be a dungeon.");
            return SCRIPT_CONTINUE;
        }
        String player_script = space_dungeon_data.getDungeonPlayerScript(dungeon_name);
        LOG("space_dungeon", "player_travel.msgDungeonGroupTravelComplete -- player_script :" + player_script);
        if (player_script != null)
        {
            attachScript(self, player_script);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgCloseDungeonTravel(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_PENDING) || utils.hasScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_ID_PENDING))
        {
            int pid = params.getInt("pid");
            if (pid > -1)
            {
                sui.closeSUI(self, pid);
            }
            removeObjVar(self, space_dungeon.VAR_SESSION_ID);
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_PENDING);
            utils.removeScriptVar(self, space_dungeon.SCRIPT_VAR_DUNGEON_ID_PENDING);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmGcwTravelSurcharge(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
        final boolean acceptGcwSkipShuttleWaitSurcharge = utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED);
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id ticket = utils.getObjIdScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID);
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id shuttle = utils.getObjIdScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID);
        if (!isIdValid(ticket) || !exists(ticket) || (utils.getContainingPlayer(ticket) != self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(shuttle) || !exists(shuttle))
        {
            return SCRIPT_CONTINUE;
        }
        if (pid != params.getInt("pageId"))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            if (acceptGcwSkipShuttleWaitSurcharge)
            {
                utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED, 1);
            }
            utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_ACCEPTED, 1);
            queueCommand(self, (1573732770), shuttle, ticket.toString(), COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmGcwTravelRestrictionSkipShuttleWait(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id ticket = utils.getObjIdScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID);
        if (!utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID))
        {
            return SCRIPT_CONTINUE;
        }
        final obj_id shuttle = utils.getObjIdScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID);
        if (!isIdValid(ticket) || !exists(ticket) || (utils.getContainingPlayer(ticket) != self))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(shuttle) || !exists(shuttle))
        {
            return SCRIPT_CONTINUE;
        }
        if (pid != params.getInt("pageId"))
        {
            return SCRIPT_CONTINUE;
        }
        final int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED, 1);
            queueCommand(self, (1573732770), shuttle, ticket.toString(), COMMAND_PRIORITY_DEFAULT);
        }
        return SCRIPT_CONTINUE;
    }
    public int boardShuttle(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_travel::boardShuttle");
        final boolean acceptGcwSkipShuttleWaitSurcharge = utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED);
        final boolean acceptGcwSurcharge = utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_ACCEPTED);
        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_ACCEPTED);
        if (isIdValid(getMountId(self)))
        {
            LOG("LOG_CHANNEL", self + " -> PLAYER_TRAVEL - cmdHndlr boardShuttle - You cannot board the shuttle when mounted on a pet/vehicle?");
            sendSystemMessage(self, new string_id(STF_FILE, "no_pets"));
            return SCRIPT_CONTINUE;
        }
        if (travel.isTravelBlocked(self, false))
        {
            return SCRIPT_CONTINUE;
        }
        if (target == null || target == obj_id.NULL_ID)
        {
            target = getLookAtTarget(self);
            if (target == null)
            {
                LOG("LOG_CHANNEL", self + " ->What shuttle do you wish to board?");
                sendSystemMessage(self, new string_id(STF_FILE, "boarding_what_shuttle"));
                return SCRIPT_CONTINUE;
            }
        }
        if (travel.isTravelShuttlePilot(target))
        {
            obj_id starport = travel.getStarportFromTerminal(target);
            obj_id shuttle = travel.getShuttleFromStarport(starport);
            if (shuttle == null || shuttle == obj_id.NULL_ID)
            {
                LOG("LOG_CHANNEL", self + " ->The shuttle is not available at this time.");
                sendSystemMessage(self, new string_id(STF_FILE, "shuttle_not_available"));
                return SCRIPT_CONTINUE;
            }
            else
            {
                target = shuttle;
            }
        }
        else if (!travel.isTravelShuttle(target))
        {
            LOG("LOG_CHANNEL", self + " ->What shuttle do you wish to board?");
            sendSystemMessage(self, new string_id(STF_FILE, "boarding_what_shuttle"));
            return SCRIPT_CONTINUE;
        }
        if (!travel.isInShuttleBoardingRange(self, target))
        {
            LOG("LOG_CHANNEL", "player_travel::msgBoardShuttle -- " + self + " is too far from shuttle " + target);
            sendSystemMessage(self, new string_id(STF_FILE, "boarding_too_far"));
            return SCRIPT_CONTINUE;
        }
        int city_id = getCityAtLocation(getLocation(target), 0);
        if (city.isCityBanned(self, city_id))
        {
            sendSystemMessage(self, SID_CANT_BOARD);
            return SCRIPT_CONTINUE;
        }
        obj_id ticket = obj_id.NULL_ID;
        if (params.length() > 0)
        {
            ticket = utils.stringToObjId(params);
        }
        String planet = getCurrentSceneName();
        final obj_id starport = travel.getStarportFromTerminal(target);
        boolean qualifiesForGcwTravelPerks = false;
        if (!travel.isShuttleAvailable(starport))
        {
            qualifiesForGcwTravelPerks = travel.qualifiesForGcwTravelPerks(self);
            if (!qualifiesForGcwTravelPerks)
            {
                LOG("LOG_CHANNEL", self + " ->The shuttle is not available at this time.");
                sendSystemMessage(self, new string_id(STF_FILE, "shuttle_not_available"));
                return SCRIPT_CONTINUE;
            }
        }
        if (starport == null)
        {
            LOG("LOG_CHANNEL", "player.player_travel::OnBoardShuttle -- Unable to find the starport for " + target);
            return SCRIPT_CONTINUE;
        }
        String depart_point = travel.getTravelPointName(starport);
        if (ticket == null || ticket == obj_id.NULL_ID)
        {
            Vector dsrc = new Vector();
            dsrc.setSize(0);
            Vector valid_tickets = new Vector();
            valid_tickets.setSize(0);
            obj_id inventory = getObjectInSlot(self, "inventory");
            obj_id[] inv_contents = utils.getContents(inventory, false);
            for (obj_id inv_content : inv_contents) {
                if (travel.isTravelTicketValid(inv_content, planet, depart_point)) {
                    String ticket_planet = travel.getTicketArrivalPlanet(inv_content);
                    String ticket_point = travel.getTicketArrivalPoint(inv_content);
                    if (ticket_planet.equals("kashyyyk_main")) {
                        ticket_planet = "kashyyyk";
                    }
                    ticket_planet = (ticket_planet.substring(0, 1)).toUpperCase() + (ticket_planet.substring(1)).toLowerCase();
                    prose_package ppLoc = prose.getPackage(SID_SUI_SELECT_DESTINATION_LOC);
                    prose.setTT(ppLoc, ticket_planet);
                    prose.setTO(ppLoc, ticket_point);
                    String loc = " \0" + packOutOfBandProsePackage(null, ppLoc);
                    dsrc = utils.addElement(dsrc, loc);
                    valid_tickets = utils.addElement(valid_tickets, inv_content);
                }
            }
            if (dsrc.size() < 1)
            {
                LOG("LOG_CHANNEL", self + " ->You do not have a ticket to board this shuttle.");
                sendSystemMessage(self, new string_id(STF_FILE, "no_ticket"));
                return SCRIPT_CONTINUE;
            }
            else
            {
                if (!utils.hasScriptVar(self, "travel.hasTicketList"))
                {
                    setObjVar(self, travel.VAR_VALID_TICKETS, valid_tickets);
                    setObjVar(self, travel.VAR_BOARDING_SHUTTLE, target);
                    utils.setScriptVar(self, "travel.hasTicketList", 1);
                    if (qualifiesForGcwTravelPerks)
                    {
                        sendSystemMessage(self, "Bypassing the shuttle timer because you are a GCW officer and your faction currently controls the planet.", "");
                    }
                    sui.listbox(self, self, utils.packStringId(SID_SUI_SELECT_DESTINATION_TITLE), sui.OK_CANCEL, utils.packStringId(SID_SUI_SELECT_DESTINATION_HEADER), dsrc, "msgBoardShuttle");
                }
                else
                {
                    sendSystemMessage(self, new string_id(STF_FILE, "boarding_ticket_selection"));
                }
                return SCRIPT_CONTINUE;
            }
        }
        else
        {
            if (!travel.isTravelTicketValid(ticket, planet, depart_point))
            {
                LOG("LOG_CHANNEL", self + " ->This ticket is not valid for the given shuttle.");
                sendSystemMessage(self, new string_id(STF_FILE, "wrong_shuttle"));
                return SCRIPT_CONTINUE;
            }
            String arrival_planet = travel.getTicketArrivalPlanet(ticket);
            String arrival_point = travel.getTicketArrivalPoint(ticket);
            city_id = findCityByName(arrival_point);
            if ((city_id != 0) && cityExists(city_id) && city.isCityBanned(self, city_id))
            {
                sendSystemMessage(self, SID_BANNED_FROM_THAT_CITY);
                return SCRIPT_CONTINUE;
            }
            if (!travel.isValidRoute(planet, depart_point, arrival_planet, arrival_point))
            {
                LOG("LOG_CHANNEL", "player_travel::OnBoardShuttle -- The route is no longer valid for " + ticket);
                sendSystemMessage(self, new string_id(STF_FILE, "route_not_available"));
                return SCRIPT_CONTINUE;
            }
            if (callable.hasAnyCallable(self))
            {
                sendSystemMessage(self, new string_id("beast", "beast_cant_travel"));
                return SCRIPT_CONTINUE;
            }
            if (!planet.equals(arrival_planet))
            {
                final String departPlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, planet);
                if ((departPlanetAvailableStarport != null) && (departPlanetAvailableStarport.length() > 0) && (!departPlanetAvailableStarport.equals(depart_point)))
                {
                    sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the departure planet, the only starport currently available for interplanetary travel on the departure planet is " + departPlanetAvailableStarport + ".", "");
                    return SCRIPT_CONTINUE;
                }
                final String arrivePlanetAvailableStarport = travel.getGcwTravelRestrictionsAvailableStarport(self, arrival_planet);
                if ((arrivePlanetAvailableStarport != null) && (arrivePlanetAvailableStarport.length() > 0) && (!arrivePlanetAvailableStarport.equals(arrival_point)))
                {
                    sendSystemMessage(self, "Because you are a GCW officer and the opposing faction currently controls the arrival planet, the only starport currently available for interplanetary travel on the arrival planet is " + arrivePlanetAvailableStarport + ".", "");
                    return SCRIPT_CONTINUE;
                }
            }
            if (qualifiesForGcwTravelPerks)
            {
                sendSystemMessage(self, "Bypassing the shuttle timer because you are a GCW officer and your faction currently controls the planet.", "");
            }
            int gcwTravelRestrictionsSkipWaitSurcharge = 0;
            if (travel.restrictedByGcwTravelRestrictions(self) && utils.hasScriptVar(starport, travel.SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT))
            {
                int shuttleAvailableCount = utils.getIntScriptVar(starport, travel.SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT);
                if ((shuttleAvailableCount % 2) == 1)
                {
                    if (!acceptGcwSkipShuttleWaitSurcharge)
                    {
                        if (utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
                        {
                            final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                            utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                            forceCloseSUIPage(pid);
                        }
                        final int timeUntilCurrentShuttleDeparts = timeUntilMessageTo(target, "msgShuttleTakeOff");
                        String announcement = "You must wait for the next shuttle because you are a GCW officer and the opposing faction currently controls the planet.\n\nOr you can pay a surcharge of 50000 credits to avoid the wait.\n\nWhat do you want to do?";
                        if (timeUntilCurrentShuttleDeparts >= 0)
                        {
                            announcement += "\n\n(The current shuttle will depart in " + (timeUntilCurrentShuttleDeparts / 60) + "m:" + (timeUntilCurrentShuttleDeparts % 60) + "s.)";
                        }
                        final int pid = sui.msgbox(self, self, announcement, sui.OK_CANCEL, "GCW Travel Restriction", sui.MSG_QUESTION, "handleConfirmGcwTravelRestrictionSkipShuttleWait");
                        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, "Wait");
                        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, "Pay");
                        showSUIPage(pid);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID, pid);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID, ticket);
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID, target);
                        return SCRIPT_CONTINUE;
                    }
                    else
                    {
                        gcwTravelRestrictionsSkipWaitSurcharge = 50000;
                    }
                }
            }
            final int gcwTravelRestrictionsSurcharge = travel.getGcwTravelRestrictionsSurcharge(self, travel.getTicketDeparturePlanet(ticket), arrival_planet);
            if (gcwTravelRestrictionsSurcharge > 0)
            {
                if (!acceptGcwSurcharge)
                {
                    if (utils.hasScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID))
                    {
                        final int pid = utils.getIntScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                        utils.removeScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID);
                        forceCloseSUIPage(pid);
                    }
                    if (gcwTravelRestrictionsSkipWaitSurcharge > 0)
                    {
                        utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SKIP_SHUTTLE_WAIT_SURCHARGE_ACCEPTED, 1);
                    }
                    final int pid = sui.msgbox(self, self, "You must pay a surcharge of " + gcwTravelRestrictionsSurcharge + " credits because you are a GCW officer and the opposing faction currently controls either the departure or the arrival planet.\n\nDo you accept the surcharge?", sui.YES_NO, "GCW Travel Surcharge", sui.MSG_QUESTION, "handleConfirmGcwTravelSurcharge");
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SUI_ID, pid);
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_TICKET_ID, ticket);
                    utils.setScriptVar(self, SCRIPTVAR_ACCEPT_GCW_SURCHARGE_SHUTTLE_ID, target);
                    return SCRIPT_CONTINUE;
                }
            }
            final int totalSurcharge = gcwTravelRestrictionsSkipWaitSurcharge + gcwTravelRestrictionsSurcharge;
            if (totalSurcharge > 0)
            {
                if (getTotalMoney(self) < totalSurcharge)
                {
                    sendSystemMessage(self, "You do not have enough credits to pay the " + totalSurcharge + " credits GCW travel surcharge.", "");
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    final int bankBalance = getBankBalance(self);
                    if (bankBalance < totalSurcharge)
                    {
                        depositCashToBank(self, (totalSurcharge - bankBalance), null, null, null);
                        if (getBankBalance(self) != totalSurcharge)
                        {
                            sendSystemMessage(self, "Could not transfer enough cash balance to bank balance to pay the " + totalSurcharge + " credits GCW travel surcharge.", "");
                            return SCRIPT_CONTINUE;
                        }
                    }
                    transferBankCreditsToNamedAccount(self, "GcwTravelSurcharge", totalSurcharge, null, null, null);
                    sendSystemMessage(self, "You have paid " + totalSurcharge + " credits GCW travel surcharge to the Galactic Travel Commission.", "");
                }
            }
            utils.setScriptVar(self, travel.VAR_DEPARTURE_PLANET, travel.getTicketDeparturePlanet(ticket));
            utils.setScriptVar(self, travel.VAR_DEPARTURE_POINT, travel.getTicketDeparturePoint(ticket));
            utils.setScriptVar(self, travel.VAR_ARRIVAL_PLANET, arrival_planet);
            utils.setScriptVar(self, travel.VAR_ARRIVAL_POINT, arrival_point);
            destroyObject(ticket);
            travel.movePlayerToDestination(self, arrival_planet, arrival_point);
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/player_travel/" + section, message);
        }
    }
    public int doCFP(obj_id self, int itvType) throws InterruptedException {
        if (travel.isTravelBlocked(self, false))
        {
            return SCRIPT_CONTINUE;
        }
        if (canCallForPickup(self))
        {
            debugLogging("//***// commandHandler : callForPickup", "////>>>> player CAN call for pickup. Executing shuttle spawn sequence.");
            sendSystemMessage(self, SID_CALLING_FOR_PICKUP);
            spawnPickupCraft(self, itvType);
        }
        return SCRIPT_CONTINUE;
    }
    public int callForPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_INSTANT_XWING_TIE);
    }
    public int callForPrivateerPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_INSTANT_PRIVATEER);
    }
    public int callForRattleTrapPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_INSTANT_JALOPY);
    }
    public int callForRoyalPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_INSTANT_ROYAL_SHIP);
    }
    public int callForTcgHomePickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_TCG_HOME_SHIP);
    }
    public int callForTcgLocationPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_TCG_LOCATION_SHIP);
    }
    public int callForSnowspeederPickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_SNOWSPEEDER_SHIP);
    }
    public int callForTcgSlave1Pickup(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        return doCFP(self, SHIP_TYPE_TCG_SLAVE1_SHIP);
    }
    public boolean canCallForPickup(obj_id player) throws InterruptedException
    {
        debugLogging("//***// canCallForPickup", "////>>>> ENTERED");
        // get the setting for minimum ITV level (if its not set, make it the max player level)
        String minLevelSetting = getConfigSetting("GameServer", "itvMinUsageLevel");
        if(minLevelSetting == null) minLevelSetting = "0";

        int minimumLevel = Integer.parseInt(minLevelSetting);
        obj_id playerCurrentMount = getMountId(player);
        if(getLevel(player) < minimumLevel){
            sendSystemMessage(player, "Instant Travel vehicles may not be used until you have reached level " + minLevelSetting, null);
            return false;
        }
        if (isIdValid(playerCurrentMount))
        {
            sendSystemMessage(player, SID_INSTANT_MOUNT_NO);
            return false;
        }
        if (isIdValid(structure.getContainingBuilding(player)))
        {
            sendSystemMessage(player, SID_LOCATION_INDOORS);
            return false;
        }
        if (isSpaceScene())
        {
            return false;
        }
        if (space_dungeon.verifyPlayerSession(player))
        {
            sendSystemMessage(player, SID_INVALID_PICKUP_LOC);
            return false;
        }
        if (buff.hasBuff(player, ITV_PICKUP_BUFF))
        {
            sendSystemMessage(player, SID_PICKUP_CANCEL);
            buff.removeBuff(player, ITV_PICKUP_BUFF);
            return false;
        }
        location here = getLocation(player);
        if (locations.isInCity(here))
        {
            sendSystemMessage(player, SID_NO_PICKUP_IN_TOWN);
            return false;
        }
        region geoCities[] = getRegionsWithGeographicalAtPoint(here, regions.GEO_CITY);
        if (geoCities != null && geoCities.length > 0)
        {
            sendSystemMessage(player, SID_INVALID_PICKUP_LOC);
            return false;
        }
        region[] regionList = getRegionsWithPvPAtPoint(here, regions.PVP_REGION_TYPE_ADVANCED);
        if (regionList != null && regionList.length > 0)
        {
            sendSystemMessage(player, SID_NO_PICKUP_IN_PVP);
            return false;
        }
        String planet = getCurrentSceneName();
        if (planet.startsWith("kashyyyk") || planet.equals("adventure1"))
        {
            sendSystemMessage(player, SID_INVALID_PICKUP_LOC);
            return false;
        }
        if (combat.isInCombat(player))
        {
            sendSystemMessage(player, SID_IN_COMBAT);
            return false;
        }
        return true;
    }
    public boolean spawnPickupCraft(obj_id player, int type) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        location here = getLocation(player);
        location spawnLoc = locations.getGoodLocationAroundLocation(here, 1.0f, 1.0f, 4.0f, 4.0f);
        if (spawnLoc == null)
        {
            sendSystemMessage(player, SID_LOCATION_NOGOOD_FOR_PICKUP);
            return false;
        }
        String pickupCraftType = "object/tangible/terminal/terminal_travel_instant_xwing.iff";
        if (type == SHIP_TYPE_INSTANT_XWING_TIE)
        {
            int playerFactionID = pvpGetAlignedFaction(player);
            if (playerFactionID == (-615855020))
            {
                pickupCraftType = "object/tangible/terminal/terminal_travel_instant_tie.iff";
                spawnLoc.y += 5.0f;
            }
        }
        else if (type == SHIP_TYPE_INSTANT_PRIVATEER)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_privateer.iff";
        }
        else if (type == SHIP_TYPE_INSTANT_ROYAL_SHIP)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_royal_ship.iff";
        }
        else if (type == SHIP_TYPE_INSTANT_JALOPY)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_jalopy.iff";
        }
        else if (type == SHIP_TYPE_TCG_LOCATION_SHIP)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_tcg_location.iff";
            playClientEffectObj(player, "sound/g9_rigger_01.snd", player, "");
        }
        else if (type == SHIP_TYPE_TCG_HOME_SHIP)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_tcg_home.iff";
            playClientEffectObj(player, "sound/solar_sailer_01.snd", player, "");
        }
        else if (type == SHIP_TYPE_SNOWSPEEDER_SHIP)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_snowspeeder.iff";
            playClientEffectObj(player, "sound/veh_t47snowspeeder_decel.snd", player, "");
        }
        else if (type == SHIP_TYPE_TCG_SLAVE1_SHIP)
        {
            pickupCraftType = "object/tangible/terminal/terminal_travel_instant_slave_1.iff";
            playClientEffectObj(player, "sound/eng_flyby_firespray.snd", player, "");
        }
        obj_id pickupCraft = create.object(pickupCraftType, spawnLoc);
        if (!isIdValid(pickupCraft))
        {
            return false;
        }
        utils.setScriptVar(player, "instantTravelShip.pickupCraft", pickupCraft);
        utils.setScriptVar(pickupCraft, "playerOwner", player);
        messageTo(pickupCraft, "initializeInstaTravelShip", null, 1, false);
        buff.applyBuff(player, ITV_PICKUP_BUFF);
        return true;
    }
    public int groupMemberLocationRequestHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id terminal = params.getObjId("terminal");
        obj_id requester = params.getObjId("requester");
        if (isIdValid(terminal) && isIdValid(requester))
        {
            location groupieLoc = getLocation(self);
            String groupieName = getName(self);
            dictionary webster = new dictionary();
            webster.put("requester", requester);
            webster.put("groupieLoc", groupieLoc);
            webster.put("groupieName", groupieName);
            region[] regionsHere = getRegionsAtPoint(groupieLoc);
            if (regionsHere != null && regionsHere.length > 0)
            {
                String[] groupieRegions = new String[regionsHere.length];
                for (int i = 0; i < regionsHere.length; i++)
                {
                    region currentRegion = regionsHere[i];
                    groupieRegions[i] = currentRegion.getName();
                }
                webster.put("groupieRegions", groupieRegions);
            }
            messageTo(terminal, "groupMemberLocationResponseHandler", webster, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
}
