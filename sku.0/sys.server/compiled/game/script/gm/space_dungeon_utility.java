package script.gm;

import script.dictionary;
import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.utils;
import script.location;
import script.obj_id;

public class space_dungeon_utility extends script.base_script
{
    public space_dungeon_utility()
    {
    }
    public static final String SCRIPT_VAR_DATA_MODE = "space_dungeon.data_mode";
    public static final String SCRIPT_VAR_MOVE_DUNGEON = "space_dungeon.move_dungeon";
    public static final String SCRIPT_VAR_TARGET = "space_dungeon.target";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (getGodLevel(self) < 10)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id target = getLookAtTarget(self);
        if (!isIdValid(target))
        {
            target = self;
        }
        location loc = getLocation(self);
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String[] command_tokens = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens())
        {
            command_tokens[i] = st.nextToken();
            i++;
        }
        text = text.toLowerCase();
        if (text.startsWith("dungeonticket"))
        {
            if (!checkFormat(command_tokens, 3))
            {
                sendSystemMessageTestingOnly(self, "Format: dungeonTicket <planet> <point> <dungeon> {quest type}");
                return SCRIPT_CONTINUE;
            }
            if (false == space_dungeon_data.isValidDungeon(command_tokens[3]))
            {
                sendSystemMessageTestingOnly(self, command_tokens[3] + " is not a valid dungeon name.");
                return SCRIPT_CONTINUE;
            }
            obj_id ticket = space_dungeon.createTicket(self, command_tokens[1], command_tokens[2], command_tokens[3]);
            if (isIdValid(ticket))
            {
                if (checkFormat(command_tokens, 4))
                {
                    setObjVar(ticket, space_dungeon.VAR_TICKET_QUEST_TYPE, command_tokens[4]);
                }
                sendSystemMessageTestingOnly(self, "Dungeon ticket " + ticket + " created.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Dungeon ticket creation failed.");
            }
        }
        if (text.startsWith("cleardungeondata"))
        {
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a lookat target.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a player targetted.");
                return SCRIPT_CONTINUE;
            }
            if (hasObjVar(target, space_dungeon.VAR_TICKET_USED))
            {
                removeObjVar(target, space_dungeon.VAR_TICKET_USED);
            }
            if (hasObjVar(target, space_dungeon.VAR_SESSION_ID))
            {
                removeObjVar(target, space_dungeon.VAR_SESSION_ID);
            }
        }
        if (text.startsWith("enddungeonsession"))
        {
            obj_id dungeon = getTopMostContainer(self);
            if (!isIdValid(dungeon) || !(hasObjVar(dungeon, space_dungeon.VAR_DUNGEON_NAME)))
            {
                sendSystemMessageTestingOnly(self, "You must be in a dungeon to do this.");
                return SCRIPT_CONTINUE;
            }
            messageTo(self, "cleanOutDungeon", null, 0.0f, false);
            if (space_dungeon.endDungeonSession(dungeon))
            {
                sendSystemMessageTestingOnly(self, "Dungeon session ended.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Failed to end dungeon session.");
            }
        }
        if (text.startsWith("ejectplayer"))
        {
            obj_id dungeon = getTopMostContainer(self);
            if (!isIdValid(dungeon) || !(hasObjVar(dungeon, space_dungeon.VAR_DUNGEON_NAME)))
            {
                sendSystemMessageTestingOnly(self, "You must be in a dungeon to do this.");
                return SCRIPT_CONTINUE;
            }
            if (!isIdValid(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a lookat target.");
                return SCRIPT_CONTINUE;
            }
            if (!isPlayer(target))
            {
                sendSystemMessageTestingOnly(self, "You must have a player targetted.");
                return SCRIPT_CONTINUE;
            }
            if (space_dungeon.ejectPlayerFromDungeon(target))
            {
                sendSystemMessageTestingOnly(self, getFirstName(target) + " has been ejected from the dungeon.");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "Dungeon ejection failed.");
            }
        }
        if (text.startsWith("dungeonstatus"))
        {
            if (!checkFormat(command_tokens, 1))
            {
                sendSystemMessageTestingOnly(self, "Format: dungeonStatus <name>");
                return SCRIPT_CONTINUE;
            }
            if (false == space_dungeon_data.isValidDungeon(command_tokens[1]))
            {
                sendSystemMessageTestingOnly(self, "The name you specified (" + command_tokens[1] + ") is not a recognized dungeon name.");
                return SCRIPT_CONTINUE;
            }
            sendSystemMessageTestingOnly(self, "Requesting dungeon data.  Do not move during this process!");
            int request_id = getClusterWideData("dungeon", command_tokens[1] + "*", false, self);
            utils.setScriptVar(self, SCRIPT_VAR_DATA_MODE + "_" + request_id, 1);
        }
        if (text.startsWith("resetdungeon"))
        {
            if (!checkFormat(command_tokens, 1))
            {
                sendSystemMessageTestingOnly(self, "Format: resetDungeon <name> {obj_id}");
                return SCRIPT_CONTINUE;
            }
            String dungeon_name = command_tokens[1];
            boolean instance_change = false;
            if (false == space_dungeon_data.isValidDungeon(dungeon_name))
            {
                sendSystemMessageTestingOnly(self, "The name you specified (" + dungeon_name + ") is not a recognized dungeon name.");
                return SCRIPT_CONTINUE;
            }
            if (command_tokens.length > 2)
            {
                obj_id dungeon_id = utils.stringToObjId(command_tokens[2]);
                if (isIdValid(dungeon_id))
                {
                    dungeon_name = dungeon_name + "-" + dungeon_id;
                    instance_change = true;
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "You must specify a valid obj_id.");
                    return SCRIPT_CONTINUE;
                }
            }
            sendSystemMessageTestingOnly(self, "Requesting dungeon data.  Do not move during this process!");
            int request_id = -1;
            if (instance_change)
            {
                request_id = getClusterWideData("dungeon", dungeon_name, false, self);
            }
            else 
            {
                request_id = getClusterWideData("dungeon", dungeon_name + "*", false, self);
            }
            utils.setScriptVar(self, SCRIPT_VAR_DATA_MODE + "_" + request_id, 2);
        }
        if (text.startsWith("movetodungeon"))
        {
            if (!checkFormat(command_tokens, 2))
            {
                sendSystemMessageTestingOnly(self, "Format: resetDungeon <name> <obj_id>");
                return SCRIPT_CONTINUE;
            }
            if (false == space_dungeon_data.isValidDungeon(command_tokens[1]))
            {
                sendSystemMessageTestingOnly(self, "The name you specified (" + command_tokens[1] + ") is not a recognized dungeon name.");
                return SCRIPT_CONTINUE;
            }
            obj_id dungeon_id = utils.stringToObjId(command_tokens[2]);
            if (isIdValid(dungeon_id))
            {
                int request_id = getClusterWideData("dungeon", command_tokens[1] + "-" + dungeon_id, false, self);
                utils.setScriptVar(self, SCRIPT_VAR_DATA_MODE + "_" + request_id, 3);
                utils.setScriptVar(self, SCRIPT_VAR_MOVE_DUNGEON, command_tokens[1]);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You must specify a valid obj_id.");
                return SCRIPT_CONTINUE;
            }
        }
        if (text.startsWith("dungeoncommands"))
        {
            if (!checkFormat(command_tokens, 1))
            {
                sendSystemMessageTestingOnly(self, "Dungeon Commands: dungeonTicket, clearDungeonData, endDungeonSession, ejectPlayer, dungeonStatus, resetDungeon, moveToDungeon");
                return SCRIPT_CONTINUE;
            }
            if (command_tokens[1].equals("?"))
            {
                sendSystemMessageTestingOnly(self, "dungeonTicket <planet> <point> <dungeon> {quest type}: creates a dungeon ticket with the specified parameters. <planet> -- planet of departure. <point> -- name of the npc ticket collector as given in space_dungeon.ticket.point {quest type} -- optional ticket quest data.  This is written in space_dungeon.ticket.quest_type.  What should go in here is dependent on the quest taken.");
                sendSystemMessageTestingOnly(self, "clearDungeonData: get a target player out of the stuck state of 'You already have an outstanding travel request.'");
                sendSystemMessageTestingOnly(self, "endDungeonSession: resets the dungeon that you are currently in.  This ejects all players within the dungeon.");
                sendSystemMessageTestingOnly(self, "dungeonStatus <name>: Lists all of the dungeon instances and their corresponding session ids for the specified name.  Dungeons with session ids of -1 are open.");
                sendSystemMessageTestingOnly(self, "resetDungeon <name> {obj_id}: Resets all of the dungeons of a specified name. If you specify the optional obj_id, it'll only reset that dungeon.");
                sendSystemMessageTestingOnly(self, "moveToDungeon <name> <obj_id>: Moves you to the start location of a given dungeon name and obj_id.  Note that this only moves you to the dungeon and is not considered as a legal transport.  Keep your god mode on or you will get ejected.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_type, int request_id, String[] element_name_list, dictionary[] dungeon_data, int lock_key) throws InterruptedException
    {
        if (!manage_name.equals("dungeon"))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(self, "Dungeon data received....");
        String script_var_name = SCRIPT_VAR_DATA_MODE + "_" + request_id;
        int data_mode = -1;
        if (utils.hasScriptVar(self, script_var_name))
        {
            data_mode = utils.getIntScriptVar(self, script_var_name);
            utils.removeScriptVar(self, script_var_name);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Unable to determine data mode.");
            return SCRIPT_CONTINUE;
        }
        if (dungeon_data != null && dungeon_data.length > 0)
        {
            for (dictionary dungeon : dungeon_data) {
                obj_id dungeon_id = dungeon.getObjId("dungeon_id");
                int session_id = dungeon.getInt("session_id");
                float[] dungeon_position =
                        {
                                dungeon.getFloat("position_x"),
                                dungeon.getFloat("position_y"),
                                dungeon.getFloat("position_z")
                        };
                switch (data_mode) {
                    case 1:
                        sendSystemMessageTestingOnly(self, "Dungeon -> " + dungeon_id + " session_id ->" + session_id);
                        break;
                    case 2:
                        sendSystemMessageTestingOnly(self, "Resetting dungeon " + dungeon_id);
                        messageTo(dungeon_id, "msgManualDungeonReset", null, 0.0f, false);
                        break;
                    case 3:
                        String dungeon_name;
                        if (utils.hasScriptVar(self, SCRIPT_VAR_MOVE_DUNGEON)) {
                            dungeon_name = utils.getStringScriptVar(self, SCRIPT_VAR_MOVE_DUNGEON);
                            utils.removeScriptVar(self, SCRIPT_VAR_MOVE_DUNGEON);
                        } else {
                            sendSystemMessageTestingOnly(self, "Unable to find dungeon name. Move failed.");
                            return SCRIPT_CONTINUE;
                        }
                        if (dungeon_data.length > 1) {
                            sendSystemMessageTestingOnly(self, "More than one dungeon was returned.  This should not have happened. Aborting movement.");
                            return SCRIPT_CONTINUE;
                        }
                        location start_loc = space_dungeon_data.getDungeonStartLocation(dungeon_name);
                        if (start_loc == null) {
                            sendSystemMessageTestingOnly(self, "Unable to find a start location for " + dungeon_name + ". Movement failed.");
                            return SCRIPT_CONTINUE;
                        }
                        String start_cell = space_dungeon_data.getDungeonStartCellName(dungeon_name);
                        if (start_cell == null) {
                            sendSystemMessageTestingOnly(self, "Unable to find a start cell for " + dungeon_name + ". Movement failed.");
                            return SCRIPT_CONTINUE;
                        }
                        warpPlayer(self, start_loc.area, dungeon_position[0], dungeon_position[1], dungeon_position[2], dungeon_id, start_cell, start_loc.x, start_loc.y, start_loc.z);
                        break;
                    default:
                        sendSystemMessageTestingOnly(self, "Unknown data mode.");
                        break;
                }
            }
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "No dungeon data received.  This could be the result of an error or that no dungeon data matched your search criteria.");
            if (utils.hasScriptVar(self, "space_dungeon"))
            {
                utils.removeScriptVar(self, "space_dungeon");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean checkFormat(String[] command_tokens, int params) throws InterruptedException
    {
        if (command_tokens == null)
        {
            return false;
        }
        if (command_tokens.length <= params)
        {
            return false;
        }
        for (int i = 1; i <= params; i++)
        {
            if (command_tokens[i].length() == 0)
            {
                return false;
            }
        }
        return true;
    }
}
