package script.item.travel_ticket;

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

public class travel_player_space_dungeon extends script.base_script
{
    public travel_player_space_dungeon()
    {
    }
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_REQUEST_TRAVEL = new string_id("dungeon/space_dungeon", "request_travel");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDING = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_type, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (player != self)
        {
            LOG("space_dungeon", "travel_player_space_dungeon.OnClusterWideDataResponse -- got a request to send a player that is not self, self: " + self + " request " + player);
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
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (request_id < 1)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_data == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_data is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (element_name_list == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- element_name_list is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_type == null)
        {
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_type is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock(manage_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = dungeon_type.substring(0, dungeon_type.length() - 1);
        for (int i = 0; i < dungeon_data.length; i++)
        {
            if (false == space_dungeon_data.isValidDungeon(dungeon_name))
            {
                LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon name of " + dungeon_name + " is not in the dungeon datatable.");
                break;
            }
            dictionary dungeon = dungeon_data[i];
            obj_id dungeon_id = dungeon.getObjId("dungeon_id");
            int session_id = dungeon.getInt("session_id");
            LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- session_id ->" + session_id + " dungeon_instance ->" + element_name_list[i]);
            if (!isIdValid(dungeon_id))
            {
                LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- bad data found for dungeon entry " + i + ". Ignoring.");
                continue;
            }
            if (session_id < 1)
            {
                setObjVar(player, space_dungeon.VAR_SESSION_ID, lock_key);
                dictionary dungeon_update = new dictionary();
                dungeon_update.put("session_id", lock_key);
                updateClusterWideData(manage_name, element_name_list[i], dungeon_update, lock_key);
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
                messageTo(dungeon_id, "msgSetSessionId", d, 0.0f, false);
                return SCRIPT_CONTINUE;
            }
        }
        releaseClusterWideDataLock(manage_name, lock_key);
        space_dungeon.cleanupPlayerTicketObjvars(player);
        space_dungeon.removeDungeonTraveler(self, request_id);
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
    public int msgStartDungeonTravel(obj_id self, dictionary params) throws InterruptedException
    {
        int session_id = params.getInt("session_id");
        LOG("space_dungeon", "msgStartDungeonTravel -- session_id ->" + session_id);
        obj_id dungeon_id = params.getObjId("dungeon_id");
        if (!isIdValid(dungeon_id))
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_id is invalid for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = params.getString("dungeon_name");
        if (dungeon_id == null)
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_name is null for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        int request_id = params.getInt("request_id");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player))
        {
            LOG("space_dungeon", "player_travel.msgStartDungeonTravel -- player is null for " + self);
            return SCRIPT_CONTINUE;
        }
        space_dungeon.removeDungeonTraveler(self, request_id);
        if (player.isAuthoritative())
        {
            location dungeon_loc = params.getLocation("dungeon_loc");
            if (dungeon_loc == null)
            {
                LOG("space_dungeon", "travel_space_dungeon.msgStartDungeonTravel -- location is null for " + self + ".");
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
        return SCRIPT_CONTINUE;
    }
}
