package script.systems.battlefield;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.battlefield;
import script.library.regions;
import script.library.pclib;
import script.library.factions;
import script.library.player_structure;
import script.library.healing;
import script.library.sui;
import script.library.utils;

public class player_battlefield extends script.base_script
{
    public player_battlefield()
    {
    }
    public static final String VAR_PLACED_STRUCTURE = "battlefield.placed_structure";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::OnLogout");
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        battlefield.expelPlayerFromBattlefield(self, master_object);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, battlefield.VAR_BATTLEFIELD))
        {
            removeObjVar(self, battlefield.VAR_BATTLEFIELD);
        }
        if (utils.hasScriptVar(self, battlefield.VAR_SELECTING_FACTION))
        {
            utils.removeScriptVar(self, battlefield.VAR_SELECTING_FACTION);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPlaceStructure(obj_id self, obj_id player, obj_id deed, location position, int rotation) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::OnPlaceStructure");
        if (player != deed)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(player, VAR_PLACED_STRUCTURE))
        {
            return SCRIPT_CONTINUE;
        }
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        int faction_id = pvpBattlefieldGetFaction(player, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        if (!battlefield.isNearBattlefieldConstructor(master_object, position, faction))
        {
            LOG("LOG_CHANNEL", self + " ->This is too far away from one of your constructors.");
            sendSystemMessageTestingOnly(self, "This is too far away from one of your constructors.");
            return SCRIPT_OVERRIDE;
        }
        String template = getStringObjVar(player, VAR_PLACED_STRUCTURE);
        removeObjVar(player, VAR_PLACED_STRUCTURE);
        dictionary stats = battlefield.getBuildableStructureStats(master_object, template);
        if (stats == null)
        {
            LOG("LOG_CHANNEL", player + " ->You cannot build that in this battlefield.");
            sendSystemMessageTestingOnly(player, "You cannot build that in this battlefield.");
            return SCRIPT_OVERRIDE;
        }
        region bf_check = battlefield.getBattlefield(position);
        if (bf_check == null)
        {
            LOG("LOG_CHANNEL", player + " ->You must place structures within the battlefield.");
            sendSystemMessageTestingOnly(player, "You must place structures within the battlefield.");
            return SCRIPT_CONTINUE;
        }
        String name = stats.getString("name");
        int cost = stats.getInt("cost");
        int build_points = battlefield.getFactionBuildPoints(master_object, faction);
        if (build_points < cost)
        {
            LOG("LOG_CHANNEL", self + " ->There are insufficent build points remaining.");
            sendSystemMessageTestingOnly(self, "There are insufficent build points remaining.");
            return SCRIPT_OVERRIDE;
        }
        battlefield.decrementFactionBuildPoints(master_object, faction, cost);
        float placement_height = canPlaceStructure(template, position, rotation);
        if (placement_height == -9997.0f)
        {
            LOG("LOG_CHANNEL", player + " ->Internal code error: canPlaceStructure");
            sendSystemMessageTestingOnly(player, "Internal code error: canPlaceStructure");
            return SCRIPT_CONTINUE;
        }
        if (placement_height == -9998.0f)
        {
            LOG("LOG_CHANNEL", player + " ->Internal script error: OnPlaceStructure");
            sendSystemMessageTestingOnly(player, "Internal script error: OnPlaceStructure");
            return SCRIPT_CONTINUE;
        }
        if (placement_height == -9999.0f)
        {
            LOG("LOG_CHANNEL", player + " ->There is no room to place the structure here.");
            sendSystemMessageTestingOnly(player, "There is no room to place the structure here.");
            return SCRIPT_CONTINUE;
        }
        position.y = getHeightAtLocation(position.x, position.z);
        stats.put("height", placement_height);
        battlefield.startBuildingConstruction(master_object, player, position, rotation, stats);
        return SCRIPT_OVERRIDE;
    }
    public int msgBattlefieldFactionSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgBattlefieldFactionSelected");
        utils.removeScriptVar(self, battlefield.VAR_SELECTING_FACTION);
        if (hasObjVar(self, battlefield.VAR_BATTLEFIELD_TO_ENTER))
        {
            LOG("LOG_CHANNEL", "!!!!!!!!!player_battlefield::msgBattlefieldFactionSelected");
            String button = params.getString("buttonPressed");
            if (button.equals("Cancel") || button.equals("No"))
            {
                removeObjVar(self, battlefield.VAR_BATTLEFIELD);
                return SCRIPT_CONTINUE;
            }
            obj_id master_object = getObjIdObjVar(self, battlefield.VAR_BATTLEFIELD_TO_ENTER);
            removeObjVar(self, battlefield.VAR_BATTLEFIELD);
            if (master_object.isLoaded())
            {
                int selected_faction_id = pvpGetAlignedFaction(self);
                String selected_faction = factions.getFactionNameByHashCode(selected_faction_id);
                if (selected_faction == null)
                {
                    String[] factions = battlefield.getFactionsAllowed(master_object);
                    String[] factions_remaining = battlefield.getFactionsRemaining(master_object);
                    int row_selected = sui.getListboxSelectedRow(params);
                    if (row_selected == -1)
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (row_selected >= factions.length)
                    {
                        LOG("LOG_CHANNEL", self + " ->That is an invalid selection.");
                        sendSystemMessageTestingOnly(self, "That is an invalid selection.");
                        return SCRIPT_CONTINUE;
                    }
                    selected_faction = factions[row_selected];
                }
                if (battlefield.isFactionRemaining(master_object, selected_faction))
                {
                    if (battlefield.canJoinFaction(master_object, self, selected_faction))
                    {
                        battlefield.addPlayerToTeam(self, selected_faction, master_object);
                    }
                }
                else 
                {
                    LOG("LOG_CHANNEL", self + " ->That faction has been eliminated from the battle.");
                    sendSystemMessageTestingOnly(self, "That faction has been eliminated from the battle.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgEndBattlefieldGame(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgEndBattlefieldGame -- " + self);
        if (hasObjVar(self, battlefield.VAR_BATTLEFIELD))
        {
            region bf = battlefield.getBattlefield(self);
            if (bf != null)
            {
                obj_id bf_current = battlefield.getMasterObjectFromRegion(bf);
                obj_id bf_entered = battlefield.getBattlefieldEntered(self);
                if (bf_current == bf_entered)
                {
                    String faction = battlefield.getPlayerTeamFaction(self);
                    if (faction != null)
                    {
                        String faction_chat_room = battlefield.getChatRoomNameFaction(bf_entered, faction);
                        chatExitRoom(faction_chat_room);
                        LOG("LOG_CHANNEL", self + " ->Exiting chatroom: " + faction_chat_room);
                    }
                    if (hasObjVar(self, battlefield.VAR_WAYPOINTS))
                    {
                        obj_id[] waypoint_list = getObjIdArrayObjVar(self, battlefield.VAR_WAYPOINTS);
                        if (waypoint_list != null)
                        {
                            for (int i = 0; i < waypoint_list.length; i++)
                            {
                                LOG("LOG_CHANNEL", "player_battlefield::msgEndBattlefieldGame -- waypoint " + waypoint_list[i] + "removed.");
                                if (isIdValid(waypoint_list[i]))
                                {
                                    destroyWaypointInDatapad(waypoint_list[i], self);
                                }
                            }
                        }
                    }
                    removeObjVar(self, battlefield.VAR_BATTLEFIELD);
                }
            }
            else 
            {
                String chatroom = params.getString("chatroom");
                chatExitRoom(chatroom);
                LOG("LOG_CHANNEL", self + " ->!!Exiting chatroom: " + chatroom);
                if (params.containsKey("chatroom_faction"))
                {
                    String chatroom_faction = params.getString("chatroom_faction");
                    chatExitRoom(chatroom_faction);
                    LOG("LOG_CHANNEL", self + " ->!!Exiting chatroom: " + chatroom_faction);
                }
                cleanPlayer(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int msgJoinBattlefieldChat(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgJoinBattlefieldChat");
        String chat_room = params.getString("chat_room");
        chatEnterRoom(chat_room);
        LOG("LOG_CHANNEL", self + " ->Joining chatroom: " + chat_room);
        return SCRIPT_CONTINUE;
    }
    public int msgExitBattlefieldChat(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgExitBattlefieldChat");
        String chat_room = params.getString("chat_room");
        chatExitRoom(chat_room);
        LOG("LOG_CHANNEL", self + " ->Exiting chatroom: " + chat_room);
        return SCRIPT_CONTINUE;
    }
    public int msgBattlefieldDeath(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgBattlefieldDeath");
        obj_id master_object = params.getObjId("master_object");
        if (hasObjVar(self, pclib.VAR_BEEN_COUPDEGRACED))
        {
            removeObjVar(self, pclib.VAR_BEEN_COUPDEGRACED);
        }
        if (master_object != null)
        {
            if (battlefield.isBattlefieldActive(master_object))
            {
                battlefield.expelPlayerFromBattlefield(self, master_object);
                LOG("LOG_CHANNEL", self + " ->You are about to be resuscitated! You may not return to the battlefield until the current battle has ended.");
                sendSystemMessageTestingOnly(self, "You are about to be resuscitated! You may not return to the battlefield until the current battle has ended.");
            }
            else 
            {
                LOG("LOG_CHANNEL", self + " ->You are about to be revived!");
                sendSystemMessageTestingOnly(self, "You are about to be revived!");
            }
        }
        if (hasObjVar(self, "combat.intIncapacitationCount"))
        {
            setObjVar(self, "combat.intIncapacitationCount", 0);
        }
        pclib.resurrectPlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int msgRemoveBattlefieldScript(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgRemoveBattlefieldScript");
        obj_id master_object = params.getObjId("master_object");
        if (master_object.isLoaded())
        {
            String chat_room = battlefield.getChatRoomNameAllFactions(master_object);
            chatExitRoom(chat_room);
            LOG("LOG_CHANNEL", self + " ->Exiting chatroom: " + chat_room);
            String faction = battlefield.getPlayerTeamFaction(self);
            if (faction != null)
            {
                String faction_chat_room = battlefield.getChatRoomNameFaction(master_object, faction);
                chatExitRoom(faction_chat_room);
                LOG("LOG_CHANNEL", self + " ->Exiting chatroom: " + faction_chat_room);
            }
        }
        cleanPlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int msgBuildStructureSelected(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgBuildStructureSelected");
        String button = params.getString("buttonPressed");
        if (button.equals("Cancel"))
        {
            return SCRIPT_CONTINUE;
        }
        int row_selected = sui.getListboxSelectedRow(params);
        if (row_selected == -1)
        {
            return SCRIPT_CONTINUE;
        }
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        int faction_id = pvpBattlefieldGetFaction(self, bf);
        String faction = factions.getFactionNameByHashCode(faction_id);
        if (!battlefield.canBuildBattlefieldStructure(master_object, self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] buildable_structures = battlefield.getBuildableStructures(master_object, faction);
        if (row_selected >= buildable_structures.length)
        {
            LOG("LOG_CHANNEL", self + " ->That is an invalid selected.");
            sendSystemMessageTestingOnly(self, "That is an invalid selected.");
            return SCRIPT_CONTINUE;
        }
        String structure_name = buildable_structures[row_selected];
        dictionary structure_stats = battlefield.getBuildableStructureStats(master_object, structure_name);
        if (structure_stats == null)
        {
            LOG("LOG_CHANNEL", self + " ->Unable to create that structure in this battlefield.");
            sendSystemMessageTestingOnly(self, "Unable to create that structure in this battlefield.");
            return SCRIPT_CONTINUE;
        }
        String template = structure_stats.getString("template");
        String name = structure_stats.getString("name");
        int cost = structure_stats.getInt("cost");
        int build_points = battlefield.getFactionBuildPoints(master_object, faction);
        if (build_points < cost)
        {
            LOG("LOG_CHANNEL", self + " ->There are insufficent build points remaining.");
            sendSystemMessageTestingOnly(self, "There are insufficent build points remaining.");
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, VAR_PLACED_STRUCTURE, template);
        enterClientStructurePlacementMode(self, self, template);
        return SCRIPT_CONTINUE;
    }
    public int msgGrantFactionStanding(obj_id self, dictionary params) throws InterruptedException
    {
        String faction = params.getString("faction");
        obj_id master_object = params.getObjId("battlefield");
        float standing = params.getFloat("standing");
        String player_faction = battlefield.getPlayerTeamFaction(self);
        obj_id player_battlefield = battlefield.getBattlefieldEntered(self);
        if (player_faction == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (player_battlefield == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (faction.equals(player_faction))
        {
            if (master_object == player_battlefield)
            {
                factions.addFactionStanding(self, faction, standing);
            }
            LOG("LOG_CHANNEL", self + " ->Your " + faction + " faction standing has been increased by " + standing + " for your battlefield deeds.");
            sendSystemMessageTestingOnly(self, "Your " + faction + " faction standing has been increased by " + standing + " for your battlefield deeds.");
        }
        return SCRIPT_CONTINUE;
    }
    public int msgAddBattlefieldWaypoint(obj_id self, dictionary params) throws InterruptedException
    {
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id structure = params.getObjId("structure");
        if (structure.isLoaded())
        {
            battlefield.addBattlefieldWaypoint(self, structure);
            LOG("LOG_CHANNEL", "player_battlefield::msgAddBattlefieldWaypoint -- adding waypoint to player " + self + " for " + structure);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgSelectFactionPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgSelectFactionPrompt -- " + self);
        obj_id master_object = params.getObjId("master_object");
        if (isIdValid(master_object))
        {
            LOG("LOG_CHANNEL", "battlefield ->" + master_object);
            String[] factions_allowed = battlefield.getFactionsAllowed(master_object);
            String prompt = "You are about to enter an active battlefield.  You must select a faction with which to ally yourself for the duration of the battle.";
            sui.listbox(self, self, prompt, sui.OK_CANCEL, "Select Faction", factions_allowed, "msgBattlefieldFactionSelected");
            utils.setScriptVar(self, battlefield.VAR_SELECTING_FACTION, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgVerifyFactionPrompt(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::msgVerifyFactionPrompt -- " + self);
        obj_id master_object = params.getObjId("master_object");
        String faction = params.getString("faction");
        if (isIdValid(master_object))
        {
            String prompt = "You are about to enter an active battlefield. Would you like to aid the " + faction + " faction?";
            sui.msgbox(self, self, prompt, sui.YES_NO, "msgBattlefieldFactionSelected");
            utils.setScriptVar(self, battlefield.VAR_SELECTING_FACTION, 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int msgBattlefieldStatus(obj_id self, dictionary params) throws InterruptedException
    {
        region bf = battlefield.getBattlefield(self);
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        utils.removeScriptVarTree(self, "battlefield.status");
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_OK)
        {
            if (!isIdValid(master_object))
            {
                sendSystemMessageTestingOnly(self, "Cannot found the battlefield. Cancelling refresh.");
            }
            else 
            {
                queueCommand(self, (-567736575), null, "", COMMAND_PRIORITY_DEFAULT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int battlefieldStatus(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::BattlefieldStatus");
        if (utils.hasScriptVar(self, "battlefield.status.pid"))
        {
            int oldpid = utils.getIntScriptVar(self, "battlefield.status.pid");
            sui.closeSUI(self, oldpid);
            utils.removeScriptVarTree(self, "battlefield.status");
        }
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            LOG("LOG_CHANNEL", self + " ->You must be at a battlefield to do that.");
            sendSystemMessageTestingOnly(self, "You must be at a battlefield to do that.");
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (!isIdValid(master_object))
        {
            LOG("LOG_CHANNEL", self + " ->You must be at a battlefield to do that.");
            sendSystemMessageTestingOnly(self, "You must be at a battlefield to do that.");
            return SCRIPT_CONTINUE;
        }
        if (params.equals("game"))
        {
            if (!battlefield.isBattlefieldActive(bf))
            {
                LOG("LOG_CHANNEL", self + " ->The battlefield must be active in order to get statistics.");
                sendSystemMessageTestingOnly(self, "The battlefield must be active in order to get statistics.");
            }
            else 
            {
                master_object = battlefield.getMasterObjectFromRegion(bf);
                dictionary stat_params = new dictionary();
                stat_params.put("player", self);
                messageTo(master_object, "msgGameStats", stat_params, 0.0f, true);
            }
            return SCRIPT_CONTINUE;
        }
        else if (params.equals("history"))
        {
            master_object = battlefield.getMasterObjectFromRegion(bf);
            dictionary stat_params = new dictionary();
            stat_params.put("player", self);
            messageTo(master_object, "msgGameStatHistory", stat_params, 0.0f, true);
            return SCRIPT_CONTINUE;
        }
        String name = battlefield.getBattlefieldLocalizedName(master_object);
        int pvp_type = bf.getPvPType();
        String pvp_type_str;
        if (pvp_type == regions.PVP_REGION_TYPE_BATTLEFIELD_PVP)
        {
            pvp_type_str = "Player vs Player";
        }
        else 
        {
            pvp_type_str = "Player vs Environment";
        }
        String game_type = battlefield.getBattlefieldGameType(bf);
        String status;
        int time;
        String time_starting;
        Vector num_factions = new Vector();
        num_factions.setSize(0);
        if (battlefield.isBattlefieldActive(bf))
        {
            status = "Active";
            time = battlefield.getGameTimeRemaining(bf);
            time_starting = "ending in";
            String[] factions_allowed = battlefield.getFactionsAllowed(bf);
            for (int i = 0; i < factions_allowed.length; i++)
            {
                obj_id[] faction_team = battlefield.getFactionTeam(bf, factions_allowed[i]);
                num_factions = utils.addElement(num_factions, factions_allowed[i] + " Team: " + faction_team.length);
            }
        }
        else 
        {
            int next_game = battlefield.getNextGameTime(bf);
            if (next_game == -9999)
            {
                status = "Disabled";
                time = -9999;
                time_starting = "";
            }
            else 
            {
                status = "Inactive";
                time = (next_game - getGameTime());
                time_starting = "starting in";
            }
        }
        String time_message;
        if (time > 0)
        {
            int[] conv_time = player_structure.convertSecondsTime(time);
            time_message = player_structure.assembleTimeRemaining(conv_time);
        }
        else 
        {
            time_message = "0 seconds.";
        }
        Vector dsrc = new Vector();
        dsrc.setSize(0);
        dsrc = utils.addElement(dsrc, "Battlefield Name: " + name);
        dsrc = utils.addElement(dsrc, "Battlefield Type: " + pvp_type_str);
        dsrc = utils.addElement(dsrc, "Objective: " + game_type);
        if (time == -9999)
        {
            dsrc = utils.addElement(dsrc, "Status: " + status);
        }
        else 
        {
            dsrc = utils.addElement(dsrc, "Status: " + status + " (" + time_starting + " " + time_message + ")");
        }
        dsrc = utils.concatArrays(dsrc, num_factions);
        int pid = sui.listbox(self, self, "Battlefield Status", sui.REFRESH_CANCEL, "Battlefield Status", dsrc, "msgBattlefieldStatus");
        if (pid > -1)
        {
            utils.setScriptVar(self, "battlefield.status.pid", pid);
            utils.setScriptVar(self, "battlefield.status.target", master_object);
        }
        return SCRIPT_CONTINUE;
    }
    public int placeBattlefieldStructure(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::placeBattlefieldStructure");
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            LOG("LOG_CHANNEL", self + " ->You must be in a battlefield to do that.");
            sendSystemMessageTestingOnly(self, "You must be in a battlefield to do that.");
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (!battlefield.isBattlefieldActive(master_object))
        {
            LOG("LOG_CHANNEL", self + " ->The battlefield must be active in order to do that.");
            sendSystemMessageTestingOnly(self, "The battlefield must be active in order to do that.");
            return SCRIPT_CONTINUE;
        }
        String faction = battlefield.getPlayerTeamFaction(self);
        if (!battlefield.canBuildBattlefieldStructure(master_object, self))
        {
            return SCRIPT_CONTINUE;
        }
        String[] buildable_structures = battlefield.getBuildableStructures(master_object, faction);
        if (buildable_structures == null)
        {
            LOG("LOG_CHANNEL", self + " ->You cannot build on this battlefield.");
            sendSystemMessageTestingOnly(self, "You cannot build on this battlefield.");
            return SCRIPT_CONTINUE;
        }
        String[] dsrc = new String[buildable_structures.length];
        for (int i = 0; i < buildable_structures.length; i++)
        {
            dictionary structure_info = battlefield.getBuildableStructureStats(master_object, buildable_structures[i]);
            int cost = structure_info.getInt("cost");
            dsrc[i] = buildable_structures[i] + " (Cost: " + cost + ")";
        }
        int build_points = battlefield.getFactionBuildPoints(master_object, faction);
        String points = "points";
        if (build_points == 1)
        {
            points = "point";
        }
        String prompt = "Select a structure to build.\n(" + build_points + " build " + points + " remaining.)";
        sui.listbox(self, self, prompt, sui.OK_CANCEL, "Battlefield Constructor", dsrc, "msgBuildStructureSelected");
        return SCRIPT_CONTINUE;
    }
    public int purchaseReinforcement(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::purchaseReinforcement");
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            LOG("LOG_CHANNEL", self + " ->You must be in a battlefield to do that.");
            sendSystemMessageTestingOnly(self, "You must be in a battlefield to do that.");
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (target == null || target == obj_id.NULL_ID)
        {
            target = getLookAtTarget(self);
            if (target == null || target == obj_id.NULL_ID)
            {
                LOG("LOG_CHANNEL", self + " ->From which installation do you wish to purchase reinforcements?");
                sendSystemMessageTestingOnly(self, "From which installation do you wish to purchase reinforcements?");
                return SCRIPT_CONTINUE;
            }
        }
        if (!battlefield.canBuildReinforcement(target))
        {
            LOG("LOG_CHANNEL", self + " ->Reinforcements are unavailable from that.");
            sendSystemMessageTestingOnly(self, "Reinforcements are unavailable from that.");
            return SCRIPT_CONTINUE;
        }
        location loc_player = getLocation(self);
        location loc_target = getLocation(target);
        if (loc_player.distance(loc_target) > battlefield.REINFORCEMENT_RANGE)
        {
            LOG("LOG_CHANNEL", self + " ->You are too far away from the installation.");
            sendSystemMessageTestingOnly(self, "You are too far away from the installation.");
            return SCRIPT_CONTINUE;
        }
        if (battlefield.buildReinforcement(master_object, target, self) == null)
        {
            LOG("LOG_CHANNEL", self + " ->Reinforcement request denied.");
            sendSystemMessageTestingOnly(self, "Reinforcement request denied.");
        }
        else 
        {
            LOG("LOG_CHANNEL", self + " ->Your reinforcements have arrived.");
            sendSystemMessageTestingOnly(self, "Your reinforcements have arrived.");
        }
        return SCRIPT_CONTINUE;
    }
    public int repairBattlefieldStructure(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "player_battlefield::repairBattlefieldStructure");
        region bf = battlefield.getBattlefield(self);
        if (bf == null)
        {
            LOG("LOG_CHANNEL", self + " ->You must be in a battlefield to do that.");
            sendSystemMessageTestingOnly(self, "You must be in a battlefield to do that.");
            return SCRIPT_CONTINUE;
        }
        obj_id master_object = battlefield.getMasterObjectFromRegion(bf);
        if (target == null || target == obj_id.NULL_ID)
        {
            target = getLookAtTarget(self);
            if (target == null || target == obj_id.NULL_ID)
            {
                LOG("LOG_CHANNEL", self + " ->Which structure do you wish to repair?");
                sendSystemMessageTestingOnly(self, "Which structure do you wish to repair?");
                return SCRIPT_CONTINUE;
            }
        }
        location loc_player = getLocation(self);
        location loc_target = getLocation(target);
        if (loc_player.distance(loc_target) > battlefield.REINFORCEMENT_RANGE)
        {
            LOG("LOG_CHANNEL", self + " ->You are too far away from the structure.");
            sendSystemMessageTestingOnly(self, "You are too far away from the structure.");
            return SCRIPT_CONTINUE;
        }
        battlefield.repairBattlefieldStructure(master_object, self, target);
        return SCRIPT_CONTINUE;
    }
    public void cleanPlayer(obj_id player) throws InterruptedException
    {
        if (player == null || player == obj_id.NULL_ID)
        {
            return;
        }
        if (hasObjVar(player, battlefield.VAR_WAYPOINTS))
        {
            obj_id[] waypoint_list = getObjIdArrayObjVar(player, battlefield.VAR_WAYPOINTS);
            if (waypoint_list != null)
            {
                for (int i = 0; i < waypoint_list.length; i++)
                {
                    obj_id waypoint = waypoint_list[i];
                    LOG("LOG_CHANNEL", "player_battlefield::cleanPlayer -- waypoint " + waypoint + "removed.");
                    if (isIdValid(waypoint))
                    {
                        destroyWaypointInDatapad(waypoint, player);
                    }
                }
            }
        }
        removeObjVar(player, battlefield.VAR_BATTLEFIELD);
        detachScript(player, battlefield.SCRIPT_PLAYER_BATTLEFIELD);
        return;
    }
}
