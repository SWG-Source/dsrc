package script.library;

import script.dictionary;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Vector;

public class travel extends script.base_script
{
    public travel()
    {
    }
    public static final String STARPORT_DATATABLE = "datatables/structure/municipal/starport.iff";
    public static final String DATATABLE_COL_STRUCTURE = "STRUCTURE";
    public static final String DATATABLE_COL_ARRIVAL_X = "ARRIVAL_X";
    public static final String DATATABLE_COL_ARRIVAL_Y = "ARRIVAL_Y";
    public static final String DATATABLE_COL_ARRIVAL_Z = "ARRIVAL_Z";
    public static final String DATATABLE_COL_ARRIVAL_CELL = "ARRIVAL_CELL";
    public static final String DATATABLE_COL_GROUND_TIME = "GROUND_TIME";
    public static final String DATATABLE_COL_AIR_TIME = "AIR_TIME";
    public static final String DATATABLE_COL_IS_SHUTTLEPORT = "IS_SHUTTLEPORT";
    public static final String DATATABLE_COL_VERSION = "VERSION";
    public static final String DATATABLE_COL_OBJECT = "OBJECT";
    public static final String DATATABLE_COL_X = "X";
    public static final String DATATABLE_COL_Y = "Y";
    public static final String DATATABLE_COL_Z = "Z";
    public static final String DATATABLE_COL_CELL = "CELL";
    public static final String DATATABLE_COL_HEADING = "HEADING";
    public static final String DATATABLE_COL_IS_TERMINAL = "IS_TERMINAL";
    public static final String DATATABLE_COL_IS_TRANSPORT = "IS_TRANSPORT";
    public static final String DATATABLE_COL_IS_PILOT = "IS_PILOT";
    public static final String SCRIPT_STARPORT = "structure.municipal.starport";
    public static final String SCRIPT_TERMINAL = "terminal.terminal_travel";
    public static final String SCRIPT_SHUTTLE = "item.travel_ticket.travel_shuttle";
    public static final String SCRIPT_SHUTTLE_PILOT = "item.travel_ticket.travel_shuttle_pilot";
    public static final String BASE_TRAVEL_TICKET = "object/tangible/travel/travel_ticket/base/base_travel_ticket.iff";
    public static final String TRAVEL_COUPON_TEMPLATE = "object/tangible/item/new_player/new_player_travel_coupon.iff";
    public static final float MAXIMUM_SHUTTLE_DISTANCE = 25.0f;
    public static final float MAXIMUM_TERMINAL_DISTANCE = 15.0f;
    public static final float MIN_RANDOM_DISTANCE = 2.0f;
    public static final float MAX_RANDOM_DISTANCE = 5.0f;
    public static final int CURRENT_VERSION = 3;
    public static final String VAR_TRAVEL = "travel";
    public static final String VAR_TRAVEL_POINT_NAME = "travel.point_name";
    public static final String VAR_ARRIVAL_LOCATION = "travel.arrival_location";
    public static final String VAR_TRAVEL_COST = "travel.cost";
    public static final String VAR_BASE_OBJECT = "travel.base_object";
    public static final String VAR_GROUND_TIME = "travel.ground_time";
    public static final String VAR_AIR_TIME = "travel.air_time";
    public static final String VAR_IS_SHUTTLEPORT = "travel.is_shuttleport";
    public static final String VAR_SHUTTLE_AVAILABLE = "travel.shuttle_available";
    public static final String SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT = "travel.shuttle_available_count";
    public static final String VAR_SHUTTLE_TIMESTAMP = "travel.shuttle_timestamp";
    public static final String VAR_VERSION = "travel.version";
    public static final String VAR_STARPORT = "travel.starport";
    public static final String VAR_DEPARTURE_PLANET = "travel.ticket.departure_planet";
    public static final String VAR_ARRIVAL_PLANET = "travel.ticket.arrival_planet";
    public static final String VAR_DEPARTURE_POINT = "travel.ticket.departure_point";
    public static final String VAR_ARRIVAL_POINT = "travel.ticket.arrival_point";
    public static final String VAR_STARPORT_ORIGIN = "travel.ticket.starport_origin";
    public static final String VAR_VALID_TICKETS = "travel.valid_tickets";
    public static final String VAR_BOARDING_SHUTTLE = "travel.boarding_shuttle";
    public static final String VAR_PURCHASING_TICKET = "travel.purchasing_ticket";
    public static final String SCRIPT_VAR_TERMINAL = "travel.ticket_terminal";
    public static final string_id SID_MUSTAFAR_UNAUTHORIZED = new string_id("travel", "mustafar_unauthorized");
    public static final string_id SID_KASHYYYK_UNAUTHORIZED = new string_id("travel", "kashyyyk_unauthorized");
    public static final int TRAVEL_BLOCK_ALLOW_LAUNCH = 1;
    public static boolean initializeStarport(obj_id structure, String travel_point, int travel_cost, boolean civic) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "structure ->" + structure + " travel ->" + travel_point + " cost ->" + travel_cost);
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        int num_items = dataTableGetNumRows(STARPORT_DATATABLE);
        String template = getTemplateName(structure);
        int idx = getStarportTableIndex(template);
        LOG("LOG_CHANNEL", "idx ->" + idx);
        if (idx == -1)
        {
            return false;
        }
        String planet = getCurrentSceneName();
        dictionary row = dataTableGetRow(STARPORT_DATATABLE, idx);
        float arrival_x = row.getFloat(DATATABLE_COL_ARRIVAL_X);
        float arrival_y = row.getFloat(DATATABLE_COL_ARRIVAL_Y);
        float arrival_z = row.getFloat(DATATABLE_COL_ARRIVAL_Z);
        String arrival_cell = row.getString(DATATABLE_COL_ARRIVAL_CELL);
        int ground_time = row.getInt(DATATABLE_COL_GROUND_TIME);
        int air_time = row.getInt(DATATABLE_COL_AIR_TIME);
        int is_shuttleport = row.getInt(DATATABLE_COL_IS_SHUTTLEPORT);
        location arrival_loc;
        if (arrival_cell.equals("WORLD_DELTA"))
        {
            location s_loc = getLocation(structure);
            float s_yaw = getYaw(structure);
            if (s_yaw < 0.0f)
            {
                s_yaw = s_yaw + 360.0f;
            }
            float[] transform = transformDeltaWorldYaw(structure, arrival_x, arrival_z);
            arrival_x = transform[0];
            arrival_z = transform[1];
            arrival_loc = new location(s_loc.x - arrival_x, s_loc.y - arrival_y, s_loc.z - arrival_z, planet, obj_id.NULL_ID);
        }
        else 
        {
            obj_id cell_id = getCellId(structure, arrival_cell);
            if (cell_id == null || cell_id == obj_id.NULL_ID)
            {
                return false;
            }
            arrival_loc = new location(arrival_x, arrival_y, arrival_z, planet, cell_id);
        }
        setObjVar(structure, VAR_GROUND_TIME, ground_time);
        setObjVar(structure, VAR_AIR_TIME, air_time);
        setObjVar(structure, VAR_SHUTTLE_AVAILABLE, 1);
        setObjVar(structure, VAR_SHUTTLE_TIMESTAMP, getGameTime());
        setObjVar(structure, VAR_VERSION, CURRENT_VERSION);
        if (is_shuttleport > 0)
        {
            setObjVar(structure, VAR_IS_SHUTTLEPORT, is_shuttleport);
        }
        else if (hasObjVar(structure, VAR_IS_SHUTTLEPORT))
        {
            removeObjVar(structure, VAR_IS_SHUTTLEPORT);
        }
        setStarportTravelPoint(structure, travel_point, arrival_loc, travel_cost, civic);
        if (hasObjVar(structure, VAR_BASE_OBJECT))
        {
            destroyBaseObjects(structure);
        }
        if (num_items > idx + 1)
        {
            float s_yaw = getYaw(structure);
            if (s_yaw < 0.0f)
            {
                s_yaw = s_yaw + 360.0f;
            }
            Vector object_list = new Vector();
            object_list.setSize(0);
            for (int i = idx + 1; i < num_items; i++)
            {
                String struct_temp = dataTableGetString(STARPORT_DATATABLE, i, DATATABLE_COL_STRUCTURE);
                if (struct_temp.length() > 0)
                {
                    break;
                }
                dictionary object_row = dataTableGetRow(STARPORT_DATATABLE, i);
                String obj_template = object_row.getString(DATATABLE_COL_OBJECT);
                float x = object_row.getFloat(DATATABLE_COL_X);
                float y = object_row.getFloat(DATATABLE_COL_Y);
                float z = object_row.getFloat(DATATABLE_COL_Z);
                String cell = object_row.getString(DATATABLE_COL_CELL);
                float heading = object_row.getFloat(DATATABLE_COL_HEADING);
                int is_terminal = object_row.getInt(DATATABLE_COL_IS_TERMINAL);
                int is_transport = object_row.getInt(DATATABLE_COL_IS_TRANSPORT);
                int is_pilot = object_row.getInt(DATATABLE_COL_IS_PILOT);
                location obj_loc;
                obj_id object;
                if (cell.equals("WORLD_DELTA"))
                {
                    float[] delta_trans = transformDeltaWorldYaw(structure, x, z);
                    x = delta_trans[0];
                    z = delta_trans[1];
                    heading = heading + s_yaw;
                    if (heading > 360)
                    {
                        heading = heading - 360;
                    }
                    location s_loc = getLocation(structure);
                    obj_loc = new location(s_loc.x - x, s_loc.y - y, s_loc.z - z, planet, obj_id.NULL_ID);
                    object = createObject(obj_template, obj_loc);
                }
                else 
                {
                    obj_id cell_id = getCellId(structure, cell);
                    if (cell_id == null || cell_id == obj_id.NULL_ID)
                    {
                        continue;
                    }
                    obj_loc = new location(x, y, z, planet, cell_id);
                    object = createObjectInCell(obj_template, structure, cell, obj_loc);
                }
                if (heading != 0.0f)
				{

				}

                {
					setYaw(object, heading);
					object_list = utils.addElement(object_list, object);
				}
                if (is_terminal == 1)
				{
					setObjVar(object, VAR_STARPORT, structure);
				}
                if (is_transport == 1)
				{
					setObjVar(object, VAR_STARPORT, structure);
					attachScript(object, SCRIPT_SHUTTLE);
				}
                if (is_pilot == 1)
				{
					setObjVar(object, VAR_STARPORT, structure);
					attachScript(object, SCRIPT_SHUTTLE_PILOT);
				}
                if (object_list.size() > 0)
                {
                    setObjVar(structure, VAR_BASE_OBJECT, object_list);
                }
            }
        }
        return true;
    }
    public static String getTravelPointName(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return null;
        }
        if (player_structure.isCivic(structure))
        {
            int cityId = getCityAtLocation(getLocation(structure), 0);
            if (cityId != 0 && cityGetTravelCost(cityId) > 0)
            {
                return cityGetName(cityId);
            }
            return null;
        }
        if (hasObjVar(structure, VAR_TRAVEL_POINT_NAME))
        {
            return getStringObjVar(structure, VAR_TRAVEL_POINT_NAME);
        }
        else 
        {
            return null;
        }
    }
    public static location getArrivalLocation(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(structure, VAR_ARRIVAL_LOCATION))
        {
            return getLocationObjVar(structure, VAR_ARRIVAL_LOCATION);
        }
        else 
        {
            return null;
        }
    }
    public static int getTravelCost(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(structure, VAR_TRAVEL_COST))
        {
            return getIntObjVar(structure, VAR_TRAVEL_COST);
        }
        else 
        {
            return -1;
        }
    }
    public static int getGroundTime(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(structure, VAR_GROUND_TIME))
        {
            return getIntObjVar(structure, VAR_GROUND_TIME);
        }
        else 
        {
            return -1;
        }
    }
    public static int getAirTime(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(structure, VAR_AIR_TIME))
        {
            return getIntObjVar(structure, VAR_AIR_TIME);
        }
        else 
        {
            return -1;
        }
    }
    public static int getShuttleTimestamp(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(structure, VAR_SHUTTLE_TIMESTAMP))
        {
            return getIntObjVar(structure, VAR_SHUTTLE_TIMESTAMP);
        }
        else 
        {
            return -1;
        }
    }
    public static obj_id[] getBaseObjects(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(structure, VAR_BASE_OBJECT))
        {
            return getObjIdArrayObjVar(structure, VAR_BASE_OBJECT);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketDeparturePlanet(obj_id ticket) throws InterruptedException
    {
        if (ticket == null || ticket == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(ticket, VAR_DEPARTURE_PLANET))
        {
            return getStringObjVar(ticket, VAR_DEPARTURE_PLANET);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketDeparturePoint(obj_id ticket) throws InterruptedException
    {
        if (ticket == null || ticket == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(ticket, VAR_DEPARTURE_POINT))
        {
            return getStringObjVar(ticket, VAR_DEPARTURE_POINT);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketArrivalPlanet(obj_id ticket) throws InterruptedException
    {
        if (ticket == null || ticket == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(ticket, VAR_ARRIVAL_PLANET))
        {
            return getStringObjVar(ticket, VAR_ARRIVAL_PLANET);
        }
        else 
        {
            return null;
        }
    }
    public static String getTicketArrivalPoint(obj_id ticket) throws InterruptedException
    {
        if (ticket == null || ticket == obj_id.NULL_ID)
        {
            return null;
        }
        if (hasObjVar(ticket, VAR_ARRIVAL_POINT))
        {
            return getStringObjVar(ticket, VAR_ARRIVAL_POINT);
        }
        else 
        {
            return null;
        }
    }
    public static int getStarportVersion(obj_id starport) throws InterruptedException
    {
        if (starport == null || starport == obj_id.NULL_ID)
        {
            return -1;
        }
        if (hasObjVar(starport, VAR_VERSION))
        {
            return getIntObjVar(starport, VAR_VERSION);
        }
        else 
        {
            return 0;
        }
    }
    public static int getStarportTableIndex(String template) throws InterruptedException
    {
        int idx = -1;
        int numItems = dataTableGetNumRows(STARPORT_DATATABLE);
        for (int i = 0; i < numItems; i++)
        {
            String starportTemplate = dataTableGetString(STARPORT_DATATABLE, i, DATATABLE_COL_STRUCTURE);
            if (starportTemplate.equals(template))
            {
                idx = i;
                break;
            }
        }
        return idx;
    }
    public static boolean isTravelTicketValid(obj_id object, String depart_planet, String depart_point) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(object, VAR_DEPARTURE_PLANET)) {
            String ticket_planet = getTicketDeparturePlanet(object);
            String ticket_point = getTicketDeparturePoint(object);
            return ticket_planet.equals(depart_planet) && ticket_point.equals(depart_point);
        }
        else 
        {
            return false;
        }
    }
    public static boolean isShuttlePort(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        return hasObjVar(structure, VAR_IS_SHUTTLEPORT);
    }
    public static boolean isTravelShuttle(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        return hasScript(object, SCRIPT_SHUTTLE);
    }
    public static boolean isTravelShuttlePilot(obj_id object) throws InterruptedException
    {
        if (object == null || object == obj_id.NULL_ID)
        {
            return false;
        }
        return hasScript(object, SCRIPT_SHUTTLE_PILOT);
    }
    public static boolean isShuttleAvailable(obj_id starport) throws InterruptedException
    {
        if (starport == null || starport == obj_id.NULL_ID)
        {
            return false;
        }
        int available = getIntObjVar(starport, VAR_SHUTTLE_AVAILABLE);
        return available == 1;
    }
    public static boolean qualifiesForGcwTravelPerks(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        final int faction = pvpGetAlignedFaction(player);
        if (faction == 0)
        {
            return false;
        }
        if (pvpGetCurrentGcwRank(player) < 7)
        {
            return false;
        }
        final int imperialScore = getGcwGroupImperialScorePercentile(getCurrentSceneName());
        if ((-615855020) == faction)
        {
            return (imperialScore >= 70);
        }
        else if ((370444368) == faction)
        {
            return (imperialScore <= 30);
        }
        return false;
    }
    public static boolean restrictedByGcwTravelRestrictions(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return false;
        }
        final int faction = pvpGetAlignedFaction(player);
        if (faction == 0)
        {
            return false;
        }
        if (pvpGetCurrentGcwRank(player) < 7)
        {
            return false;
        }
        final int imperialScore = getGcwGroupImperialScorePercentile(getCurrentSceneName());
        if ((-615855020) == faction)
        {
            return (imperialScore <= 30);
        }
        else if ((370444368) == faction)
        {
            return (imperialScore >= 70);
        }
        return false;
    }
    public static int getGcwTravelRestrictionsSurcharge(obj_id player, String departPlanet, String arrivePlanet) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return 0;
        }
        final int faction = pvpGetAlignedFaction(player);
        if (faction == 0)
        {
            return 0;
        }
        if (pvpGetCurrentGcwRank(player) < 7)
        {
            return 0;
        }
        final int imperialScoreDepartPlanet = getGcwGroupImperialScorePercentile(departPlanet);
        final int imperialScoreArrivePlanet = getGcwGroupImperialScorePercentile(arrivePlanet);
        if ((-615855020) == faction)
        {
            final int largerRebelScore = Math.max(100 - imperialScoreDepartPlanet, 100 - imperialScoreArrivePlanet);
            if (largerRebelScore > 70)
            {
                return (largerRebelScore - 70) * 500;
            }
        }
        else if ((370444368) == faction)
        {
            final int largerImperialScore = Math.max(imperialScoreDepartPlanet, imperialScoreArrivePlanet);
            if (largerImperialScore > 70)
            {
                return (largerImperialScore - 70) * 500;
            }
        }
        return 0;
    }
    public static String getGcwTravelRestrictionsAvailableStarport(obj_id player, String planet) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        if ((planet == null) || (planet.length() <= 0))
        {
            return null;
        }
        final int faction = pvpGetAlignedFaction(player);
        if (faction == 0)
        {
            return null;
        }
        if (pvpGetCurrentGcwRank(player) < 7)
        {
            return null;
        }
        final int imperialScore = getGcwGroupImperialScorePercentile(planet);
        if ((((-615855020) == faction) && (imperialScore <= 30)) || (((370444368) == faction) && (imperialScore >= 70)))
        {
            String availableStarport = null;
            int highestScore = -1;
            String[] planetTravelPoints = getPlanetTravelPoints(planet);
            if ((planetTravelPoints != null) && (planetTravelPoints.length > 0))
            {
                int numStarport = 0;
                String gcwContestedRegion;

                for (String planetTravelPoint : planetTravelPoints) {
                    if (getPlanetTravelPointInterplanetary(planet, planetTravelPoint)) {
                        ++numStarport;
                        int score = 50;
                        gcwContestedRegion = getPlanetTravelPointGcwContestedRegion(planet, planetTravelPoint);
                        if ((gcwContestedRegion != null) && (gcwContestedRegion.length() > 0)) {
                            score = getGcwImperialScorePercentile(gcwContestedRegion);
                            if ((370444368) == faction) {
                                score = 100 - score;
                            }
                        }
                        if (score > highestScore) {
                            availableStarport = planetTravelPoint;
                            highestScore = score;
                        }
                    }
                }
                if (numStarport <= 1)
                {
                    return null;
                }
            }
            return availableStarport;
        }
        return null;
    }
    public static obj_id getTravelShuttle(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        location loc = getLocation(player);
        obj_id[] items = getObjectsInRange(loc, MAXIMUM_SHUTTLE_DISTANCE);
        for (obj_id item : items) {
            if (isTravelShuttle(item)) {
                return item;
            }
        }
        return null;
    }
    public static boolean isInShuttleBoardingRange(obj_id player, obj_id shuttle) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (shuttle == null || shuttle == obj_id.NULL_ID)
        {
            return false;
        }
        location loc_player = getLocation(player);
        location loc_shuttle = getLocation(shuttle);
        float distance = loc_player.distance(loc_shuttle);
        if (distance < 0)
        {
            return false;
        }
        return distance <= MAXIMUM_SHUTTLE_DISTANCE;
    }
    public static boolean isValidRoute(String planet1, String point1, String planet2, String point2) throws InterruptedException
    {
        int planet_cost = getPlanetTravelCost(planet1, planet2);
        if (planet_cost == 0)
        {
            return false;
        }
        int point_cost1 = getPlanetTravelPointCost(planet1, point1);
        if (point_cost1 == 0)
        {
            return false;
        }
        int point_cost2 = getPlanetTravelPointCost(planet2, point2);
        if (point_cost2 == 0)
        {
            return false;
        }
        if (!planet1.equals(planet2))
        {
            boolean starport1 = getPlanetTravelPointInterplanetary(planet1, point1);
            boolean starport2 = getPlanetTravelPointInterplanetary(planet2, point2);
            LOG("LOG_CHANNEL", "starport1 ->" + starport1 + " starport2 ->" + starport2);
            if (!starport1 || !starport2)
            {
                return false;
            }
        }
        return true;
    }
    public static obj_id getStarportFromTerminal(obj_id terminal) throws InterruptedException
    {
        if (terminal == null || terminal == obj_id.NULL_ID || !isIdValid(terminal))
        {
            return null;
        }
        if (hasObjVar(terminal, VAR_STARPORT))
        {
            return getObjIdObjVar(terminal, VAR_STARPORT);
        }
        else 
        {
            return null;
        }
    }
    public static obj_id getShuttleFromStarport(obj_id starport) throws InterruptedException
    {
        obj_id[] starport_objects = travel.getBaseObjects(starport);
        obj_id shuttle = obj_id.NULL_ID;
        if (starport_objects != null)
        {
            for (obj_id starport_object : starport_objects) {
                if (travel.isTravelShuttle(starport_object)) {
                    shuttle = starport_object;
                    break;
                }
            }
        }
        if (shuttle == obj_id.NULL_ID)
        {
            return null;
        }
        else 
        {
            return shuttle;
        }
    }
    public static boolean setShuttleAvailability(obj_id starport) throws InterruptedException
    {
        if (starport == null || starport == obj_id.NULL_ID)
        {
            return false;
        }
        if (hasObjVar(starport, VAR_SHUTTLE_AVAILABLE))
        {
            int available = getIntObjVar(starport, VAR_SHUTTLE_AVAILABLE);
            if (available == 1)
            {
                setObjVar(starport, VAR_SHUTTLE_AVAILABLE, 0);
                int shuttleAvailableCount = 0;
                if (utils.hasScriptVar(starport, SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT))
                {
                    shuttleAvailableCount = utils.getIntScriptVar(starport, SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT);
                }
                ++shuttleAvailableCount;
                utils.setScriptVar(starport, SCRIPTVAR_SHUTTLE_AVAILABLE_COUNT, shuttleAvailableCount);
            }
            else 
            {
                setObjVar(starport, VAR_SHUTTLE_AVAILABLE, 1);
            }
            setObjVar(starport, VAR_SHUTTLE_TIMESTAMP, getGameTime());
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean setStarportTravelPoint(obj_id structure, String travel_point, location arrival_loc, int travel_cost, boolean civic) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        if (travel_point == null || travel_point.length() < 1)
        {
            return false;
        }
        if (arrival_loc == null)
        {
            return false;
        }
        if (travel_cost < 0)
        {
            return false;
        }
        boolean interplanetary;
        interplanetary = !isShuttlePort(structure);
        boolean setup;
        if (civic)
        {
            setup = city.addStarport(structure, arrival_loc, travel_cost, interplanetary);
        }
        else 
        {
            int type = TPT_NPC_Shuttleport;
            if (interplanetary)
            {
                type = TPT_NPC_Starport;
            }
            else 
            {
                String template = getTemplateName(structure);
                if ((template != null) && (template.equals("object/tangible/camp/camp_shuttle_beacon.iff")))
                {
                    type = TPT_PC_CampShuttleBeacon;
                }
                else if ((template != null) && (template.equals("object/tangible/gcw/static_base/invisible_beacon.iff")))
                {
                    type = TPT_NPC_StaticBaseBeacon;
                }
            }
            setup = addPlanetTravelPoint(planet, travel_point, arrival_loc, travel_cost, interplanetary, type);
        }
        if (setup)
        {
            if (!hasObjVar(structure, VAR_TRAVEL_POINT_NAME))
            {
                setObjVar(structure, VAR_TRAVEL_POINT_NAME, travel_point);
            }
            if (!hasObjVar(structure, VAR_ARRIVAL_LOCATION))
            {
                setObjVar(structure, VAR_ARRIVAL_LOCATION, arrival_loc);
            }
            if (!hasObjVar(structure, VAR_TRAVEL_COST))
            {
                setObjVar(structure, VAR_TRAVEL_COST, travel_cost);
            }
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean removeTravelPoint(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        String planet = getCurrentSceneName();
        String travel_point = "";
        if (hasObjVar(structure, VAR_TRAVEL_POINT_NAME))
        {
            travel_point = getStringObjVar(structure, VAR_TRAVEL_POINT_NAME);
        }
        else 
        {
            return false;
        }
        if (removePlanetTravelPoint(planet, travel_point))
        {
            removeObjVar(structure, VAR_TRAVEL);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static boolean destroyBaseObjects(obj_id structure) throws InterruptedException
    {
        if (structure == null || structure == obj_id.NULL_ID)
        {
            return false;
        }
        obj_id[] base_objects = getBaseObjects(structure);
        if (base_objects != null)
        {
            for (obj_id base_object : base_objects) {
                if (isIdValid(base_object)) {
                    destroyObject(base_object);
                }
            }
        }
        return true;
    }
    public static boolean purchaseTicket(obj_id player, String planet1, String point1, String planet2, String point2, boolean roundtrip, obj_id starport) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel::purchaseTicket");
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (utils.hasScriptVar(player, VAR_PURCHASING_TICKET))
        {
            LOG("LOG_CHANNEL", player + " ->You already have an outstanding ticket purchase request. Please wait for it to complete.");
            sui.msgbox(player, new string_id("travel", "purchase_pending"));
            return false;
        }
        if (planet2.equals("mustafar"))
        {
            if (!features.hasMustafarExpansionRetail(player))
            {
                sendSystemMessage(player, SID_MUSTAFAR_UNAUTHORIZED);
                return false;
            }
        }
        else if (planet2.equals("kashyyyk_main"))
        {
            if (!features.hasEpisode3Expansion(player))
            {
                sendSystemMessage(player, SID_KASHYYYK_UNAUTHORIZED);
                return false;
            }
        }
        else if (planet1.equals(planet2))
        {
            if (point1.equals(point2))
            {
                LOG("LOG_CHANNEL", player + " ->Your destination cannot be the same as your current location.");
                sui.msgbox(player, new string_id("travel", "same_spot"));
                return false;
            }
        }
        else 
        {
            if (!getPlanetTravelPointInterplanetary(planet1, point1))
            {
                LOG("LOG_CHANNEL", player + " ->Shuttleports do not offer interplanetary transportation.");
                sui.msgbox(player, new string_id("travel", "shuttle_interplanet_fail"));
                return false;
            }
        }
        int city_id = findCityByName(point2);
        if (city.isCityBanned(player, city_id))
        {
            sendSystemMessage(player, new string_id("city/city", "banned_buy_ticket"));
            return false;
        }
        int planet_cost = getPlanetTravelCost(planet1, planet2);
        if (planet_cost == 0)
        {
            LOG("LOG_CHANNEL", player + " ->Your planetary route is not valid.");
            sui.msgbox(player, new string_id("travel", "route_invalid"));
            return false;
        }
        int point_cost1 = getPlanetTravelPointCost(planet1, point1);
        if (point_cost1 == 0)
        {
            LOG("LOG_CHANNEL", player + " ->Your departure location is not valid.");
            sui.msgbox(player, new string_id("travel", "departure_invalid"));
            return false;
        }
        int point_cost2 = getPlanetTravelPointCost(planet2, point2);
        if (point_cost2 == 0)
        {
            LOG("LOG_CHANNEL", player + " ->Your destination is not valid.");
            sui.msgbox(player, new string_id("travel", "destination_invalid"));
            return false;
        }
        int total_cost = planet_cost + point_cost1 + point_cost2;
        if (roundtrip)
        {
            total_cost = total_cost * 2;
        }
        LOG("LOG_CHANNEL", "total_cost ->" + total_cost + "   " + planet_cost + "/" + point_cost1 + "/" + point_cost2);
        boolean hasTravelVoucher = false;
        boolean hasEnoughMoney = true;
        if (utils.playerHasItemByTemplate(player, TRAVEL_COUPON_TEMPLATE))
        {
            hasTravelVoucher = true;
        }
        int total_money = getTotalMoney(player);
        if (total_money < total_cost)
        {
            hasEnoughMoney = false;
            if (!hasTravelVoucher)
            {
                LOG("LOG_CHANNEL", player + " ->You do not have sufficient funds for that.");
                sui.msgbox(player, new string_id("travel", "no_cash"));
                return false;
            }
        }
        obj_id inv = getObjectInSlot(player, "inventory");
        if (inv == null || inv == obj_id.NULL_ID)
        {
            LOG("LOG_CHANNEL", "player_dispenser::msgMedicinePurchaseSelected -- can't find inventory for " + player);
            return false;
        }
        int free_space = getVolumeFree(inv);
        if (free_space < 1)
        {
            sui.msgbox(player, new string_id("travel", "inv_full"));
            return false;
        }
        if (roundtrip)
        {
            if (free_space < 2)
            {
                sui.msgbox(player, new string_id("travel", "inv_full"));
                return false;
            }
        }
        int city_fee = 0;
        if (player_structure.isCivic(starport))
        {
            city_id = getCityAtLocation(getLocation(starport), 0);
            city_fee = cityGetTravelCost(city_id);
            total_cost -= city_fee;
        }
        dictionary params = new dictionary();
        params.put("player_id", player);
        params.put("account_name", money.ACCT_TRAVEL);
        params.put("amount", total_cost);
        params.put("planet1", planet1);
        params.put("point1", point1);
        params.put("planet2", planet2);
        params.put("point2", point2);
        params.put("roundtrip", roundtrip);
        if (hasTravelVoucher)
        {
            obj_id travel_coupon = utils.getItemPlayerHasByTemplate(player, TRAVEL_COUPON_TEMPLATE);
            if (isIdValid(travel_coupon))
            {
                if (hasObjVar(travel_coupon, "owner"))
                {
                    obj_id owner = getObjIdObjVar(travel_coupon, "owner");
                    if (isIdValid(owner))
                    {
                        if (player == owner)
                        {
                            if (utils.hasScriptVar(player, "travel_voucher.travelVoucherSui"))
                            {
                                int oldSui = utils.getIntScriptVar(player, "travel_voucher.travelVoucherSui");
                                utils.removeScriptVar(player, "travel_voucher.travelVoucherSui");
                                if (oldSui > -1)
                                {
                                    forceCloseSUIPage(oldSui);
                                }
                            }
                            params.put("city_fee", city_fee);
                            params.put("starport", starport);
                            params.put("travel_coupon", travel_coupon);
                            params.put("hasEnoughMoney", hasEnoughMoney);
                            utils.setScriptVar(player, "travelTicketDict", params);
                            string_id titleMsg = new string_id("new_player", "travel_coupon");
                            string_id textMsgPurchaseOption = new string_id("new_player", "travel_coupon_use_text_option");
                            string_id textMsgNoMoney = new string_id("new_player", "travel_coupon_use_text_no_money");
                            string_id okButton = new string_id("new_player", "travel_coupon_use_coupon_button");
                            string_id cancelButtonPay = new string_id("new_player", "travel_coupon_pay_credits_button");
                            string_id cancelButtonCancel = new string_id("new_player", "travel_coupon_cancel_button");
                            String TITLE_MSG = utils.packStringId(titleMsg);
                            String TEXT_MSG = utils.packStringId(textMsgPurchaseOption) + " " + total_cost;
                            String OK_BUTTON = utils.packStringId(okButton);
                            String CANCEL_BUTTON = utils.packStringId(cancelButtonPay);
                            if (!hasEnoughMoney)
                            {
                                TEXT_MSG = utils.packStringId(textMsgNoMoney);
                                CANCEL_BUTTON = utils.packStringId(cancelButtonCancel);
                            }
                            int pid = sui.createSUIPage(sui.SUI_MSGBOX, player, player, "msgUseTravelCouponSui");
                            setSUIProperty(pid, "", "Size", "500,200");
                            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, TITLE_MSG);
                            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, TEXT_MSG);
                            sui.msgboxButtonSetup(pid, sui.YES_NO);
                            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, OK_BUTTON);
                            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, CANCEL_BUTTON);
                            utils.setScriptVar(player, "travel_voucher.travelVoucherSui", pid);
                            sui.showSUIPage(pid);
                            return false;
                        }
                        else 
                        {
                            String custLogMsg = "New Player Rewards: %TU tried to use a travel voucher but isn't the owner. Rightful owner is %TT";
                            CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, player, owner);
                            if (isGod(player))
                            {
                                sendSystemMessage(player, new string_id("travel", "not_voucher_owner"));
                                if (!hasEnoughMoney)
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }
                else 
                {
                    String custLogMsg = "New Player Rewards: %TU tried to use a travel voucher but the voucher is invalid - does not have an owner.";
                    CustomerServiceLog("NEW_PLAYER_QUESTS", custLogMsg, player);
                    if (isGod(player))
                    {
                        sendSystemMessage(player, new string_id("travel", "not_voucher_valid"));
                        if (!hasEnoughMoney)
                        {
                            return false;
                        }
                    }
                }
            }
        }
        if (money.pay(player, money.ACCT_TRAVEL, total_cost, "msgTicketPaymentComplete", params, true))
        {
            params.put("amount", city_fee);
            if ((city_fee > 0) && !money.pay(player, city.getCityHall(starport), city_fee, "msgCityTicketPaymentComplete", params, true))
            {
                return false;
            }
            LOG("LOG_CHANNEL", "Deducting " + total_cost);
            utils.setScriptVar(player, VAR_PURCHASING_TICKET, 1);
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static void showTravelVoucherSui() throws InterruptedException
    {
    }
    public static obj_id[] createTicket(obj_id player, String planet1, String point1, String planet2, String point2, boolean roundtrip) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return null;
        }
        obj_id ticket;
        obj_id inv = getObjectInSlot(player, "inventory");
        if (inv == null)
        {
            LOG("LOG_CHANNEL", "player_travel::createTicket -- Unable to get an inventory slot!");
            sendSystemMessage(player, new string_id("travel", "no_room_for_ticket"));
            ticket = createObject(BASE_TRAVEL_TICKET, getLocation(player));
        }
        else 
        {
            ticket = createObject(BASE_TRAVEL_TICKET, inv, "");
        }
        if (ticket == null)
        {
            LOG("LOG_CHANNEL", "player_travel::createTicket -- Unable to create ticket.");
            return null;
        }
        setObjVar(ticket, VAR_DEPARTURE_PLANET, planet1);
        setObjVar(ticket, VAR_DEPARTURE_POINT, point1);
        setObjVar(ticket, VAR_ARRIVAL_PLANET, planet2);
        setObjVar(ticket, VAR_ARRIVAL_POINT, point2);
        obj_id[] tickets = null;
        if (roundtrip)
        {
            tickets = new obj_id[2];
        }
        else 
        {
            tickets = new obj_id[1];
        }
        tickets[0] = ticket;
        if (roundtrip)
        {
            obj_id return_ticket = createObjectOverloaded(BASE_TRAVEL_TICKET, inv);
            setObjVar(return_ticket, VAR_DEPARTURE_PLANET, planet2);
            setObjVar(return_ticket, VAR_DEPARTURE_POINT, point2);
            setObjVar(return_ticket, VAR_ARRIVAL_PLANET, planet1);
            setObjVar(return_ticket, VAR_ARRIVAL_POINT, point1);
            tickets[1] = return_ticket;
        }
        return tickets;
    }
    public static boolean movePlayerToDestination(obj_id player, String planet, String point) throws InterruptedException
    {
        return movePlayerToDestination(player, planet, point, false);
    }
    public static boolean movePlayerToDestination(obj_id player, String planet, String point, boolean groupPickupPointTravel) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- player is not valid");
            return false;
        }
        if (planet == null)
        {
            LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- planet is null");
            return false;
        }
        if (point == null)
        {
            LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- point is null");
            return false;
        }
        if (!groupPickupPointTravel)
        {
            if (isTravelBlocked(player, false))
            {
                return false;
            }
            if (planet.equals("mustafar"))
            {
                if (!features.hasMustafarExpansionRetail(player))
                {
                    sendSystemMessage(player, SID_MUSTAFAR_UNAUTHORIZED);
                    return false;
                }
            }
            else if (planet.equals("kashyyyk_main"))
            {
                if (!features.hasEpisode3Expansion(player))
                {
                    sendSystemMessage(player, SID_KASHYYYK_UNAUTHORIZED);
                    return false;
                }
            }
        }
        location loc = getPlanetTravelPointLocation(planet, point);
        LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- location ->" + loc);
        if (loc == null)
        {
            LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- location is null");
            return false;
        }
        location arrival = utils.getRandomAwayLocation(loc, MIN_RANDOM_DISTANCE, MAX_RANDOM_DISTANCE);
        if (arrival.cell == null || arrival.cell == obj_id.NULL_ID)
        {
            setLookAtTarget(player, null);
            int numbuff = buff.getBuffOnTargetFromGroup(player, "vr_familiar");
            if (buff.hasBuff(player, numbuff))
            {
                buff.removeBuff(player, numbuff);
            }
            if (utils.hasScriptVar(player, "currentHolo"))
            {
                performance.holographicCleanup(player);
            }
            if (groupPickupPointTravel)
            {
                warpPlayer(player, planet, arrival.x, arrival.y, arrival.z, null, 0.0f, 0.0f, 0.0f);
            }
            else 
            {
                warpPlayer(player, planet, arrival.x, arrival.y, arrival.z, null, 0.0f, 0.0f, 0.0f, "msgTravelComplete");
            }
        }
        else 
        {
            LOG("LOG_CHANNEL", "travel::movePlayerToDestination -- travel point: " + planet + ", " + point + " has a cell arrival location.  This is not supported.");
        }
        return true;
    }
    public static float[] transformDeltaWorldCoord(float X, float Z, int rotation) throws InterruptedException
    {
        float x = X;
        float z = Z;
        switch (rotation)
        {
            case 0:
                break;
            case 1:
                X = z;
                Z = -x;
                break;
            case 2:
                Z = -z;
                X = -x;
                break;
            case 3:
                X = -z;
                Z = x;
                break;
        }
        return new float[]{
            X,
            Z
        };
    }
    public static float[] transformDeltaWorldYaw(obj_id object, float x, float z) throws InterruptedException
    {
        if (!isIdValid(object))
        {
            return null;
        }
        float yaw = getYaw(object);
        float yaw_radians = (float)Math.toRadians(yaw);
        float X = (x * (float)Math.cos(yaw_radians)) + (z * (float)Math.sin(yaw_radians));
        float Z = (z * (float)Math.cos(yaw_radians)) - (x * (float)Math.sin(yaw_radians));
        return new float[]{
            X,
            Z
        };
    }
    public static boolean testBoardShuttle(obj_id player, obj_id shuttle) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        obj_id starport = getStarportFromTerminal(shuttle);
        if (starport == null)
        {
            LOG("LOG_CHANNEL", "player.player_travel::OnBoardShuttle -- Unable to find the starport for " + shuttle);
        }
        String depart_point = getTravelPointName(starport);
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        obj_id inventory = getObjectInSlot(player, "inventory");
        obj_id[] inv_contents = utils.getContents(inventory, false);
        String ticket_planet;

        for (obj_id inv_content : inv_contents) {
            if (isTravelTicketValid(inv_content, planet, depart_point)) {
                ticket_planet = getTicketArrivalPlanet(inv_content);
                ticket_planet = (ticket_planet.substring(0, 1)).toUpperCase() + (ticket_planet.substring(1)).toLowerCase();
                dsrc = utils.addElement(dsrc, ticket_planet + " -- " + getTicketArrivalPoint(inv_content));
            }
        }
        if (dsrc.size() < 1)
        {
            LOG("LOG_CHANNEL", player + " ->You do not have a ticket to board this shuttle.");
            sendSystemMessage(player, new string_id("travel", "no_ticket_for_shuttle"));
            return false;
        }
        else 
        {
            string_id sd = new string_id("travel", "select_destination");
            String txt = utils.packStringId(sd);
            sui.listbox(player, player, txt, sui.OK_CANCEL, txt, dsrc, "msgBoardShuttle");
            return true;
        }
    }
    public static Vector getFilteredPortLocationsForPlanet(String planet, boolean isStarport) throws InterruptedException
    {
        if (planet == null || planet.equals(""))
        {
            return null;
        }
        String[] allPts = getPlanetTravelPoints(planet);
        if (allPts == null || allPts.length == 0)
        {
            return null;
        }
        Vector ret = new Vector();
        ret.setSize(0);
        for (String allPt : allPts) {
            if (getPlanetTravelPointInterplanetary(planet, allPt) == isStarport) {
                ret = utils.addElement(ret, allPt);
            }
        }
        return ret;
    }
    public static String[] getStarportsForPlanet(String planet) throws InterruptedException
    {
        return utils.toStaticStringArray(getFilteredPortLocationsForPlanet(planet, true));
    }
    public static String[] getStarportsForPlanet() throws InterruptedException
    {
        return getStarportsForPlanet(getCurrentSceneName());
    }
    public static String[] getShuttleportsForPlanet(String planet) throws InterruptedException
    {
        return utils.toStaticStringArray(getFilteredPortLocationsForPlanet(planet, false));
    }
    public static String[] getShuttleportsForPlanet() throws InterruptedException
    {
        return getShuttleportsForPlanet(getCurrentSceneName());
    }
    public static boolean instantTravel(obj_id player, String planet1, String point1, String planet2, String point2, boolean roundtrip, obj_id starport) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "travel::instantTravel");
        if (player == null || player == obj_id.NULL_ID)
        {
            return false;
        }
        if (utils.hasScriptVar(player, VAR_PURCHASING_TICKET))
        {
            LOG("LOG_CHANNEL", player + " ->You already have an outstanding ticket purchase request. Please wait for it to complete.");
            sui.msgbox(player, new string_id("travel", "travel_pending"));
            return false;
        }
        if (!planet1.equals(planet2))
        {
            LOG("LOG_CHANNEL", player + " ->Shuttleports do not offer interplanetary transportation.");
            sui.msgbox(player, new string_id("travel", "instant_interplanet_fail"));
            return false;
        }
        int city_id = findCityByName(point2);
        if (city.isCityBanned(player, city_id))
        {
            sendSystemMessage(player, new string_id("travel", "banned_travel"));
            return false;
        }
        if (callable.hasAnyCallable(player))
        {
            sendSystemMessage(player, new string_id("beast", "beast_cant_travel"));
            return false;
        }
        return movePlayerToDestination(player, planet2, point2);
    }
    public static boolean isTravelBlocked(obj_id player, boolean isLaunch) throws InterruptedException
    {
        boolean blocked = false;
        if (utils.hasScriptVar(player, "travelBlock"))
        {
            blocked = true;
        }
        if (isLaunch && utils.getIntScriptVar(player, "travelBlock") == TRAVEL_BLOCK_ALLOW_LAUNCH)
        {
            blocked = false;
        }
        if (instance.isInInstanceArea(player) && trial.getTop(player) == player)
        {
            blocked = true;
        }
        if (blocked)
        {
            sendSystemMessage(player, new string_id("travel", "blocked_by_authorities"));
        }
        return blocked;
    }
    public static void setTravelBlock(obj_id player, boolean allowLaunch) throws InterruptedException
    {
        int travelBlockValue = allowLaunch ? TRAVEL_BLOCK_ALLOW_LAUNCH : 0;
        utils.setScriptVar(player, "travelBlock", travelBlockValue);
    }
    public static void clearTravelBlock(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "travelBlock"))
        {
            utils.removeScriptVar(player, "travelBlock");
        }
    }
}
