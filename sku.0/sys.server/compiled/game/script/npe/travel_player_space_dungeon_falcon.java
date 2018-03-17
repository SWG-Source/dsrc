package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.utils;
import script.library.sui;
import script.library.prose;

public class travel_player_space_dungeon_falcon extends script.base_script
{
    public travel_player_space_dungeon_falcon()
    {
    }
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_REQUEST_TRAVEL = new string_id("dungeon/space_dungeon", "request_travel");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDING = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_type, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (player != self)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- got a request to send a player that is not self, self: " + self + " request " + player);
            releaseClusterWideDataLock(manage_name, lock_key);
            if (isIdValid(player))
            {
                space_dungeon.cleanupPlayerTicketObjvars(player);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id ticket = null;
        if (hasObjVar(player, space_dungeon.VAR_TICKET_USED))
        {
            ticket = getObjIdObjVar(player, space_dungeon.VAR_TICKET_USED);
        }
        else 
        {
            ticket = player;
        }
        if (!isIdValid(ticket) || !ticket.isAuthoritative())
        {
            sendSystemMessage(player, space_dungeon.SID_ILLEGAL_TICKET);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (!manage_name.equals("dungeon"))
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (request_id < 1)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_data == null)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- dungeon_data is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (element_name_list == null)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- element_name_list is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_type == null)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- dungeon_type is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = dungeon_type.substring(0, dungeon_type.length() - 1);
        if (false == space_dungeon_data.isValidDungeon(dungeon_name))
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- dungeon name of " + dungeon_name + " is not in the dungeon datatable.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        Vector potential_dungeons = new Vector();
        for (int i = 0; i < dungeon_data.length; i++)
        {
            dictionary dungeon = dungeon_data[i];
            if (dungeon == null)
            {
                LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- bad data found for dungeon entry " + i + " (dungeon instance (" + element_name_list[i] + ") has no dungeon_data). Ignoring.");
                continue;
            }
            obj_id dungeon_id = dungeon.getObjId("dungeon_id");
            int session_id = dungeon.getInt("session_id");
            float[] dungeon_position = 
            {
                dungeon.getFloat("position_x"),
                dungeon.getFloat("position_y"),
                dungeon.getFloat("position_z")
            };
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- session_id ->" + session_id + " dungeon_instance ->" + element_name_list[i] + " " + dungeon_position[0] + " " + dungeon_position[1] + " " + dungeon_position[2]);
            if (!isIdValid(dungeon_id))
            {
                LOG("space_dungeon", "travel_player_space_dungeon_falcon.OnClusterWideDataResponse -- bad data found for dungeon entry " + i + ". Ignoring.");
                continue;
            }
            if (session_id < 1)
            {
                potential_dungeons.add(new Integer(i));
            }
        }
        if (potential_dungeons.size() > 0)
        {
            int randomPotentialDungeonsIndex = rand(0, potential_dungeons.size() - 1);
            int dungeonIndex = (((Integer)potential_dungeons.get(randomPotentialDungeonsIndex))).intValue();
            dictionary dungeon = dungeon_data[dungeonIndex];
            obj_id dungeon_id = dungeon.getObjId("dungeon_id");
            int session_id = dungeon.getInt("session_id");
            setObjVar(player, space_dungeon.VAR_SESSION_ID, lock_key);
            dictionary dungeon_update = new dictionary();
            dungeon_update.put("session_id", lock_key);
            updateClusterWideData(manage_name, element_name_list[dungeonIndex], dungeon_update, lock_key);
            releaseClusterWideDataLock(manage_name, lock_key);
            dictionary d = new dictionary();
            d.put("session_id", lock_key);
            d.put("request_id", request_id);
            d.put("player", player);
            d.put("ticket_collector", self);
            if (hasObjVar(ticket, space_dungeon.VAR_TICKET_QUEST_TYPE))
            {
                d.put("quest_type", getStringObjVar(ticket, space_dungeon.VAR_TICKET_QUEST_TYPE));
            }
            setObjVar(self, "space.launch.ship", dungeon_id);
            setObjVar(self, "space.launch.startIndex", 0);
            setObjVar(self, space_dungeon.VAR_EJECT_OVERRIDE, 1);
            setObjVar(self, "space.launch.worldLoc", new location(0, 0, 0, "tutorial"));
            messageTo(dungeon_id, "msgSetSessionId", d, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        space_dungeon.cleanupPlayerTicketObjvars(player);
        space_dungeon.removeDungeonTraveler(self, request_id);
        LIVE_LOG("npe", "Could not find a falcon instance for player " + self + " something has definately gone wrong! Maybe too many people are in the tutorial planet asking for falcon instances)");
        string_id success = space_dungeon_data.getDungeonFailureString(dungeon_name);
        if (success == null)
        {
            sendSystemMessage(player, SID_UNABLE_TO_FIND_DUNGEON);
        }
        else 
        {
            sendSystemMessage(player, success);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEjectedFromDungeon(obj_id self, dictionary params) throws InterruptedException
    {
        resetShipOwner(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        resetShipOwner(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        resetShipOwner(self);
        return SCRIPT_CONTINUE;
    }
    public void resetShipOwner(obj_id self) throws InterruptedException
    {
        obj_id containing_dungeon = space_dungeon.getDungeonIdForPlayer(self);
        if (isIdValid(containing_dungeon) && isGameObjectTypeOf(getGameObjectType(containing_dungeon), GOT_ship))
        {
            LOG("npe", "Player " + self + " is resetting the ship owner to null for the falcon instance they are in (" + containing_dungeon + ")");
            setOwner(containing_dungeon, null);
        }
    }
    public int msgStartDungeonTravel(obj_id self, dictionary params) throws InterruptedException
    {
        int session_id = params.getInt("session_id");
        LOG("space_dungeon", "msgStartDungeonTravel -- session_id ->" + session_id);
        obj_id dungeon_id = params.getObjId("dungeon_id");
        if (!isIdValid(dungeon_id))
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.msgStartDungeonTravel -- dungeon_id is invalid for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = params.getString("dungeon_name");
        if (dungeon_id == null)
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.msgStartDungeonTravel -- dungeon_name is null for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        int request_id = params.getInt("request_id");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.msgStartDungeonTravel -- player is null for " + self);
            return SCRIPT_CONTINUE;
        }
        space_dungeon.removeDungeonTraveler(self, request_id);
        if (player.isAuthoritative())
        {
            location dungeon_loc = params.getLocation("dungeon_loc");
            if (dungeon_loc == null)
            {
                LOG("space_dungeon", "travel_player_space_dungeon_falcon.msgStartDungeonTravel -- location is null for " + self + ".");
                return SCRIPT_CONTINUE;
            }
            space_dungeon.moveSinglePlayerIntoDungeon(player, dungeon_id, dungeon_name, dungeon_loc);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgDungeonTravelComplete(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, space_dungeon.VAR_RESET_DUNGEON))
        {
            obj_id dungeon_id = getObjIdObjVar(self, space_dungeon.VAR_RESET_DUNGEON);
            dictionary d = new dictionary();
            messageTo(dungeon_id, "msgManualDungeonReset", d, 0.0f, false);
            removeObjVar(self, space_dungeon.VAR_RESET_DUNGEON);
        }
        obj_id dungeon = space_dungeon.getDungeonIdForPlayer(self);
        if (!isIdValid(dungeon))
        {
            LOG("space_dungeon", "travel_player_space_dungeon_falcon.msgDungeonTravelComplete - can't get id of dungeon I am in");
            return SCRIPT_CONTINUE;
        }
        setOwner(dungeon, self);
        return SCRIPT_CONTINUE;
    }
}
