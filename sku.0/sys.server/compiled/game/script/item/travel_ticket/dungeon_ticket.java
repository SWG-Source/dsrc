package script.item.travel_ticket;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.utils;

public class dungeon_ticket extends script.base_script
{
    public dungeon_ticket()
    {
    }
    public static final string_id SID_NO_COLLECTOR = new string_id("dungeon/space_dungeon", "no_collector");
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "travel_departure_planet";
        String departure_planet = space_dungeon.getTicketPlanetName(self);
        if (departure_planet == null)
        {
            return SCRIPT_CONTINUE;
        }
        string_id sid_departure_planet = new string_id("planet_n", departure_planet);
        attribs[idx] = localize(sid_departure_planet);
        idx++;
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        string_id point_name = space_dungeon.getDungeonNameStringId(space_dungeon.getTicketPointName(self));
        if (point_name != null)
        {
            names[idx] = "travel_departure_point";
            attribs[idx] = localize(point_name);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        string_id dungeon_name = space_dungeon.getDungeonNameStringId(space_dungeon.getTicketDungeonName(self));
        if (dungeon_name != null)
        {
            names[idx] = "travel_arrival_dungeon";
            attribs[idx] = localize(dungeon_name);
        }
        return SCRIPT_CONTINUE;
    }
}
