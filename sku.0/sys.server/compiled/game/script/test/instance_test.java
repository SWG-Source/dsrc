package script.test;

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

public class instance_test extends script.base_script
{
    public instance_test()
    {
    }
    public static final String dataTable = "datatables/dungeon/space_dungeon.iff";
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final boolean doLogging = false;
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "space_dungeon.ticket.point"))
        {
            removeObjVar(self, "space_dungeon.ticket.point");
        }
        if (hasObjVar(self, "space_dungeon.ticket.dungeon"))
        {
            removeObjVar(self, "space_dungeon.ticket.dungeon");
        }
        if (utils.hasScriptVar(self, "idx"))
        {
            utils.removeScriptVar(self, "idx");
        }
        if (utils.hasScriptVar(self, "instanceType"))
        {
            utils.removeScriptVar(self, "instanceType");
        }
        return SCRIPT_CONTINUE;
    }
    public int cmdActivateInstance(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String[] dungeons = dataTableGetStringColumn(dataTable, "dungeon");
        sui.listbox(self, self, "Select Instnace Dungeon", sui.OK_CANCEL, "Select Dungeon", dungeons, "handleSelectInstance", true);
        return SCRIPT_CONTINUE;
    }
    public int handleSelectInstance(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        int rows = dataTableGetNumRows(dataTable);
        if (idx < 0 || idx > rows - 1)
        {
            sendSystemMessageTestingOnly(player, "Invalid dungeon selected");
            return SCRIPT_CONTINUE;
        }
        dictionary dungeonDict = dataTableGetRow(dataTable, idx);
        String type = dungeonDict.getString("instanceType");
        if (!type.equals("dungeon_default"))
        {
            String[] parse = split(type, ':');
            utils.setScriptVar(player, "idx", idx);
            utils.setScriptVar(player, "instanceType", type);
            sui.listbox(self, player, "Select Instance Type", sui.OK_CANCEL, "Select Type", parse, "handleSelectType", true);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String dungeon = dungeonDict.getString("dungeon");
            String scene = getCurrentSceneName();
            setObjVar(self, "space_dungeon.ticket.point", scene);
            setObjVar(self, "space_dungeon.ticket.dungeon", dungeon);
            space_dungeon.sendGroupToDungeonWithoutTicket(self, dungeon, scene, scene, type, self);
            return SCRIPT_CONTINUE;
        }
    }
    public int handleSelectType(obj_id self, dictionary params) throws InterruptedException
    {
        int dungeonIdx = utils.getIntScriptVar(self, "idx");
        String dungeonType = utils.getStringScriptVar(self, "instanceType");
        String[] parse = split(dungeonType, ':');
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        dictionary dungeonDict = dataTableGetRow(dataTable, dungeonIdx);
        if (idx < 0 || idx > parse.length - 1)
        {
            sendSystemMessageTestingOnly(player, "Invalid Selection");
            return SCRIPT_CONTINUE;
        }
        else 
        {
            String type = parse[idx];
            String dungeon = dungeonDict.getString("dungeon");
            String scene = getCurrentSceneName();
            setObjVar(self, "space_dungeon.ticket.point", scene);
            setObjVar(self, "space_dungeon.ticket.dungeon", dungeon);
            space_dungeon.sendGroupToDungeonWithoutTicket(self, dungeon, scene, scene, type, self);
            return SCRIPT_CONTINUE;
        }
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_type, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        LOG("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player) || !player.isAuthoritative())
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- player is invalid or not authoritative.");
            releaseClusterWideDataLock("dungeon", lock_key);
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
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (!manage_name.equals("dungeon"))
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- ignorning manage_name " + manage_name + " because it is not dungeon.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (request_id < 1)
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- invalid request_id value of " + request_id);
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_data == null)
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_data is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (element_name_list == null)
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- element_name_list is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        if (dungeon_type == null)
        {
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon_type is null.");
            space_dungeon.cleanupPlayerTicketObjvars(player);
            releaseClusterWideDataLock("dungeon", lock_key);
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = dungeon_type.substring(0, dungeon_type.length() - 1);
        for (int i = 0; i < dungeon_data.length; i++)
        {
            if (false == space_dungeon_data.isValidDungeon(dungeon_name))
            {
                doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- dungeon name of " + dungeon_name + " is not in the dungeon datatable.");
                break;
            }
            dictionary dungeon = dungeon_data[i];
            obj_id dungeon_id = dungeon.getObjId("dungeon_id");
            int session_id = dungeon.getInt("session_id");
            doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- session_id ->" + session_id + " dungeon_instance ->" + element_name_list[i]);
            if (!isIdValid(dungeon_id))
            {
                doLogging("space_dungeon", "travel_space_dungeon.OnClusterWideDataResponse -- bad data found for dungeon entry " + i + ". Ignoring.");
                continue;
            }
            if (session_id < 1)
            {
                setObjVar(player, space_dungeon.VAR_SESSION_ID, lock_key);
                dictionary dungeon_update = new dictionary();
                dungeon_update.put("session_id", lock_key);
                updateClusterWideData("dungeon", element_name_list[i], dungeon_update, lock_key);
                releaseClusterWideDataLock("dungeon", lock_key);
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
        releaseClusterWideDataLock("dungeon", lock_key);
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
        doLogging("space_dungeon", "msgStartDungeonTravel -- session_id ->" + session_id);
        obj_id dungeon_id = params.getObjId("dungeon_id");
        if (!isIdValid(dungeon_id))
        {
            doLogging("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_id is invalid for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        String dungeon_name = params.getString("dungeon_name");
        if (dungeon_id == null)
        {
            doLogging("space_dungeon", "player_travel.msgStartDungeonTravel -- dungeon_name is null for " + self + ".");
            return SCRIPT_CONTINUE;
        }
        int request_id = params.getInt("request_id");
        obj_id player = space_dungeon.getDungeonTraveler(self, request_id);
        if (!isIdValid(player))
        {
            doLogging("space_dungeon", "player_travel.msgStartDungeonTravel -- player is null for " + self);
            return SCRIPT_CONTINUE;
        }
        space_dungeon.removeDungeonTraveler(self, request_id);
        if (player.isAuthoritative())
        {
            location dungeon_loc = params.getLocation("dungeon_loc");
            if (dungeon_loc == null)
            {
                doLogging("space_dungeon", "travel_space_dungeon.msgStartDungeonTravel -- location is null for " + self + ".");
                return SCRIPT_CONTINUE;
            }
            space_dungeon.movePlayerGroupToDungeon(player, dungeon_id, dungeon_name, dungeon_loc);
        }
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/instance_test/" + section, message);
        }
    }
}
