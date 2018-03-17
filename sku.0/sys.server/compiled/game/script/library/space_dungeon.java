package script.library;

import script.*;

import java.util.Iterator;
import java.util.Vector;

public class space_dungeon extends script.base_script
{
    public space_dungeon()
    {
    }
    public static final String TEMPLATE_TICKET = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
    public static final String TRAVEL_DUNGEON = "item.travel_ticket.travel_space_dungeon";
    public static final String PLAYER_TRAVEL_DUNGEON = "item.travel_ticket.travel_player_space_dungeon";
    public static final String DUNGEON_CONTROLLER = "theme_park.dungeon.space_dungeon_controller";
    public static final int MAXIMUM_TICKET_COLLECTOR_DISTANCE_GROUND = 15;
    public static final int MAXIMUM_TICKET_COLLECTOR_DISTANCE_SPACE = 1000;
    public static final int MAXIMUM_TRAVEL_GROUP_DISTANCE_GROUND = 50;
    public static final int MAXIMUM_TRAVEL_GROUP_DISTANCE_SPACE = 100000;
    public static final String VAR_TICKET_ROOT = "space_dungeon.ticket";
    public static final String VAR_TICKET_PLANET = "space_dungeon.ticket.planet";
    public static final String VAR_TICKET_POINT = "space_dungeon.ticket.point";
    public static final String VAR_TICKET_DUNGEON = "space_dungeon.ticket.dungeon";
    public static final String VAR_TICKET_QUEST_TYPE = "space_dungeon.ticket.quest_type";
    public static final String VAR_DUNGEON_NAME = "space_dungeon.name";
    public static final String VAR_BUILDOUT_AREA = "space_dungeon.buildout_area";
    public static final String VAR_BUILDOUT_ROW = "space_dungeon.buildout_row";
    public static final String VAR_IS_ACTIVE = "space_dungeon.is_active";
    public static final String VAR_SESSION_ID = "space_dungeon.session_id";
    public static final String VAR_PARTICIPANT_IDS = "space_dungeon.participant_ids";
    public static final String SCRIPT_VAR_TIME_REMAINING = "space_dungeon.time_remaining";
    public static final String VAR_QUEST_TYPE = "space_dungeon.quest_type";
    public static final String VAR_REGISTERED_OBJECTS = "space_dungeon.registered_objects";
    public static final String VAR_DUNGEON_START_TIME = "space_dungeon.session_start_time";
    public static final String VAR_DUNGEON_END_TIME = "space_dungeon.session_end_time";
    public static final String KASH_SLAVE_CAMP = "trando_slave_camp";
    public static final String KASH_THE_BET = "the_bet_bocctyyy";
    public static final String KASH_MONSTER_ISLAND = "monster_island_hracca";
    public static final String KASH_THE_ARENA = "kash_the_arena";
    public static final String CORVETTE_REBEL = "";
    public static final String CORVETTE_IMPERIAL = "";
    public static final String CORVETTE_NEUTRAL = "";
    public static final String SCRIPT_VAR_TRAVELERS = "space_dungeon.travelers_";
    public static final String VAR_TICKET_USED = "space_dungeon.ticket_used";
    public static final String VAR_DUNGEON_ID = "space_dungeon.dungeon_id";
    public static final String VAR_PILOT_ID = "space_dungeon.pilot_id";
    public static final String VAR_PILOT_SHIP_ID = "space_dungeon.pilot_ship_id";
    public static final String VAR_PILOT_PREVIOUS_LOCATION = "space_dungeon.pilot_previous_location";
    public static final String VAR_PASSENGER_IDS = "space_dungeon.passenger_ids";
    public static final String VAR_RESET_DUNGEON = "space_dungeon.reset_dungeon";
    public static final String VAR_EJECT_OVERRIDE = "space_dungeon.eject_override";
    public static final String SCRIPT_VAR_VALID_TICKETS = "space_dungeon.valid_tickets";
    public static final String SCRIPT_VAR_DUNGEON_PENDING = "space_dungeon.dungeon_pending";
    public static final String SCRIPT_VAR_DUNGEON_ID_PENDING = "space_dungeon.dungeon_id_pending";
    public static final String SCRIPT_VAR_DUNGEON_VALIDATED = "space_dungeon.dungeon_validated";
    public static final String SCRIPT_VAR_DUNGEON_POSITION = "space_dungeon.dungeon_position";
    public static final String PLAYER_ID = "playerId";
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_ILLEGAL_TICKET = new string_id("dungeon/space_dungeon", "illegal_ticket");
    public static final string_id SID_VALIDATING_TICKET = new string_id("dungeon/space_dungeon", "validating_ticket");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDING = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_NOT_AUTHORIZED = new string_id("dungeon/space_dungeon", "not_authorized");
    public static final string_id SID_NO_ROOM_REMAINING = new string_id("dungeon/space_dungeon", "no_room_remaining");
    public static final string_id SID_SESSION_TIME_ENDED = new string_id("dungeon/space_dungeon", "session_time_expired");
    public static final string_id SID_PILOT_LANDING_AT_DUNGEON = new string_id("dungeon/space_dungeon", "pilot_landing_at_dungeon");
    public static final string_id SID_YOUR_PILOT_EJECTED = new string_id("dungeon/space_dungeon", "your_pilot_ejected");
    public static final string_id SID_YOU_LEFT_A_PASSENGER_BEHIND = new string_id("dungeon/space_dungeon", "you_left_a_passenger_behind");
    public static final string_id SID_PASSENGER_HAS_EJECTED = new string_id("dungeon/space_dungeon", "passenger_has_ejected");
    public static final string_id SID_COPASSENGER_HAS_EJECTED = new string_id("dungeon/space_dungeon", "copassenger_has_ejected");
    public static final string_id SID_FIND_ESCAPE_POD = new string_id("dungeon/space_dungeon", "find_escape_pod");
    public static final string_id SID_YOU_DID_NOT_PILOT = new string_id("dungeon/space_dungeon", "you_did_not_pilot");
    public static final string_id SID_CATCH_A_RIDE_WITH_PILOT = new string_id("dungeon/space_dungeon", "catch_a_ride_with_pilot");
    public static final string_id SID_PILOT_HAS_LEFT_YOU = new string_id("dungeon/space_dungeon", "pilot_has_left_you");
    public static final string_id SID_PASSENGER_NO_LONGER_WITH_YOU = new string_id("dungeon/space_dungeon", "passenger_no_longer_with_you");
    public static final string_id SID_PILOT_LAUNCHED_WITHOUT_YOU = new string_id("dungeon/space_dungeon", "pilot_launched_without_you");
    public static final string_id SID_PASSENGER_LEFT_BEHIND = new string_id("dungeon/space_dungeon", "passenger_left_behind");
    public static final string_id SID_PASSENGER_CANNOT_COME = new string_id("dungeon/space_dungeon", "passenger_cannot_come");
    public static final string_id SID_DOESNT_THINK_YOUR_A_PILOT = new string_id("dungeon/space_dungeon", "doesnt_think_your_a_pilot");
    public static final string_id SID_NOW_BOARDING_PASSENGER = new string_id("dungeon/space_dungeon", "now_boarding_passenger");
    public static final string_id SID_YOU_ARE_NOW_BOARDING = new string_id("dungeon/space_dungeon", "you_are_now_boarding");
    public static final string_id SID_SUI_SELECT_DESTINATION_TITLE = new string_id("dungeon/space_dungeon", "sui_select_destination_title");
    public static final string_id SID_AUTH_TO_TRAVEL_OFFER = new string_id("dungeon/space_dungeon", "auth_to_travel_offer");
    public static final String INSTANCE_ID = "currentInstanceControllerId";
    public static final String DUNGEON_STF = "dungeon/space_dungeon";
    public static final string_id SID_LOCKOUT_MESSAGE = new string_id(DUNGEON_STF, "lockout_remaining");
    public static final string_id SID_LOCKOUT_TITLE = new string_id(DUNGEON_STF, "lockout_title");
    public static final string_id SID_GROUP_BODY = new string_id(DUNGEON_STF, "group_lockout_body");
    public static final string_id SID_SELF_BODY = new string_id(DUNGEON_STF, "self_lockout_body");
    public static final string_id SID_NO_LOCKOUT = new string_id(DUNGEON_STF, "no_lockouts");
    public static final String LIST_DUNGEON_LOCKOUT = "space_dungeon.lockout_timer.";
    public static final boolean LOGGING = false;
    public static boolean intializeSpaceDungeon(obj_id dungeon) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.intializeSpaceDungeon -- " + dungeon);
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.initializeSpaceDungeon -- dungeon obj_id is invalid");
            return false;
        }
        if (!hasObjVar(dungeon, VAR_DUNGEON_NAME))
        {
            LOG("space_dungeon", "space_dungeon.initializeSpaceDungeon -- dungeon " + dungeon + " does not have a name.");
            return false;
        }
        if (!hasObjVar(dungeon, VAR_IS_ACTIVE) || getIntObjVar(dungeon, VAR_IS_ACTIVE) != 1)
        {
            LOG("space_dungeon", "space_dungeon.initializeSpaceDungeon -- ignoring dungeon " + dungeon + " because it is not marked as active.");
            return false;
        }
        if (isIdValid(dungeon) && isGameObjectTypeOf(getGameObjectType(dungeon), GOT_ship))
        {
            setOwner(dungeon, null);
        }
        String name = getDungeonInstanceName(dungeon);
        String buildout_area = locations.getBuildoutAreaName(dungeon);
        int buildout_row = locations.getBuildoutAreaRow(dungeon);
        setObjVar(dungeon, VAR_BUILDOUT_AREA, buildout_area);
        setObjVar(dungeon, VAR_BUILDOUT_ROW, buildout_row);
        messageTo(dungeon, "dungeonCleanup", null, 0, false);
        if (hasObjVar(dungeon, space_dungeon.VAR_PARTICIPANT_IDS))
        {
            removeObjVar(dungeon, space_dungeon.VAR_PARTICIPANT_IDS);
        }
        if (hasObjVar(dungeon, space_dungeon.VAR_DUNGEON_START_TIME))
        {
            removeObjVar(dungeon, space_dungeon.VAR_DUNGEON_START_TIME);
        }
        if (hasObjVar(dungeon, space_dungeon.VAR_DUNGEON_END_TIME))
        {
            removeObjVar(dungeon, space_dungeon.VAR_DUNGEON_END_TIME);
        }
        int session_id = getDungeonSessionId(dungeon);
        if (session_id != -1)
        {
            setObjVar(dungeon, VAR_SESSION_ID, -1);
        }
        if (hasObjVar(dungeon, space_dungeon.VAR_QUEST_TYPE))
        {
            removeObjVar(dungeon, space_dungeon.VAR_QUEST_TYPE);
        }
        if (hasObjVar(dungeon, "space_dungeon.lock_key"))
        {
            removeObjVar(dungeon, "space_dungeon.lock_key");
        }
        getClusterWideData("dungeon", name, true, dungeon);
        return true;
    }
    public static boolean isSpaceDungeon(obj_id structure) throws InterruptedException
    {
        if (!isIdValid(structure))
        {
            LOG("space_dungeon", "space_dungeon.isSpaceDungeon -- structure is invalid.");
            return false;
        }
        if (hasScript(structure, DUNGEON_CONTROLLER))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getDungeonMaxPlayers(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonMaxPlayers -- dungeon is invalid.");
            return -1;
        }
        return space_dungeon_data.getDungeonMaxPlayers(getDungeonName(dungeon));
    }
    public static location getDungeonExitLocation(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonExitLocation -- dungeon is invalid.");
            return null;
        }
        return space_dungeon_data.getDungeonExitLocation(getDungeonName(dungeon));
    }
    public static String getDungeonName(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonName -- dungeon is invalid.");
            return null;
        }
        if (hasObjVar(dungeon, VAR_DUNGEON_NAME))
        {
            return getStringObjVar(dungeon, VAR_DUNGEON_NAME);
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonName -- dungeon " + dungeon + " does not have a name.");
            return null;
        }
    }
    public static string_id getDungeonNameStringId(String name) throws InterruptedException
    {
        if (name == null || name.length() < 1)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonNameStringId -- name is null or empty.");
            return null;
        }
        return utils.unpackString("@dungeon/space_dungeon:" + name);
    }
    public static String getDungeonInstanceName(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonInstanceName -- dungeon is invalid.");
            return null;
        }
        String name = getDungeonName(dungeon);
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonInstanceName -- dungeon " + dungeon + " does not have a name.");
            return null;
        }
        return name + "-" + dungeon;
    }
    public static String getDungeonPlayerScript(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonPlayerScript -- dungeon is invalid.");
            return null;
        }
        return space_dungeon_data.getDungeonPlayerScript(getDungeonName(dungeon));
    }
    public static obj_id getDungeonTraveler(obj_id ticket_collector, int request_id) throws InterruptedException
    {
        if (!isIdValid(ticket_collector))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonTraveler -- ticket_collect is not valid");
            return null;
        }
        if (request_id < 0)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonTraveler -- request_id has an illegal value: " + request_id);
            return null;
        }
        String script_var_name = SCRIPT_VAR_TRAVELERS + request_id;
        if (utils.hasScriptVar(ticket_collector, script_var_name))
        {
            obj_id player = utils.getObjIdScriptVar(ticket_collector, script_var_name);
            if (isIdValid(player) && player.isAuthoritative())
            {
                return player;
            }
            else 
            {
                return null;
            }
        }
        else 
        {
            return null;
        }
    }
    public static boolean removeDungeonTraveler(obj_id ticket_collector, int request_id) throws InterruptedException
    {
        if (!isIdValid(ticket_collector))
        {
            LOG("space_dungeon", "space_dungeon.removeDungeonTraveler -- ticket_collect is not valid");
            return false;
        }
        if (request_id < 0)
        {
            LOG("space_dungeon", "space_dungeon.removeDungeonTraveler -- request_id has an illegal value: " + request_id);
            return false;
        }
        String script_var_name = SCRIPT_VAR_TRAVELERS + request_id;
        if (utils.hasScriptVar(ticket_collector, script_var_name))
        {
            utils.removeScriptVar(ticket_collector, script_var_name);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getDungeonSessionId(obj_id item) throws InterruptedException
    {
        if (!isIdValid(item))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonSessionId -- item is not valid");
            return -1;
        }
        if (utils.hasObjVar(item, VAR_SESSION_ID))
        {
            return utils.getIntObjVar(item, VAR_SESSION_ID);
        }
        else 
        {
            return -1;
        }
    }
    public static obj_id getDungeonIdForPlayer(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, VAR_DUNGEON_ID))
        {
            return utils.getObjIdObjVar(player, VAR_DUNGEON_ID);
        }
        if (hasObjVar(player, INSTANCE_ID))
        {
            return utils.getObjIdObjVar(player, INSTANCE_ID);
        }
        return utils.getObjIdObjVar(player, VAR_DUNGEON_ID);
    }
    public static obj_id createTicket(obj_id player, String start_planet, String start_point, String dungeon) throws InterruptedException
    {
        return createTicket(player, start_planet, start_point, dungeon, TEMPLATE_TICKET);
    }
    public static obj_id createTicket(obj_id player, String start_planet, String start_point, String dungeon, String ticket_template) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  player is null.");
            return null;
        }
        if (start_planet == null || start_planet.length() < 1)
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  start_planet is null or empty.");
            return null;
        }
        if (start_point == null || start_point.length() < 1)
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  start_point is null or empty.");
            return null;
        }
        if (dungeon == null || dungeon.length() < 1)
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  dungeon is null or empty.");
            return null;
        }
        obj_id inv = getObjectInSlot(player, "inventory");
        if (inv == null)
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  player " + player + " inventory is null.");
            return null;
        }
        int free_space = getVolumeFree(inv);
        if (free_space < 1)
        {
            LOG("space_dungeon", "space_dungeon.createTicket --  player " + player + " inventory is full.");
            return null;
        }
        obj_id ticket = createObject(ticket_template, inv, "");
        setObjVar(ticket, VAR_TICKET_PLANET, start_planet);
        setObjVar(ticket, VAR_TICKET_POINT, start_point);
        setObjVar(ticket, VAR_TICKET_DUNGEON, dungeon);
        attachScript(ticket, "item.travel_ticket.dungeon_ticket");
        return ticket;
    }
    public static String getTicketDungeonName(obj_id ticket) throws InterruptedException
    {
        if (!isIdValid(ticket))
        {
            LOG("space_dungeon", "space_dungeon.getTicketDungeonName --  ticket is not valid.");
            return null;
        }
        if (hasObjVar(ticket, VAR_TICKET_DUNGEON))
        {
            return getStringObjVar(ticket, VAR_TICKET_DUNGEON);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketPointName(obj_id ticket) throws InterruptedException
    {
        if (!isIdValid(ticket))
        {
            LOG("space_dungeon", "space_dungeon.getTicketPointName --  ticket is not valid.");
            return null;
        }
        if (hasObjVar(ticket, VAR_TICKET_POINT))
        {
            return getStringObjVar(ticket, VAR_TICKET_POINT);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketPlanetName(obj_id ticket) throws InterruptedException
    {
        if (!isIdValid(ticket))
        {
            LOG("space_dungeon", "space_dungeon.getTicketPlanetName --  ticket is not valid.");
            return null;
        }
        if (hasObjVar(ticket, VAR_TICKET_PLANET))
        {
            return getStringObjVar(ticket, VAR_TICKET_PLANET);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id[] findValidDungeonTickets(obj_id player, obj_id ticket_collector) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.findDungeonTicket --  player is not valid.");
            return null;
        }
        if (!isIdValid(ticket_collector))
        {
            LOG("space_dungeon", "space_dungeon.findDungeonTicket --  ticket_collector is not valid.");
            return null;
        }
        String collector_dungeon = getTicketDungeonName(ticket_collector);
        if (collector_dungeon == null || collector_dungeon.length() < 1)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnObjectMenuSelect -- ticket collecter " + ticket_collector + " does not have a dungeon name.");
            return null;
        }
        String collector_point = getTicketPointName(ticket_collector);
        if (collector_point == null || collector_point.length() < 1)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnObjectMenuSelect -- ticket collecter " + ticket_collector + " does not have a point name.");
            return null;
        }
        String collector_planet = getCurrentSceneName();
        obj_id inv = getObjectInSlot(player, "inventory");
        if (inv == null)
        {
            LOG("space_dungeon", "space_dungeon.findDungeonTicket --  player " + player + " inventory is null.");
            return null;
        }
        obj_id[] inv_contents = utils.getContents(inv, false);
        Vector valid_tickets = new Vector();
        valid_tickets.setSize(0);
        if (inv_contents != null)
        {
            for (int i = 0; i < inv_contents.length; i++)
            {
                if (hasObjVar(inv_contents[i], VAR_TICKET_ROOT))
                {
                    String ticket_point = getTicketPointName(inv_contents[i]);
                    if (ticket_point != null && ticket_point.equals(collector_point))
                    {
                        String ticket_planet = getTicketPlanetName(inv_contents[i]);
                        if (ticket_planet != null && ticket_planet.equals(collector_planet))
                        {
                            String ticket_dungeon = getTicketDungeonName(inv_contents[i]);
                            if (ticket_dungeon != null && ticket_dungeon.equals(collector_dungeon))
                            {
                                utils.addElement(valid_tickets, inv_contents[i]);
                            }
                        }
                    }
                }
            }
        }
        if (valid_tickets.size() > 0)
        {
            obj_id[] _valid_tickets = new obj_id[0];
            if (valid_tickets != null)
            {
                _valid_tickets = new obj_id[valid_tickets.size()];
                valid_tickets.toArray(_valid_tickets);
            }
            return _valid_tickets;
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getDungeonTicketCollector(obj_id player, String point_name) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.getDungeonTicketCollector --  player is invalid.");
            return null;
        }
        location loc = getLocation(player);
        float maxTicketCollectorDistance = isSpaceScene() ? MAXIMUM_TICKET_COLLECTOR_DISTANCE_SPACE : MAXIMUM_TICKET_COLLECTOR_DISTANCE_GROUND;
        obj_id[] items = getObjectsInRange(loc, maxTicketCollectorDistance);
        for (int i = 0; i < items.length; i++)
        {
            if (hasScript(items[i], TRAVEL_DUNGEON))
            {
                if (point_name == null)
                {
                    return items[i];
                }
                else 
                {
                    String collector_point = getTicketPointName(items[i]);
                    if ((collector_point != null) && collector_point.equals(point_name))
                    {
                        return items[i];
                    }
                }
            }
        }
        return null;
    }
    public static boolean selectDungeonTicket(obj_id ticket_collector, obj_id player) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.selectDungeonTicket collector=" + ticket_collector + ", player=" + player);
        if (utils.hasScriptVar(player, space_dungeon.SCRIPT_VAR_VALID_TICKETS))
        {
            return false;
        }
        String name = space_dungeon.getTicketDungeonName(ticket_collector);
        if (name == null || name.length() < 1)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnObjectMenuSelect -- ticket collecter " + ticket_collector + " does not have a dungeon name.");
            return false;
        }
        if (hasObjVar(player, space_dungeon.VAR_TICKET_USED) || hasObjVar(player, space_dungeon.VAR_TICKET_DUNGEON))
        {
            sendSystemMessage(player, SID_REQUEST_TRAVEL_OUTSTANDING);
            return false;
        }
        obj_id[] valid_tickets = space_dungeon.findValidDungeonTickets(player, ticket_collector);
        if (valid_tickets != null)
        {
            if (valid_tickets.length == 1)
            {
                space_dungeon.activateDungeonTicket(player, valid_tickets[0], ticket_collector);
            }
            else 
            {
                String[] dsrc = new String[valid_tickets.length];
                for (int i = 0; i < valid_tickets.length; i++)
                {
                    String dungeon_name = space_dungeon.getTicketDungeonName(valid_tickets[i]);
                    String dungeon_name_loc = localize(space_dungeon.getDungeonNameStringId(dungeon_name));
                    dsrc[i] = dungeon_name_loc;
                }
                utils.setScriptVar(player, space_dungeon.SCRIPT_VAR_VALID_TICKETS, valid_tickets);
                String title = utils.packStringId(SID_SUI_SELECT_DESTINATION_TITLE);
                sui.listbox(player, player, title, sui.OK_CANCEL, title, dsrc, "msgSelectDungeonTicket");
                return true;
            }
        }
        else 
        {
            prose_package pp = prose.getPackage(SID_NO_TICKET, space_dungeon.getDungeonNameStringId(name));
            sendSystemMessageProse(player, pp);
        }
        return false;
    }
    public static boolean activateDungeonTicket(obj_id player, obj_id ticket, obj_id ticket_collector) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.activateDungeonTicket --  player is null.");
            return false;
        }
        if (!isIdValid(ticket))
        {
            LOG("space_dungeon", "space_dungeon.activateDungeonTicket --  ticket is invalid.");
            return false;
        }
        String dungeon_name = getTicketDungeonName(ticket);
        String point_name = getTicketPointName(ticket);
        String planet_name = getTicketPlanetName(ticket);
        if (dungeon_name == null || point_name == null || planet_name == null)
        {
            LOG("space_dungeon", "space_dungeon.activateDungeonTicket --  dungeon name on ticket " + ticket + " has one or more null travel values.");
            return false;
        }
        String collector_name = getTicketDungeonName(ticket_collector);
        String collector_point = getTicketPointName(ticket_collector);
        String collector_planet = getCurrentSceneName();
        if (!dungeon_name.equals(collector_name) || !point_name.equals(collector_point) || !planet_name.equals(collector_planet))
        {
            sendSystemMessage(player, SID_ILLEGAL_TICKET);
            return false;
        }
        int request_id = getClusterWideData("dungeon", dungeon_name + "*", true, ticket_collector);
        if (request_id < 1)
        {
            string_id fail = space_dungeon_data.getDungeonFailureString(dungeon_name);
            if (fail != null)
            {
                sendSystemMessage(player, fail);
            }
            else 
            {
                sendSystemMessage(player, SID_UNABLE_TO_FIND_DUNGEON);
            }
            return false;
        }
        setObjVar(player, VAR_TICKET_USED, ticket);
        String script_var_name = SCRIPT_VAR_TRAVELERS + request_id;
        utils.setScriptVar(ticket_collector, script_var_name, player);
        sendSystemMessage(player, SID_VALIDATING_TICKET);
        return true;
    }
    public static boolean sendGroupToDungeonWithoutTicket(obj_id player, String dungeon_name, String point_name, String planet_name, String quest_type, obj_id ticket_collector) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.activateDungeonTicket --  player is null.");
            return false;
        }
        if (!isDungeonLockoutTimerExpired(player, dungeon_name))
        {
            displayDungeonLockoutTimerSUI(player, dungeon_name);
            return false;
        }
        String collector_name = getTicketDungeonName(ticket_collector);
        String collector_point = getTicketPointName(ticket_collector);
        String collector_planet = getCurrentSceneName();
        if (!dungeon_name.equals(collector_name) || !point_name.equals(collector_point) || !planet_name.equals(collector_planet))
        {
            sendSystemMessage(player, SID_ILLEGAL_TICKET);
            return false;
        }
        int request_id = getClusterWideData("dungeon", dungeon_name + "*", true, ticket_collector);
        if (request_id < 1)
        {
            string_id fail = space_dungeon_data.getDungeonFailureString(dungeon_name);
            if (fail != null)
            {
                sendSystemMessage(player, fail);
            }
            else 
            {
                sendSystemMessage(player, SID_UNABLE_TO_FIND_DUNGEON);
            }
            return false;
        }
        setObjVar(player, VAR_TICKET_USED, player);
        setObjVar(player, VAR_TICKET_PLANET, planet_name);
        setObjVar(player, VAR_TICKET_POINT, point_name);
        setObjVar(player, VAR_TICKET_DUNGEON, dungeon_name);
        setObjVar(player, VAR_TICKET_QUEST_TYPE, quest_type);
        String script_var_name = SCRIPT_VAR_TRAVELERS + request_id;
        utils.setScriptVar(ticket_collector, script_var_name, player);
        sendSystemMessage(player, SID_VALIDATING_TICKET);
        return true;
    }
    public static boolean sendGroupToDungeonWithoutTicketCollector(obj_id player, String dungeon_name, String quest_type) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.sendGroupToDungeonWithoutTicketCollector --  player is null.");
            return false;
        }
        if (!hasScript(player, "item.travel_ticket.travel_player_space_dungeon") && !hasScript(player, "npe.travel_player_space_dungeon_falcon"))
        {
            LOG("space_dungeon", "space_dungeon.sendGroupToDungeonWithoutTicketCollector -- player " + player + " does not have script to act as its own ticket collector");
            return false;
        }
        int request_id = getClusterWideData("dungeon", dungeon_name + "*", true, player);
        if (request_id < 1)
        {
            string_id fail = space_dungeon_data.getDungeonFailureString(dungeon_name);
            if (fail != null)
            {
                sendSystemMessage(player, fail);
            }
            else 
            {
                sendSystemMessage(player, SID_UNABLE_TO_FIND_DUNGEON);
            }
            return false;
        }
        location start_loc = space_dungeon_data.getDungeonStartLocation(dungeon_name);
        if (start_loc == null)
        {
            LOG("space_dungeon", "space_dungeon.sendGroupToDungeonWithoutTicketCollector -- couldn't get start location for " + dungeon_name);
            return false;
        }
        String point_name = getCurrentSceneName();
        setObjVar(player, VAR_TICKET_USED, player);
        setObjVar(player, VAR_TICKET_PLANET, start_loc.area);
        setObjVar(player, VAR_TICKET_POINT, point_name);
        setObjVar(player, VAR_TICKET_DUNGEON, dungeon_name);
        setObjVar(player, VAR_TICKET_QUEST_TYPE, quest_type);
        String script_var_name = SCRIPT_VAR_TRAVELERS + request_id;
        utils.setScriptVar(player, script_var_name, player);
        return true;
    }
    public static void cleanupPlayerDungeonObjvars(obj_id player) throws InterruptedException
    {
        removeObjVar(player, VAR_SESSION_ID);
        removeObjVar(player, VAR_DUNGEON_ID);
        removeObjVar(player, INSTANCE_ID);
        removeObjVar(player, space_dungeon.VAR_PILOT_ID);
        removeObjVar(player, space_dungeon.VAR_PASSENGER_IDS);
        removeObjVar(player, VAR_PILOT_PREVIOUS_LOCATION);
        cleanupPlayerTicketObjvars(player);
    }
    public static void cleanupPlayerTicketObjvars(obj_id player) throws InterruptedException
    {
        removeObjVar(player, VAR_TICKET_USED);
        removeObjVar(player, VAR_TICKET_PLANET);
        removeObjVar(player, VAR_TICKET_POINT);
        removeObjVar(player, VAR_TICKET_DUNGEON);
        removeObjVar(player, VAR_TICKET_QUEST_TYPE);
    }
    public static boolean movePlayerGroupToDungeon(obj_id player, obj_id dungeon, String dungeon_name, location dungeon_position) throws InterruptedException
    {
        return movePlayerGroupToDungeon(player, dungeon, dungeon_name, dungeon_position, true);
    }
    public static boolean movePlayerGroupToDungeon(obj_id player, obj_id dungeon, String dungeon_name, location dungeon_position, boolean checkLockoutTimer) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.movePlayerGroupToDungeon");
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  player is null.");
            return false;
        }
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  dungeon is invalid.");
            cleanupPlayerTicketObjvars(player);
            return false;
        }
        if (dungeon_name == null || dungeon_name.length() < 1)
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  dungeon_name is null or empty.");
            cleanupPlayerTicketObjvars(player);
            return false;
        }
        if (!isDungeonLockoutTimerExpired(player, dungeon_name))
        {
            displayDungeonLockoutTimerSUI(player, dungeon_name);
            return false;
        }
        boolean isSpaceScene = isSpaceScene();
        obj_id playerPilotedShip = isSpaceScene ? getPilotedShip(player) : null;
        if (isSpaceScene && null == playerPilotedShip)
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  in space, only pilots may initiate travel to space dungeons.");
            cleanupPlayerTicketObjvars(player);
            return false;
        }
        int session_id = getDungeonSessionId(player);
        if (session_id == -1)
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  player " + player + " does not have a valid session id.");
            cleanupPlayerTicketObjvars(player);
            return false;
        }
        String start_cell = space_dungeon_data.getDungeonStartCellName(dungeon_name);
        if (start_cell == null)
        {
            LOG("space_dungeon", "space_dungeon.movePlayerToDungeon --  start_cell is null.");
            cleanupPlayerTicketObjvars(player);
            return false;
        }
        float maxTravelGroupDistance = isSpaceScene ? MAXIMUM_TRAVEL_GROUP_DISTANCE_SPACE : MAXIMUM_TRAVEL_GROUP_DISTANCE_GROUND;
        obj_id group = getGroupObject(player);
        if (isSpaceScene && null != playerPilotedShip)
        {
            Vector passengers = space_transition.getContainedPlayers(playerPilotedShip);
            if (null != passengers)
            {
                LOG("space_dungeon", "space_dungeon.movePlayerToDungeon [" + player + "] moving <= [" + passengers.size() + "] passengers.");
                setObjVar(player, VAR_PASSENGER_IDS, passengers);
                for (Iterator it = passengers.iterator(); it.hasNext(); )
                {
                    obj_id passenger = (obj_id)it.next();
                    if (!player.equals(passenger))
                    {
                        setObjVar(passenger, VAR_PILOT_ID, player);
                        setObjVar(passenger, VAR_SESSION_ID, session_id);
                        prose_package ppPilotLanding = prose.getPackage(SID_PILOT_LANDING_AT_DUNGEON);
                        prose.setTT(ppPilotLanding, player);
                        prose.setTO(ppPilotLanding, dungeon_name);
                        sendSystemMessageProse(passenger, ppPilotLanding);
                        moveSinglePlayerIntoDungeon(passenger, dungeon, dungeon_name, dungeon_position);
                    }
                }
            }
        }
        location playerLocation = getLocation(player);
        if (isIdValid(group) && group.isLoaded())
        {
            obj_id[] group_members = getGroupMemberIds(group);
            if (group_members != null && group_members.length > 0)
            {
                for (int i = 0; i < group_members.length; i++)
                {
                    if (!player.equals(group_members[i]))
                    {
                        if (group_members[i].isAuthoritative())
                        {
                            if (isSpaceScene)
                            {
                                if (playerPilotedShip.equals(getTopMostContainer(group_members[i])))
                                {
                                    continue;
                                }
                                if (null == getPilotedShip(group_members[i]))
                                {
                                    continue;
                                }
                            }
                            if (!utils.hasScriptVar(group_members[i], SCRIPT_VAR_DUNGEON_PENDING))
                            {
                                if (playerLocation.distance(getLocation(group_members[i])) <= maxTravelGroupDistance)
                                {
                                    utils.setScriptVar(group_members[i], SCRIPT_VAR_DUNGEON_PENDING, dungeon_name);
                                    utils.setScriptVar(group_members[i], SCRIPT_VAR_DUNGEON_ID_PENDING, dungeon);
                                    setObjVar(group_members[i], VAR_SESSION_ID, session_id);
                                    utils.setScriptVar(group_members[i], SCRIPT_VAR_DUNGEON_POSITION, dungeon_position);
                                    String dungeon_name_local = localize(getDungeonNameStringId(dungeon_name));
                                    prose_package ppAuthToTravel = prose.getPackage(SID_AUTH_TO_TRAVEL_OFFER);
                                    prose.setTT(ppAuthToTravel, player);
                                    prose.setTO(ppAuthToTravel, dungeon_name_local);
                                    String prompt = " \0" + packOutOfBandProsePackage(null, ppAuthToTravel);
                                    int pid = sui.msgbox(group_members[i], group_members[i], prompt, sui.YES_NO, "msgDungeonTravelConfirmed");
                                    dictionary d = new dictionary();
                                    d.put("pid", pid);
                                    messageTo(group_members[i], "msgCloseDungeonTravel", d, 30.0f, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        moveSinglePlayerIntoDungeon(player, dungeon, dungeon_name, dungeon_position);
        return true;
    }
    public static void moveSinglePlayerIntoDungeon(obj_id player, obj_id dungeon, String dungeon_name, location dungeon_position) throws InterruptedException
    {
        if (!isDungeonLockoutTimerExpired(player, dungeon_name))
        {
            displayDungeonLockoutTimerSUI(player, dungeon_name);
            return;
        }
        LOG("space_dungeon", "space_dungeon.moveSinglePlayerIntoDungeon [" + player + "] -> [" + dungeon + "].");
        boolean isSpaceScene = isSpaceScene();
        if (isSpaceScene)
        {
            if (!hasObjVar(player, VAR_PILOT_ID))
            {
                obj_id playerPilotedShip = isSpaceScene ? getPilotedShip(player) : null;
                if (null != playerPilotedShip)
                {
                    location shipPreviousLocation = getLocation(playerPilotedShip);
                    setObjVar(player, VAR_PILOT_ID, player);
                    setObjVar(player, VAR_PILOT_SHIP_ID, playerPilotedShip);
                    setObjVar(player, VAR_PILOT_PREVIOUS_LOCATION, shipPreviousLocation);
                    LOG("space_dungeon", "space_dungeon.moveSinglePlayerIntoDungeon --  Pilot objvar set for self [" + player + "].");
                }
            }
        }
        location start_loc = space_dungeon_data.getDungeonStartLocationRandomized(dungeon_name);
        String start_cell = space_dungeon_data.getDungeonStartCellName(dungeon_name);
        LOG("space_dungeon", "space_dungeon.movePlayerGroupToDungeon: area ->" + dungeon_position.area + " " + dungeon_position.x + "/" + dungeon_position.y + "/" + dungeon_position.z + " " + start_loc.x + "/" + start_loc.y + "/" + start_loc.z + " (" + start_cell + ")");
        setObjVar(player, VAR_DUNGEON_ID, dungeon);
        utils.dismountRiderJetpackCheck(player);
        callable.storeCallables(player);
        dictionary dict = new dictionary();
        dict.put(PLAYER_ID, player);
        messageTo(dungeon, "addPlayerToParticipantIdList", dict, 0, false);
        setInstanceControllerIdOnPlayer(player, dungeon);
        if (start_cell.equals(""))
        {
            vector post = new vector(dungeon_position.x + start_loc.x, dungeon_position.y + start_loc.y, dungeon_position.z + start_loc.z);
            warpPlayer(player, dungeon_position.area, post.x, post.y, post.z, obj_id.NULL_ID, 0.0f, 0.0f, 0.0f, "msgDungeonTravelComplete", true);
        }
        else 
        {
            warpPlayer(player, dungeon_position.area, dungeon_position.x, dungeon_position.y, dungeon_position.z, dungeon, start_cell, start_loc.x, start_loc.y, start_loc.z, "msgDungeonTravelComplete", true);
        }
    }
    public static boolean ejectPlayerFromDungeon(obj_id player) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon");
        if (gm.isInstanceAuthorized(player))
        {
            sendSystemMessageTestingOnly(player, "You are negating the eject call due to authrization override");
            return false;
        }
        if (!preCheckEjectStatus(player, true))
        {
            LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon -- preCheckEjectStatus failed");
            return performEmergencyEject(player);
        }
        obj_id dungeon = findValidDungeonForEject(player);
        if (dungeon == null)
        {
            performEmergencyEject(player);
            return true;
        }
        boolean wasValidatedForDungeon = deValidatePlayerForDungeon(player, dungeon);
        LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon -- player [" + player + "] was validated [" + wasValidatedForDungeon + "] for this dungeon");
        boolean dungeonEmpty = false;
        if (isIdValid(dungeon) && isGameObjectTypeOf(getGameObjectType(dungeon), GOT_ship) && player == getOwner(dungeon))
        {
            setOwner(dungeon, null);
        }
        if (wasValidatedForDungeon)
        {
            dungeonEmpty = decrementDungeonParticipantCounter(dungeon, player);
            if (!dungeonEmpty)
            {
                handlePilotPassengerEject(player);
            }
        }
        CustomerServiceLog("travel", getFirstName(player) + " (" + player + ") is leaving dungeon " + getDungeonName(dungeon));
        location exit_loc = getDungeonExitLocation(dungeon);
        if (exists(dungeon))
        {
            if (hasObjVar(player, VAR_DUNGEON_ID))
            {
                removeObjVar(player, VAR_DUNGEON_ID);
            }
            if (hasObjVar(player, VAR_EJECT_OVERRIDE))
            {
                dictionary d = new dictionary();
                d.put("pid", player);
                messageTo(player, "msgEjectedFromDungeon", d, 0.0f, true);
            }
            if (exit_loc == null)
            {
                LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon --  dungeon " + dungeon + " has no exit location. Using default.");
                location playerLoc = getLocation(player);
                String playerScene = playerLoc.area;
                if (playerScene.startsWith("kashyyyk"))
                {
                    warpPlayer(player, "kashyyyk_main", -670, 18, -137, null, -670, 18, -137);
                }
                else if (playerScene.equals("mustafar"))
                {
                    warpPlayer(player, "mustafar", -2526, 224, 1659, null, -2526, 224, 1659);
                }
                else 
                {
                    warpPlayer(player, "tatooine", 3387, 0, -4333, null, 0, 0, 0);
                }
            }
            else 
            {
                LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon --  dungeon [" + dungeon + "] warping player to " + exit_loc);
                if (exit_loc.area == "tutorial")
                {
                    sendPlayerToTutorial(player);
                }
                else 
                {
                    warpPlayer(player, exit_loc.area, exit_loc.x, exit_loc.y, exit_loc.z, null, 0, 0, 0);
                }
            }
        }
        if (dungeonEmpty)
        {
            LOG("space_dungeon", "space_dungeon.ejectPlayerFromDungeon dungeon [" + dungeon + "] is empty, reinitializing");
            intializeSpaceDungeon(dungeon);
        }
        return true;
    }
    public static boolean performEmergencyEject(obj_id player) throws InterruptedException
    {
        location playerLoc = getLocation(player);
        String playerScene = playerLoc.area;
        if (playerScene.equals("kashyyyk_main") || playerScene.equals("kashyyyk_dead_forest") || playerScene.equals("kashyyyk_dead_forest"))
        {
            return false;
        }
        if (playerScene.startsWith("kashyyyk"))
        {
            warpPlayer(player, "kashyyyk_main", -670, 18, -137, null, 0, 0, 0);
            return true;
        }
        if (!playerScene.equals("dungeon1") && !playerScene.equals("mustafar"))
        {
            return false;
        }
        else if (playerScene.equals("mustafar"))
        {
            warpPlayer(player, "mustafar", -2526, 224, 1659, null, 0, 0, 0);
            return true;
        }
        else 
        {
            warpPlayer(player, "tatooine", 3387, 0, -4333, null, 0, 0, 0);
            return true;
        }
    }
    public static boolean preCheckEjectStatus(obj_id player, boolean allowGod) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "space_dungeon.preCheckEjectStatus --  player is invalid.");
            return false;
        }
        if (!isPlayer(player))
        {
            LOG("space_dungeon", "space_dungeon.preCheckEjectStatus -- " + player + " is not a player.");
            return false;
        }
        if (!allowGod && isGod(player))
        {
            LOG("space_dungeon", "space_dungeon.preCheckEjectStatus -- " + player + " is a god, skipping eject.");
            return false;
        }
        return true;
    }
    public static boolean leaveKashyyykArena(obj_id player) throws InterruptedException
    {
        warpPlayer(player, "kashyyyk_dead_forest", -302, 38, -208, null, 0, 0, 0);
        return true;
    }
    public static obj_id findValidDungeonForEject(obj_id player) throws InterruptedException
    {
        obj_id dungeon = getDungeonIdForPlayer(player);
        if (!isIdValid(dungeon))
        {
            obj_id anyDungeon = getFirstObjectWithScript(getLocation(player), 1000.0f, "theme_park.dungeon.space_dungeon_controller");
            if (!isIdValid(anyDungeon))
            {
                return null;
            }
        }
        return dungeon;
    }
    public static boolean deValidatePlayerForDungeon(obj_id player, obj_id dungeon) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.deValidatePlayerForDungeon -- player [" + player + "], dungeon [" + dungeon + "]");
        removeInstanceControllerId(player);
        removeObjVar(player, VAR_DUNGEON_ID);
        removeObjVar(player, VAR_SESSION_ID);
        removeObjVar(player, VAR_BUILDOUT_AREA);
        removeObjVar(player, VAR_BUILDOUT_ROW);
        removeObjVar(player, space_dungeon.VAR_PILOT_ID);
        removeObjVar(player, space_dungeon.VAR_PASSENGER_IDS);
        removeObjVar(player, VAR_PILOT_PREVIOUS_LOCATION);
        space_dungeon.cleanupPlayerTicketObjvars(player);
        removePlayerScriptsForDungeon(player);
        boolean wasValidatedForDungeon = false;
        dictionary dict = new dictionary();
        dict.put(PLAYER_ID, player);
        if (isIdValid(dungeon))
        {
            messageTo(dungeon, "removePlayerFromParticipantIdList", dict, 0, false);
        }
        if (utils.hasScriptVar(player, space_dungeon.SCRIPT_VAR_DUNGEON_VALIDATED))
        {
            obj_id validatedDungeonId = utils.getObjIdScriptVar(player, space_dungeon.SCRIPT_VAR_DUNGEON_VALIDATED);
            if (dungeon.equals(validatedDungeonId))
            {
                wasValidatedForDungeon = true;
            }
            else 
            {
                LOG("space_dungeon", "space_dungeon.deValidatePlayerForDungeon -- player [" + player + "] is flagged validated for [" + validatedDungeonId + "] but is in dungeon [" + dungeon + "]");
            }
            utils.removeScriptVar(player, space_dungeon.SCRIPT_VAR_DUNGEON_VALIDATED);
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.deValidatePlayerForDungeon -- player [" + player + "] was not flagged validated");
        }
        return wasValidatedForDungeon;
    }
    public static boolean removePlayerScriptsForDungeon(obj_id player, obj_id dungeon) throws InterruptedException
    {
        return removePlayerScriptsForDungeon(player);
    }
    public static boolean removePlayerScriptsForDungeon(obj_id player) throws InterruptedException
    {
        String[] dungeonScriptList = dataTableGetStringColumn(space_dungeon_data.DUNGEON_DATATABLE, "player_script");
        if (dungeonScriptList != null && dungeonScriptList.length > 0)
        {
            for (int i = 0; i < dungeonScriptList.length; i++)
            {
                if (hasScript(player, dungeonScriptList[i]))
                {
                    detachScript(player, dungeonScriptList[i]);
                }
            }
        }
        return true;
    }
    public static boolean decrementDungeonParticipantCounter(obj_id dungeon, obj_id player) throws InterruptedException
    {
        if (null == player || dungeon == null)
        {
            LOG("space_dungeon", "space_dungeon.decrementDungeonParticipantCounter dungeon=" + dungeon + ", PLAYER IS NULL");
            return false;
        }
        Vector participantIdVector = getResizeableObjIdArrayObjVar(dungeon, VAR_PARTICIPANT_IDS);
        if (null == participantIdVector)
        {
            LOG("space_dungeon", "space_dungeon.decrementDungeonParticipantCounter dungeon=" + dungeon + ", player=" + player + ", has null participant id vector");
            return true;
        }
        int foundIndex = participantIdVector.indexOf(player);
        if (foundIndex < 0)
        {
            LOG("space_dungeon", "space_dungeon.decrementDungeonParticipantCounter dungeon=" + dungeon + ", player=" + player + ", NOT FOUND on participant id vector=" + participantIdVector.size());
            return false;
        }
        participantIdVector.remove(foundIndex);
        try
        {
            setObjVar(dungeon, VAR_PARTICIPANT_IDS, participantIdVector, resizeableArrayTypeobj_id);
        }
        catch(Throwable err)
        {
        }
        return participantIdVector.size() <= 0;
    }
    public static void incrementDungeonParticipantCounter(obj_id dungeon, obj_id player) throws InterruptedException
    {
        if (null == player)
        {
            LOG("space_dungeon", "space_dungeon.incrementDungeonParticipantCounter dungeon=" + dungeon + ", PLAYER IS NULL");
            return;
        }
        Vector participantIdVector = getResizeableObjIdArrayObjVar(dungeon, VAR_PARTICIPANT_IDS);
        if (null == participantIdVector)
        {
            participantIdVector = new Vector();
        }
        int foundIndex = participantIdVector.indexOf(player);
        if (foundIndex >= 0)
        {
            LOG("space_dungeon", "space_dungeon.incrementDungeonParticipantCounter dungeon=" + dungeon + ", player=" + player + ", ALREADY ON on participant id vector=" + participantIdVector.size());
            return;
        }
        participantIdVector.add(player);
        setObjVar(dungeon, VAR_PARTICIPANT_IDS, participantIdVector, resizeableArrayTypeobj_id);
    }
    public static void setDungeonTimeObjVars(obj_id controllerId) throws InterruptedException
    {
        setObjVar(controllerId, VAR_DUNGEON_START_TIME, getGameTime());
        int dungeonSessionMaxTime = space_dungeon_data.getMaxDungeonSesssionTime(getDungeonName(controllerId));
        int sessionEnd = getGameTime() + dungeonSessionMaxTime;
        setObjVar(controllerId, VAR_DUNGEON_END_TIME, sessionEnd);
    }
    public static boolean isDungeonFull(obj_id dungeon) throws InterruptedException
    {
        Vector participantIdVector = getResizeableObjIdArrayObjVar(dungeon, VAR_PARTICIPANT_IDS);
        int participants = (null == participantIdVector) ? 0 : participantIdVector.size();
        int max_participants = space_dungeon.getDungeonMaxPlayers(dungeon);
        return max_participants < participants;
    }
    public static void handlePilotPassengerEject(obj_id player) throws InterruptedException
    {
        obj_id pilot = getObjIdObjVar(player, VAR_PILOT_ID);
        if (null != pilot)
        {
            if (pilot.equals(player))
            {
                Vector passengers = getResizeableObjIdArrayObjVar(pilot, VAR_PASSENGER_IDS);
                for (Iterator it = passengers.iterator(); it.hasNext(); )
                {
                    obj_id passenger = (obj_id)it.next();
                    if (!pilot.equals(passenger))
                    {
                        prose_package ppPilotEjected = prose.getPackage(SID_YOUR_PILOT_EJECTED);
                        prose.setTT(ppPilotEjected, pilot);
                        sendSystemMessageProse(passenger, ppPilotEjected);
                        prose_package ppPassBehind = prose.getPackage(SID_YOU_LEFT_A_PASSENGER_BEHIND);
                        prose.setTT(ppPassBehind, passenger);
                        sendSystemMessageProse(player, ppPassBehind);
                        removeObjVar(passenger, VAR_PILOT_ID);
                    }
                }
            }
            else 
            {
                prose_package ppPassEjected = prose.getPackage(SID_PASSENGER_HAS_EJECTED);
                prose.setTT(ppPassEjected, player);
                sendSystemMessageProse(pilot, ppPassEjected);
                Vector passengers = getResizeableObjIdArrayObjVar(pilot, VAR_PASSENGER_IDS);
                for (Iterator it = passengers.iterator(); it.hasNext(); )
                {
                    obj_id passenger = (obj_id)it.next();
                    if (player.equals(passenger))
                    {
                        it.remove();
                        continue;
                    }
                    if (passenger != pilot)
                    {
                        prose_package ppCoEject = prose.getPackage(SID_COPASSENGER_HAS_EJECTED);
                        prose.setTT(ppCoEject, player);
                        sendSystemMessageProse(passenger, ppCoEject);
                    }
                }
                setObjVar(pilot, VAR_PASSENGER_IDS, passengers, resizeableArrayTypeobj_id);
            }
            dictionary d = new dictionary();
            d.put("pid", player);
            messageTo(pilot, "msgNotifyPilotEjectFromDungeon", d, 0.0f, false);
        }
    }
    public static boolean handlePilotLaunch(obj_id player, obj_id dungeon) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "], dungeon [" + dungeon + "]");
        obj_id pilot = getObjIdObjVar(player, VAR_PILOT_ID);
        if (null == pilot)
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] does not have a VAR_PILOT_ID objvar");
            sendSystemMessage(player, SID_FIND_ESCAPE_POD);
            return false;
        }
        if (pilot != player)
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] is not a pilot");
            sendSystemMessage(player, SID_YOU_DID_NOT_PILOT);
            if (isValidId(pilot))
            {
                prose_package ppRideOut = prose.getPackage(SID_CATCH_A_RIDE_WITH_PILOT);
                prose.setTT(ppRideOut, pilot);
                sendSystemMessageProse(player, ppRideOut);
            }
            else 
            {
                sendSystemMessage(player, SID_PILOT_HAS_LEFT_YOU);
            }
            return false;
        }
        location pilotLocation = getLocation(player);
        Vector passengersToLaunch = new Vector();
        passengersToLaunch.setSize(0);
        Vector passengers = getResizeableObjIdArrayObjVar(pilot, VAR_PASSENGER_IDS);
        if (null == passengers)
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] has no potential passengers");
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] has [" + passengers.size() + "] potential passengers");
            for (Iterator it = passengers.iterator(); it.hasNext(); )
            {
                obj_id passenger = (obj_id)it.next();
                if (!isIdValid(passenger))
                {
                    sendSystemMessage(pilot, SID_PASSENGER_NO_LONGER_WITH_YOU);
                    continue;
                }
                if (passenger.equals(pilot))
                {
                    continue;
                }
                location passengerLocation = getLocation(passenger);
                float distanceToPilot = pilotLocation.distance(passengerLocation);
                if (distanceToPilot > 16.0f)
                {
                    sendSystemMessage(passenger, SID_PILOT_LAUNCHED_WITHOUT_YOU);
                    removeObjVar(passenger, VAR_PILOT_ID);
                    prose_package ppLeftBehind = prose.getPackage(SID_PASSENGER_LEFT_BEHIND);
                    prose.setTT(ppLeftBehind, passenger);
                    sendSystemMessageProse(pilot, ppLeftBehind);
                    continue;
                }
                obj_id passengerDungeon = findValidDungeonForEject(passenger);
                if (dungeon != passengerDungeon)
                {
                    LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- passenger [" + passenger + "] is not in the same dungeon as pilot");
                    prose_package ppCannotCome = prose.getPackage(SID_PASSENGER_CANNOT_COME);
                    prose.setTT(ppCannotCome, passenger);
                    sendSystemMessageProse(pilot, ppCannotCome);
                }
                else 
                {
                    obj_id passengerPilotId = getObjIdObjVar(passenger, VAR_PILOT_ID);
                    if (passengerPilotId != player)
                    {
                        LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- passenger [" + passenger + "] pilot mismatch");
                        prose_package ppNotPilot = prose.getPackage(SID_DOESNT_THINK_YOUR_A_PILOT);
                        prose.setTT(ppNotPilot, passenger);
                        sendSystemMessageProse(pilot, ppNotPilot);
                    }
                    else 
                    {
                        prose_package ppNowBoardingPass = prose.getPackage(SID_NOW_BOARDING_PASSENGER);
                        prose.setTT(ppNowBoardingPass, passenger);
                        sendSystemMessageProse(pilot, ppNowBoardingPass);
                        prose_package ppNowBoarding = prose.getPackage(SID_YOU_ARE_NOW_BOARDING);
                        prose.setTT(ppNowBoarding, pilot);
                        sendSystemMessageProse(passenger, ppNowBoarding);
                        boolean wasValidatedForDungeon = deValidatePlayerForDungeon(passenger, dungeon);
                        LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- passenger [" + passenger + "] was validated [" + wasValidatedForDungeon + "] for this dungeon");
                        removePlayerScriptsForDungeon(passenger, dungeon);
                        if (wasValidatedForDungeon)
                        {
                            decrementDungeonParticipantCounter(dungeon, player);
                        }
                        removeObjVar(passenger, VAR_PILOT_ID);
                        removeObjVar(passenger, VAR_SESSION_ID);
                        passengersToLaunch = utils.addElement(passengersToLaunch, passenger);
                    }
                }
            }
        }
        LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] computed [" + passengersToLaunch.size() + "] potential passengers");
        location pilotPreviousLocation = getLocationObjVar(player, VAR_PILOT_PREVIOUS_LOCATION);
        if (null == pilotPreviousLocation)
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch -- player [" + player + "] pilotPreviousLocation invalid");
            pilotPreviousLocation = new location();
            pilotPreviousLocation.area = "space_tatooine";
            pilotPreviousLocation.x = 0.0f;
            pilotPreviousLocation.y = 0.0f;
            pilotPreviousLocation.z = 0.0f;
        }
        obj_id ship = getObjIdObjVar(player, VAR_PILOT_SHIP_ID);
        removeObjVar(player, VAR_PASSENGER_IDS);
        removeObjVar(player, VAR_PILOT_ID);
        removeObjVar(player, VAR_PILOT_SHIP_ID);
        removeObjVar(player, VAR_PILOT_PREVIOUS_LOCATION);
        removeObjVar(player, VAR_SESSION_ID);
        location groundLoc = getLocation(player);
        space_transition.launch(player, ship, utils.toStaticObjIdArray(passengersToLaunch), pilotPreviousLocation, groundLoc);
        boolean dungeonEmpty = decrementDungeonParticipantCounter(dungeon, player);
        if (dungeonEmpty)
        {
            LOG("space_dungeon", "space_dungeon.handlePilotLaunch dungeon [" + dungeon + "] is empty, reinitializing");
            intializeSpaceDungeon(dungeon);
        }
        deValidatePlayerForDungeon(player, dungeon);
        return true;
    }
    public static boolean launchPlayerFromDungeon(obj_id player) throws InterruptedException
    {
        LOG("space_dungeon", "space_dungeon.launchPlayerFromDungeon");
        if (!preCheckEjectStatus(player, true))
        {
            LOG("space_dungeon", "space_dungeon.launchPlayerFromDungeon -- preCheckEjectStatus failed");
            return false;
        }
        obj_id dungeon = findValidDungeonForEject(player);
        if (null == dungeon)
        {
            LOG("space_dungeon", "space_dungeon.launchPlayerFromDungeon -- dungeon is null");
            return false;
        }
        boolean dungeonEmpty = false;
        if (!handlePilotLaunch(player, dungeon))
        {
            utils.setScriptVar(player, space_dungeon.SCRIPT_VAR_DUNGEON_VALIDATED, dungeon);
        }
        return false;
    }
    public static boolean endDungeonSession(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "space_dungeon.endDungeonSession -- dungeon is invalid");
            return false;
        }
        obj_id[] players = getParticipantIds(dungeon);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (exists(players[i]))
                {
                    sendSystemMessage(players[i], SID_SESSION_TIME_ENDED);
                    ejectPlayerFromDungeon(players[i]);
                }
            }
        }
        intializeSpaceDungeon(dungeon);
        return true;
    }
    public static boolean verifyPlayerSession(obj_id dungeon, obj_id player) throws InterruptedException
    {
        return verifyPlayerSession(player);
    }
    public static boolean verifyPlayerSession(obj_id player) throws InterruptedException
    {
        location playerLoc = getLocation(trial.getTop(player));
        obj_id top = getTopMostContainer(player);
        String template = getTemplateName(top);
        boolean isCorvette = false;
        if (template.indexOf("dungeon_corellian_corvette") > -1)
        {
            isCorvette = true;
        }
        String buildout_table = "datatables/buildout/areas_" + playerLoc.area + ".iff";
        float locX = playerLoc.x;
        float locZ = playerLoc.z;
        obj_id dungeon = findValidDungeonForEject(player);
        if (!dataTableOpen(buildout_table) && !isCorvette)
        {
            deValidatePlayerForDungeon(player, dungeon);
            return false;
        }
        int rows = dataTableGetNumRows(buildout_table);
        String area_name = locations.getBuildoutAreaName(playerLoc);
        String player_saved_area = getStringObjVar(player, VAR_BUILDOUT_AREA);
        if (area_name == null && !isCorvette)
        {
            deValidatePlayerForDungeon(player, dungeon);
            return false;
        }
        int dungeon_row = dataTableSearchColumnForString(area_name, "dungeon_area", space_dungeon_data.DUNGEON_DATATABLE);
        if (dungeon_row == -1 && !isCorvette)
        {
            deValidatePlayerForDungeon(player, dungeon);
            return false;
        }
        if ((player_saved_area == null || player_saved_area.equals("")) && dungeon_row > -1 && !isCorvette)
        {
            ejectPlayerFromDungeon(player);
            return false;
        }
        String dungeon_area = dataTableGetString(space_dungeon_data.DUNGEON_DATATABLE, dungeon_row, "dungeon_area");
        if ((player_saved_area != null && !player_saved_area.equals("")) && (!player_saved_area.equals(area_name)) && (!player_saved_area.equals(dungeon_area)) && !isCorvette)
        {
            ejectPlayerFromDungeon(player);
            return false;
        }
        if (!isCorvette)
        {
            int playerBuildoutRow = getIntObjVar(player, VAR_BUILDOUT_ROW);
            int locationRow = locations.getBuildoutAreaRow(player);
            if (playerBuildoutRow != locationRow)
            {
                ejectPlayerFromDungeon(player);
                return false;
            }
        }
        obj_id dungeonController = getDungeonIdForPlayer(player);
        if (!isIdValid(dungeonController) || !exists(dungeonController) || !hasScript(dungeonController, "theme_park.dungeon.space_dungeon_controller"))
        {
            ejectPlayerFromDungeon(player);
            return false;
        }
        int dungeon_session_id = space_dungeon.getDungeonSessionId(dungeonController);
        int player_session_id = space_dungeon.getDungeonSessionId(player);
        if (dungeon_session_id == -1 || dungeon_session_id != player_session_id)
        {
            ejectPlayerFromDungeon(player);
            return false;
        }
        utils.setScriptVar(player, space_dungeon.SCRIPT_VAR_DUNGEON_VALIDATED, dungeonController);
        return true;
    }
    public static boolean isInSpaceDungeon(obj_id subject) throws InterruptedException
    {
        String area_name = locations.getBuildoutAreaName(getLocation(subject));
        int dungeon_row = dataTableSearchColumnForString(area_name, "dungeon_area", space_dungeon_data.DUNGEON_DATATABLE);
        return dungeon_row > -1;
    }
    public static void registerObjectWithDungeon(obj_id dungeon, obj_id registeringObject) throws InterruptedException
    {
        Vector registered = utils.getResizeableObjIdArrayScriptVar(dungeon, VAR_REGISTERED_OBJECTS);
        if (null == registered)
        {
            registered = new Vector();
        }
        registered.add(registeringObject);
        utils.setScriptVar(dungeon, VAR_REGISTERED_OBJECTS, registered);
    }
    public static obj_id[] getRegisteredObjects(obj_id dungeon) throws InterruptedException
    {
        Vector registered = utils.getResizeableObjIdArrayScriptVar(dungeon, VAR_REGISTERED_OBJECTS);
        if (null == registered)
        {
            return null;
        }
        return utils.toStaticObjIdArray(registered);
    }
    public static obj_id[] getParticipantIds(obj_id dungeon) throws InterruptedException
    {
        Vector participantId = getResizeableObjIdArrayObjVar(dungeon, VAR_PARTICIPANT_IDS);
        if (null == participantId || participantId.size() == 0)
        {
            return null;
        }
        obj_id[] returnList = new obj_id[0];
        if (participantId != null)
        {
            returnList = new obj_id[participantId.size()];
            participantId.toArray(returnList);
        }
        return returnList;
    }
    public static void playMusicInInstance(obj_id dungeon, String sound) throws InterruptedException
    {
        obj_id[] players = getParticipantIds(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int r = 0; r < players.length; r++)
        {
            playMusic(players[r], sound);
        }
    }
    public static void sendInstanceSystemMessage(obj_id dungeon, string_id message) throws InterruptedException
    {
        obj_id[] players = getParticipantIds(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        utils.sendSystemMessage(players, message);
    }
    public static int calculateNextMessage(obj_id dungeon) throws InterruptedException
    {
        int currentTime = getGameTime();
        int sessionEnd = getIntObjVar(dungeon, space_dungeon.VAR_DUNGEON_END_TIME);
        int sessionTimeRemaining = sessionEnd - currentTime;
        if (sessionTimeRemaining > 10)
        {
            if (sessionTimeRemaining > 60)
            {
                if (sessionTimeRemaining > 300)
                {
                    if (sessionTimeRemaining > 600)
                    {
                        if (sessionTimeRemaining > 900)
                        {
                            if (sessionTimeRemaining > 1800)
                            {
                                if (sessionTimeRemaining > 3600)
                                {
                                    return ((sessionEnd - 3600) - currentTime);
                                }
                                return ((sessionEnd - 1800) - currentTime);
                            }
                            return ((sessionEnd - 900) - currentTime);
                        }
                        return ((sessionEnd - 600) - currentTime);
                    }
                    return ((sessionEnd - 300) - currentTime);
                }
                return ((sessionEnd - 60) - currentTime);
            }
            return ((sessionEnd - 10) - currentTime);
        }
        return sessionEnd - currentTime;
    }
    public static void setInstanceControllerIdOnPlayer(obj_id player, obj_id controllerObject) throws InterruptedException
    {
        setObjVar(player, INSTANCE_ID, controllerObject);
    }
    public static void removeInstanceControllerId(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, INSTANCE_ID))
        {
            removeObjVar(player, INSTANCE_ID);
        }
    }
    public static obj_id getInstanceControllerId(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, INSTANCE_ID))
        {
            return getObjIdObjVar(player, INSTANCE_ID);
        }
        else 
        {
            return obj_id.NULL_ID;
        }
    }
    public static boolean validateInstanceControllerId(obj_id player) throws InterruptedException
    {
        if (!hasObjVar(player, INSTANCE_ID))
        {
            doLogging("validateInstanceControllerId", "Player did not have the instance objvar, and thus is not in an instance");
            return false;
        }
        obj_id controllerObject = getObjIdObjVar(player, INSTANCE_ID);
        doLogging("validateInstanceControllerId", "controllerObject = " + controllerObject);
        doLogging("validateInstanceControllerId", "exists precheck: " + exists(controllerObject));
        if (!exists(controllerObject) || controllerObject == obj_id.NULL_ID)
        {
            doLogging("validateInstanceControllerId", "controller object does not exist on this server or null. Removing objvar");
            removeObjVar(player, INSTANCE_ID);
            return false;
        }
        return true;
    }
    public static boolean isCloningInstance(obj_id player) throws InterruptedException
    {
        if (!validateInstanceControllerId(player))
        {
            return false;
        }
        String dataTable = cloninglib.DATATABLE_CLONE_SPAWN;
        obj_id controllerId = getInstanceControllerId(player);
        String template = getTemplateName(controllerId);
        if (dataTableSearchColumnForString(template, 0, dataTable) > -1)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isInValidInstance(obj_id player) throws InterruptedException
    {
        return validateInstanceControllerId(player);
    }
    public static void handleInstanceClone(obj_id player) throws InterruptedException
    {
        String dataTable = cloninglib.DATATABLE_CLONE_SPAWN;
        if (!hasObjVar(player, INSTANCE_ID))
        {
            debugConsoleMsg(player, "Instance Cloning.handleInstanceClone -- Player is calling for an instance clone but does not have a valid controller objVar");
            sendSystemMessageTestingOnly(player, "You died without knowning your controller.  Did you enter the instance properly?");
            location currentLoc = getLocation(player);
            warpPlayer(player, currentLoc.area, currentLoc.x, currentLoc.y, currentLoc.z, null, currentLoc.x, currentLoc.y, currentLoc.z, "handleInstanceRessurect", true);
        }
        obj_id controllerId = getInstanceControllerId(player);
        String template = getTemplateName(controllerId);
        location controllerLoc = getLocation(controllerId);
        if (dataTableSearchColumnForString(template, 0, dataTable) > -1)
        {
            float locX = dataTableGetFloat(dataTable, template, "X");
            float locY = dataTableGetFloat(dataTable, template, "Y");
            float locZ = dataTableGetFloat(dataTable, template, "Z");
            String cell = dataTableGetString(dataTable, template, "CELL");
            if (cell.equals("") || cell == null)
            {
                locX += controllerLoc.x;
                locY += controllerLoc.y;
                locZ += controllerLoc.z;
                warpPlayer(player, controllerLoc.area, locX, locY, locZ, null, locX, locY, locZ, "handleInstanceRessurect", true);
            }
            else 
            {
                warpPlayer(player, controllerLoc.area, locX, locY, locZ, controllerId, cell, locX, locY, locZ, "handleInstanceRessurect", true);
            }
        }
        else 
        {
            doLogging("handleInstanceClone", "Controller was not in the spawn table.  Respawning at location.");
            debugConsoleMsg(player, "Instance CloninghandleInstanceClone -- Players(" + player + ") controller object(" + template + "/" + controllerId + ") was not in the cloning table");
            String[] cellNames = getCellNames(controllerId);
            if (cellNames == null || cellNames.length < 1)
            {
                doLogging("handleInstanceClone", "Controller object has no cell, spawning at object");
                warpPlayer(player, controllerLoc.area, controllerLoc.x, controllerLoc.y, controllerLoc.z, null, 0, 0, 0, "handleInstanceRessurect", true);
            }
            else 
            {
                doLogging("handleInstanceClone", "Spawning in 1st cell");
                warpPlayer(player, controllerLoc.area, 0, 0, 0, controllerId, cellNames[0], 0, 0, 0, "handleInstanceRessurect", true);
            }
        }
    }
    public static void doLogging(String section, String message) throws InterruptedException
    {
        if (LOGGING)
        {
            LOG("logging/library.space_dungeon/" + section, message);
        }
    }
    public static int pollZoneOccupantsForInstancePopulation(obj_id controller) throws InterruptedException
    {
        int numberOfPlayers = 0;
        obj_id[] players = getPlayerCreaturesInRange(controller, 32000);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (hasObjVar(players[i], INSTANCE_ID))
                {
                    if (getObjIdObjVar(players[i], INSTANCE_ID) == controller)
                    {
                        numberOfPlayers++;
                    }
                }
            }
        }
        return numberOfPlayers;
    }
    public static obj_id[] getPlayersInInstance(obj_id controller) throws InterruptedException
    {
        Vector playersInInstance = new Vector();
        playersInInstance.setSize(0);
        obj_id[] players = getPlayerCreaturesInRange(controller, 32000);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                if (hasObjVar(players[i], INSTANCE_ID))
                {
                    if (getObjIdObjVar(players[i], INSTANCE_ID) == controller)
                    {
                        playersInInstance = utils.addElement(playersInInstance, players[i]);
                    }
                }
            }
        }
        if (playersInInstance != null && playersInInstance.size() > 0)
        {
            obj_id[] convertedList = new obj_id[0];
            if (playersInInstance != null)
            {
                convertedList = new obj_id[playersInInstance.size()];
                playersInInstance.toArray(convertedList);
            }
            return convertedList;
        }
        else 
        {
            return null;
        }
    }
    public static void setDungeonLockoutTimer(obj_id player, obj_id dungeon, int lockoutTime) throws InterruptedException
    {
        String dungeonName = getDungeonName(dungeon);
        setDungeonLockoutTimer(player, dungeonName, lockoutTime);
    }
    public static void setDungeonLockoutTimer(obj_id player, String dungeonName, int lockoutTime) throws InterruptedException
    {
        lockoutTime = getGameTime() + lockoutTime;
        if (isIdValid(player) && exists(player))
        {
            setObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName, lockoutTime);
        }
    }
    public static void setDungeonLockoutTimer(obj_id player, obj_id dungeon) throws InterruptedException
    {
        String dungeonName = getDungeonName(dungeon);
        int lockoutTime = dataTableGetInt(space_dungeon_data.DUNGEON_DATATABLE, dungeonName, "lockoutTimer");
        setDungeonLockoutTimer(player, dungeon, lockoutTime);
    }
    public static void setDungeonLockoutTimer(obj_id[] players, obj_id dungeon) throws InterruptedException
    {
        if (players == null || players.length == 0 || !isIdValid(dungeon))
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            setDungeonLockoutTimer(players[i], dungeon);
        }
    }
    public static void setDungeonLockoutTimer(obj_id[] players, obj_id dungeon, int time) throws InterruptedException
    {
        if (players == null || players.length == 0 || !isIdValid(dungeon))
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            setDungeonLockoutTimer(players[i], dungeon, time);
        }
    }
    public static void setDungeonLockoutTimer(obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            return;
        }
        obj_id[] players = space_dungeon.getParticipantIds(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        setDungeonLockoutTimer(players, dungeon);
    }
    public static void setDungeonLockoutTimer(obj_id dungeon, int time) throws InterruptedException
    {
        if (!isIdValid(dungeon))
        {
            return;
        }
        obj_id[] players = space_dungeon.getParticipantIds(dungeon);
        if (players == null || players.length == 0)
        {
            return;
        }
        setDungeonLockoutTimer(players, dungeon, time);
    }
    public static void addToDungeonLockoutTimer(obj_id player, obj_id dungeon, int time) throws InterruptedException
    {
        String dungeonName = getDungeonName(dungeon);
        addToDungeonLockoutTimer(player, dungeonName, time);
    }
    public static void addToDungeonLockoutTimer(obj_id player, String dungeonName, int time) throws InterruptedException
    {
        if (hasObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName))
        {
            int current = getIntObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName);
            current += time;
            setObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName, current);
        }
        else 
        {
            setDungeonLockoutTimer(player, dungeonName, time);
        }
    }
    public static void addToDungeonLockoutTimer(obj_id[] players, obj_id dungeon, int time) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            if (isIdValid(players[i]))
            {
                addToDungeonLockoutTimer(players[i], dungeon, time);
            }
        }
    }
    public static boolean validateInstanceTimerForPlayer(obj_id player, String dungeonName) throws InterruptedException
    {
        if (!hasObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName))
        {
            return true;
        }
        int endLockoutTime = getIntObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName);
        if (getGameTime() >= endLockoutTime)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean isDungeonLockoutTimerExpired(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(dungeon))
        {
            return false;
        }
        obj_id groupId = getGroupObject(player);
        String dungeonName = getDungeonName(dungeon);
        if (!isIdValid(groupId))
        {
            return validateInstanceTimerForPlayer(player, dungeonName);
        }
        else 
        {
            obj_id[] members = getGroupMemberIds(groupId);
            return isDungeonLockoutTimerExpired(members, dungeonName);
        }
    }
    public static boolean isDungeonLockoutTimerExpired(obj_id player, String dungeonName) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id groupId = getGroupObject(player);
        if (!isIdValid(groupId))
        {
            return validateInstanceTimerForPlayer(player, dungeonName);
        }
        else 
        {
            obj_id[] members = getGroupMemberIds(groupId);
            return isDungeonLockoutTimerExpired(members, dungeonName);
        }
    }
    public static boolean isDungeonLockoutTimerExpired(obj_id players[], String dungeonName) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return false;
        }
        for (obj_id player : players) {
            if (isPlayer(player) && !validateInstanceTimerForPlayer(player, dungeonName)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isDungeonLockoutTimerExpired(obj_id players[], obj_id dungeon) throws InterruptedException
    {
        if (players == null || players.length == 0 || !isIdValid(dungeon))
        {
            return false;
        }
        String dungeonName = getDungeonName(dungeon);
        for (int i = 0; i < players.length; i++)
        {
            if (!validateInstanceTimerForPlayer(players[i], dungeonName))
            {
                return false;
            }
        }
        return true;
    }
    public static int getDungeonLockoutTime(obj_id player, obj_id dungeon) throws InterruptedException
    {
        String dungeonName = getDungeonName(dungeon);
        return getDungeonLockoutTime(player, dungeonName);
    }
    public static int getDungeonLockoutTime(obj_id player, String dungeonName) throws InterruptedException
    {
        if (hasObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName))
        {
            int recordTime = getIntObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName);
            int remaining = recordTime - getGameTime();
            return remaining;
        }
        else 
        {
            return 0;
        }
    }
    public static void clearDungeonLockoutTimer(obj_id player, String dungeonName) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (hasObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName))
        {
            removeObjVar(player, LIST_DUNGEON_LOCKOUT + dungeonName);
        }
    }
    public static void clearDungeonLockoutTimer(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(dungeon))
        {
            return;
        }
        String dungeonName = getDungeonName(dungeon);
        clearDungeonLockoutTimer(player, dungeonName);
    }
    public static void clearAllDungeonLockoutTimers(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        Vector dungeonLocks = new Vector();
        dungeonLocks.setSize(0);
        String[] validDungeons = dataTableGetStringColumn(space_dungeon_data.DUNGEON_DATATABLE, 0);
        for (int i = 0; i < validDungeons.length; i++)
        {
            if (hasObjVar(player, LIST_DUNGEON_LOCKOUT + validDungeons[i]))
            {
                utils.addElement(dungeonLocks, validDungeons[i]);
            }
        }
        if (dungeonLocks == null || dungeonLocks.size() == 0)
        {
            return;
        }
        for (int k = 0; k < dungeonLocks.size(); k++)
        {
            clearDungeonLockoutTimer(player, ((String)dungeonLocks.get(k)));
        }
    }
    public static void clearAllDungeonLockoutTimers(obj_id[] players) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            clearAllDungeonLockoutTimers(players[i]);
        }
    }
    public static void displayDungeonLockoutTimerSUI(obj_id player, String dungeonName) throws InterruptedException
    {
        obj_id groupId = getGroupObject(player);
        if (!isIdValid(groupId))
        {
            prose_package pp = new prose_package();
            pp = prose.setStringId(pp, SID_LOCKOUT_MESSAGE);
            pp = prose.setTT(pp, new string_id(DUNGEON_STF, dungeonName));
            int lockoutTime = getDungeonLockoutTime(player, dungeonName);
            String lockoutTimeVerbose = utils.formatTimeVerbose(lockoutTime);
            pp = prose.setTO(pp, lockoutTimeVerbose);
            String title = "@" + SID_LOCKOUT_TITLE;
            sui.msgbox(player, player, pp, sui.OK_ONLY, title, sui.MSG_NORMAL, "noHandler");
        }
        else 
        {
            obj_id[] members = getGroupMemberIds(groupId);
            displayDungeonLockoutTimerSUI(members, dungeonName);
        }
    }
    public static void displayDungeonLockoutTimerSUI(obj_id player, obj_id dungeon) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(dungeon))
        {
            return;
        }
        String dungeonName = getDungeonName(dungeon);
        displayDungeonLockoutTimerSUI(player, dungeonName);
    }
    public static void displayDungeonLockoutTimerSUI(obj_id[] players, String dungeonName) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return;
        }
        String title = "@" + SID_LOCKOUT_TITLE;
        String body = "@" + SID_GROUP_BODY;
        String[] invalidPlayers = getTimeLockedPlayers(players, dungeonName);
        if (invalidPlayers == null || invalidPlayers.length == 0)
        {
            return;
        }
        for (int i = 0; i < players.length; i++)
        {
            sui.listbox(players[i], players[i], body, sui.OK_ONLY, title, invalidPlayers, "noHandle", true);
        }
    }
    public static String[] getTimeLockedPlayers(obj_id[] players, String dungeonName) throws InterruptedException
    {
        if (players == null || players.length == 0)
        {
            return null;
        }
        Vector lockedPlayers = new Vector();
        lockedPlayers.setSize(0);
        for (int i = 0; i < players.length; i++)
        {
            if (!validateInstanceTimerForPlayer(players[i], dungeonName))
            {
                utils.addElement(lockedPlayers, players[i]);
            }
        }
        if (lockedPlayers == null || lockedPlayers.size() == 0)
        {
            return null;
        }
        String[] playerList = new String[lockedPlayers.size()];
        for (int k = 0; k < lockedPlayers.size(); k++)
        {
            playerList[k] = getName(((obj_id)lockedPlayers.get(k)));
        }
        return playerList;
    }
    public static void displayAllDungeonLockoutTimers(obj_id player) throws InterruptedException
    {
        String[] allDungeons = dataTableGetStringColumn(space_dungeon_data.DUNGEON_DATATABLE, 0);
        Vector hasDungeons = new Vector();
        hasDungeons.setSize(0);
        for (int i = 0; i < allDungeons.length; i++)
        {
            if (hasObjVar(player, LIST_DUNGEON_LOCKOUT + allDungeons[i]))
            {
                utils.addElement(hasDungeons, allDungeons[i]);
            }
        }
        if (hasDungeons == null || hasDungeons.size() == 0)
        {
            String body = "@" + SID_NO_LOCKOUT;
            String title = "@" + SID_LOCKOUT_TITLE;
            sui.msgbox(player, player, body, sui.OK_ONLY, title, sui.MSG_NORMAL, "noHandler");
            return;
        }
        prose_package[] pp = new prose_package[hasDungeons.size()];
        for (int k = 0; k < hasDungeons.size(); k++)
        {
            pp[k] = prose.setStringId(pp[k], SID_LOCKOUT_MESSAGE);
            pp[k] = prose.setTT(pp[k], new string_id(DUNGEON_STF, ((String)hasDungeons.get(k))));
            int lockoutTime = getDungeonLockoutTime(player, ((String)hasDungeons.get(k)));
            String lockoutTimeVerbose = utils.formatTimeVerbose(lockoutTime);
            pp[k] = prose.setTO(pp[k], lockoutTimeVerbose);
        }
        String body = "@" + SID_SELF_BODY;
        String title = "@" + SID_LOCKOUT_TITLE;
        sui.listbox(player, player, body, sui.OK_ONLY, title, pp, "noHandler", true, false);
    }
}
