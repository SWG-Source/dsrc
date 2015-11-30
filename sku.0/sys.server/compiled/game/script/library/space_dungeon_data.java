package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.player_structure;
import script.library.utils;
import script.library.space_dungeon;
import script.library.sui;
import script.library.prose;

public class space_dungeon_data extends script.base_script
{
    public space_dungeon_data()
    {
    }
    public static final String TEMPLATE_TICKET = "object/tangible/travel/travel_ticket/dungeon_ticket.iff";
    public static final String DUNGEON_DATATABLE = "datatables/dungeon/space_dungeon.iff";
    public static final String TRAVEL_DUNGEON = "item.travel_ticket.travel_space_dungeon";
    public static final String DUNGEON_CONTROLLER = "theme_park.dungeon.space_dungeon_controller";
    public static string_id getDungeonSuccessString(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonSuccessString -- name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            String success_string = dataTableGetString(space_dungeon_data.DUNGEON_DATATABLE, idx, "success_string");
            return utils.unpackString(success_string);
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonSuccessString -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static string_id getDungeonFailureString(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonFailureString -- name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            String failure_string = dataTableGetString(space_dungeon_data.DUNGEON_DATATABLE, idx, "failure_string");
            return utils.unpackString(failure_string);
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonFailureString -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static int getDungeonMaxPlayers(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonFailureString -- name is null");
            return -1;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            int max_players = dataTableGetInt(space_dungeon_data.DUNGEON_DATATABLE, idx, "max_players");
            return max_players;
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonMaxPlayers -- Unable to find a datatable entry for " + name);
            return -1;
        }
    }
    public static location getDungeonStartLocation(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartLocation -- name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            dictionary row = dataTableGetRow(space_dungeon_data.DUNGEON_DATATABLE, idx);
            String planet = row.getString("planet");
            float loc_x = row.getFloat("start_loc_x");
            float loc_y = row.getFloat("start_loc_y");
            float loc_z = row.getFloat("start_loc_z");
            location start_loc = new location(loc_x, loc_y, loc_z, planet);
            return start_loc;
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartLocation -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static float getDungeonStartLocationRadius(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartLocationRadius -- name is null");
            return 0.0f;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            dictionary row = dataTableGetRow(space_dungeon_data.DUNGEON_DATATABLE, idx);
            return row.getFloat("start_loc_radius");
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartLocationRadius -- Unable to find a datatable entry for " + name);
        }
        return 0.0f;
    }
    public static location getDungeonStartLocationRandomized(String name) throws InterruptedException
    {
        location loc = getDungeonStartLocation(name);
        if (null == loc)
        {
            return null;
        }
        float startLocationRadius = getDungeonStartLocationRadius(name);
        loc.x += (-1.0f + (2.0f * random.rand())) * startLocationRadius;
        loc.z += (-1.0f + (2.0f * random.rand())) * startLocationRadius;
        return loc;
    }
    public static location getDungeonExitLocation(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonExitLocation -- name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            dictionary row = dataTableGetRow(space_dungeon_data.DUNGEON_DATATABLE, idx);
            String planet = row.getString("exit_planet");
            float loc_x = row.getFloat("exit_loc_x");
            float loc_y = row.getFloat("exit_loc_y");
            float loc_z = row.getFloat("exit_loc_z");
            location start_loc = new location(loc_x, loc_y, loc_z, planet);
            return start_loc;
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonExitLocation -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static float getDungeonExitLocationRadius(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonExitLocation -- name is null");
            return 0.0f;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            dictionary row = dataTableGetRow(space_dungeon_data.DUNGEON_DATATABLE, idx);
            return row.getFloat("exit_loc_radius");
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonExitLocation -- Unable to find a datatable entry for " + name);
        }
        return 0.0f;
    }
    public static location getDungeonExitLocationRandomized(String name) throws InterruptedException
    {
        location loc = getDungeonExitLocation(name);
        if (null == loc)
        {
            return null;
        }
        float exitLocationRadius = getDungeonExitLocationRadius(name);
        loc.x += (-1.0f + (2.0f * random.rand())) * exitLocationRadius;
        loc.z += (-1.0f + (2.0f * random.rand())) * exitLocationRadius;
        return loc;
    }
    public static String getDungeonStartCellName(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartCellName -- name is null");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            String cell_name = dataTableGetString(space_dungeon_data.DUNGEON_DATATABLE, idx, "start_cell_name");
            return cell_name;
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonStartCellName -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static String getDungeonPlayerScript(String name) throws InterruptedException
    {
        if (name == null)
        {
            LOG("space_dungeon", "space_dungeon.getDungeonPlayerScript -- name is null.");
            return null;
        }
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            String script = dataTableGetString(space_dungeon_data.DUNGEON_DATATABLE, idx, "player_script");
            if (script != null)
            {
                if (script.length() < 1)
                {
                    return null;
                }
            }
            return script;
        }
        else 
        {
            LOG("space_dungeon", "space_dungeon.getDungeonPlayerScript -- Unable to find a datatable entry for " + name);
            return null;
        }
    }
    public static boolean isValidDungeon(String name) throws InterruptedException
    {
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx == -1)
        {
            return false;
        }
        return true;
    }
    public static int getMaxDungeonSesssionTime(String name) throws InterruptedException
    {
        int idx = dataTableSearchColumnForString(name, "dungeon", space_dungeon_data.DUNGEON_DATATABLE);
        if (idx > -1)
        {
            int maxSessionTime = dataTableGetInt(space_dungeon_data.DUNGEON_DATATABLE, idx, "time_limit");
            if (maxSessionTime > 36000 || maxSessionTime < 600)
            {
                LOG("space_dungeon", "spaceDungeonData.getMaxDungeonSessionTime:: maxSessionTime was greater than 10 hours or less than 10 minutes. Setting to 30 minutes");
                maxSessionTime = 3600;
            }
            return maxSessionTime;
        }
        else 
        {
            LOG("space_dungeon", "spaceDungeonData.getMaxDungeonSessionTime:: dungeon was not found in the table, returning 30 minutes");
            return 3600;
        }
    }
}
